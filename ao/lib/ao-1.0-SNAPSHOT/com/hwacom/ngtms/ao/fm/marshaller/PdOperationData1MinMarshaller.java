/*     */ package com.hwacom.ngtms.ao.fm.marshaller;
/*     */ 
/*     */ import com.hwacom.ngtms.ao.shared.PdOperationData1Min;
/*     */ import com.thoughtworks.xstream.XStream;
/*     */ import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
/*     */ import com.thoughtworks.xstream.io.xml.DomDriver;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.StringReader;
/*     */ import java.io.Writer;
/*     */ import java.net.URI;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public class PdOperationData1MinMarshaller {
/*  22 */   private static final Logger logger = LoggerFactory.getLogger(PdOperationData1MinMarshaller.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdOperationData1Min forXml(String xml) {
/*  32 */     StringReader stringReader = new StringReader(xml);
/*     */     
/*  34 */     return (PdOperationData1Min)getXstream().fromXML(stringReader);
/*     */   }
/*     */   
/*     */   public String convertToXmlString(PdOperationData1Min pdOperationData1Min) {
/*  38 */     XStream xstream = getXstream();
/*  39 */     return xstream.toXML(pdOperationData1Min);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshalToXml(PdOperationData1Min pdOperationData1Min, String fileName) {
/*  47 */     try (Writer someWriter = new OutputStreamWriter(new FileOutputStream(fileName), 
/*  48 */           Charset.forName("UTF-8"))) {
/*  49 */       getXstream().toXML(pdOperationData1Min, someWriter);
/*  50 */     } catch (IOException e) {
/*  51 */       logger.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public PdOperationData1Min unMarshal(String fileName) {
/*  57 */     PdOperationData1Min pdOperationData1Min = new PdOperationData1Min();
/*     */     
/*  59 */     URI uri = URI.create(fileName);
/*  60 */     Path resourcePath = Paths.get(uri);
/*     */     
/*  62 */     try (InputStream is = Files.newInputStream(resourcePath, new java.nio.file.OpenOption[0])) {
/*  63 */       pdOperationData1Min = (PdOperationData1Min)getXstream().fromXML(is);
/*  64 */     } catch (Exception e) {
/*  65 */       logger.error(e.getMessage());
/*     */     } 
/*  67 */     return pdOperationData1Min;
/*     */   }
/*     */ 
/*     */   
/*     */   public PdOperationData1Min unMarshal(Path resourcePath) {
/*  72 */     PdOperationData1Min pdOperationData1Min = new PdOperationData1Min();
/*     */     
/*  74 */     try (InputStream is = Files.newInputStream(resourcePath, new java.nio.file.OpenOption[0])) {
/*  75 */       pdOperationData1Min = (PdOperationData1Min)getXstream().fromXML(is);
/*  76 */     } catch (Exception e) {
/*  77 */       logger.error(e.getMessage());
/*     */     } 
/*  79 */     return pdOperationData1Min;
/*     */   }
/*     */   
/*     */   public XStream getXstream() {
/*  83 */     XStream xstream = new XStream((HierarchicalStreamDriver)new DomDriver());
/*  84 */     xstream.useAttributeFor(PdOperationData1Min.class, "fileName");
/*  85 */     xstream.aliasField("filename", PdOperationData1Min.class, "fileName");
/*     */     
/*  87 */     xstream.useAttributeFor(PdOperationData1Min.class, "controlCenterId");
/*     */     
/*  89 */     xstream.useAttributeFor(PdOperationData1Min.class, "time");
/*  90 */     xstream.alias("file_attribute", PdOperationData1Min.class);
/*  91 */     xstream.aliasAttribute(PdOperationData1Min.class, "pdList", "onemin_pd_operation_data");
/*  92 */     xstream.useAttributeFor(PdOperationData1Min.Pd.class, "id");
/*  93 */     xstream.useAttributeFor(PdOperationData1Min.Pd.class, "pdCommStatus");
/*  94 */     xstream.alias("pd", PdOperationData1Min.Pd.class);
/*  95 */     xstream.useAttributeFor(PdOperationData1Min.Pd.Primary.class, "r");
/*  96 */     xstream.useAttributeFor(PdOperationData1Min.Pd.Primary.class, "s");
/*  97 */     xstream.useAttributeFor(PdOperationData1Min.Pd.Primary.class, "t");
/*  98 */     xstream.alias("primary", PdOperationData1Min.Pd.Primary.class);
/*  99 */     xstream.useAttributeFor(PdOperationData1Min.Pd.Secondary.class, "r");
/* 100 */     xstream.useAttributeFor(PdOperationData1Min.Pd.Secondary.class, "s");
/* 101 */     xstream.useAttributeFor(PdOperationData1Min.Pd.Secondary.class, "t");
/* 102 */     xstream.alias("secondary", PdOperationData1Min.Pd.Secondary.class);
/* 103 */     xstream.useAttributeFor(PdOperationData1Min.Pd.LoopList.class, "capacity");
/* 104 */     xstream.addImplicitCollection(PdOperationData1Min.Pd.LoopList.class, "loops");
/* 105 */     xstream.useAttributeFor(PdOperationData1Min.Pd.Loop.class, "id");
/* 106 */     xstream.useAttributeFor(PdOperationData1Min.Pd.Loop.class, "usage");
/* 107 */     xstream.alias("loop", PdOperationData1Min.Pd.Loop.class);
/* 108 */     return xstream;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\marshaller\PdOperationData1MinMarshaller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */