/*     */ package com.hwacom.ngtms.ao.am.presenter;
/*     */ 
/*     */ import com.google.gwt.core.shared.GWT;
/*     */ import com.hwacom.ngtms.ao.am.AoEP;
/*     */ import com.hwacom.ngtms.ao.am.view.RoomDeviceMonitorViewer;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmMessageDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomDeviceParamDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomGroupDeviceStatusDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomInfoDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomNCUStatusDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.DeviceSvgPositionConfigDTO;
/*     */ import com.hwacom.ngtms.room.shared.dto.RoomCctvUrlDTO;
/*     */ import com.hwacom.ngtms.room.shared.dto.RoomDeviceStatusDTO;
/*     */ import com.hwacom.ngtms.room.shared.dto.RoomDoDTO;
/*     */ import java.util.List;
/*     */ import org.fusesource.restygwt.client.Method;
/*     */ import org.fusesource.restygwt.client.MethodCallback;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RoomDeviceMonitorPresenter
/*     */ {
/*     */   private RoomDeviceMonitorViewer viewer;
/*     */   
/*     */   public RoomDeviceMonitorPresenter(RoomDeviceMonitorViewer viewer) {
/*  30 */     this.viewer = viewer;
/*     */   }
/*     */   
/*     */   public void getRoomLineData() {
/*  34 */     AoEP.aoService.getRoomLineData(new MethodCallback<List<String>>()
/*     */         {
/*     */           public void onSuccess(Method method, List<String> response)
/*     */           {
/*  38 */             RoomDeviceMonitorPresenter.this.viewer.initLineData(response);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/*  43 */             GWT.log("RoomDeviceMonitorPresenter.getRoomLineData failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void getRoomInfo() {
/*  49 */     AoEP.aoService.getRoomInfo(new MethodCallback<List<RoomInfoDTO>>()
/*     */         {
/*     */           public void onSuccess(Method method, List<RoomInfoDTO> response)
/*     */           {
/*  53 */             RoomDeviceMonitorPresenter.this.viewer.initRoomData(response);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/*  58 */             GWT.log("RoomDeviceMonitorPresenter.getRoomInfo failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void getRoomCctvVideo(final String backgroundId) {
/*  64 */     AoEP.aoService.getRoomCctvVideo(backgroundId, new MethodCallback<List<RoomCctvUrlDTO>>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, List<RoomCctvUrlDTO> response)
/*     */           {
/*  69 */             RoomDeviceMonitorPresenter.this.viewer.fillCctvUrl(response, backgroundId);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/*  74 */             GWT.log("RoomDeviceMonitorPresenter.getRoomCctvVideo failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void getRoomSafeInfo(String backgroundId) {
/*  80 */     AoEP.aoService.getRoomSafeInfo(backgroundId, new MethodCallback<RoomNCUStatusDTO>()
/*     */         {
/*     */ 
/*     */           
/*     */           public void onSuccess(Method method, RoomNCUStatusDTO response)
/*     */           {
/*  86 */             RoomDeviceMonitorPresenter.this.viewer.fillRoomSafeInfo(response);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/*  91 */             GWT.log("RoomDeviceMonitorPresenter.getRoomSafeInfo failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void queryDevicePositionConfig(final String svgName) {
/*  97 */     GWT.log("RoomDeviceMonitorPresenter.queryDevicePositionConfig");
/*  98 */     AoEP.aoService.queryDevicePositionConfig(svgName, new MethodCallback<List<DeviceSvgPositionConfigDTO>>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, List<DeviceSvgPositionConfigDTO> response)
/*     */           {
/* 103 */             RoomDeviceMonitorPresenter.this.viewer.fillDeviceSvg(svgName, response);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/* 108 */             GWT.log("RoomDeviceMonitorPresenter.queryDevicePositionConfig failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void refreshRoomDeviceStatus(RoomDeviceParamDTO dto) {
/* 114 */     AoEP.aoService.refreshRoomDeviceStatus(dto, new MethodCallback<List<RoomDeviceStatusDTO>>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, List<RoomDeviceStatusDTO> response)
/*     */           {
/* 119 */             GWT.log("refreshRoomDeviceStatus success!");
/* 120 */             RoomDeviceMonitorPresenter.this.viewer.refreshStatus(response);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/* 125 */             GWT.log("RoomDeviceMonitorPresenter.refreshRoomDeviceStatus failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void openDoor(RoomNCUStatusDTO dto) {
/* 131 */     AoEP.ncuService.openDoor(dto, new MethodCallback<Boolean>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, Boolean response)
/*     */           {
/* 136 */             RoomDeviceMonitorPresenter.this.viewer.infoDoor(response);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/* 141 */             GWT.log("RoomDeviceMonitorPresenter.openDoor failed.", exception);
/* 142 */             RoomDeviceMonitorPresenter.this.viewer.unmask();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void updateRoomDoOutput(RoomDoDTO dto, final String deviceName, final Boolean tf) {
/* 148 */     AoEP.aoService.updateRoomDoOutput(dto, new MethodCallback<Boolean>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, Boolean response)
/*     */           {
/* 153 */             if (deviceName.contains("-DO-4")) {
/* 154 */               RoomDeviceMonitorPresenter.this.viewer.safeHome(response);
/*     */             } else {
/* 156 */               RoomDeviceMonitorPresenter.this.viewer.openPower(response, tf);
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/* 162 */             GWT.log("RoomDeviceMonitorPresenter.updateRoomDoOutput failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void getAllNonRtnAlarms() {
/* 168 */     AoEP.aoAlarmService.getAllNonRtnAlarms(new MethodCallback<List<AlarmMessageDTO>>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, List<AlarmMessageDTO> response)
/*     */           {
/* 173 */             RoomDeviceMonitorPresenter.this.viewer.setAlarmMonitorGrid(response);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/* 178 */             GWT.log("RoomDeviceMonitorPresenter.getAllNonRtnAlarms failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void getRoomGroupDeviceStatus(String svgName, String groupName) {
/* 184 */     AoEP.aoService.getRoomGroupDeviceStatus(svgName, groupName, new MethodCallback<List<RoomGroupDeviceStatusDTO>>()
/*     */         {
/*     */ 
/*     */ 
/*     */           
/*     */           public void onSuccess(Method method, List<RoomGroupDeviceStatusDTO> result)
/*     */           {
/* 191 */             RoomDeviceMonitorPresenter.this.viewer.showGroupDialog(result);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/* 196 */             GWT.log("RoomDeviceMonitorPresenter.getRoomGroupDeviceStatus failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\RoomDeviceMonitorPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */