/*    */ package com.hwacom.ngtms.c.dgs.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.dgs.fm.model.RoadLaneCount;
/*    */ import com.hwacom.ngtms.c.dgs.fm.repository.RoadLaneCountRepository;
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
/*    */ 
/*    */ @Service
/*    */ public class RoadLaneCountMapStore
/*    */   extends JPAMapStore<RoadLaneCount, String>
/*    */ {
/*    */   @Autowired
/*    */   RoadLaneCountRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 28 */     setCrudRepository((CrudRepository)this.repository);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, RoadLaneCount> loadAll(Collection<String> collection) {
/* 33 */     Map<String, RoadLaneCount> map = new HashMap<>();
/* 34 */     for (RoadLaneCount c : this.repository.findAllById(collection)) {
/* 35 */       map.put(c.getId(), c);
/*    */     }
/* 37 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> loadAllKeys() {
/* 42 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonDgs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dgs\fm\mapstore\RoadLaneCountMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */