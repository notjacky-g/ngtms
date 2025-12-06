/*    */ package com.hwacom.ngtms.base.web;
/*    */ 
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.security.core.Authentication;
/*    */ import org.springframework.security.core.context.SecurityContext;
/*    */ import org.springframework.security.core.context.SecurityContextHolder;
/*    */ import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Controller
/*    */ public class WebController
/*    */ {
/* 24 */   private static final Logger logger = LoggerFactory.getLogger(WebController.class);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @RequestMapping(value={"/login.do"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
/*    */   public String login()
/*    */   {
/* 32 */     return "login";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   @RequestMapping(value={"/logout.do"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
/*    */   public String defaultPage(ModelMap model, HttpServletRequest req, HttpServletResponse resp)
/*    */   {
/* 40 */     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
/* 41 */     if (auth != null) {
/* 42 */       logger.debug("Logout User Name : {} ", auth.getName());
/* 43 */       SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
/*    */       
/* 45 */       securityContextLogoutHandler.setClearAuthentication(true);
/* 46 */       securityContextLogoutHandler.setInvalidateHttpSession(true);
/* 47 */       securityContextLogoutHandler.logout(req, resp, auth);
/*    */     }
/* 49 */     return "redirect:index.do";
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\web\WebController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */