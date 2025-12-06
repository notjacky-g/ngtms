/*     */ package com.hwacom.ngtms.ao.am.presenter;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.hwacom.ngtms.ao.am.AoEP;
/*     */ import com.hwacom.ngtms.ao.am.view.LiveFaceRecordViewer;
/*     */ import com.hwacom.ngtms.ao.shared.dto.LifeFaceLockCardDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomCardReaderLogQueryParamDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomNcuDeviceNameDTO;
/*     */ import com.sencha.gxt.widget.core.client.info.Info;
/*     */ import java.util.List;
/*     */ import org.fusesource.restygwt.client.Method;
/*     */ import org.fusesource.restygwt.client.MethodCallback;
/*     */ 
/*     */ public class LiveFaceRecordPresenter
/*     */ {
/*     */   private LiveFaceRecordViewer viewer;
/*     */   
/*     */   public LiveFaceRecordPresenter(LiveFaceRecordViewer viewer) {
/*  19 */     this.viewer = viewer;
/*     */   }
/*     */   
/*     */   public void getNcu() {
/*  23 */     AoEP.ncuService.getNcu(new MethodCallback<List<RoomNcuDeviceNameDTO>>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, List<RoomNcuDeviceNameDTO> result)
/*     */           {
/*  28 */             LiveFaceRecordPresenter.this.viewer.fillNcuStore(result);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable e) {
/*  33 */             GWT.log("LiveFaceRecordPresenter.getNcu failed.", e);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void getAllLockCards() {
/*  39 */     AoEP.ncuService.getAllLockCards(new MethodCallback<List<LifeFaceLockCardDTO>>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, List<LifeFaceLockCardDTO> result)
/*     */           {
/*  44 */             LiveFaceRecordPresenter.this.viewer.fillRecordStore(result);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable e) {
/*  49 */             GWT.log("LiveFaceRecordPresenter.getAllLockCards failed.", e);
/*  50 */             Info.display("人臉辨識", "查詢失敗");
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void setLockCard(LifeFaceLockCardDTO dto) {
/*  56 */     AoEP.ncuService.setLockCard(dto, new MethodCallback<Boolean>()
/*     */         {
/*     */ 
/*     */           
/*     */           public void onSuccess(Method method, Boolean result)
/*     */           {
/*  62 */             if (result.booleanValue()) {
/*  63 */               Info.display("訊息顯示", "鎖卡成功");
/*     */             } else {
/*  65 */               Info.display("訊息顯示", "鎖卡失敗");
/*     */             } 
/*  67 */             LiveFaceRecordPresenter.this.getAllLockCards();
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable e) {
/*  72 */             GWT.log("LiveFaceRecordPresenter.setLockCard failed.", e);
/*  73 */             Info.display("訊息顯示", "鎖卡失敗");
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void setUnLockCard(LifeFaceLockCardDTO dto) {
/*  79 */     AoEP.ncuService.setUnLockCard(dto, new MethodCallback<Boolean>()
/*     */         {
/*     */ 
/*     */           
/*     */           public void onSuccess(Method method, Boolean result)
/*     */           {
/*  85 */             if (result.booleanValue()) {
/*  86 */               Info.display("訊息顯示", "解卡成功");
/*     */             } else {
/*  88 */               Info.display("訊息顯示", "解卡失敗");
/*     */             } 
/*  90 */             LiveFaceRecordPresenter.this.getAllLockCards();
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable e) {
/*  95 */             GWT.log("LiveFaceRecordPresenter.setUnLockCard failed.", e);
/*  96 */             Info.display("人臉辨識", "解卡失敗");
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void getLockCards(RoomCardReaderLogQueryParamDTO dto) {
/* 102 */     AoEP.ncuService.getLockCards(dto, new MethodCallback<List<LifeFaceLockCardDTO>>()
/*     */         {
/*     */ 
/*     */           
/*     */           public void onSuccess(Method method, List<LifeFaceLockCardDTO> result)
/*     */           {
/* 108 */             LiveFaceRecordPresenter.this.viewer.fillRecordStore(result);
/* 109 */             Info.display("人臉辨識", "查詢成功");
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable e) {
/* 114 */             GWT.log("LiveFaceRecordPresenter.getLockCards failed.", e);
/* 115 */             Info.display("人臉辨識", "查詢失敗");
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\LiveFaceRecordPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */