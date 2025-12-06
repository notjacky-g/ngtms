/*    */ package com.hwacom.ngtms.ao.fm.service;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hazelcast.query.Predicate;
/*    */ import com.hazelcast.query.PredicateBuilder;
/*    */ import com.hwacom.ngtms.alarm.fm.shared.dto.AlarmLogDTO;
/*    */ import com.hwacom.ngtms.alarm.shared.AlarmState;
/*    */ import com.hwacom.ngtms.ao.shared.AlarmType;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*    */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*    */ import com.hwacom.ngtms.room.shared.RtuConfig;
/*    */ import com.hwacom.ngtms.rtu.fm.hz.RtuHzMap;
/*    */ import java.util.Collection;
/*    */ import java.util.Date;
/*    */ import java.util.Optional;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class SoundLightSartAlarmChecker
/*    */   extends AlarmChecker<DeviceTcConfig>
/*    */ {
/* 28 */   private Gson gson = new Gson();
/*    */ 
/*    */   
/*    */   protected Collection<DeviceTcConfig> source() {
/* 32 */     IMap<String, DeviceTcConfig> tcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*    */     
/* 34 */     PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceType").equal("聲光警報器開關");
/* 35 */     return tcConfigMap.values((Predicate)pb);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isAlarm(DeviceTcConfig tcConfig) {
/* 40 */     boolean result = false;
/* 41 */     if (tcConfig.getExtend() != null) {
/* 42 */       RtuConfig rtuConfig = (RtuConfig)this.gson.fromJson(tcConfig.getExtend(), RtuConfig.class);
/* 43 */       if (rtuConfig.isAlarm()) {
/* 44 */         IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/* 45 */         Integer value = (Integer)dataMap.get(tcConfig.getDeviceName());
/* 46 */         if (value != null) {
/* 47 */           if (value.intValue() == 1 || value.intValue() == -1) {
/* 48 */             result = true;
/*    */           } else {
/* 50 */             result = false;
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/* 55 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected AlarmLogDTO toDTO(DeviceTcConfig tcConfig, AlarmState alarmState, Optional<Integer> optDegree) {
/* 61 */     IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/* 62 */     Integer value = (Integer)dataMap.get(tcConfig.getDeviceName());
/* 63 */     AlarmLogDTO dto = new AlarmLogDTO();
/* 64 */     dto.setAlarmSubType(AlarmType.SOUND_LIGHT_START.toString());
/* 65 */     dto.setDeviceName(tcConfig.getDeviceName());
/* 66 */     dto.setAlarmState(alarmState);
/* 67 */     dto.setTimestamp(new Date());
/* 68 */     dto.setDegree(optDegree.orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.SOUND_LIGHT_START, 2.0D))));
/* 69 */     if (value != null) {
/* 70 */       if (value.intValue() == 1) {
/* 71 */         dto.setMessage("啟動中");
/* 72 */       } else if (value.intValue() == 0) {
/* 73 */         dto.setMessage("恢復正常");
/*    */       } else {
/* 75 */         dto.setMessage("訊號未接收!");
/*    */       } 
/*    */     } else {
/* 78 */       dto.setMessage("訊號未接收!");
/*    */     } 
/* 80 */     return dto;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\SoundLightSartAlarmChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */