/*     */ package com.hwacom.ngtms.c.ncc.client;
/*     */ 
/*     */ import com.hwacom.ngtms.base.rmi.RmiCallbackUtils;
/*     */ import com.hwacom.ngtms.ncc.remote.TcResponse;
/*     */ import java.util.Arrays;
/*     */ import java.util.Enumeration;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.annotation.PreDestroy;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RemoteTcRequestHandler
/*     */ {
/*  38 */   private static Logger logger = LoggerFactory.getLogger(RemoteTcRequestHandler.class);
/*     */   public static final long DefaultSendAsyncRequestTimeout = 18000L;
/*     */   public static final long DefaultSendAsyncMultipleRequestsTimeout = 21000L;
/*     */   public static final long DefaultAsyncRequest2MulipleTcTimeout = 19000L;
/*     */   private TcResponseCallbackHandler tcResponseCallbackHandler;
/*     */   private TcResponseCallback tcResponseCallbackRemote;
/*  44 */   private ConcurrentHashMap<String, TcRequestSession> tcRequestSessionMap = new ConcurrentHashMap<>();
/*     */   
/*  46 */   private AtomicLong atomicSessionId = new AtomicLong();
/*  47 */   private AtomicBoolean running = new AtomicBoolean();
/*  48 */   private ExecutorService executorService = Executors.newCachedThreadPool();
/*     */   
/*     */   private Thread expiredSessionCleanThread;
/*     */ 
/*     */   
/*     */   private static class TcRequestSession
/*     */   {
/*     */     TcResponseCallback tcResponseCallback;
/*     */     long timeout;
/*     */     boolean[] responseFlags;
/*     */     int responseCounter;
/*  59 */     AtomicBoolean discarded = new AtomicBoolean(); String deviceName; String[] deviceNames; String reqSessionId;
/*     */     private TcRequestSession() {} }
/*     */   
/*     */   @PostConstruct
/*     */   public void start() {
/*  64 */     if (this.running.compareAndSet(false, true)) {
/*  65 */       this.expiredSessionCleanThread = new Thread(this.expiredSessionCleaner, "RemoteTcRequestHandler expiredSessionCleanThread");
/*     */       
/*  67 */       this.expiredSessionCleanThread.setDaemon(true);
/*  68 */       this.expiredSessionCleanThread.start();
/*  69 */       this.tcResponseCallbackHandler = new TcResponseCallbackHandler();
/*  70 */       this
/*  71 */         .tcResponseCallbackRemote = (TcResponseCallback)RmiCallbackUtils.export(this.tcResponseCallbackHandler, TcResponseCallback.class);
/*     */     } 
/*     */   }
/*     */   
/*     */   @PreDestroy
/*     */   public void stop() {
/*  77 */     if (this.running.compareAndSet(true, false)) {
/*  78 */       RmiCallbackUtils.unexport(this.tcResponseCallbackHandler);
/*  79 */       this.expiredSessionCleanThread.interrupt();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   private Runnable expiredSessionCleaner = new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/*  90 */         while (RemoteTcRequestHandler.this.running.get()) {
/*     */           
/*     */           try {
/*  93 */             TimeUnit.MILLISECONDS.sleep(1000L);
/*  94 */             Enumeration<String> keys = RemoteTcRequestHandler.this.tcRequestSessionMap.keys();
/*  95 */             long time = System.currentTimeMillis();
/*  96 */             while (keys.hasMoreElements()) {
/*  97 */               final String sessionId = keys.nextElement();
/*  98 */               final RemoteTcRequestHandler.TcRequestSession tcRequestSession = (RemoteTcRequestHandler.TcRequestSession)RemoteTcRequestHandler.this.tcRequestSessionMap.get(sessionId);
/*  99 */               if (tcRequestSession == null || 
/* 100 */                 tcRequestSession.timeout >= time)
/* 101 */                 continue;  synchronized (tcRequestSession) {
/* 102 */                 if (tcRequestSession.timeout < time && 
/* 103 */                   tcRequestSession.discarded.compareAndSet(false, true)) {
/* 104 */                   int logCount, i; final TcResponse tcResponse; RemoteTcRequestHandler.this.tcRequestSessionMap.remove(sessionId);
/* 105 */                   RemoteTcRequestHandler.SessionType sessionType = RemoteTcRequestHandler.SessionType.fromChar(sessionId.charAt(0));
/* 106 */                   switch (sessionType) {
/*     */                     case MultipleTcSingleRequest:
/* 108 */                       logCount = 0;
/* 109 */                       for (i = 0; i < tcRequestSession.responseFlags.length; i++) {
/* 110 */                         if (!tcRequestSession.responseFlags[i]) {
/* 111 */                           final TcResponse tcResponse = new TcResponse();
/* 112 */                           tcResponse1.setResult(TcResponse.Result.TIMEOUT);
/* 113 */                           tcResponse1.setErrorCode(TcResponse.ErrorCode.TIMEOUT);
/* 114 */                           tcResponse1.setErrorReason("RemoteTcRequestHandler waiting response timeout");
/*     */ 
/*     */ 
/*     */                           
/* 118 */                           final String deviceName = tcRequestSession.deviceNames[i];
/* 119 */                           if (logCount < 3) {
/* 120 */                             RemoteTcRequestHandler.logger.warn("Does not receive any response from NccClient,  sessionId: {}, reqSessionId: {}, deviceName: {} ", new Object[] { sessionId, tcRequestSession.reqSessionId, deviceName });
/*     */ 
/*     */ 
/*     */ 
/*     */                             
/* 125 */                             logCount++;
/*     */                           } 
/* 127 */                           RemoteTcRequestHandler.this.executorService.submit(new Runnable()
/*     */                               {
/*     */                                 public void run()
/*     */                                 {
/*     */                                   try {
/* 132 */                                     tcRequestSession.tcResponseCallback.onResponse(tcRequestSession.reqSessionId, deviceName, tcResponse);
/*     */ 
/*     */                                   
/*     */                                   }
/* 136 */                                   catch (Exception ex) {
/* 137 */                                     RemoteTcRequestHandler.logger.error("tcResponseCallback.onResponse failed, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { this.val$sessionId, this.val$tcRequestSession.reqSessionId, this.val$deviceName, this.val$tcResponse, ex });
/*     */                                   } 
/*     */                                 }
/*     */                               });
/*     */                         } 
/*     */                       } 
/*     */                       break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/*     */                     case SingleTcMultipleRequest:
/* 151 */                       for (i = 0; i < tcRequestSession.responseFlags.length; i++) {
/* 152 */                         if (!tcRequestSession.responseFlags[i]) {
/* 153 */                           final TcResponse tcResponse = new TcResponse();
/* 154 */                           tcResponse1.setResult(TcResponse.Result.TIMEOUT);
/* 155 */                           tcResponse1.setErrorCode(TcResponse.ErrorCode.TIMEOUT);
/* 156 */                           tcResponse1.setErrorReason("RemoteTcRequestHandler waiting response timeout");
/*     */                           
/* 158 */                           tcResponse1.setCmdIndex(i);
/*     */ 
/*     */                           
/* 161 */                           RemoteTcRequestHandler.this.executorService.submit(new Runnable()
/*     */                               {
/*     */                                 public void run()
/*     */                                 {
/*     */                                   try {
/* 166 */                                     tcRequestSession.tcResponseCallback.onResponse(tcRequestSession.reqSessionId, tcRequestSession.deviceName, tcResponse);
/*     */ 
/*     */                                   
/*     */                                   }
/* 170 */                                   catch (Exception ex) {
/* 171 */                                     RemoteTcRequestHandler.logger.error("tcResponseCallback.onResponse failed, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { this.val$sessionId, this.val$tcRequestSession.reqSessionId, this.val$tcRequestSession.deviceName, this.val$tcResponse, ex });
/*     */                                   } 
/*     */                                 }
/*     */                               });
/*     */                         } 
/*     */                       } 
/*     */                       break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/*     */                     case SingleTcSingleRequest:
/* 185 */                       tcResponse = new TcResponse();
/* 186 */                       tcResponse.setResult(TcResponse.Result.TIMEOUT);
/* 187 */                       tcResponse.setErrorCode(TcResponse.ErrorCode.TIMEOUT);
/* 188 */                       tcResponse.setErrorReason("RemoteTcRequestHandler waiting response timeout");
/*     */ 
/*     */ 
/*     */                       
/* 192 */                       RemoteTcRequestHandler.this.executorService.submit(new Runnable()
/*     */                           {
/*     */                             public void run()
/*     */                             {
/*     */                               try {
/* 197 */                                 tcRequestSession.tcResponseCallback.onResponse(tcRequestSession.reqSessionId, tcRequestSession.deviceName, tcResponse);
/*     */ 
/*     */                               
/*     */                               }
/* 201 */                               catch (Exception ex) {
/* 202 */                                 RemoteTcRequestHandler.logger.error("tcResponseCallback.onResponse failed, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { this.val$sessionId, this.val$tcRequestSession.reqSessionId, this.val$tcRequestSession.deviceName, this.val$tcResponse, ex });
/*     */                               } 
/*     */                             }
/*     */                           });
/*     */                       break;
/*     */                   } 
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
/*     */                 } 
/*     */               } 
/*     */             } 
/* 221 */           } catch (InterruptedException interruptedException) {
/*     */           
/* 223 */           } catch (Throwable t) {
/* 224 */             RemoteTcRequestHandler.logger.error("Failed to run expiredSessionCleaner", t);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     };
/*     */   
/*     */   class TcResponseCallbackHandler
/*     */     implements TcResponseCallback {
/*     */     public void onResponse(final String sessionId, final String deviceName, final TcResponse tcResponse) {
/* 233 */       final RemoteTcRequestHandler.TcRequestSession tcRequestSession = (RemoteTcRequestHandler.TcRequestSession)RemoteTcRequestHandler.this.tcRequestSessionMap.get(sessionId);
/* 234 */       if (RemoteTcRequestHandler.logger.isTraceEnabled()) {
/* 235 */         RemoteTcRequestHandler.logger.trace("onResponse, sessionId:{}, deviceName:{}, tcResponse:{}", new Object[] { sessionId, deviceName, tcResponse });
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 240 */       if (tcRequestSession != null) {
/* 241 */         RemoteTcRequestHandler.SessionType sessionType = RemoteTcRequestHandler.SessionType.fromChar(sessionId.charAt(0));
/* 242 */         synchronized (tcRequestSession) {
/* 243 */           switch (sessionType) {
/*     */             case MultipleTcSingleRequest:
/* 245 */               if (!tcRequestSession.discarded.get()) {
/* 246 */                 RemoteTcRequestHandler.this.executorService.submit(new Runnable()
/*     */                     {
/*     */                       public void run()
/*     */                       {
/*     */                         try {
/* 251 */                           tcRequestSession.tcResponseCallback.onResponse(tcRequestSession.reqSessionId, deviceName, tcResponse);
/*     */                         }
/* 253 */                         catch (Exception ex) {
/* 254 */                           RemoteTcRequestHandler.logger.error("tcResponseCallback.onResponse failed, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { this.val$sessionId, this.val$tcRequestSession.reqSessionId, this.val$deviceName, this.val$tcResponse, ex });
/*     */                         } 
/*     */                       }
/*     */                     });
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 int i;
/*     */ 
/*     */ 
/*     */                 
/* 266 */                 for (i = 0; i < tcRequestSession.deviceNames.length; i++) {
/* 267 */                   String d = tcRequestSession.deviceNames[i];
/*     */                   
/* 269 */                   if (!tcRequestSession.responseFlags[i] && d.equals(deviceName)) {
/* 270 */                     tcRequestSession.responseFlags[i] = true;
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 274 */                 if (i < tcRequestSession.deviceNames.length) {
/* 275 */                   tcRequestSession.responseCounter++;
/* 276 */                   if (tcRequestSession.responseCounter >= tcRequestSession.responseFlags.length) {
/* 277 */                     RemoteTcRequestHandler.this.tcRequestSessionMap.remove(sessionId);
/* 278 */                     tcRequestSession.discarded.set(true);
/*     */                   }  break;
/*     */                 } 
/* 281 */                 RemoteTcRequestHandler.logger.error("DeviceName not found: {}", deviceName);
/*     */                 break;
/*     */               } 
/* 284 */               RemoteTcRequestHandler.logger.warn("tcRequestSession has been discarded, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { sessionId, tcRequestSession.reqSessionId, deviceName, tcResponse });
/*     */               break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             case SingleTcMultipleRequest:
/* 293 */               if (!tcRequestSession.discarded.get()) {
/* 294 */                 RemoteTcRequestHandler.this.executorService.submit(new Runnable()
/*     */                     {
/*     */                       public void run()
/*     */                       {
/*     */                         try {
/* 299 */                           tcRequestSession.tcResponseCallback.onResponse(tcRequestSession.reqSessionId, deviceName, tcResponse);
/*     */                         }
/* 301 */                         catch (Exception ex) {
/* 302 */                           RemoteTcRequestHandler.logger.error("tcResponseCallback.onResponse failed, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { this.val$sessionId, this.val$tcRequestSession.reqSessionId, this.val$deviceName, this.val$tcResponse, ex });
/*     */                         } 
/*     */                       }
/*     */                     });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 313 */                 tcRequestSession.responseFlags[tcResponse.getCmdIndex()] = true;
/* 314 */                 tcRequestSession.responseCounter++;
/* 315 */                 if (tcRequestSession.responseCounter >= tcRequestSession.responseFlags.length) {
/* 316 */                   RemoteTcRequestHandler.this.tcRequestSessionMap.remove(sessionId);
/* 317 */                   tcRequestSession.discarded.set(true);
/*     */                 }  break;
/*     */               } 
/* 320 */               RemoteTcRequestHandler.logger.warn("tcRequestSession has been discarded, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { sessionId, tcRequestSession.reqSessionId, deviceName, tcResponse });
/*     */               break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             case SingleTcSingleRequest:
/* 329 */               if (tcRequestSession.discarded.compareAndSet(false, true)) {
/* 330 */                 RemoteTcRequestHandler.this.executorService.submit(new Runnable()
/*     */                     {
/*     */                       public void run()
/*     */                       {
/*     */                         try {
/* 335 */                           tcRequestSession.tcResponseCallback.onResponse(tcRequestSession.reqSessionId, deviceName, tcResponse);
/*     */                         }
/* 337 */                         catch (Exception ex) {
/* 338 */                           RemoteTcRequestHandler.logger.error("tcResponseCallback.onResponse failed, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { this.val$sessionId, this.val$tcRequestSession.reqSessionId, this.val$deviceName, this.val$tcResponse, ex });
/*     */                         } 
/*     */                       }
/*     */                     });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 348 */                 RemoteTcRequestHandler.this.tcRequestSessionMap.remove(sessionId); break;
/*     */               } 
/* 350 */               RemoteTcRequestHandler.logger.warn("tcRequestSession has been discarded, sessionId: {}, reqSessionId: {}, deviceName: {}, tcResponse: {}", new Object[] { sessionId, tcRequestSession.reqSessionId, deviceName, tcResponse });
/*     */               break;
/*     */           } 
/*     */         } 
/*     */       } 
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
/*     */   private enum SessionType
/*     */   {
/* 367 */     SingleTcSingleRequest('@'),
/* 368 */     SingleTcMultipleRequest('#'),
/* 369 */     MultipleTcSingleRequest('$');
/*     */     
/*     */     char prefix;
/*     */     
/*     */     SessionType(char prefix) {
/* 374 */       this.prefix = prefix;
/*     */     }
/*     */     
/*     */     static SessionType fromChar(char c) {
/* 378 */       switch (c) {
/*     */         case '@':
/* 380 */           return SingleTcSingleRequest;
/*     */         case '#':
/* 382 */           return SingleTcMultipleRequest;
/*     */         case '$':
/* 384 */           return MultipleTcSingleRequest;
/*     */       } 
/* 386 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private String getNextSessionId(SessionType sessionType) {
/* 392 */     return sessionType.prefix + Long.toString(this.atomicSessionId.incrementAndGet());
/*     */   }
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
/*     */   public TcResponse sendTcRequest(NccEnabledRemote nccEnabledRemote, String deviceName, Object cmdBindingObj) {
/*     */     try {
/* 408 */       return nccEnabledRemote.sendTcRequest(deviceName, cmdBindingObj);
/* 409 */     } catch (Exception ex) {
/* 410 */       TcResponse tcResponse = new TcResponse();
/* 411 */       tcResponse.setResult(TcResponse.Result.FAIL);
/* 412 */       tcResponse.setErrorCode(TcResponse.ErrorCode.EXCEPTION);
/* 413 */       tcResponse.setErrorReason(
/* 414 */           ("RemoteTcRequestHandler Exception, " + ex.getMessage() == null) ? ex
/* 415 */           .toString() : ex
/* 416 */           .getMessage());
/* 417 */       return tcResponse;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendAsyncRequest(NccEnabledRemote nccEnabledRemote, String deviceName, Object cmdBindingObj, TcResponseCallback tcResponseCallback) {
/* 426 */     sendAsyncRequest(nccEnabledRemote, deviceName, cmdBindingObj, tcResponseCallback, null, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendAsyncRequest(NccEnabledRemote nccEnabledRemote, String deviceName, Object cmdBindingObj, TcResponseCallback tcResponseCallback, String reqSessionId) {
/* 435 */     sendAsyncRequest(nccEnabledRemote, deviceName, cmdBindingObj, tcResponseCallback, reqSessionId, 0L);
/*     */   }
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
/*     */   public void sendAsyncRequest(NccEnabledRemote nccEnabledRemote, String deviceName, Object cmdBindingObj, TcResponseCallback tcResponseCallback, String reqSessionId, long timeout) {
/* 464 */     String sessionId = getNextSessionId(SessionType.SingleTcSingleRequest);
/* 465 */     if (timeout <= 0L) timeout = 18000L; 
/* 466 */     if (logger.isTraceEnabled())
/* 467 */       logger.trace("sendAsyncRequest, sessionId:{}, deviceName:{}, cmdBindingObj: {}", new Object[] { sessionId, deviceName, cmdBindingObj
/*     */ 
/*     */ 
/*     */             
/* 471 */             .toString() }); 
/*     */     try {
/* 473 */       TcRequestSession tcRequestSession = new TcRequestSession();
/* 474 */       tcRequestSession.tcResponseCallback = tcResponseCallback;
/* 475 */       tcRequestSession.timeout = System.currentTimeMillis() + timeout;
/* 476 */       tcRequestSession.deviceName = deviceName;
/* 477 */       tcRequestSession.reqSessionId = reqSessionId;
/* 478 */       this.tcRequestSessionMap.put(sessionId, tcRequestSession);
/* 479 */       nccEnabledRemote.sendAsyncRequest(deviceName, cmdBindingObj, this.tcResponseCallbackRemote, sessionId);
/*     */     }
/* 481 */     catch (Exception ex) {
/* 482 */       this.tcRequestSessionMap.remove(sessionId);
/* 483 */       TcResponse tcResponse = new TcResponse();
/* 484 */       tcResponse.setResult(TcResponse.Result.FAIL);
/* 485 */       tcResponse.setErrorCode(TcResponse.ErrorCode.EXCEPTION);
/* 486 */       tcResponse.setErrorReason(
/* 487 */           ("RemoteTcRequestHandler Exception, " + ex.getMessage() == null) ? ex
/* 488 */           .toString() : ex
/* 489 */           .getMessage());
/* 490 */       tcResponseCallback.onResponse(reqSessionId, deviceName, tcResponse);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendAsyncMultipleRequests(NccEnabledRemote nccEnabledRemote, String deviceName, Object[] cmdBindingObjs, TcResponseCallback tcResponseCallback) {
/* 499 */     sendAsyncMultipleRequests(nccEnabledRemote, deviceName, cmdBindingObjs, tcResponseCallback, null, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendAsyncMultipleRequests(NccEnabledRemote nccEnabledRemote, String deviceName, Object[] cmdBindingObjs, TcResponseCallback tcResponseCallback, String reqSessionId) {
/* 509 */     sendAsyncMultipleRequests(nccEnabledRemote, deviceName, cmdBindingObjs, tcResponseCallback, reqSessionId, 0L);
/*     */   }
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
/*     */ 
/*     */   
/*     */   public void sendAsyncMultipleRequests(NccEnabledRemote nccEnabledRemote, String deviceName, Object[] cmdBindingObjs, TcResponseCallback tcResponseCallback, String reqSessionId, long timeout) {
/* 542 */     String sessionId = getNextSessionId(SessionType.SingleTcMultipleRequest);
/* 543 */     if (timeout <= 0L) timeout = 21000L; 
/* 544 */     if (logger.isTraceEnabled())
/* 545 */       logger.trace("sendAsyncMultipleRequests, sessionId:{}, deviceName:{}, cmdBindingObj[0]: {}", new Object[] { sessionId, deviceName, cmdBindingObjs[0]
/*     */ 
/*     */ 
/*     */             
/* 549 */             .toString() }); 
/*     */     try {
/* 551 */       TcRequestSession tcRequestSession = new TcRequestSession();
/* 552 */       tcRequestSession.tcResponseCallback = tcResponseCallback;
/* 553 */       tcRequestSession.timeout = System.currentTimeMillis() + timeout;
/* 554 */       tcRequestSession.responseFlags = new boolean[cmdBindingObjs.length];
/* 555 */       tcRequestSession.deviceName = deviceName;
/* 556 */       tcRequestSession.reqSessionId = reqSessionId;
/* 557 */       this.tcRequestSessionMap.put(sessionId, tcRequestSession);
/* 558 */       nccEnabledRemote.sendAsyncMultipleRequests(deviceName, cmdBindingObjs, this.tcResponseCallbackRemote, sessionId);
/*     */     }
/* 560 */     catch (Exception ex) {
/* 561 */       this.tcRequestSessionMap.remove(sessionId);
/* 562 */       for (int i = 0; i < cmdBindingObjs.length; i++) {
/* 563 */         TcResponse tcResponse = new TcResponse();
/* 564 */         tcResponse.setResult(TcResponse.Result.FAIL);
/* 565 */         tcResponse.setErrorCode(TcResponse.ErrorCode.EXCEPTION);
/* 566 */         tcResponse.setErrorReason(
/* 567 */             ("RemoteTcRequestHandler Exception, " + ex.getMessage() == null) ? ex
/* 568 */             .toString() : ex
/* 569 */             .getMessage());
/* 570 */         tcResponse.setCmdIndex(i);
/* 571 */         tcResponseCallback.onResponse(reqSessionId, deviceName, tcResponse);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendAsyncRequest2MulipleTc(NccEnabledRemote nccEnabledRemote, String[] deviceNames, Object cmdBindingObj, TcResponseCallback tcResponseCallback) {
/* 581 */     sendAsyncRequest2MulipleTc(nccEnabledRemote, deviceNames, cmdBindingObj, tcResponseCallback, null, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendAsyncRequest2MulipleTc(NccEnabledRemote nccEnabledRemote, String[] deviceNames, Object cmdBindingObj, TcResponseCallback tcResponseCallback, String reqSessionId) {
/* 591 */     sendAsyncRequest2MulipleTc(nccEnabledRemote, deviceNames, cmdBindingObj, tcResponseCallback, reqSessionId, 0L);
/*     */   }
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
/*     */   
/*     */   public void sendAsyncRequest2MulipleTc(NccEnabledRemote nccEnabledRemote, String[] deviceNames, Object cmdBindingObj, TcResponseCallback tcResponseCallback, String reqSessionId, long timeout) {
/* 623 */     String sessionId = getNextSessionId(SessionType.MultipleTcSingleRequest);
/* 624 */     if (timeout <= 0L) timeout = 19000L; 
/* 625 */     if (logger.isTraceEnabled())
/* 626 */       logger.trace("sessionId:{}, deviceNames:{}, cmdBindingObj:{}", new Object[] { sessionId, 
/*     */ 
/*     */             
/* 629 */             Arrays.toString((Object[])deviceNames), cmdBindingObj
/* 630 */             .toString() }); 
/*     */     try {
/* 632 */       TcRequestSession tcRequestSession = new TcRequestSession();
/* 633 */       tcRequestSession.tcResponseCallback = tcResponseCallback;
/* 634 */       tcRequestSession.timeout = System.currentTimeMillis() + timeout;
/* 635 */       tcRequestSession.responseFlags = new boolean[deviceNames.length];
/* 636 */       tcRequestSession.deviceNames = deviceNames;
/* 637 */       tcRequestSession.reqSessionId = reqSessionId;
/* 638 */       this.tcRequestSessionMap.put(sessionId, tcRequestSession);
/* 639 */       nccEnabledRemote.sendAsyncRequest2MulipleTc(deviceNames, cmdBindingObj, this.tcResponseCallbackRemote, sessionId);
/*     */     }
/* 641 */     catch (Exception ex) {
/* 642 */       this.tcRequestSessionMap.remove(sessionId);
/* 643 */       for (int i = 0; i < deviceNames.length; i++) {
/* 644 */         TcResponse tcResponse = new TcResponse();
/* 645 */         tcResponse.setResult(TcResponse.Result.FAIL);
/* 646 */         tcResponse.setErrorCode(TcResponse.ErrorCode.EXCEPTION);
/* 647 */         tcResponse.setErrorReason("RemoteTcRequestHandler Exception, " + ex.getMessage());
/* 648 */         tcResponse.setCmdIndex(i);
/* 649 */         tcResponseCallback.onResponse(reqSessionId, deviceNames[i], tcResponse);
/*     */       } 
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
/*     */   public boolean resetTcConnection(NccEnabledRemote nccEnabledRemote, String deviceName) {
/* 662 */     return nccEnabledRemote.resetTcConnection(deviceName);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\ncc\client\RemoteTcRequestHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */