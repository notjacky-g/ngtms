/*    */ package com.hwacom.ngtms.room.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.room.fm.model.RoomDeviceStatus;
/*    */ import com.hwacom.ngtms.room.fm.repository.RoomDeviceStatusRepository;
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
/*    */ public class RoomDeviceStatusMapStore
/*    */   extends JPAMapStore<RoomDeviceStatus, String>
/*    */ {
/*    */   @Autowired
/*    */   RoomDeviceStatusRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 27 */     setCrudRepository((CrudRepository)this.repository);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, RoomDeviceStatus> loadAll(Collection<String> collection) {
/* 32 */     Map<String, RoomDeviceStatus> map = new HashMap<>();
/* 33 */     for (RoomDeviceStatus c : this.repository.findAllById(collection)) {
/* 34 */       map.put(c.getDeviceName(), c);
/*    */     }
/* 36 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> loadAllKeys() {
/* 41 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\fm\mapstore\RoomDeviceStatusMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */