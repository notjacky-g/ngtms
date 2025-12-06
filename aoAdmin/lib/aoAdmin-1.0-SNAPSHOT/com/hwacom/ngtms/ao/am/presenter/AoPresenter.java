/*    */ package com.hwacom.ngtms.ao.am.presenter;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.hwacom.ngtms.ao.am.AoEP;
/*    */ import com.hwacom.ngtms.ao.am.view.AoViewer;
/*    */ import com.hwacom.ngtms.ao.shared.dto.AlarmMessageDTO;
/*    */ import org.fusesource.restygwt.client.Method;
/*    */ import org.fusesource.restygwt.client.MethodCallback;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AoPresenter
/*    */ {
/*    */   private AoViewer viewer;
/*    */   
/*    */   public AoPresenter(AoViewer viewer) {
/* 21 */     this.viewer = viewer;
/* 22 */     viewer.setPresenter(this);
/*    */   }
/*    */   
/*    */   public void confirmAlarm(AlarmMessageDTO dto) {
/* 26 */     AoEP.aoAlarmService.confirmAlarm(dto, new MethodCallback<String>()
/*    */         {
/*    */           public void onSuccess(Method method, String response) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable exception) {
/* 35 */             GWT.log("AoPresenter.confirmAlarm failed.", exception);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\AoPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */