/*    */ package com.hwacom.ngtms.c.dis.fm.service;
/*    */ 
/*    */ import com.google.common.collect.Ordering;
/*    */ import com.google.gwt.thirdparty.guava.common.collect.ComparisonChain;
/*    */ import com.hwacom.ngtms.c.dis.fm.model.DisQueueItem;
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.function.Predicate;
/*    */ import java.util.stream.Collectors;
/*    */ import org.springframework.context.annotation.Profile;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ @Service
/*    */ @Profile({"dev"})
/*    */ public class DisQueueItemPredicateImpl
/*    */   implements DisQueueItemPredicate {
/*    */   public List<DisQueueItem> getSortedQueue(List<DisQueueItem> queueItems, String deviceType, String deviceName) {
/*    */     Predicate<DisQueueItem> predicate;
/* 20 */     if (queueItems == null || queueItems.size() == 0) {
/* 21 */       return Collections.emptyList();
/*    */     }
/*    */     
/* 24 */     if (deviceType == null && deviceName == null) {
/* 25 */       return Collections.emptyList();
/*    */     }
/*    */ 
/*    */     
/* 29 */     if (deviceName != null) {
/* 30 */       predicate = (queueItem -> queueItem.getDeviceName().equals(paramString));
/*    */     } else {
/* 32 */       predicate = (queueItem -> queueItem.getDeviceType().equals(paramString));
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 49 */     List<DisQueueItem> sortedQueueItems = (List<DisQueueItem>)queueItems.stream().filter(predicate).sorted((o1, o2) -> ComparisonChain.start().compare(o1.getPriorityType().getPriority(), o2.getPriorityType().getPriority()).compare(o1.getRspPriority(), o2.getRspPriority(), (Comparator)Ordering.natural().nullsLast()).compare(o1.getSubPriority(), o2.getSubPriority(), (Comparator)Ordering.natural().nullsLast()).result()).collect(Collectors.toList());
/* 50 */     return sortedQueueItems;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\service\DisQueueItemPredicateImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */