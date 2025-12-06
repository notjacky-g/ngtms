/*     */ package com.hwacom.ngtms.ao.fm.service.rpt;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.ao.util.TransferHelper;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceHostLocation;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceLocationMappingConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcStatus;
/*     */ import com.hwacom.ngtms.c.fm.repository.DeviceLocationMappingConfigRepository;
/*     */ import com.hwacom.ngtms.common.rpt.ReportInquiry;
/*     */ import com.hwacom.ngtms.common.shared.PreviewGridData;
/*     */ import com.hwacom.ngtms.common.shared.PreviewGridHeader;
/*     */ import com.hwacom.ngtms.common.shared.PreviewGridRow;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*     */ import com.hwacom.ngtms.hcce.util.HcceUtils;
/*     */ import com.hwacom.ngtms.room.shared.RtuConfig;
/*     */ import com.hwacom.ngtms.room.shared.SignalType;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.apache.commons.lang3.SerializationUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class AoRoomElectricityMonitorInquiryImpl
/*     */   implements ReportInquiry
/*     */ {
/*  45 */   private static Logger logger = LoggerFactory.getLogger(AoRoomElectricityMonitorInquiryImpl.class);
/*     */   
/*  47 */   private Gson gson = new Gson();
/*     */   
/*  49 */   private static final String[] monitorDeviceType = new String[] { "油槽容量", "發電機", "主電源開關", "DC電壓", "充電機電源", "充電機狀態" };
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   private DeviceLocationMappingConfigRepository deviceLocationMappingConfigRepository;
/*     */   
/*     */   @Autowired
/*     */   private MessageSourceExt messageSourceExt;
/*     */   
/*  58 */   Map<String, String> roomAbnormalMemoMap = null;
/*     */   
/*  60 */   Map<String, Boolean> roomOilTankAlarmMap = null;
/*     */   
/*  62 */   Map<String, Boolean> roomDcPowerAlarmMap = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Map<String, Object>> inquire(Map<String, Object> inputParameter) {
/*  68 */     String userName = inputParameter.containsKey("userName") ? (String)inputParameter.get("userName") : "";
/*     */     
/*  70 */     String chartKey = (String)inputParameter.get("CHART_KEY");
/*     */     
/*  72 */     List<Map<String, Object>> resultMapArray = new ArrayList<>();
/*     */ 
/*     */     
/*  75 */     Map<String, Object> tableDataMap = new HashMap<>();
/*     */ 
/*     */     
/*  78 */     Map<String, Map<String, DeviceTcStatus>> rptRoomElectricityStatusMap = new HashMap<>();
/*     */     
/*  80 */     Map<String, List<String>> roomNoDeviceTypeMap = new HashMap<>();
/*     */     
/*  82 */     this.roomAbnormalMemoMap = new HashMap<>();
/*  83 */     this.roomOilTankAlarmMap = new HashMap<>();
/*  84 */     this.roomDcPowerAlarmMap = new HashMap<>();
/*     */     
/*  86 */     IMap<String, DeviceTcStatus> statusMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcStatus);
/*     */     
/*  88 */     IMap<Integer, DeviceHostLocation> hostLocationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceHostLocation);
/*  89 */     IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*     */     
/*  91 */     DynamicConfig dynamicConfig = HcceUtils.getDynaConfig("AoCommonFm", "centerMonitorRoomIds");
/*  92 */     String centerMonitorRoomIdStr = dynamicConfig.getValue();
/*  93 */     String[] centerMonitorLocNameArr = centerMonitorRoomIdStr.split(",");
/*     */     
/*  95 */     for (String locName : centerMonitorLocNameArr) {
/*  96 */       Map<String, DeviceTcStatus> deviceStatusMap = rptRoomElectricityStatusMap.get(locName);
/*  97 */       if (deviceStatusMap == null) {
/*  98 */         deviceStatusMap = new HashMap<>();
/*     */       }
/* 100 */       List<String> roomDeviceNames = new ArrayList<>();
/*     */       
/* 102 */       List<DeviceLocationMappingConfig> mappingConfigs = this.deviceLocationMappingConfigRepository.findByLocationName(locName);
/* 103 */       for (DeviceLocationMappingConfig mappingConfig : mappingConfigs) {
/* 104 */         roomDeviceNames.add(mappingConfig.getDeviceName());
/*     */       }
/*     */       
/* 107 */       if (roomDeviceNames.size() > 0) {
/* 108 */         String[] roomDeviceNameArr = roomDeviceNames.<String>toArray(new String[0]);
/* 109 */         EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*     */         
/* 111 */         PredicateBuilder pb = eo.get("id").in((Comparable[])roomDeviceNameArr).and((Predicate)eo.get("deviceType").in((Comparable[])monitorDeviceType));
/* 112 */         for (DeviceTcStatus status : statusMap.values((Predicate)pb)) {
/* 113 */           if (status.getDeviceType().equals("DC電壓")) {
/* 114 */             if (((DeviceTcConfig)deviceMap.get(status.getDeviceName())).getDisplayName().equals("R24-DC電壓"))
/* 115 */               deviceStatusMap.put(status.getDeviceType(), status);  continue;
/*     */           } 
/* 117 */           if (status.getDeviceType().equals("充電機狀態")) {
/* 118 */             if (((DeviceTcConfig)deviceMap.get(status.getDeviceName())).getDisplayName().equals("充電機狀態"))
/* 119 */               deviceStatusMap.put(status.getDeviceType(), status); 
/*     */             continue;
/*     */           } 
/* 122 */           deviceStatusMap.put(status.getDeviceType(), status);
/*     */         } 
/*     */         
/* 125 */         rptRoomElectricityStatusMap.put(locName, deviceStatusMap);
/*     */ 
/*     */         
/* 128 */         List<String> roomDeviceType = new ArrayList<>();
/*     */         
/* 130 */         PredicateBuilder DevicePb = (new PredicateBuilder()).getEntryObject().get("deviceName").in((Comparable[])roomDeviceNameArr);
/* 131 */         for (DeviceTcConfig config : deviceMap.values((Predicate)DevicePb)) {
/* 132 */           roomDeviceType.add(config.getDeviceType());
/*     */         }
/* 134 */         List<String> noDeviceTypeList = roomNoDeviceTypeMap.get(locName);
/* 135 */         if (noDeviceTypeList == null) {
/* 136 */           noDeviceTypeList = new ArrayList<>();
/*     */         }
/* 138 */         for (String monitorType : monitorDeviceType) {
/* 139 */           if (!roomDeviceType.contains(monitorType)) {
/* 140 */             noDeviceTypeList.add(monitorType);
/*     */           }
/*     */         } 
/* 143 */         roomNoDeviceTypeMap.put(locName, noDeviceTypeList);
/*     */       } else {
/*     */         
/* 146 */         List<String> noDeviceTypeList = roomNoDeviceTypeMap.get(locName);
/* 147 */         if (noDeviceTypeList == null) {
/* 148 */           noDeviceTypeList = new ArrayList<>();
/*     */         }
/* 150 */         for (String monitorType : monitorDeviceType) {
/* 151 */           noDeviceTypeList.add(monitorType);
/*     */         }
/* 153 */         roomNoDeviceTypeMap.put(locName, noDeviceTypeList);
/*     */       } 
/*     */     } 
/* 156 */     logger.debug("rptRoomElectricityStatusMap:'{}'", rptRoomElectricityStatusMap);
/* 157 */     logger.debug("roomNoDeviceTypeMap:'{}'", roomNoDeviceTypeMap);
/* 158 */     HashMap[] arrayOfHashMap = new HashMap[18];
/* 159 */     int row = 0;
/* 160 */     for (String locName : centerMonitorLocNameArr) {
/* 161 */       Map<String, Object> roomElectricityMonitor = new HashMap<>();
/* 162 */       Map<String, DeviceTcStatus> deviceStatusMap = rptRoomElectricityStatusMap.get(locName);
/* 163 */       PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("locName").equal(locName);
/* 164 */       DeviceHostLocation room = null;
/* 165 */       for (DeviceHostLocation location : hostLocationMap.values((Predicate)pb)) {
/* 166 */         room = location;
/*     */       }
/* 168 */       if (room != null) {
/* 169 */         roomElectricityMonitor.put("line", TransferHelper.getLineName(room.getLineId()));
/*     */       }
/* 171 */       roomElectricityMonitor.put("location", locName);
/* 172 */       DeviceTcStatus oilTank = (deviceStatusMap != null) ? deviceStatusMap.get("油槽容量") : null;
/* 173 */       DeviceTcStatus dynamo = (deviceStatusMap != null) ? deviceStatusMap.get("發電機") : null;
/*     */       
/* 175 */       DeviceTcStatus ThreePhasePower = (deviceStatusMap != null) ? deviceStatusMap.get("主電源開關") : null;
/* 176 */       DeviceTcStatus dcPower = (deviceStatusMap != null) ? deviceStatusMap.get("DC電壓") : null;
/* 177 */       DeviceTcStatus chargerPower = (deviceStatusMap != null) ? deviceStatusMap.get("充電機電源") : null;
/* 178 */       DeviceTcStatus chargerStatus = (deviceStatusMap != null) ? deviceStatusMap.get("充電機狀態") : null;
/* 179 */       if (oilTank != null) {
/* 180 */         String deviceName = oilTank.getDeviceName();
/* 181 */         roomElectricityMonitor.put("oilTank", 
/* 182 */             getStatusFromDeviceTcStatus(oilTank, deviceName, locName));
/*     */       } else {
/* 184 */         List<String> roomNoDeviceTypes = roomNoDeviceTypeMap.get(locName);
/* 185 */         if (roomNoDeviceTypes != null && roomNoDeviceTypes.contains("油槽容量")) {
/* 186 */           roomElectricityMonitor.put("oilTank", "無設備");
/*     */         } else {
/* 188 */           roomElectricityMonitor.put("oilTank", "無狀態");
/*     */         } 
/*     */       } 
/* 191 */       if (dynamo != null) {
/* 192 */         String deviceName = dynamo.getDeviceName();
/* 193 */         roomElectricityMonitor.put("dynamo", 
/* 194 */             getStatusFromDeviceTcStatus(dynamo, deviceName, locName));
/*     */       } else {
/* 196 */         List<String> roomNoDeviceTypes = roomNoDeviceTypeMap.get(locName);
/* 197 */         if (roomNoDeviceTypes != null && roomNoDeviceTypes.contains("發電機")) {
/* 198 */           roomElectricityMonitor.put("dynamo", "無設備");
/*     */         } else {
/* 200 */           roomElectricityMonitor.put("dynamo", "無狀態");
/*     */         } 
/*     */       } 
/* 203 */       if (ThreePhasePower != null) {
/* 204 */         String deviceName = ThreePhasePower.getDeviceName();
/* 205 */         roomElectricityMonitor.put("ThreePhasePower", 
/* 206 */             getStatusFromDeviceTcStatus(ThreePhasePower, deviceName, locName));
/*     */       } else {
/* 208 */         List<String> roomNoDeviceTypes = roomNoDeviceTypeMap.get(locName);
/* 209 */         if (roomNoDeviceTypes != null && roomNoDeviceTypes.contains("發電機")) {
/* 210 */           roomElectricityMonitor.put("ThreePhasePower", "無設備");
/*     */         } else {
/* 212 */           roomElectricityMonitor.put("ThreePhasePower", "無狀態");
/*     */         } 
/*     */       } 
/* 215 */       if (dcPower != null) {
/* 216 */         String deviceName = dcPower.getDeviceName();
/* 217 */         roomElectricityMonitor.put("dcPower", 
/* 218 */             getStatusFromDeviceTcStatus(dcPower, deviceName, locName));
/*     */       } else {
/* 220 */         List<String> roomNoDeviceTypes = roomNoDeviceTypeMap.get(locName);
/* 221 */         if (roomNoDeviceTypes != null && roomNoDeviceTypes.contains("DC電壓")) {
/* 222 */           roomElectricityMonitor.put("dcPower", "無設備");
/*     */         } else {
/* 224 */           roomElectricityMonitor.put("dcPower", "無狀態");
/*     */         } 
/*     */       } 
/* 227 */       if (chargerPower != null) {
/* 228 */         String deviceName = chargerPower.getDeviceName();
/* 229 */         roomElectricityMonitor.put("chargerPower", 
/* 230 */             getStatusFromDeviceTcStatus(chargerPower, deviceName, locName));
/*     */       } else {
/* 232 */         List<String> roomNoDeviceTypes = roomNoDeviceTypeMap.get(locName);
/* 233 */         if (roomNoDeviceTypes != null && roomNoDeviceTypes.contains("充電機電源")) {
/* 234 */           roomElectricityMonitor.put("chargerPower", "無設備");
/*     */         } else {
/* 236 */           roomElectricityMonitor.put("chargerPower", "無狀態");
/*     */         } 
/*     */       } 
/* 239 */       if (chargerStatus != null) {
/* 240 */         String deviceName = chargerStatus.getDeviceName();
/* 241 */         roomElectricityMonitor.put("chargerStatus", 
/* 242 */             getStatusFromDeviceTcStatus(chargerStatus, deviceName, locName));
/*     */       } else {
/* 244 */         List<String> roomNoDeviceTypes = roomNoDeviceTypeMap.get(locName);
/* 245 */         if (roomNoDeviceTypes != null && roomNoDeviceTypes.contains("充電機狀態")) {
/* 246 */           roomElectricityMonitor.put("chargerStatus", "無設備");
/*     */         } else {
/* 248 */           roomElectricityMonitor.put("chargerStatus", "無狀態");
/*     */         } 
/*     */       } 
/* 251 */       String memo = this.roomAbnormalMemoMap.get(locName);
/* 252 */       if (memo != null) {
/* 253 */         memo = memo.substring(0, memo.length() - 1);
/*     */       }
/* 255 */       roomElectricityMonitor.put("memo", memo);
/* 256 */       roomElectricityMonitor.put("oilTankAlarm", this.roomOilTankAlarmMap.get(locName));
/* 257 */       roomElectricityMonitor.put("dcPowerAlarm", this.roomDcPowerAlarmMap.get(locName));
/*     */       
/* 259 */       arrayOfHashMap[row++] = (HashMap)roomElectricityMonitor;
/*     */     } 
/*     */     
/* 262 */     tableDataMap.put("roomElectricityMonitor", arrayOfHashMap);
/* 263 */     tableDataMap.put("CHART_KEY", chartKey);
/* 264 */     tableDataMap.put("userName", userName);
/*     */     
/* 266 */     resultMapArray.add(tableDataMap);
/* 267 */     return resultMapArray;
/*     */   }
/*     */ 
/*     */   
/*     */   public PreviewGridData inquirePreviewGridData(Map<String, Object> inputParameter) {
/* 272 */     List<Map<String, Object>> reportMap = inquire(inputParameter);
/*     */     
/* 274 */     Map[] arrayOfMap = (Map[])((Map)reportMap.get(0)).get("roomElectricityMonitor");
/* 275 */     if (reportMap.size() == 0) {
/* 276 */       return new PreviewGridData();
/*     */     }
/*     */ 
/*     */     
/* 280 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/* 281 */     List<PreviewGridRow> rows = new ArrayList<>();
/* 282 */     for (Map<String, Object> record : arrayOfMap) {
/*     */       
/* 284 */       record.put("startTime", dateFormat.format(record.get("startTime")));
/*     */       
/* 286 */       PreviewGridRow row = new PreviewGridRow();
/* 287 */       row.setId(UUID.randomUUID().toString());
/* 288 */       row.setColumnValMap(record);
/* 289 */       rows.add(row);
/*     */     } 
/*     */     
/* 292 */     PreviewGridData result = new PreviewGridData();
/* 293 */     result.setHeaders(genPreviewGridHeaders(reportMap));
/* 294 */     result.setRows(rows);
/* 295 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getStatusFromDeviceTcStatus(DeviceTcStatus tcStatus, String deviceName, String locName) {
/* 300 */     IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 301 */     String status = "";
/* 302 */     Integer commStatus = tcStatus.getCommStatus();
/* 303 */     String deviceType = tcStatus.getDeviceType();
/* 304 */     if (commStatus.intValue() == 1)
/* 305 */       return "斷線"; 
/* 306 */     if (commStatus.intValue() == 0) {
/* 307 */       byte[] contextData = tcStatus.getContextData();
/* 308 */       DeviceTcConfig deviceConfig = (DeviceTcConfig)deviceMap.get(deviceName);
/* 309 */       RtuConfig rtuConfig = (RtuConfig)this.gson.fromJson(deviceConfig.getExtend(), RtuConfig.class);
/* 310 */       String signalType = "";
/* 311 */       if (rtuConfig != null) {
/* 312 */         signalType = rtuConfig.getSignalType();
/*     */       }
/* 314 */       if (SignalType.DIGITAL_IN.name().equals(signalType)) {
/* 315 */         Integer value = null;
/* 316 */         if (contextData != null) {
/* 317 */           value = (Integer)SerializationUtils.deserialize(tcStatus.getContextData());
/*     */         }
/* 319 */         if (value != null && value.intValue() == -1) {
/* 320 */           status = "斷線";
/* 321 */         } else if (value != null) {
/* 322 */           if (deviceType.equals("機房門禁")) {
/* 323 */             if (value.intValue() == 1) {
/* 324 */               status = "正常";
/* 325 */             } else if (value.intValue() == 0) {
/* 326 */               status = "開啟";
/*     */             }
/*     */           
/* 329 */           } else if (deviceType.equals("氣冷式冰水主機")) {
/* 330 */             if (value.intValue() == 1) {
/* 331 */               status = "運轉";
/* 332 */             } else if (value.intValue() == 0) {
/* 333 */               status = "正常";
/*     */             } 
/* 335 */           } else if (deviceType.equals("充電機狀態")) {
/* 336 */             if (value.intValue() == 1) {
/* 337 */               status = "告警";
/* 338 */             } else if (value.intValue() == 0) {
/* 339 */               status = "正常";
/*     */             }
/*     */           
/* 342 */           } else if (value.intValue() == 1) {
/* 343 */             status = "啟動";
/* 344 */           } else if (value.intValue() == 0) {
/* 345 */             status = "正常";
/*     */           }
/*     */         
/*     */         } 
/* 349 */       } else if (SignalType.ANALOG_IN.name().equals(signalType)) {
/* 350 */         String unit = "";
/* 351 */         if (rtuConfig != null) {
/* 352 */           unit = rtuConfig.getUnit();
/*     */         }
/* 354 */         if (contextData != null) {
/* 355 */           if ((deviceType.equals("濕度") || deviceType
/* 356 */             .equals("溫度") || deviceType
/* 357 */             .equals("DC電壓") || deviceType
/* 358 */             .equals("油槽容量") || deviceType
/* 359 */             .equals("電流") || deviceType
/* 360 */             .equals("FM發射機") || deviceType
/* 361 */             .equals("發射機輸出功率")) && (
/* 362 */             (Double)SerializationUtils.deserialize(tcStatus.getContextData())).doubleValue() != -1.0D) {
/*     */             
/* 364 */             String displayValue = String.valueOf((
/* 365 */                 (Double)SerializationUtils.deserialize(tcStatus.getContextData())).doubleValue());
/* 366 */             String updateValue = displayValue;
/* 367 */             if (displayValue.length() == 3) {
/* 368 */               updateValue = displayValue.substring(0, 3);
/* 369 */             } else if (displayValue.length() == 4) {
/* 370 */               updateValue = displayValue.substring(0, 4);
/* 371 */             } else if (displayValue.length() > 4) {
/* 372 */               updateValue = displayValue.substring(0, 5);
/*     */             } 
/* 374 */             status = updateValue + unit;
/* 375 */             float statusValue = Float.parseFloat(updateValue);
/* 376 */             if (statusValue > rtuConfig.getUpperLimit().intValue()) {
/* 377 */               String memo = this.roomAbnormalMemoMap.get(locName);
/* 378 */               if (memo == null) {
/* 379 */                 memo = deviceType + "過高,";
/*     */               } else {
/* 381 */                 memo = memo + deviceType + "過高,";
/*     */               } 
/* 383 */               this.roomAbnormalMemoMap.put(locName, memo);
/* 384 */               if (deviceType.equals("油槽容量")) {
/* 385 */                 this.roomOilTankAlarmMap.put(locName, Boolean.valueOf(true));
/* 386 */               } else if (deviceType.equals("DC電壓")) {
/* 387 */                 this.roomDcPowerAlarmMap.put(locName, Boolean.valueOf(true));
/*     */               } 
/* 389 */             } else if (statusValue < rtuConfig.getLowerLimit().intValue()) {
/* 390 */               String memo = this.roomAbnormalMemoMap.get(locName);
/* 391 */               if (memo == null) {
/* 392 */                 memo = deviceType + "過低,";
/*     */               } else {
/* 394 */                 memo = memo + deviceType + "過低,";
/*     */               } 
/* 396 */               this.roomAbnormalMemoMap.put(locName, memo);
/* 397 */               if (deviceType.equals("油槽容量")) {
/* 398 */                 this.roomOilTankAlarmMap.put(locName, Boolean.valueOf(true));
/* 399 */               } else if (deviceType.equals("DC電壓")) {
/* 400 */                 this.roomDcPowerAlarmMap.put(locName, Boolean.valueOf(true));
/*     */               } 
/*     */             } 
/*     */           } else {
/*     */             
/* 405 */             status = String.valueOf(
/* 406 */                 (Integer)SerializationUtils.deserialize(tcStatus.getContextData()) + unit);
/*     */           } 
/*     */         } else {
/* 409 */           status = "";
/*     */         } 
/* 411 */       } else if (rtuConfig != null && SignalType.DIGITAL_OUT.name().equals(signalType)) {
/* 412 */         if (contextData != null) {
/* 413 */           Integer value = (Integer)SerializationUtils.deserialize(tcStatus.getContextData());
/* 414 */           status = (value.intValue() == 1) ? "開啟" : "關閉";
/*     */         } else {
/* 416 */           status = "";
/*     */         } 
/*     */       } 
/*     */     } 
/* 420 */     return status;
/*     */   }
/*     */   
/*     */   private List<PreviewGridHeader> genPreviewGridHeaders(List<Map<String, Object>> reportMap) {
/* 424 */     List<PreviewGridHeader> headers = new ArrayList<>();
/*     */     
/* 426 */     PreviewGridHeader h1 = new PreviewGridHeader();
/* 427 */     h1.setColumnId("line");
/* 428 */     h1.setHeader(this.messageSourceExt.getMessage("國道"));
/* 429 */     h1.setWidth(Integer.valueOf(150));
/* 430 */     headers.add(h1);
/*     */     
/* 432 */     PreviewGridHeader h2 = new PreviewGridHeader();
/* 433 */     h2.setColumnId("location");
/* 434 */     h2.setHeader(this.messageSourceExt.getMessage("機房名稱"));
/* 435 */     h2.setWidth(Integer.valueOf(150));
/* 436 */     headers.add(h2);
/*     */     
/* 438 */     PreviewGridHeader h3 = new PreviewGridHeader();
/* 439 */     h3.setColumnId("oilTank");
/* 440 */     h3.setHeader(this.messageSourceExt.getMessage("油槽容量(%)"));
/* 441 */     h3.setWidth(Integer.valueOf(300));
/* 442 */     headers.add(h3);
/*     */     
/* 444 */     PreviewGridHeader h4 = new PreviewGridHeader();
/* 445 */     h4.setColumnId("dynamo");
/* 446 */     h4.setHeader(this.messageSourceExt.getMessage("發電機"));
/* 447 */     h4.setWidth(Integer.valueOf(150));
/* 448 */     headers.add(h4);
/*     */     
/* 450 */     PreviewGridHeader h5 = new PreviewGridHeader();
/* 451 */     h5.setColumnId("ThreePhasePower");
/* 452 */     h5.setHeader(this.messageSourceExt.getMessage("三相主電源"));
/* 453 */     h5.setWidth(Integer.valueOf(150));
/* 454 */     headers.add(h5);
/*     */     
/* 456 */     PreviewGridHeader h6 = new PreviewGridHeader();
/* 457 */     h6.setColumnId("dcPower");
/* 458 */     h6.setHeader(this.messageSourceExt.getMessage("DC電壓(V)"));
/* 459 */     h6.setWidth(Integer.valueOf(300));
/* 460 */     headers.add(h6);
/*     */     
/* 462 */     PreviewGridHeader h7 = new PreviewGridHeader();
/* 463 */     h7.setColumnId("chargerPower");
/* 464 */     h7.setHeader(this.messageSourceExt.getMessage("充電機電源"));
/* 465 */     h7.setWidth(Integer.valueOf(150));
/* 466 */     headers.add(h7);
/*     */     
/* 468 */     PreviewGridHeader h8 = new PreviewGridHeader();
/* 469 */     h8.setColumnId("chargerStatus");
/* 470 */     h8.setHeader(this.messageSourceExt.getMessage("充電機狀態"));
/* 471 */     h8.setWidth(Integer.valueOf(150));
/* 472 */     headers.add(h8);
/*     */     
/* 474 */     PreviewGridHeader h9 = new PreviewGridHeader();
/* 475 */     h9.setColumnId("memo");
/* 476 */     h9.setHeader(this.messageSourceExt.getMessage("備註"));
/* 477 */     h9.setWidth(Integer.valueOf(300));
/* 478 */     headers.add(h9);
/*     */     
/* 480 */     return headers;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\rpt\AoRoomElectricityMonitorInquiryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */