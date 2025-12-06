/*     */ package com.hwacom.ngtms.ao.fm.service.rpt;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceLocationMappingConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcStatus;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceType;
/*     */ import com.hwacom.ngtms.c.fm.repository.DeviceLocationMappingConfigRepository;
/*     */ import com.hwacom.ngtms.common.rpt.ReportInquiry;
/*     */ import com.hwacom.ngtms.common.shared.PreviewGridData;
/*     */ import com.hwacom.ngtms.common.shared.PreviewGridHeader;
/*     */ import com.hwacom.ngtms.common.shared.PreviewGridRow;
/*     */ import com.hwacom.ngtms.room.shared.RtuConfig;
/*     */ import com.hwacom.ngtms.room.shared.SignalType;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
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
/*     */ public class AoRoomMonitorPointStatusReportInquiryImpl
/*     */   implements ReportInquiry
/*     */ {
/*  43 */   private static Logger logger = LoggerFactory.getLogger(AoRoomMonitorPointStatusReportInquiryImpl.class);
/*     */   
/*  45 */   private Gson gson = new Gson();
/*     */   
/*     */   @Autowired
/*     */   private DeviceLocationMappingConfigRepository deviceLocationMappingConfigRepository;
/*     */   
/*     */   @Autowired
/*     */   private MessageSourceExt messageSourceExt;
/*     */   
/*     */   public List<Map<String, Object>> inquire(Map<String, Object> inputParameter) {
/*  54 */     if (!inputParameter.containsKey("location")) {
/*  55 */       logger.warn("Get inputParameter: location fail! InputParameter: '{}'", inputParameter);
/*  56 */       return Collections.emptyList();
/*     */     } 
/*  58 */     if (!inputParameter.containsKey("deviceType")) {
/*  59 */       logger.warn("Get inputParameter: deviceType fail! InputParameter: '{}'", inputParameter);
/*  60 */       return Collections.emptyList();
/*     */     } 
/*  62 */     if (!inputParameter.containsKey("signalType")) {
/*  63 */       logger.warn("Get inputParameter: signalType fail! InputParameter: '{}'", inputParameter);
/*  64 */       return Collections.emptyList();
/*     */     } 
/*     */ 
/*     */     
/*  68 */     String locationStr = (String)inputParameter.get("location");
/*  69 */     String deviceTypeStr = (String)inputParameter.get("deviceType");
/*  70 */     String signalTypeStr = (String)inputParameter.get("signalType");
/*     */ 
/*     */     
/*  73 */     String userName = inputParameter.containsKey("userName") ? (String)inputParameter.get("userName") : "";
/*  74 */     logger.debug("locationStr:'{}'", locationStr);
/*  75 */     logger.debug("deviceTypeStr:'{}'", deviceTypeStr);
/*  76 */     logger.debug("signalTypeStr:'{}'", signalTypeStr);
/*     */     
/*  78 */     String chartKey = (String)inputParameter.get("CHART_KEY");
/*     */     
/*  80 */     List<Map<String, Object>> resultMapArray = new ArrayList<>();
/*     */ 
/*     */     
/*  83 */     Map<String, Object> tableDataMap = new HashMap<>();
/*     */     
/*  85 */     IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  86 */     IMap<String, DeviceType> deviceTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/*  87 */     IMap<String, DeviceTcStatus> deviceStatusMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcStatus);
/*  88 */     List<DeviceTcStatus> rptRoomMonitorPointStatus = new ArrayList<>();
/*  89 */     List<String> allRoomDeviceType = new ArrayList<>();
/*     */     
/*  91 */     PredicateBuilder roomDeviceTypePb = (new PredicateBuilder()).getEntryObject().get("category").equal("ROOM");
/*  92 */     for (DeviceType deviceType : deviceTypeMap.values((Predicate)roomDeviceTypePb)) {
/*     */       
/*  94 */       if (!deviceType.getId().equals("cardReader")) {
/*  95 */         allRoomDeviceType.add(deviceType.getId());
/*     */       }
/*     */     } 
/*  98 */     if (locationStr.equals("全部")) {
/*  99 */       if (deviceTypeStr.equals("全部")) {
/* 100 */         String[] deviceTypeArr = allRoomDeviceType.<String>toArray(new String[0]);
/*     */         
/* 102 */         PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceType").in((Comparable[])deviceTypeArr);
/* 103 */         for (DeviceTcStatus status : deviceStatusMap.values((Predicate)pb)) {
/* 104 */           rptRoomMonitorPointStatus.add(status);
/*     */         }
/*     */       } else {
/*     */         
/* 108 */         PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceType").equal(deviceTypeStr);
/* 109 */         for (DeviceTcStatus status : deviceStatusMap.values((Predicate)pb)) {
/* 110 */           rptRoomMonitorPointStatus.add(status);
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 115 */       List<DeviceLocationMappingConfig> mappingConfigs = this.deviceLocationMappingConfigRepository.findByLocationName(locationStr);
/* 116 */       List<String> roomDevices = new ArrayList<>();
/* 117 */       for (DeviceLocationMappingConfig mappingConfig : mappingConfigs) {
/*     */         
/* 119 */         if (((DeviceTcConfig)deviceMap.get(mappingConfig.getDeviceName())).getExtend() != null) {
/* 120 */           roomDevices.add(mappingConfig.getDeviceName());
/*     */         }
/*     */       } 
/* 123 */       String[] deviceNameArr = roomDevices.<String>toArray(new String[0]);
/* 124 */       if (deviceTypeStr.equals("全部")) {
/* 125 */         PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("id").in((Comparable[])deviceNameArr);
/* 126 */         for (DeviceTcStatus status : deviceStatusMap.values((Predicate)pb)) {
/* 127 */           rptRoomMonitorPointStatus.add(status);
/*     */         }
/*     */       } else {
/* 130 */         EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*     */         
/* 132 */         PredicateBuilder pb = eo.get("id").in((Comparable[])deviceNameArr).and((Predicate)eo.get("deviceType").equal(deviceTypeStr));
/* 133 */         for (DeviceTcStatus status : deviceStatusMap.values((Predicate)pb)) {
/* 134 */           rptRoomMonitorPointStatus.add(status);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 140 */     if (signalTypeStr.contains("AI")) {
/*     */       
/* 142 */       List<DeviceTcStatus> aiDeviceStatus = filterDeviceTcStatusBySignalType(rptRoomMonitorPointStatus, SignalType.ANALOG_IN
/* 143 */           .toString());
/* 144 */       rptRoomMonitorPointStatus.clear();
/* 145 */       rptRoomMonitorPointStatus.addAll(aiDeviceStatus);
/* 146 */     } else if (signalTypeStr.contains("DI")) {
/*     */       
/* 148 */       List<DeviceTcStatus> diDeviceStatus = filterDeviceTcStatusBySignalType(rptRoomMonitorPointStatus, SignalType.DIGITAL_IN
/* 149 */           .toString());
/* 150 */       rptRoomMonitorPointStatus.clear();
/* 151 */       rptRoomMonitorPointStatus.addAll(diDeviceStatus);
/* 152 */     } else if (signalTypeStr.contains("DO")) {
/*     */       
/* 154 */       List<DeviceTcStatus> doDeviceStatus = filterDeviceTcStatusBySignalType(rptRoomMonitorPointStatus, SignalType.DIGITAL_OUT
/* 155 */           .toString());
/* 156 */       rptRoomMonitorPointStatus.clear();
/* 157 */       rptRoomMonitorPointStatus.addAll(doDeviceStatus);
/*     */     } 
/*     */     
/* 160 */     logger.debug("rptRoomMonitorPointStatus.size:'{}'", Integer.valueOf(rptRoomMonitorPointStatus.size()));
/*     */     
/* 162 */     HashMap[] arrayOfHashMap = new HashMap[rptRoomMonitorPointStatus.size()];
/* 163 */     if (rptRoomMonitorPointStatus.size() == 0) {
/* 164 */       arrayOfHashMap = new HashMap[1];
/*     */     }
/* 166 */     int row = 0;
/*     */     
/* 168 */     for (DeviceTcStatus status : rptRoomMonitorPointStatus) {
/* 169 */       Map<String, Object> roomMonitorPointStatus = new HashMap<>();
/* 170 */       String deviceName = status.getId();
/* 171 */       DeviceTcConfig deviceConfig = (DeviceTcConfig)deviceMap.get(deviceName);
/* 172 */       DeviceType deviceType = null;
/*     */       
/* 174 */       DeviceLocationMappingConfig mappingConfig = this.deviceLocationMappingConfigRepository.findByDeviceName(deviceName);
/* 175 */       RtuConfig rtuConfig = null;
/* 176 */       if (mappingConfig != null) {
/* 177 */         roomMonitorPointStatus.put("location", mappingConfig.getLocationName());
/*     */       }
/* 179 */       if (deviceConfig != null) {
/* 180 */         deviceType = (DeviceType)deviceTypeMap.get(deviceConfig.getDeviceType());
/* 181 */         if (deviceType != null && 
/* 182 */           !deviceType.getId().equals("RTU") && 
/* 183 */           !deviceType.getId().equals("SMR") && 
/* 184 */           !deviceType.getId().equals("BMS")) {
/* 185 */           rtuConfig = (RtuConfig)this.gson.fromJson(deviceConfig.getExtend(), RtuConfig.class);
/*     */         }
/* 187 */         roomMonitorPointStatus.put("pointName", deviceConfig.getDisplayName());
/*     */       } else {
/* 189 */         roomMonitorPointStatus.put("pointName", deviceName);
/*     */       } 
/* 191 */       if (deviceType != null) {
/* 192 */         roomMonitorPointStatus.put("deviceType", deviceType.getDescription());
/*     */       }
/* 194 */       if (rtuConfig != null && 
/* 195 */         rtuConfig.getSignalType() != null) {
/* 196 */         roomMonitorPointStatus.put("signalType", 
/* 197 */             getSignalNameFromSignalStr(rtuConfig.getSignalType()));
/* 198 */         roomMonitorPointStatus.put("status", 
/* 199 */             getStatusFromDeviceTcStatus(status, rtuConfig.getSignalType()));
/*     */       } 
/*     */       
/* 202 */       arrayOfHashMap[row++] = (HashMap)roomMonitorPointStatus;
/*     */     } 
/*     */     
/* 205 */     tableDataMap.put("roomMonitorPointStatus", arrayOfHashMap);
/* 206 */     tableDataMap.put("CHART_KEY", chartKey);
/* 207 */     tableDataMap.put("location", locationStr);
/* 208 */     tableDataMap.put("deviceType", deviceTypeStr);
/* 209 */     tableDataMap.put("signalType", signalTypeStr);
/* 210 */     tableDataMap.put("userName", userName);
/*     */     
/* 212 */     resultMapArray.add(tableDataMap);
/* 213 */     return resultMapArray;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<DeviceTcStatus> filterDeviceTcStatusBySignalType(List<DeviceTcStatus> statusList, String signalType) {
/* 218 */     List<DeviceTcStatus> result = new ArrayList<>();
/* 219 */     IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 220 */     for (DeviceTcStatus status : statusList) {
/* 221 */       DeviceTcConfig deviceConfig = (DeviceTcConfig)deviceMap.get(status.getId());
/* 222 */       RtuConfig rtuConfig = (RtuConfig)this.gson.fromJson(deviceConfig.getExtend(), RtuConfig.class);
/* 223 */       if (rtuConfig != null && rtuConfig
/* 224 */         .getSignalType() != null && rtuConfig
/* 225 */         .getSignalType().equals(signalType)) {
/* 226 */         result.add(status);
/*     */       }
/*     */     } 
/* 229 */     return result;
/*     */   }
/*     */   
/*     */   private String getSignalNameFromSignalStr(String signalStr) {
/* 233 */     switch (signalStr) {
/*     */       case "ANALOG_IN":
/* 235 */         return "AI(類比輸入)";
/*     */       case "DIGITAL_IN":
/* 237 */         return "DI(數位輸入)";
/*     */       case "DIGITAL_OUT":
/* 239 */         return "DO(數位輸出)";
/*     */     } 
/* 241 */     return signalStr;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getStatusFromDeviceTcStatus(DeviceTcStatus tcStatus, String signalType) {
/* 246 */     IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 247 */     String status = "";
/* 248 */     String deviceName = tcStatus.getId();
/* 249 */     Integer commStatus = tcStatus.getCommStatus();
/* 250 */     String deviceType = tcStatus.getDeviceType();
/* 251 */     if (commStatus.intValue() == 1)
/* 252 */       return "斷線"; 
/* 253 */     if (commStatus.intValue() == 0) {
/* 254 */       byte[] contextData = tcStatus.getContextData();
/* 255 */       DeviceTcConfig deviceConfig = (DeviceTcConfig)deviceMap.get(deviceName);
/* 256 */       RtuConfig rtuConfig = (RtuConfig)this.gson.fromJson(deviceConfig.getExtend(), RtuConfig.class);
/* 257 */       if (SignalType.DIGITAL_IN.name().equals(signalType)) {
/* 258 */         Integer value = null;
/* 259 */         if (contextData != null) {
/* 260 */           value = (Integer)SerializationUtils.deserialize(tcStatus.getContextData());
/*     */         }
/* 262 */         if (value != null && value.intValue() == -1) {
/* 263 */           status = "斷線";
/* 264 */         } else if (value != null) {
/* 265 */           if (deviceType.equals("機房門禁")) {
/* 266 */             if (value.intValue() == 1) {
/* 267 */               status = "正常";
/* 268 */             } else if (value.intValue() == 0) {
/* 269 */               status = "開啟";
/*     */             }
/*     */           
/* 272 */           } else if (deviceType.equals("氣冷式冰水主機")) {
/* 273 */             if (value.intValue() == 1) {
/* 274 */               status = "運轉";
/* 275 */             } else if (value.intValue() == 0) {
/* 276 */               status = "正常";
/*     */             } 
/* 278 */           } else if (deviceType.equals("充電機狀態")) {
/* 279 */             if (value.intValue() == 1) {
/* 280 */               status = "告警";
/* 281 */             } else if (value.intValue() == 0) {
/* 282 */               status = "正常";
/*     */             }
/*     */           
/* 285 */           } else if (value.intValue() == 1) {
/* 286 */             status = "啟動";
/* 287 */           } else if (value.intValue() == 0) {
/* 288 */             status = "正常";
/*     */           }
/*     */         
/*     */         } 
/* 292 */       } else if (SignalType.ANALOG_IN.name().equals(signalType)) {
/* 293 */         String unit = "";
/* 294 */         if (rtuConfig != null) {
/* 295 */           unit = rtuConfig.getUnit();
/*     */         }
/* 297 */         if (contextData != null) {
/* 298 */           if ((deviceType.equals("濕度") || deviceType
/* 299 */             .equals("溫度") || deviceType
/* 300 */             .equals("DC電壓") || deviceType
/* 301 */             .equals("油槽容量") || deviceType
/* 302 */             .equals("電流") || deviceType
/* 303 */             .equals("FM發射機") || deviceType
/* 304 */             .equals("發射機輸出功率")) && (
/* 305 */             (Double)SerializationUtils.deserialize(tcStatus.getContextData())).doubleValue() != -1.0D) {
/*     */             
/* 307 */             String displayValue = String.valueOf((
/* 308 */                 (Double)SerializationUtils.deserialize(tcStatus.getContextData())).doubleValue());
/* 309 */             String updateValue = displayValue;
/* 310 */             if (displayValue.length() == 3) {
/* 311 */               updateValue = displayValue.substring(0, 3);
/* 312 */             } else if (displayValue.length() == 4) {
/* 313 */               updateValue = displayValue.substring(0, 4);
/* 314 */             } else if (displayValue.length() > 4) {
/* 315 */               updateValue = displayValue.substring(0, 5);
/*     */             } 
/* 317 */             status = updateValue + unit;
/*     */           } else {
/*     */             
/* 320 */             status = String.valueOf(
/* 321 */                 (Integer)SerializationUtils.deserialize(tcStatus.getContextData()) + unit);
/*     */           } 
/*     */         } else {
/* 324 */           status = "";
/*     */         } 
/* 326 */       } else if (rtuConfig != null && SignalType.DIGITAL_OUT
/* 327 */         .name().equals(rtuConfig.getSignalType())) {
/* 328 */         if (contextData != null) {
/* 329 */           Integer value = (Integer)SerializationUtils.deserialize(tcStatus.getContextData());
/* 330 */           status = (value.intValue() == 1) ? "開啟" : "關閉";
/*     */         } else {
/* 332 */           status = "";
/*     */         } 
/*     */       } 
/*     */     } 
/* 336 */     return status;
/*     */   }
/*     */ 
/*     */   
/*     */   public PreviewGridData inquirePreviewGridData(Map<String, Object> inputParameter) {
/* 341 */     List<Map<String, Object>> reportMap = inquire(inputParameter);
/*     */     
/* 343 */     Map[] arrayOfMap = (Map[])((Map)reportMap.get(0)).get("roomMonitorPointStatus");
/* 344 */     if (reportMap.size() == 0) {
/* 345 */       return new PreviewGridData();
/*     */     }
/*     */ 
/*     */     
/* 349 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/* 350 */     List<PreviewGridRow> rows = new ArrayList<>();
/* 351 */     for (Map<String, Object> record : arrayOfMap) {
/*     */       
/* 353 */       record.put("startTime", dateFormat.format(record.get("startTime")));
/*     */       
/* 355 */       PreviewGridRow row = new PreviewGridRow();
/* 356 */       row.setId(UUID.randomUUID().toString());
/* 357 */       row.setColumnValMap(record);
/* 358 */       rows.add(row);
/*     */     } 
/*     */     
/* 361 */     PreviewGridData result = new PreviewGridData();
/* 362 */     result.setHeaders(genPreviewGridHeaders(reportMap));
/* 363 */     result.setRows(rows);
/* 364 */     return result;
/*     */   }
/*     */   
/*     */   private List<PreviewGridHeader> genPreviewGridHeaders(List<Map<String, Object>> reportMap) {
/* 368 */     List<PreviewGridHeader> headers = new ArrayList<>();
/*     */     
/* 370 */     PreviewGridHeader h1 = new PreviewGridHeader();
/* 371 */     h1.setColumnId("location");
/* 372 */     h1.setHeader(this.messageSourceExt.getMessage("機房名稱"));
/* 373 */     h1.setWidth(Integer.valueOf(300));
/* 374 */     headers.add(h1);
/*     */     
/* 376 */     PreviewGridHeader h2 = new PreviewGridHeader();
/* 377 */     h2.setColumnId("pointName");
/* 378 */     h2.setHeader(this.messageSourceExt.getMessage("監視點名稱"));
/* 379 */     h2.setWidth(Integer.valueOf(300));
/* 380 */     headers.add(h2);
/*     */     
/* 382 */     PreviewGridHeader h3 = new PreviewGridHeader();
/* 383 */     h3.setColumnId("deviceType");
/* 384 */     h3.setHeader(this.messageSourceExt.getMessage("設備種類"));
/* 385 */     h3.setWidth(Integer.valueOf(300));
/* 386 */     headers.add(h3);
/*     */     
/* 388 */     PreviewGridHeader h4 = new PreviewGridHeader();
/* 389 */     h4.setColumnId("signalType");
/* 390 */     h4.setHeader(this.messageSourceExt.getMessage("訊號種類"));
/* 391 */     h4.setWidth(Integer.valueOf(100));
/* 392 */     headers.add(h4);
/*     */     
/* 394 */     PreviewGridHeader h5 = new PreviewGridHeader();
/* 395 */     h5.setColumnId("status");
/* 396 */     h5.setHeader(this.messageSourceExt.getMessage("即時狀態"));
/* 397 */     h5.setWidth(Integer.valueOf(200));
/* 398 */     headers.add(h5);
/*     */     
/* 400 */     return headers;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\rpt\AoRoomMonitorPointStatusReportInquiryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */