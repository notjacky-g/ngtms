/*    */ package com.hwacom.ngtms.base.auditing;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.boot.actuate.audit.AuditEvent;
/*    */ import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
/*    */ import org.springframework.context.event.EventListener;
/*    */ import org.springframework.security.web.authentication.WebAuthenticationDetails;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class LoginAttemptsLogger
/*    */ {
/* 19 */   private static Logger logger = LoggerFactory.getLogger(LoginAttemptsLogger.class);
/*    */   
/*    */   @EventListener
/*    */   public void actualAuditEvent(AuditApplicationEvent event) {
/* 23 */     AuditEvent actualAuditEvent = event.getAuditEvent();
/*    */     
/* 25 */     logger.info("On audit application event \r\n timestamp: {}, principal: {}, type: {}, data: {}", new Object[] {actualAuditEvent
/*    */     
/* 27 */       .getTimestamp(), actualAuditEvent
/* 28 */       .getPrincipal(), actualAuditEvent
/* 29 */       .getType(), actualAuditEvent
/* 30 */       .getData() });
/*    */     
/*    */ 
/* 33 */     WebAuthenticationDetails details = (WebAuthenticationDetails)actualAuditEvent.getData().get("details");
/* 34 */     logger.info("  Remote IP address: " + details.getRemoteAddress());
/* 35 */     logger.info("  Session Id: " + details.getSessionId());
/* 36 */     logger.info("  Request URL: " + actualAuditEvent.getData().get("requestUrl"));
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\auditing\LoginAttemptsLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */