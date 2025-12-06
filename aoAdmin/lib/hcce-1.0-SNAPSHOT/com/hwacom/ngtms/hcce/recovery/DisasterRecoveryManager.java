/*    */ package com.hwacom.ngtms.hcce.recovery;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.core.exception.DisasterRecoveryException;
/*    */ import com.hwacom.ngtms.hcce.recovery.model.HcceGroupInfo;
/*    */ import com.hwacom.ngtms.hcce.recovery.repository.HcceGroupInfoRepository;
/*    */ import com.hwacom.ngtms.hcce.shared.ClusterMode;
/*    */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class DisasterRecoveryManager
/*    */ {
/* 22 */   private static Logger logger = LoggerFactory.getLogger(DisasterRecoveryManager.class);
/*    */   
/*    */   private HcceGroupInfo defaultHcceGroupInfo;
/*    */ 
/*    */   
/*    */   public void init() {
/* 28 */     this.defaultHcceGroupInfo = new HcceGroupInfo("Unknown", false, ClusterMode.Standby, "Default Group");
/*    */   }
/*    */   @Autowired
/*    */   private HcceEnv hcceEnv; @Autowired
/*    */   private HcceGroupInfoRepository hcceGroupInfoRepository;
/*    */   public ClusterMode activeOrStandby() {
/* 34 */     HcceGroupInfo info = this.hcceGroupInfoRepository.findById(this.hcceEnv.getCurrentGroupName()).orElse(null);
/* 35 */     logger.debug("HcceGroupInfo of {}: {} ", this.hcceEnv.getCurrentGroupName(), info);
/* 36 */     if (info == null) {
/* 37 */       logger.warn("HcceGroupInfo of {} was not found in DRDB, use default HcceGroupInfo: {}", this.hcceEnv
/*    */           
/* 39 */           .getCurrentGroupName(), this.defaultHcceGroupInfo);
/*    */       
/* 41 */       info = this.defaultHcceGroupInfo;
/*    */     } 
/* 43 */     if (info.isAllowActive() && info.getClusterMode() == ClusterMode.Active)
/* 44 */       return ClusterMode.Active; 
/* 45 */     return ClusterMode.Standby;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAllowActive() {
/* 50 */     HcceGroupInfo info = this.hcceGroupInfoRepository.findById(this.hcceEnv.getCurrentGroupName()).orElse(null);
/* 51 */     logger.debug("HcceGroupInfo: {}", info);
/* 52 */     if (info == null) {
/* 53 */       logger.error("HcceGroupInfo of {} was not found in DRDB, use default HcceGroupInfo", this.hcceEnv
/*    */           
/* 55 */           .getCurrentGroupName());
/* 56 */       return this.defaultHcceGroupInfo.isAllowActive();
/*    */     } 
/* 58 */     return info.isAllowActive();
/*    */   }
/*    */ 
/*    */   
/*    */   @Transactional("drdbTransactionManager")
/*    */   public void setClusterMode(ClusterMode clusterMode) throws DisasterRecoveryException {
/* 64 */     HcceGroupInfo info = this.hcceGroupInfoRepository.findById(this.hcceEnv.getCurrentGroupName()).orElse(null);
/* 65 */     if (info == null) {
/* 66 */       throw new DisasterRecoveryException("HcceGroupInfo of " + this.hcceEnv
/* 67 */           .getCurrentGroupName() + " was not found in DRDB");
/*    */     }
/* 69 */     info.setClusterMode(clusterMode);
/* 70 */     this.hcceGroupInfoRepository.save(info);
/*    */   }
/*    */ 
/*    */   
/*    */   @Transactional("drdbTransactionManager")
/*    */   public void setAllowActive(boolean allowActive) throws DisasterRecoveryException {
/* 76 */     HcceGroupInfo info = this.hcceGroupInfoRepository.findById(this.hcceEnv.getCurrentGroupName()).orElse(null);
/* 77 */     if (info == null) {
/* 78 */       throw new DisasterRecoveryException("HcceGroupInfo of " + this.hcceEnv
/* 79 */           .getCurrentGroupName() + " was not found in DRDB");
/*    */     }
/* 81 */     info.setAllowActive(allowActive);
/* 82 */     this.hcceGroupInfoRepository.save(info);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\recovery\DisasterRecoveryManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */