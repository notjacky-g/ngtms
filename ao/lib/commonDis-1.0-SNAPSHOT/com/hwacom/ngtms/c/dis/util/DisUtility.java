/*     */ package com.hwacom.ngtms.c.dis.util;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.dis.shared.DisFullText;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcStatus;
/*     */ import com.hwacom.ngtms.c.shared.DisplayMatch;
/*     */ import com.hwacom.ngtms.c.shared.TcProtocolType;
/*     */ import java.util.Date;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.apache.commons.lang.SerializationUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DisUtility
/*     */ {
/*  24 */   private static Logger logger = LoggerFactory.getLogger(DisUtility.class);
/*     */   
/*     */   public static TcProtocolType getTcProtocolTypeByDeviceName(String deviceName) {
/*  27 */     if ((deviceName == null) || (deviceName.isEmpty())) {
/*  28 */       return null;
/*     */     }
/*     */     
/*  31 */     DeviceTcConfig config = (DeviceTcConfig)HzUtils.getMap(CommonFmHzMap.DeviceTcConfig).get(deviceName);
/*  32 */     if (config == null) {
/*  33 */       return null;
/*     */     }
/*  35 */     return config.getProtocolType();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void updateDeviceStatus(String deviceName, String display, boolean isMatch)
/*     */   {
/*  44 */     IMap<String, DeviceTcStatus> statusMap = HzUtils.getMap(CommonFmHzMap.DeviceTcStatus);
/*     */     
/*  46 */     if (!statusMap.containsKey(deviceName)) {
/*  47 */       return;
/*     */     }
/*     */     try
/*     */     {
/*  51 */       if (statusMap.tryLock(deviceName, 1L, TimeUnit.SECONDS)) {
/*     */         try {
/*  53 */           DeviceTcStatus emsDeviceStatus = (DeviceTcStatus)statusMap.get(deviceName);
/*     */           
/*  55 */           emsDeviceStatus.setIsDisplayContentMatch(isMatch ? DisplayMatch.MATCH : DisplayMatch.NOT_MATCH);
/*     */           
/*  57 */           emsDeviceStatus.setDisplay(display);
/*  58 */           Date timestamp = new Date();
/*  59 */           emsDeviceStatus.setTimestamp(timestamp);
/*  60 */           emsDeviceStatus.setUpdatedBy("DIS");
/*     */           
/*  62 */           emsDeviceStatus.setLastUpdateStatus(Integer.valueOf(0));
/*  63 */           emsDeviceStatus.setLastUpdateTime(timestamp);
/*  64 */           statusMap.put(deviceName, emsDeviceStatus);
/*     */         } catch (Exception e) {
/*  66 */           logger.error("DisUtility updateEmsDeviceStatus : ", e.getMessage());
/*     */         } finally {
/*  68 */           statusMap.unlock(deviceName);
/*     */         }
/*     */       } else {
/*  71 */         logger.warn("updateEmsDeviceStatus tryLock({}) failed", deviceName);
/*     */       }
/*     */     } catch (Exception e) {
/*  74 */       logger.error("DisUtility updateEmsDeviceStatus : ", e.getMessage());
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
/*     */   public static void updateDeviceStatus(String deviceName, String display, boolean isMatch, String tcMessage, String tcIconDescription, String tcGCodeDescription)
/*     */   {
/*  90 */     IMap<String, DeviceTcStatus> statusMap = HzUtils.getMap(CommonFmHzMap.DeviceTcStatus);
/*     */     
/*  92 */     if (!statusMap.containsKey(deviceName)) {
/*  93 */       return;
/*     */     }
/*     */     try
/*     */     {
/*  97 */       if (statusMap.tryLock(deviceName, 1L, TimeUnit.SECONDS)) {
/*     */         try {
/*  99 */           DeviceTcStatus emsDeviceStatus = (DeviceTcStatus)statusMap.get(deviceName);
/*     */           
/* 101 */           emsDeviceStatus.setIsDisplayContentMatch(isMatch ? DisplayMatch.MATCH : DisplayMatch.NOT_MATCH);
/*     */           
/* 103 */           Date timestamp = new Date();
/* 104 */           emsDeviceStatus.setDisplay(display);
/* 105 */           emsDeviceStatus.setTimestamp(timestamp);
/* 106 */           emsDeviceStatus.setUpdatedBy("DIS");
/* 107 */           emsDeviceStatus.setTcMessage(tcMessage);
/* 108 */           emsDeviceStatus.setTcIconDescription(tcIconDescription);
/* 109 */           emsDeviceStatus.setTcGCodeDescription(tcGCodeDescription);
/*     */           
/* 111 */           emsDeviceStatus.setLastUpdateStatus(Integer.valueOf(0));
/* 112 */           emsDeviceStatus.setLastUpdateTime(timestamp);
/* 113 */           statusMap.put(deviceName, emsDeviceStatus);
/*     */         } catch (Exception e) {
/* 115 */           logger.error("DisUtility updateEmsDeviceStatus : ", e.getMessage());
/*     */         } finally {
/* 117 */           statusMap.unlock(deviceName);
/*     */         }
/*     */       } else {
/* 120 */         logger.warn("updateEmsDeviceStatus tryLock({}) failed", deviceName);
/*     */       }
/*     */     } catch (Exception e) {
/* 123 */       logger.error("DisUtility updateEmsDeviceStatus : ", e.getMessage());
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
/*     */   public static void updateDeviceStatus(String deviceName, String display, boolean isMatch, String tcMessage, String tcIconDescription, String tcIconDescription2, String tcGCodeDescription)
/*     */   {
/* 143 */     IMap<String, DeviceTcStatus> statusMap = HzUtils.getMap(CommonFmHzMap.DeviceTcStatus);
/*     */     
/* 145 */     if (!statusMap.containsKey(deviceName)) {
/* 146 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 150 */       if (statusMap.tryLock(deviceName, 1L, TimeUnit.SECONDS)) {
/*     */         try {
/* 152 */           DeviceTcStatus emsDeviceStatus = (DeviceTcStatus)statusMap.get(deviceName);
/*     */           
/* 154 */           emsDeviceStatus.setIsDisplayContentMatch(isMatch ? DisplayMatch.MATCH : DisplayMatch.NOT_MATCH);
/*     */           
/* 156 */           Date timestamp = new Date();
/* 157 */           emsDeviceStatus.setDisplay(display);
/* 158 */           emsDeviceStatus.setTimestamp(timestamp);
/* 159 */           emsDeviceStatus.setUpdatedBy("DIS");
/* 160 */           emsDeviceStatus.setTcMessage(tcMessage);
/* 161 */           emsDeviceStatus.setTcIconDescription(tcIconDescription);
/* 162 */           emsDeviceStatus.setTcIconDescription2(tcIconDescription2);
/* 163 */           emsDeviceStatus.setTcGCodeDescription(tcGCodeDescription);
/*     */           
/* 165 */           emsDeviceStatus.setLastUpdateStatus(Integer.valueOf(0));
/* 166 */           emsDeviceStatus.setLastUpdateTime(timestamp);
/* 167 */           statusMap.put(deviceName, emsDeviceStatus);
/*     */         } catch (Exception e) {
/* 169 */           logger.error("DisUtility updateEmsDeviceStatus : ", e.getMessage());
/*     */         } finally {
/* 171 */           statusMap.unlock(deviceName);
/*     */         }
/*     */         
/*     */       } else {
/* 175 */         logger.warn("updateEmsDeviceStatus tryLock({}) failed", deviceName);
/*     */       }
/*     */     } catch (Exception e) {
/* 178 */       logger.error("DisUtility updateEmsDeviceStatus : ", e.getMessage());
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
/*     */   public static void updateFW2DeviceStatus(String deviceName, String display, boolean isMatch, String tcMessage, String tcIconDescription, String tcGCodeDescription, DisFullText disFullText)
/*     */   {
/* 195 */     IMap<String, DeviceTcStatus> statusMap = HzUtils.getMap(CommonFmHzMap.DeviceTcStatus);
/*     */     
/* 197 */     if (!statusMap.containsKey(deviceName)) {
/* 198 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 202 */       if (statusMap.tryLock(deviceName, 1L, TimeUnit.SECONDS)) {
/*     */         try {
/* 204 */           DeviceTcStatus emsDeviceStatus = (DeviceTcStatus)statusMap.get(deviceName);
/*     */           
/* 206 */           emsDeviceStatus.setIsDisplayContentMatch(isMatch ? DisplayMatch.MATCH : DisplayMatch.NOT_MATCH);
/*     */           
/* 208 */           Date timestamp = new Date();
/* 209 */           emsDeviceStatus.setDisplay(display);
/* 210 */           emsDeviceStatus.setTimestamp(timestamp);
/* 211 */           emsDeviceStatus.setUpdatedBy("DIS");
/* 212 */           emsDeviceStatus.setTcMessage(tcMessage);
/* 213 */           emsDeviceStatus.setTcIconDescription(tcIconDescription);
/* 214 */           emsDeviceStatus.setTcGCodeDescription(tcGCodeDescription);
/*     */           
/* 216 */           emsDeviceStatus.setLastUpdateStatus(Integer.valueOf(0));
/* 217 */           emsDeviceStatus.setLastUpdateTime(timestamp);
/* 218 */           emsDeviceStatus.setContextData(SerializationUtils.serialize(disFullText));
/* 219 */           statusMap.put(deviceName, emsDeviceStatus);
/*     */         } catch (Exception e) {
/* 221 */           logger.error("DisUtility updateEmsDeviceStatus : ", e.getMessage());
/*     */         } finally {
/* 223 */           statusMap.unlock(deviceName);
/*     */         }
/*     */       } else {
/* 226 */         logger.warn("updateEmsDeviceStatus tryLock({}) failed", deviceName);
/*     */       }
/*     */     } catch (Exception e) {
/* 229 */       logger.error("DisUtility updateEmsDeviceStatus : ", e.getMessage());
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\util\DisUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */