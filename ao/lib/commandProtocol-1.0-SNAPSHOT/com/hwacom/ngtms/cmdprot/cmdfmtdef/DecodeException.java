/*    */ package com.hwacom.ngtms.cmdprot.cmdfmtdef;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DecodeException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public DecodeException() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public DecodeException(String message)
/*    */   {
/* 21 */     super(message);
/*    */   }
/*    */   
/*    */   public DecodeException(String message, Throwable cause) {
/* 25 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public DecodeException(Throwable cause) {
/* 29 */     super(cause);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\DecodeException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */