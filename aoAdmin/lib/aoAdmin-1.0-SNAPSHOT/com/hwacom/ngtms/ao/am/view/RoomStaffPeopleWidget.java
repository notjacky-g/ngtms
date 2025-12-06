/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.i18n.client.DateTimeFormat;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomStaffPeopleDTO;
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
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RoomStaffPeopleWidget
/*     */   extends Composite
/*     */ {
/*  31 */   private static RoomStaffPeopleWidgetUiBinder uiBinder = (RoomStaffPeopleWidgetUiBinder)GWT.create(RoomStaffPeopleWidgetUiBinder.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   private RoomStaffPeoplePropertyAccess propertyAccess = (RoomStaffPeoplePropertyAccess)GWT.create(RoomStaffPeoplePropertyAccess.class);
/*     */   
/*     */   @UiField
/*     */   Grid<RoomStaffPeopleDTO> grid;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<RoomStaffPeopleDTO> listStore;
/*     */   @UiField(provided = true)
/*     */   ColumnModel<RoomStaffPeopleDTO> columnModel;
/*     */   
/*     */   public RoomStaffPeopleWidget() {
/*  47 */     this.listStore = new ListStore(this.propertyAccess.id());
/*  48 */     this.listStore.addSortInfo(new Store.StoreSortInfo(RoomStaffPeoplePropertyAccess.inTime, SortDir.DESC));
/*  49 */     initColumnModel();
/*  50 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*  51 */     this.grid.getView().setAdjustForHScroll(false);
/*     */   }
/*     */   
/*     */   private void initColumnModel() {
/*  55 */     List<ColumnConfig<RoomStaffPeopleDTO, ?>> columnConfigs = new ArrayList<>();
/*     */ 
/*     */     
/*  58 */     ColumnConfig<RoomStaffPeopleDTO, String> card = new ColumnConfig(RoomStaffPeoplePropertyAccess.card);
/*     */     
/*  60 */     card.setWidth(90);
/*  61 */     card.setHeader("卡片號碼");
/*  62 */     columnConfigs.add(card);
/*     */ 
/*     */     
/*  65 */     ColumnConfig<RoomStaffPeopleDTO, String> name = new ColumnConfig(this.propertyAccess.name());
/*  66 */     name.setWidth(70);
/*  67 */     name.setHeader("持卡人員");
/*  68 */     columnConfigs.add(name);
/*     */ 
/*     */     
/*  71 */     ColumnConfig<RoomStaffPeopleDTO, String> cellPhone = new ColumnConfig(this.propertyAccess.cellPhone());
/*  72 */     cellPhone.setWidth(90);
/*  73 */     cellPhone.setHeader("電話");
/*  74 */     columnConfigs.add(cellPhone);
/*     */     
/*  76 */     ColumnConfig<RoomStaffPeopleDTO, String> inTime = new ColumnConfig(RoomStaffPeoplePropertyAccess.inTime);
/*     */     
/*  78 */     inTime.setWidth(140);
/*  79 */     inTime.setHeader("刷進時間");
/*  80 */     columnConfigs.add(inTime);
/*     */ 
/*     */     
/*  83 */     ColumnConfig<RoomStaffPeopleDTO, String> memo = new ColumnConfig(this.propertyAccess.memo());
/*  84 */     memo.setWidth(120);
/*  85 */     memo.setHeader("備註");
/*  86 */     columnConfigs.add(memo);
/*     */     
/*  88 */     this.columnModel = new ColumnModel(columnConfigs);
/*     */   }
/*     */   
/*     */   public Grid<RoomStaffPeopleDTO> getGrid() {
/*  92 */     return this.grid;
/*     */   }
/*     */   
/*     */   public ListStore<RoomStaffPeopleDTO> getListStore() {
/*  96 */     return this.listStore;
/*     */   }
/*     */ 
/*     */   
/*     */   static interface RoomStaffPeoplePropertyAccess
/*     */     extends PropertyAccess<RoomStaffPeopleDTO>
/*     */   {
/* 103 */     public static final ValueProvider<RoomStaffPeopleDTO, String> card = new ValueProvider<RoomStaffPeopleDTO, String>()
/*     */       {
/*     */         public void setValue(RoomStaffPeopleDTO object, String value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public String getValue(RoomStaffPeopleDTO object) {
/* 111 */           return object.getId();
/*     */         }
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 116 */           return "id";
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     public static final ValueProvider<RoomStaffPeopleDTO, String> inTime = new ValueProvider<RoomStaffPeopleDTO, String>()
/*     */       {
/*     */         
/*     */         public String getValue(RoomStaffPeopleDTO object)
/*     */         {
/* 129 */           return DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").format(object.getInTime());
/*     */         }
/*     */ 
/*     */         
/*     */         public void setValue(RoomStaffPeopleDTO object, String value) {}
/*     */ 
/*     */         
/*     */         public String getPath() {
/* 137 */           return "inTime";
/*     */         }
/*     */       };
/*     */     
/*     */     ModelKeyProvider<RoomStaffPeopleDTO> id();
/*     */     
/*     */     ValueProvider<RoomStaffPeopleDTO, String> name();
/*     */     
/*     */     ValueProvider<RoomStaffPeopleDTO, String> cellPhone();
/*     */     
/*     */     ValueProvider<RoomStaffPeopleDTO, String> memo();
/*     */   }
/*     */   
/*     */   static interface RoomStaffPeopleWidgetUiBinder extends UiBinder<Widget, RoomStaffPeopleWidget> {}
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\RoomStaffPeopleWidget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */