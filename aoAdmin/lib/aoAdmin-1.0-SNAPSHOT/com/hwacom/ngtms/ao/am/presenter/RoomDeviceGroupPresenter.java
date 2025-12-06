/*    */ package com.hwacom.ngtms.ao.am.presenter;
/*    */ 
/*    */ import com.google.gwt.core.shared.GWT;
/*    */ import com.hwacom.ngtms.ao.am.AoEP;
/*    */ import com.hwacom.ngtms.ao.am.view.RoomGroupViewer;
/*    */ import com.hwacom.ngtms.ao.shared.dto.RoomSetAndUnSetConfigDTO;
/*    */ import com.hwacom.ngtms.ao.shared.dto.RoomSvgGroupConifgDTO;
/*    */ import com.sencha.gxt.widget.core.client.info.Info;
/*    */ import java.util.List;
/*    */ import org.fusesource.restygwt.client.Method;
/*    */ import org.fusesource.restygwt.client.MethodCallback;
/*    */ 
/*    */ public class RoomDeviceGroupPresenter
/*    */ {
/*    */   private RoomGroupViewer viewer;
/*    */   
/*    */   public RoomDeviceGroupPresenter(RoomGroupViewer viewer) {
/* 18 */     this.viewer = viewer;
/* 19 */     getRoomGroupDeviceData();
/*    */   }
/*    */ 
/*    */   
/*    */   public void getRoomGroupDeviceData() {
/* 24 */     AoEP.aoService.getRoomGroupDeviceData(new MethodCallback<RoomSetAndUnSetConfigDTO>()
/*    */         {
/*    */           public void onSuccess(Method method, RoomSetAndUnSetConfigDTO response)
/*    */           {
/* 28 */             if (response != null) {
/* 29 */               RoomDeviceGroupPresenter.this.viewer.fillRoomGroupDeviceData(response);
/*    */             }
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable exception) {
/* 35 */             GWT.log("RoomDeviceGroupPresenter.getRoomUnsetGroupDeviceData failed.", exception);
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public void saveRoomGroupDeviceData(List<RoomSvgGroupConifgDTO> dtos) {
/* 42 */     AoEP.aoService.saveRoomGroupDeviceData(dtos, new MethodCallback<Boolean>()
/*    */         {
/*    */ 
/*    */           
/*    */           public void onSuccess(Method method, Boolean response)
/*    */           {
/* 48 */             if (response.booleanValue()) {
/* 49 */               Info.display("信息提示", "儲存成功");
/* 50 */               RoomDeviceGroupPresenter.this.viewer.refresh();
/*    */             } else {
/* 52 */               Info.display("信息提示", "儲存失敗");
/*    */             } 
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable exception) {
/* 58 */             GWT.log("RoomDeviceGroupPresenter.saveGroupDeviceData failed.", exception);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\RoomDeviceGroupPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */