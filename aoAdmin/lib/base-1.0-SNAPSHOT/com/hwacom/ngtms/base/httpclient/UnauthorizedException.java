/*    */ package com.hwacom.ngtms.base.httpclient;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnauthorizedException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String wwwAuthenticateHeaderValue;
/*    */   
/*    */   public UnauthorizedException(String wwwAuthenticateHeaderValue) {
/* 16 */     super(wwwAuthenticateHeaderValue);
/* 17 */     this.wwwAuthenticateHeaderValue = wwwAuthenticateHeaderValue;
/*    */   }
/*    */   
/*    */   public String getWwwAuthenticateHeaderValue() {
/* 21 */     return this.wwwAuthenticateHeaderValue;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\httpclient\UnauthorizedException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */