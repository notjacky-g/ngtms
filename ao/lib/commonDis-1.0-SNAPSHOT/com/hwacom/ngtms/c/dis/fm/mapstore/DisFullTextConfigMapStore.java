/*    */ package com.hwacom.ngtms.c.dis.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.dis.fm.model.DisFullTextConfig;
/*    */ import com.hwacom.ngtms.c.dis.fm.repository.DisFullTextConfigRepository;
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
/*    */ 
/*    */ @Service
/*    */ public class DisFullTextConfigMapStore
/*    */   extends JPAMapStore<DisFullTextConfig, String>
/*    */ {
/*    */   @Autowired
/*    */   DisFullTextConfigRepository disFullTextConfigRepository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository()
/*    */   {
/* 29 */     setCrudRepository(this.disFullTextConfigRepository);
/*    */   }
/*    */   
/*    */   public Map<String, DisFullTextConfig> loadAll(Collection<String> collection)
/*    */   {
/* 34 */     Map<String, DisFullTextConfig> map = new HashMap();
/*    */     
/* 36 */     List<DisFullTextConfig> disFullTextConfigs = this.disFullTextConfigRepository.findAllById(collection);
/* 37 */     for (DisFullTextConfig disFullTextConfig : disFullTextConfigs) {
/* 38 */       map.put(disFullTextConfig.getId(), disFullTextConfig);
/*    */     }
/* 40 */     return map;
/*    */   }
/*    */   
/*    */   public Set<String> loadAllKeys()
/*    */   {
/* 45 */     return this.disFullTextConfigRepository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\mapstore\DisFullTextConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */