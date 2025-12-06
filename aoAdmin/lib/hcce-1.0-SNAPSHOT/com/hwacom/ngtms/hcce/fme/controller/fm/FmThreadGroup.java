/*    */ package com.hwacom.ngtms.hcce.fme.controller.fm;
/*    */ 
/*    */ import java.util.concurrent.ThreadFactory;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FmThreadGroup
/*    */ {
/* 15 */   private static Logger logger = LoggerFactory.getLogger(FmThreadGroup.class);
/*    */   private ThreadGroup fmThreadGroup;
/*    */   private ThreadFactory threadRegisterFactory;
/* 18 */   private AtomicInteger counter = new AtomicInteger();
/*    */   
/*    */   public FmThreadGroup(final String fmName, ThreadGroup parent) {
/* 21 */     this.fmThreadGroup = new ThreadGroup(parent, "FmThreadGroup");
/* 22 */     this.threadRegisterFactory = new ThreadFactory()
/*    */       {
/*    */         public Thread newThread(Runnable r)
/*    */         {
/* 26 */           Thread thread = new Thread(FmThreadGroup.this.fmThreadGroup, r);
/* 27 */           thread.setDaemon(Boolean.TRUE.booleanValue());
/* 28 */           thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
/*    */               {
/*    */                 public void uncaughtException(Thread t, Throwable e)
/*    */                 {
/* 32 */                   FmThreadGroup.logger.error("Catch exception of FM thread: " + t.getName(), e);
/*    */                 }
/*    */               });
/* 35 */           thread.setName(fmName + "-thread-" + FmThreadGroup.this.counter.incrementAndGet());
/* 36 */           return thread;
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   protected ThreadFactory getFmThreadFactory() {
/* 42 */     return this.threadRegisterFactory;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Thread createFmThread(Runnable r) {
/* 51 */     return this.threadRegisterFactory.newThread(r);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void stopFmThreadGroup() {
/* 56 */     this.fmThreadGroup.interrupt();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void destroyFmThreadGroup() {
/* 61 */     for (int i = 0; i < 20; i++) {
/* 62 */       if (this.fmThreadGroup.activeCount() == 0) {
/* 63 */         this.fmThreadGroup.destroy();
/*    */         return;
/*    */       } 
/*    */       try {
/* 67 */         Thread.sleep(100L);
/* 68 */       } catch (InterruptedException interruptedException) {}
/*    */     } 
/*    */     
/* 71 */     Thread[] list = new Thread[this.fmThreadGroup.activeCount()];
/* 72 */     this.fmThreadGroup.enumerate(list);
/* 73 */     for (Thread t : list)
/* 74 */       logger.error("Unable to stop the Fm thread: " + t.getName()); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\controller\fm\FmThreadGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */