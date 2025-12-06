/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.presenter.DeviceSourcePresenter;
/*     */ import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
/*     */ import com.hwacom.ngtms.cam.client.ui.dnd.AddType;
/*     */ import com.sencha.gxt.core.client.ValueProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.dnd.core.client.DND;
/*     */ import com.sencha.gxt.dnd.core.client.DropTarget;
/*     */ import com.sencha.gxt.dnd.core.client.GridDragSource;
/*     */ import com.sencha.gxt.widget.core.client.Composite;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*     */ import com.sencha.gxt.widget.core.client.grid.Grid;
/*     */ import com.sencha.gxt.widget.core.client.grid.GridView;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeviceSourceViewer
/*     */   extends Composite
/*     */ {
/*  35 */   private static CctvDeviceSourceViewerUiBinder uiBinder = (CctvDeviceSourceViewerUiBinder)GWT.create(CctvDeviceSourceViewerUiBinder.class);
/*     */ 
/*     */ 
/*     */   
/*  39 */   private static GridProperties props = (GridProperties)GWT.create(GridProperties.class);
/*     */   
/*  41 */   private DeviceSourcePresenter presenter = new DeviceSourcePresenter(this);
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<DeviceConfigDTO> store;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ColumnModel<DeviceConfigDTO> cm;
/*     */   @UiField
/*     */   GridView<DeviceConfigDTO> view;
/*     */   @UiField
/*     */   Grid<DeviceConfigDTO> grid;
/*     */   
/*     */   public DeviceSourceViewer() {
/*  54 */     this.store = new ListStore(props.deviceName());
/*  55 */     this.cm = genColumnModel();
/*  56 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*     */     
/*  58 */     initDragAndDrop();
/*     */     
/*  60 */     this.grid.setHideHeaders(true);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initDragAndDrop() {
/*  65 */     GridDragSource<DeviceConfigDTO> dragSource = new GridDragSource(this.grid);
/*  66 */     dragSource.setGroup(AddType.DeviceConfig.toString());
/*     */ 
/*     */     
/*  69 */     DropTarget dropTarget = new DropTarget((Widget)this.grid);
/*  70 */     dropTarget.setOperation(DND.Operation.MOVE);
/*  71 */     dropTarget.setGroup("RemoveDevice");
/*     */   }
/*     */   
/*     */   private ColumnModel<DeviceConfigDTO> genColumnModel() {
/*  75 */     List<ColumnConfig<DeviceConfigDTO, ?>> columnConfigList = new ArrayList<>();
/*     */     
/*  77 */     columnConfigList.add(new ColumnConfig(props.displayName(), 100, ""));
/*     */     
/*  79 */     return new ColumnModel(columnConfigList);
/*     */   }
/*     */   
/*     */   public ListStore<DeviceConfigDTO> getStore() {
/*  83 */     return this.grid.getStore();
/*     */   }
/*     */   
/*     */   public void addDevices(List<DeviceConfigDTO> devices) {
/*  87 */     for (DeviceConfigDTO each : devices) {
/*  88 */       if (this.store.findModelWithKey(each.getDeviceName()) == null) {
/*  89 */         this.store.add(each);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clear() {
/*  95 */     this.store.clear();
/*     */   }
/*     */   
/*     */   public void setDeviceType(List<String> deviceTypes) {
/*  99 */     this.presenter.getRoomDevices(deviceTypes);
/*     */   }
/*     */   
/*     */   public void fillDevices(List<DeviceConfigDTO> list) {
/* 103 */     this.grid.getStore().clear();
/* 104 */     this.grid.getStore().addAll(list);
/*     */   }
/*     */   
/*     */   public void setGridForceFit(boolean forceFit) {
/* 108 */     this.view.setForceFit(forceFit);
/*     */   }
/*     */   
/*     */   public void setColumnWidth(int width) {
/* 112 */     this.cm.getColumn(0).setWidth(width);
/*     */   }
/*     */   
/*     */   static interface GridProperties extends PropertyAccess<DeviceConfigDTO> {
/*     */     ModelKeyProvider<DeviceConfigDTO> deviceName();
/*     */     
/*     */     ValueProvider<DeviceConfigDTO, String> displayName();
/*     */   }
/*     */   
/*     */   static interface CctvDeviceSourceViewerUiBinder extends UiBinder<Widget, DeviceSourceViewer> {}
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\DeviceSourceViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */