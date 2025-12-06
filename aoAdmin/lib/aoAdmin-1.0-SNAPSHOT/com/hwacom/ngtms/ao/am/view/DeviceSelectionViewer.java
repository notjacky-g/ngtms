/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.event.logical.shared.SelectionEvent;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.uibinder.client.UiHandler;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
/*     */ import com.hwacom.ngtms.cam.client.event.AmEventCenter;
/*     */ import com.hwacom.ngtms.cam.client.event.DeviceConfigDeselectEvent;
/*     */ import com.hwacom.ngtms.cam.client.event.DeviceConfigDeselectHandler;
/*     */ import com.hwacom.ngtms.cam.client.ui.dnd.AddType;
/*     */ import com.hwacom.ngtms.cam.client.ui.dnd.RemoveDeviceUtil;
/*     */ import com.sencha.gxt.core.client.ValueProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.dnd.core.client.DND;
/*     */ import com.sencha.gxt.dnd.core.client.DndDropEvent;
/*     */ import com.sencha.gxt.dnd.core.client.DropTarget;
/*     */ import com.sencha.gxt.widget.core.client.Composite;
/*     */ import com.sencha.gxt.widget.core.client.event.BeforeShowContextMenuEvent;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*     */ import com.sencha.gxt.widget.core.client.grid.Grid;
/*     */ import com.sencha.gxt.widget.core.client.menu.Item;
/*     */ import com.sencha.gxt.widget.core.client.menu.Menu;
/*     */ import com.sencha.gxt.widget.core.client.menu.MenuItem;
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
/*     */ public class DeviceSelectionViewer
/*     */   extends Composite
/*     */ {
/*  43 */   private static DeviceSelectionViewerUiBinder uiBinder = (DeviceSelectionViewerUiBinder)GWT.create(DeviceSelectionViewerUiBinder.class);
/*     */ 
/*     */ 
/*     */   
/*  47 */   private static GridProperties props = (GridProperties)GWT.create(GridProperties.class);
/*     */   
/*     */   private DropTarget dropTarget;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<DeviceConfigDTO> store;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ColumnModel<DeviceConfigDTO> cm;
/*     */   @UiField
/*     */   Grid<DeviceConfigDTO> grid;
/*     */   @UiField
/*     */   MenuItem menuItemDelete;
/*     */   @UiField
/*     */   Menu contextMenu;
/*     */   
/*     */   public DeviceSelectionViewer() {
/*  64 */     this.store = new ListStore(props.deviceName());
/*  65 */     this.cm = genColumnModel();
/*  66 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*  67 */     initDragAndDrop();
/*     */     
/*  69 */     this.grid.setHideHeaders(true);
/*     */ 
/*     */     
/*  72 */     RemoveDeviceUtil.buildDeviceGrid(this.grid);
/*     */ 
/*     */     
/*  75 */     AmEventCenter.addDeviceConfigDeselectHandler(new DeviceConfigDeselectHandler()
/*     */         {
/*     */           public void onDeviceConfigDeselect(DeviceConfigDeselectEvent event)
/*     */           {
/*  79 */             DeviceSelectionViewer.this.removeDevices(event.getData());
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  84 */     this.contextMenu.add((Widget)this.menuItemDelete);
/*  85 */     this.grid.setContextMenu(this.contextMenu);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initDragAndDrop() {
/*  90 */     this.dropTarget = new DropTarget((Widget)this.grid);
/*  91 */     this.dropTarget.setOperation(DND.Operation.COPY);
/*  92 */     this.dropTarget.setGroup(AddType.DeviceConfig.toString());
/*     */     
/*  94 */     this.dropTarget.addDropHandler(new DndDropEvent.DndDropHandler()
/*     */         {
/*     */           public void onDrop(DndDropEvent event)
/*     */           {
/*  98 */             DeviceSelectionViewer.this.addDevices((List<DeviceConfigDTO>)event.getData());
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private ColumnModel<DeviceConfigDTO> genColumnModel() {
/* 104 */     List<ColumnConfig<DeviceConfigDTO, ?>> columnConfigList = new ArrayList<>();
/*     */     
/* 106 */     columnConfigList.add(new ColumnConfig(props.displayName(), 100, ""));
/*     */     
/* 108 */     return new ColumnModel(columnConfigList);
/*     */   }
/*     */   
/*     */   public List<DeviceConfigDTO> getDevices() {
/* 112 */     return this.store.getAll();
/*     */   }
/*     */   
/*     */   public String getDevicesStr() {
/* 116 */     String devicesStr = "";
/* 117 */     for (DeviceConfigDTO each : this.store.getAll()) {
/* 118 */       devicesStr = devicesStr + ("".equals(devicesStr) ? each.getDeviceName() : ("," + each.getDeviceName()));
/*     */     }
/* 120 */     return devicesStr;
/*     */   }
/*     */   
/*     */   public void addDevices(List<DeviceConfigDTO> devices) {
/* 124 */     for (DeviceConfigDTO each : devices) {
/* 125 */       if (this.store.findModelWithKey(each.getDeviceName()) == null) {
/* 126 */         this.store.add(each);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeDevices(List<DeviceConfigDTO> devices) {
/* 132 */     for (DeviceConfigDTO each : devices) {
/* 133 */       this.store.remove(each);
/*     */     }
/*     */   }
/*     */   
/*     */   public void clear() {
/* 138 */     this.store.clear();
/*     */   }
/*     */   
/*     */   @UiHandler({"menuItemDelete"})
/*     */   void onMenuItemDeleteClicked(SelectionEvent<Item> event) {
/* 143 */     removeDevices(this.grid.getSelectionModel().getSelectedItems());
/*     */   }
/*     */   
/*     */   @UiHandler({"grid"})
/*     */   void onBeforeShowViewerContextMenu(BeforeShowContextMenuEvent event) {
/* 148 */     if (this.grid.getSelectionModel().getSelection().size() > 0) {
/* 149 */       this.menuItemDelete.setEnabled(true);
/*     */     } else {
/* 151 */       this.menuItemDelete.setEnabled(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   static interface DeviceSelectionViewerUiBinder extends UiBinder<Widget, DeviceSelectionViewer> {}
/*     */   
/*     */   static interface GridProperties extends PropertyAccess<DeviceConfigDTO> {
/*     */     ModelKeyProvider<DeviceConfigDTO> deviceName();
/*     */     
/*     */     ValueProvider<DeviceConfigDTO, String> displayName();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\DeviceSelectionViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */