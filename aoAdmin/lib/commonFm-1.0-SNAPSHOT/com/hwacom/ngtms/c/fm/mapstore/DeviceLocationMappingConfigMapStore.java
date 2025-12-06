/*    */ package com.hwacom.ngtms.c.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.fm.model.DeviceLocationMappingConfig;
/*    */ import com.hwacom.ngtms.c.fm.repository.DeviceLocationMappingConfigRepository;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.annotation.PostConstruct;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.data.repository.CrudRepository;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class DeviceLocationMappingConfigMapStore
/*    */   extends JPAMapStore<DeviceLocationMappingConfig, String>
/*    */ {
/*    */   @Autowired
/*    */   DeviceLocationMappingConfigRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 27 */     setCrudRepository((CrudRepository)this.repository);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, DeviceLocationMappingConfig> loadAll(Collection<String> collection) {
/* 32 */     Map<String, DeviceLocationMappingConfig> map = new HashMap<>();
/*    */     
/* 34 */     for (DeviceLocationMappingConfig config : this.repository.findAllById(collection)) {
/* 35 */       map.put(config.getId(), config);
/*    */     }
/* 37 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> loadAllKeys() {
/* 42 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\mapstore\DeviceLocationMappingConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */