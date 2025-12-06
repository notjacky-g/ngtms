/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.i18n.client.DateTimeFormat;
/*     */ import com.google.gwt.i18n.client.NumberFormat;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.user.client.Timer;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.presenter.PowerWaterStatusPresenter;
/*     */ import com.hwacom.ngtms.ao.shared.dto.PowerStatusDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.WaterStatusDTO;
/*     */ import com.hwacom.ngtms.cam.client.ui.AmTab;
/*     */ import com.sencha.gxt.core.client.ValueProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*     */ import com.sencha.gxt.widget.core.client.grid.Grid;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class PowerWaterStatusViewer
/*     */   extends AmTab
/*     */ {
/*  27 */   private static PowerWaterStatusViewerUiBinder uiBinder = (PowerWaterStatusViewerUiBinder)GWT.create(PowerWaterStatusViewerUiBinder.class);
/*     */ 
/*     */ 
/*     */   
/*  31 */   private final WaterPropertyAccess waterproperty = (WaterPropertyAccess)GWT.create(WaterPropertyAccess.class);
/*     */   
/*  33 */   private final PowerPropertyAccess powerproperty = (PowerPropertyAccess)GWT.create(PowerPropertyAccess.class);
/*     */   
/*  35 */   private PowerWaterStatusPresenter presenter = new PowerWaterStatusPresenter(this);
/*     */   
/*  37 */   static NumberFormat fmt = NumberFormat.getDecimalFormat();
/*     */   
/*     */   @UiField
/*     */   Grid<WaterStatusDTO> waterGrid;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<WaterStatusDTO> waterListStore;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ColumnModel<WaterStatusDTO> waterColumnModel;
/*     */   
/*     */   @UiField
/*     */   Grid<PowerStatusDTO> powerGrid;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<PowerStatusDTO> powerListStore;
/*     */   @UiField(provided = true)
/*     */   ColumnModel<PowerStatusDTO> powerColumnModel;
/*     */   private Timer timer;
/*     */   
/*     */   public PowerWaterStatusViewer() {
/*  58 */     this.waterListStore = new ListStore(this.waterproperty.id());
/*  59 */     initWaterColumnModel();
/*  60 */     this.powerListStore = new ListStore(this.powerproperty.id());
/*  61 */     initPowerColumnModel();
/*  62 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*  63 */     this.presenter.getPowerWaterStatusList();
/*  64 */     startTimer();
/*     */   }
/*     */   
/*     */   private void initWaterColumnModel() {
/*  68 */     List<ColumnConfig<WaterStatusDTO, ?>> columnConfigs = new ArrayList<>();
/*     */ 
/*     */ 
/*     */     
/*  72 */     ColumnConfig<WaterStatusDTO, String> displayNameConfig = new ColumnConfig(this.waterproperty.displayName(), 80, "機房名稱");
/*  73 */     displayNameConfig.setFixed(true);
/*  74 */     columnConfigs.add(displayNameConfig);
/*     */ 
/*     */     
/*  77 */     ColumnConfig<WaterStatusDTO, String> waterAlarmConfig = new ColumnConfig(this.waterproperty.waterAlarm(), 120, "用水警報");
/*  78 */     waterAlarmConfig.setFixed(true);
/*  79 */     columnConfigs.add(waterAlarmConfig);
/*     */     
/*  81 */     ColumnConfig<WaterStatusDTO, String> cumulateValueConfig = new ColumnConfig(WaterPropertyAccess.cumulateValue, 150, "累積值(度)");
/*     */     
/*  83 */     cumulateValueConfig.setFixed(true);
/*  84 */     columnConfigs.add(cumulateValueConfig);
/*     */     
/*  86 */     ColumnConfig<WaterStatusDTO, String> instantaneousValueConfig = new ColumnConfig(WaterPropertyAccess.instantaneousValue, 150, "瞬間值(m3/h)");
/*     */     
/*  88 */     instantaneousValueConfig.setFixed(true);
/*  89 */     columnConfigs.add(instantaneousValueConfig);
/*     */     
/*  91 */     ColumnConfig<WaterStatusDTO, String> waterLastHourConfig = new ColumnConfig(WaterPropertyAccess.waterLastHour, 120, "上小時用水量(度)");
/*     */     
/*  93 */     waterLastHourConfig.setFixed(true);
/*  94 */     columnConfigs.add(waterLastHourConfig);
/*     */     
/*  96 */     ColumnConfig<WaterStatusDTO, String> water24HourConfig = new ColumnConfig(WaterPropertyAccess.water24Hour, 180, "前24小時累用水量(度)");
/*     */     
/*  98 */     water24HourConfig.setFixed(true);
/*  99 */     columnConfigs.add(water24HourConfig);
/*     */     
/* 101 */     ColumnConfig<WaterStatusDTO, String> dataTimeConfig = new ColumnConfig(WaterPropertyAccess.dataTime, 200, "更新時間(小時)");
/*     */     
/* 103 */     dataTimeConfig.setFixed(true);
/* 104 */     columnConfigs.add(dataTimeConfig);
/*     */     
/* 106 */     this.waterColumnModel = new ColumnModel(columnConfigs);
/*     */   }
/*     */   
/*     */   private void initPowerColumnModel() {
/* 110 */     List<ColumnConfig<PowerStatusDTO, ?>> columnConfigs = new ArrayList<>();
/*     */ 
/*     */ 
/*     */     
/* 114 */     ColumnConfig<PowerStatusDTO, String> displayNameConfig = new ColumnConfig(this.powerproperty.displayName(), 80, "機房名稱");
/* 115 */     displayNameConfig.setFixed(true);
/* 116 */     columnConfigs.add(displayNameConfig);
/*     */ 
/*     */     
/* 119 */     ColumnConfig<PowerStatusDTO, String> powerAlarmConfig = new ColumnConfig(this.powerproperty.powerAlarm(), 120, "用電警報");
/* 120 */     powerAlarmConfig.setFixed(true);
/* 121 */     columnConfigs.add(powerAlarmConfig);
/*     */     
/* 123 */     ColumnConfig<PowerStatusDTO, String> kwhConfig = new ColumnConfig(PowerPropertyAccess.kwh, 120, "累積值(度)");
/*     */     
/* 125 */     kwhConfig.setFixed(true);
/* 126 */     columnConfigs.add(kwhConfig);
/*     */     
/* 128 */     ColumnConfig<PowerStatusDTO, String> powerLastHourConfig = new ColumnConfig(PowerPropertyAccess.powerLastHour, 120, "上小時用電量(度)");
/*     */     
/* 130 */     powerLastHourConfig.setFixed(true);
/* 131 */     columnConfigs.add(powerLastHourConfig);
/*     */     
/* 133 */     ColumnConfig<PowerStatusDTO, String> power24HourConfig = new ColumnConfig(PowerPropertyAccess.power24Hour, 180, "前24小時累積用電量(度)");
/*     */     
/* 135 */     power24HourConfig.setFixed(true);
/* 136 */     columnConfigs.add(power24HourConfig);
/*     */     
/* 138 */     ColumnConfig<PowerStatusDTO, String> rvConfig = new ColumnConfig(PowerPropertyAccess.rv, 80, "R相電壓(V)");
/*     */     
/* 140 */     rvConfig.setFixed(true);
/* 141 */     columnConfigs.add(rvConfig);
/*     */     
/* 143 */     ColumnConfig<PowerStatusDTO, String> svConfig = new ColumnConfig(PowerPropertyAccess.sv, 80, "S相電壓(V)");
/*     */     
/* 145 */     svConfig.setFixed(true);
/* 146 */     columnConfigs.add(svConfig);
/*     */     
/* 148 */     ColumnConfig<PowerStatusDTO, String> tvConfig = new ColumnConfig(PowerPropertyAccess.tv, 80, "T相電壓(V)");
/*     */     
/* 150 */     tvConfig.setFixed(true);
/* 151 */     columnConfigs.add(tvConfig);
/*     */     
/* 153 */     ColumnConfig<PowerStatusDTO, String> avConfig = new ColumnConfig(PowerPropertyAccess.av, 80, "平均相電壓(V)");
/*     */     
/* 155 */     avConfig.setFixed(true);
/* 156 */     columnConfigs.add(avConfig);
/*     */     
/* 158 */     ColumnConfig<PowerStatusDTO, String> riConfig = new ColumnConfig(PowerPropertyAccess.ri, 80, "R相電流(I)");
/*     */     
/* 160 */     riConfig.setFixed(true);
/* 161 */     columnConfigs.add(riConfig);
/*     */     
/* 163 */     ColumnConfig<PowerStatusDTO, String> siConfig = new ColumnConfig(PowerPropertyAccess.si, 80, "S相電流(I)");
/*     */     
/* 165 */     siConfig.setFixed(true);
/* 166 */     columnConfigs.add(siConfig);
/*     */     
/* 168 */     ColumnConfig<PowerStatusDTO, String> tiConfig = new ColumnConfig(PowerPropertyAccess.ti, 80, "T相電流(I)");
/*     */     
/* 170 */     tiConfig.setFixed(true);
/* 171 */     columnConfigs.add(tiConfig);
/*     */     
/* 173 */     ColumnConfig<PowerStatusDTO, String> aiConfig = new ColumnConfig(PowerPropertyAccess.ai, 80, "平均相電流(I)");
/*     */     
/* 175 */     aiConfig.setFixed(true);
/* 176 */     columnConfigs.add(aiConfig);
/*     */     
/* 178 */     ColumnConfig<PowerStatusDTO, String> kwConfig = new ColumnConfig(PowerPropertyAccess.kw, 80, "總功率");
/*     */     
/* 180 */     kwConfig.setFixed(true);
/* 181 */     columnConfigs.add(kwConfig);
/*     */     
/* 183 */     ColumnConfig<PowerStatusDTO, String> pfConfig = new ColumnConfig(PowerPropertyAccess.pf, 80, "總功率因數");
/*     */     
/* 185 */     pfConfig.setFixed(true);
/* 186 */     columnConfigs.add(pfConfig);
/*     */     
/* 188 */     ColumnConfig<PowerStatusDTO, String> dataTimeConfig = new ColumnConfig(PowerPropertyAccess.dataTime, 200, "更新時間(小時)");
/*     */     
/* 190 */     dataTimeConfig.setFixed(true);
/* 191 */     columnConfigs.add(dataTimeConfig);
/*     */     
/* 193 */     this.powerColumnModel = new ColumnModel(columnConfigs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static interface WaterPropertyAccess
/*     */     extends PropertyAccess<WaterStatusDTO>
/*     */   {
/* 204 */     public static final ValueProvider<WaterStatusDTO, String> cumulateValue = new ValueProvider<WaterStatusDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(WaterStatusDTO object)
/*     */         {
/* 209 */           return PowerWaterStatusViewer.fmt.format(object.getCumulateValue());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(WaterStatusDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 217 */           return "cumulateValue";
/*     */         }
/*     */       };
/*     */     
/* 221 */     public static final ValueProvider<WaterStatusDTO, String> instantaneousValue = new ValueProvider<WaterStatusDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(WaterStatusDTO object)
/*     */         {
/* 226 */           return PowerWaterStatusViewer.fmt.format(object.getInstantaneousValue());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(WaterStatusDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 234 */           return "instantaneousValue";
/*     */         }
/*     */       };
/*     */     
/* 238 */     public static final ValueProvider<WaterStatusDTO, String> waterLastHour = new ValueProvider<WaterStatusDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(WaterStatusDTO object)
/*     */         {
/* 243 */           return PowerWaterStatusViewer.fmt.format(object.getWaterLastHour());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(WaterStatusDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 251 */           return "waterLastHour";
/*     */         }
/*     */       };
/*     */     
/* 255 */     public static final ValueProvider<WaterStatusDTO, String> water24Hour = new ValueProvider<WaterStatusDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(WaterStatusDTO object)
/*     */         {
/* 260 */           return PowerWaterStatusViewer.fmt.format(object.getWater24Hour());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(WaterStatusDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 268 */           return "water24Hour";
/*     */         }
/*     */       };
/*     */     
/* 272 */     public static final ValueProvider<WaterStatusDTO, String> dataTime = new ValueProvider<WaterStatusDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(WaterStatusDTO object)
/*     */         {
/* 277 */           return DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").format(object.getDataTime());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(WaterStatusDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 285 */           return "dataTime";
/*     */         }
/*     */       };
/*     */     
/*     */     ModelKeyProvider<WaterStatusDTO> id();
/*     */     
/*     */     ValueProvider<WaterStatusDTO, String> displayName();
/*     */     
/*     */     ValueProvider<WaterStatusDTO, String> waterAlarm();
/*     */   }
/*     */   
/*     */   static interface PowerPropertyAccess
/*     */     extends PropertyAccess<PowerStatusDTO> {
/* 298 */     public static final ValueProvider<PowerStatusDTO, String> kwh = new ValueProvider<PowerStatusDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(PowerStatusDTO object)
/*     */         {
/* 303 */           return PowerWaterStatusViewer.fmt.format(object.getKwh());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(PowerStatusDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 311 */           return "kwh";
/*     */         }
/*     */       };
/*     */     
/* 315 */     public static final ValueProvider<PowerStatusDTO, String> powerLastHour = new ValueProvider<PowerStatusDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(PowerStatusDTO object)
/*     */         {
/* 320 */           return PowerWaterStatusViewer.fmt.format(object.getPowerLastHour());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(PowerStatusDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 328 */           return "powerLastHour";
/*     */         }
/*     */       };
/*     */     
/* 332 */     public static final ValueProvider<PowerStatusDTO, String> power24Hour = new ValueProvider<PowerStatusDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(PowerStatusDTO object)
/*     */         {
/* 337 */           return PowerWaterStatusViewer.fmt.format(object.getPower24Hour());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(PowerStatusDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 345 */           return "power24Hour";
/*     */         }
/*     */       };
/*     */     
/* 349 */     public static final ValueProvider<PowerStatusDTO, String> rv = new ValueProvider<PowerStatusDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(PowerStatusDTO object)
/*     */         {
/* 354 */           return PowerWaterStatusViewer.fmt.format(object.getRv());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(PowerStatusDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 362 */           return "rv";
/*     */         }
/*     */       };
/*     */     
/* 366 */     public static final ValueProvider<PowerStatusDTO, String> sv = new ValueProvider<PowerStatusDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(PowerStatusDTO object)
/*     */         {
/* 371 */           return PowerWaterStatusViewer.fmt.format(object.getSv());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(PowerStatusDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 379 */           return "sv";
/*     */         }
/*     */       };
/*     */     
/* 383 */     public static final ValueProvider<PowerStatusDTO, String> tv = new ValueProvider<PowerStatusDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(PowerStatusDTO object)
/*     */         {
/* 388 */           return PowerWaterStatusViewer.fmt.format(object.getTv());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(PowerStatusDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 396 */           return "tv";
/*     */         }
/*     */       };
/*     */     
/* 400 */     public static final ValueProvider<PowerStatusDTO, String> av = new ValueProvider<PowerStatusDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(PowerStatusDTO object)
/*     */         {
/* 405 */           return PowerWaterStatusViewer.fmt.format(object.getAv());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(PowerStatusDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 413 */           return "av";
/*     */         }
/*     */       };
/*     */     
/* 417 */     public static final ValueProvider<PowerStatusDTO, String> ri = new ValueProvider<PowerStatusDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(PowerStatusDTO object)
/*     */         {
/* 422 */           return PowerWaterStatusViewer.fmt.format(object.getRi());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(PowerStatusDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 430 */           return "ri";
/*     */         }
/*     */       };
/*     */     
/* 434 */     public static final ValueProvider<PowerStatusDTO, String> si = new ValueProvider<PowerStatusDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(PowerStatusDTO object)
/*     */         {
/* 439 */           return PowerWaterStatusViewer.fmt.format(object.getSi());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(PowerStatusDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 447 */           return "si";
/*     */         }
/*     */       };
/*     */     
/* 451 */     public static final ValueProvider<PowerStatusDTO, String> ti = new ValueProvider<PowerStatusDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(PowerStatusDTO object)
/*     */         {
/* 456 */           return PowerWaterStatusViewer.fmt.format(object.getTi());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(PowerStatusDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 464 */           return "ti";
/*     */         }
/*     */       };
/*     */     
/* 468 */     public static final ValueProvider<PowerStatusDTO, String> ai = new ValueProvider<PowerStatusDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(PowerStatusDTO object)
/*     */         {
/* 473 */           return PowerWaterStatusViewer.fmt.format(object.getAi());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(PowerStatusDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 481 */           return "ai";
/*     */         }
/*     */       };
/*     */     
/* 485 */     public static final ValueProvider<PowerStatusDTO, String> kw = new ValueProvider<PowerStatusDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(PowerStatusDTO object)
/*     */         {
/* 490 */           return PowerWaterStatusViewer.fmt.format(object.getKw());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(PowerStatusDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 498 */           return "kkwwh";
/*     */         }
/*     */       };
/*     */     
/* 502 */     public static final ValueProvider<PowerStatusDTO, String> pf = new ValueProvider<PowerStatusDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(PowerStatusDTO object)
/*     */         {
/* 507 */           return PowerWaterStatusViewer.fmt.format(object.getPf());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(PowerStatusDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 515 */           return "pf";
/*     */         }
/*     */       };
/*     */     
/* 519 */     public static final ValueProvider<PowerStatusDTO, String> dataTime = new ValueProvider<PowerStatusDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(PowerStatusDTO object)
/*     */         {
/* 524 */           return DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").format(object.getDataTime());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(PowerStatusDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 532 */           return "dataTime";
/*     */         }
/*     */       }; ModelKeyProvider<PowerStatusDTO> id();
/*     */     ValueProvider<PowerStatusDTO, String> displayName();
/*     */     ValueProvider<PowerStatusDTO, String> powerAlarm(); }
/*     */   public void fillWater(List<WaterStatusDTO> waterStatusList) {
/* 538 */     this.waterListStore.clear();
/* 539 */     this.waterListStore.addAll(waterStatusList);
/*     */   }
/*     */   
/*     */   public void fillPower(List<PowerStatusDTO> powerStatusList) {
/* 543 */     this.powerListStore.clear();
/* 544 */     this.powerListStore.addAll(powerStatusList);
/*     */   }
/*     */   
/*     */   private void startTimer() {
/* 548 */     this.timer = new Timer()
/*     */       {
/*     */         public void run()
/*     */         {
/* 552 */           PowerWaterStatusViewer.this.presenter.getPowerWaterStatusList();
/*     */         }
/*     */       };
/* 555 */     this.timer.scheduleRepeating(60000);
/* 556 */     this.timer.run();
/*     */   }
/*     */   
/*     */   static interface PowerWaterStatusViewerUiBinder extends UiBinder<Widget, PowerWaterStatusViewer> {}
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\PowerWaterStatusViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */