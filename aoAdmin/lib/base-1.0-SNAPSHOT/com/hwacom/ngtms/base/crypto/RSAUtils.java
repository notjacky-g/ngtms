/*     */ package com.hwacom.ngtms.base.crypto;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.Provider;
/*     */ import java.security.PublicKey;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.Security;
/*     */ import java.security.Signature;
/*     */ import java.security.SignatureException;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.RSAPrivateCrtKeySpec;
/*     */ import java.security.spec.RSAPublicKeySpec;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.IllegalBlockSizeException;
/*     */ import javax.crypto.NoSuchPaddingException;
/*     */ import org.bouncycastle.asn1.ASN1ObjectIdentifier;
/*     */ import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
/*     */ import org.bouncycastle.asn1.x509.DigestInfo;
/*     */ import org.bouncycastle.jce.provider.BouncyCastleProvider;
/*     */ import org.bouncycastle.util.encoders.Base64;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RSAUtils
/*     */ {
/*  42 */   private static Logger logger = LoggerFactory.getLogger(RSAUtils.class);
/*     */   
/*  44 */   private int keyLength = 2048;
/*     */   private boolean useBouncyCastleProvider;
/*  46 */   private Provider provider = null;
/*     */   private KeyPair keyPair;
/*     */   
/*     */   public RSAUtils() throws NoSuchAlgorithmException {
/*  50 */     this(2048, false, null);
/*     */   }
/*     */   
/*     */   public RSAUtils(int keyLength) throws NoSuchAlgorithmException {
/*  54 */     this.keyLength = keyLength;
/*  55 */     this.useBouncyCastleProvider = true;
/*  56 */     genKeyPair();
/*     */   }
/*     */ 
/*     */   
/*     */   public RSAUtils(int keyLength, boolean useBouncyCastleProvider, KeyPair keyPair) throws NoSuchAlgorithmException {
/*  61 */     this.keyLength = keyLength;
/*  62 */     this.useBouncyCastleProvider = useBouncyCastleProvider;
/*  63 */     if (null == keyPair) {
/*  64 */       genKeyPair();
/*     */     } else {
/*  66 */       this.keyPair = keyPair;
/*     */     } 
/*     */   }
/*     */   
/*     */   public KeyPair getKeyPair() {
/*  71 */     return this.keyPair;
/*     */   }
/*     */   
/*     */   public void setKeyPair(KeyPair keyPair) {
/*  75 */     this.keyPair = keyPair;
/*     */   }
/*     */   
/*     */   public String getPublicKeyXmlString() throws InvalidKeySpecException, NoSuchAlgorithmException {
/*  79 */     KeyFactory kf = KeyFactory.getInstance("RSA");
/*  80 */     RSAPublicKeySpec ks = kf.<RSAPublicKeySpec>getKeySpec(this.keyPair.getPublic(), RSAPublicKeySpec.class);
/*  81 */     StringBuilder sb = new StringBuilder();
/*  82 */     sb.append("<RSAKeyValue>");
/*  83 */     sb.append("    <Modulus>" + toBase64String(ks.getModulus().toByteArray()) + "</Modulus>");
/*  84 */     sb.append("    <Exponent>" + 
/*  85 */         toBase64String(ks.getPublicExponent().toByteArray()) + "</Exponent>");
/*  86 */     sb.append("</RSAKeyValue>");
/*  87 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public String getPrivateKeyXmlString() throws InvalidKeySpecException, NoSuchAlgorithmException {
/*  91 */     KeyFactory kf = KeyFactory.getInstance("RSA");
/*  92 */     RSAPrivateCrtKeySpec ks = kf.<RSAPrivateCrtKeySpec>getKeySpec(this.keyPair.getPrivate(), RSAPrivateCrtKeySpec.class);
/*  93 */     StringBuilder sb = new StringBuilder();
/*  94 */     sb.append("<RSAKeyValue>");
/*  95 */     sb.append("    <Modulus>" + toBase64String(ks.getModulus().toByteArray()) + "</Modulus>");
/*  96 */     sb.append("    <Exponent>" + 
/*  97 */         toBase64String(ks.getPublicExponent().toByteArray()) + "</Exponent>");
/*  98 */     sb.append("    <P>" + toBase64String(ks.getPrimeP().toByteArray()) + "</P>");
/*  99 */     sb.append("    <Q>" + toBase64String(ks.getPrimeQ().toByteArray()) + "</Q>");
/* 100 */     sb.append("    <DP>" + toBase64String(ks.getPrimeExponentP().toByteArray()) + "</DP>");
/* 101 */     sb.append("    <DQ>" + toBase64String(ks.getPrimeExponentQ().toByteArray()) + "</DQ>");
/* 102 */     sb.append("    <InverseQ>" + 
/* 103 */         toBase64String(ks.getCrtCoefficient().toByteArray()) + "</InverseQ>");
/* 104 */     sb.append("    <D>" + toBase64String(ks.getPrivateExponent().toByteArray()) + "</D>");
/* 105 */     sb.append("</RSAKeyValue>");
/* 106 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private String toBase64String(byte[] bytes) {
/* 110 */     int length = bytes.length;
/* 111 */     if (length % 2 != 0 && bytes[0] == 0) {
/* 112 */       bytes = Arrays.copyOfRange(bytes, 1, length);
/*     */     }
/* 114 */     return Base64.toBase64String(bytes);
/*     */   }
/*     */   
/*     */   public KeyPair genKeyPair() throws NoSuchAlgorithmException {
/* 118 */     if (this.useBouncyCastleProvider) {
/* 119 */       this.provider = (Provider)new BouncyCastleProvider();
/* 120 */       Security.addProvider(this.provider);
/*     */     } 
/*     */     
/* 123 */     KeyPairGenerator keyPairGenerator = null;
/* 124 */     if (null != this.provider) {
/* 125 */       keyPairGenerator = KeyPairGenerator.getInstance("RSA", this.provider);
/*     */     } else {
/* 127 */       keyPairGenerator = KeyPairGenerator.getInstance("RSA");
/*     */     } 
/* 129 */     keyPairGenerator.initialize(this.keyLength, new SecureRandom());
/*     */     
/* 131 */     this.keyPair = keyPairGenerator.generateKeyPair();
/* 132 */     return this.keyPair;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] signature(String plainText, HashingAlgorithms algrithm) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
/*     */     String algorithmString;
/* 139 */     Signature signatureProvider = null;
/*     */     
/* 141 */     if (algrithm == HashingAlgorithms.SHA1) {
/* 142 */       algorithmString = "SHA1withRSA";
/*     */     } else {
/* 144 */       algorithmString = "SHA256withRSA";
/*     */     } 
/* 146 */     if (null != this.provider) {
/* 147 */       signatureProvider = Signature.getInstance(algorithmString, this.provider);
/*     */     } else {
/* 149 */       signatureProvider = Signature.getInstance(algorithmString);
/*     */     } 
/* 151 */     signatureProvider.initSign(this.keyPair.getPrivate());
/*     */     
/* 153 */     signatureProvider.update(plainText.getBytes("UTF-8"));
/* 154 */     byte[] signature = signatureProvider.sign();
/*     */     
/* 156 */     logger.debug("Signature Output :\n\t" + new String(Base64.encode(signature)));
/*     */     
/* 158 */     return signature;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] messageDigest(String plainText, HashingAlgorithms algrithm) throws NoSuchAlgorithmException, IOException {
/* 164 */     MessageDigest messageDigestProvider = null;
/* 165 */     if (null != this.provider) {
/* 166 */       messageDigestProvider = MessageDigest.getInstance(algrithm.getName(), this.provider);
/*     */     } else {
/* 168 */       messageDigestProvider = MessageDigest.getInstance(algrithm.getName());
/*     */     } 
/* 170 */     messageDigestProvider.update(plainText.getBytes("UTF-8"));
/*     */     
/* 172 */     byte[] hash = messageDigestProvider.digest();
/*     */     
/* 174 */     AlgorithmIdentifier digestAlgorithm = new AlgorithmIdentifier(new ASN1ObjectIdentifier("1.3.14.3.2.26"), null);
/*     */     
/* 176 */     DigestInfo digestInfo = new DigestInfo(digestAlgorithm, hash);
/* 177 */     byte[] hashToEncrypt = digestInfo.getEncoded();
/*     */     
/* 179 */     logger.debug("MessageDigest Output :\n\t" + new String(Base64.encode(hashToEncrypt)));
/* 180 */     return hashToEncrypt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String encrypt(String plainText) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
/* 186 */     Cipher rsaCipher = null;
/* 187 */     if (null != this.provider) {
/* 188 */       rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", this.provider);
/*     */     } else {
/* 190 */       rsaCipher = Cipher.getInstance("RSA");
/*     */     } 
/*     */ 
/*     */     
/* 194 */     PublicKey pk = this.keyPair.getPublic();
/*     */ 
/*     */     
/* 197 */     rsaCipher.init(1, pk);
/*     */ 
/*     */     
/* 200 */     byte[] encByte = rsaCipher.doFinal(plainText.getBytes("UTF-8"));
/*     */ 
/*     */     
/* 203 */     byte[] base64Cipher = Base64.encode(encByte);
/* 204 */     String value = new String(base64Cipher);
/*     */     
/* 206 */     logger.debug("RSA encrypt Output :\n\t" + value);
/*     */     
/* 208 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String decrypt(String cipherText) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
/* 214 */     Cipher rsaCipher = null;
/* 215 */     if (null != this.provider) {
/* 216 */       rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", this.provider);
/*     */     } else {
/* 218 */       rsaCipher = Cipher.getInstance("RSA");
/*     */     } 
/*     */     
/* 221 */     PrivateKey pvk = this.keyPair.getPrivate();
/*     */ 
/*     */     
/* 224 */     rsaCipher.init(2, pvk);
/*     */     
/* 226 */     byte[] cipherBytes = Base64.decode(cipherText);
/*     */     
/* 228 */     byte[] decByte = rsaCipher.doFinal(cipherBytes);
/* 229 */     String value = new String(decByte, "UTF-8");
/*     */     
/* 231 */     logger.debug("RSA decrypt Output :\n\t" + value);
/* 232 */     return value;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\crypto\RSAUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */