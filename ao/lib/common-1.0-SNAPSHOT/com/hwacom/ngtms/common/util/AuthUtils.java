/*    */ package com.hwacom.ngtms.common.util;
/*    */ 
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class AuthUtils
/*    */ {
/*    */   public static String extractUserLogin(HttpServletRequest request)
/*    */   {
/*  9 */     return decryptUserLogin(request.getHeader("encryptedUserLogin"));
/*    */   }
/*    */   
/*    */   public static String decryptUserLogin(String encryptedUserLogin) {
/* 13 */     return com.hwacom.ngtms.base.crypto.TripleDESUtils.decrypt(encryptedUserLogin);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\util\AuthUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */