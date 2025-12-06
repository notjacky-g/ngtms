/*    */ package com.hwacom.ngtms.c.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.fm.model.AlarmSubTypeConfig;
/*    */ import com.hwacom.ngtms.c.fm.repository.AlarmSubTypeConfigRepository;
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
/*    */ public class AlarmSubTypeConfigMapStore
/*    */   extends JPAMapStore<AlarmSubTypeConfig, Integer>
/*    */ {
/*    */   @Autowired
/*    */   AlarmSubTypeConfigRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository()
/*    */   {
/* 27 */     setCrudRepository(this.repository);
/*    */   }
/*    */   
/*    */   public Map<Integer, AlarmSubTypeConfig> loadAll(Collection<Integer> collection)
/*    */   {
/* 32 */     Map<Integer, AlarmSubTypeConfig> map = new HashMap();
/* 33 */     for (AlarmSubTypeConfig w : this.repository.findAllById(collection)) {
/* 34 */       map.put(w.getId(), w);
/*    */     }
/* 36 */     return map;
/*    */   }
/*    */   
/*    */   public Set<Integer> loadAllKeys()
/*    */   {
/* 41 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\mapstore\AlarmSubTypeConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */