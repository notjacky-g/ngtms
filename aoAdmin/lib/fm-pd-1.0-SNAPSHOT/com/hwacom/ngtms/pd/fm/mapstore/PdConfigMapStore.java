/*    */ package com.hwacom.ngtms.pd.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.pd.fm.model.PdConfig;
/*    */ import com.hwacom.ngtms.pd.fm.repository.PdConfigRepository;
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
/*    */ public class PdConfigMapStore
/*    */   extends JPAMapStore<PdConfig, String>
/*    */ {
/*    */   @Autowired
/*    */   PdConfigRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 26 */     setCrudRepository((CrudRepository)this.repository);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, PdConfig> loadAll(Collection<String> collection) {
/* 31 */     Map<String, PdConfig> map = new HashMap<>();
/*    */     
/* 33 */     for (PdConfig config : this.repository.findAllById(collection)) {
/* 34 */       map.put(config.getDeviceName(), config);
/*    */     }
/* 36 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> loadAllKeys() {
/* 41 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\fm-pd-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\pd\fm\mapstore\PdConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */