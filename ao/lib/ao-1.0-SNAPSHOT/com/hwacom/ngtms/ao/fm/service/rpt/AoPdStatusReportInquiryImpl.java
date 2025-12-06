/*     */ package com.hwacom.ngtms.ao.fm.service.rpt;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.ao.util.AoRptValueFormatter;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceLocationMappingConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.repository.DeviceLocationMappingConfigRepository;
/*     */ import com.hwacom.ngtms.c.fm.repository.DeviceTcConfigRepository;
/*     */ import com.hwacom.ngtms.c.shared.Direction;
/*     */ import com.hwacom.ngtms.common.rpt.ReportInquiry;
/*     */ import com.hwacom.ngtms.common.shared.PreviewGridData;
/*     */ import com.hwacom.ngtms.common.shared.PreviewGridHeader;
/*     */ import com.hwacom.ngtms.common.shared.PreviewGridRow;
/*     */ import com.hwacom.ngtms.pd.fm.model.PdConfig;
/*     */ import com.hwacom.ngtms.pd.fm.model.PdLoopStatus;
/*     */ import com.hwacom.ngtms.pd.fm.model.PdStatus;
/*     */ import com.hwacom.ngtms.pd.fm.repository.PdConfigRepository;
/*     */ import com.hwacom.ngtms.pd.fm.repository.PdStatusRepository;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.data.domain.Sort;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class AoPdStatusReportInquiryImpl
/*     */   implements ReportInquiry
/*     */ {
/*  45 */   private static Logger logger = LoggerFactory.getLogger(AoPdStatusReportInquiryImpl.class);
/*     */   
/*     */   @Autowired
/*     */   private DeviceTcConfigRepository deviceTcConfigRepository;
/*     */   
/*     */   @Autowired
/*     */   private PdConfigRepository pdConfigRepository;
/*     */   @Autowired
/*     */   private PdStatusRepository pdStatusRepository;
/*     */   @Autowired
/*     */   private DeviceLocationMappingConfigRepository deviceLocationMappingConfigRepository;
/*     */   @Autowired
/*     */   private MessageSourceExt messageSourceExt;
/*     */   @Autowired
/*     */   private AoRptValueFormatter formatter;
/*     */   
/*     */   public List<Map<String, Object>> inquire(Map<String, Object> inputParameter) {
/*  62 */     if (!inputParameter.containsKey("devices")) {
/*  63 */       logger.warn("Get inputParameter: devices fail! InputParameter: '{}'", inputParameter);
/*  64 */       return Collections.emptyList();
/*     */     } 
/*  66 */     if (!inputParameter.containsKey("mileage")) {
/*  67 */       logger.warn("Get inputParameter: mileage fail! InputParameter: '{}'", inputParameter);
/*  68 */       return Collections.emptyList();
/*     */     } 
/*  70 */     if (!inputParameter.containsKey("direction")) {
/*  71 */       logger.warn("Get inputParameter: direction fail! InputParameter: '{}'", inputParameter);
/*  72 */       return Collections.emptyList();
/*     */     } 
/*  74 */     if (!inputParameter.containsKey("status")) {
/*  75 */       logger.warn("Get inputParameter: status fail! InputParameter: '{}'", inputParameter);
/*  76 */       return Collections.emptyList();
/*     */     } 
/*  78 */     if (!inputParameter.containsKey("location")) {
/*  79 */       logger.warn("Get inputParameter: location fail! InputParameter: '{}'", inputParameter);
/*  80 */       return Collections.emptyList();
/*     */     } 
/*     */ 
/*     */     
/*  84 */     String deviceNamesStr = (String)inputParameter.get("devices");
/*  85 */     String directionStr = (String)inputParameter.get("direction");
/*  86 */     Direction direction = this.formatter.getBeforeFormattedDirection(directionStr);
/*  87 */     String mileageStr = (String)inputParameter.get("mileage");
/*  88 */     String lineNameStr = (String)inputParameter.get("lineName");
/*  89 */     String statusStr = (String)inputParameter.get("status");
/*  90 */     String locationStr = (String)inputParameter.get("location");
/*     */     
/*  92 */     logger.debug("deviceNamesStr:'{}'", deviceNamesStr);
/*  93 */     logger.debug("direction:'{}'", direction);
/*  94 */     logger.debug("mileageStr:'{}'", mileageStr);
/*  95 */     logger.debug("lineNameStr:'{}'", lineNameStr);
/*  96 */     logger.debug("statusStr:'{}'", statusStr);
/*  97 */     logger.debug("locationStr:'{}'", locationStr);
/*     */ 
/*     */     
/* 100 */     Boolean quiryByDevices = Boolean.valueOf(((String)inputParameter.get("condition")).equals("device"));
/*     */ 
/*     */ 
/*     */     
/* 104 */     Boolean showDeviceName = inputParameter.containsKey("showDeviceName") ? Boolean.valueOf((String)inputParameter.get("showDeviceName")) : null;
/*     */ 
/*     */     
/* 107 */     String userName = inputParameter.containsKey("userName") ? (String)inputParameter.get("userName") : "";
/*     */     
/* 109 */     List<String> mileage = Arrays.asList(mileageStr.split(","));
/* 110 */     List<Integer> mileages = new ArrayList<>();
/* 111 */     for (String value : mileage) {
/* 112 */       mileages.add(Integer.valueOf(value));
/*     */     }
/* 114 */     if (((Integer)mileages.get(0)).equals(Integer.valueOf(-1))) {
/* 115 */       mileages.clear();
/*     */     }
/* 117 */     Integer startMileage = Integer.valueOf(0);
/* 118 */     Integer endMileage = Integer.valueOf(0);
/* 119 */     String tableDataMileage = new String();
/* 120 */     if (mileageStr != "-1" && mileages.size() > 0) {
/* 121 */       startMileage = Integer.valueOf(((Integer)mileages.get(0)).intValue() * 1000 + ((Integer)mileages.get(1)).intValue());
/* 122 */       endMileage = Integer.valueOf(((Integer)mileages.get(2)).intValue() * 1000 + ((Integer)mileages.get(3)).intValue());
/* 123 */       String startMeter = String.valueOf(mileages.get(1));
/* 124 */       String endMeter = String.valueOf(mileages.get(3));
/* 125 */       if (((Integer)mileages.get(1)).intValue() < 100 && ((Integer)mileages.get(1)).intValue() > 9) {
/* 126 */         startMeter = "0" + startMeter;
/* 127 */       } else if (((Integer)mileages.get(1)).intValue() < 10) {
/* 128 */         startMeter = "00" + startMeter;
/*     */       } 
/*     */       
/* 131 */       if (((Integer)mileages.get(3)).intValue() < 100 && ((Integer)mileages.get(3)).intValue() > 9) {
/* 132 */         endMeter = "0" + endMeter;
/* 133 */       } else if (((Integer)mileages.get(3)).intValue() < 10) {
/* 134 */         endMeter = "00" + endMeter;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 142 */       tableDataMileage = String.valueOf(mileages.get(0)) + "K" + startMeter + "~" + String.valueOf(mileages.get(2)) + "K" + endMeter;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     List<String> deviceNames = Arrays.asList(deviceNamesStr.split(","));
/*     */ 
/*     */     
/* 151 */     String chartKey = (String)inputParameter.get("CHART_KEY");
/*     */     
/* 153 */     List<Map<String, Object>> resultMapArray = new ArrayList<>();
/*     */ 
/*     */     
/* 156 */     Map<String, Object> tableDataMap = new HashMap<>();
/*     */     
/* 158 */     Sort sort = Sort.by(new String[] { "deviceName", "lineId" });
/* 159 */     List<DeviceTcConfig> rptDatas = new ArrayList<>();
/* 160 */     List<DeviceTcConfig> rptConfigs = new ArrayList<>();
/*     */     
/* 162 */     if (quiryByDevices.booleanValue()) {
/* 163 */       rptConfigs = this.deviceTcConfigRepository.findByDeviceNames(deviceNames, sort);
/* 164 */       rptDatas.addAll(rptConfigs);
/*     */     } else {
/*     */       
/* 167 */       if (!lineNameStr.equals("-1") && direction == null && mileages.size() == 0) {
/* 168 */         rptConfigs = this.deviceTcConfigRepository.findByLine(lineNameStr, sort);
/*     */       
/*     */       }
/* 171 */       else if (!lineNameStr.equals("-1") && direction != null && mileages.size() == 0) {
/* 172 */         rptConfigs = this.deviceTcConfigRepository.findByLineAndDirection(lineNameStr, direction, sort);
/*     */       
/*     */       }
/* 175 */       else if (lineNameStr.equals("-1") && direction == null && mileages.size() > 0) {
/* 176 */         rptConfigs = this.deviceTcConfigRepository.findByMileageBetween(startMileage, endMileage, sort);
/*     */       
/*     */       }
/* 179 */       else if (!lineNameStr.equals("-1") && direction == null && mileages.size() > 0) {
/*     */         
/* 181 */         rptConfigs = this.deviceTcConfigRepository.findByLineAndMileageBetween(lineNameStr, startMileage, endMileage, sort);
/*     */ 
/*     */       
/*     */       }
/* 185 */       else if (!lineNameStr.equals("-1") && direction != null && mileages.size() > 0) {
/*     */         
/* 187 */         rptConfigs = this.deviceTcConfigRepository.findByLineAndDirectionAndMileageBetween(lineNameStr, direction, startMileage, endMileage, sort);
/*     */ 
/*     */       
/*     */       }
/* 191 */       else if (lineNameStr.equals("-1") && direction != null && mileages.size() == 0) {
/* 192 */         rptConfigs = this.deviceTcConfigRepository.findByDirection(direction, sort);
/*     */       
/*     */       }
/* 195 */       else if (lineNameStr.equals("-1") && direction != null && mileages.size() > 0) {
/*     */         
/* 197 */         rptConfigs = this.deviceTcConfigRepository.findByDirectionAndMileageBetween(direction, startMileage, endMileage, sort);
/*     */ 
/*     */       
/*     */       }
/* 201 */       else if (lineNameStr.equals("-1") && direction == null && mileages.size() == 0) {
/* 202 */         rptConfigs = this.deviceTcConfigRepository.findAll(sort);
/*     */       } 
/*     */       
/* 205 */       List<DeviceTcConfig> removedList = new ArrayList<>();
/* 206 */       for (DeviceTcConfig each : rptConfigs) {
/* 207 */         if (!each.getDeviceType().equals("PD")) {
/* 208 */           removedList.add(each);
/*     */         }
/*     */       } 
/* 211 */       rptConfigs.removeAll(removedList);
/*     */       
/* 213 */       List<String> devices = new ArrayList<>();
/* 214 */       for (DeviceTcConfig deviceConfig : rptConfigs) {
/* 215 */         devices.add(deviceConfig.getDeviceName());
/*     */       }
/*     */       
/* 218 */       List<DeviceTcConfig> locationCheckDatas = new ArrayList<>();
/* 219 */       if (locationStr.equals("全部")) {
/* 220 */         locationCheckDatas.addAll(rptConfigs);
/*     */       } else {
/*     */         
/* 223 */         List<DeviceLocationMappingConfig> locationDatas = this.deviceLocationMappingConfigRepository.findByLocationNameAndDeviceNames(locationStr, devices);
/*     */         
/* 225 */         for (DeviceLocationMappingConfig each : locationDatas) {
/* 226 */           IMap<String, DeviceTcConfig> iMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 227 */           locationCheckDatas.add(iMap.get(each.getDeviceName()));
/*     */         } 
/*     */       } 
/*     */       
/* 231 */       List<DeviceTcConfig> offlineData = new ArrayList<>();
/* 232 */       List<DeviceTcConfig> phaseFailureData = new ArrayList<>();
/* 233 */       List<DeviceTcConfig> offPowerData = new ArrayList<>();
/* 234 */       List<DeviceTcConfig> loopAbnormalData = new ArrayList<>();
/* 235 */       for (DeviceTcConfig each : locationCheckDatas) {
/* 236 */         List<Boolean> checkStatusList = checkAbnormal(each);
/* 237 */         if (statusStr.equals("斷線") && ((Boolean)checkStatusList.get(0)).booleanValue() == true) {
/* 238 */           offlineData.add(each); continue;
/* 239 */         }  if (statusStr.equals("欠相") && ((Boolean)checkStatusList.get(1)).booleanValue() == true) {
/* 240 */           phaseFailureData.add(each); continue;
/* 241 */         }  if (statusStr.equals("斷電") && ((Boolean)checkStatusList.get(2)).booleanValue() == true) {
/* 242 */           offPowerData.add(each); continue;
/* 243 */         }  if (statusStr.equals("分迴路異常") && ((Boolean)checkStatusList.get(3)).booleanValue() == true) {
/* 244 */           loopAbnormalData.add(each); continue;
/* 245 */         }  if (statusStr.equals("全部")) {
/* 246 */           rptDatas.add(each);
/*     */         }
/*     */       } 
/*     */       
/* 250 */       if (statusStr.equals("斷線")) {
/* 251 */         rptDatas.addAll(offlineData);
/* 252 */       } else if (statusStr.equals("欠相")) {
/* 253 */         rptDatas.addAll(phaseFailureData);
/* 254 */       } else if (statusStr.equals("斷電")) {
/* 255 */         rptDatas.addAll(offPowerData);
/* 256 */       } else if (statusStr.equals("分迴路異常")) {
/* 257 */         rptDatas.addAll(loopAbnormalData);
/*     */       } 
/*     */     } 
/*     */     
/* 261 */     logger.debug("rptDatas.size():'{}'", Integer.valueOf(rptDatas.size()));
/* 262 */     HashMap[] arrayOfHashMap = new HashMap[rptDatas.size()];
/* 263 */     if (rptDatas.size() == 0) {
/* 264 */       arrayOfHashMap = new HashMap[1];
/*     */     }
/* 266 */     int row = 0;
/* 267 */     IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 268 */     for (DeviceTcConfig d : rptDatas) {
/* 269 */       Map<String, Object> pdStatus = new HashMap<>();
/* 270 */       DeviceTcConfig device = (DeviceTcConfig)deviceMap.get(d.getDeviceName());
/* 271 */       pdStatus.put("deviceName", (device == null || device
/*     */           
/* 273 */           .getDisplayName() == null) ? d
/* 274 */           .getDeviceName() : device
/* 275 */           .getDisplayName());
/* 276 */       pdStatus.put("location", this.deviceLocationMappingConfigRepository
/*     */ 
/*     */           
/* 279 */           .findByDeviceName(d.getDeviceName())
/* 280 */           .getLocationName());
/* 281 */       pdStatus.put("roadLine", this.formatter.getFormattedLineName(d.getLineId()));
/* 282 */       pdStatus.put("direction", this.formatter.getBeforeFormattedDirection(d.getDirection()));
/* 283 */       pdStatus.put("milepost", 
/*     */           
/* 285 */           String.valueOf(d.getMilepost().intValue() / 1000) + "K" + String.valueOf(d.getMilepost().intValue() % 1000));
/* 286 */       pdStatus.put("memo", d.getMemo());
/* 287 */       PdConfig config = this.pdConfigRepository.findByDeviceName(d.getDeviceName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 294 */       PdStatus status = this.pdStatusRepository.findByDeviceName(d.getDeviceName()).stream().sorted(Comparator.<PdStatus, Comparable>comparing(PdStatus::getDataTime).reversed()).findFirst().get();
/* 295 */       pdStatus.put("meterNo", (config.getMeterNo() != null) ? config.getMeterNo() : "");
/* 296 */       pdStatus.put("area", (config.getArea() != null) ? config.getArea() : "");
/* 297 */       pdStatus.put("phone", (config.getPhone() != null) ? config.getPhone() : "");
/* 298 */       pdStatus.put("loopNo", String.valueOf(config.getLoopNo()));
/* 299 */       if (status.getConnectivity() != null && status.getConnectivity().intValue() == 0) {
/* 300 */         pdStatus.put("primaryR", (status.getPrimaryR().intValue() == 0) ? "正常" : "異常");
/* 301 */         pdStatus.put("primaryS", (status.getPrimaryS().intValue() == 0) ? "正常" : "異常");
/* 302 */         pdStatus.put("primaryT", (status.getPrimaryT().intValue() == 0) ? "正常" : "異常");
/* 303 */         pdStatus.put("secondaryR", (status.getSecondaryR().intValue() == 0) ? "正常" : "異常");
/* 304 */         pdStatus.put("secondaryS", (status.getSecondaryS().intValue() == 0) ? "正常" : "異常");
/* 305 */         pdStatus.put("secondaryT", (status.getSecondaryT().intValue() == 0) ? "正常" : "異常");
/* 306 */         pdStatus.put("doorOpen", (status.getDoorOpen().intValue() == 0) ? "關閉" : "開啟");
/*     */       } 
/* 308 */       pdStatus.put("connectivity", (status.getConnectivity().intValue() == 0) ? "連線" : "斷線");
/* 309 */       for (PdLoopStatus loop : status.getPdLoops()) {
/* 310 */         if (Integer.valueOf(loop.getLoopId()).intValue() <= config.getLoopNo().intValue()) {
/* 311 */           if (loop.getLoopId().equals("1")) {
/* 312 */             pdStatus.put("loop1", (loop.getStatus().intValue() == 0) ? "正常" : "異常"); continue;
/* 313 */           }  if (loop.getLoopId().equals("2")) {
/* 314 */             pdStatus.put("loop2", (loop.getStatus().intValue() == 0) ? "正常" : "異常"); continue;
/* 315 */           }  if (loop.getLoopId().equals("3")) {
/* 316 */             pdStatus.put("loop3", (loop.getStatus().intValue() == 0) ? "正常" : "異常"); continue;
/* 317 */           }  if (loop.getLoopId().equals("4")) {
/* 318 */             pdStatus.put("loop4", (loop.getStatus().intValue() == 0) ? "正常" : "異常"); continue;
/* 319 */           }  if (loop.getLoopId().equals("5")) {
/* 320 */             pdStatus.put("loop5", (loop.getStatus().intValue() == 0) ? "正常" : "異常");
/*     */           }
/*     */         } 
/*     */       } 
/* 324 */       arrayOfHashMap[row++] = (HashMap)pdStatus;
/*     */     } 
/*     */     
/* 327 */     tableDataMap.put("pdStatus", arrayOfHashMap);
/* 328 */     tableDataMap.put("CHART_KEY", chartKey);
/* 329 */     tableDataMap.put("showDeviceName", showDeviceName);
/* 330 */     tableDataMap.put("lineName", 
/* 331 */         !lineNameStr.equals("-1") ? this.formatter.getFormattedLineName(lineNameStr) : "全部");
/* 332 */     tableDataMap.put("direction", directionStr);
/* 333 */     tableDataMap.put("mileage", !tableDataMileage.equals("") ? tableDataMileage : "全部");
/* 334 */     tableDataMap.put("status", statusStr);
/* 335 */     tableDataMap.put("location", locationStr);
/* 336 */     tableDataMap.put("userName", userName);
/*     */     
/* 338 */     resultMapArray.add(tableDataMap);
/*     */     
/* 340 */     return resultMapArray;
/*     */   }
/*     */ 
/*     */   
/*     */   public PreviewGridData inquirePreviewGridData(Map<String, Object> inputParameter) {
/* 345 */     List<Map<String, Object>> reportMap = inquire(inputParameter);
/* 346 */     Map[] arrayOfMap = (Map[])((Map)reportMap.get(0)).get("pdStatus");
/* 347 */     if (reportMap.size() == 0) {
/* 348 */       return new PreviewGridData();
/*     */     }
/*     */ 
/*     */     
/* 352 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/* 353 */     List<PreviewGridRow> rows = new ArrayList<>();
/* 354 */     for (Map<String, Object> record : arrayOfMap) {
/*     */       
/* 356 */       record.put("startTime", dateFormat.format(record.get("startTime")));
/*     */       
/* 358 */       PreviewGridRow row = new PreviewGridRow();
/* 359 */       row.setId(UUID.randomUUID().toString());
/* 360 */       row.setColumnValMap(record);
/* 361 */       rows.add(row);
/*     */     } 
/*     */     
/* 364 */     PreviewGridData result = new PreviewGridData();
/* 365 */     result.setHeaders(genPreviewGridHeaders(reportMap));
/* 366 */     result.setRows(rows);
/* 367 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Boolean> checkAbnormal(DeviceTcConfig config) {
/* 377 */     PdStatus status = this.pdStatusRepository.findByDeviceName(config.getDeviceName()).stream().sorted(Comparator.<PdStatus, Comparable>comparing(PdStatus::getDataTime).reversed()).findFirst().get();
/* 378 */     List<Boolean> list = new ArrayList<>();
/* 379 */     Boolean connectivityStatus = Boolean.valueOf(false);
/* 380 */     Boolean primaryStatus = Boolean.valueOf(false);
/* 381 */     Boolean secondaryStatus = Boolean.valueOf(false);
/* 382 */     Boolean loopStatus = Boolean.valueOf(false);
/* 383 */     if (status.getConnectivity().intValue() == 0) {
/* 384 */       if ((status.getPrimaryR().intValue() == 1 || status
/* 385 */         .getPrimaryS().intValue() == 1 || status
/* 386 */         .getPrimaryT().intValue() == 1 || status
/* 387 */         .getSecondaryR().intValue() == 1 || status
/* 388 */         .getSecondaryS().intValue() == 1 || status
/* 389 */         .getSecondaryT().intValue() == 1) && (status
/* 390 */         .getPrimaryR().intValue() != 1 || status
/* 391 */         .getPrimaryS().intValue() != 1 || status
/* 392 */         .getPrimaryT().intValue() != 1) && (status
/* 393 */         .getSecondaryR().intValue() != 1 || status
/* 394 */         .getSecondaryS().intValue() != 1 || status
/* 395 */         .getSecondaryT().intValue() != 1)) {
/* 396 */         primaryStatus = Boolean.valueOf(true);
/*     */       }
/* 398 */       if ((status.getPrimaryR().intValue() == 1 && status.getPrimaryS().intValue() == 1 && status.getPrimaryT().intValue() == 1) || (status
/* 399 */         .getSecondaryR().intValue() == 1 && status
/* 400 */         .getSecondaryS().intValue() == 1 && status
/* 401 */         .getSecondaryT().intValue() == 1)) {
/* 402 */         secondaryStatus = Boolean.valueOf(true);
/*     */       }
/* 404 */       for (PdLoopStatus loop : status.getPdLoops()) {
/* 405 */         if (loop.getStatus() != null && loop.getStatus().intValue() == 1) {
/* 406 */           loopStatus = Boolean.valueOf(true);
/*     */         }
/*     */       } 
/*     */     } else {
/* 410 */       connectivityStatus = Boolean.valueOf(true);
/*     */     } 
/* 412 */     list.add(connectivityStatus);
/* 413 */     list.add(primaryStatus);
/* 414 */     list.add(secondaryStatus);
/* 415 */     list.add(loopStatus);
/* 416 */     return list;
/*     */   }
/*     */   
/*     */   private List<PreviewGridHeader> genPreviewGridHeaders(List<Map<String, Object>> reportMap) {
/* 420 */     List<PreviewGridHeader> headers = new ArrayList<>();
/*     */     
/* 422 */     PreviewGridHeader h1 = new PreviewGridHeader();
/* 423 */     h1.setColumnId("deviceName");
/* 424 */     h1.setHeader(this.messageSourceExt.getMessage("設備名稱"));
/* 425 */     h1.setWidth(Integer.valueOf(100));
/* 426 */     headers.add(h1);
/*     */     
/* 428 */     PreviewGridHeader h2 = new PreviewGridHeader();
/* 429 */     h2.setColumnId("location");
/* 430 */     h2.setHeader(this.messageSourceExt.getMessage("機房名稱"));
/* 431 */     h2.setWidth(Integer.valueOf(300));
/* 432 */     headers.add(h2);
/*     */     
/* 434 */     PreviewGridHeader h3 = new PreviewGridHeader();
/* 435 */     h3.setColumnId("roadLine");
/* 436 */     h3.setHeader(this.messageSourceExt.getMessage("路線"));
/* 437 */     h3.setWidth(Integer.valueOf(100));
/* 438 */     headers.add(h3);
/*     */     
/* 440 */     PreviewGridHeader h4 = new PreviewGridHeader();
/* 441 */     h4.setColumnId("direction");
/* 442 */     h4.setHeader(this.messageSourceExt.getMessage("方向"));
/* 443 */     h4.setWidth(Integer.valueOf(100));
/* 444 */     headers.add(h4);
/*     */     
/* 446 */     PreviewGridHeader h5 = new PreviewGridHeader();
/* 447 */     h5.setColumnId("milepost");
/* 448 */     h5.setHeader(this.messageSourceExt.getMessage("里程"));
/* 449 */     h5.setWidth(Integer.valueOf(100));
/* 450 */     headers.add(h5);
/*     */     
/* 452 */     PreviewGridHeader h6 = new PreviewGridHeader();
/* 453 */     h6.setColumnId("milepost");
/* 454 */     h6.setHeader(this.messageSourceExt.getMessage("里程"));
/* 455 */     h6.setWidth(Integer.valueOf(100));
/* 456 */     headers.add(h6);
/*     */     
/* 458 */     PreviewGridHeader h7 = new PreviewGridHeader();
/* 459 */     h7.setColumnId("meterNo");
/* 460 */     h7.setHeader(this.messageSourceExt.getMessage("電錶號"));
/* 461 */     h7.setWidth(Integer.valueOf(100));
/* 462 */     headers.add(h7);
/*     */     
/* 464 */     PreviewGridHeader h8 = new PreviewGridHeader();
/* 465 */     h8.setColumnId("area");
/* 466 */     h8.setHeader(this.messageSourceExt.getMessage("所屬區處"));
/* 467 */     h8.setWidth(Integer.valueOf(100));
/* 468 */     headers.add(h8);
/*     */     
/* 470 */     PreviewGridHeader h9 = new PreviewGridHeader();
/* 471 */     h9.setColumnId("phone");
/* 472 */     h9.setHeader(this.messageSourceExt.getMessage("連絡電話"));
/* 473 */     h9.setWidth(Integer.valueOf(100));
/* 474 */     headers.add(h9);
/*     */     
/* 476 */     PreviewGridHeader h10 = new PreviewGridHeader();
/* 477 */     h10.setColumnId("primaryR");
/* 478 */     h10.setHeader(this.messageSourceExt.getMessage("R"));
/* 479 */     h10.setWidth(Integer.valueOf(100));
/* 480 */     headers.add(h10);
/*     */     
/* 482 */     PreviewGridHeader h11 = new PreviewGridHeader();
/* 483 */     h11.setColumnId("primaryS");
/* 484 */     h11.setHeader(this.messageSourceExt.getMessage("S"));
/* 485 */     h11.setWidth(Integer.valueOf(100));
/* 486 */     headers.add(h11);
/*     */     
/* 488 */     PreviewGridHeader h12 = new PreviewGridHeader();
/* 489 */     h12.setColumnId("primaryT");
/* 490 */     h12.setHeader(this.messageSourceExt.getMessage("T"));
/* 491 */     h12.setWidth(Integer.valueOf(100));
/* 492 */     headers.add(h12);
/*     */     
/* 494 */     PreviewGridHeader h13 = new PreviewGridHeader();
/* 495 */     h13.setColumnId("secondaryR");
/* 496 */     h13.setHeader(this.messageSourceExt.getMessage("R"));
/* 497 */     h13.setWidth(Integer.valueOf(100));
/* 498 */     headers.add(h13);
/*     */     
/* 500 */     PreviewGridHeader h14 = new PreviewGridHeader();
/* 501 */     h14.setColumnId("secondaryS");
/* 502 */     h14.setHeader(this.messageSourceExt.getMessage("S"));
/* 503 */     h14.setWidth(Integer.valueOf(100));
/* 504 */     headers.add(h14);
/*     */     
/* 506 */     PreviewGridHeader h15 = new PreviewGridHeader();
/* 507 */     h15.setColumnId("secondaryT");
/* 508 */     h15.setHeader(this.messageSourceExt.getMessage("T"));
/* 509 */     h15.setWidth(Integer.valueOf(100));
/* 510 */     headers.add(h15);
/*     */     
/* 512 */     PreviewGridHeader h16 = new PreviewGridHeader();
/* 513 */     h16.setColumnId("loopNo");
/* 514 */     h16.setHeader(this.messageSourceExt.getMessage("總數"));
/* 515 */     h16.setWidth(Integer.valueOf(100));
/* 516 */     headers.add(h16);
/*     */     
/* 518 */     PreviewGridHeader h17 = new PreviewGridHeader();
/* 519 */     h17.setColumnId("loop1");
/* 520 */     h17.setHeader(this.messageSourceExt.getMessage("1"));
/* 521 */     h17.setWidth(Integer.valueOf(100));
/* 522 */     headers.add(h17);
/*     */     
/* 524 */     PreviewGridHeader h18 = new PreviewGridHeader();
/* 525 */     h18.setColumnId("loop2");
/* 526 */     h18.setHeader(this.messageSourceExt.getMessage("2"));
/* 527 */     h18.setWidth(Integer.valueOf(100));
/* 528 */     headers.add(h18);
/*     */     
/* 530 */     PreviewGridHeader h19 = new PreviewGridHeader();
/* 531 */     h19.setColumnId("loop3");
/* 532 */     h19.setHeader(this.messageSourceExt.getMessage("3"));
/* 533 */     h19.setWidth(Integer.valueOf(100));
/* 534 */     headers.add(h19);
/*     */     
/* 536 */     PreviewGridHeader h20 = new PreviewGridHeader();
/* 537 */     h20.setColumnId("loop4");
/* 538 */     h20.setHeader(this.messageSourceExt.getMessage("4"));
/* 539 */     h20.setWidth(Integer.valueOf(100));
/* 540 */     headers.add(h20);
/*     */     
/* 542 */     PreviewGridHeader h21 = new PreviewGridHeader();
/* 543 */     h21.setColumnId("loop5");
/* 544 */     h21.setHeader(this.messageSourceExt.getMessage("5"));
/* 545 */     h21.setWidth(Integer.valueOf(100));
/* 546 */     headers.add(h21);
/*     */     
/* 548 */     PreviewGridHeader h22 = new PreviewGridHeader();
/* 549 */     h22.setColumnId("doorOpen");
/* 550 */     h22.setHeader(this.messageSourceExt.getMessage("箱門"));
/* 551 */     h22.setWidth(Integer.valueOf(100));
/* 552 */     headers.add(h22);
/*     */     
/* 554 */     PreviewGridHeader h23 = new PreviewGridHeader();
/* 555 */     h23.setColumnId("connectivity");
/* 556 */     h23.setHeader(this.messageSourceExt.getMessage("狀態"));
/* 557 */     h23.setWidth(Integer.valueOf(100));
/* 558 */     headers.add(h23);
/*     */     
/* 560 */     PreviewGridHeader h24 = new PreviewGridHeader();
/* 561 */     h24.setColumnId("memo");
/* 562 */     h24.setHeader(this.messageSourceExt.getMessage("備註"));
/* 563 */     h24.setWidth(Integer.valueOf(100));
/* 564 */     headers.add(h24);
/*     */     
/* 566 */     return headers;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\rpt\AoPdStatusReportInquiryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */