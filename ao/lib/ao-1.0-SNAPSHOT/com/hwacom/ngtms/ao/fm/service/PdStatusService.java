/*     */ package com.hwacom.ngtms.ao.fm.service;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.ao.fm.model.PdDeviceStatusRecord;
/*     */ import com.hwacom.ngtms.ao.fm.repository.PdDeviceStatusRecordRepository;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcStatus;
/*     */ import com.hwacom.ngtms.pd.fm.hz.PdHzMap;
/*     */ import com.hwacom.ngtms.pd.fm.model.PdLoopStatus;
/*     */ import com.hwacom.ngtms.pd.fm.model.PdStatus;
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
/*     */ @Service
/*     */ public class PdStatusService {
/*  29 */   private static final Logger logger = LoggerFactory.getLogger(PdStatusService.class);
/*     */   @Autowired
/*     */   private PdDeviceStatusRecordRepository pdDeviceStatusRecordRepository;
/*     */   
/*     */   public void process() {
/*  34 */     IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/*  35 */     IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  36 */     IMap<String, DeviceTcStatus> deviceStatusMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcStatus);
/*  37 */     IMap<String, PdStatus> statusMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.Status);
/*     */     try {
/*  39 */       EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*  40 */       PredicateBuilder pb = eo.get("deviceType").equal("PD");
/*  41 */       Map<String, DeviceTcStatus> localStatusMap = new HashMap<>();
/*  42 */       Map<String, PdStatus> localPdStatusMap = new HashMap<>();
/*  43 */       List<PdDeviceStatusRecord> recordList = new ArrayList<>();
/*  44 */       for (DeviceTcConfig device : deviceConfigMap.values((Predicate)pb)) {
/*  45 */         String pdDeviceName = device.getDeviceName();
/*  46 */         EntryObject entryObject = (new PredicateBuilder()).getEntryObject();
/*  47 */         PredicateBuilder predicateBuilder = entryObject.get("memo").equal(pdDeviceName);
/*  48 */         DeviceTcStatus tcStatus = new DeviceTcStatus();
/*  49 */         if (deviceStatusMap.get(pdDeviceName) == null) {
/*  50 */           tcStatus.setId(pdDeviceName);
/*  51 */           tcStatus.setDeviceType("PD");
/*     */         } else {
/*  53 */           tcStatus = (DeviceTcStatus)deviceStatusMap.get(pdDeviceName);
/*     */         } 
/*  55 */         List<PdLoopStatus> loopStatusList = new ArrayList<>();
/*  56 */         PdStatus status = new PdStatus();
/*  57 */         status.setDataTime(new Date());
/*  58 */         Boolean connectivity = Boolean.valueOf(true);
/*  59 */         for (DeviceTcConfig statusConfig : deviceConfigMap.values((Predicate)predicateBuilder)) {
/*  60 */           String pdStatusName = statusConfig.getDeviceName();
/*  61 */           if (dataMap.get(pdStatusName) == null || ((Integer)dataMap.get(pdStatusName)).intValue() == -1) {
/*  62 */             connectivity = Boolean.valueOf(false); continue;
/*     */           } 
/*  64 */           if (pdStatusName.contains("primaryR")) {
/*  65 */             status.setPrimaryR(checkValue(pdStatusName)); continue;
/*  66 */           }  if (pdStatusName.contains("primaryS")) {
/*  67 */             status.setPrimaryS(checkValue(pdStatusName)); continue;
/*  68 */           }  if (pdStatusName.contains("primaryT")) {
/*  69 */             status.setPrimaryT(checkValue(pdStatusName)); continue;
/*  70 */           }  if (pdStatusName.contains("secondaryR")) {
/*  71 */             status.setSecondaryR(checkValue(pdStatusName)); continue;
/*  72 */           }  if (pdStatusName.contains("secondaryS")) {
/*  73 */             status.setSecondaryS(checkValue(pdStatusName)); continue;
/*  74 */           }  if (pdStatusName.contains("secondaryT")) {
/*  75 */             status.setSecondaryT(checkValue(pdStatusName)); continue;
/*  76 */           }  if (pdStatusName.contains("doorOpen")) {
/*  77 */             status.setDoorOpen(checkValue(pdStatusName)); continue;
/*  78 */           }  if (pdStatusName.contains("loop1Status")) {
/*  79 */             PdLoopStatus loopStatus = new PdLoopStatus();
/*  80 */             loopStatus.setId(pdDeviceName + "-1");
/*  81 */             loopStatus.setLoopId("1");
/*  82 */             loopStatus.setStatus(checkValue(pdStatusName));
/*  83 */             loopStatusList.add(loopStatus); continue;
/*  84 */           }  if (pdStatusName.contains("loop2Status")) {
/*  85 */             PdLoopStatus loopStatus = new PdLoopStatus();
/*  86 */             loopStatus.setId(pdDeviceName + "-2");
/*  87 */             loopStatus.setLoopId("2");
/*  88 */             loopStatus.setStatus(checkValue(pdStatusName));
/*  89 */             loopStatusList.add(loopStatus); continue;
/*  90 */           }  if (pdStatusName.contains("loop3Status")) {
/*  91 */             PdLoopStatus loopStatus = new PdLoopStatus();
/*  92 */             loopStatus.setId(pdDeviceName + "-3");
/*  93 */             loopStatus.setLoopId("3");
/*  94 */             loopStatus.setStatus(checkValue(pdStatusName));
/*  95 */             loopStatusList.add(loopStatus); continue;
/*  96 */           }  if (pdStatusName.contains("loop4Status")) {
/*  97 */             PdLoopStatus loopStatus = new PdLoopStatus();
/*  98 */             loopStatus.setId(pdDeviceName + "-4");
/*  99 */             loopStatus.setLoopId("4");
/* 100 */             loopStatus.setStatus(checkValue(pdStatusName));
/* 101 */             loopStatusList.add(loopStatus); continue;
/* 102 */           }  if (pdStatusName.contains("loop5Status")) {
/* 103 */             PdLoopStatus loopStatus = new PdLoopStatus();
/* 104 */             loopStatus.setId(pdDeviceName + "-5");
/* 105 */             loopStatus.setLoopId("5");
/* 106 */             loopStatus.setStatus(checkValue(pdStatusName));
/* 107 */             loopStatusList.add(loopStatus);
/*     */           } 
/*     */         } 
/*     */         
/* 111 */         status.setPdLoops(loopStatusList);
/* 112 */         status.setConnectivity(Integer.valueOf(!connectivity.booleanValue() ? 1 : 0));
/* 113 */         tcStatus.setCommStatus(Integer.valueOf(!connectivity.booleanValue() ? 1 : 0));
/* 114 */         tcStatus.setLastUpdateStatus(Integer.valueOf(0));
/* 115 */         tcStatus.setLastUpdateTime(new Date());
/* 116 */         status.setDeviceName(pdDeviceName);
/*     */         
/* 118 */         if (statusMap.get(device.getDeviceName()) != null) {
/* 119 */           PdStatus oldStatus = (PdStatus)statusMap.get(device.getDeviceName());
/* 120 */           if (checkStatus(status, oldStatus)) {
/* 121 */             recordList.add(
/* 122 */                 saveRecord(pdDeviceName, 
/*     */                   
/* 124 */                   Integer.valueOf(!connectivity.booleanValue() ? 1 : 0), status
/* 125 */                   .getDoorOpen(), status
/* 126 */                   .getPrimaryR(), status
/* 127 */                   .getPrimaryS(), status
/* 128 */                   .getPrimaryT(), status
/* 129 */                   .getSecondaryR(), status
/* 130 */                   .getSecondaryS(), status
/* 131 */                   .getSecondaryT(), loopStatusList, new Date()));
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 136 */         localStatusMap.put(pdDeviceName, tcStatus);
/* 137 */         localPdStatusMap.put(pdDeviceName, status);
/*     */       } 
/*     */       
/* 140 */       deviceStatusMap.putAll(localStatusMap);
/* 141 */       statusMap.putAll(localPdStatusMap);
/* 142 */       this.pdDeviceStatusRecordRepository.saveAll(recordList);
/* 143 */     } catch (Exception e) {
/* 144 */       logger.error("PdStatusService process failed.", e);
/*     */     } 
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
/*     */   private PdDeviceStatusRecord saveRecord(String deviceName, Integer connectivity, Integer doorOpen, Integer primaryR, Integer primaryS, Integer primaryT, Integer secondaryR, Integer secondaryS, Integer secondaryT, List<PdLoopStatus> loopStatusList, Date dataTime) {
/* 160 */     PdDeviceStatusRecord record = new PdDeviceStatusRecord();
/* 161 */     record.setDeviceName(deviceName);
/* 162 */     record.setConnectivity(connectivity);
/* 163 */     record.setDoorOpen(doorOpen);
/* 164 */     record.setPrimaryR(primaryR);
/* 165 */     record.setPrimaryS(primaryS);
/* 166 */     record.setPrimaryT(primaryT);
/* 167 */     record.setSecondaryR(secondaryR);
/* 168 */     record.setSecondaryS(secondaryS);
/* 169 */     record.setSecondaryT(secondaryT);
/* 170 */     for (PdLoopStatus loopStatus : loopStatusList) {
/* 171 */       if (loopStatus.getLoopId().equals("1")) {
/* 172 */         record.setLoop1Status(loopStatus.getStatus()); continue;
/* 173 */       }  if (loopStatus.getLoopId().equals("2")) {
/* 174 */         record.setLoop2Status(loopStatus.getStatus()); continue;
/* 175 */       }  if (loopStatus.getLoopId().equals("3")) {
/* 176 */         record.setLoop3Status(loopStatus.getStatus()); continue;
/* 177 */       }  if (loopStatus.getLoopId().equals("4")) {
/* 178 */         record.setLoop4Status(loopStatus.getStatus()); continue;
/* 179 */       }  if (loopStatus.getLoopId().equals("5")) {
/* 180 */         record.setLoop5Status(loopStatus.getStatus());
/*     */       }
/*     */     } 
/* 183 */     record.setDataTime(dataTime);
/* 184 */     return record;
/*     */   }
/*     */   
/*     */   private boolean checkStatus(PdStatus newStatus, PdStatus oldStatus) {
/* 188 */     boolean result = false;
/* 189 */     if (!newStatus.getConnectivity().equals(oldStatus.getConnectivity())) {
/* 190 */       result = true;
/*     */     } else {
/* 192 */       if (newStatus.getDoorOpen() != null && 
/* 193 */         !newStatus.getDoorOpen().equals(oldStatus.getDoorOpen())) {
/* 194 */         result = true;
/*     */       }
/* 196 */       if (newStatus.getPrimaryR() != null && 
/* 197 */         !newStatus.getPrimaryR().equals(oldStatus.getPrimaryR())) {
/* 198 */         result = true;
/*     */       }
/* 200 */       if (newStatus.getPrimaryS() != null && 
/* 201 */         !newStatus.getPrimaryS().equals(oldStatus.getPrimaryS())) {
/* 202 */         result = true;
/*     */       }
/* 204 */       if (newStatus.getPrimaryT() != null && 
/* 205 */         !newStatus.getPrimaryT().equals(oldStatus.getPrimaryT())) {
/* 206 */         result = true;
/*     */       }
/* 208 */       if (newStatus.getSecondaryR() != null && 
/* 209 */         !newStatus.getSecondaryR().equals(oldStatus.getSecondaryR())) {
/* 210 */         result = true;
/*     */       }
/* 212 */       if (newStatus.getSecondaryS() != null && 
/* 213 */         !newStatus.getSecondaryS().equals(oldStatus.getSecondaryS())) {
/* 214 */         result = true;
/*     */       }
/* 216 */       if (newStatus.getSecondaryT() != null && 
/* 217 */         !newStatus.getSecondaryT().equals(oldStatus.getSecondaryT())) {
/* 218 */         result = true;
/*     */       }
/*     */       
/* 221 */       Map<String, List<PdLoopStatus>> loopStatusMap = new HashMap<>();
/* 222 */       for (PdLoopStatus loopstatus : newStatus.getPdLoops()) {
/* 223 */         List<PdLoopStatus> list = loopStatusMap.get(loopstatus.getId());
/* 224 */         if (list == null) {
/* 225 */           list = new ArrayList<>();
/*     */         }
/* 227 */         list.add(loopstatus);
/* 228 */         loopStatusMap.put(loopstatus.getId(), list);
/*     */       } 
/*     */       
/* 231 */       for (PdLoopStatus loopstatus : oldStatus.getPdLoops()) {
/* 232 */         List<PdLoopStatus> list = loopStatusMap.get(loopstatus.getId());
/* 233 */         if (list == null) {
/* 234 */           list = new ArrayList<>();
/*     */         }
/* 236 */         list.add(loopstatus);
/* 237 */         loopStatusMap.put(loopstatus.getId(), list);
/*     */       } 
/*     */       
/* 240 */       if (loopStatusMap.get(newStatus.getDeviceName() + "-1") != null && ((List)loopStatusMap
/* 241 */         .get(newStatus.getDeviceName() + "-1")).size() == 2 && 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 246 */         !((PdLoopStatus)((List<PdLoopStatus>)loopStatusMap.get(newStatus.getDeviceName() + "-1")).get(0)).getStatus().equals(((PdLoopStatus)((List<PdLoopStatus>)loopStatusMap.get(newStatus.getDeviceName() + "-1")).get(1)).getStatus())) {
/* 247 */         result = true;
/*     */       }
/* 249 */       if (loopStatusMap.get(newStatus.getDeviceName() + "-2") != null && ((List)loopStatusMap
/* 250 */         .get(newStatus.getDeviceName() + "-2")).size() == 2 && 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 255 */         !((PdLoopStatus)((List<PdLoopStatus>)loopStatusMap.get(newStatus.getDeviceName() + "-2")).get(0)).getStatus().equals(((PdLoopStatus)((List<PdLoopStatus>)loopStatusMap.get(newStatus.getDeviceName() + "-2")).get(1)).getStatus())) {
/* 256 */         result = true;
/*     */       }
/* 258 */       if (loopStatusMap.get(newStatus.getDeviceName() + "-3") != null && ((List)loopStatusMap
/* 259 */         .get(newStatus.getDeviceName() + "-3")).size() == 2 && 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 264 */         !((PdLoopStatus)((List<PdLoopStatus>)loopStatusMap.get(newStatus.getDeviceName() + "-3")).get(0)).getStatus().equals(((PdLoopStatus)((List<PdLoopStatus>)loopStatusMap.get(newStatus.getDeviceName() + "-3")).get(1)).getStatus())) {
/* 265 */         result = true;
/*     */       }
/* 267 */       if (loopStatusMap.get(newStatus.getDeviceName() + "-4") != null && ((List)loopStatusMap
/* 268 */         .get(newStatus.getDeviceName() + "-4")).size() == 2 && 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 273 */         !((PdLoopStatus)((List<PdLoopStatus>)loopStatusMap.get(newStatus.getDeviceName() + "-4")).get(0)).getStatus().equals(((PdLoopStatus)((List<PdLoopStatus>)loopStatusMap.get(newStatus.getDeviceName() + "-4")).get(1)).getStatus())) {
/* 274 */         result = true;
/*     */       }
/* 276 */       if (loopStatusMap.get(newStatus.getDeviceName() + "-5") != null && ((List)loopStatusMap
/* 277 */         .get(newStatus.getDeviceName() + "-5")).size() == 2 && 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 282 */         !((PdLoopStatus)((List<PdLoopStatus>)loopStatusMap.get(newStatus.getDeviceName() + "-5")).get(0)).getStatus().equals(((PdLoopStatus)((List<PdLoopStatus>)loopStatusMap.get(newStatus.getDeviceName() + "-5")).get(1)).getStatus())) {
/* 283 */         result = true;
/*     */       }
/*     */     } 
/* 286 */     return result;
/*     */   }
/*     */   
/*     */   private Integer checkValue(String pdStatusName) {
/* 290 */     IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/* 291 */     if (((Integer)dataMap.get(pdStatusName)).intValue() == -1) {
/* 292 */       return Integer.valueOf(1);
/*     */     }
/* 294 */     return (Integer)dataMap.get(pdStatusName);
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\PdStatusService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */