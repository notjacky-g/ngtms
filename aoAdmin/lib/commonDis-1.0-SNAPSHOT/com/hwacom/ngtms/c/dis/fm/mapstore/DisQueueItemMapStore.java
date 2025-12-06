/*    */ package com.hwacom.ngtms.c.dis.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.c.dis.fm.model.DisQueueItem;
/*    */ import com.hwacom.ngtms.c.dis.fm.repository.DisQueueItemRepository;
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
/*    */ @Service
/*    */ public class DisQueueItemMapStore
/*    */   extends JPAMapStore<DisQueueItem, String>
/*    */ {
/*    */   @Autowired
/*    */   DisQueueItemRepository queueItemRespository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 28 */     setCrudRepository((CrudRepository)this.queueItemRespository);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, DisQueueItem> loadAll(Collection<String> collection) {
/* 33 */     Map<String, DisQueueItem> map = new HashMap<>();
/* 34 */     List<DisQueueItem> queueItems = this.queueItemRespository.findAllById(collection);
/* 35 */     for (DisQueueItem queueItem : queueItems) {
/* 36 */       map.put(queueItem.getId(), queueItem);
/*    */     }
/* 38 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> loadAllKeys() {
/* 43 */     return this.queueItemRespository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\mapstore\DisQueueItemMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */