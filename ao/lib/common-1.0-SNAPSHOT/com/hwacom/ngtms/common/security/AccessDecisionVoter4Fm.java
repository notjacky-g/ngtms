/*    */ package com.hwacom.ngtms.common.security;
/*    */ 
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*    */ import com.hwacom.ngtms.common.fm.model.UrlPatternRole;
/*    */ import java.util.Collection;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.security.access.AccessDecisionVoter;
/*    */ import org.springframework.security.access.ConfigAttribute;
/*    */ import org.springframework.security.core.Authentication;
/*    */ import org.springframework.security.web.FilterInvocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AccessDecisionVoter4Fm
/*    */   implements AccessDecisionVoter<Object>
/*    */ {
/*    */   public boolean supports(ConfigAttribute attribute)
/*    */   {
/* 23 */     return true;
/*    */   }
/*    */   
/*    */   public boolean supports(Class<?> clazz)
/*    */   {
/* 28 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes)
/*    */   {
/* 34 */     IMap<Long, UrlPatternRole> roleMap = HzUtils.getMap(CommonHzMap.UrlPatternRole);
/* 35 */     String requestUri = null;
/*    */     
/* 37 */     if ((object instanceof FilterInvocation)) {
/* 38 */       FilterInvocation fi = (FilterInvocation)object;
/* 39 */       requestUri = fi.getHttpRequest().getRequestURI();
/*    */     }
/*    */     
/* 42 */     if (requestUri != null) {
/* 43 */       return 1;
/*    */     }
/* 45 */     return -1;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\security\AccessDecisionVoter4Fm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */