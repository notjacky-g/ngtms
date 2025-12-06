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
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.AlarmSubTypeConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadLine;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadSection;
/*     */ import com.hwacom.ngtms.c.shared.AlarmLogEventContext;
/*     */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*     */ import com.hwacom.ngtms.common.fm.model.User;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.StringJoiner;
/*     */ import javax.annotation.Resource;
/*     */ import javax.mail.MessagingException;
/*     */ import javax.mail.internet.MimeMessage;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.mail.MailException;
/*     */ import org.springframework.mail.javamail.JavaMailSenderImpl;
/*     */ import org.springframework.mail.javamail.MimeMessageHelper;
/*     */ import org.springframework.scheduling.annotation.Async;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class AlarmMailNotifyService
/*     */ {
/*  44 */   private static Logger logger = LoggerFactory.getLogger(AlarmMailNotifyService.class);
/*     */   
/*     */   @Autowired
/*     */   private JavaMailSenderImpl mailSender;
/*     */   
/*     */   public void onAlarmOccured(AlarmMessage alarmMessage) {
/*  50 */     logger.debug("Start to process related Mail AlarmMessage ...");
/*  51 */     if (alarmMessage == null) {
/*  52 */       logger.error("alarmMessage is null or alarmMessage.getAlarmSubType is null! do nothing!");
/*     */       return;
/*     */     } 
/*  55 */     AlarmLog alarmLog = alarmMessage.getAlarmLog();
/*  56 */     if (alarmLog == null) {
/*  57 */       logger.error("log is null or alarmMessage.getAlarmLog is null! do nothing!");
/*     */       return;
/*     */     } 
/*  60 */     Integer alarmSubType = null;
/*     */     try {
/*  62 */       alarmSubType = Integer.valueOf(alarmLog.getAlarmSubType());
/*  63 */     } catch (NumberFormatException e) {
/*  64 */       logger.error("Transfer alarmSubType failed, alarmSubType='{}'", alarmLog
/*  65 */           .getAlarmSubType(), e);
/*     */     } 
/*  67 */     if (alarmSubType == null) {
/*  68 */       logger.error("alarmSubType is null or alarmLog.getAlarmSubType is null! do nothing!");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  73 */     IMap<Integer, AlarmSubTypeConfig> alarmSubTypeConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  74 */     AlarmSubTypeConfig alarmSubTypeConfig = (AlarmSubTypeConfig)alarmSubTypeConfigMap.get(alarmSubType);
/*  75 */     if (alarmSubTypeConfig == null) {
/*  76 */       logger.error("Can not find alarmSubTypeConfig by id='{}', do nothing!", alarmSubType);
/*     */       return;
/*     */     } 
/*  79 */     logger.info("AlarmSubType : {} ; AlarmSubType.getId : {}", alarmSubTypeConfig, alarmSubTypeConfig
/*     */ 
/*     */         
/*  82 */         .getId());
/*  83 */     sendMail(alarmLog, alarmSubTypeConfig);
/*     */   } @Autowired
/*     */   private MessageSourceExt messageSourceExt; @Resource
/*     */   private Environment environment; private void sendMail(AlarmLog alarmLog, AlarmSubTypeConfig alarmSubTypeConfig) {
/*  87 */     long t1 = System.currentTimeMillis();
/*     */     
/*  89 */     String roleStr = alarmSubTypeConfig.getMailRoles();
/*  90 */     String[] roles = null;
/*  91 */     if (roleStr != null) {
/*  92 */       roles = roleStr.split(",");
/*     */     }
/*  94 */     String userStr = alarmSubTypeConfig.getMailUsers();
/*  95 */     String[] users = null;
/*  96 */     if (userStr != null) {
/*  97 */       users = userStr.split(",");
/*     */     }
/*  99 */     String otherMailStr = alarmSubTypeConfig.getMailOtherUsers();
/*     */     
/* 101 */     Set<String> mailSet = new HashSet<>();
/* 102 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 103 */     if (userMap != null && roles != null)
/*     */     {
/*     */       
/* 106 */       label84: for (User user : userMap.values()) {
/* 107 */         for (String role : user.getRoleNames()) {
/* 108 */           for (String tempRole : roles) {
/* 109 */             if (tempRole.trim().equals(role.trim())) {
/* 110 */               mailSet.add(user.getMail());
/*     */               continue label84;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 117 */     if (userMap != null && users != null)
/*     */     {
/* 119 */       for (User user : userMap.values()) {
/* 120 */         for (String tempUser : users) {
/* 121 */           if (tempUser.trim().equals(user.getLogin().trim())) {
/* 122 */             mailSet.add(user.getMail());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 128 */     StringJoiner joiner = new StringJoiner(",");
/* 129 */     for (String userMail : mailSet) {
/* 130 */       joiner.add(userMail);
/*     */     }
/* 132 */     String joinedString = joiner.toString();
/*     */     
/* 134 */     if (otherMailStr != null) {
/* 135 */       if (joinedString != null && joinedString.length() > 0) {
/* 136 */         joinedString = joinedString + "," + otherMailStr;
/*     */       } else {
/* 138 */         joinedString = otherMailStr;
/*     */       } 
/*     */     }
/* 141 */     logger.debug("send mail all members String : {}", joinedString);
/* 142 */     if (joinedString == null) {
/* 143 */       logger.info("send mail all members is null or empty! So don't send mail!");
/*     */       return;
/*     */     } 
/* 146 */     String[] toMails = StringUtils.split(joinedString, ",");
/* 147 */     if (toMails != null)
/* 148 */     { logger.debug("send mail all members ,toMails : {}", toMails.toString());
/* 149 */       String alarmSubTypeDesc = alarmSubTypeConfig.getDescription();
/*     */       
/* 151 */       String content = (alarmSubTypeConfig.getAlarmMessage() != null) ? alarmSubTypeConfig.getAlarmMessage() : "";
/* 152 */       IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 153 */       AlarmLogEventContext contextData = getContextData(alarmLog);
/* 154 */       String deviceName = (contextData == null) ? null : contextData.getDeviceName();
/* 155 */       String displayName = deviceName;
/* 156 */       if (deviceName != null) {
/* 157 */         DeviceTcConfig deviceConfig = (DeviceTcConfig)deviceConfigMap.get(deviceName);
/* 158 */         if (deviceConfig != null) {
/* 159 */           displayName = deviceConfig.getDisplayName();
/*     */         }
/*     */       } 
/* 162 */       logger.debug("send mail deviceName : {} ,alarmSubTypeDesc: {},content : {}", new Object[] { deviceName, alarmSubTypeDesc, content });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 168 */         sendMailNotificaitoin(alarmLog, contextData, alarmSubTypeDesc, content, toMails, deviceName);
/*     */       }
/* 170 */       catch (MailException e) {
/* 171 */         logger.error("MailException Error :", (Throwable)e);
/* 172 */       } catch (InterruptedException e) {
/* 173 */         logger.error("InterruptedException Error :", e);
/*     */       }  }
/* 175 */     else { logger.debug("send mail all members ,toMails : null"); }
/* 176 */      long t2 = System.currentTimeMillis();
/* 177 */     logger.debug("AlarmSubType:{};times:{};mailMembers:{}", new Object[] { alarmLog
/*     */           
/* 179 */           .getAlarmSubType(), 
/* 180 */           Long.valueOf(t2 - t1), joinedString });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Async
/*     */   public void sendMailNotificaitoin(AlarmLog alarmLog, AlarmLogEventContext contextData, String alarmSubTypeDesc, String content, String[] toMails, String displayName) throws MailException, InterruptedException {
/* 193 */     long t1 = System.currentTimeMillis();
/* 194 */     logger.debug("AlarmSubType :{};", alarmSubTypeDesc);
/* 195 */     if (toMails != null) {
/* 196 */       for (String toMail : toMails)
/* 197 */         logger.debug("toMail : {} ;", toMail); 
/*     */     } else {
/* 199 */       logger.debug("toMails is null!");
/* 200 */     }  IMap<String, RoadLine> roadLineMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadLine);
/* 201 */     IMap<String, RoadSection> roadSectionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadSection);
/*     */     
/* 203 */     logger.debug("Sending email...");
/* 204 */     MimeMessage message = this.mailSender.createMimeMessage();
/* 205 */     MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
/* 206 */     StringBuilder sb = new StringBuilder();
/* 207 */     sb.append("<html><body>");
/* 208 */     sb.append("*** 告警發生 ***<br/> ");
/* 209 */     sb.append("告警類別 : ").append(alarmSubTypeDesc).append("<br/>");
/*     */     
/* 211 */     if (displayName != null && displayName.length() != 0) {
/* 212 */       sb.append("告警設備 : ").append(displayName);
/*     */     }
/* 214 */     if (content != null && content.length() > 0) {
/* 215 */       sb.append("告警內容 : ").append(content).append("<br/>");
/*     */     }
/* 217 */     if (contextData.getLineId() != null) {
/* 218 */       RoadLine roadLine = (RoadLine)roadLineMap.get(contextData.getLineId());
/* 219 */       if (roadLine != null) {
/* 220 */         sb.append("路線 :").append(roadLine.getLineName()).append("<br/>");
/*     */       }
/*     */     } 
/* 223 */     if (contextData.getSectionId() != null) {
/* 224 */       RoadSection roadSection = (RoadSection)roadSectionMap.get(contextData.getSectionId());
/* 225 */       if (roadSection != null) {
/* 226 */         sb.append("路段 :").append(roadSection.getSectionName()).append("<br/>");
/*     */       }
/*     */     } 
/* 229 */     if (contextData.getStartMileage() != null) {
/* 230 */       sb.append("起點里程數 :").append(contextData.getStartMileage()).append("<br/>");
/*     */     }
/* 232 */     if (contextData.getEndMileage() != null) {
/* 233 */       sb.append("終點里程數 :").append(contextData.getEndMileage()).append("<br/>");
/*     */     }
/*     */     
/* 236 */     sb.append("</body></html>");
/*     */     
/*     */     try {
/* 239 */       String from = this.environment.getRequiredProperty("mail.username");
/* 240 */       logger.debug("from : {}", from);
/* 241 */       helper.setFrom(from);
/* 242 */       helper.setTo(toMails);
/* 243 */       helper.setSubject("交控中心中央電腦系統告警通知");
/* 244 */       helper.setText(sb.toString(), true);
/* 245 */       this.mailSender.send(message);
/* 246 */     } catch (MessagingException e1) {
/* 247 */       logger.error("Failed to send alarm email", (Throwable)e1);
/*     */     } 
/* 249 */     long t2 = System.currentTimeMillis();
/* 250 */     logger.debug("send mail => AlarmSubType :{};times:{};", alarmSubTypeDesc, Long.valueOf(t2 - t1));
/* 251 */     System.out.println("Email Sent!");
/*     */   }
/*     */   
/*     */   private AlarmLogEventContext getContextData(AlarmLog log) {
/* 255 */     Gson gson = new Gson();
/*     */     try {
/* 257 */       return (AlarmLogEventContext)gson.fromJson(log.getContextData(), AlarmLogEventContext.class);
/* 258 */     } catch (JsonSyntaxException e) {
/* 259 */       logger.error("getContextData failed, log='{}'", log, e);
/* 260 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\service\AlarmMailNotifyService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */