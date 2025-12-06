/*     */ package com.hwacom.ngtms.ao.am.presenter;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.hwacom.ngtms.ao.am.AoEP;
/*     */ import com.hwacom.ngtms.ao.am.view.RoomStatusMonitorViewer;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmMessageDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomInfoDTO;
/*     */ import java.util.List;
/*     */ import org.fusesource.restygwt.client.Method;
/*     */ import org.fusesource.restygwt.client.MethodCallback;
/*     */ 
/*     */ public class RoomStatusMonitorPresenter
/*     */ {
/*     */   private RoomStatusMonitorViewer viewer;
/*     */   
/*     */   public RoomStatusMonitorPresenter(RoomStatusMonitorViewer viewer) {
/*  17 */     this.viewer = viewer;
/*     */   }
/*     */   
/*     */   public void getRoomLineData() {
/*  21 */     AoEP.aoService.getRoomLineData(new MethodCallback<List<String>>()
/*     */         {
/*     */           public void onSuccess(Method method, List<String> response)
/*     */           {
/*  25 */             RoomStatusMonitorPresenter.this.viewer.initLineData(response);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/*  30 */             GWT.log("RoomStatusMonitorPresenter.getRoomLineData failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void getRoomInfo() {
/*  36 */     AoEP.aoService.getRoomInfo(new MethodCallback<List<RoomInfoDTO>>()
/*     */         {
/*     */           public void onSuccess(Method method, List<RoomInfoDTO> response)
/*     */           {
/*  40 */             RoomStatusMonitorPresenter.this.viewer.initRoomData(response);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/*  45 */             GWT.log("RoomStatusMonitorPresenter.getRoomInfo failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void getRoomStatus() {
/*  51 */     AoEP.aoService.getRoomStatus(new MethodCallback<List<RoomInfoDTO>>()
/*     */         {
/*     */           public void onSuccess(Method method, List<RoomInfoDTO> response)
/*     */           {
/*  55 */             RoomStatusMonitorPresenter.this.viewer.refreshStatus(response);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/*  60 */             GWT.log("RoomStatusMonitorPresenter.getRoomStatus failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void getSafeStatus() {
/*  66 */     AoEP.aoService.getSafeStatus(new MethodCallback<List<RoomInfoDTO>>()
/*     */         {
/*     */           public void onSuccess(Method method, List<RoomInfoDTO> response)
/*     */           {
/*  70 */             RoomStatusMonitorPresenter.this.viewer.refreshSafeStatus(response);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/*  75 */             GWT.log("RoomStatusMonitorPresenter.getSafeStatus failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void getAllNonRtnAlarms() {
/*  81 */     AoEP.aoAlarmService.getAllNonRtnAlarms(new MethodCallback<List<AlarmMessageDTO>>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, List<AlarmMessageDTO> response)
/*     */           {
/*  86 */             RoomStatusMonitorPresenter.this.viewer.setAlarmMonitorGrid(response);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/*  91 */             GWT.log("RoomDeviceMonitorPresenter.getAllNonRtnAlarms failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void alarmSendVoice() {
/*  97 */     AoEP.aoAlarmService.getPlayAlarmMp3(new MethodCallback<Void>()
/*     */         {
/*     */           public void onSuccess(Method method, Void result)
/*     */           {
/* 101 */             GWT.log("getPlayAlarmMp3.");
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/* 106 */             GWT.log("getPlayAlarmMp3 failed.", caught);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void alarmStopVoice() {
/* 112 */     AoEP.aoAlarmService.getStopAlarmMp3(new MethodCallback<Void>()
/*     */         {
/*     */           public void onSuccess(Method method, Void result)
/*     */           {
/* 116 */             GWT.log("getStopAlarmMp3.");
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/* 121 */             GWT.log("getStopAlarmMp3 failed.", caught);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\RoomStatusMonitorPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */