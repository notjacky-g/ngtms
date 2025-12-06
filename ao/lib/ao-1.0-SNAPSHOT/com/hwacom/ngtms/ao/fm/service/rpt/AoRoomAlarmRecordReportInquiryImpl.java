/*     */ package com.hwacom.ngtms.ao.fm.service.rpt;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.alarm.fm.model.AlarmLog;
/*     */ import com.hwacom.ngtms.alarm.fm.repository.AlarmLogRepository;
/*     */ import com.hwacom.ngtms.ao.fm.repository.RoomDeviceStatusRecordRepository;
/*     */ import com.hwacom.ngtms.ao.util.TransferHelper;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceLocationMappingConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceType;
/*     */ import com.hwacom.ngtms.c.fm.repository.DeviceLocationMappingConfigRepository;
/*     */ import com.hwacom.ngtms.c.fm.repository.DeviceTcConfigRepository;
/*     */ import com.hwacom.ngtms.common.rpt.ReportInquiry;
/*     */ import com.hwacom.ngtms.common.shared.PreviewGridData;
/*     */ import com.hwacom.ngtms.common.shared.PreviewGridHeader;
/*     */ import com.hwacom.ngtms.common.shared.PreviewGridRow;
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
/*     */ @Service
/*     */ public class AoRoomAlarmRecordReportInquiryImpl
/*     */   implements ReportInquiry
/*     */ {
/*  45 */   private static Logger logger = LoggerFactory.getLogger(AoRoomAlarmRecordReportInquiryImpl.class);
/*     */   
/*     */   private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
/*     */   
/*     */   @Autowired
/*     */   private AlarmLogRepository alarmLogRepository;
/*     */   
/*     */   @Autowired
/*     */   private DeviceLocationMappingConfigRepository deviceLocationMappingConfigRepository;
/*     */   @Autowired
/*     */   private RoomDeviceStatusRecordRepository roomDeviceStatusRecordRepository;
/*     */   @Autowired
/*     */   private DeviceTcConfigRepository deviceTcConfigRepository;
/*     */   @Autowired
/*     */   private MessageSourceExt messageSourceExt;
/*     */   
/*     */   public List<Map<String, Object>> inquire(Map<String, Object> inputParameter) {
/*  62 */     if (!inputParameter.containsKey("startDateTime")) {
/*  63 */       logger.warn("Get inputParameter: startDateTime fail! InputParameter: '{}'", inputParameter);
/*  64 */       return Collections.emptyList();
/*     */     } 
/*  66 */     if (!inputParameter.containsKey("endDateTime")) {
/*  67 */       logger.warn("Get inputParameter: endDateTime fail! InputParameter: '{}'", inputParameter);
/*  68 */       return Collections.emptyList();
/*     */     } 
/*  70 */     if (!inputParameter.containsKey("location")) {
/*  71 */       logger.warn("Get inputParameter: location fail! InputParameter: '{}'", inputParameter);
/*  72 */       return Collections.emptyList();
/*     */     } 
/*  74 */     if (!inputParameter.containsKey("alarmType")) {
/*  75 */       logger.warn("Get inputParameter: deviceType fail! InputParameter: '{}'", inputParameter);
/*  76 */       return Collections.emptyList();
/*     */     } 
/*     */ 
/*     */     
/*  80 */     String startDateTimeStr = (String)inputParameter.get("startDateTime");
/*  81 */     String endDateTimeStr = (String)inputParameter.get("endDateTime");
/*  82 */     String locationStr = (String)inputParameter.get("location");
/*  83 */     String alarmTypeStr = (String)inputParameter.get("alarmType");
/*     */ 
/*     */     
/*  86 */     String userName = inputParameter.containsKey("userName") ? (String)inputParameter.get("userName") : "";
/*  87 */     logger.debug("startDateTimeStr:'{}'", startDateTimeStr);
/*  88 */     logger.debug("endDateTimeStr:'{}'", endDateTimeStr);
/*  89 */     logger.debug("locationStr:'{}'", locationStr);
/*  90 */     logger.debug("deviceTypeStr:'{}'", alarmTypeStr);
/*  91 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*  92 */     Date startDateTime = null;
/*  93 */     Date endDateTime = null;
/*     */     try {
/*  95 */       startDateTime = dateFormat.parse(startDateTimeStr);
/*  96 */       endDateTime = dateFormat.parse(endDateTimeStr);
/*  97 */     } catch (ParseException e) {
/*  98 */       logger.warn("Parse date string failed! startDateTime:'{}', endDateTime:'{}'", new Object[] { startDateTimeStr, endDateTimeStr, e });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 103 */       return Collections.emptyList();
/*     */     } 
/*     */     
/* 106 */     String chartKey = (String)inputParameter.get("CHART_KEY");
/*     */     
/* 108 */     List<Map<String, Object>> resultMapArray = new ArrayList<>();
/*     */ 
/*     */     
/* 111 */     Map<String, Object> tableDataMap = new HashMap<>();
/*     */ 
/*     */     
/* 114 */     Sort sort = Sort.by(new String[] { "timestamp", "deviceName" });
/* 115 */     List<AlarmLog> rptRoomAlarmRecords = new ArrayList<>();
/* 116 */     IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 117 */     IMap<String, DeviceType> deviceTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 118 */     List<String> allDeviceType = new ArrayList<>();
/* 119 */     allDeviceType.add("NCU");
/* 120 */     allDeviceType.add("RTU");
/*     */     
/* 122 */     PredicateBuilder deviceTypePb = (new PredicateBuilder()).getEntryObject().get("category").equal("ROOM");
/* 123 */     for (DeviceType deviceType : deviceTypeMap.values((Predicate)deviceTypePb)) {
/* 124 */       allDeviceType.add(deviceType.getId());
/*     */     }
/* 126 */     String[] allDeviceTypeArr = allDeviceType.<String>toArray(new String[0]);
/*     */     
/* 128 */     if (locationStr.equals("全部")) {
/*     */       
/* 130 */       PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceType").in((Comparable[])allDeviceTypeArr);
/* 131 */       List<String> allRoomDeviceNames = new ArrayList<>();
/* 132 */       for (DeviceTcConfig device : deviceMap.values((Predicate)pb)) {
/* 133 */         allRoomDeviceNames.add(device.getDeviceName());
/*     */       }
/* 135 */       if (alarmTypeStr.equals("ALL")) {
/*     */         
/* 137 */         rptRoomAlarmRecords = this.alarmLogRepository.findByDeviceNamesAndTimestamp(allRoomDeviceNames, startDateTime, endDateTime, sort);
/*     */       } else {
/*     */         
/* 140 */         List<String> alarmTypeList = Arrays.asList(new String[] { alarmTypeStr });
/*     */         
/* 142 */         rptRoomAlarmRecords = this.alarmLogRepository.findByDeviceNamesAndTimestampAndAlarmSubTypeIds(allRoomDeviceNames, startDateTime, endDateTime, alarmTypeList, sort);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 147 */       List<DeviceLocationMappingConfig> mappingConfigs = this.deviceLocationMappingConfigRepository.findByLocationName(locationStr);
/* 148 */       List<String> roomDevices = new ArrayList<>();
/* 149 */       for (DeviceLocationMappingConfig mappingConfig : mappingConfigs) {
/* 150 */         roomDevices.add(mappingConfig.getDeviceName());
/*     */       }
/* 152 */       if (alarmTypeStr.equals("ALL")) {
/*     */         
/* 154 */         rptRoomAlarmRecords = this.alarmLogRepository.findByDeviceNamesAndTimestamp(roomDevices, startDateTime, endDateTime, sort);
/*     */       } else {
/*     */         
/* 157 */         List<String> alarmTypeList = Arrays.asList(new String[] { alarmTypeStr });
/*     */         
/* 159 */         rptRoomAlarmRecords = this.alarmLogRepository.findByDeviceNamesAndTimestampAndAlarmSubTypeIds(roomDevices, startDateTime, endDateTime, alarmTypeList, sort);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 164 */     logger.debug("rptRoomAlarmRecords.size:'{}'", Integer.valueOf(rptRoomAlarmRecords.size()));
/* 165 */     HashMap[] arrayOfHashMap = new HashMap[rptRoomAlarmRecords.size()];
/* 166 */     if (rptRoomAlarmRecords.size() == 0) {
/* 167 */       arrayOfHashMap = new HashMap[1];
/*     */     }
/* 169 */     int row = 0;
/* 170 */     for (AlarmLog alarm : rptRoomAlarmRecords) {
/* 171 */       Map<String, Object> roomAlarmRecords = new HashMap<>();
/* 172 */       String deviceName = alarm.getDeviceName();
/* 173 */       DeviceTcConfig deviceConfig = (DeviceTcConfig)deviceMap.get(deviceName);
/* 174 */       DeviceType deviceType = null;
/*     */       
/* 176 */       DeviceLocationMappingConfig mappingConfig = this.deviceLocationMappingConfigRepository.findByDeviceName(deviceName);
/* 177 */       if (mappingConfig != null) {
/* 178 */         roomAlarmRecords.put("location", mappingConfig.getLocationName());
/*     */       }
/* 180 */       if (deviceConfig != null) {
/* 181 */         deviceType = (DeviceType)deviceTypeMap.get(deviceConfig.getDeviceType());
/* 182 */         roomAlarmRecords.put("deviceName", deviceConfig.getDisplayName());
/*     */       } else {
/* 184 */         roomAlarmRecords.put("deviceName", deviceName);
/*     */       } 
/* 186 */       if (deviceType != null) {
/* 187 */         roomAlarmRecords.put("deviceType", deviceType.getDescription());
/*     */       }
/* 189 */       roomAlarmRecords.put("dataTime", dateFormat.format(alarm.getTimestamp()));
/* 190 */       roomAlarmRecords.put("alarmType", 
/* 191 */           TransferHelper.transferAlarmTypeStr(alarm.getAlarmSubType()));
/* 192 */       roomAlarmRecords.put("alarmMessage", alarm.getMessage());
/* 193 */       arrayOfHashMap[row++] = (HashMap)roomAlarmRecords;
/*     */     } 
/*     */     
/* 196 */     String alarmType = TransferHelper.transferAlarmTypeStr(alarmTypeStr);
/* 197 */     tableDataMap.put("roomAlarmRecord", arrayOfHashMap);
/* 198 */     tableDataMap.put("CHART_KEY", chartKey);
/* 199 */     tableDataMap.put("location", locationStr);
/* 200 */     tableDataMap.put("deviceType", alarmType);
/* 201 */     tableDataMap.put("userName", userName);
/*     */     
/* 203 */     resultMapArray.add(tableDataMap);
/* 204 */     return resultMapArray;
/*     */   }
/*     */ 
/*     */   
/*     */   public PreviewGridData inquirePreviewGridData(Map<String, Object> inputParameter) {
/* 209 */     List<Map<String, Object>> reportMap = inquire(inputParameter);
/* 210 */     Map[] arrayOfMap = (Map[])((Map)reportMap.get(0)).get("roomAlarmRecord");
/* 211 */     if (reportMap.size() == 0) {
/* 212 */       return new PreviewGridData();
/*     */     }
/*     */ 
/*     */     
/* 216 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/* 217 */     List<PreviewGridRow> rows = new ArrayList<>();
/* 218 */     for (Map<String, Object> record : arrayOfMap) {
/*     */       
/* 220 */       record.put("startTime", dateFormat.format(record.get("startTime")));
/*     */       
/* 222 */       PreviewGridRow row = new PreviewGridRow();
/* 223 */       row.setId(UUID.randomUUID().toString());
/* 224 */       row.setColumnValMap(record);
/* 225 */       rows.add(row);
/*     */     } 
/*     */     
/* 228 */     PreviewGridData result = new PreviewGridData();
/* 229 */     result.setHeaders(genPreviewGridHeaders(reportMap));
/* 230 */     result.setRows(rows);
/* 231 */     return result;
/*     */   }
/*     */   
/*     */   private List<PreviewGridHeader> genPreviewGridHeaders(List<Map<String, Object>> reportMap) {
/* 235 */     List<PreviewGridHeader> headers = new ArrayList<>();
/*     */     
/* 237 */     PreviewGridHeader h1 = new PreviewGridHeader();
/* 238 */     h1.setColumnId("location");
/* 239 */     h1.setHeader(this.messageSourceExt.getMessage("機房名稱"));
/* 240 */     h1.setWidth(Integer.valueOf(300));
/* 241 */     headers.add(h1);
/*     */     
/* 243 */     PreviewGridHeader h2 = new PreviewGridHeader();
/* 244 */     h2.setColumnId("deviceType");
/* 245 */     h2.setHeader(this.messageSourceExt.getMessage("種類"));
/* 246 */     h2.setWidth(Integer.valueOf(150));
/* 247 */     headers.add(h2);
/*     */     
/* 249 */     PreviewGridHeader h3 = new PreviewGridHeader();
/* 250 */     h3.setColumnId("deviceName");
/* 251 */     h3.setHeader(this.messageSourceExt.getMessage("名稱"));
/* 252 */     h3.setWidth(Integer.valueOf(300));
/* 253 */     headers.add(h3);
/*     */     
/* 255 */     PreviewGridHeader h4 = new PreviewGridHeader();
/* 256 */     h4.setColumnId("dataTime");
/* 257 */     h4.setHeader(this.messageSourceExt.getMessage("時間"));
/* 258 */     h4.setWidth(Integer.valueOf(300));
/* 259 */     headers.add(h4);
/*     */     
/* 261 */     PreviewGridHeader h5 = new PreviewGridHeader();
/* 262 */     h5.setColumnId("alarmType");
/* 263 */     h5.setHeader(this.messageSourceExt.getMessage("警報種類"));
/* 264 */     h5.setWidth(Integer.valueOf(150));
/* 265 */     headers.add(h5);
/*     */     
/* 267 */     PreviewGridHeader h6 = new PreviewGridHeader();
/* 268 */     h5.setColumnId("alarmMessage");
/* 269 */     h5.setHeader(this.messageSourceExt.getMessage("警報狀態"));
/* 270 */     h5.setWidth(Integer.valueOf(150));
/* 271 */     headers.add(h6);
/*     */     
/* 273 */     return headers;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\rpt\AoRoomAlarmRecordReportInquiryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */