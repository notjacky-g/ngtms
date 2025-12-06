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
/*    */ 
/*    */ @org.springframework.stereotype.Service
/*    */ public class ModbusDeviceConfigMapStore extends JPAMapStore<ModbusDeviceConfig, String>
/*    */ {
/*    */   @org.springframework.beans.factory.annotation.Autowired
/*    */   ModbusDeviceConfigRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository()
/*    */   {
/* 21 */     setCrudRepository(this.repository);
/*    */   }
/*    */   
/*    */   public Map<String, ModbusDeviceConfig> loadAll(Collection<String> collection)
/*    */   {
/* 26 */     Map<String, ModbusDeviceConfig> map = new HashMap();
/* 27 */     for (ModbusDeviceConfig config : this.repository.findAllById(collection)) {
/* 28 */       map.put(config.getDeviceName(), config);
/*    */     }
/* 30 */     return map;
/*    */   }
/*    */   
/*    */   public Set<String> loadAllKeys()
/*    */   {
/* 35 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\fm-rtu-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\rtu\fm\mapstore\ModbusDeviceConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */