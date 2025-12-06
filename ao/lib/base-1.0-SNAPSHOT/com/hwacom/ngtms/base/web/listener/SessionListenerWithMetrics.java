/*    */ package com.hwacom.ngtms.base.web.listener;
/*    */ 
/*    */ import com.codahale.metrics.Counter;
/*    */ import com.codahale.metrics.MetricRegistry;
/*    */ import com.codahale.metrics.Slf4jReporter;
/*    */ import com.codahale.metrics.Slf4jReporter.Builder;
/*    */ import com.hwacom.ngtms.base.util.MetricRegistryUtils;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import javax.servlet.http.HttpSessionEvent;
/*    */ import javax.servlet.http.HttpSessionListener;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SessionListenerWithMetrics
/*    */   implements HttpSessionListener
/*    */ {
/* 21 */   private static Logger logger = LoggerFactory.getLogger(SessionListenerWithMetrics.class);
/*    */   
/*    */   private final AtomicInteger activeSessions;
/*    */   private final Counter counterOfActiveSessions;
/*    */   private Integer maxInactiveInterval;
/*    */   
/*    */   static
/*    */   {
/* 29 */     Slf4jReporter reporter = Slf4jReporter.forRegistry(MetricRegistryUtils.get()).outputTo(logger).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build();
/* 30 */     reporter.start(5L, TimeUnit.MINUTES);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public SessionListenerWithMetrics()
/*    */   {
/* 40 */     this.activeSessions = new AtomicInteger();
/* 41 */     this.counterOfActiveSessions = MetricRegistryUtils.get().counter("web.sessions.active.count");
/*    */   }
/*    */   
/*    */   public SessionListenerWithMetrics(Integer maxInactiveInterval) {
/* 45 */     this();
/* 46 */     this.maxInactiveInterval = maxInactiveInterval;
/*    */   }
/*    */   
/*    */   public final int getTotalActiveSession()
/*    */   {
/* 51 */     return this.activeSessions.get();
/*    */   }
/*    */   
/*    */   public final void sessionCreated(HttpSessionEvent event)
/*    */   {
/* 56 */     this.activeSessions.incrementAndGet();
/* 57 */     this.counterOfActiveSessions.inc();
/* 58 */     if (this.maxInactiveInterval != null) {
/* 59 */       event.getSession().setMaxInactiveInterval(this.maxInactiveInterval.intValue());
/*    */     }
/*    */   }
/*    */   
/*    */   public final void sessionDestroyed(HttpSessionEvent event)
/*    */   {
/* 65 */     this.activeSessions.decrementAndGet();
/* 66 */     this.counterOfActiveSessions.dec();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\web\listener\SessionListenerWithMetrics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */