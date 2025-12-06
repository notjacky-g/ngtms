package com.hwacom.ngtms.ao.fm.remote;

import com.hwacom.ngtms.c.fm.service.RptEnvVar;
import com.hwacom.ngtms.c.shared.SubSystem;
import com.hwacom.ngtms.common.fm.model.ReportExportFile;
import com.hwacom.ngtms.common.shared.PreviewData;
import com.hwacom.ngtms.common.shared.PreviewGridData;
import com.hwacom.ngtms.common.shared.PreviewHeader;
import com.hwacom.ngtms.common.shared.ReportFormat;
import com.hwacom.ngtms.common.shared.ReportPreviewInfo;
import com.hwacom.ngtms.common.shared.ReportRepositoryTreeNode;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BulkRptRemote {
  void setRptEnvVar(RptEnvVar paramRptEnvVar);
  
  Set<String> findAllReportCategory(SubSystem paramSubSystem);
  
  Set<String> findAllReportSubCategoryByCategory(SubSystem paramSubSystem, String paramString);
  
  List<ReportRepositoryTreeNode> findReportWithCategoryAndSubCategory(SubSystem paramSubSystem, String paramString1, String paramString2);
  
  Set<String> findAllExportFileCategory();
  
  Set<String> findAllExportFileSubCategoryByCategory(String paramString);
  
  List<ReportRepositoryTreeNode> findExportFileWithCategoryAndSubCategory(String paramString1, String paramString2);
  
  ReportPreviewInfo preview(String paramString, Map<String, Object> paramMap, int paramInt, float paramFloat);
  
  List<String> retrievePrinterList();
  
  void print(String paramString1, Map<String, Object> paramMap, String paramString2);
  
  ReportExportFile retrieveExportFile(String paramString);
  
  void removeExportFile(String paramString);
  
  String export(String paramString, Map<String, Object> paramMap, ReportFormat paramReportFormat);
  
  String exportBulk(String paramString, Map<String, Object> paramMap, ReportFormat paramReportFormat);
  
  Map<String, PreviewHeader> retrievePreviewHeader(String paramString);
  
  List<PreviewData> query(String paramString, Map<String, Object> paramMap);
  
  PreviewGridData queryPreviewGridData(String paramString, Map<String, Object> paramMap);
  
  List<ReportExportFile> findHolidaySchedules();
  
  void scheduleReport(Map<String, Object> paramMap);
  
  String exportExcelFromOrderedList(List<List<String>> paramList, List<Integer> paramList1);
}


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\remote\BulkRptRemote.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */