/*    */ package com.hwacom.ngtms.c.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.fm.model.RoadCloverLeaf;
/*    */ import com.hwacom.ngtms.c.fm.repository.RoadCloverLeafRepository;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
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
/*    */ public class CloverLeafMapStore
/*    */   extends JPAMapStore<RoadCloverLeaf, String>
/*    */ {
/*    */   @Autowired
/*    */   RoadCloverLeafRepository cloverLeafRepository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository()
/*    */   {
/* 28 */     setCrudRepository(this.cloverLeafRepository);
/*    */   }
/*    */   
/*    */   public Map<String, RoadCloverLeaf> loadAll(Collection<String> collection)
/*    */   {
/* 33 */     Map<String, RoadCloverLeaf> map = new HashMap();
/* 34 */     List<RoadCloverLeaf> cloverLeafs = this.cloverLeafRepository.findAllById(collection);
/* 35 */     for (RoadCloverLeaf cloverLeaf : cloverLeafs) {
/* 36 */       map.put(cloverLeaf.getId(), cloverLeaf);
/*    */     }
/* 38 */     return map;
/*    */   }
/*    */   
/*    */   public Set<String> loadAllKeys()
/*    */   {
/* 43 */     return this.cloverLeafRepository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\mapstore\CloverLeafMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */