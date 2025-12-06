/*     */ package com.hwacom.ngtms.base.oom;
/*     */ 
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.ThreadInfo;
/*     */ import java.lang.management.ThreadMXBean;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Component
/*     */ public class MemoryWarningListenerImpl
/*     */   implements MemoryWarningListener
/*     */ {
/*  28 */   private static Logger logger = LoggerFactory.getLogger(MemoryWarningListenerImpl.class);
/*     */   
/*     */   public void memoryUsageLow(long usedMemory, long maxMemory) {
/*  31 */     logger.info("Memory usage low!!!");
/*  32 */     double percentageUsed = usedMemory / maxMemory;
/*  33 */     logger.info("percentageUsed = " + percentageUsed);
/*  34 */     dumpStacks();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void dumpStacks()
/*     */   {
/*  43 */     ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
/*  44 */     ThreadInfo[] threadInfos = mxBean.getThreadInfo(mxBean.getAllThreadIds(), 0);
/*  45 */     Map<Long, ThreadInfo> threadInfoMap = new HashMap();
/*  46 */     for (ThreadInfo threadInfo : threadInfos) {
/*  47 */       threadInfoMap.put(Long.valueOf(threadInfo.getThreadId()), threadInfo);
/*     */     }
/*     */     
/*  50 */     String home = System.getProperty("user.home");
/*  51 */     File dumpFile = new File(home, "MemoryWarning.dump");
/*  52 */     BufferedWriter writer = null;
/*     */     try {
/*  54 */       writer = new BufferedWriter(new FileWriter(dumpFile));
/*  55 */       dumpTraces(mxBean, threadInfoMap, writer);
/*     */     } catch (IOException e) {
/*  57 */       throw new IllegalStateException("An exception occurred while writing the thread dump");
/*     */     } finally {
/*  59 */       IOUtils.closeQuietly(writer);
/*     */     }
/*     */   }
/*     */   
/*     */   private void dumpTraces(ThreadMXBean mxBean, Map<Long, ThreadInfo> threadInfoMap, Writer writer) throws IOException
/*     */   {
/*  65 */     Map<Thread, StackTraceElement[]> stacks = Thread.getAllStackTraces();
/*  66 */     writer.write("Dump of " + stacks
/*     */     
/*  68 */       .size() + " thread at " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z")
/*     */       
/*     */ 
/*  71 */       .format(new Date(System.currentTimeMillis())) + "\n\n");
/*     */     
/*  73 */     for (Map.Entry<Thread, StackTraceElement[]> entry : stacks.entrySet()) {
/*  74 */       Thread thread = (Thread)entry.getKey();
/*  75 */       writer.write("\"" + thread
/*     */       
/*  77 */         .getName() + "\" prio=" + thread
/*     */         
/*  79 */         .getPriority() + " tid=" + thread
/*     */         
/*  81 */         .getId() + " " + thread
/*     */         
/*  83 */         .getState() + " " + (thread
/*     */         
/*  85 */         .isDaemon() ? "deamon" : "worker") + "\n");
/*     */       
/*  87 */       ThreadInfo threadInfo = (ThreadInfo)threadInfoMap.get(Long.valueOf(thread.getId()));
/*  88 */       if (threadInfo != null) {
/*  89 */         writer.write("    native=" + threadInfo
/*     */         
/*  91 */           .isInNative() + ", suspended=" + threadInfo
/*     */           
/*  93 */           .isSuspended() + ", block=" + threadInfo
/*     */           
/*  95 */           .getBlockedCount() + ", wait=" + threadInfo
/*     */           
/*  97 */           .getWaitedCount() + "\n");
/*     */         
/*  99 */         writer.write("    lock=" + threadInfo
/*     */         
/* 101 */           .getLockName() + " owned by " + threadInfo
/*     */           
/* 103 */           .getLockOwnerName() + " (" + threadInfo
/*     */           
/* 105 */           .getLockOwnerId() + "), cpu=" + mxBean
/*     */           
/* 107 */           .getThreadCpuTime(threadInfo.getThreadId()) / 1000000L + ", user=" + mxBean
/*     */           
/* 109 */           .getThreadUserTime(threadInfo.getThreadId()) / 1000000L + "\n");
/*     */       }
/*     */       
/* 112 */       for (StackTraceElement element : (StackTraceElement[])entry.getValue()) {
/* 113 */         writer.write("        ");
/* 114 */         writer.write(element.toString());
/* 115 */         writer.write("\n");
/*     */       }
/* 117 */       writer.write("\n");
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\oom\MemoryWarningListenerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */