/*     */ package com.hwacom.ngtms.hcce.remote;
/*     */ 
/*     */ import com.hazelcast.core.Member;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.oplog.service.BaseOpLogger;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*     */ import com.hwacom.ngtms.base.ssh.CmdExecBinaryResult;
/*     */ import com.hwacom.ngtms.base.ssh.CmdExecResult;
/*     */ import com.hwacom.ngtms.base.ssh.CmdRealTimeResponse;
/*     */ import com.hwacom.ngtms.base.ssh.service.SshClientService;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfigPk;
/*     */ import com.hwacom.ngtms.hcce.core.ClusterManager;
/*     */ import com.hwacom.ngtms.hcce.core.exception.ClusterManagerNotCoordinatorException;
/*     */ import com.hwacom.ngtms.hcce.core.exception.ClusterManagerNotReadyException;
/*     */ import com.hwacom.ngtms.hcce.core.exception.ClusterManagerOperationException;
/*     */ import com.hwacom.ngtms.hcce.core.exception.DisasterRecoveryException;
/*     */ import com.hwacom.ngtms.hcce.core.exception.FmExecutorException;
/*     */ import com.hwacom.ngtms.hcce.core.exception.FmeStaleVersionException;
/*     */ import com.hwacom.ngtms.hcce.core.exception.InvalidFmeDefinitionException;
/*     */ import com.hwacom.ngtms.hcce.core.exception.TopologyNodeCfgException;
/*     */ import com.hwacom.ngtms.hcce.fme.controller.fm.FmeMainBase;
/*     */ import com.hwacom.ngtms.hcce.fme.manager.model.FmeDefinition;
/*     */ import com.hwacom.ngtms.hcce.nodelog.model.NodeMonitorLog;
/*     */ import com.hwacom.ngtms.hcce.nodelog.repository.NodeMonitorLogRepository;
/*     */ import com.hwacom.ngtms.hcce.nodelog.repository.NodeMonitorLogSpecification;
/*     */ import com.hwacom.ngtms.hcce.shared.ClusterMode;
/*     */ import com.hwacom.ngtms.hcce.shared.CmState;
/*     */ import com.hwacom.ngtms.hcce.shared.DynamicConfigDeclare;
/*     */ import com.hwacom.ngtms.hcce.shared.FmeDefTable;
/*     */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*     */ import com.hwacom.ngtms.hcce.shared.NodeSnapshot;
/*     */ import com.hwacom.ngtms.hcce.topology.model.TopologyNodeCfg;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Resource;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.core.io.ClassPathResource;
/*     */ import org.springframework.core.io.FileSystemResource;
/*     */ import org.springframework.data.domain.Page;
/*     */ import org.springframework.data.domain.Pageable;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class CmRemoteImpl
/*     */   implements CmRemote
/*     */ {
/*  61 */   private static Logger logger = LoggerFactory.getLogger(CmRemoteImpl.class);
/*     */   @Autowired
/*     */   private SshClientService sshClientService;
/*     */   @Resource
/*     */   private Environment environment;
/*     */   @Autowired
/*     */   private ClusterManager clusterManager;
/*     */   @Autowired
/*     */   private BaseOpLogger opLogger;
/*     */   @Autowired
/*     */   private NodeMonitorLogRepository nodeMonitorLogRepository;
/*     */   private List<NodeSnapshot> nodeSnapshots;
/*     */   
/*  74 */   public void addTopologyNodeCfg(TopologyNodeCfg cfg) throws TopologyNodeCfgException, ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException { logger.info("Add TopologyNodeCfg from remote, cfg= {}", cfg);
/*  75 */     this.clusterManager.addTopologyNodeCfg(cfg);
/*     */   }
/*     */   
/*     */ 
/*     */   public void removeTopologyNodeCfg(String groupName, String nodeName)
/*     */     throws ClusterManagerNotCoordinatorException, FmExecutorException, ClusterManagerOperationException, ClusterManagerNotReadyException
/*     */   {
/*  82 */     logger.info("Remove TopologyNodeCfg from remote,groupName={}, nodeName= {}", groupName, nodeName);
/*     */     
/*  84 */     this.clusterManager.removeTopologyNodeCfg(groupName, nodeName);
/*     */   }
/*     */   
/*     */ 
/*     */   public void modifyTopologyCfgNode(TopologyNodeCfg newCfg)
/*     */     throws ClusterManagerNotCoordinatorException, TopologyNodeCfgException, ClusterManagerNotReadyException
/*     */   {
/*  91 */     logger.info("Modify TopologyNodeCfg from remote, newCfg= {}", newCfg);
/*  92 */     this.clusterManager.modifyTopologyCfgNode(newCfg);
/*     */   }
/*     */   
/*     */ 
/*     */   public void forceReloadTopologyInfo()
/*     */     throws ClusterManagerNotCoordinatorException, TopologyNodeCfgException, ClusterManagerNotReadyException
/*     */   {
/*  99 */     logger.info("Forece reload topology information from remote");
/* 100 */     this.clusterManager.forceReloadTopologyInfo();
/*     */   }
/*     */   
/*     */ 
/*     */   public void startNode(String nodeName)
/*     */     throws ClusterManagerNotCoordinatorException, ClusterManagerOperationException, ClusterManagerNotReadyException
/*     */   {
/* 107 */     logger.info("Start HC Node from remote, nodeName= {}", nodeName);
/* 108 */     this.clusterManager.startNode(nodeName);
/*     */   }
/*     */   
/*     */ 
/*     */   public void stopNode(String nodeName)
/*     */     throws ClusterManagerNotCoordinatorException, ClusterManagerOperationException, ClusterManagerNotReadyException
/*     */   {
/* 115 */     logger.info("Stop HC Node from remote, nodeName= {}", nodeName);
/* 116 */     this.clusterManager.stopNode(nodeName);
/*     */   }
/*     */   
/*     */ 
/*     */   public void restartNode(String nodeName)
/*     */     throws ClusterManagerNotCoordinatorException, ClusterManagerOperationException, ClusterManagerNotReadyException
/*     */   {
/* 123 */     logger.info("Restart HC Node from remote, nodeName= {}", nodeName);
/* 124 */     this.clusterManager.restartNode(nodeName);
/*     */   }
/*     */   
/*     */   public NodeSnapshot getCoordinatorNodeSnapshot()
/*     */   {
/* 129 */     logger.info("Get coordinator node snapshot from remote");
/* 130 */     Member coordinator = HzUtils.getCoordinator();
/*     */     
/*     */ 
/*     */ 
/* 134 */     return new NodeSnapshot(coordinator.getStringAttribute("nodeName"), coordinator.getSocketAddress().getAddress().getHostAddress(), coordinator.getSocketAddress().getPort());
/*     */   }
/*     */   
/*     */   public void shutdownCluster()
/*     */     throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException
/*     */   {
/* 140 */     logger.info("Shutdown HC-Cluster from remote");
/* 141 */     this.nodeSnapshots = this.clusterManager.shutdownCluster();
/*     */   }
/*     */   
/*     */   public void startCluster()
/*     */     throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException
/*     */   {
/* 147 */     logger.info("Start HC-Cluster from remote");
/* 148 */     if (this.nodeSnapshots == null) throw new NullPointerException("nodeSnapshots is null");
/* 149 */     this.clusterManager.startCluster(this.nodeSnapshots);
/* 150 */     this.nodeSnapshots = null;
/*     */   }
/*     */   
/*     */   public CmState getClusterManagerState()
/*     */   {
/* 155 */     return this.clusterManager.getState();
/*     */   }
/*     */   
/*     */ 
/*     */   public void promoteCluster()
/*     */     throws ClusterManagerNotCoordinatorException, ClusterManagerOperationException, DisasterRecoveryException
/*     */   {
/* 162 */     logger.info("Promote HC-Cluster from remote");
/* 163 */     this.clusterManager.promoteCluster();
/*     */   }
/*     */   
/*     */ 
/*     */   public void demoteCluster()
/*     */     throws ClusterManagerNotCoordinatorException, ClusterManagerOperationException, DisasterRecoveryException
/*     */   {
/* 170 */     logger.info("Demote HC-Cluster from remote");
/* 171 */     this.clusterManager.demoteCluster();
/*     */   }
/*     */   
/*     */   public ClusterMode clusterAcitveOrStandby()
/*     */     throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException
/*     */   {
/* 177 */     return this.clusterManager.activeOrStandby();
/*     */   }
/*     */   
/*     */ 
/*     */   public void setAllowActive(boolean allowActive)
/*     */     throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException, DisasterRecoveryException
/*     */   {
/* 184 */     logger.info("Set allow active from remote, allowActive={}", Boolean.valueOf(allowActive));
/* 185 */     this.clusterManager.setAllowActive(allowActive);
/*     */   }
/*     */   
/*     */ 
/*     */   public void submitFmeDefinition(FmeDefinition fmeDefinition)
/*     */     throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException, InvalidFmeDefinitionException
/*     */   {
/* 192 */     this.clusterManager.submitFmeDefinition(fmeDefinition);
/*     */   }
/*     */   
/*     */ 
/*     */   public void removeFmeDefinition(String groupName, String fmeName)
/*     */     throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException, InvalidFmeDefinitionException
/*     */   {
/* 199 */     this.clusterManager.removeFmeDefinition(groupName, fmeName);
/*     */   }
/*     */   
/*     */ 
/*     */   public void submitFmeDefTable(String groupName, FmeDefTable fmeDefTable)
/*     */     throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException, InvalidFmeDefinitionException, FmeStaleVersionException
/*     */   {
/* 206 */     logger.info("Submit FME Defenition Table from remote, groupName={}, fmeDefTable={}", groupName, fmeDefTable);
/*     */     
/*     */ 
/*     */ 
/* 210 */     this.clusterManager.submitFmeDefTable(groupName, fmeDefTable);
/*     */   }
/*     */   
/*     */   public void addDynamicConfig(DynamicConfig dynamicConfig)
/*     */     throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException
/*     */   {
/* 216 */     logger.info("Add dynamic config from remote, dynamicConfig={}", dynamicConfig);
/* 217 */     this.clusterManager.addDynamicConfig(dynamicConfig);
/*     */   }
/*     */   
/*     */   public void removeDynamicConfig(DynamicConfigPk dynamicConfigPk)
/*     */     throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException
/*     */   {
/* 223 */     logger.info("Remove dynamic config from remote, dynamicConfigPk={}", dynamicConfigPk);
/* 224 */     this.clusterManager.removeDynamicConfig(dynamicConfigPk);
/*     */   }
/*     */   
/*     */   public void updateDynamicConfig(DynamicConfig dynamicConfig)
/*     */     throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException
/*     */   {
/* 230 */     logger.info("Update dynamic config from remote, dynamicConfig={}", dynamicConfig);
/* 231 */     this.clusterManager.updateDynamicConfig(dynamicConfig);
/*     */   }
/*     */   
/*     */   public void removeFmeConfigs(String groupName, String module)
/*     */     throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException
/*     */   {
/* 237 */     logger.info("Remove module configs from remote, module={}", module);
/* 238 */     this.clusterManager.removeFmeConfigs(groupName, module);
/*     */   }
/*     */   
/*     */ 
/*     */   public Map<DynamicConfigPk, DynamicConfig> getFmeConfigs(String groupName, String module)
/*     */     throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException
/*     */   {
/* 245 */     return this.clusterManager.getFmeConfigs(groupName, module);
/*     */   }
/*     */   
/*     */   public void resetFme(String fmeName)
/*     */     throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException
/*     */   {
/* 251 */     logger.info("Reset FME from remote, fmeName={}", fmeName);
/* 252 */     this.clusterManager.resetFme(fmeName);
/*     */   }
/*     */   
/*     */   public DynamicConfigDeclare[] getDynamicConfigDeclare(String fmClassName) throws Throwable
/*     */   {
/* 257 */     Class<?> c = Class.forName(fmClassName);
/* 258 */     return FmeMainBase.getDynamicConfigDeclares(fmClassName);
/*     */   }
/*     */   
/*     */   public HcceEnv getHcceEnv()
/*     */   {
/* 263 */     logger.info("Get HcceEnv from remote");
/* 264 */     return this.clusterManager.getHcceEnv();
/*     */   }
/*     */   
/*     */   public String createSshClient(String host, String userName, String password) throws IOException
/*     */   {
/* 269 */     logger.info("Create Ssh client from remote, host:{}, userName:{}, password:{}", new Object[] { host, userName, password });
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 274 */     String sshId = this.sshClientService.createSshClient(host, userName, password);
/* 275 */     logger.info("Create Ssh client from remote, sshId:{}", sshId);
/* 276 */     return sshId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String createSshClient(String hostAlias)
/*     */     throws IOException
/*     */   {
/* 285 */     logger.info("Create Ssh client from remote, hostAlias:{}", hostAlias);
/* 286 */     String host = this.environment.getProperty("ssh." + hostAlias + ".host");
/* 287 */     if (host == null) throw new IOException("Host alias: " + hostAlias + " not found!");
/* 288 */     String username = this.environment.getProperty("ssh." + hostAlias + ".username");
/* 289 */     String privatekey = this.environment.getProperty("ssh." + hostAlias + ".privatekey");
/* 290 */     File file = null;
/*     */     try {
/* 292 */       ClassPathResource cr = new ClassPathResource(privatekey);
/* 293 */       file = cr.getFile();
/*     */     }
/*     */     catch (FileNotFoundException e) {
/* 296 */       FileSystemResource fs = new FileSystemResource(privatekey);
/* 297 */       file = fs.getFile();
/*     */     }
/*     */     
/* 300 */     String privateKeyPassword = this.environment.getProperty("ssh." + hostAlias + ".privatekey.password");
/* 301 */     String sshId = this.sshClientService.createSshClient(host, username, file, privateKeyPassword);
/* 302 */     logger.info("Create Ssh client from remote, sshId:{}", sshId);
/* 303 */     return sshId;
/*     */   }
/*     */   
/*     */   public CmdExecResult execCmd(String sshId, String command) throws IOException
/*     */   {
/* 308 */     logger.info("Execute command from remote, sshId:{}, command:{}", sshId, command);
/* 309 */     return this.sshClientService.execCmd(sshId, command);
/*     */   }
/*     */   
/*     */   public void execCmd(String sshId, String command, CmdRealTimeResponse cmdRealTimeResponse)
/*     */     throws IOException
/*     */   {
/* 315 */     logger.info("Execute command with callback from remote, sshId:{}, command:{}", sshId, command);
/* 316 */     this.sshClientService.execCmd(sshId, command, cmdRealTimeResponse);
/*     */   }
/*     */   
/*     */   public CmdExecBinaryResult execCmdBinary(String sshId, String command) throws IOException
/*     */   {
/* 321 */     logger.info("Execute command from remote, sshId:{}, command:{}", sshId, command);
/* 322 */     return this.sshClientService.execCmdBinary(sshId, command);
/*     */   }
/*     */   
/*     */   public void closeSshClient(String sshId)
/*     */   {
/* 327 */     logger.info("Close Ssh client command from remote, sshId:{}", sshId);
/* 328 */     this.sshClientService.closeSshClient(sshId);
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
/*     */   public void addOpLog(String userId, String cpeIp, String subSysName, String deviceName, String description, Date operationTime, OperationResult operationResult, String remark)
/*     */   {
/* 341 */     this.opLogger.addSetLog(userId, cpeIp, subSysName, deviceName, description, operationTime, operationResult, remark);
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
/*     */   public void addOpLog(String userId, String cpeIp, String subSysName, String operationItem, String deviceName, String description, Date operationTime, OperationResult operationResult, String remark)
/*     */   {
/* 356 */     this.opLogger.addLog(userId, cpeIp, subSysName, operationItem, deviceName, description, operationTime, operationResult, remark);
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
/*     */   public DynamicConfig getDynaConfig(String configName)
/*     */   {
/* 370 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setDynaConfig(String key, String value) {}
/*     */   
/*     */   public boolean isInPrimaryGroup()
/*     */   {
/* 378 */     return this.clusterManager.getHcceEnv().isInPrimaryGroup();
/*     */   }
/*     */   
/*     */   public Page<NodeMonitorLog> findMonitorLogs(Date startTime, Date endTime, Pageable pageable)
/*     */   {
/* 383 */     return this.nodeMonitorLogRepository.findAll(
/* 384 */       NodeMonitorLogSpecification.toPredicate(startTime, endTime), pageable);
/*     */   }
/*     */   
/*     */   public List<NodeMonitorLog> findMonitorLogs(Date startTime, Date endTime)
/*     */   {
/* 389 */     logger.info("findMonitorLogs startTime : {} , endTime : {} ", startTime, endTime);
/* 390 */     return this.nodeMonitorLogRepository.findAll(
/* 391 */       NodeMonitorLogSpecification.toPredicate(startTime, endTime));
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\remote\CmRemoteImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */