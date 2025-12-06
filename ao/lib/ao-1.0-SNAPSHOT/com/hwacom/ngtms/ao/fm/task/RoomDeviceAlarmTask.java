/*    */ package com.hwacom.ngtms.ao.fm.task;
/*    */ 
/*    */ import com.hwacom.ngtms.ao.fm.service.NcuOfflineAlarmChecker;
/*    */ import com.hwacom.ngtms.ao.fm.service.PdOfflineAlarmChecker;
/*    */ import com.hwacom.ngtms.ao.fm.service.RtuOfflineAlarmChecker;
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
/*    */ 
/*    */ @DisallowConcurrentExecution
/*    */ public class RoomDeviceAlarmTask
/*    */   extends JobBase
/*    */ {
/* 23 */   private static final Logger logger = LoggerFactory.getLogger(RoomDeviceAlarmTask.class);
/*    */   
/*    */   @Autowired
/*    */   private NcuOfflineAlarmChecker ncuOfflineAlarmChecker;
/*    */   
/*    */   @Autowired
/*    */   private RtuOfflineAlarmChecker rtuOfflineAlarmChecker;
/*    */   @Autowired
/*    */   private PdOfflineAlarmChecker pdOfflineAlarmChecker;
/*    */   
/*    */   protected void init(JobDataMap jobDataMap) {}
/*    */   
/*    */   protected void process() {
/* 36 */     long startTime = System.currentTimeMillis();
/* 37 */     logger.debug("Start RoomDeviceAlarmTask, {}", Long.valueOf(startTime));
/*    */     try {
/* 39 */       this.ncuOfflineAlarmChecker.check();
/* 40 */       this.rtuOfflineAlarmChecker.check();
/* 41 */       this.pdOfflineAlarmChecker.check();
/* 42 */     } catch (Exception e) {
/* 43 */       logger.error("RoomDeviceAlarmTask failed.", e);
/*    */     } 
/* 45 */     logger.debug("RoomDeviceAlarmTask end, use TimeMillis '{}'", 
/* 46 */         Long.valueOf(System.currentTimeMillis() - startTime));
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\task\RoomDeviceAlarmTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */