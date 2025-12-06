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
/*    */ public class SecurityReleaseAlarmChecker
/*    */   extends AlarmChecker<DeviceTcConfig>
/*    */ {
/* 28 */   private Gson gson = new Gson();
/*    */ 
/*    */   
/*    */   protected Collection<DeviceTcConfig> source() {
/* 32 */     IMap<String, DeviceTcConfig> tcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 33 */     PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceType").equal("燈光開關");
/* 34 */     return tcConfigMap.values((Predicate)pb);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isAlarm(DeviceTcConfig tcConfig) {
/* 39 */     boolean result = false;
/* 40 */     if (tcConfig.getExtend() != null) {
/* 41 */       RtuConfig rtuConfig = (RtuConfig)this.gson.fromJson(tcConfig.getExtend(), RtuConfig.class);
/* 42 */       if (rtuConfig.isAlarm()) {
/* 43 */         IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/* 44 */         Integer value = (Integer)dataMap.get(tcConfig.getDeviceName());
/* 45 */         if (value != null) {
/* 46 */           if (value.intValue() == 1 || value.intValue() == -1) {
/* 47 */             result = true;
/*    */           } else {
/* 49 */             result = false;
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/* 54 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected AlarmLogDTO toDTO(DeviceTcConfig tcConfig, AlarmState alarmState, Optional<Integer> optDegree) {
/* 60 */     IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/* 61 */     Integer value = (Integer)dataMap.get(tcConfig.getDeviceName());
/* 62 */     AlarmLogDTO dto = new AlarmLogDTO();
/* 63 */     dto.setAlarmSubType(AlarmType.SECURITY_RELEASE.toString());
/* 64 */     dto.setDeviceName(tcConfig.getDeviceName());
/* 65 */     dto.setAlarmState(alarmState);
/* 66 */     dto.setTimestamp(new Date());
/* 67 */     dto.setDegree(optDegree.orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.SECURITY_RELEASE, 3.0D))));
/* 68 */     if (value != null) {
/* 69 */       if (value.intValue() == 1) {
/* 70 */         dto.setMessage("保全解除中");
/* 71 */       } else if (value.intValue() == 0) {
/* 72 */         dto.setMessage("恢復正常");
/*    */       } else {
/* 74 */         dto.setMessage("訊號未接收!");
/*    */       } 
/*    */     } else {
/* 77 */       dto.setMessage("訊號未接收!");
/*    */     } 
/* 79 */     return dto;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\SecurityReleaseAlarmChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */