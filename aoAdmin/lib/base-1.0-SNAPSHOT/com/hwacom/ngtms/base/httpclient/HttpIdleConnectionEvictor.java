/*    */ package com.hwacom.ngtms.base.httpclient;
/*    */ 
/*    */ import org.apache.http.conn.HttpClientConnectionManager;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HttpIdleConnectionEvictor
/*    */   extends Thread
/*    */ {
/*    */   @Autowired
/*    */   private HttpClientConnectionManager httpClientConnectionManager;
/*    */   private volatile boolean shutdown;
/*    */   
/*    */   public HttpIdleConnectionEvictor() {
/* 20 */     start();
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 26 */       while (!this.shutdown) {
/* 27 */         synchronized (this) {
/* 28 */           wait(5000L);
/*    */           
/* 30 */           this.httpClientConnectionManager.closeExpiredConnections();
/*    */         } 
/*    */       } 
/* 33 */     } catch (InterruptedException interruptedException) {}
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void shutdown() {
/* 40 */     this.shutdown = true;
/* 41 */     synchronized (this) {
/* 42 */       notifyAll();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\httpclient\HttpIdleConnectionEvictor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */