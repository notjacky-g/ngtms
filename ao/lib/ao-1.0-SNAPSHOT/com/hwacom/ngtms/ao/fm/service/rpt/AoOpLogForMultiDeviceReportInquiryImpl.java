/*     */ package com.hwacom.ngtms.ao.fm.service.rpt;
/*     */ 
/*     */ import com.hwacom.ngtms.ao.util.AoRptValueFormatter;
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*     */ import com.hwacom.ngtms.base.oplog.model.OperationLog;
/*     */ import com.hwacom.ngtms.base.oplog.repository.OperationLogRepository;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationItem;
/*     */ import com.hwacom.ngtms.c.fm.repository.DeviceTcConfigRepository;
/*     */ import com.hwacom.ngtms.c.shared.SubSystem;
/*     */ import com.hwacom.ngtms.common.fm.model.User;
/*     */ import com.hwacom.ngtms.common.fm.repository.UserRepository;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class AoOpLogForMultiDeviceReportInquiryImpl
/*     */   implements ReportInquiry
/*     */ {
/*  46 */   private static Logger logger = LoggerFactory.getLogger(AoOpLogForMultiDeviceReportInquiryImpl.class);
/*     */   
/*     */   private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
/*     */   
/*     */   @Autowired
/*     */   private OperationLogRepository operationLogRepository;
/*     */   @Autowired
/*     */   private DeviceTcConfigRepository deviceTcConfigRepository;
/*     */   @Autowired
/*     */   private UserRepository userRepository;
/*     */   @Autowired
/*     */   private MessageSourceExt messageSourceExt;
/*     */   @Autowired
/*     */   private AoRptValueFormatter formatter;
/*     */   
/*     */   public List<Map<String, Object>> inquire(Map<String, Object> inputParameter) {
/*  62 */     if (!inputParameter.containsKey("deviceType")) {
/*  63 */       logger.warn("Get inputParameter: deviceType fail! InputParameter: '{}'", inputParameter);
/*  64 */       return Collections.emptyList();
/*     */     } 
/*  66 */     if (!inputParameter.containsKey("devices")) {
/*  67 */       logger.warn("Get inputParameter: devices fail! InputParameter: '{}'", inputParameter);
/*  68 */       return Collections.emptyList();
/*     */     } 
/*  70 */     if (!inputParameter.containsKey("operators")) {
/*  71 */       logger.warn("Get inputParameter: operators fail! InputParameter: '{}'", inputParameter);
/*  72 */       return Collections.emptyList();
/*     */     } 
/*  74 */     if (!inputParameter.containsKey("startDateTime")) {
/*  75 */       logger.warn("Get inputParameter: startDateTime fail! InputParameter: '{}'", inputParameter);
/*  76 */       return Collections.emptyList();
/*     */     } 
/*  78 */     if (!inputParameter.containsKey("endDateTime")) {
/*  79 */       logger.warn("Get inputParameter: endDateTime fail! InputParameter: '{}'", inputParameter);
/*  80 */       return Collections.emptyList();
/*     */     } 
/*     */ 
/*     */     
/*  84 */     String deviceTypeStr = (String)inputParameter.get("deviceType");
/*  85 */     String deviceNamesStr = (String)inputParameter.get("devices");
/*  86 */     String operatorsStr = (String)inputParameter.get("operators");
/*  87 */     String startDateTimeStr = (String)inputParameter.get("startDateTime");
/*  88 */     String endDateTimeStr = (String)inputParameter.get("endDateTime");
/*     */ 
/*     */     
/*  91 */     Boolean showDeviceName = inputParameter.containsKey("showDeviceName") ? Boolean.valueOf((String)inputParameter.get("showDeviceName")) : null;
/*     */ 
/*     */     
/*  94 */     String userName = inputParameter.containsKey("userName") ? (String)inputParameter.get("userName") : "";
/*     */ 
/*     */     
/*  97 */     List<String> deviceNames = Arrays.asList(deviceNamesStr.split(","));
/*  98 */     List<String> userIds = "".equals(operatorsStr) ? null : Arrays.<String>asList(operatorsStr.split(","));
/*  99 */     List<String> newUserIds = new ArrayList<>();
/*     */     
/* 101 */     if (userIds.size() > 0) {
/* 102 */       for (String userId : userIds) {
/* 103 */         if (userId.equals("#RSP")) {
/* 104 */           userId = "#PEX";
/*     */         }
/* 106 */         newUserIds.add(userId);
/*     */       } 
/*     */     }
/*     */     
/* 110 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 111 */     Date startDateTime = null;
/* 112 */     Date endDateTime = null;
/*     */     try {
/* 114 */       startDateTime = dateFormat.parse(startDateTimeStr);
/* 115 */       endDateTime = dateFormat.parse(endDateTimeStr);
/* 116 */     } catch (ParseException e) {
/* 117 */       logger.warn("Parse date string failed! startDateTime:'{}', endDateTime:'{}'", new Object[] { startDateTimeStr, endDateTimeStr, e });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 122 */       return Collections.emptyList();
/*     */     } 
/*     */ 
/*     */     
/* 126 */     String chartKey = (String)inputParameter.get("CHART_KEY");
/*     */     
/* 128 */     List<Map<String, Object>> resultMapArray = new ArrayList<>();
/*     */ 
/*     */     
/* 131 */     Map<String, Object> tableDataMap = new HashMap<>();
/*     */ 
/*     */     
/* 134 */     Map<String, String> deviceNameMap = getDeviceNameMap(deviceTypeStr, deviceNames);
/* 135 */     Map<String, String> userMap = getUserMap(newUserIds);
/*     */ 
/*     */     
/* 138 */     Sort sort = Sort.by(new String[] { "operationTime", "userId", "deviceName" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     List<OperationLog> rptOpLogForMultiDevices = (userIds == null) ? this.operationLogRepository.findByDeviceNamesAndOpTime(deviceNames, OperationItem.GET.toString(), startDateTime, endDateTime, sort) : this.operationLogRepository.findByDeviceNamesAndUserIdAndOpTime(deviceNames, newUserIds, OperationItem.GET
/*     */ 
/*     */         
/* 146 */         .toString(), startDateTime, endDateTime, sort);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     HashMap[] arrayOfHashMap = new HashMap[rptOpLogForMultiDevices.size()];
/*     */     
/* 154 */     int row = 0;
/* 155 */     for (OperationLog d : rptOpLogForMultiDevices) {
/* 156 */       Map<String, Object> opLogForMultiDevice1 = new HashMap<>();
/* 157 */       opLogForMultiDevice1.put("operationTime", d.getOperationTime());
/* 158 */       opLogForMultiDevice1.put("subSysName", this.messageSourceExt
/*     */           
/* 160 */           .getEnumMessage(
/* 161 */             (Enum)Enum.valueOf(SubSystem.class, d.getSubSysName()), ".report."));
/* 162 */       opLogForMultiDevice1.put("deviceName", deviceNameMap.get(d.getDeviceName()));
/* 163 */       opLogForMultiDevice1.put("description", d.getDescription());
/* 164 */       opLogForMultiDevice1.put("userName", 
/*     */           
/* 166 */           (userMap.get(d.getUserId()) != null) ? userMap
/* 167 */           .get(d.getUserId()) : 
/* 168 */           AoRptValueFormatter.getFormattedOperatorName(d.getUserId()));
/* 169 */       opLogForMultiDevice1.put("operationResult", this.formatter
/* 170 */           .getOperationResultName(d.getOperationResult()));
/* 171 */       opLogForMultiDevice1.put("remark", (d.getRemark() == null) ? "" : d.getRemark());
/*     */       
/* 173 */       arrayOfHashMap[row++] = (HashMap)opLogForMultiDevice1;
/*     */     } 
/*     */     
/* 176 */     tableDataMap.put("opLogForMultiDevice", arrayOfHashMap);
/* 177 */     tableDataMap.put("CHART_KEY", chartKey);
/* 178 */     tableDataMap.put("showDeviceName", showDeviceName);
/* 179 */     tableDataMap.put("startDate", startDateTime);
/* 180 */     tableDataMap.put("endDate", endDateTime);
/* 181 */     tableDataMap.put("userName", userName);
/*     */     
/* 183 */     resultMapArray.add(tableDataMap);
/*     */     
/* 185 */     return resultMapArray;
/*     */   }
/*     */   
/*     */   private Map<String, String> getDeviceNameMap(String deviceType, List<String> deviceNames) {
/* 189 */     Map<String, String> map = new HashMap<>();
/*     */     
/* 191 */     for (Object[] config : this.deviceTcConfigRepository.findNameByDeviceTypeAndDeviceNames(deviceType, deviceNames))
/*     */     {
/* 193 */       map.put((String)config[0], (String)config[1]);
/*     */     }
/* 195 */     return map;
/*     */   }
/*     */   
/*     */   private Map<String, String> getUserMap(List<String> userIds) {
/* 199 */     if (userIds == null) {
/* 200 */       return null;
/*     */     }
/* 202 */     Map<String, String> map = new HashMap<>();
/* 203 */     for (User user : this.userRepository.findByLoginIn(userIds)) {
/* 204 */       map.put(user.getLogin(), user.getName());
/*     */     }
/* 206 */     return map;
/*     */   }
/*     */   
/*     */   public PreviewGridData inquirePreviewGridData(Map<String, Object> inputParameter) {
/* 210 */     List<Map<String, Object>> reportMap = inquire(inputParameter);
/*     */     
/* 212 */     Map[] arrayOfMap = (Map[])((Map)reportMap.get(0)).get("opLogForMultiDevice");
/* 213 */     if (reportMap.size() == 0) {
/* 214 */       return new PreviewGridData();
/*     */     }
/*     */ 
/*     */     
/* 218 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 219 */     List<PreviewGridRow> rows = new ArrayList<>();
/* 220 */     for (Map<String, Object> record : arrayOfMap) {
/*     */       
/* 222 */       record.put("operationTime", dateFormat.format(record.get("operationTime")));
/*     */       
/* 224 */       PreviewGridRow row = new PreviewGridRow();
/* 225 */       row.setId(UUID.randomUUID().toString());
/* 226 */       row.setColumnValMap(record);
/* 227 */       rows.add(row);
/*     */     } 
/*     */     
/* 230 */     PreviewGridData result = new PreviewGridData();
/* 231 */     result.setHeaders(genPreviewGridHeaders(reportMap));
/* 232 */     result.setRows(rows);
/* 233 */     return result;
/*     */   }
/*     */   
/*     */   private List<PreviewGridHeader> genPreviewGridHeaders(List<Map<String, Object>> reportMapArr) {
/* 237 */     List<PreviewGridHeader> headers = new ArrayList<>();
/*     */     
/* 239 */     PreviewGridHeader h1 = new PreviewGridHeader();
/* 240 */     h1.setColumnId("operationTime");
/* 241 */     h1.setHeader(this.messageSourceExt.getMessage("rptFm.opLogForMultiDevice.header.operationTime"));
/* 242 */     h1.setWidth(Integer.valueOf(115));
/* 243 */     headers.add(h1);
/*     */     
/* 245 */     PreviewGridHeader h2 = new PreviewGridHeader();
/* 246 */     h2.setColumnId("subSysName");
/* 247 */     h2.setHeader(this.messageSourceExt.getMessage("rptFm.opLogForMultiDevice.header.subSysName"));
/* 248 */     h2.setWidth(Integer.valueOf(110));
/* 249 */     headers.add(h2);
/*     */     
/* 251 */     PreviewGridHeader h3 = new PreviewGridHeader();
/* 252 */     h3.setColumnId("deviceName");
/* 253 */     h3.setHeader(this.messageSourceExt.getMessage("rptFm.opLogForMultiDevice.header.deviceName"));
/* 254 */     h3.setWidth(Integer.valueOf(200));
/* 255 */     headers.add(h3);
/*     */     
/* 257 */     PreviewGridHeader h4 = new PreviewGridHeader();
/* 258 */     h4.setColumnId("description");
/* 259 */     h4.setHeader(this.messageSourceExt.getMessage("rptFm.opLogForMultiDevice.header.description"));
/* 260 */     h4.setWidth(Integer.valueOf(445));
/* 261 */     headers.add(h4);
/*     */     
/* 263 */     PreviewGridHeader h5 = new PreviewGridHeader();
/* 264 */     h5.setColumnId("userName");
/* 265 */     h5.setHeader(this.messageSourceExt.getMessage("rptFm.opLogForMultiDevice.header.userName"));
/* 266 */     h5.setWidth(Integer.valueOf(80));
/* 267 */     headers.add(h5);
/*     */     
/* 269 */     PreviewGridHeader h6 = new PreviewGridHeader();
/* 270 */     h6.setColumnId("operationResult");
/* 271 */     h6.setHeader(this.messageSourceExt.getMessage("rptFm.opLogForMultiDevice.header.operationResult"));
/* 272 */     h6.setWidth(Integer.valueOf(60));
/* 273 */     headers.add(h6);
/*     */     
/* 275 */     return headers;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\rpt\AoOpLogForMultiDeviceReportInquiryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */