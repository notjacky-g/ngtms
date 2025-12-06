/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import org.springframework.beans.factory.annotation.Value;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.core.annotation.Order;
/*    */ import org.springframework.security.config.annotation.web.builders.HttpSecurity;
/*    */ import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
/*    */ import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/*    */ import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
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
/*    */ @EnableWebSecurity
/*    */ @Order(50)
/*    */ public class WebSecurityConfigContentSecurityPolicy
/*    */   extends WebSecurityConfigurerAdapter
/*    */ {
/*    */   @Value("${contentSecurityPolicy.pattern:NULL}")
/*    */   private String pattern;
/*    */   @Value("${contentSecurityPolicy.frame-ancestors:'none'}")
/*    */   private String frameAncestors;
/*    */   
/*    */   protected void configure(HttpSecurity http)
/*    */     throws Exception
/*    */   {
/* 34 */     if (!"NULL".equals(this.pattern))
/*    */     {
/*    */ 
/* 37 */       http.regexMatcher(this.pattern).headers().contentSecurityPolicy("frame-ancestors " + this.frameAncestors);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\WebSecurityConfigContentSecurityPolicy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */