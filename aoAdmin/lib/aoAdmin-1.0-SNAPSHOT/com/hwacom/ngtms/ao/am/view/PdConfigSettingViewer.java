/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.event.shared.GwtEvent;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.uibinder.client.UiHandler;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.presenter.PdConfigSettingPresenter;
/*     */ import com.hwacom.ngtms.c.shared.Direction;
/*     */ import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.RoadSectionDTO;
/*     */ import com.hwacom.ngtms.cam.client.ui.AmTab;
/*     */ import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
/*     */ import com.hwacom.ngtms.pd.am.event.PdConfigSettingViewerEvent;
/*     */ import com.hwacom.ngtms.pd.am.util.StringConverter;
/*     */ import com.hwacom.ngtms.pd.shared.dto.DirectionDTO;
/*     */ import com.hwacom.ngtms.pd.shared.dto.LocationDTO;
/*     */ import com.hwacom.ngtms.pd.shared.dto.PdConfigDTO;
/*     */ import com.sencha.gxt.core.client.Style;
/*     */ import com.sencha.gxt.core.client.ValueProvider;
/*     */ import com.sencha.gxt.data.shared.LabelProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.data.shared.SortDir;
/*     */ import com.sencha.gxt.data.shared.Store;
/*     */ import com.sencha.gxt.widget.core.client.Dialog;
/*     */ import com.sencha.gxt.widget.core.client.button.TextButton;
/*     */ import com.sencha.gxt.widget.core.client.event.RowClickEvent;
/*     */ import com.sencha.gxt.widget.core.client.event.SelectEvent;
/*     */ import com.sencha.gxt.widget.core.client.form.ComboBox;
/*     */ import com.sencha.gxt.widget.core.client.form.DoubleField;
/*     */ import com.sencha.gxt.widget.core.client.form.IntegerField;
/*     */ import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
/*     */ import com.sencha.gxt.widget.core.client.form.TextField;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*     */ import com.sencha.gxt.widget.core.client.grid.Grid;
/*     */ import com.sencha.gxt.widget.core.client.info.Info;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class PdConfigSettingViewer
/*     */   extends AmTab
/*     */ {
/*  48 */   private static PdConfigSettingViewerUiBinder uiBinder = (PdConfigSettingViewerUiBinder)GWT.create(PdConfigSettingViewerUiBinder.class);
/*     */ 
/*     */ 
/*     */   
/*  52 */   private final PdConfigPropertyAccess propertyAccess = (PdConfigPropertyAccess)GWT.create(PdConfigPropertyAccess.class);
/*     */   
/*  54 */   private static Messages messages = (Messages)GWT.create(Messages.class);
/*     */   
/*  56 */   private PdConfigSettingPresenter presenter = new PdConfigSettingPresenter(this);
/*     */   
/*  58 */   private ClientFactory clientFactory = (ClientFactory)GWT.create(ClientFactory.class);
/*     */   
/*     */   @UiField
/*     */   Grid<PdConfigDTO> grid;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<PdConfigDTO> listStore;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ColumnModel<PdConfigDTO> columnModel;
/*     */   
/*     */   @UiField
/*     */   ComboBox<DirectionDTO> direction;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<DirectionDTO> directionStore;
/*     */   
/*     */   @UiField(provided = true)
/*     */   LabelProvider<DirectionDTO> directionProvider;
/*     */   
/*     */   @UiField
/*     */   ComboBox<LocationDTO> location;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<LocationDTO> locationStore;
/*     */   
/*     */   @UiField(provided = true)
/*     */   LabelProvider<LocationDTO> locationProvider;
/*     */   
/*     */   @UiField
/*     */   ComboBox<RoadLineDTO> line;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<RoadLineDTO> lineStore;
/*     */   
/*     */   @UiField(provided = true)
/*     */   LabelProvider<RoadLineDTO> lineProvider;
/*     */   @UiField
/*     */   ComboBox<RoadSectionDTO> section;
/*     */   @UiField(provided = true)
/*     */   ListStore<RoadSectionDTO> sectionStore;
/*     */   @UiField(provided = true)
/*     */   LabelProvider<RoadSectionDTO> sectionProvider;
/*     */   @UiField
/*     */   TextField deviceName;
/*     */   @UiField
/*     */   TextField displayName;
/*     */   @UiField
/*     */   IntegerField kilometer;
/*     */   @UiField
/*     */   IntegerField meter;
/*     */   @UiField
/*     */   TextField meterNo;
/*     */   @UiField
/*     */   TextField area;
/*     */   @UiField
/*     */   TextField phone;
/*     */   @UiField
/*     */   TextButton save;
/*     */   @UiField
/*     */   TextButton update;
/*     */   @UiField
/*     */   TextButton delete;
/*     */   @UiField
/*     */   TextField ip;
/*     */   @UiField
/*     */   IntegerField port;
/*     */   @UiField
/*     */   DoubleField longitude;
/*     */   @UiField
/*     */   DoubleField latitude;
/*     */   @UiField
/*     */   IntegerField loopNo;
/*     */   @UiField
/*     */   TextField memo;
/*     */   @UiField(provided = true)
/*     */   SimpleComboBox<String> enable;
/*     */   private Dialog confirmDialog;
/* 136 */   private Boolean check = Boolean.valueOf(false);
/*     */   
/*     */   public PdConfigSettingViewer() {
/* 139 */     initComboBox();
/* 140 */     this.directionStore = new ListStore(new ModelKeyProvider<DirectionDTO>()
/*     */         {
/*     */           
/*     */           public String getKey(DirectionDTO item)
/*     */           {
/* 145 */             return item.toString();
/*     */           }
/*     */         });
/* 148 */     this.directionProvider = new LabelProvider<DirectionDTO>()
/*     */       {
/*     */         public String getLabel(DirectionDTO item)
/*     */         {
/* 152 */           return StringConverter.getFormattedDirection(item.getDirection());
/*     */         }
/*     */       };
/*     */     
/* 156 */     this.locationStore = new ListStore(new ModelKeyProvider<LocationDTO>()
/*     */         {
/*     */           
/*     */           public String getKey(LocationDTO item)
/*     */           {
/* 161 */             return item.toString();
/*     */           }
/*     */         });
/* 164 */     this.locationProvider = new LabelProvider<LocationDTO>()
/*     */       {
/*     */         public String getLabel(LocationDTO item)
/*     */         {
/* 168 */           return item.getLocName();
/*     */         }
/*     */       };
/*     */     
/* 172 */     this.lineStore = new ListStore(new ModelKeyProvider<RoadLineDTO>()
/*     */         {
/*     */           
/*     */           public String getKey(RoadLineDTO item)
/*     */           {
/* 177 */             return item.toString();
/*     */           }
/*     */         });
/*     */     
/* 181 */     this.lineProvider = new LabelProvider<RoadLineDTO>()
/*     */       {
/*     */         public String getLabel(RoadLineDTO item)
/*     */         {
/* 185 */           return item.getLineName();
/*     */         }
/*     */       };
/*     */     
/* 189 */     this.sectionStore = new ListStore(new ModelKeyProvider<RoadSectionDTO>()
/*     */         {
/*     */           
/*     */           public String getKey(RoadSectionDTO item)
/*     */           {
/* 194 */             return item.toString();
/*     */           }
/*     */         });
/*     */     
/* 198 */     this.sectionProvider = new LabelProvider<RoadSectionDTO>()
/*     */       {
/*     */         public String getLabel(RoadSectionDTO item)
/*     */         {
/* 202 */           return item.getSectionName();
/*     */         }
/*     */       };
/*     */     
/* 206 */     this.listStore = new ListStore(this.propertyAccess.deviceName());
/* 207 */     initColumnModel();
/* 208 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/* 209 */     setDirectionData();
/* 210 */     this.listStore.addSortInfo(new Store.StoreSortInfo(new Comparator<PdConfigDTO>()
/*     */           {
/*     */             
/*     */             public int compare(PdConfigDTO o1, PdConfigDTO o2)
/*     */             {
/* 215 */               int flag = o1.getLineId().compareTo(o2.getLineId());
/* 216 */               if (flag == 0) {
/* 217 */                 return o1.getMilepost().compareTo(o2.getMilepost());
/*     */               }
/* 219 */               return flag;
/*     */             }
/*     */           },  SortDir.ASC));
/*     */ 
/*     */     
/* 224 */     this.grid.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
/*     */     
/* 226 */     mask(messages.pdConfigSettingViewer_info_adjusting());
/*     */     
/* 228 */     this.enable.add(messages.pdConfigSettingViewer_enable_true());
/* 229 */     this.enable.add(messages.pdConfigSettingViewer_enable_false());
/* 230 */     this.presenter.retrievePdConfigDTO();
/* 231 */     this.presenter.retrieveLocation();
/* 232 */     this.presenter.retrieveRoadLine();
/* 233 */     this.presenter.retrieveSection();
/*     */   }
/*     */   
/*     */   private void initColumnModel() {
/* 237 */     List<ColumnConfig<PdConfigDTO, ?>> columnConfigs = new ArrayList<>();
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
/*     */ 
/*     */     
/* 257 */     ColumnConfig<PdConfigDTO, String> displayName = new ColumnConfig(new ValueProvider<PdConfigDTO, String>() { public String getValue(PdConfigDTO dto) { return dto.getDisplayName(); } public void setValue(PdConfigDTO object, String value) {} public String getPath() { return "displayName"; } }, 100, messages.column_pdConfig_displayName());
/* 258 */     columnConfigs.add(displayName);
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
/*     */     
/* 277 */     ColumnConfig<PdConfigDTO, String> location = new ColumnConfig(new ValueProvider<PdConfigDTO, String>() { public String getValue(PdConfigDTO dto) { return dto.getLocation(); } public void setValue(PdConfigDTO object, String value) {} public String getPath() { return "location"; } }, 100, messages.column_pdConfig_locationNo());
/* 278 */     columnConfigs.add(location);
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
/*     */     
/* 297 */     ColumnConfig<PdConfigDTO, String> lineName = new ColumnConfig(new ValueProvider<PdConfigDTO, String>() { public String getValue(PdConfigDTO dto) { return dto.getLineName(); } public void setValue(PdConfigDTO object, String value) {} public String getPath() { return "lineName"; } }, 80, messages.column_pdConfig_lineId());
/* 298 */     columnConfigs.add(lineName);
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
/*     */     
/* 317 */     ColumnConfig<PdConfigDTO, String> direction = new ColumnConfig(new ValueProvider<PdConfigDTO, String>() { public String getValue(PdConfigDTO dto) { return StringConverter.getFormattedDirection(dto.getDirection()); } public void setValue(PdConfigDTO object, String value) {} public String getPath() { return "direction"; } }, 60, messages.column_pdConfig_direction());
/* 318 */     columnConfigs.add(direction);
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
/*     */     
/* 337 */     ColumnConfig<PdConfigDTO, String> milepost = new ColumnConfig(new ValueProvider<PdConfigDTO, String>() { public String getValue(PdConfigDTO dto) { return StringConverter.getFormattedMileage(dto.getMilepost()); } public void setValue(PdConfigDTO object, String value) {} public String getPath() { return "milepost"; } }, 80, messages.column_pdConfig_milepost());
/* 338 */     columnConfigs.add(milepost);
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
/*     */     
/* 357 */     ColumnConfig<PdConfigDTO, String> sectionName = new ColumnConfig(new ValueProvider<PdConfigDTO, String>() { public String getValue(PdConfigDTO dto) { return dto.getSectionName(); } public void setValue(PdConfigDTO object, String value) {} public String getPath() { return "sectionName"; } }, 120, messages.column_pdConfig_section());
/* 358 */     columnConfigs.add(sectionName);
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
/*     */     
/* 377 */     ColumnConfig<PdConfigDTO, String> meterNo = new ColumnConfig(new ValueProvider<PdConfigDTO, String>() { public String getValue(PdConfigDTO dto) { return dto.getMeterNo(); } public void setValue(PdConfigDTO object, String value) {} public String getPath() { return "meterNo"; } }, 100, messages.column_pdConfig_meterNo());
/* 378 */     columnConfigs.add(meterNo);
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
/*     */     
/* 397 */     ColumnConfig<PdConfigDTO, String> area = new ColumnConfig(new ValueProvider<PdConfigDTO, String>() { public String getValue(PdConfigDTO dto) { return dto.getArea(); } public void setValue(PdConfigDTO object, String value) {} public String getPath() { return "area"; } }, 80, messages.column_pdConfig_area());
/* 398 */     columnConfigs.add(area);
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
/*     */     
/* 417 */     ColumnConfig<PdConfigDTO, String> phone = new ColumnConfig(new ValueProvider<PdConfigDTO, String>() { public String getValue(PdConfigDTO dto) { return dto.getPhone(); } public void setValue(PdConfigDTO object, String value) {} public String getPath() { return "phone"; } }, 100, messages.column_pdConfig_phone());
/* 418 */     columnConfigs.add(phone);
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
/*     */     
/* 437 */     ColumnConfig<PdConfigDTO, String> ip = new ColumnConfig(new ValueProvider<PdConfigDTO, String>() { public String getValue(PdConfigDTO dto) { return dto.getIp(); } public void setValue(PdConfigDTO object, String value) {} public String getPath() { return "ip"; } }, 100, messages.column_pdConfig_ip());
/* 438 */     columnConfigs.add(ip);
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
/*     */     
/* 457 */     ColumnConfig<PdConfigDTO, Integer> port = new ColumnConfig(new ValueProvider<PdConfigDTO, Integer>() { public Integer getValue(PdConfigDTO dto) { return dto.getPort(); } public void setValue(PdConfigDTO object, Integer value) {} public String getPath() { return "port"; } }, 80, messages.column_pdConfig_port());
/* 458 */     columnConfigs.add(port);
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
/*     */     
/* 477 */     ColumnConfig<PdConfigDTO, Double> longitude = new ColumnConfig(new ValueProvider<PdConfigDTO, Double>() { public Double getValue(PdConfigDTO dto) { return dto.getLongitude(); } public void setValue(PdConfigDTO object, Double value) {} public String getPath() { return "longitude"; } }, 120, messages.column_pdConfig_longitude());
/* 478 */     columnConfigs.add(longitude);
/*     */     
/* 480 */     ColumnConfig<PdConfigDTO, Double> latitude = new ColumnConfig(new ValueProvider<PdConfigDTO, Double>()
/*     */         {
/*     */           
/*     */           public Double getValue(PdConfigDTO dto)
/*     */           {
/* 485 */             return dto.getLatitude();
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(PdConfigDTO object, Double value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 493 */             return "latitude";
/*     */           }
/*     */         },  120, "緯度");
/*     */ 
/*     */     
/* 498 */     columnConfigs.add(latitude);
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
/*     */     
/* 517 */     ColumnConfig<PdConfigDTO, Integer> loopNo = new ColumnConfig(new ValueProvider<PdConfigDTO, Integer>() { public Integer getValue(PdConfigDTO dto) { return dto.getLoopNo(); } public void setValue(PdConfigDTO object, Integer value) {} public String getPath() { return "loopNo"; } }, 70, messages.column_pdConfig_loopNo());
/* 518 */     columnConfigs.add(loopNo);
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
/*     */     
/* 537 */     ColumnConfig<PdConfigDTO, String> memo = new ColumnConfig(new ValueProvider<PdConfigDTO, String>() { public String getValue(PdConfigDTO dto) { return dto.getIp(); } public void setValue(PdConfigDTO object, String value) {} public String getPath() { return "memo"; } }, 100, messages.column_pdConfig_memo());
/* 538 */     columnConfigs.add(memo);
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
/*     */ 
/*     */ 
/*     */     
/* 559 */     ColumnConfig<PdConfigDTO, String> enable = new ColumnConfig(new ValueProvider<PdConfigDTO, String>() { public String getValue(PdConfigDTO dto) { return dto.getEnable().booleanValue() ? PdConfigSettingViewer.messages.pdConfigSettingViewer_enable_true() : PdConfigSettingViewer.messages.pdConfigSettingViewer_enable_false(); } public void setValue(PdConfigDTO object, String value) {} public String getPath() { return "enable"; } }, 60, messages.column_pdConfig_enable());
/* 560 */     columnConfigs.add(enable);
/*     */     
/* 562 */     this.columnModel = new ColumnModel(columnConfigs);
/*     */   }
/*     */   
/*     */   @UiHandler({"grid"})
/*     */   public void rowClick(RowClickEvent event) {
/* 567 */     PdConfigDTO dto = (PdConfigDTO)this.grid.getSelectionModel().getSelectedItem();
/* 568 */     this.deviceName.setValue(dto.getDeviceName());
/* 569 */     this.displayName.setValue(dto.getDisplayName());
/*     */     
/* 571 */     if (dto.getLineId() != null) {
/* 572 */       for (RoadLineDTO each : this.lineStore.getAll()) {
/* 573 */         if (each.getLineId() == dto.getLineId()) {
/* 574 */           this.line.setValue(each);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 579 */     if (dto.getLocation() != null) {
/* 580 */       for (LocationDTO each : this.locationStore.getAll()) {
/* 581 */         if (each.getLocName().equals(dto.getLocation())) {
/* 582 */           this.location.setValue(each);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 588 */     if (dto.getDirection() != null) {
/* 589 */       for (DirectionDTO each : this.directionStore.getAll()) {
/* 590 */         if (each.getDirection() == dto.getDirection()) {
/* 591 */           this.direction.setValue(each);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 597 */     if (dto.getSectionId() != null) {
/* 598 */       for (RoadSectionDTO each : this.sectionStore.getAll()) {
/* 599 */         if (each.getSectionId() == dto.getSectionId()) {
/* 600 */           this.section.setValue(each);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 606 */     this.enable.setValue(dto.getEnable().booleanValue() ? this.enable.getStore().get(0) : this.enable.getStore().get(1));
/* 607 */     if (dto.getMilepost() != null) {
/* 608 */       this.kilometer.setValue(Integer.valueOf(dto.getMilepost().intValue() / 1000));
/* 609 */       this.meter.setValue(Integer.valueOf(dto.getMilepost().intValue() % 1000));
/*     */     } 
/* 611 */     this.meterNo.setValue((dto.getMeterNo() != null) ? dto.getMeterNo() : null);
/* 612 */     this.area.setValue((dto.getArea() != null) ? dto.getArea() : null);
/* 613 */     this.phone.setValue((dto.getPhone() != null) ? dto.getPhone() : null);
/* 614 */     this.ip.setValue((dto.getIp() != null) ? dto.getIp() : null);
/* 615 */     this.port.setValue((dto.getPort() != null) ? dto.getPort() : null);
/* 616 */     this.longitude.setValue((dto.getLongitude() != null) ? dto.getLongitude() : null);
/* 617 */     this.latitude.setValue((dto.getLatitude() != null) ? dto.getLatitude() : null);
/* 618 */     this.loopNo.setValue((dto.getLoopNo() != null) ? dto.getLoopNo() : null);
/* 619 */     this.memo.setValue((dto.getMemo() != null) ? dto.getMemo() : null);
/*     */   }
/*     */   
/*     */   @UiHandler({"save"})
/*     */   public void onSave(SelectEvent se) {
/* 624 */     if (!checkField().booleanValue()) {
/* 625 */       Info.display(messages
/* 626 */           .pdConfigSettingViewer_info_fieldEmpty(), messages
/* 627 */           .pdConfigSettingViewer_info_inputAgain());
/*     */       return;
/*     */     } 
/* 630 */     PdConfigDTO dto = getPdConfigData();
/* 631 */     mask("資料處理中");
/* 632 */     this.presenter.checkPdConfig((String)this.deviceName.getValue(), "save", dto);
/* 633 */     cleanField();
/*     */   }
/*     */   
/*     */   @UiHandler({"update"})
/*     */   public void onUpdate(SelectEvent se) {
/* 638 */     if (!checkField().booleanValue()) {
/* 639 */       Info.display(messages
/* 640 */           .pdConfigSettingViewer_info_fieldEmpty(), messages
/* 641 */           .pdConfigSettingViewer_info_inputAgain());
/*     */       return;
/*     */     } 
/* 644 */     PdConfigDTO dto = getPdConfigData();
/* 645 */     mask(messages.pdConfigSettingViewer_info_adjusting());
/* 646 */     this.presenter.checkPdConfig((String)this.deviceName.getValue(), "update", dto);
/* 647 */     cleanField();
/*     */   }
/*     */   
/*     */   @UiHandler({"delete"})
/*     */   public void onDelete(SelectEvent se) {
/* 652 */     if (this.deviceName.getCurrentValue() == null) {
/* 653 */       Info.display(messages
/* 654 */           .pdConfigSettingViewer_info_deviceNameEmpty(), messages
/* 655 */           .pdConfigSettingViewer_info_selectDeviceAgain());
/*     */       return;
/*     */     } 
/* 658 */     if (this.listStore.findModelWithKey((String)this.deviceName.getCurrentValue()) == null) {
/* 659 */       Info.display("無此設備", messages.pdConfigSettingViewer_info_selectDeviceAgain());
/*     */       return;
/*     */     } 
/* 662 */     mask(messages.pdConfigSettingViewer_info_adjusting());
/* 663 */     this.presenter.checkRemovedPdConfig((String)this.deviceName.getValue());
/*     */   }
/*     */   
/*     */   public void setDirectionData() {
/* 667 */     this.directionStore.clear();
/* 668 */     DirectionDTO directionN = new DirectionDTO();
/* 669 */     directionN.setId(messages.direction_north());
/* 670 */     directionN.setDirection(Direction.N);
/* 671 */     this.directionStore.add(directionN);
/* 672 */     DirectionDTO directionS = new DirectionDTO();
/* 673 */     directionS.setId(messages.direction_south());
/* 674 */     directionS.setDirection(Direction.S);
/* 675 */     this.directionStore.add(directionS);
/* 676 */     DirectionDTO directionW = new DirectionDTO();
/* 677 */     directionW.setId(messages.direction_west());
/* 678 */     directionW.setDirection(Direction.W);
/* 679 */     this.directionStore.add(directionW);
/* 680 */     DirectionDTO directionE = new DirectionDTO();
/* 681 */     directionE.setId(messages.direction_east());
/* 682 */     directionE.setDirection(Direction.E);
/* 683 */     this.directionStore.add(directionE);
/*     */   }
/*     */   
/*     */   private Boolean checkField() {
/* 687 */     if (this.deviceName.getCurrentValue() == null || this.displayName
/* 688 */       .getCurrentValue() == null || this.location
/* 689 */       .getCurrentValue() == null || this.line
/* 690 */       .getCurrentValue() == null || this.direction
/* 691 */       .getCurrentValue() == null || this.kilometer
/* 692 */       .getCurrentValue() == null || this.meter
/* 693 */       .getCurrentValue() == null || this.section
/* 694 */       .getCurrentValue() == null || this.ip
/* 695 */       .getCurrentValue() == null || this.port
/* 696 */       .getCurrentValue() == null || this.enable
/* 697 */       .getCurrentValue() == null) {
/* 698 */       return Boolean.valueOf(false);
/*     */     }
/* 700 */     return Boolean.valueOf(true);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initComboBox() {
/* 705 */     this.enable = new SimpleComboBox(new LabelProvider<String>()
/*     */         {
/*     */           
/*     */           public String getLabel(String item)
/*     */           {
/* 710 */             return item;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void fillRoadLine(List<RoadLineDTO> list) {
/* 716 */     this.lineStore.clear();
/* 717 */     this.lineStore.addAll(list);
/*     */   }
/*     */   
/*     */   public void fillLocation(List<LocationDTO> list) {
/* 721 */     this.locationStore.clear();
/* 722 */     this.locationStore.addAll(list);
/*     */   }
/*     */   
/*     */   public void fillSection(List<RoadSectionDTO> list) {
/* 726 */     this.sectionStore.clear();
/* 727 */     this.sectionStore.addAll(list);
/*     */   }
/*     */   
/*     */   public void refillGrid() {
/* 731 */     this.listStore.clear();
/* 732 */     this.presenter.retrievePdConfigDTO();
/*     */   }
/*     */   
/*     */   public void checkData(Boolean result) {
/* 736 */     unmask();
/* 737 */     this.check = result;
/*     */   }
/*     */   
/*     */   public void showExistInfo() {
/* 741 */     unmask();
/* 742 */     Info.display(messages
/* 743 */         .pdConfigSettingViewer_info_deviceExist(), messages
/* 744 */         .pdConfigSettingViewer_info_inputAgain());
/*     */   }
/*     */   
/*     */   public void confirmDialog(String deviceName) {
/* 748 */     if (this.confirmDialog == null) {
/* 749 */       initConfirmDialog(deviceName);
/*     */     }
/* 751 */     this.confirmDialog.show();
/*     */   }
/*     */   
/*     */   private void initConfirmDialog(final String deviceName) {
/* 755 */     this.confirmDialog = new Dialog();
/* 756 */     this.confirmDialog.setHeading(messages.pdConfigSettingViewer_confirmDeleteDevice());
/* 757 */     this.confirmDialog.setModal(true);
/* 758 */     this.confirmDialog.setWidth(260);
/* 759 */     this.confirmDialog.setHeight(100);
/* 760 */     TextButton confirmButton = new TextButton();
/* 761 */     confirmButton.setText(messages.pdConfigSettingViewer_confirm());
/* 762 */     confirmButton.addSelectHandler(new SelectEvent.SelectHandler()
/*     */         {
/*     */           public void onSelect(SelectEvent event)
/*     */           {
/* 766 */             PdConfigSettingViewer.this.presenter.deletePdConfig(deviceName);
/* 767 */             PdConfigSettingViewer.this.cleanField();
/* 768 */             PdConfigSettingViewer.this.confirmDialog.hide();
/*     */           }
/*     */         });
/* 771 */     TextButton cancelButton = new TextButton();
/* 772 */     cancelButton.setText(messages.pdConfigSettingViewer_cancel());
/* 773 */     cancelButton.addSelectHandler(new SelectEvent.SelectHandler()
/*     */         {
/*     */           public void onSelect(SelectEvent event)
/*     */           {
/* 777 */             PdConfigSettingViewer.this.unmask();
/* 778 */             PdConfigSettingViewer.this.confirmDialog.hide();
/*     */           }
/*     */         });
/* 781 */     this.confirmDialog.getButtonBar().clear();
/* 782 */     this.confirmDialog.getButtonBar().add((Widget)confirmButton);
/* 783 */     this.confirmDialog.getButtonBar().add((Widget)cancelButton);
/*     */   }
/*     */   
/*     */   public PdConfigDTO getPdConfigData() {
/* 787 */     PdConfigDTO dto = new PdConfigDTO();
/* 788 */     dto.setDeviceName((String)this.deviceName.getValue());
/* 789 */     dto.setDisplayName((String)this.displayName.getValue());
/* 790 */     dto.setLocationNo(String.valueOf(((LocationDTO)this.location.getCurrentValue()).getId()));
/* 791 */     dto.setLocation(((LocationDTO)this.location.getCurrentValue()).getLocName());
/* 792 */     dto.setLineId(((RoadLineDTO)this.line.getCurrentValue()).getLineId());
/* 793 */     dto.setLineName(((RoadLineDTO)this.line.getCurrentValue()).getLineName());
/* 794 */     dto.setDirection(((DirectionDTO)this.direction.getCurrentValue()).getDirection());
/* 795 */     dto.setMilepost(Integer.valueOf(((Integer)this.kilometer.getCurrentValue()).intValue() * 1000 + ((Integer)this.meter.getCurrentValue()).intValue()));
/* 796 */     dto.setSectionId(((RoadSectionDTO)this.section.getCurrentValue()).getSectionId());
/* 797 */     dto.setSectionName(((RoadSectionDTO)this.section.getCurrentValue()).getSectionName());
/* 798 */     dto.setMeterNo((String)this.meterNo.getValue());
/* 799 */     dto.setArea((String)this.area.getValue());
/* 800 */     dto.setPhone((String)this.phone.getValue());
/* 801 */     dto.setIp((String)this.ip.getValue());
/* 802 */     dto.setPort((Integer)this.port.getValue());
/* 803 */     dto.setLongitude((Double)this.longitude.getValue());
/* 804 */     dto.setLatitude((Double)this.latitude.getValue());
/* 805 */     dto.setLoopNo((Integer)this.loopNo.getValue());
/* 806 */     dto.setMemo((String)this.memo.getValue());
/* 807 */     dto.setEnable(
/* 808 */         Boolean.valueOf((this.enable.getCurrentValue() == messages.pdConfigSettingViewer_enable_true())));
/* 809 */     return dto;
/*     */   }
/*     */   
/*     */   public void addPdConfig(List<PdConfigDTO> list) {
/* 813 */     unmask();
/* 814 */     this.listStore.addAll(list);
/*     */   }
/*     */   
/*     */   public void cleanField() {
/* 818 */     this.deviceName.clear();
/* 819 */     this.displayName.clear();
/* 820 */     this.kilometer.clear();
/* 821 */     this.meterNo.clear();
/* 822 */     this.area.clear();
/* 823 */     this.phone.clear();
/* 824 */     this.ip.clear();
/* 825 */     this.port.clear();
/* 826 */     this.longitude.clear();
/* 827 */     this.latitude.clear();
/* 828 */     this.loopNo.clear();
/* 829 */     this.memo.clear();
/* 830 */     this.direction.clear();
/* 831 */     this.location.clear();
/* 832 */     this.line.clear();
/* 833 */     this.section.clear();
/* 834 */     this.enable.clear();
/* 835 */     this.meter.clear();
/*     */   }
/*     */   
/*     */   public void updatePdConfig() {
/* 839 */     this.clientFactory
/* 840 */       .getEventBus()
/* 841 */       .fireEvent((GwtEvent)new PdConfigSettingViewerEvent(PdConfigSettingViewerEvent.Action.UPDATE));
/*     */   }
/*     */   
/*     */   static interface PdConfigSettingViewerUiBinder extends UiBinder<Widget, PdConfigSettingViewer> {}
/*     */   
/*     */   static interface PdConfigPropertyAccess extends PropertyAccess<PdConfigDTO> {
/*     */     ModelKeyProvider<PdConfigDTO> deviceName();
/*     */     
/*     */     ValueProvider<PdConfigDTO, String> displayName();
/*     */     
/*     */     ValueProvider<PdConfigDTO, String> location();
/*     */     
/*     */     ValueProvider<PdConfigDTO, String> lineName();
/*     */     
/*     */     ValueProvider<PdConfigDTO, Direction> direction();
/*     */     
/*     */     ValueProvider<PdConfigDTO, Integer> milepost();
/*     */     
/*     */     ValueProvider<PdConfigDTO, String> sectionName();
/*     */     
/*     */     ValueProvider<PdConfigDTO, String> meterNo();
/*     */     
/*     */     ValueProvider<PdConfigDTO, String> area();
/*     */     
/*     */     ValueProvider<PdConfigDTO, String> phone();
/*     */     
/*     */     ValueProvider<PdConfigDTO, String> ip();
/*     */     
/*     */     ValueProvider<PdConfigDTO, Integer> port();
/*     */     
/*     */     ValueProvider<PdConfigDTO, Double> longitude();
/*     */     
/*     */     ValueProvider<PdConfigDTO, Double> latitude();
/*     */     
/*     */     ValueProvider<PdConfigDTO, Integer> loopNo();
/*     */     
/*     */     ValueProvider<PdConfigDTO, String> memo();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\PdConfigSettingViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */