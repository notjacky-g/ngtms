/*    */ package com.hwacom.ngtms.c.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.fm.model.DeviceTcHardwareStatus;
/*    */ import com.hwacom.ngtms.c.fm.repository.DeviceTcHardwareStatusRepository;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.annotation.PostConstruct;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.data.repository.CrudRepository;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ @Service
/*    */ public class DeviceTcHardwareStatusMapStore
/*    */   extends JPAMapStore<DeviceTcHardwareStatus, String>
/*    */ {
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 21 */     setCrudRepository((CrudRepository)this.repository);
/*    */   }
/*    */   @Autowired
/*    */   DeviceTcHardwareStatusRepository repository;
/*    */   public Map<String, DeviceTcHardwareStatus> loadAll(Collection<String> collection) {
/* 26 */     Map<String, DeviceTcHardwareStatus> map = new HashMap<>();
/* 27 */     for (DeviceTcHardwareStatus config : this.repository.findAllById(collection)) {
/* 28 */       map.put(config.getId(), config);
/*    */     }
/* 30 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> loadAllKeys() {
/* 35 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\mapstore\DeviceTcHardwareStatusMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */