/*      */ package com.hwacom.ngtms.hcce.core;
/*      */ 
/*      */ import com.hazelcast.core.DistributedObject;
/*      */ import com.hazelcast.core.HazelcastInstance;
/*      */ import com.hazelcast.core.IMap;
/*      */ import com.hazelcast.core.LifecycleEvent;
/*      */ import com.hazelcast.core.LifecycleListener;
/*      */ import com.hazelcast.core.Member;
/*      */ import com.hazelcast.core.MembershipEvent;
/*      */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*      */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*      */ import com.hwacom.ngtms.base.oplog.service.BaseOpLogger;
/*      */ import com.hwacom.ngtms.base.oplog.shared.CoreSystem;
/*      */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*      */ import com.hwacom.ngtms.hcce.config.DynaConfigManager;
/*      */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*      */ import com.hwacom.ngtms.hcce.config.model.DynamicConfigPk;
/*      */ import com.hwacom.ngtms.hcce.core.coordinator.GroupLeadership;
/*      */ import com.hwacom.ngtms.hcce.core.coordinator.LeaderCallback;
/*      */ import com.hwacom.ngtms.hcce.core.exception.ClusterManagerNotCoordinatorException;
/*      */ import com.hwacom.ngtms.hcce.core.exception.ClusterManagerNotReadyException;
/*      */ import com.hwacom.ngtms.hcce.core.exception.ClusterManagerOperationException;
/*      */ import com.hwacom.ngtms.hcce.core.exception.DisasterRecoveryException;
/*      */ import com.hwacom.ngtms.hcce.core.exception.FmeStaleVersionException;
/*      */ import com.hwacom.ngtms.hcce.core.exception.InvalidFmeDefinitionException;
/*      */ import com.hwacom.ngtms.hcce.core.exception.TopologyNodeCfgException;
/*      */ import com.hwacom.ngtms.hcce.core.message.NmInternal;
/*      */ import com.hwacom.ngtms.hcce.fme.manager.FmeManager;
/*      */ import com.hwacom.ngtms.hcce.fme.manager.model.FmeDefinition;
/*      */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*      */ import com.hwacom.ngtms.hcce.hz.HzTopic;
/*      */ import com.hwacom.ngtms.hcce.recovery.DisasterRecoveryManager;
/*      */ import com.hwacom.ngtms.hcce.shared.ClientHeartbeat;
/*      */ import com.hwacom.ngtms.hcce.shared.ClusterMode;
/*      */ import com.hwacom.ngtms.hcce.shared.CmState;
/*      */ import com.hwacom.ngtms.hcce.shared.FmeDefTable;
/*      */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*      */ import com.hwacom.ngtms.hcce.shared.NodeSnapshot;
/*      */ import com.hwacom.ngtms.hcce.shared.TopologyNode;
/*      */ import com.hwacom.ngtms.hcce.topology.TopologyManager;
/*      */ import com.hwacom.ngtms.hcce.topology.model.TopologyNodeCfg;
/*      */ import com.hwacom.ngtms.hcce.util.HcceUtils;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.atomic.AtomicBoolean;
/*      */ import java.util.concurrent.atomic.AtomicReference;
/*      */ import org.slf4j.Logger;
/*      */ import org.slf4j.LoggerFactory;
/*      */ import org.springframework.beans.factory.annotation.Autowired;
/*      */ import org.springframework.beans.factory.annotation.Value;
/*      */ import org.springframework.scheduling.annotation.Scheduled;
/*      */ import org.springframework.stereotype.Component;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Component
/*      */ public class ClusterManager
/*      */   implements LeaderCallback
/*      */ {
/*   72 */   private static Logger logger = LoggerFactory.getLogger(ClusterManager.class);
/*      */   
/*      */   private enum CmEvent {
/*   75 */     Start,
/*   76 */     OnElectedLeader,
/*   77 */     OnActive,
/*   78 */     OnStandby,
/*   79 */     Stop;
/*      */   }
/*      */   
/*   82 */   private AtomicReference<CmState> cmState = new AtomicReference<>();
/*   83 */   private final AtomicBoolean imCoordinator = new AtomicBoolean(false);
/*      */   
/*      */   private GroupLeadership groupLeadership;
/*      */   
/*      */   @Autowired
/*      */   private TopologyManager topologyManager;
/*      */   
/*      */   @Autowired
/*      */   private DynaConfigManager dynaCfgManager;
/*      */   
/*      */   @Autowired
/*      */   private FmeManager fmeManager;
/*      */   
/*   96 */   private Object fsmSyncObj = new Object(); @Autowired private DisasterRecoveryManager disasterRecoveryManager; @Autowired private NodeManager nodeManager; @Autowired private HcceEnv hcceEnv; @Autowired private BaseOpLogger opLogger; @Value("${hcce.clientNode.heartbeat.fail.seconds:90}")
/*   97 */   private int heartbeatFailSeconds; private final AtomicBoolean forceStop = new AtomicBoolean(false);
/*      */ 
/*      */   
/*      */   private ClusterManager() {
/*  101 */     waitButDoNothing();
/*      */   }
/*      */   
/*      */   public void start() {
/*  105 */     logger.info("Start HcClusterManager, node name : " + this.hcceEnv.getNodeName());
/*  106 */     fsm(CmEvent.Start);
/*      */   }
/*      */   
/*      */   public void stop() {
/*  110 */     fsm(CmEvent.Stop);
/*      */   }
/*      */   
/*      */   private void fsm(CmEvent event) {
/*  114 */     synchronized (this.fsmSyncObj) {
/*  115 */       CmState state = this.cmState.get();
/*  116 */       switch (state) {
/*      */         case MERGING:
/*  118 */           waitSateDo(event);
/*      */           break;
/*      */         case MERGED:
/*  121 */           prepareSateDo(event);
/*      */           break;
/*      */         case null:
/*  124 */           activeSateDo(event);
/*      */           break;
/*      */         case null:
/*  127 */           standbySateDo(event);
/*      */           break;
/*      */         case null:
/*  130 */           restartSateDo(event);
/*      */           break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void changeToState(CmState state) {
/*  137 */     this.cmState.set(state);
/*      */   }
/*      */   
/*      */   private void setCmState(CmState state) {
/*  141 */     logger.info("ClusterManager change state from {} to {}", this.cmState.get(), state);
/*  142 */     changeToState(state);
/*  143 */     if (HzUtils.getHzInstance() != null) {
/*  144 */       IMap<String, ClusterMode> clusterModeMap = HzUtils.getMap((HzDistObjEnum)HzMap.ClusterMode);
/*  145 */       switch (state) {
/*      */         case null:
/*  147 */           clusterModeMap.set("hcCluster", ClusterMode.Active);
/*      */           break;
/*      */ 
/*      */         
/*      */         case null:
/*  152 */           clusterModeMap.set("hcCluster", ClusterMode.DisConnected);
/*      */           break;
/*      */         case null:
/*  155 */           clusterModeMap.set("hcCluster", ClusterMode.Standby);
/*      */           break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void waitButDoNothing() {
/*  165 */     changeToState(CmState.Wait);
/*  166 */     this.imCoordinator.set(false);
/*  167 */     this.forceStop.set(false);
/*      */   }
/*      */   
/*      */   private void waitSateDo(CmEvent event) {
/*  171 */     switch (event) {
/*      */       case MERGING:
/*  173 */         prepareStateEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case MERGED:
/*      */         return;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  184 */     logger.warn("Receive unexpected event {} at state ", event, this.cmState.get());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void prepareStateEntry() {
/*  190 */     setCmState(CmState.Prepare);
/*  191 */     this.disasterRecoveryManager.init();
/*  192 */     this.topologyManager.init();
/*  193 */     this.groupLeadership = new GroupLeadership(HzUtils.getHzInstance());
/*  194 */     this.groupLeadership.setCallback(this);
/*  195 */     HzUtils.getHzInstance()
/*  196 */       .getLifecycleService()
/*  197 */       .addLifecycleListener(new LifecycleListener()
/*      */         {
/*      */           public void stateChanged(LifecycleEvent event)
/*      */           {
/*  201 */             ClusterManager.logger.info(event.toString());
/*  202 */             switch (event.getState()) {
/*      */               
/*      */               case MERGING:
/*  205 */                 ClusterManager.logger.info("Hazelcast enter MERGING state, stop all HC-Cluster functions.");
/*  206 */                 ClusterManager.this.fmeManager.setFreezeFmeManagement();
/*  207 */                 ClusterManager.this.topologyManager.setFreezeRegService();
/*  208 */                 ClusterManager.this.nodeManager.getFmeController().freezeAllFmes();
/*  209 */                 ClusterManager.this.nodeManager.getFmeController().stop();
/*      */                 break;
/*      */               
/*      */               case MERGED:
/*  213 */                 ClusterManager.logger.info("Hazelcast enter MERGED state, restart this node 1 second later.");
/*  214 */                 (new Thread(new Runnable()
/*      */                     {
/*      */                       public void run()
/*      */                       {
/*      */                         try {
/*  219 */                           Thread.sleep(1000L);
/*  220 */                         } catch (InterruptedException interruptedException) {}
/*      */                         
/*  222 */                         ClusterManager.this.nodeManager.restart();
/*      */                       }
/*  225 */                     })).start();
/*      */                 break;
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void prepareSateDo(CmEvent event) {
/*  235 */     switch (event) {
/*      */       
/*      */       case null:
/*      */         
/*      */         try {
/*      */ 
/*      */           
/*  242 */           this.topologyManager.setCoordinatorNodeByName(this.hcceEnv.getNodeName());
/*  243 */           if (this.disasterRecoveryManager.activeOrStandby() == ClusterMode.Active) { activeStateEntry(); }
/*  244 */           else { standbyStateEntry(false); } 
/*  245 */         } catch (Exception ex) {
/*      */ 
/*      */           
/*  248 */           logger.error("Failed to check Active of Standby mode of current HC-Cluster. Stop this node", ex);
/*      */           
/*  250 */           (new Thread(new Runnable()
/*      */               {
/*      */                 public void run()
/*      */                 {
/*      */                   try {
/*  255 */                     Thread.sleep(100L);
/*  256 */                   } catch (InterruptedException interruptedException) {}
/*      */ 
/*      */                   
/*  259 */                   ClusterManager.this.nodeManager.stop();
/*      */                 }
/*  262 */               })).start();
/*      */         } 
/*      */         return;
/*      */ 
/*      */       
/*      */       case MERGED:
/*  268 */         waitButDoNothing();
/*      */         return;
/*      */     } 
/*  271 */     logger.warn("Receive unexpected event {} at state ", event, this.cmState.get());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void activeStateEntry() {
/*  277 */     setCmState(CmState.Active);
/*  278 */     HzUtils.getTopic((HzDistObjEnum)HzTopic.ClusterState).publish("active");
/*  279 */     this.topologyManager.refleshTopologyInfo();
/*  280 */     this.dynaCfgManager.init();
/*  281 */     this.fmeManager.start();
/*      */   }
/*      */   
/*      */   private void activeStateExit() {
/*  285 */     this.fmeManager.stop();
/*      */   }
/*      */   
/*      */   private void activeSateDo(CmEvent event) {
/*  289 */     switch (event) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case null:
/*  297 */         activeStateExit();
/*  298 */         restartStateEntry();
/*      */         return;
/*      */       case MERGED:
/*  301 */         activeStateExit();
/*  302 */         waitButDoNothing();
/*      */         return;
/*      */     } 
/*  305 */     logger.warn("Receive unexpected event {} at state ", event, this.cmState.get());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void standbyStateEntry(boolean cleanCluster) {
/*  311 */     setCmState(CmState.Standby);
/*  312 */     HzUtils.getTopic((HzDistObjEnum)HzTopic.ClusterState).publish("standby");
/*      */   }
/*      */   
/*      */   private void standbySateDo(CmEvent event) {
/*  316 */     switch (event) {
/*      */ 
/*      */       
/*      */       case null:
/*  320 */         destroyDistObjs();
/*  321 */         activeStateEntry();
/*      */         return;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case MERGED:
/*  328 */         waitButDoNothing();
/*      */         return;
/*      */     } 
/*  331 */     logger.warn("Receive unexpected event {} at state ", event, this.cmState.get());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void destroyDistObjs() {
/*  342 */     HazelcastInstance hazelcastInstance = HzUtils.getHzInstance();
/*  343 */     if (hazelcastInstance != null) {
/*  344 */       Collection<DistributedObject> distObjs = hazelcastInstance.getDistributedObjects();
/*  345 */       List<String> reserveObjList = new ArrayList<>();
/*  346 */       reserveObjList.add(HzMap.TopologyInfo.toHzName());
/*  347 */       reserveObjList.add(HzMap.ClusterMode.toHzName());
/*  348 */       reserveObjList.add(HzTopic.ClusterState.toHzName());
/*  349 */       for (DistributedObject distObj : distObjs) {
/*  350 */         if (!reserveObjList.contains(distObj.getName())) {
/*  351 */           logger.info("Destroy distObj: {} before entering active state.", distObj.getName());
/*  352 */           distObj.destroy();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void restartStateEntry() {
/*  359 */     setCmState(CmState.Restart);
/*  360 */     HzUtils.getTopic((HzDistObjEnum)HzTopic.ClusterState).publish("restart");
/*  361 */     restartCluster(2000L);
/*      */   }
/*      */   
/*      */   private void restartSateDo(CmEvent event) {
/*  365 */     switch (event) {
/*      */       case MERGING:
/*  367 */         waitButDoNothing();
/*  368 */         fsm(CmEvent.Start);
/*      */         return;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case MERGED:
/*  377 */         this.forceStop.set(true);
/*      */         return;
/*      */     } 
/*  380 */     logger.warn("Receive unexpected event {} at state ", event, this.cmState.get());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void memberAdded(MembershipEvent membershipEvent) {
/*  387 */     Member member = membershipEvent.getMember();
/*  388 */     if (isCoordinator()) {
/*  389 */       this.opLogger.addLog(CoreSystem.HCCE
/*  390 */           .getDefaultUserId(), 
/*  391 */           HzUtils.getIpAddress(member), CoreSystem.HCCE
/*  392 */           .toString(), member
/*  393 */           .getAttributes().get("nodeName").toString(), OperationResult.SUCCESS, "hcce.oplog.memberAdded", new Object[] { member
/*      */ 
/*      */             
/*  396 */             .getAttributes().get("nodeName"), member
/*  397 */             .getAttributes().get("groupName") });
/*      */     }
/*  399 */     logger.info("A member ({}) join to HC-Cluster {}", member, this.hcceEnv.getCurrentGroupName());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void memberRemoved(MembershipEvent membershipEvent) {
/*  405 */     Member member = membershipEvent.getMember();
/*  406 */     if (isCoordinator()) {
/*  407 */       this.opLogger.addLog(CoreSystem.HCCE
/*  408 */           .getDefaultUserId(), 
/*  409 */           HzUtils.getIpAddress(member), CoreSystem.HCCE
/*  410 */           .toString(), member
/*  411 */           .getAttributes().get("nodeName").toString(), OperationResult.SUCCESS, "hcce.oplog.memberRemoved", new Object[] { member
/*      */ 
/*      */             
/*  414 */             .getAttributes().get("nodeName"), member
/*  415 */             .getAttributes().get("groupName") });
/*      */     }
/*  417 */     logger.info("A member ({}) leave HC-Cluster {}", member, this.hcceEnv.getCurrentGroupName());
/*  418 */     CmState state = this.cmState.get();
/*  419 */     if (state == CmState.Active || state == CmState.Standby || state == CmState.Restart) {
/*  420 */       TopologyNode topologyNode = this.topologyManager.getNodeByMemberUuid(member.getUuid());
/*  421 */       if (topologyNode != null) {
/*  422 */         this.topologyManager.unregisterNode(member);
/*  423 */         if (state == CmState.Active) {
/*  424 */           this.fmeManager.refreshFmeStatusTable();
/*      */         }
/*  426 */         if (state != CmState.Restart);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onElectedLeader(Member member) {
/*  435 */     logger.info("This node has been elected as the coordinator");
/*  436 */     if (this.topologyManager.isNodeExist(this.hcceEnv.getNodeName())) {
/*  437 */       this.imCoordinator.set(true);
/*  438 */       fsm(CmEvent.OnElectedLeader);
/*  439 */       this.opLogger.addLog(CoreSystem.HCCE
/*  440 */           .getDefaultUserId(), 
/*  441 */           HzUtils.getIpAddress(member), CoreSystem.HCCE
/*  442 */           .toString(), member
/*  443 */           .getAttributes().get("nodeName").toString(), OperationResult.SUCCESS, "hcce.oplog.onElectedLeader", new Object[] { member
/*      */ 
/*      */             
/*  446 */             .getAttributes().get("nodeName"), member
/*  447 */             .getAttributes().get("groupName") });
/*      */     } else {
/*      */       
/*  450 */       logger.error("This node: {} has been elected as the coordinator, but it is not a member defined in Topology. Stop this node now!", this.hcceEnv
/*      */           
/*  452 */           .getNodeName());
/*  453 */       fsm(CmEvent.Stop);
/*  454 */       (new Thread(new Runnable()
/*      */           {
/*      */             public void run()
/*      */             {
/*  458 */               ClusterManager.this.nodeManager.stop();
/*      */             }
/*  461 */           })).start();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkAtActiveOrStandbyState() throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException {
/*  467 */     if (!isCoordinator())
/*  468 */       throw new ClusterManagerNotCoordinatorException("I am not the coordinator!"); 
/*  469 */     CmState state = this.cmState.get();
/*  470 */     if (state == CmState.Active || state == CmState.Standby)
/*      */       return; 
/*  472 */     throw new ClusterManagerNotReadyException("I am not ready, try again later !");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void stopNode(String nodeName) throws ClusterManagerNotCoordinatorException, ClusterManagerOperationException, ClusterManagerNotReadyException {
/*  489 */     checkAtActiveOrStandbyState();
/*  490 */     if (this.hcceEnv.getNodeName().equals(nodeName)) {
/*      */ 
/*      */ 
/*      */       
/*  494 */       (new Thread(new Runnable()
/*      */           {
/*      */             public void run()
/*      */             {
/*      */               try {
/*  499 */                 Thread.sleep(500L);
/*  500 */               } catch (InterruptedException interruptedException) {}
/*      */               
/*  502 */               ClusterManager.this.nodeManager.stop();
/*      */             }
/*  505 */           })).start();
/*      */       return;
/*      */     } 
/*  508 */     TopologyNode topologyNode = this.topologyManager.getNodeByName(nodeName);
/*  509 */     if (topologyNode == null) {
/*  510 */       throw new ClusterManagerOperationException("The node " + nodeName + " is not defined in HC-Cluster");
/*      */     }
/*  512 */     if (topologyNode.isRegistered()) {
/*  513 */       logger.info("Stop {} node", nodeName);
/*      */       
/*  515 */       NmInternal nmInternal = HcceUtils.getNmRmiClient(topologyNode
/*  516 */           .getNodeCfg().getNodeName(), topologyNode.getLastRegIpAddress());
/*  517 */       nmInternal.stopNode();
/*      */     } else {
/*  519 */       throw new ClusterManagerOperationException("The node " + nodeName + " was not registered");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startNode(String nodeName) throws ClusterManagerNotCoordinatorException, ClusterManagerOperationException, ClusterManagerNotReadyException {
/*  534 */     checkAtActiveOrStandbyState();
/*  535 */     if (this.hcceEnv.getNodeName().equals(nodeName)) {
/*  536 */       throw new ClusterManagerOperationException("The node " + nodeName + " is coordinator. Start coordinator node is prohibited");
/*      */     }
/*      */     
/*  539 */     TopologyNode topologyNode = this.topologyManager.getNodeByName(nodeName);
/*  540 */     if (topologyNode == null) {
/*  541 */       throw new ClusterManagerOperationException("The node " + nodeName + " is not defined in HC-Cluster");
/*      */     }
/*  543 */     if (topologyNode.isRegistered()) {
/*  544 */       throw new ClusterManagerOperationException("The node " + nodeName + " has registered");
/*      */     }
/*  546 */     String ipAddress = topologyNode.getLastRegIpAddress();
/*  547 */     if (ipAddress == null) {
/*  548 */       throw new ClusterManagerOperationException("Unable to get the last registered IP of " + nodeName);
/*      */     }
/*      */     
/*  551 */     logger.info("Start {} node", nodeName);
/*      */     
/*      */     try {
/*  554 */       NmInternal nmInternal = HcceUtils.getNmRmiClient(topologyNode.getNodeCfg().getNodeName(), ipAddress);
/*  555 */       nmInternal.startNode();
/*  556 */     } catch (Exception ex) {
/*      */ 
/*      */       
/*  559 */       this.topologyManager.eraseNodeIpData(nodeName);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void restartNode(String nodeName) throws ClusterManagerNotCoordinatorException, ClusterManagerOperationException, ClusterManagerNotReadyException {
/*  568 */     checkAtActiveOrStandbyState();
/*  569 */     if (this.hcceEnv.getNodeName().equals(nodeName)) {
/*      */ 
/*      */ 
/*      */       
/*  573 */       (new Thread(new Runnable()
/*      */           {
/*      */             public void run()
/*      */             {
/*      */               try {
/*  578 */                 Thread.sleep(300L);
/*  579 */               } catch (InterruptedException interruptedException) {}
/*      */               
/*  581 */               ClusterManager.this.nodeManager.restart();
/*      */             }
/*  584 */           })).start();
/*      */       return;
/*      */     } 
/*  587 */     TopologyNode topologyNode = this.topologyManager.getNodeByName(nodeName);
/*  588 */     if (topologyNode == null) {
/*  589 */       throw new ClusterManagerOperationException("The node " + nodeName + " is not defined in HC-Cluster");
/*      */     }
/*  591 */     if (topologyNode.getLastRegIpAddress() != null) {
/*  592 */       logger.info("Restart {} node", nodeName);
/*      */       
/*  594 */       NmInternal nmInternal = HcceUtils.getNmRmiClient(topologyNode
/*  595 */           .getNodeCfg().getNodeName(), topologyNode.getLastRegIpAddress());
/*  596 */       nmInternal.restartNode();
/*      */     } else {
/*  598 */       throw new ClusterManagerOperationException("Unable to get the last registered IP of " + nodeName);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void promoteCluster() throws ClusterManagerNotCoordinatorException, ClusterManagerOperationException, DisasterRecoveryException {
/*  605 */     if (!isCoordinator()) throw new ClusterManagerNotCoordinatorException(); 
/*  606 */     CmState state = this.cmState.get();
/*  607 */     if (this.disasterRecoveryManager.isAllowActive())
/*  608 */     { if (state == CmState.Standby) {
/*  609 */         this.disasterRecoveryManager.setClusterMode(ClusterMode.Active);
/*  610 */         fsm(CmEvent.OnActive);
/*      */       } else {
/*  612 */         throw new ClusterManagerOperationException("Current state: " + state + " is not standby");
/*      */       }  }
/*  614 */     else { throw new DisasterRecoveryException("The HC-Cluster could not allow to be activated"); }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void demoteCluster() throws ClusterManagerNotCoordinatorException, ClusterManagerOperationException, DisasterRecoveryException {
/*  630 */     if (!isCoordinator()) throw new ClusterManagerNotCoordinatorException(); 
/*  631 */     CmState state = this.cmState.get();
/*  632 */     if (state == CmState.Active) {
/*  633 */       this.disasterRecoveryManager.setClusterMode(ClusterMode.Standby);
/*  634 */       fsm(CmEvent.OnStandby);
/*      */     } else {
/*  636 */       throw new ClusterManagerOperationException("Current state [" + state + "] is not [active]");
/*      */     } 
/*      */   }
/*      */   
/*      */   public ClusterMode activeOrStandby() throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException {
/*  641 */     checkAtActiveOrStandbyState();
/*  642 */     return this.disasterRecoveryManager.activeOrStandby();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAllowActive(boolean allowActive) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException, DisasterRecoveryException {
/*  648 */     checkAtActiveOrStandbyState();
/*  649 */     this.disasterRecoveryManager.setAllowActive(allowActive);
/*      */   }
/*      */   
/*      */   private void restartCluster(final long waitTime) {
/*  653 */     (new Thread(new Runnable()
/*      */         {
/*      */           
/*      */           public void run()
/*      */           {
/*  658 */             ClusterManager.this.topologyManager.setFreezeRegService();
/*      */             
/*  660 */             ClusterManager.this.fmeManager.setFreezeFmeManagement();
/*      */             
/*      */             try {
/*  663 */               if (waitTime > 0L) Thread.sleep(waitTime); 
/*  664 */             } catch (InterruptedException interruptedException) {}
/*      */             
/*  666 */             Set<Member> members = HzUtils.getMembers();
/*  667 */             Member coordinator = null;
/*  668 */             List<Member> otherMembers = new ArrayList<>();
/*  669 */             for (Member member : members) {
/*  670 */               if (coordinator == null) {
/*  671 */                 coordinator = member;
/*      */                 continue;
/*      */               } 
/*  674 */               otherMembers.add(member);
/*      */             } 
/*      */             
/*  677 */             Set<TopologyNode> stopNodeSet = new HashSet<>();
/*  678 */             for (Member member : otherMembers) {
/*      */               try {
/*  680 */                 TopologyNode node = ClusterManager.this.topologyManager.getNodeByMemberUuid(member.getUuid());
/*  681 */                 if (node != null) {
/*      */                   
/*  683 */                   NmInternal nmInternal = HcceUtils.getNmRmiClient(node
/*  684 */                       .getNodeCfg().getNodeName(), node.getLastRegIpAddress());
/*  685 */                   stopNodeSet.add(node);
/*  686 */                   ClusterManager.logger.info("Restart HC-Cluster: Stop HC-Node:{}", node
/*  687 */                       .getNodeCfg().getNodeName());
/*  688 */                   nmInternal.stopNode(); continue;
/*      */                 } 
/*  690 */                 ClusterManager.logger.warn("Restart HC-Cluster: The member [{}] was found in HC-Cluster, but it have not registered.");
/*      */               
/*      */               }
/*  693 */               catch (Exception ex) {
/*  694 */                 ClusterManager.logger.error("Restart HC-Cluster: Failed to stop member ({})", member, ex);
/*      */               } 
/*      */             } 
/*  697 */             if (ClusterManager.this.forceStop.get()) {
/*  698 */               ClusterManager.this.waitButDoNothing();
/*      */               
/*      */               return;
/*      */             } 
/*  702 */             int memberNumbers = 0;
/*  703 */             for (int i = 0; i < 300; i++) {
/*      */               try {
/*  705 */                 Thread.sleep(100L);
/*  706 */               } catch (InterruptedException interruptedException) {}
/*      */               
/*  708 */               if (ClusterManager.this.forceStop.get()) {
/*  709 */                 ClusterManager.this.waitButDoNothing();
/*      */                 return;
/*      */               } 
/*  712 */               memberNumbers = HzUtils.getMembers().size();
/*  713 */               if (memberNumbers == 1)
/*      */                 break; 
/*  715 */             }  if (memberNumbers != 1) {
/*  716 */               ClusterManager.logger.warn("Restart HC-Cluster: Failed to stop memebers, current memebers in HC-Group are: {}", 
/*      */                   
/*  718 */                   HzUtils.getMembers());
/*      */             }
/*      */ 
/*      */             
/*  722 */             NmInternal coordinatorNmInternal = HcceUtils.getNmRmiClient(coordinator
/*  723 */                 .getStringAttribute("nodeName"), 
/*  724 */                 HzUtils.getIpAddress(coordinator));
/*      */             try {
/*  726 */               ClusterManager.logger.info("Restart HC-Cluster: Stop coordinator");
/*  727 */               coordinatorNmInternal.stopNode();
/*  728 */             } catch (Exception ex) {
/*  729 */               ClusterManager.logger.error("Restart HC-Cluster: Failed to stop coordinator ({})", coordinator, ex);
/*      */             } 
/*      */ 
/*      */             
/*      */             try {
/*  734 */               Thread.sleep(4000L);
/*  735 */             } catch (InterruptedException interruptedException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             try {
/*  741 */               ClusterManager.logger.info("Restart HC-Cluster: Start coordinator");
/*  742 */               coordinatorNmInternal.startNode();
/*  743 */             } catch (Exception ex) {
/*  744 */               ClusterManager.logger.error("Restart HC-Cluster: Failed to retart coordinator ({})", coordinator, ex);
/*      */             } 
/*      */ 
/*      */             
/*  748 */             memberNumbers = 0;
/*  749 */             for (int j = 0; j < 300; j++) {
/*      */               try {
/*  751 */                 Thread.sleep(100L);
/*  752 */               } catch (InterruptedException interruptedException) {}
/*      */               
/*      */               try {
/*  755 */                 if (HzUtils.getHzInstance() != null)
/*  756 */                 { memberNumbers = HzUtils.getMembers().size();
/*  757 */                   if (memberNumbers == 1)
/*      */                     break;  } 
/*  759 */               } catch (Exception exception) {}
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*  764 */             for (TopologyNode node : stopNodeSet)
/*      */             {
/*      */               try {
/*  767 */                 NmInternal nmInternal = HcceUtils.getNmRmiClient(node
/*  768 */                     .getNodeCfg().getNodeName(), node.getLastRegIpAddress());
/*  769 */                 ClusterManager.logger.info("Restart HC-Cluster: Start HC-Node {}", node
/*  770 */                     .getNodeCfg().getNodeName());
/*  771 */                 nmInternal.restartNode();
/*  772 */               } catch (Exception ex) {
/*  773 */                 ClusterManager.logger.error("Restart HC-Cluster: Failed to restart HC-Node:", node
/*      */                     
/*  775 */                     .getNodeCfg().getNodeName(), ex);
/*      */               }
/*      */             
/*      */             }
/*      */           
/*      */           }
/*  781 */         })).start();
/*      */   }
/*      */   
/*      */   public void startCluster(final List<NodeSnapshot> nodeSnapshots) {
/*  785 */     if (nodeSnapshots == null || nodeSnapshots.size() == 0)
/*  786 */       return;  (new Thread(new Runnable()
/*      */         {
/*      */           
/*      */           public void run()
/*      */           {
/*  791 */             NodeSnapshot coordinator = nodeSnapshots.get(0);
/*      */             try {
/*  793 */               ClusterManager.logger.info("Start HC-Cluster: Start coordinator");
/*      */               
/*  795 */               NmInternal coordinatorNmInternal = HcceUtils.getNmRmiClient(coordinator
/*  796 */                   .getNodeName(), coordinator.getIpAddress());
/*  797 */               coordinatorNmInternal.startNode();
/*  798 */             } catch (Exception ex) {
/*  799 */               ClusterManager.logger.error("Start HC-Cluster: Failed to retart coordinator ({})", coordinator, ex);
/*      */             } 
/*      */ 
/*      */             
/*  803 */             int memberNumbers = 0; int i;
/*  804 */             for (i = 0; i < 200; i++) {
/*      */               try {
/*  806 */                 Thread.sleep(100L);
/*  807 */               } catch (InterruptedException interruptedException) {}
/*      */               
/*      */               try {
/*  810 */                 if (HzUtils.getHzInstance() != null)
/*  811 */                 { memberNumbers = HzUtils.getMembers().size();
/*  812 */                   if (memberNumbers >= 1)
/*      */                     break;  } 
/*  814 */               } catch (Exception exception) {}
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*  819 */             for (i = 1; i < nodeSnapshots.size(); i++) {
/*  820 */               NodeSnapshot nodeSnapshot = nodeSnapshots.get(i);
/*      */               
/*      */               try {
/*  823 */                 NmInternal nmInternal = HcceUtils.getNmRmiClient(nodeSnapshot
/*  824 */                     .getNodeName(), nodeSnapshot.getIpAddress());
/*  825 */                 ClusterManager.logger.info("Start HC-Cluster: Start HC-Node {}", nodeSnapshot);
/*  826 */                 nmInternal.restartNode();
/*  827 */                 Thread.sleep(100L);
/*  828 */               } catch (Exception ex) {
/*  829 */                 ClusterManager.logger.error("Start HC-Cluster: Failed to start HC-Node: {}", nodeSnapshot
/*      */                     
/*  831 */                     .getNodeName(), ex);
/*      */               }
/*      */             
/*      */             }
/*      */           
/*      */           }
/*  837 */         })).start();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<NodeSnapshot> shutdownCluster() throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException {
/*  849 */     checkAtActiveOrStandbyState();
/*      */     
/*  851 */     this.topologyManager.setFreezeRegService();
/*      */     
/*  853 */     this.fmeManager.setFreezeFmeManagement();
/*  854 */     Set<Member> members = HzUtils.getMembers();
/*  855 */     Member coordinator = null;
/*  856 */     List<Member> otherMembers = new ArrayList<>();
/*  857 */     List<NodeSnapshot> nodeSnapshots = new ArrayList<>();
/*  858 */     for (Member member : members) {
/*  859 */       nodeSnapshots.add(new NodeSnapshot(member
/*      */             
/*  861 */             .getStringAttribute("nodeName"), member
/*  862 */             .getSocketAddress().getAddress().getHostAddress(), member
/*  863 */             .getSocketAddress().getPort()));
/*  864 */       if (coordinator == null) {
/*  865 */         coordinator = member;
/*      */         continue;
/*      */       } 
/*  868 */       otherMembers.add(member);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  873 */     for (Member member : otherMembers) {
/*      */       try {
/*  875 */         TopologyNode node = this.topologyManager.getNodeByMemberUuid(member.getUuid());
/*  876 */         if (node != null) {
/*      */           
/*  878 */           NmInternal nmInternal = HcceUtils.getNmRmiClient(node.getNodeCfg().getNodeName(), node.getLastRegIpAddress());
/*  879 */           logger.info("Shutdown HC-Cluster: Stop HC-Node:{}", node.getNodeCfg().getNodeName());
/*  880 */           nmInternal.stopNode(); continue;
/*      */         } 
/*  882 */         logger.warn("Shutdown HC-Cluster: The member [{}] was found in HC-Cluster, but it have not registered.", member);
/*      */ 
/*      */       
/*      */       }
/*  886 */       catch (Exception ex) {
/*  887 */         logger.error("Shutdown HC-Cluster: Failed to stop member ({})", member, ex);
/*      */       } 
/*      */     } 
/*      */     
/*  891 */     for (int i = 0; i < 200; i++) {
/*      */       try {
/*  893 */         Thread.sleep(100L);
/*  894 */       } catch (InterruptedException interruptedException) {}
/*      */       
/*  896 */       if (HzUtils.getMembers().size() == 1) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     try {
/*  901 */       NmInternal nmInternal = HcceUtils.getNmRmiClient(coordinator
/*  902 */           .getStringAttribute("nodeName"), 
/*  903 */           HzUtils.getIpAddress(coordinator));
/*  904 */       logger.info("Shutdown HC-Cluster: Stop coordinator");
/*  905 */       nmInternal.stopNode();
/*  906 */     } catch (Exception ex) {
/*  907 */       logger.error("Failed to stop coordinator ({})", coordinator, ex);
/*      */     } 
/*  909 */     return nodeSnapshots;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void addTopologyNodeCfg(TopologyNodeCfg cfg) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException, TopologyNodeCfgException {
/*  915 */     checkAtActiveOrStandbyState();
/*  916 */     this.topologyManager.addNode(cfg);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeTopologyNodeCfg(String groupName, String nodeName) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException, ClusterManagerOperationException {
/*  922 */     checkAtActiveOrStandbyState();
/*  923 */     if (this.hcceEnv.getCurrentGroupName().equals(groupName) && this.hcceEnv
/*  924 */       .getNodeName().equals(nodeName)) {
/*  925 */       throw new ClusterManagerOperationException("The node " + nodeName + " is the coordinator currently. Remove the node configuration of a coordinator is prohibited");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  930 */     if (this.topologyManager.isNodeAlreadyRegistered(groupName, nodeName)) stopNode(nodeName); 
/*  931 */     this.topologyManager.removeNode(groupName, nodeName);
/*      */   }
/*      */ 
/*      */   
/*      */   public void forceReloadTopologyInfo() throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException {
/*  936 */     checkAtActiveOrStandbyState();
/*  937 */     final List<TopologyNode> evictTopologyNodes = this.topologyManager.forceReloadTopologyInfo();
/*  938 */     if (evictTopologyNodes.size() > 0) {
/*  939 */       (new Thread(new Runnable()
/*      */           {
/*      */             public void run()
/*      */             {
/*  943 */               boolean evictSelf = false;
/*  944 */               for (TopologyNode node : evictTopologyNodes) {
/*  945 */                 if (ClusterManager.this.hcceEnv.getNodeName().equals(node.getNodeCfg().getNodeName())) {
/*  946 */                   evictSelf = true;
/*      */                   continue;
/*      */                 } 
/*  949 */                 NmInternal nmInternal = HcceUtils.getNmRmiClient(node
/*  950 */                     .getNodeCfg().getNodeName(), node.getLastRegIpAddress());
/*  951 */                 nmInternal.stopNode();
/*      */                 try {
/*  953 */                   Thread.sleep(10000L);
/*  954 */                 } catch (InterruptedException interruptedException) {}
/*      */               } 
/*      */ 
/*      */               
/*  958 */               if (evictSelf) {
/*      */                 try {
/*  960 */                   Thread.sleep(500L);
/*  961 */                 } catch (InterruptedException interruptedException) {}
/*      */                 
/*  963 */                 ClusterManager.this.nodeManager.stop();
/*      */               }
/*      */             
/*      */             }
/*  967 */           })).start();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void modifyTopologyCfgNode(TopologyNodeCfg newCfg) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException, TopologyNodeCfgException {
/*  975 */     checkAtActiveOrStandbyState();
/*  976 */     this.topologyManager.modifyNode(newCfg);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void submitFmeDefinition(FmeDefinition fmeDefinition) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException, InvalidFmeDefinitionException {
/*  982 */     checkAtActiveOrStandbyState();
/*  983 */     this.fmeManager.updateFmeDefinition(fmeDefinition);
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeFmeDefinition(String groupName, String fmeName) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException {
/*  988 */     checkAtActiveOrStandbyState();
/*  989 */     this.fmeManager.removeFmeDefinition(groupName, fmeName);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void submitFmeDefTable(String groupName, FmeDefTable fmeDefTable) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException, InvalidFmeDefinitionException, FmeStaleVersionException {
/*  995 */     checkAtActiveOrStandbyState();
/*  996 */     this.fmeManager.updateDefList(groupName, fmeDefTable);
/*      */   }
/*      */ 
/*      */   
/*      */   public void addDynamicConfig(DynamicConfig dynamicConfig) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException {
/* 1001 */     checkAtActiveOrStandbyState();
/* 1002 */     this.dynaCfgManager.addDynamicConfig(dynamicConfig);
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeDynamicConfig(DynamicConfigPk dynamicConfigPk) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException {
/* 1007 */     checkAtActiveOrStandbyState();
/* 1008 */     this.dynaCfgManager.removeDynamicConfig(dynamicConfigPk);
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateDynamicConfig(DynamicConfig dynamicConfig) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException {
/* 1013 */     checkAtActiveOrStandbyState();
/* 1014 */     this.dynaCfgManager.updateDynamicConfig(dynamicConfig);
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeFmeConfigs(String groupName, String fmeName) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException {
/* 1019 */     checkAtActiveOrStandbyState();
/* 1020 */     this.dynaCfgManager.removeFmeConfigs(groupName, fmeName);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<DynamicConfigPk, DynamicConfig> getFmeConfigs(String groupName, String fmeName) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException {
/* 1026 */     checkAtActiveOrStandbyState();
/* 1027 */     return this.dynaCfgManager.getFmeConfigs(groupName, fmeName);
/*      */   }
/*      */ 
/*      */   
/*      */   public void resetFme(String fmeName) throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException {
/* 1032 */     checkAtActiveOrStandbyState();
/* 1033 */     this.fmeManager.resetFme(fmeName);
/*      */   }
/*      */   
/*      */   public CmState getState() {
/* 1037 */     return this.cmState.get();
/*      */   }
/*      */   
/*      */   public boolean isCoordinator() {
/* 1041 */     return this.imCoordinator.get();
/*      */   }
/*      */   
/*      */   protected FmeManager getFmeManager() {
/* 1045 */     return this.fmeManager;
/*      */   }
/*      */   
/*      */   protected TopologyManager getTopologyManager() {
/* 1049 */     return this.topologyManager;
/*      */   }
/*      */   
/*      */   public HcceEnv getHcceEnv() {
/* 1053 */     return this.hcceEnv;
/*      */   }
/*      */   
/*      */   @Scheduled(cron = "${hcce.clientNode.heartbeat.checker.cron:20/30 * * * * ?}")
/*      */   public void checkClientHeartBeat() {
/* 1058 */     if (isCoordinator()) {
/* 1059 */       logger.debug("Check client heart beat.");
/* 1060 */       IMap<String, ClientHeartbeat> heartbeatMap = HzUtils.getMap((HzDistObjEnum)HzMap.ClientHeartbeat);
/* 1061 */       Set<String> removedHeartbeats = new HashSet<>();
/* 1062 */       long now = (new Date()).getTime();
/* 1063 */       for (ClientHeartbeat heartbeat : heartbeatMap.values()) {
/* 1064 */         if (now - heartbeat.getTime().getTime() > (this.heartbeatFailSeconds * 1000)) {
/* 1065 */           this.topologyManager.unregisterClientNode(heartbeat.getNodeName());
/* 1066 */           removedHeartbeats.add(heartbeat.getId()); continue;
/*      */         } 
/* 1068 */         this.topologyManager.registerClientNode(heartbeat.getNodeName(), heartbeat.getIp());
/*      */       } 
/*      */ 
/*      */       
/* 1072 */       for (String each : removedHeartbeats)
/* 1073 */         heartbeatMap.remove(each); 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\core\ClusterManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */