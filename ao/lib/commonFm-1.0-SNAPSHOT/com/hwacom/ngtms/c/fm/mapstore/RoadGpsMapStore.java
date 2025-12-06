/*    */ package com.hwacom.ngtms.c.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.fm.model.RoadGps;
/*    */ import com.hwacom.ngtms.c.fm.repository.RoadGpsRepository;
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
/*    */ 
/*    */ @Service
/*    */ public class RoadGpsMapStore
/*    */   extends JPAMapStore<RoadGps, Long>
/*    */ {
/*    */   @Autowired
/*    */   RoadGpsRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository()
/*    */   {
/* 27 */     setCrudRepository(this.repository);
/*    */   }
/*    */   
/*    */   public Map<Long, RoadGps> loadAll(Collection<Long> collection)
/*    */   {
/* 32 */     Map<Long, RoadGps> map = new HashMap();
/* 33 */     for (RoadGps w : this.repository.findAllById(collection)) {
/* 34 */       map.put(w.getNo(), w);
/*    */     }
/* 36 */     return map;
/*    */   }
/*    */   
/*    */   public Set<Long> loadAllKeys()
/*    */   {
/* 41 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\mapstore\RoadGpsMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */