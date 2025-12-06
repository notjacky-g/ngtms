/*    */ package com.hwacom.ngtms.ao.fm.service;
/*    */ 
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hazelcast.query.Predicate;
/*    */ import com.hazelcast.query.PredicateBuilder;
/*    */ import com.hwacom.ngtms.alarm.fm.shared.dto.AlarmLogDTO;
/*    */ import com.hwacom.ngtms.alarm.shared.AlarmState;
/*    */ import com.hwacom.ngtms.ao.shared.AlarmType;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*    */ import com.hwacom.ngtms.c.fm.model.DeviceTcStatus;
/*    */ import java.util.Collection;
/*    */ import java.util.Date;
/*    */ import java.util.Optional;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class RtuOfflineAlarmChecker
/*    */   extends AlarmChecker<DeviceTcStatus>
/*    */ {
/*    */   protected Collection<DeviceTcStatus> source() {
/* 27 */     IMap<String, DeviceTcStatus> deviceStatusMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcStatus);
/* 28 */     PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceType").equal("RTU");
/* 29 */     return deviceStatusMap.values((Predicate)pb);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isAlarm(DeviceTcStatus status) {
/* 34 */     return !status.isAlive();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected AlarmLogDTO toDTO(DeviceTcStatus status, AlarmState alarmState, Optional<Integer> optDegree) {
/* 40 */     AlarmLogDTO dto = new AlarmLogDTO();
/* 41 */     dto.setAlarmSubType(AlarmType.RTU_OFFLINE.toString());
/* 42 */     dto.setDeviceName(status.getDeviceName());
/* 43 */     dto.setAlarmState(alarmState);
/* 44 */     dto.setTimestamp(new Date());
/* 45 */     dto.setDegree(optDegree.orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.RTU_OFFLINE, 1.0D))));
/* 46 */     if (status.getCommStatus().intValue() == 0) {
/* 47 */       dto.setMessage("連線");
/*    */     } else {
/* 49 */       dto.setMessage("斷線");
/*    */     } 
/* 51 */     return dto;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\RtuOfflineAlarmChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */