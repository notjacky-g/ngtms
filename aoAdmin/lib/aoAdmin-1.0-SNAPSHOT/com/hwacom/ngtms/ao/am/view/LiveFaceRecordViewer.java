/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ 
/*     */ import com.google.gwt.cell.client.Cell;
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.event.logical.shared.ValueChangeEvent;
/*     */ import com.google.gwt.event.shared.EventHandler;
/*     */ import com.google.gwt.event.shared.GwtEvent;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.uibinder.client.UiHandler;
/*     */ import com.google.gwt.user.client.Timer;
/*     */ import com.google.gwt.user.client.ui.HasHorizontalAlignment;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.event.LiveFaceRecordEvent;
/*     */ import com.hwacom.ngtms.ao.am.presenter.LiveFaceRecordPresenter;
/*     */ import com.hwacom.ngtms.ao.shared.dto.LifeFaceLockCardDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomCardReaderLogQueryParamDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomNcuDeviceNameDTO;
/*     */ import com.hwacom.ngtms.cam.client.ui.AmTab;
/*     */ import com.hwacom.ngtms.cam.util.DateTimeUtil;
/*     */ import com.hwacom.ngtms.common.am.view.DateRangePicker;
/*     */ import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
/*     */ import com.sencha.gxt.cell.core.client.TextButtonCell;
/*     */ import com.sencha.gxt.core.client.ValueProvider;
/*     */ import com.sencha.gxt.core.client.resources.CommonStyles;
/*     */ import com.sencha.gxt.data.shared.LabelProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.data.shared.SortDir;
/*     */ import com.sencha.gxt.data.shared.Store;
/*     */ import com.sencha.gxt.widget.core.client.event.SelectEvent;
/*     */ import com.sencha.gxt.widget.core.client.form.CheckBox;
/*     */ import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*     */ import com.sencha.gxt.widget.core.client.grid.Grid;
/*     */ import com.sencha.gxt.widget.core.client.info.Info;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ public class LiveFaceRecordViewer
/*     */   extends AmTab
/*     */ {
/*  46 */   private static LiveFaceRecordViewerUiBinder uiBinder = (LiveFaceRecordViewerUiBinder)GWT.create(LiveFaceRecordViewerUiBinder.class);
/*     */ 
/*     */ 
/*     */   
/*  50 */   private LiveFaceRecordAccess propertyAccess = (LiveFaceRecordAccess)GWT.create(LiveFaceRecordAccess.class);
/*     */   
/*  52 */   private LiveFaceRecordPresenter presenter = new LiveFaceRecordPresenter(this);
/*     */   
/*  54 */   private static ClientFactory clientFactory = (ClientFactory)GWT.create(ClientFactory.class);
/*     */   
/*     */   @UiField
/*     */   DateRangePicker dateRangePicker;
/*     */   
/*     */   @UiField(provided = true)
/*     */   SimpleComboBox<RoomNcuDeviceNameDTO> ncuComboBox;
/*     */   
/*     */   @UiField
/*     */   CheckBox updateCheckBox;
/*     */   
/*     */   @UiField
/*     */   Grid<LifeFaceLockCardDTO> recordGrid;
/*     */   @UiField(provided = true)
/*     */   static ListStore<LifeFaceLockCardDTO> recordStore;
/*     */   @UiField(provided = true)
/*     */   ColumnModel<LifeFaceLockCardDTO> recordColumnModel;
/*  71 */   private Timer timer = null;
/*     */   
/*  73 */   private final int SECOND = 60000;
/*     */   
/*     */   public LiveFaceRecordViewer() {
/*  76 */     init();
/*  77 */     initColumnModel();
/*  78 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*  79 */     Date now = new Date();
/*  80 */     this.dateRangePicker.setStartDate(DateTimeUtil.dayStart(now));
/*  81 */     this.dateRangePicker.setEndDate(now);
/*     */     
/*  83 */     this.presenter.getNcu();
/*  84 */     clientFactory
/*  85 */       .getEventBus()
/*  86 */       .addHandler(LiveFaceRecordEvent.TYPE, (EventHandler)new DefaultLiveFaceRecordEventHandler());
/*     */   }
/*     */   
/*     */   private void init() {
/*  90 */     this.ncuComboBox = new SimpleComboBox(new LabelProvider<RoomNcuDeviceNameDTO>()
/*     */         {
/*     */           
/*     */           public String getLabel(RoomNcuDeviceNameDTO item)
/*     */           {
/*  95 */             return item.getDisplayName();
/*     */           }
/*     */         });
/*     */     
/*  99 */     recordStore = new ListStore(new ModelKeyProvider<LifeFaceLockCardDTO>()
/*     */         {
/*     */           
/*     */           public String getKey(LifeFaceLockCardDTO item)
/*     */           {
/* 104 */             return item.getId().toString();
/*     */           }
/*     */         });
/*     */     
/* 108 */     recordStore.addSortInfo(new Store.StoreSortInfo(this.propertyAccess.lifeFaceTime(), SortDir.DESC));
/*     */   }
/*     */   
/*     */   private void initColumnModel() {
/* 112 */     List<ColumnConfig<LifeFaceLockCardDTO, ?>> columnConfigs = new ArrayList<>();
/*     */ 
/*     */ 
/*     */     
/* 116 */     ColumnConfig<LifeFaceLockCardDTO, String> timeConfig = new ColumnConfig(this.propertyAccess.lifeFaceTime(), 100, "辨識時間");
/* 117 */     columnConfigs.add(timeConfig);
/*     */ 
/*     */ 
/*     */     
/* 121 */     ColumnConfig<LifeFaceLockCardDTO, String> locationConfig = new ColumnConfig(this.propertyAccess.lifeFaceLocation(), 100, "辨識地點");
/* 122 */     columnConfigs.add(locationConfig);
/*     */     
/* 124 */     ColumnConfig<LifeFaceLockCardDTO, String> faceConfig = new ColumnConfig(new ValueProvider<LifeFaceLockCardDTO, String>()
/*     */         {
/*     */ 
/*     */           
/*     */           public String getValue(LifeFaceLockCardDTO dto)
/*     */           {
/* 130 */             String result = null;
/* 131 */             if (dto.isFaceMatch()) {
/* 132 */               result = "成功";
/*     */             } else {
/* 134 */               result = "失敗";
/*     */             } 
/* 136 */             return result;
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(LifeFaceLockCardDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 144 */             return "isFaceMatch";
/*     */           }
/*     */         },  60, "辨識結果");
/*     */ 
/*     */     
/* 149 */     columnConfigs.add(faceConfig);
/*     */     
/* 151 */     ColumnConfig<LifeFaceLockCardDTO, String> lockConfig = new ColumnConfig(new ValueProvider<LifeFaceLockCardDTO, String>()
/*     */         {
/*     */ 
/*     */           
/*     */           public String getValue(LifeFaceLockCardDTO dto)
/*     */           {
/* 157 */             String result = null;
/* 158 */             if (dto.isLockCard()) {
/* 159 */               result = "已鎖卡";
/*     */             } else {
/* 161 */               result = "尚未鎖卡";
/*     */             } 
/* 163 */             return result;
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(LifeFaceLockCardDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 171 */             return "isLockCard";
/*     */           }
/*     */         },  60, "鎖卡狀態");
/*     */ 
/*     */     
/* 176 */     columnConfigs.add(lockConfig);
/*     */     
/* 178 */     ColumnConfig<LifeFaceLockCardDTO, String> lockPeopleConfig = new ColumnConfig(new ValueProvider<LifeFaceLockCardDTO, String>()
/*     */         {
/*     */ 
/*     */           
/*     */           public String getValue(LifeFaceLockCardDTO dto)
/*     */           {
/* 184 */             List<String> values = dto.getLockPeople();
/* 185 */             String lockPeople = "";
/* 186 */             for (int i = 0; i < values.size(); i++) {
/* 187 */               if (i == values.size() - 1) {
/* 188 */                 lockPeople = lockPeople + (String)values.get(i);
/*     */               } else {
/* 190 */                 lockPeople = lockPeople + (String)values.get(i) + ", ";
/*     */               } 
/*     */             } 
/* 193 */             return lockPeople;
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(LifeFaceLockCardDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 201 */             return "lockPeople";
/*     */           }
/*     */         }250, "鎖卡人員名單");
/*     */ 
/*     */     
/* 206 */     columnConfigs.add(lockPeopleConfig);
/*     */     
/* 208 */     ColumnConfig<LifeFaceLockCardDTO, String> lockAckConfig = new ColumnConfig(new ValueProvider<LifeFaceLockCardDTO, String>()
/*     */         {
/*     */ 
/*     */           
/*     */           public String getValue(LifeFaceLockCardDTO dto)
/*     */           {
/* 214 */             return null;
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(LifeFaceLockCardDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 222 */             return "lockAck";
/*     */           }
/*     */         },  60, "是否鎖卡");
/*     */ 
/*     */ 
/*     */     
/* 228 */     lockAckConfig.setFixed(true);
/* 229 */     lockAckConfig.setSortable(false);
/* 230 */     lockAckConfig.setColumnTextClassName(CommonStyles.get().inlineBlock());
/* 231 */     lockAckConfig.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
/* 232 */     lockAckConfig.setCell((Cell)createlockAckCell());
/*     */ 
/*     */     
/* 235 */     ColumnConfig<LifeFaceLockCardDTO, String> unLockAckConfig = new ColumnConfig(new ValueProvider<LifeFaceLockCardDTO, String>()
/*     */         {
/*     */ 
/*     */           
/*     */           public String getValue(LifeFaceLockCardDTO dto)
/*     */           {
/* 241 */             return null;
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(LifeFaceLockCardDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 249 */             return "unLockAck";
/*     */           }
/*     */         },  60, "是否解卡");
/*     */ 
/*     */ 
/*     */     
/* 255 */     unLockAckConfig.setFixed(true);
/* 256 */     unLockAckConfig.setSortable(false);
/* 257 */     unLockAckConfig.setColumnTextClassName(CommonStyles.get().inlineBlock());
/* 258 */     unLockAckConfig.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
/* 259 */     unLockAckConfig.setCell((Cell)createUnLockAckCell());
/* 260 */     columnConfigs.add(unLockAckConfig);
/*     */     
/* 262 */     this.recordColumnModel = new ColumnModel(columnConfigs);
/*     */   }
/*     */   
/*     */   private static TextButtonCell createlockAckCell() {
/* 266 */     TextButtonCell cell = new TextButtonCell();
/* 267 */     cell.setText("確認鎖卡");
/* 268 */     cell.addSelectHandler(new SelectEvent.SelectHandler()
/*     */         {
/*     */ 
/*     */           
/*     */           public void onSelect(SelectEvent event)
/*     */           {
/* 274 */             LifeFaceLockCardDTO dto = (LifeFaceLockCardDTO)LiveFaceRecordViewer.recordStore.get(event.getContext().getIndex());
/* 275 */             LiveFaceRecordViewer.clientFactory
/* 276 */               .getEventBus()
/* 277 */               .fireEventFromSource((GwtEvent)new LiveFaceRecordEvent(LiveFaceRecordEvent.Action.LOCK), dto);
/*     */           }
/*     */         });
/* 280 */     return cell;
/*     */   }
/*     */   
/*     */   private static TextButtonCell createUnLockAckCell() {
/* 284 */     TextButtonCell cell = new TextButtonCell();
/* 285 */     cell.setText("確認解卡");
/* 286 */     cell.addSelectHandler(new SelectEvent.SelectHandler()
/*     */         {
/*     */ 
/*     */           
/*     */           public void onSelect(SelectEvent event)
/*     */           {
/* 292 */             LifeFaceLockCardDTO dto = (LifeFaceLockCardDTO)LiveFaceRecordViewer.recordStore.get(event.getContext().getIndex());
/* 293 */             LiveFaceRecordViewer.clientFactory
/* 294 */               .getEventBus()
/* 295 */               .fireEventFromSource((GwtEvent)new LiveFaceRecordEvent(LiveFaceRecordEvent.Action.UNLOCK), dto);
/*     */           }
/*     */         });
/* 298 */     return cell;
/*     */   }
/*     */   
/*     */   @UiHandler({"updateCheckBox"})
/*     */   public void onUpdateCheckBoxValueChanged(ValueChangeEvent<Boolean> event) {
/* 303 */     if (((Boolean)event.getValue()).booleanValue()) {
/* 304 */       startTimer();
/*     */     } else {
/* 306 */       if (this.timer != null) {
/* 307 */         this.timer.cancel();
/* 308 */         this.timer = null;
/*     */       } 
/* 310 */       recordStore.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   @UiHandler({"queryButton"})
/*     */   public void onClickQuery(SelectEvent event) {
/* 316 */     if (checkFieldValue().booleanValue()) {
/* 317 */       mask("資料查詢中...");
/* 318 */       RoomCardReaderLogQueryParamDTO queryDto = new RoomCardReaderLogQueryParamDTO();
/* 319 */       queryDto.setStartTime(this.dateRangePicker.getStartDate());
/* 320 */       queryDto.setEndTime(this.dateRangePicker.getEndDate());
/* 321 */       queryDto.setNcnDeviceName(((RoomNcuDeviceNameDTO)this.ncuComboBox.getCurrentValue()).getDeviceName());
/* 322 */       this.presenter.getLockCards(queryDto);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Boolean checkFieldValue() {
/* 327 */     Date startDate = this.dateRangePicker.getStartDate();
/* 328 */     Date endDate = this.dateRangePicker.getEndDate();
/* 329 */     if (startDate.after(endDate)) {
/* 330 */       Info.display("查詢失敗", "起始時間需小於結束時間");
/* 331 */       return Boolean.valueOf(false);
/*     */     } 
/* 333 */     return Boolean.valueOf(true);
/*     */   }
/*     */   
/*     */   private void startTimer() {
/* 337 */     if (this.timer == null) {
/* 338 */       this.timer = new Timer()
/*     */         {
/*     */           public void run()
/*     */           {
/* 342 */             LiveFaceRecordViewer.this.presenter.getAllLockCards();
/*     */           }
/*     */         };
/* 345 */       this.timer.scheduleRepeating(60000);
/* 346 */       this.timer.run();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void fillNcuStore(List<RoomNcuDeviceNameDTO> ncus) {
/* 351 */     if (ncus != null && ncus.size() > 0) {
/* 352 */       this.ncuComboBox.getStore().clear();
/* 353 */       this.ncuComboBox.getStore().addAll(ncus);
/* 354 */       this.ncuComboBox.setValue(this.ncuComboBox.getStore().get(0));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void fillRecordStore(List<LifeFaceLockCardDTO> result) {
/* 359 */     recordStore.clear();
/* 360 */     recordStore.addAll(result);
/* 361 */     unmask();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onHide() {
/* 366 */     super.onHide();
/* 367 */     if (this.timer != null) {
/* 368 */       this.timer.cancel();
/* 369 */       this.timer = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onShow() {
/* 375 */     super.onShow();
/* 376 */     if (((Boolean)this.updateCheckBox.getValue()).booleanValue()) {
/* 377 */       startTimer();
/*     */     }
/* 379 */     else if (this.timer != null) {
/* 380 */       this.timer.cancel();
/* 381 */       this.timer = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   static interface LiveFaceRecordViewerUiBinder
/*     */     extends UiBinder<Widget, LiveFaceRecordViewer> {}
/*     */   
/*     */   class DefaultLiveFaceRecordEventHandler implements LiveFaceRecordEvent.LiveFaceRecordEventHandler {
/*     */     public void onLock(LiveFaceRecordEvent event) {
/* 390 */       LiveFaceRecordViewer.this.mask();
/* 391 */       LiveFaceRecordViewer.this.presenter.setLockCard((LifeFaceLockCardDTO)event.getSource());
/*     */     }
/*     */ 
/*     */     
/*     */     public void onUnlock(LiveFaceRecordEvent event) {
/* 396 */       LiveFaceRecordViewer.this.mask();
/* 397 */       LiveFaceRecordViewer.this.presenter.setUnLockCard((LifeFaceLockCardDTO)event.getSource());
/*     */     }
/*     */   }
/*     */   
/*     */   static interface LiveFaceRecordAccess extends PropertyAccess<LifeFaceLockCardDTO> {
/*     */     ModelKeyProvider<LifeFaceLockCardDTO> id();
/*     */     
/*     */     ValueProvider<LifeFaceLockCardDTO, String> lifeFaceTime();
/*     */     
/*     */     ValueProvider<LifeFaceLockCardDTO, String> lifeFaceLocation();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\LiveFaceRecordViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */