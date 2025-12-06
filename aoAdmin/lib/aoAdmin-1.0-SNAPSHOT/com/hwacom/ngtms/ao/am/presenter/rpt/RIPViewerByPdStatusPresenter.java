/*    */ package com.hwacom.ngtms.ao.am.presenter.rpt;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.hwacom.ngtms.ao.am.RptEP;
/*    */ import com.hwacom.ngtms.ao.am.view.rpt.RIPViewerByPdStatus;
/*    */ import com.hwacom.ngtms.ao.shared.dto.PdLocationDTO;
/*    */ import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.fusesource.restygwt.client.Method;
/*    */ import org.fusesource.restygwt.client.MethodCallback;
/*    */ 
/*    */ public class RIPViewerByPdStatusPresenter
/*    */ {
/*    */   private RIPViewerByPdStatus viewer;
/*    */   
/*    */   public RIPViewerByPdStatusPresenter(RIPViewerByPdStatus viewer) {
/* 18 */     this.viewer = viewer;
/* 19 */     viewer.setPresenter(this);
/*    */   }
/*    */   
/*    */   public void initCombobox() {
/* 23 */     RptEP.aoRptService.findPaths(new MethodCallback<List<RoadLineDTO>>()
/*    */         {
/*    */           public void onSuccess(Method method, List<RoadLineDTO> result)
/*    */           {
/* 27 */             RIPViewerByPdStatusPresenter.this.viewer.initPath(result);
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable caught) {
/* 32 */             GWT.log("RIPViewerByPdStatusPresenter.findPaths failed.", caught);
/*    */           }
/*    */         });
/* 35 */     this.viewer.initPath(new ArrayList());
/*    */   }
/*    */   
/*    */   public void retrieveLocation() {
/* 39 */     RptEP.aoRptService.retrieveLocation(new MethodCallback<List<PdLocationDTO>>()
/*    */         {
/*    */           public void onSuccess(Method method, List<PdLocationDTO> result)
/*    */           {
/* 43 */             RIPViewerByPdStatusPresenter.this.viewer.fillLocation(result);
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable caught) {
/* 48 */             GWT.log("RIPViewerByPdStatusPresenter.retrieveLocation failed.", caught);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\rpt\RIPViewerByPdStatusPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */