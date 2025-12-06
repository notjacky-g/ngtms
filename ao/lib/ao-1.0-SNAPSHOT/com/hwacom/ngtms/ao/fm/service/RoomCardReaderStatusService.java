/*     */ package com.hwacom.ngtms.ao.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.ao.fm.hz.AoHzMap;
/*     */ import com.hwacom.ngtms.ao.fm.model.NcuCardReaderMappingConfig;
/*     */ import com.hwacom.ngtms.ao.fm.model.NcuDeviceStatusRecord;
/*     */ import com.hwacom.ngtms.ao.fm.repository.NcuDeviceStatusRecordRespository;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcStatus;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class RoomCardReaderStatusService
/*     */ {
/*  31 */   private static final Logger logger = LoggerFactory.getLogger(RoomCardReaderStatusService.class);
/*     */   
/*     */   @Autowired
/*     */   HunDureNcuServiceImpl hunDureNCUServiceImpl;
/*     */   
/*     */   public void process() {
/*     */     try {
/*  38 */       IMap<String, DeviceTcConfig> deviceTcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*     */       
/*  40 */       IMap<String, NcuCardReaderMappingConfig> cardReaderMappingMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.NcuCardReaderMappingConfig);
/*  41 */       IMap<String, DeviceTcStatus> statusMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcStatus);
/*  42 */       Map<String, DeviceTcStatus> newStatusMap = new HashMap<>();
/*  43 */       List<NcuDeviceStatusRecord> statusRecords = new ArrayList<>();
/*  44 */       PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceType").equal("NCU");
/*  45 */       for (DeviceTcConfig config : deviceTcConfigMap.values((Predicate)pb)) {
/*     */         try {
/*  47 */           String deviceName = config.getDeviceName();
/*     */           
/*  49 */           PredicateBuilder pb2 = (new PredicateBuilder()).getEntryObject().get("ncuId").equal(deviceName);
/*  50 */           if (this.hunDureNCUServiceImpl.checkNCUConnect(deviceName).booleanValue()) {
/*  51 */             if (cardReaderMappingMap.values((Predicate)pb2) != null) {
/*  52 */               for (NcuCardReaderMappingConfig mappingConfig : cardReaderMappingMap.values((Predicate)pb2)) {
/*  53 */                 if (statusMap.containsKey(mappingConfig.getControlId())) {
/*  54 */                   DeviceTcStatus latestStatus = (DeviceTcStatus)statusMap.get(mappingConfig.getControlId());
/*  55 */                   latestStatus.setLastUpdateStatus(latestStatus.getCommStatus());
/*  56 */                   latestStatus.setLastUpdateTime(latestStatus.getTimestamp());
/*  57 */                   latestStatus.setTimestamp(new Date());
/*  58 */                   latestStatus.setCommStatus(Integer.valueOf(0));
/*  59 */                   newStatusMap.put(mappingConfig.getControlId(), latestStatus); continue;
/*     */                 } 
/*  61 */                 DeviceTcStatus deviceTcStatus1 = new DeviceTcStatus();
/*  62 */                 deviceTcStatus1.setId(mappingConfig.getControlId());
/*  63 */                 deviceTcStatus1.setTimestamp(new Date());
/*  64 */                 deviceTcStatus1.setDeviceType("cardReader");
/*  65 */                 deviceTcStatus1.setCommStatus(Integer.valueOf(0));
/*  66 */                 newStatusMap.put(mappingConfig.getControlId(), deviceTcStatus1);
/*     */               } 
/*     */             }
/*     */             
/*  70 */             if (statusMap.containsKey(deviceName)) {
/*  71 */               DeviceTcStatus latestStatus = (DeviceTcStatus)statusMap.get(deviceName);
/*     */               
/*  73 */               if (latestStatus.getCommStatus() != latestStatus.getCommStatus()) {
/*  74 */                 NcuDeviceStatusRecord newRecord = new NcuDeviceStatusRecord();
/*  75 */                 newRecord.setDeviceName(deviceName);
/*  76 */                 newRecord.setDataTime(new Date());
/*  77 */                 newRecord.setStatusValue(latestStatus.getCommStatus());
/*  78 */                 statusRecords.add(newRecord);
/*     */               } 
/*  80 */               latestStatus.setLastUpdateStatus(latestStatus.getCommStatus());
/*  81 */               latestStatus.setLastUpdateTime(latestStatus.getTimestamp());
/*  82 */               latestStatus.setTimestamp(new Date());
/*  83 */               latestStatus.setCommStatus(Integer.valueOf(0));
/*  84 */               newStatusMap.put(deviceName, latestStatus); continue;
/*     */             } 
/*  86 */             DeviceTcStatus deviceTcStatus = new DeviceTcStatus();
/*  87 */             deviceTcStatus.setId(deviceName);
/*  88 */             deviceTcStatus.setTimestamp(new Date());
/*  89 */             deviceTcStatus.setDeviceType(config.getDeviceType());
/*  90 */             deviceTcStatus.setCommStatus(Integer.valueOf(0));
/*  91 */             newStatusMap.put(deviceName, deviceTcStatus);
/*     */             continue;
/*     */           } 
/*  94 */           if (cardReaderMappingMap.values((Predicate)pb2) != null) {
/*  95 */             for (NcuCardReaderMappingConfig mappingConfig : cardReaderMappingMap.values((Predicate)pb2)) {
/*  96 */               if (statusMap.containsKey(mappingConfig.getControlId())) {
/*  97 */                 DeviceTcStatus latestStatus = (DeviceTcStatus)statusMap.get(mappingConfig.getControlId());
/*  98 */                 latestStatus.setLastUpdateStatus(latestStatus.getCommStatus());
/*  99 */                 latestStatus.setLastUpdateTime(latestStatus.getTimestamp());
/* 100 */                 latestStatus.setTimestamp(new Date());
/* 101 */                 latestStatus.setCommStatus(Integer.valueOf(1));
/* 102 */                 newStatusMap.put(mappingConfig.getControlId(), latestStatus); continue;
/*     */               } 
/* 104 */               DeviceTcStatus deviceTcStatus = new DeviceTcStatus();
/* 105 */               deviceTcStatus.setId(mappingConfig.getControlId());
/* 106 */               deviceTcStatus.setTimestamp(new Date());
/* 107 */               deviceTcStatus.setDeviceType("cardReader");
/* 108 */               deviceTcStatus.setCommStatus(Integer.valueOf(1));
/* 109 */               newStatusMap.put(mappingConfig.getControlId(), deviceTcStatus);
/*     */             } 
/*     */           }
/*     */           
/* 113 */           if (statusMap.containsKey(deviceName)) {
/* 114 */             DeviceTcStatus latestStatus = (DeviceTcStatus)statusMap.get(deviceName);
/*     */             
/* 116 */             if (latestStatus.getCommStatus() != latestStatus.getCommStatus()) {
/* 117 */               NcuDeviceStatusRecord newRecord = new NcuDeviceStatusRecord();
/* 118 */               newRecord.setDeviceName(deviceName);
/* 119 */               newRecord.setDataTime(new Date());
/* 120 */               newRecord.setStatusValue(latestStatus.getCommStatus());
/* 121 */               statusRecords.add(newRecord);
/*     */             } 
/* 123 */             latestStatus.setLastUpdateStatus(latestStatus.getCommStatus());
/* 124 */             latestStatus.setLastUpdateTime(latestStatus.getTimestamp());
/* 125 */             latestStatus.setTimestamp(new Date());
/* 126 */             latestStatus.setCommStatus(Integer.valueOf(1));
/* 127 */             newStatusMap.put(deviceName, latestStatus); continue;
/*     */           } 
/* 129 */           DeviceTcStatus newStatus = new DeviceTcStatus();
/* 130 */           newStatus.setId(deviceName);
/* 131 */           newStatus.setTimestamp(new Date());
/* 132 */           newStatus.setDeviceType(config.getDeviceType());
/* 133 */           newStatus.setCommStatus(Integer.valueOf(1));
/* 134 */           newStatusMap.put(deviceName, newStatus);
/*     */         
/*     */         }
/* 137 */         catch (Exception e) {
/* 138 */           logger.error("judge Ncu commStatus failed. deviceName:'{}'", config.getDeviceName(), e);
/*     */         } 
/*     */       } 
/* 141 */       this.ncuDeviceStatusRecordRespository.saveAll(statusRecords);
/* 142 */       statusMap.putAll(newStatusMap);
/* 143 */     } catch (Exception e) {
/* 144 */       logger.error("process RoomCardReaderStatus failed.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Autowired
/*     */   private NcuDeviceStatusRecordRespository ncuDeviceStatusRecordRespository;
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\RoomCardReaderStatusService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */