/*    */ package com.hwacom.ngtms.ccs.fm;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.core.exception.FmException;
/*    */ import com.hwacom.ngtms.hcce.fme.controller.fm.FmeMainBase;
/*    */ import com.hwacom.ngtms.hcce.shared.DynamicConfigDeclare;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CcsFm
/*    */   extends FmeMainBase
/*    */ {
/* 17 */   private static Logger logger = LoggerFactory.getLogger(CcsFm.class);
/*    */   
/*    */   public static final String MAX_ALARM_KEEP_TIME = "MaxAlarmKeepTime";
/*    */   public static final long defaultMaxAlarmKeepTime = 2L;
/*    */   
/*    */   static {
/* 23 */     setDynamicConfigDeclares(new DynamicConfigDeclare[] { new DynamicConfigDeclare("MaxAlarmKeepTime", 
/*    */             
/* 25 */             Long.toString(2L)) });
/*    */   }
/*    */ 
/*    */   
/*    */   public CcsFm(String name, String description) {
/* 30 */     super(name, description);
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() throws FmException {}
/*    */ 
/*    */   
/*    */   public boolean isAllowConcurrentExecution() {
/* 38 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runFm() throws Exception {
/*    */     try {
/* 47 */       logger.info("{} started", getFmeName());
/*    */       
/* 49 */       while (checkFmKeepRunning()) {
/*    */         try {
/* 51 */           Thread.sleep(5000L);
/* 52 */         } catch (InterruptedException ex) {}
/*    */       }
/*    */     
/*    */     } finally {
/*    */       
/* 57 */       logger.info("{} stopped", getFmeName());
/*    */     } 
/*    */   }
/*    */   
/*    */   public void startTesting() throws FmException {}
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\fm-ccs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ccs\fm\CcsFm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */