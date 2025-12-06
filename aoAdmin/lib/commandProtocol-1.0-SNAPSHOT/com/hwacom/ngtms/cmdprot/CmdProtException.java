/*    */ package com.hwacom.ngtms.cmdprot;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
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
/*    */   public CmdProtException(String message) {
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


/* Location:              C:\User\\user\Desktop\lib\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\CmdProtException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */