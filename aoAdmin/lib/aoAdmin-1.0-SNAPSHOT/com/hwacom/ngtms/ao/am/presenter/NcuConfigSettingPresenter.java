/*     */ package com.hwacom.ngtms.ao.am.presenter;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.hwacom.ngtms.ao.am.AoEP;
/*     */ import com.hwacom.ngtms.ao.am.view.NcuConfigSettingViewer;
/*     */ import com.hwacom.ngtms.ao.shared.dto.NcuConfigDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomPermissionDTO;
/*     */ import com.sencha.gxt.widget.core.client.info.Info;
/*     */ import java.util.List;
/*     */ import org.fusesource.restygwt.client.Method;
/*     */ import org.fusesource.restygwt.client.MethodCallback;
/*     */ 
/*     */ public class NcuConfigSettingPresenter
/*     */ {
/*     */   private NcuConfigSettingViewer viewer;
/*     */   
/*     */   public NcuConfigSettingPresenter(NcuConfigSettingViewer viewer) {
/*  18 */     this.viewer = viewer;
/*     */   }
/*     */   
/*     */   public void getNcuConfig() {
/*  22 */     AoEP.ncuService.getNcuConfig(new MethodCallback<List<NcuConfigDTO>>()
/*     */         {
/*     */           public void onSuccess(Method method, List<NcuConfigDTO> response)
/*     */           {
/*  26 */             NcuConfigSettingPresenter.this.viewer.fillDevices(response);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/*  31 */             GWT.log("NcuConfigSettingPresenter.getNcuConfig failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void addNcuConfig(NcuConfigDTO param) {
/*  37 */     AoEP.ncuService.addNcuConfig(param, new MethodCallback<Boolean>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, Boolean response)
/*     */           {
/*  42 */             NcuConfigSettingPresenter.this.viewer.showInfo("add");
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/*  47 */             GWT.log("NcuConfigSettingPresenter.addNcuConfig failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void updateNcuConfig(NcuConfigDTO param) {
/*  53 */     AoEP.ncuService.updateNcuConfig(param, new MethodCallback<Boolean>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, Boolean response)
/*     */           {
/*  58 */             NcuConfigSettingPresenter.this.viewer.showInfo("update");
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/*  63 */             GWT.log("NcuConfigSettingPresenter.updateNcuConfig failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void deleteNcuConfig(String deviceName) {
/*  69 */     AoEP.ncuService.deleteNcuConfig(deviceName, new MethodCallback<Boolean>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, Boolean response)
/*     */           {
/*  74 */             NcuConfigSettingPresenter.this.viewer.showInfo("delete");
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/*  79 */             GWT.log("NcuConfigSettingPresenter.deleteNcuConfig failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void getOneRoomPermissin(String deviceName) {
/*  85 */     AoEP.ncuService.getOneRoomPermissin(deviceName, new MethodCallback<List<RoomPermissionDTO>>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, List<RoomPermissionDTO> response)
/*     */           {
/*  90 */             NcuConfigSettingPresenter.this.viewer.exportCSV(response);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/*  95 */             GWT.log("NcuConfigSettingPresenter.getOneRoomPermissin failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void getAllRoomPermissin() {
/* 101 */     AoEP.ncuService.getAllRoomPermissin(new MethodCallback<List<RoomPermissionDTO>>()
/*     */         {
/*     */           public void onSuccess(Method method, List<RoomPermissionDTO> response)
/*     */           {
/* 105 */             NcuConfigSettingPresenter.this.viewer.exportCSV(response);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/* 110 */             GWT.log("NcuConfigSettingPresenter.getAllRoomPermissin failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void getNcuHostDataTimeNow(String devieName) {
/* 116 */     AoEP.ncuService.getNcuHostDataTimeNow(devieName, new MethodCallback<NcuConfigDTO>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, NcuConfigDTO response)
/*     */           {
/* 121 */             NcuConfigSettingPresenter.this.viewer.fillNcuDataTime(response);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/* 126 */             GWT.log("NcuConfigSettingPresenter.getNcuHostDataTimeNow failed.", exception);
/* 127 */             NcuConfigSettingPresenter.this.viewer.unmask();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void synchronizeNcuHostTime(final String devieName) {
/* 133 */     AoEP.ncuService.synchronizeNcuHostTime(devieName, new MethodCallback<Boolean>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, Boolean response)
/*     */           {
/* 138 */             if (response.booleanValue()) {
/* 139 */               NcuConfigSettingPresenter.this.viewer.unmask();
/* 140 */               Info.display("同步時間:" + devieName, "執行完成");
/*     */             } else {
/* 142 */               NcuConfigSettingPresenter.this.viewer.unmask();
/* 143 */               Info.display("同步時間:" + devieName, "執行失敗");
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/* 149 */             GWT.log("NcuConfigSettingPresenter.synchronizeNcuHostTime failed.", exception);
/* 150 */             NcuConfigSettingPresenter.this.viewer.unmask();
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\NcuConfigSettingPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */