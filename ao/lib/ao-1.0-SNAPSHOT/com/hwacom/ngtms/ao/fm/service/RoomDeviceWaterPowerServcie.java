/*     */ package com.hwacom.ngtms.ao.fm.service;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.ao.fm.hz.AoHzMap;
/*     */ import com.hwacom.ngtms.ao.fm.model.PowerStatusData;
/*     */ import com.hwacom.ngtms.ao.fm.model.WaterPowerBaseConfig;
/*     */ import com.hwacom.ngtms.ao.fm.model.WaterPowerBaseHourLogData;
/*     */ import com.hwacom.ngtms.ao.fm.model.WaterStatusData;
/*     */ import com.hwacom.ngtms.ao.fm.repository.WaterPowerBaseHourLogDataRepository;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.service.OpLogger;
/*     */ import com.hwacom.ngtms.common.fm.model.DeviceConfig;
/*     */ import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
/*     */ import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
/*     */ import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service
/*     */ public class RoomDeviceWaterPowerServcie {
/*  36 */   private static final Logger logger = LoggerFactory.getLogger(RoomDeviceWaterPowerServcie.class);
/*     */   
/*     */   @Autowired
/*     */   OpLogger opLogger;
/*     */   @Autowired
/*     */   WaterPowerBaseHourLogDataRepository waterPowerBaseHourLogDataRepository;
/*  42 */   private static Double VALUE_FORMMAT = Double.valueOf(0.01D);
/*     */ 
/*     */   
/*  45 */   private static Integer cumulateValue = Integer.valueOf(2300);
/*  46 */   private static Integer instantaneousValue = Integer.valueOf(2302);
/*     */   
/*  48 */   private double cumulateValue_D = 0.0D;
/*  49 */   private double instantaneousValue_D = 0.0D;
/*     */ 
/*     */   
/*  52 */   private static Integer va = Integer.valueOf(2350);
/*  53 */   private static Integer vb = Integer.valueOf(2351);
/*  54 */   private static Integer vc = Integer.valueOf(2352);
/*  55 */   private static Integer avgv = Integer.valueOf(2353);
/*  56 */   private static Integer ia = Integer.valueOf(2358);
/*  57 */   private static Integer ib = Integer.valueOf(2359);
/*  58 */   private static Integer ic = Integer.valueOf(2360);
/*  59 */   private static Integer avgi = Integer.valueOf(2361);
/*  60 */   private static Integer kw = Integer.valueOf(2366);
/*  61 */   private static Integer pf = Integer.valueOf(2378);
/*  62 */   private static Integer kwh = Integer.valueOf(2386);
/*  63 */   private static Integer cT = Integer.valueOf(2384);
/*     */   
/*  65 */   private double va_D = 0.0D;
/*  66 */   private double vb_D = 0.0D;
/*  67 */   private double vc_D = 0.0D;
/*  68 */   private double avgv_D = 0.0D;
/*  69 */   private double ia_D = 0.0D;
/*  70 */   private double ib_D = 0.0D;
/*  71 */   private double ic_D = 0.0D;
/*  72 */   private double avgi_D = 0.0D;
/*  73 */   private double kw_D = 0.0D;
/*  74 */   private double pf_D = 0.0D;
/*  75 */   private double kwh_D = 0.0D;
/*  76 */   private double cT_D = 0.0D;
/*     */   
/*  78 */   int[] intData = new int[36];
/*     */   
/*  80 */   ModbusMaster modbus = null;
/*     */ 
/*     */   
/*     */   public void process() {
/*  84 */     IMap<String, WaterPowerBaseConfig> waterPowerBasicMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.WaterPowerBaseConfig);
/*  85 */     IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  86 */     IMap<String, WaterStatusData> waterStatusMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.WaterStatusData);
/*  87 */     IMap<String, PowerStatusData> powerStatusMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.PowerStatusData);
/*  88 */     List<WaterPowerBaseHourLogData> saveRecords = new ArrayList<>();
/*  89 */     for (WaterPowerBaseConfig config : waterPowerBasicMap.values()) {
/*  90 */       EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*     */       
/*  92 */       PredicateBuilder pb = eo.get("deviceName").equal(config.getRtuDeviceName()).and((Predicate)eo.get("enable").equal(Boolean.valueOf(true)));
/*  93 */       DeviceTcConfig tcConfig = deviceConfigMap.values((Predicate)pb).iterator().next();
/*  94 */       this.modbus = null;
/*  95 */       if (tcConfig != null && 
/*  96 */         this.modbus == null) {
/*     */         try {
/*  98 */           createModbusMaster((DeviceConfig)tcConfig);
/*  99 */         } catch (UnknownHostException e) {
/* 100 */           logger.error("Create Modbus Master failed , deviceName = '{}'", tcConfig
/* 101 */               .getDeviceName());
/*     */         } 
/*     */       }
/*     */       
/* 105 */       if (this.modbus.isConnected()) {
/*     */         
/*     */         try {
/* 108 */           int[] intH = this.modbus.readHoldingRegisters(1, 2350, 36);
/* 109 */           if (intH != null) {
/* 110 */             for (int i = 0; i < intH.length; i++) {
/* 111 */               this.intData[i] = intH[i];
/*     */             }
/*     */           }
/*     */           
/* 115 */           byte[] temp1 = new byte[4];
/* 116 */           byte[] temp2 = new byte[4];
/* 117 */           byte[] dest = new byte[4];
/* 118 */           int[] intCumulateValue = this.modbus.readHoldingRegisters(1, cumulateValue.intValue(), 2);
/* 119 */           if (intCumulateValue != null) {
/* 120 */             temp1 = int2byte(intCumulateValue[0]);
/* 121 */             temp2 = int2byte(intCumulateValue[1]);
/* 122 */             dest[0] = temp1[3];
/* 123 */             dest[1] = temp1[2];
/* 124 */             dest[2] = temp2[3];
/* 125 */             dest[3] = temp2[2];
/* 126 */             this.cumulateValue_D = Float.intBitsToFloat(toInt(copyFrom(dest, 0, 4)));
/*     */           } 
/*     */           
/* 129 */           int[] intInstantaneousValue = this.modbus.readHoldingRegisters(1, instantaneousValue.intValue(), 2);
/* 130 */           if (intInstantaneousValue != null) {
/* 131 */             temp1 = int2byte(intInstantaneousValue[0]);
/* 132 */             temp2 = int2byte(intInstantaneousValue[1]);
/* 133 */             dest[0] = temp1[3];
/* 134 */             dest[1] = temp1[2];
/* 135 */             dest[2] = temp2[3];
/* 136 */             dest[3] = temp2[2];
/* 137 */             this.instantaneousValue_D = Float.intBitsToFloat(toInt(copyFrom(dest, 0, 4)));
/*     */           } 
/*     */           
/* 140 */           int[] intKwh = this.modbus.readHoldingRegisters(1, kwh.intValue(), 2);
/* 141 */           if (intKwh != null) {
/* 142 */             temp1 = int2byte(intKwh[0]);
/* 143 */             temp2 = int2byte(intKwh[1]);
/* 144 */             dest[0] = temp1[3];
/* 145 */             dest[1] = temp1[2];
/* 146 */             dest[2] = temp2[3];
/* 147 */             dest[3] = temp2[2];
/* 148 */             this.kwh_D = Float.intBitsToFloat(toInt(copyFrom(dest, 0, 4)));
/*     */           } 
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
/* 162 */           this.modbus.disconnect();
/* 163 */           Thread.sleep(500L);
/* 164 */           Double powerValue = Double.valueOf(this.kwh_D / 10000.0D);
/*     */           
/* 166 */           this.va_D = waterPowerValue(va.intValue());
/* 167 */           this.vb_D = waterPowerValue(vb.intValue());
/* 168 */           this.vc_D = waterPowerValue(vc.intValue());
/* 169 */           this.avgv_D = waterPowerValue(avgv.intValue());
/* 170 */           this.ia_D = waterPowerValue(ia.intValue());
/* 171 */           this.ib_D = waterPowerValue(ib.intValue());
/* 172 */           this.ic_D = waterPowerValue(ic.intValue());
/* 173 */           this.avgi_D = waterPowerValue(avgi.intValue());
/* 174 */           this.kw_D = waterPowerValue(kw.intValue());
/* 175 */           this.pf_D = waterPowerValue(pf.intValue());
/*     */ 
/*     */           
/* 178 */           Date startDate = new Date();
/* 179 */           Date endDate = new Date();
/*     */           
/* 181 */           Calendar cal = Calendar.getInstance();
/* 182 */           cal.setTime(startDate);
/* 183 */           cal.add(11, -23);
/* 184 */           Double wateCurrentLastHour = null;
/* 185 */           Double wateCurrent24Hour = null;
/* 186 */           Double powerCurrentLastHour = null;
/* 187 */           Double powerCurrent24Hour = null;
/*     */ 
/*     */           
/* 190 */           if (waterStatusMap.get(config.getId()) != null) {
/*     */             
/* 192 */             wateCurrentLastHour = Double.valueOf(this.cumulateValue_D - ((WaterStatusData)waterStatusMap.get(config.getId())).getCumulateValue().doubleValue());
/* 193 */             if (wateCurrentLastHour.doubleValue() < -1.0D || wateCurrentLastHour.doubleValue() > 100.0D) {
/*     */               
/* 195 */               WaterPowerBaseHourLogData waterPowerBaseHourLogData = this.waterPowerBaseHourLogDataRepository.findTopByRoomIdOrderByDataTimeDesc(config
/* 196 */                   .getId());
/* 197 */               if (waterPowerBaseHourLogData != null) {
/* 198 */                 wateCurrentLastHour = waterPowerBaseHourLogData.getHourWater();
/*     */               } else {
/* 200 */                 wateCurrentLastHour = Double.valueOf(0.0D);
/*     */               } 
/*     */             } 
/*     */           } else {
/* 204 */             wateCurrentLastHour = Double.valueOf(0.0D);
/*     */           } 
/*     */           
/* 207 */           if (powerStatusMap.get(config.getId()) != null) {
/* 208 */             powerCurrentLastHour = Double.valueOf(powerValue.doubleValue() - ((PowerStatusData)powerStatusMap.get(config.getId())).getKwh().doubleValue());
/* 209 */             if (powerCurrentLastHour.doubleValue() < 0.0D || powerCurrentLastHour.doubleValue() > 5000.0D) {
/*     */               
/* 211 */               WaterPowerBaseHourLogData waterPowerBaseHourLogData = this.waterPowerBaseHourLogDataRepository.findTopByRoomIdOrderByDataTimeDesc(config
/* 212 */                   .getId());
/* 213 */               if (waterPowerBaseHourLogData != null) {
/* 214 */                 powerCurrentLastHour = waterPowerBaseHourLogData.getHourPower();
/*     */               } else {
/* 216 */                 powerCurrentLastHour = Double.valueOf(0.0D);
/*     */               } 
/*     */             } 
/*     */           } else {
/* 220 */             powerCurrentLastHour = Double.valueOf(0.0D);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 225 */           List<WaterPowerBaseHourLogData> dayDatas = this.waterPowerBaseHourLogDataRepository.findByRoomIdAndDataTimeBetween(config
/* 226 */               .getId(), cal.getTime(), endDate);
/* 227 */           Double water23sum = Double.valueOf(0.0D);
/* 228 */           Double power23sum = Double.valueOf(0.0D);
/* 229 */           if (dayDatas != null) {
/* 230 */             for (WaterPowerBaseHourLogData day : dayDatas) {
/* 231 */               water23sum = Double.valueOf(water23sum.doubleValue() + day.getHourWater().doubleValue());
/* 232 */               power23sum = Double.valueOf(power23sum.doubleValue() + day.getHourPower().doubleValue());
/*     */             } 
/* 234 */             if (water23sum.doubleValue() > 0.0D) {
/* 235 */               wateCurrent24Hour = Double.valueOf(water23sum.doubleValue() + wateCurrentLastHour.doubleValue());
/*     */             } else {
/* 237 */               wateCurrent24Hour = Double.valueOf(0.0D);
/*     */             } 
/* 239 */             if (power23sum.doubleValue() > 0.0D) {
/* 240 */               powerCurrent24Hour = Double.valueOf(power23sum.doubleValue() + powerCurrentLastHour.doubleValue());
/*     */             } else {
/* 242 */               powerCurrent24Hour = Double.valueOf(0.0D);
/*     */             } 
/*     */           } else {
/* 245 */             wateCurrent24Hour = Double.valueOf(0.0D);
/* 246 */             powerCurrent24Hour = Double.valueOf(0.0D);
/*     */           } 
/*     */           
/* 249 */           Calendar alarmTime = Calendar.getInstance();
/* 250 */           int month = alarmTime.get(2) + 1;
/*     */           
/* 252 */           Double waterBase = getWaterBaseValue(month, config);
/* 253 */           String waterAlarmMeesage = "";
/* 254 */           Double waterUp = Double.valueOf(waterBase.doubleValue() * (1 + config.getWaterAlarmUper().intValue() / 100));
/*     */           
/* 256 */           if (waterBase.doubleValue() > 0.0D && wateCurrent24Hour.doubleValue() > 0.0D)
/*     */           {
/* 258 */             if (wateCurrent24Hour.doubleValue() > waterUp.doubleValue()) {
/* 259 */               waterAlarmMeesage = "告警:用水量過高";
/*     */             }
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 268 */           Integer powerBase = getPowerBaseValue(month, config);
/* 269 */           String powerAlarmMeesage = "";
/*     */ 
/*     */           
/* 272 */           Double powerLastHourUp = Double.valueOf(powerCurrentLastHour.doubleValue() * (1 + config.getPowerAlarmUper().intValue() / 100));
/*     */           
/* 274 */           if (powerStatusMap.get(config.getId()) != null && powerCurrentLastHour != null && powerCurrentLastHour
/*     */             
/* 276 */             .doubleValue() > 0.0D && 
/* 277 */             powerCurrentLastHour.doubleValue() > powerLastHourUp.doubleValue()) {
/* 278 */             powerAlarmMeesage = "告警A:用電量過高";
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 285 */           Double powerUp = Double.valueOf((powerBase.intValue() * (1 + config.getPowerAlarmUper().intValue() / 100)));
/*     */           
/* 287 */           if (powerBase.intValue() > 0 && powerCurrent24Hour != null && powerCurrent24Hour.doubleValue() > 0.0D)
/*     */           {
/* 289 */             if (powerCurrent24Hour.doubleValue() > powerUp.doubleValue()) {
/* 290 */               powerAlarmMeesage = "告警C:用電量過高";
/*     */             }
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 299 */           WaterPowerBaseHourLogData record = new WaterPowerBaseHourLogData();
/* 300 */           record.setId(UUID.randomUUID().toString());
/* 301 */           record.setRoomId(config.getId());
/* 302 */           record.setDataTime(new Date());
/* 303 */           record.setHourWater(wateCurrentLastHour);
/* 304 */           record.setHourPower(powerCurrentLastHour);
/* 305 */           record.setHourAccumulationWater(Double.valueOf(this.cumulateValue_D));
/* 306 */           record.setHourAccumulationPower(powerValue);
/* 307 */           saveRecords.add(record);
/*     */ 
/*     */           
/* 310 */           WaterStatusData water = new WaterStatusData();
/* 311 */           water.setId(config.getId());
/* 312 */           water.setDataTime(new Date());
/* 313 */           water.setCumulateValue(Double.valueOf(this.cumulateValue_D));
/* 314 */           water.setInstantaneousValue(Double.valueOf(this.instantaneousValue_D));
/*     */           
/* 316 */           water.setWaterAlarm(waterAlarmMeesage);
/* 317 */           water.setWaterLastHour(wateCurrentLastHour);
/* 318 */           water.setWater24Hour(wateCurrent24Hour);
/* 319 */           waterStatusMap.put(config.getId(), water);
/*     */ 
/*     */           
/* 322 */           PowerStatusData power = new PowerStatusData();
/* 323 */           power.setId(config.getId());
/* 324 */           power.setRv(Double.valueOf(this.va_D));
/* 325 */           power.setSv(Double.valueOf(this.vb_D));
/* 326 */           power.setTv(Double.valueOf(this.vc_D));
/* 327 */           power.setAv(Double.valueOf(this.avgv_D));
/* 328 */           power.setRi(Double.valueOf(this.ia_D));
/* 329 */           power.setSi(Double.valueOf(this.ib_D));
/* 330 */           power.setTi(Double.valueOf(this.ic_D));
/* 331 */           power.setAi(Double.valueOf(this.avgi_D));
/* 332 */           power.setKw(Double.valueOf(this.kw_D));
/* 333 */           power.setPf(Double.valueOf(this.pf_D));
/* 334 */           power.setKwh(powerValue);
/*     */           
/* 336 */           power.setPowerAlarm(powerAlarmMeesage);
/* 337 */           power.setPowerLastHour(powerCurrentLastHour);
/* 338 */           power.setPower24Hour(powerCurrent24Hour);
/* 339 */           power.setDataTime(new Date());
/* 340 */           powerStatusMap.put(config.getId(), power);
/*     */         }
/* 342 */         catch (Exception e) {
/* 343 */           logger.error("Get Modbus Value failed , deviceName = '{}'", tcConfig.getDeviceName());
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 348 */     if (saveRecords.size() > 0) {
/* 349 */       this.waterPowerBaseHourLogDataRepository.saveAll(saveRecords);
/*     */     }
/*     */   }
/*     */   
/*     */   private void createModbusMaster(DeviceConfig deviceconfig) throws UnknownHostException {
/* 354 */     TcpParameters tcpParameters = new TcpParameters();
/* 355 */     tcpParameters.setHost(InetAddress.getByName(deviceconfig.getIp()));
/* 356 */     tcpParameters.setKeepAlive(true);
/* 357 */     tcpParameters.setPort(deviceconfig.getPort().intValue());
/* 358 */     this.modbus = ModbusMasterFactory.createModbusMasterTCP(tcpParameters);
/*     */   }
/*     */   
/*     */   private double waterPowerValue(int address) {
/* 362 */     double resultValue = this.intData[address - va.intValue()] * VALUE_FORMMAT.doubleValue();
/* 363 */     if (address == pf.intValue()) {
/* 364 */       resultValue *= 0.1D;
/*     */     }
/* 366 */     return resultValue;
/*     */   }
/*     */   
/*     */   private static byte[] int2byte(int res) {
/* 370 */     byte[] targets = new byte[4];
/* 371 */     targets[3] = (byte)(res & 0xFF);
/* 372 */     targets[2] = (byte)(res >> 8 & 0xFF);
/* 373 */     targets[1] = (byte)(res >> 16 & 0xFF);
/* 374 */     targets[0] = (byte)(res >>> 24);
/* 375 */     return targets;
/*     */   }
/*     */   
/*     */   private static boolean isLittleEndian() {
/* 379 */     return (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN);
/*     */   }
/*     */   
/*     */   private static int toInt(byte[] bytes) {
/* 383 */     if (isLittleEndian()) {
/* 384 */       return 0xFF & bytes[0] | 0xFF00 & bytes[1] << 8 | 0xFF0000 & bytes[2] << 16 | 0xFF000000 & bytes[3] << 24;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 390 */     return 0xFF & bytes[3] | 0xFF00 & bytes[2] << 8 | 0xFF0000 & bytes[1] << 16 | 0xFF000000 & bytes[0] << 24;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] copyFrom(byte[] src, int off, int len) {
/* 398 */     byte[] copy = new byte[len];
/* 399 */     System.arraycopy(src, off, copy, 0, Math.min(src.length - off, len));
/* 400 */     return copy;
/*     */   }
/*     */   
/*     */   private Double getWaterBaseValue(int month, WaterPowerBaseConfig config) {
/* 404 */     switch (month) {
/*     */       case 1:
/* 406 */         return config.getWater1base();
/*     */       case 2:
/* 408 */         return config.getWater2base();
/*     */       case 3:
/* 410 */         return config.getWater3base();
/*     */       case 4:
/* 412 */         return config.getWater4base();
/*     */       case 5:
/* 414 */         return config.getWater5base();
/*     */       case 6:
/* 416 */         return config.getWater6base();
/*     */       case 7:
/* 418 */         return config.getWater7base();
/*     */       case 8:
/* 420 */         return config.getWater8base();
/*     */       case 9:
/* 422 */         return config.getWater9base();
/*     */       case 10:
/* 424 */         return config.getWater10base();
/*     */       case 11:
/* 426 */         return config.getWater11base();
/*     */       case 12:
/* 428 */         return config.getWater12base();
/*     */     } 
/* 430 */     return Double.valueOf(0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   private Integer getPowerBaseValue(int month, WaterPowerBaseConfig config) {
/* 435 */     switch (month) {
/*     */       case 1:
/* 437 */         return config.getPower1base();
/*     */       case 2:
/* 439 */         return config.getPower2base();
/*     */       case 3:
/* 441 */         return config.getPower3base();
/*     */       case 4:
/* 443 */         return config.getPower4base();
/*     */       case 5:
/* 445 */         return config.getPower5base();
/*     */       case 6:
/* 447 */         return config.getPower6base();
/*     */       case 7:
/* 449 */         return config.getPower7base();
/*     */       case 8:
/* 451 */         return config.getPower8base();
/*     */       case 9:
/* 453 */         return config.getPower9base();
/*     */       case 10:
/* 455 */         return config.getPower10base();
/*     */       case 11:
/* 457 */         return config.getPower11base();
/*     */       case 12:
/* 459 */         return config.getPower12base();
/*     */     } 
/* 461 */     return Integer.valueOf(0);
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\RoomDeviceWaterPowerServcie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */