/*    */ package com.hwacom.ngtms.c.dis.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.dis.fm.model.DisSunshineTable;
/*    */ import com.hwacom.ngtms.c.dis.fm.repository.DisSunshineTableRepository;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
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
/*    */ public class DisSunshineTableMapStore
/*    */   extends JPAMapStore<DisSunshineTable, String>
/*    */ {
/*    */   @Autowired
/*    */   DisSunshineTableRepository disSunshineConfigRepository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 29 */     setCrudRepository((CrudRepository)this.disSunshineConfigRepository);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, DisSunshineTable> loadAll(Collection<String> collection) {
/* 34 */     Map<String, DisSunshineTable> map = new HashMap<>();
/* 35 */     List<DisSunshineTable> disSunshineConfigs = this.disSunshineConfigRepository.findAllById(collection);
/* 36 */     for (DisSunshineTable disSunshineConfig : disSunshineConfigs) {
/* 37 */       map.put(disSunshineConfig.getSunDuration(), disSunshineConfig);
/*    */     }
/* 39 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> loadAllKeys() {
/* 44 */     return this.disSunshineConfigRepository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\mapstore\DisSunshineTableMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */