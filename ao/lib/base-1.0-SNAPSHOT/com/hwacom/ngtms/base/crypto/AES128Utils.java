/*     */ package com.hwacom.ngtms.base.crypto;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SecureRandom;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.IllegalBlockSizeException;
/*     */ import javax.crypto.KeyGenerator;
/*     */ import javax.crypto.NoSuchPaddingException;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import org.apache.commons.codec.binary.Base64;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AES128Utils
/*     */ {
/*     */   private SecretKeySpec skeySpec;
/*     */   
/*     */   public AES128Utils()
/*     */     throws NoSuchAlgorithmException
/*     */   {
/*  27 */     this(null);
/*     */   }
/*     */   
/*     */   public AES128Utils(SecretKeySpec skeySpec) throws NoSuchAlgorithmException {
/*  31 */     if (null == skeySpec) {
/*  32 */       generateKey();
/*     */     } else {
/*  34 */       this.skeySpec = skeySpec;
/*     */     }
/*     */   }
/*     */   
/*     */   public SecretKeySpec getSkeySpec() {
/*  39 */     return this.skeySpec;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public SecretKeySpec generateKey()
/*     */     throws NoSuchAlgorithmException
/*     */   {
/*  48 */     KeyGenerator kgen = KeyGenerator.getInstance("AES");
/*  49 */     kgen.init(128, new SecureRandom());
/*  50 */     SecretKey skey = kgen.generateKey();
/*  51 */     byte[] key = skey.getEncoded();
/*  52 */     this.skeySpec = new SecretKeySpec(key, "AES");
/*  53 */     return this.skeySpec;
/*     */   }
/*     */   
/*     */ 
/*     */   public String encrypt(String plainText)
/*     */     throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
/*     */   {
/*  60 */     Cipher cipher = Cipher.getInstance("AES");
/*  61 */     cipher.init(1, this.skeySpec);
/*  62 */     byte[] text = cipher.doFinal(plainText.getBytes());
/*     */     
/*  64 */     return encodeBase64(text);
/*     */   }
/*     */   
/*     */   public String decrypt(String base64text)
/*     */     throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
/*     */   {
/*  70 */     Cipher cipher = Cipher.getInstance("AES");
/*  71 */     cipher.init(2, this.skeySpec);
/*     */     
/*  73 */     byte[] text = decodeBase64(base64text);
/*  74 */     byte[] msg = cipher.doFinal(text);
/*     */     
/*  76 */     return new String(msg);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String encrypt(String plainText, String base64Key)
/*     */     throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
/*     */   {
/*  87 */     byte[] key = decodeBase64(base64Key);
/*  88 */     SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
/*  89 */     Cipher cipher = Cipher.getInstance("AES");
/*  90 */     cipher.init(1, skeySpec);
/*  91 */     byte[] text = cipher.doFinal(plainText.getBytes());
/*     */     
/*  93 */     return encodeBase64(text);
/*     */   }
/*     */   
/*     */ 
/*     */   public static String decrypt(String base64text, String base64Key)
/*     */     throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
/*     */   {
/* 100 */     byte[] key = decodeBase64(base64Key);
/* 101 */     SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
/* 102 */     Cipher cipher = Cipher.getInstance("AES");
/* 103 */     cipher.init(2, skeySpec);
/*     */     
/* 105 */     byte[] text = decodeBase64(base64text);
/* 106 */     byte[] msg = cipher.doFinal(text);
/*     */     
/* 108 */     return new String(msg);
/*     */   }
/*     */   
/*     */   public static String encodeBase64(byte[] bytes) throws UnsupportedEncodingException
/*     */   {
/* 113 */     byte[] enc = Base64.encodeBase64(bytes);
/* 114 */     return new String(enc);
/*     */   }
/*     */   
/*     */   public static byte[] decodeBase64(String text) throws UnsupportedEncodingException
/*     */   {
/* 119 */     byte[] bin = Base64.decodeBase64(text.getBytes());
/* 120 */     return bin;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\crypto\AES128Utils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */