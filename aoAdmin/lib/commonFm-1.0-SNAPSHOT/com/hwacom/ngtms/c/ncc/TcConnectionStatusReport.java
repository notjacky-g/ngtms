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
/*    */   public enum ConnectionStatus
/*    */   {
/* 15 */     DISCONNECTED,
/* 16 */     CONNECTED;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ConnectionStatus getConnectionStatus() {
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


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\ncc\TcConnectionStatusReport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */