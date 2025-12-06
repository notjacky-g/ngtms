/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.editor.client.Editor.Path;
/*     */ import com.google.gwt.event.shared.EventHandler;
/*     */ import com.google.gwt.event.shared.GwtEvent;
/*     */ import com.google.gwt.event.shared.HandlerRegistration;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.uibinder.client.UiHandler;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.presenter.RoleFunctionPermissionPresenter;
/*     */ import com.hwacom.ngtms.cam.client.ui.AmTab;
/*     */ import com.hwacom.ngtms.common.am.event.RoleEvent;
/*     */ import com.hwacom.ngtms.common.am.event.RoleFunctionPermissionEvent;
/*     */ import com.hwacom.ngtms.common.shared.dto.FunctionPermissionDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.RoleDTO;
/*     */ import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
/*     */ import com.sencha.gxt.core.client.Style;
/*     */ import com.sencha.gxt.core.client.ValueProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.data.shared.TreeStore;
/*     */ import com.sencha.gxt.widget.core.client.ContentPanel;
/*     */ import com.sencha.gxt.widget.core.client.Dialog;
/*     */ import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
/*     */ import com.sencha.gxt.widget.core.client.button.TextButton;
/*     */ import com.sencha.gxt.widget.core.client.button.ToolButton;
/*     */ import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
/*     */ import com.sencha.gxt.widget.core.client.event.RowClickEvent;
/*     */ import com.sencha.gxt.widget.core.client.event.SelectEvent;
/*     */ import com.sencha.gxt.widget.core.client.form.TextField;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*     */ import com.sencha.gxt.widget.core.client.grid.Grid;
/*     */ import com.sencha.gxt.widget.core.client.grid.GridView;
/*     */ import com.sencha.gxt.widget.core.client.info.Info;
/*     */ import com.sencha.gxt.widget.core.client.tree.Tree;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
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
/*     */ public class RoleFunctionPermissionViewer
/*     */   extends AmTab
/*     */ {
/*  61 */   private static RoleFunctionPermissionViewerUiBinder uiBinder = (RoleFunctionPermissionViewerUiBinder)GWT.create(RoleFunctionPermissionViewerUiBinder.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private static final RoleProperties props = (RoleProperties)GWT.create(RoleProperties.class);
/*     */ 
/*     */   
/*  69 */   private FunctionPermissionPropertyAccess propertyAccess = (FunctionPermissionPropertyAccess)GWT.create(FunctionPermissionPropertyAccess.class);
/*     */   
/*  71 */   private static final Messages messages = (Messages)GWT.create(Messages.class);
/*     */   private RoleFunctionPermissionPresenter presenter;
/*  73 */   private final ClientFactory clientFactory = (ClientFactory)GWT.create(ClientFactory.class);
/*     */   
/*     */   private final HandlerRegistration roleHandlerRegistration;
/*     */   
/*     */   private final HandlerRegistration handlerRegistration;
/*     */   
/*     */   private Runnable roleSaver;
/*     */   
/*     */   @UiField
/*     */   Grid<RoleDTO> grid;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<RoleDTO> store;
/*     */   @UiField(provided = true)
/*     */   ColumnModel<RoleDTO> cm;
/*     */   @UiField
/*     */   GridView<RoleDTO> view;
/*     */   @UiField
/*     */   ToolButton addRole;
/*     */   @UiField
/*     */   ContentPanel rolePanel;
/*     */   @UiField
/*     */   TextField name;
/*     */   @UiField
/*     */   TextField description;
/*     */   @UiField
/*     */   TextButton deleteRole;
/*     */   @UiField
/*     */   ContentPanel functionPermissionListPanel;
/*     */   @UiField
/*     */   Tree<FunctionPermissionDTO, String> tree;
/*     */   @UiField(provided = true)
/*     */   TreeStore<FunctionPermissionDTO> treeStore;
/*     */   @UiField(provided = true)
/*     */   ValueProvider<FunctionPermissionDTO, String> treeValueProvider;
/*     */   
/*     */   public RoleFunctionPermissionViewer() {
/* 110 */     this.store = new ListStore(props.key());
/* 111 */     initColumnModel();
/* 112 */     this.treeStore = new TreeStore(this.propertyAccess.id());
/* 113 */     this.treeValueProvider = this.propertyAccess.name();
/* 114 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/* 115 */     this.grid.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
/* 116 */     this.view.setAutoExpandColumn(this.cm.findColumnConfig(props.description().getPath()));
/* 117 */     this.addRole.fireEvent((GwtEvent)new SelectEvent());
/* 118 */     DefaultRoleFunctionPermissionEventHandler eventHandler = new DefaultRoleFunctionPermissionEventHandler();
/*     */     
/* 120 */     this.roleHandlerRegistration = this.clientFactory.getEventBus().addHandler(RoleEvent.TYPE, (EventHandler)eventHandler);
/* 121 */     this
/* 122 */       .handlerRegistration = this.clientFactory.getEventBus().addHandler(RoleFunctionPermissionEvent.TYPE, (EventHandler)eventHandler);
/* 123 */     this.presenter = new RoleFunctionPermissionPresenter(this);
/* 124 */     this.tree.setCheckable(true);
/* 125 */     this.tree.setCheckStyle(Tree.CheckCascade.TRI);
/* 126 */     this.tree.setAutoLoad(true);
/* 127 */     this.tree.expandAll();
/*     */   }
/*     */   
/*     */   private void initColumnModel() {
/* 131 */     List<ColumnConfig<RoleDTO, ?>> columnConfigs = new ArrayList<>();
/*     */ 
/*     */     
/* 134 */     ColumnConfig<RoleDTO, String> name = new ColumnConfig(props.name(), 140, "角色名稱");
/* 135 */     name.setSortable(true);
/* 136 */     columnConfigs.add(name);
/*     */ 
/*     */     
/* 139 */     ColumnConfig<RoleDTO, String> descriptionConfig = new ColumnConfig(props.description());
/* 140 */     descriptionConfig.setHeader("角色說明");
/* 141 */     descriptionConfig.setSortable(true);
/* 142 */     columnConfigs.add(descriptionConfig);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     this.cm = new ColumnModel(columnConfigs);
/*     */   }
/*     */   
/*     */   @UiHandler({"grid"})
/*     */   public void rowClick(RowClickEvent event) {
/* 155 */     uncheckTreeAll();
/* 156 */     RoleDTO dto = (RoleDTO)this.store.get(event.getRowIndex());
/* 157 */     if (dto != null) {
/* 158 */       fillTreeStoreSelected(dto);
/* 159 */       this.name.clearInvalid();
/* 160 */       this.name.setValue(dto.getName());
/* 161 */       this.description.clearInvalid();
/* 162 */       this.description.setValue(dto.getDescription());
/*     */     } 
/* 164 */     this.name.disable();
/* 165 */     this.deleteRole.show();
/* 166 */     this.rolePanel.syncSize();
/* 167 */     this.functionPermissionListPanel.unmask();
/* 168 */     this.roleSaver = (() -> {
/*     */         if (!this.name.validate()) {
/*     */           return;
/*     */         }
/*     */         ConfirmMessageBox box = new ConfirmMessageBox(messages.message(), messages.message_itemSaveConfirm());
/*     */         box.addDialogHideHandler(new DialogHideEvent.DialogHideHandler()
/*     */             {
/*     */               public void onDialogHide(DialogHideEvent event)
/*     */               {
/* 177 */                 if (event.getHideButton() == Dialog.PredefinedButton.YES) {
/* 178 */                   RoleDTO dto = (RoleDTO)RoleFunctionPermissionViewer.this.store.findModelWithKey(((String)RoleFunctionPermissionViewer.this.name.getValue()).trim());
/* 179 */                   if (dto != null) {
/* 180 */                     dto.setDescription((String)RoleFunctionPermissionViewer.this.description.getValue());
/* 181 */                     dto.setUpdateTime(new Date());
/* 182 */                     RoleFunctionPermissionViewer.this.clientFactory
/* 183 */                       .getEventBus()
/* 184 */                       .fireEventFromSource((GwtEvent)new RoleEvent(RoleEvent.Action.SAVE), dto);
/*     */                   }
/*     */                   else {
/*     */                     
/* 188 */                     Info.display(RoleFunctionPermissionViewer.messages.message(), RoleFunctionPermissionViewer.messages.message_itemNoExisted());
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */             });
/*     */         box.show();
/*     */       });
/*     */   }
/*     */   
/*     */   @UiHandler({"addRole"})
/*     */   public void addButton(SelectEvent event) {
/* 199 */     this.name.clear();
/* 200 */     this.description.clear();
/* 201 */     this.name.enable();
/* 202 */     this.deleteRole.hide();
/* 203 */     this.grid.getSelectionModel().deselectAll();
/* 204 */     uncheckTreeAll();
/* 205 */     this.functionPermissionListPanel.mask();
/* 206 */     this.roleSaver = (() -> {
/*     */         if (!this.name.isValid() || !this.description.isValid()) {
/*     */           return;
/*     */         }
/*     */         
/*     */         ConfirmMessageBox box = new ConfirmMessageBox(messages.message(), messages.message_itemAddConfirm());
/*     */         
/*     */         box.addDialogHideHandler(new DialogHideEvent.DialogHideHandler()
/*     */             {
/*     */               public void onDialogHide(DialogHideEvent event)
/*     */               {
/* 217 */                 if (event.getHideButton() == Dialog.PredefinedButton.YES) {
/* 218 */                   RoleDTO storeDto = (RoleDTO)RoleFunctionPermissionViewer.this.store.findModelWithKey(((String)RoleFunctionPermissionViewer.this.name.getValue()).trim());
/* 219 */                   if (storeDto == null) {
/* 220 */                     RoleDTO dto = new RoleDTO();
/* 221 */                     dto.setName((String)RoleFunctionPermissionViewer.this.name.getValue());
/* 222 */                     dto.setDescription((String)RoleFunctionPermissionViewer.this.description.getValue());
/* 223 */                     dto.setUpdateTime(new Date());
/* 224 */                     RoleFunctionPermissionViewer.this.clientFactory
/* 225 */                       .getEventBus()
/* 226 */                       .fireEventFromSource((GwtEvent)new RoleEvent(RoleEvent.Action.ADD), dto);
/*     */                   }
/*     */                   else {
/*     */                     
/* 230 */                     Info.display(RoleFunctionPermissionViewer.messages.message(), RoleFunctionPermissionViewer.messages.message_itemExisted());
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */             });
/*     */         box.show();
/*     */       });
/*     */   }
/*     */   
/*     */   @UiHandler({"deleteRole"})
/*     */   public void deleteButton(SelectEvent event) {
/* 241 */     GWT.log("deleteButton Click!");
/* 242 */     if (!this.name.validate())
/*     */       return; 
/* 244 */     ConfirmMessageBox box = new ConfirmMessageBox(messages.message(), messages.message_itemDeleteConfirm());
/* 245 */     box.addDialogHideHandler(new DialogHideEvent.DialogHideHandler()
/*     */         {
/*     */           public void onDialogHide(DialogHideEvent event)
/*     */           {
/* 249 */             if (event.getHideButton() == Dialog.PredefinedButton.YES) {
/* 250 */               RoleDTO storeDto = (RoleDTO)RoleFunctionPermissionViewer.this.store.findModelWithKey(((String)RoleFunctionPermissionViewer.this.name.getValue()).trim());
/* 251 */               if (storeDto != null) {
/* 252 */                 RoleDTO dto = new RoleDTO();
/* 253 */                 dto.setName((String)RoleFunctionPermissionViewer.this.name.getValue());
/* 254 */                 dto.setDescription((String)RoleFunctionPermissionViewer.this.description.getValue());
/* 255 */                 dto.setUpdateTime(new Date());
/* 256 */                 RoleFunctionPermissionViewer.this.clientFactory
/* 257 */                   .getEventBus()
/* 258 */                   .fireEventFromSource((GwtEvent)new RoleEvent(RoleEvent.Action.DELETE), dto);
/*     */               }
/*     */               else {
/*     */                 
/* 262 */                 Info.display(RoleFunctionPermissionViewer.messages.message(), RoleFunctionPermissionViewer.messages.message_itemNoExisted());
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/* 267 */     box.show();
/*     */   }
/*     */   
/*     */   @UiHandler({"saveRole"})
/*     */   public void saveRole(SelectEvent event) {
/* 272 */     GWT.log("saveButton Click!");
/* 273 */     this.roleSaver.run();
/*     */   }
/*     */   
/*     */   @UiHandler({"saveButton"})
/*     */   public void saveButton(SelectEvent event) {
/* 278 */     GWT.log("saveButton Click!");
/* 279 */     final RoleDTO selectedRole = (RoleDTO)this.grid.getSelectionModel().getSelectedItem();
/* 280 */     if (selectedRole == null) {
/* 281 */       Info.display(messages.message(), messages.message_notSelectedItem());
/*     */       
/*     */       return;
/*     */     } 
/* 285 */     ConfirmMessageBox box = new ConfirmMessageBox(messages.message(), messages.message_itemSaveConfirm());
/* 286 */     box.addDialogHideHandler(new DialogHideEvent.DialogHideHandler()
/*     */         {
/*     */           public void onDialogHide(DialogHideEvent event)
/*     */           {
/* 290 */             if (event.getHideButton() == Dialog.PredefinedButton.YES && 
/* 291 */               selectedRole != null) {
/* 292 */               selectedRole.setFunctionPermissions(new HashSet());
/* 293 */               for (FunctionPermissionDTO fpDto : RoleFunctionPermissionViewer.this.treeStore.getAll()) {
/* 294 */                 Tree.CheckState checkState = RoleFunctionPermissionViewer.this.tree.getChecked(fpDto);
/* 295 */                 if (Tree.CheckState.CHECKED.equals(checkState) || Tree.CheckState.PARTIAL
/* 296 */                   .equals(checkState)) {
/* 297 */                   selectedRole.addFunctionPermission(fpDto);
/*     */                 }
/*     */               } 
/* 300 */               GWT.log("selectedRole=" + selectedRole.toString());
/* 301 */               RoleFunctionPermissionViewer.this.clientFactory
/* 302 */                 .getEventBus()
/* 303 */                 .fireEventFromSource((GwtEvent)new RoleFunctionPermissionEvent(RoleFunctionPermissionEvent.Action.SAVE), selectedRole);
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 309 */     box.show();
/*     */   }
/*     */   
/*     */   private void fillTreeStoreSelected(RoleDTO roleDto) {
/* 313 */     Set<FunctionPermissionDTO> fpDtoList = roleDto.getFunctionPermissions();
/*     */     
/* 315 */     if (fpDtoList != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 321 */       fpDtoList = (Set<FunctionPermissionDTO>)fpDtoList.stream().sorted(Comparator.comparing(FunctionPermissionDTO::getLevel).reversed()).collect(Collectors.toSet());
/*     */       
/* 323 */       Set<String> fpDtoIdList = (Set<String>)fpDtoList.stream().map(FunctionPermissionDTO::getId).collect(Collectors.toSet());
/* 324 */       for (FunctionPermissionDTO fpDto : fpDtoList) {
/* 325 */         FunctionPermissionDTO storeFpDto = (FunctionPermissionDTO)this.treeStore.findModelWithKey(fpDto.getId());
/* 326 */         if (storeFpDto != null) {
/* 327 */           if (this.tree.getCheckStyle() == Tree.CheckCascade.TRI) {
/* 328 */             tri(storeFpDto, fpDtoIdList, storeFpDto); continue;
/* 329 */           }  if (this.tree.getCheckStyle() == Tree.CheckCascade.PARENTS) {
/* 330 */             this.tree.setChecked(storeFpDto, Tree.CheckState.CHECKED);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void tri(FunctionPermissionDTO storeFpDto, Set<String> fpDtoIdList, FunctionPermissionDTO fpDto) {
/* 339 */     List<FunctionPermissionDTO> children = this.treeStore.getAllChildren(storeFpDto);
/* 340 */     if (children.size() > 0) {
/* 341 */       Tree.CheckState checkState; boolean allChecked = true;
/* 342 */       boolean allUnChecked = true;
/* 343 */       for (FunctionPermissionDTO child : children) {
/*     */         
/* 345 */         boolean contain = fpDtoIdList.contains(child.getId());
/* 346 */         allChecked = (allChecked && contain);
/* 347 */         allUnChecked = (allUnChecked && !contain);
/*     */       } 
/*     */       
/* 350 */       if (allChecked) {
/* 351 */         checkState = Tree.CheckState.CHECKED;
/* 352 */       } else if (allUnChecked) {
/* 353 */         checkState = Tree.CheckState.UNCHECKED;
/*     */       } else {
/* 355 */         checkState = Tree.CheckState.PARTIAL;
/*     */       } 
/* 357 */       this.tree.setChecked(fpDto, checkState);
/*     */     } else {
/* 359 */       GWT.log("set checked=" + fpDto.getId());
/* 360 */       this.tree.setChecked(storeFpDto, Tree.CheckState.CHECKED);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void uncheckTreeAll() {
/* 365 */     List<FunctionPermissionDTO> list = this.treeStore.getAll();
/* 366 */     for (FunctionPermissionDTO item : list) {
/* 367 */       this.tree.setChecked(item, Tree.CheckState.UNCHECKED);
/*     */     }
/*     */   }
/*     */   
/*     */   @UiHandler({"expandAll"})
/*     */   public void expandAll(SelectEvent event) {
/* 373 */     this.tree.expandAll();
/*     */   }
/*     */   
/*     */   @UiHandler({"collapseAll"})
/*     */   public void collapseAll(SelectEvent event) {
/* 378 */     this.tree.collapseAll();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onUnload() {
/* 383 */     this.roleHandlerRegistration.removeHandler();
/* 384 */     this.handlerRegistration.removeHandler();
/* 385 */     super.onUnload();
/*     */   }
/*     */   
/*     */   public void removeStore(RoleDTO dto) {
/* 389 */     RoleDTO storeDto = (RoleDTO)this.store.findModelWithKey(dto.getName());
/* 390 */     if (storeDto != null) {
/* 391 */       this.store.remove(storeDto);
/* 392 */       Info.display(messages.message(), messages.message_deleteSuccessfully());
/* 393 */       this.addRole.fireEvent((GwtEvent)new SelectEvent());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addStore(RoleDTO dto) {
/* 398 */     this.store.add(dto);
/* 399 */     Info.display(messages.message(), messages.message_addSuccessfully());
/*     */   }
/*     */   
/*     */   public void updateSotre(RoleDTO dto) {
/* 403 */     GWT.log("updateSotre dto=" + dto);
/* 404 */     RoleDTO updateDto = (RoleDTO)this.store.findModelWithKey(dto.getName());
/* 405 */     if (updateDto != null) {
/* 406 */       this.store.update(dto);
/* 407 */       Info.display(messages.message(), messages.message_saveSuccessfully());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void initRoles(List<RoleDTO> roles) {
/* 412 */     for (RoleDTO dto : roles) {
/* 413 */       if (dto.getName().equals("eac_admin")) {
/* 414 */         dto.setDescription("機房門禁管理者"); continue;
/* 415 */       }  if (dto.getName().equals("eac_user")) {
/* 416 */         dto.setDescription("機房門禁使用者");
/*     */       }
/*     */     } 
/* 419 */     this.store.clear();
/* 420 */     this.store.addAll(roles);
/*     */   }
/*     */   
/*     */   public void initFunctionPermission(List<FunctionPermissionDTO> functionPermissions) {
/* 424 */     this.treeStore.clear();
/* 425 */     functionPermissions.sort(
/* 426 */         Comparator.comparing(FunctionPermissionDTO::getLevel)
/* 427 */         .thenComparing(FunctionPermissionDTO::getSequence));
/* 428 */     GWT.log(functionPermissions.toString());
/* 429 */     for (FunctionPermissionDTO functionPermission : functionPermissions) {
/* 430 */       FunctionPermissionDTO root = (FunctionPermissionDTO)this.treeStore.findModelWithKey(functionPermission.getParentId());
/* 431 */       if (root != null) {
/* 432 */         this.treeStore.add(root, functionPermission); continue;
/*     */       } 
/* 434 */       this.treeStore.add(functionPermission);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void hideRolePanel() {
/* 440 */     this.rolePanel.hide();
/*     */   }
/*     */   
/*     */   public void setCheckStyle(Tree.CheckCascade checkCascade) {
/* 444 */     this.tree.setCheckStyle(checkCascade);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class DefaultRoleFunctionPermissionEventHandler
/*     */     implements RoleFunctionPermissionEvent.RoleFunctionPermissionEventHandler, RoleEvent.RoleEventHandler
/*     */   {
/*     */     public void onSave(RoleFunctionPermissionEvent event) {
/* 469 */       GWT.log("onSave...");
/* 470 */       RoleDTO dto = (RoleDTO)event.getSource();
/* 471 */       RoleFunctionPermissionViewer.this.presenter.saveItem(dto);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onSave(RoleEvent event) {
/* 476 */       GWT.log("onSave...");
/* 477 */       RoleDTO dto = (RoleDTO)event.getSource();
/* 478 */       RoleFunctionPermissionViewer.this.presenter.saveItem(dto);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onAdd(RoleEvent event) {
/* 483 */       GWT.log("onAdd...");
/* 484 */       RoleDTO dto = (RoleDTO)event.getSource();
/* 485 */       RoleFunctionPermissionViewer.this.presenter.addItem(dto);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onDelete(RoleEvent event) {
/* 490 */       GWT.log("onDelete...");
/* 491 */       RoleDTO dto = (RoleDTO)event.getSource();
/* 492 */       RoleFunctionPermissionViewer.this.presenter.removeItem(dto);
/*     */     }
/*     */   }
/*     */   
/*     */   static interface FunctionPermissionPropertyAccess extends PropertyAccess<FunctionPermissionDTO> {
/*     */     ModelKeyProvider<FunctionPermissionDTO> id();
/*     */     
/*     */     ValueProvider<FunctionPermissionDTO, String> name();
/*     */   }
/*     */   
/*     */   public static interface RoleProperties extends PropertyAccess<RoleDTO> {
/*     */     @Path("name")
/*     */     ModelKeyProvider<RoleDTO> key();
/*     */     
/*     */     ValueProvider<RoleDTO, String> name();
/*     */     
/*     */     ValueProvider<RoleDTO, String> description();
/*     */     
/*     */     ValueProvider<RoleDTO, Date> updateTime();
/*     */   }
/*     */   
/*     */   static interface RoleFunctionPermissionViewerUiBinder extends UiBinder<Widget, RoleFunctionPermissionViewer> {}
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\RoleFunctionPermissionViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */