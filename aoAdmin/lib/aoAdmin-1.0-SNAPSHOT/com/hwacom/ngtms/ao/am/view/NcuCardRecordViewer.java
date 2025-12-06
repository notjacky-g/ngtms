/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.event.logical.shared.SelectionEvent;
/*     */ import com.google.gwt.event.logical.shared.SelectionHandler;
/*     */ import com.google.gwt.i18n.client.DateTimeFormat;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.uibinder.client.UiHandler;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.presenter.NcuCardRecordPresenter;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomCardReaderLogQueryParamDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomNCUCardLogDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomNcuDeviceNameDTO;
/*     */ import com.hwacom.ngtms.cam.client.ui.AmTab;
/*     */ import com.hwacom.ngtms.cam.util.DateTimeUtil;
/*     */ import com.hwacom.ngtms.common.am.view.DateRangePicker;
/*     */ import com.sencha.gxt.core.client.ValueProvider;
/*     */ import com.sencha.gxt.data.shared.LabelProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.widget.core.client.event.SelectEvent;
/*     */ import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*     */ import com.sencha.gxt.widget.core.client.grid.Grid;
/*     */ import com.sencha.gxt.widget.core.client.info.Info;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ public class NcuCardRecordViewer
/*     */   extends AmTab
/*     */ {
/*  36 */   private static NcuCardRecordViewerUiBinder uiBinder = (NcuCardRecordViewerUiBinder)GWT.create(NcuCardRecordViewerUiBinder.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   private final RoomCardReaderLogDataAccess roomCardReaderAccess = (RoomCardReaderLogDataAccess)GWT.create(RoomCardReaderLogDataAccess.class);
/*     */   
/*  43 */   private NcuCardRecordPresenter presenter = new NcuCardRecordPresenter(this);
/*     */   
/*     */   @UiField
/*     */   DateRangePicker dateRangePicker;
/*     */   
/*     */   @UiField(provided = true)
/*     */   SimpleComboBox<RoomNcuDeviceNameDTO> ncuComboBox;
/*     */   
/*     */   @UiField(provided = true)
/*     */   SimpleComboBox<RoomNcuDeviceNameDTO> locationComboBox;
/*     */   
/*     */   @UiField
/*     */   Grid<RoomNCUCardLogDTO> recordGrid;
/*     */   @UiField(provided = true)
/*     */   ListStore<RoomNCUCardLogDTO> recordStore;
/*     */   @UiField(provided = true)
/*     */   ColumnModel<RoomNCUCardLogDTO> recordColumnModel;
/*     */   
/*     */   public NcuCardRecordViewer() {
/*  62 */     init();
/*  63 */     initColumnModel();
/*  64 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*     */     
/*  66 */     Date now = new Date();
/*  67 */     this.dateRangePicker.setStartDate(DateTimeUtil.dayStart(now));
/*  68 */     this.dateRangePicker.setEndDate(now);
/*  69 */     addEventHandler();
/*     */     
/*  71 */     this.presenter.getNcu();
/*     */   }
/*     */   
/*     */   private void init() {
/*  75 */     this.ncuComboBox = new SimpleComboBox(new LabelProvider<RoomNcuDeviceNameDTO>()
/*     */         {
/*     */           
/*     */           public String getLabel(RoomNcuDeviceNameDTO item)
/*     */           {
/*  80 */             return item.getDisplayName();
/*     */           }
/*     */         });
/*     */     
/*  84 */     this.locationComboBox = new SimpleComboBox(new LabelProvider<RoomNcuDeviceNameDTO>()
/*     */         {
/*     */           
/*     */           public String getLabel(RoomNcuDeviceNameDTO item)
/*     */           {
/*  89 */             return item.getDisplayName();
/*     */           }
/*     */         });
/*     */     
/*  93 */     this.recordStore = new ListStore(new ModelKeyProvider<RoomNCUCardLogDTO>()
/*     */         {
/*     */           
/*     */           public String getKey(RoomNCUCardLogDTO item)
/*     */           {
/*  98 */             return item.getId().toString();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void initColumnModel() {
/* 104 */     List<ColumnConfig<RoomNCUCardLogDTO, ?>> columnConfigs = new ArrayList<>();
/*     */ 
/*     */ 
/*     */     
/* 108 */     ColumnConfig<RoomNCUCardLogDTO, String> cardReaderConfig = new ColumnConfig(this.roomCardReaderAccess.displayName(), 100, "機房名稱");
/* 109 */     columnConfigs.add(cardReaderConfig);
/*     */ 
/*     */     
/* 112 */     ColumnConfig<RoomNCUCardLogDTO, String> locationConfig = new ColumnConfig(this.roomCardReaderAccess.location(), 100, "門禁位置");
/* 113 */     columnConfigs.add(locationConfig);
/*     */     
/* 115 */     ColumnConfig<RoomNCUCardLogDTO, String> cardNumberConfig = new ColumnConfig(new ValueProvider<RoomNCUCardLogDTO, String>()
/*     */         {
/*     */ 
/*     */           
/*     */           public String getValue(RoomNCUCardLogDTO dto)
/*     */           {
/* 121 */             return dto.getCardNumber();
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(RoomNCUCardLogDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 129 */             return "cardNumber";
/*     */           }
/*     */         },  60, "卡片號碼");
/*     */ 
/*     */     
/* 134 */     columnConfigs.add(cardNumberConfig);
/*     */ 
/*     */     
/* 137 */     ColumnConfig<RoomNCUCardLogDTO, String> nameConfig = new ColumnConfig(this.roomCardReaderAccess.name(), 60, "姓名");
/* 138 */     columnConfigs.add(nameConfig);
/*     */ 
/*     */     
/* 141 */     ColumnConfig<RoomNCUCardLogDTO, String> companyConfig = new ColumnConfig(this.roomCardReaderAccess.company(), 80, "公司名稱");
/* 142 */     columnConfigs.add(companyConfig);
/*     */     
/* 144 */     ColumnConfig<RoomNCUCardLogDTO, String> timeConfig = new ColumnConfig(new ValueProvider<RoomNCUCardLogDTO, String>()
/*     */         {
/*     */ 
/*     */           
/*     */           public String getValue(RoomNCUCardLogDTO dto)
/*     */           {
/* 150 */             return DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").format(dto.getTime());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(RoomNCUCardLogDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 158 */             return "time";
/*     */           }
/*     */         },  120, "時間");
/*     */ 
/*     */     
/* 163 */     columnConfigs.add(timeConfig);
/*     */     
/* 165 */     ColumnConfig<RoomNCUCardLogDTO, String> statusCodeConfig = new ColumnConfig(new ValueProvider<RoomNCUCardLogDTO, String>()
/*     */         {
/*     */ 
/*     */           
/*     */           public String getValue(RoomNCUCardLogDTO dto)
/*     */           {
/* 171 */             return dto.getStatusCode();
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(RoomNCUCardLogDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 179 */             return "statusCode";
/*     */           }
/*     */         },  60, "狀態");
/*     */ 
/*     */ 
/*     */     
/* 185 */     ColumnConfig<RoomNCUCardLogDTO, String> eventCodeConfig = new ColumnConfig(new ValueProvider<RoomNCUCardLogDTO, String>()
/*     */         {
/*     */ 
/*     */           
/*     */           public String getValue(RoomNCUCardLogDTO dto)
/*     */           {
/* 191 */             return dto.getEventCode();
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(RoomNCUCardLogDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 199 */             return "eventCode";
/*     */           }
/*     */         },  60, "事件");
/*     */ 
/*     */ 
/*     */     
/* 205 */     columnConfigs.add(eventCodeConfig);
/*     */     
/* 207 */     this.recordColumnModel = new ColumnModel(columnConfigs);
/*     */   }
/*     */   
/*     */   private void addEventHandler() {
/* 211 */     this.ncuComboBox.addSelectionHandler(new SelectionHandler<RoomNcuDeviceNameDTO>()
/*     */         {
/*     */           
/*     */           public void onSelection(SelectionEvent<RoomNcuDeviceNameDTO> event)
/*     */           {
/* 216 */             NcuCardRecordViewer.this.presenter.getlocationByNcu(((RoomNcuDeviceNameDTO)event.getSelectedItem()).getDeviceName());
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   @UiHandler({"queryButton"})
/*     */   public void onClickQuery(SelectEvent event) {
/* 223 */     if (checkFieldValue().booleanValue()) {
/* 224 */       mask("資料查詢中...");
/* 225 */       RoomCardReaderLogQueryParamDTO queryDto = new RoomCardReaderLogQueryParamDTO();
/* 226 */       queryDto.setStartTime(this.dateRangePicker.getStartDate());
/* 227 */       queryDto.setEndTime(this.dateRangePicker.getEndDate());
/* 228 */       queryDto.setNcnDeviceName(((RoomNcuDeviceNameDTO)this.ncuComboBox.getCurrentValue()).getDeviceName());
/* 229 */       queryDto.setLocation(((RoomNcuDeviceNameDTO)this.locationComboBox.getCurrentValue()).getDeviceName());
/* 230 */       this.presenter.getNcuCardReaderLog(queryDto);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Boolean checkFieldValue() {
/* 235 */     Date startDate = this.dateRangePicker.getStartDate();
/* 236 */     Date endDate = this.dateRangePicker.getEndDate();
/* 237 */     if (startDate.after(endDate)) {
/* 238 */       Info.display("查詢失敗", "起始時間需小於結束時間");
/* 239 */       return Boolean.valueOf(false);
/*     */     } 
/* 241 */     return Boolean.valueOf(true);
/*     */   }
/*     */   
/*     */   public void fillNcuStore(List<RoomNcuDeviceNameDTO> ncus) {
/* 245 */     if (ncus != null && ncus.size() > 0) {
/* 246 */       this.ncuComboBox.getStore().clear();
/* 247 */       this.ncuComboBox.getStore().addAll(ncus);
/* 248 */       this.ncuComboBox.setValue(this.ncuComboBox.getStore().get(0));
/* 249 */       this.presenter.getlocationByNcu(((RoomNcuDeviceNameDTO)this.ncuComboBox.getStore().get(0)).getDeviceName());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void fillLocationComboBox(List<RoomNcuDeviceNameDTO> locations) {
/* 254 */     this.locationComboBox.getStore().clear();
/* 255 */     this.locationComboBox.getStore().addAll(locations);
/* 256 */     if (locations != null && locations.size() > 0) {
/* 257 */       this.locationComboBox.setValue(this.locationComboBox.getStore().get(0));
/*     */     }
/*     */   }
/*     */   
/*     */   public void fillRecordStore(List<RoomNCUCardLogDTO> result) {
/* 262 */     this.recordStore.clear();
/* 263 */     this.recordStore.addAll(result);
/* 264 */     unmask();
/*     */   }
/*     */   
/*     */   static interface NcuCardRecordViewerUiBinder extends UiBinder<Widget, NcuCardRecordViewer> {}
/*     */   
/*     */   static interface RoomCardReaderLogDataAccess extends PropertyAccess<RoomNCUCardLogDTO> {
/*     */     ModelKeyProvider<RoomNCUCardLogDTO> id();
/*     */     
/*     */     ValueProvider<RoomNCUCardLogDTO, String> displayName();
/*     */     
/*     */     ValueProvider<RoomNCUCardLogDTO, String> location();
/*     */     
/*     */     ValueProvider<RoomNCUCardLogDTO, String> name();
/*     */     
/*     */     ValueProvider<RoomNCUCardLogDTO, String> company();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\NcuCardRecordViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */