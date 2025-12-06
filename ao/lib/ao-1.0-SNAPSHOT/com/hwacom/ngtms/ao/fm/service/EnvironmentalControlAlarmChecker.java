/*     */ package com.hwacom.ngtms.ao.fm.service;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.alarm.fm.shared.dto.AlarmLogDTO;
/*     */ import com.hwacom.ngtms.alarm.shared.AlarmState;
/*     */ import com.hwacom.ngtms.ao.shared.AlarmType;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfigPk;
/*     */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*     */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*     */ import com.hwacom.ngtms.room.shared.RtuConfig;
/*     */ import com.hwacom.ngtms.rtu.fm.hz.RtuHzMap;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Optional;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class EnvironmentalControlAlarmChecker
/*     */   extends AlarmChecker<DeviceTcConfig>
/*     */ {
/*  38 */   private static final Logger logger = LoggerFactory.getLogger(EnvironmentalControlAlarmChecker.class);
/*     */   
/*  40 */   private Gson gson = new Gson();
/*     */   
/*  42 */   private static final String[] ROOM_DEVICE_TYPE = new String[] { "溫度", "濕度", "空調", "電力", "電流", "DC電壓", "FM發射機", "AC UPS市電", "AC UPS狀態", "PDU配電櫃", "凹槽電力", "油槽容量", "人體感知器", "充電機狀態", "充電機電池", "充電機電源", "航空障礙燈", "PA播放中繼放大器", "PA功率放大器", "AC UPS電池供電", "UHF-中繼器電源", "VHF-中繼器電源", "發射機反射功率", "發射機輸出功率", "UHF-中繼器輸出功率一", "UHF-中繼器輸出功率二", "VHF-中繼器輸出功率一", "VHF-中繼器輸出功率二", "PA背景聲音監聽放大器", "照明燈" };
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Collection<DeviceTcConfig> source() {
/*  80 */     IMap<String, DeviceTcConfig> tcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  81 */     String[] typeArr = ROOM_DEVICE_TYPE;
/*  82 */     DynamicConfig dyConfig = getDynamicConfig("AoCommonFm", "roomDeviceType");
/*  83 */     if (dyConfig != null && dyConfig.getValue() != null) {
/*  84 */       typeArr = dyConfig.getValue().split(",");
/*     */     }
/*  86 */     EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*  87 */     PredicateBuilder pb = eo.get("deviceType").in((Comparable[])typeArr);
/*  88 */     return tcConfigMap.values((Predicate)pb);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isAlarm(DeviceTcConfig tcConfig) {
/*  93 */     boolean result = false;
/*  94 */     if (tcConfig.getExtend() != null) {
/*  95 */       RtuConfig rtuConfig = (RtuConfig)this.gson.fromJson(tcConfig.getExtend(), RtuConfig.class);
/*  96 */       if (rtuConfig.isAlarm()) {
/*  97 */         IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/*     */         try {
/*  99 */           Integer value = (Integer)dataMap.get(tcConfig.getDeviceName());
/* 100 */           if (rtuConfig.getSignalType().equals("ANALOG_IN") && value.intValue() != -1) {
/* 101 */             Double valueDetail = Double.valueOf(0.0D);
/* 102 */             if (tcConfig.getDeviceType().equals("DC電壓") || tcConfig.getDeviceType().equals("電流")) {
/* 103 */               if (tcConfig.getDisplayName().equals("系統輸出電流值") || tcConfig
/* 104 */                 .getDisplayName().equals("R24系統輸出電流值")) {
/* 105 */                 valueDetail = Double.valueOf(value.intValue() * 1.0D);
/*     */               } else {
/* 107 */                 valueDetail = Double.valueOf(value.intValue() * 0.01D);
/*     */               }
/*     */             
/* 110 */             } else if (tcConfig.getDeviceType().equals("溫度") && value.toString().length() == 4) {
/* 111 */               valueDetail = Double.valueOf(value.intValue() * 0.01D);
/*     */             } else {
/* 113 */               valueDetail = Double.valueOf(value.intValue() * 0.1D);
/*     */             } 
/*     */             
/* 116 */             if (valueDetail.doubleValue() > rtuConfig.getUpperLimit().intValue()) {
/* 117 */               result = true;
/* 118 */             } else if (valueDetail.doubleValue() < rtuConfig.getLowerLimit().intValue()) {
/* 119 */               result = true;
/*     */             } else {
/* 121 */               result = false;
/*     */             } 
/* 123 */           } else if (rtuConfig.getSignalType().equals("DIGITAL_IN")) {
/* 124 */             if (value.intValue() == 1 || value.intValue() == -1) {
/* 125 */               result = true;
/*     */             } else {
/* 127 */               result = false;
/*     */             } 
/*     */           } else {
/* 130 */             result = false;
/*     */           } 
/* 132 */         } catch (Exception e) {
/* 133 */           logger.error("EnvironmentalControlAlarmChecker Faild, DevcieName = '{}'.", tcConfig
/*     */               
/* 135 */               .getDeviceName(), e);
/*     */           
/* 137 */           return result;
/*     */         } 
/*     */       } 
/*     */     } 
/* 141 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AlarmLogDTO toDTO(DeviceTcConfig tcConfig, AlarmState alarmState, Optional<Integer> optDegree) {
/* 147 */     IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/* 148 */     String deviceName = tcConfig.getDeviceName();
/* 149 */     String deviceType = tcConfig.getDeviceType();
/* 150 */     Integer value = (Integer)dataMap.get(deviceName);
/* 151 */     AlarmLogDTO dto = new AlarmLogDTO();
/* 152 */     dto.setDeviceName(deviceName);
/* 153 */     dto.setAlarmState(alarmState);
/* 154 */     dto.setTimestamp(new Date());
/* 155 */     if (tcConfig.getExtend() != null) {
/* 156 */       RtuConfig rtuConfig = (RtuConfig)this.gson.fromJson(tcConfig.getExtend(), RtuConfig.class);
/*     */       
/* 158 */       if (rtuConfig.getSignalType().equals("ANALOG_IN")) {
/* 159 */         Double valueDetail = Double.valueOf(0.0D);
/* 160 */         if (deviceType.equals("DC電壓") || deviceType.equals("電流")) {
/* 161 */           if (tcConfig.getDisplayName().equals("系統輸出電流值") || tcConfig
/* 162 */             .getDisplayName().equals("R24系統輸出電流值")) {
/* 163 */             valueDetail = Double.valueOf(value.intValue() * 1.0D);
/*     */           } else {
/* 165 */             valueDetail = Double.valueOf(value.intValue() * 0.01D);
/*     */           }
/*     */         
/* 168 */         } else if (deviceType.equals("溫度") && value.toString().length() == 4) {
/* 169 */           valueDetail = Double.valueOf(value.intValue() * 0.01D);
/*     */         } else {
/* 171 */           valueDetail = Double.valueOf(value.intValue() * 0.1D);
/*     */         } 
/*     */         
/* 174 */         if (value != null && value.intValue() != -1) {
/*     */           
/* 176 */           if (valueDetail.doubleValue() > rtuConfig.getUpperLimit().intValue()) {
/* 177 */             logger.debug("valueDetail = '{}', rtuUpper = '{}'.", valueDetail, rtuConfig
/* 178 */                 .getUpperLimit());
/* 179 */             dto.setAlarmSubType(AlarmType.UPPER_LIMIT.toString());
/* 180 */             dto.setDegree(optDegree.orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.UPPER_LIMIT, 2.0D))));
/* 181 */             dto.setMessage("超過上限值");
/*     */           
/*     */           }
/* 184 */           else if (valueDetail.doubleValue() < rtuConfig.getLowerLimit().intValue()) {
/* 185 */             logger.debug("valueDetail = '{}', LowerLimit = '{}'.", valueDetail, rtuConfig
/* 186 */                 .getLowerLimit());
/* 187 */             dto.setAlarmSubType(AlarmType.LOWER_LIMIT.toString());
/* 188 */             dto.setDegree(optDegree.orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.LOWER_LIMIT, 2.0D))));
/* 189 */             dto.setMessage("低於下限值");
/*     */           } else {
/* 191 */             dto.setAlarmSubType(alarmState.toString());
/* 192 */             dto.setDegree(optDegree.orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.LOWER_LIMIT, 2.0D))));
/* 193 */             dto.setMessage("恢復正常");
/*     */           } 
/*     */         } else {
/* 196 */           dto.setAlarmSubType(AlarmType.ABNORMAL.toString());
/* 197 */           dto.setDegree(optDegree.orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.ABNORMAL, 2.0D))));
/* 198 */           dto.setMessage("未收到訊號!!");
/*     */         }
/*     */       
/* 201 */       } else if (value != null && value.intValue() != -1) {
/* 202 */         if (rtuConfig.getSignalType().equals("DIGITAL_IN")) {
/* 203 */           if (value.intValue() == 1) {
/* 204 */             dto.setAlarmSubType(AlarmType.ABNORMAL.toString());
/* 205 */             dto.setDegree(optDegree.orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.ABNORMAL, 2.0D))));
/* 206 */             dto.setMessage("異常");
/*     */           } else {
/* 208 */             dto.setAlarmSubType(AlarmType.ABNORMAL.toString());
/* 209 */             dto.setDegree(optDegree.orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.ABNORMAL, 2.0D))));
/* 210 */             dto.setMessage("恢復正常");
/*     */           } 
/*     */         }
/*     */       } else {
/* 214 */         dto.setAlarmSubType(AlarmType.ABNORMAL.toString());
/* 215 */         dto.setDegree(optDegree.orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.ABNORMAL, 2.0D))));
/* 216 */         dto.setMessage("未收到訊號!!");
/*     */       } 
/*     */     } 
/*     */     
/* 220 */     return dto;
/*     */   }
/*     */   
/*     */   private DynamicConfig getDynamicConfig(String fmeName, String key) {
/* 224 */     if (fmeName == null || key == null) {
/* 225 */       logger.warn("Can not get DynamicConfig, fmeName or key is null.");
/*     */     }
/* 227 */     IMap<DynamicConfigPk, DynamicConfig> dynamicConfigMap = HzUtils.getMap((HzDistObjEnum)HzMap.DynamicConfig);
/*     */     
/* 229 */     DynamicConfig dynamicConfig = (DynamicConfig)dynamicConfigMap.get(new DynamicConfigPk(this.hcceEnv.getCurrentGroupName(), fmeName, key));
/* 230 */     return dynamicConfig;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\EnvironmentalControlAlarmChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */