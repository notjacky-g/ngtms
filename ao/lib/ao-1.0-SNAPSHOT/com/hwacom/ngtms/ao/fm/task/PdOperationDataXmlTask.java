/*    */ package com.hwacom.ngtms.ao.fm.task;
/*    */ 
/*    */ import com.hwacom.ngtms.ao.fm.service.PdOperationDataProcessor;
/*    */ import com.hwacom.ngtms.hcce.fme.controller.fm.JobBase;
/*    */ import org.quartz.JobDataMap;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ public class PdOperationDataXmlTask
/*    */   extends JobBase {
/* 12 */   private static final Logger logger = LoggerFactory.getLogger(PdOperationDataXmlTask.class);
/*    */   
/*    */   @Autowired
/*    */   private PdOperationDataProcessor pdOperationDataProcessor;
/*    */ 
/*    */   
/*    */   protected void process() {
/* 19 */     logger.debug("PdOperationDataXmlTask start");
/* 20 */     this.pdOperationDataProcessor.process();
/* 21 */     logger.debug("PdOperationDataXmlTask end");
/*    */   }
/*    */   
/*    */   protected void init(JobDataMap dataMap) {}
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\task\PdOperationDataXmlTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */