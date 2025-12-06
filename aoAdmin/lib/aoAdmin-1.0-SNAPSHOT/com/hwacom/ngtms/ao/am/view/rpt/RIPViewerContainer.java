/*    */ package com.hwacom.ngtms.ao.am.view.rpt;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.google.gwt.uibinder.client.UiBinder;
/*    */ import com.google.gwt.uibinder.client.UiField;
/*    */ import com.google.gwt.user.client.ui.Widget;
/*    */ import com.sencha.gxt.widget.core.client.Composite;
/*    */ import com.sencha.gxt.widget.core.client.container.MarginData;
/*    */ import com.sencha.gxt.widget.core.client.container.SimpleContainer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RIPViewerContainer
/*    */   extends Composite
/*    */ {
/* 20 */   private static RIPViewerContainerUiBinder uiBinder = (RIPViewerContainerUiBinder)GWT.create(RIPViewerContainerUiBinder.class);
/*    */ 
/*    */ 
/*    */   
/* 24 */   private final RIPViewerInitializer initializer = (RIPViewerInitializer)GWT.create(RIPViewerInitializer.class);
/*    */   
/*    */   private RIPViewer ripViewer;
/*    */   @UiField
/*    */   SimpleContainer ripViewerContainer;
/*    */   
/*    */   public RIPViewerContainer(String ripViewerClassName) {
/* 31 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/* 32 */     this.ripViewer = this.initializer.init(ripViewerClassName);
/* 33 */     this.ripViewerContainer.add(this.ripViewer.asWidget(), new MarginData(4, 4, 4, 4));
/*    */   }
/*    */   
/*    */   static interface RIPViewerContainerUiBinder extends UiBinder<Widget, RIPViewerContainer> {}
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\rpt\RIPViewerContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */