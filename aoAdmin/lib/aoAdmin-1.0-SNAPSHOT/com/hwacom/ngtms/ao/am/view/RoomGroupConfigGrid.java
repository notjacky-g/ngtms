/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomSvgGroupConifgDTO;
/*     */ import com.sencha.gxt.core.client.Style;
/*     */ import com.sencha.gxt.core.client.ValueProvider;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.data.shared.TreeStore;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*     */ import com.sencha.gxt.widget.core.client.grid.GridView;
/*     */ import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;
/*     */ import com.sencha.gxt.widget.core.client.treegrid.TreeGridView;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class RoomGroupConfigGrid
/*     */   extends TreeGrid<RoomSvgGroupConifgDTO> {
/*  20 */   private static final SvgGroupProertyAccess svgGroupProertyAccess = (SvgGroupProertyAccess)GWT.create(SvgGroupProertyAccess.class);
/*     */   
/*  22 */   static TreeStore<RoomSvgGroupConifgDTO> treeStore = new TreeStore(svgGroupProertyAccess
/*  23 */       .nodeKey());
/*     */   
/*  25 */   private static Messages messages = (Messages)GWT.create(Messages.class);
/*     */   
/*     */   private static ColumnConfig<RoomSvgGroupConifgDTO, String> nodeNameCol;
/*     */   
/*     */   private static ColumnModel<RoomSvgGroupConifgDTO> genColumnModel() {
/*  30 */     ArrayList<ColumnConfig<RoomSvgGroupConifgDTO, ?>> columnConfigList = new ArrayList<>();
/*  31 */     nodeNameCol = new ColumnConfig(new ValueProvider<RoomSvgGroupConifgDTO, String>()
/*     */         {
/*     */           
/*     */           public String getValue(RoomSvgGroupConifgDTO item)
/*     */           {
/*  36 */             String uiDisplayName = item.getNodeName();
/*  37 */             String[] roomName = item.getNodeName().split("-");
/*  38 */             if (roomName[1] == null || roomName[1].isEmpty()) {
/*  39 */               uiDisplayName = roomName[0];
/*     */             }
/*  41 */             return uiDisplayName;
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(RoomSvgGroupConifgDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/*  49 */             return "nodeName";
/*     */           }
/*     */         },  150, "群組名稱");
/*     */ 
/*     */     
/*  54 */     columnConfigList.add(nodeNameCol);
/*     */     
/*  56 */     return new ColumnModel(columnConfigList);
/*     */   }
/*     */   
/*     */   public void setGridStore(List<RoomSvgGroupConifgDTO> result) {
/*  60 */     treeStore.clear();
/*  61 */     for (RoomSvgGroupConifgDTO data : result) {
/*  62 */       treeStore.add(data);
/*  63 */       for (RoomSvgGroupConifgDTO roomData : data.getChildren()) {
/*  64 */         treeStore.add(data, roomData);
/*  65 */         if (roomData.getChildren() != null) {
/*  66 */           for (RoomSvgGroupConifgDTO deviceData : roomData.getChildren()) {
/*  67 */             treeStore.add(roomData, deviceData);
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setOneGridStore(RoomSvgGroupConifgDTO result) {
/*  75 */     for (RoomSvgGroupConifgDTO roomData : result.getChildren()) {
/*  76 */       if (treeStore.findModel(roomData) == null) {
/*  77 */         treeStore.add(result, roomData);
/*  78 */         if (roomData.getChildren() != null) {
/*  79 */           for (RoomSvgGroupConifgDTO deviceData : roomData.getChildren()) {
/*  80 */             treeStore.add(roomData, deviceData);
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*  85 */     expandAll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOneUpDateGridStore(RoomSvgGroupConifgDTO oldResult, RoomSvgGroupConifgDTO newResult, RoomSvgGroupConifgDTO parentNode) {
/*  93 */     if (treeStore.findModel(oldResult) != null) {
/*  94 */       treeStore.remove(oldResult);
/*     */     }
/*     */     
/*  97 */     if (treeStore.findModel(newResult) == null) {
/*  98 */       treeStore.add(parentNode, newResult);
/*  99 */       if (newResult.getChildren() != null) {
/* 100 */         for (RoomSvgGroupConifgDTO deviceData : newResult.getChildren()) {
/* 101 */           treeStore.add(newResult, deviceData);
/*     */         }
/*     */       }
/*     */     } 
/* 105 */     expandAll();
/*     */   } static interface SvgGroupProertyAccess extends PropertyAccess<RoomSvgGroupConifgDTO> {
/*     */     ModelKeyProvider<RoomSvgGroupConifgDTO> nodeKey(); ValueProvider<RoomSvgGroupConifgDTO, String> nodeName(); }
/*     */   public void setOneDeleteGridStore(RoomSvgGroupConifgDTO result) {
/* 109 */     if (result.getChildren() != null && result.getChildren().size() > 0) {
/* 110 */       RoomSvgGroupConifgDTO parentNode = (RoomSvgGroupConifgDTO)treeStore.findModelWithKey(result.getRoomName());
/* 111 */       for (RoomSvgGroupConifgDTO firstChild : parentNode.getChildren()) {
/* 112 */         if (firstChild.getNodeName().equals(result.getNodeName())) {
/* 113 */           parentNode.getChildren().remove(firstChild);
/* 114 */           treeStore.remove(result);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 119 */     } else if (treeStore.findModel(result) != null) {
/* 120 */       RoomSvgGroupConifgDTO parentNode = (RoomSvgGroupConifgDTO)treeStore.findModelWithKey(result.getRoomName());
/* 121 */       parentNode.getChildren().remove(result);
/* 122 */       treeStore.remove(result);
/*     */     } 
/*     */     
/* 125 */     collapseAll();
/*     */   }
/*     */   
/*     */   public RoomGroupConfigGrid() {
/* 129 */     super(treeStore, genColumnModel(), nodeNameCol);
/* 130 */     TreeGridView<RoomSvgGroupConifgDTO> gridView = new TreeGridView();
/* 131 */     gridView.setAutoFill(true);
/* 132 */     setView((GridView)gridView);
/* 133 */     expandAll();
/* 134 */     setHideHeaders(true);
/* 135 */     getSelectionModel().setSelectionMode(Style.SelectionMode.MULTI);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\RoomGroupConfigGrid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */