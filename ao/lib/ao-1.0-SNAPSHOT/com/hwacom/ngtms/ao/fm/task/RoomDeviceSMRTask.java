/*    */ package com.hwacom.ngtms.ao.fm.task;
/*    */ 
/*    */ import com.hwacom.ngtms.ao.fm.service.RoomDeviceSMRServcie;
/*    */ import com.hwacom.ngtms.hcce.fme.controller.fm.JobBase;
/*    */ import org.quartz.DisallowConcurrentExecution;
/*    */ import org.quartz.JobDataMap;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @DisallowConcurrentExecution
/*    */ public class RoomDeviceSMRTask
/*    */   extends JobBase
/*    */ {
/* 20 */   private static final Logger logger = LoggerFactory.getLogger(RoomDeviceSMRTask.class);
/*    */   
/*    */   @Autowired
/*    */   RoomDeviceSMRServcie roomDeviceSMRServcie;
/*    */   
/*    */   protected void init(JobDataMap jobDataMap) {}
/*    */   
/*    */   protected void process() {
/* 28 */     long startTime = System.currentTimeMillis();
/* 29 */     logger.debug("Start RoomDeviceSMRServcie, {}", Long.valueOf(startTime));
/*    */     try {
/* 31 */       this.roomDeviceSMRServcie.process();
/* 32 */     } catch (Exception e) {
/* 33 */       logger.debug("RoomDeviceSMRServcie failed.");
/*    */     } 
/* 35 */     logger.debug("RoomDeviceSMRServcie end, use TimeMillis '{}'", 
/* 36 */         Long.valueOf(System.currentTimeMillis() - startTime));
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\task\RoomDeviceSMRTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */