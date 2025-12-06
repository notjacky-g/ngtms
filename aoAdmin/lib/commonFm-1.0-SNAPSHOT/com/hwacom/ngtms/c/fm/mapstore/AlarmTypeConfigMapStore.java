/*    */ package com.hwacom.ngtms.c.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.fm.model.AlarmTypeConfig;
/*    */ import com.hwacom.ngtms.c.fm.repository.AlarmTypeConfigRepository;
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
/*    */ public class AlarmTypeConfigMapStore
/*    */   extends JPAMapStore<AlarmTypeConfig, String>
/*    */ {
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 21 */     setCrudRepository((CrudRepository)this.repository);
/*    */   }
/*    */   @Autowired
/*    */   AlarmTypeConfigRepository repository;
/*    */   public Map<String, AlarmTypeConfig> loadAll(Collection<String> collection) {
/* 26 */     Map<String, AlarmTypeConfig> map = new HashMap<>();
/* 27 */     for (AlarmTypeConfig alarmType : this.repository.findAllById(collection)) {
/* 28 */       map.put(alarmType.getAlarmType(), alarmType);
/*    */     }
/* 30 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> loadAllKeys() {
/* 35 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\mapstore\AlarmTypeConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */