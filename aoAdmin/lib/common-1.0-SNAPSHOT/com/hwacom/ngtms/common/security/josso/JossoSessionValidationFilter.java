/*     */ package com.hwacom.ngtms.common.security.josso;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.servlet.Filter;
/*     */ import javax.servlet.FilterChain;
/*     */ import javax.servlet.FilterConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.josso.gateway.jaxws.JAXWSWebserviceGatewayServiceLocator;
/*     */ import org.josso.gateway.session.exceptions.NoSuchSessionException;
/*     */ import org.josso.gateway.session.exceptions.SSOSessionException;
/*     */ import org.josso.gateway.session.service.SSOSessionManagerService;
/*     */ import org.springframework.security.core.context.SecurityContextHolder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JossoSessionValidationFilter
/*     */   implements Filter
/*     */ {
/*     */   private JAXWSWebserviceGatewayServiceLocator serviceLocator;
/*  29 */   private String jossoSessionIdName = "JOSSO_SESSIONID";
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(FilterConfig filterConfig) throws ServletException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {}
/*     */ 
/*     */   
/*     */   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
/*  41 */     Cookie[] cookies = ((HttpServletRequest)request).getCookies();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  47 */     if (cookies != null) {
/*  48 */       for (Cookie c : cookies) {
/*  49 */         if (c.getName().equals("JOSSO_AUTOMATIC_LOGIN_EXECUTED")) {
/*  50 */           if (c.getMaxAge() == -1) {
/*  51 */             Cookie cookie1 = new Cookie(c.getName(), c.getValue());
/*  52 */             cookie1.setMaxAge(60);
/*  53 */             cookie1.setPath(c.getPath());
/*  54 */             cookie1.setHttpOnly(c.isHttpOnly());
/*  55 */             cookie1.setSecure(c.getSecure());
/*  56 */             ((HttpServletResponse)response).addCookie(cookie1);
/*     */           } 
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*  62 */     Cookie cookie = null;
/*  63 */     if (cookies != null) {
/*  64 */       for (Cookie c : cookies) {
/*  65 */         if (this.jossoSessionIdName.equals(c.getName())) {
/*  66 */           cookie = c;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*  72 */     boolean valid = false;
/*  73 */     if (cookie != null) {
/*     */       SSOSessionManagerService sessionManager;
/*     */       try {
/*  76 */         sessionManager = this.serviceLocator.getSSOSessionManager();
/*  77 */       } catch (Exception ex) {
/*  78 */         SecurityContextHolder.getContext().setAuthentication(null);
/*     */         return;
/*     */       } 
/*     */       
/*  82 */       try { sessionManager.accessSession("", cookie.getValue());
/*  83 */         valid = true; }
/*  84 */       catch (NoSuchSessionException noSuchSessionException) {  }
/*  85 */       catch (SSOSessionException sSOSessionException) {}
/*     */     } 
/*     */ 
/*     */     
/*  89 */     if (!valid) {
/*  90 */       SecurityContextHolder.getContext().setAuthentication(null);
/*  91 */       if (cookie != null) {
/*  92 */         ((HttpServletRequest)request)
/*  93 */           .getSession()
/*  94 */           .setAttribute("org.josso.servlet.agent.JOSSOSecurityContext", null);
/*     */       }
/*     */     } 
/*  97 */     chain.doFilter(request, response);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setServiceLocator(JAXWSWebserviceGatewayServiceLocator serviceLocator) {
/* 102 */     this.serviceLocator = serviceLocator;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setJossoSessionIdName(String jossoSessionIdName) {
/* 107 */     this.jossoSessionIdName = jossoSessionIdName;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\security\josso\JossoSessionValidationFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */