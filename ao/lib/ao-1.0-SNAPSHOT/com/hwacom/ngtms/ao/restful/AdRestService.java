/*    */ package com.hwacom.ngtms.ao.restful;
/*    */ 
/*    */ import com.hwacom.ngtms.common.fm.service.AccountAdServiceImpl;
/*    */ import io.swagger.annotations.ApiOperation;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.web.bind.annotation.CrossOrigin;
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
/*    */ @RequestMapping({"/api/nvbr/ao/ad"})
/*    */ public class AdRestService
/*    */ {
/* 25 */   private static final Logger logger = LoggerFactory.getLogger(AdRestService.class);
/*    */   @Autowired
/*    */   private AccountAdServiceImpl adService;
/*    */   
/*    */   @RequestMapping(value = {"/sync"}, method = {RequestMethod.POST})
/*    */   @ApiOperation("AD 同步")
/*    */   public void sync(HttpServletRequest request) {
/*    */     try {
/* 33 */       this.adService.init();
/* 34 */     } catch (Exception ex) {
/* 35 */       logger.warn("Sync AD failed!", ex);
/* 36 */       throw new RuntimeException(ex.getMessage());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\restful\AdRestService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */