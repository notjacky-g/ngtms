/*    */ package com.hwacom.ngtms.room.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.room.fm.model.RoomCardReaderMappingConfig;
/*    */ import com.hwacom.ngtms.room.fm.repository.RoomCardReaderMappingConfigRepository;
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
/*    */ public class RoomCardReaderMappingConfigMapStore
/*    */   extends JPAMapStore<RoomCardReaderMappingConfig, String>
/*    */ {
/*    */   @Autowired
/*    */   RoomCardReaderMappingConfigRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 27 */     setCrudRepository((CrudRepository)this.repository);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, RoomCardReaderMappingConfig> loadAll(Collection<String> collection) {
/* 32 */     Map<String, RoomCardReaderMappingConfig> map = new HashMap<>();
/*    */     
/* 34 */     for (RoomCardReaderMappingConfig config : this.repository.findAllById(collection)) {
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


/* Location:              C:\User\\user\Desktop\lib\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\fm\mapstore\RoomCardReaderMappingConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */