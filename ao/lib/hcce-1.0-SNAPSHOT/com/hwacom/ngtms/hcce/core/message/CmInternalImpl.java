/*    */ package com.hwacom.ngtms.hcce.core.message;
/*    */ 
/*    */ import com.hazelcast.core.Member;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import com.hwacom.ngtms.hcce.core.ClusterManager;
/*    */ import com.hwacom.ngtms.hcce.core.exception.ClusterManagerNotReadyException;
/*    */ import com.hwacom.ngtms.hcce.core.exception.RegisterNodeException;
/*    */ import com.hwacom.ngtms.hcce.shared.CmState;
/*    */ import com.hwacom.ngtms.hcce.topology.TopologyManager;
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
/*    */ @Service
/*    */ public class CmInternalImpl
/*    */   implements CmInternal
/*    */ {
/* 24 */   private static Logger logger = LoggerFactory.getLogger(CmInternalImpl.class);
/*    */   @Autowired
/*    */   private TopologyManager topologyManager;
/*    */   @Autowired
/*    */   private ClusterManager cm;
/*    */   
/*    */   public void registerNode(String memberUuid, String nodeName) throws RegisterNodeException, ClusterManagerNotReadyException
/*    */   {
/* 32 */     logger.info("Register node from remote, nodeName={}, memberUuid={}", nodeName, memberUuid);
/* 33 */     CmState cmState = this.cm.getState();
/* 34 */     if ((cmState == CmState.Active) || (cmState == CmState.Standby)) {
/* 35 */       Member member = HzUtils.findMemberByUuid(memberUuid);
/* 36 */       this.topologyManager.registerNode(nodeName, member);
/*    */     } else {
/* 38 */       throw new ClusterManagerNotReadyException("Current ClusterManager state: " + cmState);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\core\message\CmInternalImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */