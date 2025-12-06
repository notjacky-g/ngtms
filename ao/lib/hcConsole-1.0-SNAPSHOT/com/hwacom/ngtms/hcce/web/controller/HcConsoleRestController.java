/*     */ package com.hwacom.ngtms.hcce.web.controller;
/*     */ 
/*     */ import com.hazelcast.config.Config;
/*     */ import com.hazelcast.config.GroupConfig;
/*     */ import com.hazelcast.config.MapConfig;
/*     */ import com.hazelcast.config.MapStoreConfig;
/*     */ import com.hazelcast.config.SerializationConfig;
/*     */ import com.hazelcast.core.Client;
/*     */ import com.hazelcast.core.Cluster;
/*     */ import com.hazelcast.core.DistributedObject;
/*     */ import com.hazelcast.core.HazelcastInstance;
/*     */ import com.hazelcast.core.IExecutorService;
/*     */ import com.hazelcast.core.ILock;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.core.IQueue;
/*     */ import com.hazelcast.core.ITopic;
/*     */ import com.hazelcast.core.Member;
/*     */ import com.hazelcast.core.MultiMap;
/*     */ import com.hazelcast.internal.json.JsonObject;
/*     */ import com.hazelcast.monitor.LocalExecutorStats;
/*     */ import com.hazelcast.monitor.LocalMultiMapStats;
/*     */ import com.hazelcast.monitor.LocalQueueStats;
/*     */ import com.hazelcast.monitor.LocalTopicStats;
/*     */ import com.hazelcast.query.SqlPredicate;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzClientUtils;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.sms.SmsClient;
/*     */ import com.hwacom.ngtms.base.sms.shared.SmsSendResponse;
/*     */ import com.hwacom.ngtms.hcce.core.NodeManager;
/*     */ import com.hwacom.ngtms.hcce.hz.DistObjType;
/*     */ import com.hwacom.ngtms.hcce.hz.HzDistObjRegister;
/*     */ import com.hwacom.ngtms.hcce.web.service.ReflectionToStringBuilderHtml;
/*     */ import com.hwacom.ngtms.hcce.web.vo.ClientInfo;
/*     */ import com.hwacom.ngtms.hcce.web.vo.DistObjInfo;
/*     */ import com.hwacom.ngtms.hcce.web.vo.IlockInfo;
/*     */ import com.hwacom.ngtms.hcce.web.vo.MemberInfo;
/*     */ import com.hwacom.ngtms.hcce.web.vo.RequestForm;
/*     */ import com.hwacom.ngtms.hcce.web.vo.SmsInfo;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.annotation.PreDestroy;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ 
/*     */ @org.springframework.web.bind.annotation.RestController
/*     */ @RequestMapping({"/hcConsole"})
/*     */ public class HcConsoleRestController
/*     */ {
/*  70 */   private static Logger logger = LoggerFactory.getLogger(HcConsoleRestController.class);
/*  71 */   private SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
/*     */   @Autowired
/*     */   private com.hwacom.ngtms.hcce.shared.HcceEnv hcceEnv;
/*     */   @Autowired
/*     */   private Config hazelcastConfig; @Autowired
/*     */   private javax.servlet.ServletContext servletContext; @Autowired
/*     */   private NodeManager nodeManager; @Autowired
/*     */   private HzDistObjRegister hzDistObjRegister; @Autowired
/*  79 */   private SmsClient smsClient; private com.hwacom.ngtms.hcce.web.vo.HcNodeInfo hcNodeInfo; private AtomicBoolean running = new AtomicBoolean(true);
/*     */   private Thread hzClientStartThread;
/*     */   private HzClientUtils hzClientUtils;
/*     */   
/*     */   @PostConstruct
/*     */   public void init() {
/*  85 */     this.hzClientStartThread = new Thread(new Runnable()
/*     */     {
/*     */ 
/*     */       public void run()
/*     */       {
/*  90 */         while (HcConsoleRestController.this.running.get()) {
/*     */           try {
/*  92 */             TimeUnit.SECONDS.sleep(5L);
/*  93 */             HazelcastInstance hazelcastInstance = HzUtils.getHzInstance();
/*  94 */             if (hazelcastInstance != null) {
/*     */               try {
/*  96 */                 Member localMember = hazelcastInstance.getCluster().getLocalMember();
/*  97 */                 HcConsoleRestController.this.hzClientUtils = 
/*  98 */                   HzClientUtils.create(hazelcastInstance
/*     */                   
/* 100 */                   .getConfig()
/* 101 */                   .getGroupConfig()
/* 102 */                   .getName(), hazelcastInstance
/*     */                   
/* 104 */                   .getConfig()
/* 105 */                   .getGroupConfig()
/* 106 */                   .getPassword(), false, 2000, 2, new String[] {localMember
/*     */                   
/*     */ 
/*     */ 
/* 110 */                   .getSocketAddress().getAddress().getHostAddress() + ":" + localMember
/*     */                   
/* 112 */                   .getSocketAddress().getPort() });
/* 113 */                 HcConsoleRestController.logger.debug("Create Hz Client successfully!");
/*     */               } catch (Exception ex) {
/* 115 */                 HcConsoleRestController.logger.error("Failed to create Hz Client", ex);
/*     */               }
/* 117 */               break;
/*     */             }
/*     */             
/*     */           }
/*     */           catch (InterruptedException localInterruptedException) {}
/*     */         }
/*     */       }
/* 124 */     });
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
/*     */   @RequestMapping(value={"/hzMemberData"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public Map<String, Object> hzMemberData()
/*     */   {
/* 143 */     HazelcastInstance hazelcastInstance = HzUtils.getHzInstance();
/* 144 */     HashMap<String, Object> result = new HashMap();
/* 145 */     result.put("timestamp", this.sdFormat.format(new Date()));
/* 146 */     List<ClientInfo> clientList = new ArrayList();
/* 147 */     List<MemberInfo> memberList = new ArrayList();
/* 148 */     Client client; boolean first; if (hazelcastInstance != null) {
/* 149 */       Collection<Client> clients = hazelcastInstance.getClientService().getConnectedClients();
/* 150 */       for (Iterator localIterator = clients.iterator(); localIterator.hasNext();) { client = (Client)localIterator.next();
/* 151 */         ClientInfo clientInfo = new ClientInfo();
/* 152 */         clientInfo.setAddress(client.getSocketAddress().toString());
/* 153 */         clientInfo.setClient(client.toString());
/* 154 */         clientList.add(clientInfo);
/*     */       }
/*     */       
/* 157 */       first = true;
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
/*     */   @RequestMapping(value={"/hzDistObjData"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public Map<String, Object> hzDistObjData()
/*     */   {
/* 181 */     HazelcastInstance hazelcastInstance = HzUtils.getHzInstance();
/* 182 */     HashMap<String, Object> result = new HashMap();
/* 183 */     result.put("timestamp", this.sdFormat.format(new Date()));
/* 184 */     List<DistObjInfo> distObjList = new ArrayList();
/* 185 */     StringBuilder comment; if (hazelcastInstance != null) {
/* 186 */       Collection<DistributedObject> distObjs = hazelcastInstance.getDistributedObjects();
/* 187 */       comment = new StringBuilder();
/* 188 */       for (DistributedObject distObj : distObjs) {
/* 189 */         DistObjInfo distObjInfo = new DistObjInfo();
/* 190 */         distObjInfo.setName(distObj.getName());
/* 191 */         comment.setLength(0);
/* 192 */         if ((distObj instanceof IMap)) {
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
/* 203 */           if ((mapConfig.getMapStoreConfig() != null) && 
/* 204 */             (mapConfig.getMapStoreConfig().isEnabled())) {
/* 205 */             distObjInfo.setDestroy(true);
/* 206 */             Object impl = mapConfig.getMapStoreConfig().getImplementation();
/* 207 */             if (impl != null) {
/* 208 */               if (comment.length() > 0) comment.append(", ");
/* 209 */               comment.append("MapStore: " + impl.getClass().getSimpleName());
/*     */             }
/*     */           }
/* 212 */           distObjInfo.setQuery(true);
/* 213 */           distObjInfo.setPerformance(true);
/* 214 */         } else if ((distObj instanceof IQueue)) {
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
/* 225 */         } else if ((distObj instanceof ITopic)) {
/* 226 */           distObjInfo.setType("ITopic");
/* 227 */           distObjInfo.setQuery(true);
/* 228 */           distObjInfo.setPerformance(true);
/*     */           
/* 230 */           HzDistObjEnum hzDistObjEnum = this.hzDistObjRegister.getDistObjEnum(DistObjType.TOPIC + "+" + distObj.getName());
/* 231 */           if (hzDistObjEnum != null) {
/* 232 */             comment.append(hzDistObjEnum.getDescription());
/*     */           }
/* 234 */         } else if ((distObj instanceof ILock)) {
/* 235 */           distObjInfo.setType("ILock");
/* 236 */           distObjInfo.setDestroy(true);
/* 237 */           distObjInfo.setQuery(true);
/*     */           
/* 239 */           HzDistObjEnum hzDistObjEnum = this.hzDistObjRegister.getDistObjEnum(DistObjType.LOCK + "+" + distObj.getName());
/* 240 */           if (hzDistObjEnum != null) {
/* 241 */             comment.append(hzDistObjEnum.getDescription());
/*     */           }
/* 243 */         } else if ((distObj instanceof MultiMap)) {
/* 244 */           distObjInfo.setType("MultiMap");
/* 245 */           distObjInfo.setPerformance(true);
/*     */           
/* 247 */           HzDistObjEnum hzDistObjEnum = this.hzDistObjRegister.getDistObjEnum(DistObjType.MULTI_MAP + "+" + distObj.getName());
/* 248 */           if (hzDistObjEnum != null) {
/* 249 */             comment.append(hzDistObjEnum.getDescription());
/*     */           }
/* 251 */         } else if ((distObj instanceof IExecutorService)) {
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
/*     */   @RequestMapping({"/IMap"})
/*     */   public Map<String, Object> iMap(@RequestParam String name, @RequestParam(required=false) String predicate, boolean useHzClient, boolean useLocal)
/*     */   {
/*     */     HazelcastInstance hazelcastInstance;
/*     */     
/*     */     HazelcastInstance hazelcastInstance;
/* 273 */     if (useHzClient) hazelcastInstance = this.hzClientUtils.getHzInstance(); else
/* 274 */       hazelcastInstance = HzUtils.getHzInstance();
/* 275 */     HashMap<String, Object> result = new HashMap();
/* 276 */     result.put("timestamp", this.sdFormat.format(new Date()));
/* 277 */     result.put("predicate", predicate);
/*     */     
/* 279 */     HashMap<String, String> dataMap = new HashMap();
/* 280 */     if (hazelcastInstance != null) {
/* 281 */       IMap<Object, Object> imap = hazelcastInstance.getMap(name);
/* 282 */       if (useLocal) {
/*     */         try {
/* 284 */           Set<Object> keySet = null;
/* 285 */           if ((predicate != null) && (predicate.length() > 0))
/* 286 */             keySet = imap.localKeySet(new SqlPredicate(predicate)); else {
/* 287 */             keySet = imap.localKeySet();
/*     */           }
/* 289 */           for (Object key : keySet) {
/* 290 */             Object value = imap.get(key);
/* 291 */             if (value != null) {
/* 292 */               if (printdirect(value)) dataMap.put(key.toString(), value.toString()); else
/* 293 */                 dataMap.put(key.toString(), ReflectionToStringBuilderHtml.toString(value));
/* 294 */             } else dataMap.put(key.toString(), "null");
/*     */           }
/*     */         } catch (Exception ex) {
/* 297 */           dataMap.put("Exception", ex.getMessage() != null ? ex.getMessage() : ex.toString());
/*     */         }
/*     */       } else {
/*     */         try {
/* 301 */           Set<Map.Entry<Object, Object>> entrySet = null;
/* 302 */           if ((predicate != null) && (predicate.length() > 0))
/* 303 */             entrySet = imap.entrySet(new SqlPredicate(predicate)); else {
/* 304 */             entrySet = imap.entrySet();
/*     */           }
/* 306 */           for (Map.Entry<Object, Object> entry : entrySet) {
/* 307 */             Object key = entry.getKey();
/* 308 */             Object value = entry.getValue();
/* 309 */             if (key == null) key = "null";
/* 310 */             if (value != null) {
/* 311 */               if (printdirect(value)) dataMap.put(key.toString(), value.toString()); else
/* 312 */                 dataMap.put(key.toString(), ReflectionToStringBuilderHtml.toString(value));
/* 313 */             } else dataMap.put(key.toString(), "null");
/*     */           }
/*     */         } catch (Exception ex) {
/* 316 */           dataMap.put("Exception", ex.getMessage() != null ? ex.getMessage() : ex.toString());
/*     */         }
/*     */       }
/*     */     } else {
/* 320 */       dataMap.put("...", "hazelcastInstance=null");
/*     */     }
/* 322 */     result.put("dataMap", dataMap);
/* 323 */     return result;
/*     */   }
/*     */   
/*     */   @RequestMapping({"/IQueue"})
/*     */   public Map<String, Object> iQueue(@RequestParam String name, @RequestParam(required=false) boolean clear)
/*     */   {
/* 329 */     HazelcastInstance hazelcastInstance = HzUtils.getHzInstance();
/* 330 */     HashMap<String, Object> result = new HashMap();
/* 331 */     result.put("timestamp", this.sdFormat.format(new Date()));
/*     */     
/* 333 */     List<String> dataList = new ArrayList();
/* 334 */     if (hazelcastInstance != null) {
/* 335 */       IQueue<Object> iQueue = hazelcastInstance.getQueue(name);
/* 336 */       if (clear) {
/* 337 */         iQueue.clear();
/*     */       }
/*     */       
/* 340 */       Object[] queueElements = iQueue.toArray();
/* 341 */       for (Object element : queueElements) {
/* 342 */         if (printdirect(element)) dataList.add(element.toString()); else {
/* 343 */           dataList.add(ReflectionToStringBuilderHtml.toString(element));
/*     */         }
/*     */       }
/*     */     }
/*     */     else {
/* 348 */       result.put("...", "hazelcastInstance=null");
/*     */     }
/* 350 */     result.put("dataList", dataList);
/* 351 */     return result;
/*     */   }
/*     */   
/*     */   @RequestMapping({"/ILock"})
/*     */   public Map<String, Object> iLock(@RequestParam String name) {
/* 356 */     HazelcastInstance hazelcastInstance = HzUtils.getHzInstance();
/* 357 */     HashMap<String, Object> result = new HashMap();
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
/* 375 */     HashMap<String, Object> result = new HashMap();
/* 376 */     result.put("timestamp", this.sdFormat.format(new Date()));
/* 377 */     if (hazelcastInstance != null) {
/* 378 */       JsonObject perfJson = null;
/* 379 */       switch (type) {
/*     */       case "IMap": 
/* 381 */         perfJson = hazelcastInstance.getMap(name).getLocalMapStats().toJson();
/* 382 */         break;
/*     */       case "IQueue": 
/* 384 */         perfJson = hazelcastInstance.getQueue(name).getLocalQueueStats().toJson();
/* 385 */         break;
/*     */       case "ITopic": 
/* 387 */         perfJson = hazelcastInstance.getTopic(name).getLocalTopicStats().toJson();
/* 388 */         break;
/*     */       case "IMultiMap": 
/* 390 */         perfJson = hazelcastInstance.getMultiMap(name).getLocalMultiMapStats().toJson();
/* 391 */         break;
/*     */       case "IExecutorService": 
/* 393 */         perfJson = hazelcastInstance.getExecutorService(name).getLocalExecutorStats().toJson();
/* 394 */         break;
/*     */       }
/*     */       
/*     */       
/* 398 */       if (perfJson != null) result.put("performance", perfJson.toString());
/*     */     }
/* 400 */     return result;
/*     */   }
/*     */   
/*     */   @RequestMapping({"/destroyRecreateDistObj"})
/*     */   public Map<String, Object> destroyRecreateDistObj(@RequestParam String type, @RequestParam String name) throws IOException
/*     */   {
/* 406 */     HazelcastInstance hazelcastInstance = HzUtils.getHzInstance();
/* 407 */     HashMap<String, Object> result = new HashMap();
/* 408 */     result.put("timestamp", this.sdFormat.format(new Date()));
/* 409 */     boolean success = false;
/* 410 */     if (hazelcastInstance != null) {
/* 411 */       switch (type) {
/*     */       case "IMap": 
/* 413 */         MapConfig mapConfig = hazelcastInstance.getConfig().getMapConfig(name);
/* 414 */         if ((mapConfig.getMapStoreConfig() != null) && 
/* 415 */           (mapConfig.getMapStoreConfig().isEnabled())) {
/* 416 */           hazelcastInstance.getMap(name).destroy();
/*     */           try {
/* 418 */             Thread.sleep(1000L);
/*     */           }
/*     */           catch (InterruptedException localInterruptedException) {}
/* 421 */           hazelcastInstance.getMap(name);
/* 422 */           success = true;
/*     */         } else {
/* 424 */           throw new IOException("The IMap: " + name + " is not configured with MapStore. Only IMaps configured with MapStore can be cleared!");
/*     */         }
/*     */         
/*     */ 
/*     */         break;
/*     */       case "IQueue": 
/* 430 */         hazelcastInstance.getQueue(name).destroy();
/*     */         try {
/* 432 */           Thread.sleep(1000L);
/*     */         }
/*     */         catch (InterruptedException localInterruptedException1) {}
/* 435 */         hazelcastInstance.getQueue(name);
/* 436 */         success = true;
/* 437 */         break;
/*     */       case "ILock": 
/* 439 */         hazelcastInstance.getLock(name).destroy();
/*     */         try {
/* 441 */           Thread.sleep(1000L);
/*     */         }
/*     */         catch (InterruptedException localInterruptedException2) {}
/* 444 */         hazelcastInstance.getLock(name);
/* 445 */         success = true;
/* 446 */         break;
/*     */       }
/*     */       
/*     */     }
/*     */     
/* 451 */     if (success)
/* 452 */       result.put("result", "Destroy & recreate " + type + " : " + name + " successfully!"); else
/* 453 */       result.put("result", "Destroy & recreate " + type + " : " + name + " failed!");
/* 454 */     return result;
/*     */   }
/*     */   
/*     */   @RequestMapping({"/getHzConfig"})
/*     */   public Map<String, Object> getHzConfig() {
/* 459 */     HazelcastInstance hazelcastInstance = HzUtils.getHzInstance();
/* 460 */     HashMap<String, Object> result = new HashMap();
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
/*     */   @RequestMapping(value={"/sendSms"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public RequestForm<SmsInfo> sendSms(@RequestBody RequestForm<SmsInfo> reqForm)
/*     */   {
/* 480 */     if ("get-record".equals(reqForm.getCmd())) {
/* 481 */       return reqForm;
/*     */     }
/* 483 */     SmsInfo smsData = (SmsInfo)reqForm.getRecord();
/* 484 */     String[] phoneNumbers = smsData.getToAddrs().split("\\s*,\\s*");
/*     */     try {
/* 486 */       for (int i = 0; i < phoneNumbers.length; i++) { phoneNumbers[i] = phoneNumbers[i].trim();
/*     */       }
/* 488 */       Map<String, List<SmsSendResponse>> resultMap = this.smsClient.sendSms(smsData.getMessages(), phoneNumbers);
/* 489 */       StringBuilder sb = new StringBuilder();
/* 490 */       for (List<SmsSendResponse> list : resultMap.values()) {
/* 491 */         for (SmsSendResponse smsSendResponse : list) {
/* 492 */           if (sb.length() > 0) sb.append('\n');
/* 493 */           sb.append(smsSendResponse);
/*     */         }
/*     */       }
/* 496 */       smsData.setResults(sb.toString());
/*     */     } catch (Exception ex) {
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


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcConsole-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\web\controller\HcConsoleRestController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */