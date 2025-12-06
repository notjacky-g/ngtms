/*     */ package com.hwacom.ngtms.ao.am.presenter;
/*     */ 
/*     */ import com.google.gwt.core.shared.GWT;
/*     */ import com.hwacom.ngtms.ao.am.view.RoomDeviceConfigSettingViewer;
/*     */ import com.hwacom.ngtms.c.shared.dto.DeviceTypeDTO;
/*     */ import com.hwacom.ngtms.room.am.RoomEP;
/*     */ import com.hwacom.ngtms.room.shared.dto.RoomDeviceConfigDTO;
/*     */ import com.hwacom.ngtms.room.shared.dto.RoomDeviceSubLocationConfigDTO;
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
/*     */ public class RoomDeviceConfigSettingPresenter
/*     */ {
/*     */   private RoomDeviceConfigSettingViewer viewer;
/*     */   
/*     */   public RoomDeviceConfigSettingPresenter(RoomDeviceConfigSettingViewer viewer) {
/*  24 */     this.viewer = viewer;
/*     */   }
/*     */   
/*     */   public void initGridData() {
/*  28 */     RoomEP.commonService.getRoomDeviceSubLocationConfigData(new MethodCallback<List<RoomDeviceSubLocationConfigDTO>>()
/*     */         {
/*     */           public void onSuccess(Method method, List<RoomDeviceSubLocationConfigDTO> response)
/*     */           {
/*  32 */             if (response.size() > 0) {
/*  33 */               RoomDeviceConfigSettingPresenter.this.viewer.fillGridData(response);
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/*  39 */             GWT.log("RoomDeviceConfigSettingPresenter.getRoomDeviceSubLocationConfigData failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fiilDeviceGridData(String locName, String subLocation) {
/*  47 */     RoomEP.commonService.getRoomDeviceConfig(locName, subLocation, new MethodCallback<List<RoomDeviceConfigDTO>>()
/*     */         {
/*     */ 
/*     */           
/*     */           public void onSuccess(Method method, List<RoomDeviceConfigDTO> response)
/*     */           {
/*  53 */             RoomDeviceConfigSettingPresenter.this.viewer.fillDeviceGrid(response);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/*  58 */             GWT.log("RoomDeviceConfigSettingPresenter.fiilDeviceGridData failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void fiilTypeCB() {
/*  64 */     RoomEP.commonService.getRoomDeviceType(new MethodCallback<List<DeviceTypeDTO>>()
/*     */         {
/*     */           public void onSuccess(Method method, List<DeviceTypeDTO> response)
/*     */           {
/*  68 */             if (response.size() > 0) {
/*  69 */               RoomDeviceConfigSettingPresenter.this.viewer.fillTypeCB(response);
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/*  75 */             GWT.log("RoomDeviceConfigSettingPresenter.fiilTypeCB failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void updateRoomDeviceConfig(RoomDeviceConfigDTO dto) {
/*  81 */     RoomEP.commonService.updateRoomDeviceConfig(dto, new MethodCallback<Boolean>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, Boolean response)
/*     */           {
/*  86 */             RoomDeviceConfigSettingPresenter.this.viewer.updateConfig(response);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/*  91 */             GWT.log("RoomDeviceConfigSettingPresenter.updateRoomDeviceConfig failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void findSettedDevcieLocation(String deviceName) {
/*  97 */     RoomEP.commonService.querySelectedRoomSubLocation(deviceName, new MethodCallback<List<String>>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, List<String> response)
/*     */           {
/* 102 */             RoomDeviceConfigSettingPresenter.this.viewer.setSettedDeviceLocation(response.get(0));
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/* 107 */             GWT.log("RoomDeviceConfigSettingPresenter.findSettedDevcieLocation failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\RoomDeviceConfigSettingPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */