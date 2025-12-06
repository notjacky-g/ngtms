/*     */ package com.hwacom.ngtms.c.dis.fm.service;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.dis.fm.hz.DisHzMap;
/*     */ import com.hwacom.ngtms.c.dis.fm.model.DisFullTextConfig;
/*     */ import com.hwacom.ngtms.c.dis.fm.model.DisFullTextType;
/*     */ import com.hwacom.ngtms.c.dis.fm.model.DisGraphic;
/*     */ import com.hwacom.ngtms.c.dis.fm.model.DisGraphicConfig;
/*     */ import com.hwacom.ngtms.c.dis.fm.model.DisMessageCompareLog;
/*     */ import com.hwacom.ngtms.c.dis.fm.model.DisPhraseConfig;
/*     */ import com.hwacom.ngtms.c.dis.fm.model.DisPhraseType;
/*     */ import com.hwacom.ngtms.c.dis.fm.repository.DisGraphicConfigRepository;
/*     */ import com.hwacom.ngtms.c.dis.fm.repository.DisGraphicRepository;
/*     */ import com.hwacom.ngtms.c.dis.fm.repository.DisMessageCompareLogRepository;
/*     */ import com.hwacom.ngtms.c.dis.shared.BrightType;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.ncc.client.NccClient;
/*     */ import com.hwacom.ngtms.c.ncc.client.TcResponseCallback;
/*     */ import com.hwacom.ngtms.ncc.remote.TcResponse;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.SetSunriseReqPm;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.imageio.ImageIO;
/*     */ import org.joda.time.DateTime;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DisManager
/*     */ {
/*  62 */   private static Logger logger = LoggerFactory.getLogger(DisManager.class);
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   protected DisGraphicConfigRepository disGraphicConfigRepository;
/*     */   
/*     */   @Autowired
/*     */   protected DisGraphicRepository disGraphicRepository;
/*     */   
/*     */   @Autowired
/*     */   protected NccClient nccClient;
/*     */   
/*     */   @Autowired
/*     */   DisMessageCompareLogRepository disMessageCompareLogRepository;
/*     */   
/*     */   protected IMap<String, DisGraphicConfig> disGraphicConfigMap;
/*     */   
/*     */   protected IMap<String, DisPhraseConfig> disPhraseConfigMap;
/*     */   
/*     */   protected IMap<String, DisPhraseType> disPhraseTypeMap;
/*     */ 
/*     */   
/*     */   protected void saveCompareLog(String deviceName, String commandMessage, String deviceDisplay) {
/*  85 */     DisMessageCompareLog log = new DisMessageCompareLog();
/*     */     
/*  87 */     IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  88 */     DeviceTcConfig deviceConfig = (DeviceTcConfig)deviceConfigMap.get(deviceName);
/*     */     
/*  90 */     log.setDataTime(new Date());
/*  91 */     log.setCommandMessage(commandMessage);
/*  92 */     log.setDeviceDisplay(deviceDisplay);
/*  93 */     log.setDeviceName(deviceName);
/*  94 */     log.setDeviceType(deviceConfig.getDeviceType());
/*  95 */     this.disMessageCompareLogRepository.save(log);
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
/*     */   public Boolean saveGraphicConfig(DisGraphicConfig disGraphicConfig, BufferedImage bufferedImage) {
/* 107 */     boolean result = false;
/*     */     
/* 109 */     if (null == disGraphicConfig || null == bufferedImage) {
/* 110 */       return Boolean.valueOf(result);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 115 */     DisGraphic newDisGraphic = new DisGraphic();
/* 116 */     String id = disGraphicConfig.getId();
/* 117 */     newDisGraphic.setDisGraphicConfigId(id);
/*     */     try {
/* 119 */       newDisGraphic.setPicture(bufferedImageToByteArray(bufferedImage, "jpg"));
/* 120 */     } catch (IOException e) {
/* 121 */       logger.error("bufferedImageToByteArray 轉換失敗  ", e);
/* 122 */       return Boolean.valueOf(result);
/*     */     } 
/*     */ 
/*     */     
/* 126 */     this.disGraphicConfigRepository.save(disGraphicConfig);
/* 127 */     this.disGraphicRepository.save(newDisGraphic);
/*     */ 
/*     */     
/* 130 */     result = true;
/*     */     
/* 132 */     return Boolean.valueOf(result);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BufferedImage convertByteArray2BufferedImage(byte[] byteArray) throws IOException {
/* 143 */     InputStream in = new ByteArrayInputStream(byteArray);
/*     */     
/* 145 */     return ImageIO.read(in);
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
/*     */   
/*     */   private byte[] bufferedImageToByteArray(BufferedImage bufferedImage, String formatName) throws IOException {
/* 158 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 159 */     ImageIO.write(bufferedImage, formatName, baos);
/* 160 */     baos.flush();
/* 161 */     byte[] imageInByte = baos.toByteArray();
/* 162 */     baos.close();
/*     */     
/* 164 */     return imageInByte;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferedImage getBufferedImage(String disGraphicConfigId) {
/* 170 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean saveDisPhraseConfig(List<DisPhraseConfig> disPhraseConfigs) {
/* 176 */     this.disPhraseTypeMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.PhraseType);
/* 177 */     this.disPhraseConfigMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.PhraseConfig);
/*     */ 
/*     */     
/* 180 */     for (DisPhraseConfig disPhraseConfig : disPhraseConfigs) {
/*     */ 
/*     */       
/* 183 */       if (this.disPhraseTypeMap.containsKey(disPhraseConfig.getPhraseType()))
/*     */       {
/* 185 */         this.disPhraseConfigMap.put(disPhraseConfig.getId(), disPhraseConfig);
/*     */       }
/*     */     } 
/*     */     
/* 189 */     return Boolean.FALSE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean saveDisPhraseType(DisPhraseType disPhraseType) {
/*     */     try {
/* 196 */       this.disPhraseTypeMap.put(disPhraseType.getId(), disPhraseType);
/* 197 */       return Boolean.TRUE;
/* 198 */     } catch (Exception e) {
/* 199 */       logger.error("saveDisPhraseType ", e);
/* 200 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean saveDisFullTextConfig(DisFullTextConfig disFullTextConfig) {
/* 211 */     IMap<String, DisFullTextConfig> disFullTextConfigMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.FullTextConfig);
/* 212 */     IMap<String, DisFullTextType> disFullTextTypeMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.FullTextType);
/*     */     
/* 214 */     Preconditions.checkNotNull(disFullTextConfig.getDisFullText(), "未輸入全文庫內容");
/* 215 */     Preconditions.checkNotNull(disFullTextTypeMap
/* 216 */         .get(disFullTextConfig.getDisFullText().getFullTextType()), "FullTextType不存在");
/*     */ 
/*     */     
/*     */     try {
/* 220 */       disFullTextConfigMap.put(disFullTextConfig.getId(), disFullTextConfig);
/* 221 */       return Boolean.TRUE;
/* 222 */     } catch (Exception e) {
/* 223 */       logger.error("saveDisFullTextConfig ", e);
/* 224 */       return Boolean.FALSE;
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public List<AbstractMap.SimpleEntry<String, Long>> sendGraphic2Tc(List<String> deviceNames, String disGraphicConfigId) {
/* 244 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean sendNewWord2Tc() {
/* 261 */     return Boolean.FALSE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public List<AbstractMap.SimpleEntry<String, Long>> getBrightType(List<String> deviceNames) {
/* 272 */     return null;
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public List<AbstractMap.SimpleEntry<String, Long>> setBrightType(List<String> deviceNames, BrightType brightType) {
/* 288 */     return null;
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public List<AbstractMap.SimpleEntry<String, Long>> setSunshineConfig(List<String> deviceNames, Date sunrise, Date sunset) {
/* 304 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendSunshineConfig2Tc(List<String> deviceNames, Date sunrise, Date sunset) {
/* 309 */     DateTime sunriseDataTime = new DateTime(sunrise);
/* 310 */     DateTime sunsetDataTime = new DateTime(sunset);
/*     */     
/* 312 */     final SetSunriseReqPm setSunriseReqPm = new SetSunriseReqPm();
/*     */     
/* 314 */     setSunriseReqPm.sunriseHour = sunriseDataTime.getHourOfDay();
/* 315 */     setSunriseReqPm.sunriseMinute = sunriseDataTime.getMinuteOfHour();
/* 316 */     setSunriseReqPm.sunsetHour = sunsetDataTime.getHourOfDay();
/* 317 */     setSunriseReqPm.sunsetMinute = sunsetDataTime.getMinuteOfHour();
/*     */     
/* 319 */     this.nccClient.sendAsyncRequest2MulipleTc(deviceNames
/* 320 */         .<String>toArray(new String[deviceNames.size()]), setSunriseReqPm, new TcResponseCallback()
/*     */         {
/*     */ 
/*     */           
/*     */           public void onResponse(String reqSessionId, String deviceName, TcResponse tcResponse)
/*     */           {
/* 326 */             DisManager.logger.debug("sendSunshineConfig2Tc  deviceName ={},setSunriseReqPm ={} ", deviceName, setSunriseReqPm);
/*     */ 
/*     */ 
/*     */             
/* 330 */             if (TcResponse.Result.SUCCESS != tcResponse.getResult()) {
/* 331 */               DisManager.logger.warn("setSunriseReqPm failed, deviceName ={}, tcResponse ={}", deviceName, tcResponse);
/*     */             }
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public List<AbstractMap.SimpleEntry<String, Long>> getSunshineConfig(List<String> deviceNames) {
/* 346 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean saveDisFullTextType(DisFullTextType disFullTextType) {
/*     */     try {
/* 353 */       IMap<String, DisFullTextType> disFullTextTypeMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.FullTextType);
/* 354 */       disFullTextTypeMap.put(disFullTextType.getId(), disFullTextType);
/* 355 */       return Boolean.TRUE;
/* 356 */     } catch (Exception e) {
/* 357 */       logger.error("saveDisFullTextType ", e);
/* 358 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void processBrightType(BrightType paramBrightType, String paramString);
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\service\DisManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */