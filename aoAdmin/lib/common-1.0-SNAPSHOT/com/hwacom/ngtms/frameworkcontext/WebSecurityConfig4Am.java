/*     */ package com.hwacom.ngtms.frameworkcontext;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.core.IQueue;
/*     */ import com.hazelcast.core.ITopic;
/*     */ import com.hwacom.ngtms.base.hazelcast.HazelcastClient;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationItem;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*     */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*     */ import com.hwacom.ngtms.common.fm.hz.CommonHzQueue;
/*     */ import com.hwacom.ngtms.common.fm.hz.CommonHzTopic;
/*     */ import com.hwacom.ngtms.common.fm.model.User;
/*     */ import com.hwacom.ngtms.common.security.AccessDecisionVoter4Am;
/*     */ import com.hwacom.ngtms.common.security.josso.JossoAgentComponentKeeperImpl;
/*     */ import com.hwacom.ngtms.common.security.josso.JossoGenericServletAgentPostAuthenticationFilter;
/*     */ import com.hwacom.ngtms.common.security.josso.JossoJAXWSWebserviceGatewayServiceLocator;
/*     */ import com.hwacom.ngtms.common.security.josso.JossoSessionValidationFilter;
/*     */ import com.hwacom.ngtms.common.shared.AccessDeniedInfo;
/*     */ import com.hwacom.ngtms.common.shared.OperationLogData;
/*     */ import com.hwacom.ngtms.common.util.IMapLocker;
/*     */ import com.hwacom.ngtms.hcce.am.server.AuthenticationUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import javax.servlet.Filter;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.josso.gateway.jaxws.JAXWSWebserviceGatewayServiceLocator;
/*     */ import org.josso.servlet.agent.GenericServletSSOAgentFilter;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.context.annotation.Bean;
/*     */ import org.springframework.context.annotation.Configuration;
/*     */ import org.springframework.security.access.AccessDecisionManager;
/*     */ import org.springframework.security.access.AccessDecisionVoter;
/*     */ import org.springframework.security.access.AccessDeniedException;
/*     */ import org.springframework.security.access.vote.AffirmativeBased;
/*     */ import org.springframework.security.authentication.AuthenticationProvider;
/*     */ import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
/*     */ import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
/*     */ import org.springframework.security.config.annotation.web.builders.HttpSecurity;
/*     */ import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
/*     */ import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/*     */ import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
/*     */ import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
/*     */ import org.springframework.security.core.Authentication;
/*     */ import org.springframework.security.core.AuthenticationException;
/*     */ import org.springframework.security.core.authority.SimpleGrantedAuthority;
/*     */ import org.springframework.security.core.context.SecurityContextHolder;
/*     */ import org.springframework.security.core.userdetails.User;
/*     */ import org.springframework.security.core.userdetails.UserDetails;
/*     */ import org.springframework.security.core.userdetails.UserDetailsService;
/*     */ import org.springframework.security.core.userdetails.UsernameNotFoundException;
/*     */ import org.springframework.security.crypto.factory.PasswordEncoderFactories;
/*     */ import org.springframework.security.web.access.AccessDeniedHandler;
/*     */ import org.springframework.security.web.authentication.AuthenticationFailureHandler;
/*     */ import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
/*     */ import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
/*     */ import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
/*     */ import org.springframework.security.web.authentication.logout.LogoutFilter;
/*     */ import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
/*     */ import org.springframework.security.web.header.HeaderWriter;
/*     */ import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
/*     */ import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
/*     */ 
/*     */ @Configuration
/*     */ @EnableWebSecurity
/*     */ public class WebSecurityConfig4Am
/*     */   extends WebSecurityConfigurerAdapter {
/*  86 */   private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig4Am.class);
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   protected HazelcastClient hzClient;
/*     */ 
/*     */   
/*     */   @Value("${security.login.surveillance.account:surveillancengtms84778623}")
/*     */   private String surveillanceAccount;
/*     */ 
/*     */   
/*     */   @Value("${security.login.failure.limit:3}")
/*     */   private int failureLimit;
/*     */ 
/*     */   
/*     */   @Value("${security.login.failure.lock.minutes:15}")
/*     */   private int lockMinutes;
/*     */ 
/*     */   
/*     */   @Value("${security.login:true}")
/*     */   private Boolean needLogin;
/*     */ 
/*     */   
/*     */   @Value("${security.loginAndLogout.loggable:false}")
/*     */   private boolean loginAndLogoutLoggable;
/*     */ 
/*     */   
/*     */   @Value("${security.login.url:/login.do}")
/*     */   private String generalLoginUrl;
/*     */ 
/*     */   
/*     */   @Value("${security.josso.login:false}")
/*     */   protected Boolean useJosso;
/*     */   
/*     */   @Value("${josso.agentLoginUrl:/josso_login/}")
/*     */   private String jossoLoginUrl;
/*     */   
/*     */   @Value("${josso.agentLogoutUrl:/josso_logout/}")
/*     */   private String jossoLogoutUrl;
/*     */   
/*     */   @Value("${josso.identityManagerWsdlLocation}")
/*     */   private String identityManagerWsdlLocation;
/*     */   
/*     */   @Value("${josso.identityProviderWsdlLocation}")
/*     */   private String identityProviderWsdlLocation;
/*     */   
/*     */   @Value("${josso.sessionManagerWsdlLocation}")
/*     */   private String sessionManagerWsdlLocation;
/*     */   
/*     */   @Value("${josso.endpoint:127.0.0.1:18080}")
/*     */   private String endpoint;
/*     */   
/*     */   @Autowired
/*     */   protected MessageSourceExt messageSourceExt;
/*     */ 
/*     */   
/*     */   protected void configure(HttpSecurity http) throws Exception {
/* 143 */     logger.info("needLogin:{}, useJosso:{}", this.needLogin, this.useJosso);
/* 144 */     http.csrf().disable();
/* 145 */     if (this.needLogin.booleanValue()) {
/* 146 */       if (this.useJosso.booleanValue()) {
/* 147 */         configJossoComponentKeeper();
/* 148 */         ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.authorizeRequests()
/* 149 */           .anyRequest())
/* 150 */           .authenticated()
/* 151 */           .accessDecisionManager(accessDecisionManager());
/* 152 */         http.logout()
/* 153 */           .logoutSuccessUrl(this.jossoLogoutUrl)
/* 154 */           .deleteCookies(new String[] { "JSESSIONID,JOSSO_SESSIONID,JOSSO_AUTOMATIC_LOGIN_EXECUTED"
/* 155 */             }).invalidateHttpSession(true)
/* 156 */           .permitAll();
/* 157 */         http.addFilterAfter((Filter)jossoSessionValidationFilter(), LogoutFilter.class);
/* 158 */         http.addFilterAfter((Filter)jossoServletFilter(), SecurityContextHolderAwareRequestFilter.class);
/* 159 */         http.addFilterBefore((Filter)
/* 160 */             jossoPostAuthenticationFilter(), SecurityContextHolderAwareRequestFilter.class);
/*     */       } else {
/* 162 */         http.csrf().disable();
/* 163 */         ((FormLoginConfigurer)((FormLoginConfigurer)((FormLoginConfigurer)((HttpSecurity)((HttpSecurity)((ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.authorizeRequests()
/* 164 */           .anyRequest())
/* 165 */           .fullyAuthenticated()
/* 166 */           .accessDecisionManager(accessDecisionManager()))
/* 167 */           .and())
/* 168 */           .exceptionHandling()
/* 169 */           .accessDeniedHandler(new AccessDeniedTopicHandler())
/* 170 */           .and())
/* 171 */           .formLogin()
/* 172 */           .failureHandler(new LoggableAuthenticationFailureHandler()))
/* 173 */           .successHandler(new LoggableAuthenticationSuccessHandler()))
/* 174 */           .loginPage(this.generalLoginUrl)
/* 175 */           .loginProcessingUrl("/j_spring_security_check"))
/* 176 */           .permitAll();
/* 177 */         http.logout().logoutSuccessHandler(new LoggableLogoutSuccessHandler()).permitAll();
/*     */       } 
/*     */     } else {
/* 180 */       ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.authorizeRequests().anyRequest()).permitAll();
/*     */     } 
/* 182 */     http.headers()
/* 183 */       .addHeaderWriter((HeaderWriter)new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
/* 189 */     if (this.needLogin.booleanValue()) {
/* 190 */       if (!this.useJosso.booleanValue()) {
/* 191 */         auth.authenticationProvider(authenticationProvider());
/*     */       } else {
/* 193 */         super.configure(auth);
/*     */       } 
/*     */     } else {
/* 196 */       super.configure(auth);
/*     */     } 
/*     */   }
/*     */   
/*     */   public AuthenticationProvider authenticationProvider() {
/* 201 */     DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
/* 202 */     provider.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
/* 203 */     provider.setUserDetailsService(new HzUserDetailsService(this.hzClient));
/* 204 */     return (AuthenticationProvider)provider;
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public AccessDecisionManager accessDecisionManager() {
/* 209 */     List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
/*     */     
/* 211 */     AccessDecisionVoter4Am amAccessDecisionVoter = new AccessDecisionVoter4Am(this.hzClient);
/* 212 */     if (!this.needLogin.booleanValue()) {
/* 213 */       amAccessDecisionVoter.setAllowAll(true);
/*     */     }
/* 215 */     else if (!this.useJosso.booleanValue()) {
/*     */       try {
/* 217 */         amAccessDecisionVoter.getAllowList().add((new URI(this.generalLoginUrl)).getPath());
/* 218 */       } catch (URISyntaxException e) {
/* 219 */         logger.warn("set allow url have exception.", e);
/*     */       } 
/*     */     } 
/*     */     
/* 223 */     decisionVoters.add(amAccessDecisionVoter);
/*     */     
/* 225 */     AffirmativeBased affirmativeBased = new AffirmativeBased(decisionVoters);
/* 226 */     return (AccessDecisionManager)affirmativeBased;
/*     */   }
/*     */   
/*     */   private void configJossoComponentKeeper() {
/* 230 */     System.getProperties()
/* 231 */       .setProperty("org.josso.agent.config.ComponentKeeperFactory", "com.hwacom.ngtms.common.security.josso.JossoAgentComponentKeeperFactoryImpl");
/*     */ 
/*     */     
/* 234 */     JossoAgentComponentKeeperImpl.setEndpoint(this.endpoint);
/* 235 */     JossoAgentComponentKeeperImpl.setIdentityManagerWsdlLocation(this.identityManagerWsdlLocation);
/* 236 */     JossoAgentComponentKeeperImpl.setSessionManagerWsdlLocation(this.sessionManagerWsdlLocation);
/* 237 */     JossoAgentComponentKeeperImpl.setIdentityProviderWsdlLocation(this.identityProviderWsdlLocation);
/*     */     
/*     */     try {
/* 240 */       Runnable preLoading = () -> {
/*     */           logger.trace("pre loading... starting");
/*     */           
/*     */           String tmpUrl = "http://127.0.0.1:8080/login.do";
/*     */           
/*     */           if (this.generalLoginUrl.startsWith("http")) {
/*     */             tmpUrl = this.generalLoginUrl;
/*     */           }
/*     */           
/*     */           int count = 0;
/*     */           
/*     */           boolean flag = false;
/*     */           while (count < 30 && !flag) {
/*     */             try {
/*     */               URLConnection urlConnection = (new URL(tmpUrl)).openConnection();
/*     */               InputStream is = urlConnection.getInputStream();
/*     */               int len = -1;
/*     */               byte[] buffer = new byte[1024];
/*     */               while ((len = is.read(buffer)) != -1);
/*     */               flag = true;
/* 260 */             } catch (IOException e) {
/*     */               try {
/*     */                 Thread.sleep(1000L);
/* 263 */               } catch (Exception exception) {}
/*     */             } 
/*     */             
/*     */             count++;
/*     */           } 
/*     */           
/*     */           logger.trace("pre loading... count:{}", Integer.valueOf(count));
/*     */         };
/* 271 */       (new Thread(preLoading)).start();
/* 272 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GenericServletSSOAgentFilter jossoServletFilter() {
/* 278 */     GenericServletSSOAgentFilter filter = new GenericServletSSOAgentFilter();
/* 279 */     return filter;
/*     */   }
/*     */ 
/*     */   
/*     */   public JossoGenericServletAgentPostAuthenticationFilter jossoPostAuthenticationFilter() {
/* 284 */     JossoGenericServletAgentPostAuthenticationFilter filter = new JossoGenericServletAgentPostAuthenticationFilter();
/*     */     
/* 286 */     filter.setHzClient(this.hzClient);
/* 287 */     return filter;
/*     */   }
/*     */ 
/*     */   
/*     */   public JossoSessionValidationFilter jossoSessionValidationFilter() {
/* 292 */     JossoSessionValidationFilter filter = new JossoSessionValidationFilter();
/* 293 */     filter.setServiceLocator(gatewayServiceLocator());
/* 294 */     return filter;
/*     */   }
/*     */   
/*     */   private JAXWSWebserviceGatewayServiceLocator gatewayServiceLocator() {
/* 298 */     JossoJAXWSWebserviceGatewayServiceLocator locator = new JossoJAXWSWebserviceGatewayServiceLocator();
/*     */     
/* 300 */     locator.setIdentityManagerWsdlLocation(this.identityManagerWsdlLocation);
/* 301 */     locator.setSessionManagerWsdlLocation(this.sessionManagerWsdlLocation);
/* 302 */     locator.setIdentityProviderWsdlLocation(this.identityProviderWsdlLocation);
/* 303 */     locator.setEndpoint(this.endpoint);
/* 304 */     return (JAXWSWebserviceGatewayServiceLocator)locator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void log(HttpServletRequest request, String username, OperationResult operationResult, String messageCode) {
/* 312 */     if (this.loginAndLogoutLoggable) {
/* 313 */       IQueue<OperationLogData> queue = this.hzClient.getIQueue((HzDistObjEnum)CommonHzQueue.OPERATION_LOG);
/* 314 */       OperationLogData log = new OperationLogData();
/* 315 */       log.setUserId(username);
/* 316 */       log.setCpeIp(request.getRemoteAddr());
/* 317 */       log.setSubSystem("");
/* 318 */       log.setOperationItem(OperationItem.SET);
/* 319 */       log.setDeviceName(null);
/* 320 */       log.setOperationTime(new Date());
/* 321 */       log.setOperationResult(operationResult);
/* 322 */       log.setRemark(null);
/* 323 */       log.setDescription(this.messageSourceExt.getMessage(messageCode));
/*     */       try {
/* 325 */         queue.put(log);
/* 326 */         logger.debug("Put log to queue. '{}'", log);
/* 327 */       } catch (InterruptedException e) {
/* 328 */         logger.warn("Put operatin log to queue failed! '{}'", log, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isUserLock(String username) {
/* 334 */     IMap<String, LocalDateTime> userLockTimeMap = this.hzClient.getIMap((HzDistObjEnum)CommonHzMap.AccountUserLockTime);
/* 335 */     LocalDateTime lockTime = (LocalDateTime)userLockTimeMap.get(username);
/* 336 */     logger.debug("User lock time: '{}'", lockTime);
/* 337 */     if (lockTime == null) {
/* 338 */       return false;
/*     */     }
/* 340 */     return lockTime.isAfter(LocalDateTime.now());
/*     */   }
/*     */   
/*     */   class HzUserDetailsService
/*     */     implements UserDetailsService {
/*     */     private HazelcastClient hzClient;
/*     */     
/*     */     public HzUserDetailsService(HazelcastClient hzClient) {
/* 348 */       this.hzClient = hzClient;
/*     */     }
/*     */ 
/*     */     
/*     */     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
/* 353 */       User user = null;
/* 354 */       IMap<String, User> userMap = null;
/*     */       try {
/* 356 */         userMap = this.hzClient.getIMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 357 */         for (User u : userMap.values()) {
/* 358 */           if (Boolean.TRUE.equals(u.getCheckExpired())) {
/* 359 */             long now = (new Date()).getTime();
/* 360 */             long start = ((Long)Optional.<Date>ofNullable(u.getStartTime()).map(Date::getTime).orElse(Long.valueOf(0L))).longValue();
/* 361 */             long end = ((Long)Optional.<Date>ofNullable(u.getEndTime()).map(Date::getTime).orElse(Long.valueOf(0L))).longValue();
/* 362 */             if (now < start || now > end) {
/*     */               continue;
/*     */             }
/*     */           } 
/* 366 */           if (Boolean.TRUE.equals(u.getEnable()) && username
/* 367 */             .equals(u.getLogin()) && 
/* 368 */             !WebSecurityConfig4Am.this.isUserLock(username)) {
/* 369 */             user = u;
/*     */             break;
/*     */           } 
/*     */         } 
/* 373 */         WebSecurityConfig4Am.logger.info("User Info : {}", user);
/* 374 */         if (user == null) {
/* 375 */           WebSecurityConfig4Am.logger.debug("User not found with the provided username: {}", username);
/* 376 */           throw new UsernameNotFoundException("User not found");
/*     */         } 
/* 378 */       } catch (Exception e) {
/* 379 */         if (userMap != null) {
/* 380 */           WebSecurityConfig4Am.logger.debug("provided username: {}, user:{}", username, user);
/*     */         }
/* 382 */         throw new RuntimeException(e.getMessage());
/*     */       } 
/*     */       
/* 385 */       List<SimpleGrantedAuthority> authorities = new ArrayList<>();
/*     */       try {
/* 387 */         Set<String> roles = new HashSet<>(user.getRoleNames());
/* 388 */         roles.add("ROLE_ANONYMOUS");
/* 389 */         for (String role : roles) {
/* 390 */           authorities.add(new SimpleGrantedAuthority(role));
/* 391 */           WebSecurityConfig4Am.logger.info("Role Info : {}", role);
/*     */         } 
/* 393 */       } catch (Exception e) {
/* 394 */         throw new RuntimeException(e.getMessage());
/*     */       } 
/*     */       
/* 397 */       return (UserDetails)new User(username, user
/* 398 */           .getPwd1(), authorities);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class LoggableAuthenticationFailureHandler
/*     */     implements AuthenticationFailureHandler
/*     */   {
/* 409 */     private String defaultFailureUrl = WebSecurityConfig4Am.this.generalLoginUrl + "?error";
/* 410 */     private SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
/* 417 */       this.handler.setDefaultFailureUrl(this.defaultFailureUrl);
/* 418 */       String username = request.getParameter("username");
/* 419 */       IMap<String, User> userMap = WebSecurityConfig4Am.this.hzClient.getIMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 420 */       Optional.<Object>ofNullable(userMap.get(username))
/* 421 */         .ifPresent(u -> WebSecurityConfig4Am.this.log(param1HttpServletRequest, param1String, OperationResult.FAILURE, "user.login"));
/* 422 */       defendBruteForce(username);
/* 423 */       this.handler.onAuthenticationFailure(request, response, exception);
/*     */     }
/*     */     
/*     */     private void defendBruteForce(String username) {
/* 427 */       if (WebSecurityConfig4Am.this.surveillanceAccount.equals(username)) {
/*     */         return;
/*     */       }
/* 430 */       int count = increaseFailureTimes(username);
/* 431 */       WebSecurityConfig4Am.logger.debug("Authenticate failure. username: '{}', count: '{}'", username, Integer.valueOf(count));
/* 432 */       IMap<String, User> userMap = WebSecurityConfig4Am.this.hzClient.getIMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 433 */       User user = (User)userMap.get(username);
/* 434 */       if (user != null) {
/* 435 */         if (count >= WebSecurityConfig4Am.this.failureLimit) {
/*     */           
/* 437 */           IMap<String, LocalDateTime> userLockTimeMap = WebSecurityConfig4Am.this.hzClient.getIMap((HzDistObjEnum)CommonHzMap.AccountUserLockTime);
/* 438 */           userLockTimeMap.put(username, LocalDateTime.now().plusMinutes(WebSecurityConfig4Am.this.lockMinutes));
/*     */         } 
/* 440 */         if (!Boolean.TRUE.equals(user.getEnable())) {
/* 441 */           this.handler.setDefaultFailureUrl(this.defaultFailureUrl + "&userDisabled");
/* 442 */         } else if (WebSecurityConfig4Am.this.isUserLock(username)) {
/*     */           
/* 444 */           IMap<String, LocalDateTime> userLockTimeMap = WebSecurityConfig4Am.this.hzClient.getIMap((HzDistObjEnum)CommonHzMap.AccountUserLockTime);
/* 445 */           LocalDateTime lockTime = (LocalDateTime)userLockTimeMap.get(username);
/* 446 */           this.handler.setDefaultFailureUrl(this.defaultFailureUrl + "&userLockMinutes=" + WebSecurityConfig4Am.this
/*     */ 
/*     */               
/* 449 */               .lockMinutes + "&userLockTime=" + lockTime
/*     */               
/* 451 */               .format(DateTimeFormatter.ofPattern("y-MM-dd HH:mm:ss")));
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private int increaseFailureTimes(String username) {
/* 458 */       IMap<String, Integer> userLoginFailureTimes = WebSecurityConfig4Am.this.hzClient.getIMap((HzDistObjEnum)CommonHzMap.AccountUserLoginFailureTimes);
/* 459 */       return ((Integer)userLoginFailureTimes.compute(username, (k, v) -> Integer.valueOf(((Integer)Optional.<Integer>ofNullable(v).orElse(Integer.valueOf(0))).intValue() + 1))).intValue();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class LoggableAuthenticationSuccessHandler
/*     */     implements AuthenticationSuccessHandler
/*     */   {
/* 469 */     private AuthenticationSuccessHandler handler = (AuthenticationSuccessHandler)new SavedRequestAwareAuthenticationSuccessHandler();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
/* 476 */       String username = AuthenticationUtils.getUserLoginName(authentication);
/* 477 */       WebSecurityConfig4Am.this.log(request, username, OperationResult.SUCCESS, "user.login");
/*     */       
/* 479 */       IMap<String, Integer> userLoginFailureTimes = WebSecurityConfig4Am.this.hzClient.getIMap((HzDistObjEnum)CommonHzMap.AccountUserLoginFailureTimes);
/* 480 */       IMapLocker.removeMapping(userLoginFailureTimes, username);
/*     */       
/* 482 */       IMap<String, LocalDateTime> userLockTimeMap = WebSecurityConfig4Am.this.hzClient.getIMap((HzDistObjEnum)CommonHzMap.AccountUserLockTime);
/* 483 */       IMapLocker.removeMapping(userLockTimeMap, username);
/* 484 */       IMap<String, User> userMap = WebSecurityConfig4Am.this.hzClient.getIMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 485 */       Optional.<Object>ofNullable(userMap.get(username))
/* 486 */         .ifPresent(user -> {
/*     */             user.setLastLoginTime(new Date());
/*     */             
/*     */             IMapLocker.addOrUpdateMapping(param1IMap, param1String, user);
/*     */           });
/*     */       
/* 492 */       this.handler.onAuthenticationSuccess(request, response, authentication);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class LoggableLogoutSuccessHandler
/*     */     implements LogoutSuccessHandler
/*     */   {
/*     */     public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
/* 501 */       WebSecurityConfig4Am.this.log(request, 
/*     */           
/* 503 */           AuthenticationUtils.getUserLoginName(authentication), OperationResult.SUCCESS, "user.logout");
/*     */ 
/*     */       
/* 506 */       response.sendRedirect("/index.do");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class AccessDeniedTopicHandler
/*     */     implements AccessDeniedHandler
/*     */   {
/*     */     public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
/* 517 */       String uri = request.getRequestURI();
/* 518 */       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
/* 519 */       String name = authentication.getName();
/* 520 */       WebSecurityConfig4Am.logger.debug("Access denied. login: '{}', uri: '{}'", name, uri);
/* 521 */       AccessDeniedInfo info = new AccessDeniedInfo();
/* 522 */       info.setLogin(name);
/* 523 */       info.setUri(uri);
/* 524 */       ITopic<AccessDeniedInfo> topic = WebSecurityConfig4Am.this.hzClient.getITopic((HzDistObjEnum)CommonHzTopic.ACCOUNT_ACCESS_DENIED);
/* 525 */       topic.publish(info);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\WebSecurityConfig4Am.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */