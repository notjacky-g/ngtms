package com.hwacom.ngtms.common.fm.service;

import com.hwacom.ngtms.common.shared.dto.ExportDTO;
import com.hwacom.ngtms.common.shared.dto.PreviewReportDTO;
import com.hwacom.ngtms.common.shared.dto.PreviewReportOutputDTO;
import com.hwacom.ngtms.common.shared.dto.ReportTreeDTO;
import com.hwacom.ngtms.hcce.fme.controller.fm.RemoteInterface;
import java.util.List;

public interface RptRemote extends RemoteInterface {
  List<ReportTreeDTO> retrieveReportTreeDTO(String paramString);
  
  PreviewReportOutputDTO previewReport(PreviewReportDTO paramPreviewReportDTO);
  
  String exportCsv(ExportDTO paramExportDTO);
  
  String exportFile(ExportDTO paramExportDTO);
}


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\service\RptRemote.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */