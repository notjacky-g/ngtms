/*     */ package com.hwacom.ngtms.common.security.josso;
/*     */ 
/*     */ import com.hazelcast.core.HazelcastInstanceNotActiveException;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.hazelcast.HazelcastClient;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*     */ import com.hwacom.ngtms.common.fm.model.Role;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.Principal;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.Filter;
/*     */ import javax.servlet.FilterChain;
/*     */ import javax.servlet.FilterConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.josso.agent.http.JOSSOSecurityContext;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
/*     */ import org.springframework.security.authentication.jaas.JaasGrantedAuthority;
/*     */ import org.springframework.security.core.Authentication;
/*     */ import org.springframework.security.core.GrantedAuthority;
/*     */ import org.springframework.security.core.context.SecurityContextHolder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JossoGenericServletAgentPostAuthenticationFilter
/*     */   implements Filter
/*     */ {
/*  48 */   private static Logger logger = LoggerFactory.getLogger(JossoGenericServletAgentPostAuthenticationFilter.class);
/*     */ 
/*     */   
/*     */   private HazelcastClient hzClient;
/*     */ 
/*     */   
/*     */   public void init(FilterConfig filterConfig) throws ServletException {}
/*     */ 
/*     */   
/*     */   public void destroy() {}
/*     */ 
/*     */   
/*     */   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
/*  61 */     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
/*  62 */     if (auth == null || !auth.isAuthenticated() || isChangeUserPrincipal(auth, request)) {
/*  63 */       SecurityContextHolder.getContext().setAuthentication(getAuthentication(request));
/*  64 */       HttpSession httpSession = ((HttpServletRequest)request).getSession();
/*  65 */       httpSession.setAttribute("REMOTE_ADDR", request.getRemoteAddr());
/*     */     } 
/*  67 */     chain.doFilter(request, response);
/*     */   }
/*     */ 
/*     */   
/*     */   private Authentication getAuthentication(ServletRequest request) {
/*     */     Authentication authentication;
/*     */     try {
/*  74 */       ClassLoader cl = Thread.currentThread().getContextClassLoader();
/*  75 */       Class<?> utilClazz = cl.loadClass("org.josso.agent.http.WebAccessControlUtil");
/*  76 */       Class<?> ctxClazz = cl.loadClass("org.josso.agent.http.JOSSOSecurityContext");
/*     */ 
/*     */       
/*  79 */       Method utilMethod = utilClazz.getMethod("getSecurityContext", new Class[] { HttpServletRequest.class });
/*  80 */       Method ctxMethod = ctxClazz.getDeclaredMethod("getCurrentPrincipal", new Class[0]);
/*     */ 
/*     */ 
/*     */       
/*  84 */       Object ctxObj = utilMethod.invoke(null, new Object[] { request });
/*  85 */       if (ctxObj == null) {
/*  86 */         return null;
/*     */       }
/*  88 */       JOSSOSecurityContext jossoCtx = (JOSSOSecurityContext)ctxObj;
/*     */ 
/*     */       
/*  91 */       Object userObj = ctxMethod.invoke(ctxObj, new Object[0]);
/*     */ 
/*     */ 
/*     */       
/*  95 */       Set<String> roles = new HashSet<>();
/*     */ 
/*     */       
/*  98 */       IMap<String, Role> roleIMap = this.hzClient.getIMap((HzDistObjEnum)CommonHzMap.AccountRole);
/*     */       
/* 100 */       for (String r : roleIMap.keySet()) {
/* 101 */         if (jossoCtx.isUserInRole(r)) {
/* 102 */           roles.add(r);
/*     */         }
/*     */       } 
/*     */       
/* 106 */       Map<String, Role> roleMap = roleIMap.getAll(roles);
/*     */       
/* 108 */       Collection<GrantedAuthority> authorities = new HashSet<>();
/* 109 */       StringBuilder sb = new StringBuilder();
/* 110 */       sb.append(((Principal)userObj).getName() + " josso role : ");
/*     */       
/* 112 */       for (String r : roles) {
/* 113 */         authorities.add(new JaasGrantedAuthority(r, (Principal)userObj));
/* 114 */         sb.append(r + ",");
/*     */       } 
/*     */       
/* 117 */       sb.append("\r\n ngtms role : ");
/*     */       
/* 119 */       roleMap
/* 120 */         .values()
/* 121 */         .stream()
/* 122 */         .forEach(r -> r.getFunctionPermissions().stream().forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 135 */       logger.debug(sb.toString());
/* 136 */       authentication = (Authentication)new UsernamePasswordAuthenticationToken(userObj, null, authorities);
/* 137 */       fetchProperties((UsernamePasswordAuthenticationToken)authentication, (Principal)userObj);
/* 138 */     } catch (ReflectiveOperationException|IllegalArgumentException|ClassCastException|SecurityException ex) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 143 */       Principal principal = ((HttpServletRequest)request).getUserPrincipal();
/* 144 */       authentication = (Authentication)new UsernamePasswordAuthenticationToken(principal, null);
/* 145 */     } catch (HazelcastInstanceNotActiveException ex) {
/* 146 */       logger.warn("connect to hazelcast failed! try later...", (Throwable)ex);
/* 147 */       authentication = null;
/*     */     } 
/*     */     
/* 150 */     return authentication;
/*     */   }
/*     */   
/*     */   private void fetchProperties(UsernamePasswordAuthenticationToken auth, Principal principal) {
/*     */     try {
/* 155 */       ClassLoader cl = Thread.currentThread().getContextClassLoader();
/* 156 */       Class<?> userClazz = cl.loadClass("org.josso.gateway.identity.SSOUser");
/* 157 */       Class<?> pairClazz = cl.loadClass("org.josso.gateway.SSONameValuePair");
/*     */       
/* 159 */       Method propsMethod = userClazz.getDeclaredMethod("getProperties", new Class[0]);
/* 160 */       Object[] pairs = (Object[])propsMethod.invoke(principal, new Object[0]);
/*     */       
/* 162 */       Method nameMethod = pairClazz.getDeclaredMethod("getName", new Class[0]);
/* 163 */       Method valueMethod = pairClazz.getDeclaredMethod("getValue", new Class[0]);
/*     */       
/* 165 */       Map<String, String> props = new HashMap<>();
/* 166 */       for (Object o : pairs) {
/* 167 */         String name = (String)nameMethod.invoke(o, new Object[0]);
/* 168 */         String value = (String)valueMethod.invoke(o, new Object[0]);
/* 169 */         props.put(name, value);
/*     */       } 
/*     */       
/* 172 */       auth.setDetails(props);
/* 173 */     } catch (ReflectiveOperationException|IllegalArgumentException|ClassCastException|SecurityException ex) {
/*     */ 
/*     */ 
/*     */       
/* 177 */       logger.error(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isChangeUserPrincipal(Authentication auth, ServletRequest request) {
/* 182 */     HttpServletRequest req = (HttpServletRequest)request;
/* 183 */     if (req.getUserPrincipal() == null) {
/* 184 */       return false;
/*     */     }
/* 186 */     return !auth.getName().equals(req.getUserPrincipal().getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHzClient(HazelcastClient hzClient) {
/* 191 */     this.hzClient = hzClient;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\security\josso\JossoGenericServletAgentPostAuthenticationFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */