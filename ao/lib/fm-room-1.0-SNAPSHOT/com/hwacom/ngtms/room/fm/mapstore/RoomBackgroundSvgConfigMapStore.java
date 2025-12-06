/*    */ package com.hwacom.ngtms.room.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.room.fm.model.RoomBackgroundSvgConfig;
/*    */ import com.hwacom.ngtms.room.fm.repository.RoomBackgroundSvgConfigRepository;
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
/*    */ public class RoomBackgroundSvgConfigMapStore
/*    */   extends JPAMapStore<RoomBackgroundSvgConfig, String>
/*    */ {
/*    */   @Autowired
/*    */   RoomBackgroundSvgConfigRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 27 */     setCrudRepository((CrudRepository)this.repository);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, RoomBackgroundSvgConfig> loadAll(Collection<String> collection) {
/* 32 */     Map<String, RoomBackgroundSvgConfig> map = new HashMap<>();
/* 33 */     for (RoomBackgroundSvgConfig c : this.repository.findAllById(collection)) {
/* 34 */       map.put(c.getNameId(), c);
/*    */     }
/* 36 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> loadAllKeys() {
/* 41 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\fm\mapstore\RoomBackgroundSvgConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */