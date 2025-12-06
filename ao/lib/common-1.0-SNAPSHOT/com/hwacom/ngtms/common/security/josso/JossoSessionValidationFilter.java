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
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.josso.gateway.jaxws.JAXWSWebserviceGatewayServiceLocator;
/*     */ import org.josso.gateway.session.exceptions.NoSuchSessionException;
/*     */ import org.josso.gateway.session.exceptions.SSOSessionException;
/*     */ import org.josso.gateway.session.service.SSOSessionManagerService;
/*     */ import org.springframework.security.core.context.SecurityContext;
/*     */ import org.springframework.security.core.context.SecurityContextHolder;
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
/*     */   public void init(FilterConfig filterConfig)
/*     */     throws ServletException
/*     */   {}
/*     */   
/*     */   public void destroy() {}
/*     */   
/*     */   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
/*     */     throws IOException, ServletException
/*     */   {
/*  41 */     Cookie[] cookies = ((HttpServletRequest)request).getCookies();
/*     */     
/*     */     Cookie localCookie1;
/*     */     
/*     */     Cookie c;
/*     */     
/*  47 */     if (cookies != null) {
/*  48 */       Cookie[] arrayOfCookie1 = cookies;int i = arrayOfCookie1.length; for (localCookie1 = 0; localCookie1 < i; localCookie1++) { c = arrayOfCookie1[localCookie1];
/*  49 */         if (c.getName().equals("JOSSO_AUTOMATIC_LOGIN_EXECUTED")) {
/*  50 */           if (c.getMaxAge() != -1) break;
/*  51 */           Cookie cookie = new Cookie(c.getName(), c.getValue());
/*  52 */           cookie.setMaxAge(60);
/*  53 */           cookie.setPath(c.getPath());
/*  54 */           cookie.setHttpOnly(c.isHttpOnly());
/*  55 */           cookie.setSecure(c.getSecure());
/*  56 */           ((HttpServletResponse)response).addCookie(cookie);
/*  57 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  62 */     Cookie cookie = null;
/*  63 */     if (cookies != null) {
/*  64 */       Cookie[] arrayOfCookie2 = cookies;localCookie1 = arrayOfCookie2.length; for (c = 0; c < localCookie1; c++) { Cookie c = arrayOfCookie2[c];
/*  65 */         if (this.jossoSessionIdName.equals(c.getName())) {
/*  66 */           cookie = c;
/*  67 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  72 */     boolean valid = false;
/*  73 */     if (cookie != null)
/*     */     {
/*     */       try {
/*  76 */         sessionManager = this.serviceLocator.getSSOSessionManager();
/*     */       } catch (Exception ex) { SSOSessionManagerService sessionManager;
/*  78 */         SecurityContextHolder.getContext().setAuthentication(null);
/*  79 */         return;
/*     */       }
/*     */       try { SSOSessionManagerService sessionManager;
/*  82 */         sessionManager.accessSession("", cookie.getValue());
/*  83 */         valid = true;
/*     */       }
/*     */       catch (NoSuchSessionException localNoSuchSessionException) {}catch (SSOSessionException localSSOSessionException) {}
/*     */     }
/*     */     
/*     */ 
/*  89 */     if (!valid) {
/*  90 */       SecurityContextHolder.getContext().setAuthentication(null);
/*  91 */       if (cookie != null)
/*     */       {
/*     */ 
/*  94 */         ((HttpServletRequest)request).getSession().setAttribute("org.josso.servlet.agent.JOSSOSecurityContext", null);
/*     */       }
/*     */     }
/*  97 */     chain.doFilter(request, response);
/*     */   }
/*     */   
/*     */   public void setServiceLocator(JAXWSWebserviceGatewayServiceLocator serviceLocator)
/*     */   {
/* 102 */     this.serviceLocator = serviceLocator;
/*     */   }
/*     */   
/*     */   public void setJossoSessionIdName(String jossoSessionIdName)
/*     */   {
/* 107 */     this.jossoSessionIdName = jossoSessionIdName;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\security\josso\JossoSessionValidationFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */