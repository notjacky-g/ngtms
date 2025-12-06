/*     */ package com.hwacom.ngtms.ao.fm.service.rpt;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.alarm.fm.model.AlarmLog;
/*     */ import com.hwacom.ngtms.alarm.fm.repository.AlarmLogRepository;
/*     */ import com.hwacom.ngtms.ao.shared.AlarmLogData;
/*     */ import com.hwacom.ngtms.ao.util.AoRptValueFormatter;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.AlarmSubTypeConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceLocationMappingConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.repository.AlarmSubTypeConfigRepository;
/*     */ import com.hwacom.ngtms.c.fm.repository.DeviceLocationMappingConfigRepository;
/*     */ import com.hwacom.ngtms.c.shared.AlarmLogEventContext;
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
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
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
/*     */ public class AoPdAlarmReportInquiryImpl
/*     */   implements ReportInquiry
/*     */ {
/*  54 */   private static Logger logger = LoggerFactory.getLogger(AoPdAlarmReportInquiryImpl.class);
/*     */   
/*     */   private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
/*     */   
/*     */   @Autowired
/*     */   private AlarmSubTypeConfigRepository alarmSubTypeConfigRepository;
/*     */   
/*     */   @Autowired
/*     */   private AlarmLogRepository alarmLogRepository;
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
/*  75 */     if (!inputParameter.containsKey("devices")) {
/*  76 */       logger.warn("Get inputParameter: devices fail! InputParameter: '{}'", inputParameter);
/*  77 */       return Collections.emptyList();
/*     */     } 
/*  79 */     if (!inputParameter.containsKey("mileage")) {
/*  80 */       logger.warn("Get inputParameter: mileage fail! InputParameter: '{}'", inputParameter);
/*  81 */       return Collections.emptyList();
/*     */     } 
/*  83 */     if (!inputParameter.containsKey("direction")) {
/*  84 */       logger.warn("Get inputParameter: direction fail! InputParameter: '{}'", inputParameter);
/*  85 */       return Collections.emptyList();
/*     */     } 
/*  87 */     if (!inputParameter.containsKey("status")) {
/*  88 */       logger.warn("Get inputParameter: status fail! InputParameter: '{}'", inputParameter);
/*  89 */       return Collections.emptyList();
/*     */     } 
/*  91 */     if (!inputParameter.containsKey("location")) {
/*  92 */       logger.warn("Get inputParameter: location fail! InputParameter: '{}'", inputParameter);
/*  93 */       return Collections.emptyList();
/*     */     } 
/*  95 */     if (!inputParameter.containsKey("startDateTime")) {
/*  96 */       logger.warn("Get inputParameter: startDateTime fail! InputParameter: '{}'", inputParameter);
/*  97 */       return Collections.emptyList();
/*     */     } 
/*  99 */     if (!inputParameter.containsKey("endDateTime")) {
/* 100 */       logger.warn("Get inputParameter: endDateTime fail! InputParameter: '{}'", inputParameter);
/* 101 */       return Collections.emptyList();
/*     */     } 
/*     */ 
/*     */     
/* 105 */     String deviceNamesStr = (String)inputParameter.get("devices");
/* 106 */     String startDateTimeStr = (String)inputParameter.get("startDateTime");
/* 107 */     String endDateTimeStr = (String)inputParameter.get("endDateTime");
/* 108 */     String directionStr = (String)inputParameter.get("direction");
/* 109 */     Direction direction = this.formatter.getBeforeFormattedDirection(directionStr);
/* 110 */     String mileageStr = (String)inputParameter.get("mileage");
/* 111 */     String lineNameStr = (String)inputParameter.get("lineName");
/* 112 */     String statusStr = (String)inputParameter.get("status");
/* 113 */     String locationStr = (String)inputParameter.get("location");
/*     */     
/* 115 */     Boolean quiryByDevices = Boolean.valueOf(((String)inputParameter.get("condition")).equals("device"));
/*     */ 
/*     */ 
/*     */     
/* 119 */     Boolean showDeviceName = inputParameter.containsKey("showDeviceName") ? Boolean.valueOf((String)inputParameter.get("showDeviceName")) : null;
/*     */ 
/*     */     
/* 122 */     String userName = inputParameter.containsKey("userName") ? (String)inputParameter.get("userName") : "";
/*     */     
/* 124 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 125 */     Date startDateTime = null;
/* 126 */     Date endDateTime = null;
/*     */     try {
/* 128 */       startDateTime = dateFormat.parse(startDateTimeStr);
/* 129 */       endDateTime = dateFormat.parse(endDateTimeStr);
/* 130 */     } catch (ParseException e) {
/* 131 */       logger.warn("Parse date string failed! startDateTime:'{}', endDateTime:'{}'", new Object[] { startDateTimeStr, endDateTimeStr, e });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 136 */       return Collections.emptyList();
/*     */     } 
/*     */     
/* 139 */     List<String> mileage = Arrays.asList(mileageStr.split(","));
/* 140 */     List<Integer> mileages = new ArrayList<>();
/* 141 */     for (String value : mileage) {
/* 142 */       mileages.add(Integer.valueOf(value));
/*     */     }
/* 144 */     if (((Integer)mileages.get(0)).equals(Integer.valueOf(-1))) {
/* 145 */       mileages.clear();
/*     */     }
/* 147 */     Integer startMileage = Integer.valueOf(0);
/* 148 */     Integer endMileage = Integer.valueOf(0);
/* 149 */     String tableDataMileage = new String();
/* 150 */     if (mileageStr != "-1" && mileages.size() > 0) {
/* 151 */       startMileage = Integer.valueOf(((Integer)mileages.get(0)).intValue() * 1000 + ((Integer)mileages.get(1)).intValue());
/* 152 */       endMileage = Integer.valueOf(((Integer)mileages.get(2)).intValue() * 1000 + ((Integer)mileages.get(3)).intValue());
/* 153 */       String startMeter = String.valueOf(mileages.get(1));
/* 154 */       String endMeter = String.valueOf(mileages.get(3));
/* 155 */       if (((Integer)mileages.get(1)).intValue() < 100 && ((Integer)mileages.get(1)).intValue() > 9) {
/* 156 */         startMeter = "0" + startMeter;
/* 157 */       } else if (((Integer)mileages.get(1)).intValue() < 10) {
/* 158 */         startMeter = "00" + startMeter;
/*     */       } 
/*     */       
/* 161 */       if (((Integer)mileages.get(3)).intValue() < 100 && ((Integer)mileages.get(3)).intValue() > 9) {
/* 162 */         endMeter = "0" + endMeter;
/* 163 */       } else if (((Integer)mileages.get(3)).intValue() < 10) {
/* 164 */         endMeter = "00" + endMeter;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 172 */       tableDataMileage = String.valueOf(mileages.get(0)) + "K" + startMeter + "~" + String.valueOf(mileages.get(2)) + "K" + endMeter;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 178 */     List<String> deviceNames = Arrays.asList(deviceNamesStr.split(","));
/*     */ 
/*     */     
/* 181 */     String chartKey = (String)inputParameter.get("CHART_KEY");
/*     */     
/* 183 */     List<Map<String, Object>> resultMapArray = new ArrayList<>();
/*     */ 
/*     */     
/* 186 */     Map<String, Object> tableDataMap = new HashMap<>();
/*     */     
/* 188 */     List<String> idList = new ArrayList<>();
/* 189 */     for (AlarmSubTypeConfig each : this.alarmSubTypeConfigRepository.findByAlarmType("PD")) {
/* 190 */       idList.add(String.valueOf(each.getId()));
/*     */     }
/*     */     
/* 193 */     Sort sort = Sort.by(new String[] { "alarmSubType", "timestamp" });
/* 194 */     List<AlarmLogData> rptDatas = new ArrayList<>();
/* 195 */     List<AlarmLogData> logDatas = new ArrayList<>();
/*     */     
/* 197 */     List<AlarmLog> rptAlarmLogs = this.alarmLogRepository.findByTimestampBetweenAndAlarmSubTypeIds(startDateTime, endDateTime, idList, sort);
/*     */ 
/*     */ 
/*     */     
/* 201 */     if (quiryByDevices.booleanValue()) {
/* 202 */       for (AlarmLog each : rptAlarmLogs) {
/* 203 */         AlarmLogEventContext context = getContextData(each);
/* 204 */         if (deviceNames.contains(context.getDeviceName())) {
/* 205 */           rptDatas.add(getAlarmLogData(each, context));
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 210 */       if (!lineNameStr.equals("-1") && direction == null && mileages.size() == 0) {
/* 211 */         for (AlarmLog each : rptAlarmLogs) {
/* 212 */           AlarmLogEventContext context = getContextData(each);
/* 213 */           if (this.formatter.getFormattedLineName(context.getLineId()).equals(lineNameStr)) {
/* 214 */             logDatas.add(getAlarmLogData(each, context));
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 219 */       else if (!lineNameStr.equals("-1") && direction != null && mileages.size() == 0) {
/* 220 */         for (AlarmLog each : rptAlarmLogs) {
/* 221 */           AlarmLogEventContext context = getContextData(each);
/* 222 */           if (this.formatter.getFormattedLineName(context.getLineId()).equals(lineNameStr) && context
/* 223 */             .getDirection() == direction) {
/* 224 */             logDatas.add(getAlarmLogData(each, context));
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 229 */       else if (lineNameStr.equals("-1") && direction == null && mileages.size() > 0) {
/* 230 */         for (AlarmLog each : rptAlarmLogs) {
/* 231 */           AlarmLogEventContext context = getContextData(each);
/* 232 */           if (context.getStartMileage().intValue() <= endMileage.intValue() && context.getEndMileage().intValue() >= startMileage.intValue()) {
/* 233 */             logDatas.add(getAlarmLogData(each, context));
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 238 */       else if (!lineNameStr.equals("-1") && direction == null && mileages.size() > 0) {
/* 239 */         for (AlarmLog each : rptAlarmLogs) {
/* 240 */           AlarmLogEventContext context = getContextData(each);
/* 241 */           if (this.formatter.getFormattedLineName(context.getLineId()).equals(lineNameStr) && context
/* 242 */             .getStartMileage().intValue() <= endMileage.intValue() && context
/* 243 */             .getEndMileage().intValue() >= startMileage.intValue()) {
/* 244 */             logDatas.add(getAlarmLogData(each, context));
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 249 */       else if (!lineNameStr.equals("-1") && direction != null && mileages.size() > 0) {
/* 250 */         for (AlarmLog each : rptAlarmLogs) {
/* 251 */           AlarmLogEventContext context = getContextData(each);
/* 252 */           if (this.formatter.getFormattedLineName(context.getLineId()).equals(lineNameStr) && context
/* 253 */             .getDirection() == direction && context
/* 254 */             .getStartMileage().intValue() <= endMileage.intValue() && context
/* 255 */             .getEndMileage().intValue() >= startMileage.intValue()) {
/* 256 */             logDatas.add(getAlarmLogData(each, context));
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 261 */       else if (lineNameStr.equals("-1") && direction != null && mileages.size() == 0) {
/* 262 */         for (AlarmLog each : rptAlarmLogs) {
/* 263 */           AlarmLogEventContext context = getContextData(each);
/* 264 */           if (context.getDirection() == direction) {
/* 265 */             logDatas.add(getAlarmLogData(each, context));
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 270 */       else if (lineNameStr.equals("-1") && direction != null && mileages.size() > 0) {
/* 271 */         for (AlarmLog each : rptAlarmLogs) {
/* 272 */           AlarmLogEventContext context = getContextData(each);
/* 273 */           if (context.getDirection() == direction && context
/* 274 */             .getStartMileage().intValue() <= endMileage.intValue() && context
/* 275 */             .getEndMileage().intValue() >= startMileage.intValue()) {
/* 276 */             logDatas.add(getAlarmLogData(each, context));
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 281 */       else if (lineNameStr.equals("-1") && direction == null && mileages.size() == 0) {
/* 282 */         for (AlarmLog each : rptAlarmLogs) {
/* 283 */           AlarmLogEventContext context = getContextData(each);
/* 284 */           logDatas.add(getAlarmLogData(each, context));
/*     */         } 
/*     */       } 
/*     */       
/* 288 */       List<String> devices = new ArrayList<>();
/* 289 */       for (AlarmLogData each : logDatas) {
/* 290 */         devices.add(each.getDeviceName());
/*     */       }
/*     */       
/* 293 */       List<AlarmLogData> locationCheckDatas = new ArrayList<>();
/* 294 */       if (locationStr.equals("全部")) {
/* 295 */         locationCheckDatas.addAll(logDatas);
/*     */       } else {
/*     */         
/* 298 */         List<DeviceLocationMappingConfig> locationDatas = this.deviceLocationMappingConfigRepository.findByLocationNameAndDeviceNames(locationStr, devices);
/*     */         
/* 300 */         List<String> deviceList = new ArrayList<>();
/* 301 */         for (DeviceLocationMappingConfig each : locationDatas) {
/* 302 */           deviceList.add(each.getDeviceName());
/*     */         }
/* 304 */         for (AlarmLog log : rptAlarmLogs) {
/* 305 */           AlarmLogEventContext context = getContextData(log);
/* 306 */           if (deviceList.contains(context.getDeviceName())) {
/* 307 */             locationCheckDatas.add(getAlarmLogData(log, context));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 312 */       List<AlarmLogData> offlineData = new ArrayList<>();
/* 313 */       List<AlarmLogData> phaseFailureData = new ArrayList<>();
/* 314 */       List<AlarmLogData> offPowerData = new ArrayList<>();
/* 315 */       List<AlarmLogData> loopAbnormalData = new ArrayList<>();
/* 316 */       for (AlarmLogData each : locationCheckDatas) {
/* 317 */         List<Boolean> checkStatusList = checkAbnormal(each);
/* 318 */         if (statusStr.equals("斷線") && ((Boolean)checkStatusList.get(0)).booleanValue() == true) {
/* 319 */           offlineData.add(each); continue;
/* 320 */         }  if (statusStr.equals("欠相") && ((Boolean)checkStatusList.get(1)).booleanValue() == true) {
/* 321 */           phaseFailureData.add(each); continue;
/* 322 */         }  if (statusStr.equals("斷電") && ((Boolean)checkStatusList.get(2)).booleanValue() == true) {
/* 323 */           offPowerData.add(each); continue;
/* 324 */         }  if (statusStr.equals("分迴路異常") && ((Boolean)checkStatusList.get(3)).booleanValue() == true) {
/* 325 */           loopAbnormalData.add(each); continue;
/* 326 */         }  if (statusStr.equals("全部")) {
/* 327 */           rptDatas.add(each);
/*     */         }
/*     */       } 
/*     */       
/* 331 */       if (statusStr.equals("斷線")) {
/* 332 */         rptDatas.addAll(offlineData);
/* 333 */       } else if (statusStr.equals("欠相")) {
/* 334 */         rptDatas.addAll(phaseFailureData);
/* 335 */       } else if (statusStr.equals("斷電")) {
/* 336 */         rptDatas.addAll(offPowerData);
/* 337 */       } else if (statusStr.equals("分迴路異常")) {
/* 338 */         rptDatas.addAll(loopAbnormalData);
/*     */       } 
/*     */     } 
/*     */     
/* 342 */     HashMap[] arrayOfHashMap = new HashMap[rptDatas.size()];
/*     */     
/* 344 */     int row = 0;
/* 345 */     IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 346 */     for (AlarmLogData d : rptDatas) {
/* 347 */       Map<String, Object> pdAlarm = new HashMap<>();
/* 348 */       DeviceTcConfig device = (DeviceTcConfig)deviceMap.get(d.getDeviceName());
/* 349 */       pdAlarm.put("deviceName", (device == null || device
/*     */           
/* 351 */           .getDisplayName() == null) ? d
/* 352 */           .getDeviceName() : device
/* 353 */           .getDisplayName());
/* 354 */       pdAlarm.put("location", this.deviceLocationMappingConfigRepository
/*     */ 
/*     */           
/* 357 */           .findByDeviceName(d.getDeviceName())
/* 358 */           .getLocationName());
/* 359 */       pdAlarm.put("roadLine", this.formatter.getFormattedLineName(d.getLineId()));
/* 360 */       pdAlarm.put("direction", this.formatter.getBeforeFormattedDirection(d.getDirection()));
/* 361 */       pdAlarm.put("startMileage", 
/*     */           
/* 363 */           String.valueOf(d.getStartMileage().intValue() / 1000) + "K" + 
/*     */           
/* 365 */           String.valueOf(d.getStartMileage().intValue() % 1000));
/* 366 */       pdAlarm.put("endMileage", 
/*     */           
/* 368 */           String.valueOf(d.getEndMileage().intValue() / 1000) + "K" + 
/*     */           
/* 370 */           String.valueOf(d.getEndMileage().intValue() % 1000));
/*     */       
/* 372 */       pdAlarm.put("memo", ((DeviceTcConfig)deviceMap.get(d.getDeviceName())).getMemo());
/*     */       
/* 374 */       PdConfig config = this.pdConfigRepository.findByDeviceName(d.getDeviceName());
/* 375 */       pdAlarm.put("meterNo", (config.getMeterNo() != null) ? config.getMeterNo() : "");
/* 376 */       pdAlarm.put("area", (config.getArea() != null) ? config.getArea() : "");
/* 377 */       pdAlarm.put("phone", (config.getPhone() != null) ? config.getPhone() : "");
/* 378 */       pdAlarm.put("dataTime", d.getTimestamp());
/* 379 */       pdAlarm.put("alarmType", this.alarmSubTypeConfigRepository
/*     */ 
/*     */           
/* 382 */           .findByAlarmSubTypeId(Integer.valueOf(d.getAlarmSubType()))
/* 383 */           .getShortName());
/* 384 */       pdAlarm.put("degree", String.valueOf(d.getDegree()));
/* 385 */       arrayOfHashMap[row++] = (HashMap)pdAlarm;
/*     */     } 
/*     */     
/* 388 */     tableDataMap.put("pdAlarm", arrayOfHashMap);
/* 389 */     tableDataMap.put("CHART_KEY", chartKey);
/* 390 */     tableDataMap.put("showDeviceName", showDeviceName);
/* 391 */     tableDataMap.put("lineName", 
/* 392 */         !lineNameStr.equals("-1") ? this.formatter.getFormattedLineName(lineNameStr) : "全部");
/* 393 */     tableDataMap.put("direction", directionStr);
/* 394 */     tableDataMap.put("mileage", !tableDataMileage.equals("") ? tableDataMileage : "全部");
/* 395 */     tableDataMap.put("status", statusStr);
/* 396 */     tableDataMap.put("location", locationStr);
/* 397 */     tableDataMap.put("startDate", startDateTime);
/* 398 */     tableDataMap.put("endDate", endDateTime);
/* 399 */     tableDataMap.put("userName", userName);
/*     */     
/* 401 */     resultMapArray.add(tableDataMap);
/*     */     
/* 403 */     return resultMapArray;
/*     */   }
/*     */ 
/*     */   
/*     */   public PreviewGridData inquirePreviewGridData(Map<String, Object> inputParameter) {
/* 408 */     List<Map<String, Object>> reportMap = inquire(inputParameter);
/* 409 */     Map[] arrayOfMap = (Map[])((Map)reportMap.get(0)).get("pdAlarm");
/* 410 */     if (reportMap.size() == 0) {
/* 411 */       return new PreviewGridData();
/*     */     }
/*     */ 
/*     */     
/* 415 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/* 416 */     List<PreviewGridRow> rows = new ArrayList<>();
/* 417 */     for (Map<String, Object> record : arrayOfMap) {
/*     */       
/* 419 */       record.put("startTime", dateFormat.format(record.get("startTime")));
/*     */       
/* 421 */       PreviewGridRow row = new PreviewGridRow();
/* 422 */       row.setId(UUID.randomUUID().toString());
/* 423 */       row.setColumnValMap(record);
/* 424 */       rows.add(row);
/*     */     } 
/*     */     
/* 427 */     PreviewGridData result = new PreviewGridData();
/* 428 */     result.setHeaders(genPreviewGridHeaders(reportMap));
/* 429 */     result.setRows(rows);
/* 430 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Boolean> checkAbnormal(AlarmLogData data) {
/* 440 */     PdStatus status = this.pdStatusRepository.findByDeviceName(data.getDeviceName()).stream().sorted(Comparator.<PdStatus, Comparable>comparing(PdStatus::getDataTime).reversed()).findFirst().get();
/* 441 */     List<Boolean> list = new ArrayList<>();
/* 442 */     Boolean connectivityStatus = Boolean.valueOf(false);
/* 443 */     Boolean primaryStatus = Boolean.valueOf(false);
/* 444 */     Boolean secondaryStatus = Boolean.valueOf(false);
/* 445 */     Boolean loopStatus = Boolean.valueOf(false);
/* 446 */     if (status.getConnectivity().intValue() == 0) {
/* 447 */       if ((status.getPrimaryR().intValue() == 1 || status
/* 448 */         .getPrimaryS().intValue() == 1 || status
/* 449 */         .getPrimaryT().intValue() == 1 || status
/* 450 */         .getSecondaryR().intValue() == 1 || status
/* 451 */         .getSecondaryS().intValue() == 1 || status
/* 452 */         .getSecondaryT().intValue() == 1) && (status
/* 453 */         .getPrimaryR().intValue() != 1 || status
/* 454 */         .getPrimaryR().intValue() != 1 || status
/* 455 */         .getPrimaryR().intValue() != 1) && (status
/* 456 */         .getSecondaryR().intValue() != 1 || status
/* 457 */         .getSecondaryS().intValue() != 1 || status
/* 458 */         .getSecondaryT().intValue() != 1)) {
/* 459 */         primaryStatus = Boolean.valueOf(true);
/*     */       }
/* 461 */       if ((status.getPrimaryR().intValue() == 1 && status.getPrimaryR().intValue() == 1 && status.getPrimaryR().intValue() == 1) || (status
/* 462 */         .getSecondaryR().intValue() == 1 && status
/* 463 */         .getSecondaryS().intValue() == 1 && status
/* 464 */         .getSecondaryT().intValue() == 1)) {
/* 465 */         secondaryStatus = Boolean.valueOf(true);
/*     */       }
/* 467 */       for (PdLoopStatus loop : status.getPdLoops()) {
/* 468 */         if (loop.getStatus() != null && loop.getStatus().intValue() == 1) {
/* 469 */           loopStatus = Boolean.valueOf(true);
/*     */         }
/*     */       } 
/*     */     } else {
/* 473 */       connectivityStatus = Boolean.valueOf(true);
/*     */     } 
/* 475 */     list.add(connectivityStatus);
/* 476 */     list.add(primaryStatus);
/* 477 */     list.add(secondaryStatus);
/* 478 */     list.add(loopStatus);
/* 479 */     return list;
/*     */   }
/*     */   
/*     */   private List<PreviewGridHeader> genPreviewGridHeaders(List<Map<String, Object>> reportMap) {
/* 483 */     List<PreviewGridHeader> headers = new ArrayList<>();
/*     */     
/* 485 */     PreviewGridHeader h1 = new PreviewGridHeader();
/* 486 */     h1.setColumnId("deviceName");
/* 487 */     h1.setHeader(this.messageSourceExt.getMessage("設備名稱"));
/* 488 */     h1.setWidth(Integer.valueOf(100));
/* 489 */     headers.add(h1);
/*     */     
/* 491 */     PreviewGridHeader h2 = new PreviewGridHeader();
/* 492 */     h2.setColumnId("location");
/* 493 */     h2.setHeader(this.messageSourceExt.getMessage("機房名稱"));
/* 494 */     h2.setWidth(Integer.valueOf(300));
/* 495 */     headers.add(h2);
/*     */     
/* 497 */     PreviewGridHeader h3 = new PreviewGridHeader();
/* 498 */     h3.setColumnId("roadLine");
/* 499 */     h3.setHeader(this.messageSourceExt.getMessage("路線"));
/* 500 */     h3.setWidth(Integer.valueOf(100));
/* 501 */     headers.add(h3);
/*     */     
/* 503 */     PreviewGridHeader h4 = new PreviewGridHeader();
/* 504 */     h4.setColumnId("direction");
/* 505 */     h4.setHeader(this.messageSourceExt.getMessage("方向"));
/* 506 */     h4.setWidth(Integer.valueOf(100));
/* 507 */     headers.add(h4);
/*     */     
/* 509 */     PreviewGridHeader h5 = new PreviewGridHeader();
/* 510 */     h5.setColumnId("milepost");
/* 511 */     h5.setHeader(this.messageSourceExt.getMessage("里程"));
/* 512 */     h5.setWidth(Integer.valueOf(100));
/* 513 */     headers.add(h5);
/*     */     
/* 515 */     PreviewGridHeader h6 = new PreviewGridHeader();
/* 516 */     h6.setColumnId("milepost");
/* 517 */     h6.setHeader(this.messageSourceExt.getMessage("里程"));
/* 518 */     h6.setWidth(Integer.valueOf(100));
/* 519 */     headers.add(h6);
/*     */     
/* 521 */     PreviewGridHeader h7 = new PreviewGridHeader();
/* 522 */     h7.setColumnId("meterNo");
/* 523 */     h7.setHeader(this.messageSourceExt.getMessage("電錶號"));
/* 524 */     h7.setWidth(Integer.valueOf(100));
/* 525 */     headers.add(h7);
/*     */     
/* 527 */     PreviewGridHeader h8 = new PreviewGridHeader();
/* 528 */     h8.setColumnId("area");
/* 529 */     h8.setHeader(this.messageSourceExt.getMessage("所屬區處"));
/* 530 */     h8.setWidth(Integer.valueOf(100));
/* 531 */     headers.add(h8);
/*     */     
/* 533 */     PreviewGridHeader h9 = new PreviewGridHeader();
/* 534 */     h9.setColumnId("phone");
/* 535 */     h9.setHeader(this.messageSourceExt.getMessage("連絡電話"));
/* 536 */     h9.setWidth(Integer.valueOf(100));
/* 537 */     headers.add(h9);
/*     */     
/* 539 */     PreviewGridHeader h10 = new PreviewGridHeader();
/* 540 */     h10.setColumnId("dataTime");
/* 541 */     h10.setHeader(this.messageSourceExt.getMessage("時間"));
/* 542 */     h10.setWidth(Integer.valueOf(100));
/* 543 */     headers.add(h10);
/*     */     
/* 545 */     PreviewGridHeader h11 = new PreviewGridHeader();
/* 546 */     h11.setColumnId("alarmType");
/* 547 */     h11.setHeader(this.messageSourceExt.getMessage("項目"));
/* 548 */     h11.setWidth(Integer.valueOf(100));
/* 549 */     headers.add(h11);
/*     */     
/* 551 */     PreviewGridHeader h12 = new PreviewGridHeader();
/* 552 */     h12.setColumnId("degree");
/* 553 */     h12.setHeader(this.messageSourceExt.getMessage("嚴重程度"));
/* 554 */     h12.setWidth(Integer.valueOf(100));
/* 555 */     headers.add(h12);
/*     */     
/* 557 */     PreviewGridHeader h13 = new PreviewGridHeader();
/* 558 */     h13.setColumnId("memo");
/* 559 */     h13.setHeader(this.messageSourceExt.getMessage("備註"));
/* 560 */     h13.setWidth(Integer.valueOf(100));
/* 561 */     headers.add(h13);
/*     */     
/* 563 */     return headers;
/*     */   }
/*     */   
/*     */   private AlarmLogEventContext getContextData(AlarmLog log) {
/* 567 */     Gson gson = new Gson();
/*     */     try {
/* 569 */       return (AlarmLogEventContext)gson.fromJson(log.getContextData(), AlarmLogEventContext.class);
/* 570 */     } catch (JsonSyntaxException e) {
/* 571 */       logger.error("getContextData failed, log='{}'", log, e);
/* 572 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private AlarmLogData getAlarmLogData(AlarmLog log, AlarmLogEventContext context) {
/* 577 */     AlarmLogData data = new AlarmLogData();
/* 578 */     data.setAlarmSubType(log.getAlarmSubType());
/* 579 */     data.setDegree(log.getDegree());
/* 580 */     data.setTimestamp(log.getTimestamp());
/* 581 */     data.setDeviceName(context.getDeviceName());
/* 582 */     data.setDirection(context.getDirection());
/* 583 */     data.setBlockLaneMark(context.getBlockLaneMark());
/* 584 */     data.setStartMileage(context.getStartMileage());
/* 585 */     data.setEndMileage(context.getEndMileage());
/* 586 */     data.setNotifySource(context.getNotifySource());
/* 587 */     data.setLineId(context.getLineId());
/* 588 */     data.setSectionId(context.getSectionId());
/* 589 */     return data;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\rpt\AoPdAlarmReportInquiryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */