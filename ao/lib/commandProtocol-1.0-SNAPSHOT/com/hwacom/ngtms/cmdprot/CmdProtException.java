/*    */ package com.hwacom.ngtms.cmdprot;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CmdProtException
/*    */   extends IOException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public CmdProtException() {}
/*    */   
/*    */   public CmdProtException(String message)
/*    */   {
/* 19 */     super(message);
/*    */   }
/*    */   
/*    */   public CmdProtException(String message, Throwable cause) {
/* 23 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public CmdProtException(Throwable cause) {
/* 27 */     super(cause);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\CmdProtException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */