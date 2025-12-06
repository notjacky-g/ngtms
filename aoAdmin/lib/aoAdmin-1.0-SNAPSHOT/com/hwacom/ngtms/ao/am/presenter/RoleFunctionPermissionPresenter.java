/*     */ package com.hwacom.ngtms.ao.am.presenter;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.event.shared.GwtEvent;
/*     */ import com.hwacom.ngtms.ao.am.view.RoleFunctionPermissionViewer;
/*     */ import com.hwacom.ngtms.common.am.AccountEP;
/*     */ import com.hwacom.ngtms.common.am.event.AccountDataEvent;
/*     */ import com.hwacom.ngtms.common.shared.dto.AccountParametersDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.FunctionPermissionDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.RoleDTO;
/*     */ import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
/*     */ import com.sencha.gxt.widget.core.client.info.Info;
/*     */ import java.util.List;
/*     */ import org.fusesource.restygwt.client.Method;
/*     */ import org.fusesource.restygwt.client.MethodCallback;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RoleFunctionPermissionPresenter
/*     */ {
/*  24 */   private final ClientFactory clientFactory = (ClientFactory)GWT.create(ClientFactory.class);
/*     */   
/*     */   private RoleFunctionPermissionViewer viewer;
/*     */   
/*     */   public RoleFunctionPermissionPresenter(RoleFunctionPermissionViewer viewer) {
/*  29 */     this.viewer = viewer;
/*  30 */     initRoles();
/*  31 */     init();
/*     */   }
/*     */   
/*     */   private void initRoles() {
/*  35 */     AccountEP.accountService.getRoles(new MethodCallback<List<RoleDTO>>()
/*     */         {
/*     */           public void onSuccess(Method method, List<RoleDTO> result)
/*     */           {
/*  39 */             if (result != null) {
/*  40 */               RoleFunctionPermissionPresenter.this.viewer.initRoles(result);
/*  41 */               RoleFunctionPermissionPresenter.this.clientFactory
/*  42 */                 .getEventBus()
/*  43 */                 .fireEventFromSource((GwtEvent)new AccountDataEvent(AccountDataEvent.Action.ROLE_INIT_DATA), result);
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/*  50 */             GWT.log("RoleFunctionPermissionPresenter.init failed.", caught);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void init() {
/*  56 */     AccountEP.accountService.getFunctionPermissions(new MethodCallback<List<FunctionPermissionDTO>>()
/*     */         {
/*     */           public void onSuccess(Method method, List<FunctionPermissionDTO> result)
/*     */           {
/*  60 */             if (result != null) {
/*  61 */               RoleFunctionPermissionPresenter.this.viewer.initFunctionPermission(result);
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/*  67 */             GWT.log("RoleFunctionPermissionPresenter.getFunctionPermissions failed.", caught);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void addItem(RoleDTO dto) {
/*  73 */     AccountParametersDTO params = new AccountParametersDTO();
/*  74 */     params.setRoleDTO(dto);
/*  75 */     AccountEP.accountService.addRole(params, new MethodCallback<RoleDTO>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, RoleDTO result)
/*     */           {
/*  80 */             if (result != null) {
/*  81 */               RoleFunctionPermissionPresenter.this.viewer.addStore(result);
/*  82 */               RoleFunctionPermissionPresenter.this.initRoles();
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/*  88 */             GWT.log("RoleFunctionPermissionPresenter.addRole failed.", caught);
/*  89 */             Info.display(AccountEP.messages.message(), AccountEP.messages.message_addFail());
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void removeItem(final RoleDTO dto) {
/*  95 */     AccountParametersDTO params = new AccountParametersDTO();
/*  96 */     params.setRoleDTO(dto);
/*  97 */     AccountEP.accountService.deleteRole(params, new MethodCallback<Boolean>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, Boolean result)
/*     */           {
/* 102 */             if (result.booleanValue()) {
/* 103 */               RoleFunctionPermissionPresenter.this.viewer.removeStore(dto);
/* 104 */               RoleFunctionPermissionPresenter.this.initRoles();
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/* 110 */             GWT.log("RoleFunctionPermissionPresenter.deleteRole failed.", caught);
/* 111 */             Info.display(AccountEP.messages.message(), AccountEP.messages.message_deleteFail());
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void saveItem(RoleDTO dto) {
/* 117 */     AccountParametersDTO params = new AccountParametersDTO();
/* 118 */     params.setRoleDTO(dto);
/* 119 */     AccountEP.accountService.saveRole(params, new MethodCallback<RoleDTO>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, RoleDTO result)
/*     */           {
/* 124 */             if (result != null) {
/* 125 */               RoleFunctionPermissionPresenter.this.viewer.updateSotre(result);
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/* 131 */             GWT.log("RoleFunctionPermissionPresenter.saveRole failed.", caught);
/* 132 */             Info.display(AccountEP.messages.message(), AccountEP.messages.message_saveFail());
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\RoleFunctionPermissionPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */