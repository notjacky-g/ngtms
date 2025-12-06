/*     */ package com.hwacom.ngtms.hcce.am.server;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.crypto.TripleDESUtils;
/*     */ import com.hwacom.ngtms.base.hazelcast.HazelcastClient;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.shared.Pair;
/*     */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*     */ import com.hwacom.ngtms.hcce.shared.ClusterMode;
/*     */ import com.hwacom.ngtms.hcce.shared.FmeExeStatus;
/*     */ import com.hwacom.ngtms.hcce.shared.TopologyInfo;
/*     */ import com.hwacom.ngtms.hcce.shared.TopologyNode;
/*     */ import com.hwacom.ngtms.hcce.shared.dto.ClusterModeDTO;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.security.core.context.SecurityContextHolder;
/*     */ import org.springframework.web.bind.annotation.CrossOrigin;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @CrossOrigin
/*     */ @RestController
/*     */ @RequestMapping({"/first"})
/*     */ public class FmeAddressRestServiceImpl
/*     */ {
/*  46 */   private static final Logger logger = LoggerFactory.getLogger(FmeAddressRestServiceImpl.class);
/*     */   
/*     */   private static final String LAST_ACCESSED_TIME = "LastAccessedTime";
/*     */   
/*     */   @Autowired
/*     */   private HazelcastClient hazelcastClient;
/*     */   @Autowired
/*     */   private Environment environment;
/*     */   @Value("${nginx.activated:false}")
/*     */   private boolean nginxActivated;
/*     */   
/*     */   @RequestMapping(value = {"/getClusterMode"}, method = {RequestMethod.GET})
/*     */   private ClusterModeDTO getClusterModeDTO() {
/*  59 */     IMap<String, ClusterMode> clusterModeMap = this.hazelcastClient.getIMap((HzDistObjEnum)HzMap.ClusterMode);
/*     */ 
/*     */     
/*  62 */     ClusterMode clusterMode = Optional.<ClusterMode>ofNullable(clusterModeMap.values().iterator().next()).orElse(ClusterMode.DisConnected);
/*  63 */     return ClusterModeDTO.valueOf(clusterMode.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/getFmeAddress"}, method = {RequestMethod.POST})
/*     */   public Pair<String, Integer> getFmeAddress(@RequestBody String fmeName) {
/*     */     try {
/*  74 */       logger.debug("Get fme address, fmeName='{}'", fmeName);
/*  75 */       IMap<String, FmeExeStatus> fmeMap = this.hazelcastClient.getIMap((HzDistObjEnum)HzMap.FmeExeStatus);
/*  76 */       FmeExeStatus fmeStatus = (FmeExeStatus)fmeMap.get(fmeName + "Fm");
/*     */       
/*  78 */       Pair<String, Integer> result = retrieveNodeIpAndPort(this.hazelcastClient
/*  79 */           .getIMap((HzDistObjEnum)HzMap.TopologyInfo), nodeMap -> {
/*     */             List<Map.Entry<String, TopologyNode>> nodes = (List<Map.Entry<String, TopologyNode>>)nodeMap.entrySet().stream().filter(()).collect(Collectors.toList());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             return (TopologyNode)((Map.Entry)nodes.get(0)).getValue();
/*     */           });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  93 */       return result;
/*  94 */     } catch (RuntimeException e) {
/*  95 */       logger.error("Get fme address failed, fmeName='{}'", fmeName, e);
/*  96 */       return new Pair(null, Integer.valueOf(0));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Pair<String, Integer> retrieveNodeIpAndPort(IMap<String, TopologyInfo> topologyInfoMap, Function<Map<String, TopologyNode>, TopologyNode> function) {
/* 103 */     return retrieveNodeIpAndPort(this.environment
/* 104 */         .getProperty("hz.group.name"), topologyInfoMap, function);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Pair<String, Integer> retrieveNodeIpAndPort(String groupName, IMap<String, TopologyInfo> topologyInfoMap, Function<Map<String, TopologyNode>, TopologyNode> function) {
/* 111 */     TopologyInfo topologyInfo = (TopologyInfo)topologyInfoMap.get(groupName);
/* 112 */     Map<String, TopologyNode> topologyNodeMap = topologyInfo.getNodeMap();
/* 113 */     TopologyNode topologyNode = function.apply(topologyNodeMap);
/* 114 */     return new Pair(topologyNode
/* 115 */         .getLastRegIpAddress(), Integer.valueOf(topologyNode.getWebContainerPort()));
/*     */   }
/*     */   
/*     */   @RequestMapping({"/retrieveArbitraryNodeIpAndPort"})
/*     */   public Pair<String, Integer> retrieveArbitraryNodeIpAndPort(HttpServletRequest request) {
/* 120 */     if (this.environment.getProperty("hz.group.name").contains("back")) {
/*     */       try {
/* 122 */         if (this.hazelcastClient.getDrIMap((HzDistObjEnum)HzMap.TopologyInfo) != null) {
/* 123 */           return retrieveNodeIpAndPort(this.environment
/* 124 */               .getProperty("dr.hz.group.name"), this.hazelcastClient
/* 125 */               .getDrIMap((HzDistObjEnum)HzMap.TopologyInfo), topologyNodeMap -> specificOrArbitraryNode(topologyNodeMap, paramHttpServletRequest.getLocalAddr()));
/*     */         }
/*     */         
/* 128 */         return new Pair("", Integer.valueOf(0));
/*     */       }
/* 130 */       catch (Exception e) {
/* 131 */         logger.error("retrieveArbitraryNodeIpAndPort failed");
/* 132 */         return new Pair("", Integer.valueOf(0));
/*     */       } 
/*     */     }
/* 135 */     return retrieveSpecificOrArbitraryNodeIpAndPort(request.getLocalAddr());
/*     */   }
/*     */   
/*     */   @RequestMapping({"/retrieveSpecificIpAndPort"})
/*     */   public Pair<String, Integer> retrieveSpecificIpAndPort(HttpServletRequest request) {
/* 140 */     return retrieveSpecificOrArbitraryNodeIpAndPort(request.getLocalAddr());
/*     */   }
/*     */   
/*     */   private Pair<String, Integer> retrieveSpecificOrArbitraryNodeIpAndPort(String specificIp) {
/*     */     try {
/* 145 */       logger.debug("Retrieve arbitrary node to get ip and port.");
/*     */       
/* 147 */       Pair<String, Integer> result = retrieveNodeIpAndPort(this.hazelcastClient
/* 148 */           .getIMap((HzDistObjEnum)HzMap.TopologyInfo), topologyNodeMap -> specificOrArbitraryNode(topologyNodeMap, paramString));
/*     */       
/* 150 */       return result;
/* 151 */     } catch (RuntimeException e) {
/* 152 */       logger.error("Retrieve arbitrary node ip failed!", e);
/* 153 */       return new Pair(specificIp, Integer.valueOf(8090));
/*     */     } 
/*     */   }
/*     */   
/*     */   Pair<String, Integer> retrieveArbitraryNodeIpAndPort() {
/* 158 */     return retrieveSpecificOrArbitraryNodeIpAndPort("");
/*     */   }
/*     */ 
/*     */   
/*     */   private TopologyNode specificOrArbitraryNode(Map<String, TopologyNode> topologyNodeMap, String specificIp) {
/* 163 */     logger.debug("Specific or arbitrary node. specificIp: '{}'", specificIp);
/* 164 */     TopologyNode topologyNode = null;
/* 165 */     for (TopologyNode each : topologyNodeMap.values()) {
/*     */       try {
/* 167 */         if (each.isRegistered()) {
/* 168 */           logger.debug("Find alive node!, nodeAddr: {}", each.getLastRegIpAddress());
/* 169 */           topologyNode = each;
/*     */           break;
/*     */         } 
/* 172 */         logger.debug("Check node heartbeat. Response is null or body is null.");
/*     */       }
/* 174 */       catch (Exception e) {
/* 175 */         logger.error("Node check Heartbeat failed, nodeAddr: {}", each.getLastRegIpAddress(), e);
/*     */       } 
/*     */     } 
/* 178 */     return topologyNode;
/*     */   }
/*     */   
/*     */   @RequestMapping({"/retrieveArbitraryBackupNodeIpAndPort"})
/*     */   public Pair<String, Integer> retrieveArbitraryBackupNodeIpAndPort(HttpServletRequest request) {
/*     */     try {
/* 184 */       logger.debug("Retrieve arbitrary backup node to get ip and port.");
/* 185 */       if (this.environment.getProperty("hz.group.name").contains("back")) {
/* 186 */         return retrieveSpecificOrArbitraryNodeIpAndPort(request.getLocalAddr());
/*     */       }
/*     */       try {
/* 189 */         if (this.hazelcastClient.getDrIMap((HzDistObjEnum)HzMap.TopologyInfo) != null) {
/*     */           
/* 191 */           Pair<String, Integer> result = retrieveNodeIpAndPort(this.environment
/* 192 */               .getProperty("dr.hz.group.name"), this.hazelcastClient
/* 193 */               .getDrIMap((HzDistObjEnum)HzMap.TopologyInfo), topologyNodeMap -> specificOrArbitraryNode(topologyNodeMap, paramHttpServletRequest.getLocalAddr()));
/*     */ 
/*     */           
/* 196 */           return result;
/*     */         } 
/* 198 */         return new Pair("", Integer.valueOf(0));
/*     */       }
/* 200 */       catch (Exception e) {
/* 201 */         logger.error("retrieveArbitraryNodeIpAndPort failed");
/* 202 */         return new Pair("", Integer.valueOf(0));
/*     */       }
/*     */     
/* 205 */     } catch (RuntimeException e) {
/* 206 */       logger.error("Retrieve arbitrary backup node ip failed!", e);
/* 207 */       return new Pair("", Integer.valueOf(0));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping({"/retrieveEncryptedUserLogin"})
/*     */   public String retrieveEncryptedUserLogin() {
/*     */     try {
/* 215 */       String userLoginName = AuthenticationUtils.getUserLoginName(
/* 216 */           SecurityContextHolder.getContext().getAuthentication());
/* 217 */       logger.debug("user login name: '{}', authorities: '{}'", userLoginName, 
/*     */ 
/*     */           
/* 220 */           SecurityContextHolder.getContext().getAuthentication().getAuthorities());
/* 221 */       return TripleDESUtils.encrypt(userLoginName);
/* 222 */     } catch (RuntimeException e) {
/* 223 */       logger.error("Retrieve encrypted user login failed!");
/* 224 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/checkNginxActivated"}, method = {RequestMethod.GET})
/*     */   public boolean checkNginxActivated() {
/* 230 */     return this.nginxActivated;
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/keepHttpSessionAlive"}, method = {RequestMethod.GET})
/*     */   public void keepHttpSessionAlive(HttpServletRequest request) {
/*     */     try {
/* 236 */       HttpSession httpSession = request.getSession(false);
/* 237 */       if (httpSession != null) {
/* 238 */         httpSession.setAttribute("LastAccessedTime", Long.valueOf(System.currentTimeMillis()));
/*     */       }
/* 240 */     } catch (RuntimeException e) {
/* 241 */       logger.warn("Keep session alive failed!", e);
/*     */     } 
/*     */   }
/*     */   @RequestMapping(value = {"/isHttpSessionTimeout"}, method = {RequestMethod.GET})
/*     */   public boolean isHttpSessionTimeout(HttpServletRequest request) {
/*     */     try {
/*     */       long inactiveTime;
/* 248 */       if (isAnonymous()) {
/* 249 */         logger.debug("Anonymous user.");
/* 250 */         return false;
/*     */       } 
/* 252 */       HttpSession httpSession = request.getSession(false);
/* 253 */       if (httpSession == null) {
/* 254 */         logger.debug("HttpSession is null. remoteAddr: '{}'", request.getRemoteAddr());
/* 255 */         return true;
/*     */       } 
/* 257 */       Object lastAccessedTimeAttribute = httpSession.getAttribute("LastAccessedTime");
/*     */       
/* 259 */       if (lastAccessedTimeAttribute == null) {
/* 260 */         logger.debug("Attribute is not exist: '{}'", "LastAccessedTime");
/* 261 */         inactiveTime = 0L;
/*     */       } else {
/* 263 */         long lastAccessedTime = ((Long)lastAccessedTimeAttribute).longValue();
/* 264 */         inactiveTime = System.currentTimeMillis() - lastAccessedTime;
/* 265 */         logger.debug("Last accessed time: '{}', remoteAddr: '{}', user: '{}', inactiveTime: '{}'", new Object[] { new Date(lastAccessedTime), request
/*     */ 
/*     */               
/* 268 */               .getRemoteAddr(), 
/* 269 */               getUserName().orElse(""), 
/* 270 */               Long.valueOf(inactiveTime) });
/*     */       } 
/* 272 */       return (inactiveTime >= (httpSession.getMaxInactiveInterval() * 1000));
/* 273 */     } catch (RuntimeException e) {
/* 274 */       logger.warn("Determine http session timeout failed!", e);
/* 275 */       return true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isAnonymous() {
/* 280 */     return SecurityContextHolder.getContext().getAuthentication() instanceof org.springframework.security.authentication.AnonymousAuthenticationToken;
/*     */   }
/*     */ 
/*     */   
/*     */   private Optional<String> getUserName() {
/*     */     try {
/* 286 */       return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
/* 287 */     } catch (Exception e) {
/* 288 */       logger.warn("Can't get user name.", e);
/* 289 */       return Optional.empty();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\hcManager-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\am\server\FmeAddressRestServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */