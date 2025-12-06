/*     */ package com.hwacom.ngtms.ao.util;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.ao.shared.dto.OperatorDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.ReportConfigDetailDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.ReportExportFileDTO;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.AlarmSubTypeConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadDivision;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadLine;
/*     */ import com.hwacom.ngtms.c.shared.Direction;
/*     */ import com.hwacom.ngtms.c.shared.LocationType;
/*     */ import com.hwacom.ngtms.c.shared.dto.RoadDivisionDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
/*     */ import com.hwacom.ngtms.common.fm.model.Report;
/*     */ import com.hwacom.ngtms.common.fm.model.ReportExportFile;
/*     */ import com.hwacom.ngtms.common.fm.model.User;
/*     */ import com.hwacom.ngtms.common.shared.PreviewData;
/*     */ import com.hwacom.ngtms.common.shared.PreviewGridData;
/*     */ import com.hwacom.ngtms.common.shared.PreviewGridHeader;
/*     */ import com.hwacom.ngtms.common.shared.PreviewGridRow;
/*     */ import com.hwacom.ngtms.common.shared.PreviewHeader;
/*     */ import com.hwacom.ngtms.common.shared.ReportFile;
/*     */ import com.hwacom.ngtms.common.shared.ReportFormat;
/*     */ import com.hwacom.ngtms.common.shared.ReportRepositoryTreeNode;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewDataDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewGridDataDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewGridHeaderDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewGridRowDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewHeaderDTO;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.apache.commons.lang.SerializationUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
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
/*     */ @Service
/*     */ public class AoRptValueFormatter
/*     */ {
/*     */   public static final String ALL = "All";
/*     */   @Autowired
/*     */   protected MessageSourceExt messageSourceExt;
/*     */   
/*     */   public String getFormattedMilepost(Integer milepost) {
/*  66 */     if (milepost == null) {
/*  67 */       return null;
/*     */     }
/*  69 */     BigDecimal kMilepost = new BigDecimal(milepost.intValue());
/*  70 */     return String.format("%.3f", new Object[] { kMilepost.divide(new BigDecimal(1000)) }).replace(".", "k+");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormattedMilepostInFloatWay(Integer milepost) {
/*  80 */     BigDecimal kMilepost = new BigDecimal(milepost.intValue());
/*  81 */     return String.format("%.3f", new Object[] { kMilepost.divide(new BigDecimal(1000)) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormattedWindDirection(int directionNumber) {
/*  91 */     String directionString = this.messageSourceExt.getMessage("rptFm.common.EnDash");
/*  92 */     switch (directionNumber)
/*     */     { case 0:
/*  94 */         directionString = this.messageSourceExt.getMessage("rptFm.windDirection.N");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 145 */         return directionString;case 1: directionString = this.messageSourceExt.getMessage("rptFm.windDirection.NNE"); return directionString;case 2: directionString = this.messageSourceExt.getMessage("rptFm.windDirection.NE"); return directionString;case 3: directionString = this.messageSourceExt.getMessage("rptFm.windDirection.ENE"); return directionString;case 4: directionString = this.messageSourceExt.getMessage("rptFm.windDirection.E"); return directionString;case 5: directionString = this.messageSourceExt.getMessage("rptFm.windDirection.ESE"); return directionString;case 6: directionString = this.messageSourceExt.getMessage("rptFm.windDirection.SE"); return directionString;case 7: directionString = this.messageSourceExt.getMessage("rptFm.windDirection.SSE"); return directionString;case 8: directionString = this.messageSourceExt.getMessage("rptFm.windDirection.S"); return directionString;case 9: directionString = this.messageSourceExt.getMessage("rptFm.windDirection.SSW"); return directionString;case 10: directionString = this.messageSourceExt.getMessage("rptFm.windDirection.SW"); return directionString;case 11: directionString = this.messageSourceExt.getMessage("rptFm.windDirection.WSW"); return directionString;case 12: directionString = this.messageSourceExt.getMessage("rptFm.windDirection.W"); return directionString;case 13: directionString = this.messageSourceExt.getMessage("rptFm.windDirection.WNW"); return directionString;case 14: directionString = this.messageSourceExt.getMessage("rptFm.windDirection.NW"); return directionString;case 15: directionString = this.messageSourceExt.getMessage("rptFm.windDirection.NNW"); return directionString; }  directionString = this.messageSourceExt.getMessage("rptFm.common.EnDash"); return directionString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormattedLocation(LocationType locationType) {
/* 156 */     if (locationType == null) {
/* 157 */       return "";
/*     */     }
/* 159 */     switch (locationType) {
/*     */       case PDF:
/* 161 */         return "主線";
/*     */       case HTML:
/* 163 */         return "主線";
/*     */       case XLS:
/* 165 */         return "隧道";
/*     */       case null:
/* 167 */         return "交流道";
/*     */       case null:
/* 169 */         return "保留";
/*     */       case null:
/* 171 */         return "服務區";
/*     */       case null:
/* 173 */         return "地方道路";
/*     */       case null:
/* 175 */         return "未知區域";
/*     */     } 
/* 177 */     return locationType.toString();
/*     */   }
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
/*     */   
/*     */   public int getConnectionStatus(boolean enable, int commStatus) {
/* 192 */     if (enable && commStatus == 0)
/* 193 */       return 1; 
/* 194 */     if (enable && commStatus == 1) {
/* 195 */       return 0;
/*     */     }
/* 197 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOperationResultName(OperationResult operationResult) {
/* 208 */     switch (operationResult) {
/*     */       case PDF:
/* 210 */         return "取消";
/*     */       case HTML:
/* 212 */         return "失敗";
/*     */       case XLS:
/* 214 */         return "成功";
/*     */     } 
/* 216 */     return operationResult.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDayOfWeekName(Date date) {
/* 221 */     if (date == null) {
/* 222 */       return null;
/*     */     }
/* 224 */     Calendar dateInCalendar = Calendar.getInstance();
/* 225 */     dateInCalendar.setTime(date);
/*     */     
/* 227 */     switch (dateInCalendar.get(7)) {
/*     */       case 1:
/* 229 */         return this.messageSourceExt.getMessage("rptFm.common.dayOfWeek.SUNDAY");
/*     */       case 2:
/* 231 */         return this.messageSourceExt.getMessage("rptFm.common.dayOfWeek.MONDAY");
/*     */       case 3:
/* 233 */         return this.messageSourceExt.getMessage("rptFm.common.dayOfWeek.TUESDAY");
/*     */       case 4:
/* 235 */         return this.messageSourceExt.getMessage("rptFm.common.dayOfWeek.WEDNESDAY");
/*     */       case 5:
/* 237 */         return this.messageSourceExt.getMessage("rptFm.common.dayOfWeek.THURSDAY");
/*     */       case 6:
/* 239 */         return this.messageSourceExt.getMessage("rptFm.common.dayOfWeek.FRIDAY");
/*     */       case 7:
/* 241 */         return this.messageSourceExt.getMessage("rptFm.common.dayOfWeek.SATURDAY");
/*     */     } 
/*     */     
/* 244 */     return null;
/*     */   }
/*     */   
/*     */   public String getFormattedYesNoOrMessageIfNull(Boolean bool, String message) {
/* 248 */     if (bool == null) {
/* 249 */       return message;
/*     */     }
/* 251 */     if (bool.booleanValue()) {
/* 252 */       return this.messageSourceExt.getMessage("rptFm.common.yes");
/*     */     }
/* 254 */     return this.messageSourceExt.getMessage("rptFm.common.no");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFormattedDuration(Date start, Date end) {
/* 259 */     if (start == null || end == null) {
/* 260 */       return "";
/*     */     }
/*     */     
/* 263 */     return getFormattedDuration(end.getTime() - start.getTime());
/*     */   }
/*     */   
/*     */   public String getFormattedDuration(long milliSeconds) {
/* 267 */     if (milliSeconds < 0L) {
/* 268 */       return "";
/*     */     }
/* 270 */     long seconds = milliSeconds / 1000L;
/* 271 */     long secondsInMinute = seconds % 60L;
/* 272 */     long minutes = seconds / 60L;
/* 273 */     long minutesInHour = minutes % 60L;
/* 274 */     long hours = minutes / 60L;
/* 275 */     long hoursInDay = hours % 24L;
/* 276 */     long days = hours / 24L;
/*     */     
/* 278 */     StringBuffer sb = new StringBuffer(20);
/* 279 */     sb.append(String.valueOf(secondsInMinute) + "秒");
/* 280 */     if (minutes > 0L) {
/* 281 */       sb.insert(0, String.valueOf(minutesInHour) + "分");
/*     */     }
/* 283 */     if (hours > 0L) {
/* 284 */       sb.insert(0, String.valueOf(hoursInDay) + "點");
/*     */     }
/* 286 */     if (days > 0L) {
/* 287 */       sb.insert(0, String.valueOf(days) + "日");
/*     */     }
/* 289 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public String getFormattedCommonDuration(long milliSeconds) {
/* 293 */     if (milliSeconds < 0L) {
/* 294 */       return "";
/*     */     }
/* 296 */     long seconds = milliSeconds / 1000L;
/* 297 */     long secondsInMinute = seconds % 60L;
/* 298 */     long minutes = seconds / 60L;
/* 299 */     long minutesInHour = minutes % 60L;
/* 300 */     long hours = minutes / 60L;
/* 301 */     long hoursInDay = hours % 24L;
/* 302 */     long days = hours / 24L;
/*     */     
/* 304 */     StringBuffer sb = new StringBuffer(20);
/* 305 */     sb.append(":" + String.valueOf(secondsInMinute));
/* 306 */     sb.insert(0, ":" + String.valueOf(minutesInHour));
/* 307 */     sb.insert(0, String.valueOf(hoursInDay));
/* 308 */     if (days > 0L) {
/* 309 */       sb.insert(0, String.valueOf(days) + ":");
/*     */     }
/* 311 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public MessageSourceExt getMessageSourceExt() {
/* 315 */     return this.messageSourceExt;
/*     */   }
/*     */   
/*     */   public String getFormattedDirection(Direction direction) {
/* 319 */     return this.messageSourceExt.getEnumFullMessage((Enum)direction);
/*     */   }
/*     */   
/*     */   public String getFormattedLocation(String locationType) {
/* 323 */     if (locationType == null) {
/* 324 */       return "";
/*     */     }
/* 326 */     switch (locationType) {
/*     */       case "F":
/* 328 */         return "主線";
/*     */       case "H":
/* 330 */         return "主線";
/*     */       case "T":
/* 332 */         return "隧道";
/*     */       case "R":
/* 334 */         return "交流道";
/*     */       case "P":
/* 336 */         return "保留";
/*     */       case "S":
/* 338 */         return "服務區";
/*     */       case "L":
/* 340 */         return "地方道路";
/*     */       case "I":
/* 342 */         return "未知區域";
/*     */     } 
/* 344 */     return locationType.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFormattedDirection(String direction) {
/* 349 */     if (direction == null) {
/* 350 */       return "";
/*     */     }
/* 352 */     switch (direction) {
/*     */       case "S":
/* 354 */         return "南向";
/*     */       case "N":
/* 356 */         return "北向";
/*     */       case "E":
/* 358 */         return "東向";
/*     */       case "W":
/* 360 */         return "西向";
/*     */       case "EN":
/* 362 */         return "東北向";
/*     */       case "ES":
/* 364 */         return "東南向";
/*     */       case "EW":
/* 366 */         return "東西向";
/*     */       case "NS":
/* 368 */         return "南北向";
/*     */       case "WN":
/* 370 */         return "西北向";
/*     */       case "WS":
/* 372 */         return "西南向";
/*     */     } 
/* 374 */     return direction.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFormattedAlarmSubType(Integer alarmSubType) {
/* 379 */     if (alarmSubType == null) {
/* 380 */       return null;
/*     */     }
/* 382 */     IMap<Integer, AlarmSubTypeConfig> typeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 383 */     return ((AlarmSubTypeConfig)typeMap.get(alarmSubType)).getShortName();
/*     */   }
/*     */   
/*     */   public Direction getBeforeFormattedDirection(String directionDisplayName) {
/* 387 */     if (directionDisplayName == null) {
/* 388 */       return null;
/*     */     }
/* 390 */     switch (directionDisplayName) {
/*     */       case "北向":
/* 392 */         return Direction.N;
/*     */       case "南向":
/* 394 */         return Direction.S;
/*     */       case "西向":
/* 396 */         return Direction.W;
/*     */       case "東向":
/* 398 */         return Direction.E;
/*     */       case "雙向":
/* 400 */         return Direction.NS;
/*     */     } 
/* 402 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBeforeFormattedDirection(Direction direction) {
/* 407 */     if (direction == null) {
/* 408 */       return null;
/*     */     }
/* 410 */     switch (direction) {
/*     */       case PDF:
/* 412 */         return "北向";
/*     */       case HTML:
/* 414 */         return "南向";
/*     */       case XLS:
/* 416 */         return "西向";
/*     */       case null:
/* 418 */         return "東向";
/*     */     } 
/* 420 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFormattedLineName(String lineId) {
/* 425 */     if (lineId == null) {
/* 426 */       return null;
/*     */     }
/* 428 */     switch (lineId) {
/*     */       case "N1":
/* 430 */         return "國道1號";
/*     */       case "N3":
/* 432 */         return "國道3號";
/*     */       case "N4":
/* 434 */         return "國道4號";
/*     */       case "N6":
/* 436 */         return "國道6號";
/*     */       case "T72":
/* 438 */         return "台72";
/*     */       case "T74":
/* 440 */         return "台74";
/*     */       case "T74甲":
/* 442 */         return "台74甲";
/*     */       case "T76":
/* 444 */         return "台76";
/*     */       case "T78":
/* 446 */         return "台78";
/*     */     } 
/* 448 */     return lineId;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFormattedLineId(String lineName) {
/* 453 */     if (lineName == null) {
/* 454 */       return null;
/*     */     }
/* 456 */     switch (lineName) {
/*     */       case "國道1號":
/* 458 */         return "N1";
/*     */       case "國道3號":
/* 460 */         return "N3";
/*     */       case "國道4號":
/* 462 */         return "N4";
/*     */       case "國道6號":
/* 464 */         return "N6";
/*     */       case "台72":
/* 466 */         return "T72";
/*     */       case "台74":
/* 468 */         return "T74";
/*     */       case "T74甲":
/* 470 */         return "T74甲";
/*     */       case "台76":
/* 472 */         return "T76";
/*     */       case "台78":
/* 474 */         return "T78";
/*     */     } 
/* 476 */     return lineName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFormattedAlarmType(String alarmType) {
/* 481 */     if (alarmType == null) {
/* 482 */       return null;
/*     */     }
/* 484 */     String str = alarmType; byte b = -1; str.hashCode(); switch (b) {
/*     */     
/* 486 */     }  return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFormattedUserName(String userId) {
/* 491 */     if (userId == null) {
/* 492 */       return null;
/*     */     }
/* 494 */     switch (userId) {
/*     */       case "#HCCE":
/* 496 */         return "機房門禁系統";
/*     */     } 
/* 498 */     return userId;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFormattedLocationName(String location) {
/* 503 */     if (location == null) {
/* 504 */       return null;
/*     */     }
/* 506 */     String str = location; byte b = -1; str.hashCode(); switch (b) {
/*     */     
/* 508 */     }  return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getFormattedOperatorName(String userId) {
/* 513 */     if (userId == null) {
/* 514 */       return null;
/*     */     }
/* 516 */     switch (userId) {
/*     */       case "#HCCE":
/* 518 */         return "機房門禁系統";
/*     */     } 
/* 520 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public static ReportRepositoryTreeNode transferRepositoryTreeNode(Report report) {
/* 525 */     ReportRepositoryTreeNode node = new ReportRepositoryTreeNode();
/* 526 */     node.setId(report.getId());
/* 527 */     node.setName(report.getName());
/* 528 */     node.setRoadMap(report.getRoadMap());
/* 529 */     node.setDepth(ReportRepositoryTreeNode.Depth.LEAF);
/* 530 */     node.setType(ReportRepositoryTreeNode.Type.REPORT);
/* 531 */     node.setRipViewerClass(report.getRipViewerClass());
/* 532 */     node.setCharts(report.getCharts());
/*     */     
/* 534 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReportRepositoryTreeNode transferRepositoryTreeNode(ReportExportFile reportExportFile) {
/* 540 */     ReportRepositoryTreeNode node = new ReportRepositoryTreeNode();
/* 541 */     node.setId(reportExportFile.getId());
/* 542 */     node.setName(reportExportFile.getName());
/* 543 */     node.setDepth(ReportRepositoryTreeNode.Depth.LEAF);
/* 544 */     node.setType(ReportRepositoryTreeNode.Type.EXPORT_FILE);
/* 545 */     node.setRipViewerClass(reportExportFile.getReport().getRipViewerClass());
/* 546 */     ReportFile exportFileDTO = new ReportFile();
/* 547 */     exportFileDTO.setId(reportExportFile.getId());
/* 548 */     exportFileDTO.setSubCategory(reportExportFile.getCategory());
/* 549 */     exportFileDTO.setReportName(reportExportFile.getReport().getName());
/* 550 */     exportFileDTO.setStart(reportExportFile.getStart());
/* 551 */     exportFileDTO.setEnd(reportExportFile.getEnd());
/* 552 */     exportFileDTO.setSource(reportExportFile.getSource());
/* 553 */     switch (reportExportFile.getFormat()) {
/*     */       case PDF:
/* 555 */         exportFileDTO.setReportFormat(ReportFormat.PDF);
/*     */         break;
/*     */       case HTML:
/* 558 */         exportFileDTO.setReportFormat(ReportFormat.HTML);
/*     */         break;
/*     */       case XLS:
/* 561 */         exportFileDTO.setReportFormat(ReportFormat.XLS);
/*     */         break;
/*     */       default:
/* 564 */         exportFileDTO.setReportFormat(null);
/*     */         break;
/*     */     } 
/* 567 */     exportFileDTO.setInputParameter(
/*     */         
/* 569 */         (HashMap)SerializationUtils.deserialize(reportExportFile.getInputParameter()));
/* 570 */     node.setExportFileInfo(exportFileDTO);
/*     */     
/* 572 */     return node;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map<String, PreviewHeaderDTO> transferToPreviewHeaderDTOMap(Map<String, PreviewHeader> previewHeaderMap) {
/* 577 */     if (previewHeaderMap == null) {
/* 578 */       return Collections.emptyMap();
/*     */     }
/*     */     
/* 581 */     Map<String, PreviewHeaderDTO> resultMap = new HashMap<>();
/* 582 */     for (Map.Entry<String, PreviewHeader> e : previewHeaderMap.entrySet()) {
/* 583 */       PreviewHeader previewHeader = e.getValue();
/* 584 */       PreviewHeaderDTO dto = new PreviewHeaderDTO();
/* 585 */       dto.setId(previewHeader.getId());
/* 586 */       dto.setHeader(previewHeader.getHeader());
/* 587 */       dto.setWidth(previewHeader.getWidth());
/* 588 */       resultMap.put(e.getKey(), dto);
/*     */     } 
/* 590 */     return resultMap;
/*     */   }
/*     */   
/*     */   public static List<PreviewDataDTO> transferToPreviewDataDTOs(List<PreviewData> previewDatas) {
/* 594 */     if (previewDatas == null) {
/* 595 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 598 */     List<PreviewDataDTO> resultList = new ArrayList<>();
/* 599 */     for (PreviewData d : previewDatas) {
/* 600 */       PreviewDataDTO dto = new PreviewDataDTO();
/* 601 */       dto.setId(UUID.randomUUID().toString());
/* 602 */       dto.setCol1(d.getCol1());
/* 603 */       dto.setCol2(d.getCol2());
/* 604 */       dto.setCol3(d.getCol3());
/* 605 */       dto.setCol4(d.getCol4());
/* 606 */       dto.setCol5(d.getCol5());
/* 607 */       dto.setCol6(d.getCol6());
/* 608 */       dto.setCol7(d.getCol7());
/* 609 */       dto.setCol8(d.getCol8());
/* 610 */       dto.setCol9(d.getCol9());
/* 611 */       dto.setCol10(d.getCol10());
/* 612 */       dto.setCol11(d.getCol11());
/* 613 */       dto.setCol12(d.getCol12());
/* 614 */       dto.setCol13(d.getCol13());
/* 615 */       dto.setCol14(d.getCol14());
/* 616 */       dto.setCol15(d.getCol15());
/* 617 */       dto.setCol16(d.getCol16());
/* 618 */       dto.setCol17(d.getCol17());
/* 619 */       dto.setCol18(d.getCol18());
/* 620 */       dto.setCol19(d.getCol19());
/* 621 */       dto.setCol20(d.getCol20());
/* 622 */       dto.setCol21(d.getCol21());
/* 623 */       dto.setCol22(d.getCol22());
/* 624 */       dto.setCol23(d.getCol23());
/* 625 */       dto.setCol24(d.getCol24());
/* 626 */       dto.setCol25(d.getCol25());
/* 627 */       dto.setCol26(d.getCol26());
/* 628 */       dto.setCol27(d.getCol27());
/* 629 */       dto.setCol28(d.getCol28());
/* 630 */       dto.setCol29(d.getCol29());
/* 631 */       dto.setCol30(d.getCol30());
/* 632 */       resultList.add(dto);
/*     */     } 
/* 634 */     return resultList;
/*     */   }
/*     */   
/*     */   public static PreviewGridDataDTO transferToPreviewGridDataDTO(PreviewGridData previewGridData) {
/* 638 */     if (previewGridData == null) {
/* 639 */       return new PreviewGridDataDTO();
/*     */     }
/*     */     
/* 642 */     List<PreviewGridHeaderDTO> headers = new ArrayList<>();
/* 643 */     for (PreviewGridHeader header : previewGridData.getHeaders()) {
/* 644 */       PreviewGridHeaderDTO dto = new PreviewGridHeaderDTO();
/* 645 */       dto.setColumnId(header.getColumnId());
/* 646 */       dto.setHeader(header.getHeader());
/* 647 */       dto.setWidth(header.getWidth());
/* 648 */       headers.add(dto);
/*     */     } 
/* 650 */     List<PreviewGridRowDTO> rows = new ArrayList<>();
/* 651 */     for (PreviewGridRow row : previewGridData.getRows()) {
/* 652 */       PreviewGridRowDTO dto = new PreviewGridRowDTO();
/* 653 */       dto.setId(row.getId());
/* 654 */       dto.setColumnValMap(row.getColumnValMap());
/* 655 */       rows.add(dto);
/*     */     } 
/*     */     
/* 658 */     PreviewGridDataDTO result = new PreviewGridDataDTO();
/* 659 */     result.setHeaders(headers);
/* 660 */     result.setRows(rows);
/* 661 */     return result;
/*     */   }
/*     */   
/*     */   public static List<OperatorDTO> transferToOperatorDTOs(List<User> users) {
/* 665 */     if (users == null) {
/* 666 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 669 */     List<OperatorDTO> dtos = new ArrayList<>(users.size());
/* 670 */     for (User user : users) {
/* 671 */       OperatorDTO dto = new OperatorDTO();
/* 672 */       dto.setId(user.getLogin());
/* 673 */       dto.setDisplayName(user.getName());
/* 674 */       dtos.add(dto);
/*     */     } 
/* 676 */     return dtos;
/*     */   }
/*     */   
/*     */   public static List<RoadLineDTO> transferToRoadLineDTO(Collection<RoadLine> roadLines) {
/* 680 */     List<RoadLineDTO> result = new ArrayList<>();
/*     */     
/* 682 */     for (RoadLine road : roadLines) {
/* 683 */       RoadLineDTO dto = new RoadLineDTO();
/* 684 */       dto.setLineId(road.getLineId());
/* 685 */       dto.setLineName(road.getLineName());
/* 686 */       dto.setDirection(road.getDirection());
/* 687 */       result.add(dto);
/*     */     } 
/* 689 */     return result;
/*     */   }
/*     */   
/*     */   public static List<RoadDivisionDTO> transferToRoadDivisionDTO(List<RoadDivision> roadDivisions) {
/* 693 */     List<RoadDivisionDTO> result = new ArrayList<>();
/*     */     
/* 695 */     for (RoadDivision roadDivision : roadDivisions) {
/* 696 */       RoadDivisionDTO dto = new RoadDivisionDTO();
/* 697 */       dto.setDivisionId(roadDivision.getDivisionId());
/* 698 */       dto.setDivisionName(roadDivision.getDivisionName());
/* 699 */       dto.setMileage(roadDivision.getMileage());
/* 700 */       result.add(dto);
/*     */     } 
/* 702 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<ReportExportFileDTO> transferToReportExportFileDTO(List<ReportExportFile> reportExportFiles) {
/* 707 */     List<ReportExportFileDTO> result = new ArrayList<>();
/*     */     
/* 709 */     for (ReportExportFile reportExportFile : reportExportFiles) {
/* 710 */       ReportExportFileDTO dto = new ReportExportFileDTO();
/* 711 */       dto.setId(reportExportFile.getId());
/* 712 */       dto.setCategory(reportExportFile.getCategory());
/* 713 */       dto.setSubCategory(reportExportFile.getSubCategory());
/* 714 */       dto.setName(reportExportFile.getName());
/* 715 */       dto.setFormat(reportExportFile.getFormat().toString());
/* 716 */       dto.setStart(reportExportFile.getStart());
/* 717 */       dto.setEnd(reportExportFile.getEnd());
/* 718 */       dto.setScheduleStart(reportExportFile.getScheduleStart());
/* 719 */       result.add(dto);
/*     */     } 
/* 721 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<ReportConfigDetailDTO> transferToReportConfigDetailDTO(Collection<Report> reports) {
/* 726 */     List<ReportConfigDetailDTO> result = new ArrayList<>();
/*     */     
/* 728 */     for (Report report : reports) {
/* 729 */       ReportConfigDetailDTO dto = new ReportConfigDetailDTO();
/* 730 */       dto.setId(report.getId());
/* 731 */       dto.setReportName(report.getName());
/* 732 */       dto.setEnable(report.isEnable().booleanValue());
/* 733 */       result.add(dto);
/*     */     } 
/* 735 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\a\\util\AoRptValueFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */