/*     */ package com.hwacom.ngtms.ao.am.view.rpt;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.event.shared.EventHandler;
/*     */ import com.google.gwt.event.shared.GwtEvent;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.uibinder.client.UiHandler;
/*     */ import com.google.gwt.user.client.Window;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.event.ReportGenerationEvent;
/*     */ import com.hwacom.ngtms.ao.am.event.ReportGenerationEventHandler;
/*     */ import com.hwacom.ngtms.ao.am.presenter.rpt.OperatorPresenter;
/*     */ import com.hwacom.ngtms.ao.am.presenter.rpt.ReportGenerationPresenter;
/*     */ import com.hwacom.ngtms.ao.am.view.Messages;
/*     */ import com.hwacom.ngtms.ao.shared.dto.ChartDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.ReportConfigBaseDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.ReportConfigDetailDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.ReportQueryConditionDTO;
/*     */ import com.hwacom.ngtms.cam.images.AmImages;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewDataDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewGridDataDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewHeaderDTO;
/*     */ import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
/*     */ import com.sencha.gxt.core.client.Style;
/*     */ import com.sencha.gxt.core.client.util.Margins;
/*     */ import com.sencha.gxt.widget.core.client.Composite;
/*     */ import com.sencha.gxt.widget.core.client.ContentPanel;
/*     */ import com.sencha.gxt.widget.core.client.Dialog;
/*     */ import com.sencha.gxt.widget.core.client.button.ButtonBar;
/*     */ import com.sencha.gxt.widget.core.client.button.TextButton;
/*     */ import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
/*     */ import com.sencha.gxt.widget.core.client.container.MarginData;
/*     */ import com.sencha.gxt.widget.core.client.event.SelectEvent;
/*     */ import com.sencha.gxt.widget.core.client.info.Info;
/*     */ import com.sencha.gxt.widget.core.client.tree.Tree;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReportGenerationViewer
/*     */   extends Composite
/*     */ {
/*  51 */   private static ReportGenerationViewerUiBinder uiBinder = (ReportGenerationViewerUiBinder)GWT.create(ReportGenerationViewerUiBinder.class);
/*     */ 
/*     */ 
/*     */   
/*  55 */   private final ClientFactory clientFactory = (ClientFactory)GWT.create(ClientFactory.class);
/*     */   
/*  57 */   private Tree.TreeAppearance treeAppearance = (Tree.TreeAppearance)GWT.create(Tree.TreeAppearance.class);
/*     */   
/*     */   private static final String pdfFormat = "PDF";
/*     */   
/*     */   private static final String xlsFormat = "XLS";
/*     */   
/*     */   private static final String xlsPoiFormat = "XLS_POI";
/*     */   
/*  65 */   private static final List<String> previewHtmlChartReportIds = Arrays.asList(new String[] { "54", "55" });
/*     */   
/*     */   private ReportGenerationPresenter presenter;
/*     */   
/*  69 */   private PrinterList printerList = (PrinterList)GWT.create(PrinterList.class);
/*     */   
/*  71 */   private ExportList exportList = (ExportList)GWT.create(ExportList.class);
/*     */   
/*     */   private Dialog printerDialog;
/*     */   
/*     */   private Dialog exportDialog;
/*     */   
/*     */   private Dialog htmlPreviewDialog;
/*     */   
/*     */   private HtmlPreviewer htmlPreview;
/*     */   @UiField
/*     */   Messages messages;
/*     */   @UiField
/*     */   BorderLayoutContainer borderContainer;
/*     */   @UiField
/*     */   ContentPanel ripContentPanel;
/*     */   @UiField
/*     */   TextButton btnExportToFile;
/*     */   @UiField
/*     */   TextButton btnPrint;
/*     */   @UiField
/*     */   TextButton btnCollapse;
/*     */   @UiField
/*     */   ButtonBar btnBarDynamic;
/*     */   @UiField
/*     */   ReportNavigator reportNavigator;
/*     */   @UiField
/*     */   ContentPanel previewContainer;
/*     */   
/*     */   public ReportGenerationViewer() {
/* 100 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/* 101 */     initExportList();
/*     */     
/* 103 */     this.clientFactory
/* 104 */       .getEventBus()
/* 105 */       .addHandler(ReportGenerationEvent.TYPE, (EventHandler)new ReportGenerationEventHandlerImpl());
/*     */     
/* 107 */     this.presenter = new ReportGenerationPresenter(this);
/*     */ 
/*     */     
/* 110 */     this.clientFactory.getEventBus().fireEvent((GwtEvent)new ReportGenerationEvent(ReportGenerationEvent.Action.GET_REPORT_CONFIG));
/*     */ 
/*     */     
/* 113 */     this.btnCollapse.setIcon(AmImages.INSTANCE.add_small());
/*     */   }
/*     */   
/*     */   private void initPrinterDialog() {
/* 117 */     if (this.printerDialog != null) {
/* 118 */       if (this.printerList.comboBox.getPageSize() > 0) {
/* 119 */         this.printerList.comboBox.select(0);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 124 */     this.printerDialog = new Dialog();
/* 125 */     this.printerDialog.setWidth(450);
/* 126 */     this.printerDialog.setHeight(80);
/* 127 */     this.printerDialog.setModal(true);
/* 128 */     this.printerDialog.setClosable(false);
/* 129 */     this.printerDialog.setResizable(false);
/*     */     
/* 131 */     TextButton cancelButton = new TextButton();
/* 132 */     cancelButton.setText(this.messages.button_text_cancel());
/* 133 */     cancelButton.setToolTip(this.messages.button_text_cancel_tooltip());
/* 134 */     cancelButton.addSelectHandler(new SelectEvent.SelectHandler()
/*     */         {
/*     */           public void onSelect(SelectEvent event)
/*     */           {
/* 138 */             ReportGenerationViewer.this.printerDialog.hide();
/*     */           }
/*     */         });
/*     */     
/* 142 */     TextButton saveButton = new TextButton();
/* 143 */     saveButton.setText(this.messages.button_text_save());
/* 144 */     saveButton.setToolTip(this.messages.button_text_save_tooltip());
/* 145 */     saveButton.addSelectHandler(new SelectEvent.SelectHandler()
/*     */         {
/*     */           public void onSelect(SelectEvent event)
/*     */           {
/* 149 */             ReportGenerationViewer.this.clientFactory.getEventBus().fireEvent((GwtEvent)new ReportGenerationEvent(ReportGenerationEvent.Action.PRINT_REPORT));
/* 150 */             ReportGenerationViewer.this.printerDialog.hide();
/*     */           }
/*     */         });
/*     */     
/* 154 */     this.printerDialog.getButtonBar().clear();
/* 155 */     this.printerDialog.getButtonBar().add((Widget)saveButton);
/* 156 */     this.printerDialog.getButtonBar().add((Widget)cancelButton);
/* 157 */     this.printerDialog.add((Widget)this.printerList, new MarginData(4, 4, 4, 4));
/*     */   }
/*     */   
/*     */   private void initExportDialog() {
/* 161 */     if (this.exportDialog != null) {
/* 162 */       if (this.exportList.comboBox.getPageSize() > 0) {
/* 163 */         this.exportList.comboBox.select(0);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 168 */     this.exportDialog = new Dialog();
/* 169 */     this.exportDialog.setWidth(240);
/* 170 */     this.exportDialog.setHeight(80);
/* 171 */     this.exportDialog.setModal(true);
/* 172 */     this.exportDialog.setClosable(false);
/* 173 */     this.exportDialog.setResizable(false);
/*     */     
/* 175 */     TextButton cancelButton = new TextButton();
/* 176 */     cancelButton.setText(this.messages.button_text_cancel());
/* 177 */     cancelButton.setToolTip(this.messages.button_text_cancel_tooltip());
/* 178 */     cancelButton.addSelectHandler(new SelectEvent.SelectHandler()
/*     */         {
/*     */           public void onSelect(SelectEvent event)
/*     */           {
/* 182 */             ReportGenerationViewer.this.exportDialog.hide();
/*     */           }
/*     */         });
/*     */     
/* 186 */     TextButton saveButton = new TextButton();
/* 187 */     saveButton.setText(this.messages.button_text_save());
/* 188 */     saveButton.setToolTip(this.messages.button_text_save_tooltip());
/* 189 */     saveButton.addSelectHandler(new SelectEvent.SelectHandler()
/*     */         {
/*     */           public void onSelect(SelectEvent event)
/*     */           {
/* 193 */             ReportGenerationViewer.this.clientFactory.getEventBus().fireEvent((GwtEvent)new ReportGenerationEvent(ReportGenerationEvent.Action.EXPORT_TO_FILE));
/* 194 */             ReportGenerationViewer.this.exportDialog.hide();
/*     */           }
/*     */         });
/*     */     
/* 198 */     this.exportDialog.getButtonBar().clear();
/* 199 */     this.exportDialog.getButtonBar().add((Widget)saveButton);
/* 200 */     this.exportDialog.getButtonBar().add((Widget)cancelButton);
/* 201 */     this.exportDialog.add((Widget)this.exportList, new MarginData(4, 4, 4, 4));
/*     */   }
/*     */   
/*     */   private void initExportList() {
/* 205 */     this.exportList.comboBox.add(this.messages.combobox_staticText_option_pdf());
/* 206 */     this.exportList.comboBox.add(this.messages.combobox_staticText_option_excel());
/* 207 */     this.exportList.comboBox.add(this.messages.combobox_staticText_option_excelPoi());
/* 208 */     this.exportList.comboBox.setValue(this.messages.combobox_staticText_option_excel());
/*     */   }
/*     */   
/*     */   private String getReportFormat() {
/* 212 */     String comboBoxVaue = (String)this.exportList.comboBox.getValue();
/* 213 */     if (this.messages.combobox_staticText_option_pdf().equals(comboBoxVaue))
/* 214 */       return "PDF"; 
/* 215 */     if (this.messages.combobox_staticText_option_excel().equals(comboBoxVaue)) {
/* 216 */       return "XLS";
/*     */     }
/* 218 */     return "XLS_POI";
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPresenter(ReportGenerationPresenter presenter) {
/* 223 */     this.presenter = presenter;
/*     */   }
/*     */   
/*     */   public void bulidRepotConfigTree(List<ReportConfigBaseDTO> data) {
/* 227 */     GWT.log("RPT: bulidRepotConfigTree");
/* 228 */     this.reportNavigator.setAndBuildTree(data);
/*     */   }
/*     */   
/*     */   public void updateRIPViewer(final ReportConfigDetailDTO dto) {
/* 232 */     this.ripContentPanel.setWidget((Widget)new RIPViewerContainer(dto
/* 233 */           .getReportQueryRequestDTO().getQueryWidgetFullName()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 239 */     RIPViewer ripViewer = getRipViewer();
/* 240 */     if (ripViewer instanceof DeviceConfigExplorer) {
/* 241 */       DeviceConfigExplorer viewer = (DeviceConfigExplorer)ripViewer;
/* 242 */       String deviceType = dto.getReportQueryRequestDTO().getDeviceType();
/* 243 */       setDeviceTypeToDeviceConfigExplorer(viewer, deviceType);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     if (ripViewer instanceof OperatorExplorer) {
/* 250 */       OperatorExplorer viewer = (OperatorExplorer)ripViewer;
/* 251 */       viewer.setPresenter(new OperatorPresenter(viewer));
/*     */     } 
/*     */     
/* 254 */     this.ripContentPanel.forceLayout();
/* 255 */     this.ripContentPanel.setHeading(dto.getReportName());
/* 256 */     this.btnBarDynamic.clear();
/* 257 */     List<ChartDTO> chartDTOs = dto.getReportQueryRequestDTO().getCharts();
/* 258 */     if (chartDTOs != null) {
/* 259 */       for (ChartDTO chart : chartDTOs) {
/* 260 */         final ChartDTO finalDTO = chart;
/* 261 */         TextButton button = new TextButton(chart.getChartName());
/* 262 */         button.getElement().setMargins(new Margins(2, 2, 2, 2));
/* 263 */         button.addSelectHandler(new SelectEvent.SelectHandler()
/*     */             {
/*     */               
/*     */               public void onSelect(SelectEvent event)
/*     */               {
/* 268 */                 GWT.log("RPT View btnPrintClicked");
/* 269 */                 if (ReportGenerationViewer.previewHtmlChartReportIds.contains(dto.getId())) {
/* 270 */                   ReportGenerationViewer.this.clientFactory
/* 271 */                     .getEventBus()
/* 272 */                     .fireEventFromSource((GwtEvent)new ReportGenerationEvent(ReportGenerationEvent.Action.PREVIEW_HTML_CHART), finalDTO);
/*     */                   
/*     */                   return;
/*     */                 } 
/* 276 */                 ReportGenerationViewer.this.clientFactory
/* 277 */                   .getEventBus()
/* 278 */                   .fireEventFromSource((GwtEvent)new ReportGenerationEvent(ReportGenerationEvent.Action.EXPORT_TO_CHART), finalDTO);
/*     */               }
/*     */             });
/*     */         
/* 282 */         this.btnBarDynamic.add((Widget)button);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void setDeviceTypeToDeviceConfigExplorer(DeviceConfigExplorer explorer, String deviceType) {
/* 289 */     if (explorer == null || deviceType == null) {
/*     */       return;
/*     */     }
/* 292 */     if ("CMS" == deviceType) {
/* 293 */       explorer.setDeviceTypes(Arrays.asList(new String[] { "CMS", "CMSRST" }));
/*     */     } else {
/* 295 */       explorer.setDeviceType(deviceType);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateHtmlPanel(String htmlStr) {
/* 300 */     GWT.log("RPT View updateHtmlPanel");
/*     */   }
/*     */   
/*     */   public ReportQueryConditionDTO getQueryCondition() {
/* 304 */     ReportQueryConditionDTO conditionDto = new ReportQueryConditionDTO();
/* 305 */     RIPViewer ripViewer = getRipViewer();
/* 306 */     if (ripViewer != null) {
/* 307 */       conditionDto.getInputParameter().putAll(ripViewer.getInputParameter());
/*     */     }
/* 309 */     ReportConfigBaseDTO dto = (ReportConfigBaseDTO)this.reportNavigator.tree.getSelectionModel().getSelectedItem();
/* 310 */     if (dto instanceof ReportConfigDetailDTO) {
/* 311 */       conditionDto.setReportId(dto.getId());
/*     */     }
/* 313 */     if (this.htmlPreview != null) {
/* 314 */       conditionDto.setZoomRatio(this.htmlPreview.getRoomRatio());
/*     */     }
/* 316 */     return conditionDto;
/*     */   }
/*     */   
/*     */   public void downloadReportFile(String reportId, String reportFormat) {
/* 320 */     String url = GWT.getHostPageBaseURL();
/* 321 */     if (url.endsWith("/")) {
/* 322 */       url = url.substring(0, url.lastIndexOf("/"));
/*     */     }
/* 324 */     Window.open(url + "/rpt/downloadFile?id=" + reportId + "&format=" + reportFormat, null, null);
/*     */   }
/*     */   
/*     */   public void fillPrinterList(List<String> printers) {
/* 328 */     this.printerList.comboBox.add(printers);
/*     */   }
/*     */   
/*     */   public void initPreviewHeader(Map<String, PreviewHeaderDTO> previewHeaderMap) {
/* 332 */     this.previewContainer.setWidget((Widget)new PreviewGrid(previewHeaderMap));
/* 333 */     this.previewContainer.forceLayout();
/*     */   }
/*     */   
/*     */   public void fillPreviewData(List<PreviewDataDTO> previewData) {
/* 337 */     PreviewGrid previewGrid = (PreviewGrid)this.previewContainer.getWidget();
/* 338 */     if (previewGrid != null) {
/* 339 */       previewGrid.listStore.clear();
/* 340 */       previewGrid.listStore.addAll(previewData);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void fillPreviewGridData(PreviewGridDataDTO previewGridData) {
/* 345 */     this.previewContainer.clear();
/* 346 */     this.previewContainer.setWidget((Widget)new PreviewGridWithinData(previewGridData));
/* 347 */     this.previewContainer.forceLayout();
/*     */   }
/*     */   
/*     */   private RIPViewer getRipViewer() {
/* 351 */     RIPViewerContainer ripViewerContainer = (RIPViewerContainer)this.ripContentPanel.getWidget();
/* 352 */     Widget ripViewer = ripViewerContainer.ripViewerContainer.getWidget();
/* 353 */     return (ripViewer == null) ? null : (RIPViewer)ripViewer;
/*     */   }
/*     */   
/*     */   @UiHandler({"btnClearContent"})
/*     */   void onClearContent(SelectEvent event) {
/* 358 */     getRipViewer().clearInputParameter();
/*     */   }
/*     */   
/*     */   @UiHandler({"btnQuery"})
/*     */   void onQuery(SelectEvent event) {
/* 363 */     GWT.log("RPT ReportGenerationImpl onQuery");
/* 364 */     this.clientFactory.getEventBus().fireEvent((GwtEvent)new ReportGenerationEvent(ReportGenerationEvent.Action.QUERY));
/*     */   }
/*     */   
/*     */   @UiHandler({"btnExportToFile"})
/*     */   void btnExportToFileClicked(SelectEvent event) {
/* 369 */     GWT.log("RPT View btnExportToFileClicked");
/* 370 */     initExportDialog();
/* 371 */     this.exportDialog.show();
/*     */   }
/*     */   
/*     */   @UiHandler({"btnPrint"})
/*     */   void btnPrintClicked(SelectEvent event) {
/* 376 */     GWT.log("RPT View btnPrintClicked");
/* 377 */     initPrinterDialog();
/* 378 */     this.printerDialog.show();
/*     */   }
/*     */   
/*     */   @UiHandler({"btnCollapse"})
/*     */   void onBtnCollapseClicked(SelectEvent event) {
/* 383 */     if (this.borderContainer.getRegionWidget(Style.LayoutRegion.NORTH).isVisible()) {
/* 384 */       this.borderContainer.hide(Style.LayoutRegion.NORTH);
/*     */     } else {
/* 386 */       this.borderContainer.show(Style.LayoutRegion.NORTH);
/*     */     } 
/*     */   }
/*     */   
/*     */   static interface ReportGenerationViewerUiBinder extends UiBinder<Widget, ReportGenerationViewer> {}
/*     */   
/*     */   class ReportGenerationEventHandlerImpl implements ReportGenerationEventHandler {
/*     */     public void onGetReportConfig(ReportGenerationEvent event) {
/* 394 */       GWT.log("RPT Event onGetReportConfig");
/* 395 */       ReportGenerationViewer.this.presenter.fetchReportConfigData();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onGetDeviceConfig(ReportGenerationEvent event) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void onQuery(ReportGenerationEvent event) {
/* 405 */       GWT.log("RPT ReportGenerationEventHandler onQuery");
/* 406 */       RIPViewer ripViewer = ReportGenerationViewer.this.getRipViewer();
/* 407 */       if (ripViewer.isValid()) {
/* 408 */         ReportConfigBaseDTO dto = (ReportConfigBaseDTO)ReportGenerationViewer.this.reportNavigator.tree.getSelectionModel().getSelectedItem();
/* 409 */         if (dto != null && dto instanceof ReportConfigDetailDTO) {
/* 410 */           ReportGenerationViewer.this.mask();
/* 411 */           if (((ReportConfigDetailDTO)dto).isQueryWithHeader()) {
/* 412 */             ReportGenerationViewer.this.presenter.queryWithHeader(dto.getId(), ReportGenerationViewer.this.getQueryCondition());
/*     */           } else {
/* 414 */             ReportGenerationViewer.this.presenter.query(dto.getId(), ReportGenerationViewer.this.getQueryCondition());
/*     */           } 
/*     */         } else {
/* 417 */           Info.display(ReportGenerationViewer.this.messages.info_err_title(), ReportGenerationViewer.this.messages.info_invalidInput_text());
/*     */         } 
/*     */       } else {
/* 420 */         Info.display(ReportGenerationViewer.this.messages.info_err_title(), ReportGenerationViewer.this.messages.info_invalidInput_text());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onExportToChart(ReportGenerationEvent event) {
/* 426 */       GWT.log("RPT Event onExportToChart");
/* 427 */       RIPViewer ripViewer = ReportGenerationViewer.this.getRipViewer();
/* 428 */       if (ripViewer.isValid()) {
/* 429 */         ChartDTO dto = (ChartDTO)event.getSource();
/* 430 */         if (dto == null || dto.getId() == null) {
/* 431 */           Info.display(ReportGenerationViewer.this.messages
/* 432 */               .info_err_downloadChart_title(), ReportGenerationViewer.this.messages.info_err_downloadChart_message());
/*     */           return;
/*     */         } 
/* 435 */         ReportConfigBaseDTO reporDdto = (ReportConfigBaseDTO)ReportGenerationViewer.this.reportNavigator.tree.getSelectionModel().getSelectedItem();
/* 436 */         if (reporDdto != null && reporDdto instanceof ReportConfigDetailDTO) {
/* 437 */           ReportGenerationViewer.this.mask();
/* 438 */           ReportGenerationViewer.this.presenter.downloadChart(reporDdto.getId(), dto.getId());
/*     */         } else {
/* 440 */           Info.display(ReportGenerationViewer.this.messages.info_err_title(), ReportGenerationViewer.this.messages.info_invalidInput_text());
/*     */         } 
/*     */       } else {
/* 443 */         Info.display(ReportGenerationViewer.this.messages.info_err_title(), ReportGenerationViewer.this.messages.info_invalidInput_text());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onExportToFile(ReportGenerationEvent event) {
/* 449 */       GWT.log("RPT Event onExportToFile");
/* 450 */       RIPViewer ripViewer = ReportGenerationViewer.this.getRipViewer();
/* 451 */       if (ripViewer.isValid()) {
/* 452 */         ReportConfigBaseDTO dto = (ReportConfigBaseDTO)ReportGenerationViewer.this.reportNavigator.tree.getSelectionModel().getSelectedItem();
/* 453 */         if (dto != null && dto instanceof ReportConfigDetailDTO) {
/* 454 */           ReportGenerationViewer.this.mask();
/* 455 */           ReportGenerationViewer.this.presenter.downloadReport(dto.getId(), ReportGenerationViewer.this.getReportFormat());
/*     */         } else {
/* 457 */           Info.display(ReportGenerationViewer.this.messages.info_err_title(), ReportGenerationViewer.this.messages.info_invalidInput_text());
/*     */         } 
/*     */       } else {
/* 460 */         Info.display(ReportGenerationViewer.this.messages.info_err_title(), ReportGenerationViewer.this.messages.info_invalidInput_text());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onPrintReport(ReportGenerationEvent event) {
/* 466 */       GWT.log("RPT Event onPrintReport");
/* 467 */       RIPViewer ripViewer = ReportGenerationViewer.this.getRipViewer();
/* 468 */       if (ripViewer.isValid()) {
/* 469 */         ReportConfigBaseDTO dto = (ReportConfigBaseDTO)ReportGenerationViewer.this.reportNavigator.tree.getSelectionModel().getSelectedItem();
/* 470 */         if (dto != null && dto instanceof ReportConfigDetailDTO) {
/* 471 */           ReportGenerationViewer.this.mask();
/* 472 */           ReportGenerationViewer.this.presenter.printReport(dto.getId(), (String)ReportGenerationViewer.this.printerList.comboBox.getValue());
/*     */         } else {
/* 474 */           Info.display(ReportGenerationViewer.this.messages.info_err_title(), ReportGenerationViewer.this.messages.info_invalidInput_text());
/*     */         } 
/*     */       } else {
/* 477 */         Info.display(ReportGenerationViewer.this.messages.info_err_title(), ReportGenerationViewer.this.messages.info_invalidInput_text());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onReportNodeSelected(ReportGenerationEvent event) {
/* 483 */       GWT.log("RPT Event onReportNodeSelected");
/* 484 */       ReportConfigBaseDTO dto = (ReportConfigBaseDTO)ReportGenerationViewer.this.reportNavigator.tree.getSelectionModel().getSelectedItem();
/* 485 */       if (dto != null && dto instanceof ReportConfigDetailDTO) {
/* 486 */         ReportGenerationViewer.this.mask();
/* 487 */         ReportGenerationViewer.this.presenter.retrievePreviewHeader(dto.getId());
/* 488 */         ReportGenerationViewer.this.updateRIPViewer((ReportConfigDetailDTO)dto);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onChangeDeviceType(ReportGenerationEvent event) {
/* 494 */       RIPViewer viewer = ReportGenerationViewer.this.getRipViewer();
/* 495 */       if (viewer instanceof DeviceConfigExplorer) {
/* 496 */         DeviceConfigExplorer explorer = (DeviceConfigExplorer)viewer;
/* 497 */         String deviceType = (String)event.getSource();
/* 498 */         ReportGenerationViewer.this.setDeviceTypeToDeviceConfigExplorer(explorer, deviceType);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onPreviewHtmlChart(ReportGenerationEvent event) {
/* 504 */       RIPViewer ripViewer = ReportGenerationViewer.this.getRipViewer();
/* 505 */       if (ripViewer.isValid()) {
/* 506 */         ChartDTO dto = (ChartDTO)event.getSource();
/* 507 */         if (dto == null || dto.getId() == null) {
/* 508 */           Info.display(ReportGenerationViewer.this.messages
/* 509 */               .info_err_downloadChart_title(), ReportGenerationViewer.this.messages.info_err_downloadChart_message());
/*     */           
/*     */           return;
/*     */         } 
/* 513 */         ReportConfigBaseDTO reporDdto = (ReportConfigBaseDTO)ReportGenerationViewer.this.reportNavigator.tree.getSelectionModel().getSelectedItem();
/* 514 */         if (reporDdto instanceof ReportConfigDetailDTO) {
/* 515 */           ReportGenerationViewer.this.initHtmlPreviewDialog(dto);
/* 516 */           ReportGenerationViewer.this.htmlPreviewDialog.show();
/*     */         } else {
/* 518 */           Info.display(ReportGenerationViewer.this.messages.info_err_title(), ReportGenerationViewer.this.messages.info_invalidInput_text());
/*     */         } 
/*     */       } else {
/* 521 */         Info.display(ReportGenerationViewer.this.messages.info_err_title(), ReportGenerationViewer.this.messages.info_invalidInput_text());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onReportPreviewToolBarZoom(ReportGenerationEvent event) {
/* 527 */       if (ReportGenerationViewer.this.htmlPreviewDialog == null || !ReportGenerationViewer.this.htmlPreviewDialog.isVisible()) {
/*     */         return;
/*     */       }
/* 530 */       ReportGenerationViewer.this.htmlPreview.reload();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void mask() {
/* 536 */     this.previewContainer.mask(this.messages.container_mask_loading());
/*     */   }
/*     */ 
/*     */   
/*     */   public void unmask() {
/* 541 */     this.previewContainer.unmask();
/*     */   }
/*     */   
/*     */   private void initHtmlPreviewDialog(ChartDTO chartDTO) {
/* 545 */     if (this.htmlPreviewDialog == null) {
/* 546 */       this.htmlPreviewDialog = new Dialog();
/* 547 */       this.htmlPreviewDialog.setWidth(1200);
/* 548 */       this.htmlPreviewDialog.setHeight(800);
/* 549 */       this.htmlPreviewDialog.setModal(true);
/* 550 */       this.htmlPreviewDialog.setResizable(false);
/* 551 */       this.htmlPreviewDialog.getButtonBar().clear();
/*     */     } else {
/* 553 */       this.htmlPreviewDialog.remove(0);
/*     */     } 
/*     */     
/* 556 */     this.htmlPreview = (HtmlPreviewer)GWT.create(HtmlPreviewer.class);
/* 557 */     this.htmlPreviewDialog.setHeading(chartDTO.getChartName());
/* 558 */     this.htmlPreviewDialog.add((Widget)this.htmlPreview, new MarginData(4, 4, 4, 4));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\rpt\ReportGenerationViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */