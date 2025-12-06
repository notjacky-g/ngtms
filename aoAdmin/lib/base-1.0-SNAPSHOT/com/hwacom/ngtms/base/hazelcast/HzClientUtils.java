/*     */ package com.hwacom.ngtms.base.hazelcast;
/*     */ 
/*     */ import com.hazelcast.client.HazelcastClient;
/*     */ import com.hazelcast.client.config.ClientConfig;
/*     */ import com.hazelcast.core.HazelcastInstance;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.core.IQueue;
/*     */ import com.hazelcast.core.ITopic;
/*     */ import com.hazelcast.core.IdGenerator;
/*     */ import com.hazelcast.core.LifecycleEvent;
/*     */ import com.hazelcast.core.LifecycleListener;
/*     */ import com.hazelcast.core.Member;
/*     */ import com.hazelcast.core.TransactionalMap;
/*     */ import com.hazelcast.core.TransactionalQueue;
/*     */ import com.hazelcast.map.listener.MapListener;
/*     */ import com.hazelcast.nio.serialization.PortableFactory;
/*     */ import com.hazelcast.transaction.TransactionContext;
/*     */ import com.hwacom.ngtms.base.hazelcast.serializer.HzPortableFactory;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
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
/*     */ public class HzClientUtils
/*     */ {
/*  46 */   private static Logger logger = LoggerFactory.getLogger(HzClientUtils.class);
/*  47 */   private HazelcastInstance hzInstance = null;
/*     */   private ClientConfig config;
/*     */   private boolean autoRecreate;
/*     */   private long autoRecreatePeriod;
/*  51 */   private AtomicBoolean askShutdowned = new AtomicBoolean(false);
/*  52 */   private MyLifecycleListener myLifecycleListener = new MyLifecycleListener();
/*     */   
/*     */   private String lifecycleListenerId;
/*  55 */   private Set<HzStateListener> hzStateListenerSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
/*     */   
/*  57 */   private Map<String, MapListenerData> listenerMap = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private HzClientUtils(HazelcastInstance hzInstance, ClientConfig config) {
/*  62 */     this.hzInstance = hzInstance;
/*  63 */     this.config = config;
/*  64 */     hzInstance.getLifecycleService().addLifecycleListener(new AutoRegisterListener());
/*     */   }
/*     */   
/*     */   public void addHzStateListener(HzStateListener hzStateListener) {
/*  68 */     this.hzStateListenerSet.add(hzStateListener);
/*     */   }
/*     */   
/*     */   public boolean isAutoRecreate() {
/*  72 */     return this.autoRecreate;
/*     */   }
/*     */   
/*     */   public long getAutoRecreatePeriod() {
/*  76 */     return this.autoRecreatePeriod;
/*     */   }
/*     */   
/*     */   public ClientConfig getConfig() {
/*  80 */     return this.config;
/*     */   }
/*     */   
/*     */   public void enableAutoRecreate(boolean autoRecreate, long period) {
/*  84 */     this.autoRecreate = autoRecreate;
/*  85 */     this.autoRecreatePeriod = period;
/*  86 */     if (autoRecreate) {
/*  87 */       if (this.lifecycleListenerId == null) {
/*  88 */         this
/*  89 */           .lifecycleListenerId = this.hzInstance.getLifecycleService().addLifecycleListener(this.myLifecycleListener);
/*     */       }
/*     */     }
/*  92 */     else if (this.lifecycleListenerId != null) {
/*  93 */       this.hzInstance.getLifecycleService().removeLifecycleListener(this.lifecycleListenerId);
/*  94 */       this.lifecycleListenerId = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   class MyLifecycleListener
/*     */     implements LifecycleListener {
/*     */     Thread thread;
/*     */     
/*     */     void shutdown() {
/* 103 */       if (this.thread != null) this.thread.interrupt();
/*     */     
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void stateChanged(LifecycleEvent event) {
/* 111 */       if (event.getState() == LifecycleEvent.LifecycleState.SHUTTING_DOWN && 
/* 112 */         !HzClientUtils.this.askShutdowned.get()) {
/* 113 */         this.thread = new Thread(new Runnable()
/*     */             {
/*     */               
/*     */               public void run()
/*     */               {
/* 118 */                 for (HzStateListener hzStateListener : HzClientUtils.this.hzStateListenerSet) {
/*     */                   try {
/* 120 */                     hzStateListener.hzShutdown();
/* 121 */                   } catch (Exception ex) {
/* 122 */                     HzClientUtils.logger.error("Exception occured when call hzShutdown()", ex);
/*     */                   } 
/*     */                 } 
/*     */                 while (true) {
/*     */                   try {
/* 127 */                     Thread.sleep(HzClientUtils.this.autoRecreatePeriod);
/* 128 */                   } catch (InterruptedException e) {
/* 129 */                     if (HzClientUtils.this.askShutdowned.get())
/*     */                       break; 
/*     */                   }  try {
/* 132 */                     if (HzClientUtils.this.askShutdowned.get())
/* 133 */                       break;  HzClientUtils.this.hzInstance = HazelcastClient.newHazelcastClient(HzClientUtils.this.config);
/* 134 */                     if (HzClientUtils.this.autoRecreate) {
/* 135 */                       HzClientUtils.this.lifecycleListenerId = HzClientUtils.this
/* 136 */                         .hzInstance
/* 137 */                         .getLifecycleService()
/* 138 */                         .addLifecycleListener(HzClientUtils.this.myLifecycleListener);
/*     */                     }
/* 140 */                     HzClientUtils.this.hzInstance
/* 141 */                       .getLifecycleService()
/* 142 */                       .addLifecycleListener(new HzClientUtils.AutoRegisterListener());
/* 143 */                     HzClientUtils.logger.info("Recreate Hazelcast Client successfully.");
/* 144 */                     for (HzStateListener hzStateListener : HzClientUtils.this.hzStateListenerSet) {
/*     */                       try {
/* 146 */                         hzStateListener.hzRecreate();
/* 147 */                       } catch (Exception ex) {
/* 148 */                         HzClientUtils.logger.error("Exception occured when call hzRestart()", ex);
/*     */                       } 
/*     */                     } 
/*     */                     break;
/* 152 */                   } catch (Exception ex) {
/* 153 */                     HzClientUtils.logger.warn("Failed to recreate Hazelcast Client , retry " + HzClientUtils.this
/*     */                         
/* 155 */                         .autoRecreatePeriod + " ms later", ex);
/*     */                   } 
/*     */                 } 
/*     */ 
/*     */                 
/* 160 */                 HzClientUtils.MyLifecycleListener.this.thread = null;
/*     */               }
/*     */             });
/* 163 */         this.thread.setDaemon(true);
/* 164 */         this.thread.start();
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
/*     */   public HazelcastInstance getHzInstance() {
/* 176 */     return this.hzInstance;
/*     */   }
/*     */   
/*     */   public boolean isActive() {
/* 180 */     if (this.hzInstance != null) return this.hzInstance.getLifecycleService().isRunning(); 
/* 181 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Member getCoordinator() {
/* 190 */     return getMembers().iterator().next();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Member> getMembers() {
/* 199 */     return this.hzInstance.getCluster().getMembers();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIpAddress(Member m) {
/* 209 */     if (m == null) {
/* 210 */       return null;
/*     */     }
/* 212 */     return m.getSocketAddress().getAddress().getHostAddress();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <K, V> IMap<K, V> getMap(HzDistObjEnum hzMap) {
/* 221 */     return this.hzInstance.getMap(hzMap.toHzName());
/*     */   }
/*     */   
/*     */   public <T> TransactionalMap<String, T> getMap(TransactionContext context, HzDistObjEnum hzMap) {
/* 225 */     return context.getMap(hzMap.toHzName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <E> IQueue<E> getQueue(HzDistObjEnum hzQueue) {
/* 234 */     return this.hzInstance.getQueue(hzQueue.toHzName());
/*     */   }
/*     */   
/*     */   public <E> TransactionalQueue<E> getQueue(TransactionContext context, HzDistObjEnum hzQueue) {
/* 238 */     return context.getQueue(hzQueue.toHzName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IdGenerator getIdGenerator(HzDistObjEnum hzIdGen) {
/* 248 */     return this.hzInstance.getIdGenerator(hzIdGen.toHzName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <E> ITopic<E> getTopic(HzDistObjEnum hzTopic) {
/* 257 */     return this.hzInstance.getTopic(hzTopic.toHzName());
/*     */   }
/*     */ 
/*     */   
/*     */   public String addMapEntryListener(HzDistObjEnum mapKey, MapListener listener, boolean includeValue) {
/* 262 */     String listenerId = getMap(mapKey).addEntryListener(listener, includeValue);
/* 263 */     MapListenerData listenerData = new MapListenerData(listenerId, mapKey, listener, includeValue);
/* 264 */     this.listenerMap.put(listenerId, listenerData);
/* 265 */     return listenerId;
/*     */   }
/*     */   
/*     */   public void removeMapEntryListener(String listenerId) {
/* 269 */     MapListenerData listenerData = this.listenerMap.remove(listenerId);
/* 270 */     if (listenerData != null) {
/* 271 */       getMap(listenerData.mapKey).removeEntryListener(listenerData.listenerId);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start(String groupName, String groupPwd, boolean redoOperation, int connectionTimeout, int connectionAttemptLimit, String... addresses) {
/* 282 */     this.config = new ClientConfig();
/* 283 */     this.config.getGroupConfig().setName(groupName).setPassword(groupPwd);
/* 284 */     this.config.getNetworkConfig().addAddress(addresses);
/* 285 */     this.config.getNetworkConfig().setConnectionTimeout(connectionTimeout);
/* 286 */     this.config.getNetworkConfig().setConnectionAttemptLimit(connectionAttemptLimit);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 291 */     this.config.getNetworkConfig().setRedoOperation(redoOperation);
/*     */     
/* 293 */     this.hzInstance = HazelcastClient.newHazelcastClient(this.config);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void start(ClientConfig config) {
/* 299 */     this.config = config;
/* 300 */     this.hzInstance = HazelcastClient.newHazelcastClient(config);
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
/*     */   public static HzClientUtils create(String groupName, String groupPwd, boolean redoOperation, int connectionTimeout, int connectionAttemptLimit, String... addresses) {
/* 318 */     ClientConfig config = new ClientConfig();
/* 319 */     config.getGroupConfig().setName(groupName).setPassword(groupPwd);
/* 320 */     config.getNetworkConfig().addAddress(addresses);
/* 321 */     config.getNetworkConfig().setConnectionTimeout(connectionTimeout);
/* 322 */     config.getNetworkConfig().setConnectionAttemptLimit(connectionAttemptLimit);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 327 */     config.getNetworkConfig().setRedoOperation(redoOperation);
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
/* 347 */     config.getSerializationConfig().addPortableFactory(1, (PortableFactory)new HzPortableFactory());
/*     */     
/* 349 */     HazelcastInstance hzInstance = HazelcastClient.newHazelcastClient(config);
/* 350 */     HzClientUtils hzClientUtils = new HzClientUtils(hzInstance, config);
/* 351 */     return hzClientUtils;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HzClientUtils create(ClientConfig config) {
/* 357 */     HazelcastInstance hzInstance = HazelcastClient.newHazelcastClient(config);
/* 358 */     HzClientUtils hzClientUtils = new HzClientUtils(hzInstance, config);
/* 359 */     return hzClientUtils;
/*     */   }
/*     */   
/*     */   public void shutdown() {
/* 363 */     this.askShutdowned.set(true);
/* 364 */     this.myLifecycleListener.shutdown();
/* 365 */     if (this.hzInstance != null) this.hzInstance.shutdown(); 
/*     */   }
/*     */   
/*     */   public HzClientUtils() {}
/*     */   
/*     */   private class AutoRegisterListener implements LifecycleListener {
/*     */     public void stateChanged(LifecycleEvent event) {
/* 372 */       if (HzClientUtils.this.hzInstance == null)
/* 373 */         return;  if (event.getState() == LifecycleEvent.LifecycleState.CLIENT_CONNECTED) {
/* 374 */         for (HzStateListener hzStateListener : HzClientUtils.this.hzStateListenerSet) {
/*     */           try {
/* 376 */             hzStateListener.hzClientConnected();
/* 377 */           } catch (Exception ex) {
/* 378 */             HzClientUtils.logger.error("Exception occured when call hzClientConnected()", ex);
/*     */           } 
/*     */         } 
/* 381 */         for (HzClientUtils.MapListenerData data : HzClientUtils.this.listenerMap.values()) {
/* 382 */           HzClientUtils.this.<K, V>getMap(data.mapKey).removeEntryListener(data.listenerId);
/*     */           
/* 384 */           String listenerId = HzClientUtils.this.<K, V>getMap(data.mapKey).addEntryListener(data.listener, data.includeValue);
/* 385 */           data.listenerId = listenerId;
/*     */         } 
/* 387 */       } else if (event.getState() == LifecycleEvent.LifecycleState.CLIENT_DISCONNECTED) {
/* 388 */         for (HzStateListener hzStateListener : HzClientUtils.this.hzStateListenerSet) {
/*     */           try {
/* 390 */             hzStateListener.hzClientDisconnected();
/* 391 */           } catch (Exception ex) {
/* 392 */             HzClientUtils.logger.error("Exception occured when call hzClientDisconnected()", ex);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private AutoRegisterListener() {} }
/*     */   
/*     */   private class MapListenerData {
/*     */     public String listenerId;
/*     */     public HzDistObjEnum mapKey;
/*     */     public MapListener listener;
/*     */     public boolean includeValue;
/*     */     
/*     */     public MapListenerData(String listenerId, HzDistObjEnum mapKey, MapListener listener, boolean includeValue) {
/* 407 */       this.listenerId = listenerId;
/* 408 */       this.mapKey = mapKey;
/* 409 */       this.listener = listener;
/* 410 */       this.includeValue = includeValue;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hazelcast\HzClientUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */