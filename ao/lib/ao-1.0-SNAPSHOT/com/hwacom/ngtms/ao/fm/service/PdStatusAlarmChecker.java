/*     */ package com.hwacom.ngtms.ao.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.alarm.fm.shared.dto.AlarmLogDTO;
/*     */ import com.hwacom.ngtms.alarm.shared.AlarmState;
/*     */ import com.hwacom.ngtms.ao.shared.AlarmType;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.rtu.fm.hz.RtuHzMap;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Optional;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class PdStatusAlarmChecker
/*     */   extends AlarmChecker<DeviceTcConfig>
/*     */ {
/*     */   protected Collection<DeviceTcConfig> source() {
/*  28 */     IMap<String, DeviceTcConfig> tcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*     */     
/*  30 */     PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceType").equal("PDStatus");
/*  31 */     return tcConfigMap.values((Predicate)pb);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isAlarm(DeviceTcConfig config) {
/*  36 */     IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/*  37 */     Integer value = (Integer)dataMap.get(config.getDeviceName());
/*  38 */     boolean result = false;
/*  39 */     if (value != null) {
/*  40 */       if (value.intValue() == 1 || value.intValue() == -1) {
/*  41 */         result = true;
/*     */       } else {
/*  43 */         result = false;
/*     */       } 
/*     */     }
/*  46 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AlarmLogDTO toDTO(DeviceTcConfig tcConfig, AlarmState alarmState, Optional<Integer> optDegree) {
/*  52 */     IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/*  53 */     String deviceName = tcConfig.getDeviceName();
/*  54 */     Integer value = (Integer)dataMap.get(deviceName);
/*  55 */     AlarmLogDTO dto = new AlarmLogDTO();
/*  56 */     dto.setDeviceName(deviceName);
/*  57 */     dto.setAlarmState(alarmState);
/*  58 */     dto.setTimestamp(new Date());
/*  59 */     if (value != null) {
/*  60 */       if (value.intValue() == 1) {
/*  61 */         dto.setMessage("異常");
/*  62 */       } else if (value.intValue() == 0) {
/*  63 */         dto.setMessage("恢復正常");
/*     */       } else {
/*  65 */         dto.setMessage("訊號未接收!");
/*     */       } 
/*     */     } else {
/*  68 */       dto.setMessage("訊號未接收!");
/*     */     } 
/*     */     
/*  71 */     if (deviceName.contains("doorOpen")) {
/*  72 */       dto.setAlarmSubType(AlarmType.PD_BOX_OPEN.toString());
/*  73 */       dto.setDegree(optDegree.orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.PD_BOX_OPEN, 2.0D))));
/*  74 */       if (value != null && value.intValue() == 1) {
/*  75 */         dto.setMessage("箱門開啟");
/*     */       }
/*  77 */     } else if (deviceName.contains("loop1Status")) {
/*  78 */       dto.setAlarmSubType(AlarmType.BRANCH_CIRCUIT_ABNORMAL_1.toString());
/*  79 */       dto.setDegree(optDegree
/*  80 */           .orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.BRANCH_CIRCUIT_ABNORMAL_1, 2.0D))));
/*  81 */     } else if (deviceName.contains("loop2Status")) {
/*  82 */       dto.setAlarmSubType(AlarmType.BRANCH_CIRCUIT_ABNORMAL_2.toString());
/*  83 */       dto.setDegree(optDegree
/*  84 */           .orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.BRANCH_CIRCUIT_ABNORMAL_2, 2.0D))));
/*  85 */     } else if (deviceName.contains("loop3Status")) {
/*  86 */       dto.setAlarmSubType(AlarmType.BRANCH_CIRCUIT_ABNORMAL_3.toString());
/*  87 */       dto.setDegree(optDegree
/*  88 */           .orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.BRANCH_CIRCUIT_ABNORMAL_3, 2.0D))));
/*  89 */     } else if (deviceName.contains("loop4Status")) {
/*  90 */       dto.setAlarmSubType(AlarmType.BRANCH_CIRCUIT_ABNORMAL_4.toString());
/*  91 */       dto.setDegree(optDegree
/*  92 */           .orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.BRANCH_CIRCUIT_ABNORMAL_4, 2.0D))));
/*  93 */     } else if (deviceName.contains("loop5Status")) {
/*  94 */       dto.setAlarmSubType(AlarmType.BRANCH_CIRCUIT_ABNORMAL_5.toString());
/*  95 */       dto.setDegree(optDegree
/*  96 */           .orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.BRANCH_CIRCUIT_ABNORMAL_5, 2.0D))));
/*  97 */     } else if (deviceName.contains("primaryR")) {
/*  98 */       dto.setAlarmSubType(AlarmType.PRIMARY_ABNORMAL_R.toString());
/*  99 */       dto.setDegree(optDegree.orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.PRIMARY_ABNORMAL_R, 2.0D))));
/* 100 */     } else if (deviceName.contains("primaryS")) {
/* 101 */       dto.setAlarmSubType(AlarmType.PRIMARY_ABNORMAL_S.toString());
/* 102 */       dto.setDegree(optDegree.orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.PRIMARY_ABNORMAL_S, 2.0D))));
/* 103 */     } else if (deviceName.contains("primaryT")) {
/* 104 */       dto.setAlarmSubType(AlarmType.PRIMARY_ABNORMAL_T.toString());
/* 105 */       dto.setDegree(optDegree.orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.PRIMARY_ABNORMAL_T, 2.0D))));
/* 106 */     } else if (deviceName.contains("secondaryR")) {
/* 107 */       dto.setAlarmSubType(AlarmType.SECONDARY_ABNORMAL_R.toString());
/* 108 */       dto.setDegree(optDegree
/* 109 */           .orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.SECONDARY_ABNORMAL_R, 2.0D))));
/* 110 */     } else if (deviceName.contains("secondaryS")) {
/* 111 */       dto.setAlarmSubType(AlarmType.SECONDARY_ABNORMAL_S.toString());
/* 112 */       dto.setDegree(optDegree
/* 113 */           .orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.SECONDARY_ABNORMAL_S, 2.0D))));
/* 114 */     } else if (deviceName.contains("secondaryT")) {
/* 115 */       dto.setAlarmSubType(AlarmType.SECONDARY_ABNORMAL_T.toString());
/* 116 */       dto.setDegree(optDegree
/* 117 */           .orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.SECONDARY_ABNORMAL_T, 2.0D))));
/*     */     } 
/* 119 */     return dto;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\PdStatusAlarmChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */