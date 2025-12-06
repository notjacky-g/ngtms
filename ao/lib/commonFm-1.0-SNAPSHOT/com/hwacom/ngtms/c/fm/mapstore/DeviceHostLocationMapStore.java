/*    */ package com.hwacom.ngtms.c.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.fm.model.DeviceHostLocation;
/*    */ import com.hwacom.ngtms.c.fm.repository.DeviceHostLocationRepository;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.annotation.PostConstruct;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class DeviceHostLocationMapStore
/*    */   extends JPAMapStore<DeviceHostLocation, Integer>
/*    */ {
/*    */   @Autowired
/*    */   private DeviceHostLocationRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository()
/*    */   {
/* 26 */     setCrudRepository(this.repository);
/*    */   }
/*    */   
/*    */   public Map<Integer, DeviceHostLocation> loadAll(Collection<Integer> collection)
/*    */   {
/* 31 */     Map<Integer, DeviceHostLocation> map = new HashMap();
/* 32 */     for (DeviceHostLocation c : this.repository.findAllById(collection)) {
/* 33 */       map.put(c.getId(), c);
/*    */     }
/* 35 */     return map;
/*    */   }
/*    */   
/*    */   public Set<Integer> loadAllKeys()
/*    */   {
/* 40 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\mapstore\DeviceHostLocationMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */