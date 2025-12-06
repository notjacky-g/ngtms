/*    */ package com.hwacom.ngtms.ao.am.presenter;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.hwacom.ngtms.ao.am.AoEP;
/*    */ import com.hwacom.ngtms.ao.am.view.AlarmMonitorGridByType;
/*    */ import com.hwacom.ngtms.ao.shared.dto.AlarmSendMessageDTO;
/*    */ import org.fusesource.restygwt.client.Method;
/*    */ import org.fusesource.restygwt.client.MethodCallback;
/*    */ 
/*    */ public class AlarmMonitorGridByTypePresenter
/*    */ {
/*    */   private AlarmMonitorGridByType viewer;
/*    */   
/*    */   public AlarmMonitorGridByTypePresenter(AlarmMonitorGridByType viewer) {
/* 15 */     this.viewer = viewer;
/*    */   }
/*    */   
/*    */   public void alarmSendMessage(AlarmSendMessageDTO dto) {
/* 19 */     AoEP.aoAlarmService.alarmSendMessage(dto, new MethodCallback<Void>()
/*    */         {
/*    */           
/*    */           public void onSuccess(Method method, Void result)
/*    */           {
/* 24 */             AlarmMonitorGridByTypePresenter.this.viewer.sendMessage();
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable caught) {
/* 29 */             GWT.log("alarmSendMessage failed.", caught);
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public void alarmSendVoice() {
/* 35 */     AoEP.aoAlarmService.getPlayAlarmMp3(new MethodCallback<Void>()
/*    */         {
/*    */           public void onSuccess(Method method, Void result)
/*    */           {
/* 39 */             GWT.log("getPlayAlarmMp3.");
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable caught) {
/* 44 */             GWT.log("getPlayAlarmMp3 failed.", caught);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\AlarmMonitorGridByTypePresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */