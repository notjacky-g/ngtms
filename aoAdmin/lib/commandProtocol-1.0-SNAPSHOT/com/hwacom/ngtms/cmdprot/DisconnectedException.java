/*    */ package com.hwacom.ngtms.cmdprot;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DisconnectedException
/*    */   extends CmdProtException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public DisconnectedException() {}
/*    */   
/*    */   public DisconnectedException(String message) {
/* 17 */     super(message);
/*    */   }
/*    */   
/*    */   public DisconnectedException(String message, Throwable cause) {
/* 21 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public DisconnectedException(Throwable cause) {
/* 25 */     super(cause);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\DisconnectedException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */