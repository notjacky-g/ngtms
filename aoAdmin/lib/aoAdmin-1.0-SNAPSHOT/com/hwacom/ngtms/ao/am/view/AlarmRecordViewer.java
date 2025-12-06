/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ 
/*     */ import com.google.gwt.cell.client.AbstractCell;
/*     */ import com.google.gwt.cell.client.Cell;
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.i18n.client.DateTimeFormat;
/*     */ import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.uibinder.client.UiHandler;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.alarm.shared.AlarmState;
/*     */ import com.hwacom.ngtms.ao.am.AoEP;
/*     */ import com.hwacom.ngtms.ao.am.presenter.AlarmRecordPresenter;
/*     */ import com.hwacom.ngtms.ao.am.util.StringConverter;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmMessageDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmRecordQueryParamDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmTypeDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomNcuDeviceNameDTO;
/*     */ import com.hwacom.ngtms.cam.client.ui.AmTab;
/*     */ import com.hwacom.ngtms.cam.client.ui.ExportCsvFile;
/*     */ import com.hwacom.ngtms.cam.util.DateTimeUtil;
/*     */ import com.hwacom.ngtms.common.am.view.DateRangePicker;
/*     */ import com.sencha.gxt.core.client.ValueProvider;
/*     */ import com.sencha.gxt.data.shared.LabelProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.data.shared.SortDir;
/*     */ import com.sencha.gxt.data.shared.Store;
/*     */ import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
/*     */ import com.sencha.gxt.data.shared.loader.PagingLoadResult;
/*     */ import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
/*     */ import com.sencha.gxt.data.shared.loader.PagingLoader;
/*     */ import com.sencha.gxt.widget.core.client.event.SelectEvent;
/*     */ import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*     */ import com.sencha.gxt.widget.core.client.grid.Grid;
/*     */ import com.sencha.gxt.widget.core.client.grid.GridView;
/*     */ import com.sencha.gxt.widget.core.client.info.Info;
/*     */ import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ public class AlarmRecordViewer extends AmTab {
/*  48 */   private static AlarmRecordViewerUiBinder uiBinder = (AlarmRecordViewerUiBinder)GWT.create(AlarmRecordViewerUiBinder.class);
/*     */   
/*  50 */   private static final AlarmLogProperties props = (AlarmLogProperties)GWT.create(AlarmLogProperties.class);
/*     */ 
/*     */ 
/*     */   
/*  54 */   private AlarmRecordPresenter presenter = new AlarmRecordPresenter(this);
/*     */   
/*  56 */   private Integer maxTimeRange = Integer.valueOf(-1702967296);
/*     */   
/*     */   private PagingLoader<PagingLoadConfig, PagingLoadResult<AlarmMessageDTO>> pagingLoader;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ColumnModel<AlarmMessageDTO> cm;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<AlarmMessageDTO> store;
/*     */   
/*     */   @UiField
/*     */   GridView<AlarmMessageDTO> view;
/*     */   @UiField
/*     */   Grid<AlarmMessageDTO> grid;
/*     */   @UiField
/*     */   DateRangePicker dateRangePicker;
/*     */   @UiField(provided = true)
/*     */   SimpleComboBox<RoomNcuDeviceNameDTO> ncuComboBox;
/*     */   @UiField(provided = true)
/*     */   SimpleComboBox<AlarmTypeDTO> alarmMonitorTypeCombo;
/*     */   @UiField
/*     */   PagingToolBar pageToolBar;
/*     */   
/*     */   public AlarmRecordViewer() {
/*  80 */     this.store = new ListStore(props.id());
/*  81 */     this.store.addSortInfo(new Store.StoreSortInfo(AlarmLogProperties.timestamp, SortDir.DESC));
/*     */     
/*  83 */     initColumnModel();
/*  84 */     initAlarmMonitorTypeCombo();
/*  85 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*     */     
/*  87 */     Date now = new Date();
/*  88 */     this.dateRangePicker.setStartDate(DateTimeUtil.dayStart(now));
/*  89 */     this.dateRangePicker.setEndDate(now);
/*  90 */     this.pageToolBar.hide();
/*     */   }
/*     */   
/*     */   private void initColumnModel() {
/*  94 */     List<ColumnConfig<AlarmMessageDTO, ?>> columnConfigs = new ArrayList<>();
/*     */ 
/*     */     
/*  97 */     ColumnConfig<AlarmMessageDTO, String> timestampConfig = new ColumnConfig(AlarmLogProperties.timestamp, 200, "時間");
/*     */     
/*  99 */     timestampConfig.setFixed(true);
/* 100 */     columnConfigs.add(timestampConfig);
/*     */ 
/*     */     
/* 103 */     ColumnConfig<AlarmMessageDTO, String> alarmTypeConfig = new ColumnConfig(props.alarmType(), 100, "告警類別");
/* 104 */     columnConfigs.add(alarmTypeConfig);
/*     */ 
/*     */     
/* 107 */     ColumnConfig<AlarmMessageDTO, AlarmState> stateConfig = new ColumnConfig(props.state(), 100, "狀態");
/* 108 */     stateConfig.setCell((Cell)new AbstractCell<AlarmState>(new String[0])
/*     */         {
/*     */           public void render(Cell.Context context, AlarmState value, SafeHtmlBuilder sb)
/*     */           {
/* 112 */             if (value == AlarmState.UNACK_ALM) {
/* 113 */               sb.appendHtmlConstant("未確認");
/* 114 */             } else if (value == AlarmState.ACK_ALM) {
/* 115 */               sb.appendHtmlConstant("待處理");
/* 116 */             } else if (value == AlarmState.UNACK_RTN) {
/* 117 */               sb.appendHtmlConstant("已自動排除");
/* 118 */             } else if (value == AlarmState.ACK_RTN) {
/* 119 */               sb.appendHtmlConstant("已處理排除");
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 125 */     ColumnConfig<AlarmMessageDTO, String> displayNameConfig = new ColumnConfig(props.displayName(), 200, "設備名稱");
/* 126 */     columnConfigs.add(displayNameConfig);
/*     */ 
/*     */     
/* 129 */     ColumnConfig<AlarmMessageDTO, String> messageConfig = new ColumnConfig(props.message(), 150, "告警訊息");
/* 130 */     columnConfigs.add(messageConfig);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     this.cm = new ColumnModel(columnConfigs);
/*     */   }
/*     */   
/*     */   @UiHandler({"queryButton"})
/*     */   void queryButton(SelectEvent se) {
/* 141 */     Date startDate = this.dateRangePicker.getStartDate();
/* 142 */     Date endDate = this.dateRangePicker.getEndDate();
/* 143 */     if (startDate == null || endDate == null) {
/*     */       return;
/*     */     }
/*     */     
/* 147 */     if (startDate.after(endDate)) {
/* 148 */       Info.display(AoEP.messages.message(), "起始時間需小於結束時間");
/*     */       return;
/*     */     } 
/* 151 */     mask(AoEP.messages.message_waiting());
/* 152 */     AlarmRecordQueryParamDTO dto = new AlarmRecordQueryParamDTO();
/* 153 */     dto.setStartTime(startDate);
/* 154 */     dto.setEndTime(endDate);
/* 155 */     dto.setRoomDto((RoomNcuDeviceNameDTO)this.ncuComboBox.getCurrentValue());
/* 156 */     dto.setAlarmMonitorType((AlarmTypeDTO)this.alarmMonitorTypeCombo.getCurrentValue());
/* 157 */     this.presenter.getAlarmLogWithParam(dto);
/*     */   }
/*     */   
/*     */   public void fillPaginLoadResult(PagingLoadResultBean<List<AlarmMessageDTO>> resultBean) {
/* 161 */     if (resultBean != null && resultBean.getData() != null && resultBean.getData().size() > 0) {
/* 162 */       this.store.clear();
/* 163 */       this.store.addAll(resultBean.getData().get(0));
/* 164 */       unmask();
/*     */     } else {
/* 166 */       this.store.clear();
/* 167 */       Info.display(AoEP.messages.message(), "無告警紀錄");
/* 168 */       unmask();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void fillAlarmResult(List<AlarmMessageDTO> result) {
/* 173 */     this.store.clear();
/* 174 */     this.store.addAll(result);
/* 175 */     unmask();
/*     */   }
/*     */   
/*     */   public void fillNcuStore(List<RoomNcuDeviceNameDTO> ncus) {
/* 179 */     if (ncus != null && ncus.size() > 0) {
/* 180 */       this.ncuComboBox.getStore().clear();
/* 181 */       List<RoomNcuDeviceNameDTO> comboResult = new ArrayList<>();
/* 182 */       for (RoomNcuDeviceNameDTO dto : ncus) {
/* 183 */         if (!dto.getDeviceName().equals("NCU_4") && !dto.getDeviceName().equals("NCU_5")) {
/* 184 */           comboResult.add(dto);
/*     */         }
/*     */       } 
/* 187 */       RoomNcuDeviceNameDTO all = new RoomNcuDeviceNameDTO();
/* 188 */       all.setDeviceName("all");
/* 189 */       all.setDisplayName("全部");
/* 190 */       RoomNcuDeviceNameDTO centralControl = new RoomNcuDeviceNameDTO();
/* 191 */       centralControl.setDeviceName("centralControl");
/* 192 */       centralControl.setDisplayName("交控中心");
/* 193 */       this.ncuComboBox.getStore().add(all);
/* 194 */       this.ncuComboBox.getStore().add(centralControl);
/* 195 */       this.ncuComboBox.getStore().addAll(comboResult);
/* 196 */       this.ncuComboBox.setValue(this.ncuComboBox.getStore().get(0));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void fillAllAlarmType(List<AlarmTypeDTO> result) {
/* 201 */     if (result != null && result.size() > 0) {
/* 202 */       this.alarmMonitorTypeCombo.getStore().clear();
/* 203 */       this.alarmMonitorTypeCombo.getStore().addAll(result);
/* 204 */       this.alarmMonitorTypeCombo.setValue(this.alarmMonitorTypeCombo.getStore().get(0));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void initAlarmMonitorTypeCombo() {
/* 209 */     this.ncuComboBox = new SimpleComboBox(new LabelProvider<RoomNcuDeviceNameDTO>()
/*     */         {
/*     */           
/*     */           public String getLabel(RoomNcuDeviceNameDTO item)
/*     */           {
/* 214 */             return item.getDisplayName();
/*     */           }
/*     */         });
/*     */     
/* 218 */     this.alarmMonitorTypeCombo = new SimpleComboBox(new LabelProvider<AlarmTypeDTO>()
/*     */         {
/*     */           
/*     */           public String getLabel(AlarmTypeDTO item)
/*     */           {
/* 223 */             return item.getAlarmType().getName();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   @UiHandler({"exportButton"})
/*     */   void exportButton(SelectEvent se) {
/* 230 */     if (this.store.size() == 0) {
/* 231 */       Info.display("無查詢紀錄", "不進行匯出");
/*     */       
/*     */       return;
/*     */     } 
/* 235 */     List<List<String>> csvData = new ArrayList<>();
/*     */ 
/*     */     
/* 238 */     List<String> title = new ArrayList<>();
/* 239 */     title.add("告警紀錄");
/* 240 */     csvData.add(title);
/*     */ 
/*     */     
/* 243 */     List<String> conditions = new ArrayList<>();
/* 244 */     conditions.add("匯出時間:");
/* 245 */     Date time = new Date();
/* 246 */     DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
/*     */     try {
/* 248 */       conditions.add(format.format(time));
/* 249 */     } catch (Exception e) {
/* 250 */       GWT.log("Transfer date failed.", e);
/*     */     } 
/* 252 */     csvData.add(conditions);
/*     */ 
/*     */     
/* 255 */     List<String> header = new ArrayList<>();
/* 256 */     for (ColumnConfig<AlarmMessageDTO, ?> config : (Iterable<ColumnConfig<AlarmMessageDTO, ?>>)this.grid.getColumnModel().getColumns()) {
/* 257 */       if (!config.isHidden()) {
/* 258 */         header.add(config.getHeader().asString());
/*     */       }
/*     */     } 
/* 261 */     csvData.add(header);
/*     */ 
/*     */     
/* 264 */     List<AlarmMessageDTO> infoData = this.store.getAll();
/* 265 */     for (AlarmMessageDTO item : infoData) {
/* 266 */       List<String> csvDatum = new ArrayList<>();
/* 267 */       for (ColumnConfig<AlarmMessageDTO, ?> config : (Iterable<ColumnConfig<AlarmMessageDTO, ?>>)this.grid.getColumnModel().getColumns()) {
/* 268 */         if (!config.isHidden()) {
/* 269 */           csvDatum.add(
/* 270 */               (config.getValueProvider().getValue(item) == null) ? "" : 
/*     */               
/* 272 */               String.valueOf(config.getValueProvider().getValue(item)));
/*     */         }
/*     */       } 
/* 275 */       csvData.add(csvDatum);
/*     */     } 
/* 277 */     ExportCsvFile.exportAsCsv("告警紀錄.csv", csvData);
/*     */   }
/*     */   
/*     */   static interface AlarmRecordViewerUiBinder
/*     */     extends UiBinder<Widget, AlarmRecordViewer> {}
/*     */   
/*     */   static interface AlarmLogProperties extends PropertyAccess<AlarmMessageDTO> {
/* 284 */     public static final ValueProvider<AlarmMessageDTO, String> timestamp = new ValueProvider<AlarmMessageDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(AlarmMessageDTO object)
/*     */         {
/* 289 */           return StringConverter.getFormattedTime(object.getTimestamp());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(AlarmMessageDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 297 */           return "timestamp";
/*     */         }
/*     */       };
/*     */     ModelKeyProvider<AlarmMessageDTO> id();
/*     */     
/*     */     ValueProvider<AlarmMessageDTO, String> alarmType();
/*     */     
/*     */     ValueProvider<AlarmMessageDTO, AlarmState> state();
/*     */     
/*     */     ValueProvider<AlarmMessageDTO, String> displayName();
/*     */     
/*     */     ValueProvider<AlarmMessageDTO, String> message();
/*     */     
/*     */     ValueProvider<AlarmMessageDTO, Integer> alarmLevel(); }
/*     */   
/*     */   public PagingToolBar getPageToolBar() {
/* 313 */     return this.pageToolBar;
/*     */   }
/*     */   
/*     */   public PagingLoader<PagingLoadConfig, PagingLoadResult<AlarmMessageDTO>> getPagingLoader() {
/* 317 */     return this.pagingLoader;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\AlarmRecordViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */