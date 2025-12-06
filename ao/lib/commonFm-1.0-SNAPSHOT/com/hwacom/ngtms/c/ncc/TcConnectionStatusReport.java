/*    */ package com.hwacom.ngtms.c.ncc;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ public class TcConnectionStatusReport
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private ConnectionStatus connectionStatus;
/*    */   private long timestamp;
/*    */   
/*    */   public static enum ConnectionStatus
/*    */   {
/* 15 */     DISCONNECTED, 
/* 16 */     CONNECTED;
/*    */     
/*    */ 
/*    */     private ConnectionStatus() {}
/*    */   }
/*    */   
/*    */ 
/*    */   public ConnectionStatus getConnectionStatus()
/*    */   {
/* 25 */     return this.connectionStatus;
/*    */   }
/*    */   
/*    */   public void setConnectionStatus(ConnectionStatus connectionStatus) {
/* 29 */     this.connectionStatus = connectionStatus;
/*    */   }
/*    */   
/*    */   public long getTimestamp() {
/* 33 */     return this.timestamp;
/*    */   }
/*    */   
/*    */   public void setTimestamp(long timestamp) {
/* 37 */     this.timestamp = timestamp;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\ncc\TcConnectionStatusReport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */