/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import java.util.concurrent.ScheduledExecutorService;
/*    */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.annotation.PreDestroy;
/*    */ import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
/*    */ import org.kie.api.event.process.DefaultProcessEventListener;
/*    */ import org.kie.api.event.process.ProcessEventListener;
/*    */ import org.kie.api.event.process.ProcessStartedEvent;
/*    */ import org.kie.api.runtime.KieContainer;
/*    */ import org.kie.api.runtime.KieSession;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
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
/*    */ public class KieSessionPool
/*    */ {
/*    */   private KieContainer kieContainer;
/*    */   private GenericKeyedObjectPool<String, KieSession> pool;
/*    */   private ScheduledExecutorService schedule;
/* 31 */   private static final Logger logger = LoggerFactory.getLogger(KieSessionPool.class);
/*    */   
/*    */   public enum KieSessionName {
/* 34 */     defaultKsession;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   private int poolSize = 100;
/*    */   
/*    */   public KieSessionPool(KieContainer kieContainer, int poolSize) {
/* 43 */     this.kieContainer = kieContainer;
/* 44 */     if (poolSize > 0) this.poolSize = poolSize;
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @PostConstruct
/*    */   public void postConstruct() {
/* 53 */     KiePoolableSessionFactory factory = new KiePoolableSessionFactory(this.kieContainer);
/* 54 */     this.pool = new GenericKeyedObjectPool(factory);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 60 */     this.pool.setTestOnReturn(true);
/* 61 */     this.pool.setMaxTotalPerKey(this.poolSize);
/* 62 */     this.schedule = new ScheduledThreadPoolExecutor(1);
/* 63 */     this.schedule.schedule(this::cleanPool, 3L, TimeUnit.MINUTES);
/*    */   }
/*    */   
/*    */   private void cleanPool() {
/* 67 */     this.pool.clearOldest();
/*    */   }
/*    */   
/*    */   @PreDestroy
/*    */   public void preDestroy() {
/* 72 */     this.kieContainer.dispose();
/* 73 */     this.pool.clear();
/* 74 */     this.schedule.shutdown();
/*    */   }
/*    */   
/*    */   public int getPoolSize() {
/* 78 */     return this.poolSize;
/*    */   }
/*    */   
/*    */   public void setPoolSize(int poolSize) {
/* 82 */     this.poolSize = poolSize;
/* 83 */     this.pool.setMaxTotalPerKey(poolSize);
/*    */   }
/*    */   
/*    */   public KieSession getSession() throws Exception {
/* 87 */     KieSession kieSession = (KieSession)this.pool.borrowObject(KieSessionName.defaultKsession.toString());
/* 88 */     kieSession.addEventListener((ProcessEventListener)new DefaultProcessEventListener()
/*    */         {
/*    */           public void beforeProcessStarted(ProcessStartedEvent event) {
/* 91 */             KieSessionPool.logger.info("{}", Long.valueOf(event.getProcessInstance().getId()));
/*    */           }
/*    */         });
/* 94 */     return kieSession;
/*    */   }
/*    */   
/*    */   public void returnObject(KieSession kieSession) {
/* 98 */     if (kieSession == null)
/* 99 */       return;  this.pool.returnObject(KieSessionName.defaultKsession.toString(), kieSession);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\KieSessionPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */