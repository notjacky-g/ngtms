/*    */ package com.hwacom.ngtms.c.dgs.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.dgs.fm.model.RingRoadConfig;
/*    */ import com.hwacom.ngtms.c.dgs.fm.repository.RingRoadConfigRepository;
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
/*    */ public class RingRoadConfigMapStore
/*    */   extends JPAMapStore<RingRoadConfig, String>
/*    */ {
/*    */   @Autowired
/*    */   RingRoadConfigRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 27 */     setCrudRepository((CrudRepository)this.repository);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, RingRoadConfig> loadAll(Collection<String> collection) {
/* 32 */     Map<String, RingRoadConfig> map = new HashMap<>();
/* 33 */     for (RingRoadConfig w : this.repository.findAllById(collection)) {
/* 34 */       map.put(w.getId(), w);
/*    */     }
/* 36 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> loadAllKeys() {
/* 41 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonDgs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dgs\fm\mapstore\RingRoadConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */