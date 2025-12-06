/*    */ package com.hwacom.ngtms.hcce.config.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*    */ import com.hwacom.ngtms.hcce.config.model.DynamicConfigPk;
/*    */ import com.hwacom.ngtms.hcce.config.repository.DynamicConfigRepository;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import javax.annotation.PostConstruct;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class DyanConfigMapStore
/*    */   extends JPAMapStore<DynamicConfig, DynamicConfigPk>
/*    */ {
/*    */   @Autowired
/*    */   private DynamicConfigRepository dynamicConfigRepository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository()
/*    */   {
/* 28 */     setCrudRepository(this.dynamicConfigRepository);
/*    */   }
/*    */   
/*    */ 
/*    */   public DynamicConfig load(DynamicConfigPk key)
/*    */   {
/* 34 */     return (DynamicConfig)this.dynamicConfigRepository.findById(key).orElse(null);
/*    */   }
/*    */   
/*    */   public Map<DynamicConfigPk, DynamicConfig> loadAll(Collection<DynamicConfigPk> collection)
/*    */   {
/* 39 */     Map<DynamicConfigPk, DynamicConfig> map = new HashMap();
/* 40 */     List<DynamicConfig> dynaCfgs = this.dynamicConfigRepository.findAllById(collection);
/* 41 */     for (DynamicConfig dynaCfg : dynaCfgs) {
/* 42 */       map.put(dynaCfg.getPk(), dynaCfg);
/*    */     }
/* 44 */     return map;
/*    */   }
/*    */   
/*    */   public Set<DynamicConfigPk> loadAllKeys()
/*    */   {
/* 49 */     return this.dynamicConfigRepository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\config\mapstore\DyanConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */