package com.hwacom.ngtms.common.fm.service;

import com.hwacom.ngtms.common.shared.dto.ExportDTO;
import com.hwacom.ngtms.common.shared.dto.PreviewReportDTO;
import com.hwacom.ngtms.common.shared.dto.PreviewReportOutputDTO;
import com.hwacom.ngtms.common.shared.dto.ReportTreeDTO;
import java.util.List;

public abstract interface ReportService
{
  public abstract List<ReportTreeDTO> retrieveReportTreeDTO(String paramString);
  
  public abstract PreviewReportOutputDTO previewReport(PreviewReportDTO paramPreviewReportDTO);
  
  public abstract String exportCsv(ExportDTO paramExportDTO);
  
  public abstract String exportFile(ExportDTO paramExportDTO);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\service\ReportService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */