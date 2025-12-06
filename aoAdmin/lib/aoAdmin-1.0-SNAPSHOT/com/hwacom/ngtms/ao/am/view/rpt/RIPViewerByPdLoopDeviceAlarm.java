/*     */ package com.hwacom.ngtms.ao.am.view.rpt;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.event.logical.shared.ValueChangeEvent;
/*     */ import com.google.gwt.event.logical.shared.ValueChangeHandler;
/*     */ import com.google.gwt.i18n.client.DateTimeFormat;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.uibinder.client.UiHandler;
/*     */ import com.google.gwt.user.client.ui.HasValue;
/*     */ import com.google.gwt.user.client.ui.Label;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.RptEP;
/*     */ import com.hwacom.ngtms.ao.am.presenter.rpt.RIPViewerByPdLoopDeviceAlarmPresenter;
/*     */ import com.hwacom.ngtms.ao.am.util.StringConverter;
/*     */ import com.hwacom.ngtms.ao.am.view.Messages;
/*     */ import com.hwacom.ngtms.ao.shared.dto.PdLocationDTO;
/*     */ import com.hwacom.ngtms.c.shared.Direction;
/*     */ import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
/*     */ import com.hwacom.ngtms.cam.client.ui.RoadTreeViewer;
/*     */ import com.hwacom.ngtms.cam.vo.IdNameNode;
/*     */ import com.hwacom.ngtms.common.am.view.DateRangePicker;
/*     */ import com.hwacom.ngtms.common.am.view.RIPViewer;
/*     */ import com.hwacom.ngtms.pd.shared.dto.DirectionDTO;
/*     */ import com.sencha.gxt.core.client.util.ToggleGroup;
/*     */ import com.sencha.gxt.data.shared.IconProvider;
/*     */ import com.sencha.gxt.data.shared.LabelProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.widget.core.client.Composite;
/*     */ import com.sencha.gxt.widget.core.client.Dialog;
/*     */ import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
/*     */ import com.sencha.gxt.widget.core.client.form.CheckBox;
/*     */ import com.sencha.gxt.widget.core.client.form.ComboBox;
/*     */ import com.sencha.gxt.widget.core.client.form.IntegerField;
/*     */ import com.sencha.gxt.widget.core.client.form.Radio;
/*     */ import com.sencha.gxt.widget.core.client.info.Info;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.BiConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RIPViewerByPdLoopDeviceAlarm
/*     */   extends Composite
/*     */   implements RIPViewer
/*     */ {
/*  57 */   private static RIPViewerByPdLoopDeviceAlarmUiBinder uiBinder = (RIPViewerByPdLoopDeviceAlarmUiBinder)GWT.create(RIPViewerByPdLoopDeviceAlarmUiBinder.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private static final Messages messages = (Messages)GWT.create(Messages.class);
/*     */   
/*  64 */   private final PathProertyAccess pathProertyAccess = (PathProertyAccess)GWT.create(PathProertyAccess.class);
/*     */   
/*     */   private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
/*     */   
/*  68 */   private RIPViewerByPdLoopDeviceAlarmPresenter presenter = new RIPViewerByPdLoopDeviceAlarmPresenter(this);
/*     */   
/*     */   @UiField
/*     */   RoadTreeViewer deviceSourceTree;
/*     */   
/*     */   @UiField
/*     */   DeviceSelectionViewer deviceTargetView;
/*     */   @UiField
/*     */   ComboBox<RoadLineDTO> path;
/*     */   @UiField(provided = true)
/*     */   ListStore<RoadLineDTO> pathStore;
/*     */   @UiField(provided = true)
/*     */   LabelProvider<RoadLineDTO> pathProvider;
/*     */   @UiField
/*     */   ComboBox<DirectionDTO> direction;
/*     */   @UiField(provided = true)
/*     */   ListStore<DirectionDTO> directionStore;
/*     */   @UiField(provided = true)
/*     */   LabelProvider<DirectionDTO> directionProvider;
/*     */   @UiField
/*     */   ComboBox<PdLocationDTO> location;
/*     */   @UiField(provided = true)
/*     */   ListStore<PdLocationDTO> locationStore;
/*     */   @UiField(provided = true)
/*     */   LabelProvider<PdLocationDTO> locationProvider;
/*     */   @UiField
/*     */   ComboBox<String> item;
/*     */   @UiField(provided = true)
/*     */   ListStore<String> itemStore;
/*     */   @UiField(provided = true)
/*     */   LabelProvider<String> itemProvider;
/*     */   @UiField
/*     */   ComboBox<String> abnormalStatusCB;
/*     */   @UiField(provided = true)
/*     */   ListStore<String> abnormalStatusStore;
/*     */   @UiField(provided = true)
/*     */   LabelProvider<String> abnormalStatusProvider;
/*     */   @UiField
/*     */   CheckBox milepostCheckBox;
/*     */   @UiField
/*     */   IntegerField milepostStartFrom;
/*     */   @UiField
/*     */   IntegerField milepostStartTo;
/*     */   @UiField
/*     */   IntegerField milepostEndFrom;
/*     */   @UiField
/*     */   IntegerField milepostEndTo;
/*     */   @UiField
/*     */   HorizontalLayoutContainer milepostContainer;
/*     */   @UiField
/*     */   Radio deviceRadio;
/*     */   @UiField
/*     */   Radio conditionRadio;
/*     */   private ToggleGroup toggle;
/*     */   @UiField
/*     */   HorizontalLayoutContainer deviceContainer;
/*     */   @UiField
/*     */   HorizontalLayoutContainer locationContainer;
/*     */   @UiField
/*     */   HorizontalLayoutContainer itemsContainer;
/*     */   @UiField
/*     */   HorizontalLayoutContainer statusContainer;
/*     */   @UiField
/*     */   HorizontalLayoutContainer roadLineContainer;
/*     */   @UiField
/*     */   HorizontalLayoutContainer milepostCheckBoxContainer;
/*     */   @UiField
/*     */   HorizontalLayoutContainer dateRangePickerContainer;
/*     */   @UiField
/*     */   DateRangePicker dateRangePicker;
/*     */   private Dialog warningDialog;
/* 139 */   private String condition = "device";
/*     */   
/*     */   public RIPViewerByPdLoopDeviceAlarm() {
/* 142 */     this.pathStore = new ListStore(this.pathProertyAccess.lineId());
/* 143 */     this.pathProvider = this.pathProertyAccess.lineName();
/*     */     
/* 145 */     this.directionStore = new ListStore(new ModelKeyProvider<DirectionDTO>()
/*     */         {
/*     */           
/*     */           public String getKey(DirectionDTO item)
/*     */           {
/* 150 */             return item.getId();
/*     */           }
/*     */         });
/* 153 */     this.directionProvider = new LabelProvider<DirectionDTO>()
/*     */       {
/*     */         public String getLabel(DirectionDTO item)
/*     */         {
/* 157 */           return StringConverter.getDirectionName(item);
/*     */         }
/*     */       };
/*     */     
/* 161 */     this.locationStore = new ListStore(new ModelKeyProvider<PdLocationDTO>()
/*     */         {
/*     */           
/*     */           public String getKey(PdLocationDTO item)
/*     */           {
/* 166 */             return item.toString();
/*     */           }
/*     */         });
/* 169 */     this.locationProvider = new LabelProvider<PdLocationDTO>()
/*     */       {
/*     */         public String getLabel(PdLocationDTO item)
/*     */         {
/* 173 */           return item.getLocName();
/*     */         }
/*     */       };
/*     */     
/* 177 */     this.itemStore = new ListStore(new ModelKeyProvider<String>()
/*     */         {
/*     */           
/*     */           public String getKey(String item)
/*     */           {
/* 182 */             return item;
/*     */           }
/*     */         });
/*     */     
/* 186 */     this.itemProvider = new LabelProvider<String>()
/*     */       {
/*     */         public String getLabel(String item)
/*     */         {
/* 190 */           return item;
/*     */         }
/*     */       };
/*     */     
/* 194 */     this.abnormalStatusStore = new ListStore(new ModelKeyProvider<String>()
/*     */         {
/*     */           
/*     */           public String getKey(String item)
/*     */           {
/* 199 */             return item;
/*     */           }
/*     */         });
/* 202 */     this.abnormalStatusProvider = new LabelProvider<String>()
/*     */       {
/*     */         public String getLabel(String item)
/*     */         {
/* 206 */           return item;
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 211 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*     */     
/* 213 */     Date now = new Date();
/* 214 */     this.dateRangePicker.setStartDate(now);
/* 215 */     this.dateRangePicker.setEndDate(now);
/* 216 */     this.dateRangePicker.setTimePickerIncrement(1);
/*     */     
/* 218 */     this.presenter.initCombobox();
/* 219 */     this.milepostContainer.disable();
/* 220 */     addEventHandlers();
/* 221 */     this.toggle.setValue((HasValue)this.deviceRadio, true);
/* 222 */     setDirectionData();
/* 223 */     this.presenter.retrieveLocation();
/* 224 */     this.abnormalStatusStore.add("全部");
/* 225 */     this.abnormalStatusStore.add("欠相");
/* 226 */     this.abnormalStatusStore.add("斷電");
/* 227 */     this.abnormalStatusStore.add("恢復");
/* 228 */     this.abnormalStatusCB.setValue(this.abnormalStatusStore.get(0));
/* 229 */     setAlarmItemCB();
/* 230 */     setDeviceType("PD");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> getInputParameter() {
/* 240 */     Map<String, Object> map = new HashMap<>();
/* 241 */     Date startDateTime = this.dateRangePicker.getStartDate();
/* 242 */     Date endDateTime = this.dateRangePicker.getEndDate();
/*     */     
/* 244 */     map.put("startDateTime", DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").format(startDateTime));
/* 245 */     map.put("endDateTime", DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").format(endDateTime));
/*     */     
/* 247 */     map.put("devices", this.deviceTargetView.getDevicesStr());
/* 248 */     map.put("lineName", ((RoadLineDTO)this.path.getValue()).getLineId());
/* 249 */     map.put("location", ((PdLocationDTO)this.location.getValue()).getLocName());
/* 250 */     map.put("item", this.item.getValue());
/* 251 */     map.put("status", this.abnormalStatusCB.getValue());
/* 252 */     map.put("direction", ((DirectionDTO)this.direction.getValue()).getId());
/* 253 */     map.put("userName", RptEP.getUserName());
/*     */     
/* 255 */     String mileages = new String();
/* 256 */     if (((Boolean)this.milepostCheckBox.getValue()).booleanValue() == true) {
/* 257 */       if (this.milepostStartFrom.getValue() != null && this.milepostStartTo
/* 258 */         .getValue() != null && this.milepostEndFrom
/* 259 */         .getValue() != null && this.milepostEndTo
/* 260 */         .getValue() != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 268 */         mileages = String.valueOf(this.milepostStartFrom.getText()) + "," + String.valueOf(this.milepostStartTo.getText()) + "," + String.valueOf(this.milepostEndFrom.getText()) + "," + String.valueOf(this.milepostEndTo.getText());
/* 269 */         map.put("mileage", mileages);
/* 270 */       } else if (this.milepostStartFrom.getValue() != null || this.milepostStartTo
/* 271 */         .getValue() != null || this.milepostEndFrom
/* 272 */         .getValue() != null || this.milepostEndTo
/* 273 */         .getValue() != null) {
/* 274 */         Info.display("里程尚有欄位還未填入", "請重新輸入");
/*     */       } else {
/* 276 */         map.put("mileage", "-1");
/*     */       } 
/*     */     } else {
/* 279 */       map.put("mileage", "-1");
/*     */     } 
/* 281 */     map.put("condition", this.condition);
/*     */     
/* 283 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInputParameter(Map<String, Object> inputParameter) {
/* 292 */     clearInputParameter();
/*     */ 
/*     */     
/* 295 */     IdNameNode idName = null;
/* 296 */     for (String key : inputParameter.get("devices")) {
/* 297 */       idName = this.deviceSourceTree.findModelWithDeviceName(key);
/* 298 */       if (idName == null) {
/*     */         continue;
/*     */       }
/* 301 */       DeviceConfigDTO dto = new DeviceConfigDTO();
/* 302 */       dto.setDeviceName(idName.getDeviceName());
/* 303 */       dto.setDisplayName(idName.getName());
/* 304 */       dto.setMilepost(idName.getMilePost());
/* 305 */       dto.setDirection(idName.getDirection());
/* 306 */       this.deviceTargetView.addDevices(Arrays.asList(new DeviceConfigDTO[] { dto }));
/*     */     } 
/*     */     
/* 309 */     String strDirection = (String)inputParameter.get("direction");
/* 310 */     DirectionDTO directionDTO = (DirectionDTO)this.direction.getStore().findModelWithKey(strDirection);
/* 311 */     if (directionDTO != null) {
/* 312 */       this.direction.setValue(directionDTO);
/*     */     }
/*     */     
/* 315 */     String strLine = (String)inputParameter.get("lineName");
/* 316 */     RoadLineDTO roadLineDTO = (RoadLineDTO)this.path.getStore().findModelWithKey(strLine);
/* 317 */     if (roadLineDTO != null) {
/* 318 */       this.path.setValue(roadLineDTO);
/*     */     }
/*     */     
/* 321 */     if (inputParameter.get("mileage") != null) {
/* 322 */       String strMileage = (String)inputParameter.get("mileage");
/* 323 */       List<String> mileages = Arrays.asList(strMileage.split(","));
/* 324 */       this.milepostCheckBox.setValue(Boolean.valueOf(true));
/* 325 */       this.milepostStartFrom.setText(mileages.get(0));
/* 326 */       this.milepostStartTo.setText(mileages.get(1));
/* 327 */       this.milepostEndFrom.setText(mileages.get(2));
/* 328 */       this.milepostEndTo.setText(mileages.get(3));
/*     */     } else {
/* 330 */       this.milepostCheckBox.setValue(Boolean.valueOf(false));
/* 331 */       this.milepostContainer.disable();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/* 337 */     if (this.deviceTargetView.getDevices().size() == 0) {
/* 338 */       return false;
/*     */     }
/*     */     
/* 341 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearInputParameter() {
/* 346 */     this.deviceTargetView.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHeading(String heading) {}
/*     */   
/*     */   private void addEventHandlers() {
/* 353 */     this.toggle = new ToggleGroup();
/* 354 */     this.toggle.add((HasValue)this.deviceRadio);
/* 355 */     this.toggle.add((HasValue)this.conditionRadio);
/* 356 */     this.toggle.addValueChangeHandler(new ValueChangeHandler<HasValue<Boolean>>()
/*     */         {
/*     */           public void onValueChange(ValueChangeEvent<HasValue<Boolean>> event)
/*     */           {
/* 360 */             ToggleGroup group = (ToggleGroup)event.getSource();
/* 361 */             Radio radio = (Radio)group.getValue();
/* 362 */             if (radio.getBoxLabel().equals(RIPViewerByPdLoopDeviceAlarm.this.deviceRadio.getBoxLabel())) {
/* 363 */               RIPViewerByPdLoopDeviceAlarm.this.deviceRadio.setValue(Boolean.valueOf(true));
/* 364 */               RIPViewerByPdLoopDeviceAlarm.this.deviceContainer.enable();
/* 365 */               RIPViewerByPdLoopDeviceAlarm.this.locationContainer.disable();
/* 366 */               RIPViewerByPdLoopDeviceAlarm.this.itemsContainer.disable();
/* 367 */               RIPViewerByPdLoopDeviceAlarm.this.statusContainer.disable();
/* 368 */               RIPViewerByPdLoopDeviceAlarm.this.roadLineContainer.disable();
/* 369 */               RIPViewerByPdLoopDeviceAlarm.this.milepostCheckBoxContainer.disable();
/* 370 */               RIPViewerByPdLoopDeviceAlarm.this.milepostCheckBox.setValue(Boolean.valueOf(false));
/* 371 */               RIPViewerByPdLoopDeviceAlarm.this.milepostContainer.disable();
/* 372 */               RIPViewerByPdLoopDeviceAlarm.this.milepostStartFrom.clear();
/* 373 */               RIPViewerByPdLoopDeviceAlarm.this.milepostStartTo.clear();
/* 374 */               RIPViewerByPdLoopDeviceAlarm.this.milepostEndFrom.clear();
/* 375 */               RIPViewerByPdLoopDeviceAlarm.this.milepostEndTo.clear();
/* 376 */               RIPViewerByPdLoopDeviceAlarm.this.condition = "device";
/* 377 */             } else if (radio.getBoxLabel().equals(RIPViewerByPdLoopDeviceAlarm.this.conditionRadio.getBoxLabel())) {
/* 378 */               RIPViewerByPdLoopDeviceAlarm.this.conditionRadio.setValue(Boolean.valueOf(true));
/* 379 */               RIPViewerByPdLoopDeviceAlarm.this.deviceContainer.disable();
/* 380 */               RIPViewerByPdLoopDeviceAlarm.this.locationContainer.enable();
/* 381 */               RIPViewerByPdLoopDeviceAlarm.this.itemsContainer.enable();
/* 382 */               RIPViewerByPdLoopDeviceAlarm.this.statusContainer.enable();
/* 383 */               RIPViewerByPdLoopDeviceAlarm.this.roadLineContainer.enable();
/* 384 */               RIPViewerByPdLoopDeviceAlarm.this.milepostCheckBoxContainer.enable();
/* 385 */               if (((Boolean)RIPViewerByPdLoopDeviceAlarm.this.milepostCheckBox.getValue()).booleanValue() == true) {
/* 386 */                 RIPViewerByPdLoopDeviceAlarm.this.milepostContainer.enable();
/*     */               }
/* 388 */               RIPViewerByPdLoopDeviceAlarm.this.condition = "condition";
/*     */             } 
/*     */           }
/*     */         });
/* 392 */     this.dateRangePicker.addValueChangeCallback(new BiConsumer<Date, Date>()
/*     */         {
/*     */           public void accept(Date t, Date u)
/*     */           {
/* 396 */             Long timeIntervalDay = Long.valueOf((u.getTime() - t.getTime()) / 86400000L);
/* 397 */             if (timeIntervalDay.longValue() > 7L) {
/* 398 */               Date now = new Date();
/* 399 */               RIPViewerByPdLoopDeviceAlarm.this.dateRangePicker.setStartDate(now);
/* 400 */               RIPViewerByPdLoopDeviceAlarm.this.dateRangePicker.setEndDate(now);
/* 401 */               RIPViewerByPdLoopDeviceAlarm.this.initDialog();
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void initDialog() {
/* 408 */     if (this.warningDialog == null) {
/* 409 */       this.warningDialog = new Dialog();
/* 410 */       this.warningDialog.setWidth(210);
/* 411 */       this.warningDialog.setHeight(70);
/* 412 */       this.warningDialog.setModal(true);
/* 413 */       this.warningDialog.setResizable(false);
/* 414 */       this.warningDialog.setHeading("查詢條件不符");
/* 415 */       this.warningDialog.setHideOnButtonClick(true);
/*     */     } 
/*     */     
/* 418 */     Label label = new Label("日期範圍不得大於7天!\n請重新選取");
/* 419 */     this.warningDialog.add((Widget)label);
/* 420 */     this.warningDialog.show();
/*     */   }
/*     */   
/*     */   private void setAlarmItemCB() {
/* 424 */     String itemAll = "全部";
/* 425 */     this.itemStore.add(itemAll);
/* 426 */     String item1 = "一次側(R)";
/* 427 */     this.itemStore.add(item1);
/* 428 */     String item2 = "一次側(S)";
/* 429 */     this.itemStore.add(item2);
/* 430 */     String item3 = "一次側(T)";
/* 431 */     this.itemStore.add(item3);
/* 432 */     String item4 = "二次側(R)";
/* 433 */     this.itemStore.add(item4);
/* 434 */     String item5 = "二次側(S)";
/* 435 */     this.itemStore.add(item5);
/* 436 */     String item6 = "二次側(T)";
/* 437 */     this.itemStore.add(item6);
/* 438 */     String item7 = "分迴路(1)";
/* 439 */     this.itemStore.add(item7);
/* 440 */     String item8 = "分迴路(2)";
/* 441 */     this.itemStore.add(item8);
/* 442 */     String item9 = "分迴路(3)";
/* 443 */     this.itemStore.add(item9);
/* 444 */     String item10 = "分迴路(4)";
/* 445 */     this.itemStore.add(item10);
/* 446 */     String item11 = "分迴路(5)";
/* 447 */     this.itemStore.add(item11);
/* 448 */     this.item.setValue(this.itemStore.get(0));
/*     */   }
/*     */   
/*     */   public String getDeviceType() {
/* 452 */     return null;
/*     */   }
/*     */   
/*     */   @UiHandler({"milepostCheckBox"})
/*     */   public void onMilepostValueChanged(ValueChangeEvent<Boolean> event) {
/* 457 */     if (((Boolean)event.getValue()).booleanValue()) {
/* 458 */       this.milepostContainer.enable();
/*     */     } else {
/* 460 */       this.milepostContainer.disable();
/* 461 */       this.milepostStartFrom.clear();
/* 462 */       this.milepostStartTo.clear();
/* 463 */       this.milepostEndFrom.clear();
/* 464 */       this.milepostEndTo.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setDirectionData() {
/* 469 */     DirectionDTO directionAll = new DirectionDTO();
/* 470 */     directionAll.setId(messages.comboBox_all());
/* 471 */     directionAll.setDirection(null);
/* 472 */     DirectionDTO directionN = new DirectionDTO();
/* 473 */     directionN.setId(messages.direction_north());
/* 474 */     directionN.setDirection(Direction.N);
/* 475 */     DirectionDTO directionS = new DirectionDTO();
/* 476 */     directionS.setId(messages.direction_south());
/* 477 */     directionS.setDirection(Direction.S);
/* 478 */     DirectionDTO directionE = new DirectionDTO();
/* 479 */     directionE.setId(messages.direction_east());
/* 480 */     directionE.setDirection(Direction.E);
/* 481 */     DirectionDTO directionW = new DirectionDTO();
/* 482 */     directionW.setId(messages.direction_west());
/* 483 */     directionW.setDirection(Direction.W);
/* 484 */     this.directionStore.addAll(
/* 485 */         Arrays.asList(new DirectionDTO[] { directionAll, directionN, directionS, directionE, directionW }));
/* 486 */     this.direction.setValue(this.directionStore.get(0));
/*     */   }
/*     */   
/*     */   public void initPath(List<RoadLineDTO> result) {
/* 490 */     RoadLineDTO all = new RoadLineDTO();
/* 491 */     all.setLineId("-1");
/* 492 */     all.setLineName(messages.comboBox_all());
/* 493 */     this.pathStore.add(all);
/* 494 */     this.pathStore.addAll(result);
/* 495 */     this.path.setValue(all);
/*     */   }
/*     */   
/*     */   public void fillLocation(List<PdLocationDTO> list) {
/* 499 */     this.locationStore.clear();
/* 500 */     PdLocationDTO all = new PdLocationDTO();
/* 501 */     all.setId(Integer.valueOf(-1));
/* 502 */     all.setLocName(messages.comboBox_all());
/* 503 */     this.locationStore.add(all);
/* 504 */     this.locationStore.addAll(list);
/* 505 */     this.location.setValue(all);
/*     */   }
/*     */   
/*     */   public void setPresenter(RIPViewerByPdLoopDeviceAlarmPresenter presenter) {
/* 509 */     this.presenter = presenter;
/*     */   }
/*     */   
/*     */   public void setDeviceType(String deviceType) {
/* 513 */     this.deviceSourceTree.setDeviceType(deviceType);
/*     */   }
/*     */   
/*     */   public void setDeviceTypes(List<String> deviceTypes) {
/* 517 */     this.deviceSourceTree.setDeviceTypeList(deviceTypes);
/*     */   }
/*     */   
/*     */   public void setVdIconProvider(IconProvider<IdNameNode> provider) {
/* 521 */     this.deviceSourceTree.setIconProvider(provider);
/*     */   }
/*     */   
/*     */   static interface RIPViewerByPdLoopDeviceAlarmUiBinder extends UiBinder<Widget, RIPViewerByPdLoopDeviceAlarm> {}
/*     */   
/*     */   static interface PathProertyAccess extends PropertyAccess<RoadLineDTO> {
/*     */     ModelKeyProvider<RoadLineDTO> lineId();
/*     */     
/*     */     LabelProvider<RoadLineDTO> lineName();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\rpt\RIPViewerByPdLoopDeviceAlarm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */