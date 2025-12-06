/*    */ package com.hwacom.ngtms.ao.am.presenter;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.hwacom.ngtms.ao.am.view.PdStatusViewer;
/*    */ import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
/*    */ import com.hwacom.ngtms.pd.am.PdEP;
/*    */ import com.hwacom.ngtms.pd.shared.dto.LoopDeviceConfigDTO;
/*    */ import com.hwacom.ngtms.pd.shared.dto.PdStatusDTO;
/*    */ import java.util.List;
/*    */ import org.fusesource.restygwt.client.Method;
/*    */ import org.fusesource.restygwt.client.MethodCallback;
/*    */ 
/*    */ public class PdStatusPresenter
/*    */ {
/*    */   private PdStatusViewer viewer;
/*    */   
/*    */   public PdStatusPresenter(PdStatusViewer viewer) {
/* 18 */     this.viewer = viewer;
/*    */   }
/*    */   
/*    */   public void retrieveRoadLine() {
/* 22 */     PdEP.pdCommonService.retrieveRoadLine(new MethodCallback<List<RoadLineDTO>>()
/*    */         {
/*    */           public void onSuccess(Method method, List<RoadLineDTO> result)
/*    */           {
/* 26 */             PdStatusPresenter.this.viewer.fillRoadLine(result);
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable caught) {
/* 31 */             GWT.log("PdStatusPresenter.retrieveRoadLine failed.", caught);
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public void retrievePdStatus() {
/* 37 */     PdEP.pdCommonService.retrievePdStatus(new MethodCallback<List<PdStatusDTO>>()
/*    */         {
/*    */           public void onSuccess(Method method, List<PdStatusDTO> result)
/*    */           {
/* 41 */             PdStatusPresenter.this.viewer.fillPdStatus(result);
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable caught) {
/* 46 */             GWT.log("PdStatusPresenter.retrievePdStatus failed.", caught);
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public void retrieveLoopDeviceConfig(List<String> deviceNames, final String displayName) {
/* 52 */     PdEP.pdCommonService.retrieveLoopDeviceConfig(deviceNames, new MethodCallback<List<LoopDeviceConfigDTO>>()
/*    */         {
/*    */           
/*    */           public void onSuccess(Method method, List<LoopDeviceConfigDTO> result)
/*    */           {
/* 57 */             PdStatusPresenter.this.viewer.fillLoopDevice(result, displayName);
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable caught) {
/* 62 */             GWT.log("PdStatusPresenter.retrieveLoopDeviceConfig failed.", caught);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\PdStatusPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */