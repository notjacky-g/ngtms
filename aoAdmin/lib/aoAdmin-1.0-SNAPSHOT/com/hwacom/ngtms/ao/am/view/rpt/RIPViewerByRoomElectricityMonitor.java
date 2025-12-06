/*    */ package com.hwacom.ngtms.ao.am.view.rpt;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.google.gwt.uibinder.client.UiBinder;
/*    */ import com.google.gwt.user.client.ui.Widget;
/*    */ import com.hwacom.ngtms.ao.am.RptEP;
/*    */ import com.hwacom.ngtms.ao.am.view.Messages;
/*    */ import com.hwacom.ngtms.common.am.view.RIPViewer;
/*    */ import com.sencha.gxt.widget.core.client.Composite;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public class RIPViewerByRoomElectricityMonitor
/*    */   extends Composite
/*    */   implements RIPViewer
/*    */ {
/* 27 */   private static RIPViewerByCardReaderLogUiBinder uiBinder = (RIPViewerByCardReaderLogUiBinder)GWT.create(RIPViewerByCardReaderLogUiBinder.class);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 32 */   private static final Messages messages = (Messages)GWT.create(Messages.class);
/*    */ 
/*    */ 
/*    */   
/*    */   public RIPViewerByRoomElectricityMonitor() {
/* 37 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*    */     
/* 39 */     addEventHandlers();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<String, Object> getInputParameter() {
/* 49 */     Map<String, Object> map = new HashMap<>();
/* 50 */     map.put("userName", RptEP.getUserName());
/*    */     
/* 52 */     return map;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setInputParameter(Map<String, Object> inputParameter) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isValid() {
/* 64 */     return true;
/*    */   }
/*    */   
/*    */   public void clearInputParameter() {}
/*    */   
/*    */   public void setHeading(String heading) {}
/*    */   
/*    */   private void addEventHandlers() {}
/*    */   
/*    */   static interface RIPViewerByCardReaderLogUiBinder extends UiBinder<Widget, RIPViewerByRoomElectricityMonitor> {}
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\rpt\RIPViewerByRoomElectricityMonitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */