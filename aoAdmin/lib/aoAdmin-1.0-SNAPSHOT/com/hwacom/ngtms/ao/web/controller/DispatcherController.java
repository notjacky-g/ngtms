/*    */ package com.hwacom.ngtms.ao.web.controller;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.security.core.Authentication;
/*    */ import org.springframework.security.core.context.SecurityContextHolder;
/*    */ import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.Model;
/*    */ import org.springframework.ui.ModelMap;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RequestMethod;
/*    */ 
/*    */ @Controller
/*    */ public class DispatcherController
/*    */ {
/* 20 */   private static Logger logger = LoggerFactory.getLogger(DispatcherController.class);
/*    */   
/*    */   @RequestMapping({"/loginAo.do"})
/*    */   public String loginAo(Model model, HttpServletRequest req, HttpServletResponse resp) {
/* 24 */     String uuid = UUID.randomUUID().toString().replaceAll("-", "");
/* 25 */     req.getSession().setAttribute("randTxt", uuid);
/* 26 */     String sessionid = req.getSession().getId();
/* 27 */     resp.setHeader("SET-COOKIE", "JSESSIONID=" + sessionid + "; secure ; HttpOnly");
/* 28 */     return "loginAo";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @RequestMapping(value = {"/logoutAo.do"}, method = {RequestMethod.GET, RequestMethod.POST})
/*    */   public String defaultPage(ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
/* 36 */     String uuid = UUID.randomUUID().toString().replaceAll("-", "");
/* 37 */     req.getSession().setAttribute("randTxt", uuid);
/* 38 */     String sessionid = req.getSession().getId();
/* 39 */     resp.setHeader("SET-COOKIE", "JSESSIONID=" + sessionid + "; secure ; HttpOnly");
/* 40 */     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
/* 41 */     if (auth != null) {
/* 42 */       logger.debug("Logout User Name : {} ", auth.getName());
/* 43 */       SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
/*    */       
/* 45 */       securityContextLogoutHandler.setClearAuthentication(true);
/* 46 */       securityContextLogoutHandler.setInvalidateHttpSession(true);
/* 47 */       securityContextLogoutHandler.logout(req, resp, auth);
/*    */     } 
/* 49 */     return "redirect:ao.html";
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\web\controller\DispatcherController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */