/*     */ package com.hwacom.ngtms.base.util;
/*     */ 
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
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
/*     */ public class StripedExecutor
/*     */ {
/*  23 */   private AtomicBoolean alive = new AtomicBoolean(true);
/*     */   
/*     */   private int threadNo;
/*     */   
/*     */   private ThreadPoolExecutor[] threadPoolExecutors;
/*     */   
/*     */ 
/*     */   public StripedExecutor(int threadNo, int corePoolSize, String threadNamePrefix)
/*     */   {
/*  32 */     if (threadNo <= 0) throw new IllegalArgumentException("threadNo must be greater than zero");
/*  33 */     this.threadNo = threadNo;
/*  34 */     if (corePoolSize > 1) { corePoolSize = 1;
/*  35 */     } else if (corePoolSize < 0) corePoolSize = 0;
/*  36 */     this.threadPoolExecutors = new ThreadPoolExecutor[threadNo];
/*  37 */     for (int i = 0; i < threadNo; i++)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  45 */       this.threadPoolExecutors[i] = new ThreadPoolExecutor(corePoolSize, 1, 65L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + i).build());
/*     */     }
/*     */   }
/*     */   
/*     */   public void shutdown() {
/*  50 */     if (this.alive.compareAndSet(true, false)) {
/*  51 */       for (int i = 0; i < this.threadNo; i++) {
/*  52 */         this.threadPoolExecutors[i].shutdownNow();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getQueueSize()
/*     */   {
/*  62 */     int size = 0;
/*  63 */     for (ThreadPoolExecutor tp : this.threadPoolExecutors) {
/*  64 */       size += tp.getQueue().size();
/*     */     }
/*  66 */     return size;
/*     */   }
/*     */   
/*     */   public void clearQueue() {
/*  70 */     for (ThreadPoolExecutor tp : this.threadPoolExecutors) {
/*  71 */       tp.getQueue().clear();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isLive() {
/*  76 */     return this.alive.get();
/*     */   }
/*     */   
/*     */   public int getThreadNo() {
/*  80 */     return this.threadNo;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void submit(Object key, Runnable command)
/*     */   {
/*  89 */     if (!this.alive.get()) return;
/*     */     int index;
/*  91 */     int index; if (key != null) index = Math.abs(key.hashCode()) % this.threadNo; else
/*  92 */       index = 0;
/*  93 */     this.threadPoolExecutors[index].submit(command);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ExecutorService getExecutorService(Object key)
/*     */   {
/*     */     int index;
/*     */     
/*     */     int index;
/*     */     
/* 104 */     if (key != null) index = Math.abs(key.hashCode()) % this.threadNo; else
/* 105 */       index = 0;
/* 106 */     return this.threadPoolExecutors[index];
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\util\StripedExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */