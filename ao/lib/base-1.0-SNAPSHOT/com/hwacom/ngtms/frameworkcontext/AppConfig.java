/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import java.util.Properties;
/*    */ import javax.annotation.Resource;
/*    */ import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
/*    */ import org.jasypt.util.password.rfc2307.RFC2307SSHAPasswordEncryptor;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.core.env.Environment;
/*    */ import org.springframework.mail.javamail.JavaMailSenderImpl;
/*    */ import org.springframework.transaction.annotation.EnableTransactionManagement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ @EnableTransactionManagement
/*    */ public class AppConfig
/*    */ {
/*    */   private static final String DATASOURCE_PASSWORD_ENCRYPTOR_KEY = "com.hwacom.ngtms.release";
/*    */   @Resource
/*    */   private Environment environment;
/*    */   
/*    */   @Bean
/*    */   public StandardPBEStringEncryptor standardPBEStringEncryptor()
/*    */   {
/* 35 */     StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
/* 36 */     encryptor.setPassword("com.hwacom.ngtms.release");
/* 37 */     return encryptor;
/*    */   }
/*    */   
/*    */   @Bean
/*    */   public RFC2307SSHAPasswordEncryptor ldapSSHAPasswordEncryptor() {
/* 42 */     RFC2307SSHAPasswordEncryptor encryptor = new RFC2307SSHAPasswordEncryptor();
/* 43 */     return encryptor;
/*    */   }
/*    */   
/*    */   @Bean
/*    */   public JavaMailSenderImpl mailSender() {
/* 48 */     JavaMailSenderImpl sender = new JavaMailSenderImpl();
/* 49 */     sender.setHost(this.environment.getRequiredProperty("mail.host"));
/* 50 */     sender.setPort(((Integer)this.environment.getRequiredProperty("mail.port", Integer.class)).intValue());
/* 51 */     String name = this.environment.getRequiredProperty("mail.username");
/* 52 */     if ((name != null) && (name.length() > 0)) {
/* 53 */       sender.setUsername(name);
/* 54 */       sender.setPassword(this.environment.getRequiredProperty("mail.password"));
/*    */     }
/*    */     
/* 57 */     Properties p = new Properties();
/* 58 */     String proxyIp = this.environment.getProperty("http.proxy.ip");
/* 59 */     if ((proxyIp != null) && (proxyIp.length() > 0)) {
/* 60 */       p.setProperty("mail.smtp.proxy.host", proxyIp);
/* 61 */       p.setProperty("mail.smtp.proxy.port", 
/*    */       
/* 63 */         (String)Optional.ofNullable(this.environment.getProperty("http.proxy.port")).orElse("3128"));
/*    */     }
/* 65 */     String auth = this.environment.getRequiredProperty("mail.smtp.auth");
/* 66 */     if ((auth != null) && (auth.length() > 0)) p.setProperty("mail.smtp.auth", auth);
/* 67 */     String tls = this.environment.getRequiredProperty("mail.smtp.starttls.enable");
/* 68 */     if ((tls != null) && (tls.length() > 0)) p.setProperty("mail.smtp.starttls.enable", tls);
/* 69 */     p.setProperty("mail.debug", this.environment.getRequiredProperty("mail.debug"));
/* 70 */     sender.setJavaMailProperties(p);
/* 71 */     return sender;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\AppConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */