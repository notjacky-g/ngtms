/*    */ package com.hwacom.ngtms.base.web;
/*    */ 
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.boot.web.servlet.error.ErrorController;
/*    */ import org.springframework.http.HttpStatus;
/*    */ import org.springframework.ui.Model;
/*    */ import org.springframework.web.bind.annotation.ControllerAdvice;
/*    */ import org.springframework.web.bind.annotation.ExceptionHandler;
/*    */ import org.springframework.web.bind.annotation.ResponseStatus;
/*    */ import org.springframework.web.servlet.ModelAndView;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ControllerAdvice
/*    */ public class WebErrorController
/*    */   implements ErrorController
/*    */ {
/* 24 */   private static Logger logger = LoggerFactory.getLogger(WebErrorController.class);
/*    */   
/*    */   @ExceptionHandler({Throwable.class})
/*    */   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
/*    */   public String exception(Throwable throwable, Model model) {
/* 29 */     logger.error("Exception during execution of SpringSecurity application", throwable);
/* 30 */     String errorMessage = (throwable != null) ? throwable.getMessage() : "Unknown error";
/* 31 */     model.addAttribute("errorMessage", errorMessage);
/* 32 */     return "error";
/*    */   }
/*    */   
/*    */   @ResponseStatus(HttpStatus.NOT_FOUND)
/*    */   public ModelAndView handleNotFound(HttpServletRequest req, HttpServletResponse res) {
/* 37 */     ModelAndView mav = new ModelAndView();
/* 38 */     mav.addObject("errors", "page does not exists");
/* 39 */     mav.setViewName("error");
/* 40 */     return mav;
/*    */   }
/*    */   
/*    */   @ResponseStatus(HttpStatus.UNAUTHORIZED)
/*    */   public ModelAndView handleUnauthorized(HttpServletRequest req, HttpServletResponse res) {
/* 45 */     ModelAndView mav = new ModelAndView();
/* 46 */     mav.addObject("errors", "you are not authorized to view this page");
/* 47 */     mav.setViewName("error");
/* 48 */     return mav;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getErrorPath() {
/* 53 */     logger.info("routing to error page");
/* 54 */     return "error";
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\web\WebErrorController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */