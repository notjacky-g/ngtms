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
/*    */   public static String getUserLoginName(Authentication authentication)
/*    */   {
/* 16 */     Object principal = authentication.getPrincipal();
/*    */     String userName;
/* 18 */     String userName; if ((principal instanceof UserDetails)) {
/* 19 */       userName = ((UserDetails)principal).getUsername(); } else { String userName;
/* 20 */       if ((principal instanceof String)) {
/* 21 */         userName = (String)principal; } else { String userName;
/* 22 */         if ((principal instanceof Principal)) {
/* 23 */           userName = ((Principal)principal).getName();
/*    */         } else
/* 25 */           userName = null;
/*    */       } }
/* 27 */     return userName;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcManager-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\am\server\AuthenticationUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */