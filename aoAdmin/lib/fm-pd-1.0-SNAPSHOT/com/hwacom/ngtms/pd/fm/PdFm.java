/*    */ package com.hwacom.ngtms.pd.fm;
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
/*    */ 
/*    */ public class PdFm
/*    */   extends FmeMainBase
/*    */ {
/* 18 */   private static Logger logger = LoggerFactory.getLogger(PdFm.class);
/*    */ 
/*    */   
/*    */   static {
/* 22 */     setDynamicConfigDeclares(new DynamicConfigDeclare[] { new DynamicConfigDeclare("addedPdDeviceType", "VD,ETAG,AVI,CMS,CSLS,LCS,CCTV") });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PdFm(String name, String description) {
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


/* Location:              C:\User\\user\Desktop\lib\fm-pd-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\pd\fm\PdFm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */