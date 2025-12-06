/*     */ package com.hwacom.ngtms.frameworkcontext;
/*     */ 
/*     */ import com.hwacom.ngtms.base.hazelcast.HazelcastClient;
/*     */ import com.hwacom.ngtms.common.security.AccessDecisionVoter4Am;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.context.annotation.Bean;
/*     */ import org.springframework.context.annotation.Configuration;
/*     */ import org.springframework.ldap.core.LdapTemplate;
/*     */ import org.springframework.ldap.core.support.DefaultDirObjectFactory;
/*     */ import org.springframework.ldap.core.support.LdapContextSource;
/*     */ import org.springframework.security.access.AccessDecisionManager;
/*     */ import org.springframework.security.access.vote.AffirmativeBased;
/*     */ import org.springframework.security.authentication.event.LoggerListener;
/*     */ import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
/*     */ import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
/*     */ import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer.ContextSourceBuilder;
/*     */ import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer.PasswordCompareConfigurer;
/*     */ import org.springframework.security.config.annotation.web.builders.HttpSecurity;
/*     */ import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
/*     */ import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/*     */ import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
/*     */ import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl;
/*     */ import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry;
/*     */ import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
/*     */ import org.springframework.security.crypto.factory.PasswordEncoderFactories;
/*     */ import org.springframework.security.crypto.password.PasswordEncoder;
/*     */ 
/*     */ @Configuration
/*     */ @EnableWebSecurity
/*     */ public class WebSecurityConfigByLdap extends WebSecurityConfigurerAdapter
/*     */ {
/*  39 */   private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfigByLdap.class);
/*     */   
/*     */   @Autowired
/*     */   private HazelcastClient hzClient;
/*     */   
/*     */   @Value("${security.login:true}")
/*     */   private Boolean needLogin;
/*     */   
/*     */   @Value("${security.josso.login:false}")
/*     */   private Boolean useJosso;
/*     */   
/*     */   @Value("${security.login.url:/login.do}")
/*     */   private String generalLoginUrl;
/*     */   
/*     */   @Value("${security.ldap.url:ldap://192.168.85.110:389/dc=hwacom,dc=com}")
/*     */   private String ldapUrl;
/*     */   
/*     */   @Value("${security.ldap.manager.dn:uid=root,cn=users,dc=hwacom,dc=com}")
/*     */   private String ldapManagerDn;
/*     */   
/*     */   @Value("${security.ldap.manager.secret:}")
/*     */   private String ldapManagerSecret;
/*     */   @Value("${security.ldap.search.base:cn=users}")
/*     */   private String ldapSearchBase;
/*     */   @Value("${security.ldap.dn.patterns:uid={0}}")
/*     */   private String ldapDnPatterns;
/*     */   
/*     */   @Bean(name={"ldapTemplate"})
/*     */   public LdapTemplate ldapTemplate()
/*     */   {
/*  69 */     LdapTemplate ldapTemplate = new LdapTemplate(ldapContextSource());
/*  70 */     return ldapTemplate;
/*     */   }
/*     */   
/*     */   @Bean(name={"contextSource"})
/*     */   public LdapContextSource ldapContextSource() {
/*  75 */     LdapContextSource ldapContextSource = new LdapContextSource();
/*  76 */     ldapContextSource.setUrl(this.ldapUrl);
/*     */     
/*  78 */     ldapContextSource.setUserDn(this.ldapManagerDn);
/*  79 */     ldapContextSource.setPassword(this.ldapManagerSecret);
/*     */     
/*  81 */     ldapContextSource.setReferral("follow");
/*  82 */     ldapContextSource.setPooled(true);
/*  83 */     ldapContextSource.setDirObjectFactory(DefaultDirObjectFactory.class);
/*  84 */     ldapContextSource.afterPropertiesSet();
/*  85 */     return ldapContextSource;
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public LoggerListener loggerListener() {
/*  90 */     return new LoggerListener();
/*     */   }
/*     */   
/*     */   protected void configure(HttpSecurity http) throws Exception
/*     */   {
/*  95 */     http.csrf().disable();
/*  96 */     ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.authorizeRequests().antMatchers(new String[] { "/css/**", "/js/**", "/images/**" })).permitAll();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 104 */     ((FormLoginConfigurer)((HttpSecurity)((ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.authorizeRequests().anyRequest()).fullyAuthenticated().accessDecisionManager(accessDecisionManager())).and()).formLogin().loginPage(this.generalLoginUrl).loginProcessingUrl("/j_spring_security_check"))
/* 105 */       .permitAll();
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public AccessDecisionManager accessDecisionManager() {
/* 110 */     List<org.springframework.security.access.AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList();
/*     */     
/* 112 */     AccessDecisionVoter4Am amAccessDecisionVoter = new AccessDecisionVoter4Am(this.hzClient);
/* 113 */     if (!this.needLogin.booleanValue()) {
/* 114 */       amAccessDecisionVoter.setAllowAll(true);
/*     */     }
/* 116 */     else if (!this.useJosso.booleanValue()) {
/*     */       try {
/* 118 */         amAccessDecisionVoter.getAllowList().add(new URI(this.generalLoginUrl).getPath());
/*     */       } catch (URISyntaxException e) {
/* 120 */         logger.warn("set allow url have exception.", e);
/*     */       }
/*     */     }
/*     */     
/* 124 */     decisionVoters.add(amAccessDecisionVoter);
/*     */     
/* 126 */     AffirmativeBased affirmativeBased = new AffirmativeBased(decisionVoters);
/* 127 */     return affirmativeBased;
/*     */   }
/*     */   
/*     */   public void configure(AuthenticationManagerBuilder auth) throws Exception
/*     */   {
/* 132 */     PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
/*     */     
/* 134 */     auth.ldapAuthentication()
/* 135 */       .contextSource()
/* 136 */       .url(this.ldapUrl)
/* 137 */       .managerDn(this.ldapManagerDn)
/* 138 */       .managerPassword(this.ldapManagerSecret)
/* 139 */       .and()
/* 140 */       .userSearchBase(this.ldapSearchBase)
/* 141 */       .userSearchFilter(this.ldapDnPatterns)
/* 142 */       .passwordCompare()
/*     */       
/* 144 */       .passwordEncoder(passwordEncoder)
/*     */       
/* 146 */       .passwordAttribute("userPassword");
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\WebSecurityConfigByLdap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */