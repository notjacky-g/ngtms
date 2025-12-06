/*     */ package com.hwacom.ngtms.ao.fm.service.rpt;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*     */ import com.hwacom.ngtms.base.oplog.model.OperationLog;
/*     */ import com.hwacom.ngtms.base.oplog.repository.OperationLogRepository;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationItem;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.common.rpt.ReportInquiry;
/*     */ import com.hwacom.ngtms.common.shared.PreviewGridData;
/*     */ import com.hwacom.ngtms.common.shared.PreviewGridHeader;
/*     */ import com.hwacom.ngtms.common.shared.PreviewGridRow;
/*     */ import com.hwacom.ngtms.room.fm.model.RoomCardConfig;
/*     */ import com.hwacom.ngtms.room.fm.repository.RoomCardConfigRepository;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
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
/*     */ public class AoCardPermissionDownloadLogReportInquiryImpl
/*     */   implements ReportInquiry
/*     */ {
/*  42 */   private static Logger logger = LoggerFactory.getLogger(AoCardPermissionDownloadLogReportInquiryImpl.class);
/*     */   
/*     */   private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
/*     */   
/*     */   @Autowired
/*     */   private RoomCardConfigRepository roomCardConfigRepository;
/*     */   
/*     */   @Autowired
/*     */   private OperationLogRepository operationLogRepository;
/*     */   @Autowired
/*     */   private MessageSourceExt messageSourceExt;
/*     */   
/*     */   public List<Map<String, Object>> inquire(Map<String, Object> inputParameter) {
/*  55 */     if (!inputParameter.containsKey("startDateTime")) {
/*  56 */       logger.warn("Get inputParameter: startDateTime fail! InputParameter: '{}'", inputParameter);
/*  57 */       return Collections.emptyList();
/*     */     } 
/*  59 */     if (!inputParameter.containsKey("endDateTime")) {
/*  60 */       logger.warn("Get inputParameter: endDateTime fail! InputParameter: '{}'", inputParameter);
/*  61 */       return Collections.emptyList();
/*     */     } 
/*  63 */     if (!inputParameter.containsKey("location")) {
/*  64 */       logger.warn("Get inputParameter: location fail! InputParameter: '{}'", inputParameter);
/*  65 */       return Collections.emptyList();
/*     */     } 
/*  67 */     if (!inputParameter.containsKey("status")) {
/*  68 */       logger.warn("Get inputParameter: status fail! InputParameter: '{}'", inputParameter);
/*  69 */       return Collections.emptyList();
/*     */     } 
/*  71 */     if (!inputParameter.containsKey("result")) {
/*  72 */       logger.warn("Get inputParameter: result fail! InputParameter: '{}'", inputParameter);
/*  73 */       return Collections.emptyList();
/*     */     } 
/*     */ 
/*     */     
/*  77 */     String startDateTimeStr = (String)inputParameter.get("startDateTime");
/*  78 */     String endDateTimeStr = (String)inputParameter.get("endDateTime");
/*  79 */     String locationStr = (String)inputParameter.get("location");
/*  80 */     String statusStr = (String)inputParameter.get("status");
/*  81 */     String resultStr = (String)inputParameter.get("result");
/*     */ 
/*     */     
/*  84 */     String userName = inputParameter.containsKey("userName") ? (String)inputParameter.get("userName") : "";
/*  85 */     logger.debug("startDateTimeStr:'{}'", startDateTimeStr);
/*  86 */     logger.debug("endDateTimeStr:'{}'", endDateTimeStr);
/*  87 */     logger.debug("locationStr:'{}'", locationStr);
/*  88 */     logger.debug("statusStr:'{}'", statusStr);
/*  89 */     logger.debug("resultStr:'{}'", resultStr);
/*  90 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*  91 */     Date startDateTime = null;
/*  92 */     Date endDateTime = null;
/*     */     try {
/*  94 */       startDateTime = dateFormat.parse(startDateTimeStr);
/*  95 */       endDateTime = dateFormat.parse(endDateTimeStr);
/*  96 */     } catch (ParseException e) {
/*  97 */       logger.warn("Parse date string failed! startDateTime:'{}', endDateTime:'{}'", new Object[] { startDateTimeStr, endDateTimeStr, e });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 102 */       return Collections.emptyList();
/*     */     } 
/*     */     
/* 105 */     String chartKey = (String)inputParameter.get("CHART_KEY");
/*     */     
/* 107 */     List<Map<String, Object>> resultMapArray = new ArrayList<>();
/*     */ 
/*     */     
/* 110 */     Map<String, Object> tableDataMap = new HashMap<>();
/*     */ 
/*     */     
/* 113 */     Sort sort = Sort.by(new String[] { "operationTime", "deviceName" });
/* 114 */     List<OperationLog> rptOperationLogs = new ArrayList<>();
/* 115 */     String subSysName = "ROOM";
/*     */ 
/*     */     
/* 118 */     if (locationStr.equals("全部")) {
/*     */       
/* 120 */       rptOperationLogs = this.operationLogRepository.findByOperationItemAndSubSysNameAndOperationTimeBetween(OperationItem.SET
/* 121 */           .name(), subSysName, startDateTime, endDateTime, sort);
/*     */     }
/*     */     else {
/*     */       
/* 125 */       rptOperationLogs = this.operationLogRepository.findByOperationItemAndSubSysNameAndDeviceNameAndOperationTimeBetween(OperationItem.SET
/* 126 */           .name(), subSysName, locationStr, startDateTime, endDateTime, sort);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     if (statusStr.equals("全部")) {
/* 134 */       List<OperationLog> operationLogsTemp = new ArrayList<>();
/* 135 */       for (OperationLog log : rptOperationLogs) {
/* 136 */         String remark = log.getRemark();
/* 137 */         if (remark != null && (remark.equals("ToCardReader") || remark.equals("NotToCardReader"))) {
/* 138 */           operationLogsTemp.add(log);
/*     */         }
/*     */       } 
/* 141 */       rptOperationLogs.clear();
/* 142 */       rptOperationLogs.addAll(operationLogsTemp);
/* 143 */     } else if (statusStr.equals("權限新增")) {
/* 144 */       List<OperationLog> operationLogsTemp = new ArrayList<>();
/* 145 */       for (OperationLog log : rptOperationLogs) {
/* 146 */         if (log.getRemark().equals("ToCardReader")) {
/* 147 */           operationLogsTemp.add(log);
/*     */         }
/*     */       } 
/* 150 */       rptOperationLogs.clear();
/* 151 */       rptOperationLogs.addAll(operationLogsTemp);
/* 152 */     } else if (statusStr.equals("權限刪除")) {
/* 153 */       List<OperationLog> operationLogsTemp = new ArrayList<>();
/* 154 */       for (OperationLog log : rptOperationLogs) {
/* 155 */         if (log.getRemark().equals("NotToCardReader")) {
/* 156 */           operationLogsTemp.add(log);
/*     */         }
/*     */       } 
/* 159 */       rptOperationLogs.clear();
/* 160 */       rptOperationLogs.addAll(operationLogsTemp);
/*     */     } 
/* 162 */     if (resultStr.equals("成功")) {
/* 163 */       List<OperationLog> operationLogsTemp = new ArrayList<>();
/* 164 */       for (OperationLog log : rptOperationLogs) {
/* 165 */         if (log.getOperationResult() == OperationResult.SUCCESS) {
/* 166 */           operationLogsTemp.add(log);
/*     */         }
/*     */       } 
/* 169 */       rptOperationLogs.clear();
/* 170 */       rptOperationLogs.addAll(operationLogsTemp);
/* 171 */     } else if (resultStr.equals("失敗")) {
/* 172 */       List<OperationLog> operationLogsTemp = new ArrayList<>();
/* 173 */       for (OperationLog log : rptOperationLogs) {
/* 174 */         if (log.getOperationResult() == OperationResult.FAILURE) {
/* 175 */           operationLogsTemp.add(log);
/*     */         }
/*     */       } 
/* 178 */       rptOperationLogs.clear();
/* 179 */       rptOperationLogs.addAll(operationLogsTemp);
/*     */     } 
/* 181 */     logger.debug("rptOperationLogs.size:'{}'", Integer.valueOf(rptOperationLogs.size()));
/* 182 */     HashMap[] arrayOfHashMap = new HashMap[rptOperationLogs.size()];
/* 183 */     if (rptOperationLogs.size() == 0) {
/* 184 */       arrayOfHashMap = new HashMap[1];
/*     */     }
/* 186 */     IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 187 */     int row = 0;
/* 188 */     String locationName = locationStr;
/* 189 */     DeviceTcConfig locDeviceConfig = (DeviceTcConfig)deviceMap.get(locationStr);
/* 190 */     if (locDeviceConfig != null) {
/* 191 */       locationName = locDeviceConfig.getDisplayName();
/*     */     }
/* 193 */     for (OperationLog log : rptOperationLogs) {
/* 194 */       Map<String, Object> carPermissionLog = new HashMap<>();
/* 195 */       DeviceTcConfig locationConfig = (DeviceTcConfig)deviceMap.get(log.getDeviceName());
/* 196 */       String location = log.getDeviceName();
/* 197 */       String cardNumber = getCardNumFromDescription(log.getDescription());
/* 198 */       RoomCardConfig roomCardConfig = this.roomCardConfigRepository.findByAba(cardNumber);
/* 199 */       if (locationConfig != null) {
/* 200 */         location = locationConfig.getDisplayName();
/*     */       }
/* 202 */       carPermissionLog.put("location", location);
/* 203 */       carPermissionLog.put("cardNumber", cardNumber);
/* 204 */       if (roomCardConfig != null) {
/* 205 */         carPermissionLog.put("name", roomCardConfig.getName());
/*     */       }
/* 207 */       carPermissionLog.put("dataTime", dateFormat.format(log.getOperationTime()));
/* 208 */       carPermissionLog.put("status", getStatusNameFromRemark(log.getRemark()));
/* 209 */       carPermissionLog.put("result", getResultNameFromEnum(log.getOperationResult()));
/* 210 */       arrayOfHashMap[row++] = (HashMap)carPermissionLog;
/*     */     } 
/* 212 */     tableDataMap.put("carPermissionLog", arrayOfHashMap);
/* 213 */     tableDataMap.put("CHART_KEY", chartKey);
/* 214 */     tableDataMap.put("location", locationName);
/* 215 */     tableDataMap.put("status", statusStr);
/* 216 */     tableDataMap.put("result", resultStr);
/* 217 */     tableDataMap.put("userName", userName);
/*     */     
/* 219 */     resultMapArray.add(tableDataMap);
/* 220 */     return resultMapArray;
/*     */   }
/*     */   
/*     */   private String getCardNumFromDescription(String description) {
/* 224 */     String cardNum = "";
/* 225 */     String[] descriptionArr = description.split(":");
/* 226 */     if (descriptionArr.length > 1) {
/* 227 */       cardNum = descriptionArr[1];
/*     */     }
/* 229 */     return cardNum;
/*     */   }
/*     */   
/*     */   private String getStatusNameFromRemark(String remark) {
/* 233 */     if (remark == null) {
/* 234 */       return "";
/*     */     }
/* 236 */     switch (remark) {
/*     */       case "ToCardReader":
/* 238 */         return "權限新增";
/*     */       case "NotToCardReader":
/* 240 */         return "權限刪除";
/*     */     } 
/* 242 */     return remark;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getResultNameFromEnum(OperationResult opResult) {
/* 247 */     switch (opResult) {
/*     */       case SUCCESS:
/* 249 */         return "成功";
/*     */       case FAILURE:
/* 251 */         return "失敗";
/*     */       case CANCEL:
/* 253 */         return "取消";
/*     */     } 
/* 255 */     return opResult.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PreviewGridData inquirePreviewGridData(Map<String, Object> inputParameter) {
/* 261 */     List<Map<String, Object>> reportMap = inquire(inputParameter);
/*     */     
/* 263 */     Map[] arrayOfMap = (Map[])((Map)reportMap.get(0)).get("carPermissionLog");
/* 264 */     if (reportMap.size() == 0) {
/* 265 */       return new PreviewGridData();
/*     */     }
/*     */ 
/*     */     
/* 269 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/* 270 */     List<PreviewGridRow> rows = new ArrayList<>();
/* 271 */     for (Map<String, Object> record : arrayOfMap) {
/*     */       
/* 273 */       record.put("startTime", dateFormat.format(record.get("startTime")));
/*     */       
/* 275 */       PreviewGridRow row = new PreviewGridRow();
/* 276 */       row.setId(UUID.randomUUID().toString());
/* 277 */       row.setColumnValMap(record);
/* 278 */       rows.add(row);
/*     */     } 
/*     */     
/* 281 */     PreviewGridData result = new PreviewGridData();
/* 282 */     result.setHeaders(genPreviewGridHeaders(reportMap));
/* 283 */     result.setRows(rows);
/* 284 */     return result;
/*     */   }
/*     */   
/*     */   private List<PreviewGridHeader> genPreviewGridHeaders(List<Map<String, Object>> reportMap) {
/* 288 */     List<PreviewGridHeader> headers = new ArrayList<>();
/*     */     
/* 290 */     PreviewGridHeader h1 = new PreviewGridHeader();
/* 291 */     h1.setColumnId("location");
/* 292 */     h1.setHeader(this.messageSourceExt.getMessage("機房名稱"));
/* 293 */     h1.setWidth(Integer.valueOf(300));
/* 294 */     headers.add(h1);
/*     */     
/* 296 */     PreviewGridHeader h2 = new PreviewGridHeader();
/* 297 */     h2.setColumnId("cardNumber");
/* 298 */     h2.setHeader(this.messageSourceExt.getMessage("磁卡編號前10碼"));
/* 299 */     h2.setWidth(Integer.valueOf(300));
/* 300 */     headers.add(h2);
/*     */     
/* 302 */     PreviewGridHeader h3 = new PreviewGridHeader();
/* 303 */     h3.setColumnId("name");
/* 304 */     h3.setHeader(this.messageSourceExt.getMessage("姓名"));
/* 305 */     h3.setWidth(Integer.valueOf(100));
/* 306 */     headers.add(h3);
/*     */     
/* 308 */     PreviewGridHeader h4 = new PreviewGridHeader();
/* 309 */     h4.setColumnId("dataTime");
/* 310 */     h4.setHeader(this.messageSourceExt.getMessage("時間"));
/* 311 */     h4.setWidth(Integer.valueOf(300));
/* 312 */     headers.add(h4);
/*     */     
/* 314 */     PreviewGridHeader h5 = new PreviewGridHeader();
/* 315 */     h5.setColumnId("status");
/* 316 */     h5.setHeader(this.messageSourceExt.getMessage("種類"));
/* 317 */     h5.setWidth(Integer.valueOf(100));
/* 318 */     headers.add(h5);
/*     */     
/* 320 */     PreviewGridHeader h6 = new PreviewGridHeader();
/* 321 */     h6.setColumnId("result");
/* 322 */     h6.setHeader(this.messageSourceExt.getMessage("結果"));
/* 323 */     h6.setWidth(Integer.valueOf(100));
/* 324 */     headers.add(h6);
/*     */     
/* 326 */     return headers;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\rpt\AoCardPermissionDownloadLogReportInquiryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */