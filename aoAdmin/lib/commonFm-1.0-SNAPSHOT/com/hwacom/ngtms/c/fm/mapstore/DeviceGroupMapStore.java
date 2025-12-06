/*     */ package com.hwacom.ngtms.c.fm.mapstore;
/*     */ 
/*     */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceGroup;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceGroupDeviceConfig;
/*     */ import com.hwacom.ngtms.c.fm.repository.DeviceGroupDeviceConfigRepository;
/*     */ import com.hwacom.ngtms.c.fm.repository.DeviceGroupRepository;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.PostConstruct;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.data.repository.CrudRepository;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class DeviceGroupMapStore
/*     */   extends JPAMapStore<DeviceGroup, String>
/*     */ {
/*  27 */   private static Logger logger = LoggerFactory.getLogger(DeviceGroupMapStore.class);
/*     */   @Autowired
/*     */   DeviceGroupRepository repository;
/*     */   
/*     */   @PostConstruct
/*     */   public void setCrudRepository() {
/*  33 */     setCrudRepository((CrudRepository)this.repository);
/*     */   }
/*     */   @Autowired
/*     */   DeviceGroupDeviceConfigRepository deviceGroupDeviceConfigRepository;
/*     */   public Map<String, DeviceGroup> loadAll(Collection<String> collection) {
/*  38 */     Map<String, DeviceGroup> map = new HashMap<>();
/*  39 */     for (DeviceGroup c : this.repository.findAllById(collection)) {
/*  40 */       if (c.getDevices().isEmpty()) {
/*  41 */         logger.debug("DeviceGroupMapStore loadAll, id='{}'", c.getGroupId());
/*     */         
/*  43 */         Set<DeviceGroupDeviceConfig> configs = this.deviceGroupDeviceConfigRepository.findByGroupId(c.getGroupId());
/*  44 */         c.setDevices(configs);
/*     */       } 
/*  46 */       map.put(c.getGroupId(), c);
/*     */     } 
/*  48 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> loadAllKeys() {
/*  53 */     return this.repository.findAllKeys();
/*     */   }
/*     */ 
/*     */   
/*     */   public void store(String key, DeviceGroup bean) {
/*  58 */     logger.debug("store start ...");
/*  59 */     logger.debug("key : {} ; DeviceGroup : {} ", key, bean);
/*  60 */     if (bean.getDevices() != null) {
/*  61 */       this.deviceGroupDeviceConfigRepository.deleteAll(this.deviceGroupDeviceConfigRepository
/*  62 */           .findByGroupId(bean.getGroupId()));
/*  63 */       bean.getDevices()
/*  64 */         .stream()
/*  65 */         .forEach(speedBean -> this.deviceGroupDeviceConfigRepository.save(speedBean));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  70 */     this.repository.save(bean);
/*     */   }
/*     */   
/*     */   public void storeAll(Map<String, DeviceGroup> map) {
/*  74 */     logger.debug("storeAll start ...");
/*  75 */     map.values()
/*  76 */       .stream()
/*  77 */       .forEach(bean -> {
/*     */           logger.debug("DeviceGroup : {} ", bean);
/*     */ 
/*     */ 
/*     */           
/*     */           if (bean.getDevices() != null) {
/*     */             bean.getDevices().stream().forEach(());
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/*  89 */     this.repository.saveAll(map.values());
/*     */   }
/*     */   
/*     */   public void delete(String key) {
/*  93 */     logger.debug("delete start ...");
/*  94 */     logger.debug("key : {} ", key);
/*  95 */     DeviceGroup bean = this.repository.findById(key).orElse(null);
/*  96 */     if (bean.getDevices() != null) {
/*  97 */       bean.getDevices()
/*  98 */         .stream()
/*  99 */         .forEach(speedBean -> this.deviceGroupDeviceConfigRepository.deleteById(speedBean.getId()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 104 */     this.repository.deleteById(key);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\mapstore\DeviceGroupMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */