/*     */ package com.hwacom.ngtms.ao.fm.service.rpt;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.ao.fm.model.RoomDeviceStatusRecord;
/*     */ import com.hwacom.ngtms.ao.fm.repository.RoomDeviceStatusRecordRepository;
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
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.stream.Collectors;
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
/*     */ public class AoRoomDeviceStatusRecordReportInquiryImpl
/*     */   implements ReportInquiry
/*     */ {
/*  43 */   private static Logger logger = LoggerFactory.getLogger(AoRoomDeviceStatusRecordReportInquiryImpl.class);
/*     */   
/*     */   private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
/*     */   
/*     */   @Autowired
/*     */   private DeviceLocationMappingConfigRepository deviceLocationMappingConfigRepository;
/*     */   
/*     */   @Autowired
/*     */   private RoomDeviceStatusRecordRepository roomDeviceStatusRecordRepository;
/*     */   @Autowired
/*     */   private DeviceTcConfigRepository deviceTcConfigRepository;
/*     */   @Autowired
/*     */   private MessageSourceExt messageSourceExt;
/*     */   
/*     */   public List<Map<String, Object>> inquire(Map<String, Object> inputParameter) {
/*  58 */     if (!inputParameter.containsKey("startDateTime")) {
/*  59 */       logger.warn("Get inputParameter: startDateTime fail! InputParameter: '{}'", inputParameter);
/*  60 */       return Collections.emptyList();
/*     */     } 
/*  62 */     if (!inputParameter.containsKey("endDateTime")) {
/*  63 */       logger.warn("Get inputParameter: endDateTime fail! InputParameter: '{}'", inputParameter);
/*  64 */       return Collections.emptyList();
/*     */     } 
/*  66 */     if (!inputParameter.containsKey("location")) {
/*  67 */       logger.warn("Get inputParameter: location fail! InputParameter: '{}'", inputParameter);
/*  68 */       return Collections.emptyList();
/*     */     } 
/*  70 */     if (!inputParameter.containsKey("deviceType")) {
/*  71 */       logger.warn("Get inputParameter: deviceType fail! InputParameter: '{}'", inputParameter);
/*  72 */       return Collections.emptyList();
/*     */     } 
/*  74 */     if (!inputParameter.containsKey("status")) {
/*  75 */       logger.warn("Get inputParameter: status fail! InputParameter: '{}'", inputParameter);
/*  76 */       return Collections.emptyList();
/*     */     } 
/*     */ 
/*     */     
/*  80 */     String startDateTimeStr = (String)inputParameter.get("startDateTime");
/*  81 */     String endDateTimeStr = (String)inputParameter.get("endDateTime");
/*  82 */     String locationStr = (String)inputParameter.get("location");
/*  83 */     String deviceTypeStr = (String)inputParameter.get("deviceType");
/*  84 */     String statusStr = (String)inputParameter.get("status");
/*     */ 
/*     */     
/*  87 */     String userName = inputParameter.containsKey("userName") ? (String)inputParameter.get("userName") : "";
/*  88 */     logger.debug("startDateTimeStr:'{}'", startDateTimeStr);
/*  89 */     logger.debug("endDateTimeStr:'{}'", endDateTimeStr);
/*  90 */     logger.debug("locationStr:'{}'", locationStr);
/*  91 */     logger.debug("deviceTypeStr:'{}'", deviceTypeStr);
/*  92 */     logger.debug("statusStr:'{}'", statusStr);
/*  93 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*  94 */     Date startDateTime = null;
/*  95 */     Date endDateTime = null;
/*     */     try {
/*  97 */       startDateTime = dateFormat.parse(startDateTimeStr);
/*  98 */       endDateTime = dateFormat.parse(endDateTimeStr);
/*  99 */     } catch (ParseException e) {
/* 100 */       logger.warn("Parse date string failed! startDateTime:'{}', endDateTime:'{}'", new Object[] { startDateTimeStr, endDateTimeStr, e });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 105 */       return Collections.emptyList();
/*     */     } 
/*     */     
/* 108 */     String chartKey = (String)inputParameter.get("CHART_KEY");
/*     */     
/* 110 */     List<Map<String, Object>> resultMapArray = new ArrayList<>();
/*     */ 
/*     */     
/* 113 */     Map<String, Object> tableDataMap = new HashMap<>();
/*     */ 
/*     */     
/* 116 */     Sort sort = Sort.by(new String[] { "dataTime", "deviceName" });
/* 117 */     Sort deviceSort = Sort.by(new String[] { "deviceType" });
/* 118 */     List<RoomDeviceStatusRecord> rptRoomDeviceStatusRecords = new ArrayList<>();
/*     */     
/* 120 */     if (locationStr.equals("全部")) {
/* 121 */       if (deviceTypeStr.equals("全部"))
/*     */       {
/* 123 */         rptRoomDeviceStatusRecords = this.roomDeviceStatusRecordRepository.findByDataTimeBetween(startDateTime, endDateTime, sort);
/*     */       }
/*     */       else
/*     */       {
/* 127 */         List<DeviceLocationMappingConfig> mappingConfigs = this.deviceLocationMappingConfigRepository.findAll();
/* 128 */         List<String> allRoomDeviceNames = new ArrayList<>();
/* 129 */         List<String> rptDeviceNames = new ArrayList<>();
/* 130 */         for (DeviceLocationMappingConfig mappingConfig : mappingConfigs) {
/* 131 */           allRoomDeviceNames.add(mappingConfig.getDeviceName());
/*     */         }
/*     */         
/* 134 */         List<DeviceTcConfig> roomDevices = this.deviceTcConfigRepository.findByDeviceTypeAndDeviceNamesWithSort(deviceTypeStr, allRoomDeviceNames, deviceSort);
/*     */         
/* 136 */         for (DeviceTcConfig config : roomDevices) {
/* 137 */           rptDeviceNames.add(config.getDeviceName());
/*     */         }
/*     */         
/* 140 */         rptRoomDeviceStatusRecords = this.roomDeviceStatusRecordRepository.findByDeviceNameInAndDataTimeBetween(rptDeviceNames, startDateTime, endDateTime, sort);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 145 */       List<DeviceLocationMappingConfig> mappingConfigs = this.deviceLocationMappingConfigRepository.findByLocationName(locationStr);
/* 146 */       List<String> roomDevices = new ArrayList<>();
/* 147 */       for (DeviceLocationMappingConfig mappingConfig : mappingConfigs) {
/* 148 */         roomDevices.add(mappingConfig.getDeviceName());
/*     */       }
/* 150 */       if (deviceTypeStr.equals("全部")) {
/*     */         
/* 152 */         rptRoomDeviceStatusRecords = this.roomDeviceStatusRecordRepository.findByDeviceNameInAndDataTimeBetween(roomDevices, startDateTime, endDateTime, sort);
/*     */       }
/*     */       else {
/*     */         
/* 156 */         List<DeviceTcConfig> rptDevices = this.deviceTcConfigRepository.findByDeviceTypeAndDeviceNamesWithSort(deviceTypeStr, roomDevices, deviceSort);
/*     */ 
/*     */         
/* 159 */         List<String> rptDeviceNames = (List<String>)rptDevices.stream().map(e -> e.getDeviceName()).collect(Collectors.toList());
/*     */         
/* 161 */         rptRoomDeviceStatusRecords = this.roomDeviceStatusRecordRepository.findByDeviceNameInAndDataTimeBetween(rptDeviceNames, startDateTime, endDateTime, sort);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 167 */     if (statusStr.equals("連線")) {
/* 168 */       List<RoomDeviceStatusRecord> onlineStatusRecords = new ArrayList<>();
/* 169 */       for (RoomDeviceStatusRecord record : rptRoomDeviceStatusRecords) {
/* 170 */         if (record.getRtuStatus() != null && record.getRtuStatus().intValue() == 0) {
/* 171 */           onlineStatusRecords.add(record);
/*     */         }
/*     */       } 
/* 174 */       rptRoomDeviceStatusRecords.clear();
/* 175 */       rptRoomDeviceStatusRecords.addAll(onlineStatusRecords);
/* 176 */     } else if (statusStr.equals("斷線")) {
/* 177 */       List<RoomDeviceStatusRecord> offlineStatusRecords = new ArrayList<>();
/* 178 */       for (RoomDeviceStatusRecord record : rptRoomDeviceStatusRecords) {
/* 179 */         if (record.getRtuStatus() != null && record.getRtuStatus().intValue() == 1) {
/* 180 */           offlineStatusRecords.add(record);
/*     */         }
/*     */       } 
/* 183 */       rptRoomDeviceStatusRecords.clear();
/* 184 */       rptRoomDeviceStatusRecords.addAll(offlineStatusRecords);
/*     */     } 
/*     */     
/* 187 */     logger.debug("rptRoomDeviceStatusRecords.size:'{}'", Integer.valueOf(rptRoomDeviceStatusRecords.size()));
/*     */     
/* 189 */     HashMap[] arrayOfHashMap = new HashMap[rptRoomDeviceStatusRecords.size()];
/* 190 */     if (rptRoomDeviceStatusRecords.size() == 0) {
/* 191 */       arrayOfHashMap = new HashMap[1];
/*     */     }
/* 193 */     IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 194 */     IMap<String, DeviceType> deviceTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 195 */     int row = 0;
/* 196 */     for (RoomDeviceStatusRecord record : rptRoomDeviceStatusRecords) {
/* 197 */       Map<String, Object> roomDeviceStatus = new HashMap<>();
/* 198 */       String deviceName = record.getDeviceName();
/* 199 */       DeviceTcConfig deviceConfig = (DeviceTcConfig)deviceMap.get(deviceName);
/* 200 */       DeviceType deviceType = null;
/*     */       
/* 202 */       DeviceLocationMappingConfig mappingConfig = this.deviceLocationMappingConfigRepository.findByDeviceName(deviceName);
/*     */       
/* 204 */       roomDeviceStatus.put("dataTime", dateFormat.format(record.getDataTime()));
/* 205 */       if (mappingConfig != null) {
/* 206 */         roomDeviceStatus.put("location", mappingConfig.getLocationName());
/*     */       }
/* 208 */       if (deviceConfig != null) {
/* 209 */         deviceType = (DeviceType)deviceTypeMap.get(deviceConfig.getDeviceType());
/* 210 */         roomDeviceStatus.put("deviceName", deviceConfig.getDisplayName());
/*     */       } else {
/* 212 */         roomDeviceStatus.put("deviceName", deviceName);
/*     */       } 
/* 214 */       if (deviceType != null) {
/* 215 */         roomDeviceStatus.put("deviceType", deviceType.getDescription());
/*     */       }
/* 217 */       roomDeviceStatus.put("status", 
/*     */           
/* 219 */           getStatusNameFromStatus(record.getRtuStatus()) + getDensity(record.getDensity()));
/* 220 */       arrayOfHashMap[row++] = (HashMap)roomDeviceStatus;
/*     */     } 
/*     */     
/* 223 */     tableDataMap.put("roomDeviceStatus", arrayOfHashMap);
/* 224 */     tableDataMap.put("CHART_KEY", chartKey);
/* 225 */     tableDataMap.put("location", locationStr);
/* 226 */     tableDataMap.put("deviceType", deviceTypeStr);
/* 227 */     tableDataMap.put("status", statusStr);
/* 228 */     tableDataMap.put("userName", userName);
/*     */     
/* 230 */     resultMapArray.add(tableDataMap);
/* 231 */     return resultMapArray;
/*     */   }
/*     */   
/*     */   private String getStatusNameFromStatus(Integer status) {
/* 235 */     switch (status.intValue()) {
/*     */       case 0:
/* 237 */         return "連線";
/*     */       case 1:
/* 239 */         return "斷線";
/*     */     } 
/* 241 */     return status.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private String getDensity(String density) {
/* 246 */     String formatValue = "";
/* 247 */     if (density.equals("-1")) {
/* 248 */       return ", 訊號值:" + density;
/*     */     }
/* 250 */     return formatValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PreviewGridData inquirePreviewGridData(Map<String, Object> inputParameter) {
/* 256 */     List<Map<String, Object>> reportMap = inquire(inputParameter);
/*     */     
/* 258 */     Map[] arrayOfMap = (Map[])((Map)reportMap.get(0)).get("roomDeviceStatus");
/* 259 */     if (reportMap.size() == 0) {
/* 260 */       return new PreviewGridData();
/*     */     }
/*     */ 
/*     */     
/* 264 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/* 265 */     List<PreviewGridRow> rows = new ArrayList<>();
/* 266 */     for (Map<String, Object> record : arrayOfMap) {
/*     */       
/* 268 */       record.put("startTime", dateFormat.format(record.get("startTime")));
/*     */       
/* 270 */       PreviewGridRow row = new PreviewGridRow();
/* 271 */       row.setId(UUID.randomUUID().toString());
/* 272 */       row.setColumnValMap(record);
/* 273 */       rows.add(row);
/*     */     } 
/*     */     
/* 276 */     PreviewGridData result = new PreviewGridData();
/* 277 */     result.setHeaders(genPreviewGridHeaders(reportMap));
/* 278 */     result.setRows(rows);
/* 279 */     return result;
/*     */   }
/*     */   
/*     */   private List<PreviewGridHeader> genPreviewGridHeaders(List<Map<String, Object>> reportMap) {
/* 283 */     List<PreviewGridHeader> headers = new ArrayList<>();
/*     */     
/* 285 */     PreviewGridHeader h1 = new PreviewGridHeader();
/* 286 */     h1.setColumnId("dataTime");
/* 287 */     h1.setHeader(this.messageSourceExt.getMessage("時間"));
/* 288 */     h1.setWidth(Integer.valueOf(300));
/* 289 */     headers.add(h1);
/*     */     
/* 291 */     PreviewGridHeader h2 = new PreviewGridHeader();
/* 292 */     h2.setColumnId("location");
/* 293 */     h2.setHeader(this.messageSourceExt.getMessage("機房名稱"));
/* 294 */     h2.setWidth(Integer.valueOf(300));
/* 295 */     headers.add(h2);
/*     */     
/* 297 */     PreviewGridHeader h3 = new PreviewGridHeader();
/* 298 */     h3.setColumnId("deviceType");
/* 299 */     h3.setHeader(this.messageSourceExt.getMessage("設備種類"));
/* 300 */     h3.setWidth(Integer.valueOf(100));
/* 301 */     headers.add(h3);
/*     */     
/* 303 */     PreviewGridHeader h4 = new PreviewGridHeader();
/* 304 */     h4.setColumnId("deviceName");
/* 305 */     h4.setHeader(this.messageSourceExt.getMessage("設備名稱"));
/* 306 */     h4.setWidth(Integer.valueOf(300));
/* 307 */     headers.add(h4);
/*     */     
/* 309 */     PreviewGridHeader h5 = new PreviewGridHeader();
/* 310 */     h5.setColumnId("status");
/* 311 */     h5.setHeader(this.messageSourceExt.getMessage("狀態"));
/* 312 */     h5.setWidth(Integer.valueOf(100));
/* 313 */     headers.add(h5);
/*     */     
/* 315 */     return headers;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\rpt\AoRoomDeviceStatusRecordReportInquiryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */