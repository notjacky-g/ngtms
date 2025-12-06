/*     */ package com.hwacom.ngtms.ao.fm.service.rpt;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.ao.util.AoRptValueFormatter;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*     */ import com.hwacom.ngtms.base.oplog.model.OperationLog;
/*     */ import com.hwacom.ngtms.base.oplog.repository.OperationLogRepository;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationItem;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
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
/*     */ @Service
/*     */ public class AoOpLogForAllSystemReportInquiryImpl
/*     */   implements ReportInquiry
/*     */ {
/*  44 */   private static Logger logger = LoggerFactory.getLogger(AoOpLogForAllSystemReportInquiryImpl.class);
/*     */   
/*     */   private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
/*     */   
/*     */   @Autowired
/*     */   private OperationLogRepository operationLogRepository;
/*     */   
/*     */   @Autowired
/*     */   private UserRepository userRepository;
/*     */   @Autowired
/*     */   private MessageSourceExt messageSourceExt;
/*     */   @Autowired
/*     */   private AoRptValueFormatter formatter;
/*     */   
/*     */   public List<Map<String, Object>> inquire(Map<String, Object> inputParameter) {
/*  59 */     if (!inputParameter.containsKey("operationTypes")) {
/*  60 */       logger.warn("Get inputParameter: operationTypes fail! InputParameter: '{}'", inputParameter);
/*  61 */       return Collections.emptyList();
/*     */     } 
/*  63 */     if (!inputParameter.containsKey("operators")) {
/*  64 */       logger.warn("Get inputParameter: operators fail! InputParameter: '{}'", inputParameter);
/*  65 */       return Collections.emptyList();
/*     */     } 
/*  67 */     if (!inputParameter.containsKey("startDateTime")) {
/*  68 */       logger.warn("Get inputParameter: startDateTime fail! InputParameter: '{}'", inputParameter);
/*  69 */       return Collections.emptyList();
/*     */     } 
/*  71 */     if (!inputParameter.containsKey("endDateTime")) {
/*  72 */       logger.warn("Get inputParameter: endDateTime fail! InputParameter: '{}'", inputParameter);
/*  73 */       return Collections.emptyList();
/*     */     } 
/*     */ 
/*     */     
/*  77 */     String operationTypesStr = (String)inputParameter.get("operationTypes");
/*  78 */     String operatorsStr = (String)inputParameter.get("operators");
/*  79 */     String startDateTimeStr = (String)inputParameter.get("startDateTime");
/*  80 */     String endDateTimeStr = (String)inputParameter.get("endDateTime");
/*     */ 
/*     */     
/*  83 */     Boolean showDeviceName = inputParameter.containsKey("showDeviceName") ? Boolean.valueOf((String)inputParameter.get("showDeviceName")) : null;
/*     */ 
/*     */     
/*  86 */     String userName = inputParameter.containsKey("userName") ? (String)inputParameter.get("userName") : "";
/*     */ 
/*     */     
/*  89 */     List<String> subSysNames = Arrays.asList(operationTypesStr.split(","));
/*  90 */     List<String> userIds = "".equals(operatorsStr) ? null : Arrays.<String>asList(operatorsStr.split(","));
/*     */     
/*  92 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*  93 */     Date startDateTime = null;
/*  94 */     Date endDateTime = null;
/*     */     try {
/*  96 */       startDateTime = dateFormat.parse(startDateTimeStr);
/*  97 */       endDateTime = dateFormat.parse(endDateTimeStr);
/*  98 */     } catch (ParseException e) {
/*  99 */       logger.warn("Parse date string failed! startDateTime:'{}', endDateTime:'{}'", new Object[] { startDateTimeStr, endDateTimeStr, e });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 104 */       return Collections.emptyList();
/*     */     } 
/*     */ 
/*     */     
/* 108 */     String chartKey = (String)inputParameter.get("CHART_KEY");
/*     */     
/* 110 */     List<Map<String, Object>> resultMapArray = new ArrayList<>();
/*     */ 
/*     */     
/* 113 */     Map<String, Object> tableDataMap = new HashMap<>();
/*     */     
/* 115 */     Map<String, String> userMap = getUserMap(userIds);
/*     */ 
/*     */     
/* 118 */     Sort sort = Sort.by(new String[] { "subSysName", "operationTime" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     List<OperationLog> rptOpLogForAllSystems = (userIds == null) ? this.operationLogRepository.findBySubSysNamesAndOpTime(subSysNames, OperationItem.GET.toString(), startDateTime, endDateTime, sort) : this.operationLogRepository.findBySubSysNamesAndUserIdAndOpTime(subSysNames, userIds, OperationItem.GET
/*     */ 
/*     */         
/* 126 */         .toString(), startDateTime, endDateTime, sort);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     HashMap[] arrayOfHashMap = new HashMap[rptOpLogForAllSystems.size()];
/*     */     
/* 134 */     int row = 0;
/* 135 */     IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 136 */     for (OperationLog d : rptOpLogForAllSystems) {
/* 137 */       Map<String, Object> opLogForAllSystem1 = new HashMap<>();
/* 138 */       opLogForAllSystem1.put("operationTime", d.getOperationTime());
/* 139 */       opLogForAllSystem1.put("subSysName", this.messageSourceExt
/*     */           
/* 141 */           .getEnumMessage(
/* 142 */             (Enum)Enum.valueOf(SubSystem.class, d.getSubSysName()), ".report."));
/*     */       
/* 144 */       if (d.getDeviceName() == null) {
/* 145 */         opLogForAllSystem1.put("description", d.getDescription());
/*     */       } else {
/* 147 */         DeviceTcConfig device = (DeviceTcConfig)deviceMap.get(d.getDeviceName());
/* 148 */         opLogForAllSystem1.put("description", (device == null || device
/*     */             
/* 150 */             .getDisplayName() == null) ? (d
/* 151 */             .getDescription() + " " + d.getDeviceName()) : (d
/* 152 */             .getDescription() + " " + device.getDisplayName()));
/*     */       } 
/* 154 */       opLogForAllSystem1.put("description", 
/*     */           
/* 156 */           (d.getDeviceName() == null) ? d
/* 157 */           .getDescription() : (d
/* 158 */           .getDescription() + " " + d.getDeviceName()));
/* 159 */       String name = userMap.get(d.getUserId());
/* 160 */       if (d.getUserId().indexOf("HCCE") > -1) {
/* 161 */         name = "中央電腦系統";
/*     */       }
/* 163 */       opLogForAllSystem1.put("userName", name);
/* 164 */       opLogForAllSystem1.put("operationResult", this.formatter
/* 165 */           .getOperationResultName(d.getOperationResult()));
/*     */       
/* 167 */       arrayOfHashMap[row++] = (HashMap)opLogForAllSystem1;
/*     */     } 
/*     */     
/* 170 */     tableDataMap.put("opLogForAllSystem", arrayOfHashMap);
/* 171 */     tableDataMap.put("CHART_KEY", chartKey);
/* 172 */     tableDataMap.put("showDeviceName", showDeviceName);
/* 173 */     tableDataMap.put("startDate", startDateTime);
/* 174 */     tableDataMap.put("endDate", endDateTime);
/* 175 */     tableDataMap.put("userName", userName);
/*     */     
/* 177 */     resultMapArray.add(tableDataMap);
/*     */     
/* 179 */     return resultMapArray;
/*     */   }
/*     */   
/*     */   private Map<String, String> getUserMap(List<String> userIds) {
/* 183 */     if (userIds == null) {
/* 184 */       return null;
/*     */     }
/* 186 */     Map<String, String> map = new HashMap<>();
/* 187 */     for (User user : this.userRepository.findByLoginIn(userIds)) {
/* 188 */       map.put(user.getLogin(), user.getName());
/*     */     }
/* 190 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   public PreviewGridData inquirePreviewGridData(Map<String, Object> inputParameter) {
/* 195 */     List<Map<String, Object>> reportMap = inquire(inputParameter);
/*     */     
/* 197 */     Map[] arrayOfMap = (Map[])((Map)reportMap.get(0)).get("opLogForAllSystem");
/* 198 */     if (reportMap.size() == 0) {
/* 199 */       return new PreviewGridData();
/*     */     }
/*     */ 
/*     */     
/* 203 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 204 */     List<PreviewGridRow> rows = new ArrayList<>();
/* 205 */     for (Map<String, Object> record : arrayOfMap) {
/*     */       
/* 207 */       record.put("operationTime", dateFormat.format(record.get("operationTime")));
/*     */       
/* 209 */       PreviewGridRow row = new PreviewGridRow();
/* 210 */       row.setId(UUID.randomUUID().toString());
/* 211 */       row.setColumnValMap(record);
/* 212 */       rows.add(row);
/*     */     } 
/*     */     
/* 215 */     PreviewGridData result = new PreviewGridData();
/* 216 */     result.setHeaders(genPreviewGridHeaders(reportMap));
/* 217 */     result.setRows(rows);
/* 218 */     return result;
/*     */   }
/*     */   
/*     */   private List<PreviewGridHeader> genPreviewGridHeaders(List<Map<String, Object>> reportMapArr) {
/* 222 */     List<PreviewGridHeader> headers = new ArrayList<>();
/*     */     
/* 224 */     PreviewGridHeader h1 = new PreviewGridHeader();
/* 225 */     h1.setColumnId("operationTime");
/* 226 */     h1.setHeader(this.messageSourceExt.getMessage("rptFm.opLogForAllSystem.header.operationTime"));
/* 227 */     h1.setWidth(Integer.valueOf(115));
/* 228 */     headers.add(h1);
/*     */     
/* 230 */     PreviewGridHeader h2 = new PreviewGridHeader();
/* 231 */     h2.setColumnId("subSysName");
/* 232 */     h2.setHeader(this.messageSourceExt.getMessage("rptFm.opLogForAllSystem.header.subSysName"));
/* 233 */     h2.setWidth(Integer.valueOf(110));
/* 234 */     headers.add(h2);
/*     */     
/* 236 */     PreviewGridHeader h3 = new PreviewGridHeader();
/* 237 */     h3.setColumnId("description");
/* 238 */     h3.setHeader(this.messageSourceExt.getMessage("rptFm.opLogForAllSystem.header.description"));
/* 239 */     h3.setWidth(Integer.valueOf(645));
/* 240 */     headers.add(h3);
/*     */     
/* 242 */     PreviewGridHeader h4 = new PreviewGridHeader();
/* 243 */     h4.setColumnId("userName");
/* 244 */     h4.setHeader(this.messageSourceExt.getMessage("rptFm.opLogForAllSystem.header.userName"));
/* 245 */     h4.setWidth(Integer.valueOf(80));
/* 246 */     headers.add(h4);
/*     */     
/* 248 */     PreviewGridHeader h5 = new PreviewGridHeader();
/* 249 */     h5.setColumnId("operationResult");
/* 250 */     h5.setHeader(this.messageSourceExt.getMessage("rptFm.opLogForAllSystem.header.operationResult"));
/* 251 */     h5.setWidth(Integer.valueOf(60));
/* 252 */     headers.add(h5);
/*     */     
/* 254 */     return headers;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\rpt\AoOpLogForAllSystemReportInquiryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */