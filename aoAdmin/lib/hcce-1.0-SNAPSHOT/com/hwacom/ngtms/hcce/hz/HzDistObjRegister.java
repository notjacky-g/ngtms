/*     */ package com.hwacom.ngtms.hcce.hz;
/*     */ 
/*     */ import com.hazelcast.config.Config;
/*     */ import com.hazelcast.config.MapConfig;
/*     */ import com.hazelcast.config.MapIndexConfig;
/*     */ import com.hazelcast.config.MapStoreConfig;
/*     */ import com.hazelcast.config.NearCacheConfig;
/*     */ import com.hazelcast.config.QueueConfig;
/*     */ import com.hazelcast.config.QueueStoreConfig;
/*     */ import com.hazelcast.core.MapStore;
/*     */ import com.hazelcast.core.QueueStore;
/*     */ import com.hazelcast.nio.serialization.PortableFactory;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.serializer.HzPortableFactory;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import javax.annotation.Resource;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ import org.springframework.context.ApplicationEvent;
/*     */ import org.springframework.context.ApplicationListener;
/*     */ import org.springframework.context.event.ContextRefreshedEvent;
/*     */ import org.springframework.core.env.Environment;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ @Component
/*     */ public class HzDistObjRegister
/*     */   implements ApplicationListener<ContextRefreshedEvent>
/*     */ {
/* 111 */   private static Logger logger = LoggerFactory.getLogger(HzDistObjRegister.class);
/*     */   
/*     */   @Resource
/*     */   private Environment environment;
/* 115 */   private Object syncObj = new Object(); @Autowired private Config hazelcastConfig; @Autowired
/* 116 */   private ApplicationContext applicationContext; private AtomicBoolean adjustHzConfigFinished = new AtomicBoolean();
/* 117 */   private LinkedHashMap<String, HzDistObjEnum> hzDistObjPool = new LinkedHashMap<>();
/*     */   
/* 119 */   private LinkedHashMap<String, HzMapEnum> hzMapPool = new LinkedHashMap<>();
/* 120 */   private LinkedHashMap<String, HzQueueEnum> hzQueuePool = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public <U extends HzDistObjEnum> void regHzDistObj(DistObjType type, Class<U> hzDistObjEnumClass) {
/* 125 */     checkDistObjNames(type, hzDistObjEnumClass);
/* 126 */     if (type.equals(DistObjType.MAP)) {
/* 127 */       if (HzMapEnum.class.isAssignableFrom(hzDistObjEnumClass)) {
/* 128 */         regHzMap((Class)hzDistObjEnumClass);
/*     */       }
/* 130 */     } else if (type.equals(DistObjType.QUEUE) && 
/* 131 */       HzQueueEnum.class.isAssignableFrom(hzDistObjEnumClass)) {
/* 132 */       regHzQueue((Class)hzDistObjEnumClass);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private <U extends HzDistObjEnum> void checkDistObjNames(DistObjType type, Class<U> hzDistObjEnumClass) {
/* 139 */     if (!hzDistObjEnumClass.isEnum())
/* 140 */       throw new IllegalArgumentException("hzDistObjEnumClass must be enum type."); 
/* 141 */     HzDistObjEnum[] enumConstants = (HzDistObjEnum[])hzDistObjEnumClass.getEnumConstants();
/* 142 */     synchronized (this.hzDistObjPool) {
/* 143 */       for (HzDistObjEnum obj : enumConstants) {
/* 144 */         String key = type + "+" + obj.toHzName();
/* 145 */         if (this.hzDistObjPool.containsKey(key))
/* 146 */           throw new IllegalArgumentException("Duplicated Hazelcast distributed object name: " + obj
/* 147 */               .toHzName()); 
/* 148 */         this.hzDistObjPool.put(key, obj);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private <U extends HzMapEnum> void regHzMap(Class<U> hzMapEnumClass) {
/* 154 */     if (!hzMapEnumClass.isEnum()) {
/* 155 */       throw new IllegalArgumentException("hzMapEnumClass must be enum type.");
/*     */     }
/* 157 */     HzMapEnum[] enumConstants = (HzMapEnum[])hzMapEnumClass.getEnumConstants();
/* 158 */     synchronized (this.hzMapPool) {
/* 159 */       for (HzMapEnum obj : enumConstants) {
/* 160 */         String key = obj.toHzName();
/* 161 */         if (this.hzMapPool.containsKey(key))
/* 162 */           throw new IllegalArgumentException("Duplicated Hazelcast IMap object name: " + key); 
/* 163 */         this.hzMapPool.put(key, obj);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private <U extends HzQueueEnum> void regHzQueue(Class<U> hzQueueEnumClass) {
/* 169 */     if (!hzQueueEnumClass.isEnum()) {
/* 170 */       throw new IllegalArgumentException("hzQueueEnumClass must be enum type.");
/*     */     }
/* 172 */     HzQueueEnum[] enumConstants = (HzQueueEnum[])hzQueueEnumClass.getEnumConstants();
/* 173 */     synchronized (this.hzQueuePool) {
/* 174 */       for (HzQueueEnum obj : enumConstants) {
/* 175 */         String key = obj.toHzName();
/* 176 */         if (this.hzQueuePool.containsKey(key))
/* 177 */           throw new IllegalArgumentException("Duplicated Hazelcast IQueue object name: " + key); 
/* 178 */         this.hzQueuePool.put(key, obj);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onApplicationEvent(ContextRefreshedEvent event) {
/* 185 */     synchronized (this.syncObj) {
/* 186 */       if (this.adjustHzConfigFinished.get()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 193 */       for (String key : this.hzMapPool.keySet()) {
/* 194 */         HzMapEnum hzMapEnum = this.hzMapPool.get(key);
/*     */         try {
/* 196 */           setupHzMap(hzMapEnum);
/* 197 */         } catch (Exception ex) {
/*     */           
/* 199 */           Package hzMapPkg = HzMap.class.getPackage();
/* 200 */           if (hzMapPkg.equals(hzMapEnum.getClass().getPackage()) && this.environment
/* 201 */             .getProperty("ngtms.product.mode") != null && "false"
/* 202 */             .equalsIgnoreCase(this.environment.getProperty("ngtms.product.mode"))) {
/* 203 */             if (logger.isTraceEnabled()) {
/* 204 */               logger.trace("Could not find the data store bean {} required by {}.{}", new Object[] { hzMapEnum
/*     */ 
/*     */                     
/* 207 */                     .getDataStoreBeanName(), hzMapEnum
/* 208 */                     .getClass().getSimpleName(), hzMapEnum
/* 209 */                     .toString() });
/*     */             }
/*     */             continue;
/*     */           } 
/* 213 */           logger.error("Could not find the data store bean {} required by {}.{}", new Object[] { hzMapEnum
/*     */ 
/*     */                 
/* 216 */                 .getDataStoreBeanName(), hzMapEnum
/* 217 */                 .getClass().getSimpleName(), hzMapEnum
/* 218 */                 .toString() });
/*     */           
/* 220 */           throw ex;
/*     */         } 
/*     */       } 
/*     */       
/* 224 */       for (String key : this.hzQueuePool.keySet()) {
/* 225 */         HzQueueEnum hzQueueEnum = this.hzQueuePool.get(key);
/*     */         try {
/* 227 */           setupHzQueue(hzQueueEnum);
/* 228 */         } catch (Exception ex) {
/*     */           
/* 230 */           Package hzQueuePkg = HzQueue.class.getPackage();
/* 231 */           if (hzQueuePkg.equals(hzQueueEnum.getClass().getPackage()) && this.environment
/* 232 */             .getProperty("ngtms.product.mode") != null && "false"
/* 233 */             .equalsIgnoreCase(this.environment.getProperty("ngtms.product.mode"))) {
/* 234 */             if (logger.isTraceEnabled()) {
/* 235 */               logger.trace("Could not find the data store bean {} required by {}.{}", new Object[] { hzQueueEnum
/*     */ 
/*     */                     
/* 238 */                     .getDataStoreBeanName(), hzQueueEnum
/* 239 */                     .getClass().getSimpleName(), hzQueueEnum
/* 240 */                     .toString() });
/*     */             }
/*     */             continue;
/*     */           } 
/* 244 */           logger.error("Could not find the data store bean {} required by {}.{}", new Object[] { hzQueueEnum
/*     */ 
/*     */                 
/* 247 */                 .getDataStoreBeanName(), hzQueueEnum
/* 248 */                 .getClass().getSimpleName(), hzQueueEnum
/* 249 */                 .toString() });
/*     */           
/* 251 */           throw ex;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 256 */       setupHzSerializer();
/*     */       
/* 258 */       if (logger.isTraceEnabled()) logger.trace("Hazelcast Config: {}", this.hazelcastConfig.toString()); 
/* 259 */       if (logger.isTraceEnabled())
/* 260 */         logger.trace("Serialization Config: {}", this.hazelcastConfig
/* 261 */             .getSerializationConfig().toString()); 
/* 262 */       this.adjustHzConfigFinished.set(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isProcessHzConfigFinished() {
/* 267 */     return this.adjustHzConfigFinished.get();
/*     */   }
/*     */   
/*     */   private void setupHzSerializer() {
/*     */     try {
/* 272 */       this.hazelcastConfig.getSerializationConfig().addPortableFactory(1, (PortableFactory)new HzPortableFactory());
/* 273 */     } catch (Exception ex) {
/* 274 */       throw new RuntimeException("Failed to setup serializer", ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setupHzMap(HzMapEnum hzMapEnum) {
/* 279 */     MapConfig mapConfig = this.hazelcastConfig.getMapConfig(hzMapEnum.toHzName());
/* 280 */     String dataStoreBeanName = hzMapEnum.getDataStoreBeanName();
/* 281 */     if (hzMapEnum.getSyncBackCount() != null) {
/* 282 */       mapConfig.setBackupCount(hzMapEnum.getSyncBackCount().intValue());
/*     */     }
/* 284 */     if (hzMapEnum.getAsyncBackCount() != null) {
/* 285 */       mapConfig.setAsyncBackupCount(hzMapEnum.getAsyncBackCount().intValue());
/*     */     }
/* 287 */     if (hzMapEnum.isReadBackupData() != null) {
/* 288 */       mapConfig.setReadBackupData(hzMapEnum.isReadBackupData().booleanValue());
/*     */     }
/* 290 */     if (hzMapEnum.getNearCacheMaxSize() != null) {
/* 291 */       NearCacheConfig nearCacheConfig = mapConfig.getNearCacheConfig();
/* 292 */       if (nearCacheConfig == null) nearCacheConfig = new NearCacheConfig(); 
/* 293 */       nearCacheConfig.setMaxSize(hzMapEnum.getNearCacheMaxSize().intValue());
/* 294 */       mapConfig.setNearCacheConfig(nearCacheConfig);
/*     */     } 
/* 296 */     if (dataStoreBeanName != null && dataStoreBeanName.length() > 0) {
/* 297 */       MapStore<?, ?> mapStore = null;
/*     */ 
/*     */       
/* 300 */       mapStore = (MapStore<?, ?>)this.applicationContext.getBean(dataStoreBeanName, MapStore.class);
/*     */       
/* 302 */       if (logger.isTraceEnabled()) {
/* 303 */         logger.trace("Set the bean {}:{} as the map store object of {}.{}", new Object[] { mapStore
/*     */ 
/*     */               
/* 306 */               .getClass().getName(), hzMapEnum
/* 307 */               .getDataStoreBeanName(), hzMapEnum
/* 308 */               .getClass().getSimpleName(), hzMapEnum
/* 309 */               .toString() });
/*     */       }
/* 311 */       MapStoreConfig mapStoreConfig = mapConfig.getMapStoreConfig();
/* 312 */       if (mapStoreConfig == null) mapStoreConfig = new MapStoreConfig(); 
/* 313 */       mapStoreConfig.setEnabled(true).setImplementation(mapStore);
/* 314 */       mapConfig.setMapStoreConfig(mapStoreConfig);
/*     */     } 
/* 316 */     HzMapIndex[] hzMapIndexes = hzMapEnum.getHzMapIndexes();
/* 317 */     if (hzMapIndexes != null && hzMapIndexes.length > 0) {
/* 318 */       List<MapIndexConfig> mapIndexConfigList = mapConfig.getMapIndexConfigs();
/* 319 */       for (HzMapIndex hzMapIndex : hzMapIndexes) {
/* 320 */         MapIndexConfig mapIndexConfig = new MapIndexConfig();
/* 321 */         mapIndexConfig.setAttribute(hzMapIndex.getAttribute());
/* 322 */         mapIndexConfig.setOrdered(hzMapIndex.isOrdered());
/* 323 */         mapIndexConfigList.add(mapIndexConfig);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setupHzQueue(HzQueueEnum hzQueueEnum) {
/* 329 */     QueueConfig queueConfig = this.hazelcastConfig.getQueueConfig(hzQueueEnum.toHzName());
/* 330 */     String dataStoreBeanName = hzQueueEnum.getDataStoreBeanName();
/* 331 */     if (hzQueueEnum.getSyncBackCount() != null) {
/* 332 */       queueConfig.setBackupCount(hzQueueEnum.getSyncBackCount().intValue());
/*     */     }
/* 334 */     if (hzQueueEnum.getAsyncBackCount() != null) {
/* 335 */       queueConfig.setAsyncBackupCount(hzQueueEnum.getAsyncBackCount().intValue());
/*     */     }
/*     */     
/* 338 */     if (dataStoreBeanName != null && dataStoreBeanName.length() > 0) {
/* 339 */       QueueStore<?> queueStore = null;
/*     */ 
/*     */       
/* 342 */       queueStore = (QueueStore)this.applicationContext.getBean(dataStoreBeanName, QueueStore.class);
/*     */       
/* 344 */       if (logger.isTraceEnabled()) {
/* 345 */         logger.trace("Set the bean {}:{} as the queue store object of {}.{}", new Object[] { queueStore
/*     */ 
/*     */               
/* 348 */               .getClass().getName(), hzQueueEnum
/* 349 */               .getDataStoreBeanName(), hzQueueEnum
/* 350 */               .getClass().getSimpleName(), hzQueueEnum
/* 351 */               .toString() });
/*     */       }
/* 353 */       QueueStoreConfig queueStoreConfig = queueConfig.getQueueStoreConfig();
/* 354 */       if (queueStoreConfig == null) queueStoreConfig = new QueueStoreConfig(); 
/* 355 */       queueStoreConfig.setEnabled(true).setStoreImplementation(queueStore);
/* 356 */       queueConfig.setQueueStoreConfig(queueStoreConfig);
/*     */     } 
/*     */   }
/*     */   
/*     */   public HzDistObjEnum getDistObjEnum(String name) {
/* 361 */     return this.hzDistObjPool.get(name);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\hz\HzDistObjRegister.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */