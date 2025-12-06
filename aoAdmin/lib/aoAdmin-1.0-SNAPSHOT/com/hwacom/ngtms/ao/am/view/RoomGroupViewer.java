/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.dom.client.Element;
/*     */ import com.google.gwt.dom.client.Node;
/*     */ import com.google.gwt.event.logical.shared.SelectionEvent;
/*     */ import com.google.gwt.event.logical.shared.SelectionHandler;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.uibinder.client.UiHandler;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.presenter.RoomDeviceGroupPresenter;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomSetAndUnSetConfigDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomSvgGroupConifgDTO;
/*     */ import com.hwacom.ngtms.cam.client.ui.AmTab;
/*     */ import com.sencha.gxt.core.client.dom.XElement;
/*     */ import com.sencha.gxt.data.shared.TreeStore;
/*     */ import com.sencha.gxt.dnd.core.client.DND;
/*     */ import com.sencha.gxt.dnd.core.client.DndDragMoveEvent;
/*     */ import com.sencha.gxt.dnd.core.client.DndDropEvent;
/*     */ import com.sencha.gxt.dnd.core.client.Insert;
/*     */ import com.sencha.gxt.dnd.core.client.TreeGridDragSource;
/*     */ import com.sencha.gxt.dnd.core.client.TreeGridDropTarget;
/*     */ import com.sencha.gxt.widget.core.client.Dialog;
/*     */ import com.sencha.gxt.widget.core.client.button.TextButton;
/*     */ import com.sencha.gxt.widget.core.client.button.ToolButton;
/*     */ import com.sencha.gxt.widget.core.client.event.BeforeShowContextMenuEvent;
/*     */ import com.sencha.gxt.widget.core.client.event.SelectEvent;
/*     */ import com.sencha.gxt.widget.core.client.form.TextField;
/*     */ import com.sencha.gxt.widget.core.client.info.Info;
/*     */ import com.sencha.gxt.widget.core.client.menu.Item;
/*     */ import com.sencha.gxt.widget.core.client.menu.Menu;
/*     */ import com.sencha.gxt.widget.core.client.menu.MenuItem;
/*     */ import com.sencha.gxt.widget.core.client.tree.Tree;
/*     */ import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ 
/*     */ public class RoomGroupViewer extends AmTab {
/*  43 */   private static RoomGroupViewerUiBinder uiBinder = (RoomGroupViewerUiBinder)GWT.create(RoomGroupViewerUiBinder.class);
/*     */ 
/*     */ 
/*     */   
/*  47 */   private RoomDeviceGroupPresenter presenter = new RoomDeviceGroupPresenter(this);
/*     */   
/*     */   @UiField
/*     */   RoomGroupConfigGrid roomGroupConfigGrid;
/*     */   @UiField
/*     */   RoomGroupUnsetConfigGrid roomGroupUnsetConfigGrid;
/*     */   @UiField
/*     */   ToolButton expandAll;
/*     */   @UiField
/*     */   ToolButton collapseAll;
/*  57 */   private Menu baseMenu = new Menu();
/*     */   
/*  59 */   private Menu mainMenu = new Menu();
/*     */   
/*  61 */   private MenuItem addGroupName = new MenuItem();
/*     */   
/*  63 */   private Menu subMenu = new Menu();
/*     */   
/*  65 */   private MenuItem upDateGroupName = new MenuItem();
/*     */   
/*  67 */   private MenuItem deleteGroupName = new MenuItem();
/*     */   
/*     */   private Dialog roomAddDialog;
/*     */   
/*     */   private Dialog roomUpDateDialog;
/*     */   
/*     */   private Dialog roomDelteDialog;
/*     */   
/*     */   public RoomGroupViewer() {
/*  76 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*  77 */     initDragAndDrop();
/*  78 */     initMenu();
/*  79 */     mask("讀取群組設定中");
/*     */   }
/*     */ 
/*     */   
/*     */   private void initDragAndDrop() {
/*  84 */     TreeGridDragSource<RoomSvgGroupConifgDTO> unsetTreeGridDragSource = new TreeGridDragSource(this.roomGroupUnsetConfigGrid);
/*     */     
/*  86 */     unsetTreeGridDragSource.setGroup("unset");
/*     */     
/*  88 */     TreeGridDropTarget<RoomSvgGroupConifgDTO> unsetGridToTreeGridDropTarget = new TreeGridDropTarget<RoomSvgGroupConifgDTO>(this.roomGroupConfigGrid)
/*     */       {
/*     */         protected void showFeedback(DndDragMoveEvent event)
/*     */         {
/*  92 */           XElement xElement = getElementFromEvent(event.getDragMoveEvent().getNativeEvent());
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  97 */           RoomSvgGroupConifgDTO targetParent = Optional.<Tree.TreeNode>ofNullable(RoomGroupViewer.this.roomGroupConfigGrid.findNode(Element.as((Node)xElement))).map(treeNode -> (RoomSvgGroupConifgDTO)treeNode.getModel()).orElse(null);
/*     */           
/*  99 */           List<RoomSvgGroupConifgDTO> selectedItems = RoomGroupViewer.this.roomGroupUnsetConfigGrid.getSelectionModel().getSelectedItems();
/* 100 */           if (selectedItems.size() > 0) {
/* 101 */             boolean check = ((RoomSvgGroupConifgDTO)selectedItems.get(0)).getCheck().booleanValue();
/* 102 */             if (!check || targetParent == null || targetParent
/*     */               
/* 104 */               .getCheck().booleanValue() || targetParent
/* 105 */               .getGroupName() == null || targetParent
/* 106 */               .getRoomName() != ((RoomSvgGroupConifgDTO)selectedItems.get(0)).getRoomName()) {
/* 107 */               Insert.get().hide();
/* 108 */               event.getStatusProxy().setStatus(false);
/*     */               return;
/*     */             } 
/* 111 */             selectedItems.forEach(item -> item.setGroupName(targetParent.getNodeName()));
/*     */ 
/*     */ 
/*     */             
/* 115 */             setFeedback(DND.Feedback.APPEND);
/* 116 */             super.showFeedback(event);
/*     */           } else {
/*     */             return;
/*     */           } 
/*     */         }
/*     */       };
/* 122 */     unsetGridToTreeGridDropTarget.setFeedback(DND.Feedback.APPEND);
/* 123 */     unsetGridToTreeGridDropTarget.setGroup("unset");
/* 124 */     unsetGridToTreeGridDropTarget.setAllowDropOnLeaf(true);
/* 125 */     unsetGridToTreeGridDropTarget.addDropHandler(new DndDropEvent.DndDropHandler()
/*     */         {
/*     */           public void onDrop(DndDropEvent event)
/*     */           {
/* 129 */             List<RoomSvgGroupConifgDTO> newDataList = new ArrayList<>();
/*     */             
/* 131 */             for (TreeStore.TreeNode<RoomSvgGroupConifgDTO> each : (Iterable<TreeStore.TreeNode<RoomSvgGroupConifgDTO>>)event.getData()) {
/* 132 */               GWT.log("unset onDrop dto: " + ((RoomSvgGroupConifgDTO)each.getData()).getNodeName());
/* 133 */               newDataList.add(each.getData());
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 139 */     TreeGridDragSource<RoomSvgGroupConifgDTO> treeGridDragSource = new TreeGridDragSource(this.roomGroupConfigGrid);
/*     */     
/* 141 */     treeGridDragSource.setGroup("self");
/*     */     
/* 143 */     TreeGridDropTarget<RoomSvgGroupConifgDTO> treeGridDropTarget = new TreeGridDropTarget<RoomSvgGroupConifgDTO>(this.roomGroupUnsetConfigGrid)
/*     */       {
/*     */         protected void showFeedback(DndDragMoveEvent event)
/*     */         {
/* 147 */           XElement xElement = getElementFromEvent(event.getDragMoveEvent().getNativeEvent());
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 152 */           RoomSvgGroupConifgDTO targetParent = Optional.<Tree.TreeNode>ofNullable(RoomGroupViewer.this.roomGroupUnsetConfigGrid.findNode(Element.as((Node)xElement))).map(treeNode -> (RoomSvgGroupConifgDTO)treeNode.getModel()).orElse(null);
/*     */           
/* 154 */           List<RoomSvgGroupConifgDTO> selectedItems = RoomGroupViewer.this.roomGroupConfigGrid.getSelectionModel().getSelectedItems();
/* 155 */           if (selectedItems.size() > 0) {
/* 156 */             boolean check = ((RoomSvgGroupConifgDTO)selectedItems.get(0)).getCheck().booleanValue();
/* 157 */             if (!check || targetParent == null || targetParent
/*     */               
/* 159 */               .getCheck().booleanValue() || targetParent
/* 160 */               .getRoomName() != ((RoomSvgGroupConifgDTO)selectedItems.get(0)).getRoomName()) {
/* 161 */               Insert.get().hide();
/* 162 */               event.getStatusProxy().setStatus(false);
/*     */               return;
/*     */             } 
/* 165 */             selectedItems.forEach(item -> item.setGroupName(null));
/*     */ 
/*     */ 
/*     */             
/* 169 */             setFeedback(DND.Feedback.APPEND);
/* 170 */             super.showFeedback(event);
/*     */           } else {
/*     */             return;
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 177 */     treeGridDropTarget.setFeedback(DND.Feedback.APPEND);
/* 178 */     treeGridDropTarget.setGroup("self");
/* 179 */     treeGridDropTarget.setAllowDropOnLeaf(true);
/* 180 */     treeGridDropTarget.setAllowSelfAsSource(true);
/* 181 */     treeGridDropTarget.addDropHandler(new DndDropEvent.DndDropHandler()
/*     */         {
/*     */           public void onDrop(DndDropEvent event)
/*     */           {
/* 185 */             List<RoomSvgGroupConifgDTO> newDataList = new ArrayList<>();
/*     */             
/* 187 */             for (TreeStore.TreeNode<RoomSvgGroupConifgDTO> each : (Iterable<TreeStore.TreeNode<RoomSvgGroupConifgDTO>>)event.getData()) {
/* 188 */               GWT.log("self onDrop dto: " + ((RoomSvgGroupConifgDTO)each.getData()).getNodeName());
/* 189 */               newDataList.add(each.getData());
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void initMenu() {
/* 196 */     this.addGroupName.setText("新增群組名稱");
/* 197 */     this.addGroupName.addSelectionHandler(new SelectionHandler<Item>()
/*     */         {
/*     */           
/*     */           public void onSelection(SelectionEvent<Item> event)
/*     */           {
/* 202 */             RoomGroupViewer.this.showAddDialog();
/*     */           }
/*     */         });
/*     */     
/* 206 */     this.upDateGroupName.setText("修改群組名稱");
/* 207 */     this.upDateGroupName.addSelectionHandler(new SelectionHandler<Item>()
/*     */         {
/*     */           
/*     */           public void onSelection(SelectionEvent<Item> event)
/*     */           {
/* 212 */             RoomGroupViewer.this.showUpDateDialog();
/*     */           }
/*     */         });
/*     */     
/* 216 */     this.deleteGroupName.setText("刪除群組名稱");
/* 217 */     this.deleteGroupName.addSelectionHandler(new SelectionHandler<Item>()
/*     */         {
/*     */           
/*     */           public void onSelection(SelectionEvent<Item> event)
/*     */           {
/* 222 */             RoomGroupViewer.this.showDeleteDialog();
/*     */           }
/*     */         });
/*     */     
/* 226 */     this.mainMenu.add((Widget)this.addGroupName);
/*     */     
/* 228 */     this.subMenu.add((Widget)this.upDateGroupName);
/* 229 */     this.subMenu.add((Widget)this.deleteGroupName);
/*     */     
/* 231 */     this.roomGroupConfigGrid.setContextMenu(this.baseMenu);
/*     */     
/* 233 */     this.roomGroupConfigGrid.addBeforeShowContextMenuHandler(new BeforeShowContextMenuEvent.BeforeShowContextMenuHandler()
/*     */         {
/*     */           public void onBeforeShowContextMenu(BeforeShowContextMenuEvent event)
/*     */           {
/* 237 */             RoomSvgGroupConifgDTO room = (RoomSvgGroupConifgDTO)RoomGroupViewer.this.roomGroupConfigGrid.getSelectionModel().getSelectedItem();
/* 238 */             if (room.getNodeName().equals(room.getRoomName())) {
/* 239 */               RoomGroupViewer.this.roomGroupConfigGrid.setContextMenu(RoomGroupViewer.this.mainMenu);
/* 240 */             } else if (!room.getNodeName().equals(room.getRoomName()) && room.getCheck().booleanValue() != true) {
/* 241 */               RoomGroupViewer.this.roomGroupConfigGrid.setContextMenu(RoomGroupViewer.this.subMenu);
/*     */             } else {
/* 243 */               RoomGroupViewer.this.roomGroupConfigGrid.setContextMenu(RoomGroupViewer.this.baseMenu);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void showAddDialog() {
/* 250 */     if (this.roomAddDialog == null) {
/* 251 */       initRoomAddDialog();
/*     */     }
/* 253 */     this.roomAddDialog.show();
/*     */   }
/*     */   
/*     */   private void initRoomAddDialog() {
/* 257 */     final TextField text = new TextField();
/* 258 */     this.roomAddDialog = new Dialog();
/* 259 */     this.roomAddDialog.setHeading("新增群組名稱");
/* 260 */     this.roomAddDialog.setModal(true);
/* 261 */     this.roomAddDialog.setWidth(200);
/* 262 */     this.roomAddDialog.setHeight(80);
/* 263 */     this.roomAddDialog.add((Widget)text);
/* 264 */     TextButton confirmButton = new TextButton();
/* 265 */     confirmButton.setText("確定");
/* 266 */     confirmButton.addSelectHandler(new SelectEvent.SelectHandler()
/*     */         {
/*     */           public void onSelect(SelectEvent event)
/*     */           {
/* 270 */             RoomSvgGroupConifgDTO room = (RoomSvgGroupConifgDTO)RoomGroupViewer.this.roomGroupConfigGrid.getSelectionModel().getSelectedItem();
/* 271 */             RoomSvgGroupConifgDTO newNode = new RoomSvgGroupConifgDTO();
/* 272 */             newNode.setNodeKey(room.getRoomName() + "-" + (String)text.getCurrentValue());
/* 273 */             newNode.setNodeName((String)text.getCurrentValue());
/* 274 */             newNode.setRoomName(room.getRoomName());
/* 275 */             newNode.setGroupName((String)text.getCurrentValue());
/* 276 */             newNode.setChildren(null);
/* 277 */             newNode.setCheck(Boolean.valueOf(false));
/*     */             
/* 279 */             for (RoomSvgGroupConifgDTO data : room.getChildren()) {
/* 280 */               if (!data.getNodeKey().equals(newNode.getNodeKey())) {
/*     */                 continue;
/*     */               }
/* 283 */               Info.display("信息提醒", "群組名稱重複");
/*     */               
/*     */               return;
/*     */             } 
/* 287 */             List<RoomSvgGroupConifgDTO> newNodes = room.getChildren();
/* 288 */             newNodes.add(newNode);
/* 289 */             room.setChildren(newNodes);
/* 290 */             RoomGroupViewer.this.roomGroupConfigGrid.setOneGridStore(room);
/* 291 */             RoomGroupViewer.this.roomAddDialog.hide();
/*     */           }
/*     */         });
/* 294 */     TextButton rejectButton = new TextButton();
/* 295 */     rejectButton.setText("取消");
/* 296 */     rejectButton.addSelectHandler(new SelectEvent.SelectHandler()
/*     */         {
/*     */           public void onSelect(SelectEvent event)
/*     */           {
/* 300 */             RoomGroupViewer.this.roomAddDialog.hide();
/*     */           }
/*     */         });
/* 303 */     this.roomAddDialog.getButtonBar().clear();
/* 304 */     this.roomAddDialog.getButtonBar().add((Widget)confirmButton);
/* 305 */     this.roomAddDialog.getButtonBar().add((Widget)rejectButton);
/*     */   }
/*     */   
/*     */   private void showUpDateDialog() {
/* 309 */     if (this.roomUpDateDialog == null) {
/* 310 */       initRoomUpDateDialog();
/*     */     }
/* 312 */     this.roomUpDateDialog.show();
/*     */   }
/*     */   
/*     */   private void initRoomUpDateDialog() {
/* 316 */     final TextField text = new TextField();
/* 317 */     this.roomUpDateDialog = new Dialog();
/* 318 */     this.roomUpDateDialog.setHeading("修改群組名稱");
/* 319 */     this.roomUpDateDialog.setModal(true);
/* 320 */     this.roomUpDateDialog.setWidth(200);
/* 321 */     this.roomUpDateDialog.setHeight(80);
/* 322 */     this.roomUpDateDialog.add((Widget)text);
/* 323 */     TextButton confirmButton = new TextButton();
/* 324 */     confirmButton.setText("確定");
/* 325 */     confirmButton.addSelectHandler(new SelectEvent.SelectHandler()
/*     */         {
/*     */           
/*     */           public void onSelect(SelectEvent event)
/*     */           {
/* 330 */             RoomSvgGroupConifgDTO groupName = (RoomSvgGroupConifgDTO)RoomGroupViewer.this.roomGroupConfigGrid.getSelectionModel().getSelectedItem();
/*     */             
/* 332 */             String renameGroup = (String)text.getCurrentValue();
/* 333 */             if (renameGroup != null && !renameGroup.isEmpty()) {
/*     */               
/* 335 */               RoomSvgGroupConifgDTO parentNode = (RoomSvgGroupConifgDTO)RoomGroupViewer.this.roomGroupConfigGrid.getTreeStore().getParent(groupName);
/*     */               
/* 337 */               for (RoomSvgGroupConifgDTO data : parentNode.getChildren()) {
/* 338 */                 if (!data.getNodeName().equals(renameGroup)) {
/*     */                   continue;
/*     */                 }
/* 341 */                 Info.display("信息提醒", "群組名稱重複");
/*     */                 
/*     */                 return;
/*     */               } 
/*     */               
/* 346 */               List<RoomSvgGroupConifgDTO> datas = RoomGroupViewer.this.roomGroupConfigGrid.getTreeStore().getAllChildren(groupName);
/* 347 */               for (RoomSvgGroupConifgDTO data : datas) {
/* 348 */                 data.setGroupName(renameGroup);
/*     */               }
/* 350 */               RoomSvgGroupConifgDTO newNode = new RoomSvgGroupConifgDTO();
/* 351 */               newNode.setNodeKey(groupName.getRoomName() + "-" + renameGroup);
/* 352 */               newNode.setNodeName(renameGroup);
/* 353 */               newNode.setRoomName(groupName.getRoomName());
/* 354 */               newNode.setGroupName(renameGroup);
/* 355 */               newNode.setChildren(datas);
/* 356 */               newNode.setCheck(Boolean.valueOf(false));
/* 357 */               RoomGroupViewer.this.roomGroupConfigGrid.setOneUpDateGridStore(groupName, newNode, parentNode);
/*     */             } else {
/* 359 */               Info.display("信息提示", "請輸入群組名稱");
/*     */               return;
/*     */             } 
/* 362 */             RoomGroupViewer.this.roomUpDateDialog.hide();
/*     */           }
/*     */         });
/* 365 */     TextButton rejectButton = new TextButton();
/* 366 */     rejectButton.setText("取消");
/* 367 */     rejectButton.addSelectHandler(new SelectEvent.SelectHandler()
/*     */         {
/*     */           public void onSelect(SelectEvent event)
/*     */           {
/* 371 */             RoomGroupViewer.this.roomUpDateDialog.hide();
/*     */           }
/*     */         });
/* 374 */     this.roomUpDateDialog.getButtonBar().clear();
/* 375 */     this.roomUpDateDialog.getButtonBar().add((Widget)confirmButton);
/* 376 */     this.roomUpDateDialog.getButtonBar().add((Widget)rejectButton);
/*     */   }
/*     */   
/*     */   private void showDeleteDialog() {
/* 380 */     if (this.roomDelteDialog == null) {
/* 381 */       initRoomDeleteDialog();
/*     */     }
/* 383 */     this.roomDelteDialog.show();
/*     */   }
/*     */   
/*     */   private void initRoomDeleteDialog() {
/* 387 */     this.roomDelteDialog = new Dialog();
/* 388 */     this.roomDelteDialog.setHeading("刪除群組名稱");
/* 389 */     this.roomDelteDialog.setModal(true);
/* 390 */     this.roomDelteDialog.setWidth(200);
/* 391 */     this.roomDelteDialog.setHeight(80);
/* 392 */     TextButton confirmButton = new TextButton();
/* 393 */     confirmButton.setText("確定");
/* 394 */     confirmButton.addSelectHandler(new SelectEvent.SelectHandler()
/*     */         {
/*     */           
/*     */           public void onSelect(SelectEvent event)
/*     */           {
/* 399 */             RoomSvgGroupConifgDTO deleteName = (RoomSvgGroupConifgDTO)RoomGroupViewer.this.roomGroupConfigGrid.getSelectionModel().getSelectedItem();
/*     */             
/* 401 */             if (deleteName.getChildren() != null && deleteName.getChildren().size() > 0) {
/*     */               
/* 403 */               List<RoomSvgGroupConifgDTO> childs = deleteName.getChildren();
/*     */ 
/*     */ 
/*     */               
/* 407 */               RoomSvgGroupConifgDTO keepChild = (RoomSvgGroupConifgDTO)RoomGroupViewer.this.roomGroupUnsetConfigGrid.getTreeStore().findModelWithKey(deleteName.getRoomName());
/* 408 */               if (keepChild != null) {
/* 409 */                 RoomGroupViewer.this.roomGroupUnsetConfigGrid.setOneGridStore(keepChild, childs);
/*     */               }
/* 411 */               RoomGroupViewer.this.roomGroupConfigGrid.setOneDeleteGridStore(deleteName);
/*     */             } else {
/*     */               
/* 414 */               RoomSvgGroupConifgDTO parentNode = (RoomSvgGroupConifgDTO)RoomGroupViewer.this.roomGroupConfigGrid.getTreeStore().getParent(deleteName);
/* 415 */               List<RoomSvgGroupConifgDTO> newNodes = parentNode.getChildren();
/* 416 */               for (RoomSvgGroupConifgDTO node : newNodes) {
/* 417 */                 if (node.getNodeName().equals(deleteName.getNodeName())) {
/* 418 */                   RoomGroupViewer.this.roomGroupConfigGrid.setOneDeleteGridStore(node);
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             } 
/* 423 */             RoomGroupViewer.this.roomDelteDialog.hide();
/*     */           }
/*     */         });
/* 426 */     TextButton rejectButton = new TextButton();
/* 427 */     rejectButton.setText("取消");
/* 428 */     rejectButton.addSelectHandler(new SelectEvent.SelectHandler()
/*     */         {
/*     */           public void onSelect(SelectEvent event)
/*     */           {
/* 432 */             RoomGroupViewer.this.roomDelteDialog.hide();
/*     */           }
/*     */         });
/* 435 */     this.roomDelteDialog.getButtonBar().clear();
/* 436 */     this.roomDelteDialog.getButtonBar().add((Widget)confirmButton);
/* 437 */     this.roomDelteDialog.getButtonBar().add((Widget)rejectButton);
/*     */   }
/*     */ 
/*     */   
/*     */   @UiHandler({"saveButton"})
/*     */   void onSave(SelectEvent e) {
/* 443 */     List<RoomSvgGroupConifgDTO> saveDatas = new ArrayList<>();
/* 444 */     for (RoomSvgGroupConifgDTO dto : this.roomGroupConfigGrid.getTreeStore().getAll()) {
/* 445 */       if (dto.getCheck().booleanValue() && dto.getGroupName() != null && dto.getRoomName() != null) {
/* 446 */         saveDatas.add(dto);
/*     */       }
/*     */     } 
/* 449 */     this.presenter.saveRoomGroupDeviceData(saveDatas);
/*     */   }
/*     */   
/*     */   @UiHandler({"expandAll"})
/*     */   void onExpandAll(SelectEvent e) {
/* 454 */     this.roomGroupConfigGrid.expandAll();
/*     */   }
/*     */   
/*     */   @UiHandler({"collapseAll"})
/*     */   void onCollapseAll(SelectEvent e) {
/* 459 */     this.roomGroupConfigGrid.collapseAll();
/*     */   }
/*     */   
/*     */   @UiHandler({"expandAllunset"})
/*     */   void onExpandAllunset(SelectEvent e) {
/* 464 */     this.roomGroupUnsetConfigGrid.expandAll();
/*     */   }
/*     */   
/*     */   @UiHandler({"collapseAllunset"})
/*     */   void onCollapseAllunset(SelectEvent e) {
/* 469 */     this.roomGroupUnsetConfigGrid.collapseAll();
/*     */   }
/*     */   
/*     */   public void setPresenter(RoomDeviceGroupPresenter presenter) {
/* 473 */     this.presenter = presenter;
/*     */   }
/*     */   
/*     */   public void fillRoomGroupDeviceData(RoomSetAndUnSetConfigDTO response) {
/* 477 */     List<RoomSvgGroupConifgDTO> setGroupConfigList = response.getSetGroupConfigList();
/*     */     
/* 479 */     Collections.sort(setGroupConfigList, new Comparator<RoomSvgGroupConifgDTO>()
/*     */         {
/*     */           
/*     */           public int compare(RoomSvgGroupConifgDTO o1, RoomSvgGroupConifgDTO o2)
/*     */           {
/* 484 */             return o1.getRoomName().compareTo(o2.getRoomName());
/*     */           }
/*     */         });
/*     */     
/* 488 */     for (RoomSvgGroupConifgDTO dto : setGroupConfigList) {
/* 489 */       if (dto.getChildren() != null && dto.getChildren().size() > 0) {
/* 490 */         Collections.sort(dto
/* 491 */             .getChildren(), new Comparator<RoomSvgGroupConifgDTO>()
/*     */             {
/*     */               public int compare(RoomSvgGroupConifgDTO o1, RoomSvgGroupConifgDTO o2)
/*     */               {
/* 495 */                 return o1.getNodeName().compareTo(o2.getNodeName());
/*     */               }
/*     */             });
/* 498 */         for (RoomSvgGroupConifgDTO child : dto.getChildren()) {
/* 499 */           if (child.getChildren() != null && child.getChildren().size() > 0) {
/* 500 */             Collections.sort(child
/* 501 */                 .getChildren(), new Comparator<RoomSvgGroupConifgDTO>()
/*     */                 {
/*     */                   public int compare(RoomSvgGroupConifgDTO o1, RoomSvgGroupConifgDTO o2)
/*     */                   {
/* 505 */                     return o1.getNodeName().compareTo(o2.getNodeName());
/*     */                   }
/*     */                 });
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 512 */     this.roomGroupConfigGrid.setGridStore(setGroupConfigList);
/* 513 */     List<RoomSvgGroupConifgDTO> unsetGroupConfigList = response.getUnsetGroupConfigList();
/*     */     
/* 515 */     Collections.sort(unsetGroupConfigList, new Comparator<RoomSvgGroupConifgDTO>()
/*     */         {
/*     */           
/*     */           public int compare(RoomSvgGroupConifgDTO o1, RoomSvgGroupConifgDTO o2)
/*     */           {
/* 520 */             return o1.getRoomName().compareTo(o2.getRoomName());
/*     */           }
/*     */         });
/*     */     
/* 524 */     for (RoomSvgGroupConifgDTO dto : unsetGroupConfigList) {
/* 525 */       if (dto.getChildren() != null && dto.getChildren().size() > 0) {
/* 526 */         Collections.sort(dto
/* 527 */             .getChildren(), new Comparator<RoomSvgGroupConifgDTO>()
/*     */             {
/*     */               public int compare(RoomSvgGroupConifgDTO o1, RoomSvgGroupConifgDTO o2)
/*     */               {
/* 531 */                 return o1.getNodeName().compareTo(o2.getNodeName());
/*     */               }
/*     */             });
/*     */       }
/*     */     } 
/* 536 */     this.roomGroupUnsetConfigGrid.setGridStore(unsetGroupConfigList);
/* 537 */     unmask();
/*     */   }
/*     */   
/*     */   public void refresh() {
/* 541 */     mask("資料更新中");
/* 542 */     this.presenter.getRoomGroupDeviceData();
/*     */   }
/*     */   
/*     */   static interface RoomGroupViewerUiBinder extends UiBinder<Widget, RoomGroupViewer> {}
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\RoomGroupViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */