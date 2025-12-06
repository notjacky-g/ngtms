/*    */ package com.hwacom.ngtms.hcce.core.exception;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FmeStaleVersionException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public FmeStaleVersionException() {}
/*    */   
/*    */   public FmeStaleVersionException(String s) {
/* 18 */     super(s);
/*    */   }
/*    */   
/*    */   public FmeStaleVersionException(Throwable cause) {
/* 22 */     super(cause);
/*    */   }
/*    */   
/*    */   public FmeStaleVersionException(String message, Throwable cause) {
/* 26 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\core\exception\FmeStaleVersionException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */