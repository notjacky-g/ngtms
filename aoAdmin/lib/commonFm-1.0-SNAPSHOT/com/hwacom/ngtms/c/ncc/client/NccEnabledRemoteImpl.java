/*     */ package com.hwacom.ngtms.c.ncc.client;
/*     */ 
/*     */ import com.hwacom.ngtms.hcce.fme.controller.fm.FmeMainBase;
/*     */ import com.hwacom.ngtms.hcce.fme.controller.fm.RemoteInterfaceImpl;
/*     */ import com.hwacom.ngtms.ncc.remote.TcResponse;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NccEnabledRemoteImpl
/*     */   extends RemoteInterfaceImpl
/*     */   implements NccEnabledRemote
/*     */ {
/*     */   private NccClient nccClient;
/*  19 */   private ConcurrentHashMap<Class<?>, CmdAroundAdvice> adviceMap = new ConcurrentHashMap<>();
/*     */   
/*  21 */   private AtomicLong atomicTemptempRequestId = new AtomicLong();
/*     */   
/*     */   public void setFmeMainBase(FmeMainBase fmeMainBase) {
/*  24 */     super.setFmeMainBase(fmeMainBase);
/*  25 */     this.nccClient = (NccClient)fmeMainBase.getApplicationContext().getBean(NccClient.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAroundAdvice(Class<?> cmdBindingClass, CmdAroundAdvice cmdAroundAdvice) {
/*  34 */     this.adviceMap.put(cmdBindingClass, cmdAroundAdvice);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAroundAdvice(Class<?> cmdBindingClass) {
/*  42 */     this.adviceMap.remove(cmdBindingClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public TcResponse sendTcRequest(String deviceName, Object cmdBindingObj) {
/*  47 */     if (this.adviceMap.size() > 0) {
/*  48 */       CmdAroundAdvice cmdAroundAdvice = this.adviceMap.get(cmdBindingObj.getClass());
/*  49 */       if (cmdAroundAdvice != null) {
/*  50 */         long tempRequestId = this.atomicTemptempRequestId.incrementAndGet();
/*     */         
/*  52 */         Object[] args = cmdAroundAdvice.before(tempRequestId, CmdAroundAdvice.CmdRequestType.SyncSingleTcSingleRequest, new Object[] { deviceName, cmdBindingObj });
/*     */ 
/*     */ 
/*     */         
/*  56 */         if (args != null) {
/*  57 */           TcResponse tcResponse1 = this.nccClient.sendTcRequest((String)args[0], args[1]);
/*  58 */           TcResponse newTcResponse = cmdAroundAdvice.after(tempRequestId, deviceName, tcResponse1);
/*  59 */           return (newTcResponse != null) ? newTcResponse : tcResponse1;
/*     */         } 
/*  61 */         TcResponse tcResponse = cmdAroundAdvice.after(tempRequestId, deviceName, null);
/*  62 */         return tcResponse;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  67 */     return this.nccClient.sendTcRequest(deviceName, cmdBindingObj);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendAsyncRequest(String deviceName, Object cmdBindingObj, final TcResponseCallback tcResponseCallback) {
/*  73 */     if (this.adviceMap.size() > 0) {
/*  74 */       final CmdAroundAdvice cmdAroundAdvice = this.adviceMap.get(cmdBindingObj.getClass());
/*  75 */       if (cmdAroundAdvice != null) {
/*  76 */         final long tempRequestId = this.atomicTemptempRequestId.incrementAndGet();
/*     */         
/*  78 */         Object[] args = cmdAroundAdvice.before(tempRequestId, CmdAroundAdvice.CmdRequestType.AsyncSingleTcSingleRequest, new Object[] { deviceName, cmdBindingObj, tcResponseCallback });
/*     */ 
/*     */ 
/*     */         
/*  82 */         if (args != null) {
/*  83 */           this.nccClient.sendAsyncRequest((String)args[0], args[1], new TcResponseCallback()
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 public void onResponse(String reqSessionId, String deviceName, TcResponse tcResponse)
/*     */                 {
/*  91 */                   TcResponse newTcResponse = cmdAroundAdvice.after(tempRequestId, deviceName, tcResponse);
/*  92 */                   tcResponseCallback.onResponse(reqSessionId, deviceName, (newTcResponse != null) ? newTcResponse : tcResponse);
/*     */                 }
/*     */               });
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 101 */     this.nccClient.sendAsyncRequest(deviceName, cmdBindingObj, tcResponseCallback);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendAsyncRequest(String deviceName, Object cmdBindingObj, final TcResponseCallback tcResponseCallback, String reqSessionId) {
/* 110 */     if (this.adviceMap.size() > 0) {
/* 111 */       final CmdAroundAdvice cmdAroundAdvice = this.adviceMap.get(cmdBindingObj.getClass());
/* 112 */       if (cmdAroundAdvice != null) {
/* 113 */         final long tempRequestId = this.atomicTemptempRequestId.incrementAndGet();
/*     */         
/* 115 */         Object[] args = cmdAroundAdvice.before(tempRequestId, CmdAroundAdvice.CmdRequestType.AsyncSingleTcSingleRequest, new Object[] { deviceName, cmdBindingObj, tcResponseCallback, reqSessionId });
/*     */ 
/*     */ 
/*     */         
/* 119 */         if (args != null) {
/* 120 */           this.nccClient.sendAsyncRequest((String)args[0], args[1], new TcResponseCallback()
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 public void onResponse(String reqSessionId, String deviceName, TcResponse tcResponse)
/*     */                 {
/* 128 */                   TcResponse newTcResponse = cmdAroundAdvice.after(tempRequestId, deviceName, tcResponse);
/* 129 */                   tcResponseCallback.onResponse(reqSessionId, deviceName, (newTcResponse != null) ? newTcResponse : tcResponse);
/*     */                 }
/*     */               }(String)args[3]);
/*     */         }
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 139 */     this.nccClient.sendAsyncRequest(deviceName, cmdBindingObj, tcResponseCallback, reqSessionId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendAsyncRequest(String deviceName, Object cmdBindingObj, final TcResponseCallback tcResponseCallback, String reqSessionId, long timeout) {
/* 149 */     if (this.adviceMap.size() > 0) {
/* 150 */       final CmdAroundAdvice cmdAroundAdvice = this.adviceMap.get(cmdBindingObj.getClass());
/* 151 */       if (cmdAroundAdvice != null) {
/* 152 */         final long tempRequestId = this.atomicTemptempRequestId.incrementAndGet();
/*     */         
/* 154 */         Object[] args = cmdAroundAdvice.before(tempRequestId, CmdAroundAdvice.CmdRequestType.AsyncSingleTcSingleRequest, new Object[] {
/*     */ 
/*     */ 
/*     */               
/* 158 */               deviceName, cmdBindingObj, tcResponseCallback, reqSessionId, Long.valueOf(timeout)
/*     */             });
/* 160 */         if (args != null) {
/* 161 */           this.nccClient.sendAsyncRequest((String)args[0], args[1], new TcResponseCallback()
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 public void onResponse(String reqSessionId, String deviceName, TcResponse tcResponse)
/*     */                 {
/* 169 */                   TcResponse newTcResponse = cmdAroundAdvice.after(tempRequestId, deviceName, tcResponse);
/* 170 */                   tcResponseCallback.onResponse(reqSessionId, deviceName, (newTcResponse != null) ? newTcResponse : tcResponse);
/*     */                 }
/* 175 */               }(String)args[3], ((Long)args[4]).longValue());
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 181 */     this.nccClient.sendAsyncRequest(deviceName, cmdBindingObj, tcResponseCallback, reqSessionId, timeout);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendAsyncMultipleRequests(String deviceName, Object[] cmdBindingObjs, final TcResponseCallback tcResponseCallback) {
/* 188 */     if (this.adviceMap.size() > 0) {
/* 189 */       final CmdAroundAdvice cmdAroundAdvice = this.adviceMap.get(cmdBindingObjs[0].getClass());
/* 190 */       if (cmdAroundAdvice != null) {
/* 191 */         final long tempRequestId = this.atomicTemptempRequestId.incrementAndGet();
/*     */         
/* 193 */         Object[] args = cmdAroundAdvice.before(tempRequestId, CmdAroundAdvice.CmdRequestType.AsyncSingleTcMultipleRequest, new Object[] { deviceName, cmdBindingObjs, tcResponseCallback });
/*     */ 
/*     */ 
/*     */         
/* 197 */         if (args != null) {
/* 198 */           this.nccClient.sendAsyncMultipleRequests((String)args[0], (Object[])args[1], new TcResponseCallback()
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 public void onResponse(String reqSessionId, String deviceName, TcResponse tcResponse)
/*     */                 {
/* 206 */                   TcResponse newTcResponse = cmdAroundAdvice.after(tempRequestId, deviceName, tcResponse);
/* 207 */                   tcResponseCallback.onResponse(reqSessionId, deviceName, (newTcResponse != null) ? newTcResponse : tcResponse);
/*     */                 }
/*     */               });
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 215 */     this.nccClient.sendAsyncMultipleRequests(deviceName, cmdBindingObjs, tcResponseCallback);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendAsyncMultipleRequests(String deviceName, Object[] cmdBindingObjs, final TcResponseCallback tcResponseCallback, String reqSessionId) {
/* 224 */     if (this.adviceMap.size() > 0) {
/* 225 */       final CmdAroundAdvice cmdAroundAdvice = this.adviceMap.get(cmdBindingObjs[0].getClass());
/* 226 */       if (cmdAroundAdvice != null) {
/* 227 */         final long tempRequestId = this.atomicTemptempRequestId.incrementAndGet();
/*     */         
/* 229 */         Object[] args = cmdAroundAdvice.before(tempRequestId, CmdAroundAdvice.CmdRequestType.AsyncSingleTcMultipleRequest, new Object[] { deviceName, cmdBindingObjs, tcResponseCallback, reqSessionId });
/*     */ 
/*     */ 
/*     */         
/* 233 */         if (args != null) {
/* 234 */           this.nccClient.sendAsyncMultipleRequests((String)args[0], (Object[])args[1], new TcResponseCallback()
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 public void onResponse(String reqSessionId, String deviceName, TcResponse tcResponse)
/*     */                 {
/* 242 */                   TcResponse newTcResponse = cmdAroundAdvice.after(tempRequestId, deviceName, tcResponse);
/* 243 */                   tcResponseCallback.onResponse(reqSessionId, deviceName, (newTcResponse != null) ? newTcResponse : tcResponse);
/*     */                 }
/*     */               }(String)args[3]);
/*     */         }
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 253 */     this.nccClient.sendAsyncMultipleRequests(deviceName, cmdBindingObjs, tcResponseCallback, reqSessionId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendAsyncMultipleRequests(String deviceName, Object[] cmdBindingObjs, final TcResponseCallback tcResponseCallback, String reqSessionId, long timeout) {
/* 264 */     if (this.adviceMap.size() > 0) {
/* 265 */       final CmdAroundAdvice cmdAroundAdvice = this.adviceMap.get(cmdBindingObjs[0].getClass());
/* 266 */       if (cmdAroundAdvice != null) {
/* 267 */         final long tempRequestId = this.atomicTemptempRequestId.incrementAndGet();
/*     */         
/* 269 */         Object[] args = cmdAroundAdvice.before(tempRequestId, CmdAroundAdvice.CmdRequestType.AsyncSingleTcMultipleRequest, new Object[] {
/*     */ 
/*     */ 
/*     */               
/* 273 */               deviceName, cmdBindingObjs, tcResponseCallback, reqSessionId, Long.valueOf(timeout)
/*     */             });
/* 275 */         if (args != null) {
/* 276 */           this.nccClient.sendAsyncMultipleRequests((String)args[0], (Object[])args[1], new TcResponseCallback()
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 public void onResponse(String reqSessionId, String deviceName, TcResponse tcResponse)
/*     */                 {
/* 284 */                   TcResponse newTcResponse = cmdAroundAdvice.after(tempRequestId, deviceName, tcResponse);
/* 285 */                   tcResponseCallback.onResponse(reqSessionId, deviceName, (newTcResponse != null) ? newTcResponse : tcResponse);
/*     */                 }
/* 290 */               }(String)args[3], ((Long)args[4]).longValue());
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 296 */     this.nccClient.sendAsyncMultipleRequests(deviceName, cmdBindingObjs, tcResponseCallback, reqSessionId, timeout);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendAsyncRequest2MulipleTc(String[] deviceNames, Object cmdBindingObj, final TcResponseCallback tcResponseCallback) {
/* 303 */     if (this.adviceMap.size() > 0) {
/* 304 */       final CmdAroundAdvice cmdAroundAdvice = this.adviceMap.get(cmdBindingObj.getClass());
/* 305 */       if (cmdAroundAdvice != null) {
/* 306 */         final long tempRequestId = this.atomicTemptempRequestId.incrementAndGet();
/*     */         
/* 308 */         Object[] args = cmdAroundAdvice.before(tempRequestId, CmdAroundAdvice.CmdRequestType.AsyncMultipleTcSingleRequest, new Object[] { deviceNames, cmdBindingObj, tcResponseCallback });
/*     */ 
/*     */ 
/*     */         
/* 312 */         if (args != null) {
/* 313 */           this.nccClient.sendAsyncRequest2MulipleTc((String[])args[0], args[1], new TcResponseCallback()
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 public void onResponse(String reqSessionId, String deviceName, TcResponse tcResponse)
/*     */                 {
/* 321 */                   TcResponse newTcResponse = cmdAroundAdvice.after(tempRequestId, deviceName, tcResponse);
/* 322 */                   tcResponseCallback.onResponse(reqSessionId, deviceName, (newTcResponse != null) ? newTcResponse : tcResponse);
/*     */                 }
/*     */               });
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 331 */     this.nccClient.sendAsyncRequest2MulipleTc(deviceNames, cmdBindingObj, tcResponseCallback);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendAsyncRequest2MulipleTc(String[] deviceNames, Object cmdBindingObj, final TcResponseCallback tcResponseCallback, String reqSessionId) {
/* 340 */     if (this.adviceMap.size() > 0) {
/* 341 */       final CmdAroundAdvice cmdAroundAdvice = this.adviceMap.get(cmdBindingObj.getClass());
/* 342 */       if (cmdAroundAdvice != null) {
/* 343 */         final long tempRequestId = this.atomicTemptempRequestId.incrementAndGet();
/*     */         
/* 345 */         Object[] args = cmdAroundAdvice.before(tempRequestId, CmdAroundAdvice.CmdRequestType.AsyncMultipleTcSingleRequest, new Object[] { deviceNames, cmdBindingObj, tcResponseCallback, reqSessionId });
/*     */ 
/*     */ 
/*     */         
/* 349 */         if (args != null) {
/* 350 */           this.nccClient.sendAsyncRequest2MulipleTc((String[])args[0], args[1], new TcResponseCallback()
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 public void onResponse(String reqSessionId, String deviceName, TcResponse tcResponse)
/*     */                 {
/* 358 */                   TcResponse newTcResponse = cmdAroundAdvice.after(tempRequestId, deviceName, tcResponse);
/* 359 */                   tcResponseCallback.onResponse(reqSessionId, deviceName, (newTcResponse != null) ? newTcResponse : tcResponse);
/*     */                 }
/*     */               }(String)args[3]);
/*     */         }
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 369 */     this.nccClient.sendAsyncRequest2MulipleTc(deviceNames, cmdBindingObj, tcResponseCallback, reqSessionId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendAsyncRequest2MulipleTc(String[] deviceNames, Object cmdBindingObj, final TcResponseCallback tcResponseCallback, String reqSessionId, long timeout) {
/* 380 */     if (this.adviceMap.size() > 0) {
/* 381 */       final CmdAroundAdvice cmdAroundAdvice = this.adviceMap.get(cmdBindingObj.getClass());
/* 382 */       if (cmdAroundAdvice != null) {
/* 383 */         final long tempRequestId = this.atomicTemptempRequestId.incrementAndGet();
/*     */         
/* 385 */         Object[] args = cmdAroundAdvice.before(tempRequestId, CmdAroundAdvice.CmdRequestType.AsyncMultipleTcSingleRequest, new Object[] {
/*     */ 
/*     */ 
/*     */               
/* 389 */               deviceNames, cmdBindingObj, tcResponseCallback, reqSessionId, Long.valueOf(timeout)
/*     */             });
/* 391 */         if (args != null) {
/* 392 */           this.nccClient.sendAsyncRequest2MulipleTc((String[])args[0], args[1], new TcResponseCallback()
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 public void onResponse(String reqSessionId, String deviceName, TcResponse tcResponse)
/*     */                 {
/* 400 */                   TcResponse newTcResponse = cmdAroundAdvice.after(tempRequestId, deviceName, tcResponse);
/* 401 */                   tcResponseCallback.onResponse(reqSessionId, deviceName, (newTcResponse != null) ? newTcResponse : tcResponse);
/*     */                 }
/* 406 */               }(String)args[3], ((Long)args[4]).longValue());
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 412 */     this.nccClient.sendAsyncRequest2MulipleTc(deviceNames, cmdBindingObj, tcResponseCallback, reqSessionId, timeout);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean resetTcConnection(String deviceName) {
/* 418 */     return this.nccClient.resetTcConnection(deviceName);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\ncc\client\NccEnabledRemoteImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */