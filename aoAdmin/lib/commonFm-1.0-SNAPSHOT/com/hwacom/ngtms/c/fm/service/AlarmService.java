/*     */ package com.hwacom.ngtms.c.fm.service;
/*     */ 
/*     */ import com.hwacom.ngtms.alarm.fm.shared.AlarmClassification;
/*     */ import com.hwacom.ngtms.alarm.fm.shared.AlarmLogContext;
/*     */ import com.hwacom.ngtms.alarm.util.AlarmUtils;
/*     */ import com.hwacom.ngtms.c.shared.AlarmLogEventContext;
/*     */ import com.hwacom.ngtms.c.shared.AlarmSource;
/*     */ import com.hwacom.ngtms.c.shared.Direction;
/*     */ import java.util.Date;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class AlarmService
/*     */ {
/*     */   public void sendAlarmAutoSid(AlarmSource alarmSource, Integer alarmSubType, Date timestamp, String deviceName, Integer degree, String lineId, String sectionId, Direction direction, Integer startMileage, Integer endMileage, String blockLaneMark) {
/*  33 */     sendAlarm(alarmSource, alarmSubType, deviceName + "." + alarmSubType, timestamp, deviceName, degree, lineId, sectionId, direction, startMileage, endMileage, blockLaneMark);
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
/*     */   public void sendAlarm(AlarmSource alarmSource, Integer alarmSubType, String sessionId, Date timestamp, String deviceName, Integer degree, String lineId, String sectionId, Direction direction, Integer startMileage, Integer endMileage, String blockLaneMark) {
/*  61 */     AlarmLogEventContext contextData = new AlarmLogEventContext();
/*  62 */     contextData.setDeviceName(deviceName);
/*  63 */     contextData.setNotifySource(Long.valueOf(alarmSource.getId().intValue()));
/*  64 */     contextData.setLineId(lineId);
/*  65 */     contextData.setSectionId(sectionId);
/*  66 */     contextData.setDirection(direction);
/*  67 */     contextData.setStartMileage(startMileage);
/*  68 */     contextData.setEndMileage(endMileage);
/*  69 */     contextData.setBlockLaneMark(blockLaneMark);
/*  70 */     AlarmUtils.sendAlarmLogDTO(AlarmClassification.EVENT, 
/*     */         
/*  72 */         String.valueOf(alarmSubType), deviceName, sessionId, timestamp, degree, (AlarmLogContext)contextData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendAlarmWithoutContext(Integer alarmSubType, String alarmSessionId, Date timestamp, Integer degree) {
/*  82 */     AlarmUtils.sendAlarmLogDTOWithoutContext(AlarmClassification.EVENT, 
/*     */         
/*  84 */         String.valueOf(alarmSubType), null, alarmSessionId, timestamp, degree);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendAlarmWithMemo(AlarmSource alarmSource, Integer alarmSubType, String sessionId, Date timestamp, String deviceName, Integer degree, String lineId, String sectionId, Direction direction, Integer startMileage, Integer endMileage, String blockLaneMark, String memo) {
/* 105 */     AlarmLogEventContext contextData = new AlarmLogEventContext();
/* 106 */     contextData.setDeviceName(deviceName);
/* 107 */     contextData.setNotifySource(Long.valueOf(alarmSource.getId().intValue()));
/* 108 */     contextData.setLineId(lineId);
/* 109 */     contextData.setSectionId(sectionId);
/* 110 */     contextData.setDirection(direction);
/* 111 */     contextData.setStartMileage(startMileage);
/* 112 */     contextData.setEndMileage(endMileage);
/* 113 */     contextData.setBlockLaneMark(blockLaneMark);
/* 114 */     contextData.setMemo(memo);
/* 115 */     AlarmUtils.sendAlarmLogDTO(AlarmClassification.EVENT, 
/*     */         
/* 117 */         String.valueOf(alarmSubType), deviceName, sessionId, timestamp, degree, (AlarmLogContext)contextData);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\service\AlarmService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */