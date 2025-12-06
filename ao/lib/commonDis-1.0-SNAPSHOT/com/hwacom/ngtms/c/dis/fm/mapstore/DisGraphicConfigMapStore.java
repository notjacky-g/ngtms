/*    */ package com.hwacom.ngtms.c.dis.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.dis.fm.model.DisGraphicConfig;
/*    */ import com.hwacom.ngtms.c.dis.fm.repository.DisGraphicConfigRepository;
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
/*    */ 
/*    */ @Service
/*    */ public class DisGraphicConfigMapStore
/*    */   extends JPAMapStore<DisGraphicConfig, String>
/*    */ {
/*    */   @Autowired
/*    */   DisGraphicConfigRepository disGraphicConfigRepository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository()
/*    */   {
/* 29 */     setCrudRepository(this.disGraphicConfigRepository);
/*    */   }
/*    */   
/*    */   public Map<String, DisGraphicConfig> loadAll(Collection<String> collection)
/*    */   {
/* 34 */     Map<String, DisGraphicConfig> map = new HashMap();
/* 35 */     List<DisGraphicConfig> disGraphicConfigs = this.disGraphicConfigRepository.findAllById(collection);
/* 36 */     for (DisGraphicConfig disGraphicConfig : disGraphicConfigs) {
/* 37 */       map.put(disGraphicConfig.getId(), disGraphicConfig);
/*    */     }
/* 39 */     return map;
/*    */   }
/*    */   
/*    */   public Set<String> loadAllKeys()
/*    */   {
/* 44 */     return this.disGraphicConfigRepository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\mapstore\DisGraphicConfigMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */