/*    */ package com.hwacom.ngtms.rtu.fm;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*    */ import com.hwacom.ngtms.hcce.core.exception.FmException;
/*    */ import com.hwacom.ngtms.hcce.fme.controller.fm.FmeMainBase;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RtuFm
/*    */   extends FmeMainBase
/*    */ {
/*    */   static {
/* 20 */     setDynamicConfigDeclares(new com.hwacom.ngtms.hcce.shared.DynamicConfigDeclare[0]);
/*    */   }
/*    */   
/* 23 */   private static Logger logger = LoggerFactory.getLogger(RtuFm.class);
/*    */   
/*    */   public RtuFm(String fmeName, String description) {
/* 26 */     super(fmeName, description);
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() throws FmException {
/* 31 */     logger.info("{} init", getFmeName());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runFm() throws Exception {
/* 38 */     logger.info("{} started", getFmeName());
/*    */     
/*    */     try {
/* 41 */       while (checkFmKeepRunning()) {
/*    */         try {
/* 43 */           Thread.sleep(5000L);
/* 44 */         } catch (InterruptedException ex) {}
/*    */       } 
/*    */     } finally {
/*    */ 
/*    */       
/*    */       try {
/* 50 */         unbindFmRmiService();
/* 51 */       } catch (Exception ex) {
/* 52 */         logger.error("Failed to unbind FM RMI Service", ex);
/*    */       } 
/* 54 */       logger.info("{} stopped", getFmeName());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onDynaConfigUpdated(String key, DynamicConfig dynamicConfig) {
/* 60 */     rescheduleJob(key, dynamicConfig.getValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAllowConcurrentExecution() {
/* 65 */     return false;
/*    */   }
/*    */   
/*    */   public void startTesting() throws FmException {}
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\fm-rtu-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\rtu\fm\RtuFm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */