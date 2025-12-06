/*     */ package com.hwacom.ngtms.base.web.filter;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.Filter;
/*     */ import javax.servlet.FilterChain;
/*     */ import javax.servlet.FilterConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletRequestWrapper;
/*     */ import org.owasp.esapi.ESAPI;
/*     */ import org.owasp.esapi.Encoder;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XSSFilter
/*     */   implements Filter
/*     */ {
/*  25 */   private static final Logger logger = LoggerFactory.getLogger(XSSFilter.class);
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
/*  36 */     chain.doFilter(new RequestWrapper((HttpServletRequest)request), response);
/*     */   }
/*     */   
/*     */   static class RequestWrapper extends HttpServletRequestWrapper {
/*     */     public RequestWrapper(HttpServletRequest servletRequest) {
/*  41 */       super();
/*     */     }
/*     */     
/*     */     public String[] getParameterValues(String parameter)
/*     */     {
/*  46 */       String[] values = super.getParameterValues(parameter);
/*  47 */       if (values == null) {
/*  48 */         return null;
/*     */       }
/*  50 */       int count = values.length;
/*  51 */       String[] encodedValues = new String[count];
/*  52 */       for (int i = 0; i < count; i++) {
/*  53 */         encodedValues[i] = stripXSS(values[i]);
/*     */       }
/*  55 */       return encodedValues;
/*     */     }
/*     */     
/*     */     public String getParameter(String parameter)
/*     */     {
/*  60 */       String value = super.getParameter(parameter);
/*  61 */       return stripXSS(value);
/*     */     }
/*     */     
/*     */     public String getHeader(String name)
/*     */     {
/*  66 */       String value = super.getHeader(name);
/*  67 */       return stripXSS(value);
/*     */     }
/*     */     
/*     */     private String stripXSS(String value) {
/*  71 */       if (value != null)
/*     */       {
/*     */ 
/*     */ 
/*  75 */         value = ESAPI.encoder().canonicalize(value);
/*     */         
/*     */ 
/*  78 */         value = value.replaceAll("", "");
/*     */         
/*     */ 
/*  81 */         Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", 2);
/*  82 */         value = scriptPattern.matcher(value).replaceAll("");
/*     */         
/*     */ 
/*     */ 
/*  86 */         scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\'(.*?)\\'", 42);
/*     */         
/*     */ 
/*  89 */         value = scriptPattern.matcher(value).replaceAll("");
/*     */         
/*     */ 
/*  92 */         scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", 42);
/*     */         
/*     */ 
/*  95 */         value = scriptPattern.matcher(value).replaceAll("");
/*     */         
/*     */ 
/*  98 */         scriptPattern = Pattern.compile("</script>", 2);
/*  99 */         value = scriptPattern.matcher(value).replaceAll("");
/*     */         
/*     */ 
/*     */ 
/* 103 */         scriptPattern = Pattern.compile("<script(.*?)>", 42);
/*     */         
/* 105 */         value = scriptPattern.matcher(value).replaceAll("");
/*     */         
/*     */ 
/*     */ 
/* 109 */         scriptPattern = Pattern.compile("eval\\((.*?)\\)", 42);
/*     */         
/* 111 */         value = scriptPattern.matcher(value).replaceAll("");
/*     */         
/*     */ 
/*     */ 
/* 115 */         scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)", 42);
/*     */         
/*     */ 
/* 118 */         value = scriptPattern.matcher(value).replaceAll("");
/*     */         
/*     */ 
/* 121 */         scriptPattern = Pattern.compile("javascript:", 2);
/* 122 */         value = scriptPattern.matcher(value).replaceAll("");
/*     */         
/*     */ 
/* 125 */         scriptPattern = Pattern.compile("vbscript:", 2);
/* 126 */         value = scriptPattern.matcher(value).replaceAll("");
/*     */         
/*     */ 
/*     */ 
/* 130 */         scriptPattern = Pattern.compile("onload(.*?)=", 42);
/*     */         
/* 132 */         value = scriptPattern.matcher(value).replaceAll("");
/*     */       }
/*     */       
/* 135 */       return value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\web\filter\XSSFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */