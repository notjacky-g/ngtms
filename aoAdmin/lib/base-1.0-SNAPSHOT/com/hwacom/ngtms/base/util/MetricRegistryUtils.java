/*    */ package com.hwacom.ngtms.base.util;
/*    */ 
/*    */ import com.codahale.metrics.Counter;
/*    */ import com.codahale.metrics.Metric;
/*    */ import com.codahale.metrics.MetricRegistry;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MetricRegistryUtils
/*    */ {
/* 15 */   private static final MetricRegistry metrics = new MetricRegistry();
/*    */ 
/*    */ 
/*    */   
/*    */   public static MetricRegistry get() {
/* 20 */     return metrics;
/*    */   }
/*    */   
/*    */   public static Map<String, Metric> getMetrics() {
/* 24 */     return metrics.getMetrics();
/*    */   }
/*    */   
/*    */   public static Counter counterOfActiveSessions() {
/* 28 */     return metrics.counter("web.sessions.active.count");
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\bas\\util\MetricRegistryUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */