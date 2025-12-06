/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.security.web.authentication.WebAuthenticationDetails;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MfaAuthenticationDetails
/*    */   extends WebAuthenticationDetails
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String verificationCode;
/*    */   
/*    */   public MfaAuthenticationDetails(HttpServletRequest request)
/*    */   {
/* 19 */     super(request);
/* 20 */     this.verificationCode = request.getParameter("verificationCode");
/*    */   }
/*    */   
/*    */   public String getVerificationCode() {
/* 24 */     return this.verificationCode;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\MfaAuthenticationDetails.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */