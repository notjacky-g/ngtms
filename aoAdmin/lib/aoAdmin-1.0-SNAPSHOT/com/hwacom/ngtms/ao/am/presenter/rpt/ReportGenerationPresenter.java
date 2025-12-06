/*     */ package com.hwacom.ngtms.ao.am.presenter.rpt;
/*     */ 
/*     */ import com.google.gwt.core.shared.GWT;
/*     */ import com.google.gwt.user.client.rpc.AsyncCallback;
/*     */ import com.hwacom.ngtms.ao.am.RptEP;
/*     */ import com.hwacom.ngtms.ao.am.view.rpt.ReportGenerationViewer;
/*     */ import com.hwacom.ngtms.ao.shared.dto.ReportConfigBaseDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.ReportPreviewInfoDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.ReportQueryConditionDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RptParametersDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewDataDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewGridDataDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewHeaderDTO;
/*     */ import com.sencha.gxt.data.client.loader.RpcProxy;
/*     */ import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
/*     */ import com.sencha.gxt.data.shared.loader.PagingLoadResult;
/*     */ import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.fusesource.restygwt.client.Method;
/*     */ import org.fusesource.restygwt.client.MethodCallback;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReportGenerationPresenter
/*     */ {
/*     */   private static final String chartKey = "CHART_KEY";
/*     */   private static final String showDeviceNameKey = "showDeviceName";
/*     */   private static final String showHeaderKey = "showHeader";
/*     */   private static final String xlsFormat = "XLS";
/*     */   private final ReportGenerationViewer viewer;
/*     */   
/*     */   public ReportGenerationPresenter(ReportGenerationViewer viewer) {
/*  44 */     this.viewer = viewer;
/*  45 */     viewer.setPresenter(this);
/*  46 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/*  50 */     RptEP.aoRptService.retrievePrinterList(new MethodCallback<List<String>>()
/*     */         {
/*     */           public void onSuccess(Method method, List<String> printers)
/*     */           {
/*  54 */             ReportGenerationPresenter.this.viewer.fillPrinterList(printers);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/*  59 */             GWT.log("ReportGenerationPresenter.retrievePrinterList failed.", caught);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public RpcProxy<PagingLoadConfig, PagingLoadResult<String>> getPreviewRpcProxy(final String chartId) {
/*  66 */     return new RpcProxy<PagingLoadConfig, PagingLoadResult<String>>()
/*     */       {
/*     */ 
/*     */         
/*     */         public void load(final PagingLoadConfig loadConfig, final AsyncCallback<PagingLoadResult<String>> callback)
/*     */         {
/*  72 */           ReportQueryConditionDTO conditionDto = ReportGenerationPresenter.this.viewer.getQueryCondition();
/*  73 */           conditionDto.setOffset(loadConfig.getOffset());
/*  74 */           conditionDto.getInputParameter().put("CHART_KEY", chartId);
/*  75 */           conditionDto.getInputParameter().put("showDeviceName", "true");
/*  76 */           conditionDto.getInputParameter().put("showHeader", "false");
/*  77 */           RptParametersDTO params = new RptParametersDTO();
/*  78 */           params.setReportQueryConditionDTO(conditionDto);
/*  79 */           RptEP.aoRptService.fetchQueryData(params, new MethodCallback<ReportPreviewInfoDTO>()
/*     */               {
/*     */                 
/*     */                 public void onSuccess(Method method, ReportPreviewInfoDTO result)
/*     */                 {
/*  84 */                   PagingLoadResultBean<String> resultBean = new PagingLoadResultBean();
/*  85 */                   resultBean.setData(Arrays.asList(new String[] { result.getReportContent() }));
/*  86 */                   resultBean.setOffset(loadConfig.getOffset());
/*  87 */                   resultBean.setTotalLength(result.getTotalPage());
/*  88 */                   callback.onSuccess(resultBean);
/*     */                 }
/*     */ 
/*     */                 
/*     */                 public void onFailure(Method method, Throwable caught) {
/*  93 */                   GWT.log("RPT RPC fetchQueryData: Failure", caught);
/*     */                 }
/*     */               });
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public void fetchReportConfigData() {
/* 101 */     RptEP.aoRptService.fetchReportConfigData(new MethodCallback<List<ReportConfigBaseDTO>>()
/*     */         {
/*     */           public void onSuccess(Method method, List<ReportConfigBaseDTO> result)
/*     */           {
/* 105 */             ReportGenerationPresenter.this.viewer.bulidRepotConfigTree(result);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/* 110 */             GWT.log("ReportGenerationPresenter.fetchReportConfigData failed.", caught);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void downloadChart(String reportName, String chartId) {
/* 116 */     ReportQueryConditionDTO conditionDto = this.viewer.getQueryCondition();
/* 117 */     conditionDto.getInputParameter().put("CHART_KEY", chartId);
/* 118 */     conditionDto.getInputParameter().put("showDeviceName", "true");
/* 119 */     conditionDto.getInputParameter().put("showHeader", "false");
/* 120 */     RptParametersDTO params = new RptParametersDTO();
/* 121 */     params.setReportName(reportName);
/* 122 */     params.setReportQueryConditionDTO(conditionDto);
/* 123 */     RptEP.aoRptService.downloadChart(params, new MethodCallback<String>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, String reportId)
/*     */           {
/* 128 */             ReportGenerationPresenter.this.viewer.downloadReportFile(reportId, "XLS");
/* 129 */             ReportGenerationPresenter.this.viewer.unmask();
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/* 134 */             GWT.log("ReportGenerationPresenter.downloadChart failed.", caught);
/* 135 */             ReportGenerationPresenter.this.viewer.unmask();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void downloadReport(String reportName, final String reportFormat) {
/* 141 */     GWT.log("RPT Presenter downloadReport");
/* 142 */     ReportQueryConditionDTO condition = this.viewer.getQueryCondition();
/* 143 */     condition
/* 144 */       .getInputParameter()
/* 145 */       .put("showDeviceName", String.valueOf("XLS".equals(reportFormat)));
/* 146 */     condition
/* 147 */       .getInputParameter()
/* 148 */       .put("showHeader", String.valueOf(!"XLS".equals(reportFormat)));
/* 149 */     RptParametersDTO params = new RptParametersDTO();
/* 150 */     params.setReportName(reportName);
/* 151 */     params.setReportQueryConditionDTO(condition);
/* 152 */     params.setReportFormat(reportFormat);
/* 153 */     RptEP.aoRptService.downloadReport(params, new MethodCallback<String>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, String reportId)
/*     */           {
/* 158 */             ReportGenerationPresenter.this.viewer.downloadReportFile(reportId, reportFormat);
/* 159 */             ReportGenerationPresenter.this.viewer.unmask();
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/* 164 */             GWT.log("ReportGenerationPresenter.downloadReport failed.", caught);
/* 165 */             ReportGenerationPresenter.this.viewer.unmask();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void printReport(String reportName, String printer) {
/* 171 */     RptParametersDTO params = new RptParametersDTO();
/* 172 */     params.setReportName(reportName);
/* 173 */     params.setReportQueryConditionDTO(this.viewer.getQueryCondition());
/* 174 */     params.setPrinter(printer);
/* 175 */     RptEP.aoRptService.printReport(params, new MethodCallback<Void>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, Void result)
/*     */           {
/* 180 */             ReportGenerationPresenter.this.viewer.unmask();
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/* 185 */             GWT.log("ReportGenerationPresenter.printReport failed.", caught);
/* 186 */             ReportGenerationPresenter.this.viewer.unmask();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void retrievePreviewHeader(String reportId) {
/* 192 */     RptParametersDTO params = new RptParametersDTO();
/* 193 */     params.setReportId(reportId);
/* 194 */     RptEP.aoRptService.fetchPreviewHeader(params, new MethodCallback<Map<String, PreviewHeaderDTO>>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, Map<String, PreviewHeaderDTO> previewHeaderMap)
/*     */           {
/* 199 */             ReportGenerationPresenter.this.viewer.initPreviewHeader(previewHeaderMap);
/* 200 */             ReportGenerationPresenter.this.viewer.unmask();
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/* 205 */             GWT.log("ReportGenerationPresenter.fetchPreviewHeader failed.", caught);
/* 206 */             ReportGenerationPresenter.this.viewer.unmask();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void query(String reportId, ReportQueryConditionDTO condition) {
/* 212 */     condition.getInputParameter().put("showDeviceName", "true");
/* 213 */     RptParametersDTO params = new RptParametersDTO();
/* 214 */     params.setReportId(reportId);
/* 215 */     params.setReportQueryConditionDTO(condition);
/* 216 */     RptEP.aoRptService.query(params, new MethodCallback<List<PreviewDataDTO>>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, List<PreviewDataDTO> previewData)
/*     */           {
/* 221 */             ReportGenerationPresenter.this.viewer.fillPreviewData(previewData);
/* 222 */             ReportGenerationPresenter.this.viewer.unmask();
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/* 227 */             GWT.log("ReportGenerationPresenter.query failed.", caught);
/* 228 */             ReportGenerationPresenter.this.viewer.unmask();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void queryWithHeader(String reportId, ReportQueryConditionDTO condition) {
/* 234 */     condition.getInputParameter().put("showDeviceName", "true");
/* 235 */     RptParametersDTO params = new RptParametersDTO();
/* 236 */     params.setReportId(reportId);
/* 237 */     params.setReportQueryConditionDTO(condition);
/* 238 */     RptEP.aoRptService.queryWithHeader(params, new MethodCallback<PreviewGridDataDTO>()
/*     */         {
/*     */           
/*     */           public void onSuccess(Method method, PreviewGridDataDTO previewGridData)
/*     */           {
/* 243 */             ReportGenerationPresenter.this.viewer.fillPreviewGridData(previewGridData);
/* 244 */             ReportGenerationPresenter.this.viewer.unmask();
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/* 249 */             GWT.log("ReportGenerationPresenter.queryWithHeader failed.", caught);
/* 250 */             ReportGenerationPresenter.this.viewer.unmask();
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\presenter\rpt\ReportGenerationPresenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */