/*    */ package com.hwacom.ngtms.common.fm;
/*    */ 
/*    */ import com.hazelcast.core.ITopic;
/*    */ import com.hazelcast.core.Message;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import com.hwacom.ngtms.common.fm.hz.CommonHzTopic;
/*    */ import com.hwacom.ngtms.common.fm.service.AccountService;
/*    */ import com.hwacom.ngtms.common.fm.task.AccountTask;
/*    */ import com.hwacom.ngtms.common.shared.AccessDeniedInfo;
/*    */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*    */ import com.hwacom.ngtms.hcce.core.exception.FmException;
/*    */ import com.hwacom.ngtms.hcce.fme.controller.fm.FmeMainBase;
/*    */ import com.hwacom.ngtms.hcce.shared.DynamicConfigDeclare;
/*    */ import org.quartz.SchedulerException;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AccountFm
/*    */   extends FmeMainBase
/*    */ {
/* 26 */   private static final Logger logger = LoggerFactory.getLogger(AccountFm.class);
/*    */   
/*    */   private static final String ACCOUNT_TASK_CRON = "accountTaskCron";
/*    */   @Autowired
/*    */   private AccountService accountService;
/*    */   
/*    */   static {
/* 33 */     setDynamicConfigDeclares(new DynamicConfigDeclare[] { new DynamicConfigDeclare("accountTaskCron", "0 0 0/8 * * ?") });
/*    */   }
/*    */ 
/*    */   
/*    */   public AccountFm(String fmeName, String description) {
/* 38 */     super(fmeName, description);
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() throws FmException {
/* 43 */     logger.info("{} init", getFmeName());
/*    */     try {
/* 45 */       addDyanConfigListener(new String[] { "accountTaskCron" });
/* 46 */     } catch (Exception e) {
/* 47 */       throw new FmException("Failed to initinalize " + getFmeName(), e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onDynaConfigUpdated(String key, DynamicConfig dynamicConfig) {
/* 53 */     if ("accountTaskCron".equals(key)) {
/* 54 */       rescheduleJob(key, dynamicConfig.getValue());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void runFm() throws Exception {
/* 60 */     logger.info("{} started", getFmeName());
/* 61 */     ITopic<AccessDeniedInfo> topic = HzUtils.getTopic((HzDistObjEnum)CommonHzTopic.ACCOUNT_ACCESS_DENIED);
/* 62 */     String registrationId = null;
/*    */     try {
/* 64 */       scheduleJob();
/*    */       
/* 66 */       registrationId = topic.addMessageListener(message -> this.accountService.notifyAccessDenied((AccessDeniedInfo)message.getMessageObject()));
/*    */       
/* 68 */       keepRunning();
/* 69 */     } catch (Exception e) {
/* 70 */       logger.error("scheduleJob Error", e);
/*    */     } finally {
/* 72 */       logger.info("{} stopped", getFmeName());
/* 73 */       if (registrationId != null) {
/* 74 */         topic.removeMessageListener(registrationId);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   private void scheduleJob() throws SchedulerException {
/* 80 */     scheduleReschedulableJob("accountTaskCron", AccountTask.class, null, 
/* 81 */         getDynaConfig("accountTaskCron").getValue());
/*    */   }
/*    */   
/*    */   private void keepRunning() {
/* 85 */     while (checkFmKeepRunning()) {
/*    */       try {
/* 87 */         Thread.sleep(5000L);
/* 88 */       } catch (InterruptedException interruptedException) {}
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAllowConcurrentExecution() {
/* 95 */     return false;
/*    */   }
/*    */   
/*    */   public void startTesting() throws FmException {}
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\AccountFm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */