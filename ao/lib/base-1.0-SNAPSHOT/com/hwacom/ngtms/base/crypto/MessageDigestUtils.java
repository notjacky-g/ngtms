/*    */ package com.hwacom.ngtms.base.crypto;
/*    */ 
/*    */ import java.security.MessageDigest;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MessageDigestUtils
/*    */ {
/* 17 */   private static Logger logger = LoggerFactory.getLogger(MessageDigestUtils.class);
/*    */   
/*    */   public static String encryptSHA1(String s) {
/* 20 */     return encrypt(s, HashingAlgorithms.SHA1);
/*    */   }
/*    */   
/*    */   public static String encryptSHA256(String s) {
/* 24 */     return encrypt(s, HashingAlgorithms.SHA256);
/*    */   }
/*    */   
/*    */   public static String encryptMD5(String s) {
/* 28 */     return encrypt(s, HashingAlgorithms.MD5);
/*    */   }
/*    */   
/*    */   private static String encrypt(String s, HashingAlgorithms algorithm) {
/* 32 */     MessageDigest sha = null;
/*    */     try {
/* 34 */       sha = MessageDigest.getInstance(algorithm.getName());
/* 35 */       sha.update(s.getBytes());
/*    */     } catch (Exception e) {
/* 37 */       logger.error(e.getMessage(), e);
/* 38 */       return "";
/*    */     }
/* 40 */     return byte2hex(sha.digest());
/*    */   }
/*    */   
/*    */   private static String byte2hex(byte[] b) {
/* 44 */     String hs = "";
/* 45 */     String stmp = "";
/* 46 */     for (int n = 0; n < b.length; n++) {
/* 47 */       stmp = Integer.toHexString(b[n] & 0xFF);
/* 48 */       if (stmp.length() == 1) hs = hs + "0" + stmp; else {
/* 49 */         hs = hs + stmp;
/*    */       }
/*    */     }
/* 52 */     return hs;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\crypto\MessageDigestUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */