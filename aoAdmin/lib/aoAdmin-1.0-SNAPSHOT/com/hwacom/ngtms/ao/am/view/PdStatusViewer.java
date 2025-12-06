/*      */ package com.hwacom.ngtms.ao.am.view;
/*      */ import com.google.gwt.cell.client.AbstractCell;
/*      */ import com.google.gwt.cell.client.Cell;
/*      */ import com.google.gwt.core.client.GWT;
/*      */ import com.google.gwt.dom.client.Element;
/*      */ import com.google.gwt.dom.client.Node;
/*      */ import com.google.gwt.event.dom.client.MouseDownEvent;
/*      */ import com.google.gwt.event.dom.client.MouseDownHandler;
/*      */ import com.google.gwt.event.logical.shared.SelectionEvent;
/*      */ import com.google.gwt.event.shared.EventHandler;
/*      */ import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
/*      */ import com.google.gwt.uibinder.client.UiBinder;
/*      */ import com.google.gwt.uibinder.client.UiField;
/*      */ import com.google.gwt.uibinder.client.UiHandler;
/*      */ import com.google.gwt.user.client.Timer;
/*      */ import com.google.gwt.user.client.ui.Label;
/*      */ import com.google.gwt.user.client.ui.Widget;
/*      */ import com.hwacom.ngtms.ao.am.presenter.PdStatusPresenter;
/*      */ import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
/*      */ import com.hwacom.ngtms.cam.client.ui.AmTab;
/*      */ import com.hwacom.ngtms.pd.am.images.AllImages;
/*      */ import com.hwacom.ngtms.pd.shared.dto.LoopDeviceConfigDTO;
/*      */ import com.hwacom.ngtms.pd.shared.dto.PdStatusDTO;
/*      */ import com.sencha.gxt.core.client.Style;
/*      */ import com.sencha.gxt.core.client.ValueProvider;
/*      */ import com.sencha.gxt.data.shared.LabelProvider;
/*      */ import com.sencha.gxt.data.shared.ListStore;
/*      */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*      */ import com.sencha.gxt.data.shared.PropertyAccess;
/*      */ import com.sencha.gxt.data.shared.Store;
/*      */ import com.sencha.gxt.widget.core.client.ContentPanel;
/*      */ import com.sencha.gxt.widget.core.client.Dialog;
/*      */ import com.sencha.gxt.widget.core.client.button.TextButton;
/*      */ import com.sencha.gxt.widget.core.client.button.ToolButton;
/*      */ import com.sencha.gxt.widget.core.client.event.HideEvent;
/*      */ import com.sencha.gxt.widget.core.client.event.RowClickEvent;
/*      */ import com.sencha.gxt.widget.core.client.event.SelectEvent;
/*      */ import com.sencha.gxt.widget.core.client.form.ComboBox;
/*      */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*      */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*      */ import com.sencha.gxt.widget.core.client.grid.Grid;
/*      */ import com.sencha.gxt.widget.core.client.grid.GridView;
/*      */ import com.sencha.gxt.widget.core.client.grid.HeaderGroupConfig;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import org.vectomatic.dom.svg.OMElement;
/*      */ import org.vectomatic.dom.svg.OMNode;
/*      */ import org.vectomatic.dom.svg.OMSVGDocument;
/*      */ import org.vectomatic.dom.svg.OMSVGSVGElement;
/*      */ import org.vectomatic.dom.svg.ui.SVGResource;
/*      */ import org.vectomatic.dom.svg.utils.OMSVGParser;
/*      */ 
/*      */ public class PdStatusViewer extends AmTab {
/*   57 */   private static PdStatusViewerUiBinder uiBinder = (PdStatusViewerUiBinder)GWT.create(PdStatusViewerUiBinder.class);
/*      */ 
/*      */ 
/*      */   
/*   61 */   private static Messages messages = (Messages)GWT.create(Messages.class);
/*      */   
/*   63 */   private final PdStatusPropertyAccess propertyAccess = (PdStatusPropertyAccess)GWT.create(PdStatusPropertyAccess.class);
/*      */ 
/*      */   
/*   66 */   private final RoadLineProertyAccess roadLineProertyAccess = (RoadLineProertyAccess)GWT.create(RoadLineProertyAccess.class);
/*      */   
/*   68 */   private PdStatusPresenter presenter = new PdStatusPresenter(this);
/*      */   
/*      */   @UiField
/*      */   Grid<PdStatusDTO> grid;
/*      */   
/*      */   @UiField(provided = true)
/*      */   ListStore<PdStatusDTO> listStore;
/*      */   
/*      */   @UiField(provided = true)
/*      */   ColumnModel<PdStatusDTO> columnModel;
/*      */   
/*      */   @UiField
/*      */   GridView<PdStatusDTO> gridView;
/*      */   
/*      */   @UiField
/*      */   ComboBox<RoadLineDTO> roadLineCB;
/*      */   
/*      */   @UiField(provided = true)
/*      */   ListStore<RoadLineDTO> roadLineStore;
/*      */   
/*      */   @UiField(provided = true)
/*      */   LabelProvider<RoadLineDTO> roadLineProvider;
/*      */   @UiField
/*      */   ComboBox<String> abnormalStatusCB;
/*      */   @UiField(provided = true)
/*      */   ListStore<String> abnormalStatusStore;
/*      */   @UiField(provided = true)
/*      */   LabelProvider<String> abnormalStatusProvider;
/*      */   @UiField
/*      */   ToolButton update;
/*      */   @UiField
/*      */   ContentPanel svgContainer;
/*  100 */   private OMSVGDocument doc = OMSVGParser.currentDocument();
/*      */   
/*  102 */   private OMSVGSVGElement svg = this.doc.createSVGSVGElement();
/*  103 */   private Element div = null;
/*      */   
/*      */   private Timer timer;
/*      */   
/*  107 */   private StatusFilter filter = new StatusFilter();
/*      */   
/*  109 */   private String selectedDevice = new String();
/*      */   
/*  111 */   private Map<Integer, List<String>> underPDsMap = new HashMap<>();
/*      */   
/*  113 */   private List<OMElement> elements = new ArrayList<>();
/*      */   
/*      */   private Dialog underPdDialog;
/*      */ 
/*      */   
/*  118 */   private static AbstractCell<String> statusCell = new AbstractCell<String>(new String[0])
/*      */     {
/*      */       
/*      */       public void render(Cell.Context context, String value, SafeHtmlBuilder sb)
/*      */       {
/*  123 */         sb.appendHtmlConstant(value);
/*      */       }
/*      */     };
/*      */   
/*      */   public PdStatusViewer() {
/*  128 */     this.roadLineStore = new ListStore(this.roadLineProertyAccess.lineId());
/*  129 */     this.roadLineProvider = this.roadLineProertyAccess.lineName();
/*      */     
/*  131 */     this.abnormalStatusStore = new ListStore(new ModelKeyProvider<String>()
/*      */         {
/*      */           
/*      */           public String getKey(String item)
/*      */           {
/*  136 */             return item;
/*      */           }
/*      */         });
/*  139 */     this.abnormalStatusProvider = new LabelProvider<String>()
/*      */       {
/*      */         public String getLabel(String item)
/*      */         {
/*  143 */           return item;
/*      */         }
/*      */       };
/*      */     
/*  147 */     this.listStore = new ListStore(this.propertyAccess.pdId());
/*  148 */     initColumnModel();
/*      */     
/*  150 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*  151 */     this.div = (Element)this.svgContainer.getElement();
/*  152 */     this.abnormalStatusStore.add("異常");
/*  153 */     this.abnormalStatusStore.add("全部");
/*  154 */     this.abnormalStatusStore.add("欠相");
/*  155 */     this.abnormalStatusStore.add("斷電");
/*  156 */     this.abnormalStatusStore.add("分迴路異常");
/*  157 */     this.abnormalStatusStore.add("斷線");
/*  158 */     this.abnormalStatusCB.setValue(this.abnormalStatusStore.get(0));
/*  159 */     this.listStore.addFilter(this.filter);
/*  160 */     this.listStore.setEnableFilters(true);
/*  161 */     this.presenter.retrieveRoadLine();
/*  162 */     this.presenter.retrievePdStatus();
/*  163 */     startTimer();
/*      */     
/*  165 */     this.grid.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
/*      */   }
/*      */   
/*      */   private void initColumnModel() {
/*  169 */     final List<ColumnConfig<PdStatusDTO, ?>> columnConfigs = new ArrayList<>();
/*      */ 
/*      */ 
/*      */     
/*  173 */     final String onlineCheck = "<span style='color:black'>" + messages.pdStatus_status_online() + "</span>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  192 */     ColumnConfig<PdStatusDTO, String> displayName = new ColumnConfig(new ValueProvider<PdStatusDTO, String>() { public String getValue(PdStatusDTO dto) { return dto.getPdDisplayName(); } public void setValue(PdStatusDTO object, String value) {} public String getPath() { return "connectivity"; } }, 100, messages.column_pdStatus_displayName());
/*  193 */     displayName.setMenuDisabled(true);
/*  194 */     columnConfigs.add(displayName);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  219 */     ColumnConfig<PdStatusDTO, String> connectivity = new ColumnConfig(new ValueProvider<PdStatusDTO, String>() { public String getValue(PdStatusDTO dto) { return "<span style='color:" + ((dto.getConnectivity().intValue() == 0) ? "black" : "red") + "'>" + ((dto.getConnectivity().intValue() == 0) ? PdStatusViewer.messages.pdStatus_status_online() : PdStatusViewer.messages.pdStatus_status_offline()) + "</span>"; } public void setValue(PdStatusDTO object, String value) {} public String getPath() { return "connectivity"; } }, 100, messages.column_pdStatus_connectivity());
/*  220 */     connectivity.setMenuDisabled(true);
/*  221 */     connectivity.setCell((Cell)statusCell);
/*  222 */     columnConfigs.add(connectivity);
/*      */     
/*  224 */     ColumnConfig<PdStatusDTO, String> primaryR = new ColumnConfig(new ValueProvider<PdStatusDTO, String>()
/*      */         {
/*      */           
/*      */           public String getValue(PdStatusDTO dto)
/*      */           {
/*  229 */             if (((ColumnConfig)columnConfigs.get(1)).getValueProvider().getValue(dto) == onlineCheck) {
/*  230 */               return "<span style='color:" + (
/*  231 */                 (dto.getPrimaryR().intValue() == 0) ? "black" : "red") + "'>" + (
/*      */                 
/*  233 */                 (dto.getPrimaryR().intValue() == 0) ? PdStatusViewer
/*  234 */                 .messages.pdStatus_status_normal() : PdStatusViewer
/*  235 */                 .messages.pdStatus_status_abnormal()) + "</span>";
/*      */             }
/*      */             
/*  238 */             return "<span style='color:black'>-</span>";
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void setValue(PdStatusDTO object, String value) {}
/*      */ 
/*      */           
/*      */           public String getPath() {
/*  247 */             return "primaryR";
/*      */           }
/*      */         },  35, "R");
/*      */ 
/*      */     
/*  252 */     primaryR.setMenuDisabled(true);
/*  253 */     primaryR.setCell((Cell)statusCell);
/*  254 */     columnConfigs.add(primaryR);
/*      */     
/*  256 */     ColumnConfig<PdStatusDTO, String> primaryS = new ColumnConfig(new ValueProvider<PdStatusDTO, String>()
/*      */         {
/*      */           
/*      */           public String getValue(PdStatusDTO dto)
/*      */           {
/*  261 */             if (((ColumnConfig)columnConfigs.get(1)).getValueProvider().getValue(dto) == onlineCheck) {
/*  262 */               return "<span style='color:" + (
/*  263 */                 (dto.getPrimaryS().intValue() == 0) ? "black" : "red") + "'>" + (
/*      */                 
/*  265 */                 (dto.getPrimaryS().intValue() == 0) ? PdStatusViewer
/*  266 */                 .messages.pdStatus_status_normal() : PdStatusViewer
/*  267 */                 .messages.pdStatus_status_abnormal()) + "</span>";
/*      */             }
/*      */             
/*  270 */             return "<span style='color:black'>-</span>";
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void setValue(PdStatusDTO object, String value) {}
/*      */ 
/*      */           
/*      */           public String getPath() {
/*  279 */             return "primaryS";
/*      */           }
/*      */         },  35, "S");
/*      */ 
/*      */     
/*  284 */     primaryS.setMenuDisabled(true);
/*  285 */     primaryS.setCell((Cell)statusCell);
/*  286 */     columnConfigs.add(primaryS);
/*      */     
/*  288 */     ColumnConfig<PdStatusDTO, String> primaryT = new ColumnConfig(new ValueProvider<PdStatusDTO, String>()
/*      */         {
/*      */           
/*      */           public String getValue(PdStatusDTO dto)
/*      */           {
/*  293 */             if (((ColumnConfig)columnConfigs.get(1)).getValueProvider().getValue(dto) == onlineCheck) {
/*  294 */               return "<span style='color:" + (
/*  295 */                 (dto.getPrimaryT().intValue() == 0) ? "black" : "red") + "'>" + (
/*      */                 
/*  297 */                 (dto.getPrimaryT().intValue() == 0) ? PdStatusViewer
/*  298 */                 .messages.pdStatus_status_normal() : PdStatusViewer
/*  299 */                 .messages.pdStatus_status_abnormal()) + "</span>";
/*      */             }
/*      */             
/*  302 */             return "<span style='color:black'>-</span>";
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void setValue(PdStatusDTO object, String value) {}
/*      */ 
/*      */           
/*      */           public String getPath() {
/*  311 */             return "primaryT";
/*      */           }
/*      */         },  35, "T");
/*      */ 
/*      */     
/*  316 */     primaryT.setMenuDisabled(true);
/*  317 */     primaryT.setCell((Cell)statusCell);
/*  318 */     columnConfigs.add(primaryT);
/*      */     
/*  320 */     ColumnConfig<PdStatusDTO, String> secondaryR = new ColumnConfig(new ValueProvider<PdStatusDTO, String>()
/*      */         {
/*      */           
/*      */           public String getValue(PdStatusDTO dto)
/*      */           {
/*  325 */             if (((ColumnConfig)columnConfigs.get(1)).getValueProvider().getValue(dto) == onlineCheck) {
/*  326 */               return "<span style='color:" + (
/*  327 */                 (dto.getSecondaryR().intValue() == 0) ? "black" : "red") + "'>" + (
/*      */                 
/*  329 */                 (dto.getSecondaryR().intValue() == 0) ? PdStatusViewer
/*  330 */                 .messages.pdStatus_status_normal() : PdStatusViewer
/*  331 */                 .messages.pdStatus_status_abnormal()) + "</span>";
/*      */             }
/*      */             
/*  334 */             return "<span style='color:black'>-</span>";
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void setValue(PdStatusDTO object, String value) {}
/*      */ 
/*      */           
/*      */           public String getPath() {
/*  343 */             return "secondaryR";
/*      */           }
/*      */         },  35, "R");
/*      */ 
/*      */     
/*  348 */     secondaryR.setMenuDisabled(true);
/*  349 */     secondaryR.setCell((Cell)statusCell);
/*  350 */     columnConfigs.add(secondaryR);
/*      */     
/*  352 */     ColumnConfig<PdStatusDTO, String> secondaryS = new ColumnConfig(new ValueProvider<PdStatusDTO, String>()
/*      */         {
/*      */           
/*      */           public String getValue(PdStatusDTO dto)
/*      */           {
/*  357 */             if (((ColumnConfig)columnConfigs.get(1)).getValueProvider().getValue(dto) == onlineCheck) {
/*  358 */               return "<span style='color:" + (
/*  359 */                 (dto.getSecondaryS().intValue() == 0) ? "black" : "red") + "'>" + (
/*      */                 
/*  361 */                 (dto.getSecondaryS().intValue() == 0) ? PdStatusViewer
/*  362 */                 .messages.pdStatus_status_normal() : PdStatusViewer
/*  363 */                 .messages.pdStatus_status_abnormal()) + "</span>";
/*      */             }
/*      */             
/*  366 */             return "<span style='color:black'>-</span>";
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void setValue(PdStatusDTO object, String value) {}
/*      */ 
/*      */           
/*      */           public String getPath() {
/*  375 */             return "secondaryS";
/*      */           }
/*      */         },  35, "S");
/*      */ 
/*      */     
/*  380 */     secondaryS.setMenuDisabled(true);
/*  381 */     secondaryS.setCell((Cell)statusCell);
/*  382 */     columnConfigs.add(secondaryS);
/*      */     
/*  384 */     ColumnConfig<PdStatusDTO, String> secondaryT = new ColumnConfig(new ValueProvider<PdStatusDTO, String>()
/*      */         {
/*      */           
/*      */           public String getValue(PdStatusDTO dto)
/*      */           {
/*  389 */             if (((ColumnConfig)columnConfigs.get(1)).getValueProvider().getValue(dto) == onlineCheck) {
/*  390 */               return "<span style='color:" + (
/*  391 */                 (dto.getSecondaryT().intValue() == 0) ? "black" : "red") + "'>" + (
/*      */                 
/*  393 */                 (dto.getSecondaryT().intValue() == 0) ? PdStatusViewer
/*  394 */                 .messages.pdStatus_status_normal() : PdStatusViewer
/*  395 */                 .messages.pdStatus_status_abnormal()) + "</span>";
/*      */             }
/*      */             
/*  398 */             return "<span style='color:black'>-</span>";
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void setValue(PdStatusDTO object, String value) {}
/*      */ 
/*      */           
/*      */           public String getPath() {
/*  407 */             return "secondaryT";
/*      */           }
/*      */         },  35, "T");
/*      */ 
/*      */     
/*  412 */     secondaryT.setMenuDisabled(true);
/*  413 */     secondaryT.setCell((Cell)statusCell);
/*  414 */     columnConfigs.add(secondaryT);
/*      */     
/*  416 */     ColumnConfig<PdStatusDTO, String> loop1Status = new ColumnConfig(new ValueProvider<PdStatusDTO, String>()
/*      */         {
/*      */           
/*      */           public String getValue(PdStatusDTO dto)
/*      */           {
/*  421 */             if (dto.getLoop1Status() != null) {
/*  422 */               if (((ColumnConfig)columnConfigs.get(1)).getValueProvider().getValue(dto) == onlineCheck) {
/*  423 */                 return "<span style='color:" + (
/*  424 */                   (dto.getLoop1Status().intValue() == 0) ? "black" : "red") + "'>" + (
/*      */                   
/*  426 */                   (dto.getLoop1Status().intValue() == 0) ? PdStatusViewer
/*  427 */                   .messages.pdStatus_status_normal() : PdStatusViewer
/*  428 */                   .messages.pdStatus_status_abnormal()) + "</span>";
/*      */               }
/*      */               
/*  431 */               return "<span style='color:black'>-</span>";
/*      */             } 
/*      */             
/*  434 */             return "<span style='color:black'>-</span>";
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void setValue(PdStatusDTO object, String value) {}
/*      */ 
/*      */           
/*      */           public String getPath() {
/*  443 */             return "loop1Status";
/*      */           }
/*      */         },  35, "1");
/*      */ 
/*      */     
/*  448 */     loop1Status.setMenuDisabled(true);
/*  449 */     loop1Status.setCell((Cell)statusCell);
/*  450 */     columnConfigs.add(loop1Status);
/*      */     
/*  452 */     ColumnConfig<PdStatusDTO, String> loop2Status = new ColumnConfig(new ValueProvider<PdStatusDTO, String>()
/*      */         {
/*      */           
/*      */           public String getValue(PdStatusDTO dto)
/*      */           {
/*  457 */             if (dto.getLoop2Status() != null) {
/*  458 */               if (((ColumnConfig)columnConfigs.get(1)).getValueProvider().getValue(dto) == onlineCheck) {
/*  459 */                 return "<span style='color:" + (
/*  460 */                   (dto.getLoop2Status().intValue() == 0) ? "black" : "red") + "'>" + (
/*      */                   
/*  462 */                   (dto.getLoop2Status().intValue() == 0) ? PdStatusViewer
/*  463 */                   .messages.pdStatus_status_normal() : PdStatusViewer
/*  464 */                   .messages.pdStatus_status_abnormal()) + "</span>";
/*      */               }
/*      */               
/*  467 */               return "<span style='color:black'>-</span>";
/*      */             } 
/*      */             
/*  470 */             return "<span style='color:black'>-</span>";
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void setValue(PdStatusDTO object, String value) {}
/*      */ 
/*      */           
/*      */           public String getPath() {
/*  479 */             return "loop2Status";
/*      */           }
/*      */         },  35, "2");
/*      */ 
/*      */     
/*  484 */     loop2Status.setMenuDisabled(true);
/*  485 */     loop2Status.setCell((Cell)statusCell);
/*  486 */     columnConfigs.add(loop2Status);
/*      */     
/*  488 */     ColumnConfig<PdStatusDTO, String> loop3Status = new ColumnConfig(new ValueProvider<PdStatusDTO, String>()
/*      */         {
/*      */           
/*      */           public String getValue(PdStatusDTO dto)
/*      */           {
/*  493 */             if (dto.getLoop3Status() != null) {
/*  494 */               if (((ColumnConfig)columnConfigs.get(1)).getValueProvider().getValue(dto) == onlineCheck) {
/*  495 */                 return "<span style='color:" + (
/*  496 */                   (dto.getLoop3Status().intValue() == 0) ? "black" : "red") + "'>" + (
/*      */                   
/*  498 */                   (dto.getLoop3Status().intValue() == 0) ? PdStatusViewer
/*  499 */                   .messages.pdStatus_status_normal() : PdStatusViewer
/*  500 */                   .messages.pdStatus_status_abnormal()) + "</span>";
/*      */               }
/*      */               
/*  503 */               return "<span style='color:black'>-</span>";
/*      */             } 
/*      */             
/*  506 */             return "<span style='color:black'>-</span>";
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void setValue(PdStatusDTO object, String value) {}
/*      */ 
/*      */           
/*      */           public String getPath() {
/*  515 */             return "loop3Status";
/*      */           }
/*      */         },  35, "3");
/*      */ 
/*      */     
/*  520 */     loop3Status.setMenuDisabled(true);
/*  521 */     loop3Status.setCell((Cell)statusCell);
/*  522 */     columnConfigs.add(loop3Status);
/*      */     
/*  524 */     ColumnConfig<PdStatusDTO, String> loop4Status = new ColumnConfig(new ValueProvider<PdStatusDTO, String>()
/*      */         {
/*      */           
/*      */           public String getValue(PdStatusDTO dto)
/*      */           {
/*  529 */             if (dto.getLoop4Status() != null) {
/*  530 */               if (((ColumnConfig)columnConfigs.get(1)).getValueProvider().getValue(dto) == onlineCheck) {
/*  531 */                 return "<span style='color:" + (
/*  532 */                   (dto.getLoop4Status().intValue() == 0) ? "black" : "red") + "'>" + (
/*      */                   
/*  534 */                   (dto.getLoop4Status().intValue() == 0) ? PdStatusViewer
/*  535 */                   .messages.pdStatus_status_normal() : PdStatusViewer
/*  536 */                   .messages.pdStatus_status_abnormal()) + "</span>";
/*      */               }
/*      */               
/*  539 */               return "<span style='color:black'>-</span>";
/*      */             } 
/*      */             
/*  542 */             return "<span style='color:black'>-</span>";
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void setValue(PdStatusDTO object, String value) {}
/*      */ 
/*      */           
/*      */           public String getPath() {
/*  551 */             return "loop4Status";
/*      */           }
/*      */         },  35, "4");
/*      */ 
/*      */     
/*  556 */     loop4Status.setMenuDisabled(true);
/*  557 */     loop4Status.setCell((Cell)statusCell);
/*  558 */     columnConfigs.add(loop4Status);
/*      */     
/*  560 */     ColumnConfig<PdStatusDTO, String> loop5Status = new ColumnConfig(new ValueProvider<PdStatusDTO, String>()
/*      */         {
/*      */           
/*      */           public String getValue(PdStatusDTO dto)
/*      */           {
/*  565 */             if (dto.getLoop5Status() != null) {
/*  566 */               if (((ColumnConfig)columnConfigs.get(1)).getValueProvider().getValue(dto) == onlineCheck) {
/*  567 */                 return "<span style='color:" + (
/*  568 */                   (dto.getLoop5Status().intValue() == 0) ? "black" : "red") + "'>" + (
/*      */                   
/*  570 */                   (dto.getLoop5Status().intValue() == 0) ? PdStatusViewer
/*  571 */                   .messages.pdStatus_status_normal() : PdStatusViewer
/*  572 */                   .messages.pdStatus_status_abnormal()) + "</span>";
/*      */               }
/*      */               
/*  575 */               return "<span style='color:black'>-</span>";
/*      */             } 
/*      */             
/*  578 */             return "<span style='color:black'>-</span>";
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void setValue(PdStatusDTO object, String value) {}
/*      */ 
/*      */           
/*      */           public String getPath() {
/*  587 */             return "loop5Status";
/*      */           }
/*      */         },  35, "5");
/*      */ 
/*      */     
/*  592 */     loop5Status.setMenuDisabled(true);
/*  593 */     loop5Status.setCell((Cell)statusCell);
/*  594 */     columnConfigs.add(loop5Status);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  623 */     ColumnConfig<PdStatusDTO, String> doorOpen = new ColumnConfig(new ValueProvider<PdStatusDTO, String>() { public String getValue(PdStatusDTO dto) { if (((ColumnConfig)columnConfigs.get(1)).getValueProvider().getValue(dto) == onlineCheck) return "<span style='color:" + ((dto.getDoorOpen().intValue() == 0) ? "black" : "red") + "'>" + ((dto.getDoorOpen().intValue() == 0) ? PdStatusViewer.messages.pdStatus_status_close() : PdStatusViewer.messages.pdStatus_status_open()) + "</span>";  return "<span style='color:black'>-</span>"; } public void setValue(PdStatusDTO object, String value) {} public String getPath() { return "doorOpen"; } }, 100, messages.column_pdStatus_doorOpen());
/*  624 */     doorOpen.setMenuDisabled(true);
/*  625 */     doorOpen.setCell((Cell)statusCell);
/*  626 */     columnConfigs.add(doorOpen);
/*      */     
/*  628 */     this.columnModel = new ColumnModel(columnConfigs);
/*  629 */     this.columnModel.addHeaderGroup(0, 2, new HeaderGroupConfig(messages
/*  630 */           .pdStatus_headerGroup_primary(), 1, 3));
/*  631 */     this.columnModel.addHeaderGroup(0, 5, new HeaderGroupConfig(messages
/*  632 */           .pdStatus_headerGroup_secondary(), 1, 3));
/*  633 */     this.columnModel.addHeaderGroup(0, 8, new HeaderGroupConfig(messages
/*  634 */           .pdStatus_headerGroup_loop(), 1, 5));
/*      */   }
/*      */   
/*      */   @UiHandler({"grid"})
/*      */   public void rowClick(RowClickEvent event) {
/*  639 */     PdStatusDTO dto = (PdStatusDTO)this.grid.getSelectionModel().getSelectedItem();
/*  640 */     this.selectedDevice = dto.getPdId();
/*  641 */     fillSvg(dto);
/*      */   }
/*      */   
/*      */   @UiHandler({"update"})
/*      */   public void onUpdate(SelectEvent se) {
/*  646 */     this.presenter.retrievePdStatus();
/*      */   }
/*      */   
/*      */   @UiHandler({"roadLineCB"})
/*      */   public void onRoadLineCB(SelectionEvent<RoadLineDTO> se) {
/*  651 */     this.listStore.setEnableFilters(false);
/*  652 */     this.listStore.setEnableFilters(true);
/*      */   }
/*      */   
/*      */   @UiHandler({"abnormalStatusCB"})
/*      */   public void onAbnormalStatusCB(SelectionEvent<String> se) {
/*  657 */     this.listStore.setEnableFilters(false);
/*  658 */     this.listStore.setEnableFilters(true);
/*      */   }
/*      */   
/*      */   private void startTimer() {
/*  662 */     this.timer = new Timer()
/*      */       {
/*      */         public void run()
/*      */         {
/*  666 */           PdStatusViewer.this.presenter.retrievePdStatus();
/*  667 */           PdStatusViewer.this.clearSVGComponent();
/*      */         }
/*      */       };
/*  670 */     this.timer.scheduleRepeating(60000);
/*  671 */     this.timer.run();
/*      */   }
/*      */   
/*      */   private class StatusFilter implements Store.StoreFilter<PdStatusDTO> {
/*      */     private StatusFilter() {}
/*      */     
/*      */     public boolean select(Store<PdStatusDTO> store, PdStatusDTO parent, PdStatusDTO item) {
/*  678 */       List<Boolean> checkList = PdStatusViewer.this.checkAbnormal(item);
/*  679 */       if (!((RoadLineDTO)PdStatusViewer.this.roadLineCB.getCurrentValue()).getLineId().equals("all") && 
/*  680 */         !item.getLineId().equals(((RoadLineDTO)PdStatusViewer.this.roadLineCB.getCurrentValue()).getLineId())) {
/*  681 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  685 */       if (!((String)PdStatusViewer.this.abnormalStatusCB.getCurrentValue()).equals("全部")) {
/*  686 */         if (((String)PdStatusViewer.this.abnormalStatusCB.getCurrentValue()).equals("異常") && item
/*  687 */           .getConnectivity().intValue() == 0 && 
/*  688 */           !((Boolean)checkList.get(0)).booleanValue() && 
/*  689 */           !((Boolean)checkList.get(1)).booleanValue() && 
/*  690 */           !((Boolean)checkList.get(2)).booleanValue()) {
/*  691 */           return false;
/*      */         }
/*  693 */         if (((String)PdStatusViewer.this.abnormalStatusCB.getCurrentValue()).equals("斷線") && item.getConnectivity().intValue() == 0) {
/*  694 */           return false;
/*      */         }
/*  696 */         if (((String)PdStatusViewer.this.abnormalStatusCB.getCurrentValue()).equals("欠相") && !((Boolean)checkList.get(0)).booleanValue()) {
/*  697 */           return false;
/*      */         }
/*  699 */         if (((String)PdStatusViewer.this.abnormalStatusCB.getCurrentValue()).equals("斷電") && !((Boolean)checkList.get(1)).booleanValue()) {
/*  700 */           return false;
/*      */         }
/*  702 */         if (((String)PdStatusViewer.this.abnormalStatusCB.getCurrentValue()).equals("分迴路異常") && !((Boolean)checkList.get(2)).booleanValue()) {
/*  703 */           return false;
/*      */         }
/*      */       } 
/*  706 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   private List<Boolean> checkAbnormal(PdStatusDTO dto) {
/*  711 */     List<Boolean> list = new ArrayList<>();
/*  712 */     Boolean primaryStatus = Boolean.valueOf(false);
/*  713 */     Boolean secondaryStatus = Boolean.valueOf(false);
/*  714 */     Boolean loopStatus = Boolean.valueOf(false);
/*  715 */     if (dto.getConnectivity().intValue() == 0) {
/*  716 */       if ((dto.getPrimaryR().intValue() == 1 || dto
/*  717 */         .getPrimaryS().intValue() == 1 || dto
/*  718 */         .getPrimaryT().intValue() == 1 || dto
/*  719 */         .getSecondaryR().intValue() == 1 || dto
/*  720 */         .getSecondaryS().intValue() == 1 || dto
/*  721 */         .getSecondaryT().intValue() == 1) && (dto
/*  722 */         .getPrimaryR().intValue() != 1 || dto.getPrimaryR().intValue() != 1 || dto.getPrimaryR().intValue() != 1) && (dto
/*  723 */         .getSecondaryR().intValue() != 1 || dto
/*  724 */         .getSecondaryS().intValue() != 1 || dto
/*  725 */         .getSecondaryT().intValue() != 1)) {
/*  726 */         primaryStatus = Boolean.valueOf(true);
/*      */       }
/*  728 */       if ((dto.getPrimaryR().intValue() == 1 && dto.getPrimaryR().intValue() == 1 && dto.getPrimaryR().intValue() == 1) || (dto
/*  729 */         .getSecondaryR().intValue() == 1 && dto.getSecondaryS().intValue() == 1 && dto.getSecondaryT().intValue() == 1)) {
/*  730 */         secondaryStatus = Boolean.valueOf(true);
/*      */       }
/*  732 */       if ((dto.getLoop1Status() != null && dto.getLoop1Status().intValue() == 1) || (dto
/*  733 */         .getLoop2Status() != null && dto.getLoop2Status().intValue() == 1) || (dto
/*  734 */         .getLoop3Status() != null && dto.getLoop3Status().intValue() == 1) || (dto
/*  735 */         .getLoop4Status() != null && dto.getLoop4Status().intValue() == 1) || (dto
/*  736 */         .getLoop5Status() != null && dto.getLoop5Status().intValue() == 1)) {
/*  737 */         loopStatus = Boolean.valueOf(true);
/*      */       }
/*      */     } 
/*  740 */     list.add(primaryStatus);
/*  741 */     list.add(secondaryStatus);
/*  742 */     list.add(loopStatus);
/*  743 */     return list;
/*      */   }
/*      */   
/*      */   public void fillRoadLine(List<RoadLineDTO> list) {
/*  747 */     this.roadLineStore.clear();
/*  748 */     RoadLineDTO allDto = new RoadLineDTO();
/*  749 */     allDto.setLineId("all");
/*  750 */     allDto.setLineName(messages.pdStatus_comboBox_all());
/*  751 */     list.add(0, allDto);
/*  752 */     this.roadLineStore.addAll(list);
/*  753 */     this.roadLineCB.setValue(this.roadLineStore.get(0));
/*      */   }
/*      */   
/*      */   public void fillPdStatus(List<PdStatusDTO> list) {
/*  757 */     this.listStore.clear();
/*  758 */     this.listStore.addAll(list);
/*  759 */     if (!this.selectedDevice.equals("")) {
/*  760 */       this.grid.getSelectionModel().select(true, (Object[])new PdStatusDTO[] { (PdStatusDTO)this.listStore.findModelWithKey(this.selectedDevice) });
/*  761 */       fillSvg((PdStatusDTO)this.grid.getSelectionModel().getSelectedItem());
/*      */     } 
/*      */   }
/*      */   
/*      */   public PdStatusDTO getPdStatusFromDeviceName(String deviceName) {
/*  766 */     PdStatusDTO result = null;
/*  767 */     for (PdStatusDTO dto : this.listStore.getAll()) {
/*  768 */       if (deviceName != null && deviceName.equals(dto.getPdId())) {
/*  769 */         result = dto;
/*      */       }
/*      */     } 
/*  772 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public void fillSvg(PdStatusDTO dto) {
/*  777 */     clearSVGComponent();
/*      */     
/*  779 */     SVGResource resource = AllImages.INSTANCE.pd();
/*  780 */     if (resource == null) {
/*  781 */       GWT.log("Fill svg failed, can not get resource.");
/*      */       return;
/*      */     } 
/*  784 */     OMSVGSVGElement svgElement = resource.getSvg();
/*      */     
/*  786 */     this.svg.setViewBox(0.0F, 0.0F, svgElement
/*      */ 
/*      */         
/*  789 */         .getWidth().getBaseVal().getValue(), svgElement
/*  790 */         .getHeight().getBaseVal().getValue());
/*      */     
/*  792 */     Boolean check = Boolean.valueOf((dto.getConnectivity().intValue() == 1));
/*  793 */     OMElement primaryRElement = svgElement.getElementById("primaryR");
/*  794 */     primaryRElement.setAttribute("style", 
/*  795 */         setSvgColor(primaryRElement.getAttribute("style"), check, dto.getPrimaryR()));
/*      */     
/*  797 */     OMElement primarySElement = svgElement.getElementById("primaryS");
/*  798 */     primarySElement.setAttribute("style", 
/*  799 */         setSvgColor(primarySElement.getAttribute("style"), check, dto.getPrimaryS()));
/*      */     
/*  801 */     OMElement primaryTElement = svgElement.getElementById("primaryT");
/*  802 */     primaryTElement.setAttribute("style", 
/*  803 */         setSvgColor(primaryTElement.getAttribute("style"), check, dto.getPrimaryT()));
/*      */     
/*  805 */     OMElement secondaryRElement = svgElement.getElementById("secondaryR");
/*  806 */     secondaryRElement.setAttribute("style", 
/*  807 */         setSvgColor(secondaryRElement.getAttribute("style"), check, dto.getSecondaryR()));
/*      */     
/*  809 */     OMElement secondarySElement = svgElement.getElementById("secondaryS");
/*  810 */     secondarySElement.setAttribute("style", 
/*  811 */         setSvgColor(secondarySElement.getAttribute("style"), check, dto.getSecondaryS()));
/*      */     
/*  813 */     OMElement secondaryTElement = svgElement.getElementById("secondaryT");
/*  814 */     secondaryTElement.setAttribute("style", 
/*  815 */         setSvgColor(secondaryTElement.getAttribute("style"), check, dto.getSecondaryT()));
/*      */     
/*  817 */     OMElement loop1Element = svgElement.getElementById("loop1Status");
/*  818 */     if (dto.getLoop1Status() != null) {
/*  819 */       loop1Element.setAttribute("style", 
/*  820 */           setSvgColor(loop1Element.getAttribute("style"), check, dto.getLoop1Status()));
/*      */     } else {
/*  822 */       List<String> styleList = Arrays.asList(loop1Element.getAttribute("style").split(";"));
/*  823 */       styleList.set(1, "fill:#666666");
/*  824 */       loop1Element.setAttribute("style", String.join(";", (Iterable)styleList));
/*      */       
/*  826 */       OMElement loop1DevcieElement = svgElement.getElementById("loop1");
/*      */       
/*  828 */       List<String> devcieStyleList = Arrays.asList(loop1DevcieElement.getAttribute("style").split(";"));
/*  829 */       devcieStyleList.set(1, "fill:#666666");
/*  830 */       devcieStyleList.set(2, "fill-opacity:0.8");
/*  831 */       loop1DevcieElement.setAttribute("style", String.join(";", (Iterable)devcieStyleList));
/*      */     } 
/*      */     
/*  834 */     OMElement loop2Element = svgElement.getElementById("loop2Status");
/*  835 */     if (dto.getLoop2Status() != null) {
/*  836 */       loop2Element.setAttribute("style", 
/*  837 */           setSvgColor(loop2Element.getAttribute("style"), check, dto.getLoop2Status()));
/*      */     } else {
/*  839 */       List<String> styleList = Arrays.asList(loop2Element.getAttribute("style").split(";"));
/*  840 */       styleList.set(1, "fill:#666666");
/*  841 */       loop2Element.setAttribute("style", String.join(";", (Iterable)styleList));
/*      */       
/*  843 */       OMElement loop2DevcieElement = svgElement.getElementById("loop2");
/*      */       
/*  845 */       List<String> devcieStyleList = Arrays.asList(loop2DevcieElement.getAttribute("style").split(";"));
/*  846 */       devcieStyleList.set(1, "fill:#666666");
/*  847 */       devcieStyleList.set(2, "fill-opacity:0.8");
/*  848 */       loop2DevcieElement.setAttribute("style", String.join(";", (Iterable)devcieStyleList));
/*      */     } 
/*      */     
/*  851 */     OMElement loop3Element = svgElement.getElementById("loop3Status");
/*  852 */     if (dto.getLoop3Status() != null) {
/*  853 */       loop3Element.setAttribute("style", 
/*  854 */           setSvgColor(loop3Element.getAttribute("style"), check, dto.getLoop3Status()));
/*      */     } else {
/*  856 */       List<String> styleList = Arrays.asList(loop3Element.getAttribute("style").split(";"));
/*  857 */       styleList.set(1, "fill:#666666");
/*  858 */       loop3Element.setAttribute("style", String.join(";", (Iterable)styleList));
/*      */       
/*  860 */       OMElement loop3DevcieElement = svgElement.getElementById("loop3");
/*      */       
/*  862 */       List<String> devcieStyleList = Arrays.asList(loop3DevcieElement.getAttribute("style").split(";"));
/*  863 */       devcieStyleList.set(1, "fill:#666666");
/*  864 */       devcieStyleList.set(2, "fill-opacity:0.8");
/*  865 */       loop3DevcieElement.setAttribute("style", String.join(";", (Iterable)devcieStyleList));
/*      */     } 
/*      */     
/*  868 */     OMElement loop4Element = svgElement.getElementById("loop4Status");
/*  869 */     if (dto.getLoop4Status() != null) {
/*  870 */       loop4Element.setAttribute("style", 
/*  871 */           setSvgColor(loop4Element.getAttribute("style"), check, dto.getLoop4Status()));
/*      */     } else {
/*  873 */       List<String> styleList = Arrays.asList(loop4Element.getAttribute("style").split(";"));
/*  874 */       styleList.set(1, "fill:#666666");
/*  875 */       loop4Element.setAttribute("style", String.join(";", (Iterable)styleList));
/*      */       
/*  877 */       OMElement loop4DevcieElement = svgElement.getElementById("loop4");
/*      */       
/*  879 */       List<String> devcieStyleList = Arrays.asList(loop4DevcieElement.getAttribute("style").split(";"));
/*  880 */       devcieStyleList.set(1, "fill:#666666");
/*  881 */       devcieStyleList.set(2, "fill-opacity:0.8");
/*  882 */       loop4DevcieElement.setAttribute("style", String.join(";", (Iterable)devcieStyleList));
/*      */     } 
/*      */     
/*  885 */     OMElement loop5Element = svgElement.getElementById("loop5Status");
/*  886 */     if (dto.getLoop5Status() != null) {
/*  887 */       loop5Element.setAttribute("style", 
/*  888 */           setSvgColor(loop5Element.getAttribute("style"), check, dto.getLoop5Status()));
/*      */     } else {
/*  890 */       List<String> styleList = Arrays.asList(loop5Element.getAttribute("style").split(";"));
/*  891 */       styleList.set(1, "fill:#666666");
/*  892 */       loop5Element.setAttribute("style", String.join(";", (Iterable)styleList));
/*      */       
/*  894 */       OMElement loop5DevcieElement = svgElement.getElementById("loop5");
/*      */       
/*  896 */       List<String> devcieStyleList = Arrays.asList(loop5DevcieElement.getAttribute("style").split(";"));
/*  897 */       devcieStyleList.set(1, "fill:#666666");
/*  898 */       devcieStyleList.set(2, "fill-opacity:0.8");
/*  899 */       loop5DevcieElement.setAttribute("style", String.join(";", (Iterable)devcieStyleList));
/*      */     } 
/*  901 */     this.elements = new ArrayList<>();
/*  902 */     this.elements.add(loop1Element);
/*  903 */     this.elements.add(loop2Element);
/*  904 */     this.elements.add(loop3Element);
/*  905 */     this.elements.add(loop4Element);
/*  906 */     this.elements.add(loop5Element);
/*      */     
/*  908 */     List<String> deviceNames = new ArrayList<>();
/*  909 */     deviceNames.add(dto.getPdId());
/*  910 */     this.presenter.retrieveLoopDeviceConfig(deviceNames, dto.getPdDisplayName());
/*      */     
/*  912 */     this.svg.appendChild((OMNode)svgElement);
/*  913 */     this.div.appendChild((Node)this.svg.getElement());
/*      */   }
/*      */   
/*      */   public String setSvgColor(String style, Boolean check, Integer status) {
/*  917 */     List<String> styleList = Arrays.asList(style.split(";"));
/*      */     
/*  919 */     if (check.booleanValue() == true) {
/*  920 */       styleList.set(1, "fill:#666666");
/*      */     }
/*  922 */     else if (status.equals(Integer.valueOf(0))) {
/*  923 */       styleList.set(1, "fill:#00ff00");
/*      */     } else {
/*  925 */       styleList.set(1, "fill:#ff0000");
/*      */     } 
/*      */     
/*  928 */     return String.join(";", (Iterable)styleList);
/*      */   }
/*      */   
/*      */   private void clearSVGComponent() {
/*  932 */     this.div.removeAllChildren();
/*  933 */     this.svg = this.doc.createSVGSVGElement();
/*      */   }
/*      */ 
/*      */   
/*      */   public void fillLoopDevice(List<LoopDeviceConfigDTO> result, String displayName) {
/*  938 */     this.underPDsMap = new HashMap<>();
/*  939 */     for (LoopDeviceConfigDTO loop : result) {
/*      */       
/*  941 */       if (!loop.getCheck().booleanValue()) {
/*  942 */         List<String> deviceL1 = new ArrayList<>();
/*  943 */         List<String> deviceL2 = new ArrayList<>();
/*  944 */         List<String> deviceL3 = new ArrayList<>();
/*  945 */         List<String> deviceL4 = new ArrayList<>();
/*  946 */         List<String> deviceL5 = new ArrayList<>();
/*  947 */         for (LoopDeviceConfigDTO loopC1 : loop.getChildren()) {
/*      */           
/*  949 */           if (!loopC1.getCheck().booleanValue() && loopC1.getChildren() != null) {
/*  950 */             for (LoopDeviceConfigDTO loopC2 : loopC1.getChildren()) {
/*  951 */               if (loopC2.getCheck().booleanValue()) {
/*  952 */                 if (loopC2.getLoopNo().intValue() == 1) {
/*  953 */                   deviceL1.add(loopC2.getDisplayName()); continue;
/*  954 */                 }  if (loopC2.getLoopNo().intValue() == 2) {
/*  955 */                   deviceL2.add(loopC2.getDisplayName()); continue;
/*  956 */                 }  if (loopC2.getLoopNo().intValue() == 3) {
/*  957 */                   deviceL3.add(loopC2.getDisplayName()); continue;
/*  958 */                 }  if (loopC2.getLoopNo().intValue() == 4) {
/*  959 */                   deviceL4.add(loopC2.getDisplayName()); continue;
/*  960 */                 }  if (loopC2.getLoopNo().intValue() == 5) {
/*  961 */                   deviceL5.add(loopC2.getDisplayName());
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           }
/*      */         } 
/*  967 */         if (deviceL1.size() > 0) {
/*  968 */           this.underPDsMap.put(Integer.valueOf(1), deviceL1);
/*      */         }
/*  970 */         if (deviceL2.size() > 0) {
/*  971 */           this.underPDsMap.put(Integer.valueOf(2), deviceL2);
/*      */         }
/*  973 */         if (deviceL3.size() > 0) {
/*  974 */           this.underPDsMap.put(Integer.valueOf(3), deviceL3);
/*      */         }
/*  976 */         if (deviceL4.size() > 0) {
/*  977 */           this.underPDsMap.put(Integer.valueOf(4), deviceL4);
/*      */         }
/*  979 */         if (deviceL5.size() > 0) {
/*  980 */           this.underPDsMap.put(Integer.valueOf(5), deviceL5);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  985 */     if (!this.underPDsMap.isEmpty()) {
/*  986 */       if (this.underPDsMap.containsKey(Integer.valueOf(1)) && ((List)this.underPDsMap.get(Integer.valueOf(1))).size() > 0) {
/*  987 */         showUnderPdClick(this.elements.get(0), displayName, Integer.valueOf(1));
/*      */       }
/*  989 */       if (this.underPDsMap.containsKey(Integer.valueOf(2)) && ((List)this.underPDsMap.get(Integer.valueOf(2))).size() > 0) {
/*  990 */         showUnderPdClick(this.elements.get(1), displayName, Integer.valueOf(2));
/*      */       }
/*  992 */       if (this.underPDsMap.containsKey(Integer.valueOf(3)) && ((List)this.underPDsMap.get(Integer.valueOf(3))).size() > 0) {
/*  993 */         showUnderPdClick(this.elements.get(2), displayName, Integer.valueOf(3));
/*      */       }
/*  995 */       if (this.underPDsMap.containsKey(Integer.valueOf(4)) && ((List)this.underPDsMap.get(Integer.valueOf(4))).size() > 0) {
/*  996 */         showUnderPdClick(this.elements.get(3), displayName, Integer.valueOf(4));
/*      */       }
/*  998 */       if (this.underPDsMap.containsKey(Integer.valueOf(5)) && ((List)this.underPDsMap.get(Integer.valueOf(5))).size() > 0) {
/*  999 */         showUnderPdClick(this.elements.get(4), displayName, Integer.valueOf(5));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void showUnderPdDialog(String pdDisplayName, String loopStirng, String underPD) {
/* 1005 */     if (this.underPdDialog == null) {
/* 1006 */       initUnderPdDialog(pdDisplayName, loopStirng, underPD);
/*      */     }
/* 1008 */     this.underPdDialog.show();
/*      */   }
/*      */   
/*      */   private void initUnderPdDialog(String pdDisplayName, String loopStirng, String underPD) {
/* 1012 */     this.underPdDialog = new Dialog();
/* 1013 */     this.underPdDialog.setHeading(pdDisplayName + "迴路" + loopStirng + " :底下設備列表");
/* 1014 */     this.underPdDialog.setModal(true);
/* 1015 */     this.underPdDialog.setWidth(250);
/* 1016 */     this.underPdDialog.setHeight(450);
/* 1017 */     Label label = new Label();
/* 1018 */     StringBuilder message = new StringBuilder();
/* 1019 */     if (underPD != null && !underPD.isEmpty()) {
/* 1020 */       label.setHeight("1");
/* 1021 */       label.setWidth("1");
/* 1022 */       message.append(underPD);
/* 1023 */       label.setText(message.toString());
/* 1024 */       this.underPdDialog.add((Widget)label);
/*      */     } 
/* 1026 */     TextButton confirmButton = new TextButton();
/* 1027 */     confirmButton.setText("確認");
/* 1028 */     confirmButton.addSelectHandler(new SelectEvent.SelectHandler()
/*      */         {
/*      */           public void onSelect(SelectEvent event)
/*      */           {
/* 1032 */             PdStatusViewer.this.underPdDialog.hide();
/*      */           }
/*      */         });
/*      */     
/* 1036 */     this.underPdDialog.getButtonBar().clear();
/* 1037 */     this.underPdDialog.getButtonBar().add((Widget)confirmButton);
/*      */     
/* 1039 */     this.underPdDialog.addHideHandler(new HideEvent.HideHandler()
/*      */         {
/*      */           public void onHide(HideEvent event)
/*      */           {
/* 1043 */             PdStatusViewer.this.underPdDialog = null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public void showUnderPdClick(OMElement element, final String pdDisplayName, final Integer loopNum) {
/* 1050 */     element.addDomHandler((EventHandler)new MouseDownHandler()
/*      */         {
/*      */ 
/*      */           
/*      */           public void onMouseDown(MouseDownEvent event)
/*      */           {
/* 1056 */             PdStatusViewer.this.showUnderPdDialog(pdDisplayName, loopNum
/* 1057 */                 .toString(), String.join("\r\n", (Iterable<? extends CharSequence>)PdStatusViewer.this.underPDsMap.get(loopNum)));
/*      */           }
/* 1060 */         }MouseDownEvent.getType());
/*      */   }
/*      */   
/*      */   static interface PdStatusPropertyAccess extends PropertyAccess<PdStatusDTO> {
/*      */     ModelKeyProvider<PdStatusDTO> pdId();
/*      */     
/*      */     ValueProvider<PdStatusDTO, String> pdDisplayName();
/*      */     
/*      */     ValueProvider<PdStatusDTO, Integer> primaryR();
/*      */     
/*      */     ValueProvider<PdStatusDTO, Integer> primaryS();
/*      */     
/*      */     ValueProvider<PdStatusDTO, Integer> primaryT();
/*      */     
/*      */     ValueProvider<PdStatusDTO, Integer> secondaryR();
/*      */     
/*      */     ValueProvider<PdStatusDTO, Integer> secondaryS();
/*      */     
/*      */     ValueProvider<PdStatusDTO, Integer> secondaryT();
/*      */     
/*      */     ValueProvider<PdStatusDTO, Integer> connectivity();
/*      */     
/*      */     ValueProvider<PdStatusDTO, Integer> doorOpen();
/*      */     
/*      */     ValueProvider<PdStatusDTO, Integer> loop1Status();
/*      */     
/*      */     ValueProvider<PdStatusDTO, Integer> loop2Status();
/*      */     
/*      */     ValueProvider<PdStatusDTO, Integer> loop3Status();
/*      */     
/*      */     ValueProvider<PdStatusDTO, Integer> loop4Status();
/*      */     
/*      */     ValueProvider<PdStatusDTO, Integer> loop5Status();
/*      */   }
/*      */   
/*      */   static interface RoadLineProertyAccess extends PropertyAccess<RoadLineDTO> {
/*      */     ModelKeyProvider<RoadLineDTO> lineId();
/*      */     
/*      */     LabelProvider<RoadLineDTO> lineName();
/*      */   }
/*      */   
/*      */   static interface PdStatusViewerUiBinder extends UiBinder<Widget, PdStatusViewer> {}
/*      */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\PdStatusViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */