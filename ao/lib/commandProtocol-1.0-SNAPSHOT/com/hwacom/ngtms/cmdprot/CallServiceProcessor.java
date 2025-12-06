/*     */ package com.hwacom.ngtms.cmdprot;
/*     */ 
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.ImmediateEventExecutor;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.concurrent.ScheduledFuture;
/*     */ import java.util.HashMap;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ public class CallServiceProcessor
/*     */ {
/*     */   private ChannelHandlerContext ctx;
/*  29 */   private HashMap<String, CallContext> callContextMap = new HashMap();
/*     */   private boolean destroyed;
/*     */   private int timeoutCounter;
/*  32 */   private int maxTimeoutTimes = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CallServiceProcessor(ChannelHandlerContext ctx)
/*     */   {
/*  40 */     this.ctx = ctx;
/*     */   }
/*     */   
/*     */   public int getMaxTimeoutTimes() {
/*  44 */     return this.maxTimeoutTimes;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMaxTimeoutTimes(int maxTimeoutTimes)
/*     */   {
/*  52 */     this.maxTimeoutTimes = maxTimeoutTimes;
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
/*     */   public Promise<Object> sendRequest(String id, Object command, long requestTimeout)
/*     */   {
/*  66 */     return sendRequest(id, command, requestTimeout, null);
/*     */   }
/*     */   
/*     */   public Promise<Object> sendRequest(final String id, final Object command, final long requestTimeout, Promise<Object> promise)
/*     */   {
/*  71 */     if (this.destroyed) { return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  76 */     if (!this.ctx.executor().inEventLoop()) {
/*  77 */       Promise<Object> promise1 = ImmediateEventExecutor.INSTANCE.newPromise();
/*  78 */       this.ctx.executor()
/*  79 */         .execute(new Runnable()
/*     */         {
/*     */ 
/*     */           public void run()
/*     */           {
/*  83 */             CallServiceProcessor.this.sendRequest(id, command, requestTimeout, this.val$promise1);
/*     */           }
/*  85 */         });
/*  86 */       return promise1;
/*     */     }
/*  88 */     CallContext callContext = (CallContext)this.callContextMap.remove(id);
/*  89 */     if (callContext != null) {
/*  90 */       callContext.scheduledFuture.cancel(true);
/*  91 */       callContext.promise.setFailure(new CommandSessionException("Duplicated Session ID: " + id));
/*  92 */     } else { callContext = new CallContext(); }
/*  93 */     if (promise == null) promise = ImmediateEventExecutor.INSTANCE.newPromise();
/*  94 */     callContext.promise = promise;
/*     */     
/*     */ 
/*  97 */     callContext.scheduledFuture = this.ctx.executor().schedule(new Runnable()
/*     */     {
/*     */ 
/*     */       public void run()
/*     */       {
/* 101 */         CallServiceProcessor.CallContext callContext = (CallServiceProcessor.CallContext)CallServiceProcessor.this.callContextMap.remove(id);
/* 102 */         callContext.promise.setFailure(new TimeoutException("Waiting response timeout, " + requestTimeout + " ms."));
/*     */         
/*     */ 
/* 105 */         if (CallServiceProcessor.this.maxTimeoutTimes > 0) {
/* 106 */           CallServiceProcessor.access$208(CallServiceProcessor.this);
/* 107 */           if (CallServiceProcessor.this.timeoutCounter > CallServiceProcessor.this.maxTimeoutTimes) CallServiceProcessor.this.ctx.close(); } } }, requestTimeout, TimeUnit.MILLISECONDS);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 114 */     this.callContextMap.put(id, callContext);
/* 115 */     this.ctx.writeAndFlush(command);
/* 116 */     return callContext.promise;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void recevieResponse(String id, Object command)
/*     */   {
/* 125 */     if (this.destroyed) return;
/* 126 */     CallContext callContext = (CallContext)this.callContextMap.remove(id);
/* 127 */     this.timeoutCounter = 0;
/* 128 */     if (callContext != null) {
/* 129 */       callContext.scheduledFuture.cancel(true);
/* 130 */       callContext.promise.setSuccess(command);
/*     */     }
/*     */   }
/*     */   
/*     */   public void destroy()
/*     */   {
/* 136 */     if (this.destroyed) return;
/* 137 */     this.destroyed = true;
/* 138 */     for (CallContext callContext : this.callContextMap.values()) {
/* 139 */       callContext.scheduledFuture.cancel(true);
/* 140 */       callContext.promise.setFailure(new DisconnectedException("CallServiceProcessor destroyed!"));
/*     */     }
/* 142 */     this.callContextMap.clear();
/*     */   }
/*     */   
/*     */   static class CallContext
/*     */   {
/*     */     Promise<Object> promise;
/*     */     ScheduledFuture<?> scheduledFuture;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\CallServiceProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */