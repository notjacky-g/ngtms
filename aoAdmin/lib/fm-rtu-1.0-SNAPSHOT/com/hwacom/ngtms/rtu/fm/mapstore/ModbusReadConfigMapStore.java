/*    */ package com.hwacom.ngtms.rtu.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.rtu.fm.model.ModbusReadConfig;
/*    */ import com.hwacom.ngtms.rtu.fm.repository.ModbusReadConfigRepository;
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
/*    */ public class ModbusReadConfigMapStore
/*    */   extends JPAMapStore<ModbusReadConfig, Long>
/*    */ {
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 21 */     setCrudRepository((CrudRepository)this.repository);
/*    */   }
/*    */   @Autowired
/*    */   ModbusReadConfigRepository repository;
/*    */   public Map<Long, ModbusReadConfig> loadAll(Collection<Long> collection) {
/* 26 */     Map<Long, ModbusReadConfig> map = new HashMap<>();
/* 27 */     for (ModbusReadConfig config : this.repository.findAllById(collection)) {
/* 28 */       map.put(config.getId(), config);
/*    */     }
/* 30 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<Long> loadAllKeys() {
/* 35 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\fm-rtu-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\rtu\fm\mapstore\ModbusReadConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */