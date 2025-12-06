/*     */ package com.hwacom.ngtms.base.hazelcast;
/*     */ 
/*     */ import com.hazelcast.client.config.ClientConfig;
/*     */ import com.hazelcast.core.HazelcastInstance;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.core.IQueue;
/*     */ import com.hazelcast.core.ITopic;
/*     */ import com.hazelcast.core.LifecycleService;
/*     */ import com.hazelcast.map.listener.MapListener;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.annotation.PreDestroy;
/*     */ import javax.annotation.Resource;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.core.env.Environment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HazelcastClient
/*     */ {
/*  24 */   private static Logger logger = LoggerFactory.getLogger(HazelcastClient.class);
/*     */   
/*  26 */   private AtomicBoolean hasShutdown = new AtomicBoolean();
/*     */   private HzClientUtils hzClientUtils;
/*     */   private HzClientUtils drHzClientUtils;
/*     */   @Resource
/*     */   private Environment environment;
/*     */   
/*     */   @PostConstruct
/*     */   public void initHzClient()
/*     */   {
/*  35 */     Thread thread1 = new Thread(new Runnable()
/*     */     {
/*     */ 
/*     */       public void run()
/*     */       {
/*  40 */         long reCreatePeriod = ((Integer)HazelcastClient.this.environment.getProperty("hz.recreatePeriod", Integer.class)).intValue();
/*  41 */         while (!HazelcastClient.this.hasShutdown.get()) {
/*     */           try {
/*  43 */             HazelcastClient.this.hzClientUtils = 
/*  44 */               HzClientUtils.create(
/*  45 */               HazelcastClient.this.environment.getProperty("hz.group.name"), 
/*  46 */               HazelcastClient.this.environment.getProperty("hz.group.password"), 
/*  47 */               ((Boolean)HazelcastClient.this.environment.getProperty("hz.redoOperation", Boolean.class)).booleanValue(), 
/*  48 */               ((Integer)HazelcastClient.this.environment.getProperty("hz.connection.timeout", Integer.class)).intValue(), 
/*  49 */               ((Integer)HazelcastClient.this.environment.getProperty("hz.connection.attempt.limit", Integer.class)).intValue(), 
/*  50 */               (String[])HazelcastClient.this.environment.getProperty("hz.members", String[].class));
/*  51 */             HazelcastClient.logger.info("Connect to HzGroup: " + 
/*     */             
/*  53 */               HazelcastClient.this.environment.getProperty("hz.group.name") + " successfully");
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/*  57 */             HazelcastClient.logger.warn("Failed to connect HzGroup: " + 
/*     */             
/*  59 */               HazelcastClient.this.environment.getProperty("hz.group.name") + ", reconnect after " + reCreatePeriod + " ms.", ex);
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */             try
/*     */             {
/*  66 */               Thread.sleep(reCreatePeriod);
/*     */             } catch (InterruptedException localInterruptedException) {}
/*     */           }
/*     */         }
/*  70 */         HazelcastClient.this.hzClientUtils.enableAutoRecreate(
/*  71 */           ((Boolean)HazelcastClient.this.environment.getProperty("hz.autoRecreate", Boolean.class)).booleanValue(), reCreatePeriod);
/*     */       }
/*  73 */     });
/*  74 */     thread1.setDaemon(true);
/*  75 */     thread1.start();
/*     */     
/*  77 */     if (((Boolean)this.environment.getProperty("dr.hz.enable", Boolean.class, Boolean.valueOf(false))).booleanValue()) {
/*  78 */       Thread thread2 = new Thread(new Runnable()
/*     */       {
/*     */ 
/*     */         public void run()
/*     */         {
/*     */ 
/*  84 */           long reCreatePeriod = ((Integer)HazelcastClient.this.environment.getProperty("dr.hz.recreatePeriod", Integer.class)).intValue();
/*  85 */           while (!HazelcastClient.this.hasShutdown.get()) {
/*     */             try {
/*  87 */               HazelcastClient.this.drHzClientUtils = 
/*  88 */                 HzClientUtils.create(
/*  89 */                 HazelcastClient.this.environment.getProperty("dr.hz.group.name"), 
/*  90 */                 HazelcastClient.this.environment.getProperty("dr.hz.group.password"), 
/*  91 */                 ((Boolean)HazelcastClient.this.environment.getProperty("dr.hz.redoOperation", Boolean.class)).booleanValue(), 
/*  92 */                 ((Integer)HazelcastClient.this.environment.getProperty("dr.hz.connection.timeout", Integer.class)).intValue(), 
/*  93 */                 ((Integer)HazelcastClient.this.environment.getProperty("dr.hz.connection.attempt.limit", Integer.class)).intValue(), 
/*     */                 
/*  95 */                 (String[])HazelcastClient.this.environment.getProperty("dr.hz.members", String[].class));
/*  96 */               HazelcastClient.logger.info("Connect to dr HzGroup: " + 
/*     */               
/*  98 */                 HazelcastClient.this.environment.getProperty("dr.hz.group.name") + " successfully");
/*     */             }
/*     */             catch (Exception ex)
/*     */             {
/* 102 */               HazelcastClient.logger.warn("Failed to connect dr HzGroup: " + 
/*     */               
/* 104 */                 HazelcastClient.this.environment.getProperty("dr.hz.group.name") + ", reconnect after " + reCreatePeriod + " ms.", ex);
/*     */               
/*     */ 
/*     */ 
/*     */ 
/*     */               try
/*     */               {
/* 111 */                 Thread.sleep(reCreatePeriod);
/*     */               } catch (InterruptedException localInterruptedException) {}
/*     */             }
/*     */           }
/* 115 */           HazelcastClient.this.drHzClientUtils.enableAutoRecreate(
/* 116 */             ((Boolean)HazelcastClient.this.environment.getProperty("dr.hz.autoRecreate", Boolean.class)).booleanValue(), reCreatePeriod);
/*     */         }
/* 118 */       });
/* 119 */       thread2.setDaemon(true);
/* 120 */       thread2.start();
/*     */     }
/*     */   }
/*     */   
/*     */   public ClientConfig getClientConfig() {
/* 125 */     return this.hzClientUtils.getConfig();
/*     */   }
/*     */   
/*     */   public ClientConfig getDrClientConfig() {
/* 129 */     return this.drHzClientUtils.getConfig();
/*     */   }
/*     */   
/*     */   public void addHzStateListener(HzStateListener hzStateListener) {
/* 133 */     this.hzClientUtils.addHzStateListener(hzStateListener);
/*     */   }
/*     */   
/*     */   public void addDrHzStateListener(HzStateListener hzStateListener) {
/* 137 */     this.drHzClientUtils.addHzStateListener(hzStateListener);
/*     */   }
/*     */   
/*     */   public void addHzMapListener(HzDistObjEnum mapKey, MapListener listener, boolean includeValue) {
/* 141 */     this.hzClientUtils.addMapEntryListener(mapKey, listener, includeValue);
/*     */   }
/*     */   
/*     */   public void addDrMapListener(HzDistObjEnum mapKey, MapListener listener, boolean includeValue) {
/* 145 */     this.drHzClientUtils.addMapEntryListener(mapKey, listener, includeValue);
/*     */   }
/*     */   
/*     */   public <K, V> IMap<K, V> getIMap(HzDistObjEnum mapKey) {
/* 149 */     return this.hzClientUtils.getMap(mapKey);
/*     */   }
/*     */   
/*     */   public <T> IQueue<T> getIQueue(HzDistObjEnum queue) {
/* 153 */     return this.hzClientUtils.getQueue(queue);
/*     */   }
/*     */   
/*     */   public <T> ITopic<T> getITopic(HzDistObjEnum topic) {
/* 157 */     return this.hzClientUtils.getTopic(topic);
/*     */   }
/*     */   
/*     */   public <K, V> IMap<K, V> getDrIMap(HzDistObjEnum mapKey) {
/* 161 */     return this.drHzClientUtils.getMap(mapKey);
/*     */   }
/*     */   
/*     */   public <T> IQueue<T> getDrIQueue(HzDistObjEnum queue) {
/* 165 */     return this.drHzClientUtils.getQueue(queue);
/*     */   }
/*     */   
/*     */   public <T> ITopic<T> getDrITopic(HzDistObjEnum topic) {
/* 169 */     return this.drHzClientUtils.getTopic(topic);
/*     */   }
/*     */   
/*     */   public boolean isHzRunning() {
/* 173 */     if (this.hzClientUtils == null) return false;
/* 174 */     HazelcastInstance hazelcastInstance = this.hzClientUtils.getHzInstance();
/* 175 */     if (hazelcastInstance == null) {
/* 176 */       return false;
/*     */     }
/* 178 */     return hazelcastInstance.getLifecycleService().isRunning();
/*     */   }
/*     */   
/*     */   public boolean isDrHzRunning()
/*     */   {
/* 183 */     if (this.drHzClientUtils == null) return false;
/* 184 */     HazelcastInstance hazelcastInstance = this.drHzClientUtils.getHzInstance();
/* 185 */     if (hazelcastInstance == null) {
/* 186 */       return false;
/*     */     }
/* 188 */     return hazelcastInstance.getLifecycleService().isRunning();
/*     */   }
/*     */   
/*     */   public HazelcastInstance getHzInstance()
/*     */   {
/* 193 */     return this.hzClientUtils.getHzInstance();
/*     */   }
/*     */   
/*     */   public HazelcastInstance getDrHzInstance() {
/* 197 */     return this.drHzClientUtils.getHzInstance();
/*     */   }
/*     */   
/*     */   @PreDestroy
/*     */   public void shutdown() {
/* 202 */     this.hasShutdown.set(true);
/* 203 */     if (this.hzClientUtils != null) this.hzClientUtils.shutdown();
/* 204 */     if (this.drHzClientUtils != null) this.drHzClientUtils.shutdown();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hazelcast\HazelcastClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */