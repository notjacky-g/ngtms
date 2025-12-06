/*    */ package com.hwacom.ngtms.ao.am.presenter.rpt;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.hwacom.ngtms.ao.am.RptEP;
/*    */ import com.hwacom.ngtms.ao.am.view.rpt.RIPViewerByPdLoopDeviceAlarm;
/*    */ import com.hwacom.ngtms.ao.shared.dto.PdLocationDTO;
/*    */ import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
/*    */ import java.util.List;
/*    */ import org.fusesource.restygwt.client.Method;
/*    */ import org.fusesource.restygwt.client.MethodCallback;
/*    */ 
/*    */ public class RIPViewerByPdLoopDeviceAlarmPresenter
/*    */ {
/*    */   private RIPViewerByPdLoopDeviceAlarm viewer;
/*    */   
/*    */   public RIPViewerByPdLoopDeviceAlarmPresenter(RIPViewerByPdLoopDeviceAlarm viewer) {
/* 17 */     this.viewer = viewer;
/* 18 */     viewer.setPresenter(this);
/*    */   }
/*    */   
/*    */   public void initCombobox() {
/* 22 */     RptEP.aoRptService.findPaths(new MethodCallback<List<RoadLineDTO>>()
/*    */         {
/*    */           public void onSuccess(Method method, List<RoadLineDTO> result)
/*    */           {
/* 26 */             RIPViewerByPdLoopDeviceAlarmPresenter.this.viewer.initPath(result);
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable caught) {
/* 31 */             GWT.log("RIPViewerByPdStatusPresenter.findPaths failed.", caught);
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public void retrieveLocation() {
/* 37 */     RptEP.aoRptService.retrieveLocation(new MethodCallback<List<PdLocationDTO>>()
/*    */         {
/*    */           public void onSuccess(Method method, List<PdLocationDTO> result)
/*    */           {
/* 41 */             RIPViewerByPdLoopDeviceAlarmPresenter.this.viewer.fillLocation(result);
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable caught) {
/* 46 */             GWT.log("RIPViewerByPdStatusPresenter.retrieveLocation failed.", caught);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\rpt\RIPViewerByPdLoopDeviceAlarmPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */