/*     */ package com.hwacom.ngtms.ao.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.ao.fm.marshaller.EqConfigDataXmlMarshaller;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Cctv;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.EqConfigData;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcStatus;
/*     */ import com.hwacom.ngtms.ccs.fm.hz.CcsHzMap;
/*     */ import com.hwacom.ngtms.ccs.shared.CctvConfig;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfigPk;
/*     */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*     */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service
/*     */ public class CctvDeviceConfigService {
/*  35 */   private static final Logger logger = LoggerFactory.getLogger(CctvDeviceConfigService.class);
/*     */   
/*     */   @Autowired
/*     */   private HcceEnv hcceEnv;
/*     */   
/*     */   public void process() {
/*  41 */     EqConfigData eqConfigData = new EqConfigData();
/*  42 */     logger.info("getCctvDevcieConfigXmlData.");
/*  43 */     String urlStr = getDynamicConfig("cctvDeviceConfigUrl").getValue();
/*     */     try {
/*  45 */       URLConnection conn = null;
/*  46 */       URL url = new URL(urlStr);
/*  47 */       conn = url.openConnection();
/*  48 */       conn.setConnectTimeout(10000);
/*     */       
/*  50 */       try (InputStream is = conn.getInputStream()) {
/*  51 */         EqConfigDataXmlMarshaller marshaller = new EqConfigDataXmlMarshaller();
/*  52 */         eqConfigData = (EqConfigData)marshaller.getXstream().fromXML(is);
/*     */       } 
/*  54 */     } catch (IOException e) {
/*  55 */       logger.error("GetCctvDeviceConfigXmlData failed, can not connect to URL=[{}]", urlStr);
/*  56 */       eqConfigData = null;
/*     */     } 
/*  58 */     IMap<String, DeviceTcConfig> configMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  59 */     IMap<String, DeviceTcStatus> statusMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcStatus);
/*  60 */     IMap<String, CctvConfig> cctvConfigMap = HzUtils.getMap((HzDistObjEnum)CcsHzMap.CctvConfig);
/*     */     
/*  62 */     PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceType").equal("CCTV");
/*     */     
/*  64 */     Map<String, CctvConfig> cctvMap = new HashMap<>();
/*  65 */     List<Cctv> cctvs = eqConfigData.getOnedayEqConfigData().getCctvEqConfig().getCctv();
/*  66 */     for (Cctv cctv : cctvs) {
/*     */       try {
/*  68 */         String deviceName = cctv.getUniqueId();
/*  69 */         logger.info("cctv deviceName:'{}', directionId:'{}', mileage:'{}', interUrl:'{}', intraUrl:'{}'", new Object[] { cctv
/*     */               
/*  71 */               .getUniqueId(), cctv
/*  72 */               .getDirectionId(), cctv
/*  73 */               .getMilepost(), cctv
/*  74 */               .getUrlInterPic().getLinkurl(), cctv
/*  75 */               .getUrlIntraPic().getLinkurl() });
/*  76 */         CctvConfig cctvConfig = (CctvConfig)cctvConfigMap.get(cctv.getUniqueId());
/*  77 */         if (cctvConfig == null) {
/*  78 */           cctvConfig = new CctvConfig();
/*     */         }
/*  80 */         cctvConfig.setDeviceName(deviceName);
/*  81 */         cctvConfig.setExternalUrl(cctv.getUrlIntraPic().getLinkurl());
/*  82 */         cctvConfig.setHighUrl(cctv.getUrlInterPic().getLinkurl());
/*  83 */         cctvConfig.setLowUrl(cctv.getUrlInterPic().getLinkurl());
/*  84 */         cctvMap.put(deviceName, cctvConfig);
/*  85 */       } catch (Exception e) {
/*  86 */         logger.error("Save cctv deviceTcConfig failed, deviceName:'{}'", cctv.getUniqueId(), e);
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     cctvConfigMap.putAll(cctvMap);
/*     */ 
/*     */     
/*  93 */     List<String> removeStatus = new ArrayList<>();
/*  94 */     for (DeviceTcStatus status : statusMap.values((Predicate)pb)) {
/*  95 */       if (configMap.get(status.getDeviceName()) != null) {
/*  96 */         removeStatus.add(status.getDeviceName());
/*     */       }
/*     */     } 
/*  99 */     for (String status : removeStatus) {
/* 100 */       statusMap.remove(status);
/*     */     }
/*     */   }
/*     */   
/*     */   private DynamicConfig getDynamicConfig(String configName) {
/* 105 */     IMap<DynamicConfigPk, DynamicConfig> dynamicConfigMap = HzUtils.getMap((HzDistObjEnum)HzMap.DynamicConfig);
/*     */     
/* 107 */     DynamicConfig dynamicConfig = (DynamicConfig)dynamicConfigMap.get(new DynamicConfigPk(this.hcceEnv
/* 108 */           .getCurrentGroupName(), "AoCommonFm", configName));
/* 109 */     return dynamicConfig;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\CctvDeviceConfigService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */