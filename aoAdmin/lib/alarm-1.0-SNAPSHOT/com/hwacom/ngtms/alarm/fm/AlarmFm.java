/*    */ package com.hwacom.ngtms.alarm.fm;
/*    */ 
/*    */ import com.hwacom.ngtms.alarm.fm.service.AlarmReceiveProcessor;
/*    */ import com.hwacom.ngtms.hcce.core.exception.FmException;
/*    */ import com.hwacom.ngtms.hcce.fme.controller.fm.FmeMainBase;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AlarmFm
/*    */   extends FmeMainBase
/*    */ {
/* 18 */   private static final Logger logger = LoggerFactory.getLogger(AlarmFm.class);
/*    */   @Autowired
/*    */   private AlarmReceiveProcessor alarmReceiveProcessor;
/*    */   
/*    */   public AlarmFm(String fmeName, String description) {
/* 23 */     super(fmeName, description);
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() throws FmException {
/* 28 */     logger.info("{} init", getFmeName());
/*    */     try {
/* 30 */       this.alarmReceiveProcessor.start();
/* 31 */     } catch (Exception e) {
/* 32 */       logger.error("alarmReceiveProcessor initinalize failed", e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runFm() throws Exception {
/* 42 */     logger.info("{} started", getFmeName());
/* 43 */     Runnable listenerRemover = null;
/*    */     try {
/* 45 */       keepRunning();
/* 46 */     } catch (Exception e) {
/* 47 */       logger.error("scheduleJob Error", e);
/*    */     } finally {
/* 49 */       logger.info("{} stopped", getFmeName());
/*    */       try {
/* 51 */         this.alarmReceiveProcessor.stop();
/* 52 */       } catch (Exception ex) {
/* 53 */         logger.error("Failed to stop alarm receiver", ex);
/*    */       } 
/* 55 */       if (listenerRemover != null) {
/* 56 */         listenerRemover.run();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   private void keepRunning() {
/* 62 */     while (checkFmKeepRunning()) {
/*    */       try {
/* 64 */         Thread.sleep(5000L);
/* 65 */       } catch (InterruptedException interruptedException) {}
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAllowConcurrentExecution() {
/* 72 */     return false;
/*    */   }
/*    */   
/*    */   public void startTesting() throws FmException {}
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\alarm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\alarm\fm\AlarmFm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */