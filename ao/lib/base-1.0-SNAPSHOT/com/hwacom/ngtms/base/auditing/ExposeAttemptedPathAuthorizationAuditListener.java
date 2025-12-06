/*    */ package com.hwacom.ngtms.base.auditing;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.springframework.boot.actuate.audit.AuditEvent;
/*    */ import org.springframework.boot.actuate.security.AbstractAuthorizationAuditListener;
/*    */ import org.springframework.security.access.AccessDeniedException;
/*    */ import org.springframework.security.access.event.AbstractAuthorizationEvent;
/*    */ import org.springframework.security.access.event.AuthorizationFailureEvent;
/*    */ import org.springframework.security.core.Authentication;
/*    */ import org.springframework.security.web.FilterInvocation;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class ExposeAttemptedPathAuthorizationAuditListener
/*    */   extends AbstractAuthorizationAuditListener
/*    */ {
/*    */   public static final String AUTHORIZATION_FAILURE = "AUTHORIZATION_FAILURE";
/*    */   
/*    */   public void onApplicationEvent(AbstractAuthorizationEvent event)
/*    */   {
/* 26 */     if ((event instanceof AuthorizationFailureEvent)) {
/* 27 */       onAuthorizationFailureEvent((AuthorizationFailureEvent)event);
/*    */     }
/*    */   }
/*    */   
/*    */   private void onAuthorizationFailureEvent(AuthorizationFailureEvent event) {
/* 32 */     Map<String, Object> data = new HashMap();
/* 33 */     data.put("type", event.getAccessDeniedException().getClass().getName());
/* 34 */     data.put("message", event.getAccessDeniedException().getMessage());
/* 35 */     data.put("requestUrl", ((FilterInvocation)event.getSource()).getRequestUrl());
/* 36 */     if (event.getAuthentication().getDetails() != null) {
/* 37 */       data.put("details", event.getAuthentication().getDetails());
/*    */     }
/* 39 */     publish(new AuditEvent(event.getAuthentication().getName(), "AUTHORIZATION_FAILURE", data));
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\auditing\ExposeAttemptedPathAuthorizationAuditListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */