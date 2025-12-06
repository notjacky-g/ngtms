/*     */ package com.hwacom.ngtms.base.crypto;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.SecretKeyFactory;
/*     */ import javax.crypto.spec.DESedeKeySpec;
/*     */ import org.apache.commons.codec.binary.Base64;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TripleDESUtils
/*     */ {
/*  23 */   private static String TripleDESMethodTransformation = "DESede/ECB/PKCS5Padding";
/*  24 */   private static String TripleDESMethod = "DESede";
/*  25 */   private static String key = "p5(z+TeKjfM7kHPB(+ZTYFyePsrb0D31gJpD}}014X08q(qMCWL7bJB0AkMSg7GN";
/*     */   
/*  27 */   private static Logger logger = LoggerFactory.getLogger(TripleDESUtils.class);
/*     */ 
/*     */ 
/*     */   
/*     */   public static String encrypt(String plainText) {
/*     */     try {
/*  33 */       DESedeKeySpec pass = new DESedeKeySpec(key.getBytes());
/*  34 */       SecretKeyFactory skf = SecretKeyFactory.getInstance(TripleDESMethod);
/*  35 */       SecretKey deskey = skf.generateSecret(pass);
/*     */       
/*  37 */       Cipher c1 = Cipher.getInstance(TripleDESMethodTransformation);
/*  38 */       c1.init(1, deskey);
/*  39 */       byte[] text = c1.doFinal(plainText.getBytes());
/*  40 */       return encodeBase64(text);
/*  41 */     } catch (Exception e) {
/*  42 */       logger.error(e.getMessage(), e);
/*     */       
/*  44 */       return null;
/*     */     } 
/*     */   }
/*     */   public static String decrypt(String base64text) {
/*  48 */     String value = null;
/*     */     try {
/*  50 */       DESedeKeySpec pass = new DESedeKeySpec(key.getBytes());
/*  51 */       SecretKeyFactory skf = SecretKeyFactory.getInstance(TripleDESMethod);
/*  52 */       SecretKey deskey = skf.generateSecret(pass);
/*     */       
/*  54 */       Cipher c1 = Cipher.getInstance(TripleDESMethodTransformation);
/*  55 */       c1.init(2, deskey);
/*  56 */       byte[] text = decodeBase64(base64text);
/*  57 */       byte[] decryptContent = c1.doFinal(text);
/*  58 */       value = new String(decryptContent);
/*  59 */     } catch (Exception e) {
/*  60 */       logger.error(e.getMessage(), e);
/*     */     } 
/*  62 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String encodeBase64(byte[] bytes) throws UnsupportedEncodingException {
/*  67 */     byte[] enc = Base64.encodeBase64(bytes);
/*  68 */     return new String(enc);
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] decodeBase64(String text) throws UnsupportedEncodingException {
/*  73 */     byte[] bin = Base64.decodeBase64(text.getBytes());
/*  74 */     return bin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String generateRandomString(int minLength, int maxLength, int minLCaseCount, int minUCaseCount, int minNumCount, int minSpecialCount) {
/*     */     char[] randomString;
/*  85 */     String LCaseChars = "abcdefgijkmnopqrstwxyz";
/*  86 */     String UCaseChars = "ABCDEFGHJKLMNPQRSTWXYZ";
/*  87 */     String NumericChars = "1234567890";
/*  88 */     String SpecialChars = "!()-+{}^";
/*     */     
/*  90 */     Map<String, Integer> charGroupsUsed = new HashMap<>();
/*  91 */     charGroupsUsed.put("lcase", Integer.valueOf(minLCaseCount));
/*  92 */     charGroupsUsed.put("ucase", Integer.valueOf(minUCaseCount));
/*  93 */     charGroupsUsed.put("num", Integer.valueOf(minNumCount));
/*  94 */     charGroupsUsed.put("special", Integer.valueOf(minSpecialCount));
/*     */     
/*  96 */     byte[] randomBytes = new byte[4];
/*     */     
/*  98 */     (new Random()).nextBytes(randomBytes);
/*     */     
/* 100 */     int seed = (randomBytes[0] & Byte.MAX_VALUE) << 24 | randomBytes[1] << 16 | randomBytes[2] << 8 | randomBytes[3];
/*     */ 
/*     */     
/* 103 */     Random random = new Random(seed);
/*     */     
/* 105 */     int randomIndex = -1;
/*     */     
/* 107 */     if (minLength < maxLength) {
/* 108 */       randomIndex = random.nextInt(maxLength - minLength + 1) + minLength;
/* 109 */       randomString = new char[randomIndex];
/*     */     } else {
/* 111 */       randomString = new char[minLength];
/*     */     } 
/* 113 */     int requiredCharactersLeft = minLCaseCount + minUCaseCount + minNumCount + minSpecialCount;
/* 114 */     for (int i = 0; i < randomString.length; i++) {
/* 115 */       String selectableChars = "";
/* 116 */       if (requiredCharactersLeft < randomString.length - i) {
/* 117 */         selectableChars = LCaseChars + UCaseChars + NumericChars + SpecialChars;
/*     */       } else {
/* 119 */         for (Map.Entry<String, Integer> charGroup : charGroupsUsed.entrySet()) {
/* 120 */           if (((Integer)charGroup.getValue()).intValue() > 0) {
/* 121 */             if ("lcase".equals(charGroup.getKey())) {
/* 122 */               selectableChars = selectableChars + LCaseChars; continue;
/* 123 */             }  if ("ucase".equals(charGroup.getKey())) {
/* 124 */               selectableChars = selectableChars + UCaseChars; continue;
/* 125 */             }  if ("num".equals(charGroup.getKey())) {
/* 126 */               selectableChars = selectableChars + NumericChars; continue;
/* 127 */             }  if ("special".equals(charGroup.getKey())) {
/* 128 */               selectableChars = selectableChars + SpecialChars;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/* 133 */       randomIndex = random.nextInt(selectableChars.length() - 1);
/* 134 */       char nextChar = selectableChars.charAt(randomIndex);
/*     */       
/* 136 */       randomString[i] = nextChar;
/* 137 */       if (LCaseChars.indexOf(nextChar) > -1) {
/* 138 */         charGroupsUsed.put("lcase", 
/* 139 */             Integer.valueOf(((Integer)charGroupsUsed.get("lcase")).intValue() - 1));
/* 140 */         if (((Integer)charGroupsUsed.get("lcase")).intValue() >= 0) {
/* 141 */           requiredCharactersLeft--;
/*     */         }
/* 143 */       } else if (UCaseChars.indexOf(nextChar) > -1) {
/* 144 */         charGroupsUsed.put("ucase", 
/* 145 */             Integer.valueOf(((Integer)charGroupsUsed.get("ucase")).intValue() - 1));
/* 146 */         if (((Integer)charGroupsUsed.get("ucase")).intValue() >= 0) {
/* 147 */           requiredCharactersLeft--;
/*     */         }
/* 149 */       } else if (NumericChars.indexOf(nextChar) > -1) {
/* 150 */         charGroupsUsed.put("num", 
/* 151 */             Integer.valueOf(((Integer)charGroupsUsed.get("num")).intValue() - 1));
/* 152 */         if (((Integer)charGroupsUsed.get("num")).intValue() >= 0) {
/* 153 */           requiredCharactersLeft--;
/*     */         }
/* 155 */       } else if (SpecialChars.indexOf(nextChar) > -1) {
/* 156 */         charGroupsUsed.put("special", 
/* 157 */             Integer.valueOf(((Integer)charGroupsUsed.get("special")).intValue() - 1));
/* 158 */         if (((Integer)charGroupsUsed.get("special")).intValue() >= 0) {
/* 159 */           requiredCharactersLeft--;
/*     */         }
/*     */       } 
/*     */     } 
/* 163 */     return new String(randomString);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\crypto\TripleDESUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */