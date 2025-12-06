/*    */ package com.hwacom.ngtms.c.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.fm.model.RoadLine;
/*    */ import com.hwacom.ngtms.c.fm.repository.RoadLineRepository;
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
/*    */ public class RoadLineMapStore
/*    */   extends JPAMapStore<RoadLine, String>
/*    */ {
/*    */   @Autowired
/*    */   RoadLineRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository()
/*    */   {
/* 27 */     setCrudRepository(this.repository);
/*    */   }
/*    */   
/*    */   public Map<String, RoadLine> loadAll(Collection<String> collection)
/*    */   {
/* 32 */     Map<String, RoadLine> map = new HashMap();
/* 33 */     for (RoadLine w : this.repository.findAllById(collection)) {
/* 34 */       map.put(w.getLineId(), w);
/*    */     }
/* 36 */     return map;
/*    */   }
/*    */   
/*    */   public Set<String> loadAllKeys()
/*    */   {
/* 41 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\mapstore\RoadLineMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */