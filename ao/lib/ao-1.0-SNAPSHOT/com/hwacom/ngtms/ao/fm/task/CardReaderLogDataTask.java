/*    */ package com.hwacom.ngtms.ao.fm.task;
/*    */ 
/*    */ import com.hwacom.ngtms.ao.fm.service.CardReaderLogDataService;
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
/*    */ public class CardReaderLogDataTask
/*    */   extends JobBase
/*    */ {
/* 20 */   private static final Logger logger = LoggerFactory.getLogger(CardReaderLogDataTask.class);
/*    */   
/*    */   @Autowired
/*    */   CardReaderLogDataService cardReaderLogDataService;
/*    */ 
/*    */   
/*    */   protected void init(JobDataMap jobDataMap) {}
/*    */   
/*    */   protected void process() {
/* 29 */     long startTime = System.currentTimeMillis();
/* 30 */     logger.debug("Start CardReaderLogDataTask, {}", Long.valueOf(startTime));
/* 31 */     this.cardReaderLogDataService.process();
/* 32 */     logger.debug("CardReaderLogDataTask end, use TimeMillis '{}'", 
/* 33 */         Long.valueOf(System.currentTimeMillis() - startTime));
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\task\CardReaderLogDataTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */