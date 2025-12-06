/*     */ package com.hwacom.ngtms.hcce.fme.manager;
/*     */ 
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import com.google.gwt.thirdparty.guava.common.base.Objects;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.core.Member;
/*     */ import com.hazelcast.core.TransactionalMap;
/*     */ import com.hazelcast.query.Predicates;
/*     */ import com.hazelcast.transaction.TransactionContext;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.oplog.service.BaseOpLogger;
/*     */ import com.hwacom.ngtms.base.oplog.shared.CoreSystem;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*     */ import com.hwacom.ngtms.hcce.config.DynaConfigManager;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfigPk;
/*     */ import com.hwacom.ngtms.hcce.core.exception.FmeStaleVersionException;
/*     */ import com.hwacom.ngtms.hcce.core.exception.InvalidFmeDefinitionException;
/*     */ import com.hwacom.ngtms.hcce.core.message.NmInternal;
/*     */ import com.hwacom.ngtms.hcce.fme.controller.runtime.FmResourceInfo;
/*     */ import com.hwacom.ngtms.hcce.fme.manager.model.FmeDefinition;
/*     */ import com.hwacom.ngtms.hcce.fme.manager.model.FmeDefinitionPk;
/*     */ import com.hwacom.ngtms.hcce.fme.manager.model.ProhibitNode;
/*     */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*     */ import com.hwacom.ngtms.hcce.shared.FmeDefTable;
/*     */ import com.hwacom.ngtms.hcce.shared.FmeExeStatus;
/*     */ import com.hwacom.ngtms.hcce.shared.FmeState;
/*     */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*     */ import com.hwacom.ngtms.hcce.shared.TopologyInfo;
/*     */ import com.hwacom.ngtms.hcce.shared.TopologyNode;
/*     */ import com.hwacom.ngtms.hcce.topology.TopologyManager;
/*     */ import com.hwacom.ngtms.hcce.util.HcceUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.PreDestroy;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Component
/*     */ public class FmeManager
/*     */ {
/*  62 */   private static Logger logger = LoggerFactory.getLogger(FmeManager.class);
/*  63 */   private long fmeManagementInterval = 5L;
/*  64 */   private long fmeManagementFirstDelay = 5L;
/*     */   @Autowired
/*     */   private FmeDataService fmeDataService;
/*     */   @Autowired
/*     */   private TopologyManager topologyManager;
/*     */   @Autowired
/*     */   private DynaConfigManager dynaConfigManager;
/*     */   @Autowired
/*     */   private HcceEnv hcceEnv;
/*  73 */   private ConcurrentHashMap<String, String> resetFmeMap = new ConcurrentHashMap<>(); @Autowired
/*     */   private BaseOpLogger opLogger; private IMap<String, FmeExeStatus> fmeExeStatusMap;
/*  75 */   private final ScheduledExecutorService fmeMgrExecutor = Executors.newSingleThreadScheduledExecutor((new ThreadFactoryBuilder())
/*  76 */       .setNameFormat("fmeManager-thread").build()); private IMap<String, FmeDefTable> fmeDefTableMap; protected IMap<String, FmResourceInfo> fmResourceMap;
/*  77 */   private ScheduledFuture<?> fmeMgrCcheduledFuture = null;
/*  78 */   private Object fmeManagementSyncObj = new Object();
/*  79 */   private AtomicBoolean freezeFmeManagement = new AtomicBoolean();
/*     */ 
/*     */   
/*     */   public void start() {
/*  83 */     this.freezeFmeManagement.set(false);
/*  84 */     this.fmeExeStatusMap = HzUtils.getMap((HzDistObjEnum)HzMap.FmeExeStatus);
/*  85 */     this.fmeDefTableMap = HzUtils.getMap((HzDistObjEnum)HzMap.FmeDefTable);
/*  86 */     this.fmResourceMap = HzUtils.getMap((HzDistObjEnum)HzMap.FmResource);
/*     */     try {
/*  88 */       loadDefTable(this.hcceEnv.getPrimaryGroupName());
/*  89 */       loadDefTable(this.hcceEnv.getBackupGroupName());
/*  90 */     } catch (InvalidFmeDefinitionException ex) {
/*  91 */       throw new RuntimeException("Failed to load FME Definition Table from DB", ex);
/*     */     } 
/*  93 */     refreshFmeStatusTable();
/*  94 */     TopologyInfo topologyInfo = this.topologyManager.getTopologyInfo(this.hcceEnv.getCurrentGroupName());
/*  95 */     long curTime = System.currentTimeMillis();
/*  96 */     if (curTime - topologyInfo.getStartTime().getTime() > this.fmeManagementFirstDelay * 1000L) {
/*  97 */       this
/*  98 */         .fmeMgrCcheduledFuture = this.fmeMgrExecutor.scheduleWithFixedDelay(this.fmeManagementJob, 1L, this.fmeManagementInterval, TimeUnit.SECONDS);
/*     */     } else {
/*     */       
/* 101 */       this
/* 102 */         .fmeMgrCcheduledFuture = this.fmeMgrExecutor.scheduleWithFixedDelay(this.fmeManagementJob, this.fmeManagementFirstDelay, this.fmeManagementInterval, TimeUnit.SECONDS);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 108 */     if (this.fmeMgrCcheduledFuture != null) this.fmeMgrCcheduledFuture.cancel(true); 
/*     */   }
/*     */   
/*     */   @PreDestroy
/*     */   protected void destroy() {
/* 113 */     this.fmeMgrExecutor.shutdownNow();
/*     */   }
/*     */   
/*     */   public FmeDefTable getFmeDefTable(String groupName) {
/* 117 */     return (FmeDefTable)this.fmeDefTableMap.get(groupName);
/*     */   }
/*     */   
/*     */   private void setFmeDefTable(String groupName, FmeDefTable fmeDefTable) {
/* 121 */     this.fmeDefTableMap.set(groupName, fmeDefTable);
/*     */   }
/*     */   
/*     */   private void loadDefTable(String groupName) throws InvalidFmeDefinitionException {
/* 125 */     FmeDefTable fmeDefTable = getFmeDefTable(groupName);
/* 126 */     if (fmeDefTable != null) {
/* 127 */       logger.debug("FmeDefTable of {} exists in IMDG, skip loading procedure", groupName);
/*     */       return;
/*     */     } 
/* 130 */     logger.info("Load FME Defitions from DB, groupName={}", groupName);
/* 131 */     fmeDefTable = this.fmeDataService.loadFmeDefTable(groupName);
/* 132 */     setFmeDefTable(groupName, fmeDefTable);
/*     */ 
/*     */     
/* 135 */     HashSet<FmeDefinitionPk> fmeIdSet = new HashSet<>();
/* 136 */     for (FmeDefinition fmeDefinition : fmeDefTable.getDefinitions()) {
/* 137 */       fmeIdSet.add(fmeDefinition.getPk());
/*     */     }
/* 139 */     IMap<DynamicConfigPk, DynamicConfig> dynaCfgMap = HzUtils.getMap((HzDistObjEnum)HzMap.DynamicConfig);
/* 140 */     for (DynamicConfigPk key : dynaCfgMap.keySet(Predicates.equal("groupName", groupName))) {
/* 141 */       if (!fmeIdSet.contains(new FmeDefinitionPk(key.getGroupName(), key.getFmeName()))) {
/* 142 */         this.dynaConfigManager.removeDynamicConfig(key);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isNodeAssignedFme(String nodeName) {
/* 148 */     for (FmeExeStatus status : this.fmeExeStatusMap.values()) {
/* 149 */       if (nodeName.equals(status.getAssignedNodeName())) {
/* 150 */         return true;
/*     */       }
/*     */     } 
/* 153 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refreshFmeStatusTable() {
/* 161 */     String assignedMemberUuid = null;
/* 162 */     HashSet<String> memberUuids = new HashSet<>();
/* 163 */     for (Member m : HzUtils.getMembers()) {
/* 164 */       memberUuids.add(m.getUuid());
/*     */     }
/* 166 */     synchronized (this.fmeManagementSyncObj) {
/* 167 */       for (FmeExeStatus fmeExeStatus : this.fmeExeStatusMap.values()) {
/* 168 */         assignedMemberUuid = fmeExeStatus.getAssignedMemberUuid();
/* 169 */         if (assignedMemberUuid == null) {
/*     */           continue;
/*     */         }
/* 172 */         if (memberUuids.contains(assignedMemberUuid)) {
/*     */           continue;
/*     */         }
/* 175 */         logger.info("The HC-Node:{} disappeared, reset execution status of FME:{}", fmeExeStatus
/*     */             
/* 177 */             .getAssignedNodeName(), fmeExeStatus
/* 178 */             .getFmeName());
/* 179 */         fmeExeStatus.setState(FmeState.NoNode);
/* 180 */         fmeExeStatus.resetAssignedNodeInfo();
/* 181 */         this.fmeExeStatusMap.put(fmeExeStatus.getFmeName(), fmeExeStatus);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateFmeDefinition(FmeDefinition fmeDefinition) throws InvalidFmeDefinitionException {
/* 188 */     String groupName = fmeDefinition.getGroupName();
/* 189 */     FmeDefTable fmeDefTable = getFmeDefTable(groupName);
/* 190 */     List<FmeDefinition> definitions = fmeDefTable.getDefinitions();
/* 191 */     if (definitions.contains(fmeDefinition)) {
/* 192 */       definitions.set(definitions.indexOf(fmeDefinition), fmeDefinition);
/*     */     } else {
/* 194 */       definitions.add(fmeDefinition);
/*     */     } 
/* 196 */     this.fmeDataService.verifyFmeDefinitions((List<FmeDefinition>)definitions
/*     */         
/* 198 */         .stream()
/* 199 */         .filter(definition -> Objects.equal(definition.getGroupName(), paramString))
/* 200 */         .collect(Collectors.toList()));
/* 201 */     TransactionContext context = HzUtils.getHzInstance().newTransactionContext();
/* 202 */     context.beginTransaction();
/*     */     try {
/* 204 */       TransactionalMap<String, FmeDefTable> fmeDefMap = HzUtils.getMap(context, (HzDistObjEnum)HzMap.FmeDefTable);
/* 205 */       fmeDefMap.set(groupName, fmeDefTable);
/*     */ 
/*     */ 
/*     */       
/* 209 */       this.fmeDataService.updateFmeDefList(groupName, definitions);
/* 210 */       context.commitTransaction();
/* 211 */     } catch (Throwable t) {
/* 212 */       context.rollbackTransaction();
/* 213 */       throw t;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeFmeDefinition(String groupName, String fmeName) {
/* 218 */     TransactionContext context = HzUtils.getHzInstance().newTransactionContext();
/* 219 */     context.beginTransaction();
/*     */     try {
/* 221 */       TransactionalMap<String, FmeDefTable> fmeDefMap = HzUtils.getMap(context, (HzDistObjEnum)HzMap.FmeDefTable);
/* 222 */       FmeDefTable fmeDefTable = (FmeDefTable)fmeDefMap.get(groupName);
/* 223 */       fmeDefTable.setDefinitions((List)fmeDefTable
/*     */           
/* 225 */           .getDefinitions()
/* 226 */           .stream()
/* 227 */           .filter(fmeDefinition -> !fmeDefinition.getFmeName().equals(paramString))
/* 228 */           .collect(Collectors.toList()));
/* 229 */       fmeDefMap.set(groupName, fmeDefTable);
/*     */ 
/*     */ 
/*     */       
/* 233 */       this.fmeDataService.updateFmeDefList(groupName, fmeDefTable.getDefinitions());
/* 234 */       context.commitTransaction();
/* 235 */     } catch (Throwable t) {
/* 236 */       context.rollbackTransaction();
/* 237 */       throw t;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateDefList(String groupName, FmeDefTable updatedTable) throws InvalidFmeDefinitionException, FmeStaleVersionException {
/* 244 */     FmeDefTable fmeDefTable = getFmeDefTable(groupName);
/* 245 */     if (fmeDefTable != null && 
/* 246 */       !fmeDefTable.getIssueVersion().equals(updatedTable.getIssueVersion())) {
/* 247 */       throw new FmeStaleVersionException("The version number:" + updatedTable
/*     */           
/* 249 */           .getIssueVersion() + " of the updated FmeDefTable is not equal to the version number:" + fmeDefTable
/*     */           
/* 251 */           .getIssueVersion() + " of FmeDefTable on IMDG");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 257 */     List<FmeDefinition> definitions = updatedTable.getDefinitions();
/* 258 */     if (definitions == null)
/* 259 */     { definitions = new ArrayList<>();
/* 260 */       updatedTable.setDefinitions(definitions); }
/* 261 */     else { this.fmeDataService.verifyFmeDefinitions(definitions); }
/*     */     
/* 263 */     TransactionContext context = HzUtils.getHzInstance().newTransactionContext();
/* 264 */     context.beginTransaction();
/*     */     try {
/* 266 */       TransactionalMap<String, FmeDefTable> fmeDefMap = HzUtils.getMap(context, (HzDistObjEnum)HzMap.FmeDefTable);
/* 267 */       long newVersionNo = System.currentTimeMillis();
/* 268 */       updatedTable.setIssueVersion(Long.valueOf(newVersionNo));
/* 269 */       fmeDefMap.set(groupName, updatedTable);
/*     */ 
/*     */ 
/*     */       
/* 273 */       this.fmeDataService.updateFmeDefList(groupName, definitions);
/* 274 */       context.commitTransaction();
/* 275 */     } catch (Throwable t) {
/* 276 */       context.rollbackTransaction();
/* 277 */       throw t;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetFme(String fmeName) {
/* 289 */     this.resetFmeMap.put(fmeName, fmeName);
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
/*     */ 
/*     */ 
/*     */   
/* 324 */   private final Runnable fmeManagementJob = new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try {
/* 329 */           FmeManager.this.fmeManagement();
/* 330 */         } catch (Exception ex) {
/* 331 */           FmeManager.logger.error("Faile to exectue FME management", ex);
/*     */         } 
/*     */       }
/*     */     };
/*     */   
/*     */   private void fmeManagement() {
/* 337 */     if (this.freezeFmeManagement.get())
/* 338 */       return;  synchronized (this.fmeManagementSyncObj) {
/*     */       
/* 340 */       FmeDefTable fmeDefTable = getFmeDefTable(this.hcceEnv.getCurrentGroupName());
/* 341 */       if (fmeDefTable == null) {
/* 342 */         logger.warn("FmeDefTable is empty!");
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */ 
/*     */       
/* 350 */       HashMap<String, FmeDefinition> fmeDefMap = new HashMap<>();
/* 351 */       for (FmeDefinition def : fmeDefTable.getDefinitions()) {
/* 352 */         fmeDefMap.put(def.getFmeName(), def);
/*     */       }
/*     */ 
/*     */       
/* 356 */       for (String fmeName : this.fmeExeStatusMap.keySet()) {
/* 357 */         FmeExeStatus status = (FmeExeStatus)this.fmeExeStatusMap.get(fmeName);
/*     */         
/* 359 */         FmeDefinition fmeDefinition = fmeDefMap.get(fmeName);
/* 360 */         if (fmeDefinition == null) {
/* 361 */           logger.info("The FME:{} is not found in FME defination Table, remove the FME on {}", fmeName, status
/*     */ 
/*     */               
/* 364 */               .getAssignedNodeName());
/* 365 */           removeFme(status.getAssignedNodeName(), fmeName, status.getAssignedMemberIpAddress());
/* 366 */           this.dynaConfigManager.removeFmeConfigs(this.hcceEnv.getCurrentGroupName(), fmeName);
/*     */           
/*     */           continue;
/*     */         } 
/* 370 */         if (this.resetFmeMap.containsKey(fmeName)) {
/* 371 */           removeFme(status.getAssignedNodeName(), fmeName, status.getAssignedMemberIpAddress());
/*     */           
/* 373 */           fmeDefMap.remove(fmeName);
/*     */           continue;
/*     */         } 
/* 376 */         switch (status.getState()) {
/*     */           
/*     */           case Starting:
/* 379 */             fmeDefMap.remove(fmeName);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case Running:
/* 385 */             if (!fmeDefinition.getEnable().booleanValue()) {
/* 386 */               logger.info("The FME:{} is disabled, remove the FME on {}", fmeName, status
/*     */ 
/*     */                   
/* 389 */                   .getAssignedNodeName());
/* 390 */               removeFme(status
/* 391 */                   .getAssignedNodeName(), fmeName, status.getAssignedMemberIpAddress()); continue;
/* 392 */             }  if (isRunningOnProhibitNode(fmeDefinition, status)) {
/* 393 */               logger.info("The FME:{} is running on prohibit node ,remove the FME on {}", fmeName, status
/*     */ 
/*     */                   
/* 396 */                   .getAssignedNodeName());
/* 397 */               removeFme(status
/* 398 */                   .getAssignedNodeName(), fmeName, status.getAssignedMemberIpAddress()); continue;
/* 399 */             }  if (!fmeDefinition.getClassName().equals(status.getFmeClassName())) {
/* 400 */               logger.info("The class name of FME:{} has chagned, remove original FME on {}, and start the new one later", fmeName, status
/*     */ 
/*     */                   
/* 403 */                   .getAssignedNodeName());
/* 404 */               removeFme(status
/* 405 */                   .getAssignedNodeName(), fmeName, status.getAssignedMemberIpAddress());
/*     */               continue;
/*     */             } 
/* 408 */             fmeDefMap.remove(fmeName);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       } 
/* 420 */       this.resetFmeMap.clear();
/*     */       
/* 422 */       for (FmeDefinition def : fmeDefMap.values()) {
/* 423 */         if (def.getEnable().booleanValue()) {
/* 424 */           addFme(def);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addFme(FmeDefinition fmeDefinition) {
/* 438 */     String fmeName = fmeDefinition.getFmeName();
/* 439 */     FmeExeStatus fmeStatus = new FmeExeStatus();
/* 440 */     fmeStatus.setFmeClassName(fmeDefinition.getClassName());
/* 441 */     fmeStatus.setFmeName(fmeName);
/* 442 */     fmeStatus.setState(FmeState.NoNode);
/*     */     
/* 444 */     Map<String, TopologyNode> priorityMembers = getPriorityMembers(fmeDefinition);
/* 445 */     for (TopologyNode topologyNode : priorityMembers.values()) {
/*     */       
/*     */       try {
/* 448 */         NmInternal nmInternal = HcceUtils.getNmRmiClient(topologyNode
/* 449 */             .getNodeCfg().getNodeName(), topologyNode.getLastRegIpAddress());
/* 450 */         nmInternal.addFme(fmeName, fmeDefinition.getClassName(), fmeDefinition.getDescription());
/* 451 */         fmeStatus.setState(FmeState.Starting);
/* 452 */         fmeStatus.setAssignedNodeInfo(topologyNode
/* 453 */             .getNodeCfg().getNodeName(), topologyNode
/* 454 */             .getMemberUuid(), topologyNode
/* 455 */             .getLastRegIpAddress());
/* 456 */         fmeStatus.setStartTime(new Date());
/* 457 */         this.fmeExeStatusMap.set(fmeName, fmeStatus);
/* 458 */         nmInternal.startFme(fmeName);
/* 459 */         logger.info("Add and start FME:{} successfully on HC-Node:{}", fmeName, topologyNode
/*     */ 
/*     */             
/* 462 */             .getNodeCfg().getNodeName());
/* 463 */         this.opLogger.addLog(CoreSystem.HCCE
/* 464 */             .getDefaultUserId(), this.hcceEnv
/* 465 */             .getLocalIpAddress(), CoreSystem.HCCE
/* 466 */             .toString(), topologyNode
/* 467 */             .getNodeCfg().getNodeName(), OperationResult.SUCCESS, "hcce.oplog.startFme", new Object[] { topologyNode
/*     */ 
/*     */               
/* 470 */               .getNodeCfg().getNodeName(), fmeName, this.hcceEnv
/*     */               
/* 472 */               .getCurrentGroupName() });
/*     */         break;
/* 474 */       } catch (Exception ex) {
/* 475 */         fmeStatus.setState(FmeState.NoNode);
/* 476 */         fmeStatus.resetAssignedNodeInfo();
/* 477 */         logger.warn("Failed to start FME:{} on HC-Node:{}, try next one!", new Object[] { fmeName, topologyNode
/*     */               
/* 479 */               .getNodeCfg().getNodeName(), ex });
/* 480 */         this.opLogger.addLog(CoreSystem.HCCE
/* 481 */             .getDefaultUserId(), this.hcceEnv
/* 482 */             .getLocalIpAddress(), CoreSystem.HCCE
/* 483 */             .toString(), topologyNode
/* 484 */             .getNodeCfg().getNodeName(), OperationResult.FAILURE, "hcce.oplog.startFme.fail", new Object[] { topologyNode
/*     */ 
/*     */               
/* 487 */               .getNodeCfg().getNodeName(), fmeName, this.hcceEnv
/*     */               
/* 489 */               .getCurrentGroupName() });
/*     */       } 
/*     */     } 
/*     */     
/* 493 */     if (fmeStatus.getState() == FmeState.NoNode) {
/* 494 */       this.fmeExeStatusMap.put(fmeName, fmeStatus);
/* 495 */       logger.warn("There is no HC-Node could execute FME:{}", fmeName);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeFme(String nodeName, String fmeName, String assignedMemberIpAddress) {
/*     */     try {
/* 501 */       if (assignedMemberIpAddress != null) {
/* 502 */         HcceUtils.getNmRmiClient(nodeName, assignedMemberIpAddress).removeFme(fmeName);
/* 503 */         this.opLogger.addLog(CoreSystem.HCCE
/* 504 */             .getDefaultUserId(), this.hcceEnv
/* 505 */             .getLocalIpAddress(), CoreSystem.HCCE
/* 506 */             .toString(), nodeName, OperationResult.SUCCESS, "hcce.oplog.stopFme", new Object[] { nodeName, fmeName, this.hcceEnv
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 512 */               .getCurrentGroupName() });
/*     */       } 
/* 514 */     } catch (Exception ex) {
/* 515 */       logger.error("Failed to remove FME:" + fmeName, ex);
/*     */     } 
/* 517 */     this.fmeExeStatusMap.remove(fmeName);
/* 518 */     this.fmResourceMap.remove(fmeName);
/*     */   }
/*     */   
/*     */   private boolean isRunningOnProhibitNode(FmeDefinition fmeDefinition, FmeExeStatus status) {
/* 522 */     if (status.getAssignedNodeName() == null) return false; 
/* 523 */     if (fmeDefinition.getProhibitNodes() == null) return false; 
/* 524 */     for (ProhibitNode pn : fmeDefinition.getProhibitNodes()) {
/* 525 */       if (pn.getNodeName().equals(status.getAssignedNodeName())) return true; 
/*     */     } 
/* 527 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<String, TopologyNode> getPriorityMembers(FmeDefinition fmeDefintion) {
/* 536 */     String nodePriority = fmeDefintion.getNodePriority();
/* 537 */     Map<String, TopologyNode> priorityMembers = null;
/*     */     
/* 539 */     if (StringUtils.isNotBlank(nodePriority)) {
/*     */       
/* 541 */       priorityMembers = new LinkedHashMap<>();
/* 542 */       String[] nodePriorityArray = nodePriority.split("\\s*,\\s*");
/* 543 */       for (String nodeName : nodePriorityArray) {
/* 544 */         TopologyNode topologyNode = this.topologyManager.getNodeByName(nodeName);
/* 545 */         if (topologyNode != null && topologyNode.isRegistered()) {
/* 546 */           priorityMembers.put(nodeName, topologyNode);
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 551 */       priorityMembers = this.topologyManager.getRegistedMemberMap();
/*     */     } 
/*     */     
/* 554 */     List<ProhibitNode> prohibitNodes = fmeDefintion.getProhibitNodes();
/* 555 */     if (prohibitNodes != null) {
/* 556 */       for (ProhibitNode prohibitNode : prohibitNodes) {
/* 557 */         priorityMembers.remove(prohibitNode.getNodeName());
/*     */       }
/*     */     }
/*     */     
/* 561 */     return priorityMembers;
/*     */   }
/*     */   
/*     */   public long getFmeManagementFirstDelay() {
/* 565 */     return this.fmeManagementFirstDelay;
/*     */   }
/*     */   
/*     */   public void setFmeManagementFirstDelay(long fmeManagementFirstDelay) {
/* 569 */     this.fmeManagementFirstDelay = fmeManagementFirstDelay;
/*     */   }
/*     */   
/*     */   public long getFmeManagementInterval() {
/* 573 */     return this.fmeManagementInterval;
/*     */   }
/*     */   
/*     */   public void setFmeManagementInterval(long fmeManagementInterval) {
/* 577 */     this.fmeManagementInterval = fmeManagementInterval;
/*     */   }
/*     */   
/*     */   public boolean isFreezeFmeManagement() {
/* 581 */     return this.freezeFmeManagement.get();
/*     */   }
/*     */   
/*     */   public void setFreezeFmeManagement() {
/* 585 */     logger.info("Freeze FME management service");
/* 586 */     this.freezeFmeManagement.set(true);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\manager\FmeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */