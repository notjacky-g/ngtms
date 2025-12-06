/*     */ package com.hwacom.ngtms.ao.fm.service;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.ao.fm.model.RoomDeviceStatusRecord;
/*     */ import com.hwacom.ngtms.ao.fm.repository.RoomDeviceStatusRecordRepository;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcStatus;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfigPk;
/*     */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*     */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*     */ import com.hwacom.ngtms.room.shared.RtuConfig;
/*     */ import com.hwacom.ngtms.rtu.fm.hz.RtuHzMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang3.SerializationUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service
/*     */ public class RoomDeviceStatusService {
/*  34 */   private static final Logger logger = LoggerFactory.getLogger(RoomDeviceStatusService.class);
/*     */   
/*  36 */   private Gson gson = new Gson();
/*     */   
/*  38 */   private static final String[] ROOM_DEVICE_TYPE = new String[] { "溫度", "濕度", "空調", "電力", "電流", "DC電壓", "發電機", "FM發射機", "AC UPS市電", "AC UPS狀態", "PDU配電櫃", "凹槽電力", "機房門禁", "油槽容量", "主電源開關", "人體感知器", "充電機狀態", "充電機電池", "充電機電源", "航空障礙燈", "PA播放中繼放大器", "PA功率放大器", "AC UPS電池供電", "火警受信總機", "UHF-中繼器電源", "VHF-中繼器電源", "發射機反射功率", "發射機輸出功率", "UHF-中繼器輸出功率一", "UHF-中繼器輸出功率二", "VHF-中繼器輸出功率一", "VHF-中繼器輸出功率二", "PA背景聲音監聽放大器", "燈光開關", "聲光警報器開關", "照明燈" };
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
/*     */   @Autowired
/*     */   private HcceEnv hcceEnv;
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
/*     */   @Autowired
/*     */   private RoomDeviceStatusRecordRepository roomDeviceStatusRecordRepository;
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
/*     */   public void process() {
/*     */     try {
/*  83 */       IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  84 */       IMap<String, DeviceTcStatus> statusMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcStatus);
/*  85 */       IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/*  86 */       Map<String, DeviceTcStatus> newStatusMap = new HashMap<>();
/*  87 */       String[] typeArr = ROOM_DEVICE_TYPE;
/*  88 */       DynamicConfig dyConfig = getDynamicConfig("AoCommonFm", "roomDeviceType");
/*  89 */       if (dyConfig != null && dyConfig.getValue() != null) {
/*  90 */         typeArr = dyConfig.getValue().split(",");
/*     */       }
/*  92 */       EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*  93 */       PredicateBuilder pb = eo.get("deviceType").in((Comparable[])typeArr);
/*  94 */       List<RoomDeviceStatusRecord> recordList = new ArrayList<>();
/*  95 */       Date now = new Date();
/*  96 */       String offline = "-1";
/*  97 */       for (DeviceTcConfig tcConfig : deviceConfigMap.values((Predicate)pb)) {
/*     */         try {
/*  99 */           String deviceName = tcConfig.getDeviceName();
/* 100 */           String extend = tcConfig.getExtend();
/*     */           
/* 102 */           if (extend != null && this.gson.fromJson(extend, RtuConfig.class) != null) {
/* 103 */             RtuConfig rtuConfig = (RtuConfig)this.gson.fromJson(extend, RtuConfig.class);
/* 104 */             String signalType = rtuConfig.getSignalType();
/* 105 */             DeviceTcStatus statusValue = (DeviceTcStatus)statusMap.get(deviceName);
/* 106 */             Integer value = (Integer)dataMap.get(deviceName);
/*     */ 
/*     */             
/* 109 */             if (statusValue != null && statusValue.getId() != null) {
/* 110 */               if (value != null) {
/* 111 */                 if (value.intValue() == -1) {
/* 112 */                   statusValue.setCommStatus(Integer.valueOf(1));
/*     */                 } else {
/* 114 */                   if (signalType.equals("ANALOG_IN")) {
/* 115 */                     Double displayValue = Double.valueOf(0.0D);
/* 116 */                     if (statusValue.getContextData() != null)
/*     */                     {
/* 118 */                       displayValue = Double.valueOf(((Double)SerializationUtils.deserialize(statusValue.getContextData())).doubleValue());
/*     */                     }
/* 120 */                     Double valueDetail = Double.valueOf(0.0D);
/* 121 */                     if (statusValue.getDeviceType().equals("DC電壓") || statusValue
/* 122 */                       .getDeviceType().equals("電流")) {
/* 123 */                       if (tcConfig.getDisplayName().equals("系統輸出電流值") || tcConfig
/* 124 */                         .getDisplayName().equals("R24系統輸出電流值")) {
/* 125 */                         valueDetail = Double.valueOf(value.intValue() * 1.0D);
/*     */                       } else {
/* 127 */                         valueDetail = Double.valueOf(value.intValue() * 0.01D);
/*     */                       }
/*     */                     
/* 130 */                     } else if (statusValue.getDeviceType().equals("溫度") && value
/* 131 */                       .toString().length() == 4) {
/* 132 */                       valueDetail = Double.valueOf(value.intValue() * 0.01D);
/*     */                     } else {
/* 134 */                       valueDetail = Double.valueOf(value.intValue() * 0.1D);
/*     */                     } 
/*     */ 
/*     */                     
/* 138 */                     if (Double.doubleToLongBits(displayValue.doubleValue()) != 
/* 139 */                       Double.doubleToLongBits(valueDetail.doubleValue())) {
/* 140 */                       recordList.add(saveRecord(deviceName, signalType, Integer.valueOf(0), value.toString(), now));
/*     */                     }
/* 142 */                     statusValue.setContextData(SerializationUtils.serialize(valueDetail));
/*     */                   } else {
/* 144 */                     Integer statusDataValue = Integer.valueOf(0);
/* 145 */                     if (statusValue.getContextData() != null)
/*     */                     {
/* 147 */                       statusDataValue = (Integer)SerializationUtils.deserialize(statusValue.getContextData());
/*     */                     }
/*     */                     
/* 150 */                     if (!statusDataValue.equals(value)) {
/* 151 */                       recordList.add(saveRecord(deviceName, signalType, Integer.valueOf(0), value.toString(), now));
/*     */                     }
/* 153 */                     statusValue.setContextData(SerializationUtils.serialize(value));
/*     */                   } 
/* 155 */                   statusValue.setCommStatus(Integer.valueOf(0));
/*     */                 } 
/*     */               } else {
/* 158 */                 statusValue.setCommStatus(Integer.valueOf(1));
/*     */               } 
/* 160 */               statusValue.setLastUpdateStatus(statusValue.getCommStatus());
/* 161 */               statusValue.setLastUpdateTime(statusValue.getTimestamp());
/* 162 */               statusValue.setTimestamp(now);
/* 163 */               newStatusMap.put(deviceName, statusValue); continue;
/*     */             } 
/* 165 */             DeviceTcStatus newStatus = new DeviceTcStatus();
/* 166 */             newStatus.setId(deviceName);
/* 167 */             newStatus.setDeviceType(tcConfig.getDeviceType());
/* 168 */             newStatus.setTimestamp(new Date());
/* 169 */             if (value != null) {
/* 170 */               if (value.intValue() == -1) {
/* 171 */                 newStatus.setCommStatus(Integer.valueOf(1));
/*     */               } else {
/* 173 */                 if (signalType.equals("ANALOG_IN")) {
/* 174 */                   Double valueDetail = Double.valueOf(0.0D);
/* 175 */                   if (tcConfig.getDeviceType().equals("DC電壓") || tcConfig
/* 176 */                     .getDeviceType().equals("電流")) {
/* 177 */                     valueDetail = Double.valueOf(value.intValue() * 0.01D);
/*     */                   }
/* 179 */                   else if (tcConfig.getDeviceType().equals("溫度") && value.toString().length() == 4) {
/* 180 */                     valueDetail = Double.valueOf(value.intValue() * 0.01D);
/*     */                   } else {
/* 182 */                     valueDetail = Double.valueOf(value.intValue() * 0.1D);
/*     */                   } 
/*     */                   
/* 185 */                   newStatus.setContextData(SerializationUtils.serialize(valueDetail));
/*     */                 } else {
/* 187 */                   newStatus.setContextData(SerializationUtils.serialize(value));
/*     */                 } 
/* 189 */                 newStatus.setCommStatus(Integer.valueOf(0));
/*     */               } 
/*     */             } else {
/* 192 */               newStatus.setCommStatus(Integer.valueOf(1));
/* 193 */               recordList.add(saveRecord(deviceName, signalType, Integer.valueOf(1), offline, now));
/*     */             } 
/* 195 */             newStatusMap.put(deviceName, newStatus);
/*     */           }
/*     */         
/* 198 */         } catch (Exception e) {
/* 199 */           logger.error("judge RTU status failed. deviceName:'{}'", tcConfig.getDeviceName(), e);
/*     */         } 
/*     */       } 
/* 202 */       statusMap.putAll(newStatusMap);
/* 203 */       this.roomDeviceStatusRecordRepository.saveAll(recordList);
/* 204 */     } catch (Exception e) {
/* 205 */       logger.error("RoomDeviceStatusService error.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private RoomDeviceStatusRecord saveRecord(String deviceName, String sinalType, Integer rtuStatus, String density, Date dataTime) {
/* 211 */     RoomDeviceStatusRecord record = new RoomDeviceStatusRecord();
/* 212 */     record.setDeviceName(deviceName);
/* 213 */     record.setSignalType(sinalType);
/* 214 */     record.setRtuStatus(rtuStatus);
/* 215 */     record.setDensity(density);
/* 216 */     record.setDataTime(dataTime);
/* 217 */     return record;
/*     */   }
/*     */   
/*     */   private DynamicConfig getDynamicConfig(String fmeName, String key) {
/* 221 */     if (fmeName == null || key == null) {
/* 222 */       logger.warn("Can not get DynamicConfig, fmeName or key is null.");
/*     */     }
/* 224 */     IMap<DynamicConfigPk, DynamicConfig> dynamicConfigMap = HzUtils.getMap((HzDistObjEnum)HzMap.DynamicConfig);
/*     */     
/* 226 */     DynamicConfig dynamicConfig = (DynamicConfig)dynamicConfigMap.get(new DynamicConfigPk(this.hcceEnv.getCurrentGroupName(), fmeName, key));
/* 227 */     return dynamicConfig;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\RoomDeviceStatusService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */