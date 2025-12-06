/*    */ package com.hwacom.ngtms.ao.am.event;
/*    */ 
/*    */ import com.google.gwt.event.shared.EventHandler;
/*    */ import com.google.gwt.event.shared.GwtEvent;
/*    */ import com.google.web.bindery.event.shared.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReportGenerationEvent
/*    */   extends GwtEvent<ReportGenerationEventHandler>
/*    */ {
/* 13 */   public static final GwtEvent.Type<ReportGenerationEventHandler> TYPE = new GwtEvent.Type();
/*    */   
/*    */   private Action action;
/*    */   
/*    */   public enum Action
/*    */   {
/* 19 */     GET_REPORT_CONFIG,
/* 20 */     GET_DEVICE_CONFIG,
/*    */ 
/*    */     
/* 23 */     QUERY,
/* 24 */     EXPORT_TO_CHART,
/* 25 */     EXPORT_TO_FILE,
/* 26 */     PRINT_REPORT,
/*    */ 
/*    */     
/* 29 */     REPORT_NODE_SELECTED,
/*    */ 
/*    */     
/* 32 */     CHANGE_DEVICE_TYPE,
/*    */     
/* 34 */     PREVIEW_HTML_CHART,
/* 35 */     REPORT_PREVIEW_TOOL_BAR_ZOOM;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ReportGenerationEvent(Action action) {
/* 41 */     this.action = action;
/*    */   }
/*    */ 
/*    */   
/*    */   public GwtEvent.Type<ReportGenerationEventHandler> getAssociatedType() {
/* 46 */     return TYPE;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void dispatch(ReportGenerationEventHandler handler) {
/* 51 */     if (this.action == Action.GET_REPORT_CONFIG) {
/* 52 */       handler.onGetReportConfig(this);
/* 53 */     } else if (this.action == Action.GET_DEVICE_CONFIG) {
/* 54 */       handler.onGetDeviceConfig(this);
/* 55 */     } else if (this.action == Action.QUERY) {
/* 56 */       handler.onQuery(this);
/* 57 */     } else if (this.action == Action.EXPORT_TO_CHART) {
/* 58 */       handler.onExportToChart(this);
/* 59 */     } else if (this.action == Action.EXPORT_TO_FILE) {
/* 60 */       handler.onExportToFile(this);
/* 61 */     } else if (this.action == Action.PRINT_REPORT) {
/* 62 */       handler.onPrintReport(this);
/* 63 */     } else if (this.action == Action.REPORT_NODE_SELECTED) {
/* 64 */       handler.onReportNodeSelected(this);
/* 65 */     } else if (this.action == Action.CHANGE_DEVICE_TYPE) {
/* 66 */       handler.onChangeDeviceType(this);
/* 67 */     } else if (this.action == Action.PREVIEW_HTML_CHART) {
/* 68 */       handler.onPreviewHtmlChart(this);
/* 69 */     } else if (this.action == Action.REPORT_PREVIEW_TOOL_BAR_ZOOM) {
/* 70 */       handler.onReportPreviewToolBarZoom(this);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\event\ReportGenerationEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */