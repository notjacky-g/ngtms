/*     */ package com.hwacom.ngtms.common.fm.service;
/*     */ 
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*     */ import com.hwacom.ngtms.common.fm.model.Report;
/*     */ import com.hwacom.ngtms.common.fm.repository.ReportRepository;
/*     */ import com.hwacom.ngtms.common.rpt.ReportInquiry;
/*     */ import com.hwacom.ngtms.common.shared.ReportFormat;
/*     */ import com.hwacom.ngtms.common.shared.dto.ExportDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewReportDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewReportOutputDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.ReportTreeDTO;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringWriter;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.sf.jasperreports.engine.DefaultJasperReportsContext;
/*     */ import net.sf.jasperreports.engine.JRDataSource;
/*     */ import net.sf.jasperreports.engine.JRException;
/*     */ import net.sf.jasperreports.engine.JasperCompileManager;
/*     */ import net.sf.jasperreports.engine.JasperFillManager;
/*     */ import net.sf.jasperreports.engine.JasperPrint;
/*     */ import net.sf.jasperreports.engine.JasperPrintManager;
/*     */ import net.sf.jasperreports.engine.JasperReport;
/*     */ import net.sf.jasperreports.engine.JasperReportsContext;
/*     */ import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
/*     */ import net.sf.jasperreports.engine.export.HtmlExporter;
/*     */ import net.sf.jasperreports.engine.export.HtmlResourceHandler;
/*     */ import net.sf.jasperreports.engine.export.JRCsvExporter;
/*     */ import net.sf.jasperreports.engine.export.JRPdfExporter;
/*     */ import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
/*     */ import net.sf.jasperreports.export.ExporterConfiguration;
/*     */ import net.sf.jasperreports.export.ExporterInput;
/*     */ import net.sf.jasperreports.export.ExporterOutput;
/*     */ import net.sf.jasperreports.export.ReportExportConfiguration;
/*     */ import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
/*     */ import net.sf.jasperreports.export.SimpleExporterInput;
/*     */ import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
/*     */ import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
/*     */ import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
/*     */ import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
/*     */ import net.sf.jasperreports.export.SimpleWriterExporterOutput;
/*     */ import org.apache.commons.codec.binary.Base64;
/*     */ import org.apache.poi.EncryptedDocumentException;
/*     */ import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
/*     */ import org.apache.poi.ss.usermodel.Cell;
/*     */ import org.apache.poi.ss.usermodel.CellStyle;
/*     */ import org.apache.poi.ss.usermodel.Row;
/*     */ import org.apache.poi.ss.usermodel.Sheet;
/*     */ import org.apache.poi.ss.usermodel.Workbook;
/*     */ import org.apache.poi.ss.usermodel.WorkbookFactory;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.BeansException;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service
/*     */ public class RptRemoteImpl
/*     */   implements RptRemote {
/*  80 */   private static final Logger logger = LoggerFactory.getLogger(RptRemoteImpl.class);
/*     */   
/*     */   @Value("${report.exportDirection:/report/}")
/*     */   private String exportDirectory;
/*     */   
/*     */   @Value("${report.exportSchDirection:/report/sch/}")
/*     */   private String exportSchDirectory;
/*     */   
/*     */   @Value("${report.exportUrl:http://localhost/report/}")
/*     */   private String exportUrl;
/*     */   @Autowired
/*     */   private ReportRepository reportRepository;
/*     */   @Autowired
/*     */   private ApplicationContext applicationContext;
/*     */   
/*     */   public List<ReportTreeDTO> retrieveReportTreeDTO(String module) {
/*  96 */     List<Report> reports = this.reportRepository.findByModule(module);
/*  97 */     Set<ReportTreeDTO> resultSet = new HashSet<>();
/*     */     
/*  99 */     for (Report report : reports) {
/* 100 */       ReportTreeDTO root = null;
/* 101 */       if (report.getSubCategory() != null && report.getCategory() != null) {
/* 102 */         root = generateCategoryReportTreeDTO(module, report.getCategory());
/* 103 */       } else if (report.getSubCategory() != null || report.getCategory() != null) {
/*     */ 
/*     */         
/* 106 */         root = generateSubCategoryReportTreeDTO(module, report.getCategory(), report.getSubCategory());
/*     */       } else {
/*     */         
/* 109 */         root = generateReportTreeDTO(report);
/*     */       } 
/* 111 */       resultSet.add(root);
/*     */     } 
/* 113 */     return new ArrayList<>(resultSet);
/*     */   }
/*     */   
/*     */   public List<ReportTreeDTO> retrieveReportTreeDTOWithCategory(String module) {
/* 117 */     Set<String> categories = this.reportRepository.findAllCategoryByModule(module);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     List<ReportTreeDTO> result = (List<ReportTreeDTO>)categories.stream().map(category -> { ReportTreeDTO dto = new ReportTreeDTO(); dto.setId(category); dto.setName(category); dto.setChildren(generateSubcategoryReportTreeDTO(paramString1, category)); return dto; }).collect(Collectors.toList());
/* 130 */     return result;
/*     */   }
/*     */   
/*     */   private ReportTreeDTO generateCategoryReportTreeDTO(String module, String category) {
/* 134 */     ReportTreeDTO dto = new ReportTreeDTO();
/* 135 */     dto.setId(category);
/* 136 */     dto.setName(category);
/* 137 */     dto.setChildren(generateSubcategoryReportTreeDTO(module, category));
/* 138 */     return dto;
/*     */   }
/*     */ 
/*     */   
/*     */   private ReportTreeDTO generateSubCategoryReportTreeDTO(String module, String category, String subCategory) {
/* 143 */     ReportTreeDTO dto = new ReportTreeDTO();
/* 144 */     dto.setId(category + "_" + subCategory);
/* 145 */     dto.setName(subCategory);
/* 146 */     dto.setChildren(generateReportTreeDTO(module, category, subCategory));
/* 147 */     return dto;
/*     */   }
/*     */   
/*     */   private ReportTreeDTO generateReportTreeDTO(Report report) {
/* 151 */     ReportTreeDTO dto = new ReportTreeDTO();
/* 152 */     dto.setId(report.getId());
/* 153 */     dto.setName(report.getName());
/* 154 */     dto.setRipViewerClass(report.getRipViewerClass());
/* 155 */     return dto;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<ReportTreeDTO> generateSubcategoryReportTreeDTO(String module, String category) {
/* 160 */     Set<String> subcategories = this.reportRepository.findAllSubCategoryByModuleAndCategory(module, category);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 172 */     List<ReportTreeDTO> result = (List<ReportTreeDTO>)subcategories.stream().map(subcategory -> { ReportTreeDTO dto = new ReportTreeDTO(); dto.setId(paramString1 + "_" + subcategory); dto.setName(subcategory); dto.setChildren(generateReportTreeDTO(paramString2, paramString1, subcategory)); return dto; }).collect(Collectors.toList());
/* 173 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<ReportTreeDTO> generateReportTreeDTO(String module, String category, String subcategory) {
/* 179 */     List<Report> reports = this.reportRepository.findByModuleAndCategoryAndSubCategoryOrderByNameAsc(module, category, subcategory);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     List<ReportTreeDTO> result = (List<ReportTreeDTO>)reports.stream().map(report -> { ReportTreeDTO dto = new ReportTreeDTO(); dto.setId(report.getId()); dto.setName(report.getName()); if (report.getSubReport10Name() != null) dto.setChartSize(Integer.valueOf(report.getSubReport10Name()));  dto.setRipViewerClass(report.getRipViewerClass()); return dto; }).collect(Collectors.toList());
/* 196 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PreviewReportOutputDTO previewReport(PreviewReportDTO dto) {
/*     */     try {
/* 203 */       JasperPrint print = generateJasperPrint(this.reportRepository
/* 204 */           .findById(dto.getReportId()).get(), dto.getInputParameters());
/* 205 */       HtmlExporter htmlExporter = new HtmlExporter();
/* 206 */       htmlExporter.setExporterInput((ExporterInput)new SimpleExporterInput(print));
/* 207 */       StringWriter writer = new StringWriter();
/*     */       
/* 209 */       SimpleHtmlReportConfiguration conf = new SimpleHtmlReportConfiguration();
/*     */       try {
/* 211 */         SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(writer);
/* 212 */         output.setImageHandler(new HtmlResourceHandler()
/*     */             {
/* 214 */               Map<String, String> images = new HashMap<>();
/*     */ 
/*     */               
/*     */               public void handleResource(String id, byte[] data) {
/* 218 */                 this.images.put(id, "data:image/png;base64," + Base64.encodeBase64String(data));
/*     */               }
/*     */ 
/*     */               
/*     */               public String getResourcePath(String id) {
/* 223 */                 return this.images.get(id);
/*     */               }
/*     */             });
/*     */         
/* 227 */         htmlExporter.setExporterInput((ExporterInput)new SimpleExporterInput(print));
/* 228 */         htmlExporter.setExporterOutput((ExporterOutput)output);
/* 229 */         conf.setPageIndex(Integer.valueOf(dto.getPageIndex()));
/* 230 */         conf.setZoomRatio(Float.valueOf(dto.getZoomRatio()));
/* 231 */         htmlExporter.setConfiguration((ReportExportConfiguration)conf);
/* 232 */         htmlExporter.exportReport();
/* 233 */       } catch (JRException e) {
/* 234 */         logger.warn("preview report failed.", (Throwable)e);
/*     */       } 
/*     */       
/* 237 */       PreviewReportOutputDTO result = new PreviewReportOutputDTO();
/* 238 */       result.setReport(writer.toString());
/* 239 */       result.setTotalPage(print.getPages().size());
/* 240 */       return result;
/* 241 */     } catch (JRException e) {
/* 242 */       logger.error("can't generate report with given id:'{}':{}", dto
/* 243 */           .getReportId(), e.getMessage());
/* 244 */       throw new RuntimeException(e);
/* 245 */     } catch (InterruptedException e) {
/* 246 */       throw new RuntimeException("Thread interrupted!", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private JasperPrint generateJasperPrint(Report report, Map<String, Object> inputParameter) throws JRException, InterruptedException {
/* 253 */     ByteArrayInputStream inputStream = new ByteArrayInputStream(report.getMasterReport().getBytes(StandardCharsets.UTF_8));
/* 254 */     if (Thread.interrupted()) {
/* 255 */       throw new InterruptedException();
/*     */     }
/*     */     
/* 258 */     List<Map<String, Object>> map = getInquiry(report.getReportInquiryClass()).inquire(inputParameter);
/* 259 */     if (Thread.interrupted()) {
/* 260 */       throw new InterruptedException();
/*     */     }
/* 262 */     JasperReport masterReport = JasperCompileManager.compileReport(inputStream);
/* 263 */     Map<String, Object> parameters = new HashMap<>();
/* 264 */     if (report.getSubReport1Name() != null && report.getSubReport1() != null) {
/* 265 */       parameters.put(report.getSubReport1Name(), generateSubReport(report.getSubReport1()));
/*     */     }
/* 267 */     if (report.getSubReport2Name() != null && report.getSubReport2() != null) {
/* 268 */       parameters.put(report.getSubReport2Name(), generateSubReport(report.getSubReport2()));
/*     */     }
/* 270 */     if (report.getSubReport3Name() != null && report.getSubReport3() != null) {
/* 271 */       parameters.put(report.getSubReport3Name(), generateSubReport(report.getSubReport3()));
/*     */     }
/* 273 */     if (report.getSubReport4Name() != null && report.getSubReport4() != null) {
/* 274 */       parameters.put(report.getSubReport4Name(), generateSubReport(report.getSubReport4()));
/*     */     }
/* 276 */     if (report.getSubReport5Name() != null && report.getSubReport5() != null) {
/* 277 */       parameters.put(report.getSubReport5Name(), generateSubReport(report.getSubReport5()));
/*     */     }
/* 279 */     if (report.getSubReport6Name() != null && report.getSubReport6() != null) {
/* 280 */       parameters.put(report.getSubReport6Name(), generateSubReport(report.getSubReport6()));
/*     */     }
/* 282 */     if (report.getSubReport7Name() != null && report.getSubReport7() != null) {
/* 283 */       parameters.put(report.getSubReport7Name(), generateSubReport(report.getSubReport7()));
/*     */     }
/* 285 */     if (report.getSubReport8Name() != null && report.getSubReport8() != null) {
/* 286 */       parameters.put(report.getSubReport8Name(), generateSubReport(report.getSubReport8()));
/*     */     }
/* 288 */     if (report.getSubReport9Name() != null && report.getSubReport9() != null) {
/* 289 */       parameters.put(report.getSubReport9Name(), generateSubReport(report.getSubReport9()));
/*     */     }
/* 291 */     if (report.getSubReport10Name() != null && report.getSubReport10() != null) {
/* 292 */       parameters.put(report.getSubReport10Name(), generateSubReport(report.getSubReport10()));
/*     */     }
/* 294 */     if (Thread.interrupted()) {
/* 295 */       throw new InterruptedException();
/*     */     }
/* 297 */     return JasperFillManager.fillReport(masterReport, parameters, (JRDataSource)new JRMapArrayDataSource(map
/* 298 */           .toArray()));
/*     */   }
/*     */   
/*     */   private ReportInquiry getInquiry(String className) {
/*     */     try {
/* 303 */       return (ReportInquiry)this.applicationContext.getBean(className);
/* 304 */     } catch (BeansException e) {
/* 305 */       logger.error("can't get bean with '{}'", className, e);
/* 306 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private JasperReport generateSubReport(String subReport) throws JRException, InterruptedException {
/* 312 */     if (Thread.interrupted()) {
/* 313 */       throw new InterruptedException();
/*     */     }
/*     */     
/* 316 */     ByteArrayInputStream subReportInputStream = new ByteArrayInputStream(subReport.getBytes(StandardCharsets.UTF_8));
/* 317 */     if (Thread.interrupted()) {
/* 318 */       throw new InterruptedException();
/*     */     }
/*     */     
/* 321 */     return JasperCompileManager.compileReport(subReportInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String exportCsv(ExportDTO dto) {
/*     */     try {
/* 328 */       JasperPrint print = generateJasperPrint(this.reportRepository
/* 329 */           .findById(dto.getReportId()).get(), dto.getInputParameters());
/* 330 */       JRCsvExporter exporter = new JRCsvExporter();
/* 331 */       exporter.setExporterInput((ExporterInput)new SimpleExporterInput(print));
/* 332 */       StringWriter stringWriter = new StringWriter();
/* 333 */       exporter.setExporterOutput((ExporterOutput)new SimpleWriterExporterOutput(stringWriter));
/* 334 */       exporter.setConfiguration((ExporterConfiguration)new SimpleCsvExporterConfiguration());
/* 335 */       exporter.exportReport();
/* 336 */       return stringWriter.toString();
/* 337 */     } catch (JRException e) {
/* 338 */       logger.error("Can't export report as csv with given id:'{}':{}", dto
/* 339 */           .getReportId(), e.getMessage());
/* 340 */       throw new RuntimeException(e);
/* 341 */     } catch (InterruptedException e) {
/* 342 */       throw new RuntimeException("Thread interrupted!", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String exportFile(ExportDTO dto) {
/*     */     try {
/*     */       String fileName;
/* 349 */       Report report = this.reportRepository.findById(dto.getReportId()).get();
/*     */       
/* 351 */       if (dto.getReportFormat() == ReportFormat.XLS) {
/* 352 */         fileName = exportXls(report, dto.getInputParameters());
/* 353 */       } else if (dto.getReportFormat() == ReportFormat.CSV) {
/* 354 */         fileName = exportCsv(report, dto.getInputParameters());
/* 355 */       } else if (dto.getReportFormat() == ReportFormat.PNG) {
/* 356 */         fileName = exportPng(report, dto.getInputParameters(), dto.getChartPage());
/* 357 */       } else if (dto.getReportFormat() == ReportFormat.JPEG) {
/* 358 */         fileName = exportJpeg(report, dto.getInputParameters(), dto.getChartPage());
/*     */       } else {
/* 360 */         fileName = exportPdf(report, dto.getInputParameters());
/*     */       } 
/* 362 */       String url = this.exportUrl + fileName;
/* 363 */       return url;
/* 364 */     } catch (RuntimeException e) {
/* 365 */       logger.error("Can't export file id:'{}':{}", dto.getReportId(), e.getMessage());
/* 366 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String exportCsv(Report report, Map<String, Object> inputParameters) {
/*     */     String fileName;
/*     */     try {
/* 373 */       JasperPrint print = generateJasperPrint(report, inputParameters);
/* 374 */       JRCsvExporter exporter = new JRCsvExporter();
/* 375 */       exporter.setExporterInput((ExporterInput)new SimpleExporterInput(print));
/* 376 */       StringWriter stringWriter = new StringWriter();
/* 377 */       exporter.setExporterOutput((ExporterOutput)new SimpleWriterExporterOutput(stringWriter));
/* 378 */       SimpleCsvExporterConfiguration config = new SimpleCsvExporterConfiguration();
/* 379 */       config.setWriteBOM(Boolean.valueOf(true));
/* 380 */       exporter.setConfiguration((ExporterConfiguration)config);
/* 381 */       exporter.exportReport();
/* 382 */       byte[] bytes = stringWriter.toString().getBytes();
/* 383 */       ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bytes.length);
/* 384 */       outputStream.write(bytes);
/* 385 */       fileName = createReportExportFile(outputStream, ReportFormat.CSV);
/* 386 */     } catch (IOException e) {
/* 387 */       logger.error("Can't export report as csv with given id:'{}':{}", report
/* 388 */           .getId(), e.getMessage());
/* 389 */       throw new RuntimeException(e);
/* 390 */     } catch (JRException e) {
/* 391 */       logger.error("Can't export report as csv with given id:'{}':{}", report
/* 392 */           .getId(), e.getMessage());
/* 393 */       throw new RuntimeException(e);
/* 394 */     } catch (InterruptedException e) {
/* 395 */       throw new RuntimeException("Thread interrupted!", e);
/*     */     } 
/*     */     
/* 398 */     return fileName;
/*     */   }
/*     */   public String exportXls(Report report, Map<String, Object> inputParameters) {
/*     */     String fileName;
/* 402 */     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/*     */     
/*     */     try {
/* 405 */       JasperPrint print = generateJasperPrint(report, inputParameters);
/* 406 */       JRXlsxExporter exporter = new JRXlsxExporter();
/* 407 */       exporter.setExporterInput((ExporterInput)new SimpleExporterInput(print));
/* 408 */       exporter.setExporterOutput((ExporterOutput)new SimpleOutputStreamExporterOutput(outputStream));
/* 409 */       exporter.exportReport();
/* 410 */       ByteArrayOutputStream transferOut = transferNumberValueInExcelFormat(outputStream);
/* 411 */       fileName = createReportExportFile(transferOut, ReportFormat.XLS);
/* 412 */     } catch (JRException e) {
/* 413 */       logger.error("Can't export report as xls with given id:'{}':{}", report
/* 414 */           .getId(), e.getMessage());
/* 415 */       throw new RuntimeException(e);
/* 416 */     } catch (InterruptedException e) {
/* 417 */       throw new RuntimeException("Thread interrupted!", e);
/*     */     } 
/*     */     
/* 420 */     return fileName;
/*     */   }
/*     */   public String exportPdf(Report report, Map<String, Object> inputParameters) {
/*     */     String fileName;
/* 424 */     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/*     */     
/*     */     try {
/* 427 */       JasperPrint print = generateJasperPrint(report, inputParameters);
/* 428 */       JRPdfExporter exporter = new JRPdfExporter();
/* 429 */       exporter.setExporterInput((ExporterInput)new SimpleExporterInput(print));
/* 430 */       exporter.setExporterOutput((ExporterOutput)new SimpleOutputStreamExporterOutput(outputStream));
/* 431 */       exporter.setConfiguration((ExporterConfiguration)new SimplePdfExporterConfiguration());
/* 432 */       exporter.exportReport();
/* 433 */       fileName = createReportExportFile(outputStream, ReportFormat.PDF);
/* 434 */     } catch (JRException e) {
/* 435 */       logger.error("Can't export report as pdf with given id:'{}':{}", report
/* 436 */           .getId(), e.getMessage());
/* 437 */       throw new RuntimeException(e);
/* 438 */     } catch (InterruptedException e) {
/* 439 */       throw new RuntimeException("Thread interrupted!", e);
/*     */     } 
/*     */     
/* 442 */     return fileName;
/*     */   }
/*     */   
/*     */   public String exportPng(Report report, Map<String, Object> inputParameters, Integer pageIndex) {
/*     */     String fileName;
/*     */     try {
/* 448 */       JasperPrint print = generateJasperPrint(report, inputParameters);
/*     */       
/* 450 */       JasperPrintManager printManager = JasperPrintManager.getInstance((JasperReportsContext)DefaultJasperReportsContext.getInstance());
/* 451 */       fileName = UUID.randomUUID().toString() + "." + ReportFormat.PNG.toString().toLowerCase();
/* 452 */       File file = null;
/* 453 */       file = new File(this.exportDirectory + fileName);
/*     */       
/* 455 */       OutputStream ouputStream = new FileOutputStream(file);
/*     */       
/* 457 */       BufferedImage rendered_image = null;
/* 458 */       rendered_image = (BufferedImage)JasperPrintManager.printPageToImage(print, pageIndex.intValue(), 1.6F);
/* 459 */       ImageIO.write(rendered_image, "png", ouputStream);
/* 460 */     } catch (IOException e) {
/* 461 */       logger.error("Can't export report as png with given id:'{}':{}", report
/* 462 */           .getId(), e.getMessage());
/* 463 */       throw new RuntimeException(e);
/* 464 */     } catch (JRException e) {
/* 465 */       logger.error("Can't export report as png with given id:'{}':{}", report
/* 466 */           .getId(), e.getMessage());
/* 467 */       throw new RuntimeException(e);
/* 468 */     } catch (InterruptedException e) {
/* 469 */       throw new RuntimeException("Thread interrupted!", e);
/*     */     } 
/* 471 */     return fileName;
/*     */   }
/*     */   
/*     */   public String exportJpeg(Report report, Map<String, Object> inputParameters, Integer pageIndex) {
/*     */     String fileName;
/*     */     try {
/* 477 */       JasperPrint print = generateJasperPrint(report, inputParameters);
/*     */       
/* 479 */       JasperPrintManager printManager = JasperPrintManager.getInstance((JasperReportsContext)DefaultJasperReportsContext.getInstance());
/* 480 */       fileName = UUID.randomUUID().toString() + "." + ReportFormat.JPEG.toString().toLowerCase();
/* 481 */       File file = null;
/* 482 */       file = new File(this.exportDirectory + fileName);
/*     */       
/* 484 */       OutputStream ouputStream = new FileOutputStream(file);
/*     */       
/* 486 */       BufferedImage rendered_image = null;
/* 487 */       rendered_image = (BufferedImage)JasperPrintManager.printPageToImage(print, pageIndex.intValue(), 1.6F);
/* 488 */       ImageIO.write(rendered_image, "jpeg", ouputStream);
/* 489 */     } catch (IOException e) {
/* 490 */       logger.error("Can't export report as png with given id:'{}':{}", report
/* 491 */           .getId(), e.getMessage());
/* 492 */       throw new RuntimeException(e);
/* 493 */     } catch (JRException e) {
/* 494 */       logger.error("Can't export report as png with given id:'{}':{}", report
/* 495 */           .getId(), e.getMessage());
/* 496 */       throw new RuntimeException(e);
/* 497 */     } catch (InterruptedException e) {
/* 498 */       throw new RuntimeException("Thread interrupted!", e);
/*     */     } 
/* 500 */     return fileName;
/*     */   }
/*     */   
/*     */   private ByteArrayOutputStream transferNumberValueInExcelFormat(ByteArrayOutputStream jasperOut) {
/* 504 */     logger.debug("Transfer Number Value In Excel Format. Jasper outputStream size:'{}' bytes, about:'{}' KBytes, '{}' MBytes", new Object[] {
/*     */           
/* 506 */           Integer.valueOf(jasperOut.size()), 
/* 507 */           Integer.valueOf(jasperOut.size() / 1024), 
/* 508 */           Integer.valueOf(jasperOut.size() / 1048576) });
/* 509 */     if (jasperOut.size() >= 10485760) {
/*     */ 
/*     */       
/* 512 */       String fileName = UUID.randomUUID().toString() + ".xlsx";
/* 513 */       File file = new File(this.exportDirectory, fileName);
/* 514 */       try (FileOutputStream fos = new FileOutputStream(file)) {
/* 515 */         jasperOut.writeTo(fos);
/* 516 */       } catch (IOException e) {
/* 517 */         logger.error("Pre-store large excel file fail, message:'{}'", e);
/* 518 */         return jasperOut;
/*     */       } 
/*     */ 
/*     */       
/* 522 */       try (Workbook wb = WorkbookFactory.create(file)) {
/* 523 */         return transferNumberValueFromWorkbook(wb);
/* 524 */       } catch (IOException e) {
/* 525 */         logger.error("Transfer Number Value In Excel Format. transfer File fail, message:'{}'", e);
/* 526 */         return jasperOut;
/* 527 */       } catch (InvalidFormatException e) {
/* 528 */         logger.error("Transfer Number Value In Excel Format. Invalid Format Error, message:'{}'", (Throwable)e);
/*     */         
/* 530 */         return jasperOut;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 535 */     try (Workbook wb = WorkbookFactory.create(new ByteArrayInputStream(jasperOut.toByteArray()))) {
/* 536 */       return transferNumberValueFromWorkbook(wb);
/* 537 */     } catch (IOException e) {
/* 538 */       logger.error("Transfer Number Value In Excel Format. transfer File fail, message:'{}'", e);
/* 539 */       return jasperOut;
/* 540 */     } catch (EncryptedDocumentException e) {
/* 541 */       logger.error("Transfer Number Value In Excel Format. Encrypted Document Error, message:'{}'", (Throwable)e);
/*     */       
/* 543 */       return jasperOut;
/* 544 */     } catch (InvalidFormatException e) {
/* 545 */       logger.error("Transfer Number Value In Excel Format. Invalid Format Error, message:'{}'", (Throwable)e);
/*     */       
/* 547 */       return jasperOut;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ByteArrayOutputStream transferNumberValueFromWorkbook(Workbook wb) throws IOException {
/* 553 */     Pattern pattern = Pattern.compile("^\\d+$");
/* 554 */     List<Integer> needTransferCells = new ArrayList<>();
/* 555 */     for (int i = 0; i < wb.getNumberOfSheets(); i++) {
/* 556 */       Sheet sheet = wb.getSheetAt(i);
/* 557 */       if (sheet != null)
/*     */       {
/*     */         
/* 560 */         for (Row r : sheet) {
/* 561 */           needTransferCells.clear();
/* 562 */           for (Cell c : r) {
/* 563 */             if (1 == c.getCellType()) {
/* 564 */               String value = c.getStringCellValue();
/* 565 */               if (value == null || value.isEmpty()) {
/*     */                 continue;
/*     */               }
/* 568 */               if (!pattern.matcher(value).matches()) {
/*     */                 continue;
/*     */               }
/* 571 */               needTransferCells.add(Integer.valueOf(c.getColumnIndex()));
/*     */             } 
/*     */           } 
/*     */           
/* 575 */           for (Integer each : needTransferCells) {
/* 576 */             Cell originalCell = r.getCell(each.intValue());
/* 577 */             CellStyle style = originalCell.getCellStyle();
/* 578 */             String value = originalCell.getStringCellValue();
/* 579 */             r.removeCell(originalCell);
/*     */             
/* 581 */             Cell newCell = r.createCell(each.intValue(), 0);
/* 582 */             newCell.setCellStyle(style);
/* 583 */             newCell.setCellValue((new Double(value)).doubleValue());
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 588 */     ByteArrayOutputStream transferOut = new ByteArrayOutputStream();
/* 589 */     wb.write(transferOut);
/* 590 */     transferOut.close();
/* 591 */     return transferOut;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String createReportExportFile(ByteArrayOutputStream outputStream, ReportFormat format) {
/* 610 */     String fileName = UUID.randomUUID().toString() + "." + ((ReportFormat.XLS == format) ? (format.toString().toLowerCase() + "x") : format.toString().toLowerCase());
/*     */     
/* 612 */     File file = null;
/* 613 */     file = new File(this.exportDirectory + fileName);
/* 614 */     logger.debug("create report file path:'{}'", file.getAbsolutePath());
/* 615 */     try (OutputStream out = new FileOutputStream(file)) {
/* 616 */       outputStream.writeTo(out);
/* 617 */     } catch (FileNotFoundException e) {
/* 618 */       logger.error("No such file:'{}'", file.getAbsolutePath());
/* 619 */       return null;
/* 620 */     } catch (IOException e) {
/* 621 */       logger.error("Something wrong while retrieving Export File, message:'{}'", e);
/* 622 */       return null;
/*     */     } 
/* 624 */     return fileName;
/*     */   }
/*     */ 
/*     */   
/*     */   public DynamicConfig getDynaConfig(String configName) {
/* 629 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDynaConfig(String key, String value) {}
/*     */ 
/*     */   
/*     */   public boolean isInPrimaryGroup() {
/* 637 */     return false;
/*     */   }
/*     */   
/*     */   public void addOpLog(String userId, String cpeIp, String subSysName, String deviceName, String description, Date operationTime, OperationResult operationResult, String remark) {}
/*     */   
/*     */   public void addOpLog(String userId, String cpeIp, String subSysName, String operationItem, String deviceName, String description, Date operationTime, OperationResult operationResult, String remark) {}
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\service\RptRemoteImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */