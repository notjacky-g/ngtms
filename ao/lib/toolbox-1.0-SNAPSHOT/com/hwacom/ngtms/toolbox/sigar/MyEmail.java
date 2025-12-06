/*     */ package com.hwacom.ngtms.toolbox.sigar;
/*     */ 
/*     */ import java.util.Properties;
/*     */ import javax.mail.Message;
/*     */ import javax.mail.Message.RecipientType;
/*     */ import javax.mail.MessagingException;
/*     */ import javax.mail.Session;
/*     */ import javax.mail.Transport;
/*     */ import javax.mail.internet.InternetAddress;
/*     */ import javax.mail.internet.MimeMessage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MyEmail
/*     */ {
/*     */   private Properties properties;
/*     */   
/*     */   public MyEmail(Properties properties)
/*     */   {
/*  22 */     this.properties = properties;
/*     */   }
/*     */   
/*     */   public void send(String data, String subject)
/*     */   {
/*  27 */     String to = this.properties.getProperty("mail.to", "root@localhost");
/*     */     
/*     */ 
/*  30 */     String from = this.properties.getProperty("mail.from", "root@localhost");
/*     */     
/*     */ 
/*  33 */     String host = this.properties.getProperty("mail.host", "localhost");
/*     */     
/*     */ 
/*  36 */     this.properties.setProperty("mail.smtp.host", host);
/*     */     
/*     */ 
/*  39 */     Session session = Session.getDefaultInstance(this.properties);
/*     */     
/*     */     try
/*     */     {
/*  43 */       MimeMessage message = new MimeMessage(session);
/*     */       
/*     */ 
/*  46 */       message.setFrom(new InternetAddress(from));
/*     */       
/*     */ 
/*  49 */       message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
/*     */       
/*     */ 
/*  52 */       message.setSubject("Warning!! [CronJob Auto detection] Warning!! " + subject);
/*     */       
/*     */ 
/*  55 */       message.setText(data);
/*     */       
/*     */ 
/*  58 */       Transport.send(message);
/*     */     }
/*     */     catch (MessagingException localMessagingException) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void sendHtml(String data, String subject, boolean isHtml)
/*     */   {
/*  67 */     String to = this.properties.getProperty("mail.to", "root@localhost");
/*  68 */     String[] recipients = to.split(",");
/*     */     
/*     */ 
/*  71 */     String from = this.properties.getProperty("mail.from", "root@localhost");
/*     */     
/*     */ 
/*  74 */     String host = this.properties.getProperty("mail.host", "localhost");
/*     */     
/*     */ 
/*  77 */     this.properties.setProperty("mail.smtp.host", host);
/*     */     
/*     */ 
/*  80 */     Session session = Session.getDefaultInstance(this.properties, null);
/*  81 */     session.setDebug(false);
/*     */     try
/*     */     {
/*  84 */       Message message = new MimeMessage(session);
/*     */       
/*     */ 
/*  87 */       InternetAddress addressFrom = new InternetAddress(from);
/*  88 */       message.setFrom(addressFrom);
/*     */       
/*  90 */       InternetAddress[] addressTo = new InternetAddress[recipients.length];
/*  91 */       for (int i = 0; i < recipients.length; i++) {
/*  92 */         addressTo[i] = new InternetAddress(recipients[i]);
/*     */       }
/*  94 */       message.setRecipients(Message.RecipientType.TO, addressTo);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 101 */       message.setSubject("Warning!! [CronJob Auto detection] Warning!! " + subject);
/*     */       
/*     */ 
/* 104 */       if (isHtml) {
/* 105 */         message.setContent(data, "text/html");
/*     */       } else {
/* 107 */         message.setContent(data, "text/plain");
/*     */       }
/*     */       
/* 110 */       Transport.send(message);
/*     */     }
/*     */     catch (MessagingException localMessagingException) {}
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\toolbox-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\toolbox\sigar\MyEmail.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */