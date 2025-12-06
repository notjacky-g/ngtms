/*    */ package com.hwacom.ngtms.common.fm.service;
/*    */ 
/*    */ import com.hwacom.ngtms.base.rmi.RmiUtils;
/*    */ import com.hwacom.ngtms.common.shared.dto.ExportDTO;
/*    */ import com.hwacom.ngtms.common.shared.dto.PreviewReportDTO;
/*    */ import com.hwacom.ngtms.common.shared.dto.PreviewReportOutputDTO;
/*    */ import com.hwacom.ngtms.common.shared.dto.ReportTreeDTO;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.beans.factory.annotation.Value;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class ReportDelegate
/*    */ {
/*    */   @Autowired
/*    */   private ReportService reportService;
/*    */   @Value("${report.usingRptServiceRemotePolicy:false}")
/*    */   private boolean usingRptServiceRemotePolicy;
/*    */   @Value("${report.rmiUrl:rmi://localhost/rptRemote}")
/*    */   private String rmiUrl;
/*    */   
/*    */   public List<ReportTreeDTO> retrieveReportTreeDTO(String module) {
/* 31 */     if (this.usingRptServiceRemotePolicy) {
/* 32 */       RptRemote remote = (RptRemote)RmiUtils.getRemoteClient(this.rmiUrl, RptRemote.class);
/* 33 */       return remote.retrieveReportTreeDTO(module);
/*    */     } 
/* 35 */     return this.reportService.retrieveReportTreeDTO(module);
/*    */   }
/*    */ 
/*    */   
/*    */   public PreviewReportOutputDTO previewReport(PreviewReportDTO dto) {
/* 40 */     if (this.usingRptServiceRemotePolicy) {
/* 41 */       RptRemote remote = (RptRemote)RmiUtils.getRemoteClient(this.rmiUrl, RptRemote.class);
/* 42 */       return remote.previewReport(dto);
/*    */     } 
/* 44 */     return this.reportService.previewReport(dto);
/*    */   }
/*    */ 
/*    */   
/*    */   public String exportCsv(ExportDTO dto) {
/* 49 */     if (this.usingRptServiceRemotePolicy) {
/* 50 */       RptRemote remote = (RptRemote)RmiUtils.getRemoteClient(this.rmiUrl, RptRemote.class);
/* 51 */       return remote.exportCsv(dto);
/*    */     } 
/* 53 */     return this.reportService.exportCsv(dto);
/*    */   }
/*    */ 
/*    */   
/*    */   public String exportFile(ExportDTO dto) {
/* 58 */     if (this.usingRptServiceRemotePolicy) {
/* 59 */       RptRemote remote = (RptRemote)RmiUtils.getRemoteClient(this.rmiUrl, RptRemote.class);
/* 60 */       return remote.exportFile(dto);
/*    */     } 
/* 62 */     return this.reportService.exportFile(dto);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\service\ReportDelegate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */