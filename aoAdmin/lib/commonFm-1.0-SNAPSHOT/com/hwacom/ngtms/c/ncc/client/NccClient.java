/*      */ package com.hwacom.ngtms.c.ncc.client;
/*      */ 
/*      */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*      */ import com.hazelcast.core.IMap;
/*      */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*      */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*      */ import com.hwacom.ngtms.base.rmi.RmiCallbackUtils;
/*      */ import com.hwacom.ngtms.base.rmi.RmiUtils;
/*      */ import com.hwacom.ngtms.base.sysperflog.service.SysPerfLog;
/*      */ import com.hwacom.ngtms.base.sysperflog.service.SysPerfLogService;
/*      */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*      */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*      */ import com.hwacom.ngtms.c.fm.model.MfccConfig;
/*      */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*      */ import com.hwacom.ngtms.ncc.remote.NccCallback;
/*      */ import com.hwacom.ngtms.ncc.remote.NccRemote;
/*      */ import com.hwacom.ngtms.ncc.remote.TcResponse;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.LinkedBlockingQueue;
/*      */ import java.util.concurrent.ThreadPoolExecutor;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.concurrent.atomic.AtomicBoolean;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import javax.annotation.PostConstruct;
/*      */ import javax.annotation.PreDestroy;
/*      */ import javax.annotation.Resource;
/*      */ import org.slf4j.Logger;
/*      */ import org.slf4j.LoggerFactory;
/*      */ import org.springframework.beans.factory.annotation.Autowired;
/*      */ import org.springframework.core.env.Environment;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class NccClient
/*      */ {
/*   57 */   private static Logger logger = LoggerFactory.getLogger(NccClient.class);
/*      */   public static final long DefaultSendAsyncRequestTimeout = 21000L;
/*      */   public static final long DefaultSendAsyncMultipleRequestsTimeout = 24000L;
/*      */   public static final long DefaultAsyncRequest2MulipleTcTimeout = 22000L;
/*      */   public static final int MaxAPoolSize = 64;
/*      */   public static final int MaxBPoolSize = 128;
/*      */   private NccCallbackHandler nccCallbackHandler;
/*      */   private NccCallback nccCallback;
/*   65 */   private ConcurrentHashMap<String, TcRequestSession> tcRequestSessionMap = new ConcurrentHashMap<>();
/*      */   
/*   67 */   private AtomicLong atomicSessionId = new AtomicLong();
/*   68 */   private AtomicBoolean running = new AtomicBoolean();
/*      */   private ThreadPoolExecutor onResponseExecutorServiceA;
/*      */   private ThreadPoolExecutor onResponseExecutorServiceB;
/*   71 */   private AtomicInteger waitingOnResponseNoA = new AtomicInteger();
/*   72 */   private AtomicInteger waitingOnResponseNoB = new AtomicInteger();
/*      */   private Thread expiredSessionCleanThread;
/*      */   @Resource
/*      */   private Environment environment;
/*      */   @Autowired
/*      */   private HcceEnv hcceEnv;
/*      */   @Autowired
/*      */   private SysPerfLogService sysPerfLogService;
/*      */   private int nccRmiPort;
/*      */   
/*      */   private static class TcRequestSession
/*      */   {
/*      */     TcResponseCallback tcResponseCallback;
/*      */     long timeout;
/*      */     boolean[] responseFlags;
/*      */     int responseCounter;
/*      */     String deviceName;
/*   89 */     AtomicBoolean discarded = new AtomicBoolean(); String[] deviceNames; String reqSessionId; String sessionId; boolean useThreadPool;
/*      */     private TcRequestSession() {} }
/*      */   
/*      */   @PostConstruct
/*      */   public void start() {
/*   94 */     if (this.running.compareAndSet(false, true)) {
/*   95 */       this.nccRmiPort = ((Integer)this.environment.getProperty("ncc.rmi.registry.port", Integer.class, Integer.valueOf(4321))).intValue();
/*   96 */       this.expiredSessionCleanThread = new Thread(this.expiredSessionCleaner, "NccClient expiredSessionCleanThread");
/*      */       
/*   98 */       this.expiredSessionCleanThread.setDaemon(true);
/*   99 */       this.expiredSessionCleanThread.start();
/*  100 */       this.nccCallbackHandler = new NccCallbackHandler();
/*  101 */       this.nccCallback = (NccCallback)RmiCallbackUtils.export(this.nccCallbackHandler, NccCallback.class);
/*      */ 
/*      */       
/*  104 */       this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  111 */         .onResponseExecutorServiceA = new ThreadPoolExecutor(8, 64, 65L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), (new ThreadFactoryBuilder()).setNameFormat("NccClient OnResponse A %d").build());
/*      */       
/*  113 */       this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  120 */         .onResponseExecutorServiceB = new ThreadPoolExecutor(32, 128, 65L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), (new ThreadFactoryBuilder()).setNameFormat("NccClient OnResponse B %d").build());
/*  121 */       this.onResponseExecutorServiceB.getQueue().clear();
/*      */     } 
/*      */   }
/*      */   
/*      */   @PreDestroy
/*      */   public void stop() {
/*  127 */     if (this.running.compareAndSet(true, false)) {
/*  128 */       RmiCallbackUtils.unexport(this.nccCallbackHandler);
/*  129 */       this.expiredSessionCleanThread.interrupt();
/*  130 */       this.onResponseExecutorServiceA.shutdown();
/*  131 */       this.onResponseExecutorServiceB.shutdown();
/*      */     } 
/*      */   }
/*      */   
/*      */   public int clearOnResponseExecutorServiceA() {
/*  136 */     ArrayList<Runnable> list = new ArrayList<>();
/*  137 */     int count = this.onResponseExecutorServiceA.getQueue().drainTo(list);
/*  138 */     this.waitingOnResponseNoA.addAndGet(-count);
/*  139 */     return this.onResponseExecutorServiceA.getQueue().size();
/*      */   }
/*      */   
/*      */   public int clearOnResponseExecutorServiceB() {
/*  143 */     ArrayList<Runnable> list = new ArrayList<>();
/*  144 */     int count = this.onResponseExecutorServiceB.getQueue().drainTo(list);
/*  145 */     this.waitingOnResponseNoB.addAndGet(-count);
/*  146 */     return this.onResponseExecutorServiceB.getQueue().size();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  151 */   private Runnable expiredSessionCleaner = new Runnable()
/*      */     {
/*      */       private long lastWriteSysPerfLogTime;
/*      */       
/*      */       private long accExeTime;
/*      */       private int exeTimes;
/*      */       
/*      */       public void run() {
/*  159 */         this.lastWriteSysPerfLogTime = System.currentTimeMillis();
/*  160 */         long t1 = 0L, t2 = 0L;
/*  161 */         while (NccClient.this.running.get()) {
/*      */           try {
/*  163 */             t1 = 0L;
/*      */             
/*  165 */             TimeUnit.MILLISECONDS.sleep(1000L);
/*  166 */             t1 = System.nanoTime();
/*  167 */             long time = System.currentTimeMillis();
/*  168 */             Enumeration<String> keys = NccClient.this.tcRequestSessionMap.keys();
/*  169 */             while (keys.hasMoreElements()) {
/*  170 */               String sessionId = keys.nextElement();
/*  171 */               final NccClient.TcRequestSession tcRequestSession = (NccClient.TcRequestSession)NccClient.this.tcRequestSessionMap.get(sessionId);
/*  172 */               if (tcRequestSession == null || 
/*  173 */                 tcRequestSession.timeout >= time)
/*  174 */                 continue;  synchronized (tcRequestSession) {
/*  175 */                 if (tcRequestSession.timeout < time && 
/*  176 */                   tcRequestSession.discarded.compareAndSet(false, true)) {
/*  177 */                   int logCount, i; final TcResponse tcResponse; NccClient.this.tcRequestSessionMap.remove(sessionId);
/*  178 */                   NccClient.SessionType sessionType = NccClient.SessionType.fromChar(sessionId.charAt(0));
/*  179 */                   switch (sessionType) {
/*      */                     case MultipleTcSingleRequest:
/*  181 */                       logCount = 0;
/*  182 */                       for (i = 0; i < tcRequestSession.responseFlags.length; i++) {
/*  183 */                         if (!tcRequestSession.responseFlags[i]) {
/*  184 */                           final TcResponse tcResponse = new TcResponse();
/*  185 */                           tcResponse1.setResult(TcResponse.Result.TIMEOUT);
/*  186 */                           tcResponse1.setErrorCode(TcResponse.ErrorCode.TIMEOUT);
/*  187 */                           tcResponse1.setErrorReason("NccClient waiting response timeout");
/*  188 */                           final String deviceName = tcRequestSession.deviceNames[i];
/*  189 */                           if (logCount < 3) {
/*  190 */                             NccClient.logger.warn("Does not receive any response from NCC, sessionId:{}, reqSessionId: {}, deviceName: {} ", new Object[] { tcRequestSession.sessionId, tcRequestSession.reqSessionId, deviceName });
/*      */ 
/*      */ 
/*      */ 
/*      */                             
/*  195 */                             logCount++;
/*      */                           } 
/*  197 */                           if (tcRequestSession.useThreadPool) {
/*  198 */                             NccClient.this.waitingOnResponseNoB.incrementAndGet();
/*  199 */                             NccClient.this.onResponseExecutorServiceB.submit(new Runnable()
/*      */                                 {
/*      */                                   public void run()
/*      */                                   {
/*      */                                     try {
/*  204 */                                       tcRequestSession.tcResponseCallback.onResponse(tcRequestSession.reqSessionId, deviceName, tcResponse);
/*      */ 
/*      */                                     
/*      */                                     }
/*  208 */                                     catch (Exception ex) {
/*  209 */                                       NccClient.logger.error("tcResponseCallback.onResponse failed, sessionId:{}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { this.val$tcRequestSession.sessionId, this.val$tcRequestSession.reqSessionId, this.val$deviceName, this.val$tcResponse, ex });
/*      */ 
/*      */                                     
/*      */                                     }
/*      */                                     finally {
/*      */ 
/*      */ 
/*      */                                       
/*  217 */                                       NccClient.this.waitingOnResponseNoB.decrementAndGet();
/*      */                                     } 
/*      */                                   }
/*      */                                 });
/*      */                           } else {
/*      */                             try {
/*  223 */                               tcRequestSession.tcResponseCallback.onResponse(tcRequestSession.reqSessionId, deviceName, tcResponse1);
/*      */                             }
/*  225 */                             catch (Exception ex) {
/*  226 */                               NccClient.logger.error("tcResponseCallback.onResponse failed, sessionId:{}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { tcRequestSession.sessionId, tcRequestSession.reqSessionId, deviceName, tcResponse1, ex });
/*      */                             } 
/*      */                           } 
/*      */                         } 
/*      */                       } 
/*      */                       break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*      */                     case SingleTcMultipleRequest:
/*  239 */                       for (i = 0; i < tcRequestSession.responseFlags.length; i++) {
/*  240 */                         if (!tcRequestSession.responseFlags[i]) {
/*  241 */                           final TcResponse tcResponse = new TcResponse();
/*  242 */                           tcResponse1.setResult(TcResponse.Result.TIMEOUT);
/*  243 */                           tcResponse1.setErrorCode(TcResponse.ErrorCode.TIMEOUT);
/*  244 */                           tcResponse1.setErrorReason("NccClient waiting response timeout");
/*  245 */                           tcResponse1.setCmdIndex(i);
/*  246 */                           if (tcRequestSession.useThreadPool) {
/*  247 */                             NccClient.this.waitingOnResponseNoA.incrementAndGet();
/*  248 */                             NccClient.this.onResponseExecutorServiceA.submit(new Runnable()
/*      */                                 {
/*      */                                   public void run()
/*      */                                   {
/*      */                                     try {
/*  253 */                                       tcRequestSession.tcResponseCallback.onResponse(tcRequestSession.reqSessionId, tcRequestSession.deviceName, tcResponse);
/*      */ 
/*      */                                     
/*      */                                     }
/*  257 */                                     catch (Exception ex) {
/*  258 */                                       NccClient.logger.error("tcResponseCallback.onResponse failed, sessionId:{}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { this.val$tcRequestSession.sessionId, this.val$tcRequestSession.reqSessionId, this.val$tcRequestSession.deviceName, this.val$tcResponse, ex });
/*      */ 
/*      */                                     
/*      */                                     }
/*      */                                     finally {
/*      */ 
/*      */ 
/*      */                                       
/*  266 */                                       NccClient.this.waitingOnResponseNoA.decrementAndGet();
/*      */                                     } 
/*      */                                   }
/*      */                                 });
/*      */                           } else {
/*      */                             try {
/*  272 */                               tcRequestSession.tcResponseCallback.onResponse(tcRequestSession.reqSessionId, tcRequestSession.deviceName, tcResponse1);
/*      */ 
/*      */                             
/*      */                             }
/*  276 */                             catch (Exception ex) {
/*  277 */                               NccClient.logger.error("tcResponseCallback.onResponse failed, sessionId:{}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { tcRequestSession.sessionId, tcRequestSession.reqSessionId, tcRequestSession.deviceName, tcResponse1, ex });
/*      */                             } 
/*      */                           } 
/*      */                         } 
/*      */                       } 
/*      */                       break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*      */                     case SingleTcSingleRequest:
/*  290 */                       tcResponse = new TcResponse();
/*  291 */                       tcResponse.setResult(TcResponse.Result.TIMEOUT);
/*  292 */                       tcResponse.setErrorCode(TcResponse.ErrorCode.TIMEOUT);
/*  293 */                       tcResponse.setErrorReason("NccClient waiting response timeout");
/*  294 */                       if (tcRequestSession.useThreadPool) {
/*  295 */                         NccClient.this.waitingOnResponseNoA.incrementAndGet();
/*  296 */                         NccClient.this.onResponseExecutorServiceA.submit(new Runnable()
/*      */                             {
/*      */                               public void run()
/*      */                               {
/*      */                                 try {
/*  301 */                                   tcRequestSession.tcResponseCallback.onResponse(tcRequestSession.reqSessionId, tcRequestSession.deviceName, tcResponse);
/*      */ 
/*      */                                 
/*      */                                 }
/*  305 */                                 catch (Exception ex) {
/*  306 */                                   NccClient.logger.error("tcResponseCallback.onResponse failed, sessionId:{}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { this.val$tcRequestSession.sessionId, this.val$tcRequestSession.reqSessionId, this.val$tcRequestSession.deviceName, this.val$tcResponse, ex });
/*      */ 
/*      */                                 
/*      */                                 }
/*      */                                 finally {
/*      */ 
/*      */ 
/*      */                                   
/*  314 */                                   NccClient.this.waitingOnResponseNoA.decrementAndGet();
/*      */                                 }  }
/*      */                             });
/*      */                         break;
/*      */                       } 
/*      */                       try {
/*  320 */                         tcRequestSession.tcResponseCallback.onResponse(tcRequestSession.reqSessionId, tcRequestSession.deviceName, tcResponse);
/*      */ 
/*      */                       
/*      */                       }
/*  324 */                       catch (Exception ex) {
/*  325 */                         NccClient.logger.error("tcResponseCallback.onResponse failed, sessionId:{}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { tcRequestSession.sessionId, tcRequestSession.reqSessionId, tcRequestSession.deviceName, tcResponse, ex });
/*      */                       } 
/*      */                       break;
/*      */                   } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 } 
/*      */               } 
/*      */             } 
/*  343 */           } catch (InterruptedException interruptedException) {
/*      */           
/*  345 */           } catch (Throwable t) {
/*  346 */             NccClient.logger.error("Failed to run expiredSessionCleaner", t);
/*      */           
/*      */           }
/*      */           finally {
/*      */             
/*  351 */             if (t1 != 0L) {
/*  352 */               t2 = System.nanoTime();
/*  353 */               this.accExeTime += t2 - t1;
/*  354 */               this.exeTimes++;
/*  355 */               long curTime = System.currentTimeMillis();
/*  356 */               if (curTime > this.lastWriteSysPerfLogTime + 60000L) {
/*  357 */                 int a = NccClient.this.waitingOnResponseNoA.get();
/*  358 */                 int b = NccClient.this.waitingOnResponseNoB.get();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  371 */                 SysPerfLog sysPerfLog = NccClient.this.sysPerfLogService.createSysPerfLog(NccClient.this.hcceEnv.getCurrentGroupName(), NccClient.this.hcceEnv.getNodeName() + ".NccClient.expiredSessionCleaner", curTime).addCounterRound("avExeTimePerLoop", this.accExeTime / 1000000.0D / this.exeTimes, 2).addCounter("ReqSessionMapSize", Integer.valueOf(NccClient.this.tcRequestSessionMap.size())).addCounter("WaitingOnResponseNoA", Integer.valueOf(a)).addCounter("WaitingOnResponseNoB", Integer.valueOf(b));
/*  372 */                 if (a > 320) {
/*  373 */                   sysPerfLog.issueAlarm(true);
/*  374 */                   sysPerfLog.setDescription("WaitingOnResponseNoA too high");
/*      */                 } 
/*  376 */                 if (b > 640) {
/*  377 */                   sysPerfLog.issueAlarm(true);
/*  378 */                   if (sysPerfLog.getDescription() != null)
/*  379 */                   { sysPerfLog.setDescription(sysPerfLog
/*  380 */                         .getDescription() + " and WaitingOnResponseNoB too high"); }
/*  381 */                   else { sysPerfLog.setDescription("WaitingOnResponseNoB too high"); }
/*      */                 
/*  383 */                 }  sysPerfLog.write();
/*  384 */                 this.lastWriteSysPerfLogTime = curTime;
/*  385 */                 this.accExeTime = 0L;
/*  386 */                 this.exeTimes = 0;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/*      */     };
/*      */   
/*      */   class NccCallbackHandler
/*      */     implements NccCallback {
/*      */     public void onResponse(final String sessionId, final String deviceName, final TcResponse tcResponse) {
/*  397 */       final NccClient.TcRequestSession tcRequestSession = (NccClient.TcRequestSession)NccClient.this.tcRequestSessionMap.get(sessionId);
/*  398 */       if (NccClient.logger.isTraceEnabled()) {
/*  399 */         NccClient.logger.trace("onResponse, sessionId:{}, deviceName:{}, tcResponse:{}", new Object[] { sessionId, deviceName, tcResponse });
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  404 */       if (tcRequestSession != null) {
/*  405 */         NccClient.SessionType sessionType = NccClient.SessionType.fromChar(sessionId.charAt(0));
/*  406 */         synchronized (tcRequestSession) {
/*  407 */           switch (sessionType) {
/*      */             case MultipleTcSingleRequest:
/*  409 */               if (!tcRequestSession.discarded.get()) {
/*  410 */                 if (tcRequestSession.useThreadPool) {
/*  411 */                   NccClient.this.waitingOnResponseNoB.incrementAndGet();
/*  412 */                   NccClient.this.onResponseExecutorServiceB.submit(new Runnable()
/*      */                       {
/*      */                         public void run()
/*      */                         {
/*      */                           try {
/*  417 */                             tcRequestSession.tcResponseCallback.onResponse(tcRequestSession.reqSessionId, deviceName, tcResponse);
/*      */                           }
/*  419 */                           catch (Exception ex) {
/*  420 */                             NccClient.logger.error("tcResponseCallback.onResponse failed, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { this.val$sessionId, this.val$tcRequestSession.reqSessionId, this.val$deviceName, this.val$tcResponse, ex });
/*      */ 
/*      */                           
/*      */                           }
/*      */                           finally {
/*      */ 
/*      */ 
/*      */                             
/*  428 */                             NccClient.this.waitingOnResponseNoB.decrementAndGet();
/*      */                           } 
/*      */                         }
/*      */                       });
/*      */                 } else {
/*      */                   try {
/*  434 */                     tcRequestSession.tcResponseCallback.onResponse(tcRequestSession.reqSessionId, deviceName, tcResponse);
/*      */                   }
/*  436 */                   catch (Exception ex) {
/*  437 */                     NccClient.logger.error("tcResponseCallback.onResponse failed, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { sessionId, tcRequestSession.reqSessionId, deviceName, tcResponse, ex });
/*      */                   } 
/*      */                 } 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 int i;
/*      */ 
/*      */ 
/*      */                 
/*  448 */                 for (i = 0; i < tcRequestSession.deviceNames.length; i++) {
/*  449 */                   String d = tcRequestSession.deviceNames[i];
/*      */                   
/*  451 */                   if (!tcRequestSession.responseFlags[i] && d.equals(deviceName)) {
/*  452 */                     tcRequestSession.responseFlags[i] = true;
/*      */                     break;
/*      */                   } 
/*      */                 } 
/*  456 */                 if (i < tcRequestSession.deviceNames.length) {
/*  457 */                   tcRequestSession.responseCounter++;
/*  458 */                   if (tcRequestSession.responseCounter >= tcRequestSession.responseFlags.length) {
/*  459 */                     NccClient.this.tcRequestSessionMap.remove(sessionId);
/*  460 */                     tcRequestSession.discarded.set(true);
/*      */                   }  break;
/*      */                 } 
/*  463 */                 NccClient.logger.error("DeviceName not found: {}", deviceName);
/*      */                 break;
/*      */               } 
/*  466 */               NccClient.logger.warn("tcRequestSession has been discarded, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { sessionId, tcRequestSession.reqSessionId, deviceName, tcResponse });
/*      */               break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             case SingleTcMultipleRequest:
/*  475 */               if (!tcRequestSession.discarded.get()) {
/*  476 */                 if (tcRequestSession.useThreadPool) {
/*  477 */                   NccClient.this.waitingOnResponseNoA.incrementAndGet();
/*  478 */                   NccClient.this.onResponseExecutorServiceA.submit(new Runnable()
/*      */                       {
/*      */                         public void run()
/*      */                         {
/*      */                           try {
/*  483 */                             tcRequestSession.tcResponseCallback.onResponse(tcRequestSession.reqSessionId, deviceName, tcResponse);
/*      */                           }
/*  485 */                           catch (Exception ex) {
/*  486 */                             NccClient.logger.error("tcResponseCallback.onResponse failed, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { this.val$sessionId, this.val$tcRequestSession.reqSessionId, this.val$deviceName, this.val$tcResponse, ex });
/*      */ 
/*      */                           
/*      */                           }
/*      */                           finally {
/*      */ 
/*      */ 
/*      */                             
/*  494 */                             NccClient.this.waitingOnResponseNoA.decrementAndGet();
/*      */                           } 
/*      */                         }
/*      */                       });
/*      */                 } else {
/*      */                   try {
/*  500 */                     tcRequestSession.tcResponseCallback.onResponse(tcRequestSession.reqSessionId, deviceName, tcResponse);
/*      */                   }
/*  502 */                   catch (Exception ex) {
/*  503 */                     NccClient.logger.error("tcResponseCallback.onResponse failed, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { sessionId, tcRequestSession.reqSessionId, deviceName, tcResponse, ex });
/*      */                   } 
/*      */                 } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  513 */                 tcRequestSession.responseFlags[tcResponse.getCmdIndex()] = true;
/*  514 */                 tcRequestSession.responseCounter++;
/*  515 */                 if (tcRequestSession.responseCounter >= tcRequestSession.responseFlags.length) {
/*  516 */                   NccClient.this.tcRequestSessionMap.remove(sessionId);
/*  517 */                   tcRequestSession.discarded.set(true);
/*      */                 }  break;
/*      */               } 
/*  520 */               NccClient.logger.warn("tcRequestSession has been discarded, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { sessionId, tcRequestSession.reqSessionId, deviceName, tcResponse });
/*      */               break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             case SingleTcSingleRequest:
/*  529 */               if (tcRequestSession.discarded.compareAndSet(false, true)) {
/*  530 */                 if (tcRequestSession.useThreadPool) {
/*  531 */                   NccClient.this.waitingOnResponseNoA.incrementAndGet();
/*  532 */                   NccClient.this.onResponseExecutorServiceA.submit(new Runnable()
/*      */                       {
/*      */                         public void run()
/*      */                         {
/*      */                           try {
/*  537 */                             tcRequestSession.tcResponseCallback.onResponse(tcRequestSession.reqSessionId, deviceName, tcResponse);
/*      */                           }
/*  539 */                           catch (Exception ex) {
/*  540 */                             NccClient.logger.error("tcResponseCallback.onResponse failed, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { this.val$sessionId, this.val$tcRequestSession.reqSessionId, this.val$deviceName, this.val$tcResponse, ex });
/*      */ 
/*      */                           
/*      */                           }
/*      */                           finally {
/*      */ 
/*      */ 
/*      */                             
/*  548 */                             NccClient.this.waitingOnResponseNoA.decrementAndGet();
/*      */                           } 
/*      */                         }
/*      */                       });
/*      */                 } else {
/*      */                   try {
/*  554 */                     tcRequestSession.tcResponseCallback.onResponse(tcRequestSession.reqSessionId, deviceName, tcResponse);
/*      */                   }
/*  556 */                   catch (Exception ex) {
/*  557 */                     NccClient.logger.error("tcResponseCallback.onResponse failed, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { sessionId, tcRequestSession.reqSessionId, deviceName, tcResponse, ex });
/*      */                   } 
/*      */                 } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  567 */                 NccClient.this.tcRequestSessionMap.remove(sessionId); break;
/*      */               } 
/*  569 */               NccClient.logger.warn("tcRequestSession has been discarded, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { sessionId, tcRequestSession.reqSessionId, deviceName, tcResponse });
/*      */               break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNccUrl(String deviceName, Map<String, String> nccUrlCache) throws Exception {
/*  586 */     IMap<String, DeviceTcConfig> tcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  587 */     DeviceTcConfig tcConfig = (DeviceTcConfig)tcConfigMap.get(deviceName);
/*  588 */     if (tcConfig != null) {
/*  589 */       if (!tcConfig.isEnable().booleanValue())
/*  590 */         throw new Exception("The device: " + deviceName + " was not enabled"); 
/*  591 */       String mfccId = tcConfig.getMfccId();
/*  592 */       if (mfccId != null) {
/*      */         
/*  594 */         if (nccUrlCache != null) {
/*  595 */           String str = nccUrlCache.get(mfccId);
/*  596 */           if (str != null) return str; 
/*      */         } 
/*  598 */         IMap<String, MfccConfig> mfccConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.MfccConfig);
/*  599 */         MfccConfig mfccConfig = (MfccConfig)mfccConfigMap.get(mfccId);
/*  600 */         if (mfccConfig == null)
/*  601 */           throw new Exception("The MFCC with id: " + mfccId + " was not found in MfccConfig table"); 
/*  602 */         IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  603 */         DeviceTcConfig hostConfig = (DeviceTcConfig)deviceConfigMap.get(mfccConfig.getHostId());
/*  604 */         if (hostConfig == null) {
/*  605 */           throw new Exception("The host with id: " + mfccConfig
/*      */               
/*  607 */               .getHostId() + " was not found in DeviceTcConfig table");
/*      */         }
/*  609 */         String nccUrl = "rmi://" + hostConfig.getIp() + ":" + this.nccRmiPort + "/ncc";
/*  610 */         if (nccUrlCache != null) {
/*  611 */           nccUrlCache.put(mfccId, nccUrl);
/*      */         }
/*  613 */         return nccUrl;
/*      */       } 
/*      */       
/*  616 */       throw new Exception("The device: " + deviceName + " was not assigned to any MFCC");
/*      */     } 
/*  618 */     throw new Exception("The device config of " + deviceName + " was not found");
/*      */   }
/*      */   
/*      */   private enum SessionType
/*      */   {
/*  623 */     SingleTcSingleRequest('@'),
/*  624 */     SingleTcMultipleRequest('#'),
/*  625 */     MultipleTcSingleRequest('$');
/*      */     
/*      */     char prefix;
/*      */     
/*      */     SessionType(char prefix) {
/*  630 */       this.prefix = prefix;
/*      */     }
/*      */     
/*      */     static SessionType fromChar(char c) {
/*  634 */       switch (c) {
/*      */         case '@':
/*  636 */           return SingleTcSingleRequest;
/*      */         case '#':
/*  638 */           return SingleTcMultipleRequest;
/*      */         case '$':
/*  640 */           return MultipleTcSingleRequest;
/*      */       } 
/*  642 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private String getNextSessionId(SessionType sessionType) {
/*  648 */     return sessionType.prefix + Long.toString(this.atomicSessionId.incrementAndGet());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TcResponse sendTcRequest(String deviceName, Object cmdBindingObj) {
/*      */     try {
/*  661 */       NccRemote nccRemote = (NccRemote)RmiUtils.getRemoteClient(getNccUrl(deviceName, null), NccRemote.class);
/*  662 */       return nccRemote.sendTcRequest(deviceName, cmdBindingObj);
/*  663 */     } catch (Exception ex) {
/*  664 */       TcResponse tcResponse = new TcResponse();
/*  665 */       tcResponse.setResult(TcResponse.Result.FAIL);
/*  666 */       tcResponse.setErrorCode(TcResponse.ErrorCode.EXCEPTION);
/*  667 */       tcResponse.setErrorReason(
/*  668 */           ("NccClient Exception, " + ex.getMessage() == null) ? ex.toString() : ex.getMessage());
/*  669 */       return tcResponse;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendAsyncRequest(String deviceName, Object cmdBindingObj, TcResponseCallback tcResponseCallback) {
/*  675 */     sendAsyncRequest(deviceName, cmdBindingObj, tcResponseCallback, null, 0L, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendAsyncRequest(String deviceName, Object cmdBindingObj, TcResponseCallback tcResponseCallback, String reqSessionId) {
/*  683 */     sendAsyncRequest(deviceName, cmdBindingObj, tcResponseCallback, reqSessionId, 0L, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendAsyncRequest(String deviceName, Object cmdBindingObj, TcResponseCallback tcResponseCallback, String reqSessionId, long timeout) {
/*  692 */     sendAsyncRequest(deviceName, cmdBindingObj, tcResponseCallback, reqSessionId, timeout, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendAsyncRequest(final String deviceName, Object cmdBindingObj, final TcResponseCallback tcResponseCallback, final String reqSessionId, long timeout, boolean useThreadPool) {
/*  719 */     final String sessionId = getNextSessionId(SessionType.SingleTcSingleRequest);
/*  720 */     if (timeout <= 0L) timeout = 21000L; 
/*  721 */     if (logger.isTraceEnabled())
/*  722 */       logger.trace("sendAsyncRequest, sessionId:{}, deviceName:{}, cmdBindingObj: {}", new Object[] { sessionId, deviceName, cmdBindingObj
/*      */ 
/*      */ 
/*      */             
/*  726 */             .toString() }); 
/*      */     try {
/*  728 */       NccRemote nccRemote = (NccRemote)RmiUtils.getRemoteClient(getNccUrl(deviceName, null), NccRemote.class);
/*  729 */       TcRequestSession tcRequestSession = new TcRequestSession();
/*  730 */       tcRequestSession.tcResponseCallback = tcResponseCallback;
/*  731 */       tcRequestSession.timeout = System.currentTimeMillis() + timeout;
/*  732 */       tcRequestSession.deviceName = deviceName;
/*  733 */       tcRequestSession.reqSessionId = reqSessionId;
/*  734 */       tcRequestSession.sessionId = sessionId;
/*  735 */       tcRequestSession.useThreadPool = useThreadPool;
/*  736 */       this.tcRequestSessionMap.put(sessionId, tcRequestSession);
/*  737 */       nccRemote.sendAsyncRequest(deviceName, cmdBindingObj, this.nccCallback, sessionId);
/*  738 */     } catch (Exception ex) {
/*  739 */       this.tcRequestSessionMap.remove(sessionId);
/*  740 */       final TcResponse tcResponse = new TcResponse();
/*  741 */       tcResponse.setResult(TcResponse.Result.FAIL);
/*  742 */       tcResponse.setErrorCode(TcResponse.ErrorCode.EXCEPTION);
/*  743 */       tcResponse.setErrorReason(
/*  744 */           ("NccClient Exception, " + ex.getMessage() == null) ? ex.toString() : ex.getMessage());
/*  745 */       if (useThreadPool) {
/*  746 */         this.waitingOnResponseNoA.incrementAndGet();
/*  747 */         this.onResponseExecutorServiceA.submit(new Runnable()
/*      */             {
/*      */               public void run()
/*      */               {
/*      */                 try {
/*  752 */                   tcResponseCallback.onResponse(reqSessionId, deviceName, tcResponse);
/*  753 */                 } catch (Exception ex) {
/*  754 */                   NccClient.logger.error("tcResponseCallback.onResponse failed, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { this.val$sessionId, this.val$reqSessionId, this.val$deviceName, this.val$tcResponse, ex });
/*      */ 
/*      */                 
/*      */                 }
/*      */                 finally {
/*      */ 
/*      */ 
/*      */                   
/*  762 */                   NccClient.this.waitingOnResponseNoA.decrementAndGet();
/*      */                 } 
/*      */               }
/*      */             });
/*      */       } else {
/*      */         try {
/*  768 */           tcResponseCallback.onResponse(reqSessionId, deviceName, tcResponse);
/*  769 */         } catch (Exception ex1) {
/*  770 */           logger.error("tcResponseCallback.onResponse failed, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { sessionId, reqSessionId, deviceName, tcResponse, ex1 });
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendAsyncMultipleRequests(String deviceName, Object[] cmdBindingObjs, TcResponseCallback tcResponseCallback) {
/*  784 */     sendAsyncMultipleRequests(deviceName, cmdBindingObjs, tcResponseCallback, null, 0L, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendAsyncMultipleRequests(String deviceName, Object[] cmdBindingObjs, TcResponseCallback tcResponseCallback, String reqSessionId) {
/*  792 */     sendAsyncMultipleRequests(deviceName, cmdBindingObjs, tcResponseCallback, reqSessionId, 0L, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendAsyncMultipleRequests(String deviceName, Object[] cmdBindingObjs, TcResponseCallback tcResponseCallback, String reqSessionId, long timeout) {
/*  802 */     sendAsyncMultipleRequests(deviceName, cmdBindingObjs, tcResponseCallback, reqSessionId, timeout, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendAsyncMultipleRequests(final String deviceName, Object[] cmdBindingObjs, final TcResponseCallback tcResponseCallback, final String reqSessionId, long timeout, boolean useThreadPool) {
/*  834 */     final String sessionId = getNextSessionId(SessionType.SingleTcMultipleRequest);
/*  835 */     if (timeout <= 0L) timeout = 24000L; 
/*  836 */     if (logger.isTraceEnabled())
/*  837 */       logger.trace("sendAsyncMultipleRequests, sessionId:{}, deviceName:{}, cmdBindingObj[0]: {}", new Object[] { sessionId, deviceName, cmdBindingObjs[0]
/*      */ 
/*      */ 
/*      */             
/*  841 */             .toString() }); 
/*      */     try {
/*  843 */       NccRemote nccRemote = (NccRemote)RmiUtils.getRemoteClient(getNccUrl(deviceName, null), NccRemote.class);
/*  844 */       TcRequestSession tcRequestSession = new TcRequestSession();
/*  845 */       tcRequestSession.tcResponseCallback = tcResponseCallback;
/*  846 */       tcRequestSession.timeout = System.currentTimeMillis() + timeout;
/*  847 */       tcRequestSession.responseFlags = new boolean[cmdBindingObjs.length];
/*  848 */       tcRequestSession.deviceName = deviceName;
/*  849 */       tcRequestSession.reqSessionId = reqSessionId;
/*  850 */       tcRequestSession.sessionId = sessionId;
/*  851 */       tcRequestSession.useThreadPool = useThreadPool;
/*  852 */       this.tcRequestSessionMap.put(sessionId, tcRequestSession);
/*  853 */       nccRemote.sendAsyncMultipleRequests(deviceName, cmdBindingObjs, this.nccCallback, sessionId);
/*  854 */     } catch (Exception ex) {
/*  855 */       this.tcRequestSessionMap.remove(sessionId);
/*  856 */       for (int i = 0; i < cmdBindingObjs.length; i++) {
/*  857 */         final TcResponse tcResponse = new TcResponse();
/*  858 */         tcResponse.setResult(TcResponse.Result.FAIL);
/*  859 */         tcResponse.setErrorCode(TcResponse.ErrorCode.EXCEPTION);
/*  860 */         tcResponse.setErrorReason(
/*  861 */             ("NccClient Exception, " + ex.getMessage() == null) ? ex.toString() : ex.getMessage());
/*  862 */         tcResponse.setCmdIndex(i);
/*  863 */         if (useThreadPool) {
/*  864 */           this.waitingOnResponseNoA.incrementAndGet();
/*  865 */           this.onResponseExecutorServiceA.submit(new Runnable()
/*      */               {
/*      */                 public void run()
/*      */                 {
/*      */                   try {
/*  870 */                     tcResponseCallback.onResponse(reqSessionId, deviceName, tcResponse);
/*  871 */                   } catch (Exception ex) {
/*  872 */                     NccClient.logger.error("tcResponseCallback.onResponse failed, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { this.val$sessionId, this.val$reqSessionId, this.val$deviceName, this.val$tcResponse, ex });
/*      */ 
/*      */                   
/*      */                   }
/*      */                   finally {
/*      */ 
/*      */ 
/*      */                     
/*  880 */                     NccClient.this.waitingOnResponseNoA.decrementAndGet();
/*      */                   } 
/*      */                 }
/*      */               });
/*      */         } else {
/*      */           try {
/*  886 */             tcResponseCallback.onResponse(reqSessionId, deviceName, tcResponse);
/*  887 */           } catch (Exception ex1) {
/*  888 */             logger.error("tcResponseCallback.onResponse failed, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { sessionId, reqSessionId, deviceName, tcResponse, ex1 });
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendAsyncRequest2MulipleTc(String[] deviceNames, Object cmdBindingObj, TcResponseCallback tcResponseCallback) {
/*  903 */     sendAsyncRequest2MulipleTc(deviceNames, cmdBindingObj, tcResponseCallback, null, 0L, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendAsyncRequest2MulipleTc(String[] deviceNames, Object cmdBindingObj, TcResponseCallback tcResponseCallback, String reqSessionId) {
/*  911 */     sendAsyncRequest2MulipleTc(deviceNames, cmdBindingObj, tcResponseCallback, reqSessionId, 0L, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendAsyncRequest2MulipleTc(String[] deviceNames, Object cmdBindingObj, TcResponseCallback tcResponseCallback, String reqSessionId, long timeout) {
/*  921 */     sendAsyncRequest2MulipleTc(deviceNames, cmdBindingObj, tcResponseCallback, reqSessionId, timeout, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendAsyncRequest2MulipleTc(String[] deviceNames, Object cmdBindingObj, final TcResponseCallback tcResponseCallback, final String reqSessionId, long timeout, boolean useThreadPool) {
/*  952 */     HashMap<String, List<String>> deviceNameMap = new HashMap<>();
/*  953 */     if (timeout <= 0L) timeout = 22000L; 
/*  954 */     HashMap<String, String> nccUrlCache = new HashMap<>();
/*  955 */     for (int i = 0; i < deviceNames.length; i++) {
/*  956 */       String nccUrl = null;
/*      */       try {
/*  958 */         nccUrl = getNccUrl(deviceNames[i], nccUrlCache);
/*  959 */       } catch (Exception ex) {
/*  960 */         final TcResponse tcResponse = new TcResponse();
/*  961 */         tcResponse.setResult(TcResponse.Result.FAIL);
/*  962 */         tcResponse.setErrorCode(TcResponse.ErrorCode.EXCEPTION);
/*  963 */         tcResponse.setErrorReason(
/*  964 */             ("NccClient Exception, " + ex.getMessage() == null) ? ex.toString() : ex.getMessage());
/*  965 */         tcResponseCallback.onResponse(reqSessionId, deviceNames[i], tcResponse);
/*      */       } 
/*      */       
/*  968 */       List<String> deviceNameList = deviceNameMap.get(nccUrl);
/*  969 */       if (deviceNameList == null) {
/*  970 */         deviceNameList = new ArrayList<>();
/*  971 */         deviceNameMap.put(nccUrl, deviceNameList);
/*      */       } 
/*  973 */       deviceNameList.add(deviceNames[i]);
/*      */     } 
/*      */     
/*  976 */     long curTime = System.currentTimeMillis();
/*  977 */     for (String nccUrl : deviceNameMap.keySet()) {
/*  978 */       final String sessionId = getNextSessionId(SessionType.MultipleTcSingleRequest);
/*  979 */       List<String> deviceNameList = deviceNameMap.get(nccUrl);
/*  980 */       String[] deviceNameSubset = new String[deviceNameList.size()];
/*  981 */       deviceNameList.toArray(deviceNameSubset);
/*  982 */       if (logger.isTraceEnabled())
/*  983 */         logger.trace("sendAsyncRequest2MulipleTc, sessionId:{}, nccUrl: {}, deviceNames[0]:{}, cmdBindingObj:{}", new Object[] { sessionId, nccUrl, deviceNameSubset[0], cmdBindingObj
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  988 */               .toString() }); 
/*      */       try {
/*  990 */         NccRemote nccRemote = (NccRemote)RmiUtils.getRemoteClient(nccUrl, NccRemote.class);
/*  991 */         TcRequestSession tcRequestSession = new TcRequestSession();
/*  992 */         tcRequestSession.tcResponseCallback = tcResponseCallback;
/*  993 */         tcRequestSession.timeout = curTime + timeout;
/*  994 */         tcRequestSession.responseFlags = new boolean[deviceNameSubset.length];
/*  995 */         tcRequestSession.deviceNames = deviceNameSubset;
/*  996 */         tcRequestSession.reqSessionId = reqSessionId;
/*  997 */         tcRequestSession.sessionId = sessionId;
/*  998 */         tcRequestSession.useThreadPool = useThreadPool;
/*  999 */         this.tcRequestSessionMap.put(sessionId, tcRequestSession);
/* 1000 */         nccRemote.sendAsyncRequest2MulipleTc(deviceNameSubset, cmdBindingObj, this.nccCallback, sessionId);
/*      */       }
/* 1002 */       catch (Exception ex) {
/* 1003 */         this.tcRequestSessionMap.remove(sessionId);
/* 1004 */         for (int j = 0; j < deviceNameSubset.length; j++) {
/* 1005 */           final TcResponse tcResponse = new TcResponse();
/* 1006 */           tcResponse.setResult(TcResponse.Result.FAIL);
/* 1007 */           tcResponse.setErrorCode(TcResponse.ErrorCode.EXCEPTION);
/* 1008 */           tcResponse.setErrorReason(
/* 1009 */               ("NccClient Exception, " + ex.getMessage() == null) ? ex.toString() : ex.getMessage());
/* 1010 */           final String deviceName = deviceNameSubset[j];
/* 1011 */           if (useThreadPool) {
/* 1012 */             this.waitingOnResponseNoB.incrementAndGet();
/* 1013 */             this.onResponseExecutorServiceB.submit(new Runnable()
/*      */                 {
/*      */                   public void run()
/*      */                   {
/*      */                     try {
/* 1018 */                       tcResponseCallback.onResponse(reqSessionId, deviceName, tcResponse);
/* 1019 */                     } catch (Exception ex) {
/* 1020 */                       NccClient.logger.error("tcResponseCallback.onResponse failed, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { this.val$sessionId, this.val$reqSessionId, this.val$deviceName, this.val$tcResponse, ex });
/*      */ 
/*      */                     
/*      */                     }
/*      */                     finally {
/*      */ 
/*      */ 
/*      */                       
/* 1028 */                       NccClient.this.waitingOnResponseNoB.decrementAndGet();
/*      */                     } 
/*      */                   }
/*      */                 });
/*      */           } else {
/*      */             try {
/* 1034 */               tcResponseCallback.onResponse(reqSessionId, deviceName, tcResponse);
/* 1035 */             } catch (Exception ex1) {
/* 1036 */               logger.error("tcResponseCallback.onResponse failed, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { sessionId, reqSessionId, deviceName, tcResponse, ex1 });
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean resetTcConnection(String deviceName) {
/*      */     try {
/* 1057 */       NccRemote nccRemote = (NccRemote)RmiUtils.getRemoteClient(getNccUrl(deviceName, null), NccRemote.class);
/* 1058 */       return nccRemote.resetTcConnection(deviceName);
/* 1059 */     } catch (Exception ex) {
/* 1060 */       logger.error("Reset Tc Connection failed, deviceName=" + deviceName, ex);
/* 1061 */       return false;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\ncc\client\NccClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */