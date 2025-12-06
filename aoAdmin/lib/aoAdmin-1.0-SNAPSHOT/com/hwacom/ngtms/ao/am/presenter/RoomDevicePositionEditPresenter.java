/*    */ package com.hwacom.ngtms.ao.am.presenter;
/*    */ 
/*    */ import com.google.gwt.core.shared.GWT;
/*    */ import com.hwacom.ngtms.ao.am.AoEP;
/*    */ import com.hwacom.ngtms.ao.am.view.RoomDevicePositionEditViewer;
/*    */ import com.hwacom.ngtms.c.shared.dto.DeviceSvgPositionConfigDTO;
/*    */ import com.hwacom.ngtms.room.am.RoomEP;
/*    */ import com.hwacom.ngtms.room.shared.dto.RoomDeviceSubLocationConfigDTO;
/*    */ import com.sencha.gxt.widget.core.client.info.Info;
/*    */ import java.util.List;
/*    */ import org.fusesource.restygwt.client.Method;
/*    */ import org.fusesource.restygwt.client.MethodCallback;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RoomDevicePositionEditPresenter
/*    */ {
/*    */   private RoomDevicePositionEditViewer viewer;
/*    */   
/*    */   public RoomDevicePositionEditPresenter(RoomDevicePositionEditViewer viewer) {
/* 25 */     this.viewer = viewer;
/* 26 */     getRoomDeviceSubLocationConfigData();
/*    */   }
/*    */   
/*    */   public void getRoomDeviceSubLocationConfigData() {
/* 30 */     RoomEP.commonService.getRoomDeviceSubLocationConfigData(new MethodCallback<List<RoomDeviceSubLocationConfigDTO>>()
/*    */         {
/*    */           public void onSuccess(Method method, List<RoomDeviceSubLocationConfigDTO> response)
/*    */           {
/* 34 */             if (response.size() > 0) {
/* 35 */               RoomDevicePositionEditPresenter.this.viewer.fillGridData(response);
/*    */             }
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable exception) {
/* 41 */             GWT.log("RoomDeviceMonitorPresenter.getRoomDeviceSubLocationConfigData failed.", exception);
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public void queryDevicePositionConfig(String imageName) {
/* 48 */     GWT.log("RoomDevicePositionEditPresenter.queryDevicePositionConfig");
/* 49 */     AoEP.aoService.queryDevicePositionConfig(imageName, new MethodCallback<List<DeviceSvgPositionConfigDTO>>()
/*    */         {
/*    */           
/*    */           public void onSuccess(Method method, List<DeviceSvgPositionConfigDTO> response)
/*    */           {
/* 54 */             RoomDevicePositionEditPresenter.this.viewer.fillDeviceSvg(response);
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable exception) {
/* 59 */             GWT.log("RoomDevicePositionEditPresenter.queryDevicePositionConfig failed.", exception);
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public void saveRoomDevicePositionConfigs(List<DeviceSvgPositionConfigDTO> position) {
/* 65 */     GWT.log("RoomDevicePositionEditPresenter.saveRoomDevicePositionConfigs!!");
/* 66 */     AoEP.aoService.saveRoomDevicePositionConfigs(position, new MethodCallback<Boolean>()
/*    */         {
/*    */           
/*    */           public void onSuccess(Method method, Boolean result)
/*    */           {
/* 71 */             if (result.booleanValue()) {
/* 72 */               Info.display(RoomEP.messages.info_save(), RoomEP.messages.info_successFully());
/*    */             } else {
/* 74 */               Info.display(RoomEP.messages.info_save(), RoomEP.messages.info_fail());
/*    */             } 
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable exception) {
/* 80 */             GWT.log("RoomDevicePositionEditPresenter.saveDdsDevicePositionConfigs failed.", exception);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\RoomDevicePositionEditPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */