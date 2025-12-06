/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.security.authentication.AuthenticationDetailsSource;
/*    */ import org.springframework.security.web.authentication.WebAuthenticationDetails;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MfaWebAuthenticationDetailsSource
/*    */   implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails>
/*    */ {
/*    */   public WebAuthenticationDetails buildDetails(HttpServletRequest context)
/*    */   {
/* 18 */     return new MfaAuthenticationDetails(context);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\MfaWebAuthenticationDetailsSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */