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
/*    */ public class PrinterList
/*    */   extends Composite
/*    */ {
/* 18 */   private static PrinterListUiBinder uiBinder = (PrinterListUiBinder)GWT.create(PrinterListUiBinder.class);
/*    */   
/*    */   @UiField
/*    */   StringComboBox comboBox;
/*    */ 
/*    */   
/*    */   public PrinterList() {
/* 25 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*    */   }
/*    */   
/*    */   static interface PrinterListUiBinder extends UiBinder<Widget, PrinterList> {}
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\rpt\PrinterList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */