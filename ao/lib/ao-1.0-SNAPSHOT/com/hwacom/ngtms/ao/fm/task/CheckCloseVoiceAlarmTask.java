/*     */ package com.hwacom.ngtms.ao.fm.task;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.ao.fm.hz.AoHzMap;
/*     */ import com.hwacom.ngtms.ao.shared.dto.NcuVoiceDTO;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*     */ import com.hwacom.ngtms.common.fm.model.DeviceConfig;
/*     */ import com.hwacom.ngtms.hcce.fme.controller.fm.JobBase;
/*     */ import com.hwacom.ngtms.rtu.fm.hz.RtuHzMap;
/*     */ import com.hwacom.ngtms.rtu.fm.model.ModbusDeviceConfig;
/*     */ import com.hwacom.ngtms.rtu.fm.model.ModbusPinMapping;
/*     */ import com.hwacom.ngtms.rtu.fm.model.ModbusReadConfig;
/*     */ import com.hwacom.ngtms.rtu.shared.ModbusDataType;
/*     */ import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
/*     */ import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
/*     */ import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
/*     */ import java.math.BigInteger;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import org.quartz.DisallowConcurrentExecution;
/*     */ import org.quartz.JobDataMap;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @DisallowConcurrentExecution
/*     */ public class CheckCloseVoiceAlarmTask
/*     */   extends JobBase
/*     */ {
/*  39 */   private static final Logger logger = LoggerFactory.getLogger(CheckCloseVoiceAlarmTask.class);
/*     */ 
/*     */   
/*     */   protected void init(JobDataMap jobDataMap) {}
/*     */ 
/*     */   
/*     */   protected void process() {
/*  46 */     long startTime = System.currentTimeMillis();
/*  47 */     logger.debug("Start CheckCloseVoiceAlarmTask, {}", Long.valueOf(startTime));
/*  48 */     IMap<String, NcuVoiceDTO> voiceAutoCloseMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.NcuVoiceAutoCloseData);
/*  49 */     IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/*     */     try {
/*  51 */       if (voiceAutoCloseMap.size() > 0) {
/*  52 */         for (NcuVoiceDTO dto : voiceAutoCloseMap.values()) {
/*  53 */           if (dataMap.get(dto.getId()) != null && ((Integer)dataMap.get(dto.getId())).intValue() == 0) {
/*  54 */             voiceAutoCloseMap.remove(dto.getId());
/*     */             
/*     */             continue;
/*     */           } 
/*  58 */           if (voiceAutoCloseMap.get(dto.getId()) != null && ((Integer)dataMap.get(dto.getId())).intValue() == 1) {
/*  59 */             Date from = new Date();
/*     */             
/*  61 */             if (dto.getDateTime().before(from)) {
/*  62 */               IMap<String, ModbusDeviceConfig> cfgMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusDeviceConfig);
/*  63 */               IMap<String, DeviceConfig> devCfgMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.DeviceConfig);
/*  64 */               IMap<Long, ModbusReadConfig> rMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusReadConfig);
/*  65 */               IMap<Long, ModbusPinMapping> pMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusPinMapping);
/*     */ 
/*     */               
/*  68 */               PredicateBuilder pinPb = (new PredicateBuilder()).getEntryObject().get("keyName").equal(dto.getId());
/*  69 */               ModbusPinMapping pin = null;
/*  70 */               Iterator<ModbusPinMapping> iterator = pMap.values((Predicate)pinPb).iterator(); if (iterator.hasNext()) { ModbusPinMapping p = iterator.next();
/*  71 */                 pin = p; }
/*     */ 
/*     */               
/*  74 */               if (pin == null) {
/*  75 */                 logger.warn("Can not find ModbusPinMapping keyName='{}'", dto.getId());
/*     */               }
/*     */               
/*  78 */               ModbusDeviceConfig cfg = (ModbusDeviceConfig)cfgMap.get(pin.getDeviceName());
/*  79 */               DeviceConfig devCfg = (DeviceConfig)devCfgMap.get(cfg.getDeviceName());
/*  80 */               if (devCfg == null) {
/*  81 */                 logger.warn("{} : device config is null!", cfg.getDeviceName());
/*     */               }
/*  83 */               EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*     */ 
/*     */ 
/*     */               
/*  87 */               PredicateBuilder pb = eo.get("pinGroupId").equal(cfg.getPinGroupId()).and((Predicate)eo.get("dataType").equal((Comparable)ModbusDataType.HOLDING_REGISTER_DO));
/*     */               
/*  89 */               BigInteger data = getDoValue(cfg.getDeviceName(), pin.getAddress());
/*  90 */               if (data == null) {
/*  91 */                 data = BigInteger.valueOf(0L);
/*     */               }
/*  93 */               for (ModbusReadConfig readCfg : rMap.values((Predicate)pb)) {
/*  94 */                 if (readCfg.getStartAddress().intValue() + 40001 != pin.getAddress().intValue()) {
/*  95 */                   logger.debug("keyName:'{}', address:'{}'", pin.getKeyName(), pin.getAddress());
/*     */                   continue;
/*     */                 } 
/*  98 */                 ModbusMaster modbus = createModbusMaster(devCfg);
/*  99 */                 if (data.testBit(pin.getBitNumber().intValue()))
/*     */                 {
/* 101 */                   data = data.clearBit(pin.getBitNumber().intValue());
/*     */                 }
/* 103 */                 logger.debug("writeSingleRegister, slaveId='{}', startAddress='{}', register='{}'", new Object[] { cfg
/*     */                       
/* 105 */                       .getSlaveId(), readCfg
/* 106 */                       .getStartAddress(), data });
/*     */                 
/* 108 */                 modbus.writeSingleRegister(cfg
/* 109 */                     .getSlaveId().intValue(), readCfg.getStartAddress().intValue(), data.intValue());
/* 110 */                 if (modbus != null && modbus.isConnected()) {
/* 111 */                   modbus.disconnect();
/*     */                 }
/*     */               }
/*     */             
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/* 119 */     } catch (Exception e) {
/* 120 */       logger.debug("CheckCloseVoiceAlarmTask failed.");
/*     */     } 
/* 122 */     logger.debug("CheckCloseVoiceAlarmTask end, use TimeMillis '{}'", 
/*     */         
/* 124 */         Long.valueOf(System.currentTimeMillis() - startTime));
/*     */   }
/*     */   
/*     */   private BigInteger getDoValue(String deviceName, Integer address) {
/* 128 */     IMap<Long, ModbusPinMapping> pMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusPinMapping);
/* 129 */     IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/* 130 */     BigInteger data = BigInteger.valueOf(0L);
/* 131 */     EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*     */     
/* 133 */     PredicateBuilder pb = eo.get("deviceName").equal(deviceName).and((Predicate)eo.get("address").equal(address));
/* 134 */     for (ModbusPinMapping pin : pMap.values((Predicate)pb)) {
/* 135 */       Integer value = (Integer)dataMap.get(pin.getKeyName());
/* 136 */       if (value != null) {
/* 137 */         if (value.intValue() == 1) {
/* 138 */           data = data.setBit(pin.getBitNumber().intValue()); continue;
/*     */         } 
/* 140 */         data = data.clearBit(pin.getBitNumber().intValue());
/*     */       } 
/*     */     } 
/*     */     
/* 144 */     return data;
/*     */   }
/*     */   
/*     */   private ModbusMaster createModbusMaster(DeviceConfig devCfg) throws UnknownHostException {
/* 148 */     TcpParameters tcpParameters = new TcpParameters();
/* 149 */     tcpParameters.setHost(InetAddress.getByName(devCfg.getIp()));
/* 150 */     tcpParameters.setKeepAlive(true);
/* 151 */     tcpParameters.setPort(devCfg.getPort().intValue());
/* 152 */     ModbusMaster modbus = ModbusMasterFactory.createModbusMasterTCP(tcpParameters);
/* 153 */     return modbus;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\task\CheckCloseVoiceAlarmTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */