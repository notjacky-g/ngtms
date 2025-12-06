/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.i18n.client.DateTimeFormat;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.uibinder.client.UiHandler;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.presenter.NcuConfigSettingPresenter;
/*     */ import com.hwacom.ngtms.ao.shared.dto.NcuConfigDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomPermissionDTO;
/*     */ import com.hwacom.ngtms.cam.client.ui.AmTab;
/*     */ import com.hwacom.ngtms.cam.client.ui.ExportCsvFile;
/*     */ import com.hwacom.ngtms.room.am.util.StringConverter;
/*     */ import com.sencha.gxt.core.client.Style;
/*     */ import com.sencha.gxt.core.client.ValueProvider;
/*     */ import com.sencha.gxt.data.shared.LabelProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.widget.core.client.button.TextButton;
/*     */ import com.sencha.gxt.widget.core.client.event.RowClickEvent;
/*     */ import com.sencha.gxt.widget.core.client.event.SelectEvent;
/*     */ import com.sencha.gxt.widget.core.client.form.DoubleField;
/*     */ import com.sencha.gxt.widget.core.client.form.IntegerField;
/*     */ import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
/*     */ import com.sencha.gxt.widget.core.client.form.TextField;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*     */ import com.sencha.gxt.widget.core.client.grid.Grid;
/*     */ import com.sencha.gxt.widget.core.client.info.Info;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ public class NcuConfigSettingViewer
/*     */   extends AmTab
/*     */ {
/*  39 */   private static NcuConfigSettingViewerUiBinder uiBinder = (NcuConfigSettingViewerUiBinder)GWT.create(NcuConfigSettingViewerUiBinder.class);
/*     */ 
/*     */ 
/*     */   
/*  43 */   private final NcuConfigPropertyAccess propertyAccess = (NcuConfigPropertyAccess)GWT.create(NcuConfigPropertyAccess.class);
/*     */   
/*  45 */   private NcuConfigSettingPresenter presenter = new NcuConfigSettingPresenter(this);
/*     */   
/*     */   @UiField
/*     */   Grid<NcuConfigDTO> grid;
/*     */   @UiField(provided = true)
/*     */   ListStore<NcuConfigDTO> listStore;
/*     */   @UiField(provided = true)
/*     */   ColumnModel<NcuConfigDTO> columnModel;
/*     */   @UiField
/*     */   TextButton delete;
/*     */   @UiField
/*     */   TextButton update;
/*     */   @UiField
/*     */   TextField deviceName;
/*     */   @UiField
/*     */   TextField displayName;
/*     */   @UiField
/*     */   TextField ip;
/*     */   @UiField
/*     */   TextField port;
/*     */   @UiField
/*     */   DoubleField longitude;
/*     */   @UiField
/*     */   DoubleField latitude;
/*     */   @UiField
/*     */   IntegerField alarmTime;
/*     */   
/*     */   @UiField(provided = true)
/*  73 */   SimpleComboBox<String> enableCombo = new SimpleComboBox(new LabelProvider<String>()
/*     */       {
/*     */ 
/*     */ 
/*     */         
/*     */         public String getLabel(String item)
/*     */         {
/*  80 */           return item;
/*     */         }
/*     */       });
/*     */   
/*     */   public NcuConfigSettingViewer() {
/*  85 */     this.listStore = new ListStore(this.propertyAccess.id());
/*  86 */     initColumnModel();
/*  87 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*  88 */     this.grid.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
/*  89 */     mask("資料查詢中");
/*  90 */     this.presenter.getNcuConfig();
/*     */   }
/*     */   
/*     */   private void initColumnModel() {
/*  94 */     List<ColumnConfig<NcuConfigDTO, ?>> columnConfigs = new ArrayList<>();
/*     */ 
/*     */     
/*  97 */     ColumnConfig<NcuConfigDTO, String> displayName = new ColumnConfig(new ValueProvider<NcuConfigDTO, String>()
/*     */         {
/*     */           
/*     */           public String getValue(NcuConfigDTO dto)
/*     */           {
/* 102 */             return dto.getName();
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(NcuConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 110 */             return "displayName";
/*     */           }
/*     */         },  180, "設備名稱");
/*     */ 
/*     */     
/* 115 */     columnConfigs.add(displayName);
/*     */     
/* 117 */     ColumnConfig<NcuConfigDTO, String> ip = new ColumnConfig(new ValueProvider<NcuConfigDTO, String>()
/*     */         {
/*     */           
/*     */           public String getValue(NcuConfigDTO dto)
/*     */           {
/* 122 */             return (dto.getIp() != null) ? dto.getIp() : "";
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(NcuConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 130 */             return "ip";
/*     */           }
/*     */         },  130, "網路IP");
/*     */ 
/*     */     
/* 135 */     columnConfigs.add(ip);
/*     */     
/* 137 */     ColumnConfig<NcuConfigDTO, String> port = new ColumnConfig(new ValueProvider<NcuConfigDTO, String>()
/*     */         {
/*     */           
/*     */           public String getValue(NcuConfigDTO dto)
/*     */           {
/* 142 */             return (dto.getPort() != null) ? dto.getPort() : "";
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(NcuConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 150 */             return "port";
/*     */           }
/*     */         },  100, "網路通訊埠");
/*     */ 
/*     */     
/* 155 */     columnConfigs.add(port);
/*     */     
/* 157 */     ColumnConfig<NcuConfigDTO, String> longitude = new ColumnConfig(new ValueProvider<NcuConfigDTO, String>()
/*     */         {
/*     */           
/*     */           public String getValue(NcuConfigDTO dto)
/*     */           {
/* 162 */             return (dto.getLongitude() != null) ? String.valueOf(dto.getLongitude()) : "";
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(NcuConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 170 */             return "longitude";
/*     */           }
/*     */         },  120, "經度");
/*     */ 
/*     */     
/* 175 */     columnConfigs.add(longitude);
/*     */     
/* 177 */     ColumnConfig<NcuConfigDTO, String> latitude = new ColumnConfig(new ValueProvider<NcuConfigDTO, String>()
/*     */         {
/*     */           
/*     */           public String getValue(NcuConfigDTO dto)
/*     */           {
/* 182 */             return (dto.getLatitude() != null) ? String.valueOf(dto.getLatitude()) : "";
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(NcuConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 190 */             return "latitude";
/*     */           }
/*     */         },  120, "緯度");
/*     */ 
/*     */     
/* 195 */     columnConfigs.add(latitude);
/*     */     
/* 197 */     ColumnConfig<NcuConfigDTO, String> alarmTime = new ColumnConfig(new ValueProvider<NcuConfigDTO, String>()
/*     */         {
/*     */           
/*     */           public String getValue(NcuConfigDTO dto)
/*     */           {
/* 202 */             return String.valueOf(dto.getAlarmTime());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(NcuConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 210 */             return "alarmTime";
/*     */           }
/*     */         },  100, "警告音關閉(秒)");
/*     */ 
/*     */     
/* 215 */     columnConfigs.add(alarmTime);
/*     */     
/* 217 */     ColumnConfig<NcuConfigDTO, String> enable = new ColumnConfig(new ValueProvider<NcuConfigDTO, String>()
/*     */         {
/*     */           
/*     */           public String getValue(NcuConfigDTO dto)
/*     */           {
/* 222 */             return dto.isEnable() ? "是" : "否";
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(NcuConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 230 */             return "enable";
/*     */           }
/*     */         },  100, "是否啟用");
/*     */ 
/*     */     
/* 235 */     columnConfigs.add(enable);
/*     */     
/* 237 */     ColumnConfig<NcuConfigDTO, String> dataTimeNow = new ColumnConfig(new ValueProvider<NcuConfigDTO, String>()
/*     */         {
/*     */           
/*     */           public String getValue(NcuConfigDTO dto)
/*     */           {
/* 242 */             return dto.getDataTimeNow();
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(NcuConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 250 */             return "dataTimeNow";
/*     */           }
/*     */         },  200, "現場時間");
/*     */ 
/*     */     
/* 255 */     columnConfigs.add(dataTimeNow);
/*     */     
/* 257 */     this.columnModel = new ColumnModel(columnConfigs);
/*     */   }
/*     */   
/*     */   private boolean checkField() {
/* 261 */     if (this.deviceName.getCurrentValue() == null || ((String)this.deviceName
/* 262 */       .getCurrentValue()).isEmpty() || this.displayName
/* 263 */       .getCurrentValue() == null || ((String)this.displayName
/* 264 */       .getCurrentValue()).isEmpty() || this.ip
/* 265 */       .getCurrentValue() == null || ((String)this.ip
/* 266 */       .getCurrentValue()).isEmpty() || this.port
/* 267 */       .getCurrentValue() == null || ((String)this.port
/* 268 */       .getCurrentValue()).isEmpty() || this.latitude
/* 269 */       .getCurrentValue() == null || this.longitude
/* 270 */       .getCurrentValue() == null || this.alarmTime
/* 271 */       .getCurrentValue() == null) {
/* 272 */       return false;
/*     */     }
/* 274 */     return true;
/*     */   }
/*     */   
/*     */   public void fillDevices(List<NcuConfigDTO> list) {
/* 278 */     unmask();
/* 279 */     this.listStore.clear();
/* 280 */     this.enableCombo.getStore().clear();
/* 281 */     this.listStore.addAll(list);
/* 282 */     this.delete.disable();
/* 283 */     this.update.disable();
/* 284 */     this.enableCombo.getStore().add("是");
/* 285 */     this.enableCombo.getStore().add("否");
/* 286 */     this.enableCombo.setValue(this.enableCombo.getStore().get(0));
/*     */   }
/*     */   
/*     */   public void fillNcuDataTime(NcuConfigDTO dto) {
/* 290 */     if (dto != null && dto.getId() != null) {
/* 291 */       if (this.listStore.findModelWithKey(dto.getId()) != null) {
/* 292 */         NcuConfigDTO exitDTO = (NcuConfigDTO)this.listStore.findModelWithKey(dto.getId());
/* 293 */         exitDTO.setDataTimeNow(dto.getDataTimeNow());
/* 294 */         this.listStore.update(exitDTO);
/*     */       } 
/*     */     } else {
/* 297 */       Info.display("回傳失敗", "查詢失敗");
/*     */     } 
/* 299 */     unmask();
/*     */   }
/*     */   
/*     */   public void showInfo(String event) {
/* 303 */     unmask();
/* 304 */     if (event.equals("add")) {
/* 305 */       Info.display("新增成功", "");
/* 306 */     } else if (event.equals("update")) {
/* 307 */       Info.display("修改成功", "");
/*     */     } else {
/* 309 */       Info.display("刪除成功", "");
/*     */     } 
/*     */     
/* 312 */     this.deviceName.clear();
/* 313 */     this.displayName.clear();
/* 314 */     this.ip.clear();
/* 315 */     this.port.clear();
/* 316 */     this.latitude.clear();
/* 317 */     this.longitude.clear();
/*     */     
/* 319 */     mask("資料查詢中");
/* 320 */     this.presenter.getNcuConfig();
/*     */   }
/*     */   
/*     */   public void fillInputField() {
/* 324 */     NcuConfigDTO dto = (NcuConfigDTO)this.grid.getSelectionModel().getSelectedItem();
/* 325 */     this.deviceName.setValue(dto.getId());
/* 326 */     this.displayName.setValue(dto.getName());
/* 327 */     this.ip.setValue(dto.getIp());
/* 328 */     this.port.setValue(dto.getPort());
/* 329 */     this.longitude.setValue(dto.getLongitude());
/* 330 */     this.latitude.setValue(dto.getLatitude());
/* 331 */     this.alarmTime.setValue(dto.getAlarmTime());
/* 332 */     this.enableCombo.setValue(dto.isEnable() ? "是" : "否");
/*     */   }
/*     */   
/*     */   private NcuConfigDTO getInputData() {
/* 336 */     NcuConfigDTO dto = new NcuConfigDTO();
/* 337 */     dto.setId((String)this.deviceName.getValue());
/* 338 */     dto.setName((String)this.displayName.getValue());
/* 339 */     dto.setIp((String)this.ip.getValue());
/* 340 */     dto.setPort((String)this.port.getValue());
/* 341 */     dto.setLongitude((Double)this.longitude.getValue());
/* 342 */     dto.setLatitude((Double)this.latitude.getValue());
/* 343 */     dto.setAlarmTime((Integer)this.alarmTime.getValue());
/* 344 */     dto.setEnable(((String)this.enableCombo.getValue()).equals("是"));
/* 345 */     return dto;
/*     */   }
/*     */   
/*     */   @UiHandler({"grid"})
/*     */   public void rowClick(RowClickEvent event) {
/* 350 */     if (this.grid.getSelectionModel().getSelectedItem() != null) {
/* 351 */       this.delete.enable();
/* 352 */       this.update.enable();
/*     */     } 
/* 354 */     fillInputField();
/*     */   }
/*     */   
/*     */   @UiHandler({"queryNowTime"})
/*     */   public void onQueryNowTime(SelectEvent event) {
/* 359 */     if (this.grid.getSelectionModel().getSelectedItem() != null) {
/* 360 */       mask("查詢時間...");
/* 361 */       this.presenter.getNcuHostDataTimeNow(((NcuConfigDTO)this.grid.getSelectionModel().getSelectedItem()).getId());
/*     */     } else {
/* 363 */       Info.display("訊息提示", "請選擇機房主機");
/*     */     } 
/*     */   }
/*     */   
/*     */   @UiHandler({"synchronize"})
/*     */   public void onSynchronize(SelectEvent event) {
/* 369 */     if (this.grid.getSelectionModel().getSelectedItem() != null) {
/* 370 */       mask("同步時間...");
/* 371 */       this.presenter.synchronizeNcuHostTime(((NcuConfigDTO)this.grid.getSelectionModel().getSelectedItem()).getId());
/*     */     } else {
/* 373 */       Info.display("訊息提示", "請選擇機房主機");
/*     */     } 
/*     */   }
/*     */   
/*     */   @UiHandler({"save"})
/*     */   public void onSave(SelectEvent se) {
/* 379 */     if (this.deviceName != null && this.listStore.findModelWithKey((String)this.deviceName.getCurrentValue()) != null) {
/* 380 */       Info.display("設備已存在", "請重新輸入");
/*     */       
/*     */       return;
/*     */     } 
/* 384 */     if (!checkField()) {
/* 385 */       Info.display("輸入欄位有誤", "請重新輸入");
/*     */       
/*     */       return;
/*     */     } 
/* 389 */     mask("新增中...");
/* 390 */     this.presenter.addNcuConfig(getInputData());
/*     */   }
/*     */   
/*     */   @UiHandler({"update"})
/*     */   public void onUpdate(SelectEvent se) {
/* 395 */     if (this.deviceName == null || this.listStore.findModelWithKey((String)this.deviceName.getCurrentValue()) == null) {
/* 396 */       Info.display("設備不存在", "請重新選取");
/*     */       
/*     */       return;
/*     */     } 
/* 400 */     if (!checkField()) {
/* 401 */       Info.display("輸入欄位有誤", "請重新輸入");
/*     */       
/*     */       return;
/*     */     } 
/* 405 */     mask("修改中...");
/* 406 */     this.presenter.updateNcuConfig(getInputData());
/*     */   }
/*     */   
/*     */   @UiHandler({"delete"})
/*     */   public void onDelete(SelectEvent se) {
/* 411 */     if (this.deviceName.getCurrentValue() == null) {
/* 412 */       Info.display("欲刪除設備為空", "請重新選取");
/*     */       
/*     */       return;
/*     */     } 
/* 416 */     if (this.listStore.findModelWithKey((String)this.deviceName.getCurrentValue()) == null) {
/* 417 */       Info.display("欲刪除設備不存在", "請重新選取");
/*     */       
/*     */       return;
/*     */     } 
/* 421 */     mask("刪除中...");
/* 422 */     this.presenter.deleteNcuConfig((String)this.deviceName.getValue());
/*     */   }
/*     */   
/*     */   @UiHandler({"printOne"})
/*     */   public void printOne(SelectEvent event) {
/* 427 */     if (((String)this.deviceName.getCurrentValue()).equals(null) || ((String)this.deviceName.getCurrentValue()).equals("")) {
/* 428 */       Info.display("請選擇單一機房", "查詢條件不能為空");
/*     */       return;
/*     */     } 
/* 431 */     mask("匯出單一機房權限中");
/* 432 */     this.presenter.getOneRoomPermissin((String)this.deviceName.getCurrentValue());
/*     */   }
/*     */   
/*     */   @UiHandler({"printAll"})
/*     */   public void printAll(SelectEvent event) {
/* 437 */     mask("匯出全部機房權限中");
/* 438 */     this.presenter.getAllRoomPermissin();
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void exportCSV(List<RoomPermissionDTO> response) {
/* 458 */     unmask();
/* 459 */     if (response.size() == 0) {
/* 460 */       Info.display("無資料內容", "不進行匯出");
/*     */       return;
/*     */     } 
/* 463 */     List<List<String>> csvData = new ArrayList<>();
/*     */     
/* 465 */     List<String> title = new ArrayList<>();
/* 466 */     title.add("機房權限");
/* 467 */     csvData.add(title);
/*     */     
/* 469 */     List<String> conditions = new ArrayList<>();
/* 470 */     conditions.add("匯出時間:");
/* 471 */     Date time = new Date();
/* 472 */     DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
/*     */     try {
/* 474 */       conditions.add(format.format(time));
/* 475 */     } catch (Exception e) {
/* 476 */       GWT.log("Transfer date failed.", e);
/*     */     } 
/* 478 */     csvData.add(conditions);
/*     */ 
/*     */     
/* 481 */     List<String> header = new ArrayList<>();
/* 482 */     header.add("機房");
/* 483 */     header.add("人員");
/* 484 */     header.add("卡號");
/* 485 */     header.add("開始時間");
/* 486 */     header.add("結束時間");
/* 487 */     header.add("公司");
/* 488 */     header.add("電話");
/* 489 */     csvData.add(header);
/*     */ 
/*     */     
/* 492 */     List<RoomPermissionDTO> infoData = response;
/* 493 */     for (RoomPermissionDTO item : infoData) {
/* 494 */       List<String> csvDatum = new ArrayList<>();
/* 495 */       for (String head : header) {
/* 496 */         switch (head) {
/*     */           case "機房":
/* 498 */             csvDatum.add(item.getRoom().equals(null) ? "" : item.getRoom());
/*     */           
/*     */           case "人員":
/* 501 */             csvDatum.add(item.getName().equals(null) ? "" : item.getName());
/*     */           
/*     */           case "卡號":
/* 504 */             csvDatum.add(item.getCard().equals(null) ? "" : item.getCard());
/*     */           
/*     */           case "開始時間":
/* 507 */             csvDatum.add(
/* 508 */                 StringConverter.getTimeyyyyMMdd(item.getCardStartDate()).equals(null) ? "" : 
/*     */                 
/* 510 */                 StringConverter.getTimeyyyyMMdd(item.getCardStartDate()));
/*     */           
/*     */           case "結束時間":
/* 513 */             csvDatum.add(
/* 514 */                 StringConverter.getTimeyyyyMMdd(item.getCardEndDate()).equals(null) ? "" : 
/*     */                 
/* 516 */                 StringConverter.getTimeyyyyMMdd(item.getCardEndDate()));
/*     */           
/*     */           case "公司":
/* 519 */             csvDatum.add(item.getCompany().equals(null) ? "" : item.getCompany());
/*     */           
/*     */           case "電話":
/* 522 */             csvDatum.add(item.getPhone().equals(null) ? "" : item.getPhone());
/*     */         } 
/*     */ 
/*     */ 
/*     */       
/*     */       } 
/* 528 */       csvData.add(csvDatum);
/*     */     } 
/* 530 */     ExportCsvFile.exportAsCsv("機房權限.csv", csvData);
/*     */   }
/*     */   
/*     */   static interface NcuConfigSettingViewerUiBinder extends UiBinder<Widget, NcuConfigSettingViewer> {}
/*     */   
/*     */   static interface NcuConfigPropertyAccess extends PropertyAccess<NcuConfigDTO> {
/*     */     ModelKeyProvider<NcuConfigDTO> id();
/*     */     
/*     */     ValueProvider<NcuConfigDTO, String> name();
/*     */     
/*     */     ValueProvider<NcuConfigDTO, Boolean> enable();
/*     */     
/*     */     ValueProvider<NcuConfigDTO, String> ip();
/*     */     
/*     */     ValueProvider<NcuConfigDTO, String> port();
/*     */     
/*     */     ValueProvider<NcuConfigDTO, Double> longitude();
/*     */     
/*     */     ValueProvider<NcuConfigDTO, Double> latitude();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\NcuConfigSettingViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */