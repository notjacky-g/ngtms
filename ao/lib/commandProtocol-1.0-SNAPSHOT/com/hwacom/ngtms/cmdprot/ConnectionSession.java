/*    */ package com.hwacom.ngtms.cmdprot;
/*    */ 
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import java.net.SocketAddress;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ScheduledFuture;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConnectionSession
/*    */ {
/* 26 */   private static Logger logger = LoggerFactory.getLogger(ConnectionSession.class);
/*    */   protected ChannelHandlerContext ctx;
/* 28 */   private Map<String, ScheduledFuture<?>> scheduledFutureMap = new HashMap();
/*    */   private SocketAddress remoteAddress;
/*    */   private SocketAddress localAddress;
/*    */   private long startTime;
/*    */   
/*    */   public ConnectionSession() {}
/*    */   
/*    */   public ConnectionSession(ChannelHandlerContext ctx)
/*    */   {
/* 37 */     this.ctx = ctx;
/* 38 */     this.startTime = System.currentTimeMillis();
/* 39 */     this.remoteAddress = ctx.channel().remoteAddress();
/* 40 */     this.localAddress = ctx.channel().localAddress();
/*    */   }
/*    */   
/*    */   public synchronized void addScheduledFuture(String scheduleTaskName, ScheduledFuture<?> s) {
/* 44 */     ScheduledFuture<?> s1 = (ScheduledFuture)this.scheduledFutureMap.get(scheduleTaskName);
/* 45 */     if (s1 != null) s1.cancel(true);
/* 46 */     this.scheduledFutureMap.put(scheduleTaskName, s);
/*    */   }
/*    */   
/*    */   public synchronized void removeScheduledFuture(String scheduleTaskName) {
/* 50 */     ScheduledFuture<?> s1 = (ScheduledFuture)this.scheduledFutureMap.remove(scheduleTaskName);
/* 51 */     if (s1 != null) { s1.cancel(true);
/*    */     } else {
/* 53 */       logger.warn("The schedule task {} has no Schedule Future", scheduleTaskName);
/*    */     }
/*    */   }
/*    */   
/*    */   public synchronized void close() {
/* 58 */     for (ScheduledFuture<?> s : this.scheduledFutureMap.values()) {
/* 59 */       s.cancel(true);
/*    */     }
/* 61 */     this.ctx.close();
/*    */   }
/*    */   
/*    */   public ChannelHandlerContext getCtx() {
/* 65 */     return this.ctx;
/*    */   }
/*    */   
/*    */   public SocketAddress getRemoteAddress() {
/* 69 */     return this.remoteAddress;
/*    */   }
/*    */   
/*    */   public SocketAddress getLocalAddress() {
/* 73 */     return this.localAddress;
/*    */   }
/*    */   
/*    */   public long getStartTime() {
/* 77 */     return this.startTime;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\ConnectionSession.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */