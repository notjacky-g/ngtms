/*     */ package com.hwacom.ngtms.hcce.core;
/*     */ 
/*     */ import com.hazelcast.client.HazelcastClient;
/*     */ import com.hazelcast.client.config.ClientConfig;
/*     */ import com.hazelcast.config.Config;
/*     */ import com.hazelcast.core.Hazelcast;
/*     */ import com.hazelcast.core.HazelcastInstance;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.core.Member;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtilsSetter;
/*     */ import com.hwacom.ngtms.base.oplog.service.BaseOpLogger;
/*     */ import com.hwacom.ngtms.base.oplog.shared.CoreSystem;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*     */ import com.hwacom.ngtms.base.sysperflog.SysPerfLogListener;
/*     */ import com.hwacom.ngtms.base.sysperflog.service.SysPerfLog;
/*     */ import com.hwacom.ngtms.base.sysperflog.service.SysPerfLogService;
/*     */ import com.hwacom.ngtms.base.util.ApplicationContextHelper;
/*     */ import com.hwacom.ngtms.hcce.core.exception.ClusterManagerNotReadyException;
/*     */ import com.hwacom.ngtms.hcce.core.exception.FmeOperationException;
/*     */ import com.hwacom.ngtms.hcce.core.exception.NodeManagerNotReadyException;
/*     */ import com.hwacom.ngtms.hcce.core.exception.RegisterNodeException;
/*     */ import com.hwacom.ngtms.hcce.core.message.CmInternal;
/*     */ import com.hwacom.ngtms.hcce.fme.controller.FmeController;
/*     */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*     */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*     */ import com.hwacom.ngtms.hcce.shared.TopologyInfo;
/*     */ import com.hwacom.ngtms.hcce.shared.TopologyNode;
/*     */ import com.hwacom.ngtms.hcce.util.HcceUtils;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.annotation.PreDestroy;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Component
/*     */ public class NodeManager
/*     */ {
/*  53 */   private static Logger logger = LoggerFactory.getLogger(NodeManager.class);
/*     */   private volatile boolean forceStop;
/*     */   
/*  56 */   public enum NmState { Idle,
/*  57 */     Starting,
/*  58 */     Running,
/*  59 */     Stop; }
/*     */ 
/*     */   
/*     */   private enum NmEvent {
/*  63 */     Start,
/*  64 */     Stop,
/*  65 */     RegisterSuccess,
/*  66 */     RegisterFail,
/*  67 */     Restart;
/*     */   }
/*     */ 
/*     */   
/*  71 */   private volatile AtomicReference<NmState> nmState = new AtomicReference<>();
/*     */   
/*     */   private HazelcastInstance hzInstance;
/*     */   
/*     */   @Autowired
/*     */   protected ApplicationContext applicationContext;
/*     */   
/*     */   @Autowired
/*     */   private Config hazelcastConfig;
/*     */   @Autowired
/*     */   private ClientConfig hazelcastClientConfig;
/*  82 */   private Object fsmSyncObj = new Object();
/*     */   @Autowired
/*     */   private ClusterManager hcClusterManager;
/*     */   @Autowired
/*     */   private FmeController fmeController;
/*     */   @Autowired
/*     */   private HcceEnv hcceEnv;
/*     */   
/*     */   @PostConstruct
/*     */   protected void postConstruct() {
/*  92 */     ApplicationContextHelper.getInstance().setAppContext(this.applicationContext);
/*  93 */     this.hcClusterManager
/*  94 */       .getFmeManager()
/*  95 */       .setFmeManagementFirstDelay(this.hcceEnv.getFmeManagementFirstDelay());
/*  96 */     this.hcClusterManager.getFmeManager().setFmeManagementInterval(this.hcceEnv.getFmeManagementInterval());
/*  97 */     if (this.sysPerfLogService != null)
/*  98 */       this.sysPerfLogService.addSysPerfLogListener(this.sysPerfLogListener); 
/*     */   } @Autowired
/*     */   private BaseOpLogger opLogger; @Autowired
/*     */   private SysPerfLogService sysPerfLogService; private long startTime; private SysPerfLogListener sysPerfLogListener; private NodeManager() {
/* 102 */     this.sysPerfLogListener = new SysPerfLogListener()
/*     */       {
/*     */         public void onSysPerfLog(SysPerfLog sysPerfLog)
/*     */         {
/*     */           try {
/* 107 */             if (HzUtils.getHzInstance() != null) {
/* 108 */               IMap<String, SysPerfLog> sysPerfLogMap = HzUtils.getMap((HzDistObjEnum)HzMap.SysPerfLog);
/* 109 */               sysPerfLogMap.put(sysPerfLog.getModule() + "." + sysPerfLog.getJobName(), sysPerfLog);
/*     */             } 
/* 111 */           } catch (Exception ex) {
/* 112 */             NodeManager.logger.warn("Failed to put sysPerfLog to HzMap.SysPerfLog", ex.getMessage());
/*     */           }  }
/*     */       };
/*     */     this.startTime = System.currentTimeMillis();
/*     */     this.nmState.set(NmState.Idle);
/*     */   } public void start() {
/* 118 */     fsm(NmEvent.Start);
/*     */   }
/*     */   
/*     */   public void stop() {
/* 122 */     fsm(NmEvent.Stop);
/*     */   }
/*     */   
/*     */   @PreDestroy
/*     */   public void stopFromContainer() {
/* 127 */     if (this.hcClusterManager.isCoordinator()) {
/* 128 */       this.hcClusterManager.getTopologyManager().setFreezeRegService();
/* 129 */       this.hcClusterManager.getFmeManager().setFreezeFmeManagement();
/*     */     } 
/* 131 */     fsm(NmEvent.Stop);
/*     */   }
/*     */   
/*     */   public void restart() {
/* 135 */     fsm(NmEvent.Restart);
/*     */   }
/*     */   
/*     */   private void fsm(NmEvent event) {
/* 139 */     synchronized (this.fsmSyncObj) {
/* 140 */       switch ((NmState)this.nmState.get()) {
/*     */         case Start:
/* 142 */           idleStateDo(event);
/*     */           break;
/*     */         case Restart:
/* 145 */           startingStateDo(event);
/*     */           break;
/*     */         case RegisterSuccess:
/* 148 */           runningStateDo(event);
/*     */           break;
/*     */         case RegisterFail:
/* 151 */           stopStateDo(event);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void idleStateDo(NmEvent event) {
/* 158 */     switch (event) {
/*     */       case Start:
/* 160 */         startingStateEntry();
/*     */         return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 169 */     logger.warn("Receive unexpected event {} at state ", event, this.nmState.get());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void startingStateEntry() {
/* 175 */     setState(NmState.Starting);
/* 176 */     this.forceStop = false;
/* 177 */     _start();
/*     */   }
/*     */   
/*     */   private void startingStateDo(NmEvent event) {
/* 181 */     switch (event) {
/*     */       case Start:
/*     */       case Restart:
/*     */         return;
/*     */       
/*     */       case RegisterSuccess:
/* 187 */         if (this.forceStop) { stopStateEntry(); }
/* 188 */         else { runningStateEntry(); }
/*     */       
/*     */       case RegisterFail:
/* 191 */         stopStateEntry();
/*     */ 
/*     */ 
/*     */       
/*     */       case Stop:
/* 196 */         this.forceStop = true;
/*     */     } 
/*     */ 
/*     */     
/* 200 */     logger.warn("Receive unexpected event {} at state ", event, this.nmState.get());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void runningStateEntry() {
/* 206 */     this.fmeController.start();
/* 207 */     setState(NmState.Running);
/*     */   }
/*     */   
/*     */   private void runningStateDo(NmEvent event) {
/* 211 */     switch (event) {
/*     */ 
/*     */       
/*     */       case Restart:
/* 215 */         stopStateEntry();
/* 216 */         fsm(NmEvent.Start);
/*     */         return;
/*     */ 
/*     */       
/*     */       case Stop:
/* 221 */         stopStateEntry();
/*     */         return;
/*     */     } 
/* 224 */     logger.warn("Receive unexpected event {} at state ", event, this.nmState.get());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void stopStateEntry() {
/* 231 */     _stop();
/*     */   }
/*     */   
/*     */   private void stopStateDo(NmEvent event) {
/* 235 */     switch (event) {
/*     */       case Start:
/* 237 */         startingStateEntry();
/*     */       
/*     */       case Restart:
/* 240 */         startingStateEntry();
/*     */ 
/*     */       
/*     */       case Stop:
/*     */         return;
/*     */     } 
/*     */     
/* 247 */     logger.warn("Receive unexpected event {} at state ", event, this.nmState.get());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NmState getState() {
/* 265 */     return this.nmState.get();
/*     */   }
/*     */   
/*     */   private void setState(NmState newState) {
/* 269 */     logger.info("NodeManage change state from {} to {}", this.nmState.get(), newState);
/* 270 */     this.nmState.set(newState);
/*     */   }
/*     */   
/*     */   private void _start() {
/* 274 */     logger.info("Start NodeManager...");
/* 275 */     this.opLogger.addLog(CoreSystem.HCCE
/* 276 */         .getDefaultUserId(), this.hcceEnv
/* 277 */         .getLocalIpAddress(), CoreSystem.HCCE
/* 278 */         .toString(), this.hcceEnv
/* 279 */         .getNodeName(), OperationResult.SUCCESS, "hcce.oplog.nodeStart", new Object[] { this.hcceEnv
/*     */ 
/*     */           
/* 282 */           .getNodeName(), this.hcceEnv
/* 283 */           .getCurrentGroupName() });
/*     */     
/*     */     try {
/* 286 */       Thread task = new Thread(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 290 */               boolean result = false;
/*     */               
/*     */               try {
/* 293 */                 if (NodeManager.this.checkNodeRegistrable()) {
/*     */ 
/*     */                   
/* 296 */                   NodeManager.logger.info("Start Hazelcast");
/* 297 */                   if (NodeManager.this.forceStop) {
/* 298 */                     NodeManager.logger.info("Force stop registration");
/*     */                   } else {
/* 300 */                     NodeManager.this.hzInstance = Hazelcast.newHazelcastInstance(NodeManager.this.hazelcastConfig);
/* 301 */                     HzUtilsSetter.setHzInstance(NodeManager.this.hzInstance);
/* 302 */                     NodeManager.this.hcceEnv.setLocalIpAddress((
/* 303 */                         (InetSocketAddress)NodeManager.this.hzInstance.getLocalEndpoint().getSocketAddress())
/* 304 */                         .getAddress()
/* 305 */                         .getHostAddress());
/*     */ 
/*     */                     
/* 308 */                     NodeManager.this.hcClusterManager.start();
/* 309 */                     if (NodeManager.this.registerNode()) result = true; 
/*     */                   } 
/*     */                 } 
/* 312 */               } catch (Exception e) {
/* 313 */                 NodeManager.logger.info("Current Instances: {}", Hazelcast.getAllHazelcastInstances());
/* 314 */                 NodeManager.logger.error("Failed to start NodeManager", e);
/*     */               } finally {
/* 316 */                 if (result) {
/* 317 */                   NodeManager.logger.info("Register to ClusterManager successfully!");
/* 318 */                   NodeManager.this.fsm(NodeManager.NmEvent.RegisterSuccess);
/*     */                 } else {
/* 320 */                   NodeManager.logger.warn("Failed to register to ClusterManager!");
/* 321 */                   NodeManager.this.fsm(NodeManager.NmEvent.RegisterFail);
/*     */                 } 
/*     */               } 
/*     */             }
/*     */           });
/* 326 */       task.start();
/* 327 */     } catch (Exception ex) {
/* 328 */       logger.error("Failed to start NodeManager", ex);
/* 329 */       fsm(NmEvent.Stop);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void _stop() {
/* 334 */     logger.info("Stop NodeManager...");
/* 335 */     this.opLogger.addLog(CoreSystem.HCCE
/* 336 */         .getDefaultUserId(), this.hcceEnv
/* 337 */         .getLocalIpAddress(), CoreSystem.HCCE
/* 338 */         .toString(), this.hcceEnv
/* 339 */         .getNodeName(), OperationResult.SUCCESS, "hcce.oplog.nodeStop", new Object[] { this.hcceEnv
/*     */ 
/*     */           
/* 342 */           .getNodeName(), this.hcceEnv
/* 343 */           .getCurrentGroupName() });
/* 344 */     setState(NmState.Stop);
/* 345 */     this.fmeController.stop();
/* 346 */     this.hcClusterManager.stop();
/* 347 */     if (this.hzInstance != null) {
/* 348 */       HzUtilsSetter.setHzInstance(null);
/* 349 */       this.hzInstance.getLifecycleService().shutdown();
/* 350 */       this.hzInstance = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkNodeRegistrable() {
/* 361 */     if (logger.isDebugEnabled())
/* 362 */       logger.debug("start checking whether the node {} could register to HC-Cluster", this.hcceEnv
/* 363 */           .getNodeName()); 
/* 364 */     Boolean result = Boolean.FALSE;
/* 365 */     HazelcastInstance client = null;
/*     */     try {
/* 367 */       client = HazelcastClient.newHazelcastClient(this.hazelcastClientConfig);
/* 368 */     } catch (Exception e) {
/* 369 */       logger.info("Failed to connect HC-Cluster with HZ Client, register this node to a new formed HC-Cluster directly.");
/*     */       
/* 371 */       return true;
/*     */     } 
/*     */     
/*     */     try {
/* 375 */       IMap<String, TopologyInfo> topologyMap = client.getMap(HzMap.TopologyInfo.toHzName());
/* 376 */       TopologyInfo topologyInfo = (TopologyInfo)topologyMap.get(this.hcceEnv.getCurrentGroupName());
/* 377 */       if (topologyInfo == null) {
/* 378 */         logger.error("HC-Cluster: {} found, but the topology infromation was not found", this.hcceEnv
/*     */             
/* 380 */             .getCurrentGroupName());
/* 381 */         return false;
/*     */       } 
/* 383 */       if (topologyInfo.getNodeMap() == null) {
/* 384 */         logger.error("HC-Cluster: {} found, but the node name \"{}\" does not defined in topology", this.hcceEnv
/*     */             
/* 386 */             .getCurrentGroupName(), this.hcceEnv
/* 387 */             .getNodeName());
/* 388 */         result = Boolean.valueOf(false);
/*     */       } 
/* 390 */       TopologyNode topologyNode = (TopologyNode)topologyInfo.getNodeMap().get(this.hcceEnv.getNodeName());
/* 391 */       if (topologyNode != null) {
/* 392 */         if (!topologyNode.isRegistered()) {
/* 393 */           logger.info("HC-Cluster: {} found, and the node name \"{}\" is registrable, join this node to the cluster.", this.hcceEnv
/*     */               
/* 395 */               .getCurrentGroupName(), this.hcceEnv
/* 396 */               .getNodeName());
/* 397 */           result = Boolean.valueOf(true);
/*     */         } else {
/* 399 */           logger.error("HC-Cluster: {} found, but the node name \"{}\" has been registered", this.hcceEnv
/*     */               
/* 401 */               .getCurrentGroupName(), this.hcceEnv
/* 402 */               .getNodeName());
/* 403 */           result = Boolean.valueOf(false);
/*     */         } 
/*     */       } else {
/* 406 */         logger.error("HC-Cluster: {} found, but the node name \"{}\" does not defined in topology", this.hcceEnv
/*     */             
/* 408 */             .getCurrentGroupName(), this.hcceEnv
/* 409 */             .getNodeName());
/* 410 */         result = Boolean.valueOf(false);
/*     */       } 
/* 412 */     } catch (Exception e) {
/* 413 */       logger.error("Failed to check node registerable to HC-Cluster", e);
/* 414 */       return false;
/*     */     } finally {
/* 416 */       client.shutdown();
/*     */     } 
/* 418 */     return result.booleanValue();
/*     */   }
/*     */   
/*     */   private boolean registerNode() {
/* 422 */     logger.info("Begin registering {} to HC-Cluster", this.hcceEnv.getNodeName());
/* 423 */     Member localMember = this.hzInstance.getCluster().getLocalMember();
/* 424 */     boolean result = false;
/* 425 */     CmInternal cmInternal = HcceUtils.getCmRmiClient();
/* 426 */     for (int i = 0; i < 10; i++) {
/*     */       try {
/* 428 */         if (this.forceStop) {
/* 429 */           logger.info("Force stop registration!");
/*     */           break;
/*     */         } 
/* 432 */         Thread.sleep(1000L);
/* 433 */         cmInternal.registerNode(localMember.getUuid(), this.hcceEnv.getNodeName());
/* 434 */         result = true;
/*     */         break;
/* 436 */       } catch (ClusterManagerNotReadyException e) {
/* 437 */         logger.info("Failed to register current node, ClusterManager is not ready, try again later! ({})", 
/*     */             
/* 439 */             Integer.valueOf(i));
/* 440 */       } catch (RegisterNodeException e) {
/*     */         
/* 442 */         logger.error("Failed to register current node", (Throwable)e);
/*     */         break;
/* 444 */       } catch (Exception e) {
/* 445 */         logger.info("Failed to register current node, try again later! ({})", Integer.valueOf(i), e);
/*     */       } 
/*     */     } 
/* 448 */     return result;
/*     */   }
/*     */   
/*     */   public FmeController getFmeController() {
/* 452 */     return this.fmeController;
/*     */   }
/*     */   
/*     */   private void checkAtRunningState() throws NodeManagerNotReadyException {
/* 456 */     if (this.nmState.get() != NmState.Running) {
/* 457 */       throw new NodeManagerNotReadyException("The state of NodeManager is " + this.nmState
/* 458 */           .get() + ", not in runngin state");
/*     */     }
/*     */   }
/*     */   
/*     */   public void addFme(String fmeName, String className, String description) throws NodeManagerNotReadyException, FmeOperationException {
/* 463 */     checkAtRunningState();
/* 464 */     this.fmeController.addFme(fmeName, className, description);
/*     */   }
/*     */   
/*     */   public void startFme(String fmeName) throws NodeManagerNotReadyException, FmeOperationException {
/* 468 */     checkAtRunningState();
/* 469 */     this.fmeController.startFme(fmeName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeFme(String fmeName) throws NodeManagerNotReadyException {
/* 478 */     checkAtRunningState();
/* 479 */     this.fmeController.removeFme(fmeName);
/*     */   }
/*     */   
/*     */   public long getStartTime() {
/* 483 */     return this.startTime;
/*     */   }
/*     */   
/*     */   public HcceEnv getHcceEnv() {
/* 487 */     return this.hcceEnv;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\core\NodeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */