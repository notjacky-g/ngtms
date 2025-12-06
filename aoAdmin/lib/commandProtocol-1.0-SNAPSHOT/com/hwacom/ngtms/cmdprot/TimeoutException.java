/*    */ package com.hwacom.ngtms.cmdprot;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TimeoutException
/*    */   extends CmdProtException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public TimeoutException() {}
/*    */   
/*    */   public TimeoutException(String message) {
/* 17 */     super(message);
/*    */   }
/*    */   
/*    */   public TimeoutException(String message, Throwable cause) {
/* 21 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public TimeoutException(Throwable cause) {
/* 25 */     super(cause);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\TimeoutException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */