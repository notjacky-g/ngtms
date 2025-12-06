/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ 
/*     */ import com.google.gwt.cell.client.AbstractCell;
/*     */ import com.google.gwt.cell.client.Cell;
/*     */ import com.google.gwt.cell.client.DateCell;
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.editor.client.Editor.Path;
/*     */ import com.google.gwt.event.shared.EventHandler;
/*     */ import com.google.gwt.event.shared.GwtEvent;
/*     */ import com.google.gwt.event.shared.HandlerRegistration;
/*     */ import com.google.gwt.i18n.client.DateTimeFormat;
/*     */ import com.google.gwt.i18n.shared.DateTimeFormat;
/*     */ import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.uibinder.client.UiHandler;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.presenter.UserPresenter;
/*     */ import com.hwacom.ngtms.cam.client.ui.AmTab;
/*     */ import com.hwacom.ngtms.cam.client.ui.ExportCsvFile;
/*     */ import com.hwacom.ngtms.common.am.event.AccountDataEvent;
/*     */ import com.hwacom.ngtms.common.am.event.UnitViewerEvent;
/*     */ import com.hwacom.ngtms.common.am.event.UserEvent;
/*     */ import com.hwacom.ngtms.common.am.event.UserViewerEvent;
/*     */ import com.hwacom.ngtms.common.shared.dto.UnitDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.UserDTO;
/*     */ import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
/*     */ import com.sencha.gxt.core.client.Style;
/*     */ import com.sencha.gxt.core.client.ValueProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.data.shared.SortDir;
/*     */ import com.sencha.gxt.data.shared.Store;
/*     */ import com.sencha.gxt.widget.core.client.ContentPanel;
/*     */ import com.sencha.gxt.widget.core.client.button.TextButton;
/*     */ import com.sencha.gxt.widget.core.client.event.SelectEvent;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*     */ import com.sencha.gxt.widget.core.client.grid.Grid;
/*     */ import com.sencha.gxt.widget.core.client.grid.GridView;
/*     */ import com.sencha.gxt.widget.core.client.info.Info;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UserViewer
/*     */   extends AmTab
/*     */ {
/*  56 */   private static UserViewerUiBinder uiBinder = (UserViewerUiBinder)GWT.create(UserViewerUiBinder.class);
/*     */ 
/*     */   
/*     */   private static final String DATE_FORMAT_PATTERN = "yyyy/MM/dd";
/*     */   
/*     */   private static final String DATE_TIME_FORMAT_PATTERN = "yyyy/MM/dd HH:mm:ss";
/*     */   
/*  63 */   private static final Messages messages = (Messages)GWT.create(Messages.class);
/*     */   
/*  65 */   private static final UserProperties props = (UserProperties)GWT.create(UserProperties.class);
/*     */   
/*  67 */   private UserPresenter presenter = new UserPresenter(this);
/*  68 */   private final ClientFactory clientFactory = (ClientFactory)GWT.create(ClientFactory.class);
/*     */   
/*     */   private final HandlerRegistration handlerRegistration;
/*     */   private final HandlerRegistration accountDataHandlerRegistration;
/*     */   private final HandlerRegistration unitViewerEventHandlerRegistration;
/*     */   @UiField
/*     */   ContentPanel gridContentPanel;
/*     */   @UiField(provided = true)
/*     */   ColumnModel<UserDTO> cm;
/*     */   @UiField(provided = true)
/*     */   ListStore<UserDTO> store;
/*     */   @UiField
/*     */   GridView<UserDTO> view;
/*     */   @UiField
/*     */   Grid<UserDTO> grid;
/*     */   
/*     */   public UserViewer() {
/*  85 */     this.store = new ListStore(props.key());
/*  86 */     initColumnModel();
/*  87 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*  88 */     this.store.addSortInfo(new Store.StoreSortInfo(props.name(), SortDir.ASC));
/*  89 */     this.grid.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
/*  90 */     this
/*  91 */       .handlerRegistration = this.clientFactory.getEventBus().addHandler(UserEvent.TYPE, (EventHandler)new DefaultUserEventHandler());
/*  92 */     this
/*     */ 
/*     */       
/*  95 */       .accountDataHandlerRegistration = this.clientFactory.getEventBus().addHandler(AccountDataEvent.TYPE, (EventHandler)new DefaultAccountDataEventHandler());
/*  96 */     this
/*     */ 
/*     */       
/*  99 */       .unitViewerEventHandlerRegistration = this.clientFactory.getEventBus().addHandler(UnitViewerEvent.TYPE, (EventHandler)new DefaultUnitViewerEventHandler());
/*     */   }
/*     */   
/*     */   private void initColumnModel() {
/* 103 */     List<ColumnConfig<UserDTO, ?>> columnConfigs = new ArrayList<>();
/*     */     
/* 105 */     ColumnConfig<UserDTO, String> login = new ColumnConfig(props.login());
/* 106 */     login.setWidth(160);
/* 107 */     login.setHeader("登入帳號");
/* 108 */     login.setHideable(true);
/* 109 */     login.setMenuDisabled(false);
/* 110 */     login.setSortable(true);
/* 111 */     columnConfigs.add(login);
/*     */     
/* 113 */     ColumnConfig<UserDTO, String> name = new ColumnConfig(props.name());
/* 114 */     name.setWidth(160);
/* 115 */     name.setHeader("帳號名稱");
/* 116 */     name.setHideable(true);
/* 117 */     name.setMenuDisabled(false);
/* 118 */     name.setSortable(true);
/* 119 */     columnConfigs.add(name);
/*     */     
/* 121 */     ColumnConfig<UserDTO, String> accountType = new ColumnConfig(UserProperties.accountType);
/*     */     
/* 123 */     accountType.setWidth(70);
/* 124 */     accountType.setHeader("帳號種類");
/* 125 */     accountType.setHideable(true);
/* 126 */     accountType.setMenuDisabled(false);
/* 127 */     accountType.setSortable(true);
/* 128 */     columnConfigs.add(accountType);
/*     */     
/* 130 */     ColumnConfig<UserDTO, Date> lockTime = new ColumnConfig(props.lockTime());
/* 131 */     lockTime.setWidth(120);
/* 132 */     lockTime.setHeader("登入失敗鎖定時間");
/* 133 */     lockTime.setCell((Cell)new DateCell((DateTimeFormat)DateTimeFormat.getFormat("yyyy/MM/dd HH:mm:ss")));
/* 134 */     lockTime.setHideable(true);
/* 135 */     lockTime.setMenuDisabled(false);
/* 136 */     lockTime.setSortable(true);
/* 137 */     columnConfigs.add(lockTime);
/*     */ 
/*     */     
/* 140 */     ColumnConfig<UserDTO, Integer> loginFailureCount = new ColumnConfig(props.loginFailureCount());
/* 141 */     loginFailureCount.setWidth(90);
/* 142 */     loginFailureCount.setHeader("登入失敗次數");
/* 143 */     loginFailureCount.setHideable(true);
/* 144 */     loginFailureCount.setMenuDisabled(false);
/* 145 */     loginFailureCount.setSortable(true);
/* 146 */     columnConfigs.add(loginFailureCount);
/*     */     
/* 148 */     ColumnConfig<UserDTO, Date> lastPwdChangeTime = new ColumnConfig(props.lastPwdChangeTime());
/* 149 */     lastPwdChangeTime.setWidth(120);
/* 150 */     lastPwdChangeTime.setHeader("密碼修改時間");
/* 151 */     lastPwdChangeTime.setCell((Cell)new DateCell((DateTimeFormat)DateTimeFormat.getFormat("yyyy/MM/dd HH:mm:ss")));
/* 152 */     lastPwdChangeTime.setHideable(true);
/* 153 */     lastPwdChangeTime.setMenuDisabled(false);
/* 154 */     lastPwdChangeTime.setSortable(true);
/* 155 */     columnConfigs.add(lastPwdChangeTime);
/*     */     
/* 157 */     ColumnConfig<UserDTO, Boolean> enable = new ColumnConfig(props.enable());
/* 158 */     enable.setWidth(70);
/* 159 */     enable.setHeader("是否啟用");
/* 160 */     enable.setHideable(true);
/* 161 */     enable.setMenuDisabled(false);
/* 162 */     enable.setSortable(true);
/* 163 */     enable.setCell((Cell)new AbstractCell<Boolean>(new String[0])
/*     */         {
/*     */           
/*     */           public void render(Cell.Context context, Boolean value, SafeHtmlBuilder sb)
/*     */           {
/* 168 */             String style = "style='color: " + (value.booleanValue() ? "green" : "red") + "'";
/* 169 */             sb.appendHtmlConstant("<span " + style + ">" + (
/*     */ 
/*     */ 
/*     */                 
/* 173 */                 value.booleanValue() ? UserViewer.messages.message_yes() : UserViewer.messages.message_no()) + "</span>");
/*     */           }
/*     */         });
/*     */     
/* 177 */     columnConfigs.add(enable);
/*     */     
/* 179 */     ColumnConfig<UserDTO, String> roleStr = new ColumnConfig(props.roleStr());
/* 180 */     roleStr.setWidth(200);
/* 181 */     roleStr.setHeader("已選角色");
/* 182 */     roleStr.setHideable(true);
/* 183 */     roleStr.setMenuDisabled(false);
/* 184 */     roleStr.setSortable(true);
/* 185 */     columnConfigs.add(roleStr);
/*     */     
/* 187 */     ColumnConfig<UserDTO, String> unitStr = new ColumnConfig(props.unitStr());
/* 188 */     unitStr.setWidth(150);
/* 189 */     unitStr.setHeader("已選單位");
/* 190 */     unitStr.setHideable(true);
/* 191 */     unitStr.setMenuDisabled(false);
/* 192 */     unitStr.setSortable(true);
/* 193 */     columnConfigs.add(unitStr);
/*     */     
/* 195 */     ColumnConfig<UserDTO, Date> updateTime = new ColumnConfig(props.updateTime());
/* 196 */     updateTime.setWidth(120);
/* 197 */     updateTime.setHeader("更新時間");
/* 198 */     updateTime.setCell((Cell)new DateCell((DateTimeFormat)DateTimeFormat.getFormat("yyyy/MM/dd HH:mm:ss")));
/* 199 */     columnConfigs.add(updateTime);
/*     */     
/* 201 */     ColumnConfig<UserDTO, String> mobile = new ColumnConfig(props.mobile());
/* 202 */     mobile.setWidth(100);
/* 203 */     mobile.setHeader("手機");
/* 204 */     mobile.setHideable(true);
/* 205 */     mobile.setMenuDisabled(false);
/* 206 */     mobile.setSortable(true);
/* 207 */     columnConfigs.add(mobile);
/*     */     
/* 209 */     ColumnConfig<UserDTO, String> mail = new ColumnConfig(props.mail());
/* 210 */     mail.setWidth(150);
/* 211 */     mail.setHeader("Mail");
/* 212 */     mail.setHideable(true);
/* 213 */     mail.setMenuDisabled(false);
/* 214 */     mail.setSortable(true);
/* 215 */     columnConfigs.add(mail);
/*     */ 
/*     */     
/* 218 */     ColumnConfig<UserDTO, String> descriptionConfig = new ColumnConfig(props.description());
/* 219 */     descriptionConfig.setWidth(250);
/* 220 */     descriptionConfig.setHeader("帳號說明");
/* 221 */     descriptionConfig.setHideable(true);
/* 222 */     descriptionConfig.setMenuDisabled(false);
/* 223 */     descriptionConfig.setSortable(true);
/* 224 */     columnConfigs.add(descriptionConfig);
/*     */ 
/*     */     
/* 227 */     ColumnConfig<UserDTO, Boolean> checkExpired = new ColumnConfig(props.checkExpired());
/* 228 */     checkExpired.setWidth(100);
/* 229 */     checkExpired.setHeader("是否有期限限制");
/* 230 */     checkExpired.setHideable(true);
/* 231 */     checkExpired.setMenuDisabled(false);
/* 232 */     checkExpired.setSortable(true);
/* 233 */     checkExpired.setCell((Cell)new AbstractCell<Boolean>(new String[0])
/*     */         {
/*     */           
/*     */           public void render(Cell.Context context, Boolean value, SafeHtmlBuilder sb)
/*     */           {
/* 238 */             String style = "style='color: " + (value.booleanValue() ? "green" : "red") + "'";
/* 239 */             sb.appendHtmlConstant("<span " + style + ">" + (
/*     */ 
/*     */ 
/*     */                 
/* 243 */                 value.booleanValue() ? UserViewer.messages.message_yes() : UserViewer.messages.message_no()) + "</span>");
/*     */           }
/*     */         });
/*     */     
/* 247 */     columnConfigs.add(checkExpired);
/*     */     
/* 249 */     ColumnConfig<UserDTO, Date> startTime = new ColumnConfig(props.startTime());
/* 250 */     startTime.setWidth(80);
/* 251 */     startTime.setHeader("開始時間");
/* 252 */     startTime.setCell((Cell)new DateCell((DateTimeFormat)DateTimeFormat.getFormat("yyyy/MM/dd")));
/* 253 */     columnConfigs.add(startTime);
/*     */     
/* 255 */     ColumnConfig<UserDTO, Date> endTime = new ColumnConfig(props.endTime());
/* 256 */     endTime.setWidth(80);
/* 257 */     endTime.setHeader("結束時間");
/* 258 */     endTime.setCell((Cell)new DateCell((DateTimeFormat)DateTimeFormat.getFormat("yyyy/MM/dd")));
/* 259 */     columnConfigs.add(endTime);
/*     */     
/* 261 */     this.cm = new ColumnModel(columnConfigs);
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
/*     */   static interface UserViewerUiBinder
/*     */     extends UiBinder<Widget, UserViewer> {}
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
/*     */   public static interface UserProperties
/*     */     extends PropertyAccess<UserDTO>
/*     */   {
/* 294 */     public static final ValueProvider<UserDTO, String> accountType = new ValueProvider<UserDTO, String>()
/*     */       {
/*     */         public void setValue(UserDTO object, String value) {}
/*     */ 
/*     */ 
/*     */         
/*     */         public String getValue(UserDTO object) {
/* 301 */           return Objects.equals(Boolean.TRUE, object.getCheckExpired()) ? "臨時" : "永久";
/*     */         }
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 306 */           return "accountType";
/*     */         }
/*     */       }; @Path("login")
/*     */     ModelKeyProvider<UserDTO> key(); ValueProvider<UserDTO, String> login(); ValueProvider<UserDTO, String> pwd1(); ValueProvider<UserDTO, String> name(); ValueProvider<UserDTO, String> description(); ValueProvider<UserDTO, Date> startTime(); ValueProvider<UserDTO, Date> endTime(); ValueProvider<UserDTO, Date> updateTime(); ValueProvider<UserDTO, Boolean> checkExpired(); ValueProvider<UserDTO, Boolean> enable();
/*     */     ValueProvider<UserDTO, String> roleStr();
/*     */     ValueProvider<UserDTO, String> mobile();
/*     */     ValueProvider<UserDTO, String> mail();
/*     */     ValueProvider<UserDTO, String> unitStr();
/*     */     ValueProvider<UserDTO, Date> lockTime();
/*     */     ValueProvider<UserDTO, Integer> loginFailureCount();
/*     */     ValueProvider<UserDTO, Date> lastPwdChangeTime(); }
/*     */   @UiHandler({"exportButton"})
/*     */   void exportButton(SelectEvent se) {
/* 319 */     if (this.store.size() == 0) {
/* 320 */       Info.display("無內容", "不進行匯出");
/*     */       
/*     */       return;
/*     */     } 
/* 324 */     List<List<String>> csvData = new ArrayList<>();
/*     */ 
/*     */     
/* 327 */     List<String> title = new ArrayList<>();
/* 328 */     title.add("帳號名單");
/* 329 */     csvData.add(title);
/*     */ 
/*     */     
/* 332 */     List<String> conditions = new ArrayList<>();
/* 333 */     conditions.add("匯出時間:");
/* 334 */     Date time = new Date();
/* 335 */     DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
/*     */     try {
/* 337 */       conditions.add(format.format(time));
/* 338 */     } catch (Exception e) {
/* 339 */       GWT.log("Transfer date failed.", e);
/*     */     } 
/* 341 */     csvData.add(conditions);
/*     */ 
/*     */     
/* 344 */     List<String> header = new ArrayList<>();
/* 345 */     for (ColumnConfig<UserDTO, ?> config : (Iterable<ColumnConfig<UserDTO, ?>>)this.grid.getColumnModel().getColumns()) {
/* 346 */       if (!config.isHidden()) {
/* 347 */         header.add(config.getHeader().asString());
/*     */       }
/*     */     } 
/* 350 */     csvData.add(header);
/*     */ 
/*     */     
/* 353 */     List<UserDTO> infoData = this.store.getAll();
/* 354 */     for (UserDTO item : infoData) {
/* 355 */       List<String> csvDatum = new ArrayList<>();
/* 356 */       for (ColumnConfig<UserDTO, ?> config : (Iterable<ColumnConfig<UserDTO, ?>>)this.grid.getColumnModel().getColumns()) {
/* 357 */         if (!config.isHidden()) {
/* 358 */           csvDatum.add(
/* 359 */               (config.getValueProvider().getValue(item) == null) ? "" : 
/*     */               
/* 361 */               String.valueOf(config.getValueProvider().getValue(item)));
/*     */         }
/*     */       } 
/* 364 */       csvData.add(csvDatum);
/*     */     } 
/* 366 */     ExportCsvFile.exportAsCsv("帳號名單.csv", csvData);
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
/*     */   protected void onUnload() {
/* 378 */     this.handlerRegistration.removeHandler();
/* 379 */     this.accountDataHandlerRegistration.removeHandler();
/* 380 */     this.unitViewerEventHandlerRegistration.removeHandler();
/* 381 */     super.onUnload();
/*     */   }
/*     */   
/*     */   public void init(List<UserDTO> users) {
/* 385 */     this.store.clear();
/* 386 */     this.store.addAll(users);
/* 387 */     this.clientFactory
/* 388 */       .getEventBus()
/* 389 */       .fireEventFromSource((GwtEvent)new UserViewerEvent(UserViewerEvent.Action.GRID_READY), this);
/*     */   }
/*     */   
/*     */   public void setPresenter(UserPresenter presenter) {
/* 393 */     this.presenter = presenter;
/*     */   }
/*     */   
/*     */   public void addButtonInGridContentPanel(TextButton button) {
/* 397 */     this.gridContentPanel.addButton((Widget)button);
/*     */   }
/*     */   
/*     */   static interface UnitProperties extends PropertyAccess<UnitDTO> {
/*     */     @Path("name")
/*     */     ModelKeyProvider<UnitDTO> id();
/*     */     
/*     */     ValueProvider<UnitDTO, String> name();
/*     */   }
/*     */   
/*     */   class DefaultUserEventHandler implements UserEvent.UserEventHandler {
/*     */     public void onSave(UserEvent event) {}
/*     */     
/*     */     public void onAdd(UserEvent event) {}
/*     */     
/*     */     public void onDelete(UserEvent event) {}
/*     */   }
/*     */   
/*     */   class DefaultAccountDataEventHandler implements AccountDataEvent.AccountDataEventHandler {
/*     */     public void onRoleInitData(AccountDataEvent event) {}
/*     */   }
/*     */   
/*     */   class DefaultUnitViewerEventHandler implements UnitViewerEvent.UnitViewerEventHandler {
/*     */     public void onUnitAdded(UnitViewerEvent event) {}
/*     */     
/*     */     public void onUnitRemoved(UnitViewerEvent event) {}
/*     */     
/*     */     public void onUnitUpdated(UnitViewerEvent event) {}
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\UserViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */