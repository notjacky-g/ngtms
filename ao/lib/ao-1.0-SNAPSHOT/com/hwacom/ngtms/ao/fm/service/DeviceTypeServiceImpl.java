/*     */ package com.hwacom.ngtms.ao.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceType;
/*     */ import com.hwacom.ngtms.c.fm.service.DeviceTypeService;
/*     */ import org.springframework.context.annotation.Profile;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ @Profile({"ao"})
/*     */ public class DeviceTypeServiceImpl
/*     */   implements DeviceTypeService
/*     */ {
/*     */   public DeviceType getAvi() {
/*  23 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/*  24 */     if (map == null || map.get("AVI") == null) {
/*  25 */       return null;
/*     */     }
/*  27 */     return (DeviceType)map.get("AVI");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getCctv() {
/*  32 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/*  33 */     if (map == null || map.get("CCTV") == null) {
/*  34 */       return null;
/*     */     }
/*  36 */     return (DeviceType)map.get("CCTV");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getCctvDetect() {
/*  41 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/*  42 */     if (map == null || map.get("CCTVDETECT") == null) {
/*  43 */       return null;
/*     */     }
/*  45 */     return (DeviceType)map.get("CCTVDETECT");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getCctvIid() {
/*  50 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/*  51 */     if (map == null || map.get("CCTVIID") == null) {
/*  52 */       return null;
/*     */     }
/*  54 */     return (DeviceType)map.get("CCTVIID");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getCms() {
/*  59 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/*  60 */     if (map == null || map.get("CMS") == null) {
/*  61 */       return null;
/*     */     }
/*  63 */     return (DeviceType)map.get("CMS");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getCmsRst() {
/*  68 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/*  69 */     if (map == null || map.get("CMSRST") == null) {
/*  70 */       return null;
/*     */     }
/*  72 */     return (DeviceType)map.get("CMSRST");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getCsls() {
/*  77 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/*  78 */     if (map == null || map.get("CSLS") == null) {
/*  79 */       return null;
/*     */     }
/*  81 */     return (DeviceType)map.get("CSLS");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getEt() {
/*  86 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/*  87 */     if (map == null || map.get("ET") == null) {
/*  88 */       return null;
/*     */     }
/*  90 */     return (DeviceType)map.get("ET");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getEtc() {
/*  95 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/*  96 */     if (map == null || map.get("ETC") == null) {
/*  97 */       return null;
/*     */     }
/*  99 */     return (DeviceType)map.get("ETC");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getEtag() {
/* 104 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 105 */     if (map == null || map.get("ETAG") == null) {
/* 106 */       return null;
/*     */     }
/* 108 */     return (DeviceType)map.get("ETAG");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getIid() {
/* 113 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 114 */     if (map == null || map.get("IID") == null) {
/* 115 */       return null;
/*     */     }
/* 117 */     return (DeviceType)map.get("IID");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getPd() {
/* 122 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 123 */     if (map == null || map.get("PD") == null) {
/* 124 */       return null;
/*     */     }
/* 126 */     return (DeviceType)map.get("PD");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getRd() {
/* 131 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 132 */     if (map == null || map.get("RD") == null) {
/* 133 */       return null;
/*     */     }
/* 135 */     return (DeviceType)map.get("RD");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getRgs() {
/* 140 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 141 */     if (map == null || map.get("RGS") == null) {
/* 142 */       return null;
/*     */     }
/* 144 */     return (DeviceType)map.get("RGS");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getRms() {
/* 149 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 150 */     if (map == null || map.get("RMS") == null) {
/* 151 */       return null;
/*     */     }
/* 153 */     return (DeviceType)map.get("RMS");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getScm() {
/* 158 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 159 */     if (map == null || map.get("SCM") == null) {
/* 160 */       return null;
/*     */     }
/* 162 */     return (DeviceType)map.get("SCM");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getScs() {
/* 167 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 168 */     if (map == null || map.get("SCS") == null) {
/* 169 */       return null;
/*     */     }
/* 171 */     return (DeviceType)map.get("SCS");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getSig() {
/* 176 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 177 */     if (map == null || map.get("SIG") == null) {
/* 178 */       return null;
/*     */     }
/* 180 */     return (DeviceType)map.get("SIG");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getTem() {
/* 185 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 186 */     if (map == null || map.get("TEM") == null) {
/* 187 */       return null;
/*     */     }
/* 189 */     return (DeviceType)map.get("TEM");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getTts() {
/* 194 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 195 */     if (map == null || map.get("TTS") == null) {
/* 196 */       return null;
/*     */     }
/* 198 */     return (DeviceType)map.get("TTS");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getVd() {
/* 203 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 204 */     if (map == null || map.get("VD") == null) {
/* 205 */       return null;
/*     */     }
/* 207 */     return (DeviceType)map.get("VD");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getVi() {
/* 212 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 213 */     if (map == null || map.get("VI") == null) {
/* 214 */       return null;
/*     */     }
/* 216 */     return (DeviceType)map.get("VI");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getWd() {
/* 221 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 222 */     if (map == null || map.get("WD") == null) {
/* 223 */       return null;
/*     */     }
/* 225 */     return (DeviceType)map.get("WD");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getWis() {
/* 230 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 231 */     if (map == null || map.get("WIS") == null) {
/* 232 */       return null;
/*     */     }
/* 234 */     return (DeviceType)map.get("WIS");
/*     */   }
/*     */ 
/*     */   
/*     */   public DeviceType getDeviceType(String type) {
/* 239 */     IMap<String, DeviceType> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 240 */     if (map == null || map.get(type) == null) {
/* 241 */       return null;
/*     */     }
/* 243 */     return (DeviceType)map.get(type);
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\DeviceTypeServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */