/*    */ package com.hwacom.ngtms.ao.am.view.rpt;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.google.gwt.event.logical.shared.SelectionEvent;
/*    */ import com.google.gwt.event.logical.shared.SelectionHandler;
/*    */ import com.google.gwt.event.shared.GwtEvent;
/*    */ import com.google.gwt.uibinder.client.UiBinder;
/*    */ import com.google.gwt.uibinder.client.UiField;
/*    */ import com.google.gwt.user.client.ui.Widget;
/*    */ import com.hwacom.ngtms.ao.am.event.ReportGenerationEvent;
/*    */ import com.hwacom.ngtms.ao.shared.dto.ReportConfigBaseDTO;
/*    */ import com.hwacom.ngtms.ao.shared.dto.ReportConfigDetailDTO;
/*    */ import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
/*    */ import com.sencha.gxt.core.client.Style;
/*    */ import com.sencha.gxt.core.client.ValueProvider;
/*    */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*    */ import com.sencha.gxt.data.shared.PropertyAccess;
/*    */ import com.sencha.gxt.data.shared.TreeStore;
/*    */ import com.sencha.gxt.widget.core.client.Composite;
/*    */ import com.sencha.gxt.widget.core.client.tree.Tree;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReportNavigator
/*    */   extends Composite
/*    */ {
/* 32 */   private static ReportNavigatorUiBinder uiBinder = (ReportNavigatorUiBinder)GWT.create(ReportNavigatorUiBinder.class);
/*    */ 
/*    */ 
/*    */   
/* 36 */   private static ReportConfigProperties props = (ReportConfigProperties)GWT.create(ReportConfigProperties.class);
/*    */   
/* 38 */   private final ClientFactory clientFactory = (ClientFactory)GWT.create(ClientFactory.class);
/*    */   
/*    */   private List<ReportConfigBaseDTO> reportConfigBaseDTOs;
/*    */   
/*    */   @UiField(provided = true)
/*    */   TreeStore<ReportConfigBaseDTO> store;
/*    */   
/*    */   @UiField(provided = true)
/*    */   ValueProvider<ReportConfigBaseDTO, String> valueProvider;
/*    */   @UiField
/*    */   Tree<ReportConfigBaseDTO, String> tree;
/*    */   
/*    */   public ReportNavigator() {
/* 51 */     this.store = new TreeStore(props.id());
/* 52 */     this.valueProvider = props.displayName();
/* 53 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 59 */     this.tree.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
/* 60 */     this.tree.getSelectionModel()
/* 61 */       .addSelectionHandler(new SelectionHandler<ReportConfigBaseDTO>()
/*    */         {
/*    */           
/*    */           public void onSelection(SelectionEvent<ReportConfigBaseDTO> event)
/*    */           {
/* 66 */             ReportConfigBaseDTO dto = (ReportConfigBaseDTO)event.getSelectedItem();
/* 67 */             if (dto instanceof ReportConfigDetailDTO) {
/* 68 */               ReportConfigDetailDTO detailDTO = (ReportConfigDetailDTO)dto;
/* 69 */               ReportNavigator.this.clientFactory
/* 70 */                 .getEventBus()
/* 71 */                 .fireEventFromSource((GwtEvent)new ReportGenerationEvent(ReportGenerationEvent.Action.REPORT_NODE_SELECTED), detailDTO);
/*    */             } 
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public void setAndBuildTree(List<ReportConfigBaseDTO> data) {
/* 79 */     this.reportConfigBaseDTOs = data;
/* 80 */     buildTree((ReportConfigBaseDTO)null, this.reportConfigBaseDTOs);
/*    */   } public static interface ReportConfigProperties extends PropertyAccess<ReportConfigBaseDTO> {
/*    */     ModelKeyProvider<ReportConfigBaseDTO> id(); ValueProvider<ReportConfigBaseDTO, String> displayName(); }
/*    */   static interface ReportNavigatorUiBinder extends UiBinder<Widget, ReportNavigator> {}
/*    */   private void buildTree(ReportConfigBaseDTO parent, List<ReportConfigBaseDTO> reportConfigBaseDTOs) {
/* 85 */     for (ReportConfigBaseDTO node : reportConfigBaseDTOs) {
/* 86 */       if (parent == null) {
/* 87 */         this.store.add(node);
/*    */       } else {
/* 89 */         this.store.add(parent, node);
/*    */       } 
/*    */       
/* 92 */       if (node.getChildren() != null)
/* 93 */         buildTree(node, node.getChildren()); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\rpt\ReportNavigator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */