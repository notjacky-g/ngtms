/*    */ package com.hwacom.ngtms.base.web.filter;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.web.filter.OncePerRequestFilter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DisableHttpMethodFilter
/*    */   extends OncePerRequestFilter
/*    */ {
/* 20 */   private static final Logger logger = LoggerFactory.getLogger(DisableHttpMethodFilter.class);
/*    */   
/*    */ 
/*    */   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
/*    */     throws ServletException, IOException
/*    */   {
/* 26 */     HttpServletRequest httpRequest = request;
/* 27 */     HttpServletResponse httpResponse = response;
/*    */     
/* 29 */     if (("TRACE".equalsIgnoreCase(httpRequest.getMethod())) || 
/* 30 */       ("TRACK".equalsIgnoreCase(httpRequest.getMethod())) || 
/*    */       
/* 32 */       ("PATCH".equalsIgnoreCase(httpRequest.getMethod()))) {
/* 33 */       httpResponse.setStatus(405);
/* 34 */       return;
/*    */     }
/* 36 */     filterChain.doFilter(request, response);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\web\filter\DisableHttpMethodFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */