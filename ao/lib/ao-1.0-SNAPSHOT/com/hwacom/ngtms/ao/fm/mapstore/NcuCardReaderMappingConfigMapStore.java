/*    */ package com.hwacom.ngtms.ao.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.ao.fm.model.NcuCardReaderMappingConfig;
/*    */ import com.hwacom.ngtms.ao.fm.repository.NcuCardReaderMappingConfigRepository;
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
/*    */ 
/*    */ @Service
/*    */ public class NcuCardReaderMappingConfigMapStore
/*    */   extends JPAMapStore<NcuCardReaderMappingConfig, String>
/*    */ {
/*    */   @Autowired
/*    */   NcuCardReaderMappingConfigRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 28 */     setCrudRepository((CrudRepository)this.repository);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, NcuCardReaderMappingConfig> loadAll(Collection<String> collection) {
/* 33 */     Map<String, NcuCardReaderMappingConfig> map = new HashMap<>();
/* 34 */     for (NcuCardReaderMappingConfig c : this.repository.findAllById(collection)) {
/* 35 */       map.put(c.getId(), c);
/*    */     }
/* 37 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> loadAllKeys() {
/* 42 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\mapstore\NcuCardReaderMappingConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */