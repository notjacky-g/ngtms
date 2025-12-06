/*    */ package com.hwacom.ngtms.c.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.fm.model.TunnelConfig;
/*    */ import com.hwacom.ngtms.c.fm.repository.TunnelConfigRepository;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ @org.springframework.stereotype.Service
/*    */ public class TunnelConfigMapStore extends JPAMapStore<TunnelConfig, String>
/*    */ {
/*    */   @org.springframework.beans.factory.annotation.Autowired
/*    */   private TunnelConfigRepository repository;
/*    */   
/*    */   @javax.annotation.PostConstruct
/*    */   public void setCrudRepository()
/*    */   {
/* 20 */     setCrudRepository(this.repository);
/*    */   }
/*    */   
/*    */   public Map<String, TunnelConfig> loadAll(Collection<String> collection)
/*    */   {
/* 25 */     Map<String, TunnelConfig> resultMap = new HashMap();
/* 26 */     for (TunnelConfig config : this.repository.findAllById(collection)) {
/* 27 */       resultMap.put(config.getTunnelId(), config);
/*    */     }
/*    */     
/* 30 */     return resultMap;
/*    */   }
/*    */   
/*    */   public Set<String> loadAllKeys()
/*    */   {
/* 35 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\mapstore\TunnelConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */