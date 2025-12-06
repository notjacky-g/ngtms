/*    */ package com.hwacom.ngtms.alarm.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.alarm.fm.model.AlarmLevelConfig;
/*    */ import com.hwacom.ngtms.alarm.fm.repository.AlarmLevelConfigRepository;
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.annotation.PostConstruct;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.data.repository.CrudRepository;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class AlarmLevelConfigMapStore
/*    */   extends JPAMapStore<AlarmLevelConfig, String>
/*    */ {
/* 25 */   private static final Logger logger = LoggerFactory.getLogger(AlarmLevelConfigMapStore.class);
/*    */   @Autowired
/*    */   AlarmLevelConfigRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 31 */     setCrudRepository((CrudRepository)this.repository);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, AlarmLevelConfig> loadAll(Collection<String> collection) {
/* 36 */     Map<String, AlarmLevelConfig> map = new HashMap<>();
/* 37 */     for (AlarmLevelConfig c : this.repository.findAllById(collection)) {
/* 38 */       map.put(c.getId(), c);
/*    */     }
/* 40 */     logger.debug("loadAll map size:'{}'", Integer.valueOf(map.size()));
/* 41 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> loadAllKeys() {
/* 46 */     Set<String> keys = this.repository.findAllKeys();
/* 47 */     logger.debug("loadAllKeys keys:'{}'", keys);
/* 48 */     return keys;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\alarm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\alarm\fm\mapstore\AlarmLevelConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */