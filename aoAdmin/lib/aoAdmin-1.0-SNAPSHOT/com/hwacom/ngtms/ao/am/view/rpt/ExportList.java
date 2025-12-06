/*    */ package com.hwacom.ngtms.ao.am.view.rpt;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.google.gwt.uibinder.client.UiBinder;
/*    */ import com.google.gwt.uibinder.client.UiField;
/*    */ import com.google.gwt.user.client.ui.Widget;
/*    */ import com.sencha.gxt.widget.core.client.Composite;
/*    */ import com.sencha.gxt.widget.core.client.form.StringComboBox;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExportList
/*    */   extends Composite
/*    */ {
/* 18 */   private static ExportListUiBinder uiBinder = (ExportListUiBinder)GWT.create(ExportListUiBinder.class);
/*    */   
/*    */   @UiField
/*    */   StringComboBox comboBox;
/*    */ 
/*    */   
/*    */   public ExportList() {
/* 25 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*    */   }
/*    */   
/*    */   static interface ExportListUiBinder extends UiBinder<Widget, ExportList> {}
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\rpt\ExportList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */