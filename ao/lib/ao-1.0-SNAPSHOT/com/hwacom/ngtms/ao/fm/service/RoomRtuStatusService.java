/*     */ package com.hwacom.ngtms.ao.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.ao.fm.model.NcuDeviceStatusRecord;
/*     */ import com.hwacom.ngtms.ao.fm.repository.NcuDeviceStatusRecordRespository;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcStatus;
/*     */ import com.hwacom.ngtms.rtu.fm.hz.RtuHzMap;
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
/*     */ public class RoomRtuStatusService
/*     */ {
/*  30 */   private static final Logger logger = LoggerFactory.getLogger(RoomRtuStatusService.class);
/*     */   @Autowired
/*     */   private NcuDeviceStatusRecordRespository ncuDeviceStatusRecordRespository;
/*     */   
/*     */   public void process() {
/*     */     try {
/*  36 */       IMap<String, DeviceTcConfig> deviceTcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  37 */       IMap<String, DeviceTcStatus> statusMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcStatus);
/*  38 */       IMap<String, Boolean> connectStatusMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusConnectStatus);
/*  39 */       Map<String, DeviceTcStatus> newStatusMap = new HashMap<>();
/*  40 */       List<NcuDeviceStatusRecord> statusRecords = new ArrayList<>();
/*  41 */       PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceType").equal("RTU");
/*  42 */       for (DeviceTcConfig config : deviceTcConfigMap.values((Predicate)pb)) {
/*  43 */         String deviceName = config.getDeviceName();
/*  44 */         if (statusMap.containsKey(deviceName)) {
/*  45 */           DeviceTcStatus latestStatus = (DeviceTcStatus)statusMap.get(deviceName);
/*  46 */           latestStatus.setLastUpdateStatus(latestStatus.getCommStatus());
/*  47 */           latestStatus.setLastUpdateTime(latestStatus.getTimestamp());
/*  48 */           latestStatus.setTimestamp(new Date());
/*  49 */           if (connectStatusMap.get(deviceName) == null) {
/*  50 */             latestStatus.setCommStatus(Integer.valueOf(1));
/*     */           }
/*  52 */           else if (((Boolean)connectStatusMap.get(deviceName)).booleanValue()) {
/*  53 */             latestStatus.setCommStatus(Integer.valueOf(0));
/*     */           } else {
/*  55 */             latestStatus.setCommStatus(Integer.valueOf(1));
/*     */           } 
/*     */           
/*  58 */           newStatusMap.put(deviceName, latestStatus);
/*     */           
/*  60 */           NcuDeviceStatusRecord ncuDeviceStatusRecord1 = this.ncuDeviceStatusRecordRespository.findTopByDeviceNameOrderByDataTimeDesc(deviceName);
/*  61 */           if (ncuDeviceStatusRecord1 != null) {
/*  62 */             if (ncuDeviceStatusRecord1.getStatusValue() == ((DeviceTcStatus)newStatusMap.get(deviceName)).getCommStatus()) {
/*     */               continue;
/*     */             }
/*  65 */             NcuDeviceStatusRecord ncuDeviceStatusRecord = new NcuDeviceStatusRecord();
/*  66 */             ncuDeviceStatusRecord.setDeviceName(deviceName);
/*  67 */             ncuDeviceStatusRecord.setDataTime(new Date());
/*  68 */             ncuDeviceStatusRecord.setStatusValue(((DeviceTcStatus)newStatusMap.get(deviceName)).getCommStatus());
/*  69 */             statusRecords.add(ncuDeviceStatusRecord);
/*     */             continue;
/*     */           } 
/*  72 */           NcuDeviceStatusRecord ncuDeviceStatusRecord2 = new NcuDeviceStatusRecord();
/*  73 */           ncuDeviceStatusRecord2.setDeviceName(deviceName);
/*  74 */           ncuDeviceStatusRecord2.setDataTime(new Date());
/*  75 */           ncuDeviceStatusRecord2.setStatusValue(((DeviceTcStatus)newStatusMap.get(deviceName)).getCommStatus());
/*  76 */           statusRecords.add(ncuDeviceStatusRecord2);
/*     */           continue;
/*     */         } 
/*  79 */         DeviceTcStatus newStatus = new DeviceTcStatus();
/*  80 */         newStatus.setId(deviceName);
/*  81 */         newStatus.setTimestamp(new Date());
/*  82 */         newStatus.setDeviceType(config.getDeviceType());
/*  83 */         if (connectStatusMap.get(deviceName) == null) {
/*  84 */           newStatus.setCommStatus(Integer.valueOf(1));
/*     */         }
/*  86 */         else if (((Boolean)connectStatusMap.get(deviceName)).booleanValue()) {
/*  87 */           newStatus.setCommStatus(Integer.valueOf(0));
/*     */         } else {
/*  89 */           newStatus.setCommStatus(Integer.valueOf(1));
/*     */         } 
/*     */         
/*  92 */         newStatusMap.put(deviceName, newStatus);
/*     */         
/*  94 */         NcuDeviceStatusRecord oldRecord = this.ncuDeviceStatusRecordRespository.findTopByDeviceNameOrderByDataTimeDesc(deviceName);
/*  95 */         if (oldRecord != null) {
/*  96 */           if (oldRecord.getStatusValue() == ((DeviceTcStatus)newStatusMap.get(deviceName)).getCommStatus()) {
/*     */             continue;
/*     */           }
/*  99 */           NcuDeviceStatusRecord ncuDeviceStatusRecord = new NcuDeviceStatusRecord();
/* 100 */           ncuDeviceStatusRecord.setDeviceName(deviceName);
/* 101 */           ncuDeviceStatusRecord.setDataTime(new Date());
/* 102 */           ncuDeviceStatusRecord.setStatusValue(((DeviceTcStatus)newStatusMap.get(deviceName)).getCommStatus());
/* 103 */           statusRecords.add(ncuDeviceStatusRecord);
/*     */           continue;
/*     */         } 
/* 106 */         NcuDeviceStatusRecord newRecord = new NcuDeviceStatusRecord();
/* 107 */         newRecord.setDeviceName(deviceName);
/* 108 */         newRecord.setDataTime(new Date());
/* 109 */         newRecord.setStatusValue(((DeviceTcStatus)newStatusMap.get(deviceName)).getCommStatus());
/* 110 */         statusRecords.add(newRecord);
/*     */       } 
/*     */ 
/*     */       
/* 114 */       this.ncuDeviceStatusRecordRespository.saveAll(statusRecords);
/* 115 */       statusMap.putAll(newStatusMap);
/* 116 */     } catch (Exception e) {
/* 117 */       logger.error("process RoomCardReaderStatus failed.", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\RoomRtuStatusService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */