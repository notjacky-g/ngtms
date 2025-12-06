/*    */ package com.hwacom.ngtms.ao.fm.service;
/*    */ 
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hwacom.ngtms.alarm.fm.model.AlarmLog;
/*    */ import com.hwacom.ngtms.alarm.fm.service.AlarmPostProcessor;
/*    */ import com.hwacom.ngtms.alarm.fm.shared.AlarmMessage;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*    */ import com.hwacom.ngtms.c.fm.model.AlarmSubTypeConfig;
/*    */ import com.hwacom.ngtms.c.fm.service.AlarmMailNotifyService;
/*    */ import com.hwacom.ngtms.c.fm.service.AlarmSmsNotifyService;
/*    */ import java.util.List;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.context.annotation.Profile;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ @Profile({"ao"})
/*    */ public class AlarmPostProcessorImpl
/*    */   implements AlarmPostProcessor
/*    */ {
/* 34 */   private Logger logger = LoggerFactory.getLogger(AlarmPostProcessorImpl.class);
/*    */   
/*    */   @Autowired
/*    */   private AlarmSmsNotifyService alarmSmsNotifyService;
/*    */   
/*    */   @Autowired
/*    */   private AlarmMailNotifyService alarmMailNotifyService;
/*    */   
/*    */   public void process(List<AlarmLog> alarmLogs) {}
/*    */   
/*    */   public void notify(List<AlarmMessage> alarmMessages) {
/* 45 */     IMap<Integer, AlarmSubTypeConfig> alarmSubTypeConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 46 */     for (AlarmMessage message : alarmMessages) {
/* 47 */       AlarmLog log = message.getAlarmLog();
/* 48 */       if (log == null) {
/* 49 */         this.logger.warn("Notify failed, alarmLog is null, alarmMessage='{}'", message);
/*    */         
/*    */         continue;
/*    */       } 
/* 53 */       AlarmSubTypeConfig subType = (AlarmSubTypeConfig)alarmSubTypeConfigMap.get(Integer.valueOf(log.getAlarmSubType()));
/* 54 */       if (subType == null) {
/* 55 */         this.logger.warn("Notify failed, AlarmSubType is null, alarmMessage='{}'", message);
/*    */         continue;
/*    */       } 
/* 58 */       if (subType.getSendSms() != null && subType.getSendSms().booleanValue()) {
/* 59 */         this.logger.debug("alarmSubTypeConfig.getSendSms : {}", subType.getSendSms());
/* 60 */         this.alarmSmsNotifyService.onAlarmOccured(message);
/*    */       } else {
/* 62 */         this.logger.debug("alarmSubTypeConfig.getSendSms : null");
/*    */       } 
/*    */       
/* 65 */       if (subType.getSendMail() != null && subType.getSendMail().booleanValue()) {
/* 66 */         this.logger.debug("alarmSubTypeConfig.getSendMail : {}", subType.getSendMail());
/* 67 */         this.alarmMailNotifyService.onAlarmOccured(message); continue;
/*    */       } 
/* 69 */       this.logger.debug("alarmSubTypeConfig.getSendMail : null");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\AlarmPostProcessorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */