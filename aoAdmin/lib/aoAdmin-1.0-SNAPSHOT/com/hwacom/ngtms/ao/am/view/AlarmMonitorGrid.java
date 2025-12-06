/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ 
/*     */ import com.google.gwt.cell.client.AbstractCell;
/*     */ import com.google.gwt.cell.client.Cell;
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.core.client.JsonUtils;
/*     */ import com.google.gwt.event.shared.GwtEvent;
/*     */ import com.google.gwt.json.client.JSONObject;
/*     */ import com.google.gwt.json.client.JSONValue;
/*     */ import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.user.client.Timer;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.alarm.shared.AlarmState;
/*     */ import com.hwacom.ngtms.ao.am.event.AlarmMonitorEvent;
/*     */ import com.hwacom.ngtms.ao.am.util.StringConverter;
/*     */ import com.hwacom.ngtms.ao.am.websocket.AlarmWebSocket;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmMessageDTO;
/*     */ import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
/*     */ import com.sencha.gxt.cell.core.client.TextButtonCell;
/*     */ import com.sencha.gxt.core.client.ValueProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.data.shared.SortDir;
/*     */ import com.sencha.gxt.data.shared.Store;
/*     */ import com.sencha.gxt.widget.core.client.Composite;
/*     */ import com.sencha.gxt.widget.core.client.event.RefreshEvent;
/*     */ import com.sencha.gxt.widget.core.client.event.SelectEvent;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*     */ import com.sencha.gxt.widget.core.client.grid.Grid;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ public class AlarmMonitorGrid extends Composite {
/*  39 */   private static AlarmMonitorGridUiBinder uiBinder = (AlarmMonitorGridUiBinder)GWT.create(AlarmMonitorGridUiBinder.class);
/*     */ 
/*     */ 
/*     */   
/*  43 */   private AlarmLogPropertyAccess alarmLogPropertyAccess = (AlarmLogPropertyAccess)GWT.create(AlarmLogPropertyAccess.class);
/*     */   
/*  45 */   private static ClientFactory clientFactory = (ClientFactory)GWT.create(ClientFactory.class);
/*     */   
/*     */   @UiField
/*     */   Grid<AlarmMessageDTO> alarmGrid;
/*     */   
/*     */   @UiField(provided = true)
/*     */   static ListStore<AlarmMessageDTO> listStore;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ColumnModel<AlarmMessageDTO> columnModel;
/*     */   
/*     */   private AlarmWebSocket alarmWebSocket;
/*     */   
/*     */   private int alarmWebSocketReconnectionCount;
/*     */   private Timer alarmWebSocketReconnectionTimer;
/*     */   
/*     */   public AlarmMonitorGrid() {
/*  62 */     listStore = new ListStore(this.alarmLogPropertyAccess.id());
/*  63 */     listStore.addSortInfo(new Store.StoreSortInfo(this.alarmLogPropertyAccess.alarmLevel(), SortDir.DESC));
/*  64 */     listStore.addSortInfo(new Store.StoreSortInfo(AlarmLogPropertyAccess.timestamp, SortDir.DESC));
/*  65 */     initColumnModel();
/*  66 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*  67 */     this.alarmWebSocket = createAlarmWebSocket();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initColumnModel() {
/*  73 */     List<ColumnConfig<AlarmMessageDTO, ?>> columnConfigList = new ArrayList<>();
/*     */     
/*  75 */     ColumnConfig<AlarmMessageDTO, String> timestampConfig = new ColumnConfig(AlarmLogPropertyAccess.timestamp, 130, "時間");
/*     */     
/*  77 */     timestampConfig.setFixed(true);
/*  78 */     columnConfigList.add(timestampConfig);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     columnConfigList.add(new ColumnConfig(this.alarmLogPropertyAccess.displayName(), 1, "設備名稱"));
/*     */     
/*  87 */     columnConfigList.add(new ColumnConfig(this.alarmLogPropertyAccess.message(), 1, "告警訊息"));
/*     */ 
/*     */     
/*  90 */     ColumnConfig<AlarmMessageDTO, AlarmState> stateConfig = new ColumnConfig(this.alarmLogPropertyAccess.state(), 80, "告警狀態");
/*  91 */     stateConfig.setFixed(true);
/*  92 */     stateConfig.setCell((Cell)new AbstractCell<AlarmState>(new String[0])
/*     */         {
/*     */           public void render(Cell.Context context, AlarmState value, SafeHtmlBuilder sb)
/*     */           {
/*  96 */             if (value == AlarmState.UNACK_ALM) {
/*  97 */               sb.appendHtmlConstant("待復原");
/*  98 */             } else if (value == AlarmState.ACK_ALM) {
/*  99 */               sb.appendHtmlConstant("待處理");
/* 100 */             } else if (value == AlarmState.UNACK_RTN) {
/* 101 */               sb.appendHtmlConstant("已自動排除");
/* 102 */             } else if (value == AlarmState.ACK_RTN) {
/* 103 */               sb.appendHtmlConstant("已處理排除");
/*     */             } 
/*     */           }
/*     */         });
/* 107 */     columnConfigList.add(stateConfig);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     this.columnModel = new ColumnModel(columnConfigList);
/*     */   }
/*     */   
/*     */   private static TextButtonCell createAckCell() {
/* 121 */     TextButtonCell cell = new TextButtonCell();
/* 122 */     cell.setText("確認");
/* 123 */     cell.addSelectHandler(new SelectEvent.SelectHandler()
/*     */         {
/*     */           
/*     */           public void onSelect(SelectEvent event)
/*     */           {
/* 128 */             AlarmMessageDTO dto = (AlarmMessageDTO)AlarmMonitorGrid.listStore.get(event.getContext().getIndex());
/* 129 */             AlarmMonitorGrid.clientFactory
/* 130 */               .getEventBus()
/* 131 */               .fireEventFromSource((GwtEvent)new AlarmMonitorEvent(AlarmMonitorEvent.Action.CLICK), dto);
/*     */           }
/*     */         });
/* 134 */     return cell;
/*     */   }
/*     */   
/*     */   private void addEventHandlers() {
/* 138 */     this.alarmGrid.addRefreshHandler(event -> {
/*     */           for (AlarmMessageDTO dto : listStore.getAll()) {
/*     */             changeStoreColor(dto);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private AlarmWebSocket createAlarmWebSocket() {
/* 147 */     GWT.log("createAlarmWebSocket.");
/* 148 */     AlarmWebSocket webSocket = new AlarmWebSocket(new AlarmWebSocket.WebSocketHandler()
/*     */         {
/*     */           
/*     */           public void onOpen()
/*     */           {
/* 153 */             GWT.log("on Open");
/*     */           }
/*     */ 
/*     */           
/*     */           public void onMessage(String message) {
/* 158 */             JSONObject json = new JSONObject(JsonUtils.safeEval(message));
/* 159 */             JSONValue remove = json.get("remove");
/* 160 */             if (remove != null) {
/* 161 */               AlarmMonitorGrid.this.deleteAlarm(remove.isString().stringValue());
/*     */             } else {
/* 163 */               AlarmMessageDTO dto = new AlarmMessageDTO();
/* 164 */               dto.setId(json.get("id").isString().stringValue());
/* 165 */               dto.setTimestamp(new Date((long)json.get("timestamp").isNumber().doubleValue()));
/* 166 */               dto.setDeviceName(json.get("deviceName").isString().stringValue());
/* 167 */               dto.setDisplayName(json.get("displayName").isString().stringValue());
/* 168 */               dto.setMessage(json.get("message").isString().stringValue());
/* 169 */               dto.setAlarmLevel((int)json.get("alarmLevel").isNumber().doubleValue());
/* 170 */               dto.setState(AlarmState.valueOf(json.get("state").isString().stringValue()));
/* 171 */               AlarmMonitorGrid.this.addOrUpdateAlarm(dto);
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public void onClose(int code, String reason) {
/* 177 */             AlarmMonitorGrid.this.alarmWebSocketReconnectionCount++;
/* 178 */             if (AlarmMonitorGrid.this.alarmWebSocketReconnectionCount > 3) {
/* 179 */               AlarmMonitorGrid.this.reconnectAlarmWebSocketInDelayMillis(60000);
/*     */             } else {
/* 181 */               GWT.log("on Close!");
/* 182 */               AlarmMonitorGrid.this.alarmWebSocket.open();
/*     */             } 
/*     */           }
/*     */         });
/* 186 */     webSocket.open();
/* 187 */     return webSocket;
/*     */   }
/*     */   
/*     */   private void reconnectAlarmWebSocketInDelayMillis(int delayMillis) {
/* 191 */     if (this.alarmWebSocketReconnectionTimer == null) {
/* 192 */       this.alarmWebSocketReconnectionTimer = new Timer()
/*     */         {
/*     */           public void run()
/*     */           {
/* 196 */             AlarmMonitorGrid.this.alarmWebSocket.open();
/*     */           }
/*     */         };
/*     */     }
/* 200 */     this.alarmWebSocketReconnectionTimer.schedule(delayMillis);
/*     */   }
/*     */   
/*     */   private void addOrUpdateAlarm(AlarmMessageDTO dto) {
/* 204 */     if (listStore.findModel(dto) == null) {
/* 205 */       listStore.add(dto);
/* 206 */       int size = listStore.getAll().size();
/* 207 */       if (size > 50) {
/* 208 */         listStore.remove(size - 1);
/*     */       }
/* 210 */       changeStoreColor(dto);
/*     */     } else {
/* 212 */       listStore.update(dto);
/* 213 */       listStore.applySort(false);
/* 214 */       changeStoreColor(dto);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void changeStoreColor(AlarmMessageDTO dto) {
/* 219 */     int row = listStore.indexOf(dto);
/* 220 */     for (int i = 0; i < this.columnModel.getColumnCount(); i++) {
/* 221 */       if (dto.getAlarmType().equals("偵測非法入侵")) {
/* 222 */         this.alarmGrid.getView().getCell(row, i).getStyle().setBackgroundColor("#FF6633");
/* 223 */       } else if (dto.getAlarmType().equals("機房開啟失敗") || dto.getAlarmType().equals("人臉辨識失敗")) {
/* 224 */         this.alarmGrid.getView().getCell(row, i).getStyle().setBackgroundColor("#FF8080");
/*     */       } else {
/* 226 */         this.alarmGrid.getView().getCell(row, i).getStyle().setBackgroundColor("#FFFF00");
/*     */       } 
/* 228 */       if (dto.getState().equals(AlarmState.UNACK_RTN)) {
/* 229 */         this.alarmGrid.getView().getCell(row, i).getStyle().setBackgroundColor("#99E6E6");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setListStore(List<AlarmMessageDTO> result) {
/* 235 */     listStore.clear();
/* 236 */     listStore.addAll(result);
/* 237 */     if (listStore.size() > 0) {
/* 238 */       for (AlarmMessageDTO dto : result) {
/* 239 */         changeStoreColor(dto);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void deleteAlarm(String id) {
/* 245 */     listStore.remove(listStore.findModelWithKey(id));
/*     */   }
/*     */   
/*     */   static interface AlarmMonitorGridUiBinder
/*     */     extends UiBinder<Widget, AlarmMonitorGrid> {}
/*     */   
/*     */   static interface AlarmLogPropertyAccess
/*     */     extends PropertyAccess<AlarmMessageDTO>
/*     */   {
/* 254 */     public static final ValueProvider<AlarmMessageDTO, String> timestamp = new ValueProvider<AlarmMessageDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(AlarmMessageDTO object)
/*     */         {
/* 259 */           return StringConverter.getFormattedTime(object.getTimestamp());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(AlarmMessageDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 267 */           return "timestamp";
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 277 */     public static final ValueProvider<AlarmMessageDTO, String> ack = new ValueProvider<AlarmMessageDTO, String>()
/*     */       {
/*     */         public String getValue(AlarmMessageDTO object)
/*     */         {
/* 281 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(AlarmMessageDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 289 */           return "ack";
/*     */         }
/*     */       };
/*     */     
/*     */     ModelKeyProvider<AlarmMessageDTO> id();
/*     */     
/*     */     ValueProvider<AlarmMessageDTO, Integer> alarmLevel();
/*     */     
/*     */     ValueProvider<AlarmMessageDTO, String> displayName();
/*     */     
/*     */     ValueProvider<AlarmMessageDTO, String> message();
/*     */     
/*     */     ValueProvider<AlarmMessageDTO, AlarmState> state();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\AlarmMonitorGrid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */