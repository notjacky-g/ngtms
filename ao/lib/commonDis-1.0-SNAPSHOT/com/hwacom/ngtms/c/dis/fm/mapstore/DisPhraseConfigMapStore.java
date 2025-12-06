/*    */ package com.hwacom.ngtms.c.dis.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.dis.fm.model.DisPhraseConfig;
/*    */ import com.hwacom.ngtms.c.dis.fm.repository.DisPhraseConfigRepository;
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
/*    */ public class DisPhraseConfigMapStore
/*    */   extends JPAMapStore<DisPhraseConfig, String>
/*    */ {
/*    */   @Autowired
/*    */   DisPhraseConfigRepository disPhraseConfigRepository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository()
/*    */   {
/* 29 */     setCrudRepository(this.disPhraseConfigRepository);
/*    */   }
/*    */   
/*    */   public Map<String, DisPhraseConfig> loadAll(Collection<String> collection)
/*    */   {
/* 34 */     Map<String, DisPhraseConfig> map = new HashMap();
/* 35 */     List<DisPhraseConfig> disPhraseConfigs = this.disPhraseConfigRepository.findAllById(collection);
/* 36 */     for (DisPhraseConfig disPhraseConfig : disPhraseConfigs) {
/* 37 */       map.put(disPhraseConfig.getId(), disPhraseConfig);
/*    */     }
/* 39 */     return map;
/*    */   }
/*    */   
/*    */   public Set<String> loadAllKeys()
/*    */   {
/* 44 */     return this.disPhraseConfigRepository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\mapstore\DisPhraseConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */