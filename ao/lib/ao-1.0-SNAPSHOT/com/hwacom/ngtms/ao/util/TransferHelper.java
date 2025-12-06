/*     */ package com.hwacom.ngtms.ao.util;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.alarm.fm.model.AlarmLog;
/*     */ import com.hwacom.ngtms.ao.shared.AlarmType;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomAnalogTreeDTO;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.AlarmSubTypeConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceLocationMappingConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadLine;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadSection;
/*     */ import com.hwacom.ngtms.c.shared.AlarmLogEventContext;
/*     */ import com.hwacom.ngtms.c.shared.AlarmSource;
/*     */ import com.hwacom.ngtms.c.shared.dto.AlarmLogDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
/*     */ import java.text.SimpleDateFormat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransferHelper
/*     */ {
/*  30 */   public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
/*     */   
/*     */   private static boolean getStatusBoolean(Integer status) {
/*  33 */     boolean statusBoolean = false;
/*  34 */     if (status.intValue() == 1) {
/*  35 */       statusBoolean = false;
/*  36 */     } else if (status.intValue() == 0) {
/*  37 */       statusBoolean = true;
/*     */     } 
/*  39 */     return statusBoolean;
/*     */   }
/*     */   
/*     */   private static String getKMExpress(Integer mileage) {
/*  43 */     if (mileage == null) {
/*  44 */       return null;
/*     */     }
/*  46 */     Integer km1 = Integer.valueOf(mileage.intValue() / 1000);
/*  47 */     Integer km2 = Integer.valueOf(mileage.intValue() % 1000);
/*  48 */     String km = km1.toString() + "k+" + km2.toString();
/*  49 */     return km;
/*     */   }
/*     */   
/*     */   public static AlarmLogDTO getAlarmLogDTO(AlarmLog alarmLog) {
/*  53 */     if (alarmLog == null) {
/*  54 */       return null;
/*     */     }
/*  56 */     Gson gson = new Gson();
/*  57 */     IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  58 */     IMap<String, RoadLine> roadMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadLine);
/*  59 */     IMap<String, RoadSection> sectionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadSection);
/*     */     
/*  61 */     IMap<Integer, AlarmSubTypeConfig> alarmSubTypeConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.AlarmSubTypeConfig);
/*  62 */     AlarmLogDTO dto = new AlarmLogDTO();
/*  63 */     DeviceTcConfig device = null;
/*  64 */     if (alarmLog.getDeviceName() != null) {
/*  65 */       device = (DeviceTcConfig)deviceMap.get(alarmLog.getDeviceName());
/*     */     }
/*  67 */     RoadLine roadLine = null;
/*  68 */     RoadSection roadSection = null;
/*  69 */     dto.setId(alarmLog.getId());
/*  70 */     Integer alarmSubTypeId = Integer.valueOf(alarmLog.getAlarmSubType());
/*  71 */     if (alarmSubTypeConfigMap.get(alarmSubTypeId) != null) {
/*  72 */       dto.setAlarmSubType(((AlarmSubTypeConfig)alarmSubTypeConfigMap.get(alarmSubTypeId)).getShortName());
/*     */     } else {
/*  74 */       dto.setAlarmSubType("無類別");
/*     */     } 
/*  76 */     dto.setAlarmSessionId(alarmLog.getAlarmSessionId());
/*  77 */     dto.setTimestamp(alarmLog.getTimestamp());
/*  78 */     dto.setDeviceName(alarmLog.getDeviceName());
/*  79 */     dto.setAlarmContextData(alarmLog.getContextData());
/*  80 */     if (device != null) {
/*  81 */       dto.setDisplayName(device.getDisplayName());
/*     */     }
/*  83 */     AlarmLogEventContext contextData = null;
/*  84 */     if (alarmLog.getContextData() != null) {
/*  85 */       contextData = (AlarmLogEventContext)gson.fromJson(alarmLog.getContextData(), AlarmLogEventContext.class);
/*     */     }
/*  87 */     if (contextData != null) {
/*  88 */       roadLine = (contextData.getLineId() != null) ? (RoadLine)roadMap.get(contextData.getLineId()) : null;
/*     */       
/*  90 */       roadSection = (contextData.getSectionId() != null) ? (RoadSection)sectionMap.get(contextData.getSectionId()) : null;
/*  91 */       dto.setSectionId(contextData.getSectionId());
/*  92 */       dto.setLineId((contextData.getLineId() != null) ? contextData.getLineId() : null);
/*  93 */       dto.setStartMileage(
/*  94 */           (contextData.getStartMileage() != null) ? contextData.getStartMileage() : null);
/*  95 */       dto.setEndMileage((contextData.getEndMileage() != null) ? contextData.getEndMileage() : null);
/*  96 */       dto.setDirection(
/*  97 */           (contextData.getDirection() != null) ? contextData.getDirection().toString() : null);
/*  98 */       if (contextData.getNotifySource() != null) {
/*  99 */         dto.setAlarmSource(getAlarmSourceFromId(Integer.valueOf(Math.toIntExact(contextData.getNotifySource().longValue()))));
/*     */       }
/*     */     } 
/* 102 */     dto.setDegree(alarmLog.getDegree());
/* 103 */     if (roadLine != null) {
/* 104 */       dto.setLineName(roadLine.getLineName());
/*     */     }
/* 106 */     if (roadSection != null) {
/* 107 */       dto.setSectionName(roadSection.getSectionName());
/*     */     }
/*     */     
/* 110 */     return dto;
/*     */   }
/*     */   
/*     */   public static AlarmSource getAlarmSourceFromId(Integer id) {
/* 114 */     if (id == null) {
/* 115 */       return null;
/*     */     }
/* 117 */     if (id == AlarmSource.AUTO_INCIDENT_DETECTION.getId())
/* 118 */       return AlarmSource.AUTO_INCIDENT_DETECTION; 
/* 119 */     if (id == AlarmSource.DETECTOR.getId())
/* 120 */       return AlarmSource.DETECTOR; 
/* 121 */     if (id == AlarmSource.MCNS.getId()) {
/* 122 */       return AlarmSource.MCNS;
/*     */     }
/* 124 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String setRtuDeviceName(String statusName, Integer milepost) {
/* 129 */     if (statusName == null) {
/* 130 */       return null;
/*     */     }
/* 132 */     String[] statusNames = statusName.split(" ");
/* 133 */     String statusStart = null;
/* 134 */     String result = null;
/* 135 */     if (statusNames.length > 1 && 
/* 136 */       statusNames[1].indexOf("啟動") != -1) {
/* 137 */       statusStart = statusNames[1];
/* 138 */       String[] statusStarts = statusStart.split("_");
/* 139 */       String deviceId = statusStarts[0];
/* 140 */       if ("ACH-R11".equals(deviceId)) {
/* 141 */         result = "RTU-3-3";
/* 142 */       } else if ("ACH-R12".equals(deviceId)) {
/* 143 */         result = "RTU-3-4";
/* 144 */       } else if ("ACH-R13".equals(deviceId)) {
/* 145 */         result = "RTU-4-3";
/* 146 */       } else if ("ACH-R14".equals(deviceId)) {
/* 147 */         result = "RTU-4-4";
/* 148 */       } else if ("ACH-R15".equals(deviceId)) {
/* 149 */         result = "RTU-5-3";
/* 150 */       } else if ("ACH-R16".equals(deviceId)) {
/* 151 */         result = "RTU-5-4";
/* 152 */       } else if ("ACH-R01".equals(deviceId) && milepost.intValue() == 432928) {
/* 153 */         result = "RTU-2-3";
/* 154 */       } else if ("ACH-R02".equals(deviceId) && milepost.intValue() == 432928) {
/* 155 */         result = "RTU-2-4";
/* 156 */       } else if ("ACH-R03".equals(deviceId) && milepost.intValue() == 432928) {
/* 157 */         result = null;
/* 158 */       } else if ("ACH-R17".equals(deviceId)) {
/* 159 */         result = "RTU-6-3";
/* 160 */       } else if ("ACH-R18".equals(deviceId)) {
/* 161 */         result = "RTU-6-4";
/* 162 */       } else if ("ACH-R01".equals(deviceId) && milepost.intValue() == 434600) {
/* 163 */         result = "RTU-8-3";
/* 164 */       } else if ("ACH-R02".equals(deviceId) && milepost.intValue() == 434600) {
/* 165 */         result = "RTU-8-4";
/* 166 */       } else if ("ACH-R03".equals(deviceId) && milepost.intValue() == 434600) {
/* 167 */         result = null;
/*     */       } 
/*     */     } 
/*     */     
/* 171 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static RoomAnalogTreeDTO transferToRoomAnalogTreeDTO(DeviceTcConfig hostConfig, DeviceLocationMappingConfig locationMappingConfig) {
/* 176 */     RoomAnalogTreeDTO result = null;
/* 177 */     if (hostConfig == null || locationMappingConfig == null) {
/* 178 */       return null;
/*     */     }
/* 180 */     String deviceType = hostConfig.getDeviceType();
/* 181 */     String displayName = locationMappingConfig.getLocationName();
/* 182 */     if (locationMappingConfig.getSubLocation() != null) {
/* 183 */       displayName = displayName + "_" + locationMappingConfig.getSubLocation();
/*     */     }
/* 185 */     if (hostConfig != null && hostConfig
/* 186 */       .getDeviceName() != null && deviceType != null && (deviceType
/*     */       
/* 188 */       .equals("濕度") || deviceType
/* 189 */       .equals("DC電壓") || deviceType
/* 190 */       .equals("油槽容量") || deviceType
/* 191 */       .equals("溫度") || deviceType
/* 192 */       .equals("電流") || deviceType
/* 193 */       .equals("FM發射機") || deviceType
/* 194 */       .equals("發射機輸出功率"))) {
/* 195 */       result = new RoomAnalogTreeDTO();
/* 196 */       result.setType(RoomAnalogTreeDTO.Type.ANALOG);
/* 197 */       result.setId(hostConfig.getDeviceName());
/* 198 */       if (deviceType.equals("濕度")) {
/* 199 */         result.setAnalogType(RoomAnalogTreeDTO.AnalogType.HUMIDITY);
/* 200 */       } else if (deviceType.equals("DC電壓")) {
/* 201 */         result.setAnalogType(RoomAnalogTreeDTO.AnalogType.VOLTAGE);
/* 202 */       } else if (deviceType.equals("油槽容量")) {
/* 203 */         result.setAnalogType(RoomAnalogTreeDTO.AnalogType.OILTANK);
/* 204 */       } else if (deviceType.equals("溫度")) {
/* 205 */         result.setAnalogType(RoomAnalogTreeDTO.AnalogType.TEMPERATURE);
/* 206 */       } else if (deviceType.equals("電流")) {
/* 207 */         result.setAnalogType(RoomAnalogTreeDTO.AnalogType.CURRENT);
/* 208 */       } else if (deviceType.equals("FM發射機")) {
/* 209 */         result.setAnalogType(RoomAnalogTreeDTO.AnalogType.FMT);
/* 210 */       } else if (deviceType.equals("發射機輸出功率")) {
/* 211 */         result.setAnalogType(RoomAnalogTreeDTO.AnalogType.TRANSMITTERPOWER);
/*     */       } 
/* 213 */       result.setDisplayName(displayName + "_" + hostConfig.getDisplayName());
/*     */     } 
/* 215 */     return result;
/*     */   }
/*     */   
/*     */   public static String getNcuName(String ncuDeviceName) {
/* 219 */     if (ncuDeviceName == null) {
/* 220 */       return null;
/*     */     }
/* 222 */     String[] ncuSp = ncuDeviceName.split("-");
/* 223 */     switch (ncuSp[0]) {
/*     */       case "NCU_1":
/* 225 */         return "中港溪機房";
/*     */       case "NCU_2":
/* 227 */         return "苗栗機房";
/*     */       case "NCU_3":
/* 229 */         return "泰安機房";
/*     */       case "NCU_4":
/* 231 */         return "交控中心3F";
/*     */       case "NCU_5":
/* 233 */         return "交控中心4F";
/*     */       case "NCU_6":
/* 235 */         return "彰化機房";
/*     */       case "NCU_7":
/* 237 */         return "員林機房";
/*     */       case "NCU_8":
/* 239 */         return "斗南機房";
/*     */       case "NCU_9":
/* 241 */         return "後龍機房";
/*     */       case "NCU_10":
/* 243 */         return "西湖機房";
/*     */       case "NCU_11":
/* 245 */         return "大甲機房";
/*     */       case "NCU_12":
/* 247 */         return "清水機房";
/*     */       case "NCU_13":
/* 249 */         return "彰化系統機房";
/*     */       case "NCU_14":
/* 251 */         return "草屯機房";
/*     */       case "NCU_15":
/* 253 */         return "南投機房";
/*     */       case "NCU_16":
/* 255 */         return "名間機房";
/*     */       case "NCU_17":
/* 257 */         return "林內機房";
/*     */       case "NCU_18":
/* 259 */         return "東草屯機房";
/*     */       case "NCU_19":
/* 261 */         return "國姓1號隧道西口機房";
/*     */       case "NCU_20":
/* 263 */         return "國姓2號隧道西口機房";
/*     */       case "NCU_21":
/* 265 */         return "埔里隧道東口機房";
/*     */       case "NCU_22":
/* 267 */         return "國姓1號隧道輔助機房";
/*     */       case "NCU_23":
/* 269 */         return "國姓1號隧道東口機房";
/*     */       case "NCU_24":
/* 271 */         return "埔里隧道維修機房";
/*     */     } 
/* 273 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getRoomName(String cardName) {
/* 278 */     if (cardName == null) {
/* 279 */       return null;
/*     */     }
/* 281 */     switch (cardName) {
/*     */       case "FRSCam-ZhonggangCreek":
/* 283 */         return "中港溪機房";
/*     */       case "FRSCam-Miaoli":
/* 285 */         return "苗栗機房";
/*     */       case "FRSCam-Taian":
/* 287 */         return "泰安機房";
/*     */       case "FRSCam-Center3F":
/* 289 */         return "交控中心3F";
/*     */       case "FRSCam-Center4F":
/* 291 */         return "交控中心4F";
/*     */       case "FRSCam-Changhua":
/* 293 */         return "彰化機房";
/*     */       case "FRSCam-Yuanlin":
/* 295 */         return "員林機房";
/*     */       case "FRSCam-Dounan":
/* 297 */         return "斗南機房";
/*     */       case "FRSCam-Houlong":
/* 299 */         return "後龍機房";
/*     */       case "FRSCam-Xihu":
/* 301 */         return "西湖機房";
/*     */       case "FRSCam-Dajia":
/* 303 */         return "大甲機房";
/*     */       case "FRSCam-Cingshui":
/* 305 */         return "清水機房";
/*     */       case "FRSCam-ChanghuaSystem":
/* 307 */         return "彰化系統機房";
/*     */       case "FRSCam-Caotun":
/* 309 */         return "草屯機房";
/*     */       case "FRSCam-Nantou":
/* 311 */         return "南投機房";
/*     */       case "FRSCam-Mingjian":
/* 313 */         return "名間機房";
/*     */       case "FRSCam-Rinnai":
/* 315 */         return "林內機房";
/*     */       case "FRSCam-EastCaotun":
/* 317 */         return "東草屯機房";
/*     */       case "FRSCam-GuoxingNo1TunnelWestExit":
/* 319 */         return "國姓1號隧道西口機房";
/*     */       case "FRSCam-GuoxingNo1TunnelAuxiliary":
/* 321 */         return "國姓2號隧道西口機房";
/*     */       case "FRSCam-GuoxingNo1TunnelEastExit":
/* 323 */         return "埔里隧道東口機房";
/*     */       case "FRSCam-GuoxingNo2TunnelWestExit":
/* 325 */         return "國姓1號隧道輔助機房";
/*     */       case "FRSCam-PuliTunnelEastExit":
/* 327 */         return "國姓1號隧道東口機房";
/*     */       case "FRSCam-PuliTunnelMaintenance":
/* 329 */         return "埔里隧道維修機房";
/*     */     } 
/* 331 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getNcuId(String cardName) {
/* 336 */     if (cardName == null) {
/* 337 */       return null;
/*     */     }
/* 339 */     switch (cardName) {
/*     */       case "FRSCam-ZhonggangCreek":
/* 341 */         return "NCU_1";
/*     */       case "FRSCam-Miaoli":
/* 343 */         return "NCU_2";
/*     */       case "FRSCam-Taian":
/* 345 */         return "NCU_3";
/*     */       case "FRSCam-Center3F":
/* 347 */         return "NCU_4";
/*     */       case "FRSCam-Center4F":
/* 349 */         return "NCU_5";
/*     */       case "FRSCam-Changhua":
/* 351 */         return "NCU_6";
/*     */       case "FRSCam-Yuanlin":
/* 353 */         return "NCU_7";
/*     */       case "FRSCam-Dounan":
/* 355 */         return "NCU_8";
/*     */       case "FRSCam-Houlong":
/* 357 */         return "NCU_9";
/*     */       case "FRSCam-Xihu":
/* 359 */         return "NCU_10";
/*     */       case "FRSCam-Dajia":
/* 361 */         return "NCU_11";
/*     */       case "FRSCam-Cingshui":
/* 363 */         return "NCU_12";
/*     */       case "FRSCam-ChanghuaSystem":
/* 365 */         return "NCU_13";
/*     */       case "FRSCam-Caotun":
/* 367 */         return "NCU_14";
/*     */       case "FRSCam-Nantou":
/* 369 */         return "NCU_15";
/*     */       case "FRSCam-Mingjian":
/* 371 */         return "NCU_16";
/*     */       case "FRSCam-Rinnai":
/* 373 */         return "NCU_17";
/*     */       case "FRSCam-EastCaotun":
/* 375 */         return "NCU_18";
/*     */       case "FRSCam-GuoxingNo1TunnelWestExit":
/* 377 */         return "NCU_19";
/*     */       case "FRSCam-GuoxingNo1TunnelAuxiliary":
/* 379 */         return "NCU_20";
/*     */       case "FRSCam-GuoxingNo1TunnelEastExit":
/* 381 */         return "NCU_21";
/*     */       case "FRSCam-GuoxingNo2TunnelWestExit":
/* 383 */         return "NCU_22";
/*     */       case "FRSCam-PuliTunnelEastExit":
/* 385 */         return "NCU_23";
/*     */       case "FRSCam-PuliTunnelMaintenance":
/* 387 */         return "NCU_24";
/*     */     } 
/* 389 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getLineName(String lineId) {
/* 394 */     if (lineId == null) {
/* 395 */       return null;
/*     */     }
/* 397 */     switch (lineId) {
/*     */       case "N1":
/* 399 */         return "國道1號";
/*     */       case "N1甲":
/* 401 */         return "國道1號甲線";
/*     */       case "N2":
/* 403 */         return "國道2號";
/*     */       case "N2甲":
/* 405 */         return "國道2號甲線";
/*     */       case "N3":
/* 407 */         return "國道3號";
/*     */       case "N3甲":
/* 409 */         return "國道3號甲線";
/*     */       case "N4":
/* 411 */         return "國道4號";
/*     */       case "N5":
/* 413 */         return "國道5號";
/*     */       case "N6":
/* 415 */         return "國道6號";
/*     */       case "N7":
/* 417 */         return "國道7號";
/*     */       case "N8":
/* 419 */         return "國道8號";
/*     */       case "N10":
/* 421 */         return "國道10號";
/*     */     } 
/* 423 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String resultToErrCode(Integer value) {
/* 428 */     switch (value.intValue()) {
/*     */       case 0:
/* 430 */         return "成功";
/*     */       case 1:
/* 432 */         return "資料長度與合法卡長度不符";
/*     */       case 2:
/* 434 */         return "合法卡長度不符";
/*     */       case 3:
/* 436 */         return "已超過可容納的筆數";
/*     */       case 4:
/* 438 */         return "卡號已存在";
/*     */       case 5:
/* 440 */         return "系統參數設定值錯誤";
/*     */       case 6:
/* 442 */         return "卡號不存在";
/*     */       case 7:
/* 444 */         return "設定資料長度過長";
/*     */       case 8:
/* 446 */         return "讀取資料長度過長";
/*     */       case 9:
/* 448 */         return "資料長度不符";
/*     */       case 10:
/* 450 */         return "裝置無回應";
/*     */       case 11:
/* 452 */         return "裝置回應資料錯誤";
/*     */       case 12:
/* 454 */         return "寫入裝置資料過長";
/*     */       case 13:
/* 456 */         return "登入密碼錯誤";
/*     */       case 14:
/* 458 */         return "未登入";
/*     */       case 15:
/* 460 */         return "輸入資料錯誤";
/*     */       case 16:
/* 462 */         return "已有登入者";
/*     */       case 17:
/* 464 */         return "未登入";
/*     */       case 18:
/* 466 */         return "已有登入其他command";
/*     */       case 19:
/* 468 */         return "主機寫入flash失敗";
/*     */       case 20:
/* 470 */         return "裝置回應錯誤 error";
/*     */       case 21:
/* 472 */         return "裝置回應錯誤 BCC Error";
/*     */       case 22:
/* 474 */         return "裝置回應錯誤 CMD Error";
/*     */     } 
/* 476 */     return "該訊息未收納";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String resultToStatusCode(String value) {
/* 481 */     switch (value) {
/*     */       case "01":
/* 483 */         return "合法卡";
/*     */       case "02":
/* 485 */         return "密碼錯誤";
/*     */       case "03":
/* 487 */         return "由於當需要密碼時,必需為刷卡,不能用按鍵輸入卡號";
/*     */       case "04":
/* 489 */         return "權限不合";
/*     */       case "05":
/* 491 */         return "假日權限不合";
/*     */       case "06":
/* 493 */         return "卡片時效已過";
/*     */       case "07":
/* 495 */         return "無此卡號";
/*     */       case "08":
/* 497 */         return "主副卡權限比對失敗";
/*     */       case "09":
/* 499 */         return "雙重卡權限比對失敗";
/*     */       case "0A":
/* 501 */         return "考勤卡";
/*     */       case "0B":
/* 503 */         return "黑名單發警報";
/*     */       case "0C":
/* 505 */         return "解除警報";
/*     */       case "0D":
/* 507 */         return "第一張主卡或副卡或雙重卡";
/*     */       case "0E":
/* 509 */         return "輸入代碼錯誤";
/*     */       case "0F":
/* 511 */         return "刷卡加代碼不開門";
/*     */       case "10":
/* 513 */         return "巡邏卡開門";
/*     */       case "11":
/* 515 */         return "巡邏卡不開門";
/*     */       case "12":
/* 517 */         return "巡邏卡權限不符,不開門";
/*     */       case "13":
/* 519 */         return "Device ID to write setting to";
/*     */       case "14":
/* 521 */         return "刷卡加代碼權限不符,不開門";
/*     */       case "15":
/* 523 */         return "黑名單";
/*     */       case "16":
/* 525 */         return "在常開時區刷卡";
/*     */       case "17":
/* 527 */         return "anti比對失敗";
/*     */       case "18":
/* 529 */         return "無任何卡號開門";
/*     */       case "1D":
/* 531 */         return "錯誤超過次數,發警報";
/*     */       case "1E":
/* 533 */         return "解除警報";
/*     */     } 
/* 535 */     return "出現未歸類的狀態";
/*     */   }
/*     */ 
/*     */   
/*     */   public static RoadLineDTO transferRoadLineDTO(RoadLine roadLine) {
/* 540 */     RoadLineDTO dto = new RoadLineDTO();
/* 541 */     dto.setLineId(roadLine.getLineId());
/* 542 */     dto.setLineName(roadLine.getLineName());
/* 543 */     dto.setDirection(roadLine.getDirection());
/* 544 */     dto.setStartMileage(roadLine.getStartMileage());
/* 545 */     dto.setEndMileage(roadLine.getEndMileage());
/* 546 */     return dto;
/*     */   }
/*     */   
/*     */   public static String transferAlarmTypeStr(String alarmTypeStr) {
/* 550 */     if (alarmTypeStr == null) {
/* 551 */       return "";
/*     */     }
/* 553 */     switch (alarmTypeStr) {
/*     */       case "ALL":
/* 555 */         return "全部";
/*     */       case "UPPER_LIMIT":
/* 557 */         return AlarmType.UPPER_LIMIT.getName();
/*     */       case "LOWER_LIMIT":
/* 559 */         return AlarmType.LOWER_LIMIT.getName();
/*     */       case "ABNORMAL":
/* 561 */         return AlarmType.ABNORMAL.getName();
/*     */       case "ROOM_OPEN_FAIL":
/* 563 */         return AlarmType.ROOM_OPEN_FAIL.getName();
/*     */       case "IDENTIFY_FAIL":
/* 565 */         return AlarmType.IDENTIFY_FAIL.getName();
/*     */       case "DOOR_OPEN":
/* 567 */         return AlarmType.DOOR_OPEN.getName();
/*     */       case "SOUND_LIGHT_START":
/* 569 */         return AlarmType.SOUND_LIGHT_START.getName();
/*     */       case "SECURITY_RELEASE":
/* 571 */         return AlarmType.SECURITY_RELEASE.getName();
/*     */       case "NCU_OFFLINE":
/* 573 */         return AlarmType.NCU_OFFLINE.getName();
/*     */       case "RTU_OFFLINE":
/* 575 */         return AlarmType.RTU_OFFLINE.getName();
/*     */       case "PD_OFFLINE":
/* 577 */         return AlarmType.PD_OFFLINE.getName();
/*     */       case "PD_BOX_OPEN":
/* 579 */         return AlarmType.PD_BOX_OPEN.getName();
/*     */       case "PRIMARY_ABNORMAL_R":
/* 581 */         return AlarmType.PRIMARY_ABNORMAL_R.getName();
/*     */       case "PRIMARY_ABNORMAL_S":
/* 583 */         return AlarmType.PRIMARY_ABNORMAL_S.getName();
/*     */       case "PRIMARY_ABNORMAL_T":
/* 585 */         return AlarmType.PRIMARY_ABNORMAL_T.getName();
/*     */       case "SECONDARY_ABNORMAL_R":
/* 587 */         return AlarmType.SECONDARY_ABNORMAL_R.getName();
/*     */       case "SECONDARY_ABNORMAL_S":
/* 589 */         return AlarmType.SECONDARY_ABNORMAL_S.getName();
/*     */       case "SECONDARY_ABNORMAL_T":
/* 591 */         return AlarmType.SECONDARY_ABNORMAL_T.getName();
/*     */       case "BRANCH_CIRCUIT_ABNORMAL_1":
/* 593 */         return AlarmType.BRANCH_CIRCUIT_ABNORMAL_1.getName();
/*     */       case "BRANCH_CIRCUIT_ABNORMAL_2":
/* 595 */         return AlarmType.BRANCH_CIRCUIT_ABNORMAL_2.getName();
/*     */       case "BRANCH_CIRCUIT_ABNORMAL_3":
/* 597 */         return AlarmType.BRANCH_CIRCUIT_ABNORMAL_3.getName();
/*     */       case "BRANCH_CIRCUIT_ABNORMAL_4":
/* 599 */         return AlarmType.BRANCH_CIRCUIT_ABNORMAL_4.getName();
/*     */       case "BRANCH_CIRCUIT_ABNORMAL_5":
/* 601 */         return AlarmType.BRANCH_CIRCUIT_ABNORMAL_5.getName();
/*     */     } 
/* 603 */     return alarmTypeStr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String returnCCTVurl(String deviceName) {
/* 613 */     if (deviceName == null) {
/* 614 */       return "";
/*     */     }
/* 616 */     switch (deviceName) {
/*     */       case "3030014":
/* 618 */         return "http://10.122.41.54:9090/mjpeg/721/4CIF";
/*     */       case "3030015":
/* 620 */         return "http://10.122.41.55:9090/mjpeg/408/4CIF";
/*     */       case "3030013":
/* 622 */         return "http://10.122.41.53:9090/mjpeg/722/4CIF";
/*     */       case "3030069":
/* 624 */         return "http://10.122.41.53:9090/mjpeg/727/4CIF";
/*     */       case "3030068":
/* 626 */         return "http://10.122.41.52:9090/mjpeg/728/4CIF";
/*     */       case "3030039":
/* 628 */         return "http://10.122.41.55:9090/mjpeg/724/4CIF";
/*     */       case "3030040":
/* 630 */         return "http://10.122.41.56:9090/mjpeg/725/4CIF";
/*     */       case "3030038":
/* 632 */         return "http://10.122.41.54:9090/mjpeg/726/4CIF";
/*     */       case "3030023":
/* 634 */         return "http://10.122.41.52:9090/mjpeg/723/4CIF";
/*     */       case "3030129":
/* 636 */         return "http://10.122.41.51:9090/mjpeg/702/4CIF";
/*     */       case "3030130":
/* 638 */         return "http://10.122.41.52:9090/mjpeg/703/4CIF";
/*     */       case "3030131":
/* 640 */         return "http://10.122.41.53:9090/mjpeg/704/4CIF";
/*     */       case "3030572":
/* 642 */         return "http://10.122.41.52:9090/mjpeg/534/4CIF";
/*     */       case "3030573":
/* 644 */         return "http://10.122.41.53:9090/mjpeg/535/4CIF";
/*     */       case "3030574":
/* 646 */         return "http://10.122.41.54:9090/mjpeg/536/4CIF";
/*     */       case "3030575":
/* 648 */         return "http://10.122.41.55:9090/mjpeg/537/4CIF";
/*     */       case "3030137":
/* 650 */         return "http://10.122.41.52:9090/mjpeg/711/4CIF";
/*     */       case "3030138":
/* 652 */         return "http://10.122.41.53:9090/mjpeg/710/4CIF";
/*     */       case "3030132":
/* 654 */         return "http://10.122.41.54:9090/mjpeg/705/4CIF";
/*     */       case "3030133":
/* 656 */         return "http://10.122.41.55:9090/mjpeg/706/4CIF";
/*     */       case "3030134":
/* 658 */         return "http://10.122.41.56:9090/mjpeg/707/4CIF";
/*     */       case "3030135":
/* 660 */         return "http://10.122.41.51:9090/mjpeg/708/4CIF";
/*     */       case "3030139":
/* 662 */         return "http://10.122.41.54:9090/mjpeg/712/4CIF";
/*     */       case "3030140":
/* 664 */         return "http://10.122.41.55:9090/mjpeg/713/4CIF";
/*     */       case "3030141":
/* 666 */         return "http://10.122.41.56:9090/mjpeg/714/4CIF";
/*     */       case "3030576":
/* 668 */         return "http://10.122.41.56:9090/mjpeg/538/4CIF";
/*     */     } 
/* 670 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getLineIdToValue(String lineId) {
/* 675 */     if (lineId == null) {
/* 676 */       return "";
/*     */     }
/*     */     
/* 679 */     switch (lineId) {
/*     */       case "N1":
/* 681 */         return "1";
/*     */       case "N3":
/* 683 */         return "3";
/*     */       case "N4":
/* 685 */         return "4";
/*     */       case "N6":
/* 687 */         return "6";
/*     */       case "T72":
/* 689 */         return "72";
/*     */       case "T74":
/* 691 */         return "74";
/*     */       case "T74甲":
/* 693 */         return "74a";
/*     */       case "T76":
/* 695 */         return "76";
/*     */       case "T78":
/* 697 */         return "78";
/*     */     } 
/* 699 */     return "";
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\a\\util\TransferHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */