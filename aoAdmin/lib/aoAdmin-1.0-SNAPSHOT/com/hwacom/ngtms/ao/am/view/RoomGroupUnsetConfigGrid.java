/*    */ package com.hwacom.ngtms.ao.am.view;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.hwacom.ngtms.ao.shared.dto.RoomSvgGroupConifgDTO;
/*    */ import com.sencha.gxt.core.client.Style;
/*    */ import com.sencha.gxt.core.client.ValueProvider;
/*    */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*    */ import com.sencha.gxt.data.shared.PropertyAccess;
/*    */ import com.sencha.gxt.data.shared.TreeStore;
/*    */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*    */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*    */ import com.sencha.gxt.widget.core.client.grid.GridView;
/*    */ import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;
/*    */ import com.sencha.gxt.widget.core.client.treegrid.TreeGridView;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class RoomGroupUnsetConfigGrid
/*    */   extends TreeGrid<RoomSvgGroupConifgDTO> {
/* 20 */   private static final SvgGroupProertyAccess svgGroupProertyAccess = (SvgGroupProertyAccess)GWT.create(SvgGroupProertyAccess.class);
/*    */   
/* 22 */   static TreeStore<RoomSvgGroupConifgDTO> treeStore = new TreeStore(svgGroupProertyAccess
/* 23 */       .nodeKey());
/*    */   
/* 25 */   private static Messages messages = (Messages)GWT.create(Messages.class);
/*    */   
/*    */   private static ColumnConfig<RoomSvgGroupConifgDTO, String> nodeNameCol;
/*    */   
/*    */   private static ColumnModel<RoomSvgGroupConifgDTO> genColumnModel() {
/* 30 */     ArrayList<ColumnConfig<RoomSvgGroupConifgDTO, ?>> columnConfigList = new ArrayList<>();
/* 31 */     nodeNameCol = new ColumnConfig(new ValueProvider<RoomSvgGroupConifgDTO, String>()
/*    */         {
/*    */           
/*    */           public String getValue(RoomSvgGroupConifgDTO item)
/*    */           {
/* 36 */             String uiDisplayName = item.getNodeName();
/* 37 */             String[] roomName = item.getNodeName().split("-");
/* 38 */             if (roomName[1] == null || roomName[1].isEmpty()) {
/* 39 */               uiDisplayName = roomName[0];
/*    */             }
/* 41 */             return uiDisplayName;
/*    */           }
/*    */ 
/*    */           
/*    */           public void setValue(RoomSvgGroupConifgDTO object, String value) {}
/*    */ 
/*    */           
/*    */           public String getPath() {
/* 49 */             return "nodeName";
/*    */           }
/*    */         },  150, "群組名稱");
/*    */ 
/*    */     
/* 54 */     columnConfigList.add(nodeNameCol);
/*    */     
/* 56 */     return new ColumnModel(columnConfigList);
/*    */   }
/*    */   
/*    */   public RoomGroupUnsetConfigGrid() {
/* 60 */     super(treeStore, genColumnModel(), nodeNameCol);
/* 61 */     TreeGridView<RoomSvgGroupConifgDTO> gridView = new TreeGridView();
/* 62 */     gridView.setAutoFill(true);
/* 63 */     setView((GridView)gridView);
/* 64 */     expandAll();
/* 65 */     setHideHeaders(true);
/* 66 */     getSelectionModel().setSelectionMode(Style.SelectionMode.MULTI);
/*    */   } static interface SvgGroupProertyAccess extends PropertyAccess<RoomSvgGroupConifgDTO> {
/*    */     ModelKeyProvider<RoomSvgGroupConifgDTO> nodeKey(); ValueProvider<RoomSvgGroupConifgDTO, String> nodeName(); }
/*    */   public void setOneGridStore(RoomSvgGroupConifgDTO result, List<RoomSvgGroupConifgDTO> childs) {
/* 70 */     for (RoomSvgGroupConifgDTO roomData : childs) {
/* 71 */       if (treeStore.findModel(roomData) == null) {
/* 72 */         roomData.setGroupName(null);
/* 73 */         treeStore.add(result, roomData);
/*    */       } 
/*    */     } 
/* 76 */     collapseAll();
/* 77 */     expandAll();
/*    */   }
/*    */   
/*    */   public void setGridStore(List<RoomSvgGroupConifgDTO> result) {
/* 81 */     treeStore.clear();
/* 82 */     buildTree((RoomSvgGroupConifgDTO)null, result);
/*    */   }
/*    */   
/*    */   private void buildTree(RoomSvgGroupConifgDTO parent, List<RoomSvgGroupConifgDTO> data) {
/* 86 */     for (RoomSvgGroupConifgDTO item : data) {
/* 87 */       if (parent == null) {
/* 88 */         treeStore.add(item);
/*    */       } else {
/* 90 */         treeStore.add(parent, item);
/*    */       } 
/* 92 */       if (item.getChildren() != null)
/* 93 */         buildTree(item, item.getChildren()); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\RoomGroupUnsetConfigGrid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */