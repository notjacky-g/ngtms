/*    */ package com.hwacom.ngtms.rtu.restful;
/*    */ 
/*    */ import com.hwacom.ngtms.c.restful.BaseRestful;
/*    */ import javax.annotation.PostConstruct;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.web.bind.annotation.CrossOrigin;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RestController;
/*    */ 
/*    */ @CrossOrigin
/*    */ @RestController
/*    */ @RequestMapping({"/api/rtu/common"})
/*    */ public class RtuCommonRestServiceImpl
/*    */   extends BaseRestful {
/* 16 */   private static final Logger logger = LoggerFactory.getLogger(RtuCommonRestServiceImpl.class);
/*    */   
/*    */   @PostConstruct
/*    */   public void init() {}
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\fm-rtu-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\rtu\restful\RtuCommonRestServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */