/*    */ package com.hwacom.ngtms.c.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.fm.model.RoadSection;
/*    */ import com.hwacom.ngtms.c.fm.repository.RoadSectionRepository;
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
/*    */ public class RoadSectionMapStore
/*    */   extends JPAMapStore<RoadSection, String>
/*    */ {
/*    */   @Autowired
/*    */   RoadSectionRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository()
/*    */   {
/* 26 */     setCrudRepository(this.repository);
/*    */   }
/*    */   
/*    */   public Map<String, RoadSection> loadAll(Collection<String> collection)
/*    */   {
/* 31 */     Map<String, RoadSection> map = new HashMap();
/* 32 */     for (RoadSection w : this.repository.findAllById(collection)) {
/* 33 */       map.put(w.getSectionId(), w);
/*    */     }
/* 35 */     return map;
/*    */   }
/*    */   
/*    */   public Set<String> loadAllKeys()
/*    */   {
/* 40 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\mapstore\RoadSectionMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */