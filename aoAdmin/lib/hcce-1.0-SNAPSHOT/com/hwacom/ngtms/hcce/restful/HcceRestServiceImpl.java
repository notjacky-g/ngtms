/*     */ package com.hwacom.ngtms.hcce.restful;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.oplog.service.BaseOpLogger;
/*     */ import com.hwacom.ngtms.base.oplog.shared.CoreSystem;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*     */ import com.hwacom.ngtms.base.ssh.CmdExecResult;
/*     */ import com.hwacom.ngtms.base.ssh.service.SshClientService;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfigPk;
/*     */ import com.hwacom.ngtms.hcce.core.exception.ClusterManagerNotCoordinatorException;
/*     */ import com.hwacom.ngtms.hcce.core.exception.ClusterManagerNotReadyException;
/*     */ import com.hwacom.ngtms.hcce.core.exception.ClusterManagerOperationException;
/*     */ import com.hwacom.ngtms.hcce.core.exception.DisasterRecoveryException;
/*     */ import com.hwacom.ngtms.hcce.core.exception.TopologyNodeCfgException;
/*     */ import com.hwacom.ngtms.hcce.fme.controller.fm.FmeMainBase;
/*     */ import com.hwacom.ngtms.hcce.fme.manager.model.FmeDefinition;
/*     */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*     */ import com.hwacom.ngtms.hcce.shared.ClientHeartbeat;
/*     */ import com.hwacom.ngtms.hcce.shared.ClusterMode;
/*     */ import com.hwacom.ngtms.hcce.shared.CmState;
/*     */ import com.hwacom.ngtms.hcce.shared.DynamicConfigDeclare;
/*     */ import com.hwacom.ngtms.hcce.shared.FmeDefTable;
/*     */ import com.hwacom.ngtms.hcce.shared.FmeExeStatus;
/*     */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*     */ import com.hwacom.ngtms.hcce.shared.TopologyInfo;
/*     */ import com.hwacom.ngtms.hcce.shared.TopologyNode;
/*     */ import com.hwacom.ngtms.hcce.shared.dto.ClusterModeDTO;
/*     */ import com.hwacom.ngtms.hcce.shared.dto.DbStatusDTO;
/*     */ import com.hwacom.ngtms.hcce.shared.dto.DynamicConfigDTO;
/*     */ import com.hwacom.ngtms.hcce.shared.dto.FmeDefinitionDTO;
/*     */ import com.hwacom.ngtms.hcce.shared.dto.HcceEnvDTO;
/*     */ import com.hwacom.ngtms.hcce.shared.dto.NodeDTO;
/*     */ import com.hwacom.ngtms.hcce.shared.dto.SystemEnvDTO;
/*     */ import com.hwacom.ngtms.hcce.shared.dto.TopologyGroup;
/*     */ import com.hwacom.ngtms.hcce.shared.dto.TopologyNodeCfgVO;
/*     */ import com.hwacom.ngtms.hcce.shared.dto.TopologyNodeStatusDTO;
/*     */ import com.hwacom.ngtms.hcce.topology.model.TopologyNodeCfg;
/*     */ import com.hwacom.ngtms.hcce.util.HcceUtils;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.annotation.PreDestroy;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.BeanUtils;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.core.io.ClassPathResource;
/*     */ import org.springframework.core.io.FileSystemResource;
/*     */ import org.springframework.data.domain.PageRequest;
/*     */ import org.springframework.data.domain.Pageable;
/*     */ import org.springframework.data.domain.Sort;
/*     */ import org.springframework.web.bind.annotation.CrossOrigin;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @CrossOrigin
/*     */ @RestController
/*     */ @RequestMapping({"/api/hcManager"})
/*     */ public class HcceRestServiceImpl
/*     */ {
/*  87 */   private static final Logger logger = LoggerFactory.getLogger(HcceRestServiceImpl.class);
/*  88 */   private static final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("#.##");
/*     */   
/*     */   private static final int NUMBER_OF_PER_PAGE = 50;
/*     */   
/*     */   private static final int NODE_MONITOR_LOG_REPEAT_TIME = 2;
/*     */   
/*     */   public static final String UPDATE_CLIENT_HEARTBEAT_URL = "http://{0}:{1}/api/hcManager/clientHeartbeat";
/*     */   
/*     */   @Autowired
/*     */   private Environment env;
/*     */   
/*     */   @Autowired
/*     */   private SshClientService sshClientService;
/*     */   @Autowired
/*     */   private BaseOpLogger opLogger;
/*     */   @Autowired
/*     */   private HcceEnv hcceEnv;
/*     */   private Map<String, String> sshIdMap;
/*     */   private Map<String, String> dbHealthScriptMap;
/*     */   private String dbNames;
/*     */   private boolean isNodeMonitorLog = false;
/* 109 */   private ScheduledExecutorService nodeMonitorLogExecutorService = Executors.newSingleThreadScheduledExecutor();
/*     */   
/*     */   private int count;
/*     */   
/*     */   @PostConstruct
/*     */   public void nodeMonitorLog() {
/*     */     try {
/* 116 */       this.isNodeMonitorLog = ((Boolean)this.env.getProperty("log.monitor.system", Boolean.class, Boolean.FALSE)).booleanValue();
/*     */       
/* 118 */       this.dbNames = this.env.getProperty("ssh.db.names", "oldb");
/*     */       
/* 120 */       if (this.sshIdMap == null) {
/* 121 */         this.sshIdMap = new HashMap<>();
/*     */       }
/*     */       
/* 124 */       if (this.dbHealthScriptMap == null) {
/* 125 */         this.dbHealthScriptMap = new HashMap<>();
/*     */       }
/*     */       
/* 128 */       for (String n : this.dbNames.split(",")) {
/* 129 */         if (this.sshIdMap.get(n) == null) {
/* 130 */           this.sshIdMap.put(n, null);
/*     */         }
/*     */         
/* 133 */         if (this.dbHealthScriptMap.get(n) == null) {
/* 134 */           this.dbHealthScriptMap.put(n, this.env
/* 135 */               .getProperty("checkHealth." + n, "/ngtms/bin/check_wsrep_status.sh"));
/*     */         }
/*     */       } 
/* 138 */     } catch (Exception e) {
/* 139 */       logger.error(e.getMessage(), e);
/*     */     } 
/* 141 */     if (this.isNodeMonitorLog) {
/* 142 */       Runnable runNodeMonitorLog = () -> {
/*     */           try {
/*     */             updateHcStatus();
/*     */             if (this.count == 0 || this.count >= 20) {
/*     */               updateDbStatus();
/*     */               if (this.count >= 20)
/*     */                 this.count = 0; 
/*     */             } 
/*     */             this.count++;
/* 151 */           } catch (Exception e) {
/*     */             logger.error(e.getMessage(), e);
/*     */           } 
/*     */         };
/*     */       
/* 156 */       this.nodeMonitorLogExecutorService.scheduleWithFixedDelay(runNodeMonitorLog, 1L, 2L, TimeUnit.SECONDS);
/*     */     } 
/*     */   }
/*     */   
/*     */   public HcceRestServiceImpl() {
/* 161 */     this.count = 0;
/*     */   }
/*     */   @PreDestroy
/*     */   public void destroy() {
/* 165 */     this.nodeMonitorLogExecutorService.shutdownNow();
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/getSystemEnv"}, method = {RequestMethod.GET})
/*     */   public SystemEnvDTO getSystemEnv() {
/*     */     try {
/* 171 */       SystemEnvDTO value = new SystemEnvDTO();
/* 172 */       IMap<String, SystemEnvDTO> systemStatusMap = HzUtils.getMap((HzDistObjEnum)HzMap.SystemStatus);
/* 173 */       Collection<SystemEnvDTO> c = systemStatusMap.values();
/* 174 */       if (c != null && c.size() > 0) {
/* 175 */         value = c.iterator().next();
/*     */       }
/* 177 */       return value;
/* 178 */     } catch (Exception e) {
/* 179 */       logger.error("get SystemEnv failed.", e);
/* 180 */       return new SystemEnvDTO();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateHcStatus() {
/*     */     try {
/* 186 */       SystemEnvDTO systemEnvVo = null;
/* 187 */       IMap<String, SystemEnvDTO> systemStatusMap = HzUtils.getMap((HzDistObjEnum)HzMap.SystemStatus);
/* 188 */       Collection<SystemEnvDTO> c = systemStatusMap.values();
/* 189 */       if (c != null && c.size() > 0) {
/* 190 */         systemEnvVo = c.iterator().next();
/*     */       } else {
/* 192 */         systemEnvVo = new SystemEnvDTO();
/*     */       } 
/*     */       
/* 195 */       HcceEnv hcceEnv = HcceUtils.getCoordinatorCmRemote().getHcceEnv();
/* 196 */       HcceEnvDTO hcceEnvVo = new HcceEnvDTO();
/* 197 */       BeanUtils.copyProperties(hcceEnv, hcceEnvVo);
/* 198 */       systemEnvVo.setHcceEnv(hcceEnvVo);
/*     */       
/* 200 */       IMap<String, ClusterMode> clusterModeMap = HzUtils.getMap((HzDistObjEnum)HzMap.ClusterMode);
/* 201 */       ClusterMode clusterMode = clusterModeMap.values().iterator().next();
/* 202 */       if (clusterMode != null) {
/* 203 */         systemEnvVo.setClusterMode(ClusterModeDTO.valueOf(clusterMode.toString()));
/*     */       }
/* 205 */       systemEnvVo.setUpdateTime(new Date());
/*     */       
/* 207 */       systemStatusMap.put(systemEnvVo.getHcceEnv().getCurrentGroupName(), systemEnvVo);
/*     */     
/*     */     }
/* 210 */     catch (Exception e) {
/* 211 */       logger.error("updateHcStatus Error: ", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateDbStatus() {
/* 216 */     SystemEnvDTO systemEnvVo = null;
/* 217 */     IMap<String, SystemEnvDTO> systemStatusMap = HzUtils.getMap((HzDistObjEnum)HzMap.SystemStatus);
/* 218 */     Collection<SystemEnvDTO> c = systemStatusMap.values();
/* 219 */     if (c != null && c.size() > 0) {
/* 220 */       systemEnvVo = c.iterator().next();
/*     */     } else {
/* 222 */       systemEnvVo = new SystemEnvDTO();
/*     */     } 
/*     */     
/* 225 */     for (String n : this.dbNames.split(",")) {
/* 226 */       String sshId = this.sshIdMap.get(n);
/* 227 */       String dbHealthScript = this.dbHealthScriptMap.get(n);
/* 228 */       if (sshId == null) {
/*     */         try {
/* 230 */           sshId = createSshClient(n);
/* 231 */         } catch (IOException e) {
/* 232 */           logger.error("create {} SshClient Error: ", n, e);
/*     */         } 
/*     */       }
/*     */       
/* 236 */       CmdExecResult cmdExecResult = null;
/* 237 */       if (sshId != null) {
/*     */         try {
/* 239 */           cmdExecResult = this.sshClientService.execCmd(sshId, dbHealthScript);
/* 240 */           Map<String, DbStatusDTO> statusMap = systemEnvVo.getDbStatusMap();
/* 241 */           if (statusMap == null) {
/* 242 */             statusMap = new HashMap<>();
/*     */           }
/* 244 */           statusMap.put(n, processDbStatusCvsResult(cmdExecResult));
/* 245 */           systemEnvVo.setDbStatusMap(statusMap);
/* 246 */           systemEnvVo.setUpdateTime(new Date());
/* 247 */           systemStatusMap.put(systemEnvVo.getHcceEnv().getCurrentGroupName(), systemEnvVo);
/* 248 */         } catch (IOException e) {
/* 249 */           logger.error("update {} Status Error: ", n, e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/fetchTopologyNodeCfgData/{topologyGroup}"}, method = {RequestMethod.GET})
/*     */   public List<TopologyNodeCfgVO> fetchTopologyNodeCfgData(@PathVariable("topologyGroup") String topologyGroup) throws Exception {
/* 258 */     List<TopologyNodeCfgVO> VOList = new ArrayList<>();
/* 259 */     for (TopologyNodeCfgVO VO : getTopologyNodeCfgData()) {
/* 260 */       if (topologyGroup.equals(VO.getGroupName())) {
/* 261 */         VOList.add(VO);
/*     */       }
/*     */     } 
/* 264 */     return VOList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/fetchTopologyNodeStatusData/{topologyGroup}"}, method = {RequestMethod.GET})
/*     */   public List<TopologyNodeStatusDTO> fetchTopologyNodeStatusData(@PathVariable("topologyGroup") String topologyGroup) {
/* 273 */     List<TopologyNodeStatusDTO> list = new ArrayList<>();
/*     */     try {
/* 275 */       IMap<String, TopologyInfo> topologyInfoMap = HzUtils.getMap((HzDistObjEnum)HzMap.TopologyInfo);
/* 276 */       for (TopologyInfo topologyInfo : topologyInfoMap.values()) {
/* 277 */         for (TopologyNode topologyNode : topologyInfo.getNodeMap().values()) {
/* 278 */           if (!topologyGroup.equals(topologyNode.getNodeCfg().getGroupName())) {
/*     */             continue;
/*     */           }
/* 281 */           logger.debug("fetchTopologyNodeStatusData topologyNode: {}", topologyNode);
/* 282 */           TopologyNodeStatusDTO vo = new TopologyNodeStatusDTO();
/* 283 */           vo.setGroupName(topologyNode.getNodeCfg().getGroupName());
/* 284 */           vo.setNodeName(topologyNode.getNodeCfg().getNodeName());
/* 285 */           vo.setCoordinator(topologyNode.isCoordinator());
/* 286 */           vo.setLastRegIpAddress(topologyNode.getLastRegIpAddress());
/* 287 */           vo.setRegisterTime(topologyNode.getRegisterTime());
/* 288 */           vo.setRegistered(topologyNode.isRegistered());
/* 289 */           if (topologyNode.getRegisterTime() != null) {
/* 290 */             Date nowDate = new Date();
/* 291 */             double diff = (nowDate.getTime() - topologyNode.getRegisterTime().getTime());
/* 292 */             double diffDays = diff / 8.64E7D;
/* 293 */             vo.setServiceTime(DOUBLE_FORMAT.format(diffDays));
/*     */           } 
/*     */           
/* 296 */           if (topologyNode.isRegistered()) {
/* 297 */             vo.setOperation("Stop");
/*     */           } else {
/* 299 */             vo.setOperation("Start");
/*     */           } 
/* 301 */           if (topologyNode.isCoordinator()) {
/* 302 */             vo.setOperation("");
/*     */           }
/* 304 */           list.add(vo);
/*     */         } 
/*     */       } 
/* 307 */     } catch (Exception e) {
/* 308 */       logger.error("fetchTopologyNodeStatusData Error: ", e);
/*     */     } 
/*     */     
/* 311 */     return list;
/*     */   }
/*     */   
/*     */   private List<TopologyNodeCfgVO> getTopologyNodeCfgData() throws Exception {
/* 315 */     IMap<String, TopologyInfo> topologyConfigMap = HzUtils.getMap((HzDistObjEnum)HzMap.TopologyInfo);
/* 316 */     if (topologyConfigMap != null) {
/* 317 */       List<TopologyNodeCfgVO> result = new ArrayList<>();
/* 318 */       for (TopologyInfo topologyInfo : topologyConfigMap.values()) {
/* 319 */         for (TopologyNode topologyNode : topologyInfo.getNodeMap().values()) {
/* 320 */           TopologyNodeCfg nodeCfg = topologyNode.getNodeCfg();
/* 321 */           TopologyNodeCfgVO vo = new TopologyNodeCfgVO();
/* 322 */           BeanUtils.copyProperties(nodeCfg, vo);
/* 323 */           result.add(vo);
/*     */         } 
/*     */       } 
/* 326 */       logger.debug("getTopologyNodeCfgData result: {}", result);
/* 327 */       return result;
/*     */     } 
/* 329 */     return new ArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/addTopologyNodeCfg"}, method = {RequestMethod.POST})
/*     */   public TopologyNodeCfgVO addTopologyNodeCfg(@RequestBody TopologyNodeCfgVO vo) throws Exception {
/* 335 */     logger.debug("addTopologyNodeCfg: {}", vo.toString());
/* 336 */     TopologyNodeCfg nodeCfg = new TopologyNodeCfg();
/* 337 */     BeanUtils.copyProperties(vo, nodeCfg);
/*     */     try {
/* 339 */       HcceUtils.getCoordinatorCmRemote().addTopologyNodeCfg(nodeCfg);
/* 340 */       logger.debug("addTopologyNodeCfg Finishlly");
/* 341 */     } catch (TopologyNodeCfgException|ClusterManagerNotCoordinatorException|ClusterManagerNotReadyException e) {
/*     */ 
/*     */       
/* 344 */       logger.warn("addTopologyNodeCfg failed!", e);
/* 345 */       throw new Exception(e.getMessage());
/*     */     } 
/* 347 */     return vo;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/saveTopologyNodeCfg"}, method = {RequestMethod.POST})
/*     */   public TopologyNodeCfgVO saveTopologyNodeCfg(@RequestBody TopologyNodeCfgVO vo) throws Exception {
/* 352 */     logger.debug("saveTopologyNodeCfg: {}", vo.toString());
/* 353 */     TopologyNodeCfg nodeCfg = new TopologyNodeCfg();
/* 354 */     BeanUtils.copyProperties(vo, nodeCfg);
/*     */     try {
/* 356 */       HcceUtils.getCoordinatorCmRemote().modifyTopologyCfgNode(nodeCfg);
/* 357 */       logger.debug("saveTopologyNodeCfg Finishlly");
/* 358 */     } catch (ClusterManagerNotCoordinatorException|TopologyNodeCfgException|ClusterManagerNotReadyException e) {
/*     */ 
/*     */       
/* 361 */       logger.warn("saveTopologyNodeCfg failed!", e);
/* 362 */       throw new Exception(e.getMessage());
/*     */     } 
/* 364 */     return vo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/deleteTopologyNodeCfg/{groupName}/{nodeName}"}, method = {RequestMethod.DELETE})
/*     */   public Boolean deleteTopologyNodeCfg(@PathVariable("groupName") String groupName, @PathVariable("nodeName") String nodeName) throws Exception {
/* 374 */     logger.debug("deleteTopologyNodeCfg groupName: {}, nodeName : {}", groupName, nodeName);
/*     */     try {
/* 376 */       HcceUtils.getCoordinatorCmRemote().removeTopologyNodeCfg(groupName, nodeName);
/* 377 */       logger.debug("deleteTopologyNodeCfg Finishlly");
/* 378 */     } catch (ClusterManagerNotCoordinatorException|ClusterManagerOperationException|ClusterManagerNotReadyException e) {
/*     */ 
/*     */       
/* 381 */       logger.warn("deleteTopologyNodeCfg failed!", e);
/* 382 */       throw new Exception(e.getMessage());
/*     */     } 
/* 384 */     return Boolean.valueOf(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/startNode/{topologyGroup}/{nodeName}"}, method = {RequestMethod.POST})
/*     */   public Boolean startNode(@PathVariable("topologyGroup") String topologyGroup, @PathVariable("nodeName") String nodeName) throws Exception {
/* 392 */     logger.debug("startHcNode topologyGroup: {}, nodeName: {}", topologyGroup, nodeName);
/* 393 */     if (TopologyGroup.HC_BACKUP_GROUP.toString().equals(topologyGroup)) {
/*     */       try {
/* 395 */         HcceUtils.getCoordinatorCmRemote().startNode(nodeName);
/* 396 */       } catch (Exception e) {
/* 397 */         logger.warn("startHcNode failed!", e);
/* 398 */         throw new Exception(e.getMessage(), e);
/*     */       } 
/* 400 */       logger.debug("To Start Backup Node: {}", nodeName);
/* 401 */     } else if (TopologyGroup.HC_PRIMARY_GROUP.toString().equals(topologyGroup)) {
/*     */       try {
/* 403 */         HcceUtils.getCoordinatorCmRemote().startNode(nodeName);
/* 404 */       } catch (Exception e) {
/* 405 */         logger.warn("startHcNode failed!", e);
/* 406 */         throw new Exception(e.getMessage(), e);
/*     */       } 
/* 408 */       logger.debug("To Start Primary Node: {}", nodeName);
/*     */     } 
/*     */     
/* 411 */     return Boolean.valueOf(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/stopNode/{topologyGroup}/{nodeName}"}, method = {RequestMethod.POST})
/*     */   public Boolean stopNode(@PathVariable("topologyGroup") String topologyGroup, @PathVariable("nodeName") String nodeName) throws Exception {
/* 419 */     logger.debug("stopHcNode topologyGroup: {}, nodeName: {}", topologyGroup, nodeName);
/*     */     
/* 421 */     if (TopologyGroup.HC_BACKUP_GROUP.toString().equals(topologyGroup)) {
/*     */       try {
/* 423 */         HcceUtils.getCoordinatorCmRemote().stopNode(nodeName);
/* 424 */       } catch (Exception e) {
/* 425 */         logger.warn("stopHcNode failed!", e);
/* 426 */         throw new Exception(e.getMessage(), e);
/*     */       } 
/* 428 */       logger.debug("To Stop Backup Node: {}", nodeName);
/* 429 */     } else if (TopologyGroup.HC_PRIMARY_GROUP.toString().equals(topologyGroup)) {
/*     */       try {
/* 431 */         HcceUtils.getCoordinatorCmRemote().stopNode(nodeName);
/* 432 */       } catch (Exception e) {
/* 433 */         logger.warn("stopHcNode failed!", e);
/* 434 */         throw new Exception(e.getMessage(), e);
/*     */       } 
/* 436 */       logger.debug("To Stop Primary Node: {}", nodeName);
/*     */     } 
/* 438 */     return Boolean.valueOf(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/removeNode/{topologyGroup}/{nodeName}"}, method = {RequestMethod.POST})
/*     */   public Boolean removeNode(@PathVariable("topologyGroup") String topologyGroup, @PathVariable("nodeName") String nodeName) throws Exception {
/*     */     try {
/* 447 */       logger.debug("removeHcNode topologyGroup: {}, nodeName: {}", topologyGroup, nodeName);
/* 448 */       IMap<String, TopologyInfo> topologyInfoMap = HzUtils.getMap((HzDistObjEnum)HzMap.TopologyInfo);
/* 449 */       TopologyInfo info = (TopologyInfo)topologyInfoMap.get(topologyGroup);
/*     */       
/* 451 */       ((TopologyNode)info.getNodeMap().get(nodeName)).setRegistered(false);
/* 452 */       ((TopologyNode)info.getNodeMap().get(nodeName)).setRegisterTime(null);
/* 453 */       topologyInfoMap.put(topologyGroup, info);
/* 454 */       return Boolean.valueOf(true);
/* 455 */     } catch (Exception e) {
/* 456 */       logger.warn("removeHcNode failed!", e);
/* 457 */       return Boolean.valueOf(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/fetchFmeDefinitionData/{groupName}"}, method = {RequestMethod.GET})
/*     */   public List<FmeDefinitionDTO> fetchFmeDefinitionData(@PathVariable("groupName") String groupName) throws Exception {
/* 464 */     List<FmeDefinitionDTO> resultList = new ArrayList<>();
/* 465 */     IMap<String, FmeDefTable> fmeDefTableMap = HzUtils.getMap((HzDistObjEnum)HzMap.FmeDefTable);
/* 466 */     IMap<String, FmeExeStatus> fmeExeStatusMap = HzUtils.getMap((HzDistObjEnum)HzMap.FmeExeStatus);
/*     */     try {
/* 468 */       FmeDefTable fmeDefTable = (FmeDefTable)fmeDefTableMap.get(groupName);
/* 469 */       if (fmeDefTable != null) {
/* 470 */         List<FmeDefinition> definitions = fmeDefTable.getDefinitions();
/* 471 */         for (FmeDefinition fmeDefinition : definitions) {
/* 472 */           FmeDefinitionDTO vo = new FmeDefinitionDTO();
/* 473 */           vo.setClassName(fmeDefinition.getClassName());
/* 474 */           vo.setDescription(fmeDefinition.getDescription());
/* 475 */           vo.setEnable(fmeDefinition.getEnable().booleanValue());
/* 476 */           vo.setFmeName(fmeDefinition.getFmeName());
/* 477 */           vo.setGroupName(fmeDefinition.getGroupName());
/* 478 */           vo.setNodePriority(fmeDefinition.getNodePriority());
/* 479 */           vo.setOperation("Restart");
/*     */ 
/*     */           
/* 482 */           FmeExeStatus fmeExeStatus = (FmeExeStatus)fmeExeStatusMap.get(vo.getFmeName());
/* 483 */           if (fmeExeStatus != null) {
/* 484 */             vo.setNowNode(fmeExeStatus.getAssignedNodeName());
/* 485 */             vo.setAssignedMemberIpAddress(fmeExeStatus.getAssignedMemberIpAddress());
/* 486 */             vo.setAssignedMemberUuid(fmeExeStatus.getAssignedMemberUuid());
/* 487 */             vo.setFmeStatus(fmeExeStatus.getState().toString());
/* 488 */             vo.setStartTime(fmeExeStatus.getStartTime());
/*     */           } 
/* 490 */           resultList.add(vo);
/*     */         } 
/*     */       } 
/* 493 */     } catch (Exception e) {
/* 494 */       logger.warn("fetchFmeDefinitionData failed!", e);
/* 495 */       throw new Exception(e.getMessage());
/*     */     } 
/* 497 */     return resultList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/fetchNodeData/{topologyGroup}"}, method = {RequestMethod.GET})
/*     */   public List<NodeDTO> fetchNodeData(@PathVariable("topologyGroup") String topologyGroup) throws Exception {
/* 504 */     List<NodeDTO> nodeList = new ArrayList<>();
/* 505 */     List<TopologyNodeCfgVO> nodeCfgList = fetchTopologyNodeCfgData(topologyGroup);
/* 506 */     for (TopologyNodeCfgVO nodeCfg : nodeCfgList) {
/* 507 */       NodeDTO vo = new NodeDTO();
/* 508 */       vo.setKey(nodeCfg.getKey());
/* 509 */       vo.setName(nodeCfg.getNodeName());
/* 510 */       nodeList.add(vo);
/*     */     } 
/* 512 */     logger.debug("nodeList => {}", nodeList);
/* 513 */     return nodeList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/getFmeDyNamicConfig/{groupName}/{fmeName}/{className}/{topologyGroup}"}, method = {RequestMethod.GET})
/*     */   public List<DynamicConfigDTO> getFmeDyNamicConfig(@PathVariable("groupName") String groupName, @PathVariable("fmeName") String fmeName, @PathVariable("className") String className, @PathVariable("topologyGroup") String topologyGroup) {
/* 525 */     logger.debug("getFmeDyNamicConfig groupName:'{}', nodeName:'{}', className:'{}', topologyGroup:'{}'", new Object[] { groupName, fmeName, className, topologyGroup });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 533 */       DynamicConfigDeclare[] dynamicConfigDeclares = FmeMainBase.getDynamicConfigDeclares(className);
/* 534 */       List<DynamicConfigDTO> result = new ArrayList<>();
/* 535 */       if (dynamicConfigDeclares == null) {
/* 536 */         return result;
/*     */       }
/*     */ 
/*     */       
/* 540 */       Map<DynamicConfigPk, DynamicConfig> fmeConfigs = HcceUtils.getCoordinatorCmRemote().getFmeConfigs(groupName, fmeName);
/* 541 */       for (DynamicConfigDeclare each : dynamicConfigDeclares) {
/* 542 */         String value = each.getDefaultValue();
/* 543 */         if (fmeConfigs != null) {
/*     */           
/* 545 */           DynamicConfig dynamicConfig = fmeConfigs.get(new DynamicConfigPk(groupName, fmeName, each.getName()));
/* 546 */           if (dynamicConfig != null) {
/* 547 */             value = dynamicConfig.getValue();
/*     */           }
/*     */         } 
/* 550 */         result.add(new DynamicConfigDTO(groupName, fmeName, each.getName(), value));
/*     */       } 
/* 552 */       return result;
/* 553 */     } catch (Throwable e) {
/* 554 */       logger.warn("Get fme dynamic config failed!", e);
/* 555 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/updateDynamicConfig"}, method = {RequestMethod.POST})
/*     */   public DynamicConfigDTO updateDynamicConfig(@RequestBody DynamicConfigDTO dynamicConfigVO) {
/* 561 */     logger.debug("updateDynamicConfig DynamicConfigVO: {}", dynamicConfigVO);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 568 */       DynamicConfig dynamicConfig = new DynamicConfig(dynamicConfigVO.getGroupName(), dynamicConfigVO.getFmeName(), dynamicConfigVO.getName(), dynamicConfigVO.getValue());
/* 569 */       HcceUtils.getCoordinatorCmRemote().updateDynamicConfig(dynamicConfig);
/* 570 */     } catch (Exception e) {
/* 571 */       logger.error("updateDynamicConfig Error : ", e);
/* 572 */       dynamicConfigVO = null;
/*     */     } 
/* 574 */     return dynamicConfigVO;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/saveFme"}, method = {RequestMethod.POST})
/*     */   public void saveFme(@RequestBody FmeDefinitionDTO dto) {
/*     */     try {
/* 580 */       logger.debug("saveFme FmeDefinitionVO: {}", dto.toString());
/* 581 */       FmeDefinition bean = new FmeDefinition();
/* 582 */       BeanUtils.copyProperties(dto, bean);
/* 583 */       logger.debug("saveFme FmeDefinition: {}", bean.toString());
/* 584 */       HcceUtils.getCoordinatorCmRemote().submitFmeDefinition(bean);
/* 585 */     } catch (Exception e) {
/* 586 */       logger.warn("Save fme definition failed! dto: '{}'", dto, e);
/* 587 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/deleteFme/{groupName}/{fmeName}"}, method = {RequestMethod.DELETE})
/*     */   public void deleteFme(@PathVariable("groupName") String groupName, @PathVariable("fmeName") String fmeName) {
/*     */     try {
/* 595 */       logger.debug("deleteFme groupName: {}, nodeName: {}", groupName, fmeName);
/* 596 */       HcceUtils.getCoordinatorCmRemote().removeFmeDefinition(groupName, fmeName);
/* 597 */     } catch (Exception e) {
/* 598 */       logger.warn("Delete fme definition failed! groupName: '{}', fmeName: '{}'", new Object[] { groupName, fmeName, e });
/*     */       
/* 600 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/resetFme"}, method = {RequestMethod.POST})
/*     */   public Boolean resetFme(@RequestBody FmeDefinitionDTO VO, HttpServletRequest request) throws Exception {
/* 607 */     logger.debug("resetFme FmeDefinitionVO: {}", VO.toString());
/* 608 */     FmeDefinition bean = new FmeDefinition();
/* 609 */     BeanUtils.copyProperties(VO, bean);
/* 610 */     logger.debug("resetFme FmeDefinition: {}", bean.toString());
/*     */     try {
/* 612 */       HcceUtils.getCoordinatorCmRemote().resetFme(VO.getFmeName());
/* 613 */       this.opLogger.addLog((VO
/* 614 */           .getUserId() == null || VO.getUserId().isEmpty()) ? CoreSystem.HCCE
/* 615 */           .getDefaultUserId() : VO
/* 616 */           .getUserId(), this.hcceEnv
/* 617 */           .getLocalIpAddress(), CoreSystem.HCCE
/* 618 */           .toString(), null, OperationResult.SUCCESS, "hcce.oplog.restart", new Object[] { VO
/*     */ 
/*     */ 
/*     */             
/* 622 */             .getFmeName(), this.hcceEnv
/* 623 */             .getCurrentGroupName() });
/* 624 */     } catch (Exception e) {
/*     */       
/* 626 */       this.opLogger.addLog((VO
/* 627 */           .getUserId() == null || VO.getUserId().isEmpty()) ? CoreSystem.HCCE
/* 628 */           .getDefaultUserId() : VO
/* 629 */           .getUserId(), this.hcceEnv
/* 630 */           .getLocalIpAddress(), CoreSystem.HCCE
/* 631 */           .toString(), null, OperationResult.FAILURE, "hcce.oplog.restart", new Object[] { VO
/*     */ 
/*     */ 
/*     */             
/* 635 */             .getFmeName(), this.hcceEnv
/* 636 */             .getCurrentGroupName() });
/* 637 */       throw new Exception(e.getMessage(), e);
/*     */     } 
/* 639 */     return Boolean.valueOf(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void promote() throws ClusterManagerNotCoordinatorException, ClusterManagerNotReadyException, DisasterRecoveryException, ClusterManagerOperationException {
/* 645 */     HcceUtils.getCoordinatorCmRemote().setAllowActive(true);
/* 646 */     HcceUtils.getCoordinatorCmRemote().promoteCluster();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void demote() throws ClusterManagerNotCoordinatorException, ClusterManagerOperationException, DisasterRecoveryException {
/* 652 */     HcceUtils.getCoordinatorCmRemote().demoteCluster();
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/doSystemAction"}, method = {RequestMethod.POST})
/*     */   public void doSystemAction(@RequestBody ClusterModeDTO clusterMode) throws Exception {
/* 657 */     CmState state = HcceUtils.getCoordinatorCmRemote().getClusterManagerState();
/* 658 */     if (state == CmState.Active) {
/* 659 */       demote();
/* 660 */     } else if (state == CmState.Standby) {
/* 661 */       promote();
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/startCluster"}, method = {RequestMethod.POST})
/*     */   public Boolean startCluster() throws Exception {
/* 667 */     logger.debug("startCluster...");
/* 668 */     Boolean result = Boolean.valueOf(false);
/*     */     try {
/* 670 */       HcceUtils.getCoordinatorCmRemote().startCluster();
/* 671 */     } catch (Exception e) {
/* 672 */       logger.warn("startCluster failed!", e);
/* 673 */       throw new Exception(e.getMessage());
/*     */     } 
/* 675 */     return result;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/stopCluster"}, method = {RequestMethod.POST})
/*     */   public Boolean stopCluster() throws Exception {
/* 680 */     logger.debug("stopCluster...");
/* 681 */     Boolean result = Boolean.valueOf(false);
/*     */     try {
/* 683 */       HcceUtils.getCoordinatorCmRemote().shutdownCluster();
/* 684 */     } catch (Exception e) {
/* 685 */       logger.warn("stopCluster failed!", e);
/* 686 */       throw new Exception(e.getMessage());
/*     */     } 
/* 688 */     return result;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/clientHeartbeat"}, method = {RequestMethod.POST})
/*     */   public void updateClientHeartbeat(@RequestBody ClientHeartbeat heartbeat) {
/*     */     try {
/* 694 */       IMap<String, ClientHeartbeat> heartbeatMap = HzUtils.getMap((HzDistObjEnum)HzMap.ClientHeartbeat);
/* 695 */       heartbeatMap.putIfAbsent(heartbeat.getId(), heartbeat);
/* 696 */       heartbeatMap.merge(heartbeat.getId(), heartbeat, (h1, h2) -> paramClientHeartbeat1);
/* 697 */       logger.debug("Update client heartbeat. Heartbeat: '{}'", heartbeat.toString());
/* 698 */     } catch (Exception e) {
/* 699 */       logger.error("Update client heartbeat failed!", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/checkNodeHeartbeat"}, method = {RequestMethod.GET})
/*     */   public Date checkNodeHeartbeat() {
/*     */     try {
/* 706 */       return new Date();
/* 707 */     } catch (Exception e) {
/* 708 */       logger.error("check node heartbeat failed!", e);
/* 709 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Pageable constructNodePerformanceLogsPageSpecification(int pageIndex) {
/* 720 */     return (Pageable)new PageRequest(pageIndex, 50, new Sort(Sort.Direction.DESC, new String[] { "recordTime" }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Pageable constructMonitorSystemsPageSpecification(int pageIndex) {
/* 726 */     return (Pageable)new PageRequest(pageIndex, 50, new Sort(Sort.Direction.DESC, new String[] { "updateDate" }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private DbStatusDTO processDbStatusCvsResult(CmdExecResult cmdExecResult) {
/* 732 */     DbStatusDTO result = new DbStatusDTO();
/* 733 */     result.setDescriptoin("");
/* 734 */     if (cmdExecResult != null) {
/* 735 */       logger.debug("cmdExecResult = {}", cmdExecResult);
/* 736 */       logger.debug("cmdExecResult.getExitStatus() = {}", cmdExecResult.getExitStatus());
/* 737 */       if (cmdExecResult.getStdOut() != null) {
/* 738 */         for (String msg : cmdExecResult.getStdOut()) {
/* 739 */           if (!result.getDescriptoin().isEmpty()) {
/* 740 */             result.setDescriptoin(result.getDescriptoin() + "\r");
/*     */           }
/* 742 */           result.setDescriptoin(result.getDescriptoin() + msg);
/* 743 */           logger.debug("StdOut : {}", msg);
/* 744 */           try (BufferedReader reader = new BufferedReader(new StringReader(msg))) {
/* 745 */             String line = reader.readLine();
/* 746 */             String[] lineSplit = line.split("\\s+");
/* 747 */             int length = lineSplit.length;
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
/*     */           }
/* 759 */           catch (IOException e) {
/* 760 */             logger.warn(e.getMessage(), e);
/*     */           } 
/*     */         } 
/*     */       }
/* 764 */       if (cmdExecResult.getStdErr() != null) {
/* 765 */         for (String msg : cmdExecResult.getStdErr()) {
/* 766 */           logger.debug("StdErr : {}", msg);
/*     */         }
/*     */       }
/*     */     } 
/* 770 */     return result;
/*     */   }
/*     */   
/*     */   private String createSshClient(String hostAlias) throws IOException {
/* 774 */     logger.info("Create Ssh client from remote, hostAlias:{}", hostAlias);
/* 775 */     String host = this.env.getProperty("ssh." + hostAlias + ".host");
/* 776 */     if (host == null) throw new IOException("Host alias: " + hostAlias + " not found!"); 
/* 777 */     String username = this.env.getProperty("ssh." + hostAlias + ".username");
/* 778 */     String privatekey = this.env.getProperty("ssh." + hostAlias + ".privatekey");
/* 779 */     File file = null;
/*     */     try {
/* 781 */       ClassPathResource cr = new ClassPathResource(privatekey);
/* 782 */       file = cr.getFile();
/* 783 */     } catch (FileNotFoundException e) {
/*     */ 
/*     */       
/* 786 */       FileSystemResource fs = new FileSystemResource(privatekey);
/* 787 */       file = fs.getFile();
/*     */     } 
/* 789 */     String privateKeyPassword = this.env.getProperty("ssh." + hostAlias + ".privatekey.password");
/* 790 */     logger.debug("connect test : '{}', '{}', '{}', '{}'", new Object[] { host, username, file, privateKeyPassword });
/* 791 */     String sshId = this.sshClientService.createSshClient(host, username, file, privateKeyPassword);
/* 792 */     logger.info("Create Ssh client from remote, sshId:{}", sshId);
/* 793 */     return sshId;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\restful\HcceRestServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */