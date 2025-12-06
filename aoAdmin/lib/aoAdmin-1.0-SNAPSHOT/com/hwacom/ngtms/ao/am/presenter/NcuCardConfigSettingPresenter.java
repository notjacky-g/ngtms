/*     */ package com.hwacom.ngtms.ao.am.presenter;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.hwacom.ngtms.ao.am.AoEP;
/*     */ import com.hwacom.ngtms.ao.am.view.NcuCardConfigSettingViewer;
/*     */ import com.hwacom.ngtms.room.am.RoomEP;
/*     */ import com.hwacom.ngtms.room.shared.dto.RoomCardConfigDTO;
/*     */ import com.hwacom.ngtms.room.shared.dto.RoomCardGroupConfigDTO;
/*     */ import com.hwacom.ngtms.room.shared.dto.RoomCardIssueParam;
/*     */ import com.sencha.gxt.widget.core.client.info.Info;
/*     */ import java.util.List;
/*     */ import org.fusesource.restygwt.client.Method;
/*     */ import org.fusesource.restygwt.client.MethodCallback;
/*     */ 
/*     */ public class NcuCardConfigSettingPresenter
/*     */ {
/*     */   private NcuCardConfigSettingViewer viewer;
/*     */   
/*     */   public NcuCardConfigSettingPresenter(NcuCardConfigSettingViewer viewer) {
/*  20 */     this.viewer = viewer;
/*  21 */     viewer.setPresenter(this);
/*  22 */     getRoomCardConfig();
/*  23 */     getCardGroupConfig();
/*     */   }
/*     */   
/*     */   public void getRoomCardConfig() {
/*  27 */     RoomEP.commonService.getRoomCardConfig(new MethodCallback<List<RoomCardConfigDTO>>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, List<RoomCardConfigDTO> result)
/*     */           {
/*  32 */             NcuCardConfigSettingPresenter.this.viewer.fillRoomCardConfig(result);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable e) {
/*  37 */             GWT.log("NcuCardControlPresenter getRooCardConfig failed.", e);
/*  38 */             Info.display("查詢卡片資料", "查詢失敗");
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void getCardGroupConfig() {
/*  44 */     RoomEP.commonService.getRoomCardGroupConfig(new MethodCallback<List<RoomCardGroupConfigDTO>>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, List<RoomCardGroupConfigDTO> result)
/*     */           {
/*  49 */             NcuCardConfigSettingPresenter.this.viewer.fillRoomCardGroup(result);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable e) {
/*  54 */             GWT.log("NcuCardControlPresenter.getCardGroupConfig failed.", e);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void createRoomCardConfig(RoomCardConfigDTO dto) {
/*  60 */     AoEP.ncuService.createRoomCardConfig(dto, new MethodCallback<Boolean>()
/*     */         {
/*     */ 
/*     */           
/*     */           public void onSuccess(Method method, Boolean result)
/*     */           {
/*  66 */             if (result.booleanValue()) {
/*  67 */               Info.display("新增卡片", "新增成功");
/*  68 */               NcuCardConfigSettingPresenter.this.getRoomCardConfig();
/*  69 */               NcuCardConfigSettingPresenter.this.viewer.hideCreateDialog();
/*     */             } else {
/*  71 */               Info.display("新增卡片", "新增失敗");
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable e) {
/*  77 */             GWT.log("NcuCardControlPresenter.createRoomCardConfig failed.", e);
/*  78 */             Info.display("新增卡片", "新增異常");
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void saveCardConfig(RoomCardIssueParam param) {
/*  84 */     AoEP.ncuService.saveCardConfig(param, new MethodCallback<Boolean>()
/*     */         {
/*     */ 
/*     */           
/*     */           public void onSuccess(Method method, Boolean result)
/*     */           {
/*  90 */             if (result.booleanValue()) {
/*  91 */               Info.display("卡片資料", "儲存成功");
/*  92 */               NcuCardConfigSettingPresenter.this.viewer.refresh();
/*  93 */               NcuCardConfigSettingPresenter.this.getRoomCardConfig();
/*     */             } else {
/*  95 */               Info.display("卡片資料", "儲存失敗");
/*  96 */               NcuCardConfigSettingPresenter.this.viewer.refresh();
/*  97 */               NcuCardConfigSettingPresenter.this.getRoomCardConfig();
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable e) {
/* 103 */             GWT.log("NcuCardControlPresenter.saveCardConfigAndPermission failed.", e);
/* 104 */             Info.display("卡片資料", "儲存異常");
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\NcuCardConfigSettingPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */