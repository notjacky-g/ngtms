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
/*     */ public class EnvironmentalControlAlarmTypeChecker
/*     */   extends AlarmChecker<DeviceTcConfig>
/*     */ {
/*  38 */   private static final Logger logger = LoggerFactory.getLogger(EnvironmentalControlAlarmTypeChecker.class);
/*     */   
/*  40 */   private Gson gson = new Gson();
/*     */   
/*  42 */   private static final String[] ROOM_DEVICE_TYPE = new String[] { "火警受信總機", "發電機", "主電源開關" };
/*     */   
/*     */   @Autowired
/*     */   private HcceEnv hcceEnv;
/*     */   
/*     */   protected Collection<DeviceTcConfig> source() {
/*  48 */     IMap<String, DeviceTcConfig> tcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  49 */     String[] typeArr = ROOM_DEVICE_TYPE;
/*  50 */     DynamicConfig dyConfig = getDynamicConfig("AoCommonFm", "roomDeviceType");
/*  51 */     if (dyConfig != null && dyConfig.getValue() != null) {
/*  52 */       typeArr = dyConfig.getValue().split(",");
/*     */     }
/*  54 */     EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*  55 */     PredicateBuilder pb = eo.get("deviceType").in((Comparable[])typeArr);
/*  56 */     return tcConfigMap.values((Predicate)pb);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isAlarm(DeviceTcConfig tcConfig) {
/*  61 */     boolean result = false;
/*  62 */     if (tcConfig.getExtend() != null) {
/*  63 */       RtuConfig rtuConfig = (RtuConfig)this.gson.fromJson(tcConfig.getExtend(), RtuConfig.class);
/*  64 */       if (rtuConfig.isAlarm()) {
/*  65 */         IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/*     */         try {
/*  67 */           Integer value = (Integer)dataMap.get(tcConfig.getDeviceName());
/*  68 */           if (rtuConfig.getSignalType().equals("DIGITAL_IN")) {
/*  69 */             if (value.intValue() == 1 || value.intValue() == -1) {
/*  70 */               result = true;
/*     */             } else {
/*  72 */               result = false;
/*     */             } 
/*     */           } else {
/*  75 */             result = false;
/*     */           } 
/*  77 */         } catch (Exception e) {
/*  78 */           logger.error("EnvironmentalControlAlarmChecker Faild, DevcieName = '{}'.", tcConfig
/*     */               
/*  80 */               .getDeviceName(), e);
/*     */           
/*  82 */           return result;
/*     */         } 
/*     */       } 
/*     */     } 
/*  86 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AlarmLogDTO toDTO(DeviceTcConfig tcConfig, AlarmState alarmState, Optional<Integer> optDegree) {
/*  92 */     IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/*  93 */     String deviceName = tcConfig.getDeviceName();
/*  94 */     Integer value = (Integer)dataMap.get(deviceName);
/*  95 */     AlarmLogDTO dto = new AlarmLogDTO();
/*  96 */     dto.setDeviceName(deviceName);
/*  97 */     dto.setAlarmState(alarmState);
/*  98 */     dto.setTimestamp(new Date());
/*  99 */     if (tcConfig.getExtend() != null) {
/* 100 */       RtuConfig rtuConfig = (RtuConfig)this.gson.fromJson(tcConfig.getExtend(), RtuConfig.class);
/* 101 */       if (value != null && value.intValue() != -1) {
/* 102 */         if (rtuConfig.getSignalType().equals("DIGITAL_IN")) {
/* 103 */           if (value.intValue() == 1) {
/* 104 */             dto.setAlarmSubType(AlarmType.ABNORMAL.toString());
/* 105 */             dto.setDegree(optDegree.orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.ABNORMAL, 2.0D))));
/* 106 */             dto.setMessage("異常");
/*     */           } else {
/* 108 */             dto.setAlarmSubType(AlarmType.ABNORMAL.toString());
/* 109 */             dto.setDegree(optDegree.orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.ABNORMAL, 2.0D))));
/* 110 */             dto.setMessage("恢復正常");
/*     */           } 
/*     */         }
/*     */       } else {
/* 114 */         dto.setAlarmSubType(AlarmType.ABNORMAL.toString());
/* 115 */         dto.setDegree(optDegree.orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.ABNORMAL, 2.0D))));
/* 116 */         dto.setMessage("未收到訊號!!");
/*     */       } 
/*     */     } 
/* 119 */     return dto;
/*     */   }
/*     */   
/*     */   private DynamicConfig getDynamicConfig(String fmeName, String key) {
/* 123 */     if (fmeName == null || key == null) {
/* 124 */       logger.warn("Can not get DynamicConfig, fmeName or key is null.");
/*     */     }
/* 126 */     IMap<DynamicConfigPk, DynamicConfig> dynamicConfigMap = HzUtils.getMap((HzDistObjEnum)HzMap.DynamicConfig);
/*     */     
/* 128 */     DynamicConfig dynamicConfig = (DynamicConfig)dynamicConfigMap.get(new DynamicConfigPk(this.hcceEnv.getCurrentGroupName(), fmeName, key));
/* 129 */     return dynamicConfig;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\EnvironmentalControlAlarmTypeChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */