/*     */ package com.hwacom.ngtms.ao.am.presenter;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.user.client.rpc.AsyncCallback;
/*     */ import com.hwacom.ngtms.ao.am.AoEP;
/*     */ import com.hwacom.ngtms.ao.am.view.AlarmRecordViewer;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmLogPageLoadDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmMessageDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmRecordQueryParamDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmTypeDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomNcuDeviceNameDTO;
/*     */ import com.sencha.gxt.data.client.loader.RpcProxy;
/*     */ import com.sencha.gxt.data.shared.loader.DataProxy;
/*     */ import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
/*     */ import com.sencha.gxt.data.shared.loader.PagingLoadResult;
/*     */ import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.fusesource.restygwt.client.Method;
/*     */ import org.fusesource.restygwt.client.MethodCallback;
/*     */ 
/*     */ public class AlarmRecordPresenter
/*     */ {
/*     */   private AlarmRecordViewer viewer;
/*     */   
/*     */   public AlarmRecordPresenter(AlarmRecordViewer viewer) {
/*  27 */     this.viewer = viewer;
/*  28 */     getNcu();
/*  29 */     getAllAlarmType();
/*     */   }
/*     */   
/*     */   public void getNcu() {
/*  33 */     AoEP.ncuService.getNcu(new MethodCallback<List<RoomNcuDeviceNameDTO>>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, List<RoomNcuDeviceNameDTO> result)
/*     */           {
/*  38 */             AlarmRecordPresenter.this.viewer.fillNcuStore(result);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable e) {
/*  43 */             GWT.log("AlarmRecordPresenter.getNcu failed.", e);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void getAllAlarmType() {
/*  49 */     AoEP.aoAlarmService.getAllAlarmType(new MethodCallback<List<AlarmTypeDTO>>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, List<AlarmTypeDTO> result)
/*     */           {
/*  54 */             AlarmRecordPresenter.this.viewer.fillAllAlarmType(result);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable e) {
/*  59 */             GWT.log("AlarmRecordPresenter.getAllAlarmType failed.", e);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public DataProxy<PagingLoadConfig, PagingLoadResult<AlarmMessageDTO>> getPreviewRpcProxy(final AlarmRecordQueryParamDTO dto) {
/*  66 */     return (DataProxy<PagingLoadConfig, PagingLoadResult<AlarmMessageDTO>>)new RpcProxy<PagingLoadConfig, PagingLoadResult<AlarmMessageDTO>>()
/*     */       {
/*     */ 
/*     */         
/*     */         public void load(final PagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<AlarmMessageDTO>> callback)
/*     */         {
/*  72 */           dto.setOffset(loadConfig.getOffset());
/*  73 */           AlarmRecordPresenter.this.viewer.getPageToolBar().bind(AlarmRecordPresenter.this.viewer.getPagingLoader());
/*     */           
/*  75 */           AoEP.aoAlarmService.getAlarmLogWithParamPageLoad(dto, new MethodCallback<AlarmLogPageLoadDTO>()
/*     */               {
/*     */ 
/*     */                 
/*     */                 public void onSuccess(Method method, AlarmLogPageLoadDTO result)
/*     */                 {
/*  81 */                   PagingLoadResultBean<List<AlarmMessageDTO>> resultBean = new PagingLoadResultBean();
/*     */                   
/*  83 */                   List<List<AlarmMessageDTO>> data = new ArrayList<>();
/*  84 */                   data.add(result.getData());
/*  85 */                   resultBean.setData(data);
/*  86 */                   resultBean.setOffset(loadConfig.getOffset());
/*  87 */                   resultBean.setTotalLength(result.getTotalPage());
/*     */                 }
/*     */ 
/*     */ 
/*     */                 
/*     */                 public void onFailure(Method method, Throwable exception) {
/*  93 */                   GWT.log("AlarmRecordPresenter.getAlarmLogWithParamPageLoad failed.", exception);
/*     */                 }
/*     */               });
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public void getAlarmLogWithParam(AlarmRecordQueryParamDTO dto) {
/* 101 */     AoEP.aoAlarmService.getAlarmLogWithParam(dto, new MethodCallback<List<AlarmMessageDTO>>()
/*     */         {
/*     */ 
/*     */           
/*     */           public void onSuccess(Method method, List<AlarmMessageDTO> response)
/*     */           {
/* 107 */             AlarmRecordPresenter.this.viewer.fillAlarmResult(response);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable exception) {
/* 112 */             GWT.log("AlarmRecordPresenter.getAlarmLogWithParam failed.", exception);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\AlarmRecordPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */