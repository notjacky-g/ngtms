/*    */ package com.hwacom.ngtms.base.oom;
/*    */ 
/*    */ import java.lang.management.ManagementFactory;
/*    */ import java.lang.management.MemoryMXBean;
/*    */ import java.lang.management.MemoryPoolMXBean;
/*    */ import java.lang.management.MemoryType;
/*    */ import java.lang.management.MemoryUsage;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import javax.management.Notification;
/*    */ import javax.management.NotificationEmitter;
/*    */ import javax.management.NotificationListener;
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
/*    */ public class MemoryWarningService
/*    */ {
/* 27 */   private final Collection<MemoryWarningListener> listeners = new ArrayList();
/*    */   
/* 29 */   private static final MemoryPoolMXBean tenuredGenPool = ;
/*    */   
/*    */   public MemoryWarningService() {
/* 32 */     MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
/* 33 */     NotificationEmitter emitter = (NotificationEmitter)mbean;
/* 34 */     emitter.addNotificationListener(new NotificationListener() {
/*    */       public void handleNotification(Notification n, Object hb) { long maxMemory;
/*    */         long usedMemory;
/* 37 */         if (n.getType().equals("java.management.memory.threshold.exceeded")) {
/* 38 */           maxMemory = MemoryWarningService.tenuredGenPool.getUsage().getMax();
/* 39 */           usedMemory = MemoryWarningService.tenuredGenPool.getUsage().getUsed();
/* 40 */           for (MemoryWarningListener listener : MemoryWarningService.this.listeners)
/* 41 */             listener.memoryUsageLow(usedMemory, maxMemory); } } }, null, null);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean addListener(MemoryWarningListener listener)
/*    */   {
/* 51 */     return this.listeners.add(listener);
/*    */   }
/*    */   
/*    */   public boolean removeListener(MemoryWarningListener listener) {
/* 55 */     return this.listeners.remove(listener);
/*    */   }
/*    */   
/*    */   public void setPercentageUsageThreshold(double percentage) {
/* 59 */     if ((percentage <= 0.0D) || (percentage > 1.0D)) {
/* 60 */       throw new IllegalArgumentException("Percentage not in range");
/*    */     }
/* 62 */     long maxMemory = tenuredGenPool.getUsage().getMax();
/* 63 */     long warningThreshold = (maxMemory * percentage);
/* 64 */     tenuredGenPool.setUsageThreshold(warningThreshold);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private static MemoryPoolMXBean findTenuredGenPool()
/*    */   {
/* 72 */     for (MemoryPoolMXBean pool : )
/*    */     {
/*    */ 
/* 75 */       if ((pool.getType() == MemoryType.HEAP) && (pool.isUsageThresholdSupported())) {
/* 76 */         return pool;
/*    */       }
/*    */     }
/* 79 */     throw new AssertionError("Could not find tenured space");
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\oom\MemoryWarningService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */