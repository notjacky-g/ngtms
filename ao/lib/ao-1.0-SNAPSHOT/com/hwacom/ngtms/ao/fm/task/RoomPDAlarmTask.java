/*    */ package com.hwacom.ngtms.ao.fm.task;
/*    */ 
/*    */ import com.hwacom.ngtms.ao.fm.service.PdStatusAlarmChecker;
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
/*    */ public class RoomPDAlarmTask
/*    */   extends JobBase
/*    */ {
/* 21 */   private static final Logger logger = LoggerFactory.getLogger(RoomPDAlarmTask.class);
/*    */   
/*    */   @Autowired
/*    */   private PdStatusAlarmChecker pdStatusAlarmChecker;
/*    */ 
/*    */   
/*    */   protected void init(JobDataMap jobDataMap) {}
/*    */   
/*    */   protected void process() {
/* 30 */     long startTime = System.currentTimeMillis();
/* 31 */     logger.debug("Start RoomPDAlarmTask, {}", Long.valueOf(startTime));
/*    */     try {
/* 33 */       this.pdStatusAlarmChecker.check();
/* 34 */     } catch (Exception e) {
/* 35 */       logger.error("RoomPDAlarmTask failed.", e);
/*    */     } 
/* 37 */     logger.debug("RoomPDAlarmTask end, use TimeMillis '{}'", 
/* 38 */         Long.valueOf(System.currentTimeMillis() - startTime));
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\task\RoomPDAlarmTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */