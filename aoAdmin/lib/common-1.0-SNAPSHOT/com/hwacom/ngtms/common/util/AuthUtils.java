/*    */ package com.hwacom.ngtms.common.util;
/*    */ 
/*    */ import com.hwacom.ngtms.base.crypto.TripleDESUtils;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class AuthUtils
/*    */ {
/*    */   public static String extractUserLogin(HttpServletRequest request) {
/*  9 */     return decryptUserLogin(request.getHeader("encryptedUserLogin"));
/*    */   }
/*    */   
/*    */   public static String decryptUserLogin(String encryptedUserLogin) {
/* 13 */     return TripleDESUtils.decrypt(encryptedUserLogin);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\commo\\util\AuthUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */