/*    */ package com.hwacom.ngtms.ao.fm.service;
/*    */ 
/*    */ import com.hwacom.ngtms.alarm.fm.shared.dto.AlarmLogDTO;
/*    */ import com.hwacom.ngtms.alarm.shared.AlarmState;
/*    */ import com.hwacom.ngtms.ao.fm.model.NcuCardReaderLogData;
/*    */ import com.hwacom.ngtms.ao.shared.AlarmType;
/*    */ import java.util.Date;
/*    */ import java.util.Optional;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class RoomOpenFailAlarmChecker
/*    */   extends AlarmChecker<NcuCardReaderLogData>
/*    */ {
/*    */   protected boolean isAlarm(NcuCardReaderLogData ncuCardReaderLogData) {
/* 22 */     boolean result = false;
/* 23 */     if (ncuCardReaderLogData.getStatusCode().equals("01")) {
/* 24 */       result = false;
/*    */     } else {
/* 26 */       result = true;
/*    */     } 
/* 28 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AlarmLogDTO toDTO(NcuCardReaderLogData ncuCardReaderLogData, AlarmState alarmState, Optional<Integer> optDegree) {
/* 36 */     AlarmLogDTO dto = new AlarmLogDTO();
/* 37 */     dto.setAlarmSubType(AlarmType.ROOM_OPEN_FAIL.toString());
/* 38 */     dto.setDeviceName(ncuCardReaderLogData.getNcuId());
/* 39 */     dto.setAlarmState(alarmState);
/* 40 */     dto.setTimestamp(new Date());
/* 41 */     dto.setDegree(optDegree.orElseGet(() -> Integer.valueOf(changeToAlarmLevel(AlarmType.ROOM_OPEN_FAIL, 2.0D))));
/* 42 */     dto.setMessage("卡號:" + ncuCardReaderLogData.getCardNumber() + " 開啟失敗");
/* 43 */     return dto;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\RoomOpenFailAlarmChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */