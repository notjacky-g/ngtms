/*    */ package com.hwacom.ngtms.base.httpclient;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnauthorizedException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */ 
/*    */   private String wwwAuthenticateHeaderValue;
/*    */   
/*    */ 
/*    */   public UnauthorizedException(String wwwAuthenticateHeaderValue)
/*    */   {
/* 16 */     super(wwwAuthenticateHeaderValue);
/* 17 */     this.wwwAuthenticateHeaderValue = wwwAuthenticateHeaderValue;
/*    */   }
/*    */   
/*    */   public String getWwwAuthenticateHeaderValue() {
/* 21 */     return this.wwwAuthenticateHeaderValue;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\httpclient\UnauthorizedException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */