/*     */ package com.hwacom.ngtms.frameworkcontext;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.context.annotation.Bean;
/*     */ import org.springframework.context.annotation.Configuration;
/*     */ import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
/*     */ import org.springframework.security.config.annotation.web.builders.HttpSecurity;
/*     */ import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
/*     */ import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/*     */ import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
/*     */ import org.springframework.security.core.AuthenticationException;
/*     */ import org.springframework.security.core.userdetails.UserDetailsService;
/*     */ import org.springframework.security.provisioning.InMemoryUserDetailsManager;
/*     */ import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Configuration
/*     */ @EnableWebSecurity
/*     */ public class WebSecurityConfigByHttpBasicAuthentication
/*     */   extends WebSecurityConfigurerAdapter
/*     */ {
/*  36 */   private static Logger logger = LoggerFactory.getLogger(WebSecurityConfigByHttpBasicAuthentication.class);
/*     */   
/*  38 */   private static AuthenticationEntryPoint authEntryPoint = new AuthenticationEntryPoint();
/*     */   
/*     */   @Value("${security.basic.authentication.file:}")
/*     */   private String basicAuthenticationFile;
/*     */ 
/*     */   
/*     */   protected void configure(HttpSecurity http) throws Exception {
/*  45 */     ((HttpSecurity)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((HttpSecurity)http.csrf()
/*  46 */       .disable())
/*  47 */       .authorizeRequests()
/*  48 */       .anyRequest())
/*  49 */       .authenticated()
/*  50 */       .and())
/*  51 */       .httpBasic()
/*  52 */       .authenticationEntryPoint((org.springframework.security.web.AuthenticationEntryPoint)authEntryPoint);
/*     */   }
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
/*  58 */     auth.userDetailsService((UserDetailsService)inMemoryUserDetailsManager());
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
/*  63 */     Properties p = new Properties();
/*  64 */     InputStream input = null;
/*     */     try {
/*  66 */       logger.debug("InMemoryUserDetailsManager basicAuthenticationFile: {}", this.basicAuthenticationFile);
/*     */       
/*  68 */       if (this.basicAuthenticationFile == null || "".equalsIgnoreCase(this.basicAuthenticationFile.trim())) {
/*  69 */         this
/*  70 */           .basicAuthenticationFile = System.getProperty("user.dir") + File.separatorChar + "users.properties";
/*     */       }
/*  72 */       input = new FileInputStream(this.basicAuthenticationFile);
/*  73 */       p.load(input);
/*  74 */     } catch (IOException ex) {
/*  75 */       logger.error(ex.getMessage(), ex);
/*     */     } finally {
/*  77 */       if (input != null) {
/*     */         try {
/*  79 */           input.close();
/*  80 */         } catch (IOException iOException) {}
/*     */       }
/*     */     } 
/*     */     
/*  84 */     return new InMemoryUserDetailsManager(p);
/*     */   }
/*     */ 
/*     */   
/*     */   static class AuthenticationEntryPoint
/*     */     extends BasicAuthenticationEntryPoint
/*     */   {
/*     */     public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx) throws IOException, ServletException {
/*  92 */       response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
/*  93 */       response.setStatus(401);
/*  94 */       PrintWriter writer = response.getWriter();
/*  95 */       writer.println("HTTP Status 401 - " + authEx.getMessage());
/*     */     }
/*     */ 
/*     */     
/*     */     public void afterPropertiesSet() throws Exception {
/* 100 */       setRealmName("DeveloperStack");
/* 101 */       super.afterPropertiesSet();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\WebSecurityConfigByHttpBasicAuthentication.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */