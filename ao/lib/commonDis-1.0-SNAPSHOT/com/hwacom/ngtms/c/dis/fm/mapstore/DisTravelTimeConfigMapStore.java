/*    */ package com.hwacom.ngtms.c.dis.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.dis.fm.model.DisTravelTimeConfig;
/*    */ import com.hwacom.ngtms.c.dis.fm.repository.DisTravelTimeConfigRepository;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.annotation.PostConstruct;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class DisTravelTimeConfigMapStore
/*    */   extends JPAMapStore<DisTravelTimeConfig, String>
/*    */ {
/*    */   @Autowired
/*    */   DisTravelTimeConfigRepository disTravelTimeConfigRepository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository()
/*    */   {
/* 28 */     setCrudRepository(this.disTravelTimeConfigRepository);
/*    */   }
/*    */   
/*    */   public Map<String, DisTravelTimeConfig> loadAll(Collection<String> collection)
/*    */   {
/* 33 */     Map<String, DisTravelTimeConfig> map = new HashMap();
/*    */     
/* 35 */     List<DisTravelTimeConfig> disTravelTimeConfigs = this.disTravelTimeConfigRepository.findAllById(collection);
/* 36 */     for (DisTravelTimeConfig disTravelTimeConfig : disTravelTimeConfigs) {
/* 37 */       map.put(disTravelTimeConfig.getId(), disTravelTimeConfig);
/*    */     }
/* 39 */     return map;
/*    */   }
/*    */   
/*    */   public Set<String> loadAllKeys()
/*    */   {
/* 44 */     return this.disTravelTimeConfigRepository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\mapstore\DisTravelTimeConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */