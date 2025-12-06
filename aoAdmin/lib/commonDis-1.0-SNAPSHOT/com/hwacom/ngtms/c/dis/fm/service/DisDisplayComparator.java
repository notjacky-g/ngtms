/*    */ package com.hwacom.ngtms.c.dis.fm.service;
/*    */ 
/*    */ import com.hwacom.ngtms.c.dis.fm.model.DisDisplayChangeLog;
/*    */ import com.hwacom.ngtms.c.dis.fm.model.DisMessageCompareLog;
/*    */ import com.hwacom.ngtms.c.dis.fm.repository.DisDisplayChangeLogRepository;
/*    */ import com.hwacom.ngtms.c.dis.fm.repository.DisMessageCompareLogRepository;
/*    */ import com.hwacom.ngtms.c.dis.util.DisUtility;
/*    */ import com.hwacom.ngtms.c.shared.DisplayMatch;
/*    */ import java.util.Date;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DisDisplayComparator
/*    */ {
/* 23 */   protected static final Logger logger = LoggerFactory.getLogger(DisDisplayComparator.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Autowired
/*    */   protected DisMessageCompareLogRepository disMessageCompareLogRepository;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Autowired
/*    */   protected DisDisplayChangeLogRepository disDisplayChangeLogRepository;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void saveCompareResult(String deviceName, String deviceType, Integer boardId, String commandMessage, String deviceDisplay, boolean changeLog) {
/* 44 */     boolean isDisplayContentMatch = commandMessage.equals(deviceDisplay);
/* 45 */     if (!isDisplayContentMatch) {
/*    */       
/* 47 */       DisMessageCompareLog log = new DisMessageCompareLog();
/* 48 */       log.setDeviceType(deviceType);
/* 49 */       log.setDataTime(new Date());
/* 50 */       log.setCommandMessage(commandMessage);
/* 51 */       log.setDeviceDisplay(deviceDisplay);
/* 52 */       log.setDeviceName(deviceName);
/* 53 */       log.setIsDisplayContentMatch(isDisplayContentMatch ? DisplayMatch.MATCH : DisplayMatch.NOT_MATCH);
/*    */       
/*    */       try {
/* 56 */         this.disMessageCompareLogRepository.save(log);
/* 57 */       } catch (Exception e) {
/* 58 */         logger.error("disMessageCompareLogRepository save error", e);
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 63 */     DisUtility.updateDeviceStatus(deviceName, deviceDisplay, isDisplayContentMatch);
/*    */     
/* 65 */     if (changeLog) {
/*    */       
/* 67 */       DisDisplayChangeLog disDisplayChangeLog = new DisDisplayChangeLog();
/* 68 */       disDisplayChangeLog.setDeviceType(deviceType);
/* 69 */       disDisplayChangeLog.setDataTime(new Date());
/* 70 */       disDisplayChangeLog.setBoardId(boardId);
/* 71 */       disDisplayChangeLog.setDeviceName(deviceName);
/* 72 */       disDisplayChangeLog.setDeviceMessage(deviceDisplay);
/*    */       
/*    */       try {
/* 75 */         this.disDisplayChangeLogRepository.save(disDisplayChangeLog);
/* 76 */       } catch (Exception e) {
/* 77 */         logger.error("disDisplayChangeLogRepository save error", e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\service\DisDisplayComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */