/*    */ package com.hwacom.ngtms.ao.am.view.rpt;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.google.gwt.uibinder.client.UiBinder;
/*    */ import com.google.gwt.user.client.ui.Widget;
/*    */ import com.sencha.gxt.widget.core.client.Composite;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RptViewer
/*    */   extends Composite
/*    */ {
/* 21 */   private static RptViewerUiBinder uiBinder = (RptViewerUiBinder)GWT.create(RptViewerUiBinder.class);
/*    */ 
/*    */ 
/*    */   
/*    */   public RptViewer() {
/* 26 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*    */   }
/*    */   
/*    */   static interface RptViewerUiBinder extends UiBinder<Widget, RptViewer> {}
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\rpt\RptViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */