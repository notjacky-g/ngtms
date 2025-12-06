/*     */ package com.hwacom.ngtms.hcce.web.controller;
/*     */ 
/*     */ import com.hazelcast.config.Config;
/*     */ import com.hazelcast.config.MapConfig;
/*     */ import com.hazelcast.core.Client;
/*     */ import com.hazelcast.core.DistributedObject;
/*     */ import com.hazelcast.core.HazelcastInstance;
/*     */ import com.hazelcast.core.ILock;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.core.IQueue;
/*     */ import com.hazelcast.core.Member;
/*     */ import com.hazelcast.internal.json.JsonObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.SqlPredicate;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzClientUtils;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.sms.SmsClient;
/*     */ import com.hwacom.ngtms.base.sms.shared.SmsSendResponse;
/*     */ import com.hwacom.ngtms.hcce.core.NodeManager;
/*     */ import com.hwacom.ngtms.hcce.hz.DistObjType;
/*     */ import com.hwacom.ngtms.hcce.hz.HzDistObjRegister;
/*     */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*     */ import com.hwacom.ngtms.hcce.web.service.ReflectionToStringBuilderHtml;
/*     */ import com.hwacom.ngtms.hcce.web.vo.ClientInfo;
/*     */ import com.hwacom.ngtms.hcce.web.vo.DistObjInfo;
/*     */ import com.hwacom.ngtms.hcce.web.vo.HcNodeInfo;
/*     */ import com.hwacom.ngtms.hcce.web.vo.IlockInfo;
/*     */ import com.hwacom.ngtms.hcce.web.vo.MemberInfo;
/*     */ import com.hwacom.ngtms.hcce.web.vo.RequestForm;
/*     */ import com.hwacom.ngtms.hcce.web.vo.SmsInfo;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.annotation.PreDestroy;
/*     */ import javax.servlet.ServletContext;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.bind.annotation.RestController;
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
/*     */ @RestController
/*     */ @RequestMapping({"/hcConsole"})
/*     */ public class HcConsoleRestController
/*     */ {
/*  70 */   private static Logger logger = LoggerFactory.getLogger(HcConsoleRestController.class);
/*  71 */   private SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
/*     */   
/*     */   @Autowired
/*     */   private HcceEnv hcceEnv;
/*     */   @Autowired
/*     */   private Config hazelcastConfig;
/*     */   @Autowired
/*     */   private ServletContext servletContext;
/*  79 */   private AtomicBoolean running = new AtomicBoolean(true); @Autowired
/*     */   private NodeManager nodeManager; @Autowired
/*     */   private HzDistObjRegister hzDistObjRegister; @Autowired
/*     */   private SmsClient smsClient; private HcNodeInfo hcNodeInfo; private Thread hzClientStartThread; private HzClientUtils hzClientUtils;
/*     */   @PostConstruct
/*     */   public void init() {
/*  85 */     this.hzClientStartThread = new Thread(new Runnable()
/*     */         {
/*     */           
/*     */           public void run()
/*     */           {
/*  90 */             while (HcConsoleRestController.this.running.get()) {
/*     */               try {
/*  92 */                 TimeUnit.SECONDS.sleep(5L);
/*  93 */                 HazelcastInstance hazelcastInstance = HzUtils.getHzInstance();
/*  94 */                 if (hazelcastInstance != null) {
/*     */                   try {
/*  96 */                     Member localMember = hazelcastInstance.getCluster().getLocalMember();
/*  97 */                     HcConsoleRestController.this.hzClientUtils = 
/*  98 */                       HzClientUtils.create(hazelcastInstance
/*     */                         
/* 100 */                         .getConfig()
/* 101 */                         .getGroupConfig()
/* 102 */                         .getName(), hazelcastInstance
/*     */                         
/* 104 */                         .getConfig()
/* 105 */                         .getGroupConfig()
/* 106 */                         .getPassword(), false, 2000, 2, new String[] { localMember
/*     */ 
/*     */ 
/*     */                           
/* 110 */                           .getSocketAddress().getAddress().getHostAddress() + ":" + localMember
/*     */                           
/* 112 */                           .getSocketAddress().getPort() });
/* 113 */                     HcConsoleRestController.logger.debug("Create Hz Client successfully!");
/* 114 */                   } catch (Exception ex) {
/* 115 */                     HcConsoleRestController.logger.error("Failed to create Hz Client", ex);
/*     */                   } 
/*     */                   break;
/*     */                 } 
/* 119 */               } catch (InterruptedException interruptedException) {}
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 125 */     this.hzClientStartThread.setDaemon(true);
/* 126 */     this.hzClientStartThread.start();
/*     */   }
/*     */   
/*     */   @PreDestroy
/*     */   public void stop() {
/* 131 */     this.running.set(false);
/* 132 */     if (this.hzClientStartThread != null) this.hzClientStartThread.interrupt(); 
/* 133 */     if (this.hzClientUtils != null) {
/* 134 */       this.hzClientUtils.shutdown();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/hzMemberData"}, method = {RequestMethod.GET})
/*     */   public Map<String, Object> hzMemberData() {
/* 143 */     HazelcastInstance hazelcastInstance = HzUtils.getHzInstance();
/* 144 */     HashMap<String, Object> result = new HashMap<>();
/* 145 */     result.put("timestamp", this.sdFormat.format(new Date()));
/* 146 */     List<ClientInfo> clientList = new ArrayList<>();
/* 147 */     List<MemberInfo> memberList = new ArrayList<>();
/* 148 */     if (hazelcastInstance != null) {
/* 149 */       Collection<Client> clients = hazelcastInstance.getClientService().getConnectedClients();
/* 150 */       for (Client client : clients) {
/* 151 */         ClientInfo clientInfo = new ClientInfo();
/* 152 */         clientInfo.setAddress(client.getSocketAddress().toString());
/* 153 */         clientInfo.setClient(client.toString());
/* 154 */         clientList.add(clientInfo);
/*     */       } 
/*     */       
/* 157 */       boolean first = true;
/* 158 */       for (Member member : hazelcastInstance.getCluster().getMembers()) {
/* 159 */         MemberInfo memberInfo = new MemberInfo();
/* 160 */         memberInfo.setUuid(member.getUuid());
/* 161 */         if (first) {
/* 162 */           first = false;
/* 163 */           memberInfo.setCoordinator(true);
/*     */         } 
/* 165 */         memberInfo.setAddress(member.getSocketAddress().toString());
/* 166 */         memberInfo.setLocalMember(member.localMember());
/* 167 */         memberInfo.setAttributes(member.getAttributes().toString());
/* 168 */         memberList.add(memberInfo);
/*     */       } 
/*     */     } 
/* 171 */     result.put("clients", clientList);
/* 172 */     result.put("members", memberList);
/* 173 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/hzDistObjData"}, method = {RequestMethod.GET})
/*     */   public Map<String, Object> hzDistObjData() {
/* 181 */     HazelcastInstance hazelcastInstance = HzUtils.getHzInstance();
/* 182 */     HashMap<String, Object> result = new HashMap<>();
/* 183 */     result.put("timestamp", this.sdFormat.format(new Date()));
/* 184 */     List<DistObjInfo> distObjList = new ArrayList<>();
/* 185 */     if (hazelcastInstance != null) {
/* 186 */       Collection<DistributedObject> distObjs = hazelcastInstance.getDistributedObjects();
/* 187 */       StringBuilder comment = new StringBuilder();
/* 188 */       for (DistributedObject distObj : distObjs) {
/* 189 */         DistObjInfo distObjInfo = new DistObjInfo();
/* 190 */         distObjInfo.setName(distObj.getName());
/* 191 */         comment.setLength(0);
/* 192 */         if (distObj instanceof IMap) {
/* 193 */           distObjInfo.setType("IMap");
/* 194 */           distObjInfo.setPredicate(true);
/* 195 */           distObjInfo.setSize(((IMap)distObj).size());
/* 196 */           MapConfig mapConfig = hazelcastInstance.getConfig().getMapConfig(distObj.getName());
/*     */           
/* 198 */           HzDistObjEnum hzDistObjEnum = this.hzDistObjRegister.getDistObjEnum(DistObjType.MAP + "+" + distObj.getName());
/* 199 */           if (hzDistObjEnum != null) {
/* 200 */             comment.append(hzDistObjEnum.getDescription());
/*     */           }
/*     */           
/* 203 */           if (mapConfig.getMapStoreConfig() != null && mapConfig
/* 204 */             .getMapStoreConfig().isEnabled()) {
/* 205 */             distObjInfo.setDestroy(true);
/* 206 */             Object impl = mapConfig.getMapStoreConfig().getImplementation();
/* 207 */             if (impl != null) {
/* 208 */               if (comment.length() > 0) comment.append(", "); 
/* 209 */               comment.append("MapStore: " + impl.getClass().getSimpleName());
/*     */             } 
/*     */           } 
/* 212 */           distObjInfo.setQuery(true);
/* 213 */           distObjInfo.setPerformance(true);
/* 214 */         } else if (distObj instanceof IQueue) {
/* 215 */           distObjInfo.setType("IQueue");
/* 216 */           distObjInfo.setSize(((IQueue)distObj).size());
/* 217 */           distObjInfo.setDestroy(true);
/* 218 */           distObjInfo.setQuery(true);
/* 219 */           distObjInfo.setPerformance(true);
/*     */           
/* 221 */           HzDistObjEnum hzDistObjEnum = this.hzDistObjRegister.getDistObjEnum(DistObjType.QUEUE + "+" + distObj.getName());
/* 222 */           if (hzDistObjEnum != null) {
/* 223 */             comment.append(hzDistObjEnum.getDescription());
/*     */           }
/* 225 */         } else if (distObj instanceof com.hazelcast.core.ITopic) {
/* 226 */           distObjInfo.setType("ITopic");
/* 227 */           distObjInfo.setQuery(true);
/* 228 */           distObjInfo.setPerformance(true);
/*     */           
/* 230 */           HzDistObjEnum hzDistObjEnum = this.hzDistObjRegister.getDistObjEnum(DistObjType.TOPIC + "+" + distObj.getName());
/* 231 */           if (hzDistObjEnum != null) {
/* 232 */             comment.append(hzDistObjEnum.getDescription());
/*     */           }
/* 234 */         } else if (distObj instanceof ILock) {
/* 235 */           distObjInfo.setType("ILock");
/* 236 */           distObjInfo.setDestroy(true);
/* 237 */           distObjInfo.setQuery(true);
/*     */           
/* 239 */           HzDistObjEnum hzDistObjEnum = this.hzDistObjRegister.getDistObjEnum(DistObjType.LOCK + "+" + distObj.getName());
/* 240 */           if (hzDistObjEnum != null) {
/* 241 */             comment.append(hzDistObjEnum.getDescription());
/*     */           }
/* 243 */         } else if (distObj instanceof com.hazelcast.core.MultiMap) {
/* 244 */           distObjInfo.setType("MultiMap");
/* 245 */           distObjInfo.setPerformance(true);
/*     */           
/* 247 */           HzDistObjEnum hzDistObjEnum = this.hzDistObjRegister.getDistObjEnum(DistObjType.MULTI_MAP + "+" + distObj.getName());
/* 248 */           if (hzDistObjEnum != null) {
/* 249 */             comment.append(hzDistObjEnum.getDescription());
/*     */           }
/* 251 */         } else if (distObj instanceof com.hazelcast.core.IExecutorService) {
/* 252 */           distObjInfo.setType("IExecutorService");
/* 253 */           distObjInfo.setPerformance(true);
/*     */         } 
/*     */         
/* 256 */         if (comment.length() > 0) {
/* 257 */           distObjInfo.setComment(comment.toString());
/*     */         }
/* 259 */         distObjList.add(distObjInfo);
/*     */       } 
/*     */     } 
/* 262 */     result.put("distObjs", distObjList);
/* 263 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping({"/IMap"})
/*     */   public Map<String, Object> iMap(@RequestParam String name, @RequestParam(required = false) String predicate, boolean useHzClient, boolean useLocal) {
/*     */     HazelcastInstance hazelcastInstance;
/* 273 */     if (useHzClient) { hazelcastInstance = this.hzClientUtils.getHzInstance(); }
/* 274 */     else { hazelcastInstance = HzUtils.getHzInstance(); }
/* 275 */      HashMap<String, Object> result = new HashMap<>();
/* 276 */     result.put("timestamp", this.sdFormat.format(new Date()));
/* 277 */     result.put("predicate", predicate);
/*     */     
/* 279 */     HashMap<String, String> dataMap = new HashMap<>();
/* 280 */     if (hazelcastInstance != null) {
/* 281 */       IMap<Object, Object> imap = hazelcastInstance.getMap(name);
/* 282 */       if (useLocal) {
/*     */         try {
/* 284 */           Set<Object> keySet = null;
/* 285 */           if (predicate != null && predicate.length() > 0)
/* 286 */           { keySet = imap.localKeySet((Predicate)new SqlPredicate(predicate)); }
/* 287 */           else { keySet = imap.localKeySet(); }
/*     */           
/* 289 */           for (Object key : keySet) {
/* 290 */             Object value = imap.get(key);
/* 291 */             if (value != null) {
/* 292 */               if (printdirect(value)) { dataMap.put(key.toString(), value.toString()); continue; }
/* 293 */                dataMap.put(key.toString(), ReflectionToStringBuilderHtml.toString(value)); continue;
/* 294 */             }  dataMap.put(key.toString(), "null");
/*     */           } 
/* 296 */         } catch (Exception ex) {
/* 297 */           dataMap.put("Exception", (ex.getMessage() != null) ? ex.getMessage() : ex.toString());
/*     */         } 
/*     */       } else {
/*     */         try {
/* 301 */           Set<Map.Entry<Object, Object>> entrySet = null;
/* 302 */           if (predicate != null && predicate.length() > 0)
/* 303 */           { entrySet = imap.entrySet((Predicate)new SqlPredicate(predicate)); }
/* 304 */           else { entrySet = imap.entrySet(); }
/*     */           
/* 306 */           for (Map.Entry<Object, Object> entry : entrySet) {
/* 307 */             Object key = entry.getKey();
/* 308 */             Object value = entry.getValue();
/* 309 */             if (key == null) key = "null"; 
/* 310 */             if (value != null) {
/* 311 */               if (printdirect(value)) { dataMap.put(key.toString(), value.toString()); continue; }
/* 312 */                dataMap.put(key.toString(), ReflectionToStringBuilderHtml.toString(value)); continue;
/* 313 */             }  dataMap.put(key.toString(), "null");
/*     */           } 
/* 315 */         } catch (Exception ex) {
/* 316 */           dataMap.put("Exception", (ex.getMessage() != null) ? ex.getMessage() : ex.toString());
/*     */         } 
/*     */       } 
/*     */     } else {
/* 320 */       dataMap.put("...", "hazelcastInstance=null");
/*     */     } 
/* 322 */     result.put("dataMap", dataMap);
/* 323 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping({"/IQueue"})
/*     */   public Map<String, Object> iQueue(@RequestParam String name, @RequestParam(required = false) boolean clear) {
/* 329 */     HazelcastInstance hazelcastInstance = HzUtils.getHzInstance();
/* 330 */     HashMap<String, Object> result = new HashMap<>();
/* 331 */     result.put("timestamp", this.sdFormat.format(new Date()));
/*     */     
/* 333 */     List<String> dataList = new ArrayList<>();
/* 334 */     if (hazelcastInstance != null) {
/* 335 */       IQueue<Object> iQueue = hazelcastInstance.getQueue(name);
/* 336 */       if (clear) {
/* 337 */         iQueue.clear();
/*     */       }
/*     */       
/* 340 */       Object[] queueElements = iQueue.toArray();
/* 341 */       for (Object element : queueElements) {
/* 342 */         if (printdirect(element)) { dataList.add(element.toString()); }
/* 343 */         else { dataList.add(ReflectionToStringBuilderHtml.toString(element)); }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 348 */       result.put("...", "hazelcastInstance=null");
/*     */     } 
/* 350 */     result.put("dataList", dataList);
/* 351 */     return result;
/*     */   }
/*     */   
/*     */   @RequestMapping({"/ILock"})
/*     */   public Map<String, Object> iLock(@RequestParam String name) {
/* 356 */     HazelcastInstance hazelcastInstance = HzUtils.getHzInstance();
/* 357 */     HashMap<String, Object> result = new HashMap<>();
/* 358 */     result.put("timestamp", this.sdFormat.format(new Date()));
/* 359 */     if (hazelcastInstance != null) {
/* 360 */       ILock iLock = hazelcastInstance.getLock(name);
/* 361 */       IlockInfo ilockInfo = new IlockInfo();
/* 362 */       ilockInfo.setLocked(iLock.isLocked());
/* 363 */       ilockInfo.setLockCount(iLock.getLockCount());
/* 364 */       ilockInfo.setRemainingLeaseTime(iLock.getRemainingLeaseTime());
/* 365 */       result.put("ilockInfo", ilockInfo);
/*     */     } else {
/* 367 */       result.put("...", "hazelcastInstance=null");
/*     */     } 
/* 369 */     return result;
/*     */   }
/*     */   
/*     */   @RequestMapping({"/performance"})
/*     */   public Map<String, Object> performance(@RequestParam String type, @RequestParam String name) {
/* 374 */     HazelcastInstance hazelcastInstance = HzUtils.getHzInstance();
/* 375 */     HashMap<String, Object> result = new HashMap<>();
/* 376 */     result.put("timestamp", this.sdFormat.format(new Date()));
/* 377 */     if (hazelcastInstance != null) {
/* 378 */       JsonObject perfJson = null;
/* 379 */       switch (type) {
/*     */         case "IMap":
/* 381 */           perfJson = hazelcastInstance.getMap(name).getLocalMapStats().toJson();
/*     */           break;
/*     */         case "IQueue":
/* 384 */           perfJson = hazelcastInstance.getQueue(name).getLocalQueueStats().toJson();
/*     */           break;
/*     */         case "ITopic":
/* 387 */           perfJson = hazelcastInstance.getTopic(name).getLocalTopicStats().toJson();
/*     */           break;
/*     */         case "IMultiMap":
/* 390 */           perfJson = hazelcastInstance.getMultiMap(name).getLocalMultiMapStats().toJson();
/*     */           break;
/*     */         case "IExecutorService":
/* 393 */           perfJson = hazelcastInstance.getExecutorService(name).getLocalExecutorStats().toJson();
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 398 */       if (perfJson != null) result.put("performance", perfJson.toString()); 
/*     */     } 
/* 400 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping({"/destroyRecreateDistObj"})
/*     */   public Map<String, Object> destroyRecreateDistObj(@RequestParam String type, @RequestParam String name) throws IOException {
/* 406 */     HazelcastInstance hazelcastInstance = HzUtils.getHzInstance();
/* 407 */     HashMap<String, Object> result = new HashMap<>();
/* 408 */     result.put("timestamp", this.sdFormat.format(new Date()));
/* 409 */     boolean success = false;
/* 410 */     if (hazelcastInstance != null) {
/* 411 */       MapConfig mapConfig; switch (type) {
/*     */         case "IMap":
/* 413 */           mapConfig = hazelcastInstance.getConfig().getMapConfig(name);
/* 414 */           if (mapConfig.getMapStoreConfig() != null && mapConfig
/* 415 */             .getMapStoreConfig().isEnabled()) {
/* 416 */             hazelcastInstance.getMap(name).destroy();
/*     */             try {
/* 418 */               Thread.sleep(1000L);
/* 419 */             } catch (InterruptedException interruptedException) {}
/*     */             
/* 421 */             hazelcastInstance.getMap(name);
/* 422 */             success = true; break;
/*     */           } 
/* 424 */           throw new IOException("The IMap: " + name + " is not configured with MapStore. Only IMaps configured with MapStore can be cleared!");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case "IQueue":
/* 430 */           hazelcastInstance.getQueue(name).destroy();
/*     */           try {
/* 432 */             Thread.sleep(1000L);
/* 433 */           } catch (InterruptedException interruptedException) {}
/*     */           
/* 435 */           hazelcastInstance.getQueue(name);
/* 436 */           success = true;
/*     */           break;
/*     */         case "ILock":
/* 439 */           hazelcastInstance.getLock(name).destroy();
/*     */           try {
/* 441 */             Thread.sleep(1000L);
/* 442 */           } catch (InterruptedException interruptedException) {}
/*     */           
/* 444 */           hazelcastInstance.getLock(name);
/* 445 */           success = true;
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 451 */     if (success)
/* 452 */     { result.put("result", "Destroy & recreate " + type + " : " + name + " successfully!"); }
/* 453 */     else { result.put("result", "Destroy & recreate " + type + " : " + name + " failed!"); }
/* 454 */      return result;
/*     */   }
/*     */   
/*     */   @RequestMapping({"/getHzConfig"})
/*     */   public Map<String, Object> getHzConfig() {
/* 459 */     HazelcastInstance hazelcastInstance = HzUtils.getHzInstance();
/* 460 */     HashMap<String, Object> result = new HashMap<>();
/* 461 */     result.put("timestamp", this.sdFormat.format(new Date()));
/* 462 */     if (hazelcastInstance != null) {
/* 463 */       result.put("hzConfig", this.hazelcastConfig.toString());
/* 464 */       result.put("hzSerializerConfig", this.hazelcastConfig.getSerializationConfig().toString());
/*     */     } 
/* 466 */     return result;
/*     */   }
/*     */   
/*     */   @RequestMapping({"/suggestGc"})
/*     */   public void suggestGc() {
/* 471 */     logger.info("Suggest garbage collection");
/* 472 */     System.gc();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/sendSms"}, method = {RequestMethod.POST})
/*     */   public RequestForm<SmsInfo> sendSms(@RequestBody RequestForm<SmsInfo> reqForm) {
/* 480 */     if ("get-record".equals(reqForm.getCmd())) {
/* 481 */       return reqForm;
/*     */     }
/* 483 */     SmsInfo smsData = (SmsInfo)reqForm.getRecord();
/* 484 */     String[] phoneNumbers = smsData.getToAddrs().split("\\s*,\\s*");
/*     */     try {
/* 486 */       for (int i = 0; i < phoneNumbers.length; ) { phoneNumbers[i] = phoneNumbers[i].trim(); i++; }
/*     */       
/* 488 */       Map<String, List<SmsSendResponse>> resultMap = this.smsClient.sendSms(smsData.getMessages(), phoneNumbers);
/* 489 */       StringBuilder sb = new StringBuilder();
/* 490 */       for (List<SmsSendResponse> list : resultMap.values()) {
/* 491 */         for (SmsSendResponse smsSendResponse : list) {
/* 492 */           if (sb.length() > 0) sb.append('\n'); 
/* 493 */           sb.append(smsSendResponse);
/*     */         } 
/*     */       } 
/* 496 */       smsData.setResults(sb.toString());
/* 497 */     } catch (Exception ex) {
/* 498 */       StringWriter sw = new StringWriter();
/* 499 */       PrintWriter pw = new PrintWriter(sw);
/* 500 */       ex.printStackTrace(pw);
/* 501 */       smsData.setResults(sw.toString());
/* 502 */       pw.close();
/*     */     } 
/* 504 */     reqForm.setStatus("success");
/* 505 */     return reqForm;
/*     */   }
/*     */   
/*     */   private boolean printdirect(Object obj) {
/* 509 */     if (obj.getClass().getName().startsWith("java")) return true; 
/* 510 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\hcConsole-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\web\controller\HcConsoleRestController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */