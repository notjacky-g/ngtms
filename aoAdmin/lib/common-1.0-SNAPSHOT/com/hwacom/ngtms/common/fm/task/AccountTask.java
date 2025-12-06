/*    */ package com.hwacom.ngtms.common.fm.task;
/*    */ 
/*    */ import com.hwacom.ngtms.common.fm.service.AccountService;
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
/*    */ public class AccountTask
/*    */   extends JobBase
/*    */ {
/* 18 */   private static final Logger logger = LoggerFactory.getLogger(AccountTask.class);
/*    */   
/*    */   @Autowired
/*    */   private AccountService accountService;
/*    */ 
/*    */   
/*    */   protected void init(JobDataMap jobDataMap) {}
/*    */   
/*    */   protected void process() {
/* 27 */     logger.debug("Start account task.");
/* 28 */     this.accountService.validateAccount();
/* 29 */     logger.debug("End account task.");
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\task\AccountTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */