/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.event.dom.client.KeyUpEvent;
/*     */ import com.google.gwt.event.logical.shared.SelectionEvent;
/*     */ import com.google.gwt.event.shared.EventHandler;
/*     */ import com.google.gwt.event.shared.GwtEvent;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.uibinder.client.UiHandler;
/*     */ import com.google.gwt.user.client.Timer;
/*     */ import com.google.gwt.user.client.ui.Label;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.event.RoomConfigDataEvent;
/*     */ import com.hwacom.ngtms.ao.am.event.RoomControlEvent;
/*     */ import com.hwacom.ngtms.ao.am.presenter.NcuCardConfigSettingPresenter;
/*     */ import com.hwacom.ngtms.cam.client.ui.AmTab;
/*     */ import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
/*     */ import com.hwacom.ngtms.room.am.util.StringConverter;
/*     */ import com.hwacom.ngtms.room.shared.CardStatus;
/*     */ import com.hwacom.ngtms.room.shared.dto.RoomCardConfigDTO;
/*     */ import com.hwacom.ngtms.room.shared.dto.RoomCardGroupConfigDTO;
/*     */ import com.hwacom.ngtms.room.shared.dto.RoomCardIssueParam;
/*     */ import com.sencha.gxt.core.client.Style;
/*     */ import com.sencha.gxt.core.client.ValueProvider;
/*     */ import com.sencha.gxt.data.shared.LabelProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.data.shared.Store;
/*     */ import com.sencha.gxt.widget.core.client.Dialog;
/*     */ import com.sencha.gxt.widget.core.client.button.TextButton;
/*     */ import com.sencha.gxt.widget.core.client.button.ToolButton;
/*     */ import com.sencha.gxt.widget.core.client.event.HideEvent;
/*     */ import com.sencha.gxt.widget.core.client.event.RowClickEvent;
/*     */ import com.sencha.gxt.widget.core.client.event.SelectEvent;
/*     */ import com.sencha.gxt.widget.core.client.form.ComboBox;
/*     */ import com.sencha.gxt.widget.core.client.form.DateField;
/*     */ import com.sencha.gxt.widget.core.client.form.TextField;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*     */ import com.sencha.gxt.widget.core.client.grid.Grid;
/*     */ import com.sencha.gxt.widget.core.client.info.Info;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public class NcuCardConfigSettingViewer
/*     */   extends AmTab
/*     */ {
/*  52 */   private static NcuCardConfigSettingViewerUiBinder uiBinder = (NcuCardConfigSettingViewerUiBinder)GWT.create(NcuCardConfigSettingViewerUiBinder.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private RoomCardConfigPropertyAccess roomCardConfigPropertyAccess = (RoomCardConfigPropertyAccess)GWT.create(RoomCardConfigPropertyAccess.class);
/*     */   
/*  60 */   private NcuCardConfigSettingPresenter presenter = new NcuCardConfigSettingPresenter(this);
/*     */   
/*  62 */   private final ClientFactory clientFactory = (ClientFactory)GWT.create(ClientFactory.class);
/*     */   
/*     */   private Dialog createCardDialog;
/*     */   
/*     */   private Store.StoreFilter<RoomCardConfigDTO> abafilter;
/*     */   
/*     */   private Store.StoreFilter<RoomCardConfigDTO> issuefilter;
/*     */   
/*     */   @UiField
/*     */   Label aba;
/*     */   
/*     */   @UiField
/*     */   TextField name;
/*     */   
/*     */   @UiField
/*     */   TextField company;
/*     */   
/*     */   @UiField
/*     */   TextField tel;
/*     */   
/*     */   @UiField
/*     */   DateField startDate;
/*     */   
/*     */   @UiField
/*     */   DateField endDate;
/*     */   
/*     */   @UiField
/*     */   TextField memo;
/*     */   
/*     */   @UiField
/*     */   Grid<RoomCardConfigDTO> grid;
/*     */   @UiField(provided = true)
/*     */   public ListStore<RoomCardConfigDTO> listStore;
/*     */   @UiField(provided = true)
/*     */   ColumnModel<RoomCardConfigDTO> columnModel;
/*     */   @UiField
/*     */   TextField queryAba;
/*     */   @UiField
/*     */   ComboBox<String> queryIssueComboBox;
/*     */   @UiField(provided = true)
/*     */   ListStore<String> queryIssueCbStore;
/*     */   @UiField(provided = true)
/*     */   LabelProvider<String> queryIssueCbLabelProvider;
/*     */   @UiField
/*     */   ComboBox<RoomCardGroupConfigDTO> cardGroupComboBox;
/*     */   @UiField(provided = true)
/*     */   ListStore<RoomCardGroupConfigDTO> cardGroupCbStore;
/*     */   @UiField(provided = true)
/*     */   LabelProvider<RoomCardGroupConfigDTO> cardGroupCbLabelProvider;
/*     */   @UiField
/*     */   ComboBox<CardStatus> cardStatusComboBox;
/*     */   @UiField(provided = true)
/*     */   ListStore<CardStatus> cardStatusCbStore;
/*     */   @UiField(provided = true)
/*     */   LabelProvider<CardStatus> cardStatusCbLabelProvider;
/*     */   @UiField
/*     */   ToolButton createCard;
/*     */   @UiField
/*     */   TextButton save;
/*     */   
/*     */   public NcuCardConfigSettingViewer() {
/* 123 */     initStoreAndProvider();
/* 124 */     initColumnModel();
/* 125 */     initFilter();
/* 126 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/* 127 */     this.grid.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
/* 128 */     fillQueryIssue();
/* 129 */     fillCardStatusCb();
/* 130 */     this.clientFactory
/* 131 */       .getEventBus()
/* 132 */       .addHandler(RoomControlEvent.TYPE, (EventHandler)new DefaultRoomControlEventHandler());
/*     */   }
/*     */   
/*     */   private void initStoreAndProvider() {
/* 136 */     this.listStore = new ListStore(new ModelKeyProvider<RoomCardConfigDTO>()
/*     */         {
/*     */ 
/*     */           
/*     */           public String getKey(RoomCardConfigDTO item)
/*     */           {
/* 142 */             return item.getId();
/*     */           }
/*     */         });
/*     */     
/* 146 */     this.queryIssueCbStore = new ListStore(new ModelKeyProvider<String>()
/*     */         {
/*     */ 
/*     */           
/*     */           public String getKey(String item)
/*     */           {
/* 152 */             return item;
/*     */           }
/*     */         });
/*     */     
/* 156 */     this.cardGroupCbStore = new ListStore(new ModelKeyProvider<RoomCardGroupConfigDTO>()
/*     */         {
/*     */ 
/*     */           
/*     */           public String getKey(RoomCardGroupConfigDTO item)
/*     */           {
/* 162 */             return item.getId().toString();
/*     */           }
/*     */         });
/*     */     
/* 166 */     this.queryIssueCbLabelProvider = new LabelProvider<String>()
/*     */       {
/*     */         
/*     */         public String getLabel(String item)
/*     */         {
/* 171 */           return item;
/*     */         }
/*     */       };
/*     */     
/* 175 */     this.cardGroupCbLabelProvider = new LabelProvider<RoomCardGroupConfigDTO>()
/*     */       {
/*     */         
/*     */         public String getLabel(RoomCardGroupConfigDTO item)
/*     */         {
/* 180 */           return item.getName();
/*     */         }
/*     */       };
/*     */     
/* 184 */     this.cardStatusCbStore = new ListStore(new ModelKeyProvider<CardStatus>()
/*     */         {
/*     */ 
/*     */           
/*     */           public String getKey(CardStatus item)
/*     */           {
/* 190 */             return item.name();
/*     */           }
/*     */         });
/*     */     
/* 194 */     this.cardStatusCbLabelProvider = new LabelProvider<CardStatus>()
/*     */       {
/*     */         
/*     */         public String getLabel(CardStatus item)
/*     */         {
/* 199 */           return StringConverter.getCardStatus(item);
/*     */         }
/*     */       };
/* 202 */     this.listStore = new ListStore(this.roomCardConfigPropertyAccess.id());
/*     */   }
/*     */   
/*     */   private void initColumnModel() {
/* 206 */     List<ColumnConfig<RoomCardConfigDTO, ?>> ColumnConfigs = new ArrayList<>();
/*     */ 
/*     */     
/* 209 */     ColumnConfig<RoomCardConfigDTO, String> abaColumn = new ColumnConfig(this.roomCardConfigPropertyAccess.aba(), 100, "卡片編號");
/* 210 */     ColumnConfigs.add(abaColumn);
/*     */ 
/*     */     
/* 213 */     ColumnConfig<RoomCardConfigDTO, String> NameColumn = new ColumnConfig(this.roomCardConfigPropertyAccess.name(), 50, "姓名");
/* 214 */     ColumnConfigs.add(NameColumn);
/*     */     
/* 216 */     ColumnConfig<RoomCardConfigDTO, String> cardStatusColumn = new ColumnConfig(new ValueProvider<RoomCardConfigDTO, String>()
/*     */         {
/*     */ 
/*     */           
/*     */           public String getValue(RoomCardConfigDTO object)
/*     */           {
/* 222 */             return StringConverter.getCardStatus(object.getCardStatus());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(RoomCardConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 230 */             return "cardStatusColumn";
/*     */           }
/*     */         },  60, "卡片狀態");
/*     */ 
/*     */     
/* 235 */     ColumnConfigs.add(cardStatusColumn);
/*     */     
/* 237 */     ColumnConfig<RoomCardConfigDTO, String> issuedColumn = new ColumnConfig(new ValueProvider<RoomCardConfigDTO, String>()
/*     */         {
/*     */ 
/*     */           
/*     */           public String getValue(RoomCardConfigDTO object)
/*     */           {
/* 243 */             return StringConverter.getYesOrNo(object.getIssued());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(RoomCardConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 251 */             return "issuedColumn";
/*     */           }
/*     */         },  75, "是否發卡");
/*     */ 
/*     */     
/* 256 */     ColumnConfigs.add(issuedColumn);
/*     */     
/* 258 */     ColumnConfig<RoomCardConfigDTO, String> issueDateColumn = new ColumnConfig(new ValueProvider<RoomCardConfigDTO, String>()
/*     */         {
/*     */ 
/*     */           
/*     */           public String getValue(RoomCardConfigDTO object)
/*     */           {
/* 264 */             if (object.getIssueDate() == null) {
/* 265 */               return "";
/*     */             }
/* 267 */             return StringConverter.getTimeyyyyMMdd(object.getIssueDate());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(RoomCardConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 275 */             return "issueDateColumn";
/*     */           }
/*     */         },  80, "發卡日期");
/*     */ 
/*     */     
/* 280 */     ColumnConfigs.add(issueDateColumn);
/*     */     
/* 282 */     ColumnConfig<RoomCardConfigDTO, String> dateValueColumn = new ColumnConfig(new ValueProvider<RoomCardConfigDTO, String>()
/*     */         {
/*     */ 
/*     */           
/*     */           public String getValue(RoomCardConfigDTO object)
/*     */           {
/* 288 */             if (object.getCardStartDate() == null || object.getCardEndDate() == null) {
/* 289 */               return "";
/*     */             }
/* 291 */             return StringConverter.getTimeyyyyMMdd(object.getCardStartDate()) + "~" + 
/*     */               
/* 293 */               StringConverter.getTimeyyyyMMdd(object.getCardEndDate());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(RoomCardConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 301 */             return null;
/*     */           }
/*     */         },  200, "有效時間");
/*     */ 
/*     */     
/* 306 */     ColumnConfigs.add(dateValueColumn);
/*     */     
/* 308 */     this.columnModel = new ColumnModel(ColumnConfigs);
/*     */   }
/*     */   
/*     */   private void fillQueryIssue() {
/* 312 */     this.queryIssueCbStore.add("全部");
/* 313 */     this.queryIssueCbStore.add("是");
/* 314 */     this.queryIssueCbStore.add("否");
/* 315 */     this.queryIssueComboBox.setValue(this.queryIssueCbStore.get(0));
/*     */   }
/*     */   
/*     */   private void fillCardStatusCb() {
/* 319 */     this.cardStatusCbStore.add(CardStatus.ENABLE);
/* 320 */     this.cardStatusCbStore.add(CardStatus.DISABLE);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initFilter() {
/* 325 */     this.abafilter = new Store.StoreFilter<RoomCardConfigDTO>()
/*     */       {
/*     */         
/*     */         public boolean select(Store<RoomCardConfigDTO> store, RoomCardConfigDTO parent, RoomCardConfigDTO item)
/*     */         {
/* 330 */           return true;
/*     */         }
/*     */       };
/* 333 */     this.issuefilter = new Store.StoreFilter<RoomCardConfigDTO>()
/*     */       {
/*     */         
/*     */         public boolean select(Store<RoomCardConfigDTO> store, RoomCardConfigDTO parent, RoomCardConfigDTO item)
/*     */         {
/* 338 */           return true;
/*     */         }
/*     */       };
/* 341 */     this.listStore.addFilter(this.abafilter);
/* 342 */     this.listStore.addFilter(this.issuefilter);
/* 343 */     this.listStore.setEnableFilters(true);
/*     */   }
/*     */   
/*     */   public void setPresenter(NcuCardConfigSettingPresenter presenter) {
/* 347 */     this.presenter = presenter;
/*     */   }
/*     */   
/*     */   @UiHandler({"grid"})
/*     */   public void onGridSelect(RowClickEvent event) {
/* 352 */     RoomCardConfigDTO selectItem = (RoomCardConfigDTO)this.grid.getSelectionModel().getSelectedItem();
/* 353 */     if (selectItem.getGroupName() != null) {
/* 354 */       if (this.cardGroupCbStore.getAll() != null && this.cardGroupCbStore.getAll().size() > 0) {
/* 355 */         for (RoomCardGroupConfigDTO groupDto : this.cardGroupCbStore.getAll()) {
/* 356 */           if (selectItem.getGroupName().equals(groupDto.getName())) {
/* 357 */             this.cardGroupComboBox.setValue(groupDto);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } else {
/* 362 */       RoomCardGroupConfigDTO dto = (RoomCardGroupConfigDTO)this.cardGroupCbStore.findModelWithKey("1");
/* 363 */       if (dto != null) {
/* 364 */         this.cardGroupComboBox.setValue(dto);
/*     */       } else {
/* 366 */         this.cardGroupComboBox.setValue(this.cardGroupCbStore.get(0));
/*     */       } 
/*     */     } 
/* 369 */     this.aba.setText(selectItem.getAba());
/* 370 */     this.cardStatusComboBox.setValue(selectItem.getCardStatus());
/* 371 */     this.name.setValue(selectItem.getName());
/* 372 */     this.company.setValue(selectItem.getCompany());
/* 373 */     this.tel.setValue(selectItem.getTel());
/* 374 */     if (selectItem.getCardStartDate() != null) {
/* 375 */       this.startDate.setValue(selectItem.getCardStartDate());
/*     */     } else {
/* 377 */       this.startDate.setValue(null);
/*     */     } 
/* 379 */     if (selectItem.getCardEndDate() != null) {
/* 380 */       this.endDate.setValue(selectItem.getCardEndDate());
/*     */     } else {
/* 382 */       this.endDate.setValue(null);
/*     */     } 
/* 384 */     this.memo.setValue(selectItem.getMemo());
/*     */   }
/*     */   
/*     */   @UiHandler({"queryAba"})
/*     */   public void onQueryAba(KeyUpEvent event) {
/* 389 */     Timer timer = new Timer()
/*     */       {
/*     */         public void run() {}
/*     */       };
/*     */     
/* 394 */     timer.schedule(500);
/* 395 */     final String aba = (String)this.queryAba.getCurrentValue();
/* 396 */     if (aba == null || aba.equals("")) {
/* 397 */       GWT.log("aba == null");
/* 398 */       this.listStore.removeFilter(this.abafilter);
/*     */     } else {
/* 400 */       GWT.log("aba != null, aba: " + aba);
/*     */       
/* 402 */       this.listStore.removeFilter(this.abafilter);
/* 403 */       this.abafilter = new Store.StoreFilter<RoomCardConfigDTO>()
/*     */         {
/*     */           
/*     */           public boolean select(Store<RoomCardConfigDTO> store, RoomCardConfigDTO parent, RoomCardConfigDTO item)
/*     */           {
/* 408 */             return item.getAba().contains(aba);
/*     */           }
/*     */         };
/* 411 */       this.listStore.addFilter(this.abafilter);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @UiHandler({"queryIssueComboBox"})
/*     */   public void onQueryIssue(SelectionEvent<String> event) {
/* 418 */     final Boolean issue = StringConverter.getYesOrNoBoolean((String)event.getSelectedItem());
/* 419 */     if (issue == null) {
/* 420 */       GWT.log("issue == null, remove issueFilter");
/* 421 */       this.listStore.removeFilter(this.issuefilter);
/*     */     } else {
/* 423 */       this.listStore.removeFilter(this.issuefilter);
/* 424 */       this.issuefilter = new Store.StoreFilter<RoomCardConfigDTO>()
/*     */         {
/*     */           
/*     */           public boolean select(Store<RoomCardConfigDTO> store, RoomCardConfigDTO parent, RoomCardConfigDTO item)
/*     */           {
/* 429 */             return (issue == item.getIssued());
/*     */           }
/*     */         };
/* 432 */       this.listStore.addFilter(this.issuefilter);
/* 433 */       GWT.log("issue != null,update issueFilter");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @UiHandler({"save"})
/*     */   public void onSave(SelectEvent event) {
/* 440 */     RoomCardConfigDTO dto = (RoomCardConfigDTO)this.grid.getSelectionModel().getSelectedItem();
/* 441 */     if (dto == null) {
/* 442 */       Info.display("訊息提醒", "請選擇一張卡片");
/*     */       return;
/*     */     } 
/* 445 */     mask("資料儲存中...");
/* 446 */     RoomCardIssueParam issueParam = new RoomCardIssueParam();
/* 447 */     RoomCardConfigDTO issueCard = new RoomCardConfigDTO();
/* 448 */     issueCard.setId(dto.getId());
/* 449 */     issueCard.setAba(dto.getAba());
/* 450 */     issueCard.setIssued(dto.getIssued());
/* 451 */     issueCard.setIssueDate(dto.getIssueDate());
/* 452 */     if (this.cardGroupComboBox.getCurrentValue() != null) {
/* 453 */       issueCard.setGroupName(((RoomCardGroupConfigDTO)this.cardGroupComboBox.getCurrentValue()).getName());
/*     */     }
/* 455 */     issueCard.setCardStatus((CardStatus)this.cardStatusComboBox.getCurrentValue());
/* 456 */     issueCard.setMemo(this.memo.getText());
/* 457 */     issueCard.setCompany(this.company.getText());
/* 458 */     issueCard.setName(this.name.getText());
/* 459 */     issueCard.setTel(this.tel.getText());
/* 460 */     issueCard.setCardStartDate((Date)this.startDate.getCurrentValue());
/* 461 */     issueCard.setCardEndDate((Date)this.endDate.getCurrentValue());
/* 462 */     issueParam.setRoomCardConfigDto(issueCard);
/* 463 */     this.presenter.saveCardConfig(issueParam);
/*     */   }
/*     */   
/*     */   @UiHandler({"createCard"})
/*     */   public void onCreateCard(SelectEvent event) {
/* 468 */     if (this.createCardDialog == null) {
/* 469 */       initCreateCardDialog();
/*     */     }
/* 471 */     this.createCardDialog.show();
/*     */   }
/*     */   
/*     */   private void initCreateCardDialog() {
/* 475 */     this.createCardDialog = new Dialog();
/* 476 */     this.createCardDialog.setHeading("新增卡片");
/* 477 */     this.createCardDialog.setHeight(100);
/* 478 */     this.createCardDialog.setWidth(250);
/* 479 */     this.createCardDialog.setModal(true);
/* 480 */     final RoomCardCreateWidget widget = new RoomCardCreateWidget();
/* 481 */     this.createCardDialog.add((Widget)widget);
/*     */     
/* 483 */     TextButton createButton = new TextButton();
/* 484 */     createButton.setText("新增");
/* 485 */     createButton.addSelectHandler(new SelectEvent.SelectHandler()
/*     */         {
/*     */           public void onSelect(SelectEvent event)
/*     */           {
/* 489 */             String newAba = (String)widget.aba.getCurrentValue();
/* 490 */             if (newAba == null || newAba.length() != 10) {
/* 491 */               Info.display("新增卡片失敗", "卡號需為10碼");
/*     */               return;
/*     */             } 
/* 494 */             Boolean abaDuplicate = Boolean.valueOf(false);
/* 495 */             for (RoomCardConfigDTO roomCardConfigDTO : NcuCardConfigSettingViewer.this.listStore.getAll()) {
/* 496 */               if (newAba.equals(roomCardConfigDTO.getAba())) {
/* 497 */                 abaDuplicate = Boolean.valueOf(true);
/*     */               }
/*     */             } 
/* 500 */             if (abaDuplicate.booleanValue()) {
/* 501 */               Info.display("新增卡片失敗", "卡號已重複");
/*     */               return;
/*     */             } 
/* 504 */             RoomCardConfigDTO dto = new RoomCardConfigDTO();
/* 505 */             dto.setAba(newAba);
/* 506 */             NcuCardConfigSettingViewer.this.presenter.createRoomCardConfig(dto);
/*     */           }
/*     */         });
/*     */     
/* 510 */     TextButton cancel = new TextButton();
/* 511 */     cancel.setText("取消");
/* 512 */     cancel.addSelectHandler(new SelectEvent.SelectHandler()
/*     */         {
/*     */           public void onSelect(SelectEvent event)
/*     */           {
/* 516 */             NcuCardConfigSettingViewer.this.createCardDialog.hide();
/*     */           }
/*     */         });
/* 519 */     this.createCardDialog.getButtonBar().clear();
/* 520 */     this.createCardDialog.getButtonBar().add((Widget)createButton);
/* 521 */     this.createCardDialog.getButtonBar().add((Widget)cancel);
/*     */     
/* 523 */     this.createCardDialog.addHideHandler(new HideEvent.HideHandler()
/*     */         {
/*     */           public void onHide(HideEvent event)
/*     */           {
/* 527 */             NcuCardConfigSettingViewer.this.createCardDialog = null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void hideCreateDialog() {
/* 533 */     if (this.createCardDialog != null) {
/* 534 */       this.createCardDialog.hide();
/*     */     }
/*     */   }
/*     */   
/*     */   public void fillRoomCardConfig(List<RoomCardConfigDTO> result) {
/* 539 */     this.listStore.clear();
/* 540 */     this.listStore.addAll(result);
/* 541 */     unmask();
/*     */   }
/*     */   
/*     */   public void fillRoomCardGroup(List<RoomCardGroupConfigDTO> cardGroupList) {
/* 545 */     this.cardGroupCbStore.clear();
/* 546 */     this.cardGroupCbStore.addAll(cardGroupList);
/*     */   }
/*     */   
/*     */   public void refresh() {
/* 550 */     this.clientFactory.getEventBus().fireEvent((GwtEvent)new RoomConfigDataEvent(RoomConfigDataEvent.Action.GET));
/*     */   }
/*     */   
/*     */   static interface NcuCardConfigSettingViewerUiBinder extends UiBinder<Widget, NcuCardConfigSettingViewer> {}
/*     */   
/*     */   class DefaultRoomControlEventHandler implements RoomControlEvent.RoomControlEventHandler {
/*     */     public void onGet(RoomControlEvent event) {
/* 557 */       NcuCardConfigSettingViewer.this.presenter.getRoomCardConfig();
/*     */     }
/*     */   }
/*     */   
/*     */   static interface RoomCardConfigPropertyAccess extends PropertyAccess<RoomCardConfigDTO> {
/*     */     ModelKeyProvider<RoomCardConfigDTO> id();
/*     */     
/*     */     ValueProvider<RoomCardConfigDTO, String> aba();
/*     */     
/*     */     ValueProvider<RoomCardConfigDTO, String> groupName();
/*     */     
/*     */     ValueProvider<RoomCardConfigDTO, String> company();
/*     */     
/*     */     ValueProvider<RoomCardConfigDTO, String> name();
/*     */     
/*     */     ValueProvider<RoomCardConfigDTO, String> tel();
/*     */     
/*     */     ValueProvider<RoomCardConfigDTO, String> memo();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\NcuCardConfigSettingViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */