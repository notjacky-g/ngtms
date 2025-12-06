/*    */ package com.hwacom.ngtms.hcce.fme.controller.fm;
/*    */ 
/*    */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*    */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class RemoteInterfaceImpl
/*    */   implements RemoteInterface
/*    */ {
/*    */   private FmeMainBase fmeMainBase;
/*    */   
/*    */   public DynamicConfig getDynaConfig(String configName) {
/* 18 */     return this.fmeMainBase.getDynaConfig(configName);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDynaConfig(String key, String value) {
/* 23 */     this.fmeMainBase.setDynaConfig(key, value);
/*    */   }
/*    */   
/*    */   public FmeMainBase getFmeMainBase() {
/* 27 */     return this.fmeMainBase;
/*    */   }
/*    */   
/*    */   public void setFmeMainBase(FmeMainBase fmeMainBase) {
/* 31 */     this.fmeMainBase = fmeMainBase;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInPrimaryGroup() {
/* 36 */     return this.fmeMainBase.isInPrimaryGroup();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addOpLog(String userId, String cpeIp, String subSysName, String deviceName, String description, Date operationTime, OperationResult operationResult, String remark) {
/* 49 */     this.fmeMainBase
/* 50 */       .getOpLogger()
/* 51 */       .addSetLog(userId, cpeIp, subSysName, deviceName, description, operationTime, operationResult, remark);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addOpLog(String userId, String cpeIp, String subSysName, String operationItem, String deviceName, String description, Date operationTime, OperationResult operationResult, String remark) {
/* 73 */     this.fmeMainBase
/* 74 */       .getOpLogger()
/* 75 */       .addLog(userId, cpeIp, subSysName, operationItem, deviceName, description, operationTime, operationResult, remark);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\controller\fm\RemoteInterfaceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */