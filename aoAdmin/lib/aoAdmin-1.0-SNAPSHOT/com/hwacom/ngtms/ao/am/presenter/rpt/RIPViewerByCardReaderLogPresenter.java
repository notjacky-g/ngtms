/*    */ package com.hwacom.ngtms.ao.am.presenter.rpt;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.hwacom.ngtms.ao.am.RptEP;
/*    */ import com.hwacom.ngtms.ao.am.view.rpt.RIPViewerByCardReaderLog;
/*    */ import com.hwacom.ngtms.ao.shared.dto.RoomNcuDeviceNameDTO;
/*    */ import java.util.List;
/*    */ import org.fusesource.restygwt.client.Method;
/*    */ import org.fusesource.restygwt.client.MethodCallback;
/*    */ 
/*    */ public class RIPViewerByCardReaderLogPresenter
/*    */ {
/*    */   private RIPViewerByCardReaderLog viewer;
/*    */   
/*    */   public RIPViewerByCardReaderLogPresenter(RIPViewerByCardReaderLog viewer) {
/* 16 */     this.viewer = viewer;
/* 17 */     viewer.setPresenter(this);
/*    */   }
/*    */   
/*    */   public void retrieveLocation() {
/* 21 */     RptEP.ncuService.getNcu(new MethodCallback<List<RoomNcuDeviceNameDTO>>()
/*    */         {
/*    */           
/*    */           public void onSuccess(Method method, List<RoomNcuDeviceNameDTO> result)
/*    */           {
/* 26 */             RIPViewerByCardReaderLogPresenter.this.viewer.fillLocation(result);
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable e) {
/* 31 */             GWT.log("NcuCardRecordPresenter.getNcu failed.", e);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\rpt\RIPViewerByCardReaderLogPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */