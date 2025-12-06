/*     */ package com.hwacom.ngtms.ao.fm.service.rpt;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.ao.fm.model.NcuCardReaderLogData;
/*     */ import com.hwacom.ngtms.ao.fm.model.NcuCardReaderMappingConfig;
/*     */ import com.hwacom.ngtms.ao.fm.repository.NcuCardReaderLogDataRepository;
/*     */ import com.hwacom.ngtms.ao.fm.repository.NcuCardReaderMappingConfigRepository;
/*     */ import com.hwacom.ngtms.ao.shared.dto.NcuCardReaderLogDTO;
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
/*     */ public class AoCardReaderLogReportInquiryImpl
/*     */   implements ReportInquiry
/*     */ {
/*  47 */   private static Logger logger = LoggerFactory.getLogger(AoCardReaderLogReportInquiryImpl.class);
/*     */   
/*     */   private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
/*     */   
/*     */   private static final String cardReaderTapOutId = "35";
/*     */   
/*     */   @Autowired
/*     */   private RoomCardConfigRepository roomCardConfigRepository;
/*     */   
/*     */   @Autowired
/*     */   private NcuCardReaderMappingConfigRepository ncuCardReaderMappingConfigRepository;
/*     */   @Autowired
/*     */   private NcuCardReaderLogDataRepository ncuCardReaderLogDataRepository;
/*     */   @Autowired
/*     */   private OperationLogRepository operationLogRepository;
/*     */   @Autowired
/*     */   private MessageSourceExt messageSourceExt;
/*     */   
/*     */   public List<Map<String, Object>> inquire(Map<String, Object> inputParameter) {
/*  66 */     if (!inputParameter.containsKey("startDateTime")) {
/*  67 */       logger.warn("Get inputParameter: startDateTime fail! InputParameter: '{}'", inputParameter);
/*  68 */       return Collections.emptyList();
/*     */     } 
/*  70 */     if (!inputParameter.containsKey("endDateTime")) {
/*  71 */       logger.warn("Get inputParameter: endDateTime fail! InputParameter: '{}'", inputParameter);
/*  72 */       return Collections.emptyList();
/*     */     } 
/*  74 */     if (!inputParameter.containsKey("status")) {
/*  75 */       logger.warn("Get inputParameter: status fail! InputParameter: '{}'", inputParameter);
/*  76 */       return Collections.emptyList();
/*     */     } 
/*  78 */     if (!inputParameter.containsKey("location")) {
/*  79 */       logger.warn("Get inputParameter: location fail! InputParameter: '{}'", inputParameter);
/*  80 */       return Collections.emptyList();
/*     */     } 
/*     */ 
/*     */     
/*  84 */     String startDateTimeStr = (String)inputParameter.get("startDateTime");
/*  85 */     String endDateTimeStr = (String)inputParameter.get("endDateTime");
/*  86 */     String locationStr = (String)inputParameter.get("location");
/*  87 */     String statusStr = (String)inputParameter.get("status");
/*     */     
/*  89 */     logger.debug("startDateTimeStr:'{}'", startDateTimeStr);
/*  90 */     logger.debug("endDateTimeStr:'{}'", endDateTimeStr);
/*  91 */     logger.debug("locationStr:'{}'", locationStr);
/*  92 */     logger.debug("statusStr:'{}'", statusStr);
/*     */     
/*  94 */     String userName = inputParameter.containsKey("userName") ? (String)inputParameter.get("userName") : "";
/*     */     
/*  96 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*  97 */     Date startDateTime = null;
/*  98 */     Date endDateTime = null;
/*     */     try {
/* 100 */       startDateTime = dateFormat.parse(startDateTimeStr);
/* 101 */       endDateTime = dateFormat.parse(endDateTimeStr);
/* 102 */     } catch (ParseException e) {
/* 103 */       logger.warn("Parse date string failed! startDateTime:'{}', endDateTime:'{}'", new Object[] { startDateTimeStr, endDateTimeStr, e });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 108 */       return Collections.emptyList();
/*     */     } 
/*     */     
/* 111 */     String chartKey = (String)inputParameter.get("CHART_KEY");
/*     */     
/* 113 */     List<Map<String, Object>> resultMapArray = new ArrayList<>();
/*     */ 
/*     */     
/* 116 */     Map<String, Object> tableDataMap = new HashMap<>();
/*     */ 
/*     */     
/* 119 */     Sort sort = Sort.by(new String[] { "dataTime", "ncuId" });
/*     */     
/* 121 */     Sort operationSort = Sort.by(new String[] { "operationTime", "deviceName" });
/* 122 */     List<NcuCardReaderLogData> cardReaderLogs = new ArrayList<>();
/* 123 */     List<OperationLog> operationLogs = new ArrayList<>();
/* 124 */     List<NcuCardReaderLogDTO> rptCardReaderLogs = new ArrayList<>();
/*     */     
/* 126 */     if (locationStr.equals("全部")) {
/* 127 */       if (statusStr.equals("全部")) {
/*     */         
/* 129 */         cardReaderLogs = this.ncuCardReaderLogDataRepository.findByDataTimeBetween(startDateTime, endDateTime, sort);
/*     */         
/* 131 */         operationLogs = this.operationLogRepository.findByOperationItemAndSubSysNameAndOperationTimeBetween(OperationItem.SET
/* 132 */             .toString(), "ROOM", startDateTime, endDateTime, operationSort);
/* 133 */       } else if (statusStr.equals("遠端開門")) {
/*     */         
/* 135 */         operationLogs = this.operationLogRepository.findByOperationItemAndSubSysNameAndOperationTimeBetween(OperationItem.SET
/* 136 */             .toString(), "ROOM", startDateTime, endDateTime, operationSort);
/* 137 */       } else if (statusStr.equals("刷進")) {
/*     */         
/* 139 */         cardReaderLogs = this.ncuCardReaderLogDataRepository.findByDataTimeBetweenAndDeviceIdNot(startDateTime, endDateTime, "35", sort);
/*     */       }
/* 141 */       else if (statusStr.equals("刷出")) {
/*     */         
/* 143 */         cardReaderLogs = this.ncuCardReaderLogDataRepository.findByDataTimeBetweenAndDeviceId(startDateTime, endDateTime, "35", sort);
/*     */       }
/*     */     
/*     */     }
/* 147 */     else if (statusStr.equals("全部")) {
/*     */       
/* 149 */       cardReaderLogs = this.ncuCardReaderLogDataRepository.findByDataTimeBetweenAndNcuId(startDateTime, endDateTime, locationStr, sort);
/*     */ 
/*     */ 
/*     */       
/* 153 */       operationLogs = this.operationLogRepository.findByOperationItemAndSubSysNameAndDeviceNameAndOperationTimeBetween(OperationItem.SET
/* 154 */           .toString(), "ROOM", locationStr, startDateTime, endDateTime, operationSort);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 160 */     else if (statusStr.equals("遠端開門")) {
/*     */ 
/*     */       
/* 163 */       operationLogs = this.operationLogRepository.findByOperationItemAndSubSysNameAndDeviceNameAndOperationTimeBetween(OperationItem.SET
/* 164 */           .toString(), "ROOM", locationStr, startDateTime, endDateTime, operationSort);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 170 */     else if (statusStr.equals("刷進")) {
/*     */       
/* 172 */       cardReaderLogs = this.ncuCardReaderLogDataRepository.findByDataTimeBetweenAndNcuIdAndDeviceIdNot(startDateTime, endDateTime, locationStr, "35");
/*     */     }
/* 174 */     else if (statusStr.equals("刷出")) {
/*     */       
/* 176 */       cardReaderLogs = this.ncuCardReaderLogDataRepository.findByDataTimeBetweenAndNcuIdAndDeviceId(startDateTime, endDateTime, locationStr, "35");
/*     */     } 
/*     */ 
/*     */     
/* 180 */     for (NcuCardReaderLogData data : cardReaderLogs) {
/* 181 */       rptCardReaderLogs.add(transferToDtoFromNcuCardReaderLog(data));
/*     */     }
/* 183 */     for (OperationLog log : operationLogs) {
/* 184 */       if (log.getSubSysName().equals("ROOM") && log
/* 185 */         .getRemark() != null && log
/* 186 */         .getRemark().contains("openDoor")) {
/* 187 */         rptCardReaderLogs.add(transferToDtoFromOperationLog(log));
/*     */       }
/*     */     } 
/* 190 */     Collections.sort(rptCardReaderLogs, new Comparator<NcuCardReaderLogDTO>()
/*     */         {
/*     */           
/*     */           public int compare(NcuCardReaderLogDTO o1, NcuCardReaderLogDTO o2)
/*     */           {
/* 195 */             int flag = o1.getDataTime().compareTo(o2.getDataTime());
/* 196 */             if (flag == 0) {
/* 197 */               return o1.getNcuId().compareTo(o2.getNcuId());
/*     */             }
/* 199 */             return flag;
/*     */           }
/*     */         });
/*     */     
/* 203 */     logger.debug("rptCardReaderLogs.size():'{}'", Integer.valueOf(rptCardReaderLogs.size()));
/* 204 */     HashMap[] arrayOfHashMap = new HashMap[rptCardReaderLogs.size()];
/* 205 */     if (rptCardReaderLogs.size() == 0) {
/* 206 */       arrayOfHashMap = new HashMap[1];
/*     */     }
/* 208 */     IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 209 */     String locationName = locationStr;
/* 210 */     DeviceTcConfig locDeviceConfig = (DeviceTcConfig)deviceMap.get(locationStr);
/* 211 */     if (locDeviceConfig != null) {
/* 212 */       locationName = locDeviceConfig.getDisplayName();
/*     */     }
/* 214 */     int row = 0;
/* 215 */     for (NcuCardReaderLogDTO dto : rptCardReaderLogs) {
/* 216 */       Map<String, Object> cardReaderLog = new HashMap<>();
/* 217 */       cardReaderLog.put("location", dto.getLocation());
/* 218 */       cardReaderLog.put("displaName", dto.getDisplayName());
/* 219 */       cardReaderLog.put("cardNumber", dto.getCardNumber());
/* 220 */       cardReaderLog.put("name", dto.getUserName());
/* 221 */       cardReaderLog.put("company", dto.getUserCompany());
/* 222 */       cardReaderLog.put("status", dto.getStatus());
/* 223 */       cardReaderLog.put("dataTime", dateFormat.format(dto.getDataTime()));
/* 224 */       cardReaderLog.put("result", dto.getResult());
/* 225 */       arrayOfHashMap[row++] = (HashMap)cardReaderLog;
/*     */     } 
/*     */     
/* 228 */     tableDataMap.put("cardReaderLog", arrayOfHashMap);
/* 229 */     tableDataMap.put("CHART_KEY", chartKey);
/* 230 */     tableDataMap.put("status", statusStr);
/* 231 */     tableDataMap.put("location", locationName);
/* 232 */     tableDataMap.put("userName", userName);
/*     */     
/* 234 */     resultMapArray.add(tableDataMap);
/* 235 */     return resultMapArray;
/*     */   }
/*     */   
/*     */   private String getStatusNameFromStatus(String status) {
/* 239 */     if (status == null)
/* 240 */       return ""; 
/* 241 */     if (status.equals("35")) {
/* 242 */       return "刷出";
/*     */     }
/* 244 */     return "刷進";
/*     */   }
/*     */ 
/*     */   
/*     */   private NcuCardReaderLogDTO transferToDtoFromNcuCardReaderLog(NcuCardReaderLogData data) {
/* 249 */     IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 250 */     DeviceTcConfig ncuConfig = (DeviceTcConfig)deviceMap.get(data.getNcuId());
/* 251 */     RoomCardConfig roomCardConfig = this.roomCardConfigRepository.findByAba(data.getCardNumber());
/*     */     
/* 253 */     NcuCardReaderMappingConfig mappingConfig = this.ncuCardReaderMappingConfigRepository.findByNcuIdAndDeviceId(data
/* 254 */         .getNcuId(), data.getDeviceId());
/* 255 */     DeviceTcConfig cardReaderConfig = null;
/* 256 */     if (mappingConfig != null) {
/* 257 */       cardReaderConfig = (DeviceTcConfig)deviceMap.get(mappingConfig.getControlId());
/*     */     }
/* 259 */     String statusCode = data.getStatusCode();
/* 260 */     NcuCardReaderLogDTO dto = new NcuCardReaderLogDTO();
/* 261 */     dto.setId(data.getId());
/* 262 */     dto.setCardNumber(data.getCardNumber());
/* 263 */     dto.setDataTime(data.getDataTime());
/*     */     
/* 265 */     dto.setDeviceId(data.getDeviceId());
/* 266 */     dto.setNcuId(data.getNcuId());
/* 267 */     if (ncuConfig != null) {
/* 268 */       dto.setLocation(ncuConfig.getDisplayName());
/*     */     }
/* 270 */     if (cardReaderConfig != null) {
/* 271 */       dto.setDisplayName(cardReaderConfig.getDisplayName());
/*     */     }
/* 273 */     if (roomCardConfig != null) {
/* 274 */       dto.setUserCompany(roomCardConfig.getCompany());
/* 275 */       dto.setUserName(roomCardConfig.getName());
/*     */     } 
/* 277 */     dto.setStatus(getStatusNameFromStatus(data.getDeviceId()));
/* 278 */     dto.setResult(getResultNameFromStatusCode(statusCode));
/* 279 */     return dto;
/*     */   }
/*     */   
/*     */   private NcuCardReaderLogDTO transferToDtoFromOperationLog(OperationLog log) {
/* 283 */     logger.debug("OperationLog:'{}'", log);
/* 284 */     IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 285 */     DeviceTcConfig ncuConfig = (DeviceTcConfig)deviceMap.get(log.getDeviceName());
/*     */     
/* 287 */     String remarkStr = log.getRemark();
/* 288 */     String deviceId = "";
/* 289 */     if (remarkStr != null) {
/* 290 */       String[] token = remarkStr.split(":");
/* 291 */       if (token.length > 0) {
/* 292 */         deviceId = token[1];
/*     */       }
/*     */     } 
/*     */     
/* 296 */     NcuCardReaderMappingConfig mappingConfig = this.ncuCardReaderMappingConfigRepository.findByNcuIdAndDeviceId(log.getDeviceName(), deviceId);
/* 297 */     DeviceTcConfig cardReaderConfig = null;
/* 298 */     if (mappingConfig != null) {
/* 299 */       cardReaderConfig = (DeviceTcConfig)deviceMap.get(mappingConfig.getControlId());
/*     */     }
/* 301 */     NcuCardReaderLogDTO dto = new NcuCardReaderLogDTO();
/* 302 */     dto.setId("" + log.getId());
/* 303 */     dto.setDataTime(log.getOperationTime());
/* 304 */     dto.setDeviceId(deviceId);
/* 305 */     if (cardReaderConfig != null) {
/* 306 */       dto.setDisplayName(cardReaderConfig.getDisplayName());
/*     */     }
/* 308 */     dto.setNcuId(log.getDeviceName());
/* 309 */     if (ncuConfig != null) {
/* 310 */       dto.setLocation(ncuConfig.getDisplayName());
/*     */     }
/* 312 */     dto.setStatus("遠端開門");
/* 313 */     dto.setResult(getResultNameFromOpResult(log.getOperationResult()));
/* 314 */     return dto;
/*     */   }
/*     */   
/*     */   private String getResultNameFromOpResult(OperationResult opResult) {
/* 318 */     if (opResult == null) {
/* 319 */       return "";
/*     */     }
/* 321 */     switch (opResult) {
/*     */       case SUCCESS:
/* 323 */         return "成功";
/*     */       case FAILURE:
/* 325 */         return "失敗";
/*     */       case CANCEL:
/* 327 */         return "取消";
/*     */     } 
/* 329 */     return opResult.name();
/*     */   }
/*     */ 
/*     */   
/*     */   private String getResultNameFromStatusCode(String statusCode) {
/* 334 */     if (statusCode == null) {
/* 335 */       return "失敗";
/*     */     }
/* 337 */     if (statusCode.equals("01")) {
/* 338 */       return "成功";
/*     */     }
/* 340 */     return "失敗";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PreviewGridData inquirePreviewGridData(Map<String, Object> inputParameter) {
/* 346 */     List<Map<String, Object>> reportMap = inquire(inputParameter);
/* 347 */     Map[] arrayOfMap = (Map[])((Map)reportMap.get(0)).get("cardReaderLog");
/* 348 */     if (reportMap.size() == 0) {
/* 349 */       return new PreviewGridData();
/*     */     }
/*     */ 
/*     */     
/* 353 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/* 354 */     List<PreviewGridRow> rows = new ArrayList<>();
/* 355 */     for (Map<String, Object> record : arrayOfMap) {
/*     */       
/* 357 */       record.put("startTime", dateFormat.format(record.get("startTime")));
/*     */       
/* 359 */       PreviewGridRow row = new PreviewGridRow();
/* 360 */       row.setId(UUID.randomUUID().toString());
/* 361 */       row.setColumnValMap(record);
/* 362 */       rows.add(row);
/*     */     } 
/*     */     
/* 365 */     PreviewGridData result = new PreviewGridData();
/* 366 */     result.setHeaders(genPreviewGridHeaders(reportMap));
/* 367 */     result.setRows(rows);
/* 368 */     return result;
/*     */   }
/*     */   
/*     */   private List<PreviewGridHeader> genPreviewGridHeaders(List<Map<String, Object>> reportMap) {
/* 372 */     List<PreviewGridHeader> headers = new ArrayList<>();
/*     */     
/* 374 */     PreviewGridHeader h1 = new PreviewGridHeader();
/* 375 */     h1.setColumnId("location");
/* 376 */     h1.setHeader(this.messageSourceExt.getMessage("機房名稱"));
/* 377 */     h1.setWidth(Integer.valueOf(300));
/* 378 */     headers.add(h1);
/*     */     
/* 380 */     PreviewGridHeader h2 = new PreviewGridHeader();
/* 381 */     h2.setColumnId("displaName");
/* 382 */     h2.setHeader(this.messageSourceExt.getMessage("讀卡機名稱"));
/* 383 */     h2.setWidth(Integer.valueOf(300));
/* 384 */     headers.add(h2);
/*     */     
/* 386 */     PreviewGridHeader h3 = new PreviewGridHeader();
/* 387 */     h3.setColumnId("cardNumber");
/* 388 */     h3.setHeader(this.messageSourceExt.getMessage("磁卡編號前10碼"));
/* 389 */     h3.setWidth(Integer.valueOf(300));
/* 390 */     headers.add(h3);
/*     */     
/* 392 */     PreviewGridHeader h4 = new PreviewGridHeader();
/* 393 */     h4.setColumnId("name");
/* 394 */     h4.setHeader(this.messageSourceExt.getMessage("姓名"));
/* 395 */     h4.setWidth(Integer.valueOf(100));
/* 396 */     headers.add(h4);
/*     */     
/* 398 */     PreviewGridHeader h5 = new PreviewGridHeader();
/* 399 */     h5.setColumnId("company");
/* 400 */     h5.setHeader(this.messageSourceExt.getMessage("公司"));
/* 401 */     h5.setWidth(Integer.valueOf(150));
/* 402 */     headers.add(h5);
/*     */     
/* 404 */     PreviewGridHeader h6 = new PreviewGridHeader();
/* 405 */     h6.setColumnId("status");
/* 406 */     h6.setHeader(this.messageSourceExt.getMessage("刷進/刷出"));
/* 407 */     h6.setWidth(Integer.valueOf(150));
/* 408 */     headers.add(h6);
/*     */     
/* 410 */     PreviewGridHeader h7 = new PreviewGridHeader();
/* 411 */     h7.setColumnId("dataTime");
/* 412 */     h7.setHeader(this.messageSourceExt.getMessage("時間"));
/* 413 */     h7.setWidth(Integer.valueOf(300));
/* 414 */     headers.add(h7);
/*     */     
/* 416 */     return headers;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\rpt\AoCardReaderLogReportInquiryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */