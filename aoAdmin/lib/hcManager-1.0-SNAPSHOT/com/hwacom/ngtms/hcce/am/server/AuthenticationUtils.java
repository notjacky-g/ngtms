/*    */ package com.hwacom.ngtms.hcce.am.server;
/*    */ 
/*    */ import java.security.Principal;
/*    */ import org.springframework.security.core.Authentication;
/*    */ import org.springframework.security.core.userdetails.UserDetails;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuthenticationUtils
/*    */ {
/*    */   public static String getUserLoginName(Authentication authentication) {
/*    */     String userName;
/* 16 */     Object principal = authentication.getPrincipal();
/*    */     
/* 18 */     if (principal instanceof UserDetails) {
/* 19 */       userName = ((UserDetails)principal).getUsername();
/* 20 */     } else if (principal instanceof String) {
/* 21 */       userName = (String)principal;
/* 22 */     } else if (principal instanceof Principal) {
/* 23 */       userName = ((Principal)principal).getName();
/*    */     } else {
/* 25 */       userName = null;
/*    */     } 
/* 27 */     return userName;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcManager-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\am\server\AuthenticationUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */