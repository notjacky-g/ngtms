/*    */ package com.hwacom.ngtms.ao.am.presenter;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.hwacom.ngtms.ao.am.AoEP;
/*    */ import com.hwacom.ngtms.ao.am.view.RoomAnalogRecordViewer;
/*    */ import com.hwacom.ngtms.ao.shared.dto.RoomAnalogRecordDTO;
/*    */ import com.hwacom.ngtms.ao.shared.dto.RoomAnalogRecordQueryParamDTO;
/*    */ import com.hwacom.ngtms.ao.shared.dto.RoomAnalogTreeDTO;
/*    */ import com.sencha.gxt.widget.core.client.info.Info;
/*    */ import java.util.List;
/*    */ import org.fusesource.restygwt.client.Method;
/*    */ import org.fusesource.restygwt.client.MethodCallback;
/*    */ 
/*    */ public class RoomAnalogRecordPresenter
/*    */ {
/*    */   private RoomAnalogRecordViewer viewer;
/*    */   
/*    */   public RoomAnalogRecordPresenter(RoomAnalogRecordViewer viewer) {
/* 19 */     this.viewer = viewer;
/* 20 */     viewer.setPresenter(this);
/* 21 */     initTree();
/*    */   }
/*    */   
/*    */   public void initTree() {
/* 25 */     GWT.log("initTree");
/* 26 */     AoEP.aoService.getRoomAnalogTree(new MethodCallback<List<RoomAnalogTreeDTO>>()
/*    */         {
/*    */           
/*    */           public void onSuccess(Method method, List<RoomAnalogTreeDTO> result)
/*    */           {
/* 31 */             RoomAnalogRecordPresenter.this.viewer.initTree(result);
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable e) {
/* 36 */             GWT.log("RoomAnalogRecordPresenter.getRoomAnalogTreeNode failed.");
/* 37 */             Info.display("機房類比點位", "查詢失敗");
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public void getRoomAnalogRecord(RoomAnalogRecordQueryParamDTO dto) {
/* 43 */     AoEP.aoService.getRoomAnalogRecord(dto, new MethodCallback<List<RoomAnalogRecordDTO>>()
/*    */         {
/*    */ 
/*    */           
/*    */           public void onSuccess(Method method, List<RoomAnalogRecordDTO> result)
/*    */           {
/* 49 */             Info.display("查詢類比監控資料", "查詢成功");
/* 50 */             RoomAnalogRecordPresenter.this.viewer.fillChart(result);
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable e) {
/* 55 */             GWT.log("RoomAnalogRecordPresenter.getRoomAnalogRecord failed.", e);
/* 56 */             Info.display("查詢類比監控資料", "查詢失敗");
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\RoomAnalogRecordPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */