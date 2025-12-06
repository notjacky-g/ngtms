/*     */ package com.hwacom.ngtms.ao.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.ao.fm.marshaller.EqConfigDataXmlMarshaller;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Cctv;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.CctvEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Cms;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.CmsEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Csls;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.CslsEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.EqConfigData;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Et;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.EtEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.EtagDataRow;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.EtagEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Lcs;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.LcsEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.OnedayEqConfigData;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Qld;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.QldEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Rgs;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.RgsEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Rms;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.RmsEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Scs;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.ScsEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Vd;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.VdEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Vi;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.ViEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Wd;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.WdEqConfig;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.Wis;
/*     */ import com.hwacom.ngtms.ao.shared.eqconfig.WisEqConfig;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.shared.Direction;
/*     */ import com.hwacom.ngtms.ccs.fm.hz.CcsHzMap;
/*     */ import com.hwacom.ngtms.ccs.shared.CctvConfig;
/*     */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*     */ import com.hwacom.ngtms.common.fm.model.DeviceConfig;
/*     */ import com.hwacom.ngtms.hcce.util.HcceUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service
/*     */ public class EqConfigDataService
/*     */ {
/*  58 */   private static final Logger logger = LoggerFactory.getLogger(EqConfigDataService.class);
/*     */ 
/*     */   
/*     */   public void process() {
/*  62 */     logger.debug("GetXmlDataFromUrl");
/*  63 */     EqConfigData data = null;
/*  64 */     String urlStr = "";
/*     */     try {
/*  66 */       IMap<String, DeviceConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.DeviceConfig);
/*  67 */       IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  68 */       IMap<String, CctvConfig> cctvConfigMap = HzUtils.getMap((HzDistObjEnum)CcsHzMap.CctvConfig);
/*  69 */       urlStr = HcceUtils.getDynaConfig("AoCommonFm", "eqConfigDataUrl").getValue();
/*  70 */       URLConnection conn = null;
/*  71 */       URL url = new URL(urlStr);
/*  72 */       conn = url.openConnection();
/*  73 */       conn.setConnectTimeout(10000);
/*     */       
/*  75 */       try (InputStream is = conn.getInputStream()) {
/*  76 */         EqConfigDataXmlMarshaller marshaller = new EqConfigDataXmlMarshaller();
/*  77 */         logger.debug("inputStream:'{}'", is);
/*  78 */         data = (EqConfigData)marshaller.getXstream().fromXML(is);
/*  79 */         logger.debug("EqConfigData:'{}'", data);
/*     */       } 
/*     */       
/*  82 */       if (data != null) {
/*  83 */         Map<String, DeviceTcConfig> deviceSaveMap = new HashMap<>();
/*  84 */         Map<String, CctvConfig> cctvMap = new HashMap<>();
/*  85 */         OnedayEqConfigData oneEqDayConfig = data.getOnedayEqConfigData();
/*  86 */         CslsEqConfig cslsConfig = oneEqDayConfig.getCslsEqConfig();
/*  87 */         CmsEqConfig cmsConfig = oneEqDayConfig.getCmsEqConfig();
/*  88 */         LcsEqConfig lcsConfig = oneEqDayConfig.getLcsEqConfig();
/*  89 */         RgsEqConfig rgsConfig = oneEqDayConfig.getRgsEqConfig();
/*  90 */         RmsEqConfig rmsConfig = oneEqDayConfig.getRmsEqConfig();
/*  91 */         VdEqConfig vdConfig = oneEqDayConfig.getVdEqConfig();
/*  92 */         ViEqConfig viConfig = oneEqDayConfig.getViEqConfig();
/*  93 */         WdEqConfig wdConfig = oneEqDayConfig.getWdEqConfig();
/*  94 */         WisEqConfig wisConfig = oneEqDayConfig.getWisEqConfig();
/*  95 */         CctvEqConfig cctvConfig = oneEqDayConfig.getCctvEqConfig();
/*  96 */         ScsEqConfig scsConfig = oneEqDayConfig.getScsEqConfig();
/*  97 */         EtagEqConfig etagConfig = oneEqDayConfig.getEtagEqConfig();
/*  98 */         EtEqConfig etEqConfig = oneEqDayConfig.getEtEqConfig();
/*  99 */         QldEqConfig qldEqConfig = oneEqDayConfig.getQldEqConfig();
/*     */         
/* 101 */         if (cslsConfig != null && cslsConfig.getCsls() != null) {
/* 102 */           for (Csls csls : cslsConfig.getCsls()) {
/*     */             try {
/* 104 */               DeviceTcConfig deviceConfig = new DeviceTcConfig();
/* 105 */               deviceConfig.setDeviceName(csls.getUniqueId());
/* 106 */               deviceConfig.setDisplayName(csls.getEqId());
/* 107 */               deviceConfig.setLineId(getLineIdFromId(csls.getFreewayId(), csls.getExpresswayId()));
/* 108 */               deviceConfig.setDeviceType("CSLS");
/* 109 */               deviceConfig.setDirection(getDirectionFromId(csls.getDirectionId()));
/* 110 */               deviceConfig.setLongitude(getDoubleFromStr(csls.getLongitude()));
/* 111 */               deviceConfig.setLatitude(getDoubleFromStr(csls.getLatitude()));
/* 112 */               deviceConfig.setMilepost(csls.getMilepost());
/* 113 */               deviceConfig.setProject("ao");
/* 114 */               deviceSaveMap.put(csls.getUniqueId(), deviceConfig);
/* 115 */             } catch (Exception e) {
/* 116 */               logger.error("save csls deviceTcConfig failed. deviceName:'{}'", csls
/* 117 */                   .getUniqueId(), e);
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 122 */         if (cmsConfig != null && cmsConfig.getCms() != null) {
/* 123 */           for (Cms cms : cmsConfig.getCms()) {
/*     */             try {
/* 125 */               DeviceTcConfig deviceConfig = new DeviceTcConfig();
/* 126 */               deviceConfig.setDeviceName(cms.getUniqueId());
/* 127 */               deviceConfig.setDisplayName(cms.getEqId());
/* 128 */               deviceConfig.setLineId(getLineIdFromId(cms.getFreewayId(), cms.getExpresswayId()));
/* 129 */               deviceConfig.setDeviceType("CMS");
/* 130 */               deviceConfig.setDirection(getDirectionFromId(cms.getDirectionId()));
/* 131 */               deviceConfig.setLongitude(getDoubleFromStr(cms.getLongitude()));
/* 132 */               deviceConfig.setLatitude(getDoubleFromStr(cms.getLatitude()));
/* 133 */               deviceConfig.setMilepost(cms.getMilepost());
/* 134 */               deviceConfig.setProject("ao");
/* 135 */               deviceSaveMap.put(cms.getUniqueId(), deviceConfig);
/* 136 */             } catch (Exception e) {
/* 137 */               logger.error("save cms deviceTcConfig failed. deviceName:'{}'", cms.getUniqueId(), e);
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 142 */         if (lcsConfig != null && lcsConfig.getLcs() != null) {
/* 143 */           for (Lcs lcs : lcsConfig.getLcs()) {
/*     */             try {
/* 145 */               DeviceTcConfig deviceConfig = new DeviceTcConfig();
/* 146 */               deviceConfig.setDeviceName(lcs.getUniqueId());
/* 147 */               deviceConfig.setDisplayName(lcs.getEqId());
/* 148 */               deviceConfig.setLineId(getLineIdFromId(lcs.getFreewayId(), lcs.getExpresswayId()));
/* 149 */               deviceConfig.setDeviceType("LCS");
/* 150 */               deviceConfig.setDirection(getDirectionFromId(lcs.getDirectionId()));
/* 151 */               deviceConfig.setLongitude(getDoubleFromStr(lcs.getLongitude()));
/* 152 */               deviceConfig.setLatitude(getDoubleFromStr(lcs.getLatitude()));
/* 153 */               deviceConfig.setMilepost(lcs.getMilepost());
/* 154 */               deviceConfig.setProject("ao");
/* 155 */               deviceSaveMap.put(lcs.getUniqueId(), deviceConfig);
/* 156 */             } catch (Exception e) {
/* 157 */               logger.error("save lcs deviceTcConfig failed. deviceName:'{}'", lcs.getUniqueId(), e);
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 162 */         if (rgsConfig != null && rgsConfig.getRgs() != null) {
/* 163 */           for (Rgs rgs : rgsConfig.getRgs()) {
/*     */             try {
/* 165 */               DeviceTcConfig deviceConfig = new DeviceTcConfig();
/* 166 */               deviceConfig.setDeviceName(rgs.getUniqueId());
/* 167 */               deviceConfig.setDisplayName(rgs.getEqId());
/* 168 */               deviceConfig.setLineId(getLineIdFromId(rgs.getFreewayId(), rgs.getExpresswayId()));
/* 169 */               deviceConfig.setDeviceType("RGS");
/* 170 */               deviceConfig.setDirection(getDirectionFromId(rgs.getDirectionId()));
/* 171 */               deviceConfig.setLongitude(getDoubleFromStr(rgs.getLongitude()));
/* 172 */               deviceConfig.setLatitude(getDoubleFromStr(rgs.getLatitude()));
/* 173 */               deviceConfig.setMilepost(rgs.getMilepost());
/* 174 */               deviceConfig.setProject("ao");
/* 175 */               deviceSaveMap.put(rgs.getUniqueId(), deviceConfig);
/* 176 */             } catch (Exception e) {
/* 177 */               logger.error("save rgs deviceTcConfig failed. deviceName:'{}'", rgs.getUniqueId(), e);
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 182 */         if (rmsConfig != null && rmsConfig.getRms() != null) {
/* 183 */           for (Rms rms : rmsConfig.getRms()) {
/*     */             try {
/* 185 */               DeviceTcConfig deviceConfig = new DeviceTcConfig();
/* 186 */               deviceConfig.setDeviceName(rms.getUniqueId());
/* 187 */               deviceConfig.setDisplayName(rms.getEqId());
/* 188 */               deviceConfig.setLineId(getLineIdFromId(rms.getFreewayId(), rms.getExpresswayId()));
/* 189 */               deviceConfig.setDeviceType("RMS");
/* 190 */               deviceConfig.setDirection(getDirectionFromId(rms.getDirectionId()));
/* 191 */               deviceConfig.setLongitude(getDoubleFromStr(rms.getLongitude()));
/* 192 */               deviceConfig.setLatitude(getDoubleFromStr(rms.getLatitude()));
/* 193 */               deviceConfig.setMilepost(rms.getMilepost());
/* 194 */               deviceConfig.setProject("ao");
/* 195 */               deviceSaveMap.put(rms.getUniqueId(), deviceConfig);
/* 196 */             } catch (Exception e) {
/* 197 */               logger.error("save rms deviceTcConfig failed. deviceName:'{}'", rms.getUniqueId(), e);
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 202 */         if (vdConfig != null && vdConfig.getVd() != null) {
/* 203 */           for (Vd vd : vdConfig.getVd()) {
/*     */             try {
/* 205 */               DeviceTcConfig deviceConfig = new DeviceTcConfig();
/* 206 */               deviceConfig.setDeviceName(vd.getUniqueId());
/* 207 */               deviceConfig.setDisplayName(vd.getEqId());
/* 208 */               deviceConfig.setLineId(getLineIdFromId(vd.getFreewayId(), vd.getExpresswayId()));
/* 209 */               deviceConfig.setDeviceType("VD");
/* 210 */               deviceConfig.setDirection(getDirectionFromId(vd.getDirectionId()));
/* 211 */               deviceConfig.setLongitude(getDoubleFromStr(vd.getLongitude()));
/* 212 */               deviceConfig.setLatitude(getDoubleFromStr(vd.getLatitude()));
/* 213 */               deviceConfig.setMilepost(vd.getMilepost());
/* 214 */               deviceConfig.setProject("ao");
/* 215 */               deviceSaveMap.put(vd.getUniqueId(), deviceConfig);
/* 216 */             } catch (Exception e) {
/* 217 */               logger.error("save vd deviceTcConfig failed. deviceName:'{}'", vd.getUniqueId(), e);
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 222 */         if (viConfig != null && viConfig.getVi() != null) {
/* 223 */           for (Vi vi : viConfig.getVi()) {
/*     */             try {
/* 225 */               DeviceTcConfig deviceConfig = new DeviceTcConfig();
/* 226 */               deviceConfig.setDeviceName(vi.getUniqueId());
/* 227 */               deviceConfig.setDisplayName(vi.getEqId());
/* 228 */               deviceConfig.setLineId(getLineIdFromId(vi.getFreewayId(), vi.getExpresswayId()));
/* 229 */               deviceConfig.setDeviceType("VI");
/* 230 */               deviceConfig.setDirection(getDirectionFromId(vi.getDirectionId()));
/* 231 */               deviceConfig.setLongitude(getDoubleFromStr(vi.getLongitude()));
/* 232 */               deviceConfig.setLatitude(getDoubleFromStr(vi.getLatitude()));
/* 233 */               deviceConfig.setMilepost(vi.getMilepost());
/* 234 */               deviceConfig.setProject("ao");
/* 235 */               deviceSaveMap.put(vi.getUniqueId(), deviceConfig);
/* 236 */             } catch (Exception e) {
/* 237 */               logger.error("save vi deviceTcConfig failed. deviceName:'{}'", vi.getUniqueId(), e);
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 242 */         if (wdConfig != null && wdConfig.getWd() != null) {
/* 243 */           for (Wd wd : wdConfig.getWd()) {
/*     */             try {
/* 245 */               DeviceTcConfig deviceConfig = new DeviceTcConfig();
/* 246 */               deviceConfig.setDeviceName(wd.getUniqueId());
/* 247 */               deviceConfig.setDisplayName(wd.getEqId());
/* 248 */               deviceConfig.setLineId(getLineIdFromId(wd.getFreewayId(), wd.getExpresswayId()));
/* 249 */               deviceConfig.setDeviceType("WD");
/* 250 */               deviceConfig.setDirection(getDirectionFromId(wd.getDirectionId()));
/* 251 */               deviceConfig.setLongitude(getDoubleFromStr(wd.getLongitude()));
/* 252 */               deviceConfig.setLatitude(getDoubleFromStr(wd.getLatitude()));
/* 253 */               deviceConfig.setMilepost(wd.getMilepost());
/* 254 */               deviceConfig.setProject("ao");
/* 255 */               deviceSaveMap.put(wd.getUniqueId(), deviceConfig);
/* 256 */             } catch (Exception e) {
/* 257 */               logger.error("save wd deviceTcConfig failed. deviceName:'{}'", wd.getUniqueId(), e);
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 262 */         if (wisConfig != null && wisConfig.getWis() != null) {
/* 263 */           for (Wis wis : wisConfig.getWis()) {
/*     */             try {
/* 265 */               DeviceTcConfig deviceConfig = new DeviceTcConfig();
/* 266 */               deviceConfig.setDeviceName(wis.getUniqueId());
/* 267 */               deviceConfig.setDisplayName(wis.getEqId());
/* 268 */               deviceConfig.setLineId(getLineIdFromId(wis.getFreewayId(), wis.getExpresswayId()));
/* 269 */               deviceConfig.setDeviceType("WIS");
/* 270 */               deviceConfig.setDirection(getDirectionFromId(wis.getDirectionId()));
/* 271 */               deviceConfig.setLongitude(getDoubleFromStr(wis.getLongitude()));
/* 272 */               deviceConfig.setLatitude(getDoubleFromStr(wis.getLatitude()));
/* 273 */               deviceConfig.setMilepost(wis.getMilepost());
/* 274 */               deviceConfig.setProject("ao");
/* 275 */               deviceSaveMap.put(wis.getUniqueId(), deviceConfig);
/* 276 */             } catch (Exception e) {
/* 277 */               logger.error("save wis deviceTcConfig failed. deviceName:'{}'", wis.getUniqueId(), e);
/*     */             } 
/*     */           } 
/*     */         }
/* 281 */         if (cctvConfig != null && cctvConfig.getCctv() != null) {
/* 282 */           for (Cctv cctv : cctvConfig.getCctv()) {
/*     */             try {
/* 284 */               DeviceTcConfig deviceConfig = new DeviceTcConfig();
/* 285 */               deviceConfig.setDeviceName(cctv.getUniqueId());
/* 286 */               deviceConfig.setDisplayName(cctv.getEqId());
/* 287 */               deviceConfig.setLineId(getLineIdFromId(cctv.getFreewayId(), cctv.getExpresswayId()));
/* 288 */               deviceConfig.setDeviceType("CCTV");
/* 289 */               deviceConfig.setDirection(getDirectionFromId(cctv.getDirectionId()));
/* 290 */               deviceConfig.setLongitude(getDoubleFromStr(cctv.getLongitude()));
/* 291 */               deviceConfig.setLatitude(getDoubleFromStr(cctv.getLatitude()));
/* 292 */               deviceConfig.setMilepost(cctv.getMilepost());
/* 293 */               deviceConfig.setProject("ao");
/* 294 */               deviceSaveMap.put(cctv.getUniqueId(), deviceConfig);
/* 295 */               if (cctv.getEqId().indexOf("機房") != -1 || cctv
/* 296 */                 .getEqId().indexOf("4F") != -1 || cctv
/* 297 */                 .getEqId().indexOf("中區交控中心") != -1) {
/* 298 */                 CctvConfig cctvUrlConfig = new CctvConfig();
/* 299 */                 cctvUrlConfig.setDeviceName(cctv.getUniqueId() + "-" + cctv.getEqId());
/* 300 */                 cctvUrlConfig.setExternalUrl(cctv.getUrlIntra().getLinkurl());
/* 301 */                 cctvUrlConfig.setHighUrl(cctv.getUrlIntra().getLinkurl());
/* 302 */                 cctvUrlConfig.setLowUrl(cctv.getUrlIntra().getLinkurl());
/* 303 */                 cctvMap.put(cctv.getUniqueId(), cctvUrlConfig);
/*     */               } 
/* 305 */             } catch (Exception e) {
/* 306 */               logger.error("save cctv deviceTcConfig failed. deviceName:'{}'", cctv
/* 307 */                   .getUniqueId(), e);
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 312 */         if (scsConfig != null && scsConfig.getScs() != null) {
/* 313 */           for (Scs scs : scsConfig.getScs()) {
/*     */             try {
/* 315 */               DeviceTcConfig deviceConfig = new DeviceTcConfig();
/* 316 */               deviceConfig.setDeviceName(scs.getEqId());
/* 317 */               deviceConfig.setDisplayName(scs.getEqId());
/* 318 */               deviceConfig.setLineId(getLineIdFromId(scs.getFreewayId(), scs.getExpresswayId()));
/* 319 */               deviceConfig.setDeviceType("SCS");
/* 320 */               deviceConfig.setDirection(getDirectionFromId(scs.getDirectionId()));
/* 321 */               deviceConfig.setLongitude(getDoubleFromStr(scs.getLongitude()));
/* 322 */               deviceConfig.setLatitude(getDoubleFromStr(scs.getLatitude()));
/* 323 */               deviceConfig.setMilepost(scs.getMilepost());
/* 324 */               deviceConfig.setProject("ao");
/* 325 */               deviceSaveMap.put(scs.getEqId(), deviceConfig);
/* 326 */             } catch (Exception e) {
/* 327 */               logger.error("save scs deviceTcConfig failed. deviceName:'{}'", scs.getUniqueId(), e);
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 332 */         if (etagConfig != null && etagConfig.getEtagDataList() != null) {
/* 333 */           for (EtagDataRow etag : etagConfig.getEtagDataList()) {
/*     */             try {
/* 335 */               DeviceTcConfig deviceConfig = new DeviceTcConfig();
/* 336 */               deviceConfig.setDeviceName(etag.getEqId());
/* 337 */               deviceConfig.setDisplayName(etag.getEqId());
/* 338 */               deviceConfig.setLineId(getLineIdFromId(etag.getFreewayId(), etag.getExpresswayId()));
/* 339 */               deviceConfig.setDeviceType("ETAG");
/* 340 */               deviceConfig.setDirection(getDirectionFromId(Integer.valueOf(etag.getDirectionId())));
/* 341 */               deviceConfig.setLongitude(getDoubleFromStr(etag.getLongitude()));
/* 342 */               deviceConfig.setLatitude(getDoubleFromStr(etag.getLatitude()));
/* 343 */               deviceConfig.setMilepost(Integer.valueOf(etag.getMilepost()));
/* 344 */               deviceConfig.setProject("ao");
/* 345 */               deviceSaveMap.put(etag.getEqId(), deviceConfig);
/* 346 */             } catch (Exception e) {
/* 347 */               logger.error("save etag deviceTcConfig failed. deviceName:'{}'", etag.getEqId(), e);
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 352 */         if (etEqConfig != null && etEqConfig.getEt() != null) {
/* 353 */           for (Et et : etEqConfig.getEt()) {
/*     */             try {
/* 355 */               DeviceTcConfig deviceConfig = new DeviceTcConfig();
/* 356 */               deviceConfig.setDeviceName(et.getEqId());
/* 357 */               deviceConfig.setDisplayName(et.getEqId());
/* 358 */               deviceConfig.setLineId(getLineIdFromId(et.getFreewayId(), et.getExpresswayId()));
/* 359 */               deviceConfig.setDeviceType("ET");
/* 360 */               deviceConfig.setDirection(getDirectionFromId(Integer.valueOf(et.getDirectionId().intValue())));
/* 361 */               deviceConfig.setLongitude(getDoubleFromStr(et.getLongitude()));
/* 362 */               deviceConfig.setLatitude(getDoubleFromStr(et.getLatitude()));
/* 363 */               deviceConfig.setMilepost(Integer.valueOf(et.getMilepost().intValue()));
/* 364 */               deviceConfig.setProject("ao");
/* 365 */               deviceSaveMap.put(et.getEqId(), deviceConfig);
/* 366 */             } catch (Exception e) {
/* 367 */               logger.error("save et deviceTcConfig failed. deviceName:'{}'", et.getEqId(), e);
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 372 */         if (qldEqConfig != null && qldEqConfig.getQld() != null) {
/* 373 */           for (Qld qld : qldEqConfig.getQld()) {
/*     */             try {
/* 375 */               DeviceTcConfig deviceConfig = new DeviceTcConfig();
/* 376 */               deviceConfig.setDeviceName(qld.getEqId());
/* 377 */               deviceConfig.setDisplayName(qld.getEqId());
/* 378 */               deviceConfig.setLineId(getLineIdFromId(qld.getFreewayId(), qld.getExpresswayId()));
/* 379 */               deviceConfig.setDeviceType("QLD");
/* 380 */               deviceConfig.setDirection(getDirectionFromId(Integer.valueOf(qld.getDirectionId().intValue())));
/* 381 */               deviceConfig.setLongitude(getDoubleFromStr(qld.getLongitude()));
/* 382 */               deviceConfig.setLatitude(getDoubleFromStr(qld.getLatitude()));
/* 383 */               deviceConfig.setMilepost(Integer.valueOf(qld.getMilepost().intValue()));
/* 384 */               deviceConfig.setProject("ao");
/* 385 */               deviceSaveMap.put(qld.getEqId(), deviceConfig);
/* 386 */             } catch (Exception e) {
/* 387 */               logger.error("save qld deviceTcConfig failed. deviceName:'{}'", qld.getEqId(), e);
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 392 */         deviceMap.putAll(deviceSaveMap);
/* 393 */         deviceConfigMap.putAll(deviceSaveMap);
/* 394 */         cctvConfigMap.putAll(cctvMap);
/*     */       }
/*     */     
/* 397 */     } catch (IOException e) {
/* 398 */       logger.error("GetXmlData failed, can not connect to URL=[{}]", urlStr);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Direction getDirectionFromId(Integer directionId) {
/* 403 */     switch (directionId.intValue()) {
/*     */       case 1:
/* 405 */         return Direction.E;
/*     */       case 2:
/* 407 */         return Direction.W;
/*     */       case 3:
/* 409 */         return Direction.S;
/*     */       case 4:
/* 411 */         return Direction.N;
/*     */     } 
/* 413 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getLineIdFromId(String freewayId, String expresswayId) {
/* 418 */     if (freewayId.equals("0")) {
/* 419 */       switch (expresswayId) {
/*     */         case "72":
/* 421 */           return "T72";
/*     */         case "74":
/* 423 */           return "T74";
/*     */         case "74A":
/* 425 */           return "T74甲";
/*     */         case "76":
/* 427 */           return "T76";
/*     */         case "78":
/* 429 */           return "T78";
/*     */       } 
/* 431 */       return null;
/*     */     } 
/*     */     
/* 434 */     switch (freewayId) {
/*     */       case "1":
/* 436 */         return "N1";
/*     */       case "3":
/* 438 */         return "N3";
/*     */       case "4":
/* 440 */         return "N4";
/*     */       case "6":
/* 442 */         return "N6";
/*     */     } 
/* 444 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Double getDoubleFromStr(String str) {
/* 450 */     if (!str.isEmpty()) {
/* 451 */       return Double.valueOf(Double.parseDouble(str));
/*     */     }
/* 453 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\EqConfigDataService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */