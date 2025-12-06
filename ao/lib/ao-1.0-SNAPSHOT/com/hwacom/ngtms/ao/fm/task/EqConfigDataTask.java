/*    */ package com.hwacom.ngtms.ao.fm.task;
/*    */ 
/*    */ import com.hwacom.ngtms.ao.fm.service.EqConfigDataService;
/*    */ import com.hwacom.ngtms.hcce.fme.controller.fm.JobBase;
/*    */ import org.quartz.JobDataMap;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ public class EqConfigDataTask
/*    */   extends JobBase {
/* 12 */   private static final Logger logger = LoggerFactory.getLogger(EqConfigDataTask.class);
/*    */   
/*    */   @Autowired
/*    */   EqConfigDataService eqConfigDataService;
/*    */ 
/*    */   
/*    */   protected void init(JobDataMap jobDataMap) {}
/*    */   
/*    */   protected void process() {
/* 21 */     logger.debug("EqConfigDataTask.");
/* 22 */     this.eqConfigDataService.process();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\task\EqConfigDataTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */