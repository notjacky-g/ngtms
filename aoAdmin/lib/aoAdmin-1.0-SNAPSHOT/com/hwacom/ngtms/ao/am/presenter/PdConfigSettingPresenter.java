/*     */ package com.hwacom.ngtms.ao.am.presenter;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.hwacom.ngtms.ao.am.view.PdConfigSettingViewer;
/*     */ import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.RoadSectionDTO;
/*     */ import com.hwacom.ngtms.pd.am.PdEP;
/*     */ import com.hwacom.ngtms.pd.shared.dto.LocationDTO;
/*     */ import com.hwacom.ngtms.pd.shared.dto.PdConfigDTO;
/*     */ import com.hwacom.ngtms.pd.shared.dto.PdParametersDTO;
/*     */ import java.util.List;
/*     */ import org.fusesource.restygwt.client.Method;
/*     */ import org.fusesource.restygwt.client.MethodCallback;
/*     */ 
/*     */ public class PdConfigSettingPresenter
/*     */ {
/*     */   private PdConfigSettingViewer viewer;
/*     */   
/*     */   public PdConfigSettingPresenter(PdConfigSettingViewer viewer) {
/*  20 */     this.viewer = viewer;
/*     */   }
/*     */   
/*     */   public void retrievePdConfigDTO() {
/*  24 */     PdEP.pdCommonService.retrievePdConfig(new MethodCallback<List<PdConfigDTO>>()
/*     */         {
/*     */           public void onSuccess(Method method, List<PdConfigDTO> result)
/*     */           {
/*  28 */             PdConfigSettingPresenter.this.viewer.addPdConfig(result);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/*  33 */             GWT.log("PdConfigSettingPresenter.retrievePdConfig failed.", caught);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void checkPdConfig(String deviceName, final String action, final PdConfigDTO dto) {
/*  39 */     PdParametersDTO params = new PdParametersDTO();
/*  40 */     params.setPdDeviceName(deviceName);
/*  41 */     PdEP.pdCommonService.checkPdConfig(params, new MethodCallback<Boolean>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, Boolean response)
/*     */           {
/*  46 */             if (!response.booleanValue()) {
/*  47 */               PdConfigSettingPresenter.this.savePdConfig(dto);
/*     */             } else {
/*  49 */               if (action.equals("save")) {
/*  50 */                 PdConfigSettingPresenter.this.viewer.showExistInfo();
/*     */                 return;
/*     */               } 
/*  53 */               PdConfigSettingPresenter.this.updatePdConfig(dto);
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/*  60 */             GWT.log("PdConfigSettingPresenter.checkPdConfig failed.", caught);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void checkRemovedPdConfig(final String deviceName) {
/*  66 */     PdParametersDTO dto = new PdParametersDTO();
/*  67 */     dto.setPdDeviceName(deviceName);
/*  68 */     PdEP.pdCommonService.checkRemovedPdConfig(dto, new MethodCallback<Boolean>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, Boolean response)
/*     */           {
/*  73 */             if (!response.booleanValue()) {
/*  74 */               PdConfigSettingPresenter.this.deletePdConfig(deviceName);
/*  75 */               PdConfigSettingPresenter.this.viewer.cleanField();
/*     */             } else {
/*  77 */               PdConfigSettingPresenter.this.viewer.confirmDialog(deviceName);
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/*  83 */             GWT.log("PdConfigSettingPresenter.checkRemovedPdConfig failed.", caught);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void savePdConfig(PdConfigDTO dto) {
/*  89 */     PdEP.pdCommonService.savePdConfig(dto, new MethodCallback<Void>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, Void response)
/*     */           {
/*  94 */             PdConfigSettingPresenter.this.viewer.refillGrid();
/*  95 */             PdConfigSettingPresenter.this.viewer.updatePdConfig();
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/* 100 */             GWT.log("PdConfigSettingPresenter.savePdConfig failed.", caught);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void updatePdConfig(PdConfigDTO dto) {
/* 106 */     PdEP.pdCommonService.updatePdConfig(dto, new MethodCallback<Void>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, Void response)
/*     */           {
/* 111 */             PdConfigSettingPresenter.this.viewer.refillGrid();
/* 112 */             PdConfigSettingPresenter.this.viewer.updatePdConfig();
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/* 117 */             GWT.log("PdConfigSettingPresenter.updatePdConfig failed.", caught);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void deletePdConfig(String deviceName) {
/* 123 */     PdParametersDTO dto = new PdParametersDTO();
/* 124 */     dto.setPdDeviceName(deviceName);
/* 125 */     PdEP.pdCommonService.deletePdConfig(dto, new MethodCallback<Void>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, Void response)
/*     */           {
/* 130 */             PdConfigSettingPresenter.this.viewer.refillGrid();
/* 131 */             PdConfigSettingPresenter.this.viewer.updatePdConfig();
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/* 136 */             GWT.log("PdConfigSettingPresenter.deletePdConfig failed.", caught);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void retrieveRoadLine() {
/* 142 */     PdEP.pdCommonService.retrieveRoadLine(new MethodCallback<List<RoadLineDTO>>()
/*     */         {
/*     */           public void onSuccess(Method method, List<RoadLineDTO> result)
/*     */           {
/* 146 */             PdConfigSettingPresenter.this.viewer.fillRoadLine(result);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/* 151 */             GWT.log("PdConfigSettingPresenter.retrieveRoadLine failed.", caught);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void retrieveLocation() {
/* 157 */     PdEP.pdCommonService.retrieveLocation(new MethodCallback<List<LocationDTO>>()
/*     */         {
/*     */           public void onSuccess(Method method, List<LocationDTO> result)
/*     */           {
/* 161 */             PdConfigSettingPresenter.this.viewer.fillLocation(result);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/* 166 */             GWT.log("PdConfigSettingPresenter.retrieveLocation failed.", caught);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void retrieveSection() {
/* 172 */     PdEP.pdCommonService.retrieveSection(new MethodCallback<List<RoadSectionDTO>>()
/*     */         {
/*     */           public void onSuccess(Method method, List<RoadSectionDTO> result)
/*     */           {
/* 176 */             PdConfigSettingPresenter.this.viewer.fillSection(result);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/* 181 */             GWT.log("PdConfigSettingPresenter.retrieveSection failed.", caught);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\PdConfigSettingPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */