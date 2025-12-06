/*    */ package com.hwacom.ngtms.cmdprot.cmdfmtdef;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandFormatException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public CommandFormatException() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public CommandFormatException(String message)
/*    */   {
/* 22 */     super(message);
/*    */   }
/*    */   
/*    */   public CommandFormatException(String message, Throwable cause) {
/* 26 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public CommandFormatException(Throwable cause) {
/* 30 */     super(cause);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\CommandFormatException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */