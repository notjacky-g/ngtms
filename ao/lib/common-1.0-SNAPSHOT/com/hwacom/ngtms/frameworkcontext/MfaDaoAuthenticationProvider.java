/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hwacom.ngtms.base.hazelcast.HazelcastClient;
/*    */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*    */ import com.hwacom.ngtms.common.fm.model.User;
/*    */ import dev.samstevens.totp.code.CodeGenerator;
/*    */ import dev.samstevens.totp.code.CodeVerifier;
/*    */ import dev.samstevens.totp.code.DefaultCodeGenerator;
/*    */ import dev.samstevens.totp.code.DefaultCodeVerifier;
/*    */ import dev.samstevens.totp.time.SystemTimeProvider;
/*    */ import dev.samstevens.totp.time.TimeProvider;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.security.authentication.BadCredentialsException;
/*    */ import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
/*    */ import org.springframework.security.core.Authentication;
/*    */ import org.springframework.security.core.AuthenticationException;
/*    */ import org.springframework.security.core.userdetails.UsernameNotFoundException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MfaDaoAuthenticationProvider
/*    */   extends DaoAuthenticationProvider
/*    */ {
/* 29 */   private static final Logger logger = LoggerFactory.getLogger(MfaDaoAuthenticationProvider.class);
/*    */   private HazelcastClient hzClient;
/*    */   
/*    */   public MfaDaoAuthenticationProvider(HazelcastClient hzClient)
/*    */   {
/* 34 */     this.hzClient = hzClient;
/*    */   }
/*    */   
/*    */   public Authentication authenticate(Authentication authentication) throws AuthenticationException
/*    */   {
/* 39 */     Authentication result = super.authenticate(authentication);
/* 40 */     logger.debug("Mfa authenticate.");
/* 41 */     Object details = authentication.getDetails();
/* 42 */     if ((details instanceof MfaAuthenticationDetails)) {
/* 43 */       MfaAuthenticationDetails authenticationDetails = (MfaAuthenticationDetails)details;
/* 44 */       String verificationCode = authenticationDetails.getVerificationCode();
/* 45 */       IMap<String, User> userMap = this.hzClient.getIMap(CommonHzMap.AccountUser);
/* 46 */       User user = (User)userMap.get(authentication.getName());
/* 47 */       if (user == null) {
/* 48 */         throw new UsernameNotFoundException("Invalid username.");
/*    */       }
/* 50 */       if (!verify(user, verificationCode)) {
/* 51 */         throw new BadCredentialsException("Invalid verfication code");
/*    */       }
/*    */     }
/* 54 */     return result;
/*    */   }
/*    */   
/*    */   private boolean verify(User user, String verificationCode) {
/* 58 */     TimeProvider timeProvider = new SystemTimeProvider();
/* 59 */     CodeGenerator codeGenerator = new DefaultCodeGenerator();
/* 60 */     CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
/* 61 */     boolean result = verifier.isValidCode(user.getMfaSecret(), verificationCode);
/* 62 */     logger.debug("Verify user. user: '{}', valid: '{}'", user.getLogin(), Boolean.valueOf(result));
/* 63 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\MfaDaoAuthenticationProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */