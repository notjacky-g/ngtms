/*     */ package com.hwacom.ngtms.common.websocket;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.websocket.EncodeException;
/*     */ import javax.websocket.EndpointConfig;
/*     */ import javax.websocket.RemoteEndpoint.Async;
/*     */ import javax.websocket.RemoteEndpoint.Basic;
/*     */ import javax.websocket.Session;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.security.core.Authentication;
/*     */ import org.springframework.security.core.context.SecurityContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractServerEndpoint
/*     */ {
/*  26 */   private static final Logger logger = LoggerFactory.getLogger(AbstractServerEndpoint.class);
/*     */   
/*     */ 
/*     */   protected Session session;
/*     */   
/*  31 */   protected int timeoutInMillis = 1000;
/*     */   protected String userId;
/*     */   protected String ip;
/*     */   
/*     */   protected void setSession(Session session)
/*     */   {
/*  37 */     this.session = session;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void init(Session session, EndpointConfig config)
/*     */   {
/*  49 */     setSession(session);
/*     */     
/*  51 */     HttpSession httpSession = (HttpSession)config.getUserProperties().get(HttpSession.class.getName());
/*  52 */     if (httpSession != null)
/*     */     {
/*     */ 
/*  55 */       SecurityContext securityContext = (SecurityContext)httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
/*     */       
/*  57 */       if (securityContext != null) {
/*  58 */         this.userId = securityContext.getAuthentication().getName();
/*     */       }
/*  60 */       this.ip = ((String)httpSession.getAttribute("REMOTE_ADDR"));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized <T> void send(T message)
/*     */   {
/*     */     try
/*     */     {
/*  72 */       if (!this.session.isOpen()) {
/*  73 */         logger.warn("Session is closed. Abort send message. session: '{}', message: '{}'", this.session
/*     */         
/*  75 */           .getId(), message);
/*     */         
/*  77 */         return;
/*     */       }
/*  79 */       Future<Void> f = this.session.getAsyncRemote().sendObject(message);
/*  80 */       f.get(this.timeoutInMillis, TimeUnit.MILLISECONDS);
/*  81 */       logger.debug("WebSocket Session:'{}' asynchronously send message:'{}'", this.session
/*  82 */         .getId(), message);
/*     */     } catch (InterruptedException|ExecutionException|TimeoutException ex) {
/*  84 */       logger.warn("WebSocket Session:'{}' send message:'{}' timeouted! 1 sec.", new Object[] {this.session
/*     */       
/*  86 */         .getId(), message, ex });
/*     */     }
/*     */     catch (RuntimeException e)
/*     */     {
/*  90 */       logger.warn("WebSocket Session:'{}' asynchronously send message:'{}' failed! Resend synchronously.", new Object[] {this.session
/*     */       
/*  92 */         .getId(), message, e });
/*     */       
/*     */ 
/*  95 */       if (this.session.isOpen()) {
/*     */         try {
/*  97 */           this.session.getBasicRemote().sendObject(message);
/*     */         } catch (IOException|EncodeException e1) {
/*  99 */           logger.warn("WebSocket Session:'{}' synchronously resend message:'{}' error!", new Object[] {this.session
/*     */           
/* 101 */             .getId(), message, e });
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
/*     */ 
/*     */   protected String getUserId()
/*     */   {
/* 117 */     return this.userId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getIp()
/*     */   {
/* 128 */     return this.ip;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\websocket\AbstractServerEndpoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */