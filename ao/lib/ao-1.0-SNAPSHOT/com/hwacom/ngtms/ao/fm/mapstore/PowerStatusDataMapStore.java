/*    */ package com.hwacom.ngtms.ao.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.ao.fm.model.PowerStatusData;
/*    */ import com.hwacom.ngtms.ao.fm.repository.PowerStatusDataRepository;
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
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
/*    */ 
/*    */ @Service
/*    */ public class PowerStatusDataMapStore
/*    */   extends JPAMapStore<PowerStatusData, String>
/*    */ {
/*    */   @Autowired
/*    */   PowerStatusDataRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 27 */     setCrudRepository((CrudRepository)this.repository);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, PowerStatusData> loadAll(Collection<String> collection) {
/* 32 */     Map<String, PowerStatusData> map = new HashMap<>();
/* 33 */     for (PowerStatusData c : this.repository.findAllById(collection)) {
/* 34 */       map.put(c.getId(), c);
/*    */     }
/* 36 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> loadAllKeys() {
/* 41 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\mapstore\PowerStatusDataMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */