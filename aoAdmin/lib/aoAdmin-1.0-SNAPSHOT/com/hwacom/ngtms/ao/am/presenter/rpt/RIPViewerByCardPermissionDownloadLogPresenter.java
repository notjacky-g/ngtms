/*    */ package com.hwacom.ngtms.ao.am.presenter.rpt;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.hwacom.ngtms.ao.am.RptEP;
/*    */ import com.hwacom.ngtms.ao.am.view.rpt.RIPViewerByCardPermissionDownloadLog;
/*    */ import com.hwacom.ngtms.ao.shared.dto.RoomNcuDeviceNameDTO;
/*    */ import java.util.List;
/*    */ import org.fusesource.restygwt.client.Method;
/*    */ import org.fusesource.restygwt.client.MethodCallback;
/*    */ 
/*    */ 
/*    */ public class RIPViewerByCardPermissionDownloadLogPresenter
/*    */ {
/*    */   private RIPViewerByCardPermissionDownloadLog viewer;
/*    */   
/*    */   public RIPViewerByCardPermissionDownloadLogPresenter(RIPViewerByCardPermissionDownloadLog viewer) {
/* 17 */     this.viewer = viewer;
/* 18 */     viewer.setPresenter(this);
/*    */   }
/*    */   
/*    */   public void retrieveLocation() {
/* 22 */     RptEP.ncuService.getNcu(new MethodCallback<List<RoomNcuDeviceNameDTO>>()
/*    */         {
/*    */           
/*    */           public void onSuccess(Method method, List<RoomNcuDeviceNameDTO> result)
/*    */           {
/* 27 */             RIPViewerByCardPermissionDownloadLogPresenter.this.viewer.fillLocation(result);
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable e) {
/* 32 */             GWT.log("NcuCardRecordPresenter.getNcu failed.", e);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\rpt\RIPViewerByCardPermissionDownloadLogPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */