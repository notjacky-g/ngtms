/*    */ package com.hwacom.ngtms.ao.fm.task;
/*    */ 
/*    */ import com.hwacom.ngtms.ao.fm.service.EnvironmentalControlAlarmChecker;
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
/*    */ 
/*    */ 
/*    */ public class RoomEnvironmentalControlAlarmTask
/*    */   extends JobBase
/*    */ {
/* 20 */   private static final Logger logger = LoggerFactory.getLogger(RoomEnvironmentalControlAlarmTask.class);
/*    */   
/*    */   @Autowired
/*    */   private EnvironmentalControlAlarmChecker environmentalControlAlarmChecker;
/*    */ 
/*    */   
/*    */   protected void init(JobDataMap jobDataMap) {}
/*    */   
/*    */   protected void process() {
/* 29 */     long startTime = System.currentTimeMillis();
/* 30 */     logger.debug("Start RoomEnvironmentalControlAlarmTask, {}", Long.valueOf(startTime));
/*    */     try {
/* 32 */       this.environmentalControlAlarmChecker.check();
/* 33 */     } catch (Exception e) {
/* 34 */       logger.error("RoomEnvironmentalControlAlarmTask failed.", e);
/*    */     } 
/* 36 */     logger.debug("RoomEnvironmentalControlAlarmTask end, use TimeMillis '{}'", 
/*    */         
/* 38 */         Long.valueOf(System.currentTimeMillis() - startTime));
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\task\RoomEnvironmentalControlAlarmTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */