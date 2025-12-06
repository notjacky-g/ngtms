/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.event.dom.client.KeyUpEvent;
/*     */ import com.google.gwt.event.logical.shared.SelectionEvent;
/*     */ import com.google.gwt.event.logical.shared.ValueChangeEvent;
/*     */ import com.google.gwt.event.logical.shared.ValueChangeHandler;
/*     */ import com.google.gwt.event.shared.EventHandler;
/*     */ import com.google.gwt.event.shared.GwtEvent;
/*     */ import com.google.gwt.i18n.client.DateTimeFormat;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.uibinder.client.UiHandler;
/*     */ import com.google.gwt.user.client.Timer;
/*     */ import com.google.gwt.user.client.ui.Label;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.event.RoomConfigDataEvent;
/*     */ import com.hwacom.ngtms.ao.am.event.RoomControlEvent;
/*     */ import com.hwacom.ngtms.ao.am.presenter.NcuCardControlPresenter;
/*     */ import com.hwacom.ngtms.ao.shared.dto.NcuConfigDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomNCUCardDTO;
/*     */ import com.hwacom.ngtms.cam.client.ui.AmTab;
/*     */ import com.hwacom.ngtms.cam.client.ui.ExportCsvFile;
/*     */ import com.hwacom.ngtms.common.am.util.DateTimeUtil;
/*     */ import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
/*     */ import com.hwacom.ngtms.room.am.util.StringConverter;
/*     */ import com.hwacom.ngtms.room.shared.CardStatus;
/*     */ import com.hwacom.ngtms.room.shared.dto.RoomCardConfigDTO;
/*     */ import com.hwacom.ngtms.room.shared.dto.RoomCardGroupConfigDTO;
/*     */ import com.hwacom.ngtms.room.shared.dto.RoomCardReaderMappingConfigDTO;
/*     */ import com.sencha.gxt.core.client.Style;
/*     */ import com.sencha.gxt.core.client.ValueProvider;
/*     */ import com.sencha.gxt.data.shared.LabelProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.data.shared.Store;
/*     */ import com.sencha.gxt.data.shared.TreeStore;
/*     */ import com.sencha.gxt.widget.core.client.Dialog;
/*     */ import com.sencha.gxt.widget.core.client.button.TextButton;
/*     */ import com.sencha.gxt.widget.core.client.event.HideEvent;
/*     */ import com.sencha.gxt.widget.core.client.event.RowClickEvent;
/*     */ import com.sencha.gxt.widget.core.client.event.SelectEvent;
/*     */ import com.sencha.gxt.widget.core.client.form.CheckBox;
/*     */ import com.sencha.gxt.widget.core.client.form.ComboBox;
/*     */ import com.sencha.gxt.widget.core.client.form.DateField;
/*     */ import com.sencha.gxt.widget.core.client.form.TextField;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*     */ import com.sencha.gxt.widget.core.client.grid.Grid;
/*     */ import com.sencha.gxt.widget.core.client.info.Info;
/*     */ import com.sencha.gxt.widget.core.client.tree.Tree;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NcuCardControlViewer
/*     */   extends AmTab
/*     */ {
/*  68 */   private static NcuCardControlViewerUiBinder uiBinder = (NcuCardControlViewerUiBinder)GWT.create(NcuCardControlViewerUiBinder.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   private RoomCardConfigPropertyAccess roomCardConfigPropertyAccess = (RoomCardConfigPropertyAccess)GWT.create(RoomCardConfigPropertyAccess.class);
/*     */   
/*  75 */   private NcuCardControlPresenter presenter = new NcuCardControlPresenter(this);
/*     */   
/*  77 */   private final ClientFactory clientFactory = (ClientFactory)GWT.create(ClientFactory.class);
/*     */   
/*  79 */   private List<String> lineNames = new ArrayList<>();
/*     */   
/*     */   @UiField
/*     */   TextField queryCard;
/*     */   
/*     */   @UiField
/*     */   TextField queryName;
/*     */   
/*     */   @UiField
/*     */   ComboBox<RoomCardGroupConfigDTO> queryGroupComboBox;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<RoomCardGroupConfigDTO> queryGroupCbStore;
/*     */   
/*     */   @UiField(provided = true)
/*     */   LabelProvider<RoomCardGroupConfigDTO> queryGroupCbLabelProvider;
/*     */   
/*     */   @UiField
/*     */   Grid<RoomCardConfigDTO> grid;
/*     */   
/*     */   @UiField(provided = true)
/*     */   public ListStore<RoomCardConfigDTO> listStore;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ColumnModel<RoomCardConfigDTO> columnModel;
/*     */   
/*     */   @UiField(provided = true)
/*     */   TreeStore<CardPermissionVO> treeStore;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ValueProvider<CardPermissionVO, String> treeValueProvider;
/*     */   @UiField
/*     */   public Tree<CardPermissionVO, String> tree;
/*     */   @UiField
/*     */   TextButton issue;
/*     */   @UiField
/*     */   CheckBox notAllowedCheckBox;
/*     */   @UiField
/*     */   CheckBox updateCheckBox;
/*     */   @UiField
/*     */   DateField startDate;
/*     */   @UiField
/*     */   DateField endDate;
/*     */   private Store.StoreFilter<RoomCardConfigDTO> cardfilter;
/*     */   private Store.StoreFilter<RoomCardConfigDTO> namefilter;
/*     */   private Store.StoreFilter<RoomCardConfigDTO> groupfilter;
/* 125 */   private CardPermissionVO root = new CardPermissionVO("root", "機房");
/*     */   
/*     */   private Dialog addCardFailDialog;
/*     */   
/*     */   public NcuCardControlViewer() {
/* 130 */     initStoreAndProvider();
/* 131 */     initColumnModel();
/* 132 */     initFilter();
/* 133 */     initTree();
/* 134 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/* 135 */     this.presenter.getCardGroupConfig();
/* 136 */     this.tree.setCheckable(true);
/* 137 */     this.tree.setCheckStyle(Tree.CheckCascade.TRI);
/* 138 */     this.tree.setCheckNodes(Tree.CheckNodes.BOTH);
/* 139 */     this.grid.getSelectionModel().setSelectionMode(Style.SelectionMode.MULTI);
/* 140 */     Date now = new Date();
/* 141 */     this.startDate.setValue(DateTimeUtil.dayStart(now));
/* 142 */     this.endDate.setValue(now);
/* 143 */     this.notAllowedCheckBox.setValue(Boolean.valueOf(false));
/* 144 */     this.updateCheckBox.setValue(Boolean.valueOf(false));
/* 145 */     this.startDate.disable();
/* 146 */     this.endDate.disable();
/* 147 */     this.clientFactory
/* 148 */       .getEventBus()
/* 149 */       .addHandler(RoomConfigDataEvent.TYPE, (EventHandler)new DefaultRoomConfigDataEventHandler());
/* 150 */     addEventHander();
/*     */   }
/*     */   
/*     */   private void addEventHander() {
/* 154 */     this.updateCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>()
/*     */         {
/*     */ 
/*     */           
/*     */           public void onValueChange(ValueChangeEvent<Boolean> event)
/*     */           {
/* 160 */             if (((Boolean)NcuCardControlViewer.this.updateCheckBox.getValue()).booleanValue()) {
/* 161 */               NcuCardControlViewer.this.tree.disable();
/* 162 */               NcuCardControlViewer.this.startDate.enable();
/* 163 */               NcuCardControlViewer.this.endDate.enable();
/*     */             } else {
/* 165 */               NcuCardControlViewer.this.tree.enable();
/* 166 */               NcuCardControlViewer.this.startDate.disable();
/* 167 */               NcuCardControlViewer.this.endDate.disable();
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void initStoreAndProvider() {
/* 175 */     this.queryGroupCbStore = new ListStore(new ModelKeyProvider<RoomCardGroupConfigDTO>()
/*     */         {
/*     */ 
/*     */           
/*     */           public String getKey(RoomCardGroupConfigDTO item)
/*     */           {
/* 181 */             return item.getId().toString();
/*     */           }
/*     */         });
/* 184 */     this.listStore = new ListStore(new ModelKeyProvider<RoomCardConfigDTO>()
/*     */         {
/*     */ 
/*     */           
/*     */           public String getKey(RoomCardConfigDTO item)
/*     */           {
/* 190 */             return item.getId();
/*     */           }
/*     */         });
/*     */     
/* 194 */     this.treeStore = new TreeStore(new ModelKeyProvider<CardPermissionVO>()
/*     */         {
/*     */ 
/*     */           
/*     */           public String getKey(NcuCardControlViewer.CardPermissionVO item)
/*     */           {
/* 200 */             return item.getId();
/*     */           }
/*     */         });
/*     */     
/* 204 */     this.queryGroupCbLabelProvider = new LabelProvider<RoomCardGroupConfigDTO>()
/*     */       {
/*     */         
/*     */         public String getLabel(RoomCardGroupConfigDTO item)
/*     */         {
/* 209 */           return item.getName();
/*     */         }
/*     */       };
/*     */     
/* 213 */     this.treeValueProvider = new ValueProvider<CardPermissionVO, String>()
/*     */       {
/*     */         public void setValue(NcuCardControlViewer.CardPermissionVO object, String value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public String getValue(NcuCardControlViewer.CardPermissionVO object) {
/* 221 */           return object.getName();
/*     */         }
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 226 */           return "treeValueProvider";
/*     */         }
/*     */       };
/*     */     
/* 230 */     this.listStore = new ListStore(this.roomCardConfigPropertyAccess.id());
/*     */   }
/*     */   
/*     */   private void initColumnModel() {
/* 234 */     List<ColumnConfig<RoomCardConfigDTO, ?>> ColumnConfigs = new ArrayList<>();
/*     */ 
/*     */     
/* 237 */     ColumnConfig<RoomCardConfigDTO, String> abaColumn = new ColumnConfig(this.roomCardConfigPropertyAccess.aba(), 100, "卡片編號");
/* 238 */     ColumnConfigs.add(abaColumn);
/*     */ 
/*     */     
/* 241 */     ColumnConfig<RoomCardConfigDTO, String> nameColumn = new ColumnConfig(this.roomCardConfigPropertyAccess.name(), 80, "姓名");
/* 242 */     ColumnConfigs.add(nameColumn);
/*     */ 
/*     */     
/* 245 */     ColumnConfig<RoomCardConfigDTO, String> companyColumn = new ColumnConfig(this.roomCardConfigPropertyAccess.company(), 100, "公司名稱");
/* 246 */     ColumnConfigs.add(companyColumn);
/*     */ 
/*     */     
/* 249 */     ColumnConfig<RoomCardConfigDTO, String> telColumn = new ColumnConfig(this.roomCardConfigPropertyAccess.tel(), 80, "連絡電話");
/* 250 */     ColumnConfigs.add(telColumn);
/*     */ 
/*     */     
/* 253 */     ColumnConfig<RoomCardConfigDTO, String> groupNameColumn = new ColumnConfig(this.roomCardConfigPropertyAccess.groupName(), 100, "群組名稱");
/* 254 */     ColumnConfigs.add(groupNameColumn);
/*     */     
/* 256 */     ColumnConfig<RoomCardConfigDTO, String> dateValueColumn = new ColumnConfig(new ValueProvider<RoomCardConfigDTO, String>()
/*     */         {
/*     */ 
/*     */           
/*     */           public String getValue(RoomCardConfigDTO object)
/*     */           {
/* 262 */             if (object.getCardStartDate() == null || object.getCardEndDate() == null) {
/* 263 */               return "";
/*     */             }
/* 265 */             return StringConverter.getTimeyyyyMMdd(object.getCardStartDate()) + "~" + 
/*     */               
/* 267 */               StringConverter.getTimeyyyyMMdd(object.getCardEndDate());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(RoomCardConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 275 */             return null;
/*     */           }
/*     */         },  200, "使用期限");
/*     */ 
/*     */     
/* 280 */     ColumnConfigs.add(dateValueColumn);
/*     */     
/* 282 */     this.columnModel = new ColumnModel(ColumnConfigs);
/*     */   }
/*     */   
/*     */   private void initFilter() {
/* 286 */     this.cardfilter = new Store.StoreFilter<RoomCardConfigDTO>()
/*     */       {
/*     */         
/*     */         public boolean select(Store<RoomCardConfigDTO> store, RoomCardConfigDTO parent, RoomCardConfigDTO item)
/*     */         {
/* 291 */           return true;
/*     */         }
/*     */       };
/* 294 */     this.namefilter = new Store.StoreFilter<RoomCardConfigDTO>()
/*     */       {
/*     */         
/*     */         public boolean select(Store<RoomCardConfigDTO> store, RoomCardConfigDTO parent, RoomCardConfigDTO item)
/*     */         {
/* 299 */           return true;
/*     */         }
/*     */       };
/* 302 */     this.groupfilter = new Store.StoreFilter<RoomCardConfigDTO>()
/*     */       {
/*     */         
/*     */         public boolean select(Store<RoomCardConfigDTO> store, RoomCardConfigDTO parent, RoomCardConfigDTO item)
/*     */         {
/* 307 */           return true;
/*     */         }
/*     */       };
/* 310 */     this.listStore.addFilter(this.cardfilter);
/* 311 */     this.listStore.addFilter(this.namefilter);
/* 312 */     this.listStore.addFilter(this.groupfilter);
/* 313 */     this.listStore.setEnableFilters(true);
/*     */   }
/*     */   
/*     */   public void initTree() {
/* 317 */     this.treeStore.clear();
/* 318 */     this.treeStore.add(this.root);
/* 319 */     this.presenter.getRoomLineData();
/*     */   }
/*     */   
/*     */   public void setPresenter(NcuCardControlPresenter presenter) {
/* 323 */     this.presenter = presenter;
/*     */   }
/*     */   
/*     */   @UiHandler({"queryCard"})
/*     */   public void onQueryCard(KeyUpEvent event) {
/* 328 */     Timer timer = new Timer()
/*     */       {
/*     */         public void run() {}
/*     */       };
/*     */     
/* 333 */     timer.schedule(500);
/* 334 */     final String cardValue = (String)this.queryCard.getCurrentValue();
/* 335 */     if (cardValue == null || cardValue.equals("")) {
/* 336 */       this.listStore.removeFilter(this.cardfilter);
/*     */     } else {
/* 338 */       this.listStore.removeFilter(this.cardfilter);
/* 339 */       this.cardfilter = new Store.StoreFilter<RoomCardConfigDTO>()
/*     */         {
/*     */           
/*     */           public boolean select(Store<RoomCardConfigDTO> store, RoomCardConfigDTO parent, RoomCardConfigDTO item)
/*     */           {
/* 344 */             return item.getAba().contains(cardValue);
/*     */           }
/*     */         };
/* 347 */       this.listStore.addFilter(this.cardfilter);
/*     */     } 
/*     */   }
/*     */   
/*     */   @UiHandler({"queryName"})
/*     */   public void onQueryName(KeyUpEvent event) {
/* 353 */     Timer timer = new Timer()
/*     */       {
/*     */         public void run() {}
/*     */       };
/*     */     
/* 358 */     timer.schedule(500);
/* 359 */     final String nameValue = (String)this.queryName.getCurrentValue();
/* 360 */     if (nameValue == null || nameValue.equals("")) {
/* 361 */       this.listStore.removeFilter(this.namefilter);
/*     */     } else {
/* 363 */       this.listStore.removeFilter(this.namefilter);
/* 364 */       this.namefilter = new Store.StoreFilter<RoomCardConfigDTO>()
/*     */         {
/*     */           
/*     */           public boolean select(Store<RoomCardConfigDTO> store, RoomCardConfigDTO parent, RoomCardConfigDTO item)
/*     */           {
/* 369 */             return item.getName().contains(nameValue);
/*     */           }
/*     */         };
/* 372 */       this.listStore.addFilter(this.namefilter);
/*     */     } 
/*     */   }
/*     */   
/*     */   @UiHandler({"queryGroupComboBox"})
/*     */   public void onQueryGroup(SelectionEvent<RoomCardGroupConfigDTO> event) {
/* 378 */     final String groupName = ((RoomCardGroupConfigDTO)event.getSelectedItem()).getName();
/* 379 */     if (groupName.equals("全部") || groupName == null) {
/* 380 */       this.listStore.removeFilter(this.groupfilter);
/*     */     } else {
/* 382 */       this.listStore.removeFilter(this.groupfilter);
/* 383 */       this.groupfilter = new Store.StoreFilter<RoomCardConfigDTO>()
/*     */         {
/*     */           
/*     */           public boolean select(Store<RoomCardConfigDTO> store, RoomCardConfigDTO parent, RoomCardConfigDTO item)
/*     */           {
/* 388 */             return (groupName == item.getGroupName());
/*     */           }
/*     */         };
/* 391 */       this.listStore.addFilter(this.groupfilter);
/*     */     } 
/*     */   }
/*     */   
/*     */   @UiHandler({"grid"})
/*     */   public void onGridSelect(RowClickEvent event) {
/* 397 */     RoomCardConfigDTO selectItem = (RoomCardConfigDTO)this.grid.getSelectionModel().getSelectedItem();
/* 398 */     this.presenter.getRoomCardReaderMappingConfig(selectItem.getAba());
/*     */   }
/*     */ 
/*     */   
/*     */   @UiHandler({"issue"})
/*     */   public void onIssueImmediately(SelectEvent event) {
/* 404 */     List<RoomCardConfigDTO> dtos = this.grid.getSelectionModel().getSelectedItems();
/* 405 */     if (dtos == null || dtos.size() == 0) {
/* 406 */       Info.display("訊息提醒", "請選取卡片");
/*     */     }
/* 408 */     mask("卡片權限設定中...");
/* 409 */     RoomNCUCardDTO cardDTO = new RoomNCUCardDTO();
/* 410 */     cardDTO.setCardReaderAddCardMap(getTreePermissionMap());
/* 411 */     List<String> cardNos = new ArrayList<>();
/* 412 */     for (RoomCardConfigDTO roomConfigDTO : dtos) {
/* 413 */       cardNos.add(roomConfigDTO.getAba());
/*     */     }
/* 415 */     cardDTO.setCardNos(cardNos);
/* 416 */     cardDTO.setUpDateCard(((Boolean)this.updateCheckBox.getValue()).booleanValue());
/* 417 */     cardDTO.setAccessControlDoor(((Boolean)this.notAllowedCheckBox.getValue()).booleanValue());
/* 418 */     if (((Boolean)this.updateCheckBox.getValue()).booleanValue()) {
/* 419 */       cardDTO.setCardStartDate((Date)this.startDate.getCurrentValue());
/* 420 */       cardDTO.setCardEndDate((Date)this.endDate.getCurrentValue());
/*     */     } 
/* 422 */     this.presenter.addCardPermission(cardDTO);
/*     */   }
/*     */   
/*     */   @UiHandler({"expandAll"})
/*     */   public void expandAll(SelectEvent event) {
/* 427 */     this.tree.expandAll();
/*     */   }
/*     */   
/*     */   @UiHandler({"collapseAll"})
/*     */   public void collapseAll(SelectEvent event) {
/* 432 */     this.tree.collapseAll();
/*     */   }
/*     */ 
/*     */   
/*     */   private Map<String, Boolean> getTreePermissionMap() {
/* 437 */     Map<String, Boolean> result = new HashMap<>();
/* 438 */     for (CardPermissionVO vo : getTreeCheckedVO()) {
/* 439 */       result.put(vo.getId(), Boolean.valueOf(true));
/*     */     }
/* 441 */     for (CardPermissionVO vo : getTreeUncheckVO()) {
/* 442 */       result.put(vo.getId(), Boolean.valueOf(false));
/*     */     }
/* 444 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<CardPermissionVO> getTreeCheckedVO() {
/* 449 */     List<CardPermissionVO> checkedTreeVoList = new ArrayList<>(this.tree.getCheckedSelection());
/* 450 */     List<CardPermissionVO> removeList = new ArrayList<>();
/* 451 */     Iterator<CardPermissionVO> checkTreeIt = checkedTreeVoList.iterator();
/* 452 */     while (checkTreeIt.hasNext()) {
/* 453 */       CardPermissionVO vo = checkTreeIt.next();
/* 454 */       if (vo.getId() == null || this.lineNames.contains(vo.getId()) || vo.getId().equals("root"))
/*     */       {
/* 456 */         removeList.add(vo);
/*     */       }
/*     */     } 
/* 459 */     GWT.log("get allchecked tree VO list: " + checkedTreeVoList);
/* 460 */     checkedTreeVoList.removeAll(removeList);
/* 461 */     return checkedTreeVoList;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<CardPermissionVO> getTreeUncheckVO() {
/* 466 */     List<CardPermissionVO> allTreeVoList = new ArrayList<>(this.treeStore.getAll());
/* 467 */     List<CardPermissionVO> checkedTreeVoList = new ArrayList<>(this.tree.getCheckedSelection());
/* 468 */     List<CardPermissionVO> removeList = new ArrayList<>();
/* 469 */     Iterator<CardPermissionVO> allTreeIt = allTreeVoList.iterator();
/*     */     
/* 471 */     while (allTreeIt.hasNext()) {
/* 472 */       CardPermissionVO vo = allTreeIt.next();
/* 473 */       if (vo.getId() == null || this.lineNames.contains(vo.getId()) || vo.getId().equals("root")) {
/* 474 */         removeList.add(vo);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 479 */     allTreeVoList.removeAll(removeList);
/* 480 */     allTreeVoList.removeAll(checkedTreeVoList);
/* 481 */     return allTreeVoList;
/*     */   }
/*     */   
/*     */   public void initLineData(List<String> response) {
/* 485 */     for (String lineName : response) {
/* 486 */       this.treeStore.add(this.root, new CardPermissionVO(lineName, lineName));
/* 487 */       this.lineNames.add(lineName);
/*     */     } 
/* 489 */     this.presenter.getNcuTreeData();
/*     */   }
/*     */   
/*     */   public void fillTreeData(List<NcuConfigDTO> response) {
/* 493 */     for (NcuConfigDTO dto : response) {
/* 494 */       if (dto.getId() == null) {
/*     */         continue;
/*     */       }
/* 497 */       CardPermissionVO parent = (CardPermissionVO)this.treeStore.findModelWithKey(dto.getLineName());
/* 498 */       this.treeStore.add(parent, new CardPermissionVO(dto.getId(), dto.getName()));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillTreeCheckValue(List<RoomCardReaderMappingConfigDTO> cardReaderMappingDtos) {
/* 504 */     for (CardPermissionVO vo : this.tree.getCheckedSelection()) {
/* 505 */       this.tree.setChecked(vo, Tree.CheckState.UNCHECKED);
/*     */     }
/* 507 */     List<String> cardReaderDeviceName = new ArrayList<>();
/* 508 */     for (RoomCardReaderMappingConfigDTO dto : cardReaderMappingDtos) {
/* 509 */       if (dto.getLoginCardReader().booleanValue()) {
/* 510 */         cardReaderDeviceName.add(dto.getReaderId());
/*     */       }
/*     */     } 
/* 513 */     for (CardPermissionVO vo : this.treeStore.getAll()) {
/* 514 */       if (vo.getId() != null && cardReaderDeviceName != null && cardReaderDeviceName
/*     */         
/* 516 */         .contains(vo.getId())) {
/* 517 */         this.tree.setChecked(vo, Tree.CheckState.CHECKED);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public class CardPermissionVO
/*     */   {
/*     */     private String id;
/*     */     private String name;
/*     */     
/*     */     public CardPermissionVO(String id, String name) {
/* 528 */       this.id = id;
/* 529 */       this.name = name;
/*     */     }
/*     */     
/*     */     public String getId() {
/* 533 */       return this.id;
/*     */     }
/*     */     
/*     */     public void setId(String id) {
/* 537 */       this.id = id;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 541 */       return this.name;
/*     */     }
/*     */     
/*     */     public void setLocName(String name) {
/* 545 */       this.name = name;
/*     */     }
/*     */   }
/*     */   
/*     */   public void showAddCardFailDialog(String failMsg) {
/* 550 */     if (failMsg != null && this.addCardFailDialog == null) {
/* 551 */       initAddCardFailDialog(failMsg);
/*     */     }
/* 553 */     this.addCardFailDialog.show();
/*     */   }
/*     */   
/*     */   private void initAddCardFailDialog(String failMsg) {
/* 557 */     this.addCardFailDialog = new Dialog();
/* 558 */     this.addCardFailDialog.setHeading("結果通知");
/* 559 */     this.addCardFailDialog.setHeight(300);
/* 560 */     this.addCardFailDialog.setWidth(400);
/* 561 */     this.addCardFailDialog.setModal(true);
/* 562 */     Label label = new Label();
/* 563 */     StringBuilder failMessage = new StringBuilder();
/* 564 */     if (failMsg != null && !failMsg.isEmpty()) {
/* 565 */       label.setHeight("1");
/* 566 */       label.setWidth("1");
/* 567 */       failMessage.append(failMsg);
/* 568 */       label.setText(failMessage.toString());
/* 569 */       this.addCardFailDialog.add((Widget)label);
/*     */     } 
/*     */     
/* 572 */     TextButton cancel = new TextButton();
/* 573 */     cancel.setText("確定");
/* 574 */     cancel.addSelectHandler(new SelectEvent.SelectHandler()
/*     */         {
/*     */           public void onSelect(SelectEvent event)
/*     */           {
/* 578 */             NcuCardControlViewer.this.addCardFailDialog.hide();
/*     */           }
/*     */         });
/* 581 */     this.addCardFailDialog.getButtonBar().clear();
/* 582 */     this.addCardFailDialog.getButtonBar().add((Widget)cancel);
/*     */     
/* 584 */     this.addCardFailDialog.addHideHandler(new HideEvent.HideHandler()
/*     */         {
/*     */           public void onHide(HideEvent event)
/*     */           {
/* 588 */             NcuCardControlViewer.this.addCardFailDialog = null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void fillRoomCardConfig(List<RoomCardConfigDTO> result) {
/* 594 */     this.listStore.clear();
/* 595 */     List<RoomCardConfigDTO> enableCards = new ArrayList<>();
/* 596 */     for (RoomCardConfigDTO dto : result) {
/* 597 */       if (dto.getCardStatus().equals(CardStatus.ENABLE)) {
/* 598 */         enableCards.add(dto);
/*     */       }
/*     */     } 
/* 601 */     this.listStore.addAll(enableCards);
/* 602 */     unmask();
/*     */   }
/*     */   
/*     */   public void fillRoomCardGroup(List<RoomCardGroupConfigDTO> cardGroupList) {
/* 606 */     this.queryGroupCbStore.clear();
/* 607 */     if (cardGroupList != null && cardGroupList.size() > 0) {
/* 608 */       RoomCardGroupConfigDTO dto = new RoomCardGroupConfigDTO();
/* 609 */       Long value = Long.valueOf(0L);
/* 610 */       dto.setId(value);
/* 611 */       dto.setName("全部");
/* 612 */       cardGroupList.add(0, dto);
/* 613 */       this.queryGroupCbStore.addAll(cardGroupList);
/* 614 */       this.queryGroupComboBox.setValue(this.queryGroupCbStore.get(0));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void refresh() {
/* 619 */     this.clientFactory.getEventBus().fireEvent((GwtEvent)new RoomControlEvent(RoomControlEvent.Action.GET));
/*     */   }
/*     */   
/*     */   class DefaultRoomConfigDataEventHandler
/*     */     implements RoomConfigDataEvent.RoomConfigDataEventHandler
/*     */   {
/*     */     public void onGet(RoomConfigDataEvent event) {
/* 626 */       NcuCardControlViewer.this.presenter.getRoomCardConfig();
/*     */     }
/*     */   }
/*     */   
/*     */   @UiHandler({"exportButton"})
/*     */   void exportButton(SelectEvent se) {
/* 632 */     if (this.listStore.size() == 0) {
/* 633 */       Info.display("無內容", "不進行匯出");
/*     */       
/*     */       return;
/*     */     } 
/* 637 */     List<List<String>> csvData = new ArrayList<>();
/*     */ 
/*     */     
/* 640 */     List<String> title = new ArrayList<>();
/* 641 */     title.add("卡片權限");
/* 642 */     csvData.add(title);
/*     */ 
/*     */     
/* 645 */     List<String> conditions = new ArrayList<>();
/* 646 */     conditions.add("匯出時間:");
/* 647 */     Date time = new Date();
/* 648 */     DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
/*     */     try {
/* 650 */       conditions.add(format.format(time));
/* 651 */     } catch (Exception e) {
/* 652 */       GWT.log("Transfer date failed.", e);
/*     */     } 
/* 654 */     csvData.add(conditions);
/*     */ 
/*     */     
/* 657 */     List<String> header = new ArrayList<>();
/* 658 */     for (ColumnConfig<RoomCardConfigDTO, ?> config : (Iterable<ColumnConfig<RoomCardConfigDTO, ?>>)this.grid.getColumnModel().getColumns()) {
/* 659 */       if (!config.isHidden()) {
/* 660 */         header.add(config.getHeader().asString());
/*     */       }
/*     */     } 
/* 663 */     csvData.add(header);
/*     */ 
/*     */     
/* 666 */     List<RoomCardConfigDTO> infoData = this.listStore.getAll();
/* 667 */     for (RoomCardConfigDTO item : infoData) {
/* 668 */       List<String> csvDatum = new ArrayList<>();
/* 669 */       for (ColumnConfig<RoomCardConfigDTO, ?> config : (Iterable<ColumnConfig<RoomCardConfigDTO, ?>>)this.grid.getColumnModel().getColumns()) {
/* 670 */         if (!config.isHidden()) {
/* 671 */           csvDatum.add(
/* 672 */               (config.getValueProvider().getValue(item) == null) ? "" : 
/*     */               
/* 674 */               String.valueOf(config.getValueProvider().getValue(item)));
/*     */         }
/*     */       } 
/* 677 */       csvData.add(csvDatum);
/*     */     } 
/* 679 */     ExportCsvFile.exportAsCsv("卡片權限.csv", csvData);
/*     */   }
/*     */   
/*     */   static interface RoomCardConfigPropertyAccess extends PropertyAccess<RoomCardConfigDTO> {
/*     */     ModelKeyProvider<RoomCardConfigDTO> id();
/*     */     
/*     */     ValueProvider<RoomCardConfigDTO, String> company();
/*     */     
/*     */     ValueProvider<RoomCardConfigDTO, String> name();
/*     */     
/*     */     ValueProvider<RoomCardConfigDTO, String> tel();
/*     */     
/*     */     ValueProvider<RoomCardConfigDTO, String> groupName();
/*     */     
/*     */     ValueProvider<RoomCardConfigDTO, String> aba();
/*     */     
/*     */     ValueProvider<RoomCardConfigDTO, Date> cardStartDate();
/*     */     
/*     */     ValueProvider<RoomCardConfigDTO, Date> cardEndDate();
/*     */   }
/*     */   
/*     */   static interface NcuCardControlViewerUiBinder extends UiBinder<Widget, NcuCardControlViewer> {}
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\NcuCardControlViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */