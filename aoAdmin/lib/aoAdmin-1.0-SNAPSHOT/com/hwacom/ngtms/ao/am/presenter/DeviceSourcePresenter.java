/*    */ package com.hwacom.ngtms.ao.am.presenter;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.hwacom.ngtms.ao.am.AoEP;
/*    */ import com.hwacom.ngtms.ao.am.view.DeviceSourceViewer;
/*    */ import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
/*    */ import java.util.List;
/*    */ import org.fusesource.restygwt.client.Method;
/*    */ import org.fusesource.restygwt.client.MethodCallback;
/*    */ 
/*    */ public class DeviceSourcePresenter
/*    */ {
/*    */   private final DeviceSourceViewer viewer;
/*    */   
/*    */   public DeviceSourcePresenter(DeviceSourceViewer viewer) {
/* 16 */     this.viewer = viewer;
/*    */   }
/*    */   
/*    */   public void getRoomDevices(List<String> types) {
/* 20 */     AoEP.camService.getDeviceConfig(types, new MethodCallback<List<DeviceConfigDTO>>()
/*    */         {
/*    */           
/*    */           public void onSuccess(Method method, List<DeviceConfigDTO> response)
/*    */           {
/* 25 */             DeviceSourcePresenter.this.viewer.fillDevices(response);
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable exception) {
/* 30 */             GWT.log("DeviceSourcePresenter.getRoomDevices failed.", exception);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\DeviceSourcePresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */