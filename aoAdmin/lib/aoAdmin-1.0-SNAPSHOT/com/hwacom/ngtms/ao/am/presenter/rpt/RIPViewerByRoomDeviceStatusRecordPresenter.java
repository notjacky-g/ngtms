/*    */ package com.hwacom.ngtms.ao.am.presenter.rpt;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.hwacom.ngtms.ao.am.RptEP;
/*    */ import com.hwacom.ngtms.ao.am.view.rpt.RIPViewerByRoomDeviceStatusRecord;
/*    */ import com.hwacom.ngtms.ao.shared.dto.PdLocationDTO;
/*    */ import com.hwacom.ngtms.c.shared.dto.DeviceTypeDTO;
/*    */ import java.util.List;
/*    */ import org.fusesource.restygwt.client.Method;
/*    */ import org.fusesource.restygwt.client.MethodCallback;
/*    */ 
/*    */ public class RIPViewerByRoomDeviceStatusRecordPresenter
/*    */ {
/*    */   private RIPViewerByRoomDeviceStatusRecord viewer;
/*    */   
/*    */   public RIPViewerByRoomDeviceStatusRecordPresenter(RIPViewerByRoomDeviceStatusRecord viewer) {
/* 17 */     this.viewer = viewer;
/* 18 */     viewer.setPresenter(this);
/*    */   }
/*    */   
/*    */   public void retrieveLocation() {
/* 22 */     RptEP.aoRptService.retrieveLocation(new MethodCallback<List<PdLocationDTO>>()
/*    */         {
/*    */           public void onSuccess(Method method, List<PdLocationDTO> result)
/*    */           {
/* 26 */             RIPViewerByRoomDeviceStatusRecordPresenter.this.viewer.fillLocation(result);
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable caught) {
/* 31 */             GWT.log("RIPViewerByPdStatusPresenter.retrieveLocation failed.", caught);
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public void getDeviceTypeByCategory(String category) {
/* 37 */     RptEP.aoRptService.fetchDeviceTypeByCategory(category, new MethodCallback<List<DeviceTypeDTO>>()
/*    */         {
/*    */           
/*    */           public void onSuccess(Method method, List<DeviceTypeDTO> response)
/*    */           {
/* 42 */             RIPViewerByRoomDeviceStatusRecordPresenter.this.viewer.setDeviceTypeCB(response);
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable caught) {
/* 47 */             GWT.log("RIPViewerByPdStatusPresenter.getDeviceTypeByCategory failed.", caught);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\rpt\RIPViewerByRoomDeviceStatusRecordPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */