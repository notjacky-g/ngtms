/*    */ package com.hwacom.ngtms.cmdprot.cmdfmtdef;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandDefinitionException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public CommandDefinitionException() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public CommandDefinitionException(String message)
/*    */   {
/* 21 */     super(message);
/*    */   }
/*    */   
/*    */   public CommandDefinitionException(Throwable cause) {
/* 25 */     super(cause);
/*    */   }
/*    */   
/*    */   public CommandDefinitionException(String message, Throwable cause) {
/* 29 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\CommandDefinitionException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */