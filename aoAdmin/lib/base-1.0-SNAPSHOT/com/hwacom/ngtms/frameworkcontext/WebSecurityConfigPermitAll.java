/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.security.authentication.event.LoggerListener;
/*    */ import org.springframework.security.config.annotation.web.builders.HttpSecurity;
/*    */ import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
/*    */ import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/*    */ import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ @EnableWebSecurity
/*    */ public class WebSecurityConfigPermitAll
/*    */   extends WebSecurityConfigurerAdapter
/*    */ {
/*    */   @Bean
/*    */   public LoggerListener loggerListener() {
/* 22 */     return new LoggerListener();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void configure(HttpSecurity http) throws Exception {
/* 27 */     http.csrf().disable();
/* 28 */     ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.authorizeRequests().anyRequest()).permitAll();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\WebSecurityConfigPermitAll.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */