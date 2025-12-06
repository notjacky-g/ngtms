/*    */ package com.hwacom.ngtms.rtu.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.rtu.fm.model.ModbusDeviceConfig;
/*    */ import com.hwacom.ngtms.rtu.fm.repository.ModbusDeviceConfigRepository;
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
/*    */ public class ModbusDeviceConfigMapStore
/*    */   extends JPAMapStore<ModbusDeviceConfig, String>
/*    */ {
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 21 */     setCrudRepository((CrudRepository)this.repository);
/*    */   }
/*    */   @Autowired
/*    */   ModbusDeviceConfigRepository repository;
/*    */   public Map<String, ModbusDeviceConfig> loadAll(Collection<String> collection) {
/* 26 */     Map<String, ModbusDeviceConfig> map = new HashMap<>();
/* 27 */     for (ModbusDeviceConfig config : this.repository.findAllById(collection)) {
/* 28 */       map.put(config.getDeviceName(), config);
/*    */     }
/* 30 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> loadAllKeys() {
/* 35 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\fm-rtu-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\rtu\fm\mapstore\ModbusDeviceConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */