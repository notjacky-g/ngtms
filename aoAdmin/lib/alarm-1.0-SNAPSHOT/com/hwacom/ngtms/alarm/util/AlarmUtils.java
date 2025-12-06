/*    */ package com.hwacom.ngtms.alarm.util;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gwt.user.client.rpc.IsSerializable;
/*    */ import com.hwacom.ngtms.alarm.fm.hz.AlarmHzQueue;
/*    */ import com.hwacom.ngtms.alarm.fm.shared.AlarmClassification;
/*    */ import com.hwacom.ngtms.alarm.fm.shared.dto.AlarmLogDTO;
/*    */ import com.hwacom.ngtms.alarm.shared.AlarmState;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import java.util.Date;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AlarmUtils
/*    */   implements IsSerializable
/*    */ {
/* 23 */   private static Logger logger = LoggerFactory.getLogger(AlarmUtils.class);
/* 24 */   private static Gson gson = new Gson();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T extends com.hwacom.ngtms.alarm.fm.shared.AlarmLogContext> void sendAlarmLogDTO(AlarmClassification classification, String alarmSubType, String deviceName, String alarmSessionId, Date timestamp, Integer degree, T contextData) {
/* 34 */     logger.debug("sendAlarmLogDTO, classification='{}', alarmSubType='{}', deviceName='{}' alarmSessionId='{}', timestamp='{}', degree='{}', contextData='{}'", new Object[] { classification, alarmSubType, deviceName, alarmSessionId, timestamp, degree, contextData });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 43 */     AlarmLogDTO dto = new AlarmLogDTO();
/* 44 */     dto.setAlarmClassification(classification);
/* 45 */     dto.setAlarmSubType(alarmSubType);
/* 46 */     dto.setDeviceName(deviceName);
/* 47 */     dto.setAlarmSessionId(alarmSessionId);
/* 48 */     dto.setTimestamp(timestamp);
/* 49 */     dto.setDegree(degree);
/* 50 */     AlarmState alarmState = AlarmState.UNACK_ALM;
/* 51 */     if (degree.intValue() == 0) {
/* 52 */       alarmState = AlarmState.UNACK_RTN;
/*    */     }
/* 54 */     dto.setAlarmState(alarmState);
/* 55 */     if (contextData != null) {
/* 56 */       dto.setContextData(gson.toJson(contextData));
/*    */     }
/* 58 */     if (!HzUtils.getQueue((HzDistObjEnum)AlarmHzQueue.Message).offer(dto)) {
/* 59 */       logger.error("Failed to insert the alarm message to queue, {}", dto);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void sendAlarmLogDTOWithoutContext(AlarmClassification classification, String alarmSubType, String deviceName, String alarmSessionId, Date timestamp, Integer degree) {
/* 70 */     sendAlarmLogDTO(classification, alarmSubType, deviceName, alarmSessionId, timestamp, degree, null);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\alarm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\alar\\util\AlarmUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */