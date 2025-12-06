/*    */ package com.hwacom.ngtms.ao.fm.task;
/*    */ 
/*    */ import com.hwacom.ngtms.ao.fm.service.AoAlarmService;
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
/*    */ public class AutoConfirmRtnAlarmTask
/*    */   extends JobBase
/*    */ {
/* 21 */   private static final Logger logger = LoggerFactory.getLogger(AutoConfirmRtnAlarmTask.class);
/*    */   
/*    */   @Autowired
/*    */   private AoAlarmService aoAlarmService;
/*    */ 
/*    */   
/*    */   protected void init(JobDataMap jobDataMap) {}
/*    */   
/*    */   protected void process() {
/* 30 */     long startTime = System.currentTimeMillis();
/* 31 */     logger.debug("Start AutoConfirmRtnAlarmTask, {}", Long.valueOf(startTime));
/*    */     try {
/* 33 */       this.aoAlarmService.confirmAllUnackRtnAlarm();
/* 34 */     } catch (Exception e) {
/* 35 */       logger.error("AutoConfirmRtnAlarmTask failed.", e);
/*    */     } 
/* 37 */     logger.debug("AutoConfirmRtnAlarmTask end, use TimeMillis '{}'", 
/* 38 */         Long.valueOf(System.currentTimeMillis() - startTime));
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\task\AutoConfirmRtnAlarmTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */