/*     */ package com.hwacom.ngtms.hcce.topology;
/*     */ import com.hazelcast.core.HazelcastInstance;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.core.Member;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.hcce.core.exception.RegisterNodeException;
/*     */ import com.hwacom.ngtms.hcce.core.exception.TopologyNodeCfgException;
/*     */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*     */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*     */ import com.hwacom.ngtms.hcce.shared.TopologyInfo;
/*     */ import com.hwacom.ngtms.hcce.shared.TopologyNode;
/*     */ import com.hwacom.ngtms.hcce.shared.dto.TopologyGroup;
/*     */ import com.hwacom.ngtms.hcce.topology.model.TopologyNodeCfg;
/*     */ import com.hwacom.ngtms.hcce.topology.model.TopologyNodeCfgPk;
/*     */ import com.hwacom.ngtms.hcce.topology.repository.TopologyNodeCfgRepository;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class TopologyManager {
/*  35 */   private static Logger logger = LoggerFactory.getLogger(TopologyManager.class); @Autowired
/*     */   private TopologyNodeCfgRepository topologyNodeCfgRepository; @Autowired
/*     */   private HcceEnv hcceEnv;
/*     */   @Autowired
/*     */   private Environment env;
/*     */   private IMap<String, TopologyInfo> topologyMap;
/*  41 */   private Object syncObj = new Object();
/*  42 */   private AtomicBoolean freezeRegService = new AtomicBoolean();
/*     */ 
/*     */   
/*     */   public void init() {
/*  46 */     this.freezeRegService.set(false);
/*  47 */     this.topologyMap = HzUtils.getMap((HzDistObjEnum)HzMap.TopologyInfo);
/*  48 */     loadTopologyInfo();
/*     */   }
/*     */   
/*     */   public boolean isFreezeRegService() {
/*  52 */     return this.freezeRegService.get();
/*     */   }
/*     */   
/*     */   public void setFreezeRegService() {
/*  56 */     logger.info("Freeze Registration service");
/*  57 */     this.freezeRegService.set(true);
/*     */   }
/*     */   
/*     */   public TopologyInfo getTopologyInfo(String groupName) {
/*  61 */     return (TopologyInfo)this.topologyMap.get(groupName);
/*     */   }
/*     */   
/*     */   private void setTopologyInfo(String groupName, TopologyInfo topologyInfo) {
/*  65 */     this.topologyMap.set(groupName, topologyInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, TopologyInfo> createTopologyInfo() {
/*  75 */     return createTopology();
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
/*     */   public void putTopology2Map(HazelcastInstance instance, Map<String, TopologyInfo> topologies) {
/*  87 */     IMap<Object, Object> map = instance.getMap(HzMap.TopologyInfo.toString());
/*  88 */     map.set(getPrimaryGroupName(), getPrimaryTopologyInfo(topologies));
/*  89 */     map.set(getBackupGroupName(), getBackupTopologyInfo(topologies));
/*  90 */     Optional.<String>ofNullable(getClientGroupName())
/*  91 */       .ifPresent(clientGroupName -> paramIMap.set(clientGroupName, getClientTopologyInfo(paramMap)));
/*     */   }
/*     */   
/*     */   private TopologyInfo getPrimaryTopologyInfo(Map<String, TopologyInfo> topologies) {
/*  95 */     return topologies.get("primaryTopologyInfo");
/*     */   }
/*     */   
/*     */   private void loadTopologyInfo() {
/*  99 */     synchronized (this.syncObj) {
/*     */       
/* 101 */       TopologyInfo topologyInfo = getTopologyInfo(this.hcceEnv.getCurrentGroupName());
/* 102 */       if (topologyInfo != null) {
/* 103 */         if (logger.isDebugEnabled())
/* 104 */           logger.debug("TopologyInfo exists in IMDG, skip loading procedure"); 
/*     */         return;
/*     */       } 
/* 107 */       Map<String, TopologyInfo> topologies = createTopology();
/* 108 */       if (topologies.size() == 0) {
/*     */         return;
/*     */       }
/* 111 */       setTopologyInfo(getPrimaryGroupName(), getPrimaryTopologyInfo(topologies));
/* 112 */       setTopologyInfo(getBackupGroupName(), getBackupTopologyInfo(topologies));
/* 113 */       Optional.<String>ofNullable(getClientGroupName())
/* 114 */         .ifPresent(clientGroupName -> setTopologyInfo(clientGroupName, getClientTopologyInfo(paramMap)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private TopologyInfo getClientTopologyInfo(Map<String, TopologyInfo> topologies) {
/* 121 */     return topologies.get("clientTopologyInfo");
/*     */   }
/*     */   
/*     */   private String getBackupGroupName() {
/* 125 */     return this.hcceEnv.getBackupGroupName();
/*     */   }
/*     */   
/*     */   private String getPrimaryGroupName() {
/* 129 */     return this.hcceEnv.getPrimaryGroupName();
/*     */   }
/*     */   
/*     */   private String getClientGroupName() {
/* 133 */     return this.hcceEnv.getClientGroupName();
/*     */   }
/*     */   
/*     */   private Map<String, TopologyInfo> createTopology() {
/* 137 */     HashMap<String, TopologyInfo> result = new HashMap<>();
/* 138 */     logger.info("Load topology node config from DB");
/* 139 */     List<TopologyNodeCfg> nodeCfgs = this.topologyNodeCfgRepository.findAll();
/* 140 */     TopologyInfo primaryTopologyInfo = new TopologyInfo();
/* 141 */     primaryTopologyInfo.setStartTime(new Date());
/* 142 */     TopologyInfo backupTopologyInfo = new TopologyInfo();
/* 143 */     backupTopologyInfo.setStartTime(new Date());
/* 144 */     TopologyInfo clientTopologyInfo = new TopologyInfo();
/* 145 */     clientTopologyInfo.setStartTime(new Date());
/* 146 */     if (nodeCfgs.size() > 0) {
/* 147 */       for (TopologyNodeCfg nodeCfg : nodeCfgs) {
/* 148 */         logger.info("TopologyNodeCfg : " + nodeCfg);
/* 149 */         if (nodeCfg.getGroupName().equals(getPrimaryGroupName())) {
/* 150 */           primaryTopologyInfo.addTopologyNode(nodeCfg); continue;
/* 151 */         }  if (nodeCfg.getGroupName().equals(getBackupGroupName())) {
/* 152 */           backupTopologyInfo.addTopologyNode(nodeCfg); continue;
/* 153 */         }  if (nodeCfg.getGroupName().equals(getClientGroupName())) {
/* 154 */           clientTopologyInfo.addTopologyNode(nodeCfg); continue;
/*     */         } 
/* 156 */         logger.warn("Unkown group name: {}, give up the topology node config: {}", nodeCfg
/*     */             
/* 158 */             .getGroupName(), nodeCfg);
/*     */       } 
/*     */     }
/*     */     
/* 162 */     if (primaryTopologyInfo.getNodeMap() == null) primaryTopologyInfo.addTopologyNode(null); 
/* 163 */     if (backupTopologyInfo.getNodeMap() == null) backupTopologyInfo.addTopologyNode(null); 
/* 164 */     if (clientTopologyInfo.getNodeMap() == null) clientTopologyInfo.addTopologyNode(null); 
/* 165 */     result.put("primaryTopologyInfo", primaryTopologyInfo);
/* 166 */     result.put("backupTopologyInfo", backupTopologyInfo);
/* 167 */     result.put("clientTopologyInfo", clientTopologyInfo);
/* 168 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<TopologyNode> forceReloadTopologyInfo() {
/* 177 */     synchronized (this.syncObj) {
/* 178 */       TopologyInfo newTopologyInfo, curTopologyInfo; Map<String, TopologyInfo> topology = createTopology();
/* 179 */       TopologyInfo newPrimaryTopologyInfo = getPrimaryTopologyInfo(topology);
/* 180 */       TopologyInfo newBackupTopologyInfo = getBackupTopologyInfo(topology);
/*     */ 
/*     */       
/* 183 */       if (this.hcceEnv.isInPrimaryGroup()) {
/* 184 */         newTopologyInfo = newPrimaryTopologyInfo;
/* 185 */         curTopologyInfo = getTopologyInfo(getPrimaryGroupName());
/*     */       } else {
/* 187 */         newTopologyInfo = newBackupTopologyInfo;
/* 188 */         curTopologyInfo = getTopologyInfo(getBackupGroupName());
/*     */       } 
/* 190 */       List<TopologyNode> evictTopologyNodes = new ArrayList<>();
/* 191 */       for (TopologyNode curNode : curTopologyInfo.getNodeMap().values()) {
/* 192 */         TopologyNode newNode = (TopologyNode)newTopologyInfo.getNodeMap().get(curNode.getNodeCfg().getNodeName());
/* 193 */         if (newNode == null) {
/* 194 */           if (curNode.isRegistered()) evictTopologyNodes.add(curNode);  continue;
/*     */         } 
/* 196 */         newNode.setMemberUuid(curNode.getMemberUuid());
/* 197 */         newNode.setRegistered(curNode.isCoordinator());
/* 198 */         newNode.setCoordinator(curNode.isCoordinator());
/* 199 */         newNode.setRegisterTime(curNode.getRegisterTime());
/* 200 */         newNode.setUnregisterTime(curNode.getUnregisterTime());
/* 201 */         newNode.setLastRegIpAddress(curNode.getLastRegIpAddress());
/* 202 */         newNode.setWebContainerPort(((Integer)this.env.getProperty("server.port", Integer.class)).intValue());
/*     */       } 
/*     */ 
/*     */       
/* 206 */       setTopologyInfo(getPrimaryGroupName(), newPrimaryTopologyInfo);
/* 207 */       setTopologyInfo(getBackupGroupName(), newBackupTopologyInfo);
/*     */       
/* 209 */       return evictTopologyNodes;
/*     */     } 
/*     */   }
/*     */   
/*     */   private TopologyInfo getBackupTopologyInfo(Map<String, TopologyInfo> topology) {
/* 214 */     return topology.get("backupTopologyInfo");
/*     */   }
/*     */   
/*     */   public boolean isNodeExist(String nodeName) {
/* 218 */     synchronized (this.syncObj) {
/* 219 */       TopologyInfo topologyInfo = getTopologyInfo(this.hcceEnv.getCurrentGroupName());
/* 220 */       if (topologyInfo != null) {
/* 221 */         Map<String, TopologyNode> nodeMap = topologyInfo.getNodeMap();
/* 222 */         if (nodeMap != null) {
/* 223 */           TopologyNode node = nodeMap.get(nodeName);
/* 224 */           if (node != null) return true; 
/*     */         } 
/*     */       } 
/* 227 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setCoordinatorNodeByName(String nodeName) {
/* 232 */     synchronized (this.syncObj) {
/* 233 */       TopologyInfo topologyInfo = getTopologyInfo(this.hcceEnv.getCurrentGroupName());
/*     */       
/* 235 */       for (TopologyNode n : topologyInfo.getNodeMap().values()) {
/* 236 */         n.setCoordinator(false);
/*     */       }
/* 238 */       TopologyNode node = (TopologyNode)topologyInfo.getNodeMap().get(nodeName);
/* 239 */       node.setCoordinator(true);
/* 240 */       setTopologyInfo(this.hcceEnv.getCurrentGroupName(), topologyInfo);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> refleshTopologyInfo() {
/* 249 */     synchronized (this.syncObj) {
/* 250 */       if (logger.isDebugEnabled()) logger.debug("Refresh Topology Info"); 
/* 251 */       Set<Member> members = HzUtils.getMembers();
/* 252 */       TopologyInfo topologyInfo = getTopologyInfo(this.hcceEnv.getCurrentGroupName());
/* 253 */       List<String> removedMembers = new ArrayList<>();
/* 254 */       for (TopologyNode n : topologyInfo.getNodeMap().values()) {
/* 255 */         if (n.getMemberUuid() == null) {
/*     */           continue;
/*     */         }
/* 258 */         boolean needsUpdate = true;
/* 259 */         for (Member m : members) {
/* 260 */           if (n.getMemberUuid().equals(m.getUuid())) {
/* 261 */             needsUpdate = false;
/*     */             break;
/*     */           } 
/*     */         } 
/* 265 */         if (needsUpdate) {
/*     */           
/* 267 */           logger.info("Remove the registration of {}", n.getNodeCfg().getNodeName());
/* 268 */           removedMembers.add(n.getMemberUuid());
/* 269 */           n.setMemberUuid(null);
/* 270 */           n.setRegistered(false);
/* 271 */           n.setRegisterTime(new Date());
/*     */         } 
/*     */       } 
/* 274 */       setTopologyInfo(this.hcceEnv.getCurrentGroupName(), topologyInfo);
/* 275 */       logger.info("Current topologyInfo : " + topologyInfo);
/* 276 */       return removedMembers;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void eraseNodeIpData(String nodeName) {
/* 281 */     synchronized (this.syncObj) {
/* 282 */       TopologyInfo topologyInfo = getTopologyInfo(this.hcceEnv.getCurrentGroupName());
/* 283 */       TopologyNode node = (TopologyNode)topologyInfo.getNodeMap().get(nodeName);
/* 284 */       if (node == null)
/* 285 */         return;  if (node.isRegistered())
/* 286 */         return;  node.setLastRegIpAddress(null);
/* 287 */       node.setWebContainerPort(0);
/* 288 */       setTopologyInfo(this.hcceEnv.getCurrentGroupName(), topologyInfo);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void registerNode(String nodeName, Member member) throws RegisterNodeException {
/* 293 */     synchronized (this.syncObj) {
/* 294 */       TopologyInfo topologyInfo = getTopologyInfo(this.hcceEnv.getCurrentGroupName());
/* 295 */       TopologyNode node = (TopologyNode)topologyInfo.getNodeMap().get(nodeName);
/* 296 */       if (node == null) {
/* 297 */         throw new RegisterNodeException("The node " + nodeName + " is not defined in Topology");
/*     */       }
/* 299 */       if (node.isRegistered()) {
/* 300 */         throw new RegisterNodeException("The node " + nodeName + " has been register by " + 
/*     */ 
/*     */ 
/*     */             
/* 304 */             HzUtils.findMemberByUuid(node
/* 305 */               .getMemberUuid() + ", reject the registration from " + member));
/*     */       }
/* 307 */       if (this.freezeRegService.get()) {
/* 308 */         throw new RegisterNodeException("The registration service has been frozen.");
/*     */       }
/* 310 */       node.setMemberUuid(member.getUuid());
/* 311 */       node.setRegistered(true);
/* 312 */       node.setRegisterTime(new Date());
/* 313 */       node.setUnregisterTime(null);
/* 314 */       node.setLastRegIpAddress(member.getSocketAddress().getAddress().getHostAddress());
/* 315 */       node.setWebContainerPort(((Integer)this.env.getProperty("server.port", Integer.class)).intValue());
/* 316 */       logger.info("The topology node {} of group {} has been registered to ({}) successfully", new Object[] { nodeName, this.hcceEnv
/*     */ 
/*     */             
/* 319 */             .getCurrentGroupName(), member });
/*     */       
/* 321 */       setTopologyInfo(this.hcceEnv.getCurrentGroupName(), topologyInfo);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void registerClientNode(String nodeName, String ip) {
/* 326 */     synchronized (this.syncObj) {
/* 327 */       TopologyInfo topologyInfo = getTopologyInfo(TopologyGroup.HC_CLIENT_GROUP.toString());
/* 328 */       if (topologyInfo == null) {
/* 329 */         logger.error("The groupName '{}' is not defined in Topology", TopologyGroup.HC_CLIENT_GROUP
/*     */             
/* 331 */             .toString());
/*     */         return;
/*     */       } 
/* 334 */       TopologyNode node = (TopologyNode)topologyInfo.getNodeMap().get(nodeName);
/* 335 */       if (node == null) {
/* 336 */         logger.error("The node '{}' is not defined in Topology", nodeName);
/*     */         return;
/*     */       } 
/* 339 */       if (node.isRegistered()) {
/*     */         return;
/*     */       }
/* 342 */       node.setRegistered(true);
/* 343 */       node.setRegisterTime(new Date());
/* 344 */       node.setUnregisterTime(null);
/* 345 */       node.setLastRegIpAddress(ip);
/* 346 */       logger.info("The topology node {} of group {} has been registered successfully", nodeName, TopologyGroup.HC_CLIENT_GROUP
/*     */ 
/*     */           
/* 349 */           .toString());
/* 350 */       setTopologyInfo(TopologyGroup.HC_CLIENT_GROUP.toString(), topologyInfo);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unregisterNode(Member member) {
/* 355 */     synchronized (this.syncObj) {
/* 356 */       TopologyInfo topologyInfo = getTopologyInfo(this.hcceEnv.getCurrentGroupName());
/* 357 */       for (TopologyNode n : topologyInfo.getNodeMap().values()) {
/* 358 */         if (!n.isRegistered()) {
/*     */           continue;
/*     */         }
/* 361 */         if (member.getUuid().equals(n.getMemberUuid())) {
/* 362 */           n.setMemberUuid(null);
/* 363 */           n.setRegistered(false);
/* 364 */           n.setRegisterTime(new Date());
/* 365 */           logger.info("The topology node {} has been unregistered from {}", n
/*     */               
/* 367 */               .getNodeCfg().getNodeName(), member);
/*     */           
/* 369 */           setTopologyInfo(this.hcceEnv.getCurrentGroupName(), topologyInfo);
/* 370 */           HzUtils.getQueue((HzDistObjEnum)HzQueue.NodeUnregistered).offer(n);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unregisterClientNode(String nodeName) {
/* 378 */     synchronized (this.syncObj) {
/* 379 */       TopologyInfo topologyInfo = getTopologyInfo(TopologyGroup.HC_CLIENT_GROUP.toString());
/* 380 */       if (topologyInfo != null) {
/* 381 */         for (TopologyNode n : topologyInfo.getNodeMap().values()) {
/* 382 */           if (!n.isRegistered()) {
/*     */             continue;
/*     */           }
/* 385 */           if (Objects.equals(n.getNodeCfg().getNodeName(), nodeName)) {
/* 386 */             n.setMemberUuid(null);
/* 387 */             n.setRegistered(false);
/* 388 */             n.setRegisterTime(new Date());
/* 389 */             logger.info("The topology node {} has been unregistered from {}", n
/*     */                 
/* 391 */                 .getNodeCfg().getNodeName(), TopologyGroup.HC_CLIENT_GROUP
/* 392 */                 .toString());
/* 393 */             setTopologyInfo(TopologyGroup.HC_CLIENT_GROUP.toString(), topologyInfo);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 398 */         logger.debug("Can't find node: '{}' from '{}'", nodeName, TopologyGroup.HC_CLIENT_GROUP
/* 399 */             .toString());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeClientNode(String nodeName) {
/* 405 */     synchronized (this.syncObj) {
/* 406 */       TopologyInfo topologyInfo = getTopologyInfo(TopologyGroup.HC_CLIENT_GROUP.toString());
/* 407 */       if (topologyInfo != null) {
/* 408 */         if (topologyInfo.getNodeMap() != null && topologyInfo.getNodeMap().get(nodeName) != null) {
/* 409 */           topologyInfo.getNodeMap().remove(nodeName);
/* 410 */           logger.info("The topology node {} has been removed from {}", nodeName, TopologyGroup.HC_CLIENT_GROUP
/*     */ 
/*     */               
/* 413 */               .toString());
/* 414 */           setTopologyInfo(TopologyGroup.HC_CLIENT_GROUP.toString(), topologyInfo);
/*     */         } 
/*     */       } else {
/* 417 */         logger.debug("Can't find node: '{}' from '{}'", nodeName, TopologyGroup.HC_CLIENT_GROUP
/* 418 */             .toString());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNodeAlreadyRegistered(String groupName, String nodeName) {
/* 436 */     TopologyInfo topologyInfo = getTopologyInfo(groupName);
/* 437 */     if (topologyInfo != null) {
/* 438 */       TopologyNode node = (TopologyNode)topologyInfo.getNodeMap().get(nodeName);
/* 439 */       if (node != null && node.isRegistered()) return true; 
/*     */     } 
/* 441 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TopologyNode getNodeByMemberUuid(String memberUuid) {
/* 451 */     synchronized (this.syncObj) {
/*     */       
/* 453 */       Map<String, TopologyNode> nodeMap = getTopologyInfo(this.hcceEnv.getCurrentGroupName()).getNodeMap();
/* 454 */       for (TopologyNode node : nodeMap.values()) {
/* 455 */         if (memberUuid.equals(node.getMemberUuid())) return node; 
/*     */       } 
/* 457 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public TopologyNode getNodeByName(String nodeName) {
/* 462 */     synchronized (this.syncObj) {
/* 463 */       return (TopologyNode)getTopologyInfo(this.hcceEnv.getCurrentGroupName()).getNodeMap().get(nodeName);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, TopologyNode> getRegistedMemberMap() {
/* 472 */     synchronized (this.syncObj) {
/* 473 */       Map<String, TopologyNode> nodeMemberMap = new TreeMap<>();
/*     */       
/* 475 */       Map<String, TopologyNode> nodeMap = getTopologyInfo(this.hcceEnv.getCurrentGroupName()).getNodeMap();
/* 476 */       for (TopologyNode node : nodeMap.values()) {
/* 477 */         if (node.isRegistered()) nodeMemberMap.put(node.getNodeCfg().getNodeName(), node); 
/*     */       } 
/* 479 */       return nodeMemberMap;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addNode(TopologyNodeCfg nodeCfg) throws TopologyNodeCfgException {
/* 484 */     verifyNodeData(nodeCfg);
/* 485 */     synchronized (this.syncObj) {
/* 486 */       TopologyInfo topologyInfo = getTopologyInfo(nodeCfg.getGroupName());
/* 487 */       TopologyNode node = (TopologyNode)topologyInfo.getNodeMap().get(nodeCfg.getNodeName());
/* 488 */       if (node != null)
/* 489 */         throw new TopologyNodeCfgException("The name " + nodeCfg
/* 490 */             .getNodeName() + " has existed in topology"); 
/* 491 */       topologyInfo.addTopologyNode(nodeCfg);
/*     */       
/* 493 */       setTopologyInfo(nodeCfg.getGroupName(), topologyInfo);
/* 494 */       this.topologyNodeCfgRepository.save(nodeCfg);
/* 495 */       logger.info("Add a new node to Topology : {}", nodeCfg);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeNode(String groupName, String nodeName) {
/* 500 */     synchronized (this.syncObj) {
/* 501 */       TopologyInfo topologyInfo = getTopologyInfo(groupName);
/* 502 */       topologyInfo.removeTopologyNode(nodeName);
/*     */       
/* 504 */       setTopologyInfo(groupName, topologyInfo);
/* 505 */       this.topologyNodeCfgRepository.deleteById(new TopologyNodeCfgPk(groupName, nodeName));
/* 506 */       logger.info("Remove the node: {} from Topology", nodeName);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void modifyNode(TopologyNodeCfg newCfg) throws TopologyNodeCfgException {
/* 516 */     synchronized (this.syncObj) {
/* 517 */       TopologyInfo topologyInfo = getTopologyInfo(newCfg.getGroupName());
/* 518 */       TopologyNode node = (TopologyNode)topologyInfo.getNodeMap().get(newCfg.getNodeName());
/* 519 */       if (node == null)
/* 520 */         throw new TopologyNodeCfgException("The name " + newCfg
/*     */             
/* 522 */             .getNodeName() + " was not found in topology of group " + newCfg
/*     */             
/* 524 */             .getGroupName()); 
/* 525 */       TopologyNodeCfg oriNodeCfg = node.getNodeCfg();
/* 526 */       node.setNodeCfg(newCfg);
/*     */       
/* 528 */       setTopologyInfo(newCfg.getGroupName(), topologyInfo);
/* 529 */       TopologyNodeCfg nodeCfg = this.topologyNodeCfgRepository.findById(newCfg.getPk()).orElse(null);
/* 530 */       logger.info("Modify node config of {} from {} to {}", new Object[] { newCfg
/*     */             
/* 532 */             .getNodeName(), oriNodeCfg, newCfg });
/* 533 */       nodeCfg.setDescription(newCfg.getDescription());
/* 534 */       this.topologyNodeCfgRepository.save(nodeCfg);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void verifyNodeData(TopologyNodeCfg nodeCfg) {
/* 545 */     if (StringUtils.isBlank(nodeCfg.getGroupName())) {
/* 546 */       throw new IllegalArgumentException("Group name cannot be empty.");
/*     */     }
/* 548 */     if (StringUtils.isBlank(nodeCfg.getNodeName())) {
/* 549 */       throw new IllegalArgumentException("Node name cannot be empty.");
/*     */     }
/* 551 */     if (StringUtils.isBlank(nodeCfg.getDescription()))
/* 552 */       throw new IllegalArgumentException("Description cannot be empty."); 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\topology\TopologyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */