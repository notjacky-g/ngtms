/*     */ package com.hwacom.ngtms.common.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*     */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*     */ import com.hwacom.ngtms.common.fm.model.User;
/*     */ import com.hwacom.ngtms.common.util.IMapLocker;
/*     */ import dev.samstevens.totp.exceptions.QrGenerationException;
/*     */ import dev.samstevens.totp.secret.DefaultSecretGenerator;
/*     */ import dev.samstevens.totp.secret.SecretGenerator;
/*     */ import freemarker.template.Configuration;
/*     */ import freemarker.template.Template;
/*     */ import java.io.StringWriter;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.PostConstruct;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class MfaService
/*     */ {
/*  34 */   private static final Logger logger = LoggerFactory.getLogger(MfaService.class);
/*     */   
/*     */   @Autowired
/*     */   private MessageSourceExt messageSourceExt;
/*     */   
/*     */   @Autowired
/*     */   private MailService mailService;
/*     */   
/*     */   @Value("${security.mfa.authenticator.label:#{null}}")
/*     */   private String label;
/*     */   
/*     */   @Value("${security.mfa.authenticator.issuer:HwaCom}")
/*     */   private String issuer;
/*     */   
/*     */   @Value("${security.email.template.directory:#{null}}")
/*     */   private String templateDirectory;
/*     */   private Configuration cfg;
/*  51 */   private SecretGenerator secretGenerator = (SecretGenerator)new DefaultSecretGenerator();
/*     */   
/*     */   @PostConstruct
/*     */   public void init() {
/*  55 */     this.cfg = this.mailService.newConfiguration(Optional.ofNullable(this.templateDirectory));
/*     */   }
/*     */   
/*     */   public void sendSecretForExistUser() {
/*  59 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/*  60 */     for (User user : userMap.values()) {
/*     */       try {
/*  62 */         if (user.getMfaSecret() == null) {
/*  63 */           generateSecretIfAbsentAndSendEmail(user.getLogin());
/*     */         }
/*  65 */       } catch (Exception e) {
/*  66 */         logger.warn("Generate MFA secret failed! user: '{}'", user.getLogin(), e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void generateSecretIfAbsentAndSendEmail(String login) {
/*     */     try {
/*  73 */       IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/*  74 */       User user = (User)userMap.get(login);
/*  75 */       if (user.getMail() == null || user.getMail().isEmpty()) {
/*  76 */         logger.info("Skipped! User has no email. login: '{}'", login);
/*     */         return;
/*     */       } 
/*  79 */       if (user.getMfaSecret() == null) {
/*  80 */         String mfaSecret = this.secretGenerator.generate();
/*  81 */         user.setMfaSecret(mfaSecret);
/*  82 */         IMapLocker.addOrUpdateMapping(userMap, user.getLogin(), user);
/*     */       } 
/*  84 */       MfaQrCodeGenerator qrCodeGenerator = new MfaQrCodeGenerator(user, this.label, this.issuer);
/*  85 */       byte[] bytes = qrCodeGenerator.getQrCode();
/*     */       
/*  87 */       MailAttachment attachment = new MailAttachment("qrcode.png", bytes, qrCodeGenerator.getQrCodeImageMimeType());
/*  88 */       this.mailService.sendMail(user, this.messageSourceExt
/*     */           
/*  90 */           .getMessage("security.mfa.subject"), 
/*  91 */           generateMailContent(user, qrCodeGenerator.getQrCodeDataUri()), true, new MailAttachment[] { attachment });
/*     */ 
/*     */       
/*  94 */       logger.info("Generate secret and send email. login: '{}'", login);
/*  95 */     } catch (QrGenerationException e) {
/*  96 */       logger.warn("Generate qr code failed! login: '{}'", login, e);
/*  97 */     } catch (Exception e) {
/*  98 */       logger.warn("Send secret failed! login: '{}'", login, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String generateMailContent(User user, String dataUri) throws Exception {
/* 103 */     Template template = this.cfg.getTemplate("mfa.html");
/* 104 */     Map<String, Object> map = new HashMap<>();
/* 105 */     map.put("login", user.getLogin());
/* 106 */     map.put("name", user.getName());
/* 107 */     map.put("qrCode", dataUri);
/* 108 */     map.put("secretCode", user.getMfaSecret());
/* 109 */     StringWriter stringWriter = new StringWriter();
/* 110 */     template.process(map, stringWriter);
/* 111 */     return stringWriter.toString();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\service\MfaService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */