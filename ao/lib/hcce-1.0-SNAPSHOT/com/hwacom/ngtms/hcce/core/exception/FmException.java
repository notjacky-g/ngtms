/*    */ package com.hwacom.ngtms.hcce.core.exception;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FmException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 2354673388180530241L;
/*    */   
/*    */ 
/*    */ 
/*    */   public FmException() {}
/*    */   
/*    */ 
/*    */ 
/*    */   public FmException(String s)
/*    */   {
/* 18 */     super(s);
/*    */   }
/*    */   
/*    */   public FmException(Throwable cause) {
/* 22 */     super(cause);
/*    */   }
/*    */   
/*    */   public FmException(String message, Throwable cause) {
/* 26 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\core\exception\FmException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */