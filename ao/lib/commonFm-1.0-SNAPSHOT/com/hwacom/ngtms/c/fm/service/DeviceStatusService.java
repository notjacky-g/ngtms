/*     */ package com.hwacom.ngtms.c.fm.service;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcHardwareStatus;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcStatus;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceType;
/*     */ import com.hwacom.ngtms.c.shared.OpStatusHiNibble;
/*     */ import com.hwacom.ngtms.c.shared.OpStatusLowNibble;
/*     */ import com.hwacom.ngtms.c.shared.TcProtocolType;
/*     */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.CommRestartAndTestRspPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.GetHwStatusRspPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.OpStatusPm;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ @org.springframework.stereotype.Service
/*     */ public class DeviceStatusService
/*     */ {
/*  31 */   private static Logger logger = LoggerFactory.getLogger(DeviceStatusService.class);
/*     */   
/*     */   private static final String BIT = "bit";
/*     */   
/*     */   public DeviceTcHardwareStatus getByDeviceTypeAndBitNo(String deviceType, TcProtocolType protocolType, int bitNo)
/*     */   {
/*  37 */     if (deviceType == null) {
/*  38 */       logger.warn("getByDeviceTypeAndBitNo failed, deviceType is null.");
/*  39 */       return null;
/*     */     }
/*  41 */     IMap<String, DeviceTcHardwareStatus> map = HzUtils.getMap(CommonFmHzMap.DeviceTcHardwareStatus);
/*     */     
/*  43 */     EntryObject eo = new PredicateBuilder().getEntryObject();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  48 */     PredicateBuilder pb = eo.get("bitNo").equal(Integer.valueOf(bitNo)).and(eo.get("deviceType").equal(deviceType).or(eo.get("common").equal(Boolean.valueOf(true)))).and(eo.get("protocolType").equal(protocolType));
/*  49 */     DeviceTcHardwareStatus result = null;
/*  50 */     Iterator localIterator = map.values(pb).iterator(); if (localIterator.hasNext()) { DeviceTcHardwareStatus config = (DeviceTcHardwareStatus)localIterator.next();
/*  51 */       result = config;
/*     */     }
/*     */     
/*     */ 
/*  55 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<DeviceTcHardwareStatus> getHardwareStatusList(String deviceType, TcProtocolType protocolType)
/*     */   {
/*  67 */     if (deviceType == null) {
/*  68 */       logger.warn("getHardwareStatusList failed, deviceType is null.");
/*  69 */       return Collections.emptyList();
/*     */     }
/*  71 */     if (protocolType == null) {
/*  72 */       IMap<String, DeviceTcConfig> tcConfigMap = HzUtils.getMap(CommonFmHzMap.DeviceTcConfig);
/*     */       
/*  74 */       PredicateBuilder pb = new PredicateBuilder().getEntryObject().get("deviceType").equal(deviceType);
/*  75 */       for (DeviceTcConfig config : tcConfigMap.values(pb)) {
/*  76 */         if (config.getProtocolType() != null) {
/*  77 */           protocolType = config.getProtocolType();
/*  78 */           break;
/*     */         }
/*     */       }
/*     */     }
/*  82 */     if (protocolType == null) {
/*  83 */       logger.error("Can not find protocolType by deviceType='{}'", deviceType);
/*  84 */       return Collections.emptyList();
/*     */     }
/*  86 */     IMap<String, DeviceTcHardwareStatus> map = HzUtils.getMap(CommonFmHzMap.DeviceTcHardwareStatus);
/*     */     
/*  88 */     EntryObject eo = new PredicateBuilder().getEntryObject();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  93 */     PredicateBuilder pb = eo.get("deviceType").equal(deviceType).or(eo.get("common").equal(Boolean.valueOf(true))).and(eo.get("protocolType").equal(protocolType));
/*     */     
/*  95 */     return Lists.newArrayList(map.values(pb));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<DeviceTcHardwareStatus> getAbnormalHardwareStatus(DeviceTcStatus deviceStatus, TcProtocolType protocolType)
/*     */   {
/* 106 */     if ((deviceStatus == null) || (deviceStatus.getDeviceType() == null)) {
/* 107 */       logger.warn("getAbnormalHardwareStatus failed, deviceStatus or deviceType is null.");
/* 108 */       return Collections.emptyList();
/*     */     }
/* 110 */     List<DeviceTcHardwareStatus> result = new ArrayList();
/*     */     
/* 112 */     for (DeviceTcHardwareStatus status : getHardwareStatusList(deviceStatus.getDeviceType(), protocolType)) {
/* 113 */       if (deviceStatus.getBit(status.getBitNo().intValue())) {
/* 114 */         result.add(status);
/*     */       }
/*     */     }
/*     */     
/* 118 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static List<DeviceTcStatus> getDeviceStatusByHardwareStatus(DeviceTcHardwareStatus hardwareStatus, DeviceType deviceType)
/*     */   {
/* 130 */     if ((hardwareStatus == null) || (deviceType == null)) {
/* 131 */       logger.warn("getDeviceStatusByHardwareStatus failed, hardwareStatus or deviceType is null.");
/* 132 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 135 */     IMap<String, DeviceTcStatus> deviceStatusMap = HzUtils.getMap(CommonFmHzMap.DeviceTcStatus);
/* 136 */     EntryObject e = new PredicateBuilder().getEntryObject();
/*     */     
/*     */ 
/*     */ 
/* 140 */     PredicateBuilder pb = e.get("deviceType").equal(deviceType.getId()).and(e.get("bit" + hardwareStatus.getBitNo()).equal(Boolean.TRUE));
/*     */     
/* 142 */     return Lists.newArrayList(deviceStatusMap.values(pb));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<DeviceTcHardwareStatus> getAbnormalHwByGetHwStatusRspPm(GetHwStatusRspPm getHwStatusRspPm, String deviceType)
/*     */   {
/* 154 */     DeviceTcStatus deviceStatus = fromGetHwStatusRspPm(getHwStatusRspPm, deviceType);
/* 155 */     return getAbnormalHardwareStatus(deviceStatus, TcProtocolType.FW2);
/*     */   }
/*     */   
/*     */   public DeviceTcStatus fromGetHwStatusRspPm(GetHwStatusRspPm getHwStatusRspPm, String deviceType) {
/* 159 */     return fromHwStatusPm(getHwStatusRspPm.hwStatusPm, deviceType);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<DeviceTcHardwareStatus> getAbnormalHwByLcsHwStatusPm(com.hwacom.ngtms.ncc.tc.ct3.cmdparam.lcs.HwStatusPm HwStatusPm, String deviceType)
/*     */   {
/* 171 */     DeviceTcStatus deviceStatus = fromCt3LcsHwStatusPm(HwStatusPm, deviceType);
/* 172 */     return getAbnormalHardwareStatus(deviceStatus, TcProtocolType.CT3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<DeviceTcHardwareStatus> getAbnormalHwByCslsHwStatusPm(com.hwacom.ngtms.ncc.tc.ct3.cmdparam.csls.HwStatusPm HwStatusPm, String deviceType)
/*     */   {
/* 183 */     DeviceTcStatus deviceStatus = fromCt3CslsHwStatusPm(HwStatusPm, deviceType);
/* 184 */     return getAbnormalHardwareStatus(deviceStatus, TcProtocolType.CT3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<DeviceTcHardwareStatus> getCt3AbnormalHwByGetHwStatusRspPm(CommRestartAndTestRspPm commRestartAndTestRspPm, String deviceType)
/*     */   {
/* 195 */     DeviceTcStatus deviceStatus = fromCt3HwStatusReportPm(commRestartAndTestRspPm, deviceType);
/* 196 */     return getAbnormalHardwareStatus(deviceStatus, TcProtocolType.CT3);
/*     */   }
/*     */   
/*     */   public DeviceTcStatus fromCt3HwStatusReportPm(CommRestartAndTestRspPm commRestartAndTestRspPm, String deviceType)
/*     */   {
/* 201 */     DeviceTcStatus deviceStatus = new DeviceTcStatus();
/* 202 */     deviceStatus.setDeviceType(deviceType);
/*     */     
/* 204 */     byte[] hwStatus = commRestartAndTestRspPm.hwStatus;
/*     */     
/* 206 */     int bitNo = 0;
/*     */     
/* 208 */     for (int i = 0; i < hwStatus.length; i++) {
/* 209 */       byte b = hwStatus[i];
/* 210 */       for (int j = 0; j < 8; j++) {
/* 211 */         deviceStatus.setBit(bitNo, (b & 0x1) != 0);
/* 212 */         b = (byte)(b >> 1);
/* 213 */         bitNo++;
/*     */       }
/*     */     }
/*     */     
/* 217 */     return deviceStatus;
/*     */   }
/*     */   
/*     */   public DeviceTcStatus fromHwStatusPm(com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm hwStatusPm, String deviceType) {
/* 221 */     DeviceTcStatus deviceStatus = new DeviceTcStatus();
/* 222 */     deviceStatus.setDeviceType(deviceType);
/*     */     
/* 224 */     deviceStatus.setBit(0, hwStatusPm.deviceFailure != 0);
/* 225 */     deviceStatus.setBit(1, hwStatusPm.doorOpen != 0);
/* 226 */     deviceStatus.setBit(2, hwStatusPm.portableTest != 0);
/* 227 */     deviceStatus.setBit(3, hwStatusPm.panelOperate != 0);
/* 228 */     deviceStatus.setBit(4, hwStatusPm.basicParam != 0);
/* 229 */     deviceStatus.setBit(5, hwStatusPm.selfRestart != 0);
/* 230 */     deviceStatus.setBit(6, hwStatusPm.lightOff != 0);
/* 231 */     deviceStatus.setBit(7, hwStatusPm.didoFailure != 0);
/*     */     
/* 233 */     byte[] typeStatus = hwStatusPm.typeStatus;
/*     */     
/* 235 */     int bitNo = 8;
/*     */     
/* 237 */     for (int i = 0; i < typeStatus.length; i++) {
/* 238 */       byte b = typeStatus[i];
/* 239 */       for (int j = 0; j < 8; j++) {
/* 240 */         deviceStatus.setBit(bitNo, (b & 0x1) != 0);
/* 241 */         b = (byte)(b >> 1);
/* 242 */         bitNo++;
/*     */       }
/*     */     }
/*     */     
/* 246 */     return deviceStatus;
/*     */   }
/*     */   
/*     */   public DeviceTcStatus fromCt3LcsHwStatusPm(com.hwacom.ngtms.ncc.tc.ct3.cmdparam.lcs.HwStatusPm hwStatusPm, String deviceType)
/*     */   {
/* 251 */     DeviceTcStatus deviceStatus = new DeviceTcStatus();
/* 252 */     deviceStatus.setDeviceType(deviceType);
/*     */     
/* 254 */     deviceStatus.setBit(0, hwStatusPm.deviceFailure != 0);
/* 255 */     deviceStatus.setBit(1, hwStatusPm.doorOpen != 0);
/* 256 */     deviceStatus.setBit(2, hwStatusPm.portableTest != 0);
/* 257 */     deviceStatus.setBit(3, hwStatusPm.panelOperate != 0);
/* 258 */     deviceStatus.setBit(4, hwStatusPm.basicParam != 0);
/* 259 */     deviceStatus.setBit(5, hwStatusPm.selfRestart != 0);
/* 260 */     deviceStatus.setBit(6, hwStatusPm.lightOff != 0);
/* 261 */     deviceStatus.setBit(7, hwStatusPm.didoFailure != 0);
/*     */     
/* 263 */     byte[] typeStatus = hwStatusPm.typeStatus;
/*     */     
/* 265 */     int bitNo = 8;
/*     */     
/* 267 */     for (int i = 0; i < typeStatus.length; i++) {
/* 268 */       byte b = typeStatus[i];
/* 269 */       for (int j = 0; j < 8; j++) {
/* 270 */         deviceStatus.setBit(bitNo, (b & 0x1) != 0);
/* 271 */         b = (byte)(b >> 1);
/* 272 */         bitNo++;
/*     */       }
/*     */     }
/*     */     
/* 276 */     return deviceStatus;
/*     */   }
/*     */   
/*     */   public DeviceTcStatus fromCt3CslsHwStatusPm(com.hwacom.ngtms.ncc.tc.ct3.cmdparam.csls.HwStatusPm hwStatusPm, String deviceType)
/*     */   {
/* 281 */     DeviceTcStatus deviceStatus = new DeviceTcStatus();
/* 282 */     deviceStatus.setDeviceType(deviceType);
/*     */     
/* 284 */     deviceStatus.setBit(0, hwStatusPm.deviceFailure != 0);
/* 285 */     deviceStatus.setBit(1, hwStatusPm.doorOpen != 0);
/* 286 */     deviceStatus.setBit(2, hwStatusPm.portableTest != 0);
/* 287 */     deviceStatus.setBit(3, hwStatusPm.panelOperate != 0);
/* 288 */     deviceStatus.setBit(4, hwStatusPm.basicParam != 0);
/* 289 */     deviceStatus.setBit(5, hwStatusPm.selfRestart != 0);
/* 290 */     deviceStatus.setBit(6, hwStatusPm.lightOff != 0);
/* 291 */     deviceStatus.setBit(7, hwStatusPm.didoFailure != 0);
/*     */     
/* 293 */     byte[] typeStatus = hwStatusPm.typeStatus;
/*     */     
/* 295 */     int bitNo = 8;
/*     */     
/* 297 */     for (int i = 0; i < typeStatus.length; i++) {
/* 298 */       byte b = typeStatus[i];
/* 299 */       for (int j = 0; j < 8; j++) {
/* 300 */         deviceStatus.setBit(bitNo, (b & 0x1) != 0);
/* 301 */         b = (byte)(b >> 1);
/* 302 */         bitNo++;
/*     */       }
/*     */     }
/*     */     
/* 306 */     return deviceStatus;
/*     */   }
/*     */   
/*     */   public DeviceTcStatus fromCt3HwStatusPm(CommRestartAndTestRspPm pm, String deviceType)
/*     */   {
/* 311 */     DeviceTcStatus deviceStatus = new DeviceTcStatus();
/* 312 */     deviceStatus.setDeviceType(deviceType);
/*     */     
/* 314 */     byte[] hwStatus = pm.hwStatus;
/* 315 */     int bitNo = 0;
/* 316 */     for (int i = 0; i < hwStatus.length; i++) {
/* 317 */       byte b = hwStatus[i];
/* 318 */       for (int j = 0; j < 8; j++) {
/* 319 */         deviceStatus.setBit(bitNo, (b & 0x1) != 0);
/* 320 */         b = (byte)(b >> 1);
/* 321 */         bitNo++;
/*     */       }
/*     */     }
/*     */     
/* 325 */     return deviceStatus;
/*     */   }
/*     */   
/*     */   public static void setByHwStatusPm(com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm hwStatusPm, DeviceTcStatus deviceStatus) {
/* 329 */     if ((hwStatusPm == null) || (deviceStatus == null)) {
/* 330 */       return;
/*     */     }
/* 332 */     deviceStatus.setBit(0, hwStatusPm.deviceFailure != 0);
/* 333 */     deviceStatus.setBit(1, hwStatusPm.doorOpen != 0);
/* 334 */     deviceStatus.setBit(2, hwStatusPm.portableTest != 0);
/* 335 */     deviceStatus.setBit(3, hwStatusPm.panelOperate != 0);
/* 336 */     deviceStatus.setBit(4, hwStatusPm.basicParam != 0);
/* 337 */     deviceStatus.setBit(5, hwStatusPm.selfRestart != 0);
/* 338 */     deviceStatus.setBit(6, hwStatusPm.lightOff != 0);
/* 339 */     deviceStatus.setBit(7, hwStatusPm.didoFailure != 0);
/*     */     
/* 341 */     byte[] typeStatus = hwStatusPm.typeStatus;
/*     */     
/* 343 */     int bitNo = 8;
/*     */     
/* 345 */     for (int i = 0; i < typeStatus.length; i++) {
/* 346 */       byte b = typeStatus[i];
/* 347 */       for (int j = 0; j < 8; j++) {
/* 348 */         deviceStatus.setBit(bitNo, (b & 0x1) != 0);
/* 349 */         b = (byte)(b >> 1);
/* 350 */         bitNo++;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setByLcsHwStatusPm(com.hwacom.ngtms.ncc.tc.ct3.cmdparam.lcs.HwStatusPm hwStatusPm, DeviceTcStatus deviceStatus)
/*     */   {
/* 357 */     if ((hwStatusPm == null) || (deviceStatus == null)) {
/* 358 */       return;
/*     */     }
/* 360 */     deviceStatus.setBit(0, hwStatusPm.deviceFailure != 0);
/* 361 */     deviceStatus.setBit(1, hwStatusPm.doorOpen != 0);
/* 362 */     deviceStatus.setBit(2, hwStatusPm.portableTest != 0);
/* 363 */     deviceStatus.setBit(3, hwStatusPm.panelOperate != 0);
/* 364 */     deviceStatus.setBit(4, hwStatusPm.basicParam != 0);
/* 365 */     deviceStatus.setBit(5, hwStatusPm.selfRestart != 0);
/* 366 */     deviceStatus.setBit(6, hwStatusPm.lightOff != 0);
/* 367 */     deviceStatus.setBit(7, hwStatusPm.didoFailure != 0);
/*     */     
/* 369 */     byte[] typeStatus = hwStatusPm.typeStatus;
/*     */     
/* 371 */     int bitNo = 8;
/*     */     
/* 373 */     for (int i = 0; i < typeStatus.length; i++) {
/* 374 */       byte b = typeStatus[i];
/* 375 */       for (int j = 0; j < 8; j++) {
/* 376 */         deviceStatus.setBit(bitNo, (b & 0x1) != 0);
/* 377 */         b = (byte)(b >> 1);
/* 378 */         bitNo++;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static void setByCslsHwStatusPm(com.hwacom.ngtms.ncc.tc.ct3.cmdparam.csls.HwStatusPm hwStatusPm, DeviceTcStatus deviceStatus)
/*     */   {
/* 386 */     if ((hwStatusPm == null) || (deviceStatus == null)) {
/* 387 */       return;
/*     */     }
/* 389 */     deviceStatus.setBit(0, hwStatusPm.deviceFailure != 0);
/* 390 */     deviceStatus.setBit(1, hwStatusPm.doorOpen != 0);
/* 391 */     deviceStatus.setBit(2, hwStatusPm.portableTest != 0);
/* 392 */     deviceStatus.setBit(3, hwStatusPm.panelOperate != 0);
/* 393 */     deviceStatus.setBit(4, hwStatusPm.basicParam != 0);
/* 394 */     deviceStatus.setBit(5, hwStatusPm.selfRestart != 0);
/* 395 */     deviceStatus.setBit(6, hwStatusPm.lightOff != 0);
/* 396 */     deviceStatus.setBit(7, hwStatusPm.didoFailure != 0);
/*     */     
/* 398 */     byte[] typeStatus = hwStatusPm.typeStatus;
/*     */     
/* 400 */     int bitNo = 8;
/*     */     
/* 402 */     for (int i = 0; i < typeStatus.length; i++) {
/* 403 */       byte b = typeStatus[i];
/* 404 */       for (int j = 0; j < 8; j++) {
/* 405 */         deviceStatus.setBit(bitNo, (b & 0x1) != 0);
/* 406 */         b = (byte)(b >> 1);
/* 407 */         bitNo++;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setByCt3CommRestartAndTestRspPm(CommRestartAndTestRspPm pm, DeviceTcStatus deviceStatus)
/*     */   {
/* 414 */     if ((pm == null) || (deviceStatus == null)) {
/* 415 */       logger.error("CommRestartAndTestRspPm or deviceTcStatus is null, CommRestartAndTestRspPm:'{}', deviceTcStatus:'{}'", pm, deviceStatus);
/*     */       
/*     */ 
/*     */ 
/* 419 */       return;
/*     */     }
/* 421 */     byte[] hwStatus = pm.hwStatus;
/* 422 */     int bitNo = 0;
/* 423 */     for (int i = 0; i < hwStatus.length; i++) {
/* 424 */       byte b = hwStatus[i];
/* 425 */       for (int j = 0; j < 8; j++) {
/* 426 */         deviceStatus.setBit(bitNo, (b & 0x1) != 0);
/* 427 */         b = (byte)(b >> 1);
/* 428 */         bitNo++;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static OpStatusHiNibble covertToOpStatusHiNibble(OpStatusPm opStatusPm) {
/* 434 */     int opStatusHiInteger = opStatusPm.hiNibble;
/*     */     
/*     */     OpStatusHiNibble hiNibble;
/*     */     OpStatusHiNibble hiNibble;
/* 438 */     if ((opStatusHiInteger <= 6) && (opStatusHiInteger >= 0)) {
/* 439 */       hiNibble = OpStatusHiNibble.valueOf(opStatusHiInteger);
/*     */     } else {
/* 441 */       hiNibble = OpStatusHiNibble.ABNORMAL;
/*     */     }
/*     */     
/* 444 */     return hiNibble;
/*     */   }
/*     */   
/*     */   public static OpStatusLowNibble covertToOpStatusLowNibble(OpStatusPm opStatusPm) {
/* 448 */     int opStatusLowInteger = opStatusPm.lowNibble;
/*     */     
/*     */     OpStatusLowNibble lowNibble;
/*     */     OpStatusLowNibble lowNibble;
/* 452 */     if ((opStatusLowInteger <= 7) && (opStatusLowInteger >= 0)) {
/* 453 */       lowNibble = OpStatusLowNibble.valueOf(opStatusLowInteger);
/*     */     } else {
/* 455 */       lowNibble = OpStatusLowNibble.ABNORMAL;
/*     */     }
/*     */     
/* 458 */     return lowNibble;
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
/*     */   public boolean isAbnormalInSpecifiedHardwareStatuses(DeviceTcStatus deviceStatus, Set<DeviceTcHardwareStatus> specifiedStatuses)
/*     */   {
/* 472 */     for (DeviceTcHardwareStatus status : specifiedStatuses) {
/* 473 */       if (deviceStatus.getBit(status.getBitNo().intValue())) {
/* 474 */         return true;
/*     */       }
/*     */     }
/* 477 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\service\DeviceStatusService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */