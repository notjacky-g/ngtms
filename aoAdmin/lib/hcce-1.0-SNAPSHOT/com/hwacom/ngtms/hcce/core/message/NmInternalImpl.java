/*    */ package com.hwacom.ngtms.hcce.core.message;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.core.NodeManager;
/*    */ import com.hwacom.ngtms.hcce.core.exception.FmeOperationException;
/*    */ import com.hwacom.ngtms.hcce.core.exception.NodeManagerNotReadyException;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class NmInternalImpl
/*    */   implements NmInternal
/*    */ {
/* 21 */   private static Logger logger = LoggerFactory.getLogger(NmInternalImpl.class);
/*    */   
/*    */   @Autowired
/*    */   private NodeManager nodeManager;
/*    */   
/*    */   public void stopNode() {
/* 27 */     logger.info("Stop Node from remote");
/* 28 */     this.nodeManager.stop();
/*    */   }
/*    */ 
/*    */   
/*    */   public void startNode() {
/* 33 */     logger.info("Start Node from remote");
/* 34 */     this.nodeManager.start();
/*    */   }
/*    */ 
/*    */   
/*    */   public void restartNode() {
/* 39 */     logger.info("Restart Node from remote");
/* 40 */     this.nodeManager.restart();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void addFme(String fmeName, String className, String description) throws FmeOperationException, NodeManagerNotReadyException {
/* 46 */     logger.info("Add FME from remote, fmeName={}, className={}, description={}", new Object[] { fmeName, className, description });
/*    */ 
/*    */     
/* 49 */     this.nodeManager.addFme(fmeName, className, description);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startFme(String fmeName) throws NodeManagerNotReadyException, FmeOperationException {
/* 60 */     logger.info("Start FME from remote, fmeName={}", fmeName);
/* 61 */     this.nodeManager.startFme(fmeName);
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeFme(String fmeName) throws NodeManagerNotReadyException {
/* 66 */     logger.info("Remove FME from remote, fmeName={}", fmeName);
/* 67 */     this.nodeManager.removeFme(fmeName);
/*    */   }
/*    */ 
/*    */   
/*    */   public NodeManager.NmState getNmState() {
/* 72 */     return this.nodeManager.getState();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\core\message\NmInternalImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */