/*    */ package com.hwacom.ngtms.c.dgs.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.dgs.fm.model.RampVdConfig;
/*    */ import com.hwacom.ngtms.c.dgs.fm.repository.RampVdConfigRepository;
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
/*    */ public class RmpVdConfigMapStore
/*    */   extends JPAMapStore<RampVdConfig, Integer>
/*    */ {
/*    */   @Autowired
/*    */   RampVdConfigRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 26 */     setCrudRepository((CrudRepository)this.repository);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<Integer, RampVdConfig> loadAll(Collection<Integer> collection) {
/* 31 */     Map<Integer, RampVdConfig> map = new HashMap<>();
/* 32 */     for (RampVdConfig c : this.repository.findAllById(collection)) {
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


/* Location:              C:\User\\user\Desktop\lib\commonDgs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dgs\fm\mapstore\RmpVdConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */