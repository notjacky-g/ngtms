/*      */ package com.hwacom.ngtms.ao.fm.service.rpt;
/*      */ 
/*      */ import com.hwacom.ngtms.ao.util.AoRptValueFormatter;
/*      */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*      */ import com.hwacom.ngtms.c.fm.service.RptEnvVar;
/*      */ import com.hwacom.ngtms.c.shared.SubSystem;
/*      */ import com.hwacom.ngtms.common.fm.model.Report;
/*      */ import com.hwacom.ngtms.common.fm.model.ReportExportFile;
/*      */ import com.hwacom.ngtms.common.fm.repository.ReportExportFileRepository;
/*      */ import com.hwacom.ngtms.common.fm.repository.ReportRepository;
/*      */ import com.hwacom.ngtms.common.rpt.ReportInquiry;
/*      */ import com.hwacom.ngtms.common.shared.PreviewData;
/*      */ import com.hwacom.ngtms.common.shared.PreviewGridData;
/*      */ import com.hwacom.ngtms.common.shared.PreviewGridHeader;
/*      */ import com.hwacom.ngtms.common.shared.PreviewGridRow;
/*      */ import com.hwacom.ngtms.common.shared.PreviewHeader;
/*      */ import com.hwacom.ngtms.common.shared.ReportFormat;
/*      */ import com.hwacom.ngtms.common.shared.ReportPreviewInfo;
/*      */ import com.hwacom.ngtms.common.shared.ReportRepositoryTreeNode;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.EOFException;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.io.Writer;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.print.PrintService;
/*      */ import javax.print.PrintServiceLookup;
/*      */ import javax.print.attribute.HashPrintServiceAttributeSet;
/*      */ import javax.print.attribute.PrintServiceAttributeSet;
/*      */ import javax.print.attribute.standard.PrinterName;
/*      */ import net.sf.jasperreports.engine.JRDataSource;
/*      */ import net.sf.jasperreports.engine.JRException;
/*      */ import net.sf.jasperreports.engine.JasperCompileManager;
/*      */ import net.sf.jasperreports.engine.JasperFillManager;
/*      */ import net.sf.jasperreports.engine.JasperPrint;
/*      */ import net.sf.jasperreports.engine.JasperReport;
/*      */ import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
/*      */ import net.sf.jasperreports.engine.export.HtmlExporter;
/*      */ import net.sf.jasperreports.engine.export.HtmlResourceHandler;
/*      */ import net.sf.jasperreports.engine.export.JRPdfExporter;
/*      */ import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
/*      */ import net.sf.jasperreports.engine.export.JRXlsExporter;
/*      */ import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
/*      */ import net.sf.jasperreports.export.ExporterConfiguration;
/*      */ import net.sf.jasperreports.export.ExporterInput;
/*      */ import net.sf.jasperreports.export.ExporterOutput;
/*      */ import net.sf.jasperreports.export.ReportExportConfiguration;
/*      */ import net.sf.jasperreports.export.SimpleExporterInput;
/*      */ import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
/*      */ import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
/*      */ import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
/*      */ import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
/*      */ import org.apache.commons.codec.binary.Base64;
/*      */ import org.apache.commons.lang.LocaleUtils;
/*      */ import org.apache.commons.lang.SerializationUtils;
/*      */ import org.apache.poi.EncryptedDocumentException;
/*      */ import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
/*      */ import org.apache.poi.ss.usermodel.Cell;
/*      */ import org.apache.poi.ss.usermodel.CellStyle;
/*      */ import org.apache.poi.ss.usermodel.Row;
/*      */ import org.apache.poi.ss.usermodel.Sheet;
/*      */ import org.apache.poi.ss.usermodel.Workbook;
/*      */ import org.apache.poi.ss.usermodel.WorkbookFactory;
/*      */ import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/*      */ import org.slf4j.Logger;
/*      */ import org.slf4j.LoggerFactory;
/*      */ import org.springframework.beans.BeansException;
/*      */ import org.springframework.beans.factory.annotation.Autowired;
/*      */ import org.springframework.context.ApplicationContext;
/*      */ import org.springframework.data.domain.Sort;
/*      */ import org.springframework.stereotype.Service;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Service
/*      */ public class RptService
/*      */ {
/*   99 */   private static Logger logger = LoggerFactory.getLogger(RptService.class);
/*      */   
/*  101 */   private final String datePattern = "yyyyMMdd";
/*  102 */   private final String datePatternWithDash = "yyyy-MM-dd";
/*  103 */   private final String dateTimePattern = "yyyy-MM-dd HH:mm:ss";
/*  104 */   private final String timePattern = "HH:mm:ss";
/*  105 */   private final long dayInMillis = 86400000L;
/*      */   @Autowired
/*      */   private ReportRepository reportRepository;
/*      */   @Autowired
/*      */   private ReportExportFileRepository reportExportFileRepository;
/*      */   @Autowired
/*      */   private ApplicationContext applicationContext;
/*      */   @Autowired
/*      */   private MessageSourceExt messageSourceExt;
/*      */   @Autowired
/*      */   private RptEnvVar rptEnvVar;
/*      */   
/*      */   public void setRptEnvVar(RptEnvVar rptEnvVar) {
/*  118 */     this.rptEnvVar = rptEnvVar;
/*      */   }
/*      */   
/*      */   public Set<String> findAllReportCategory(SubSystem module) {
/*  122 */     return this.reportRepository.findAllCategoryByModule(module.toString());
/*      */   }
/*      */   
/*      */   public Set<String> findAllReportSubCategoryByCategory(SubSystem module, String category) {
/*  126 */     return this.reportRepository.findAllSubCategoryByModuleAndCategory(module.toString(), category);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public List<ReportRepositoryTreeNode> findReportWithCategoryAndSubCategory(SubSystem module, String category, String subCategory) {
/*  132 */     List<Report> reports = this.reportRepository.findByModuleAndCategoryAndSubCategoryOrderByNameAsc(module
/*  133 */         .toString(), category, subCategory);
/*  134 */     List<ReportRepositoryTreeNode> result = new ArrayList<>();
/*  135 */     for (Report report : reports) {
/*  136 */       result.add(AoRptValueFormatter.transferRepositoryTreeNode(report));
/*      */     }
/*  138 */     return result;
/*      */   }
/*      */   
/*      */   public Set<String> findAllExportFileCategory() {
/*  142 */     return this.reportExportFileRepository.findAllCategory();
/*      */   }
/*      */   
/*      */   public Set<String> findAllExportFileSubCategoryByCategory(String category) {
/*  146 */     return this.reportExportFileRepository.findAllSubCategoryByCategory(category);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public List<ReportRepositoryTreeNode> findExportFileWithCategoryAndSubCategory(String category, String subCategory) {
/*  152 */     List<ReportExportFile> files = this.reportExportFileRepository.findByCategoryAndSubCategory(category, subCategory);
/*  153 */     List<ReportRepositoryTreeNode> result = new ArrayList<>();
/*  154 */     for (ReportExportFile file : files) {
/*  155 */       result.add(AoRptValueFormatter.transferRepositoryTreeNode(file));
/*      */     }
/*  157 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ReportPreviewInfo preview(String reportId, Map<String, Object> inputParameter, int pageNumber, float zoomRatio) throws RptFmException {
/*      */     JasperPrint print;
/*  164 */     Report report = this.reportRepository.findById(reportId).orElse(null);
/*  165 */     if (report == null) {
/*  166 */       throw new IllegalStateException("can't find Report with geiven id:'" + reportId + "'");
/*      */     }
/*      */ 
/*      */     
/*  170 */     HtmlExporter htmlExporter = new HtmlExporter();
/*  171 */     StringWriter writer = new StringWriter();
/*  172 */     SimpleHtmlReportConfiguration conf = new SimpleHtmlReportConfiguration();
/*  173 */     conf.setPageIndex(Integer.valueOf(pageNumber));
/*  174 */     conf.setZoomRatio(Float.valueOf(zoomRatio));
/*      */     try {
/*  176 */       SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(writer);
/*  177 */       output.setImageHandler(new HtmlResourceHandler()
/*      */           {
/*  179 */             Map<String, String> images = new HashMap<>();
/*      */ 
/*      */             
/*      */             public void handleResource(String id, byte[] data) {
/*  183 */               this.images.put(id, "data:image/png;base64," + Base64.encodeBase64String(data));
/*      */             }
/*      */ 
/*      */             
/*      */             public String getResourcePath(String id) {
/*  188 */               return this.images.get(id);
/*      */             }
/*      */           });
/*      */       
/*  192 */       print = generateJasperPrint(report, inputParameter, null);
/*  193 */       htmlExporter.setExporterInput((ExporterInput)new SimpleExporterInput(print));
/*  194 */       htmlExporter.setExporterOutput((ExporterOutput)output);
/*  195 */       htmlExporter.setConfiguration((ReportExportConfiguration)conf);
/*  196 */       htmlExporter.exportReport();
/*  197 */     } catch (JRException e) {
/*  198 */       logger.error("can't generate report with given id:'{}':{}", reportId, e.getMessage());
/*  199 */       throw new RptFmException(e);
/*  200 */     } catch (InterruptedException e) {
/*  201 */       throw new RptFmException(e);
/*      */     } 
/*      */     
/*  204 */     ReportPreviewInfo info = new ReportPreviewInfo();
/*  205 */     info.setReportContent(writer.toString());
/*  206 */     info.setTotalPage(print.getPages().size());
/*  207 */     info.setPreviewTime(new Date());
/*      */     
/*  209 */     return info;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private JasperPrint generateJasperPrint(Report report, Map<String, Object> inputParameter, ReportFormat format) throws JRException, InterruptedException {
/*  215 */     long start = System.currentTimeMillis();
/*      */     
/*  217 */     ByteArrayInputStream inputStream = new ByteArrayInputStream(report.getMasterReport().getBytes(StandardCharsets.UTF_8));
/*  218 */     if (Thread.interrupted()) {
/*  219 */       throw new InterruptedException();
/*      */     }
/*      */     
/*  222 */     List<Map<String, Object>> mapArray = getInquiry(report.getReportInquiryClass()).inquire(inputParameter);
/*  223 */     if (Thread.interrupted()) {
/*  224 */       throw new InterruptedException();
/*      */     }
/*  226 */     JasperReport masterReport = JasperCompileManager.compileReport(inputStream);
/*  227 */     Map<String, Object> parameters = new HashMap<>();
/*  228 */     if (report.getSubReport1Name() != null && report.getSubReport1() != null) {
/*  229 */       parameters.put(report.getSubReport1Name(), generateSubReport(report.getSubReport1()));
/*      */     }
/*  231 */     if (report.getSubReport2Name() != null && report.getSubReport2() != null) {
/*  232 */       parameters.put(report.getSubReport2Name(), generateSubReport(report.getSubReport2()));
/*      */     }
/*  234 */     if (report.getSubReport3Name() != null && report.getSubReport3() != null) {
/*  235 */       parameters.put(report.getSubReport3Name(), generateSubReport(report.getSubReport3()));
/*      */     }
/*  237 */     if (report.getSubReport4Name() != null && report.getSubReport4() != null) {
/*  238 */       parameters.put(report.getSubReport4Name(), generateSubReport(report.getSubReport4()));
/*      */     }
/*  240 */     if (report.getSubReport5Name() != null && report.getSubReport5() != null) {
/*  241 */       parameters.put(report.getSubReport5Name(), generateSubReport(report.getSubReport5()));
/*      */     }
/*  243 */     if (report.getSubReport6Name() != null && report.getSubReport6() != null) {
/*  244 */       parameters.put(report.getSubReport6Name(), generateSubReport(report.getSubReport6()));
/*      */     }
/*  246 */     if (report.getSubReport7Name() != null && report.getSubReport7() != null) {
/*  247 */       parameters.put(report.getSubReport7Name(), generateSubReport(report.getSubReport7()));
/*      */     }
/*  249 */     if (report.getSubReport8Name() != null && report.getSubReport8() != null) {
/*  250 */       parameters.put(report.getSubReport8Name(), generateSubReport(report.getSubReport8()));
/*      */     }
/*  252 */     if (report.getSubReport9Name() != null && report.getSubReport9() != null) {
/*  253 */       parameters.put(report.getSubReport9Name(), generateSubReport(report.getSubReport9()));
/*      */     }
/*  255 */     if (report.getSubReport10Name() != null && report.getSubReport10() != null) {
/*  256 */       parameters.put(report.getSubReport10Name(), generateSubReport(report.getSubReport10()));
/*      */     }
/*  258 */     if (format == ReportFormat.XLS) {
/*  259 */       parameters.put("IS_IGNORE_PAGINATION", Boolean.TRUE);
/*      */     }
/*  261 */     Locale locale = getLocale(this.messageSourceExt.getMessage("rptFm.reportLocale"));
/*  262 */     if (locale != null) {
/*  263 */       parameters.put("REPORT_LOCALE", locale);
/*      */     }
/*  265 */     if (Thread.interrupted()) {
/*  266 */       throw new InterruptedException();
/*      */     }
/*  268 */     logger.trace("generateJasperPrint took:'{}' ms", Long.valueOf(System.currentTimeMillis() - start));
/*  269 */     return JasperFillManager.fillReport(masterReport, parameters, (JRDataSource)new JRMapArrayDataSource(mapArray
/*  270 */           .toArray()));
/*      */   }
/*      */   
/*      */   private ReportInquiry getInquiry(String className) {
/*      */     try {
/*  275 */       return (ReportInquiry)this.applicationContext.getBean(Class.forName(className));
/*  276 */     } catch (BeansException e) {
/*  277 */       logger.error("can't get bean with '{}'", className, e);
/*  278 */       throw new RptFmException(e);
/*  279 */     } catch (ClassNotFoundException e) {
/*  280 */       logger.error("class '{}' not found!", className, e);
/*  281 */       throw new RptFmException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private JasperReport generateSubReport(String subReport) throws JRException, InterruptedException {
/*  287 */     if (Thread.interrupted()) {
/*  288 */       throw new InterruptedException();
/*      */     }
/*      */     
/*  291 */     ByteArrayInputStream subReportInputStream = new ByteArrayInputStream(subReport.getBytes(StandardCharsets.UTF_8));
/*  292 */     if (Thread.interrupted()) {
/*  293 */       throw new InterruptedException();
/*      */     }
/*      */     
/*  296 */     return JasperCompileManager.compileReport(subReportInputStream);
/*      */   }
/*      */   
/*      */   public List<String> retrievePrinterList() {
/*  300 */     List<String> printerList = new ArrayList<>();
/*  301 */     PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
/*  302 */     for (PrintService service : services) {
/*  303 */       printerList.add(service.getName());
/*      */     }
/*      */     
/*  306 */     return printerList;
/*      */   }
/*      */   
/*      */   public void print(String reportId, Map<String, Object> inputParameter, String printer) {
/*  310 */     if (reportId == null || reportId.isEmpty()) {
/*  311 */       throw new IllegalArgumentException("'reportId' can be neither null nor empty!");
/*      */     }
/*  313 */     Report report = this.reportRepository.findById(reportId).orElse(null);
/*  314 */     if (report == null) {
/*  315 */       throw new IllegalStateException("can't find Report with geiven id:'" + reportId + "'");
/*      */     }
/*      */     
/*      */     try {
/*  319 */       JasperPrint jasperPrint = generateJasperPrint(report, inputParameter, null);
/*  320 */       print(jasperPrint, printer);
/*  321 */     } catch (JRException e) {
/*  322 */       throw new RptFmException(e);
/*  323 */     } catch (InterruptedException ex) {
/*  324 */       throw new RptFmException("Thread interrupted!", ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void print(JasperPrint jasperPrint, String printer) throws JRException, InterruptedException {
/*  330 */     JRPrintServiceExporter exporter = new JRPrintServiceExporter();
/*  331 */     exporter.setExporterInput((ExporterInput)new SimpleExporterInput(jasperPrint));
/*  332 */     PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
/*  333 */     printServiceAttributeSet.add(new PrinterName(printer, null));
/*  334 */     SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
/*      */     
/*  336 */     configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
/*  337 */     exporter.setConfiguration((ExporterConfiguration)configuration);
/*  338 */     if (Thread.interrupted()) {
/*  339 */       throw new InterruptedException();
/*      */     }
/*  341 */     exporter.exportReport();
/*      */   }
/*      */   
/*      */   public ReportExportFile retrieveExportFile(String id) {
/*  345 */     ReportExportFile exportFile = this.reportExportFileRepository.findById(id).orElse(null);
/*  346 */     if (exportFile == null) {
/*  347 */       return null;
/*      */     }
/*      */     
/*  350 */     String directoryText = this.rptEnvVar.getExportFileDirectory();
/*  351 */     File file = new File(directoryText + exportFile.getFile());
/*  352 */     logger.debug("retrieve file path:'{}'", file.getAbsolutePath());
/*  353 */     try(FileInputStream in = new FileInputStream(file); 
/*  354 */         ByteArrayOutputStream out = new ByteArrayOutputStream()) {
/*      */       while (true) {
/*      */         try {
/*  357 */           int c; if ((c = in.read()) != -1) {
/*  358 */             out.write(c); continue;
/*      */           } 
/*  360 */           exportFile.setFileData(out.toByteArray());
/*  361 */         } catch (EOFException eOFException) {} break;
/*      */       } 
/*  363 */     } catch (FileNotFoundException e1) {
/*  364 */       logger.error("No such file:'{}'", file.getAbsolutePath());
/*  365 */     } catch (IOException e1) {
/*  366 */       logger.error("Something wrong while retrieving Export File, message:'{}'", e1);
/*      */     } 
/*  368 */     return exportFile;
/*      */   }
/*      */   
/*      */   public void removeExportFile(String id) {
/*  372 */     this.reportExportFileRepository.deleteById(id);
/*      */   } public String export(String reportId, Map<String, Object> inputParameter, ReportFormat format) {
/*      */     JasperPrint print;
/*      */     ReportExportFile exportFile;
/*  376 */     if (reportId == null || reportId.isEmpty()) {
/*  377 */       throw new IllegalArgumentException("'reportId' can be neither null nor empty!");
/*      */     }
/*  379 */     Report report = this.reportRepository.findById(reportId).orElse(null);
/*  380 */     if (report == null) {
/*  381 */       throw new IllegalStateException("can't find Report with geiven id:'" + reportId + "'");
/*      */     }
/*      */     
/*  384 */     if (ReportFormat.CSV == format) {
/*  385 */       return exportCsv(report, inputParameter);
/*      */     }
/*  387 */     if (ReportFormat.XLS_POI == format) {
/*  388 */       return exportXlsPoi(report, inputParameter);
/*      */     }
/*      */     
/*  391 */     Date start = new Date();
/*      */     
/*      */     try {
/*  394 */       print = generateJasperPrint(report, inputParameter, format);
/*  395 */     } catch (JRException ex) {
/*  396 */       throw new RptFmException("can't not get corresponding JasperPrint", ex);
/*  397 */     } catch (InterruptedException ex) {
/*  398 */       throw new RptFmException("Thread interrupted!", ex);
/*      */     } 
/*  400 */     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/*  401 */     if (format == ReportFormat.PDF) {
/*  402 */       JRPdfExporter pdfExporter = new JRPdfExporter();
/*  403 */       pdfExporter.setExporterInput((ExporterInput)new SimpleExporterInput(print));
/*  404 */       pdfExporter.setExporterOutput((ExporterOutput)new SimpleOutputStreamExporterOutput(outputStream));
/*      */       try {
/*  406 */         pdfExporter.exportReport();
/*  407 */       } catch (JRException e) {
/*  408 */         logger.info("export report:'{}' failed!", reportId, e);
/*      */       } 
/*  410 */     } else if (format == ReportFormat.XLS) {
/*  411 */       print.setProperty("net.sf.jasperreports.export.xls.detect.cell.type", "true");
/*      */       
/*  413 */       JRXlsxExporter xlsxExporter = new JRXlsxExporter();
/*  414 */       xlsxExporter.setExporterInput((ExporterInput)new SimpleExporterInput(print));
/*  415 */       xlsxExporter.setExporterOutput((ExporterOutput)new SimpleOutputStreamExporterOutput(outputStream));
/*      */       try {
/*  417 */         xlsxExporter.exportReport();
/*  418 */       } catch (JRException e) {
/*  419 */         logger.info("export report:'{}' failed!", reportId, e);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  424 */     if (format == ReportFormat.XLS) {
/*  425 */       ByteArrayOutputStream transferOut = transferNumberValueInExcelFormat(outputStream);
/*  426 */       exportFile = createReportExportFile(inputParameter, report, start, transferOut, format);
/*      */     } else {
/*  428 */       exportFile = createReportExportFile(inputParameter, report, start, outputStream, format);
/*      */     } 
/*      */     
/*  431 */     return exportFile.getId();
/*      */   }
/*      */   
/*      */   private ByteArrayOutputStream transferNumberValueInExcelFormat(ByteArrayOutputStream jasperOut) {
/*  435 */     logger.debug("Transfer Number Value In Excel Format. Jasper outputStream size:'{}' bytes, about:'{}' KBytes, '{}' MBytes", new Object[] {
/*      */           
/*  437 */           Integer.valueOf(jasperOut.size()), 
/*  438 */           Integer.valueOf(jasperOut.size() / 1024), 
/*  439 */           Integer.valueOf(jasperOut.size() / 1048576) });
/*  440 */     if (jasperOut.size() >= 10485760) {
/*      */ 
/*      */ 
/*      */       
/*  444 */       String fileName = UUID.randomUUID().toString() + ".xlsx";
/*  445 */       File file = new File(this.rptEnvVar.getAbsoluteResourcePath(), fileName);
/*  446 */       try (FileOutputStream fos = new FileOutputStream(file)) {
/*  447 */         jasperOut.writeTo(fos);
/*  448 */       } catch (IOException e) {
/*  449 */         logger.error("Pre-store large excel file fail, message:'{}'", e);
/*  450 */         return jasperOut;
/*      */       } 
/*      */ 
/*      */       
/*  454 */       try (Workbook wb = WorkbookFactory.create(file)) {
/*  455 */         return transferNumberValueFromWorkbook(wb);
/*  456 */       } catch (IOException e) {
/*  457 */         logger.error("Transfer Number Value In Excel Format. transfer File fail, message:'{}'", e);
/*  458 */         return jasperOut;
/*  459 */       } catch (InvalidFormatException e) {
/*  460 */         logger.error("Transfer Number Value In Excel Format. Invalid Format Error, message:'{}'", (Throwable)e);
/*      */         
/*  462 */         return jasperOut;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  467 */     try (Workbook wb = WorkbookFactory.create(new ByteArrayInputStream(jasperOut.toByteArray()))) {
/*  468 */       return transferNumberValueFromWorkbook(wb);
/*  469 */     } catch (IOException e) {
/*  470 */       logger.error("Transfer Number Value In Excel Format. transfer File fail, message:'{}'", e);
/*  471 */       return jasperOut;
/*  472 */     } catch (EncryptedDocumentException e) {
/*  473 */       logger.error("Transfer Number Value In Excel Format. Encrypted Document Error, message:'{}'", (Throwable)e);
/*      */       
/*  475 */       return jasperOut;
/*  476 */     } catch (InvalidFormatException e) {
/*  477 */       logger.error("Transfer Number Value In Excel Format. Invalid Format Error, message:'{}'", (Throwable)e);
/*      */       
/*  479 */       return jasperOut;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private ByteArrayOutputStream transferNumberValueFromWorkbook(Workbook wb) throws IOException {
/*  485 */     Pattern pattern = Pattern.compile("^\\d+$");
/*  486 */     List<Integer> needTransferCells = new ArrayList<>();
/*  487 */     for (int i = 0; i < wb.getNumberOfSheets(); i++) {
/*  488 */       Sheet sheet = wb.getSheetAt(i);
/*  489 */       if (sheet != null)
/*      */       {
/*      */         
/*  492 */         for (Row r : sheet) {
/*  493 */           needTransferCells.clear();
/*  494 */           for (Cell c : r) {
/*  495 */             if (1 == c.getCellType()) {
/*  496 */               String value = c.getStringCellValue();
/*  497 */               if (value == null || value.isEmpty()) {
/*      */                 continue;
/*      */               }
/*  500 */               if (!pattern.matcher(value).matches()) {
/*      */                 continue;
/*      */               }
/*  503 */               needTransferCells.add(Integer.valueOf(c.getColumnIndex()));
/*      */             } 
/*      */           } 
/*      */           
/*  507 */           for (Integer each : needTransferCells) {
/*  508 */             Cell originalCell = r.getCell(each.intValue());
/*  509 */             CellStyle style = originalCell.getCellStyle();
/*  510 */             String value = originalCell.getStringCellValue();
/*  511 */             r.removeCell(originalCell);
/*      */             
/*  513 */             Cell newCell = r.createCell(each.intValue(), 0);
/*  514 */             newCell.setCellStyle(style);
/*  515 */             newCell.setCellValue((new Double(value)).doubleValue());
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*  520 */     ByteArrayOutputStream transferOut = new ByteArrayOutputStream();
/*  521 */     wb.write(transferOut);
/*  522 */     transferOut.close();
/*  523 */     return transferOut;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String exportXlsPoi(Report report, Map<String, Object> inputParameter) {
/*  532 */     Date start = new Date();
/*  533 */     ReportInquiry inquiry = getInquiry(report.getReportInquiryClass());
/*      */     
/*  535 */     String fileName = inquiry.generatePoiFile(inputParameter, report.getSlaveReport1(), report.getSlaveReport2());
/*      */ 
/*      */     
/*  538 */     ReportExportFile exportFile = createReportExportFile(inputParameter, report, start, ReportFormat.XLS_POI, fileName);
/*      */     
/*  540 */     return exportFile.getId();
/*      */   }
/*      */   
/*      */   public String exportBulk(String reportId, Map<String, Object> inputParameter, ReportFormat format) {
/*      */     JasperPrint print;
/*  545 */     if (reportId == null || reportId.isEmpty()) {
/*  546 */       throw new IllegalArgumentException("'reportId' can be neither null nor empty!");
/*      */     }
/*  548 */     Report report = this.reportRepository.findById(reportId).orElse(null);
/*  549 */     if (report == null) {
/*  550 */       throw new IllegalStateException("can't find Report with geiven id:'" + reportId + "'");
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  555 */       print = generateJasperPrint(report, inputParameter, format);
/*  556 */     } catch (JRException ex) {
/*  557 */       throw new RptFmException("can't not get corresponding JasperPrint", ex);
/*  558 */     } catch (InterruptedException ex) {
/*  559 */       throw new RptFmException("Thread interrupted!", ex);
/*      */     } 
/*      */     
/*  562 */     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/*  563 */     if (format == ReportFormat.PDF) {
/*  564 */       JRPdfExporter pdfExporter = new JRPdfExporter();
/*  565 */       pdfExporter.setExporterInput((ExporterInput)new SimpleExporterInput(print));
/*  566 */       pdfExporter.setExporterOutput((ExporterOutput)new SimpleOutputStreamExporterOutput(outputStream));
/*      */       try {
/*  568 */         pdfExporter.exportReport();
/*  569 */       } catch (JRException e) {
/*  570 */         logger.info("export report:'{}' failed!", print.getName(), e);
/*      */       } 
/*  572 */     } else if (format == ReportFormat.XLS) {
/*  573 */       JRXlsExporter xlsExporter = new JRXlsExporter();
/*  574 */       xlsExporter.setExporterInput((ExporterInput)new SimpleExporterInput(print));
/*  575 */       xlsExporter.setExporterOutput((ExporterOutput)new SimpleOutputStreamExporterOutput(outputStream));
/*      */       try {
/*  577 */         xlsExporter.exportReport();
/*  578 */       } catch (JRException e) {
/*  579 */         logger.info("export report:'{}' failed!", print.getName(), e);
/*      */       } 
/*      */     } 
/*      */     
/*  583 */     String fileName = UUID.randomUUID().toString() + "." + format.toString().toLowerCase();
/*  584 */     File file = new File(this.rptEnvVar.getAbsoluteResourcePath(), fileName);
/*  585 */     logger.debug("create report file path:'{}'", file.getAbsolutePath());
/*  586 */     try (OutputStream out = new FileOutputStream(file)) {
/*  587 */       outputStream.writeTo(out);
/*  588 */     } catch (FileNotFoundException e) {
/*  589 */       logger.error("No such file:'{}'", file.getAbsolutePath());
/*  590 */     } catch (IOException e) {
/*  591 */       logger.error("Something wrong while retrieving Export File, message:'{}'", e);
/*      */     } 
/*      */     
/*  594 */     return this.rptEnvVar.getResourceBaseUri() + fileName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ReportExportFile createReportExportFile(Map<String, Object> inputParameter, Report report, Date start, ByteArrayOutputStream outputStream, ReportFormat format) {
/*  613 */     ReportExportFile exportFile = new ReportExportFile();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  619 */     String fileName = UUID.randomUUID().toString() + "." + ((ReportFormat.XLS == format) ? (format.toString().toLowerCase() + "x") : format.toString().toLowerCase());
/*  620 */     exportFile.setCategory(report.getCategory());
/*  621 */     exportFile.setName(report
/*  622 */         .getSubCategory() + report
/*  623 */         .getName() + "-" + (new SimpleDateFormat("yyyyMMdd"))
/*      */         
/*  625 */         .format(new Date()));
/*  626 */     exportFile.setSubCategory(report.getSubCategory());
/*  627 */     exportFile.setStart(start);
/*  628 */     exportFile.setSource("");
/*  629 */     exportFile.setFormat(format);
/*  630 */     exportFile.setFile(fileName);
/*  631 */     exportFile.setReport(report);
/*  632 */     exportFile.setInputParameter(
/*  633 */         SerializationUtils.serialize((HashMap)
/*  634 */           getStringValueInputParameter(inputParameter)));
/*      */     
/*  636 */     if (inputParameter.get("SchScheduleStart") != null) {
/*  637 */       String schScheduleStartText = (String)inputParameter.get("SchScheduleStart");
/*      */       try {
/*  639 */         Date scheduleStart = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(schScheduleStartText);
/*  640 */         exportFile.setScheduleStart(scheduleStart);
/*  641 */       } catch (ParseException e) {
/*  642 */         logger.error("Parse schScheduleStart Fail :'{}'", schScheduleStartText);
/*      */       } 
/*      */     } 
/*  645 */     this.reportExportFileRepository.save(exportFile);
/*      */ 
/*      */     
/*  648 */     String reportModule = (String)inputParameter.get("ReportModule");
/*  649 */     File file = new File(this.rptEnvVar.getExportFileDirectory() + fileName);
/*  650 */     logger.debug("create report file path:'{}'", file.getAbsolutePath());
/*  651 */     try (OutputStream out = new FileOutputStream(file)) {
/*  652 */       outputStream.writeTo(out);
/*  653 */     } catch (FileNotFoundException e) {
/*  654 */       logger.error("No such file:'{}'", file.getAbsolutePath());
/*  655 */     } catch (IOException e) {
/*  656 */       logger.error("Something wrong while retrieving Export File, message:'{}'", e);
/*      */     } 
/*  658 */     return exportFile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ReportExportFile createReportExportFile(Map<String, Object> inputParameter, Report report, Date start, ReportFormat format, String fileName) {
/*  676 */     ReportExportFile exportFile = new ReportExportFile();
/*  677 */     exportFile.setCategory(report.getCategory());
/*  678 */     exportFile.setName(report
/*  679 */         .getSubCategory() + report
/*  680 */         .getName() + "-" + (new SimpleDateFormat("yyyyMMdd"))
/*      */         
/*  682 */         .format(new Date()));
/*  683 */     exportFile.setSubCategory(report.getSubCategory());
/*  684 */     exportFile.setStart(start);
/*  685 */     exportFile.setSource("");
/*  686 */     exportFile.setFormat(format);
/*  687 */     exportFile.setFile(fileName);
/*  688 */     exportFile.setReport(report);
/*  689 */     exportFile.setInputParameter(
/*  690 */         SerializationUtils.serialize((HashMap)
/*  691 */           getStringValueInputParameter(inputParameter)));
/*  692 */     this.reportExportFileRepository.save(exportFile);
/*      */     
/*  694 */     return exportFile;
/*      */   }
/*      */   
/*      */   private Map<String, String> getStringValueInputParameter(Map<String, Object> inputParameter) {
/*  698 */     Map<String, String> stringValueInputParameter = new HashMap<>();
/*  699 */     for (Map.Entry<String, Object> e : inputParameter.entrySet()) {
/*  700 */       stringValueInputParameter.put(e
/*  701 */           .getKey(), (e.getValue() == null) ? null : e.getValue().toString());
/*      */     }
/*  703 */     return stringValueInputParameter;
/*      */   }
/*      */   
/*      */   public Map<String, PreviewHeader> retrievePreviewHeader(String reportId) {
/*  707 */     Report report = this.reportRepository.findById(reportId).orElse(null);
/*  708 */     if (report == null) {
/*  709 */       throw new IllegalStateException("can't find Report with geiven id:'" + reportId + "'");
/*      */     }
/*  711 */     return getInquiry(report.getReportInquiryClass()).retrievePreviewHeader();
/*      */   }
/*      */   
/*      */   public List<PreviewData> query(String reportId, Map<String, Object> inputParameter) {
/*  715 */     Report report = this.reportRepository.findById(reportId).orElse(null);
/*  716 */     if (report == null) {
/*  717 */       throw new IllegalStateException("can't find Report with geiven id:'" + reportId + "'");
/*      */     }
/*  719 */     return getInquiry(report.getReportInquiryClass()).inquireGridData(inputParameter);
/*      */   }
/*      */   
/*      */   public PreviewGridData queryPreviewGridData(String reportId, Map<String, Object> inputParameter) {
/*  723 */     Report report = this.reportRepository.findById(reportId).orElse(null);
/*  724 */     if (report == null) {
/*  725 */       throw new IllegalStateException("can't find Report with geiven id:'" + reportId + "'");
/*      */     }
/*  727 */     logger.info("queryPreviewGridData ReportInquiryClass:'{}', inputParameter:'{}'", report
/*      */         
/*  729 */         .getReportInquiryClass(), inputParameter);
/*      */     
/*  731 */     return getInquiry(report.getReportInquiryClass()).inquirePreviewGridData(inputParameter);
/*      */   }
/*      */   
/*      */   public List<ReportExportFile> findHolidaySchedules() {
/*  735 */     Sort sort = new Sort(new Sort.Order[] { new Sort.Order(Sort.Direction.DESC, "scheduleStart") });
/*  736 */     return this.reportExportFileRepository.findByReport_Module(SubSystem.SCH.toString(), sort);
/*      */   }
/*      */   
/*      */   public void scheduleReport(Map<String, Object> inputParameter) {
/*  740 */     Map<String, String> parameter = getStringValueInputParameter(inputParameter);
/*  741 */     String jobName = parameter.get("scheduleJobName");
/*  742 */     if (jobName == null || jobName.isEmpty()) {
/*  743 */       logger.error("Can't generate Schedule Report Export File with null or empty scheduleJobName!");
/*      */       
/*      */       return;
/*      */     } 
/*  747 */     Report report = genScheduleReport(parameter);
/*  748 */     genScheduleReportExportFile(report, parameter);
/*      */   }
/*      */   
/*      */   private Report genScheduleReport(Map<String, String> inputParameter) {
/*  752 */     Report report = findByModuleAndName(SubSystem.SCH, inputParameter.get("scheduleJobName"));
/*  753 */     if (report == null) {
/*  754 */       report = new Report();
/*  755 */       report.setModule(SubSystem.SCH.toString());
/*  756 */       report.setName(inputParameter.get("scheduleJobName"));
/*  757 */       report.setRoadMap(Boolean.FALSE);
/*  758 */       this.reportRepository.save(report);
/*      */     } 
/*      */     
/*  761 */     return report;
/*      */   }
/*      */   private void genScheduleReportExportFile(Report report, Map<String, String> inputParameter) {
/*      */     Date startDate, endDate;
/*  765 */     String startDateStr = inputParameter.get("startDate");
/*  766 */     String endDateStr = inputParameter.get("endDate");
/*  767 */     if (startDateStr == null || endDateStr == null) {
/*  768 */       logger.error("Can't generate Schedule Report Export File: got null value of startDate:'{}' or endDate:'{}'", startDateStr, endDateStr);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  775 */     SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*      */ 
/*      */     
/*      */     try {
/*  779 */       startDate = dateTimeFormat.parse(startDateStr + " 00:00:00");
/*  780 */       endDate = dateTimeFormat.parse(endDateStr + " 00:00:00");
/*  781 */     } catch (ParseException e) {
/*  782 */       logger.error("Can't parse date string, check if you have followed this format: 'yyyy-MM-dd', startDate:'{}', endDate:'{}'", startDateStr, endDateStr);
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  790 */     long timeSpan = endDate.getTime() - startDate.getTime();
/*  791 */     if (timeSpan < 0L) {
/*  792 */       logger.error("Can't generate Schedule Report Export File records: endDate is not beyond or equal to startDate! endDate:'{}', startDate:'{}'", endDate, startDate);
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  800 */     String executionTime1 = inputParameter.get("executionTime1");
/*  801 */     String executionTime2 = inputParameter.get("executionTime2");
/*  802 */     String executionTime3 = inputParameter.get("executionTime3");
/*  803 */     if (executionTime1 == null) {
/*  804 */       logger.error("Can't generate Schedule Report Export File records: executionTime1 should not be null!");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  809 */     if (!validateExecutionTime(executionTime1, executionTime2, executionTime3)) {
/*      */       return;
/*      */     }
/*  812 */     logger.info("generate Schedule Report Export File, startDate:'{}', endDate:'{}' executionTime1:'{}', executionTime2:'{}', executionTime3:'{}'", new Object[] { startDate, endDate, executionTime1, executionTime2, executionTime3 });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  821 */     String jobName = inputParameter.get("scheduleJobName");
/*  822 */     List<ReportExportFile> reportExportFiles = new ArrayList<>();
/*  823 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*  824 */     int days = Long.valueOf(timeSpan / 86400000L).intValue();
/*  825 */     for (int i = 0; i <= days; i++) {
/*  826 */       Date date = new Date(startDate.getTime() + i * 86400000L);
/*  827 */       String dateStr = dateFormat.format(date);
/*      */       try {
/*  829 */         Date scheduleStart1 = dateTimeFormat.parse(dateStr + " " + executionTime1 + ":00");
/*  830 */         String scheduleName = jobName + "_" + getAmOrPmStringOfDay(scheduleStart1);
/*  831 */         if (findByNameAndScheduleStart(scheduleName, scheduleStart1) == null) {
/*  832 */           reportExportFiles.add(
/*  833 */               createReportExportFile(scheduleName, scheduleStart1, ReportFormat.XLS, report, inputParameter));
/*      */         }
/*      */       }
/*  836 */       catch (ParseException e) {
/*  837 */         logger.error("parse scheduleStart1 fail, message:'{}'", e);
/*      */       } 
/*  839 */       if (executionTime2 != null) {
/*      */         try {
/*  841 */           Date scheduleStart2 = dateTimeFormat.parse(dateStr + " " + executionTime2 + ":00");
/*  842 */           String scheduleName = jobName + "_" + getAmOrPmStringOfDay(scheduleStart2);
/*  843 */           if (findByNameAndScheduleStart(scheduleName, scheduleStart2) == null) {
/*  844 */             reportExportFiles.add(
/*  845 */                 createReportExportFile(scheduleName, scheduleStart2, ReportFormat.XLS, report, inputParameter));
/*      */           }
/*      */         }
/*  848 */         catch (ParseException e) {
/*  849 */           logger.error("parse scheduleStart2 fail, message:'{}'", e);
/*      */           return;
/*      */         } 
/*      */       }
/*  853 */       if (executionTime3 != null) {
/*      */         try {
/*  855 */           Date scheduleStart3 = dateTimeFormat.parse(dateStr + " " + executionTime3 + ":00");
/*  856 */           String scheduleName = jobName + "_" + getAmOrPmStringOfDay(scheduleStart3);
/*  857 */           if (findByNameAndScheduleStart(scheduleName, scheduleStart3) == null) {
/*  858 */             reportExportFiles.add(
/*  859 */                 createReportExportFile(scheduleName, scheduleStart3, ReportFormat.XLS, report, inputParameter));
/*      */           }
/*      */         }
/*  862 */         catch (ParseException e) {
/*  863 */           logger.error("parse scheduleStart3 fail, message:'{}'", e);
/*      */           return;
/*      */         } 
/*      */       }
/*      */     } 
/*  868 */     this.reportExportFileRepository.saveAll(reportExportFiles);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ReportExportFile createReportExportFile(String name, Date scheduleStart, ReportFormat format, Report report, Map<String, String> inputParameter) {
/*  877 */     ReportExportFile exportFile = new ReportExportFile();
/*  878 */     exportFile.setName(name);
/*  879 */     exportFile.setScheduleStart(scheduleStart);
/*  880 */     exportFile.setFormat(format);
/*  881 */     exportFile.setReport(report);
/*  882 */     exportFile.setInputParameter(
/*  883 */         SerializationUtils.serialize((HashMap)inputParameter));
/*      */     
/*  885 */     return exportFile;
/*      */   }
/*      */   
/*      */   private String getAmOrPmStringOfDay(Date date) {
/*  889 */     if (date == null) {
/*  890 */       return null;
/*      */     }
/*  892 */     Calendar calendar = Calendar.getInstance();
/*  893 */     calendar.setTime(date);
/*  894 */     if (calendar.get(9) == 0) {
/*  895 */       return this.messageSourceExt.getMessage("rptFm.common.AM");
/*      */     }
/*  897 */     return this.messageSourceExt.getMessage("rptFm.common.PM");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean validateExecutionTime(String executionTime1Str, String executionTime2Str, String executionTime3Str) {
/*  903 */     SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
/*      */     try {
/*  905 */       timeFormat.parse(executionTime1Str + ":00");
/*  906 */       if (executionTime2Str != null) {
/*  907 */         timeFormat.parse(executionTime2Str + ":00");
/*      */       }
/*  909 */       if (executionTime3Str != null) {
/*  910 */         timeFormat.parse(executionTime3Str + ":00");
/*      */       }
/*  912 */     } catch (ParseException e) {
/*  913 */       logger.error("Can't parse executionTime string, check if you have followed this format: 'HH:mm', executionTime1:'{}', executionTime2:'{}', executionTime3:'{}'", new Object[] { executionTime1Str, executionTime2Str, executionTime3Str });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  919 */       return false;
/*      */     } 
/*  921 */     return true;
/*      */   }
/*      */   
/*      */   private ReportExportFile findByNameAndScheduleStart(String name, Date scheduleStart) {
/*  925 */     return this.reportExportFileRepository.findFirstByNameAndScheduleStart(name, scheduleStart);
/*      */   }
/*      */   
/*      */   private Report findByModuleAndName(SubSystem module, String name) {
/*  929 */     return this.reportRepository.findFirstByModuleAndName(module.toString(), name);
/*      */   }
/*      */   
/*      */   private Locale getLocale(String localeStr) {
/*      */     try {
/*  934 */       return LocaleUtils.toLocale(localeStr);
/*  935 */     } catch (IllegalArgumentException e) {
/*  936 */       logger.warn("can't parse report locale string: '{}'", localeStr, e);
/*  937 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private String exportCsv(Report report, Map<String, Object> inputParameter) {
/*  943 */     PreviewGridData previewGridData = getInquiry(report.getReportInquiryClass()).inquirePreviewGridData(inputParameter);
/*  944 */     List<PreviewGridHeader> headers = previewGridData.getHeaders();
/*  945 */     List<PreviewGridRow> rows = previewGridData.getRows();
/*      */     
/*  947 */     ByteArrayOutputStream os = new ByteArrayOutputStream();
/*  948 */     Writer writer = null;
/*      */     try {
/*  950 */       StringBuilder sb = new StringBuilder();
/*  951 */       writer = new OutputStreamWriter(os, "big5");
/*      */ 
/*      */       
/*  954 */       for (PreviewGridHeader item : headers) {
/*  955 */         String header = getCsvColumnVal(item.getHeader());
/*  956 */         if (sb.length() != 0) {
/*  957 */           sb.append(',');
/*      */         }
/*  959 */         sb.append(header);
/*      */       } 
/*  961 */       sb.append('\n');
/*  962 */       writer.write(sb.toString());
/*      */ 
/*      */       
/*  965 */       for (PreviewGridRow item : rows) {
/*  966 */         sb.setLength(0);
/*  967 */         Map<String, ?> map = item.getColumnValMap();
/*  968 */         for (int i = 0; i < headers.size(); i++) {
/*  969 */           Object obj = map.get(((PreviewGridHeader)headers.get(i)).getColumnId());
/*  970 */           obj = (obj != null) ? obj : "";
/*  971 */           String columnVal = getCsvColumnVal(String.valueOf(obj));
/*  972 */           if (i != 0) {
/*  973 */             sb.append(',');
/*      */           }
/*  975 */           sb.append(columnVal);
/*      */         } 
/*  977 */         sb.append('\n');
/*  978 */         writer.write(sb.toString());
/*      */       } 
/*  980 */       writer.flush();
/*  981 */     } catch (IOException e) {
/*  982 */       logger.warn("Export Csv failed!", e);
/*      */     } finally {
/*  984 */       if (writer != null) {
/*      */         try {
/*  986 */           writer.close();
/*  987 */         } catch (IOException e) {
/*  988 */           logger.warn("Close OutputStreamWriter failed!", e);
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  994 */     ReportExportFile exportFile = createReportExportFile(inputParameter, report, new Date(), os, ReportFormat.CSV);
/*  995 */     return exportFile.getId();
/*      */   }
/*      */   
/*      */   public String exportExcelFromOrderedList(List<List<String>> data, List<Integer> columnWidths) {
/*  999 */     String fileName = UUID.randomUUID().toString() + ".xlsx";
/* 1000 */     File file = new File(this.rptEnvVar.getAbsoluteResourcePath(), fileName);
/* 1001 */     Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
/* 1002 */     try(XSSFWorkbook null = new XSSFWorkbook(); 
/* 1003 */         FileOutputStream fos = new FileOutputStream(file)) {
/* 1004 */       Sheet sheet = xSSFWorkbook.createSheet("Sheet1");
/*      */       
/* 1006 */       if (columnWidths != null) {
/* 1007 */         int column = 0;
/* 1008 */         for (Integer width : columnWidths) {
/* 1009 */           if (width != null && width.intValue() >= 0) {
/* 1010 */             sheet.setColumnWidth(column, width.intValue() * 256);
/*      */           }
/* 1012 */           column++;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1017 */       CellStyle verticalCenter = xSSFWorkbook.createCellStyle();
/* 1018 */       verticalCenter.setVerticalAlignment((short)1);
/* 1019 */       CellStyle wrapAndVerticalCenter = xSSFWorkbook.createCellStyle();
/* 1020 */       wrapAndVerticalCenter.setWrapText(true);
/* 1021 */       wrapAndVerticalCenter.setVerticalAlignment((short)1);
/* 1022 */       int rowNum = 0;
/* 1023 */       for (List<String> rowData : data) {
/* 1024 */         Row row = sheet.createRow(rowNum++);
/* 1025 */         int colNum = 0;
/* 1026 */         for (String value : rowData) {
/* 1027 */           Cell cell = row.createCell(colNum++);
/* 1028 */           if (value == null) {
/* 1029 */             cell.setCellType(1);
/* 1030 */             cell.setCellValue("");
/* 1031 */             cell.setCellStyle(verticalCenter);
/*      */             
/*      */             continue;
/*      */           } 
/* 1035 */           if (pattern.matcher(value).matches()) {
/* 1036 */             if (isOverIntegerRange(value)) {
/* 1037 */               cell.setCellType(1);
/* 1038 */               cell.setCellValue(value);
/* 1039 */               cell.setCellStyle(verticalCenter); continue;
/*      */             } 
/* 1041 */             cell.setCellType(0);
/* 1042 */             cell.setCellValue((new Double(value)).doubleValue());
/* 1043 */             cell.setCellStyle(verticalCenter);
/*      */             continue;
/*      */           } 
/* 1046 */           cell.setCellType(1);
/* 1047 */           cell.setCellValue(value);
/* 1048 */           if (value.contains(System.lineSeparator())) {
/* 1049 */             cell.setCellStyle(wrapAndVerticalCenter); continue;
/*      */           } 
/* 1051 */           cell.setCellStyle(verticalCenter);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1057 */       xSSFWorkbook.write(fos);
/* 1058 */       return this.rptEnvVar.getResourceBaseUri() + fileName;
/* 1059 */     } catch (IOException e) {
/* 1060 */       logger.error("Export Excel From Ordered List fail, message:'{}'", e);
/* 1061 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean isOverIntegerRange(String value) {
/* 1066 */     String numStr = value.startsWith("-") ? value.substring(1) : value;
/*      */     
/* 1068 */     return (numStr.split("\\.")[0].length() > 10);
/*      */   }
/*      */   
/*      */   private String getCsvColumnVal(String text) {
/* 1072 */     if (text.indexOf('\r') != -1 || text.indexOf('\n') != -1 || text.indexOf(',') != -1) {
/* 1073 */       text = "\"" + text + "\"";
/*      */     }
/* 1075 */     return text;
/*      */   }
/*      */   
/*      */   public List<Report> findReportBySubSystem(SubSystem subSystem) {
/* 1079 */     return this.reportRepository.findByModule(subSystem.toString());
/*      */   }
/*      */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\rpt\RptService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */