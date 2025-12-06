/*    */ package com.hwacom.ngtms.cmdprot;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandSessionException
/*    */   extends CmdProtException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */ 
/*    */ 
/*    */   public CommandSessionException() {}
/*    */   
/*    */ 
/*    */   public CommandSessionException(String message)
/*    */   {
/* 17 */     super(message);
/*    */   }
/*    */   
/*    */   public CommandSessionException(String message, Throwable cause) {
/* 21 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public CommandSessionException(Throwable cause) {
/* 25 */     super(cause);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\CommandSessionException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */