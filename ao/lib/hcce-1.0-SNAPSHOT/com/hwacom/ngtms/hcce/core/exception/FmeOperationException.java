/*    */ package com.hwacom.ngtms.hcce.core.exception;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FmeOperationException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 2354673388180530241L;
/*    */   
/*    */ 
/*    */ 
/*    */   public FmeOperationException() {}
/*    */   
/*    */ 
/*    */ 
/*    */   public FmeOperationException(String s)
/*    */   {
/* 18 */     super(s);
/*    */   }
/*    */   
/*    */   public FmeOperationException(Throwable cause) {
/* 22 */     super(cause);
/*    */   }
/*    */   
/*    */   public FmeOperationException(String message, Throwable cause) {
/* 26 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\core\exception\FmeOperationException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */