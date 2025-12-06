/*     */ package com.hwacom.ngtms.ao.fm.service.rpt;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.ao.fm.hz.AoHzMap;
/*     */ import com.hwacom.ngtms.ao.fm.model.PowerStatusData;
/*     */ import com.hwacom.ngtms.ao.fm.model.WaterPowerBaseConfig;
/*     */ import com.hwacom.ngtms.ao.fm.model.WaterStatusData;
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
/*     */ import java.util.Iterator;
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
/*     */ public class AoRoomEnvironmentMonitorInquiryImpl
/*     */   implements ReportInquiry
/*     */ {
/*  49 */   private static Logger logger = LoggerFactory.getLogger(AoRoomEnvironmentMonitorInquiryImpl.class);
/*     */   
/*  51 */   private Gson gson = new Gson();
/*     */   
/*     */   @Autowired
/*     */   private DeviceLocationMappingConfigRepository deviceLocationMappingConfigRepository;
/*     */   
/*     */   @Autowired
/*     */   private MessageSourceExt messageSourceExt;
/*  58 */   Map<String, String> roomAbnormalMemoMap = null;
/*     */   
/*  60 */   Map<String, Boolean> roomTemperatureAlarmMap = null;
/*     */   
/*  62 */   Map<String, Boolean> roomHumidityAlarmMap = null;
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
/*  78 */     Map<String, Map<String, DeviceTcStatus>> rptRoomEnvironmentStatusMap = new HashMap<>();
/*     */     
/*  80 */     Map<String, List<String>> roomNoDeviceTypeMap = new HashMap<>();
/*     */     
/*  82 */     this.roomAbnormalMemoMap = new HashMap<>();
/*  83 */     this.roomTemperatureAlarmMap = new HashMap<>();
/*  84 */     this.roomHumidityAlarmMap = new HashMap<>();
/*     */     
/*  86 */     IMap<String, DeviceTcStatus> statusMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcStatus);
/*     */     
/*  88 */     IMap<Integer, DeviceHostLocation> hostLocationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceHostLocation);
/*  89 */     IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  90 */     IMap<String, WaterStatusData> waterStatusMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.WaterStatusData);
/*  91 */     IMap<String, PowerStatusData> powerStatusMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.PowerStatusData);
/*     */     
/*  93 */     IMap<String, WaterPowerBaseConfig> waterPowerBasicMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.WaterPowerBaseConfig);
/*     */     
/*  95 */     DynamicConfig dynamicConfig = HcceUtils.getDynaConfig("AoCommonFm", "centerMonitorRoomIds");
/*  96 */     String centerMonitorRoomIdStr = dynamicConfig.getValue();
/*  97 */     String[] centerMonitorLocNameArr = centerMonitorRoomIdStr.split(",");
/*     */     
/*  99 */     for (String locName : centerMonitorLocNameArr) {
/* 100 */       Map<String, DeviceTcStatus> deviceStatusMap = rptRoomEnvironmentStatusMap.get(locName);
/* 101 */       if (deviceStatusMap == null) {
/* 102 */         deviceStatusMap = new HashMap<>();
/*     */       }
/* 104 */       List<String> roomDeviceNames = new ArrayList<>();
/* 105 */       List<String> airConditionerNames = new ArrayList<>();
/*     */       
/* 107 */       List<DeviceLocationMappingConfig> mappingConfigs = this.deviceLocationMappingConfigRepository.findByLocationName(locName);
/* 108 */       for (DeviceLocationMappingConfig mappingConfig : mappingConfigs) {
/* 109 */         String deviceName = mappingConfig.getDeviceName();
/* 110 */         if (deviceName != null) {
/* 111 */           DeviceTcConfig tcConfig = (DeviceTcConfig)deviceMap.get(deviceName);
/* 112 */           if (tcConfig != null) {
/* 113 */             if (tcConfig.getDisplayName().equals("溫度") && tcConfig.getDeviceType().equals("溫度")) {
/* 114 */               roomDeviceNames.add(deviceName);
/*     */             }
/* 116 */             if (tcConfig.getDisplayName().equals("濕度") && tcConfig.getDeviceType().equals("濕度")) {
/* 117 */               roomDeviceNames.add(deviceName);
/*     */             }
/* 119 */             if (tcConfig.getDeviceType().equals("空調")) {
/* 120 */               airConditionerNames.add(deviceName);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 126 */       if (roomDeviceNames.size() > 0) {
/* 127 */         for (String roomDeviceName : roomDeviceNames) {
/* 128 */           if (statusMap.get(roomDeviceName) != null) {
/* 129 */             deviceStatusMap.put(((DeviceTcStatus)statusMap
/* 130 */                 .get(roomDeviceName)).getDeviceType(), statusMap.get(roomDeviceName));
/*     */           }
/*     */         } 
/* 133 */         rptRoomEnvironmentStatusMap.put(locName, deviceStatusMap);
/*     */       } 
/*     */       
/* 136 */       if (airConditionerNames.size() > 0) {
/* 137 */         for (String airConditionerName : airConditionerNames) {
/* 138 */           if (statusMap.get(airConditionerName) != null && (
/* 139 */             (DeviceTcStatus)statusMap.get(airConditionerName)).getCommStatus().intValue() == 1) {
/*     */             break;
/*     */           }
/*     */           
/* 143 */           deviceStatusMap.put(((DeviceTcStatus)statusMap
/* 144 */               .get(airConditionerName)).getDeviceType(), statusMap.get(airConditionerName));
/*     */         } 
/* 146 */         rptRoomEnvironmentStatusMap.put(locName, deviceStatusMap);
/*     */       } 
/*     */     } 
/* 149 */     logger.debug("rptRoomEnvironmentStatusMap:'{}'", rptRoomEnvironmentStatusMap);
/* 150 */     logger.debug("roomNoDeviceTypeMap:'{}'", roomNoDeviceTypeMap);
/* 151 */     HashMap[] arrayOfHashMap = new HashMap[18];
/* 152 */     int row = 0;
/* 153 */     for (String locName : centerMonitorLocNameArr) {
/* 154 */       Map<String, Object> roomEnvironmentMonitor = new HashMap<>();
/* 155 */       Map<String, DeviceTcStatus> deviceStatusMap = rptRoomEnvironmentStatusMap.get(locName);
/* 156 */       PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("locName").equal(locName);
/* 157 */       for (DeviceHostLocation location : hostLocationMap.values((Predicate)pb)) {
/* 158 */         roomEnvironmentMonitor.put("line", TransferHelper.getLineName(location.getLineId()));
/*     */       }
/*     */       
/* 161 */       PredicateBuilder pb2 = (new PredicateBuilder()).getEntryObject().get("displayName").equal(locName);
/* 162 */       Iterator<WaterPowerBaseConfig> waterPowerConfig = waterPowerBasicMap.values((Predicate)pb2).iterator();
/* 163 */       if (waterPowerConfig.hasNext()) {
/* 164 */         String waterPowerId = ((WaterPowerBaseConfig)waterPowerConfig.next()).getId();
/* 165 */         WaterStatusData waterStatusData = (WaterStatusData)waterStatusMap.get(waterPowerId);
/* 166 */         PowerStatusData powerStatusData = (PowerStatusData)powerStatusMap.get(waterPowerId);
/* 167 */         if (waterStatusData != null) {
/*     */           
/* 169 */           if (waterStatusData.getCumulateValue().doubleValue() <= 0.0D) {
/* 170 */             roomEnvironmentMonitor.put("waterStatus", "無設備");
/*     */           } else {
/* 172 */             roomEnvironmentMonitor.put("waterStatus", waterStatusData.getWaterAlarm());
/* 173 */             if (waterStatusData.getWaterAlarm() == null || waterStatusData
/* 174 */               .getWaterAlarm().equals("")) {
/* 175 */               roomEnvironmentMonitor.put("waterStatus", "無告警");
/*     */             }
/*     */           } 
/*     */         } else {
/* 179 */           roomEnvironmentMonitor.put("waterStatus", "無設備");
/*     */         } 
/* 181 */         if (powerStatusData != null) {
/*     */           
/* 183 */           if (powerStatusData.getKwh().doubleValue() <= 0.0D) {
/* 184 */             roomEnvironmentMonitor.put("powerStatus", "無設備");
/*     */           } else {
/* 186 */             roomEnvironmentMonitor.put("powerStatus", powerStatusData.getPowerAlarm());
/* 187 */             if (powerStatusData.getPowerAlarm() == null || powerStatusData
/* 188 */               .getPowerAlarm().equals("")) {
/* 189 */               roomEnvironmentMonitor.put("powerStatus", "無告警");
/*     */             }
/*     */           } 
/*     */         } else {
/* 193 */           roomEnvironmentMonitor.put("powerStatus", "無設備");
/*     */         } 
/*     */       } else {
/* 196 */         roomEnvironmentMonitor.put("waterStatus", "無設備");
/* 197 */         roomEnvironmentMonitor.put("powerStatus", "無設備");
/*     */       } 
/* 199 */       roomEnvironmentMonitor.put("location", locName);
/* 200 */       DeviceTcStatus temperature = (deviceStatusMap != null) ? deviceStatusMap.get("溫度") : null;
/* 201 */       DeviceTcStatus humidity = (deviceStatusMap != null) ? deviceStatusMap.get("濕度") : null;
/* 202 */       DeviceTcStatus airConditioner = (deviceStatusMap != null) ? deviceStatusMap.get("空調") : null;
/* 203 */       if (temperature != null) {
/* 204 */         String deviceName = temperature.getDeviceName();
/* 205 */         roomEnvironmentMonitor.put("temperature", 
/* 206 */             getStatusFromDeviceTcStatus(temperature, deviceName, locName));
/*     */       } 
/*     */       
/* 209 */       if (humidity != null) {
/* 210 */         String deviceName = humidity.getDeviceName();
/* 211 */         roomEnvironmentMonitor.put("humidity", 
/* 212 */             getStatusFromDeviceTcStatus(humidity, deviceName, locName));
/*     */       } 
/*     */       
/* 215 */       Boolean temperatueAbnormal = this.roomTemperatureAlarmMap.get(locName);
/* 216 */       Boolean hunidityAbnormal = this.roomHumidityAlarmMap.get(locName);
/* 217 */       if (temperatueAbnormal != null && hunidityAbnormal != null && 
/*     */         
/* 219 */         !temperatueAbnormal.booleanValue() && 
/* 220 */         !hunidityAbnormal.booleanValue()) {
/* 221 */         roomEnvironmentMonitor.put("temperatureHumidityAbnormal", "正常");
/*     */       } else {
/* 223 */         roomEnvironmentMonitor.put("temperatureHumidityAbnormal", "異常");
/*     */       } 
/*     */       
/* 226 */       if (airConditioner != null) {
/* 227 */         String deviceName = airConditioner.getDeviceName();
/* 228 */         roomEnvironmentMonitor.put("airConditioner", 
/* 229 */             getStatusFromDeviceTcStatus(airConditioner, deviceName, locName));
/*     */       } 
/*     */       
/* 232 */       String memo = this.roomAbnormalMemoMap.get(locName);
/* 233 */       if (memo != null) {
/* 234 */         memo = memo.substring(0, memo.length() - 1);
/*     */       }
/* 236 */       roomEnvironmentMonitor.put("memo", memo);
/* 237 */       roomEnvironmentMonitor.put("temperatureAlarm", this.roomTemperatureAlarmMap.get(locName));
/* 238 */       roomEnvironmentMonitor.put("humidityAlarm", this.roomHumidityAlarmMap.get(locName));
/*     */       
/* 240 */       arrayOfHashMap[row++] = (HashMap)roomEnvironmentMonitor;
/*     */     } 
/*     */     
/* 243 */     tableDataMap.put("roomEnvironmentMonitor", arrayOfHashMap);
/* 244 */     tableDataMap.put("CHART_KEY", chartKey);
/* 245 */     tableDataMap.put("userName", userName);
/*     */     
/* 247 */     resultMapArray.add(tableDataMap);
/* 248 */     return resultMapArray;
/*     */   }
/*     */ 
/*     */   
/*     */   public PreviewGridData inquirePreviewGridData(Map<String, Object> inputParameter) {
/* 253 */     List<Map<String, Object>> reportMap = inquire(inputParameter);
/*     */     
/* 255 */     Map[] arrayOfMap = (Map[])((Map)reportMap.get(0)).get("roomEnvironmentMonitor");
/* 256 */     if (reportMap.size() == 0) {
/* 257 */       return new PreviewGridData();
/*     */     }
/*     */ 
/*     */     
/* 261 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/* 262 */     List<PreviewGridRow> rows = new ArrayList<>();
/* 263 */     for (Map<String, Object> record : arrayOfMap) {
/*     */       
/* 265 */       record.put("startTime", dateFormat.format(record.get("startTime")));
/*     */       
/* 267 */       PreviewGridRow row = new PreviewGridRow();
/* 268 */       row.setId(UUID.randomUUID().toString());
/* 269 */       row.setColumnValMap(record);
/* 270 */       rows.add(row);
/*     */     } 
/*     */     
/* 273 */     PreviewGridData result = new PreviewGridData();
/* 274 */     result.setHeaders(genPreviewGridHeaders(reportMap));
/* 275 */     result.setRows(rows);
/* 276 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getStatusFromDeviceTcStatus(DeviceTcStatus tcStatus, String deviceName, String locName) {
/* 281 */     IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 282 */     String status = "";
/* 283 */     Integer commStatus = tcStatus.getCommStatus();
/* 284 */     String deviceType = tcStatus.getDeviceType();
/* 285 */     if (commStatus.intValue() == 1)
/* 286 */       return "斷線"; 
/* 287 */     if (commStatus.intValue() == 0) {
/* 288 */       byte[] contextData = tcStatus.getContextData();
/* 289 */       DeviceTcConfig deviceConfig = (DeviceTcConfig)deviceMap.get(deviceName);
/* 290 */       RtuConfig rtuConfig = (RtuConfig)this.gson.fromJson(deviceConfig.getExtend(), RtuConfig.class);
/* 291 */       String signalType = "";
/* 292 */       if (rtuConfig != null) {
/* 293 */         signalType = rtuConfig.getSignalType();
/*     */       }
/* 295 */       if (SignalType.DIGITAL_IN.name().equals(signalType)) {
/* 296 */         Integer value = null;
/* 297 */         if (contextData != null) {
/* 298 */           value = (Integer)SerializationUtils.deserialize(tcStatus.getContextData());
/*     */         }
/* 300 */         if (value != null && value.intValue() == -1) {
/* 301 */           status = "異常";
/* 302 */         } else if (value != null) {
/* 303 */           if (deviceType.equals("機房門禁")) {
/* 304 */             if (value.intValue() == 1) {
/* 305 */               status = "正常";
/* 306 */             } else if (value.intValue() == 0) {
/* 307 */               status = "開啟";
/*     */             }
/*     */           
/* 310 */           } else if (deviceType.equals("氣冷式冰水主機")) {
/* 311 */             if (value.intValue() == 1) {
/* 312 */               status = "運轉";
/* 313 */             } else if (value.intValue() == 0) {
/* 314 */               status = "正常";
/*     */             } 
/* 316 */           } else if (deviceType.equals("充電機狀態")) {
/* 317 */             if (value.intValue() == 1) {
/* 318 */               status = "告警";
/* 319 */             } else if (value.intValue() == 0) {
/* 320 */               status = "正常";
/*     */             } 
/*     */           } else {
/* 323 */             status = "正常";
/*     */           } 
/*     */         } 
/* 326 */       } else if (SignalType.ANALOG_IN.name().equals(signalType)) {
/* 327 */         String unit = "";
/* 328 */         if (rtuConfig != null) {
/* 329 */           unit = rtuConfig.getUnit();
/*     */         }
/*     */         
/* 332 */         if (contextData != null) {
/* 333 */           if ((deviceType.equals("濕度") || deviceType
/* 334 */             .equals("溫度") || deviceType
/* 335 */             .equals("DC電壓") || deviceType
/* 336 */             .equals("油槽容量") || deviceType
/* 337 */             .equals("電流") || deviceType
/* 338 */             .equals("FM發射機") || deviceType
/* 339 */             .equals("發射機輸出功率")) && (
/* 340 */             (Double)SerializationUtils.deserialize(tcStatus.getContextData())).doubleValue() != -1.0D) {
/*     */             
/* 342 */             String displayValue = String.valueOf((
/* 343 */                 (Double)SerializationUtils.deserialize(tcStatus.getContextData())).doubleValue());
/* 344 */             String updateValue = displayValue;
/* 345 */             if (displayValue.length() == 3) {
/* 346 */               updateValue = displayValue.substring(0, 3);
/* 347 */             } else if (displayValue.length() == 4) {
/* 348 */               updateValue = displayValue.substring(0, 4);
/* 349 */             } else if (displayValue.length() > 4) {
/* 350 */               updateValue = displayValue.substring(0, 5);
/*     */             } 
/* 352 */             status = updateValue + unit;
/* 353 */             float statusValue = Float.parseFloat(updateValue);
/* 354 */             Integer upperThreshold = rtuConfig.getUpperLimit();
/*     */             
/* 356 */             Boolean isTempOrHum = Boolean.valueOf(false);
/* 357 */             if (deviceType.equals("濕度")) {
/* 358 */               upperThreshold = Integer.valueOf(80);
/* 359 */               isTempOrHum = Boolean.valueOf(true);
/* 360 */             } else if (deviceType.equals("溫度")) {
/* 361 */               upperThreshold = Integer.valueOf(30);
/* 362 */               isTempOrHum = Boolean.valueOf(true);
/*     */             } 
/* 364 */             this.roomTemperatureAlarmMap.put(locName, Boolean.valueOf(false));
/* 365 */             this.roomHumidityAlarmMap.put(locName, Boolean.valueOf(false));
/* 366 */             if (statusValue > upperThreshold.intValue()) {
/* 367 */               String memo = this.roomAbnormalMemoMap.get(locName);
/* 368 */               if (memo == null) {
/* 369 */                 memo = deviceType + "過高,";
/*     */               } else {
/* 371 */                 memo = memo + deviceType + "過高,";
/*     */               } 
/* 373 */               this.roomAbnormalMemoMap.put(locName, memo);
/* 374 */               if (deviceType.equals("溫度")) {
/* 375 */                 this.roomTemperatureAlarmMap.put(locName, Boolean.valueOf(true));
/* 376 */               } else if (deviceType.equals("濕度")) {
/* 377 */                 this.roomHumidityAlarmMap.put(locName, Boolean.valueOf(true));
/*     */               }
/*     */             
/* 380 */             } else if (!isTempOrHum.booleanValue() && statusValue < rtuConfig.getLowerLimit().intValue()) {
/* 381 */               String memo = this.roomAbnormalMemoMap.get(locName);
/* 382 */               if (memo == null) {
/* 383 */                 memo = deviceType + "過低,";
/*     */               } else {
/* 385 */                 memo = memo + deviceType + "過低,";
/*     */               } 
/*     */             } 
/*     */           } else {
/*     */             
/* 390 */             status = String.valueOf(
/* 391 */                 (Integer)SerializationUtils.deserialize(tcStatus.getContextData()) + unit);
/*     */           } 
/*     */         } else {
/* 394 */           status = "";
/*     */         } 
/* 396 */       } else if (rtuConfig != null && SignalType.DIGITAL_OUT.name().equals(signalType)) {
/* 397 */         if (contextData != null) {
/* 398 */           Integer value = (Integer)SerializationUtils.deserialize(tcStatus.getContextData());
/* 399 */           status = (value.intValue() == 1) ? "開啟" : "關閉";
/*     */         } else {
/* 401 */           status = "";
/*     */         } 
/*     */       } 
/*     */     } 
/* 405 */     return status;
/*     */   }
/*     */   
/*     */   private List<PreviewGridHeader> genPreviewGridHeaders(List<Map<String, Object>> reportMap) {
/* 409 */     List<PreviewGridHeader> headers = new ArrayList<>();
/*     */     
/* 411 */     PreviewGridHeader h1 = new PreviewGridHeader();
/* 412 */     h1.setColumnId("line");
/* 413 */     h1.setHeader(this.messageSourceExt.getMessage("國道"));
/* 414 */     h1.setWidth(Integer.valueOf(150));
/* 415 */     headers.add(h1);
/*     */     
/* 417 */     PreviewGridHeader h2 = new PreviewGridHeader();
/* 418 */     h2.setColumnId("location");
/* 419 */     h2.setHeader(this.messageSourceExt.getMessage("機房"));
/* 420 */     h2.setWidth(Integer.valueOf(150));
/* 421 */     headers.add(h2);
/*     */     
/* 423 */     PreviewGridHeader h3 = new PreviewGridHeader();
/* 424 */     h3.setColumnId("temperature");
/* 425 */     h3.setHeader(this.messageSourceExt.getMessage("溫度(30°C↓)"));
/* 426 */     h3.setWidth(Integer.valueOf(150));
/* 427 */     headers.add(h3);
/*     */     
/* 429 */     PreviewGridHeader h4 = new PreviewGridHeader();
/* 430 */     h4.setColumnId("humidity");
/* 431 */     h4.setHeader(this.messageSourceExt.getMessage("濕度(80%↓)"));
/* 432 */     h4.setWidth(Integer.valueOf(150));
/* 433 */     headers.add(h4);
/*     */     
/* 435 */     PreviewGridHeader h5 = new PreviewGridHeader();
/* 436 */     h5.setColumnId("temperatureHumidityAbnormal");
/* 437 */     h5.setHeader(this.messageSourceExt.getMessage("溫、濕度不合格"));
/* 438 */     h5.setWidth(Integer.valueOf(150));
/* 439 */     headers.add(h5);
/*     */     
/* 441 */     PreviewGridHeader h6 = new PreviewGridHeader();
/* 442 */     h6.setColumnId("waterStatus");
/* 443 */     h6.setHeader(this.messageSourceExt.getMessage("用水量"));
/* 444 */     h6.setWidth(Integer.valueOf(150));
/* 445 */     headers.add(h6);
/*     */     
/* 447 */     PreviewGridHeader h7 = new PreviewGridHeader();
/* 448 */     h7.setColumnId("powerStatus");
/* 449 */     h7.setHeader(this.messageSourceExt.getMessage("用電量"));
/* 450 */     h7.setWidth(Integer.valueOf(300));
/* 451 */     headers.add(h7);
/*     */     
/* 453 */     PreviewGridHeader h8 = new PreviewGridHeader();
/* 454 */     h8.setColumnId("airConditioner");
/* 455 */     h8.setHeader(this.messageSourceExt.getMessage("AB空調"));
/* 456 */     h8.setWidth(Integer.valueOf(150));
/* 457 */     headers.add(h8);
/*     */     
/* 459 */     PreviewGridHeader h9 = new PreviewGridHeader();
/* 460 */     h9.setColumnId("memo");
/* 461 */     h9.setHeader(this.messageSourceExt.getMessage("備註"));
/* 462 */     h9.setWidth(Integer.valueOf(300));
/* 463 */     headers.add(h9);
/*     */     
/* 465 */     return headers;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\rpt\AoRoomEnvironmentMonitorInquiryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */