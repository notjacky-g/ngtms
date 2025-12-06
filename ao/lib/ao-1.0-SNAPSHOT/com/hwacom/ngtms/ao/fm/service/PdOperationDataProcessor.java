/*     */ package com.hwacom.ngtms.ao.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.ao.fm.marshaller.PdOperationData1MinMarshaller;
/*     */ import com.hwacom.ngtms.ao.shared.PdOperationData1Min;
/*     */ import com.hwacom.ngtms.ao.util.FileUtils;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.pd.fm.hz.PdHzMap;
/*     */ import com.hwacom.ngtms.pd.fm.model.PdConfig;
/*     */ import com.hwacom.ngtms.pd.fm.model.PdLoopStatus;
/*     */ import com.hwacom.ngtms.pd.fm.model.PdStatus;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class PdOperationDataProcessor
/*     */ {
/*  32 */   private static final Logger logger = LoggerFactory.getLogger(PdOperationDataProcessor.class);
/*     */   
/*     */   @Autowired
/*     */   Environment env;
/*     */   private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
/*     */   
/*     */   public void process() {
/*  39 */     createXmlData(getPdOperationData());
/*     */   }
/*     */   
/*     */   public void createXmlData(PdOperationData1Min pdOperationData1Min) {
/*  43 */     PdOperationData1MinMarshaller marshaller = new PdOperationData1MinMarshaller();
/*  44 */     String text = marshaller.convertToXmlString(pdOperationData1Min);
/*  45 */     StringBuilder textSb = new StringBuilder();
/*  46 */     textSb.insert(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
/*  47 */     textSb.append(text);
/*     */     
/*  49 */     String outputDir = (String)this.env.getProperty("dir.output", String.class, "D:/xml");
/*     */     
/*  51 */     String fileName = (String)this.env.getProperty("xml.pdOperation", String.class, "1min_pd_operation_data.xml");
/*  52 */     String sourceFileName = outputDir + File.separator + fileName;
/*     */     try {
/*  54 */       FileUtils.writeFile(sourceFileName, textSb.toString().replaceAll("__", "_"));
/*  55 */     } catch (IOException e) {
/*  56 */       logger.error("write pd operation data failed", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public PdOperationData1Min getPdOperationData() {
/*  61 */     PdOperationData1Min data = new PdOperationData1Min();
/*  62 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */     try {
/*  64 */       logger.debug("Get Pd operation data.");
/*  65 */       IMap<String, PdConfig> pdConfigMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.Config);
/*  66 */       IMap<String, PdStatus> pdStatusMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.Status);
/*  67 */       data.setTime(dateFormat.format(new Date()));
/*  68 */       data.setFileName("1min_pd_operation_data.xml");
/*  69 */       data.setControlCenterId("Central");
/*  70 */       List<PdOperationData1Min.Pd> list = new ArrayList<>();
/*  71 */       for (PdStatus status : pdStatusMap.values()) {
/*  72 */         PdOperationData1Min.Pd pd = new PdOperationData1Min.Pd();
/*  73 */         pd.setId(status.getDeviceName());
/*  74 */         pd.setPdCommonStatus(String.valueOf(status.getConnectivity()));
/*  75 */         PdOperationData1Min.Pd.Primary p = new PdOperationData1Min.Pd.Primary();
/*  76 */         p.setR((status.getPrimaryR() != null) ? String.valueOf(status.getPrimaryR()) : "1");
/*  77 */         p.setS((status.getPrimaryS() != null) ? String.valueOf(status.getPrimaryS()) : "1");
/*  78 */         p.setT((status.getPrimaryT() != null) ? String.valueOf(status.getPrimaryT()) : "1");
/*  79 */         pd.setPrimary(p);
/*  80 */         PdOperationData1Min.Pd.Secondary s = new PdOperationData1Min.Pd.Secondary();
/*  81 */         s.setR((status.getSecondaryR() != null) ? String.valueOf(status.getSecondaryR()) : "1");
/*  82 */         s.setS((status.getSecondaryS() != null) ? String.valueOf(status.getSecondaryS()) : "1");
/*  83 */         s.setT((status.getSecondaryT() != null) ? String.valueOf(status.getSecondaryT()) : "1");
/*  84 */         pd.setSecondary(s);
/*  85 */         PdOperationData1Min.Pd.LoopList loopList = new PdOperationData1Min.Pd.LoopList();
/*  86 */         List<PdOperationData1Min.Pd.Loop> loops = new ArrayList<>();
/*     */         
/*  88 */         loopList.setCapacity(String.valueOf(((PdConfig)pdConfigMap.get(status.getDeviceName())).getLoopNo()));
/*  89 */         for (int i = 1; i <= ((PdConfig)pdConfigMap.get(status.getDeviceName())).getLoopNo().intValue(); i++) {
/*  90 */           PdOperationData1Min.Pd.Loop loop = new PdOperationData1Min.Pd.Loop();
/*  91 */           loop.setId(String.valueOf(i));
/*  92 */           if (status.getConnectivity().intValue() == 0) {
/*  93 */             for (PdLoopStatus loopStatus : status.getPdLoops()) {
/*  94 */               if (loopStatus.getLoopId().equals(loop.getId())) {
/*  95 */                 loop.setUsage(String.valueOf(loopStatus.getStatus()));
/*     */               }
/*     */             } 
/*     */           } else {
/*  99 */             loop.setUsage("1");
/*     */           } 
/* 101 */           loops.add(loop);
/*     */         } 
/* 103 */         loopList.setLoops(loops);
/* 104 */         pd.setLoopList(loopList);
/* 105 */         list.add(pd);
/*     */       } 
/* 107 */       data.setPdList(list);
/* 108 */     } catch (RuntimeException e) {
/* 109 */       logger.error("Get Pd operation data failed.", e);
/*     */     } 
/* 111 */     return data;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\PdOperationDataProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */