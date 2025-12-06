/*     */ package com.hwacom.ngtms.common.restful;
/*     */ 
/*     */ import com.hwacom.ngtms.common.fm.service.ReportDelegate;
/*     */ import com.hwacom.ngtms.common.shared.ReportFormat;
/*     */ import com.hwacom.ngtms.common.shared.dto.ExportDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewReportDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewReportOutputDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.ReportTreeDTO;
/*     */ import io.swagger.annotations.ApiOperation;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.bind.annotation.CrossOrigin;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ 
/*     */ 
/*     */ @CrossOrigin
/*     */ @RestController
/*     */ @RequestMapping({"/api/rpt"})
/*     */ public class ReportViewerRestServiceImpl
/*     */ {
/*  27 */   private static final Logger logger = LoggerFactory.getLogger(ReportViewerRestServiceImpl.class);
/*     */   @Autowired
/*     */   private ReportDelegate reportDelegate;
/*     */   
/*     */   @RequestMapping({"/retrieveReportTreeDTO/{module}"})
/*     */   public List<ReportTreeDTO> retrieveReportTreeDTO(@PathVariable("module") String module) {
/*     */     try {
/*  34 */       return this.reportDelegate.retrieveReportTreeDTO(module);
/*     */     } catch (RuntimeException ex) {
/*  36 */       logger.warn("Retrieve report tree dto failed!", ex);
/*  37 */       throw ex;
/*     */     }
/*     */   }
/*     */   
/*     */   @RequestMapping(value={"/previewReport"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public PreviewReportOutputDTO previewReport(@RequestBody PreviewReportDTO dto) {
/*     */     try {
/*  44 */       logger.info("Preview report. reportId: '{}'", dto.getReportId());
/*  45 */       return this.reportDelegate.previewReport(dto);
/*     */     } catch (RuntimeException ex) {
/*  47 */       logger.warn("Preview report failed! reportId: '{}'", dto.getReportId(), ex);
/*  48 */       throw ex;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @RequestMapping(value={"/exportCsv"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"text/plain;charset=UTF-8"})
/*     */   @ApiOperation("匯出報表 CSV")
/*     */   public String exportCsv(HttpServletRequest request, @RequestBody ExportDTO dto)
/*     */   {
/*     */     try
/*     */     {
/*  60 */       logger.debug("Export csv. reportId: '{}'", dto.getReportId());
/*  61 */       return this.reportDelegate.exportCsv(dto);
/*     */     } catch (RuntimeException ex) {
/*  63 */       logger.warn("Export report failed! reportId: '{}'", dto.getReportId(), ex); }
/*  64 */     return "";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @RequestMapping(value={"/export"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"text/plain;charset=UTF-8"})
/*     */   @ApiOperation("匯出報表")
/*     */   public String export(HttpServletRequest request, @RequestBody ExportDTO dto)
/*     */   {
/*     */     try
/*     */     {
/*  76 */       if (dto == null) {
/*  77 */         throw new IllegalArgumentException("ExportDTO is null");
/*     */       }
/*  79 */       if (dto.getReportId() == null) {
/*  80 */         throw new IllegalArgumentException("ReportId is null");
/*     */       }
/*  82 */       if (dto.getReportFormat() == null) {
/*  83 */         throw new IllegalArgumentException("ReportFormat is null");
/*     */       }
/*  85 */       logger.debug("Export pdf. reportId: '{}'", dto.getReportId());
/*     */       
/*  87 */       if (ReportFormat.CSV == dto.getReportFormat()) {
/*  88 */         return this.reportDelegate.exportCsv(dto);
/*     */       }
/*  90 */       return this.reportDelegate.exportFile(dto);
/*     */     }
/*     */     catch (RuntimeException ex) {
/*  93 */       logger.warn("Export report failed! reportId: '{}'", dto.getReportId(), ex); }
/*  94 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @RequestMapping(value={"/exportMultiType"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, produces={"text/plain;charset=UTF-8"})
/*     */   @ApiOperation("匯出報表")
/*     */   public String exportMultiType(HttpServletRequest request, @RequestBody ExportDTO dto)
/*     */   {
/*     */     try
/*     */     {
/* 106 */       if (dto == null) {
/* 107 */         throw new IllegalArgumentException("ExportDTO is null");
/*     */       }
/* 109 */       if (dto.getReportId() == null) {
/* 110 */         throw new IllegalArgumentException("ReportId is null");
/*     */       }
/* 112 */       if (dto.getReportFormat() == null) {
/* 113 */         throw new IllegalArgumentException("ReportFormat is null");
/*     */       }
/* 115 */       logger.debug("Export pdf. reportId: '{}'", dto.getReportId());
/*     */       
/* 117 */       return this.reportDelegate.exportFile(dto);
/*     */     } catch (RuntimeException ex) {
/* 119 */       logger.warn("Export report failed! reportId: '{}'", dto.getReportId(), ex); }
/* 120 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\restful\ReportViewerRestServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */