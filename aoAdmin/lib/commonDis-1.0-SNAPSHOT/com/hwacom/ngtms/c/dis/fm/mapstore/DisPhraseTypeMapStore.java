/*    */ package com.hwacom.ngtms.c.dis.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.dis.fm.model.DisPhraseType;
/*    */ import com.hwacom.ngtms.c.dis.fm.repository.DisPhraseTypeRepository;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
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
/*    */ public class DisPhraseTypeMapStore
/*    */   extends JPAMapStore<DisPhraseType, String>
/*    */ {
/*    */   @Autowired
/*    */   DisPhraseTypeRepository disPhraseTypeRepository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 29 */     setCrudRepository((CrudRepository)this.disPhraseTypeRepository);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, DisPhraseType> loadAll(Collection<String> collection) {
/* 34 */     Map<String, DisPhraseType> map = new HashMap<>();
/* 35 */     List<DisPhraseType> disPhraseTypes = this.disPhraseTypeRepository.findAllById(collection);
/* 36 */     for (DisPhraseType disPhraseType : disPhraseTypes) {
/* 37 */       map.put(disPhraseType.getId(), disPhraseType);
/*    */     }
/* 39 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> loadAllKeys() {
/* 44 */     return this.disPhraseTypeRepository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\mapstore\DisPhraseTypeMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */