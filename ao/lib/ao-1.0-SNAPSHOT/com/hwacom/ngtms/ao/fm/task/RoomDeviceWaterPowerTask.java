/*    */ package com.hwacom.ngtms.ao.fm.task;
/*    */ 
/*    */ import com.hwacom.ngtms.ao.fm.service.RoomDeviceWaterPowerServcie;
/*    */ import com.hwacom.ngtms.hcce.fme.controller.fm.JobBase;
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
/*    */ public class RoomDeviceWaterPowerTask
/*    */   extends JobBase
/*    */ {
/* 18 */   private static final Logger logger = LoggerFactory.getLogger(RoomDeviceWaterPowerTask.class);
/*    */   
/*    */   @Autowired
/*    */   RoomDeviceWaterPowerServcie roomDeviceWaterPowerServcie;
/*    */   
/*    */   protected void init(JobDataMap jobDataMap) {}
/*    */   
/*    */   protected void process() {
/* 26 */     long startTime = System.currentTimeMillis();
/* 27 */     logger.debug("Start RoomDeviceWaterPowerServcie, {}", Long.valueOf(startTime));
/*    */     try {
/* 29 */       this.roomDeviceWaterPowerServcie.process();
/* 30 */     } catch (Exception e) {
/* 31 */       logger.debug("RoomDeviceWaterPowerServcie failed.");
/*    */     } 
/* 33 */     logger.debug("RoomDeviceWaterPowerServcie end, use TimeMillis '{}'", 
/*    */         
/* 35 */         Long.valueOf(System.currentTimeMillis() - startTime));
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\task\RoomDeviceWaterPowerTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */