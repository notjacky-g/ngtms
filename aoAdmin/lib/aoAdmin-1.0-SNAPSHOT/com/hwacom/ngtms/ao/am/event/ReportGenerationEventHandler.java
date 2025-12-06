package com.hwacom.ngtms.ao.am.event;

import com.google.gwt.event.shared.EventHandler;

public interface ReportGenerationEventHandler extends EventHandler {
  void onGetReportConfig(ReportGenerationEvent paramReportGenerationEvent);
  
  void onGetDeviceConfig(ReportGenerationEvent paramReportGenerationEvent);
  
  void onQuery(ReportGenerationEvent paramReportGenerationEvent);
  
  void onExportToChart(ReportGenerationEvent paramReportGenerationEvent);
  
  void onExportToFile(ReportGenerationEvent paramReportGenerationEvent);
  
  void onPrintReport(ReportGenerationEvent paramReportGenerationEvent);
  
  void onReportNodeSelected(ReportGenerationEvent paramReportGenerationEvent);
  
  void onChangeDeviceType(ReportGenerationEvent paramReportGenerationEvent);
  
  void onPreviewHtmlChart(ReportGenerationEvent paramReportGenerationEvent);
  
  void onReportPreviewToolBarZoom(ReportGenerationEvent paramReportGenerationEvent);
}


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\event\ReportGenerationEventHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */