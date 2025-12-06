/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import com.hwacom.ngtms.common.security.AccessDecisionVoter4Fm;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.security.access.AccessDecisionManager;
/*    */ import org.springframework.security.access.AccessDecisionVoter;
/*    */ import org.springframework.security.access.vote.AffirmativeBased;
/*    */ import org.springframework.security.config.annotation.web.builders.HttpSecurity;
/*    */ import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
/*    */ import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/*    */ import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
/*    */ import org.springframework.security.web.header.HeaderWriter;
/*    */ import org.springframework.security.web.header.writers.StaticHeadersWriter;
/*    */ import org.springframework.security.web.header.writers.frameoptions.AllowFromStrategy;
/*    */ import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ @EnableWebSecurity
/*    */ public class WebSecurityConfig4Fm
/*    */   extends WebSecurityConfigurerAdapter
/*    */ {
/*    */   protected void configure(HttpSecurity http) throws Exception {
/* 33 */     http.csrf().disable();
/* 34 */     ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.authorizeRequests()
/* 35 */       .anyRequest())
/* 36 */       .authenticated()
/* 37 */       .accessDecisionManager(accessDecisionManager());
/* 38 */     http.headers()
/* 39 */       .addHeaderWriter((HeaderWriter)new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
/*    */     
/* 41 */     http.requestMatcher(request -> request.getServletPath().toLowerCase().contains("gwtupload"))
/* 42 */       .headers()
/* 43 */       .addHeaderWriter((HeaderWriter)new StaticHeadersWriter("Access-Control-Allow-Credentials", new String[] { "true" })
/*    */         {
/*    */           public void writeHeaders(HttpServletRequest request, HttpServletResponse response)
/*    */           {
/* 47 */             super.writeHeaders(request, response);
/* 48 */             response.addHeader("Content-Security-Policy", "frame-ancestors " + request
/* 49 */                 .getHeader("Origin"));
/* 50 */             response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
/* 51 */             response.setHeader("X-Frame-Options", "ALLOW-FROM " + request.getHeader("Origin"));
/*    */           }
/* 54 */         }).addHeaderWriter((HeaderWriter)new XFrameOptionsHeaderWriter(new AllowFromStrategy()
/*    */           {
/*    */             
/*    */             public String getAllowFromValue(HttpServletRequest request)
/*    */             {
/* 59 */               return request.getHeader("Origin");
/*    */             }
/*    */           }));
/*    */   }
/*    */   
/*    */   @Bean
/*    */   @Autowired
/*    */   public AccessDecisionManager accessDecisionManager() {
/* 67 */     List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
/*    */     
/* 69 */     AccessDecisionVoter4Fm fmAccessDecisionVoter = new AccessDecisionVoter4Fm();
/* 70 */     decisionVoters.add(fmAccessDecisionVoter);
/*    */     
/* 72 */     AffirmativeBased affirmativeBased = new AffirmativeBased(decisionVoters);
/* 73 */     return (AccessDecisionManager)affirmativeBased;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\WebSecurityConfig4Fm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */