/*     */ package com.hwacom.ngtms.ao.am.view.rpt;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.event.logical.shared.ValueChangeEvent;
/*     */ import com.google.gwt.event.logical.shared.ValueChangeHandler;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.uibinder.client.UiHandler;
/*     */ import com.google.gwt.user.client.ui.HasValue;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.RptEP;
/*     */ import com.hwacom.ngtms.ao.am.presenter.rpt.RIPViewerByPdStatusPresenter;
/*     */ import com.hwacom.ngtms.ao.am.util.StringConverter;
/*     */ import com.hwacom.ngtms.ao.am.view.Messages;
/*     */ import com.hwacom.ngtms.ao.shared.dto.PdLocationDTO;
/*     */ import com.hwacom.ngtms.c.shared.Direction;
/*     */ import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
/*     */ import com.hwacom.ngtms.cam.client.ui.RoadTreeViewer;
/*     */ import com.hwacom.ngtms.cam.vo.IdNameNode;
/*     */ import com.hwacom.ngtms.common.am.view.RIPViewer;
/*     */ import com.hwacom.ngtms.pd.shared.dto.DirectionDTO;
/*     */ import com.sencha.gxt.core.client.util.ToggleGroup;
/*     */ import com.sencha.gxt.data.shared.IconProvider;
/*     */ import com.sencha.gxt.data.shared.LabelProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.widget.core.client.Composite;
/*     */ import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
/*     */ import com.sencha.gxt.widget.core.client.form.CheckBox;
/*     */ import com.sencha.gxt.widget.core.client.form.ComboBox;
/*     */ import com.sencha.gxt.widget.core.client.form.IntegerField;
/*     */ import com.sencha.gxt.widget.core.client.form.Radio;
/*     */ import com.sencha.gxt.widget.core.client.info.Info;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RIPViewerByPdStatus
/*     */   extends Composite
/*     */   implements RIPViewer
/*     */ {
/*  51 */   private static RIPViewerByPdStatusUiBinder uiBinder = (RIPViewerByPdStatusUiBinder)GWT.create(RIPViewerByPdStatusUiBinder.class);
/*     */ 
/*     */ 
/*     */   
/*  55 */   private static final Messages messages = (Messages)GWT.create(Messages.class);
/*     */   
/*  57 */   private final PathProertyAccess pathProertyAccess = (PathProertyAccess)GWT.create(PathProertyAccess.class);
/*     */   
/*  59 */   private RIPViewerByPdStatusPresenter presenter = new RIPViewerByPdStatusPresenter(this);
/*     */   @UiField
/*     */   RoadTreeViewer deviceSourceTree;
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
/*     */   HorizontalLayoutContainer statusContainer;
/*     */   @UiField
/*     */   HorizontalLayoutContainer roadLineContainer;
/*     */   @UiField
/*     */   HorizontalLayoutContainer milepostCheckBoxContainer;
/* 115 */   private String condition = "device";
/*     */   
/*     */   public RIPViewerByPdStatus() {
/* 118 */     this.pathStore = new ListStore(this.pathProertyAccess.lineId());
/* 119 */     this.pathProvider = this.pathProertyAccess.lineName();
/*     */     
/* 121 */     this.directionStore = new ListStore(new ModelKeyProvider<DirectionDTO>()
/*     */         {
/*     */           
/*     */           public String getKey(DirectionDTO item)
/*     */           {
/* 126 */             return item.getId();
/*     */           }
/*     */         });
/* 129 */     this.directionProvider = new LabelProvider<DirectionDTO>()
/*     */       {
/*     */         public String getLabel(DirectionDTO item)
/*     */         {
/* 133 */           return StringConverter.getDirectionName(item);
/*     */         }
/*     */       };
/*     */     
/* 137 */     this.locationStore = new ListStore(new ModelKeyProvider<PdLocationDTO>()
/*     */         {
/*     */           
/*     */           public String getKey(PdLocationDTO item)
/*     */           {
/* 142 */             return item.toString();
/*     */           }
/*     */         });
/* 145 */     this.locationProvider = new LabelProvider<PdLocationDTO>()
/*     */       {
/*     */         public String getLabel(PdLocationDTO item)
/*     */         {
/* 149 */           return item.getLocName();
/*     */         }
/*     */       };
/*     */     
/* 153 */     this.abnormalStatusStore = new ListStore(new ModelKeyProvider<String>()
/*     */         {
/*     */           
/*     */           public String getKey(String item)
/*     */           {
/* 158 */             return item;
/*     */           }
/*     */         });
/* 161 */     this.abnormalStatusProvider = new LabelProvider<String>()
/*     */       {
/*     */         public String getLabel(String item)
/*     */         {
/* 165 */           return item;
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 170 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*     */     
/* 172 */     this.presenter.initCombobox();
/* 173 */     this.milepostContainer.disable();
/* 174 */     addEventHandlers();
/* 175 */     this.toggle.setValue((HasValue)this.deviceRadio, true);
/* 176 */     setDirectionData();
/* 177 */     this.presenter.retrieveLocation();
/* 178 */     this.abnormalStatusStore.add("全部");
/* 179 */     this.abnormalStatusStore.add("欠相");
/* 180 */     this.abnormalStatusStore.add("斷電");
/* 181 */     this.abnormalStatusStore.add("分迴路異常");
/* 182 */     this.abnormalStatusStore.add("斷線");
/* 183 */     this.abnormalStatusCB.setValue(this.abnormalStatusStore.get(0));
/* 184 */     setDeviceType("PD");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> getInputParameter() {
/* 194 */     Map<String, Object> map = new HashMap<>();
/* 195 */     map.put("devices", this.deviceTargetView.getDevicesStr());
/* 196 */     map.put("lineName", ((RoadLineDTO)this.path.getValue()).getLineId());
/* 197 */     map.put("location", ((PdLocationDTO)this.location.getValue()).getLocName());
/* 198 */     map.put("status", this.abnormalStatusCB.getValue());
/* 199 */     GWT.log("direction id: " + ((DirectionDTO)this.direction.getValue()).getId());
/* 200 */     map.put("direction", ((DirectionDTO)this.direction.getValue()).getId());
/* 201 */     map.put("userName", RptEP.getUserName());
/*     */     
/* 203 */     String mileages = new String();
/* 204 */     if (((Boolean)this.milepostCheckBox.getValue()).booleanValue() == true) {
/* 205 */       if (this.milepostStartFrom.getValue() != null && this.milepostStartTo
/* 206 */         .getValue() != null && this.milepostEndFrom
/* 207 */         .getValue() != null && this.milepostEndTo
/* 208 */         .getValue() != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 216 */         mileages = String.valueOf(this.milepostStartFrom.getText()) + "," + String.valueOf(this.milepostStartTo.getText()) + "," + String.valueOf(this.milepostEndFrom.getText()) + "," + String.valueOf(this.milepostEndTo.getText());
/* 217 */         map.put("mileage", mileages);
/* 218 */       } else if (this.milepostStartFrom.getValue() != null || this.milepostStartTo
/* 219 */         .getValue() != null || this.milepostEndFrom
/* 220 */         .getValue() != null || this.milepostEndTo
/* 221 */         .getValue() != null) {
/* 222 */         Info.display("里程尚有欄位還未填入", "請重新輸入");
/*     */       } else {
/* 224 */         map.put("mileage", "-1");
/*     */       } 
/*     */     } else {
/* 227 */       map.put("mileage", "-1");
/*     */     } 
/* 229 */     map.put("condition", this.condition);
/*     */     
/* 231 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInputParameter(Map<String, Object> inputParameter) {
/* 240 */     clearInputParameter();
/*     */ 
/*     */     
/* 243 */     IdNameNode idName = null;
/* 244 */     for (String key : inputParameter.get("devices")) {
/* 245 */       idName = this.deviceSourceTree.findModelWithDeviceName(key);
/* 246 */       if (idName == null) {
/*     */         continue;
/*     */       }
/* 249 */       DeviceConfigDTO dto = new DeviceConfigDTO();
/* 250 */       dto.setDeviceName(idName.getDeviceName());
/* 251 */       dto.setDisplayName(idName.getName());
/* 252 */       dto.setMilepost(idName.getMilePost());
/* 253 */       dto.setDirection(idName.getDirection());
/* 254 */       this.deviceTargetView.addDevices(Arrays.asList(new DeviceConfigDTO[] { dto }));
/*     */     } 
/*     */     
/* 257 */     String strDirection = (String)inputParameter.get("direction");
/* 258 */     DirectionDTO directionDTO = (DirectionDTO)this.direction.getStore().findModelWithKey(strDirection);
/* 259 */     if (directionDTO != null) {
/* 260 */       this.direction.setValue(directionDTO);
/*     */     }
/*     */     
/* 263 */     String strLine = (String)inputParameter.get("lineName");
/* 264 */     RoadLineDTO roadLineDTO = (RoadLineDTO)this.path.getStore().findModelWithKey(strLine);
/* 265 */     if (roadLineDTO != null) {
/* 266 */       this.path.setValue(roadLineDTO);
/*     */     }
/*     */     
/* 269 */     if (inputParameter.get("mileage") != null) {
/* 270 */       String strMileage = (String)inputParameter.get("mileage");
/* 271 */       List<String> mileages = Arrays.asList(strMileage.split(","));
/* 272 */       this.milepostCheckBox.setValue(Boolean.valueOf(true));
/* 273 */       this.milepostStartFrom.setText(mileages.get(0));
/* 274 */       this.milepostStartTo.setText(mileages.get(1));
/* 275 */       this.milepostEndFrom.setText(mileages.get(2));
/* 276 */       this.milepostEndTo.setText(mileages.get(3));
/*     */     } else {
/* 278 */       this.milepostCheckBox.setValue(Boolean.valueOf(false));
/* 279 */       this.milepostContainer.disable();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/* 285 */     if (this.deviceTargetView.getDevices().size() == 0) {
/* 286 */       return false;
/*     */     }
/*     */     
/* 289 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearInputParameter() {
/* 294 */     this.deviceTargetView.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHeading(String heading) {}
/*     */   
/*     */   private void addEventHandlers() {
/* 301 */     this.toggle = new ToggleGroup();
/* 302 */     this.toggle.add((HasValue)this.deviceRadio);
/* 303 */     this.toggle.add((HasValue)this.conditionRadio);
/* 304 */     this.toggle.addValueChangeHandler(new ValueChangeHandler<HasValue<Boolean>>()
/*     */         {
/*     */           public void onValueChange(ValueChangeEvent<HasValue<Boolean>> event)
/*     */           {
/* 308 */             ToggleGroup group = (ToggleGroup)event.getSource();
/* 309 */             Radio radio = (Radio)group.getValue();
/* 310 */             if (radio.getBoxLabel().equals(RIPViewerByPdStatus.this.deviceRadio.getBoxLabel())) {
/* 311 */               RIPViewerByPdStatus.this.deviceRadio.setValue(Boolean.valueOf(true));
/* 312 */               RIPViewerByPdStatus.this.deviceContainer.enable();
/* 313 */               RIPViewerByPdStatus.this.locationContainer.disable();
/* 314 */               RIPViewerByPdStatus.this.statusContainer.disable();
/* 315 */               RIPViewerByPdStatus.this.roadLineContainer.disable();
/* 316 */               RIPViewerByPdStatus.this.milepostCheckBoxContainer.disable();
/* 317 */               RIPViewerByPdStatus.this.milepostCheckBox.setValue(Boolean.valueOf(false));
/* 318 */               RIPViewerByPdStatus.this.milepostContainer.disable();
/* 319 */               RIPViewerByPdStatus.this.milepostStartFrom.clear();
/* 320 */               RIPViewerByPdStatus.this.milepostStartTo.clear();
/* 321 */               RIPViewerByPdStatus.this.milepostEndFrom.clear();
/* 322 */               RIPViewerByPdStatus.this.milepostEndTo.clear();
/* 323 */               RIPViewerByPdStatus.this.condition = "device";
/* 324 */             } else if (radio.getBoxLabel().equals(RIPViewerByPdStatus.this.conditionRadio.getBoxLabel())) {
/* 325 */               RIPViewerByPdStatus.this.conditionRadio.setValue(Boolean.valueOf(true));
/* 326 */               RIPViewerByPdStatus.this.deviceContainer.disable();
/* 327 */               RIPViewerByPdStatus.this.locationContainer.enable();
/* 328 */               RIPViewerByPdStatus.this.statusContainer.enable();
/* 329 */               RIPViewerByPdStatus.this.roadLineContainer.enable();
/* 330 */               RIPViewerByPdStatus.this.milepostCheckBoxContainer.enable();
/* 331 */               if (((Boolean)RIPViewerByPdStatus.this.milepostCheckBox.getValue()).booleanValue() == true) {
/* 332 */                 RIPViewerByPdStatus.this.milepostContainer.enable();
/*     */               }
/* 334 */               RIPViewerByPdStatus.this.condition = "condition";
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public String getDeviceType() {
/* 341 */     return null;
/*     */   }
/*     */   
/*     */   @UiHandler({"milepostCheckBox"})
/*     */   public void onMilepostValueChanged(ValueChangeEvent<Boolean> event) {
/* 346 */     if (((Boolean)event.getValue()).booleanValue()) {
/* 347 */       this.milepostContainer.enable();
/*     */     } else {
/* 349 */       this.milepostContainer.disable();
/* 350 */       this.milepostStartFrom.clear();
/* 351 */       this.milepostStartTo.clear();
/* 352 */       this.milepostEndFrom.clear();
/* 353 */       this.milepostEndTo.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setDirectionData() {
/* 358 */     DirectionDTO directionAll = new DirectionDTO();
/* 359 */     directionAll.setId("-1");
/* 360 */     directionAll.setDirection(null);
/* 361 */     DirectionDTO directionN = new DirectionDTO();
/* 362 */     directionN.setId("北向");
/* 363 */     directionN.setDirection(Direction.N);
/* 364 */     DirectionDTO directionS = new DirectionDTO();
/* 365 */     directionS.setId("南向");
/* 366 */     directionS.setDirection(Direction.S);
/* 367 */     DirectionDTO directionE = new DirectionDTO();
/* 368 */     directionE.setId("東向");
/* 369 */     directionE.setDirection(Direction.E);
/* 370 */     DirectionDTO directionW = new DirectionDTO();
/* 371 */     directionW.setId("西向");
/* 372 */     directionW.setDirection(Direction.W);
/* 373 */     this.directionStore.addAll(
/* 374 */         Arrays.asList(new DirectionDTO[] { directionAll, directionN, directionS, directionE, directionW }));
/* 375 */     GWT.log("directionStore.get(0): " + ((DirectionDTO)this.directionStore.get(0)).getId());
/* 376 */     this.direction.setValue(this.directionStore.get(0));
/*     */   }
/*     */   
/*     */   public void initPath(List<RoadLineDTO> result) {
/* 380 */     RoadLineDTO all = new RoadLineDTO();
/* 381 */     all.setLineId("-1");
/* 382 */     all.setLineName(messages.comboBox_all());
/* 383 */     this.pathStore.add(all);
/* 384 */     this.pathStore.addAll(result);
/* 385 */     this.path.setValue(all);
/*     */   }
/*     */   
/*     */   public void fillLocation(List<PdLocationDTO> list) {
/* 389 */     this.locationStore.clear();
/* 390 */     PdLocationDTO all = new PdLocationDTO();
/* 391 */     all.setId(Integer.valueOf(-1));
/* 392 */     all.setLocName(messages.comboBox_all());
/* 393 */     this.locationStore.add(all);
/* 394 */     this.locationStore.addAll(list);
/* 395 */     this.location.setValue(all);
/*     */   }
/*     */   
/*     */   public void setPresenter(RIPViewerByPdStatusPresenter presenter) {
/* 399 */     this.presenter = presenter;
/*     */   }
/*     */   
/*     */   public void setDeviceType(String deviceType) {
/* 403 */     this.deviceSourceTree.setDeviceType(deviceType);
/*     */   }
/*     */   
/*     */   public void setDeviceTypes(List<String> deviceTypes) {
/* 407 */     this.deviceSourceTree.setDeviceTypeList(deviceTypes);
/*     */   }
/*     */   
/*     */   public void setVdIconProvider(IconProvider<IdNameNode> provider) {
/* 411 */     this.deviceSourceTree.setIconProvider(provider);
/*     */   }
/*     */   
/*     */   static interface RIPViewerByPdStatusUiBinder extends UiBinder<Widget, RIPViewerByPdStatus> {}
/*     */   
/*     */   static interface PathProertyAccess extends PropertyAccess<RoadLineDTO> {
/*     */     ModelKeyProvider<RoadLineDTO> lineId();
/*     */     
/*     */     LabelProvider<RoadLineDTO> lineName();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\rpt\RIPViewerByPdStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */