/*    */ package com.hwacom.ngtms.ao.am.websocket;
/*    */ 
/*    */ import com.hwacom.ngtms.ao.am.AoEP;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AlarmWebSocket
/*    */ {
/*    */   private String uri;
/*    */   private WebSocketHandler handler;
/*    */   
/*    */   public AlarmWebSocket(WebSocketHandler handler) {
/* 16 */     this.handler = handler;
/* 17 */     this.uri = "ws://" + AoEP.webSocketIp + ":" + AoEP.webSocketPort + "/websocket/ao/alarm";
/*    */   }
/*    */   
/*    */   public native void send(String paramString);
/*    */   
/*    */   public native void close();
/*    */   
/*    */   public native boolean isClosed();
/*    */   
/*    */   public native void open();
/*    */   
/*    */   public static interface WebSocketHandler {
/*    */     void onOpen();
/*    */     
/*    */     void onMessage(String param1String);
/*    */     
/*    */     void onClose(int param1Int, String param1String);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\websocket\AlarmWebSocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */