/*     */ package com.hwacom.ngtms.ao.fm.marshaller;
/*     */ 
/*     */ import com.hwacom.ngtms.ao.shared.PdConfigData1Day;
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
/*     */ public class PdConfigData1DayMarshaller {
/*  22 */   private static final Logger logger = LoggerFactory.getLogger(PdConfigData1DayMarshaller.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PdConfigData1Day forXml(String xml) {
/*  32 */     StringReader stringReader = new StringReader(xml);
/*     */     
/*  34 */     return (PdConfigData1Day)getXstream().fromXML(stringReader);
/*     */   }
/*     */   
/*     */   public String convertToXmlString(PdConfigData1Day pdConfigData1Day) {
/*  38 */     XStream xstream = getXstream();
/*  39 */     return xstream.toXML(pdConfigData1Day);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshalToXml(PdConfigData1Day pdConfigData1Day, String fileName) {
/*  47 */     try (Writer someWriter = new OutputStreamWriter(new FileOutputStream(fileName), 
/*  48 */           Charset.forName("UTF-8"))) {
/*  49 */       getXstream().toXML(pdConfigData1Day, someWriter);
/*  50 */     } catch (IOException e) {
/*  51 */       logger.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public PdConfigData1Day unMarshal(String fileName) {
/*  57 */     PdConfigData1Day pdConfigData1Day = new PdConfigData1Day();
/*     */     
/*  59 */     URI uri = URI.create(fileName);
/*  60 */     Path resourcePath = Paths.get(uri);
/*     */     
/*  62 */     try (InputStream is = Files.newInputStream(resourcePath, new java.nio.file.OpenOption[0])) {
/*  63 */       pdConfigData1Day = (PdConfigData1Day)getXstream().fromXML(is);
/*  64 */     } catch (Exception e) {
/*  65 */       logger.error(e.getMessage());
/*     */     } 
/*  67 */     return pdConfigData1Day;
/*     */   }
/*     */ 
/*     */   
/*     */   public PdConfigData1Day unMarshal(Path resourcePath) {
/*  72 */     PdConfigData1Day pdConfigData1Day = new PdConfigData1Day();
/*     */     
/*  74 */     try (InputStream is = Files.newInputStream(resourcePath, new java.nio.file.OpenOption[0])) {
/*  75 */       pdConfigData1Day = (PdConfigData1Day)getXstream().fromXML(is);
/*  76 */     } catch (Exception e) {
/*  77 */       logger.error(e.getMessage());
/*     */     } 
/*  79 */     return pdConfigData1Day;
/*     */   }
/*     */   
/*     */   public XStream getXstream() {
/*  83 */     XStream xstream = new XStream((HierarchicalStreamDriver)new DomDriver());
/*  84 */     xstream.useAttributeFor(PdConfigData1Day.class, "fileName");
/*  85 */     xstream.aliasAttribute(PdConfigData1Day.class, "fileName", "file_name");
/*  86 */     xstream.useAttributeFor(PdConfigData1Day.class, "controlCenterId");
/*  87 */     xstream.aliasAttribute(PdConfigData1Day.class, "controlCenterId", "control_center_id");
/*  88 */     xstream.useAttributeFor(PdConfigData1Day.class, "time");
/*  89 */     xstream.alias("file_attribute", PdConfigData1Day.class);
/*  90 */     xstream.aliasAttribute(PdConfigData1Day.class, "rooms", "oneday_pd_config_data");
/*  91 */     xstream.useAttributeFor(PdConfigData1Day.EngineRoom.class, "id");
/*  92 */     xstream.addImplicitCollection(PdConfigData1Day.EngineRoom.class, "pdList");
/*  93 */     xstream.alias("engine_rooms", PdConfigData1Day.EngineRoom.class);
/*  94 */     xstream.useAttributeFor(PdConfigData1Day.EngineRoom.PdConfig.class, "id");
/*  95 */     xstream.useAttributeFor(PdConfigData1Day.EngineRoom.PdConfig.class, "freewayId");
/*  96 */     xstream.aliasAttribute(PdConfigData1Day.EngineRoom.PdConfig.class, "freewayId", "freeway_id");
/*  97 */     xstream.useAttributeFor(PdConfigData1Day.EngineRoom.PdConfig.class, "directionId");
/*  98 */     xstream.aliasAttribute(PdConfigData1Day.EngineRoom.PdConfig.class, "directionId", "direction_id");
/*     */     
/* 100 */     xstream.useAttributeFor(PdConfigData1Day.EngineRoom.PdConfig.class, "milepost");
/* 101 */     xstream.useAttributeFor(PdConfigData1Day.EngineRoom.PdConfig.class, "odhId");
/* 102 */     xstream.aliasAttribute(PdConfigData1Day.EngineRoom.PdConfig.class, "odhId", "odh_id");
/* 103 */     xstream.useAttributeFor(PdConfigData1Day.EngineRoom.PdConfig.class, "odhIp");
/* 104 */     xstream.aliasAttribute(PdConfigData1Day.EngineRoom.PdConfig.class, "odhIp", "odh_ip");
/* 105 */     xstream.useAttributeFor(PdConfigData1Day.EngineRoom.PdConfig.class, "longitude");
/* 106 */     xstream.useAttributeFor(PdConfigData1Day.EngineRoom.PdConfig.class, "latitude");
/* 107 */     xstream.alias("pd", PdConfigData1Day.EngineRoom.PdConfig.class);
/* 108 */     xstream.useAttributeFor(PdConfigData1Day.EngineRoom.PdConfig.Loop.class, "id");
/* 109 */     xstream.alias("loop", PdConfigData1Day.EngineRoom.PdConfig.Loop.class);
/* 110 */     xstream.useAttributeFor(PdConfigData1Day.EngineRoom.PdConfig.Loop.Eq.class, "eqId");
/* 111 */     xstream.aliasAttribute(PdConfigData1Day.EngineRoom.PdConfig.Loop.Eq.class, "eqId", "eq_id");
/* 112 */     xstream.alias("eq", PdConfigData1Day.EngineRoom.PdConfig.Loop.Eq.class);
/* 113 */     return xstream;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\marshaller\PdConfigData1DayMarshaller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */