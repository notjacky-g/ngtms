/*    */ package com.hwacom.ngtms.cmdprot.cmdfmtdef;
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
/*    */ public class BadFrameException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public BadFrameException() {}
/*    */   
/*    */   public BadFrameException(String message) {
/* 21 */     super(message);
/*    */   }
/*    */   
/*    */   public BadFrameException(String message, Throwable cause) {
/* 25 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public BadFrameException(Throwable cause) {
/* 29 */     super(cause);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\BadFrameException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */