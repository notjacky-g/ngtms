/*     */ package com.hwacom.ngtms.c.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.AlarmSubTypeConfig;
/*     */ import com.hwacom.ngtms.c.shared.AlarmMessage;
/*     */ import com.hwacom.ngtms.c.shared.Direction;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfigPk;
/*     */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*     */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import org.apache.http.NameValuePair;
/*     */ import org.apache.http.client.methods.CloseableHttpResponse;
/*     */ import org.apache.http.client.methods.HttpGet;
/*     */ import org.apache.http.client.methods.HttpUriRequest;
/*     */ import org.apache.http.client.utils.URLEncodedUtils;
/*     */ import org.apache.http.impl.client.CloseableHttpClient;
/*     */ import org.apache.http.message.BasicNameValuePair;
/*     */ import org.apache.http.util.EntityUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.context.annotation.Profile;
/*     */ import org.springframework.scheduling.annotation.Async;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ @Profile({"dev"})
/*     */ public class AlarmEventLockServiceImpl
/*     */   implements AlarmEventService
/*     */ {
/*  43 */   private static Logger logger = LoggerFactory.getLogger(AlarmEventLockServiceImpl.class);
/*  44 */   private static String PARAM_ENCODED = "BIG5";
/*     */   @Autowired
/*     */   private HcceEnv hcceEnv;
/*     */   @Autowired
/*     */   private CloseableHttpClient httpclient;
/*     */   
/*     */   public void onAlarmOccured(AlarmMessage alarmMessage, AlarmSubTypeConfig alarmSubTypeConfig) {
/*  51 */     logger.debug("Start to process event lock AlarmMessage ...");
/*  52 */     if (alarmMessage == null || alarmMessage.getAlarmSubType() == null) {
/*  53 */       logger.error("alarmMessage is null or alarmMessage.getAlarmSubType is null! do nothing!");
/*     */       return;
/*     */     } 
/*  56 */     sendLockEventToCctv(alarmSubTypeConfig, alarmMessage);
/*     */   }
/*     */ 
/*     */   
/*     */   @Async
/*     */   private void sendLockEventToCctv(AlarmSubTypeConfig alarmSubTypeConfig, AlarmMessage alarmMessage) {
/*  62 */     logger.info("sendLockEventToCctv AlarmSubType : {} ; AlarmSubType.getId : {}", alarmMessage
/*     */         
/*  64 */         .getAlarmSubType(), alarmMessage
/*  65 */         .getAlarmSubType());
/*  66 */     logger.debug("sendLockEventToCctv AlarmSubTypeConfigDto : {} ", alarmSubTypeConfig);
/*  67 */     logger.debug("sendLockEventToCctv AlarmMessage : {} ", alarmMessage);
/*     */ 
/*     */     
/*  70 */     String submitUrl = getDynaConfig("CctvEventLockUrl").getValue();
/*  71 */     List<NameValuePair> urlParams = new ArrayList<>();
/*     */     
/*  73 */     String deviceName = alarmMessage.getDeviceName();
/*  74 */     String lineId = alarmMessage.getLineId();
/*  75 */     Integer startMileage = alarmMessage.getStartMileage();
/*  76 */     Integer endMileage = alarmMessage.getEndMileage();
/*  77 */     Direction direction = alarmMessage.getDirection();
/*  78 */     Integer degree = alarmMessage.getDegree();
/*     */ 
/*     */     
/*  81 */     String type = "";
/*  82 */     if (deviceName != null) {
/*  83 */       type = "deviceName";
/*  84 */     } else if (lineId != null && startMileage != null && direction != null) {
/*  85 */       type = "lineDirectionMileage";
/*     */     } else {
/*  87 */       type = "latlng";
/*     */     } 
/*  89 */     urlParams.add(new BasicNameValuePair("type", type));
/*     */     
/*  91 */     urlParams.add(new BasicNameValuePair("deviceName", (deviceName != null) ? deviceName : ""));
/*  92 */     urlParams.add(new BasicNameValuePair("alarmSubType", alarmSubTypeConfig.getDescription()));
/*  93 */     urlParams.add(new BasicNameValuePair("degree", (degree != null) ? String.valueOf(degree) : ""));
/*  94 */     urlParams.add(new BasicNameValuePair("stop", (degree.intValue() == 0) ? "1" : "0"));
/*     */     
/*  96 */     urlParams.add(new BasicNameValuePair("lineId", (lineId != null) ? lineId : ""));
/*  97 */     urlParams.add(new BasicNameValuePair("direction", (direction != null) ? 
/*  98 */           String.valueOf(direction) : ""));
/*  99 */     urlParams.add(new BasicNameValuePair("startMileage", (startMileage != null) ? 
/*     */           
/* 101 */           String.valueOf(startMileage) : ""));
/* 102 */     urlParams.add(new BasicNameValuePair("endMileage", (endMileage != null) ? 
/* 103 */           String.valueOf(endMileage) : ""));
/*     */     
/* 105 */     String paramString = URLEncodedUtils.format(urlParams, PARAM_ENCODED);
/* 106 */     submitUrl = submitUrl + "?" + paramString;
/* 107 */     logger.info("SubmitUrl : {} ", submitUrl);
/*     */     
/* 109 */     HttpGet httpPost = new HttpGet(submitUrl);
/* 110 */     CloseableHttpResponse httpResponse = null;
/*     */     try {
/* 112 */       httpResponse = this.httpclient.execute((HttpUriRequest)httpPost);
/* 113 */       if (200 == httpResponse.getStatusLine().getStatusCode()) {
/* 114 */         String response = EntityUtils.toString(httpResponse.getEntity());
/* 115 */         logger.info("response : {} ", response);
/*     */ 
/*     */         
/* 118 */         IMap<String, AlarmMessage> alarmEventLock = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmEventLock);
/* 119 */         if (degree.intValue() == 0) {
/*     */           
/* 121 */           alarmEventLock.remove(alarmMessage.getAlarmSessionId());
/*     */         } else {
/* 123 */           alarmEventLock.set(alarmMessage.getAlarmSessionId(), alarmMessage);
/*     */         } 
/*     */       } 
/* 126 */     } catch (Exception ex) {
/* 127 */       logger.error("Failed ", ex);
/* 128 */       String description = ex.getMessage();
/* 129 */       if (description == null) {
/* 130 */         Throwable th = ex.getCause();
/* 131 */         if (th != null) description = th.getMessage(); 
/*     */       } 
/*     */     } finally {
/* 134 */       if (httpResponse != null) {
/*     */         try {
/* 136 */           EntityUtils.consume(httpResponse.getEntity());
/* 137 */         } catch (Exception e) {
/* 138 */           logger.error("Failed to consume Entity", e);
/*     */         } 
/*     */         try {
/* 141 */           httpResponse.close();
/* 142 */         } catch (IOException e) {
/* 143 */           logger.error("Failed to close httpResponse", e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private DynamicConfig getDynaConfig(String configName) {
/* 150 */     IMap<DynamicConfigPk, DynamicConfig> dynamicConfigMap = HzUtils.getMap((HzDistObjEnum)HzMap.DynamicConfig);
/*     */     
/* 152 */     DynamicConfig dynamicConfig = (DynamicConfig)dynamicConfigMap.get(new DynamicConfigPk(this.hcceEnv
/*     */           
/* 154 */           .getCurrentGroupName(), "CommonFm", configName));
/* 155 */     return dynamicConfig;
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkExpirtEventLock() {
/* 160 */     IMap<String, AlarmMessage> eventLockMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmEventLock);
/* 161 */     IMap<Integer, AlarmSubTypeConfig> subTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/* 162 */     eventLockMap
/* 163 */       .values()
/* 164 */       .forEach(alarmMessage -> {
/*     */           AlarmSubTypeConfig subType = (AlarmSubTypeConfig)paramIMap.get(alarmMessage.getAlarmSubType());
/*     */           if (subType == null) {
/*     */             logger.warn("Can not find alarmSubType, id='{}'", alarmMessage.getAlarmSubType());
/*     */             return;
/*     */           } 
/*     */           if (subType.getEventLockTime() == null) {
/*     */             logger.info("AlarmSubType doesn't set event lock time, id='{}'", alarmMessage.getAlarmSubType());
/*     */             return;
/*     */           } 
/*     */           Calendar c = Calendar.getInstance();
/*     */           c.setTime(alarmMessage.getTimestamp());
/*     */           c.add(12, subType.getEventLockTime().intValue());
/*     */           if (Calendar.getInstance().after(c)) {
/*     */             alarmMessage.setDegree(Integer.valueOf(0));
/*     */             sendLockEventToCctv(subType, alarmMessage);
/*     */           } 
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\service\AlarmEventLockServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */