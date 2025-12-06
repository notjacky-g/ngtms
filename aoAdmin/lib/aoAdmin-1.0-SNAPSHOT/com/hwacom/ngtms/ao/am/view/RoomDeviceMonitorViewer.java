/*      */ package com.hwacom.ngtms.ao.am.view;
/*      */ 
/*      */ import com.google.gwt.cell.client.AbstractCell;
/*      */ import com.google.gwt.cell.client.Cell;
/*      */ import com.google.gwt.core.client.GWT;
/*      */ import com.google.gwt.dom.client.Element;
/*      */ import com.google.gwt.dom.client.Node;
/*      */ import com.google.gwt.event.dom.client.ClickEvent;
/*      */ import com.google.gwt.event.dom.client.ClickHandler;
/*      */ import com.google.gwt.event.dom.client.ContextMenuEvent;
/*      */ import com.google.gwt.event.dom.client.ContextMenuHandler;
/*      */ import com.google.gwt.event.logical.shared.SelectionEvent;
/*      */ import com.google.gwt.event.logical.shared.SelectionHandler;
/*      */ import com.google.gwt.event.shared.EventHandler;
/*      */ import com.google.gwt.event.shared.GwtEvent;
/*      */ import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
/*      */ import com.google.gwt.uibinder.client.UiBinder;
/*      */ import com.google.gwt.uibinder.client.UiField;
/*      */ import com.google.gwt.uibinder.client.UiHandler;
/*      */ import com.google.gwt.user.client.Timer;
/*      */ import com.google.gwt.user.client.ui.Image;
/*      */ import com.google.gwt.user.client.ui.Widget;
/*      */ import com.hwacom.ngtms.ao.am.images.room.roomDevice.RoomDeviceImages;
/*      */ import com.hwacom.ngtms.ao.am.images.room.roomStatus.RoomStatusImages;
/*      */ import com.hwacom.ngtms.ao.am.presenter.RoomDeviceMonitorPresenter;
/*      */ import com.hwacom.ngtms.ao.am.util.StringConverter;
/*      */ import com.hwacom.ngtms.ao.shared.dto.AlarmMessageDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomDeviceParamDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomGroupDeviceStatusDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomInfoDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomNCUStatusDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomStaffPeopleDTO;
/*      */ import com.hwacom.ngtms.c.shared.dto.DeviceSvgPositionConfigDTO;
/*      */ import com.hwacom.ngtms.cam.client.ui.AmTab;
/*      */ import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
/*      */ import com.hwacom.ngtms.room.am.event.RoomDeviceConfigEvent;
/*      */ import com.hwacom.ngtms.room.shared.dto.RoomCctvUrlDTO;
/*      */ import com.hwacom.ngtms.room.shared.dto.RoomDeviceStatusDTO;
/*      */ import com.hwacom.ngtms.room.shared.dto.RoomDeviceSubLocationConfigDTO;
/*      */ import com.hwacom.ngtms.room.shared.dto.RoomDoDTO;
/*      */ import com.sencha.gxt.core.client.Style;
/*      */ import com.sencha.gxt.core.client.ValueProvider;
/*      */ import com.sencha.gxt.core.client.dom.XElement;
/*      */ import com.sencha.gxt.data.shared.LabelProvider;
/*      */ import com.sencha.gxt.data.shared.ListStore;
/*      */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*      */ import com.sencha.gxt.data.shared.PropertyAccess;
/*      */ import com.sencha.gxt.data.shared.SortDir;
/*      */ import com.sencha.gxt.data.shared.Store;
/*      */ import com.sencha.gxt.data.shared.TreeStore;
/*      */ import com.sencha.gxt.widget.core.client.ContentPanel;
/*      */ import com.sencha.gxt.widget.core.client.Dialog;
/*      */ import com.sencha.gxt.widget.core.client.button.TextButton;
/*      */ import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
/*      */ import com.sencha.gxt.widget.core.client.event.SelectEvent;
/*      */ import com.sencha.gxt.widget.core.client.event.ShowContextMenuEvent;
/*      */ import com.sencha.gxt.widget.core.client.form.ComboBox;
/*      */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*      */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*      */ import com.sencha.gxt.widget.core.client.grid.Grid;
/*      */ import com.sencha.gxt.widget.core.client.info.Info;
/*      */ import com.sencha.gxt.widget.core.client.menu.Item;
/*      */ import com.sencha.gxt.widget.core.client.menu.Menu;
/*      */ import com.sencha.gxt.widget.core.client.menu.MenuItem;
/*      */ import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
/*      */ import com.sencha.gxt.widget.core.client.tree.Tree;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.vectomatic.dom.svg.OMElement;
/*      */ import org.vectomatic.dom.svg.OMNode;
/*      */ import org.vectomatic.dom.svg.OMSVGDocument;
/*      */ import org.vectomatic.dom.svg.OMSVGSVGElement;
/*      */ import org.vectomatic.dom.svg.ui.SVGResource;
/*      */ import org.vectomatic.dom.svg.utils.OMSVGParser;
/*      */ 
/*      */ 
/*      */ public class RoomDeviceMonitorViewer
/*      */   extends AmTab
/*      */ {
/*   85 */   private static RoomDeviceMonitorViewerUiBinder uiBinder = (RoomDeviceMonitorViewerUiBinder)GWT.create(RoomDeviceMonitorViewerUiBinder.class);
/*      */ 
/*      */ 
/*      */   
/*   89 */   private static GridProperties props = (GridProperties)GWT.create(GridProperties.class);
/*      */   
/*   91 */   private static GroupGridProperties groupProps = (GroupGridProperties)GWT.create(GroupGridProperties.class);
/*      */   
/*   93 */   private RoomDeviceMonitorPresenter presenter = new RoomDeviceMonitorPresenter(this);
/*      */   
/*   95 */   private final ClientFactory clientFactory = (ClientFactory)GWT.create(ClientFactory.class);
/*      */   
/*   97 */   private Map<String, SVGResource> roomMap = new HashMap<>();
/*      */   
/*   99 */   private Set<String> svgDeviceNameList = new HashSet<>();
/*      */   
/*  101 */   private OMSVGDocument doc = OMSVGParser.currentDocument();
/*      */   
/*  103 */   private OMSVGSVGElement svg = this.doc.createSVGSVGElement();
/*  104 */   private Element div = null;
/*  105 */   private final String STYLE = "style";
/*      */   
/*  107 */   private String colorRed = "#FF3333";
/*      */   
/*  109 */   private String colorGreen = "#2bb92b";
/*      */   
/*  111 */   private String colorYellow = "#d2d20e";
/*      */   
/*  113 */   private String colorGrey = "#D0D0D0";
/*      */   
/*  115 */   private Timer timer = null;
/*      */   
/*  117 */   private final int SECOND = 5000;
/*      */   
/*  119 */   private Timer safeTimer = null;
/*      */   
/*  121 */   private final int SAFESECOND = 5000;
/*      */   
/*      */   private String selectedSubLocationId;
/*      */   
/*  125 */   private Menu openMenu = new Menu();
/*      */   
/*  127 */   private MenuItem open = new MenuItem();
/*      */   
/*  129 */   private Menu settingDIMenu = new Menu();
/*      */   
/*  131 */   private MenuItem setting1 = new MenuItem();
/*  132 */   private MenuItem setting2 = new MenuItem();
/*  133 */   private MenuItem setting3 = new MenuItem();
/*      */   
/*  135 */   private Menu settingDOMenu = new Menu();
/*      */   
/*  137 */   private MenuItem settingDO = new MenuItem();
/*      */   
/*  139 */   private Menu analogRecordMenu = new Menu();
/*      */   
/*  141 */   private MenuItem analogRecord = new MenuItem();
/*      */   
/*      */   private Dialog doorDialog;
/*      */   
/*      */   private Dialog alarmToneDialog;
/*      */   
/*      */   private Dialog safeDialog;
/*      */   
/*      */   private Dialog groupDialog;
/*      */   
/*      */   private String openedDevice;
/*      */   
/*      */   private String settedDevice;
/*      */   
/*      */   private String analogDevice;
/*      */   
/*      */   private String alarmToneDevice;
/*      */   
/*  159 */   private RoomInfoVO root = new RoomInfoVO("root", "國道機房");
/*      */   
/*  161 */   private List<String> lineNames = new ArrayList<>();
/*      */   
/*  163 */   private List<RoomInfoVO> notRoomList = new ArrayList<>();
/*      */   
/*  165 */   private Menu roomMenu = new Menu();
/*      */   
/*  167 */   private MenuItem roomHome = new MenuItem();
/*      */   
/*  169 */   private MenuItem roomAlarmOpen = new MenuItem();
/*      */   
/*      */   private Grid<RoomGroupDeviceStatusDTO> groupGrid;
/*      */   
/*      */   @UiField
/*      */   Image frame;
/*      */   
/*      */   @UiField
/*      */   ContentPanel svgContainer;
/*      */   
/*      */   @UiField
/*      */   ContentPanel aboveSvgContainer;
/*      */   
/*      */   @UiField(provided = true)
/*      */   TreeStore<RoomInfoVO> treeStore;
/*      */   
/*      */   @UiField(provided = true)
/*      */   ValueProvider<RoomInfoVO, String> treeValueProvider;
/*      */   
/*      */   @UiField
/*      */   Tree<RoomInfoVO, String> tree;
/*      */   @UiField
/*      */   VerticalLayoutContainer roomCardSafeContainer;
/*      */   @UiField(provided = true)
/*      */   ListStore<RoomCctvUrlDTO> deviceStore;
/*      */   @UiField(provided = true)
/*      */   LabelProvider<RoomCctvUrlDTO> deviceProvider;
/*      */   @UiField
/*      */   ComboBox<RoomCctvUrlDTO> deviceCB;
/*      */   @UiField
/*      */   ContentPanel framePanel;
/*      */   @UiField
/*      */   AlarmMonitorGrid alarmMonitorGrid;
/*      */   RoomStaffPeopleWidget roomStaffPeopleWidget;
/*      */   
/*      */   public RoomDeviceMonitorViewer() {
/*  205 */     this.deviceStore = new ListStore(new ModelKeyProvider<RoomCctvUrlDTO>()
/*      */         {
/*      */           
/*      */           public String getKey(RoomCctvUrlDTO item)
/*      */           {
/*  210 */             return item.getDeviceName();
/*      */           }
/*      */         });
/*      */     
/*  214 */     this.deviceProvider = new LabelProvider<RoomCctvUrlDTO>()
/*      */       {
/*      */         public String getLabel(RoomCctvUrlDTO item)
/*      */         {
/*  218 */           return item.getDisplayName();
/*      */         }
/*      */       };
/*      */     
/*  222 */     this.treeStore = new TreeStore(new ModelKeyProvider<RoomInfoVO>()
/*      */         {
/*      */ 
/*      */           
/*      */           public String getKey(RoomDeviceMonitorViewer.RoomInfoVO item)
/*      */           {
/*  228 */             return item.getId();
/*      */           }
/*      */         });
/*      */     
/*  232 */     this.treeValueProvider = new ValueProvider<RoomInfoVO, String>()
/*      */       {
/*      */         public void setValue(RoomDeviceMonitorViewer.RoomInfoVO object, String value) {}
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public String getValue(RoomDeviceMonitorViewer.RoomInfoVO object) {
/*  240 */           return object.getName();
/*      */         }
/*      */ 
/*      */         
/*      */         public String getPath() {
/*  245 */           return "treeValueProvider";
/*      */         }
/*      */       };
/*  248 */     initTree();
/*  249 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*  250 */     initMenu();
/*  251 */     this.tree.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
/*  252 */     this.div = (Element)this.svgContainer.getElement();
/*  253 */     this.svgContainer.addDomHandler((EventHandler)new ContextMenuHandler()
/*      */         {
/*      */           
/*      */           public void onContextMenu(ContextMenuEvent event)
/*      */           {
/*  258 */             event.preventDefault();
/*      */           }
/*      */         }, 
/*  261 */         ContextMenuEvent.getType());
/*  262 */     addEventHandler();
/*      */   }
/*      */   
/*      */   private void initTree() {
/*  266 */     this.treeStore.clear();
/*  267 */     this.treeStore.add(this.root);
/*  268 */     this.presenter.getRoomLineData();
/*      */   }
/*      */   
/*      */   private void initMenu() {
/*  272 */     this.setting1.setText("屬性設定");
/*  273 */     this.setting1.addSelectionHandler(new SelectionHandler<Item>()
/*      */         {
/*      */           
/*      */           public void onSelection(SelectionEvent<Item> event)
/*      */           {
/*  278 */             RoomDeviceMonitorViewer.this.clientFactory
/*  279 */               .getEventBus()
/*  280 */               .fireEventFromSource((GwtEvent)new RoomDeviceConfigEvent(RoomDeviceConfigEvent.Action.CLICK), RoomDeviceMonitorViewer.this
/*  281 */                 .settedDevice);
/*      */           }
/*      */         });
/*      */     
/*  285 */     this.setting2.setText("屬性設定");
/*  286 */     this.setting2.addSelectionHandler(new SelectionHandler<Item>()
/*      */         {
/*      */           
/*      */           public void onSelection(SelectionEvent<Item> event)
/*      */           {
/*  291 */             RoomDeviceMonitorViewer.this.clientFactory
/*  292 */               .getEventBus()
/*  293 */               .fireEventFromSource((GwtEvent)new RoomDeviceConfigEvent(RoomDeviceConfigEvent.Action.CLICK), RoomDeviceMonitorViewer.this
/*  294 */                 .analogDevice);
/*      */           }
/*      */         });
/*      */     
/*  298 */     this.setting3.setText("屬性設定");
/*  299 */     this.setting3.addSelectionHandler(new SelectionHandler<Item>()
/*      */         {
/*      */           
/*      */           public void onSelection(SelectionEvent<Item> event)
/*      */           {
/*  304 */             RoomDeviceMonitorViewer.this.clientFactory
/*  305 */               .getEventBus()
/*  306 */               .fireEventFromSource((GwtEvent)new RoomDeviceConfigEvent(RoomDeviceConfigEvent.Action.CLICK), RoomDeviceMonitorViewer.this
/*  307 */                 .settedDevice);
/*      */           }
/*      */         });
/*      */     
/*  311 */     this.analogRecord.setText("趨勢圖");
/*  312 */     this.analogRecord.addSelectionHandler(new SelectionHandler<Item>()
/*      */         {
/*      */           
/*      */           public void onSelection(SelectionEvent<Item> event)
/*      */           {
/*  317 */             RoomDeviceMonitorViewer.this.clientFactory
/*  318 */               .getEventBus()
/*  319 */               .fireEventFromSource((GwtEvent)new RoomDeviceConfigEvent(RoomDeviceConfigEvent.Action.ANALOG_RECORD), RoomDeviceMonitorViewer.this
/*      */                 
/*  321 */                 .analogDevice);
/*      */           }
/*      */         });
/*      */     
/*  325 */     this.settingDO.setText("設備開關");
/*  326 */     this.settingDO.addSelectionHandler(new SelectionHandler<Item>()
/*      */         {
/*      */           public void onSelection(SelectionEvent<Item> event) {}
/*      */         });
/*      */ 
/*      */ 
/*      */     
/*  333 */     this.open.setText("遠端開門");
/*  334 */     this.open.addSelectionHandler(new SelectionHandler<Item>()
/*      */         {
/*      */           
/*      */           public void onSelection(SelectionEvent<Item> event)
/*      */           {
/*  339 */             RoomDeviceMonitorViewer.this.showDoorDialog();
/*      */           }
/*      */         });
/*      */     
/*  343 */     this.roomHome.setText("遠端保全控制");
/*  344 */     this.roomHome.addSelectionHandler(new SelectionHandler<Item>()
/*      */         {
/*      */           
/*      */           public void onSelection(SelectionEvent<Item> event)
/*      */           {
/*  349 */             if (RoomDeviceMonitorViewer.this.alarmToneDevice != null) {
/*  350 */               RoomDeviceMonitorViewer.this.showSafeDialog();
/*      */             }
/*      */           }
/*      */         });
/*      */     
/*  355 */     this.roomAlarmOpen.setText("遠端警報音控制");
/*  356 */     this.roomAlarmOpen.addSelectionHandler(new SelectionHandler<Item>()
/*      */         {
/*      */           
/*      */           public void onSelection(SelectionEvent<Item> event)
/*      */           {
/*  361 */             if (RoomDeviceMonitorViewer.this.alarmToneDevice != null) {
/*  362 */               RoomDeviceMonitorViewer.this.showAlarmToneDialog();
/*      */             }
/*      */           }
/*      */         });
/*  366 */     this.settingDIMenu.add((Widget)this.setting1);
/*  367 */     this.analogRecordMenu.add((Widget)this.setting2);
/*  368 */     this.analogRecordMenu.add((Widget)this.analogRecord);
/*  369 */     this.settingDOMenu.add((Widget)this.setting3);
/*  370 */     this.settingDOMenu.add((Widget)this.settingDO);
/*  371 */     this.openMenu.add((Widget)this.open);
/*  372 */     this.roomMenu.add((Widget)this.roomHome);
/*  373 */     this.roomMenu.add((Widget)this.roomAlarmOpen);
/*      */   }
/*      */   
/*      */   public void setAlarmMonitorGrid(List<AlarmMessageDTO> alarms) {
/*  377 */     this.alarmMonitorGrid.setListStore(alarms);
/*      */   }
/*      */   
/*      */   private void addEventHandler() {
/*  381 */     this.tree.getSelectionModel()
/*  382 */       .addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<RoomInfoVO>()
/*      */         {
/*      */           
/*      */           public void onSelectionChanged(SelectionChangedEvent<RoomDeviceMonitorViewer.RoomInfoVO> event)
/*      */           {
/*  387 */             RoomDeviceMonitorViewer.this.notRoomList.addAll(RoomDeviceMonitorViewer.this.treeStore.getChildren(RoomDeviceMonitorViewer.this.root));
/*  388 */             RoomDeviceMonitorViewer.this.notRoomList.add(RoomDeviceMonitorViewer.this.root);
/*  389 */             RoomDeviceMonitorViewer.RoomInfoVO selectedItem = (RoomDeviceMonitorViewer.RoomInfoVO)event.getSource().getSelectedItem();
/*  390 */             if (!RoomDeviceMonitorViewer.this.notRoomList.contains(selectedItem)) {
/*  391 */               RoomDeviceMonitorViewer.this.roomCardSafeContainer.clear();
/*  392 */               RoomDeviceMonitorViewer.this.aboveSvgContainer.setHeading(selectedItem.getName());
/*  393 */               RoomDeviceMonitorViewer.this.selectedSubLocationId = selectedItem.getId();
/*      */               
/*  395 */               RoomDeviceMonitorViewer.this.presenter.getRoomCctvVideo(RoomDeviceMonitorViewer.this.selectedSubLocationId);
/*      */               
/*  397 */               RoomDeviceMonitorViewer.this.fillRoomSvg(RoomDeviceMonitorViewer.this.selectedSubLocationId);
/*  398 */               RoomDeviceMonitorViewer.this.notRoomList.clear();
/*      */             } else {
/*  400 */               RoomDeviceMonitorViewer.this.roomCardSafeContainer.clear();
/*  401 */               RoomDeviceMonitorViewer.this.notRoomList.clear();
/*      */               return;
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   @UiHandler({"deviceCB"})
/*      */   void onDeviceCBSelected(SelectionEvent<RoomCctvUrlDTO> event) {
/*  410 */     if (((RoomCctvUrlDTO)event.getSelectedItem()).getUrl() != null) {
/*  411 */       this.frame.setUrl(((RoomCctvUrlDTO)event.getSelectedItem()).getUrl());
/*      */     }
/*      */   }
/*      */   
/*      */   @UiHandler({"expandAll"})
/*      */   public void expandAll(SelectEvent event) {
/*  417 */     this.tree.expandAll();
/*      */   }
/*      */   
/*      */   @UiHandler({"collapseAll"})
/*      */   public void collapseAll(SelectEvent event) {
/*  422 */     this.tree.collapseAll();
/*      */   }
/*      */   
/*      */   public void initLineData(List<String> response) {
/*  426 */     for (String lineName : response) {
/*  427 */       this.treeStore.add(this.root, new RoomInfoVO(lineName, lineName));
/*  428 */       this.lineNames.add(lineName);
/*      */     } 
/*  430 */     this.presenter.getRoomInfo();
/*      */   }
/*      */   
/*      */   public void initRoomData(List<RoomInfoDTO> response) {
/*  434 */     if (response == null) {
/*  435 */       Info.display("訊息提示", "無機房資料");
/*      */       return;
/*      */     } 
/*  438 */     for (RoomInfoDTO dto : response) {
/*  439 */       RoomInfoVO parent = (RoomInfoVO)this.treeStore.findModelWithKey(dto.getLineName());
/*  440 */       this.treeStore.add(parent, new RoomInfoVO(dto.getId(), dto.getName()));
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setRoomBackground(Map<String, SVGResource> svgResourceMap) {
/*  445 */     this.roomMap.putAll(svgResourceMap);
/*      */   }
/*      */   
/*      */   public void expandSvgContainer() {
/*  449 */     this.svgContainer.expand();
/*      */   }
/*      */   
/*      */   public void infoDoor(Boolean result) {
/*  453 */     if (result.booleanValue() == true) {
/*  454 */       Info.display("遠端開門", "開啟成功");
/*      */     } else {
/*  456 */       Info.display("遠端開門", "關閉失敗");
/*      */     } 
/*  458 */     this.presenter.getRoomSafeInfo(this.selectedSubLocationId);
/*  459 */     unmask();
/*  460 */     this.doorDialog.hide();
/*      */   }
/*      */   
/*      */   public void openPower(Boolean result, Boolean tf) {
/*  464 */     if (result.booleanValue() == true) {
/*  465 */       if (tf.booleanValue() == true) {
/*  466 */         Info.display("遠端警報音", "開啟成功");
/*      */       } else {
/*  468 */         Info.display("遠端警報音", "關閉成功");
/*      */       } 
/*      */     } else {
/*  471 */       Info.display("遠端警報音", "控制失敗");
/*      */     } 
/*  473 */     this.presenter.getRoomSafeInfo(this.selectedSubLocationId);
/*  474 */     RoomDeviceParamDTO dto = new RoomDeviceParamDTO();
/*  475 */     dto.setSelectedSubLocationId(this.selectedSubLocationId);
/*  476 */     dto.setDeviceNameList(new ArrayList<>(this.svgDeviceNameList));
/*  477 */     this.presenter.refreshRoomDeviceStatus(dto);
/*  478 */     this.alarmToneDialog.hide();
/*      */   }
/*      */   
/*      */   public void safeHome(Boolean result) {
/*  482 */     if (result.booleanValue() == true) {
/*  483 */       Info.display("遠端保全復歸", "復歸成功");
/*      */     } else {
/*  485 */       Info.display("遠端保全復歸", "控制失敗");
/*      */     } 
/*  487 */     this.presenter.getRoomSafeInfo(this.selectedSubLocationId);
/*  488 */     RoomDeviceParamDTO dto = new RoomDeviceParamDTO();
/*  489 */     dto.setSelectedSubLocationId(this.selectedSubLocationId);
/*  490 */     dto.setDeviceNameList(new ArrayList<>(this.svgDeviceNameList));
/*  491 */     this.presenter.refreshRoomDeviceStatus(dto);
/*  492 */     this.safeDialog.hide();
/*      */   }
/*      */   
/*      */   public void receiveEvent(String backGroundId) {
/*  496 */     RoomInfoVO selected = null;
/*  497 */     for (RoomInfoVO dto : this.treeStore.getAll()) {
/*  498 */       if (dto.getId().equals(backGroundId)) {
/*  499 */         selected = dto;
/*  500 */         this.tree.getSelectionModel().select(selected, true);
/*      */         break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillRoomSvg(String svgName) {
/*  509 */     if (svgName == null) {
/*  510 */       GWT.log("svgName is null.");
/*      */       
/*      */       return;
/*      */     } 
/*  514 */     clearSVGComponent();
/*  515 */     SVGResource resource = this.roomMap.get(svgName);
/*  516 */     if (resource == null) {
/*  517 */       GWT.log("Fill background svg failed, can not get resource, svgName=" + svgName);
/*      */       return;
/*      */     } 
/*  520 */     OMSVGSVGElement svgElement = resource.getSvg();
/*  521 */     float svgWidth = 0.0F;
/*  522 */     float svgHeight = 0.0F;
/*  523 */     svgWidth = svgElement.getWidth().getBaseVal().getValue();
/*  524 */     svgHeight = svgElement.getHeight().getBaseVal().getValue() - 300.0F;
/*  525 */     this.svg.setWidth(null, svgWidth);
/*  526 */     this.svg.setHeight(null, svgHeight);
/*      */     
/*  528 */     this.svg.setViewBox(0.0F, 0.0F, svgElement
/*      */ 
/*      */         
/*  531 */         .getWidth().getBaseVal().getValue(), svgElement
/*  532 */         .getHeight().getBaseVal().getValue());
/*      */     
/*  534 */     this.svg.appendChild((OMNode)svgElement);
/*  535 */     this.div.appendChild((Node)this.svg.getElement());
/*  536 */     this.presenter.queryDevicePositionConfig(svgName);
/*      */   }
/*      */   
/*      */   public void fillCctvUrl(List<RoomCctvUrlDTO> response, String backgroundId) {
/*  540 */     this.deviceStore.clear();
/*  541 */     this.deviceCB.clear();
/*  542 */     this.deviceStore.addAll(response);
/*  543 */     if (this.deviceStore == null || this.deviceStore.size() == 0) {
/*  544 */       this.frame.setUrl("");
/*  545 */       this.framePanel.mask("該機房無攝影機設備");
/*      */     } else {
/*  547 */       this.framePanel.unmask();
/*      */       
/*  549 */       String cctvDeviceName = StringConverter.getMappingCctvDevice(backgroundId);
/*  550 */       if (this.deviceStore.findModelWithKey(cctvDeviceName) != null) {
/*  551 */         this.deviceCB.setValue(this.deviceStore.findModelWithKey(cctvDeviceName));
/*      */       } else {
/*  553 */         this.deviceCB.setValue(this.deviceStore.get(0));
/*      */       } 
/*  555 */       this.frame.setUrl(((RoomCctvUrlDTO)this.deviceCB.getCurrentValue()).getUrl());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void fillDeviceSvg(String svgName, List<DeviceSvgPositionConfigDTO> response) {
/*  560 */     for (DeviceSvgPositionConfigDTO config : response) {
/*  561 */       SVGResource resource = getDeviceSvgResource(config.getDeviceType());
/*  562 */       if (resource == null) {
/*  563 */         GWT.log("Can not find svg image, deviceType=" + config.getDeviceType());
/*      */         
/*      */         continue;
/*      */       } 
/*  567 */       this.svgDeviceNameList.add(config.getDeviceName());
/*  568 */       OMSVGSVGElement svgElement = resource.getSvg();
/*  569 */       float svgWidth = 0.0F;
/*  570 */       float svgHeight = 0.0F;
/*  571 */       svgWidth = 1.5F * svgElement.getWidth().getBaseVal().getValue();
/*  572 */       svgHeight = 1.5F * svgElement.getHeight().getBaseVal().getValue();
/*  573 */       svgElement.setWidth(null, svgWidth);
/*  574 */       svgElement.setHeight(null, svgHeight);
/*      */       
/*  576 */       if (svgElement.getElementById("deviceName") != null) {
/*  577 */         OMElement tooltip = svgElement.getElementById("deviceName");
/*  578 */         tooltip.getElement().setAttribute("device", config.getDeviceName());
/*      */       } 
/*      */       
/*  581 */       if (svgElement.getElementById("displayName") != null) {
/*  582 */         OMElement displayNameElement = svgElement.getElementById("displayName");
/*  583 */         displayNameElement.getElement().setInnerText(config.getDisplayName() + " ");
/*  584 */         displayNameElement.getElement().setAttribute("id", config.getDeviceName() + "_displayName");
/*      */       } 
/*      */       
/*  587 */       if (svgElement.getElementById("status") != null) {
/*  588 */         OMElement statusElement = svgElement.getElementById("status");
/*  589 */         statusElement.getElement().setAttribute("id", config.getDeviceName() + "_status");
/*      */       } 
/*      */       
/*  592 */       svgElement.getX().getBaseVal().setValue(config.getPositionX().floatValue());
/*  593 */       svgElement.getY().getBaseVal().setValue(config.getPositionY().floatValue());
/*  594 */       svgElement.setId(config.getDeviceName());
/*  595 */       this.svg.appendChild((OMNode)svgElement);
/*  596 */       if (!config.getDeviceType().equals("cardReader") && !config.getDeviceType().equals("CCTV")) {
/*      */         
/*  598 */         if (config.getDeviceType().equals("濕度") || config
/*  599 */           .getDeviceType().equals("DC電壓") || config
/*  600 */           .getDeviceType().equals("油槽容量") || config
/*  601 */           .getDeviceType().equals("溫度") || config
/*  602 */           .getDeviceType().equals("電流") || config
/*  603 */           .getDeviceType().equals("FM發射機") || config
/*  604 */           .getDeviceType().equals("發射機輸出功率")) {
/*  605 */           showAnalogRecordContextMenu((OMElement)svgElement, config.getDeviceName()); continue;
/*  606 */         }  if (config.getDeviceType().equals("ROOM_DEVICE_GROUP")) {
/*  607 */           showDeviceGroupDialog((OMElement)svgElement, config); continue;
/*      */         } 
/*  609 */         showSettingDIContextMenu((OMElement)svgElement, config.getDeviceName()); continue;
/*      */       } 
/*  611 */       if (config.getDeviceType().equals("cardReader")) {
/*  612 */         showOpenDoorContextMenu((OMElement)svgElement, config.getDeviceName());
/*      */       }
/*      */     } 
/*      */     
/*  616 */     this.presenter.getRoomSafeInfo(this.selectedSubLocationId);
/*  617 */     startTimer();
/*      */   }
/*      */   
/*      */   public void fillRoomSafeInfo(RoomNCUStatusDTO response) {
/*  621 */     if (response == null || response.getId() == null) {
/*      */       return;
/*      */     }
/*  624 */     this.roomCardSafeContainer.clear();
/*  625 */     ContentPanel safePanel = new ContentPanel();
/*  626 */     safePanel.setHeading(response.getRoomName());
/*  627 */     safePanel.setCollapsible(true);
/*  628 */     OMSVGDocument safeDoc = OMSVGParser.currentDocument();
/*  629 */     OMSVGSVGElement safSvg = safeDoc.createSVGSVGElement();
/*  630 */     Element safeDiv = null;
/*  631 */     VerticalLayoutContainer statusSvgContainer = new VerticalLayoutContainer();
/*  632 */     XElement xElement = statusSvgContainer.getElement();
/*  633 */     OMSVGSVGElement svgElement = RoomStatusImages.INSTANCE.roomCarReaderStatus().getSvg();
/*  634 */     showSafeContextMenu((OMElement)svgElement, response.getId());
/*  635 */     float width = svgElement.getWidth().getBaseVal().getValue();
/*  636 */     float height = svgElement.getHeight().getBaseVal().getValue();
/*  637 */     safSvg.setViewBox(0.0F, 0.0F, width, height);
/*      */     
/*  639 */     OMElement safeWordElement = svgElement.getElementById("safeWord");
/*  640 */     OMElement cStatusElement = svgElement.getElementById("cStatus");
/*  641 */     if (response.getPreservationStatus() == 0) {
/*  642 */       safeWordElement.getElement().setInnerText("保全啟動");
/*  643 */       cStatusElement.getElement().getStyle().setProperty("fill", this.colorGreen);
/*  644 */     } else if (response.getPreservationStatus() == 1) {
/*  645 */       safeWordElement.getElement().setInnerText("保全解除");
/*  646 */       cStatusElement.getElement().getStyle().setProperty("fill", this.colorYellow);
/*  647 */     } else if (response.getPreservationStatus() == 2) {
/*  648 */       safeWordElement.getElement().setInnerText("非法入侵");
/*  649 */       cStatusElement.getElement().getStyle().setProperty("fill", this.colorRed);
/*      */     } else {
/*  651 */       safeWordElement.getElement().setInnerText("主機斷線");
/*  652 */       cStatusElement.getElement().getStyle().setProperty("fill", this.colorGrey);
/*      */     } 
/*      */     
/*  655 */     OMElement alarmToneElement = svgElement.getElementById("alarmTone");
/*  656 */     if (response.getAlarmVideoStatus() == 0) {
/*  657 */       alarmToneElement.getElement().getStyle().setProperty("fill", this.colorGreen);
/*  658 */     } else if (response.getAlarmVideoStatus() == 1) {
/*  659 */       alarmToneElement.getElement().getStyle().setProperty("fill", this.colorYellow);
/*      */     } else {
/*  661 */       alarmToneElement.getElement().getStyle().setProperty("fill", this.colorGrey);
/*      */     } 
/*  663 */     safSvg.appendChild((OMNode)svgElement);
/*  664 */     xElement.appendChild((Node)safSvg.getElement());
/*  665 */     statusSvgContainer.forceLayout();
/*  666 */     safePanel.add((Widget)statusSvgContainer);
/*  667 */     safePanel.forceLayout();
/*  668 */     this.roomCardSafeContainer.add((Widget)safePanel);
/*  669 */     if (response.getStaffpeople() != null && response.getStaffpeople().size() > 0) {
/*  670 */       ContentPanel staffPanel = new ContentPanel();
/*  671 */       staffPanel.setHeading("逗留人員清單");
/*  672 */       staffPanel.setCollapsible(true);
/*  673 */       this.roomStaffPeopleWidget = new RoomStaffPeopleWidget();
/*  674 */       this.roomStaffPeopleWidget.getListStore().addAll(response.getStaffpeople());
/*  675 */       safeWordElement
/*  676 */         .getElement()
/*  677 */         .setInnerText(((RoomStaffPeopleDTO)this.roomStaffPeopleWidget.getListStore().get(0)).getName());
/*  678 */       cStatusElement.getElement().getStyle().setProperty("fill", this.colorYellow);
/*  679 */       staffPanel.add((Widget)this.roomStaffPeopleWidget);
/*  680 */       staffPanel.forceLayout();
/*  681 */       this.roomCardSafeContainer.add((Widget)staffPanel);
/*      */     } 
/*  683 */     this.roomCardSafeContainer.forceLayout();
/*      */   }
/*      */   
/*      */   public void refreshStatus(List<RoomDeviceStatusDTO> response) {
/*  687 */     for (RoomDeviceStatusDTO dto : response) {
/*  688 */       String deviceName = dto.getDeviceName();
/*  689 */       OMElement svgNode = this.svg.getElementById(dto.getDeviceName());
/*  690 */       Element svgDevice = svgNode.getElement();
/*  691 */       if (((Element)svgDevice
/*  692 */         .getElementsByTagName("title")
/*  693 */         .getItem(0))
/*  694 */         .getAttribute("device")
/*  695 */         .equals(deviceName)) {
/*  696 */         if (this.svg.getElementById(deviceName + "_status") != null) {
/*  697 */           OMElement statusChange = this.svg.getElementById(deviceName + "_status");
/*  698 */           if (dto.getStatus().intValue() == 1) {
/*  699 */             statusChange.getElement().setAttribute("style", "fill:" + this.colorRed);
/*  700 */           } else if (dto.getStatus().intValue() == 0) {
/*  701 */             if (dto.getStatusContent() != null) {
/*  702 */               if (dto.getStatusContent().equals("開啟")) {
/*  703 */                 statusChange.getElement().setAttribute("style", "fill:" + this.colorYellow);
/*      */               } else {
/*  705 */                 statusChange.getElement().setAttribute("style", "fill:" + this.colorGreen);
/*      */               } 
/*      */             }
/*      */           } else {
/*  709 */             statusChange.getElement().setAttribute("style", "fill:" + this.colorGrey);
/*      */           } 
/*      */         } 
/*  712 */         if (dto.getStatusContent() != null && 
/*  713 */           this.svg.getElementById(deviceName + "_displayName") != null) {
/*  714 */           OMElement displayNameElement = this.svg.getElementById(deviceName + "_displayName");
/*  715 */           String[] displayName = null;
/*  716 */           displayName = displayNameElement.getElement().getInnerText().split(" ");
/*  717 */           displayNameElement
/*  718 */             .getElement()
/*  719 */             .setInnerText(displayName[0] + " " + dto.getStatusContent());
/*      */         } 
/*      */         continue;
/*      */       } 
/*  723 */       GWT.log("Can not find deviceName, deviceName=" + deviceName);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void showDeviceGroupDialog(OMElement element, final DeviceSvgPositionConfigDTO dto) {
/*  730 */     element.addDomHandler((EventHandler)new ClickHandler()
/*      */         {
/*      */           
/*      */           public void onClick(ClickEvent event)
/*      */           {
/*  735 */             GWT.log("showDeviceGroupDialog on click. svgName: " + dto
/*      */                 
/*  737 */                 .getSvgName() + ", groupName: " + dto
/*      */                 
/*  739 */                 .getDeviceGroupName());
/*  740 */             RoomDeviceMonitorViewer.this.fillGroupDialogData(dto);
/*      */           }
/*      */         }, 
/*  743 */         ClickEvent.getType());
/*      */   }
/*      */   
/*      */   public void fillGroupDialogData(DeviceSvgPositionConfigDTO dto) {
/*  747 */     if (this.groupDialog == null) {
/*  748 */       initGroupgroupDialog();
/*      */     }
/*  750 */     if (this.groupGrid == null) {
/*  751 */       initGroupGrid();
/*  752 */       this.groupDialog.add((Widget)this.groupGrid);
/*      */     } 
/*  754 */     this.presenter.getRoomGroupDeviceStatus(dto.getSvgName(), dto.getDeviceGroupName());
/*      */   }
/*      */   
/*      */   public void showGroupDialog(List<RoomGroupDeviceStatusDTO> result) {
/*  758 */     this.groupGrid.getStore().clear();
/*  759 */     this.groupGrid.getStore().addAll(result);
/*  760 */     this.groupDialog.show();
/*      */   }
/*      */   
/*      */   public void initGroupgroupDialog() {
/*  764 */     this.groupDialog = new Dialog();
/*  765 */     this.groupDialog.setHeading("設備群組");
/*  766 */     this.groupDialog.setModal(true);
/*  767 */     this.groupDialog.setWidth(320);
/*  768 */     this.groupDialog.setHeight(500);
/*  769 */     this.groupDialog.getButtonBar().clear();
/*      */   }
/*      */ 
/*      */   
/*      */   public void initGroupGrid() {
/*  774 */     ColumnConfig<RoomGroupDeviceStatusDTO, String> nameConfig = new ColumnConfig(groupProps.displayName(), 100, "設備");
/*      */     
/*  776 */     ColumnConfig<RoomGroupDeviceStatusDTO, String> statusConfig = new ColumnConfig(new ValueProvider<RoomGroupDeviceStatusDTO, String>()
/*      */         {
/*      */ 
/*      */           
/*      */           public String getValue(RoomGroupDeviceStatusDTO object)
/*      */           {
/*  782 */             return StringConverter.getAlarmState(object.getStatus());
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(RoomGroupDeviceStatusDTO object, String value) {}
/*      */ 
/*      */           
/*      */           public String getPath() {
/*  790 */             return "status";
/*      */           }
/*      */         },  80, "狀態");
/*      */ 
/*      */     
/*  795 */     ColumnConfig<RoomGroupDeviceStatusDTO, String> contentConfig = new ColumnConfig(new ValueProvider<RoomGroupDeviceStatusDTO, String>()
/*      */         {
/*      */ 
/*      */           
/*      */           public String getValue(RoomGroupDeviceStatusDTO object)
/*      */           {
/*  801 */             return object.getStatusContent();
/*      */           }
/*      */ 
/*      */           
/*      */           public void setValue(RoomGroupDeviceStatusDTO object, String value) {}
/*      */ 
/*      */           
/*      */           public String getPath() {
/*  809 */             return "content";
/*      */           }
/*      */         },  80, "狀態內容");
/*      */ 
/*      */     
/*  814 */     statusConfig.setCell((Cell)new AbstractCell<String>(new String[0])
/*      */         {
/*      */           public void render(Cell.Context context, String value, SafeHtmlBuilder sb)
/*      */           {
/*  818 */             String style = "style='color: " + (value.equals("正常") ? "green" : "red") + "'";
/*  819 */             sb.appendHtmlConstant("<span " + style + ">" + value + "</span>");
/*      */           }
/*      */         });
/*      */     
/*  823 */     List<ColumnConfig<RoomGroupDeviceStatusDTO, ?>> columns = new ArrayList<>();
/*      */     
/*  825 */     columns.add(nameConfig);
/*  826 */     columns.add(statusConfig);
/*  827 */     columns.add(contentConfig);
/*      */     
/*  829 */     ColumnModel<RoomGroupDeviceStatusDTO> cm = new ColumnModel(columns);
/*      */     
/*  831 */     ListStore<RoomGroupDeviceStatusDTO> store = new ListStore(groupProps.deviceName());
/*  832 */     store.addSortInfo(new Store.StoreSortInfo(new Comparator<RoomGroupDeviceStatusDTO>()
/*      */           {
/*      */             
/*      */             public int compare(RoomGroupDeviceStatusDTO o1, RoomGroupDeviceStatusDTO o2)
/*      */             {
/*  837 */               return o1.getDisplayName().compareTo(o2.getDisplayName());
/*      */             }
/*      */           },  SortDir.ASC));
/*      */     
/*  841 */     this.groupGrid = new Grid(store, cm);
/*  842 */     this.groupGrid.getView().setForceFit(true);
/*      */   }
/*      */   
/*      */   private void clearSVGComponent() {
/*  846 */     this.div.removeAllChildren();
/*  847 */     this.svg = this.doc.createSVGSVGElement();
/*  848 */     this.svgDeviceNameList = new HashSet<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SVGResource getDeviceSvgResource(String deviceType) {
/*  856 */     if (deviceType.equals("ROOM_DEVICE_GROUP"))
/*  857 */       return RoomDeviceImages.INSTANCE.group(); 
/*  858 */     if (deviceType.equals("cardReader")) {
/*  859 */       return RoomDeviceImages.INSTANCE.doorSingal();
/*      */     }
/*  861 */     return RoomDeviceImages.INSTANCE.inputSingal();
/*      */   }
/*      */   
/*      */   public class RoomInfoVO
/*      */   {
/*      */     private String id;
/*      */     private String name;
/*      */     
/*      */     public RoomInfoVO(String id, String name) {
/*  870 */       this.id = id;
/*  871 */       this.name = name;
/*      */     }
/*      */     
/*      */     public String getId() {
/*  875 */       return this.id;
/*      */     }
/*      */     
/*      */     public void setId(String id) {
/*  879 */       this.id = id;
/*      */     }
/*      */     
/*      */     public String getName() {
/*  883 */       return this.name;
/*      */     }
/*      */     
/*      */     public void setName(String name) {
/*  887 */       this.name = name;
/*      */     }
/*      */   }
/*      */   
/*      */   private void showDoorDialog() {
/*  892 */     if (this.doorDialog == null) {
/*  893 */       initDoorDialog();
/*      */     }
/*  895 */     this.doorDialog.show();
/*      */   }
/*      */   
/*      */   private void initDoorDialog() {
/*  899 */     this.doorDialog = new Dialog();
/*  900 */     this.doorDialog.setHeading("是否確定開門");
/*  901 */     this.doorDialog.setModal(true);
/*  902 */     this.doorDialog.setWidth(260);
/*  903 */     this.doorDialog.setHeight(100);
/*  904 */     TextButton confirmButton = new TextButton();
/*  905 */     confirmButton.setText("是");
/*  906 */     confirmButton.addSelectHandler(new SelectEvent.SelectHandler()
/*      */         {
/*      */           public void onSelect(SelectEvent event)
/*      */           {
/*  910 */             RoomNCUStatusDTO dto = new RoomNCUStatusDTO();
/*  911 */             dto.setId(RoomDeviceMonitorViewer.this.openedDevice);
/*  912 */             RoomDeviceMonitorViewer.this.presenter.openDoor(dto);
/*  913 */             RoomDeviceMonitorViewer.this.mask("遠端開門指令傳送中");
/*      */           }
/*      */         });
/*  916 */     TextButton cancelButton = new TextButton();
/*  917 */     cancelButton.setText("取消");
/*  918 */     cancelButton.addSelectHandler(new SelectEvent.SelectHandler()
/*      */         {
/*      */           public void onSelect(SelectEvent event)
/*      */           {
/*  922 */             RoomDeviceMonitorViewer.this.doorDialog.hide();
/*      */           }
/*      */         });
/*  925 */     this.doorDialog.getButtonBar().clear();
/*  926 */     this.doorDialog.getButtonBar().add((Widget)confirmButton);
/*  927 */     this.doorDialog.getButtonBar().add((Widget)cancelButton);
/*      */   }
/*      */   
/*      */   private void showAlarmToneDialog() {
/*  931 */     if (this.alarmToneDialog == null) {
/*  932 */       initAlarmToneDialog();
/*      */     }
/*  934 */     this.alarmToneDialog.show();
/*      */   }
/*      */   
/*      */   private void initAlarmToneDialog() {
/*  938 */     this.alarmToneDialog = new Dialog();
/*  939 */     this.alarmToneDialog.setHeading("遠端警報音控制");
/*  940 */     this.alarmToneDialog.setModal(true);
/*  941 */     this.alarmToneDialog.setWidth(260);
/*  942 */     this.alarmToneDialog.setHeight(100);
/*  943 */     TextButton confirmButton = new TextButton();
/*  944 */     confirmButton.setText("開啟");
/*  945 */     confirmButton.addSelectHandler(new SelectEvent.SelectHandler()
/*      */         {
/*      */           public void onSelect(SelectEvent event)
/*      */           {
/*  949 */             String deviceName = RoomDeviceMonitorViewer.this.alarmToneDevice + "-DO-2";
/*  950 */             RoomDoDTO dto = new RoomDoDTO();
/*  951 */             dto.setDeviceName(deviceName);
/*  952 */             dto.setValue(Boolean.valueOf(true));
/*  953 */             RoomDeviceMonitorViewer.this.presenter.updateRoomDoOutput(dto, deviceName, Boolean.valueOf(true));
/*      */           }
/*      */         });
/*  956 */     TextButton rejectButton = new TextButton();
/*  957 */     rejectButton.setText("關閉");
/*  958 */     rejectButton.addSelectHandler(new SelectEvent.SelectHandler()
/*      */         {
/*      */           public void onSelect(SelectEvent event)
/*      */           {
/*  962 */             String deviceName = RoomDeviceMonitorViewer.this.alarmToneDevice + "-DO-2";
/*  963 */             RoomDoDTO dto = new RoomDoDTO();
/*  964 */             dto.setDeviceName(deviceName);
/*  965 */             dto.setValue(Boolean.valueOf(false));
/*  966 */             RoomDeviceMonitorViewer.this.presenter.updateRoomDoOutput(dto, deviceName, Boolean.valueOf(false));
/*      */           }
/*      */         });
/*  969 */     TextButton cancelButton = new TextButton();
/*  970 */     cancelButton.setText("取消");
/*  971 */     cancelButton.addSelectHandler(new SelectEvent.SelectHandler()
/*      */         {
/*      */           public void onSelect(SelectEvent event)
/*      */           {
/*  975 */             RoomDeviceMonitorViewer.this.alarmToneDialog.hide();
/*      */           }
/*      */         });
/*  978 */     this.alarmToneDialog.getButtonBar().clear();
/*      */ 
/*      */     
/*  981 */     this.alarmToneDialog.getButtonBar().add((Widget)rejectButton);
/*  982 */     this.alarmToneDialog.getButtonBar().add((Widget)cancelButton);
/*      */   }
/*      */   
/*      */   private void showSafeDialog() {
/*  986 */     if (this.safeDialog == null) {
/*  987 */       initSafeDialog();
/*      */     }
/*  989 */     this.safeDialog.show();
/*      */   }
/*      */   
/*      */   private void initSafeDialog() {
/*  993 */     this.safeDialog = new Dialog();
/*  994 */     this.safeDialog.setHeading("遠端保全復歸");
/*  995 */     this.safeDialog.setModal(true);
/*  996 */     this.safeDialog.setWidth(260);
/*  997 */     this.safeDialog.setHeight(100);
/*  998 */     TextButton confirmButton = new TextButton();
/*  999 */     confirmButton.setText("確定");
/* 1000 */     confirmButton.addSelectHandler(new SelectEvent.SelectHandler()
/*      */         {
/*      */           public void onSelect(SelectEvent event)
/*      */           {
/* 1004 */             String safeDeviceName = RoomDeviceMonitorViewer.this.alarmToneDevice + "-DO-4";
/* 1005 */             RoomDoDTO dto = new RoomDoDTO();
/* 1006 */             dto.setDeviceName(safeDeviceName);
/* 1007 */             dto.setValue(Boolean.valueOf(true));
/* 1008 */             RoomDeviceMonitorViewer.this.presenter.updateRoomDoOutput(dto, safeDeviceName, Boolean.valueOf(true));
/*      */           }
/*      */         });
/*      */     
/* 1012 */     TextButton cancelButton = new TextButton();
/* 1013 */     cancelButton.setText("取消");
/* 1014 */     cancelButton.addSelectHandler(new SelectEvent.SelectHandler()
/*      */         {
/*      */           public void onSelect(SelectEvent event)
/*      */           {
/* 1018 */             RoomDeviceMonitorViewer.this.safeDialog.hide();
/*      */           }
/*      */         });
/* 1021 */     this.safeDialog.getButtonBar().clear();
/* 1022 */     this.safeDialog.getButtonBar().add((Widget)confirmButton);
/* 1023 */     this.safeDialog.getButtonBar().add((Widget)cancelButton);
/*      */   }
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
/*      */   public void showSafeContextMenu(OMElement element, final String deviceName) {
/* 1039 */     element.addDomHandler((EventHandler)new ContextMenuHandler()
/*      */         {
/*      */           
/*      */           public void onContextMenu(ContextMenuEvent event)
/*      */           {
/* 1044 */             int x = event.getNativeEvent().getClientX();
/* 1045 */             int y = event.getNativeEvent().getClientY();
/* 1046 */             event.preventDefault();
/* 1047 */             RoomDeviceMonitorViewer.this.onShowContextMenu(RoomDeviceMonitorViewer.this.roomMenu, x, y);
/* 1048 */             RoomDeviceMonitorViewer.this.alarmToneDevice = deviceName;
/*      */           }
/* 1051 */         }ContextMenuEvent.getType());
/*      */   }
/*      */   
/*      */   public void showSettingDIContextMenu(OMElement element, final String deviceName) {
/* 1055 */     element.addDomHandler((EventHandler)new ContextMenuHandler()
/*      */         {
/*      */           
/*      */           public void onContextMenu(ContextMenuEvent event)
/*      */           {
/* 1060 */             int x = event.getNativeEvent().getClientX();
/* 1061 */             int y = event.getNativeEvent().getClientY();
/* 1062 */             event.preventDefault();
/* 1063 */             RoomDeviceMonitorViewer.this.onShowContextMenu(RoomDeviceMonitorViewer.this.settingDIMenu, x, y);
/* 1064 */             RoomDeviceMonitorViewer.this.settedDevice = deviceName;
/*      */           }
/* 1067 */         }ContextMenuEvent.getType());
/*      */   }
/*      */   
/*      */   public void showSettingDOContextMenu(OMElement element, final String deviceName) {
/* 1071 */     element.addDomHandler((EventHandler)new ContextMenuHandler()
/*      */         {
/*      */           
/*      */           public void onContextMenu(ContextMenuEvent event)
/*      */           {
/* 1076 */             int x = event.getNativeEvent().getClientX();
/* 1077 */             int y = event.getNativeEvent().getClientY();
/* 1078 */             event.preventDefault();
/* 1079 */             RoomDeviceMonitorViewer.this.onShowContextMenu(RoomDeviceMonitorViewer.this.settingDOMenu, x, y);
/* 1080 */             RoomDeviceMonitorViewer.this.settedDevice = deviceName;
/*      */           }
/* 1083 */         }ContextMenuEvent.getType());
/*      */   }
/*      */   
/*      */   public void showOpenDoorContextMenu(OMElement element, final String deviceName) {
/* 1087 */     element.addDomHandler((EventHandler)new ContextMenuHandler()
/*      */         {
/*      */           
/*      */           public void onContextMenu(ContextMenuEvent event)
/*      */           {
/* 1092 */             int x = event.getNativeEvent().getClientX();
/* 1093 */             int y = event.getNativeEvent().getClientY();
/* 1094 */             event.preventDefault();
/* 1095 */             RoomDeviceMonitorViewer.this.onShowContextMenu(RoomDeviceMonitorViewer.this.openMenu, x, y);
/* 1096 */             RoomDeviceMonitorViewer.this.openedDevice = deviceName;
/*      */           }
/* 1099 */         }ContextMenuEvent.getType());
/*      */   }
/*      */   
/*      */   public void showAnalogRecordContextMenu(OMElement element, final String deviceName) {
/* 1103 */     element.addDomHandler((EventHandler)new ContextMenuHandler()
/*      */         {
/*      */           
/*      */           public void onContextMenu(ContextMenuEvent event)
/*      */           {
/* 1108 */             int x = event.getNativeEvent().getClientX();
/* 1109 */             int y = event.getNativeEvent().getClientY();
/* 1110 */             event.preventDefault();
/* 1111 */             RoomDeviceMonitorViewer.this.onShowContextMenu(RoomDeviceMonitorViewer.this.analogRecordMenu, x, y);
/* 1112 */             RoomDeviceMonitorViewer.this.analogDevice = deviceName;
/*      */           }
/* 1115 */         }ContextMenuEvent.getType());
/*      */   }
/*      */   
/*      */   protected void onShowContextMenu(Menu menu, int clientX, int clientY) {
/* 1119 */     menu.showAt(clientX, clientY);
/* 1120 */     fireEvent((GwtEvent)new ShowContextMenuEvent(menu));
/*      */   }
/*      */   
/*      */   private void startTimer() {
/* 1124 */     if (this.timer == null) {
/* 1125 */       this.timer = new Timer()
/*      */         {
/*      */           public void run()
/*      */           {
/* 1129 */             RoomDeviceParamDTO dto = new RoomDeviceParamDTO();
/* 1130 */             dto.setSelectedSubLocationId(RoomDeviceMonitorViewer.this.selectedSubLocationId);
/* 1131 */             dto.setDeviceNameList(new ArrayList(RoomDeviceMonitorViewer.this.svgDeviceNameList));
/* 1132 */             RoomDeviceMonitorViewer.this.presenter.refreshRoomDeviceStatus(dto);
/*      */           }
/*      */         };
/* 1135 */       this.timer.scheduleRepeating(5000);
/* 1136 */       this.timer.run();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void startSafeTimer() {
/* 1141 */     if (this.safeTimer == null) {
/* 1142 */       this.safeTimer = new Timer()
/*      */         {
/*      */           public void run()
/*      */           {
/* 1146 */             if (RoomDeviceMonitorViewer.this.selectedSubLocationId != null) {
/* 1147 */               RoomDeviceMonitorViewer.this.presenter.getRoomSafeInfo(RoomDeviceMonitorViewer.this.selectedSubLocationId);
/*      */             }
/*      */           }
/*      */         };
/* 1151 */       this.safeTimer.scheduleRepeating(5000);
/* 1152 */       this.safeTimer.run();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onShow() {
/* 1158 */     super.onShow();
/* 1159 */     if (this.svgDeviceNameList != null && !this.svgDeviceNameList.isEmpty()) {
/* 1160 */       startTimer();
/*      */     }
/* 1162 */     else if (this.timer != null) {
/* 1163 */       this.timer.cancel();
/* 1164 */       this.timer = null;
/*      */     } 
/*      */     
/* 1167 */     startSafeTimer();
/* 1168 */     this.presenter.getAllNonRtnAlarms();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onHide() {
/* 1173 */     super.onHide();
/* 1174 */     if (this.timer != null) {
/* 1175 */       this.timer.cancel();
/* 1176 */       this.timer = null;
/*      */     } 
/* 1178 */     if (this.safeTimer != null) {
/* 1179 */       this.safeTimer.cancel();
/* 1180 */       this.safeTimer = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   static interface GroupGridProperties extends PropertyAccess<RoomGroupDeviceStatusDTO> {
/*      */     ModelKeyProvider<RoomGroupDeviceStatusDTO> deviceName();
/*      */     
/*      */     ValueProvider<RoomGroupDeviceStatusDTO, String> displayName();
/*      */   }
/*      */   
/*      */   static interface GridProperties extends PropertyAccess<RoomDeviceSubLocationConfigDTO> {
/*      */     ModelKeyProvider<RoomDeviceSubLocationConfigDTO> id();
/*      */     
/*      */     ValueProvider<RoomDeviceSubLocationConfigDTO, String> locationName();
/*      */   }
/*      */   
/*      */   static interface RoomDeviceMonitorViewerUiBinder extends UiBinder<Widget, RoomDeviceMonitorViewer> {}
/*      */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\RoomDeviceMonitorViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */