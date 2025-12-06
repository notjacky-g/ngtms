/*    */ package com.hwacom.ngtms.c.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*    */ import com.hwacom.ngtms.c.fm.repository.DeviceTcConfigRepository;
/*    */ import com.hwacom.ngtms.common.fm.repository.DeviceConfigRepository;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.annotation.PostConstruct;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.data.repository.CrudRepository;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class DeviceTcConfigMapStore
/*    */   extends JPAMapStore<DeviceTcConfig, String>
/*    */ {
/*    */   @Autowired
/*    */   DeviceTcConfigRepository repository;
/*    */   @Autowired
/*    */   DeviceConfigRepository deviceConfigRepository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 29 */     setCrudRepository((CrudRepository)this.repository);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, DeviceTcConfig> loadAll(Collection<String> collection) {
/* 34 */     Map<String, DeviceTcConfig> map = new HashMap<>();
/* 35 */     for (DeviceTcConfig w : this.repository.findAllById(collection)) {
/* 36 */       map.put(w.getDeviceName(), w);
/*    */     }
/* 38 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> loadAllKeys() {
/* 43 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\mapstore\DeviceTcConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */