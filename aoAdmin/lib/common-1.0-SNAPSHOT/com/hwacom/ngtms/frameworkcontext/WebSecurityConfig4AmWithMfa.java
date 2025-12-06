/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.security.authentication.AuthenticationProvider;
/*    */ import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
/*    */ import org.springframework.security.config.annotation.web.builders.HttpSecurity;
/*    */ import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
/*    */ import org.springframework.security.crypto.factory.PasswordEncoderFactories;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ @EnableWebSecurity
/*    */ public class WebSecurityConfig4AmWithMfa
/*    */   extends WebSecurityConfig4Am
/*    */ {
/* 22 */   private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig4AmWithMfa.class);
/*    */ 
/*    */   
/*    */   protected void configure(HttpSecurity http) throws Exception {
/* 26 */     this.useJosso = Boolean.valueOf(false);
/* 27 */     super.configure(http);
/* 28 */     http.formLogin().authenticationDetailsSource(new MfaWebAuthenticationDetailsSource());
/*    */   }
/*    */ 
/*    */   
/*    */   public AuthenticationProvider authenticationProvider() {
/* 33 */     DaoAuthenticationProvider provider = new MfaDaoAuthenticationProvider(this.hzClient);
/* 34 */     provider.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
/* 35 */     provider.setUserDetailsService(new WebSecurityConfig4Am.HzUserDetailsService(this, this.hzClient));
/* 36 */     return (AuthenticationProvider)provider;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\WebSecurityConfig4AmWithMfa.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */