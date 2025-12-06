/*     */ package com.hwacom.ngtms.c.dis.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.dis.fm.hz.DisHzMap;
/*     */ import com.hwacom.ngtms.c.dis.fm.model.DisQueueItem;
/*     */ import com.hwacom.ngtms.c.shared.PriorityType;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DisQueueManager
/*     */ {
/*  38 */   private static Logger logger = LoggerFactory.getLogger(DisQueueManager.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   private DisQueueItemPredicate disQueueItemPredicate;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putQueue(DisQueueItem queueItem) {
/*  50 */     logger.debug("Put queue, queueItem='{}'", queueItem);
/*  51 */     IMap<String, DisQueueItem> queueItemMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.QueueItem);
/*  52 */     queueItemMap.set(queueItem.getId(), queueItem);
/*  53 */     processeTopQueueItem(
/*  54 */         getTopPriorityByDeviceName(queueItem.getDeviceName()), queueItem.getDeviceName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisQueueItem getTopPriorityByDeviceName(String deviceName) {
/*  64 */     IMap<String, DisQueueItem> queueItemMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.QueueItem);
/*     */     
/*  66 */     List<DisQueueItem> sortedQueueItems = this.disQueueItemPredicate.getSortedQueue(new ArrayList<>(queueItemMap
/*  67 */           .values()), null, deviceName);
/*  68 */     return (sortedQueueItems == null || sortedQueueItems.size() == 0) ? null : sortedQueueItems
/*     */       
/*  70 */       .get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, DisQueueItem> getTopPrioritiesByMultiDeviceName(List<String> deviceNameList) {
/*  81 */     Map<String, DisQueueItem> result = new HashMap<>();
/*  82 */     deviceNameList.forEach(deviceName -> {
/*     */           DisQueueItem queueItem = getTopPriorityByDeviceName(deviceName);
/*     */           
/*     */           paramMap.put(queueItem.getDeviceName(), queueItem);
/*     */         });
/*  87 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<DisQueueItem> getSortedQueue(String deviceName) {
/*  97 */     IMap<String, DisQueueItem> queueItemMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.QueueItem);
/*  98 */     return this.disQueueItemPredicate.getSortedQueue(new ArrayList<>(queueItemMap
/*  99 */           .values()), null, deviceName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractMap.SimpleEntry<String, DisQueueItem> removeFromQueueById(String id) {
/* 110 */     logger.debug("Remove from queue by id, id='{}'", id);
/* 111 */     IMap<String, DisQueueItem> queueItemMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.QueueItem);
/* 112 */     if (!queueItemMap.containsKey(id)) {
/* 113 */       logger.debug("Id is not exist, id='{}'", id);
/* 114 */       return new AbstractMap.SimpleEntry<>(null, null);
/*     */     } 
/* 116 */     DisQueueItem removed = null;
/*     */     try {
/* 118 */       if (queueItemMap.tryLock(id, 1L, TimeUnit.SECONDS)) {
/*     */         try {
/* 120 */           logger.debug("Lock QueueItemMap by id='{}'", id);
/* 121 */           removed = (DisQueueItem)queueItemMap.remove(id);
/* 122 */         } catch (Exception e) {
/* 123 */           logger.error("Lock QueueItemMap by id='{}' failed.", id, e);
/*     */         } finally {
/* 125 */           logger.debug("Unlock QueueItemMap by id='{}'", id);
/* 126 */           queueItemMap.unlock(id);
/*     */         } 
/*     */       }
/* 129 */       processeTopQueueItem(
/* 130 */           getTopPriorityByDeviceName(removed.getDeviceName()), removed.getDeviceName());
/* 131 */     } catch (InterruptedException e) {
/* 132 */       logger.error("Remove from queue by id failed, id='{}'", id, e);
/*     */     } 
/* 134 */     return (removed == null) ? null : new AbstractMap.SimpleEntry<>(removed
/*     */ 
/*     */         
/* 137 */         .getDeviceName(), getTopPriorityByDeviceName(removed.getDeviceName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeQueueByIncidentId(String incidentId, String deviceType) {
/* 146 */     IMap<String, DisQueueItem> queueItemMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.QueueItem);
/* 147 */     EntryObject e = (new PredicateBuilder()).getEntryObject();
/*     */     
/* 149 */     PredicateBuilder pb = e.get("incidentId").equal(incidentId).and((Predicate)e.get("deviceType").equal(deviceType));
/* 150 */     Collection<DisQueueItem> collection = queueItemMap.values((Predicate)pb);
/* 151 */     for (DisQueueItem t : collection) {
/* 152 */       queueItemMap.remove(t.getId());
/* 153 */       processeTopQueueItem(getTopPriorityByDeviceName(t.getDeviceName()), t.getDeviceName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisQueueItem removeFromQueueByUser(String deviceName) {
/* 165 */     IMap<String, DisQueueItem> queueItemMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.QueueItem);
/* 166 */     EntryObject e = (new PredicateBuilder()).getEntryObject();
/*     */ 
/*     */ 
/*     */     
/* 170 */     PredicateBuilder pb = e.get("deviceName").equal(deviceName).and((Predicate)e.get("priorityType").in(new Comparable[] { (Comparable)PriorityType.Manual, (Comparable)PriorityType.ManualOverride }));
/*     */     
/* 172 */     List<DisQueueItem> removeList = new ArrayList<>(queueItemMap.values((Predicate)pb));
/* 173 */     if (removeList.size() == 1) {
/* 174 */       DisQueueItem t = removeList.get(0);
/* 175 */       String id = t.getId();
/* 176 */       queueItemMap.lock(id);
/*     */       try {
/* 178 */         queueItemMap.remove(id);
/*     */       } finally {
/* 180 */         queueItemMap.unlock(id);
/*     */       } 
/* 182 */     } else if (removeList.size() >= 2) {
/* 183 */       DisQueueItem removeItem = null;
/* 184 */       for (DisQueueItem t : removeList) {
/* 185 */         if (removeItem == null) {
/* 186 */           removeItem = t; continue;
/*     */         } 
/* 188 */         if (removeItem.getPriorityType() != null && t
/* 189 */           .getPriorityType() != null && t
/* 190 */           .getPriorityType().getPriority() < removeItem.getPriorityType().getPriority()) {
/* 191 */           removeItem = t;
/*     */         }
/*     */       } 
/*     */       
/* 195 */       if (removeItem != null) {
/* 196 */         String id = removeItem.getId();
/* 197 */         queueItemMap.lock(id);
/*     */         try {
/* 199 */           queueItemMap.remove(id);
/*     */         } finally {
/* 201 */           queueItemMap.unlock(id);
/*     */         } 
/*     */       } 
/*     */     } 
/* 205 */     DisQueueItem topQueueItem = getTopPriorityByDeviceName(deviceName);
/* 206 */     processeTopQueueItem(topQueueItem, deviceName);
/* 207 */     return topQueueItem;
/*     */   }
/*     */   
/*     */   public abstract void processeTopQueueItem(DisQueueItem paramDisQueueItem, String paramString);
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\service\DisQueueManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */