/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.uibinder.client.UiHandler;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.presenter.RoomDeviceConfigSettingPresenter;
/*     */ import com.hwacom.ngtms.c.shared.dto.DeviceTypeDTO;
/*     */ import com.hwacom.ngtms.cam.client.ui.AmTab;
/*     */ import com.hwacom.ngtms.room.am.util.StringConverter;
/*     */ import com.hwacom.ngtms.room.shared.SignalType;
/*     */ import com.hwacom.ngtms.room.shared.dto.RoomDeviceConfigDTO;
/*     */ import com.hwacom.ngtms.room.shared.dto.RoomDeviceSubLocationConfigDTO;
/*     */ import com.sencha.gxt.core.client.Style;
/*     */ import com.sencha.gxt.core.client.ValueProvider;
/*     */ import com.sencha.gxt.data.shared.LabelProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.widget.core.client.event.RowClickEvent;
/*     */ import com.sencha.gxt.widget.core.client.event.SelectEvent;
/*     */ import com.sencha.gxt.widget.core.client.form.ComboBox;
/*     */ import com.sencha.gxt.widget.core.client.form.IntegerField;
/*     */ import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
/*     */ import com.sencha.gxt.widget.core.client.form.TextField;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*     */ import com.sencha.gxt.widget.core.client.grid.Grid;
/*     */ import com.sencha.gxt.widget.core.client.grid.GridView;
/*     */ import com.sencha.gxt.widget.core.client.info.Info;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class RoomDeviceConfigSettingViewer
/*     */   extends AmTab
/*     */ {
/*  40 */   private static RoomDeviceConfigSettingViewerUiBinder uiBinder = (RoomDeviceConfigSettingViewerUiBinder)GWT.create(RoomDeviceConfigSettingViewerUiBinder.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   private static GridProperties props = (GridProperties)GWT.create(GridProperties.class);
/*     */ 
/*     */   
/*  48 */   private final RoomDeviceConfigPropertyAccess propertyAccess = (RoomDeviceConfigPropertyAccess)GWT.create(RoomDeviceConfigPropertyAccess.class);
/*     */   
/*  50 */   private RoomDeviceConfigSettingPresenter presenter = new RoomDeviceConfigSettingPresenter(this);
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<RoomDeviceSubLocationConfigDTO> store;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ColumnModel<RoomDeviceSubLocationConfigDTO> cm;
/*     */   
/*     */   @UiField
/*     */   GridView<RoomDeviceSubLocationConfigDTO> view;
/*     */   
/*     */   @UiField
/*     */   Grid<RoomDeviceSubLocationConfigDTO> grid;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<RoomDeviceConfigDTO> deviceStore;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ColumnModel<RoomDeviceConfigDTO> column;
/*     */   
/*     */   @UiField
/*     */   GridView<RoomDeviceConfigDTO> gridView;
/*     */   
/*     */   @UiField
/*     */   Grid<RoomDeviceConfigDTO> deviceGrid;
/*     */   @UiField
/*     */   TextField displayName;
/*     */   @UiField
/*     */   TextField unit;
/*     */   @UiField
/*     */   IntegerField upperLimit;
/*     */   @UiField
/*     */   IntegerField lowerLimit;
/*     */   @UiField(provided = true)
/*     */   SimpleComboBox<String> enable;
/*     */   @UiField(provided = true)
/*     */   ListStore<DeviceTypeDTO> typeStore;
/*     */   @UiField(provided = true)
/*     */   LabelProvider<DeviceTypeDTO> typeProvider;
/*     */   @UiField
/*     */   ComboBox<DeviceTypeDTO> type;
/*  91 */   private String settedDeviceName = new String();
/*     */   
/*     */   public RoomDeviceConfigSettingViewer() {
/*  94 */     this.store = new ListStore(props.id());
/*  95 */     this.cm = genColumnModel();
/*  96 */     this.deviceStore = new ListStore(this.propertyAccess.deviceName());
/*  97 */     initColumnModel();
/*     */     
/*  99 */     this.typeStore = new ListStore(new ModelKeyProvider<DeviceTypeDTO>()
/*     */         {
/*     */           
/*     */           public String getKey(DeviceTypeDTO item)
/*     */           {
/* 104 */             return item.getId();
/*     */           }
/*     */         });
/*     */     
/* 108 */     this.typeProvider = new LabelProvider<DeviceTypeDTO>()
/*     */       {
/*     */         public String getLabel(DeviceTypeDTO item)
/*     */         {
/* 112 */           return item.getDescription();
/*     */         }
/*     */       };
/*     */     
/* 116 */     this.enable = new SimpleComboBox(new LabelProvider<String>()
/*     */         {
/*     */           
/*     */           public String getLabel(String item)
/*     */           {
/* 121 */             return item;
/*     */           }
/*     */         });
/*     */     
/* 125 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*     */     
/* 127 */     this.enable.add("是");
/* 128 */     this.enable.add("否");
/* 129 */     this.grid.setHideHeaders(true);
/* 130 */     this.grid.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
/* 131 */     this.deviceGrid.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
/* 132 */     this.presenter.initGridData();
/* 133 */     this.presenter.fiilTypeCB();
/*     */   }
/*     */   
/*     */   private void initColumnModel() {
/* 137 */     List<ColumnConfig<RoomDeviceConfigDTO, ?>> columnConfigList = new ArrayList<>();
/*     */ 
/*     */     
/* 140 */     ColumnConfig<RoomDeviceConfigDTO, String> displayName = new ColumnConfig(new ValueProvider<RoomDeviceConfigDTO, String>()
/*     */         {
/*     */           
/*     */           public String getValue(RoomDeviceConfigDTO dto)
/*     */           {
/* 145 */             return dto.getDisplayName();
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(RoomDeviceConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 153 */             return "displayName";
/*     */           }
/*     */         },  70, "監視點名稱");
/*     */ 
/*     */     
/* 158 */     displayName.setMenuDisabled(true);
/* 159 */     columnConfigList.add(displayName);
/*     */     
/* 161 */     ColumnConfig<RoomDeviceConfigDTO, String> typeDiscription = new ColumnConfig(new ValueProvider<RoomDeviceConfigDTO, String>()
/*     */         {
/*     */           
/*     */           public String getValue(RoomDeviceConfigDTO dto)
/*     */           {
/* 166 */             return dto.getTypeDiscription();
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(RoomDeviceConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 174 */             return "typeDiscription";
/*     */           }
/*     */         },  70, "監視點設備類別");
/*     */ 
/*     */     
/* 179 */     typeDiscription.setMenuDisabled(true);
/* 180 */     columnConfigList.add(typeDiscription);
/*     */     
/* 182 */     ColumnConfig<RoomDeviceConfigDTO, String> signalType = new ColumnConfig(new ValueProvider<RoomDeviceConfigDTO, String>()
/*     */         {
/*     */           
/*     */           public String getValue(RoomDeviceConfigDTO dto)
/*     */           {
/* 187 */             return StringConverter.getFormattedSignalType(dto.getSignalType());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(RoomDeviceConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 195 */             return "signalType";
/*     */           }
/*     */         },  70, "訊號種類");
/*     */ 
/*     */     
/* 200 */     signalType.setMenuDisabled(true);
/* 201 */     columnConfigList.add(signalType);
/*     */     
/* 203 */     ColumnConfig<RoomDeviceConfigDTO, String> unit = new ColumnConfig(new ValueProvider<RoomDeviceConfigDTO, String>()
/*     */         {
/*     */           
/*     */           public String getValue(RoomDeviceConfigDTO dto)
/*     */           {
/* 208 */             return dto.getUnit();
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(RoomDeviceConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 216 */             return "unit";
/*     */           }
/*     */         },  70, "單位");
/*     */ 
/*     */     
/* 221 */     unit.setMenuDisabled(true);
/* 222 */     columnConfigList.add(unit);
/*     */     
/* 224 */     ColumnConfig<RoomDeviceConfigDTO, String> upperLimit = new ColumnConfig(new ValueProvider<RoomDeviceConfigDTO, String>()
/*     */         {
/*     */           
/*     */           public String getValue(RoomDeviceConfigDTO dto)
/*     */           {
/* 229 */             if (dto.getUpperLimit().intValue() != 0) {
/* 230 */               return String.valueOf(dto.getUpperLimit());
/*     */             }
/* 232 */             return "";
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void setValue(RoomDeviceConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 241 */             return "upperLimit";
/*     */           }
/*     */         },  70, "上限值");
/*     */ 
/*     */     
/* 246 */     upperLimit.setMenuDisabled(true);
/* 247 */     columnConfigList.add(upperLimit);
/*     */     
/* 249 */     ColumnConfig<RoomDeviceConfigDTO, String> lowerLimit = new ColumnConfig(new ValueProvider<RoomDeviceConfigDTO, String>()
/*     */         {
/*     */           
/*     */           public String getValue(RoomDeviceConfigDTO dto)
/*     */           {
/* 254 */             if (dto.getLowerLimit().intValue() != 0) {
/* 255 */               return String.valueOf(dto.getLowerLimit());
/*     */             }
/* 257 */             return "";
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void setValue(RoomDeviceConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 266 */             return "lowerLimit";
/*     */           }
/*     */         },  70, "下限值");
/*     */ 
/*     */     
/* 271 */     lowerLimit.setMenuDisabled(true);
/* 272 */     columnConfigList.add(lowerLimit);
/*     */     
/* 274 */     ColumnConfig<RoomDeviceConfigDTO, String> alarmCheck = new ColumnConfig(new ValueProvider<RoomDeviceConfigDTO, String>()
/*     */         {
/*     */           
/*     */           public String getValue(RoomDeviceConfigDTO dto)
/*     */           {
/* 279 */             return (dto.getAlarmCheck().booleanValue() == true) ? "是" : "否";
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(RoomDeviceConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 287 */             return "alarmCheck";
/*     */           }
/*     */         },  70, "是否發出警報");
/*     */ 
/*     */     
/* 292 */     alarmCheck.setMenuDisabled(true);
/* 293 */     columnConfigList.add(alarmCheck);
/*     */     
/* 295 */     this.column = new ColumnModel(columnConfigList);
/*     */   }
/*     */   
/*     */   private ColumnModel<RoomDeviceSubLocationConfigDTO> genColumnModel() {
/* 299 */     List<ColumnConfig<RoomDeviceSubLocationConfigDTO, ?>> columnConfigList = new ArrayList<>();
/*     */     
/* 301 */     ColumnConfig<RoomDeviceSubLocationConfigDTO, String> locationName = new ColumnConfig(new ValueProvider<RoomDeviceSubLocationConfigDTO, String>()
/*     */         {
/*     */           
/*     */           public String getValue(RoomDeviceSubLocationConfigDTO dto)
/*     */           {
/* 306 */             if (dto.getSubLocation() != null && dto.getSubLocation() != "") {
/* 307 */               return dto.getLocationName() + "-" + dto.getSubLocation();
/*     */             }
/* 309 */             return dto.getLocationName();
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void setValue(RoomDeviceSubLocationConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 318 */             return "locationName";
/*     */           }
/*     */         },  100, "");
/*     */ 
/*     */     
/* 323 */     locationName.setMenuDisabled(true);
/* 324 */     columnConfigList.add(locationName);
/*     */     
/* 326 */     return new ColumnModel(columnConfigList);
/*     */   }
/*     */   
/*     */   @UiHandler({"grid"})
/*     */   public void onGridRowClick(RowClickEvent event) {
/* 331 */     RoomDeviceSubLocationConfigDTO selectedDto = (RoomDeviceSubLocationConfigDTO)this.grid.getSelectionModel().getSelectedItem();
/* 332 */     String subLocationName = null;
/* 333 */     if (selectedDto.getSubLocation().equals(null) || selectedDto.getSubLocation().equals("")) {
/* 334 */       subLocationName = null;
/*     */     } else {
/* 336 */       subLocationName = selectedDto.getSubLocation();
/*     */     } 
/* 338 */     this.presenter.fiilDeviceGridData(selectedDto.getLocationName(), subLocationName);
/*     */   }
/*     */   
/*     */   @UiHandler({"deviceGrid"})
/*     */   public void onRowClick(RowClickEvent event) {
/* 343 */     RoomDeviceConfigDTO selectedDevice = (RoomDeviceConfigDTO)this.deviceGrid.getSelectionModel().getSelectedItem();
/* 344 */     this.displayName.setValue(selectedDevice.getDisplayName());
/* 345 */     this.enable.setValue(selectedDevice.getAlarmCheck().booleanValue() ? "是" : "否");
/* 346 */     this.type.setValue(this.typeStore.findModelWithKey(selectedDevice.getType()));
/* 347 */     if (selectedDevice.getSignalType() == SignalType.DIGITAL_IN) {
/* 348 */       this.unit.disable();
/* 349 */       this.unit.clear();
/* 350 */       this.upperLimit.disable();
/* 351 */       this.upperLimit.clear();
/* 352 */       this.lowerLimit.disable();
/* 353 */       this.lowerLimit.clear();
/* 354 */     } else if (selectedDevice.getSignalType() == SignalType.ANALOG_IN) {
/* 355 */       this.unit.enable();
/* 356 */       this.unit.setAllowBlank(false);
/* 357 */       this.unit.setValue(selectedDevice.getUnit());
/* 358 */       this.upperLimit.enable();
/* 359 */       this.upperLimit.setAllowBlank(false);
/* 360 */       this.upperLimit.setValue(selectedDevice.getUpperLimit());
/* 361 */       this.lowerLimit.enable();
/* 362 */       this.lowerLimit.setAllowBlank(false);
/* 363 */       this.lowerLimit.setValue(selectedDevice.getLowerLimit());
/*     */     } 
/*     */   }
/*     */   
/*     */   @UiHandler({"saveButton"})
/*     */   public void onSelectSaveButton(SelectEvent event) {
/* 369 */     RoomDeviceConfigDTO selectedDevice = (RoomDeviceConfigDTO)this.deviceGrid.getSelectionModel().getSelectedItem();
/* 370 */     SignalType deviceSignalType = selectedDevice.getSignalType();
/* 371 */     if (checkField(deviceSignalType).booleanValue()) {
/* 372 */       Info.display("儲存失敗", "尚有欄位為空");
/*     */       return;
/*     */     } 
/* 375 */     selectedDevice.setDisplayName((String)this.displayName.getCurrentValue());
/* 376 */     selectedDevice.setType(((DeviceTypeDTO)this.type.getCurrentValue()).getId());
/* 377 */     selectedDevice.setAlarmCheck(Boolean.valueOf((this.enable.getCurrentValue() == "是")));
/* 378 */     if (deviceSignalType == SignalType.ANALOG_IN) {
/* 379 */       selectedDevice.setUpperLimit((Integer)this.upperLimit.getCurrentValue());
/* 380 */       selectedDevice.setLowerLimit((Integer)this.lowerLimit.getCurrentValue());
/* 381 */       selectedDevice.setUnit((String)this.unit.getCurrentValue());
/*     */     } 
/* 383 */     this.presenter.updateRoomDeviceConfig(selectedDevice);
/*     */   }
/*     */   
/*     */   public Boolean checkField(SignalType signalType) {
/* 387 */     Boolean check = Boolean.valueOf(false);
/* 388 */     if (this.displayName == null || this.type.getValue() == null) {
/* 389 */       check = Boolean.valueOf(true);
/*     */     }
/* 391 */     else if (signalType == SignalType.ANALOG_IN && (
/* 392 */       this.unit == null || this.upperLimit == null || this.lowerLimit == null)) {
/* 393 */       check = Boolean.valueOf(true);
/*     */     } 
/*     */ 
/*     */     
/* 397 */     return check;
/*     */   }
/*     */   
/*     */   public void updateConfig(Boolean result) {
/* 401 */     if (result.booleanValue()) {
/* 402 */       Info.display("儲存成功", "");
/*     */     } else {
/* 404 */       Info.display("儲存失敗", "");
/*     */     } 
/* 406 */     RoomDeviceSubLocationConfigDTO selected = (RoomDeviceSubLocationConfigDTO)this.grid.getSelectionModel().getSelectedItem();
/* 407 */     String subLocationName = null;
/* 408 */     if (selected.getSubLocation().equals(null) || selected.getSubLocation().equals("")) {
/* 409 */       subLocationName = null;
/*     */     } else {
/* 411 */       subLocationName = selected.getSubLocation();
/*     */     } 
/* 413 */     this.presenter.fiilDeviceGridData(selected.getLocationName(), subLocationName);
/*     */   }
/*     */   
/*     */   public void fillGridData(List<RoomDeviceSubLocationConfigDTO> response) {
/* 417 */     Collections.sort(response, new Comparator<RoomDeviceSubLocationConfigDTO>()
/*     */         {
/*     */           
/*     */           public int compare(RoomDeviceSubLocationConfigDTO o1, RoomDeviceSubLocationConfigDTO o2)
/*     */           {
/* 422 */             return o1.getLocationName().compareTo(o2.getLocationName());
/*     */           }
/*     */         });
/* 425 */     this.store.clear();
/* 426 */     this.store.addAll(response);
/*     */   }
/*     */   
/*     */   public void fillDeviceGrid(List<RoomDeviceConfigDTO> list) {
/* 430 */     this.deviceStore.clear();
/* 431 */     for (RoomDeviceConfigDTO each : list) {
/* 432 */       this.deviceStore.add(each);
/*     */     }
/* 434 */     if (!this.settedDeviceName.equals("")) {
/* 435 */       this.deviceGrid.getSelectionModel().select(true, (Object[])new RoomDeviceConfigDTO[] { (RoomDeviceConfigDTO)this.deviceStore.findModelWithKey(this.settedDeviceName) });
/* 436 */       RoomDeviceConfigDTO selectedDevice = (RoomDeviceConfigDTO)this.deviceGrid.getSelectionModel().getSelectedItem();
/* 437 */       this.displayName.setValue(selectedDevice.getDisplayName());
/* 438 */       this.enable.setValue(selectedDevice.getAlarmCheck().booleanValue() ? "是" : "否");
/* 439 */       this.type.setValue(this.typeStore.findModelWithKey(selectedDevice.getType()));
/* 440 */       if (selectedDevice.getSignalType() == SignalType.DIGITAL_IN) {
/* 441 */         this.unit.disable();
/* 442 */         this.unit.clear();
/* 443 */         this.upperLimit.disable();
/* 444 */         this.upperLimit.clear();
/* 445 */         this.lowerLimit.disable();
/* 446 */         this.lowerLimit.clear();
/* 447 */       } else if (selectedDevice.getSignalType() == SignalType.ANALOG_IN) {
/* 448 */         this.unit.enable();
/* 449 */         this.unit.setAllowBlank(false);
/* 450 */         this.unit.setValue(selectedDevice.getUnit());
/* 451 */         this.upperLimit.enable();
/* 452 */         this.upperLimit.setAllowBlank(false);
/* 453 */         this.upperLimit.setValue(selectedDevice.getUpperLimit());
/* 454 */         this.lowerLimit.enable();
/* 455 */         this.lowerLimit.setAllowBlank(false);
/* 456 */         this.lowerLimit.setValue(selectedDevice.getLowerLimit());
/*     */       } 
/* 458 */       this.settedDeviceName = new String();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void fillTypeCB(List<DeviceTypeDTO> list) {
/* 463 */     this.typeStore.addAll(list);
/*     */   }
/*     */   
/*     */   public void receiveSettingEvent(String deviceName) {
/* 467 */     this.settedDeviceName = deviceName;
/* 468 */     this.presenter.findSettedDevcieLocation(this.settedDeviceName);
/*     */   }
/*     */   
/*     */   public void setSettedDeviceLocation(String id) {
/* 472 */     this.grid.getSelectionModel().select(true, (Object[])new RoomDeviceSubLocationConfigDTO[] { (RoomDeviceSubLocationConfigDTO)this.store.findModelWithKey(id) });
/* 473 */     RoomDeviceSubLocationConfigDTO selectedDto = (RoomDeviceSubLocationConfigDTO)this.grid.getSelectionModel().getSelectedItem();
/* 474 */     String subLocationName = null;
/* 475 */     if (selectedDto.getSubLocation().equals(null) || selectedDto.getSubLocation().equals("")) {
/* 476 */       subLocationName = null;
/*     */     } else {
/* 478 */       subLocationName = selectedDto.getSubLocation();
/*     */     } 
/* 480 */     this.presenter.fiilDeviceGridData(selectedDto.getLocationName(), subLocationName);
/*     */   }
/*     */   
/*     */   public void setPresenter(RoomDeviceConfigSettingPresenter presenter) {
/* 484 */     this.presenter = presenter;
/*     */   }
/*     */   
/*     */   static interface RoomDeviceConfigSettingViewerUiBinder extends UiBinder<Widget, RoomDeviceConfigSettingViewer> {}
/*     */   
/*     */   static interface GridProperties extends PropertyAccess<RoomDeviceSubLocationConfigDTO> {
/*     */     ModelKeyProvider<RoomDeviceSubLocationConfigDTO> id();
/*     */     
/*     */     ValueProvider<RoomDeviceSubLocationConfigDTO, String> locationName();
/*     */   }
/*     */   
/*     */   static interface RoomDeviceConfigPropertyAccess extends PropertyAccess<RoomDeviceConfigDTO> {
/*     */     ModelKeyProvider<RoomDeviceConfigDTO> deviceName();
/*     */     
/*     */     ValueProvider<RoomDeviceConfigDTO, String> displayName();
/*     */     
/*     */     ValueProvider<RoomDeviceConfigDTO, SignalType> signalType();
/*     */     
/*     */     ValueProvider<RoomDeviceConfigDTO, String> typeDiscription();
/*     */     
/*     */     ValueProvider<RoomDeviceConfigDTO, String> unit();
/*     */     
/*     */     ValueProvider<RoomDeviceConfigDTO, Integer> upperLimit();
/*     */     
/*     */     ValueProvider<RoomDeviceConfigDTO, Integer> lowerLimit();
/*     */     
/*     */     ValueProvider<RoomDeviceConfigDTO, Boolean> alarmCheck();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\RoomDeviceConfigSettingViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */