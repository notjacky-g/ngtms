/*    */ package com.hwacom.ngtms.common.fm.service;
/*    */ 
/*    */ import com.hwacom.ngtms.common.fm.model.User;
/*    */ import dev.samstevens.totp.code.HashingAlgorithm;
/*    */ import dev.samstevens.totp.exceptions.QrGenerationException;
/*    */ import dev.samstevens.totp.qr.QrData;
/*    */ import dev.samstevens.totp.qr.QrGenerator;
/*    */ import dev.samstevens.totp.qr.ZxingPngQrGenerator;
/*    */ import dev.samstevens.totp.util.Utils;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MfaQrCodeGenerator
/*    */ {
/* 21 */   private static final Logger logger = LoggerFactory.getLogger(MfaQrCodeGenerator.class);
/*    */   
/*    */   private User user;
/*    */   
/*    */   private QrData data;
/*    */   
/*    */   private QrGenerator generator;
/*    */   
/*    */   private String dataUri;
/*    */   
/*    */   private byte[] qrCode;
/*    */   
/*    */   public MfaQrCodeGenerator(User user, String label, String issuer) {
/* 34 */     this.user = user;
/* 35 */     this
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 43 */       .data = (new QrData.Builder()).label((label != null) ? label : user.getLogin()).secret(user.getMfaSecret()).issuer(issuer).algorithm(HashingAlgorithm.SHA1).digits(6).period(30).build();
/* 44 */     this.generator = (QrGenerator)new ZxingPngQrGenerator();
/*    */   }
/*    */   
/*    */   public String getQrCodeImageMimeType() {
/* 48 */     return this.generator.getImageMimeType();
/*    */   }
/*    */   
/*    */   public byte[] getQrCode() throws QrGenerationException {
/* 52 */     if (this.qrCode != null) {
/* 53 */       return this.qrCode;
/*    */     }
/* 55 */     this.qrCode = this.generator.generate(this.data);
/* 56 */     return this.qrCode;
/*    */   }
/*    */   
/*    */   public String getQrCodeDataUri() throws QrGenerationException {
/* 60 */     if (this.dataUri != null) {
/* 61 */       return this.dataUri;
/*    */     }
/* 63 */     this.dataUri = Utils.getDataUriForImage(getQrCode(), this.generator.getImageMimeType());
/* 64 */     logger.debug("Qr code for user: '{}', dataUri: '{}'", this.user.getLogin(), this.dataUri);
/* 65 */     return this.dataUri;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\service\MfaQrCodeGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */