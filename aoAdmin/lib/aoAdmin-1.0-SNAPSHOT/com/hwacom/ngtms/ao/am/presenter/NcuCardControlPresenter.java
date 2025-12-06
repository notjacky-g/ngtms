/*     */ package com.hwacom.ngtms.ao.am.presenter;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.hwacom.ngtms.ao.am.AoEP;
/*     */ import com.hwacom.ngtms.ao.am.view.NcuCardControlViewer;
/*     */ import com.hwacom.ngtms.ao.shared.dto.NcuConfigDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomNCUCardDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomNCUMessageDTO;
/*     */ import com.hwacom.ngtms.room.am.RoomEP;
/*     */ import com.hwacom.ngtms.room.shared.dto.RoomCardConfigDTO;
/*     */ import com.hwacom.ngtms.room.shared.dto.RoomCardGroupConfigDTO;
/*     */ import com.hwacom.ngtms.room.shared.dto.RoomCardReaderMappingConfigDTO;
/*     */ import com.sencha.gxt.widget.core.client.info.Info;
/*     */ import java.util.List;
/*     */ import org.fusesource.restygwt.client.Method;
/*     */ import org.fusesource.restygwt.client.MethodCallback;
/*     */ 
/*     */ public class NcuCardControlPresenter
/*     */ {
/*     */   private NcuCardControlViewer viewer;
/*     */   
/*     */   public NcuCardControlPresenter(NcuCardControlViewer viewer) {
/*  23 */     this.viewer = viewer;
/*  24 */     viewer.setPresenter(this);
/*  25 */     getRoomCardConfig();
/*     */   }
/*     */   
/*     */   public void getCardGroupConfig() {
/*  29 */     RoomEP.commonService.getRoomCardGroupConfig(new MethodCallback<List<RoomCardGroupConfigDTO>>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, List<RoomCardGroupConfigDTO> result)
/*     */           {
/*  34 */             NcuCardControlPresenter.this.viewer.fillRoomCardGroup(result);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable e) {
/*  39 */             GWT.log("NcuCardControlPresenter.getCardGroupConfig failed.", e);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void getRoomCardConfig() {
/*  45 */     RoomEP.commonService.getRoomCardConfig(new MethodCallback<List<RoomCardConfigDTO>>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, List<RoomCardConfigDTO> result)
/*     */           {
/*  50 */             NcuCardControlPresenter.this.viewer.fillRoomCardConfig(result);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable e) {
/*  55 */             GWT.log("NcuCardControlPresenter getRooCardConfig failed.", e);
/*  56 */             Info.display("查詢卡片資料", "查詢失敗");
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void getRoomLineData() {
/*  62 */     AoEP.aoService.getRoomLineData(new MethodCallback<List<String>>()
/*     */         {
/*     */           public void onSuccess(Method method, List<String> response)
/*     */           {
/*  66 */             NcuCardControlPresenter.this.viewer.initLineData(response);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/*  71 */             GWT.log("NcuCardControlPresenter.getRoomLineData failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void getNcuTreeData() {
/*  77 */     AoEP.ncuService.getNcuTreeData(new MethodCallback<List<NcuConfigDTO>>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, List<NcuConfigDTO> response)
/*     */           {
/*  82 */             if (response.size() > 0) {
/*  83 */               NcuCardControlPresenter.this.viewer.fillTreeData(response);
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable e) {
/*  89 */             GWT.log("NcuCardControlPresenter.getNcuTreeData failed.", e);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void getRoomCardReaderMappingConfig(String aba) {
/*  95 */     RoomEP.commonService.getRoomCardReaderMappingConfig(aba, new MethodCallback<List<RoomCardReaderMappingConfigDTO>>()
/*     */         {
/*     */ 
/*     */           
/*     */           public void onSuccess(Method method, List<RoomCardReaderMappingConfigDTO> result)
/*     */           {
/* 101 */             NcuCardControlPresenter.this.viewer.fillTreeCheckValue(result);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable e) {
/* 106 */             GWT.log("NcuCardControlPresenter.getRoomCardReaderMappingConfig failed.", e);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void addCardPermission(RoomNCUCardDTO dto) {
/* 112 */     AoEP.ncuService.addCardPermission(dto, new MethodCallback<RoomNCUMessageDTO>()
/*     */         {
/*     */ 
/*     */           
/*     */           public void onSuccess(Method method, RoomNCUMessageDTO result)
/*     */           {
/* 118 */             if (result != null) {
/* 119 */               NcuCardControlPresenter.this.viewer.showAddCardFailDialog(result.getNcuMessage());
/* 120 */               NcuCardControlPresenter.this.viewer.refresh();
/* 121 */               NcuCardControlPresenter.this.getRoomCardConfig();
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable e) {
/* 127 */             GWT.log("NcuCardControlPresenter.saveCardConfigAndPermission failed.", e);
/* 128 */             Info.display("權限設定", "執行失敗");
/* 129 */             NcuCardControlPresenter.this.getRoomCardConfig();
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\NcuCardControlPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */