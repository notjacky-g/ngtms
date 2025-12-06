/*    */ package com.hwacom.ngtms.c.dgs.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.dgs.fm.model.RampVdType;
/*    */ import com.hwacom.ngtms.c.dgs.fm.repository.RampVdTypeRepository;
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
/*    */ @Service
/*    */ public class RmpVdTypeMapStore
/*    */   extends JPAMapStore<RampVdType, Integer>
/*    */ {
/*    */   @Autowired
/*    */   RampVdTypeRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 26 */     setCrudRepository((CrudRepository)this.repository);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<Integer, RampVdType> loadAll(Collection<Integer> collection) {
/* 31 */     Map<Integer, RampVdType> map = new HashMap<>();
/* 32 */     for (RampVdType c : this.repository.findAllById(collection)) {
/* 33 */       map.put(c.getId(), c);
/*    */     }
/* 35 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<Integer> loadAllKeys() {
/* 40 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonDgs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dgs\fm\mapstore\RmpVdTypeMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */