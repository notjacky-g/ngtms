/*    */ package com.hwacom.ngtms.room.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.room.fm.model.RoomCardReaderLog;
/*    */ import com.hwacom.ngtms.room.fm.repository.RoomCardReaderLogRepository;
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
/*    */ @Service
/*    */ public class RoomCardReaderLogMapStore
/*    */   extends JPAMapStore<RoomCardReaderLog, String>
/*    */ {
/*    */   @Autowired
/*    */   RoomCardReaderLogRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 26 */     setCrudRepository((CrudRepository)this.repository);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, RoomCardReaderLog> loadAll(Collection<String> collection) {
/* 31 */     Map<String, RoomCardReaderLog> map = new HashMap<>();
/*    */     
/* 33 */     for (RoomCardReaderLog config : this.repository.findAllById(collection)) {
/* 34 */       map.put(config.getId(), config);
/*    */     }
/* 36 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> loadAllKeys() {
/* 41 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\fm\mapstore\RoomCardReaderLogMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */