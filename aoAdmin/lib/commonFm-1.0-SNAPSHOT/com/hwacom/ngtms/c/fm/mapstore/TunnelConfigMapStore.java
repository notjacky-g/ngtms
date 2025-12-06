/*    */ package com.hwacom.ngtms.c.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.fm.model.TunnelConfig;
/*    */ import com.hwacom.ngtms.c.fm.repository.TunnelConfigRepository;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.annotation.PostConstruct;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.data.repository.CrudRepository;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ @Service
/*    */ public class TunnelConfigMapStore
/*    */   extends JPAMapStore<TunnelConfig, String> {
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 20 */     setCrudRepository((CrudRepository)this.repository);
/*    */   }
/*    */   @Autowired
/*    */   private TunnelConfigRepository repository;
/*    */   public Map<String, TunnelConfig> loadAll(Collection<String> collection) {
/* 25 */     Map<String, TunnelConfig> resultMap = new HashMap<>();
/* 26 */     for (TunnelConfig config : this.repository.findAllById(collection)) {
/* 27 */       resultMap.put(config.getTunnelId(), config);
/*    */     }
/*    */     
/* 30 */     return resultMap;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> loadAllKeys() {
/* 35 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\mapstore\TunnelConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */