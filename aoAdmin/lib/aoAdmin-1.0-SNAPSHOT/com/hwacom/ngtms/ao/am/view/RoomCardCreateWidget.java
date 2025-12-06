/*    */ package com.hwacom.ngtms.ao.am.view;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.google.gwt.uibinder.client.UiBinder;
/*    */ import com.google.gwt.uibinder.client.UiField;
/*    */ import com.google.gwt.user.client.ui.Widget;
/*    */ import com.sencha.gxt.widget.core.client.Composite;
/*    */ import com.sencha.gxt.widget.core.client.form.TextField;
/*    */ 
/*    */ public class RoomCardCreateWidget
/*    */   extends Composite
/*    */ {
/* 13 */   private static RoomCardCreateWidgetUiBinder uiBinder = (RoomCardCreateWidgetUiBinder)GWT.create(RoomCardCreateWidgetUiBinder.class);
/*    */   
/*    */   @UiField
/*    */   TextField aba;
/*    */ 
/*    */   
/*    */   public RoomCardCreateWidget() {
/* 20 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*    */   }
/*    */   
/*    */   static interface RoomCardCreateWidgetUiBinder extends UiBinder<Widget, RoomCardCreateWidget> {}
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\RoomCardCreateWidget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */