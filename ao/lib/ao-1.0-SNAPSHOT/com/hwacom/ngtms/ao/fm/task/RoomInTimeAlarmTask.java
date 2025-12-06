/*    */ package com.hwacom.ngtms.ao.fm.task;
/*    */ 
/*    */ import com.hwacom.ngtms.ao.fm.service.DoorOpenAlarmChecker;
/*    */ import com.hwacom.ngtms.ao.fm.service.EnvironmentalControlAlarmTypeChecker;
/*    */ import com.hwacom.ngtms.ao.fm.service.SecurityReleaseAlarmChecker;
/*    */ import com.hwacom.ngtms.ao.fm.service.SoundLightSartAlarmChecker;
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
/*    */ public class RoomInTimeAlarmTask
/*    */   extends JobBase
/*    */ {
/* 22 */   private static final Logger logger = LoggerFactory.getLogger(RoomInTimeAlarmTask.class);
/*    */   
/*    */   @Autowired
/*    */   private SecurityReleaseAlarmChecker securityReleaseAlarmChecker;
/*    */   
/*    */   @Autowired
/*    */   private SoundLightSartAlarmChecker soundLightSartAlarmChecker;
/*    */   @Autowired
/*    */   private DoorOpenAlarmChecker doorOpenAlarmChecker;
/*    */   @Autowired
/*    */   private EnvironmentalControlAlarmTypeChecker environmentalControlAlarmTypeChecker;
/*    */   
/*    */   protected void init(JobDataMap jobDataMap) {}
/*    */   
/*    */   protected void process() {
/* 37 */     long startTime = System.currentTimeMillis();
/* 38 */     logger.debug("Start RoomInTimeAlarmTask, {}", Long.valueOf(startTime));
/*    */     try {
/* 40 */       this.securityReleaseAlarmChecker.check();
/* 41 */       this.soundLightSartAlarmChecker.check();
/* 42 */       this.doorOpenAlarmChecker.check();
/* 43 */       this.environmentalControlAlarmTypeChecker.check();
/* 44 */     } catch (Exception e) {
/* 45 */       logger.error("RoomInTimeAlarmTask failed.", e);
/*    */     } 
/* 47 */     logger.debug("RoomInTimeAlarmTask end, use TimeMillis '{}'", 
/* 48 */         Long.valueOf(System.currentTimeMillis() - startTime));
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\task\RoomInTimeAlarmTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */