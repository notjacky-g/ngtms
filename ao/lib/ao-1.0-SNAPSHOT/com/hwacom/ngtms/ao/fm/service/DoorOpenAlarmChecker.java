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
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class DoorOpenAlarmChecker
/*    */   extends AlarmChecker<DeviceTcConfig>
/*    */ {
/* 30 */   private Gson gson = new Gson();
/*    */   
/* 32 */   private static final Logger logger = LoggerFactory.getLogger(DoorOpenAlarmChecker.class);
/*    */ 
/*    */   
/*    */   protected Collection<DeviceTcConfig> source() {
/* 36 */     IMap<String, DeviceTcConfig> tcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 37 */     PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceType").equal("機房門禁");
/* 38 */     return tcConfigMap.values((Predicate)pb);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isAlarm(DeviceTcConfig tcConfig) {
/* 43 */     boolean result = false;
/* 44 */     if (tcConfig.getExtend() != null) {
/* 45 */       RtuConfig rtuConfig = (RtuConfig)this.gson.fromJson(tcConfig.getExtend(), RtuConfig.class);
/* 46 */       if (rtuConfig.isAlarm()) {
/* 47 */         IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/* 48 */         Integer value = (Integer)dataMap.get(tcConfig.getDeviceName());
/* 49 */         if (value != null) {
/* 50 */           if (value.intValue() == 0 || value.intValue() == -1) {
/* 51 */             result = true;
/*    */           } else {
/* 53 */             result = false;
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/* 58 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected AlarmLogDTO toDTO(DeviceTcConfig tcConfig, AlarmState alarmState, Optional<Integer> optDegree) {
/* 64 */     IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/* 65 */     Integer value = (Integer)dataMap.get(tcConfig.getDeviceName());
/* 66 */     AlarmLogDTO dto = new AlarmLogDTO();
/* 67 */     dto.setAlarmSubType(AlarmType.DOOR_OPEN.toString());
/* 68 */     dto.setDeviceName(tcConfig.getDeviceName());
/* 69 */     dto.setAlarmState(alarmState);
/* 70 */     dto.setTimestamp(new Date());
/* 71 */     dto.setDegree(optDegree.orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.DOOR_OPEN, 3.0D))));
/* 72 */     if (value != null) {
/* 73 */       if (value.intValue() == 0) {
/* 74 */         dto.setMessage("開啟中");
/* 75 */       } else if (value.intValue() == 1) {
/* 76 */         dto.setMessage("恢復正常");
/*    */       } else {
/* 78 */         dto.setMessage("訊號未接收!");
/*    */       } 
/*    */     } else {
/* 81 */       dto.setMessage("訊號未接收!");
/*    */     } 
/* 83 */     return dto;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\DoorOpenAlarmChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */