/*     */ package com.hwacom.ngtms.ao.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.ao.fm.marshaller.PdConfigData1DayMarshaller;
/*     */ import com.hwacom.ngtms.ao.shared.PdConfigData1Day;
/*     */ import com.hwacom.ngtms.ao.util.FileUtils;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceLocationMappingConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.shared.Direction;
/*     */ import com.hwacom.ngtms.pd.fm.hz.PdHzMap;
/*     */ import com.hwacom.ngtms.pd.fm.model.PdConfig;
/*     */ import com.hwacom.ngtms.pd.fm.model.PdLoopDeviceConfig;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service
/*     */ public class PdConfigDataProcessor {
/*  36 */   private static final Logger logger = LoggerFactory.getLogger(PdConfigDataProcessor.class);
/*     */   
/*     */   @Autowired
/*     */   Environment env;
/*     */   private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
/*     */   
/*     */   public void process() {
/*  43 */     createXmlData(getPdConfigData());
/*     */   }
/*     */   
/*     */   public void createXmlData(PdConfigData1Day pdConfigData1Day) {
/*  47 */     PdConfigData1DayMarshaller marshaller = new PdConfigData1DayMarshaller();
/*  48 */     String text = marshaller.convertToXmlString(pdConfigData1Day);
/*  49 */     StringBuilder textSb = new StringBuilder();
/*  50 */     textSb.insert(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
/*  51 */     textSb.append(text);
/*     */     
/*  53 */     String outputDir = (String)this.env.getProperty("dir.output", String.class, "D:/xml");
/*  54 */     String fileName = (String)this.env.getProperty("xml.pdConfig", String.class, "1day_pd_config_data.xml");
/*  55 */     String sourceFileName = outputDir + File.separator + fileName;
/*     */     try {
/*  57 */       FileUtils.writeFile(sourceFileName, textSb.toString().replaceAll("__", "_"));
/*  58 */     } catch (IOException e) {
/*  59 */       logger.error("write pd config data failed", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private PdConfigData1Day getPdConfigData() {
/*  64 */     PdConfigData1Day data = new PdConfigData1Day();
/*  65 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */     try {
/*  67 */       logger.debug("Get Pd config data.");
/*  68 */       IMap<String, PdConfig> pdConfigMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.Config);
/*  69 */       IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*     */       
/*  71 */       IMap<Long, PdLoopDeviceConfig> pdLoopDeviceConfigMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.LoopDeviceConfig);
/*  72 */       data.setTime(dateFormat.format(new Date()));
/*  73 */       data.setFileName("1day_pd_config_data.xml");
/*  74 */       data.setControlCenterId("Central");
/*  75 */       Map<String, List<String>> roomMap = new HashMap<>();
/*  76 */       for (PdConfig pdConfig : pdConfigMap.values()) {
/*  77 */         List<String> list = new ArrayList<>();
/*  78 */         if (roomMap.get(getLocationName(pdConfig.getDeviceName())) != null) {
/*  79 */           list = roomMap.get(getLocationName(pdConfig.getDeviceName()));
/*     */         }
/*  81 */         list.add(pdConfig.getDeviceName());
/*  82 */         roomMap.put(getLocationName(pdConfig.getDeviceName()), list);
/*     */       } 
/*     */       
/*  85 */       List<PdConfigData1Day.EngineRoom> rooms = new ArrayList<>();
/*  86 */       for (Map.Entry<String, List<String>> each : roomMap.entrySet()) {
/*  87 */         PdConfigData1Day.EngineRoom room = new PdConfigData1Day.EngineRoom();
/*  88 */         room.setId(each.getKey());
/*  89 */         List<PdConfigData1Day.EngineRoom.PdConfig> pds = new ArrayList<>();
/*  90 */         for (String pdDevice : each.getValue()) {
/*  91 */           PdConfigData1Day.EngineRoom.PdConfig pd = new PdConfigData1Day.EngineRoom.PdConfig();
/*  92 */           DeviceTcConfig config = (DeviceTcConfig)deviceConfigMap.get(pdDevice);
/*  93 */           pd.setId(pdDevice);
/*  94 */           if (config != null) {
/*  95 */             pd.setDirectionId(getDirectionId(config.getDirection()));
/*  96 */             pd.setMilepost(String.valueOf(config.getMilepost()));
/*  97 */             pd.setOdhId("");
/*  98 */             pd.setOdhIp("");
/*  99 */             pd.setLatitude(String.valueOf(config.getLatitude()));
/* 100 */             pd.setLongitude(String.valueOf(config.getLongitude()));
/* 101 */             pd.setFreewayId(getLineIdToValue(config.getLineId()));
/* 102 */             List<PdConfigData1Day.EngineRoom.PdConfig.Loop> loops = new ArrayList<>();
/* 103 */             for (int i = 1; i <= ((PdConfig)pdConfigMap.get(pdDevice)).getLoopNo().intValue(); i++) {
/* 104 */               PdConfigData1Day.EngineRoom.PdConfig.Loop loop = new PdConfigData1Day.EngineRoom.PdConfig.Loop();
/* 105 */               loop.setId(String.valueOf(i));
/* 106 */               List<PdConfigData1Day.EngineRoom.PdConfig.Loop.Eq> eqs = new ArrayList<>();
/* 107 */               EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*     */               
/* 109 */               PredicateBuilder pb = eo.get("pdDeviceName").equal(pdDevice).and((Predicate)eo.get("loopNo").equal(Integer.valueOf(i)));
/* 110 */               for (PdLoopDeviceConfig loopDeviceConfig : pdLoopDeviceConfigMap.values((Predicate)pb)) {
/* 111 */                 PdConfigData1Day.EngineRoom.PdConfig.Loop.Eq eq = new PdConfigData1Day.EngineRoom.PdConfig.Loop.Eq();
/* 112 */                 eq.setEqId(loopDeviceConfig.getDeviceName());
/* 113 */                 eqs.add(eq);
/*     */               } 
/* 115 */               loop.setEqs(eqs);
/* 116 */               loops.add(loop);
/*     */             } 
/* 118 */             pd.setLoops(loops);
/*     */           } 
/* 120 */           pds.add(pd);
/*     */         } 
/* 122 */         room.setPd(pds);
/* 123 */         rooms.add(room);
/*     */       } 
/* 125 */       data.setRooms(rooms);
/*     */     }
/* 127 */     catch (RuntimeException e) {
/* 128 */       logger.error("Get Pd config data failed.", e);
/*     */     } 
/* 130 */     return data;
/*     */   }
/*     */   
/*     */   private String getDirectionId(Direction direction) {
/* 134 */     if (direction == null) {
/* 135 */       return "";
/*     */     }
/*     */     
/* 138 */     switch (direction) {
/*     */       
/*     */       case E:
/* 141 */         return "1";
/*     */       
/*     */       case W:
/* 144 */         return "2";
/*     */       
/*     */       case S:
/* 147 */         return "3";
/*     */       
/*     */       case N:
/* 150 */         return "4";
/*     */       
/*     */       case EW:
/* 153 */         return "5";
/*     */       
/*     */       case NS:
/* 156 */         return "6";
/*     */     } 
/* 158 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   private String getLineIdToValue(String lineId) {
/* 163 */     if (lineId == null) {
/* 164 */       return "";
/*     */     }
/*     */     
/* 167 */     switch (lineId) {
/*     */       case "N1":
/* 169 */         return "1";
/*     */       case "N3":
/* 171 */         return "3";
/*     */       case "N4":
/* 173 */         return "4";
/*     */       case "N6":
/* 175 */         return "6";
/*     */       case "T72":
/* 177 */         return "72";
/*     */       case "T74":
/* 179 */         return "74";
/*     */       case "T74甲":
/* 181 */         return "74a";
/*     */       case "T76":
/* 183 */         return "76";
/*     */       case "T78":
/* 185 */         return "78";
/*     */     } 
/* 187 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getLocationName(String deviceName) {
/* 193 */     IMap<String, DeviceLocationMappingConfig> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/*     */     
/* 195 */     PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceName").equal(deviceName);
/* 196 */     Iterator<DeviceLocationMappingConfig> iterator = map.values((Predicate)pb).iterator(); if (iterator.hasNext()) { DeviceLocationMappingConfig each = iterator.next();
/* 197 */       return each.getLocationName(); }
/*     */     
/* 199 */     return "";
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\PdConfigDataProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */