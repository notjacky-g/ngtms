/*     */ package com.hwacom.ngtms.c.fm.service;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.alarm.fm.model.AlarmLog;
/*     */ import com.hwacom.ngtms.alarm.fm.shared.AlarmMessage;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*     */ import com.hwacom.ngtms.base.sms.SmsClient;
/*     */ import com.hwacom.ngtms.base.sms.shared.SendSmsCallback;
/*     */ import com.hwacom.ngtms.base.sms.shared.SmsSendResponse;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.AlarmSubTypeConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadLine;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadSection;
/*     */ import com.hwacom.ngtms.c.shared.AlarmLogEventContext;
/*     */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*     */ import com.hwacom.ngtms.common.fm.model.User;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.StringJoiner;
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
/*     */ public class AlarmSmsNotifyService
/*     */ {
/*  40 */   private static Logger logger = LoggerFactory.getLogger(AlarmSmsNotifyService.class);
/*     */   public static final String SMS_ACCOUNT = "10371";
/*     */   public static final String SMS_PASSWORD = "10371";
/*     */   @Autowired
/*     */   private MessageSourceExt messageSourceExt;
/*     */   @Autowired
/*     */   SmsClient smsClient;
/*     */   
/*     */   public void onAlarmOccured(AlarmMessage alarmMessage) {
/*  49 */     logger.debug("Start to process related Sms AlarmMessage ...");
/*  50 */     if (alarmMessage == null) {
/*  51 */       logger.error("alarmMessage is null or alarmMessage.getAlarmSubType is null! do nothing!");
/*     */       return;
/*     */     } 
/*  54 */     AlarmLog alarmLog = alarmMessage.getAlarmLog();
/*  55 */     if (alarmLog == null) {
/*  56 */       logger.error("log is null or alarmMessage.getAlarmLog is null! do nothing!");
/*     */       return;
/*     */     } 
/*  59 */     Integer alarmSubType = null;
/*     */     try {
/*  61 */       alarmSubType = Integer.valueOf(alarmLog.getAlarmSubType());
/*  62 */     } catch (NumberFormatException e) {
/*  63 */       logger.error("Transfer alarmSubType failed, alarmSubType='{}'", alarmLog
/*  64 */           .getAlarmSubType(), e);
/*     */     } 
/*  66 */     if (alarmSubType == null) {
/*  67 */       logger.error("alarmSubType is null or alarmLog.getAlarmSubType is null! do nothing!");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  72 */     IMap<Integer, AlarmSubTypeConfig> alarmSubTypeConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  73 */     AlarmSubTypeConfig alarmSubTypeConfig = (AlarmSubTypeConfig)alarmSubTypeConfigMap.get(alarmSubType);
/*  74 */     if (alarmSubTypeConfig == null) {
/*  75 */       logger.error("Can not find alarmSubTypeConfig by id='{}', do nothing!", alarmSubType);
/*     */       return;
/*     */     } 
/*  78 */     logger.info("AlarmSubType : {} ; AlarmSubType.getId : {}", alarmSubTypeConfig, alarmSubTypeConfig
/*     */ 
/*     */         
/*  81 */         .getId());
/*  82 */     sendSms(alarmLog, alarmSubTypeConfig);
/*     */   }
/*     */   
/*     */   private void sendSms(AlarmLog alarmLog, AlarmSubTypeConfig alarmSubTypeConfig) {
/*  86 */     long t1 = System.currentTimeMillis();
/*     */     
/*  88 */     String roleStr = alarmSubTypeConfig.getSmsRoles();
/*  89 */     String[] roles = null;
/*  90 */     if (roleStr != null) {
/*  91 */       roles = roleStr.split(",");
/*     */     }
/*  93 */     String userStr = alarmSubTypeConfig.getSmsUsers();
/*  94 */     String[] users = null;
/*  95 */     if (userStr != null) {
/*  96 */       users = userStr.split(",");
/*     */     }
/*  98 */     String otherSmsStr = alarmSubTypeConfig.getSmsOtherUsers();
/*     */     
/* 100 */     Set<String> smsSet = new HashSet<>();
/* 101 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 102 */     if (userMap != null && roles != null)
/*     */     {
/*     */       
/* 105 */       label89: for (User user : userMap.values()) {
/* 106 */         for (String role : user.getRoleNames()) {
/* 107 */           for (String tempRole : roles) {
/* 108 */             if (tempRole.trim().equals(role.trim())) {
/* 109 */               smsSet.add(user.getMobile());
/*     */               continue label89;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 116 */     if (userMap != null && users != null)
/*     */     {
/* 118 */       for (User user : userMap.values()) {
/* 119 */         for (String tempUser : users) {
/* 120 */           if (tempUser.trim().equals(user.getLogin().trim())) {
/* 121 */             smsSet.add(user.getMobile());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 127 */     StringJoiner joiner = new StringJoiner(",");
/* 128 */     for (String userSms : smsSet) {
/* 129 */       joiner.add(userSms);
/*     */     }
/* 131 */     String joinedString = joiner.toString();
/*     */     
/* 133 */     if (otherSmsStr != null) {
/* 134 */       joinedString = joinedString + "," + otherSmsStr;
/*     */     }
/* 136 */     String[] toMembers = joinedString.split(",");
/* 137 */     logger.debug("send sms all members : {}", joinedString);
/*     */     
/* 139 */     String alarmSubTypeDesc = this.messageSourceExt.getMessage("smgFm.AlarmSubType." + alarmLog.getAlarmSubType());
/*     */     
/* 141 */     String content = (alarmSubTypeConfig.getAlarmMessage() != null) ? alarmSubTypeConfig.getAlarmMessage() : "";
/* 142 */     IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 143 */     AlarmLogEventContext contextData = getContextData(alarmLog);
/* 144 */     String deviceName = (contextData == null) ? null : contextData.getDeviceName();
/* 145 */     String displayName = deviceName;
/* 146 */     if (deviceName != null) {
/* 147 */       DeviceTcConfig deviceConfig = (DeviceTcConfig)deviceConfigMap.get(deviceName);
/* 148 */       if (deviceConfig != null) {
/* 149 */         displayName = deviceConfig.getDisplayName();
/*     */       }
/*     */     } 
/*     */     
/* 153 */     logger.debug("send sms all members : {}", joinedString);
/* 154 */     logger.debug("send sms all toMembers : {}", (toMembers != null) ? toMembers.toString() : "");
/* 155 */     IMap<String, RoadLine> roadLineMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadLine);
/* 156 */     IMap<String, RoadSection> roadSectionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadSection);
/* 157 */     StringBuilder sendMessage = new StringBuilder();
/* 158 */     sendMessage.append("告警類別 :").append(alarmSubTypeDesc).append(";");
/* 159 */     if (displayName != null && displayName.length() != 0) {
/* 160 */       sendMessage.append("告警設備 :").append(displayName).append(";");
/*     */     }
/* 162 */     if (content != null && content.length() > 0) {
/* 163 */       sendMessage.append("告警內容 :").append(content).append(";");
/*     */     }
/* 165 */     if (contextData.getLineId() != null) {
/* 166 */       RoadLine roadLine = (RoadLine)roadLineMap.get(contextData.getLineId());
/* 167 */       if (roadLine != null) {
/* 168 */         sendMessage.append("路線 :").append(roadLine.getLineName()).append(";");
/*     */       }
/*     */     } 
/* 171 */     if (contextData.getSectionId() != null) {
/* 172 */       RoadSection roadSection = (RoadSection)roadSectionMap.get(contextData.getSectionId());
/* 173 */       if (roadSection != null) {
/* 174 */         sendMessage.append("路段 :").append(roadSection.getSectionName()).append(";");
/*     */       }
/*     */     } 
/* 177 */     if (contextData.getStartMileage() != null) {
/* 178 */       sendMessage.append("起點里程數 :").append(contextData.getStartMileage()).append(";");
/*     */     }
/* 180 */     if (contextData.getEndMileage() != null) {
/* 181 */       sendMessage.append("終點里程數 :").append(contextData.getEndMileage()).append(";");
/*     */     }
/*     */     
/* 184 */     this.smsClient.setSmsEnabled(true);
/* 185 */     this.smsClient.sendSmsAsync(new SendSmsCallback()
/*     */         {
/*     */ 
/*     */           
/*     */           public void onSendResponse(Map<String, List<SmsSendResponse>> resultMap)
/*     */           {
/* 191 */             AlarmSmsNotifyService.logger.debug("send Sms Async resultMap : {}", resultMap);
/*     */           }
/* 194 */         }sendMessage.toString(), toMembers);
/*     */     
/* 196 */     long t2 = System.currentTimeMillis();
/* 197 */     logger.debug("send sms => AlarmSubType :{};times:{};smsMembers:{}", new Object[] { alarmLog
/*     */           
/* 199 */           .getAlarmSubType().toString(), 
/* 200 */           Long.valueOf(t2 - t1), joinedString });
/*     */   }
/*     */ 
/*     */   
/*     */   private AlarmLogEventContext getContextData(AlarmLog log) {
/* 205 */     Gson gson = new Gson();
/*     */     try {
/* 207 */       return (AlarmLogEventContext)gson.fromJson(log.getContextData(), AlarmLogEventContext.class);
/* 208 */     } catch (JsonSyntaxException e) {
/* 209 */       logger.error("getContextData failed, log='{}'", log, e);
/* 210 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\service\AlarmSmsNotifyService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */