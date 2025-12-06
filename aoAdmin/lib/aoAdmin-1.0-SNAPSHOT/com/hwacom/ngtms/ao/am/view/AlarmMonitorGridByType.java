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
/*     */ import com.hwacom.ngtms.ao.am.event.AlarmMonitorGridByTypeEvent;
/*     */ import com.hwacom.ngtms.ao.am.presenter.AlarmMonitorGridByTypePresenter;
/*     */ import com.hwacom.ngtms.ao.am.util.StringConverter;
/*     */ import com.hwacom.ngtms.ao.am.websocket.AlarmWebSocket;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmMessageDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmSendMessageDTO;
/*     */ import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
/*     */ import com.sencha.gxt.core.client.ValueProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.data.shared.SortDir;
/*     */ import com.sencha.gxt.data.shared.Store;
/*     */ import com.sencha.gxt.widget.core.client.Composite;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*     */ import com.sencha.gxt.widget.core.client.grid.Grid;
/*     */ import com.sencha.gxt.widget.core.client.info.Info;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ public class AlarmMonitorGridByType extends Composite {
/*  39 */   private static AlarmMonitorGridByTypeUiBinder uiBinder = (AlarmMonitorGridByTypeUiBinder)GWT.create(AlarmMonitorGridByTypeUiBinder.class);
/*     */ 
/*     */ 
/*     */   
/*  43 */   private AlarmMonitorGridByTypePresenter presenter = new AlarmMonitorGridByTypePresenter(this);
/*     */   
/*  45 */   private AlarmLogPropertyAccess alarmLogPropertyAccess = (AlarmLogPropertyAccess)GWT.create(AlarmLogPropertyAccess.class);
/*     */   
/*  47 */   private final ClientFactory clientFactory = (ClientFactory)GWT.create(ClientFactory.class);
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
/*     */   public AlarmMonitorGridByType() {
/*  64 */     listStore = new ListStore(this.alarmLogPropertyAccess.id());
/*  65 */     listStore.addSortInfo(new Store.StoreSortInfo(this.alarmLogPropertyAccess.alarmLevel(), SortDir.DESC));
/*  66 */     listStore.addSortInfo(new Store.StoreSortInfo(AlarmLogPropertyAccess.timestamp, SortDir.DESC));
/*  67 */     initColumnModel();
/*  68 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*  69 */     this.alarmWebSocket = createAlarmWebSocket();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initColumnModel() {
/*  74 */     List<ColumnConfig<AlarmMessageDTO, ?>> columnConfigList = new ArrayList<>();
/*     */     
/*  76 */     ColumnConfig<AlarmMessageDTO, String> timestampConfig = new ColumnConfig(AlarmLogPropertyAccess.timestamp, 130, "時間");
/*     */     
/*  78 */     timestampConfig.setFixed(true);
/*  79 */     columnConfigList.add(timestampConfig);
/*     */     
/*  81 */     columnConfigList.add(new ColumnConfig(this.alarmLogPropertyAccess.displayName(), 1, "設備名稱"));
/*     */     
/*  83 */     columnConfigList.add(new ColumnConfig(this.alarmLogPropertyAccess.message(), 1, "告警訊息"));
/*     */ 
/*     */     
/*  86 */     ColumnConfig<AlarmMessageDTO, AlarmState> stateConfig = new ColumnConfig(this.alarmLogPropertyAccess.state(), 80, "告警狀態");
/*  87 */     stateConfig.setFixed(true);
/*  88 */     stateConfig.setCell((Cell)new AbstractCell<AlarmState>(new String[0])
/*     */         {
/*     */           public void render(Cell.Context context, AlarmState value, SafeHtmlBuilder sb)
/*     */           {
/*  92 */             if (value == AlarmState.UNACK_ALM) {
/*  93 */               sb.appendHtmlConstant("待復原");
/*  94 */             } else if (value == AlarmState.ACK_ALM) {
/*  95 */               sb.appendHtmlConstant("待處理");
/*  96 */             } else if (value == AlarmState.UNACK_RTN) {
/*  97 */               sb.appendHtmlConstant("已自動排除");
/*  98 */             } else if (value == AlarmState.ACK_RTN) {
/*  99 */               sb.appendHtmlConstant("已處理排除");
/*     */             } 
/*     */           }
/*     */         });
/* 103 */     columnConfigList.add(stateConfig);
/* 104 */     this.columnModel = new ColumnModel(columnConfigList);
/*     */   }
/*     */   
/*     */   private AlarmWebSocket createAlarmWebSocket() {
/* 108 */     GWT.log("createAlarmWebSocket.");
/* 109 */     AlarmWebSocket webSocket = new AlarmWebSocket(new AlarmWebSocket.WebSocketHandler()
/*     */         {
/*     */           
/*     */           public void onOpen()
/*     */           {
/* 114 */             GWT.log("on Open");
/*     */           }
/*     */ 
/*     */           
/*     */           public void onMessage(String message) {
/* 119 */             JSONObject json = new JSONObject(JsonUtils.safeEval(message));
/* 120 */             JSONValue remove = json.get("remove");
/* 121 */             if (remove != null) {
/* 122 */               AlarmMonitorGridByType.this.deleteAlarm(remove.isString().stringValue());
/*     */             } else {
/* 124 */               AlarmMessageDTO dto = new AlarmMessageDTO();
/* 125 */               dto.setId(json.get("id").isString().stringValue());
/* 126 */               dto.setAlarmType(json.get("alarmType").isString().stringValue());
/* 127 */               dto.setTimestamp(new Date((long)json.get("timestamp").isNumber().doubleValue()));
/* 128 */               dto.setDeviceName(json.get("deviceName").isString().stringValue());
/* 129 */               dto.setDisplayName(json.get("displayName").isString().stringValue());
/* 130 */               dto.setMessage(json.get("message").isString().stringValue());
/* 131 */               dto.setAlarmLevel((int)json.get("alarmLevel").isNumber().doubleValue());
/* 132 */               dto.setState(AlarmState.valueOf(json.get("state").isString().stringValue()));
/* 133 */               AlarmMonitorGridByType.this.addOrUpdateAlarm(dto);
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public void onClose(int code, String reason) {
/* 139 */             AlarmMonitorGridByType.this.alarmWebSocketReconnectionCount++;
/* 140 */             if (AlarmMonitorGridByType.this.alarmWebSocketReconnectionCount > 3) {
/* 141 */               AlarmMonitorGridByType.this.reconnectAlarmWebSocketInDelayMillis(60000);
/*     */             } else {
/* 143 */               GWT.log("on Close!");
/* 144 */               AlarmMonitorGridByType.this.alarmWebSocket.open();
/*     */             } 
/*     */           }
/*     */         });
/* 148 */     webSocket.open();
/* 149 */     return webSocket;
/*     */   }
/*     */   
/*     */   private void reconnectAlarmWebSocketInDelayMillis(int delayMillis) {
/* 153 */     if (this.alarmWebSocketReconnectionTimer == null) {
/* 154 */       this.alarmWebSocketReconnectionTimer = new Timer()
/*     */         {
/*     */           public void run()
/*     */           {
/* 158 */             AlarmMonitorGridByType.this.alarmWebSocket.open();
/*     */           }
/*     */         };
/*     */     }
/* 162 */     this.alarmWebSocketReconnectionTimer.schedule(delayMillis);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addOrUpdateAlarm(AlarmMessageDTO dto) {
/* 167 */     if (dto.getDisplayName().contains("火警") && (
/* 168 */       dto.getState().equals(AlarmState.UNACK_ALM) || dto
/* 169 */       .getState().equals(AlarmState.ACK_ALM))) {
/* 170 */       AlarmSendMessageDTO messageDTO = new AlarmSendMessageDTO();
/* 171 */       messageDTO.setMessages(
/* 172 */           StringConverter.getFormattedTime(dto.getTimestamp()) + " " + dto
/*     */           
/* 174 */           .getDisplayName() + "-告警");
/*     */       
/* 176 */       this.presenter.alarmSendMessage(messageDTO);
/* 177 */       if (listStore.findModel(dto) == null) {
/* 178 */         listStore.add(dto);
/* 179 */         int size = listStore.getAll().size();
/* 180 */         if (size > 50) {
/* 181 */           listStore.remove(size - 1);
/*     */         }
/* 183 */         changeStoreColor(dto);
/*     */       } else {
/* 185 */         listStore.update(dto);
/* 186 */         listStore.applySort(false);
/* 187 */         changeStoreColor(dto);
/*     */       } 
/* 189 */       this.clientFactory
/* 190 */         .getEventBus()
/* 191 */         .fireEvent((GwtEvent)new AlarmMonitorGridByTypeEvent(AlarmMonitorGridByTypeEvent.Action.ALERT));
/*     */     } 
/*     */ 
/*     */     
/* 195 */     if (dto.getDisplayName().contains("發電機設備") && (
/* 196 */       dto.getState().equals(AlarmState.UNACK_ALM) || dto
/* 197 */       .getState().equals(AlarmState.ACK_ALM))) {
/* 198 */       AlarmSendMessageDTO messageDTO = new AlarmSendMessageDTO();
/* 199 */       messageDTO.setMessages(
/* 200 */           StringConverter.getFormattedTime(dto.getTimestamp()) + " " + dto
/*     */           
/* 202 */           .getDisplayName() + "-啟動");
/*     */       
/* 204 */       this.presenter.alarmSendMessage(messageDTO);
/* 205 */       if (listStore.findModel(dto) == null) {
/* 206 */         listStore.add(dto);
/* 207 */         int size = listStore.getAll().size();
/* 208 */         if (size > 50) {
/* 209 */           listStore.remove(size - 1);
/*     */         }
/* 211 */         changeStoreColor(dto);
/*     */       } else {
/* 213 */         listStore.update(dto);
/* 214 */         listStore.applySort(false);
/* 215 */         changeStoreColor(dto);
/*     */       } 
/* 217 */       this.clientFactory
/* 218 */         .getEventBus()
/* 219 */         .fireEvent((GwtEvent)new AlarmMonitorGridByTypeEvent(AlarmMonitorGridByTypeEvent.Action.ALERT));
/*     */     } 
/*     */ 
/*     */     
/* 223 */     if (dto.getDisplayName().contains("三相主電源開關") && (
/* 224 */       dto.getState().equals(AlarmState.UNACK_ALM) || dto
/* 225 */       .getState().equals(AlarmState.ACK_ALM))) {
/*     */       
/* 227 */       AlarmSendMessageDTO messageDTO = new AlarmSendMessageDTO();
/* 228 */       messageDTO.setMessages(
/* 229 */           StringConverter.getFormattedTime(dto.getTimestamp()) + " " + dto
/*     */           
/* 231 */           .getDisplayName() + "-市電斷線");
/*     */       
/* 233 */       this.presenter.alarmSendMessage(messageDTO);
/* 234 */       if (listStore.findModel(dto) == null) {
/* 235 */         listStore.add(dto);
/* 236 */         int size = listStore.getAll().size();
/* 237 */         if (size > 50) {
/* 238 */           listStore.remove(size - 1);
/*     */         }
/* 240 */         changeStoreColor(dto);
/*     */       } else {
/* 242 */         listStore.update(dto);
/* 243 */         listStore.applySort(false);
/* 244 */         changeStoreColor(dto);
/*     */       } 
/* 246 */       this.clientFactory
/* 247 */         .getEventBus()
/* 248 */         .fireEvent((GwtEvent)new AlarmMonitorGridByTypeEvent(AlarmMonitorGridByTypeEvent.Action.ALERT));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void changeStoreColor(AlarmMessageDTO dto) {
/* 254 */     int row = listStore.indexOf(dto);
/* 255 */     for (int i = 0; i < this.columnModel.getColumnCount(); i++) {
/* 256 */       this.alarmGrid.getView().getCell(row, i).getStyle().setBackgroundColor("#EA0000");
/*     */     }
/*     */   }
/*     */   
/*     */   public void setListStore(List<AlarmMessageDTO> result) {
/* 261 */     listStore.clear();
/* 262 */     listStore.addAll(result);
/* 263 */     if (listStore.size() > 0) {
/* 264 */       for (AlarmMessageDTO dto : result) {
/* 265 */         changeStoreColor(dto);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void deleteAlarm(String id) {
/* 271 */     listStore.remove(listStore.findModelWithKey(id));
/*     */   }
/*     */   
/*     */   static interface AlarmMonitorGridByTypeUiBinder
/*     */     extends UiBinder<Widget, AlarmMonitorGridByType> {}
/*     */   
/*     */   static interface AlarmLogPropertyAccess
/*     */     extends PropertyAccess<AlarmMessageDTO>
/*     */   {
/* 280 */     public static final ValueProvider<AlarmMessageDTO, String> timestamp = new ValueProvider<AlarmMessageDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(AlarmMessageDTO object)
/*     */         {
/* 285 */           return StringConverter.getFormattedTime(object.getTimestamp());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(AlarmMessageDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 293 */           return "timestamp";
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 303 */     public static final ValueProvider<AlarmMessageDTO, String> ack = new ValueProvider<AlarmMessageDTO, String>()
/*     */       {
/*     */         public String getValue(AlarmMessageDTO object)
/*     */         {
/* 307 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(AlarmMessageDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 315 */           return "ack";
/*     */         }
/*     */       }; ModelKeyProvider<AlarmMessageDTO> id(); ValueProvider<AlarmMessageDTO, Integer> alarmLevel(); ValueProvider<AlarmMessageDTO, String> displayName();
/*     */     ValueProvider<AlarmMessageDTO, String> message();
/*     */     ValueProvider<AlarmMessageDTO, AlarmState> state(); }
/*     */   public void sendMessage() {
/* 321 */     Info.display("簡訊發送", "發送成功");
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\AlarmMonitorGridByType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */