/*    */ package com.hwacom.ngtms.c.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.fm.model.BranchConfig;
/*    */ import com.hwacom.ngtms.c.fm.repository.BranchConfigRepository;
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
/*    */ public class BranchConfigMapStore
/*    */   extends JPAMapStore<BranchConfig, String>
/*    */ {
/*    */   @Autowired
/*    */   BranchConfigRepository branchConfigRepository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository()
/*    */   {
/* 28 */     setCrudRepository(this.branchConfigRepository);
/*    */   }
/*    */   
/*    */   public Map<String, BranchConfig> loadAll(Collection<String> collection)
/*    */   {
/* 33 */     Map<String, BranchConfig> map = new HashMap();
/* 34 */     List<BranchConfig> branches = this.branchConfigRepository.findAllById(collection);
/* 35 */     for (BranchConfig branchConfig : branches) {
/* 36 */       map.put(branchConfig.getId(), branchConfig);
/*    */     }
/* 38 */     return map;
/*    */   }
/*    */   
/*    */   public Set<String> loadAllKeys()
/*    */   {
/* 43 */     return this.branchConfigRepository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\mapstore\BranchConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */