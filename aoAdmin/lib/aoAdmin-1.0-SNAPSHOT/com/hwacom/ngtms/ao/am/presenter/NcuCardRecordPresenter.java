/*    */ package com.hwacom.ngtms.ao.am.presenter;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.hwacom.ngtms.ao.am.AoEP;
/*    */ import com.hwacom.ngtms.ao.am.view.NcuCardRecordViewer;
/*    */ import com.hwacom.ngtms.ao.shared.dto.RoomCardReaderLogQueryParamDTO;
/*    */ import com.hwacom.ngtms.ao.shared.dto.RoomNCUCardLogDTO;
/*    */ import com.hwacom.ngtms.ao.shared.dto.RoomNcuDeviceNameDTO;
/*    */ import com.sencha.gxt.widget.core.client.info.Info;
/*    */ import java.util.List;
/*    */ import org.fusesource.restygwt.client.Method;
/*    */ import org.fusesource.restygwt.client.MethodCallback;
/*    */ 
/*    */ public class NcuCardRecordPresenter
/*    */ {
/*    */   private NcuCardRecordViewer viewer;
/*    */   
/*    */   public NcuCardRecordPresenter(NcuCardRecordViewer viewer) {
/* 19 */     this.viewer = viewer;
/*    */   }
/*    */   
/*    */   public void getNcu() {
/* 23 */     AoEP.ncuService.getNcu(new MethodCallback<List<RoomNcuDeviceNameDTO>>()
/*    */         {
/*    */           
/*    */           public void onSuccess(Method method, List<RoomNcuDeviceNameDTO> result)
/*    */           {
/* 28 */             NcuCardRecordPresenter.this.viewer.fillNcuStore(result);
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable e) {
/* 33 */             GWT.log("NcuCardRecordPresenter.getNcu failed.", e);
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public void getlocationByNcu(String deviceName) {
/* 39 */     AoEP.ncuService.getlocationByNcu(deviceName, new MethodCallback<List<RoomNcuDeviceNameDTO>>()
/*    */         {
/*    */ 
/*    */           
/*    */           public void onSuccess(Method method, List<RoomNcuDeviceNameDTO> result)
/*    */           {
/* 45 */             NcuCardRecordPresenter.this.viewer.fillLocationComboBox(result);
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable e) {
/* 50 */             GWT.log("NcuCardRecordPresenter.getlocationByNcu failed.", e);
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public void getNcuCardReaderLog(RoomCardReaderLogQueryParamDTO dto) {
/* 56 */     AoEP.ncuService.getNcuCardReaderLog(dto, new MethodCallback<List<RoomNCUCardLogDTO>>()
/*    */         {
/*    */ 
/*    */           
/*    */           public void onSuccess(Method method, List<RoomNCUCardLogDTO> result)
/*    */           {
/* 62 */             NcuCardRecordPresenter.this.viewer.fillRecordStore(result);
/* 63 */             Info.display("門禁卡機紀錄查詢", "查詢成功");
/*    */           }
/*    */ 
/*    */           
/*    */           public void onFailure(Method method, Throwable e) {
/* 68 */             GWT.log("NcuCardRecordPresenter.getRoomCardReaderLogData failed.", e);
/* 69 */             Info.display("門禁卡機紀錄查詢", "查詢失敗");
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\NcuCardRecordPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */