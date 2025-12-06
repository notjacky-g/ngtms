/*    */ package com.hwacom.ngtms.common;
/*    */ 
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*    */ import java.time.LocalDateTime;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.web.bind.annotation.CrossOrigin;
/*    */ import org.springframework.web.bind.annotation.PathVariable;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RequestMethod;
/*    */ import org.springframework.web.bind.annotation.RestController;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CrossOrigin
/*    */ @RestController
/*    */ @RequestMapping({"/api/common"})
/*    */ public class CommonRestService
/*    */ {
/* 26 */   private static final Logger logger = LoggerFactory.getLogger(CommonRestService.class);
/*    */   
/*    */   @RequestMapping(value = {"/unlock/{user}"}, method = {RequestMethod.GET})
/*    */   public void unlock(@PathVariable("user") String user) {
/*    */     try {
/* 31 */       IMap<String, LocalDateTime> userLockTimeMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUserLockTime);
/* 32 */       userLockTimeMap.remove(user);
/* 33 */     } catch (RuntimeException ex) {
/* 34 */       logger.warn("Unlock user failed! user: '{}'", user, ex);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\CommonRestService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */