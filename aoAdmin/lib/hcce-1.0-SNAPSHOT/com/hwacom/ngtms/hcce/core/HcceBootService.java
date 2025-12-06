/*    */ package com.hwacom.ngtms.hcce.core;
/*    */ 
/*    */ import com.hazelcast.client.HazelcastClient;
/*    */ import com.hazelcast.client.config.ClientConfig;
/*    */ import com.hazelcast.core.HazelcastInstance;
/*    */ import com.hwacom.ngtms.base.rmi.RmiUtils;
/*    */ import com.hwacom.ngtms.hcce.hz.HzDistObjRegister;
/*    */ import com.hwacom.ngtms.hcce.shared.TopologyInfo;
/*    */ import com.hwacom.ngtms.hcce.topology.TopologyManager;
/*    */ import com.hwacom.ngtms.hcce.topology.repository.TopologyNodeCfgRepository;
/*    */ import java.rmi.RemoteException;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import javax.annotation.Resource;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.core.env.Environment;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class HcceBootService
/*    */ {
/*    */   @Resource
/*    */   private Environment environment;
/*    */   @Autowired
/*    */   private HzDistObjRegister hzDistObjRegister;
/*    */   @Autowired
/*    */   private NodeManager nodeManager;
/*    */   @Autowired
/*    */   private ClusterManager hcClusterManager;
/*    */   @Autowired
/*    */   private TopologyManager topologyManager;
/*    */   @Autowired
/*    */   private ClientConfig hazelcastClientConfig;
/*    */   @Autowired
/*    */   private TopologyNodeCfgRepository topologyNodeCfgRepository;
/* 39 */   private static Logger logger = LoggerFactory.getLogger(HcceBootService.class);
/*    */   
/*    */   public void run() throws InterruptedException {
/* 42 */     (new Thread(() -> {
/*    */           waitUtilHzConfigFinished();
/*    */           
/*    */           try {
/*    */             Thread.sleep(2000L);
/*    */             
/*    */             logger.info("Start Node Manager");
/*    */             
/*    */             logger.info("HcceEnv: {}", this.nodeManager.getHcceEnv());
/*    */             this.nodeManager.start();
/* 52 */           } catch (InterruptedException e) {
/*    */             
/*    */             logger.info("failed to start hcce", e);
/*    */           }
/*    */         
/* 57 */         }"HcNodeStater")).start();
/*    */   }
/*    */   
/*    */   private void waitUtilHzConfigFinished() {
/* 61 */     while (!this.hzDistObjRegister.isProcessHzConfigFinished()) {
/* 62 */       logger.info("not yet process HzConfig Finished");
/*    */       try {
/* 64 */         TimeUnit.SECONDS.sleep(1L);
/* 65 */       } catch (InterruptedException e) {
/* 66 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private void addTopology2Map() {
/* 72 */     HazelcastInstance hazelcastInstance = null;
/*    */     try {
/* 74 */       hazelcastInstance = HazelcastClient.newHazelcastClient(this.hazelcastClientConfig);
/* 75 */       Map<String, TopologyInfo> topologyInfo = this.topologyManager.createTopologyInfo();
/* 76 */       this.topologyManager.putTopology2Map(hazelcastInstance, topologyInfo);
/* 77 */     } catch (Exception e) {
/* 78 */       logger.info("Failed to connect HC-Cluster with HZ Client, register this node to a new formed HC-Cluster directly.");
/*    */     } finally {
/*    */       
/* 81 */       if (hazelcastInstance != null) {
/* 82 */         hazelcastInstance.shutdown();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void stop() {
/* 88 */     if (this.nodeManager != null) {
/* 89 */       logger.info("**************** @002 ****************");
/* 90 */       logger.info("Stop Hc Node");
/* 91 */       logger.info("*************************************");
/* 92 */       logger.info("Stop Node Manager");
/* 93 */       this.nodeManager.stopFromContainer();
/*    */     } 
/*    */     
/*    */     try {
/* 97 */       RmiUtils.unbindAllRmiService();
/* 98 */     } catch (RemoteException e) {
/* 99 */       logger.error("Failed to unbind all rmi services", e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\core\HcceBootService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */