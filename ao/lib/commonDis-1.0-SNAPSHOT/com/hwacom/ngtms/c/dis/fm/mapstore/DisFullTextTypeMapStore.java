/*    */ package com.hwacom.ngtms.c.dis.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.dis.fm.model.DisFullTextType;
/*    */ import com.hwacom.ngtms.c.dis.fm.repository.DisFullTextTypeRepository;
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
/*    */ public class DisFullTextTypeMapStore
/*    */   extends JPAMapStore<DisFullTextType, String>
/*    */ {
/*    */   @Autowired
/*    */   DisFullTextTypeRepository disFullTextTypeRepository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository()
/*    */   {
/* 28 */     setCrudRepository(this.disFullTextTypeRepository);
/*    */   }
/*    */   
/*    */   public Map<String, DisFullTextType> loadAll(Collection<String> collection)
/*    */   {
/* 33 */     Map<String, DisFullTextType> map = new HashMap();
/* 34 */     List<DisFullTextType> disFullTextTypes = this.disFullTextTypeRepository.findAllById(collection);
/* 35 */     for (DisFullTextType disFullTextType : disFullTextTypes) {
/* 36 */       map.put(disFullTextType.getId(), disFullTextType);
/*    */     }
/* 38 */     return map;
/*    */   }
/*    */   
/*    */   public Set<String> loadAllKeys()
/*    */   {
/* 43 */     return this.disFullTextTypeRepository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\mapstore\DisFullTextTypeMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */