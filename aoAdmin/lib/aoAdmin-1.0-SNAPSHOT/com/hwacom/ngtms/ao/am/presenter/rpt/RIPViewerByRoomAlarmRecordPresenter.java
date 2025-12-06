/*    */ package com.hwacom.ngtms.ao.am.presenter.rpt;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.hwacom.ngtms.ao.am.RptEP;
/*    */ import com.hwacom.ngtms.ao.am.view.rpt.RIPViewerByRoomAlarmRecord;
/*    */ import com.hwacom.ngtms.ao.shared.dto.AlarmTypeDTO;
/*    */ import com.hwacom.ngtms.ao.shared.dto.PdLocationDTO;
/*    */ import java.util.List;
/*    */ import org.fusesource.restygwt.client.Method;
/*    */ import org.fusesource.restygwt.client.MethodCallback;
/*    */ 
/*    */ public class RIPViewerByRoomAlarmRecordPresenter
/*    */ {
/*    */   private RIPViewerByRoomAlarmRecord viewer;
/*    */   
/*    */   public RIPViewerByRoomAlarmRecordPresenter(RIPViewerByRoomAlarmRecord viewer) {
/* 17 */     this.viewer = viewer;
/* 18 */     viewer.setPresenter(this);
/*    */   }
/*    */   
/*    */   public void retrieveLocation() {
/* 22 */     RptEP.aoRptService.retrieveLocation(new MethodCallback<List<PdLocationDTO>>()
/*    */         {
/*    */           public void onSuccess(Method method, List<PdLocationDTO> result)
/*    */           {
/* 26 */             RIPViewerByRoomAlarmRecordPresenter.this.viewer.fillLocation(result);
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable caught) {
/* 31 */             GWT.log("RIPViewerByPdStatusPresenter.retrieveLocation failed.", caught);
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public void getAllAlarmType() {
/* 37 */     RptEP.aoRptService.getAllAlarmType(new MethodCallback<List<AlarmTypeDTO>>()
/*    */         {
/*    */           public void onSuccess(Method method, List<AlarmTypeDTO> result)
/*    */           {
/* 41 */             RIPViewerByRoomAlarmRecordPresenter.this.viewer.fillAlarmType(result);
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable caught) {
/* 46 */             GWT.log("RIPViewerByPdStatusPresenter.getAllAlarmType failed.", caught);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\rpt\RIPViewerByRoomAlarmRecordPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */