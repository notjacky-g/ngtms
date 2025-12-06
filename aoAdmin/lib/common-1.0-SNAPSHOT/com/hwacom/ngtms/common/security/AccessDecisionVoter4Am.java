/*     */ package com.hwacom.ngtms.common.security;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.hazelcast.HazelcastClient;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*     */ import com.hwacom.ngtms.common.fm.model.FunctionPermission;
/*     */ import com.hwacom.ngtms.common.fm.model.Role;
/*     */ import com.hwacom.ngtms.common.fm.model.UrlPatternRole;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.security.access.AccessDecisionVoter;
/*     */ import org.springframework.security.access.ConfigAttribute;
/*     */ import org.springframework.security.core.Authentication;
/*     */ import org.springframework.security.core.GrantedAuthority;
/*     */ import org.springframework.security.web.FilterInvocation;
/*     */ import org.springframework.util.AntPathMatcher;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AccessDecisionVoter4Am
/*     */   implements AccessDecisionVoter<Object>
/*     */ {
/*  35 */   private static final Logger logger = LoggerFactory.getLogger(AccessDecisionVoter4Am.class);
/*     */   
/*     */   private HazelcastClient hzClient;
/*     */   
/*     */   private boolean allowAll = false;
/*     */   
/*  41 */   private List<String> allowList = new LinkedList<>();
/*     */   
/*  43 */   private String anonymousRole = "ROLE_ANONYMOUS";
/*     */   
/*     */   public AccessDecisionVoter4Am(HazelcastClient hazelcastClient) {
/*  46 */     this.hzClient = hazelcastClient;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supports(ConfigAttribute attribute) {
/*  51 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supports(Class clazz) {
/*  56 */     return true;
/*     */   }
/*     */   
/*     */   public int vote(Authentication authentication, Object object, Collection attributes) {
/*     */     IMap<Long, UrlPatternRole> roleMap;
/*  61 */     if (this.allowAll) {
/*  62 */       return 1;
/*     */     }
/*     */     
/*  65 */     String uri = null;
/*  66 */     String ip = null;
/*  67 */     List<String> authorityList = new LinkedList<>();
/*  68 */     for (GrantedAuthority authority : authentication.getAuthorities()) {
/*  69 */       logger.trace("authority has {}", authority.getAuthority());
/*  70 */       authorityList.add(authority.getAuthority());
/*     */     } 
/*     */     
/*  73 */     if (object instanceof FilterInvocation) {
/*  74 */       FilterInvocation fi = (FilterInvocation)object;
/*  75 */       uri = fi.getHttpRequest().getRequestURI();
/*  76 */       ip = fi.getRequest().getRemoteAddr();
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  81 */       roleMap = this.hzClient.getIMap((HzDistObjEnum)CommonHzMap.UrlPatternRole);
/*  82 */     } catch (Exception ex) {
/*  83 */       return -1;
/*     */     } 
/*     */     
/*  86 */     if (uri != null) {
/*  87 */       logger.info("request uri is {} , ip is {}", uri, ip);
/*     */       
/*  89 */       AntPathMatcher matcher = new AntPathMatcher();
/*  90 */       for (String url : this.allowList) {
/*  91 */         if (matcher.match(url, uri)) {
/*  92 */           return 1;
/*     */         }
/*     */       } 
/*     */       
/*  96 */       for (UrlPatternRole role : roleMap.values()) {
/*     */         
/*  98 */         boolean anonymousOrRoleMatched = (Objects.equals(role.getRole(), this.anonymousRole) || authorityList.contains(role.getRole()));
/*  99 */         if (anonymousOrRoleMatched && matcher.match(role.getPattern(), uri)) {
/* 100 */           return 1;
/*     */         }
/*     */       } 
/* 103 */       int checkW = checkWhiteList(matcher, authorityList, uri);
/* 104 */       logger.info("checkWhiteList result: {}", Integer.valueOf(checkW));
/* 105 */       return checkW;
/*     */     } 
/* 107 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private int checkWhiteList(AntPathMatcher matcher, List<String> roleNames, String uri) {
/* 112 */     if (matcher.match("/first/**", uri)) {
/* 113 */       return 1;
/*     */     }
/* 115 */     return checkFunctionPermission(roleNames, uri, matcher);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int checkFunctionPermission(List<String> roleNames, String uri, AntPathMatcher matcher) {
/* 121 */     IMap<String, FunctionPermission> functionPermissionMap = this.hzClient.getIMap((HzDistObjEnum)CommonHzMap.AccountFunctionPermission);
/* 122 */     logger.info("roleNames is {}", roleNames);
/* 123 */     Set<String> urlMappings = toUrlMappings(roleNames, functionPermissionMap);
/* 124 */     logger.info("urlMappings is {}", urlMappings);
/* 125 */     for (String urlMapping : urlMappings) {
/* 126 */       if (matcher.match(toSubPath(urlMapping), uri) || uri.endsWith(urlMapping)) {
/* 127 */         return 1;
/*     */       }
/*     */     } 
/* 130 */     Set<String> allUrlMappings = toUrlMappings(functionPermissionMap);
/* 131 */     if (!urlMappings.isEmpty() && matcher.match("/*.do", uri) && !isEndsWith(uri, allUrlMappings)) {
/* 132 */       return 1;
/*     */     }
/* 134 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<String> toUrlMappings(List<String> roleNames, IMap<String, FunctionPermission> functionPermissionMap) {
/* 140 */     IMap<String, Role> roleMap = this.hzClient.getIMap((HzDistObjEnum)CommonHzMap.AccountRole);
/*     */     try {
/* 142 */       Set<String> result = new HashSet<>();
/* 143 */       for (String roleName : roleNames) {
/*     */ 
/*     */ 
/*     */         
/* 147 */         Set<String> functionPermissionIds = Optional.<Object>ofNullable(roleMap.get(roleName)).map(role -> role.getFunctionPermissions()).orElse(new HashSet<>());
/* 148 */         for (String id : functionPermissionIds) {
/* 149 */           Optional.<Object>ofNullable(functionPermissionMap.get(id))
/* 150 */             .map(FunctionPermission::getUrlMapping)
/* 151 */             .filter(Objects::nonNull)
/* 152 */             .ifPresent(urlMapping -> paramSet.add(urlMapping));
/*     */         }
/*     */       } 
/* 155 */       return result;
/* 156 */     } catch (Exception e) {
/* 157 */       logger.error("toUrlMappings error.", e);
/* 158 */       return Collections.emptySet();
/*     */     } 
/*     */   }
/*     */   
/*     */   private Set<String> toUrlMappings(IMap<String, FunctionPermission> functionPermissionMap) {
/* 163 */     return (Set<String>)functionPermissionMap
/* 164 */       .values()
/* 165 */       .stream()
/* 166 */       .map(functionPermission -> functionPermission.getUrlMapping())
/* 167 */       .filter(Objects::nonNull)
/* 168 */       .collect(Collectors.toSet());
/*     */   }
/*     */   
/*     */   private boolean isEndsWith(String uri, Set<String> allUrlMappings) {
/* 172 */     for (String urlMapping : allUrlMappings) {
/* 173 */       if (uri.endsWith(urlMapping)) {
/* 174 */         return true;
/*     */       }
/*     */     } 
/* 177 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String toSubPath(String urlMapping) {
/* 187 */     return "/" + urlMapping.split("\\.")[0] + "/**";
/*     */   }
/*     */   
/*     */   public void setAllowAll(boolean allowAll) {
/* 191 */     this.allowAll = allowAll;
/*     */   }
/*     */   
/*     */   public List<String> getAllowList() {
/* 195 */     return this.allowList;
/*     */   }
/*     */   
/*     */   public String getAnonymousRole() {
/* 199 */     return this.anonymousRole;
/*     */   }
/*     */   
/*     */   public void setAnonymousRole(String anonymousRole) {
/* 203 */     this.anonymousRole = anonymousRole;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\security\AccessDecisionVoter4Am.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */