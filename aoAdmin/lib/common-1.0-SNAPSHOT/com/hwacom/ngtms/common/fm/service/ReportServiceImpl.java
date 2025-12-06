/*     */ package com.hwacom.ngtms.common.fm.service;
/*     */ 
/*     */ import com.hwacom.ngtms.common.fm.model.Report;
/*     */ import com.hwacom.ngtms.common.fm.repository.ReportRepository;
/*     */ import com.hwacom.ngtms.common.rpt.ReportInquiry;
/*     */ import com.hwacom.ngtms.common.shared.ReportFormat;
/*     */ import com.hwacom.ngtms.common.shared.dto.ExportDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewReportDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewReportOutputDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.ReportTreeDTO;
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
/*     */ import org.springframework.context.annotation.Profile;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service
/*     */ @Profile({"dev"})
/*     */ public class ReportServiceImpl
/*     */   implements ReportService {
/*  79 */   private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
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
/*  95 */     List<Report> reports = this.reportRepository.findByModule(module);
/*  96 */     Set<ReportTreeDTO> resultSet = new HashSet<>();
/*     */     
/*  98 */     for (Report report : reports) {
/*  99 */       ReportTreeDTO root = null;
/* 100 */       if (report.getSubCategory() != null && report.getCategory() != null) {
/* 101 */         root = generateCategoryReportTreeDTO(module, report.getCategory());
/* 102 */       } else if (report.getSubCategory() != null || report.getCategory() != null) {
/*     */ 
/*     */         
/* 105 */         root = generateSubCategoryReportTreeDTO(module, report.getCategory(), report.getSubCategory());
/*     */       } else {
/*     */         
/* 108 */         root = generateReportTreeDTO(report);
/*     */       } 
/* 110 */       resultSet.add(root);
/*     */     } 
/* 112 */     return new ArrayList<>(resultSet);
/*     */   }
/*     */   
/*     */   public List<ReportTreeDTO> retrieveReportTreeDTOWithCategory(String module) {
/* 116 */     Set<String> categories = this.reportRepository.findAllCategoryByModule(module);
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
/* 128 */     List<ReportTreeDTO> result = (List<ReportTreeDTO>)categories.stream().map(category -> { ReportTreeDTO dto = new ReportTreeDTO(); dto.setId(category); dto.setName(category); dto.setChildren(generateSubcategoryReportTreeDTO(paramString1, category)); return dto; }).collect(Collectors.toList());
/* 129 */     return result;
/*     */   }
/*     */   
/*     */   private ReportTreeDTO generateCategoryReportTreeDTO(String module, String category) {
/* 133 */     ReportTreeDTO dto = new ReportTreeDTO();
/* 134 */     dto.setId(category);
/* 135 */     dto.setName(category);
/* 136 */     dto.setChildren(generateSubcategoryReportTreeDTO(module, category));
/* 137 */     return dto;
/*     */   }
/*     */ 
/*     */   
/*     */   private ReportTreeDTO generateSubCategoryReportTreeDTO(String module, String category, String subCategory) {
/* 142 */     ReportTreeDTO dto = new ReportTreeDTO();
/* 143 */     dto.setId(category + "_" + subCategory);
/* 144 */     dto.setName(subCategory);
/* 145 */     dto.setChildren(generateReportTreeDTO(module, category, subCategory));
/* 146 */     return dto;
/*     */   }
/*     */   
/*     */   private ReportTreeDTO generateReportTreeDTO(Report report) {
/* 150 */     ReportTreeDTO dto = new ReportTreeDTO();
/* 151 */     dto.setId(report.getId());
/* 152 */     dto.setName(report.getName());
/* 153 */     dto.setRipViewerClass(report.getRipViewerClass());
/* 154 */     return dto;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<ReportTreeDTO> generateSubcategoryReportTreeDTO(String module, String category) {
/* 159 */     Set<String> subcategories = this.reportRepository.findAllSubCategoryByModuleAndCategory(module, category);
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
/* 171 */     List<ReportTreeDTO> result = (List<ReportTreeDTO>)subcategories.stream().map(subcategory -> { ReportTreeDTO dto = new ReportTreeDTO(); dto.setId(paramString1 + "_" + subcategory); dto.setName(subcategory); dto.setChildren(generateReportTreeDTO(paramString2, paramString1, subcategory)); return dto; }).collect(Collectors.toList());
/* 172 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<ReportTreeDTO> generateReportTreeDTO(String module, String category, String subcategory) {
/* 178 */     List<Report> reports = this.reportRepository.findByModuleAndCategoryAndSubCategoryOrderByNameAsc(module, category, subcategory);
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
/* 194 */     List<ReportTreeDTO> result = (List<ReportTreeDTO>)reports.stream().map(report -> { ReportTreeDTO dto = new ReportTreeDTO(); dto.setId(report.getId()); dto.setName(report.getName()); if (report.getSubReport10Name() != null) dto.setChartSize(Integer.valueOf(report.getSubReport10Name()));  dto.setRipViewerClass(report.getRipViewerClass()); return dto; }).collect(Collectors.toList());
/* 195 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PreviewReportOutputDTO previewReport(PreviewReportDTO dto) {
/*     */     try {
/* 202 */       JasperPrint print = generateJasperPrint(this.reportRepository
/* 203 */           .findById(dto.getReportId()).get(), dto.getInputParameters());
/* 204 */       HtmlExporter htmlExporter = new HtmlExporter();
/* 205 */       htmlExporter.setExporterInput((ExporterInput)new SimpleExporterInput(print));
/* 206 */       StringWriter writer = new StringWriter();
/*     */       
/* 208 */       SimpleHtmlReportConfiguration conf = new SimpleHtmlReportConfiguration();
/*     */       try {
/* 210 */         SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(writer);
/* 211 */         output.setImageHandler(new HtmlResourceHandler()
/*     */             {
/* 213 */               Map<String, String> images = new HashMap<>();
/*     */ 
/*     */               
/*     */               public void handleResource(String id, byte[] data) {
/* 217 */                 this.images.put(id, "data:image/png;base64," + Base64.encodeBase64String(data));
/*     */               }
/*     */ 
/*     */               
/*     */               public String getResourcePath(String id) {
/* 222 */                 return this.images.get(id);
/*     */               }
/*     */             });
/*     */         
/* 226 */         htmlExporter.setExporterInput((ExporterInput)new SimpleExporterInput(print));
/* 227 */         htmlExporter.setExporterOutput((ExporterOutput)output);
/* 228 */         conf.setPageIndex(Integer.valueOf(dto.getPageIndex()));
/* 229 */         conf.setZoomRatio(Float.valueOf(dto.getZoomRatio()));
/* 230 */         htmlExporter.setConfiguration((ReportExportConfiguration)conf);
/* 231 */         htmlExporter.exportReport();
/* 232 */       } catch (JRException e) {
/* 233 */         logger.warn("preview report failed.", (Throwable)e);
/*     */       } 
/*     */       
/* 236 */       PreviewReportOutputDTO result = new PreviewReportOutputDTO();
/* 237 */       result.setReport(writer.toString());
/* 238 */       result.setTotalPage(print.getPages().size());
/* 239 */       return result;
/* 240 */     } catch (JRException e) {
/* 241 */       logger.error("can't generate report with given id:'{}':{}", dto
/* 242 */           .getReportId(), e.getMessage());
/* 243 */       throw new RuntimeException(e);
/* 244 */     } catch (InterruptedException e) {
/* 245 */       throw new RuntimeException("Thread interrupted!", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private JasperPrint generateJasperPrint(Report report, Map<String, Object> inputParameter) throws JRException, InterruptedException {
/* 252 */     ByteArrayInputStream inputStream = new ByteArrayInputStream(report.getMasterReport().getBytes(StandardCharsets.UTF_8));
/* 253 */     if (Thread.interrupted()) {
/* 254 */       throw new InterruptedException();
/*     */     }
/*     */     
/* 257 */     List<Map<String, Object>> map = getInquiry(report.getReportInquiryClass()).inquire(inputParameter);
/* 258 */     if (Thread.interrupted()) {
/* 259 */       throw new InterruptedException();
/*     */     }
/* 261 */     JasperReport masterReport = JasperCompileManager.compileReport(inputStream);
/* 262 */     Map<String, Object> parameters = new HashMap<>();
/* 263 */     if (report.getSubReport1Name() != null && report.getSubReport1() != null) {
/* 264 */       parameters.put(report.getSubReport1Name(), generateSubReport(report.getSubReport1()));
/*     */     }
/* 266 */     if (report.getSubReport2Name() != null && report.getSubReport2() != null) {
/* 267 */       parameters.put(report.getSubReport2Name(), generateSubReport(report.getSubReport2()));
/*     */     }
/* 269 */     if (report.getSubReport3Name() != null && report.getSubReport3() != null) {
/* 270 */       parameters.put(report.getSubReport3Name(), generateSubReport(report.getSubReport3()));
/*     */     }
/* 272 */     if (report.getSubReport4Name() != null && report.getSubReport4() != null) {
/* 273 */       parameters.put(report.getSubReport4Name(), generateSubReport(report.getSubReport4()));
/*     */     }
/* 275 */     if (report.getSubReport5Name() != null && report.getSubReport5() != null) {
/* 276 */       parameters.put(report.getSubReport5Name(), generateSubReport(report.getSubReport5()));
/*     */     }
/* 278 */     if (report.getSubReport6Name() != null && report.getSubReport6() != null) {
/* 279 */       parameters.put(report.getSubReport6Name(), generateSubReport(report.getSubReport6()));
/*     */     }
/* 281 */     if (report.getSubReport7Name() != null && report.getSubReport7() != null) {
/* 282 */       parameters.put(report.getSubReport7Name(), generateSubReport(report.getSubReport7()));
/*     */     }
/* 284 */     if (report.getSubReport8Name() != null && report.getSubReport8() != null) {
/* 285 */       parameters.put(report.getSubReport8Name(), generateSubReport(report.getSubReport8()));
/*     */     }
/* 287 */     if (report.getSubReport9Name() != null && report.getSubReport9() != null) {
/* 288 */       parameters.put(report.getSubReport9Name(), generateSubReport(report.getSubReport9()));
/*     */     }
/* 290 */     if (report.getSubReport10Name() != null && report.getSubReport10() != null) {
/* 291 */       parameters.put(report.getSubReport10Name(), generateSubReport(report.getSubReport10()));
/*     */     }
/* 293 */     if (Thread.interrupted()) {
/* 294 */       throw new InterruptedException();
/*     */     }
/* 296 */     return JasperFillManager.fillReport(masterReport, parameters, (JRDataSource)new JRMapArrayDataSource(map
/* 297 */           .toArray()));
/*     */   }
/*     */   
/*     */   private ReportInquiry getInquiry(String className) {
/*     */     try {
/* 302 */       return (ReportInquiry)this.applicationContext.getBean(className);
/* 303 */     } catch (BeansException e) {
/* 304 */       logger.error("can't get bean with '{}'", className, e);
/* 305 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private JasperReport generateSubReport(String subReport) throws JRException, InterruptedException {
/* 311 */     if (Thread.interrupted()) {
/* 312 */       throw new InterruptedException();
/*     */     }
/*     */     
/* 315 */     ByteArrayInputStream subReportInputStream = new ByteArrayInputStream(subReport.getBytes(StandardCharsets.UTF_8));
/* 316 */     if (Thread.interrupted()) {
/* 317 */       throw new InterruptedException();
/*     */     }
/*     */     
/* 320 */     return JasperCompileManager.compileReport(subReportInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String exportCsv(ExportDTO dto) {
/*     */     try {
/* 327 */       JasperPrint print = generateJasperPrint(this.reportRepository
/* 328 */           .findById(dto.getReportId()).get(), dto.getInputParameters());
/* 329 */       JRCsvExporter exporter = new JRCsvExporter();
/* 330 */       exporter.setExporterInput((ExporterInput)new SimpleExporterInput(print));
/* 331 */       StringWriter stringWriter = new StringWriter();
/* 332 */       exporter.setExporterOutput((ExporterOutput)new SimpleWriterExporterOutput(stringWriter));
/* 333 */       exporter.setConfiguration((ExporterConfiguration)new SimpleCsvExporterConfiguration());
/* 334 */       exporter.exportReport();
/* 335 */       return stringWriter.toString();
/* 336 */     } catch (JRException e) {
/* 337 */       logger.error("Can't export report as csv with given id:'{}':{}", dto
/* 338 */           .getReportId(), e.getMessage());
/* 339 */       throw new RuntimeException(e);
/* 340 */     } catch (InterruptedException e) {
/* 341 */       throw new RuntimeException("Thread interrupted!", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String exportFile(ExportDTO dto) {
/*     */     try {
/*     */       String fileName;
/* 348 */       Report report = this.reportRepository.findById(dto.getReportId()).get();
/*     */       
/* 350 */       if (dto.getReportFormat() == ReportFormat.XLS) {
/* 351 */         fileName = exportXls(report, dto.getInputParameters());
/* 352 */       } else if (dto.getReportFormat() == ReportFormat.CSV) {
/* 353 */         fileName = exportCsv(report, dto.getInputParameters());
/* 354 */       } else if (dto.getReportFormat() == ReportFormat.PNG) {
/* 355 */         fileName = exportPng(report, dto.getInputParameters(), dto.getChartPage());
/* 356 */       } else if (dto.getReportFormat() == ReportFormat.JPEG) {
/* 357 */         fileName = exportJpeg(report, dto.getInputParameters(), dto.getChartPage());
/*     */       } else {
/* 359 */         fileName = exportPdf(report, dto.getInputParameters());
/*     */       } 
/* 361 */       String url = this.exportUrl + fileName;
/* 362 */       return url;
/* 363 */     } catch (RuntimeException e) {
/* 364 */       logger.error("Can't export file id:'{}':{}", dto.getReportId(), e.getMessage());
/* 365 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String exportCsv(Report report, Map<String, Object> inputParameters) {
/*     */     String fileName;
/*     */     try {
/* 372 */       JasperPrint print = generateJasperPrint(report, inputParameters);
/* 373 */       JRCsvExporter exporter = new JRCsvExporter();
/* 374 */       exporter.setExporterInput((ExporterInput)new SimpleExporterInput(print));
/* 375 */       StringWriter stringWriter = new StringWriter();
/* 376 */       exporter.setExporterOutput((ExporterOutput)new SimpleWriterExporterOutput(stringWriter));
/* 377 */       SimpleCsvExporterConfiguration config = new SimpleCsvExporterConfiguration();
/* 378 */       config.setWriteBOM(Boolean.valueOf(true));
/* 379 */       exporter.setConfiguration((ExporterConfiguration)config);
/* 380 */       exporter.exportReport();
/* 381 */       byte[] bytes = stringWriter.toString().getBytes();
/* 382 */       ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bytes.length);
/* 383 */       outputStream.write(bytes);
/* 384 */       fileName = createReportExportFile(outputStream, ReportFormat.CSV);
/* 385 */     } catch (IOException e) {
/* 386 */       logger.error("Can't export report as csv with given id:'{}':{}", report
/* 387 */           .getId(), e.getMessage());
/* 388 */       throw new RuntimeException(e);
/* 389 */     } catch (JRException e) {
/* 390 */       logger.error("Can't export report as csv with given id:'{}':{}", report
/* 391 */           .getId(), e.getMessage());
/* 392 */       throw new RuntimeException(e);
/* 393 */     } catch (InterruptedException e) {
/* 394 */       throw new RuntimeException("Thread interrupted!", e);
/*     */     } 
/*     */     
/* 397 */     return fileName;
/*     */   }
/*     */   public String exportXls(Report report, Map<String, Object> inputParameters) {
/*     */     String fileName;
/* 401 */     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/*     */     
/*     */     try {
/* 404 */       JasperPrint print = generateJasperPrint(report, inputParameters);
/* 405 */       JRXlsxExporter exporter = new JRXlsxExporter();
/* 406 */       exporter.setExporterInput((ExporterInput)new SimpleExporterInput(print));
/* 407 */       exporter.setExporterOutput((ExporterOutput)new SimpleOutputStreamExporterOutput(outputStream));
/* 408 */       exporter.exportReport();
/* 409 */       ByteArrayOutputStream transferOut = transferNumberValueInExcelFormat(outputStream);
/* 410 */       fileName = createReportExportFile(transferOut, ReportFormat.XLS);
/* 411 */     } catch (JRException e) {
/* 412 */       logger.error("Can't export report as xls with given id:'{}':{}", report
/* 413 */           .getId(), e.getMessage());
/* 414 */       throw new RuntimeException(e);
/* 415 */     } catch (InterruptedException e) {
/* 416 */       throw new RuntimeException("Thread interrupted!", e);
/*     */     } 
/*     */     
/* 419 */     return fileName;
/*     */   }
/*     */   public String exportPdf(Report report, Map<String, Object> inputParameters) {
/*     */     String fileName;
/* 423 */     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/*     */     
/*     */     try {
/* 426 */       JasperPrint print = generateJasperPrint(report, inputParameters);
/* 427 */       JRPdfExporter exporter = new JRPdfExporter();
/* 428 */       exporter.setExporterInput((ExporterInput)new SimpleExporterInput(print));
/* 429 */       exporter.setExporterOutput((ExporterOutput)new SimpleOutputStreamExporterOutput(outputStream));
/* 430 */       exporter.setConfiguration((ExporterConfiguration)new SimplePdfExporterConfiguration());
/* 431 */       exporter.exportReport();
/* 432 */       fileName = createReportExportFile(outputStream, ReportFormat.PDF);
/* 433 */     } catch (JRException e) {
/* 434 */       logger.error("Can't export report as pdf with given id:'{}':{}", report
/* 435 */           .getId(), e.getMessage());
/* 436 */       throw new RuntimeException(e);
/* 437 */     } catch (InterruptedException e) {
/* 438 */       throw new RuntimeException("Thread interrupted!", e);
/*     */     } 
/*     */     
/* 441 */     return fileName;
/*     */   }
/*     */   
/*     */   public String exportPng(Report report, Map<String, Object> inputParameters, Integer pageIndex) {
/*     */     String fileName;
/*     */     try {
/* 447 */       JasperPrint print = generateJasperPrint(report, inputParameters);
/*     */       
/* 449 */       JasperPrintManager printManager = JasperPrintManager.getInstance((JasperReportsContext)DefaultJasperReportsContext.getInstance());
/* 450 */       fileName = UUID.randomUUID().toString() + "." + ReportFormat.PNG.toString().toLowerCase();
/* 451 */       File file = null;
/* 452 */       file = new File(this.exportDirectory + fileName);
/*     */       
/* 454 */       OutputStream ouputStream = new FileOutputStream(file);
/*     */       
/* 456 */       BufferedImage rendered_image = null;
/* 457 */       rendered_image = (BufferedImage)JasperPrintManager.printPageToImage(print, pageIndex.intValue(), 1.6F);
/* 458 */       ImageIO.write(rendered_image, "png", ouputStream);
/* 459 */     } catch (IOException e) {
/* 460 */       logger.error("Can't export report as png with given id:'{}':{}", report
/* 461 */           .getId(), e.getMessage());
/* 462 */       throw new RuntimeException(e);
/* 463 */     } catch (JRException e) {
/* 464 */       logger.error("Can't export report as png with given id:'{}':{}", report
/* 465 */           .getId(), e.getMessage());
/* 466 */       throw new RuntimeException(e);
/* 467 */     } catch (InterruptedException e) {
/* 468 */       throw new RuntimeException("Thread interrupted!", e);
/*     */     } 
/* 470 */     return fileName;
/*     */   }
/*     */   
/*     */   public String exportJpeg(Report report, Map<String, Object> inputParameters, Integer pageIndex) {
/*     */     String fileName;
/*     */     try {
/* 476 */       JasperPrint print = generateJasperPrint(report, inputParameters);
/*     */       
/* 478 */       JasperPrintManager printManager = JasperPrintManager.getInstance((JasperReportsContext)DefaultJasperReportsContext.getInstance());
/* 479 */       fileName = UUID.randomUUID().toString() + "." + ReportFormat.JPEG.toString().toLowerCase();
/* 480 */       File file = null;
/* 481 */       file = new File(this.exportDirectory + fileName);
/*     */       
/* 483 */       OutputStream ouputStream = new FileOutputStream(file);
/*     */       
/* 485 */       BufferedImage rendered_image = null;
/* 486 */       rendered_image = (BufferedImage)JasperPrintManager.printPageToImage(print, pageIndex.intValue(), 1.6F);
/* 487 */       ImageIO.write(rendered_image, "jpeg", ouputStream);
/* 488 */     } catch (IOException e) {
/* 489 */       logger.error("Can't export report as png with given id:'{}':{}", report
/* 490 */           .getId(), e.getMessage());
/* 491 */       throw new RuntimeException(e);
/* 492 */     } catch (JRException e) {
/* 493 */       logger.error("Can't export report as png with given id:'{}':{}", report
/* 494 */           .getId(), e.getMessage());
/* 495 */       throw new RuntimeException(e);
/* 496 */     } catch (InterruptedException e) {
/* 497 */       throw new RuntimeException("Thread interrupted!", e);
/*     */     } 
/* 499 */     return fileName;
/*     */   }
/*     */   
/*     */   private ByteArrayOutputStream transferNumberValueInExcelFormat(ByteArrayOutputStream jasperOut) {
/* 503 */     logger.debug("Transfer Number Value In Excel Format. Jasper outputStream size:'{}' bytes, about:'{}' KBytes, '{}' MBytes", new Object[] {
/*     */           
/* 505 */           Integer.valueOf(jasperOut.size()), 
/* 506 */           Integer.valueOf(jasperOut.size() / 1024), 
/* 507 */           Integer.valueOf(jasperOut.size() / 1048576) });
/* 508 */     if (jasperOut.size() >= 10485760) {
/*     */ 
/*     */       
/* 511 */       String fileName = UUID.randomUUID().toString() + ".xlsx";
/* 512 */       File file = new File(this.exportDirectory, fileName);
/* 513 */       try (FileOutputStream fos = new FileOutputStream(file)) {
/* 514 */         jasperOut.writeTo(fos);
/* 515 */       } catch (IOException e) {
/* 516 */         logger.error("Pre-store large excel file fail, message:'{}'", e);
/* 517 */         return jasperOut;
/*     */       } 
/*     */ 
/*     */       
/* 521 */       try (Workbook wb = WorkbookFactory.create(file)) {
/* 522 */         return transferNumberValueFromWorkbook(wb);
/* 523 */       } catch (IOException e) {
/* 524 */         logger.error("Transfer Number Value In Excel Format. transfer File fail, message:'{}'", e);
/* 525 */         return jasperOut;
/* 526 */       } catch (InvalidFormatException e) {
/* 527 */         logger.error("Transfer Number Value In Excel Format. Invalid Format Error, message:'{}'", (Throwable)e);
/*     */         
/* 529 */         return jasperOut;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 534 */     try (Workbook wb = WorkbookFactory.create(new ByteArrayInputStream(jasperOut.toByteArray()))) {
/* 535 */       return transferNumberValueFromWorkbook(wb);
/* 536 */     } catch (IOException e) {
/* 537 */       logger.error("Transfer Number Value In Excel Format. transfer File fail, message:'{}'", e);
/* 538 */       return jasperOut;
/* 539 */     } catch (EncryptedDocumentException e) {
/* 540 */       logger.error("Transfer Number Value In Excel Format. Encrypted Document Error, message:'{}'", (Throwable)e);
/*     */       
/* 542 */       return jasperOut;
/* 543 */     } catch (InvalidFormatException e) {
/* 544 */       logger.error("Transfer Number Value In Excel Format. Invalid Format Error, message:'{}'", (Throwable)e);
/*     */       
/* 546 */       return jasperOut;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ByteArrayOutputStream transferNumberValueFromWorkbook(Workbook wb) throws IOException {
/* 552 */     Pattern pattern = Pattern.compile("^\\d+$");
/* 553 */     List<Integer> needTransferCells = new ArrayList<>();
/* 554 */     for (int i = 0; i < wb.getNumberOfSheets(); i++) {
/* 555 */       Sheet sheet = wb.getSheetAt(i);
/* 556 */       if (sheet != null)
/*     */       {
/*     */         
/* 559 */         for (Row r : sheet) {
/* 560 */           needTransferCells.clear();
/* 561 */           for (Cell c : r) {
/* 562 */             if (1 == c.getCellType()) {
/* 563 */               String value = c.getStringCellValue();
/* 564 */               if (value == null || value.isEmpty()) {
/*     */                 continue;
/*     */               }
/* 567 */               if (!pattern.matcher(value).matches()) {
/*     */                 continue;
/*     */               }
/* 570 */               needTransferCells.add(Integer.valueOf(c.getColumnIndex()));
/*     */             } 
/*     */           } 
/*     */           
/* 574 */           for (Integer each : needTransferCells) {
/* 575 */             Cell originalCell = r.getCell(each.intValue());
/* 576 */             CellStyle style = originalCell.getCellStyle();
/* 577 */             String value = originalCell.getStringCellValue();
/* 578 */             r.removeCell(originalCell);
/*     */             
/* 580 */             Cell newCell = r.createCell(each.intValue(), 0);
/* 581 */             newCell.setCellStyle(style);
/* 582 */             newCell.setCellValue((new Double(value)).doubleValue());
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 587 */     ByteArrayOutputStream transferOut = new ByteArrayOutputStream();
/* 588 */     wb.write(transferOut);
/* 589 */     transferOut.close();
/* 590 */     return transferOut;
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
/* 609 */     String fileName = UUID.randomUUID().toString() + "." + ((ReportFormat.XLS == format) ? (format.toString().toLowerCase() + "x") : format.toString().toLowerCase());
/*     */     
/* 611 */     File file = null;
/* 612 */     file = new File(this.exportDirectory + fileName);
/* 613 */     logger.debug("create report file path:'{}'", file.getAbsolutePath());
/* 614 */     try (OutputStream out = new FileOutputStream(file)) {
/* 615 */       outputStream.writeTo(out);
/* 616 */     } catch (FileNotFoundException e) {
/* 617 */       logger.error("No such file:'{}'", file.getAbsolutePath());
/* 618 */       return null;
/* 619 */     } catch (IOException e) {
/* 620 */       logger.error("Something wrong while retrieving Export File, message:'{}'", e);
/* 621 */       return null;
/*     */     } 
/* 623 */     return fileName;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\service\ReportServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */