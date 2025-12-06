/*     */ package com.hwacom.ngtms.ao.fm.service.rpt;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.alarm.fm.model.AlarmLog;
/*     */ import com.hwacom.ngtms.alarm.fm.repository.AlarmLogRepository;
/*     */ import com.hwacom.ngtms.ao.shared.AlarmType;
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
/*     */ import com.hwacom.ngtms.pd.fm.model.PdLoopDeviceConfig;
/*     */ import com.hwacom.ngtms.pd.fm.repository.PdLoopDeviceConfigRepository;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ @Service
/*     */ public class AoPdLoopDeviceAlarmReportInquiryImpl
/*     */   implements ReportInquiry
/*     */ {
/*  47 */   private static Logger logger = LoggerFactory.getLogger(AoPdLoopDeviceAlarmReportInquiryImpl.class);
/*     */ 
/*     */   
/*     */   private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
/*     */   
/*  52 */   private static final String ALARM_SUB_TYPES_ALL = AlarmType.PRIMARY_ABNORMAL_R
/*  53 */     .toString() + "," + AlarmType.PRIMARY_ABNORMAL_S
/*     */     
/*  55 */     .toString() + "," + AlarmType.PRIMARY_ABNORMAL_T
/*     */     
/*  57 */     .toString() + "," + AlarmType.SECONDARY_ABNORMAL_R
/*     */     
/*  59 */     .toString() + "," + AlarmType.SECONDARY_ABNORMAL_S
/*     */     
/*  61 */     .toString() + "," + AlarmType.SECONDARY_ABNORMAL_T
/*     */     
/*  63 */     .toString() + "," + AlarmType.BRANCH_CIRCUIT_ABNORMAL_1
/*     */     
/*  65 */     .toString() + "," + AlarmType.BRANCH_CIRCUIT_ABNORMAL_2
/*     */     
/*  67 */     .toString() + "," + AlarmType.BRANCH_CIRCUIT_ABNORMAL_3
/*     */     
/*  69 */     .toString() + "," + AlarmType.BRANCH_CIRCUIT_ABNORMAL_4
/*     */     
/*  71 */     .toString() + "," + AlarmType.BRANCH_CIRCUIT_ABNORMAL_5
/*     */     
/*  73 */     .toString();
/*     */   
/*     */   @Autowired
/*     */   private AlarmLogRepository alarmLogRepository;
/*     */   
/*     */   @Autowired
/*     */   private DeviceTcConfigRepository deviceTcConfigRepository;
/*     */   @Autowired
/*     */   private DeviceLocationMappingConfigRepository deviceLocationMappingConfigRepository;
/*     */   @Autowired
/*     */   private MessageSourceExt messageSourceExt;
/*     */   @Autowired
/*     */   private AoRptValueFormatter formatter;
/*     */   @Autowired
/*     */   private PdLoopDeviceConfigRepository pdLoopDeviceConfigRepository;
/*     */   
/*     */   public List<Map<String, Object>> inquire(Map<String, Object> inputParameter) {
/*  90 */     IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*     */     
/*  92 */     if (!inputParameter.containsKey("devices")) {
/*  93 */       logger.warn("Get inputParameter: devices fail! InputParameter: '{}'", inputParameter);
/*  94 */       return Collections.emptyList();
/*     */     } 
/*  96 */     if (!inputParameter.containsKey("startDateTime")) {
/*  97 */       logger.warn("Get inputParameter: startDateTime fail! InputParameter: '{}'", inputParameter);
/*  98 */       return Collections.emptyList();
/*     */     } 
/* 100 */     if (!inputParameter.containsKey("endDateTime")) {
/* 101 */       logger.warn("Get inputParameter: endDateTime fail! InputParameter: '{}'", inputParameter);
/* 102 */       return Collections.emptyList();
/*     */     } 
/* 104 */     if (!inputParameter.containsKey("mileage")) {
/* 105 */       logger.warn("Get inputParameter: mileage fail! InputParameter: '{}'", inputParameter);
/* 106 */       return Collections.emptyList();
/*     */     } 
/* 108 */     if (!inputParameter.containsKey("direction")) {
/* 109 */       logger.warn("Get inputParameter: direction fail! InputParameter: '{}'", inputParameter);
/* 110 */       return Collections.emptyList();
/*     */     } 
/* 112 */     if (!inputParameter.containsKey("item")) {
/* 113 */       logger.warn("Get inputParameter: item fail! InputParameter: '{}'", inputParameter);
/* 114 */       return Collections.emptyList();
/*     */     } 
/* 116 */     if (!inputParameter.containsKey("status")) {
/* 117 */       logger.warn("Get inputParameter: status fail! InputParameter: '{}'", inputParameter);
/* 118 */       return Collections.emptyList();
/*     */     } 
/* 120 */     if (!inputParameter.containsKey("location")) {
/* 121 */       logger.warn("Get inputParameter: location fail! InputParameter: '{}'", inputParameter);
/* 122 */       return Collections.emptyList();
/*     */     } 
/*     */ 
/*     */     
/* 126 */     String startDateTimeStr = (String)inputParameter.get("startDateTime");
/* 127 */     String endDateTimeStr = (String)inputParameter.get("endDateTime");
/* 128 */     String deviceNamesStr = (String)inputParameter.get("devices");
/* 129 */     String directionStr = (String)inputParameter.get("direction");
/* 130 */     Direction direction = this.formatter.getBeforeFormattedDirection(directionStr);
/* 131 */     String mileageStr = (String)inputParameter.get("mileage");
/* 132 */     String lineNameStr = (String)inputParameter.get("lineName");
/* 133 */     String alarmTypeStr = (String)inputParameter.get("item");
/* 134 */     String statusStr = (String)inputParameter.get("status");
/* 135 */     String locationStr = (String)inputParameter.get("location");
/*     */     
/* 137 */     logger.debug("startDateTimeStr:'{}'", startDateTimeStr);
/* 138 */     logger.debug("endDateTimeStr:'{}'", endDateTimeStr);
/* 139 */     logger.debug("deviceNamesStr:'{}'", deviceNamesStr);
/* 140 */     logger.debug("direction:'{}'", direction);
/* 141 */     logger.debug("mileageStr:'{}'", mileageStr);
/* 142 */     logger.debug("lineNameStr:'{}'", lineNameStr);
/* 143 */     logger.debug("alarmTypeStr:'{}'", alarmTypeStr);
/* 144 */     logger.debug("statusStr:'{}'", statusStr);
/* 145 */     logger.debug("locationStr:'{}'", locationStr);
/*     */ 
/*     */     
/* 148 */     Boolean quiryByDevices = Boolean.valueOf(((String)inputParameter.get("condition")).equals("device"));
/*     */ 
/*     */ 
/*     */     
/* 152 */     Boolean showDeviceName = inputParameter.containsKey("showDeviceName") ? Boolean.valueOf((String)inputParameter.get("showDeviceName")) : null;
/*     */ 
/*     */     
/* 155 */     String userName = inputParameter.containsKey("userName") ? (String)inputParameter.get("userName") : "";
/*     */     
/* 157 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 158 */     Date startDateTime = null;
/* 159 */     Date endDateTime = null;
/*     */     try {
/* 161 */       startDateTime = dateFormat.parse(startDateTimeStr);
/* 162 */       endDateTime = dateFormat.parse(endDateTimeStr);
/* 163 */     } catch (ParseException e) {
/* 164 */       logger.warn("Parse date string failed! startDateTime:'{}', endDateTime:'{}'", new Object[] { startDateTimeStr, endDateTimeStr, e });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 169 */       return Collections.emptyList();
/*     */     } 
/*     */     
/* 172 */     List<String> mileage = Arrays.asList(mileageStr.split(","));
/* 173 */     List<Integer> mileages = new ArrayList<>();
/* 174 */     for (String value : mileage) {
/* 175 */       mileages.add(Integer.valueOf(value));
/*     */     }
/* 177 */     if (((Integer)mileages.get(0)).equals(Integer.valueOf(-1))) {
/* 178 */       mileages.clear();
/*     */     }
/* 180 */     Integer startMileage = Integer.valueOf(0);
/* 181 */     Integer endMileage = Integer.valueOf(0);
/* 182 */     String tableDataMileage = new String();
/* 183 */     if (mileageStr != "-1" && mileages.size() > 0) {
/* 184 */       startMileage = Integer.valueOf(((Integer)mileages.get(0)).intValue() * 1000 + ((Integer)mileages.get(1)).intValue());
/* 185 */       endMileage = Integer.valueOf(((Integer)mileages.get(2)).intValue() * 1000 + ((Integer)mileages.get(3)).intValue());
/* 186 */       String startMeter = String.valueOf(mileages.get(1));
/* 187 */       String endMeter = String.valueOf(mileages.get(3));
/* 188 */       if (((Integer)mileages.get(1)).intValue() < 100 && ((Integer)mileages.get(1)).intValue() > 9) {
/* 189 */         startMeter = "0" + startMeter;
/* 190 */       } else if (((Integer)mileages.get(1)).intValue() < 10) {
/* 191 */         startMeter = "00" + startMeter;
/*     */       } 
/*     */       
/* 194 */       if (((Integer)mileages.get(3)).intValue() < 100 && ((Integer)mileages.get(3)).intValue() > 9) {
/* 195 */         endMeter = "0" + endMeter;
/* 196 */       } else if (((Integer)mileages.get(3)).intValue() < 10) {
/* 197 */         endMeter = "00" + endMeter;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 205 */       tableDataMileage = String.valueOf(mileages.get(0)) + "K" + startMeter + "~" + String.valueOf(mileages.get(2)) + "K" + endMeter;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 211 */     List<String> deviceNames = Arrays.asList(deviceNamesStr.split(","));
/*     */ 
/*     */     
/* 214 */     String chartKey = (String)inputParameter.get("CHART_KEY");
/*     */     
/* 216 */     List<Map<String, Object>> resultMapArray = new ArrayList<>();
/*     */ 
/*     */     
/* 219 */     Map<String, Object> tableDataMap = new HashMap<>();
/*     */     
/* 221 */     Sort sort = Sort.by(new String[] { "timestamp", "deviceName" });
/* 222 */     Sort deviceSort = Sort.by(new String[] { "deviceName", "lineId" });
/*     */     
/* 224 */     List<AlarmLog> rptAlarms = new ArrayList<>();
/*     */     
/* 226 */     List<String> alarmSubTypeIds = new ArrayList<>();
/* 227 */     String[] typeArr = ALARM_SUB_TYPES_ALL.split(",");
/* 228 */     for (String value : typeArr) {
/* 229 */       alarmSubTypeIds.add(value);
/*     */     }
/*     */     
/* 232 */     if (quiryByDevices.booleanValue()) {
/*     */       
/* 234 */       List<String> quiryNames = new ArrayList<>();
/* 235 */       for (String name : deviceNames) {
/* 236 */         quiryNames.add(name + "-loop1Status");
/* 237 */         quiryNames.add(name + "-loop2Status");
/* 238 */         quiryNames.add(name + "-loop3Status");
/* 239 */         quiryNames.add(name + "-loop4Status");
/* 240 */         quiryNames.add(name + "-loop5Status");
/* 241 */         quiryNames.add(name + "-primaryR");
/* 242 */         quiryNames.add(name + "-primaryS");
/* 243 */         quiryNames.add(name + "-primaryT");
/* 244 */         quiryNames.add(name + "-secondaryR");
/* 245 */         quiryNames.add(name + "-secondaryS");
/* 246 */         quiryNames.add(name + "-secondaryT");
/*     */       } 
/* 248 */       if (deviceNames.size() > 0 && quiryNames.size() > 0) {
/* 249 */         List<AlarmLog> alarms = new ArrayList<>();
/*     */         
/* 251 */         if (quiryNames.size() > 1635) {
/* 252 */           List<String> quiryName1 = quiryNames.subList(0, 1635);
/* 253 */           List<AlarmLog> alarm1 = new ArrayList<>();
/*     */           
/* 255 */           alarm1 = this.alarmLogRepository.findByDeviceNamesAndTimestampAndAlarmSubTypeIds(quiryName1, startDateTime, endDateTime, alarmSubTypeIds, sort);
/*     */           
/* 257 */           alarms.addAll(alarm1);
/* 258 */           List<String> quiryName2 = quiryNames.subList(1636, quiryNames.size());
/* 259 */           List<AlarmLog> alarm2 = new ArrayList<>();
/*     */           
/* 261 */           alarm2 = this.alarmLogRepository.findByDeviceNamesAndTimestampAndAlarmSubTypeIds(quiryName2, startDateTime, endDateTime, alarmSubTypeIds, sort);
/*     */           
/* 263 */           alarms.addAll(alarm2);
/*     */         } else {
/*     */           
/* 266 */           alarms = this.alarmLogRepository.findByDeviceNamesAndTimestampAndAlarmSubTypeIds(quiryNames, startDateTime, endDateTime, alarmSubTypeIds, sort);
/*     */         } 
/*     */         
/* 269 */         rptAlarms.addAll(alarms);
/*     */       } 
/*     */     } else {
/* 272 */       List<DeviceTcConfig> pdCondifgs = new ArrayList<>();
/*     */       
/* 274 */       if (!lineNameStr.equals("-1") && direction == null && mileages.size() == 0) {
/* 275 */         pdCondifgs = this.deviceTcConfigRepository.findByLine(lineNameStr, deviceSort);
/*     */       
/*     */       }
/* 278 */       else if (!lineNameStr.equals("-1") && direction != null && mileages.size() == 0) {
/*     */         
/* 280 */         pdCondifgs = this.deviceTcConfigRepository.findByLineAndDirection(lineNameStr, direction, deviceSort);
/*     */       
/*     */       }
/* 283 */       else if (lineNameStr.equals("-1") && direction == null && mileages.size() > 0) {
/*     */         
/* 285 */         pdCondifgs = this.deviceTcConfigRepository.findByMileageBetween(startMileage, endMileage, deviceSort);
/*     */       
/*     */       }
/* 288 */       else if (!lineNameStr.equals("-1") && direction == null && mileages.size() > 0) {
/*     */         
/* 290 */         pdCondifgs = this.deviceTcConfigRepository.findByLineAndMileageBetween(lineNameStr, startMileage, endMileage, deviceSort);
/*     */ 
/*     */       
/*     */       }
/* 294 */       else if (!lineNameStr.equals("-1") && direction != null && mileages.size() > 0) {
/*     */         
/* 296 */         pdCondifgs = this.deviceTcConfigRepository.findByLineAndDirectionAndMileageBetween(lineNameStr, direction, startMileage, endMileage, deviceSort);
/*     */ 
/*     */       
/*     */       }
/* 300 */       else if (lineNameStr.equals("-1") && direction != null && mileages.size() == 0) {
/* 301 */         pdCondifgs = this.deviceTcConfigRepository.findByDirection(direction, deviceSort);
/*     */       
/*     */       }
/* 304 */       else if (lineNameStr.equals("-1") && direction != null && mileages.size() > 0) {
/*     */         
/* 306 */         pdCondifgs = this.deviceTcConfigRepository.findByDirectionAndMileageBetween(direction, startMileage, endMileage, deviceSort);
/*     */ 
/*     */       
/*     */       }
/* 310 */       else if (lineNameStr.equals("-1") && direction == null && mileages.size() == 0) {
/* 311 */         pdCondifgs = this.deviceTcConfigRepository.findAll(deviceSort);
/*     */       } 
/*     */       
/* 314 */       List<DeviceTcConfig> removedList = new ArrayList<>();
/* 315 */       for (DeviceTcConfig each : pdCondifgs) {
/* 316 */         if (!each.getDeviceType().equals("PD")) {
/* 317 */           removedList.add(each);
/*     */         }
/*     */       } 
/*     */       
/* 321 */       pdCondifgs.removeAll(removedList);
/* 322 */       List<String> condiList = new ArrayList<>();
/* 323 */       for (DeviceTcConfig deviceConfig : pdCondifgs) {
/* 324 */         condiList.add(deviceConfig.getDeviceName());
/*     */       }
/* 326 */       List<String> condiResult = new ArrayList<>();
/* 327 */       if (!locationStr.equals("全部")) {
/*     */         
/* 329 */         List<DeviceLocationMappingConfig> locationDatas = this.deviceLocationMappingConfigRepository.findByLocationNameAndDeviceNames(locationStr, condiList);
/*     */         
/* 331 */         for (DeviceLocationMappingConfig each : locationDatas) {
/* 332 */           String name = each.getDeviceName();
/* 333 */           condiResult.add(name + "-loop1Status");
/* 334 */           condiResult.add(name + "-loop2Status");
/* 335 */           condiResult.add(name + "-loop3Status");
/* 336 */           condiResult.add(name + "-loop4Status");
/* 337 */           condiResult.add(name + "-loop5Status");
/* 338 */           condiResult.add(name + "-primaryR");
/* 339 */           condiResult.add(name + "-primaryS");
/* 340 */           condiResult.add(name + "-primaryT");
/* 341 */           condiResult.add(name + "-secondaryR");
/* 342 */           condiResult.add(name + "-secondaryS");
/* 343 */           condiResult.add(name + "-secondaryT");
/*     */         } 
/*     */       } 
/*     */       
/* 347 */       List<AlarmLog> allpdAlarm = new ArrayList<>();
/* 348 */       if (!locationStr.equals("全部")) {
/*     */         
/* 350 */         allpdAlarm = this.alarmLogRepository.findByDeviceNamesAndTimestampAndAlarmSubTypeIds(condiResult, startDateTime, endDateTime, alarmSubTypeIds, sort);
/*     */       }
/*     */       else {
/*     */         
/* 354 */         allpdAlarm = this.alarmLogRepository.findByTimestampBetweenAndAlarmSubTypeIds(startDateTime, endDateTime, alarmSubTypeIds, sort);
/*     */       } 
/*     */ 
/*     */       
/* 358 */       List<String> coniditionAlarmTypes = getAlarmTypeByItemAndStatus(alarmTypeStr);
/*     */       
/* 360 */       List<AlarmLog> selectAlarm = new ArrayList<>();
/*     */       
/* 362 */       if (coniditionAlarmTypes == null) {
/* 363 */         selectAlarm.addAll(allpdAlarm);
/*     */       } else {
/* 365 */         for (AlarmLog alarm : allpdAlarm) {
/* 366 */           if (coniditionAlarmTypes.contains(alarm.getAlarmSubType())) {
/* 367 */             selectAlarm.add(alarm);
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 373 */       if (selectAlarm.size() > 0) {
/* 374 */         if (statusStr.equals("欠相")) {
/* 375 */           for (AlarmLog log : selectAlarm) {
/* 376 */             if (log.getMessage().equals("異常")) {
/* 377 */               rptAlarms.add(log);
/*     */             }
/*     */           } 
/* 380 */         } else if (statusStr.equals("斷電")) {
/* 381 */           for (AlarmLog log : selectAlarm) {
/* 382 */             if (log.getMessage().equals("訊號未接收!")) {
/* 383 */               rptAlarms.add(log);
/*     */             }
/*     */           } 
/* 386 */         } else if (statusStr.equals("恢復")) {
/* 387 */           for (AlarmLog log : selectAlarm) {
/* 388 */             if (log.getMessage().equals("恢復正常")) {
/* 389 */               rptAlarms.add(log);
/*     */             }
/*     */           } 
/*     */         } else {
/* 393 */           rptAlarms.addAll(selectAlarm);
/*     */         } 
/*     */       }
/*     */     } 
/* 397 */     Integer mapArrSize = Integer.valueOf(0);
/*     */     
/* 399 */     for (AlarmLog alarm : rptAlarms) {
/* 400 */       String pdName = ((DeviceTcConfig)deviceMap.get(alarm.getDeviceName())).getMemo();
/* 401 */       List<String> pdDeviceNameList = new ArrayList<>();
/* 402 */       pdDeviceNameList.add(pdName);
/*     */       
/* 404 */       List<PdLoopDeviceConfig> pdLoopDeviceConfigs = this.pdLoopDeviceConfigRepository.findByPdDeviceNameIn(pdDeviceNameList);
/* 405 */       if (pdLoopDeviceConfigs != null && pdLoopDeviceConfigs.size() > 0) {
/* 406 */         for (PdLoopDeviceConfig pdLoopDeviceConfig : pdLoopDeviceConfigs) {
/* 407 */           DeviceTcConfig loopDevice = (DeviceTcConfig)deviceMap.get(pdLoopDeviceConfig.getDeviceName());
/* 408 */           if (loopDevice != null) {
/* 409 */             mapArrSize = Integer.valueOf(mapArrSize.intValue() + 1);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 415 */     logger.debug("pdLoopDeviceAlarm.size:'{}'", mapArrSize);
/* 416 */     HashMap[] arrayOfHashMap = new HashMap[mapArrSize.intValue()];
/* 417 */     if (mapArrSize.intValue() == 0) {
/* 418 */       arrayOfHashMap = new HashMap[1];
/*     */     }
/*     */     
/* 421 */     logger.debug("rptAlarms.size:'{}'", Integer.valueOf(rptAlarms.size()));
/*     */     
/* 423 */     int row = 0;
/* 424 */     for (AlarmLog alarm : rptAlarms) {
/* 425 */       String pdName = ((DeviceTcConfig)deviceMap.get(alarm.getDeviceName())).getMemo();
/* 426 */       List<String> pdDeviceNameList = new ArrayList<>();
/* 427 */       pdDeviceNameList.add(pdName);
/*     */       
/* 429 */       List<PdLoopDeviceConfig> pdLoopDeviceConfigs = this.pdLoopDeviceConfigRepository.findByPdDeviceNameIn(pdDeviceNameList);
/* 430 */       if (pdLoopDeviceConfigs != null && pdLoopDeviceConfigs.size() > 0)
/*     */       {
/* 432 */         for (PdLoopDeviceConfig pdLoopDeviceConfig : pdLoopDeviceConfigs) {
/* 433 */           Map<String, Object> pdLoopDeviceAlarm = new HashMap<>();
/* 434 */           DeviceTcConfig pdDeviceConfig = (DeviceTcConfig)deviceMap.get(pdName);
/* 435 */           pdLoopDeviceAlarm.put("pdDeviceName", pdName);
/* 436 */           pdLoopDeviceAlarm.put("location", this.deviceLocationMappingConfigRepository
/*     */               
/* 438 */               .findByDeviceName(pdName).getLocationName());
/* 439 */           pdLoopDeviceAlarm.put("roadLine", this.formatter
/* 440 */               .getFormattedLineName(pdDeviceConfig.getLineId()));
/* 441 */           pdLoopDeviceAlarm.put("direction", this.formatter
/* 442 */               .getBeforeFormattedDirection(pdDeviceConfig.getDirection()));
/* 443 */           pdLoopDeviceAlarm.put("milepost", 
/*     */               
/* 445 */               String.valueOf(pdDeviceConfig.getMilepost().intValue() / 1000) + "K+" + 
/*     */               
/* 447 */               String.valueOf(pdDeviceConfig.getMilepost().intValue() % 1000));
/* 448 */           pdLoopDeviceAlarm.put("timeStamp", dateFormat.format(alarm.getTimestamp()));
/* 449 */           pdLoopDeviceAlarm.put("item", getItemNameFromAlarmType(alarm.getAlarmSubType()));
/* 450 */           pdLoopDeviceAlarm.put("status", getStatusNameFromAalrmType(alarm.getMessage()));
/* 451 */           pdLoopDeviceAlarm.put("loopNo", "" + pdLoopDeviceConfig.getLoopNo());
/*     */           
/* 453 */           DeviceTcConfig loopDevice = (DeviceTcConfig)deviceMap.get(pdLoopDeviceConfig.getDeviceName());
/* 454 */           if (loopDevice != null) {
/* 455 */             pdLoopDeviceAlarm.put("loopDeviceName", loopDevice.getDisplayName());
/* 456 */             pdLoopDeviceAlarm.put("deviceType", loopDevice.getDeviceType());
/* 457 */             pdLoopDeviceAlarm.put("loopDeviceRoadLine", this.formatter
/* 458 */                 .getFormattedLineName(loopDevice.getLineId()));
/* 459 */             pdLoopDeviceAlarm.put("loopDeviceDirection", this.formatter
/*     */                 
/* 461 */                 .getBeforeFormattedDirection(loopDevice.getDirection()));
/* 462 */             pdLoopDeviceAlarm.put("loopDeviceMilepost", 
/*     */                 
/* 464 */                 String.valueOf(loopDevice.getMilepost().intValue() / 1000) + "K+" + 
/*     */                 
/* 466 */                 String.valueOf(loopDevice.getMilepost().intValue() % 1000));
/* 467 */             pdLoopDeviceAlarm.put("diameter", "" + pdLoopDeviceConfig.getDiameter());
/* 468 */             arrayOfHashMap[row++] = (HashMap)pdLoopDeviceAlarm; continue;
/*     */           } 
/* 470 */           logger.debug("loopDevice = null, '{}'", pdLoopDeviceConfig.getDeviceName());
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 475 */     tableDataMap.put("pdLoopDeviceAlarm", arrayOfHashMap);
/* 476 */     tableDataMap.put("CHART_KEY", chartKey);
/* 477 */     tableDataMap.put("showDeviceName", showDeviceName);
/* 478 */     tableDataMap.put("lineName", 
/* 479 */         !lineNameStr.equals("-1") ? this.formatter.getFormattedLineName(lineNameStr) : "全部");
/* 480 */     tableDataMap.put("direction", directionStr);
/* 481 */     tableDataMap.put("mileage", !tableDataMileage.equals("") ? tableDataMileage : "全部");
/* 482 */     tableDataMap.put("status", statusStr);
/* 483 */     tableDataMap.put("location", locationStr);
/* 484 */     tableDataMap.put("userName", userName);
/*     */     
/* 486 */     resultMapArray.add(tableDataMap);
/* 487 */     return resultMapArray;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<String> getAlarmTypeByItemAndStatus(String item) {
/* 492 */     List<String> filterAlarmType = new ArrayList<>();
/* 493 */     if (item.equals("全部")) {
/* 494 */       filterAlarmType = null;
/* 495 */     } else if (item.equals("一次側(R)")) {
/* 496 */       filterAlarmType.add(AlarmType.PRIMARY_ABNORMAL_R.toString());
/* 497 */     } else if (item.equals("一次側(S)")) {
/* 498 */       filterAlarmType.add(AlarmType.PRIMARY_ABNORMAL_S.toString());
/* 499 */     } else if (item.equals("一次側(T)")) {
/* 500 */       filterAlarmType.add(AlarmType.PRIMARY_ABNORMAL_T.toString());
/* 501 */     } else if (item.equals("二次側(R)")) {
/* 502 */       filterAlarmType.add(AlarmType.SECONDARY_ABNORMAL_R.toString());
/* 503 */     } else if (item.equals("二次側(S)")) {
/* 504 */       filterAlarmType.add(AlarmType.SECONDARY_ABNORMAL_S.toString());
/* 505 */     } else if (item.equals("二次側(T)")) {
/* 506 */       filterAlarmType.add(AlarmType.SECONDARY_ABNORMAL_T.toString());
/* 507 */     } else if (item.equals("分迴路(1)")) {
/* 508 */       filterAlarmType.add(AlarmType.BRANCH_CIRCUIT_ABNORMAL_1.toString());
/* 509 */     } else if (item.equals("分迴路(2)")) {
/* 510 */       filterAlarmType.add(AlarmType.BRANCH_CIRCUIT_ABNORMAL_2.toString());
/* 511 */     } else if (item.equals("分迴路(3)")) {
/* 512 */       filterAlarmType.add(AlarmType.BRANCH_CIRCUIT_ABNORMAL_3.toString());
/* 513 */     } else if (item.equals("分迴路(4)")) {
/* 514 */       filterAlarmType.add(AlarmType.BRANCH_CIRCUIT_ABNORMAL_4.toString());
/* 515 */     } else if (item.equals("分迴路(5)")) {
/* 516 */       filterAlarmType.add(AlarmType.BRANCH_CIRCUIT_ABNORMAL_5.toString());
/*     */     } 
/* 518 */     return filterAlarmType;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getItemNameFromAlarmType(String alarmType) {
/* 523 */     if (alarmType == null) {
/* 524 */       return null;
/*     */     }
/* 526 */     if (alarmType.equals(AlarmType.PRIMARY_ABNORMAL_R.toString()))
/* 527 */       return "一次側(R)"; 
/* 528 */     if (alarmType.equals(AlarmType.PRIMARY_ABNORMAL_S.toString()))
/* 529 */       return "一次側(S)"; 
/* 530 */     if (alarmType.equals(AlarmType.PRIMARY_ABNORMAL_T.toString()))
/* 531 */       return "一次側(T)"; 
/* 532 */     if (alarmType.equals(AlarmType.SECONDARY_ABNORMAL_R.toString()))
/* 533 */       return "二次側(R)"; 
/* 534 */     if (alarmType.equals(AlarmType.SECONDARY_ABNORMAL_S.toString()))
/* 535 */       return "二次側(S)"; 
/* 536 */     if (alarmType.equals(AlarmType.SECONDARY_ABNORMAL_T.toString()))
/* 537 */       return "二次側(T)"; 
/* 538 */     if (alarmType.equals(AlarmType.BRANCH_CIRCUIT_ABNORMAL_1.toString()))
/* 539 */       return "分迴路(1)"; 
/* 540 */     if (alarmType.equals(AlarmType.BRANCH_CIRCUIT_ABNORMAL_2.toString()))
/* 541 */       return "分迴路(2)"; 
/* 542 */     if (alarmType.equals(AlarmType.BRANCH_CIRCUIT_ABNORMAL_3.toString()))
/* 543 */       return "分迴路(3)"; 
/* 544 */     if (alarmType.equals(AlarmType.BRANCH_CIRCUIT_ABNORMAL_4.toString()))
/* 545 */       return "分迴路(4)"; 
/* 546 */     if (alarmType.equals(AlarmType.BRANCH_CIRCUIT_ABNORMAL_5.toString())) {
/* 547 */       return "分迴路(5)";
/*     */     }
/* 549 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   private String getStatusNameFromAalrmType(String message) {
/* 554 */     if (message == null) {
/* 555 */       return null;
/*     */     }
/* 557 */     if (message.equals("異常"))
/* 558 */       return "欠相"; 
/* 559 */     if (message.equals("訊號未接收!"))
/* 560 */       return "斷電"; 
/* 561 */     if (message.equals("恢復正常")) {
/* 562 */       return "恢復";
/*     */     }
/* 564 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PreviewGridData inquirePreviewGridData(Map<String, Object> inputParameter) {
/* 570 */     List<Map<String, Object>> reportMap = inquire(inputParameter);
/*     */     
/* 572 */     Map[] arrayOfMap = (Map[])((Map)reportMap.get(0)).get("pdLoopDeviceAlarm");
/* 573 */     if (reportMap.size() == 0) {
/* 574 */       return new PreviewGridData();
/*     */     }
/*     */ 
/*     */     
/* 578 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/* 579 */     List<PreviewGridRow> rows = new ArrayList<>();
/* 580 */     for (Map<String, Object> record : arrayOfMap) {
/*     */       
/* 582 */       record.put("startTime", dateFormat.format(record.get("startTime")));
/*     */       
/* 584 */       PreviewGridRow row = new PreviewGridRow();
/* 585 */       row.setId(UUID.randomUUID().toString());
/* 586 */       row.setColumnValMap(record);
/* 587 */       rows.add(row);
/*     */     } 
/*     */     
/* 590 */     PreviewGridData result = new PreviewGridData();
/* 591 */     result.setHeaders(genPreviewGridHeaders(reportMap));
/* 592 */     result.setRows(rows);
/* 593 */     return result;
/*     */   }
/*     */   
/*     */   private List<PreviewGridHeader> genPreviewGridHeaders(List<Map<String, Object>> reportMap) {
/* 597 */     List<PreviewGridHeader> headers = new ArrayList<>();
/*     */     
/* 599 */     PreviewGridHeader h1 = new PreviewGridHeader();
/* 600 */     h1.setColumnId("pdDeviceName");
/* 601 */     h1.setHeader(this.messageSourceExt.getMessage("PD點名稱"));
/* 602 */     h1.setWidth(Integer.valueOf(100));
/* 603 */     headers.add(h1);
/*     */     
/* 605 */     PreviewGridHeader h2 = new PreviewGridHeader();
/* 606 */     h2.setColumnId("location");
/* 607 */     h2.setHeader(this.messageSourceExt.getMessage("機房名稱"));
/* 608 */     h2.setWidth(Integer.valueOf(300));
/* 609 */     headers.add(h2);
/*     */     
/* 611 */     PreviewGridHeader h3 = new PreviewGridHeader();
/* 612 */     h3.setColumnId("roadLine");
/* 613 */     h3.setHeader(this.messageSourceExt.getMessage("路線"));
/* 614 */     h3.setWidth(Integer.valueOf(100));
/* 615 */     headers.add(h3);
/*     */     
/* 617 */     PreviewGridHeader h4 = new PreviewGridHeader();
/* 618 */     h4.setColumnId("direction");
/* 619 */     h4.setHeader(this.messageSourceExt.getMessage("方向"));
/* 620 */     h4.setWidth(Integer.valueOf(100));
/* 621 */     headers.add(h4);
/*     */     
/* 623 */     PreviewGridHeader h5 = new PreviewGridHeader();
/* 624 */     h5.setColumnId("milepost");
/* 625 */     h5.setHeader(this.messageSourceExt.getMessage("里程"));
/* 626 */     h5.setWidth(Integer.valueOf(100));
/* 627 */     headers.add(h5);
/*     */     
/* 629 */     PreviewGridHeader h6 = new PreviewGridHeader();
/* 630 */     h6.setColumnId("timeStamp");
/* 631 */     h6.setHeader(this.messageSourceExt.getMessage("時間"));
/* 632 */     h6.setWidth(Integer.valueOf(300));
/* 633 */     headers.add(h6);
/*     */     
/* 635 */     PreviewGridHeader h7 = new PreviewGridHeader();
/* 636 */     h7.setColumnId("item");
/* 637 */     h7.setHeader(this.messageSourceExt.getMessage("項目"));
/* 638 */     h7.setWidth(Integer.valueOf(100));
/* 639 */     headers.add(h7);
/*     */     
/* 641 */     PreviewGridHeader h8 = new PreviewGridHeader();
/* 642 */     h8.setColumnId("status");
/* 643 */     h8.setHeader(this.messageSourceExt.getMessage("狀態"));
/* 644 */     h8.setWidth(Integer.valueOf(100));
/* 645 */     headers.add(h8);
/*     */     
/* 647 */     PreviewGridHeader h9 = new PreviewGridHeader();
/* 648 */     h9.setColumnId("loopNo");
/* 649 */     h9.setHeader(this.messageSourceExt.getMessage("迴路數"));
/* 650 */     h9.setWidth(Integer.valueOf(100));
/* 651 */     headers.add(h9);
/*     */     
/* 653 */     PreviewGridHeader h10 = new PreviewGridHeader();
/* 654 */     h10.setColumnId("loopDeviceName");
/* 655 */     h10.setHeader(this.messageSourceExt.getMessage("設備名稱"));
/* 656 */     h10.setWidth(Integer.valueOf(100));
/* 657 */     headers.add(h10);
/*     */     
/* 659 */     PreviewGridHeader h11 = new PreviewGridHeader();
/* 660 */     h11.setColumnId("deviceType");
/* 661 */     h11.setHeader(this.messageSourceExt.getMessage("設備種類"));
/* 662 */     h11.setWidth(Integer.valueOf(100));
/* 663 */     headers.add(h11);
/*     */     
/* 665 */     PreviewGridHeader h12 = new PreviewGridHeader();
/* 666 */     h12.setColumnId("loopDeviceRoadLine");
/* 667 */     h12.setHeader(this.messageSourceExt.getMessage("設備路線"));
/* 668 */     h12.setWidth(Integer.valueOf(100));
/* 669 */     headers.add(h12);
/*     */     
/* 671 */     PreviewGridHeader h13 = new PreviewGridHeader();
/* 672 */     h13.setColumnId("loopDeviceDirection");
/* 673 */     h13.setHeader(this.messageSourceExt.getMessage("設備方向"));
/* 674 */     h13.setWidth(Integer.valueOf(100));
/* 675 */     headers.add(h13);
/*     */     
/* 677 */     PreviewGridHeader h14 = new PreviewGridHeader();
/* 678 */     h14.setColumnId("loopDeviceMilepost");
/* 679 */     h14.setHeader(this.messageSourceExt.getMessage("設備里程"));
/* 680 */     h14.setWidth(Integer.valueOf(100));
/* 681 */     headers.add(h14);
/*     */     
/* 683 */     PreviewGridHeader h15 = new PreviewGridHeader();
/* 684 */     h15.setColumnId("diameter");
/* 685 */     h15.setHeader(this.messageSourceExt.getMessage("線徑"));
/* 686 */     h15.setWidth(Integer.valueOf(100));
/* 687 */     headers.add(h15);
/*     */     
/* 689 */     return headers;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\rpt\AoPdLoopDeviceAlarmReportInquiryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */