/*     */ package com.hwacom.ngtms.frameworkcontext;
/*     */ 
/*     */ import com.hwacom.ngtms.base.hazelcast.HazelcastClient;
/*     */ import com.hwacom.ngtms.common.security.AccessDecisionVoter4Am;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.context.annotation.Bean;
/*     */ import org.springframework.context.annotation.Configuration;
/*     */ import org.springframework.ldap.core.ContextSource;
/*     */ import org.springframework.ldap.core.LdapTemplate;
/*     */ import org.springframework.ldap.core.support.DefaultDirObjectFactory;
/*     */ import org.springframework.ldap.core.support.LdapContextSource;
/*     */ import org.springframework.security.access.AccessDecisionManager;
/*     */ import org.springframework.security.access.AccessDecisionVoter;
/*     */ import org.springframework.security.access.vote.AffirmativeBased;
/*     */ import org.springframework.security.authentication.AuthenticationManager;
/*     */ import org.springframework.security.authentication.AuthenticationProvider;
/*     */ import org.springframework.security.authentication.ProviderManager;
/*     */ import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
/*     */ import org.springframework.security.config.annotation.web.builders.HttpSecurity;
/*     */ import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
/*     */ import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/*     */ import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
/*     */ import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
/*     */ import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Configuration
/*     */ @EnableWebSecurity
/*     */ public class WebSecurityConfigByActiveDirectory
/*     */   extends WebSecurityConfigurerAdapter
/*     */ {
/*  42 */   private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfigByActiveDirectory.class);
/*     */   
/*     */   @Autowired
/*     */   private HazelcastClient hzClient;
/*     */   
/*     */   @Value("${security.login.url:/login.do}")
/*     */   private String generalLoginUrl;
/*     */   
/*     */   @Value("${security.ad.domain:NGTMS.hwacom.com}")
/*     */   private String adDomain;
/*     */   
/*     */   @Value("${security.ad.url:ldap://192.168.85.11:389/}")
/*     */   private String adUrl;
/*     */   
/*     */   @Value("${security.ad.base:DC=NGTMS,DC=hwacom,DC=com}")
/*     */   private String adBase;
/*     */   
/*     */   @Value("${security.ad.manager.dn:cn=Administrator,cn=Users,dc=NGTMS,dc=hwacom,dc=com}")
/*     */   private String adManagerDn;
/*     */   
/*     */   @Value("${security.ad.manager.secret:!QAZ2wsx}")
/*     */   private String adManagerSecret;
/*     */   
/*     */   @Value("${security.login:true}")
/*     */   private Boolean needLogin;
/*     */   @Value("${security.josso.login:false}")
/*     */   private Boolean useJosso;
/*     */   
/*     */   @Bean(name = {"ldapTemplate"})
/*     */   public LdapTemplate ldapTemplate() {
/*  72 */     LdapTemplate ldapTemplate = new LdapTemplate((ContextSource)ldapContextSource());
/*  73 */     return ldapTemplate;
/*     */   }
/*     */   
/*     */   @Bean(name = {"contextSource"})
/*     */   public LdapContextSource ldapContextSource() {
/*  78 */     if (isConfigurationValid(this.adUrl, this.adBase)) {
/*  79 */       LdapContextSource ldapContextSource = new LdapContextSource();
/*  80 */       ldapContextSource.setUrl(this.adUrl);
/*  81 */       ldapContextSource.setBase(this.adBase);
/*  82 */       ldapContextSource.setUserDn(this.adManagerDn);
/*  83 */       ldapContextSource.setPassword(this.adManagerSecret);
/*     */       
/*  85 */       ldapContextSource.setReferral("follow");
/*  86 */       ldapContextSource.setPooled(true);
/*  87 */       ldapContextSource.setDirObjectFactory(DefaultDirObjectFactory.class);
/*  88 */       ldapContextSource.afterPropertiesSet();
/*  89 */       return ldapContextSource;
/*     */     } 
/*  91 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isConfigurationValid(String url, String base) {
/*  95 */     if (url == null || url.isEmpty() || base == null || base.isEmpty()) {
/*  96 */       logger.error("Warning! Your LDAP server is not configured.");
/*  97 */       logger.info("Did you configure your LDAP settings in your application.yml?");
/*  98 */       return false;
/*     */     } 
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void configure(HttpSecurity http) throws Exception {
/* 106 */     http.csrf().disable();
/* 107 */     ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.authorizeRequests().antMatchers(new String[] { "/css/**", "/js/**", "/images/**" })).permitAll();
/* 108 */     ((FormLoginConfigurer)((HttpSecurity)((ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.authorizeRequests()
/* 109 */       .anyRequest())
/* 110 */       .fullyAuthenticated()
/* 111 */       .accessDecisionManager(accessDecisionManager()))
/* 112 */       .and())
/* 113 */       .formLogin()
/* 114 */       .loginPage(this.generalLoginUrl)
/* 115 */       .loginProcessingUrl("/j_spring_security_check"))
/* 116 */       .permitAll();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
/* 121 */     auth.authenticationProvider(activeDirectoryLdapAuthenticationProvider())
/* 122 */       .userDetailsService(userDetailsService());
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public AuthenticationManager authenticationManager() {
/* 127 */     return (AuthenticationManager)new ProviderManager(Arrays.asList(new AuthenticationProvider[] { activeDirectoryLdapAuthenticationProvider() }));
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public AccessDecisionManager accessDecisionManager() {
/* 132 */     List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
/*     */     
/* 134 */     AccessDecisionVoter4Am amAccessDecisionVoter = new AccessDecisionVoter4Am(this.hzClient);
/* 135 */     if (!this.needLogin.booleanValue()) {
/* 136 */       amAccessDecisionVoter.setAllowAll(true);
/*     */     }
/* 138 */     else if (!this.useJosso.booleanValue()) {
/*     */       try {
/* 140 */         amAccessDecisionVoter.getAllowList().add((new URI(this.generalLoginUrl)).getPath());
/* 141 */       } catch (URISyntaxException e) {
/* 142 */         logger.warn("set allow url have exception.", e);
/*     */       } 
/*     */     } 
/*     */     
/* 146 */     decisionVoters.add(amAccessDecisionVoter);
/*     */     
/* 148 */     AffirmativeBased affirmativeBased = new AffirmativeBased(decisionVoters);
/* 149 */     return (AccessDecisionManager)affirmativeBased;
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public AuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
/* 154 */     ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider(this.adDomain, this.adUrl);
/*     */     
/* 156 */     provider.setConvertSubErrorCodesToExceptions(true);
/* 157 */     provider.setUseAuthenticationRequestCredentials(true);
/* 158 */     return (AuthenticationProvider)provider;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\WebSecurityConfigByActiveDirectory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */