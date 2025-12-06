/*    */ package com.hwacom.ngtms.ao.context;
/*    */ 
/*    */ import com.hwacom.ngtms.common.ldap.SimpleActiveDirectory;
/*    */ import org.springframework.beans.factory.annotation.Value;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.ldap.core.ContextSource;
/*    */ import org.springframework.ldap.core.LdapTemplate;
/*    */ import org.springframework.ldap.core.support.DefaultDirObjectFactory;
/*    */ import org.springframework.ldap.core.support.LdapContextSource;
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
/*    */ public class ActiveDirectoryConfig
/*    */ {
/*    */   @Value("${security.ad.domain:NGTMS.hwacom.com}")
/*    */   private String adDomain;
/*    */   @Value("${security.ad.url:ldap://192.168.85.11:389/}")
/*    */   private String adUrl;
/*    */   @Value("${security.ad.base:DC=NGTMS,DC=hwacom,DC=com}")
/*    */   private String adBase;
/*    */   @Value("${security.ad.manager.dn:cn=Administrator,cn=Users,dc=NGTMS,dc=hwacom,dc=com}")
/*    */   private String adManagerDn;
/*    */   @Value("${security.ad.manager.secret:!QAZ2wsx}")
/*    */   private String adManagerSecret;
/*    */   
/*    */   @Bean(name = {"ldapTemplate"})
/*    */   public LdapTemplate ldapTemplate() {
/* 37 */     LdapTemplate ldapTemplate = new LdapTemplate((ContextSource)ldapContextSource());
/* 38 */     return ldapTemplate;
/*    */   }
/*    */   
/*    */   @Bean(name = {"contextSource"})
/*    */   public LdapContextSource ldapContextSource() {
/* 43 */     LdapContextSource ldapContextSource = new LdapContextSource();
/* 44 */     ldapContextSource.setUrl(this.adUrl);
/* 45 */     ldapContextSource.setBase(this.adBase);
/* 46 */     ldapContextSource.setUserDn(this.adManagerDn);
/* 47 */     ldapContextSource.setPassword(this.adManagerSecret);
/* 48 */     ldapContextSource.setReferral("follow");
/* 49 */     ldapContextSource.setPooled(true);
/* 50 */     ldapContextSource.setDirObjectFactory(DefaultDirObjectFactory.class);
/* 51 */     ldapContextSource.afterPropertiesSet();
/* 52 */     return ldapContextSource;
/*    */   }
/*    */   
/*    */   @Bean
/*    */   public SimpleActiveDirectory simpleActiveDirectory() {
/* 57 */     return new SimpleActiveDirectory();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\context\ActiveDirectoryConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */