/*    */ package com.hwacom.ngtms.cmdprot.cmdfmtdef;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RedundancyCheckException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public RedundancyCheckException() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public RedundancyCheckException(String message)
/*    */   {
/* 21 */     super(message);
/*    */   }
/*    */   
/*    */   public RedundancyCheckException(String message, Throwable cause) {
/* 25 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public RedundancyCheckException(Throwable cause) {
/* 29 */     super(cause);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\RedundancyCheckException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */