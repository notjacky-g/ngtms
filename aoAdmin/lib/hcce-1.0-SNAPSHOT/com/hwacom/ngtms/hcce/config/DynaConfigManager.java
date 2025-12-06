/*    */ package com.hwacom.ngtms.hcce.config;
/*    */ 
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hazelcast.query.EntryObject;
/*    */ import com.hazelcast.query.Predicate;
/*    */ import com.hazelcast.query.PredicateBuilder;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*    */ import com.hwacom.ngtms.hcce.config.model.DynamicConfigPk;
/*    */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class DynaConfigManager
/*    */ {
/*    */   private IMap<DynamicConfigPk, DynamicConfig> dynaCfgMap;
/*    */   
/*    */   public void init() {
/* 25 */     this.dynaCfgMap = HzUtils.getMap((HzDistObjEnum)HzMap.DynamicConfig);
/*    */   }
/*    */   
/*    */   public DynamicConfig getConfig(DynamicConfigPk dynamicConfigPk) {
/* 29 */     return (DynamicConfig)this.dynaCfgMap.get(dynamicConfigPk);
/*    */   }
/*    */   
/*    */   public void addDynamicConfig(DynamicConfig cfg) {
/* 33 */     this.dynaCfgMap.set(cfg.getPk(), cfg);
/*    */   }
/*    */   
/*    */   public void updateDynamicConfig(DynamicConfig cfg) {
/* 37 */     this.dynaCfgMap.set(cfg.getPk(), cfg);
/*    */   }
/*    */   
/*    */   public void removeDynamicConfig(DynamicConfigPk id) {
/* 41 */     this.dynaCfgMap.delete(id);
/*    */   }
/*    */   
/*    */   public void removeFmeConfigs(String groupName, String fmeName) {
/* 45 */     EntryObject e = (new PredicateBuilder()).getEntryObject();
/*    */     
/* 47 */     Set<DynamicConfigPk> keySet = this.dynaCfgMap.keySet((Predicate)e.get("groupName").equal(groupName).and((Predicate)e.get("fmeName").equal(fmeName)));
/* 48 */     for (DynamicConfigPk key : keySet) {
/* 49 */       this.dynaCfgMap.delete(key);
/*    */     }
/*    */   }
/*    */   
/*    */   public Map<DynamicConfigPk, DynamicConfig> getFmeConfigs(String groupName, String fmeName) {
/* 54 */     EntryObject e = (new PredicateBuilder()).getEntryObject();
/*    */     
/* 56 */     Set<DynamicConfigPk> keySet = this.dynaCfgMap.keySet((Predicate)e.get("groupName").equal(groupName).and((Predicate)e.get("fmeName").equal(fmeName)));
/* 57 */     return this.dynaCfgMap.getAll(keySet);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\config\DynaConfigManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */