/*    */ package com.hwacom.ngtms.ao.restful;
/*    */ 
/*    */ import com.hwacom.ngtms.common.fm.service.AccountService;
/*    */ import com.hwacom.ngtms.common.shared.dto.UserBasicInfoDTO;
/*    */ import com.hwacom.ngtms.common.util.AuthUtils;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.core.env.Environment;
/*    */ import org.springframework.web.bind.annotation.CrossOrigin;
/*    */ import org.springframework.web.bind.annotation.PathVariable;
/*    */ import org.springframework.web.bind.annotation.RequestBody;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RequestMethod;
/*    */ import org.springframework.web.bind.annotation.RestController;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CrossOrigin
/*    */ @RestController
/*    */ @RequestMapping({"/api/account/ao"})
/*    */ public class AoAccountRestServiceImpl
/*    */ {
/* 29 */   private static final Logger logger = LoggerFactory.getLogger(AoAccountRestServiceImpl.class);
/*    */   
/*    */   @Autowired
/*    */   private AccountService accountService;
/*    */   
/*    */   @RequestMapping(value = {"/retrieveUserBasicInfo/{login}"}, method = {RequestMethod.GET})
/*    */   public UserBasicInfoDTO retrieveUserBasicInfo(@PathVariable("login") String login) {
/*    */     try {
/* 37 */       return this.accountService.findUserBasicInfo(AuthUtils.decryptUserLogin(login));
/* 38 */     } catch (RuntimeException ex) {
/* 39 */       logger.warn("Retrieve user basic info failed!", ex);
/* 40 */       return null;
/*    */     } 
/*    */   } @Autowired
/*    */   protected Environment environment;
/*    */   @RequestMapping(value = {"/saveUserBasicInfo"}, method = {RequestMethod.POST})
/*    */   public void saveUserBasicInfo(HttpServletRequest request, @RequestBody UserBasicInfoDTO dto) {
/*    */     try {
/* 47 */       String login = AuthUtils.extractUserLogin(request);
/* 48 */       if (!login.equals(dto.getLogin())) {
/* 49 */         logger.warn("Login value is modified! expect: '{}', actual: '{}'", login, dto.getLogin());
/* 50 */         dto.setLogin(login);
/*    */       } 
/* 52 */       this.accountService.saveUserBasicInfo(dto);
/* 53 */     } catch (RuntimeException ex) {
/* 54 */       logger.warn("Save user basic info failed!", ex);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\restful\AoAccountRestServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */