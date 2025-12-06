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
/*    */ 
/*    */ @org.springframework.stereotype.Service
/*    */ public class AlarmTypeConfigMapStore extends JPAMapStore<AlarmTypeConfig, String>
/*    */ {
/*    */   @org.springframework.beans.factory.annotation.Autowired
/*    */   AlarmTypeConfigRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository()
/*    */   {
/* 21 */     setCrudRepository(this.repository);
/*    */   }
/*    */   
/*    */   public Map<String, AlarmTypeConfig> loadAll(Collection<String> collection)
/*    */   {
/* 26 */     Map<String, AlarmTypeConfig> map = new HashMap();
/* 27 */     for (AlarmTypeConfig alarmType : this.repository.findAllById(collection)) {
/* 28 */       map.put(alarmType.getAlarmType(), alarmType);
/*    */     }
/* 30 */     return map;
/*    */   }
/*    */   
/*    */   public Set<String> loadAllKeys()
/*    */   {
/* 35 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\mapstore\AlarmTypeConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */