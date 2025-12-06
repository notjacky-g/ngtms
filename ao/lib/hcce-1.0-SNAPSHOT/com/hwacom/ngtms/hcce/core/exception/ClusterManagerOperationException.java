/*    */ package com.hwacom.ngtms.hcce.core.exception;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClusterManagerOperationException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 2354673388180530241L;
/*    */   
/*    */ 
/*    */ 
/*    */   public ClusterManagerOperationException() {}
/*    */   
/*    */ 
/*    */ 
/*    */   public ClusterManagerOperationException(String s)
/*    */   {
/* 18 */     super(s);
/*    */   }
/*    */   
/*    */   public ClusterManagerOperationException(Throwable cause) {
/* 22 */     super(cause);
/*    */   }
/*    */   
/*    */   public ClusterManagerOperationException(String message, Throwable cause) {
/* 26 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\core\exception\ClusterManagerOperationException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */