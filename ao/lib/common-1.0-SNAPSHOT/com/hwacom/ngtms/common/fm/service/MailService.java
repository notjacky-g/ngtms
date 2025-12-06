/*    */ package com.hwacom.ngtms.common.fm.service;
/*    */ 
/*    */ import com.hwacom.ngtms.common.fm.model.User;
/*    */ import freemarker.template.Configuration;
/*    */ import freemarker.template.TemplateExceptionHandler;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.util.Optional;
/*    */ import javax.mail.MessagingException;
/*    */ import javax.mail.internet.MimeMessage;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.core.io.ByteArrayResource;
/*    */ import org.springframework.mail.javamail.JavaMailSender;
/*    */ import org.springframework.mail.javamail.MimeMessageHelper;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class MailService
/*    */ {
/* 28 */   private static final Logger logger = LoggerFactory.getLogger(MailService.class);
/*    */   @Autowired
/*    */   private JavaMailSender javaMailSender;
/*    */   
/*    */   public Configuration newConfiguration(Optional<String> optTemplateDirectory) {
/* 33 */     return newConfiguration(optTemplateDirectory, getClass(), "templates");
/*    */   }
/*    */   
/*    */   public Configuration newConfiguration(Optional<String> optTemplateDirectory, Class resourceLoaderClass, String basePackagePath)
/*    */   {
/* 38 */     Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
/* 39 */     boolean defaultTemplateLoading = true;
/* 40 */     if (optTemplateDirectory.isPresent()) {
/*    */       try {
/* 42 */         cfg.setDirectoryForTemplateLoading(new File((String)optTemplateDirectory.get()));
/* 43 */         defaultTemplateLoading = false;
/*    */       } catch (IOException e) {
/* 45 */         logger.warn("Init MfaService bean failed!", e);
/*    */       }
/*    */     }
/* 48 */     if (defaultTemplateLoading) {
/* 49 */       cfg.setClassForTemplateLoading(resourceLoaderClass, basePackagePath);
/*    */     }
/* 51 */     cfg.setDefaultEncoding("UTF-8");
/* 52 */     cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
/* 53 */     cfg.setLogTemplateExceptions(false);
/* 54 */     cfg.setWrapUncheckedExceptions(true);
/* 55 */     return cfg;
/*    */   }
/*    */   
/*    */   public void sendMail(User user, String subject, String mailContent) throws MessagingException {
/* 59 */     sendMail(user, subject, mailContent, false, new MailAttachment[0]);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void sendMail(User user, String subject, String mailContent, boolean inlineImage, MailAttachment... attachments)
/*    */     throws MessagingException
/*    */   {
/* 69 */     String mail = user.getMail();
/* 70 */     if ((mail != null) && (!mail.isEmpty())) {
/* 71 */       MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
/* 72 */       MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, attachments.length != 0, "UTF-8");
/*    */       
/* 74 */       mimeMessage.setSubject(subject);
/* 75 */       helper.setText(mailContent, true);
/* 76 */       helper.setTo(new String[] { mail });
/* 77 */       for (MailAttachment attachment : attachments) {
/* 78 */         if (inlineImage) {
/* 79 */           helper.addInline(attachment
/* 80 */             .getAttachmentFilename(), new ByteArrayResource(attachment
/* 81 */             .getBytes()), attachment
/* 82 */             .getContentType());
/*    */         } else {
/* 84 */           helper.addAttachment(attachment
/* 85 */             .getAttachmentFilename(), new ByteArrayResource(attachment
/* 86 */             .getBytes()), attachment
/* 87 */             .getContentType());
/*    */         }
/*    */       }
/* 90 */       this.javaMailSender.send(mimeMessage);
/* 91 */       logger.debug("Send email successful. login: '{}'", user.getLogin());
/*    */     } else {
/* 93 */       logger.info("Mail is empty. login: '{}'", user.getLogin());
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\service\MailService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */