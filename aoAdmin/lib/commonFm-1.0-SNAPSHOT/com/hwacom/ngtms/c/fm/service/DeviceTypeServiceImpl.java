/*     */ package com.hwacom.ngtms.c.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceType;
/*     */ import org.springframework.context.annotation.Profile;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ @Profile({"dev"})
/*     */ public class DeviceTypeServiceImpl
/*     */   implements DeviceTypeService
/*     */ {
/*     */   public DeviceType getAvi() {
/*  22 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/*  23 */     if (map == null || map.get("AVI") == null) {
/*  24 */       return null;
/*     */     }
/*  26 */     return (DeviceType)map.get("AVI");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getCctv() {
/*  31 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/*  32 */     if (map == null || map.get("CCTV") == null) {
/*  33 */       return null;
/*     */     }
/*  35 */     return (DeviceType)map.get("CCTV");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getCctvDetect() {
/*  40 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/*  41 */     if (map == null || map.get("CCTVDETECT") == null) {
/*  42 */       return null;
/*     */     }
/*  44 */     return (DeviceType)map.get("CCTVDETECT");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getCctvIid() {
/*  49 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/*  50 */     if (map == null || map.get("CCTVIID") == null) {
/*  51 */       return null;
/*     */     }
/*  53 */     return (DeviceType)map.get("CCTVIID");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getCms() {
/*  58 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/*  59 */     if (map == null || map.get("CMS") == null) {
/*  60 */       return null;
/*     */     }
/*  62 */     return (DeviceType)map.get("CMS");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getCmsRst() {
/*  67 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/*  68 */     if (map == null || map.get("CMSRST") == null) {
/*  69 */       return null;
/*     */     }
/*  71 */     return (DeviceType)map.get("CMSRST");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getCsls() {
/*  76 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/*  77 */     if (map == null || map.get("CSLS") == null) {
/*  78 */       return null;
/*     */     }
/*  80 */     return (DeviceType)map.get("CSLS");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getEt() {
/*  85 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/*  86 */     if (map == null || map.get("ET") == null) {
/*  87 */       return null;
/*     */     }
/*  89 */     return (DeviceType)map.get("ET");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getEtc() {
/*  94 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/*  95 */     if (map == null || map.get("ETC") == null) {
/*  96 */       return null;
/*     */     }
/*  98 */     return (DeviceType)map.get("ETC");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getEtag() {
/* 103 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 104 */     if (map == null || map.get("ETAG") == null) {
/* 105 */       return null;
/*     */     }
/* 107 */     return (DeviceType)map.get("ETAG");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getIid() {
/* 112 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 113 */     if (map == null || map.get("IID") == null) {
/* 114 */       return null;
/*     */     }
/* 116 */     return (DeviceType)map.get("IID");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getPd() {
/* 121 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 122 */     if (map == null || map.get("PD") == null) {
/* 123 */       return null;
/*     */     }
/* 125 */     return (DeviceType)map.get("PD");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getRd() {
/* 130 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 131 */     if (map == null || map.get("RD") == null) {
/* 132 */       return null;
/*     */     }
/* 134 */     return (DeviceType)map.get("RD");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getRgs() {
/* 139 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 140 */     if (map == null || map.get("RGS") == null) {
/* 141 */       return null;
/*     */     }
/* 143 */     return (DeviceType)map.get("RGS");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getRms() {
/* 148 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 149 */     if (map == null || map.get("RMS") == null) {
/* 150 */       return null;
/*     */     }
/* 152 */     return (DeviceType)map.get("RMS");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getScm() {
/* 157 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 158 */     if (map == null || map.get("SCM") == null) {
/* 159 */       return null;
/*     */     }
/* 161 */     return (DeviceType)map.get("SCM");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getScs() {
/* 166 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 167 */     if (map == null || map.get("SCS") == null) {
/* 168 */       return null;
/*     */     }
/* 170 */     return (DeviceType)map.get("SCS");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getSig() {
/* 175 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 176 */     if (map == null || map.get("SIG") == null) {
/* 177 */       return null;
/*     */     }
/* 179 */     return (DeviceType)map.get("SIG");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getTem() {
/* 184 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 185 */     if (map == null || map.get("TEM") == null) {
/* 186 */       return null;
/*     */     }
/* 188 */     return (DeviceType)map.get("TEM");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getTts() {
/* 193 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 194 */     if (map == null || map.get("TTS") == null) {
/* 195 */       return null;
/*     */     }
/* 197 */     return (DeviceType)map.get("TTS");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getVd() {
/* 202 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 203 */     if (map == null || map.get("VD") == null) {
/* 204 */       return null;
/*     */     }
/* 206 */     return (DeviceType)map.get("VD");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getVi() {
/* 211 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 212 */     if (map == null || map.get("VI") == null) {
/* 213 */       return null;
/*     */     }
/* 215 */     return (DeviceType)map.get("VI");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getWd() {
/* 220 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 221 */     if (map == null || map.get("WD") == null) {
/* 222 */       return null;
/*     */     }
/* 224 */     return (DeviceType)map.get("WD");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getWis() {
/* 229 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 230 */     if (map == null || map.get("WIS") == null) {
/* 231 */       return null;
/*     */     }
/* 233 */     return (DeviceType)map.get("WIS");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getDeviceType(String type) {
/* 238 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 239 */     if (map == null || map.get(type) == null) {
/* 240 */       return null;
/*     */     }
/* 242 */     return (DeviceType)map.get(type);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\service\DeviceTypeServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */