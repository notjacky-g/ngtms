/*    */ package com.hwacom.ngtms.ao.fm.task;
/*    */ 
/*    */ import com.hwacom.ngtms.ao.fm.service.RoomDeviceStatusService;
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
/*    */ public class RoomDeviceStatusTask
/*    */   extends JobBase
/*    */ {
/* 20 */   private static final Logger logger = LoggerFactory.getLogger(RoomDeviceStatusTask.class);
/*    */   
/*    */   @Autowired
/*    */   RoomDeviceStatusService roomDeviceStatusService;
/*    */   
/*    */   protected void init(JobDataMap jobDataMap) {}
/*    */   
/*    */   protected void process() {
/* 28 */     long startTime = System.currentTimeMillis();
/* 29 */     logger.debug("Start RoomDeviceStatusTask, {}", Long.valueOf(startTime));
/*    */     try {
/* 31 */       this.roomDeviceStatusService.process();
/* 32 */     } catch (Exception e) {
/* 33 */       logger.debug("RoomDeviceStatusTask failed.");
/*    */     } 
/* 35 */     logger.debug("RoomDeviceStatusTask end, use TimeMillis '{}'", 
/* 36 */         Long.valueOf(System.currentTimeMillis() - startTime));
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\task\RoomDeviceStatusTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */