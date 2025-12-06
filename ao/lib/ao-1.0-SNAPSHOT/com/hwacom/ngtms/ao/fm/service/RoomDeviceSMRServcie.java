/*     */ package com.hwacom.ngtms.ao.fm.service;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.ao.shared.dto.SMRDTO;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.service.OpLogger;
/*     */ import com.hwacom.ngtms.common.fm.model.DeviceConfig;
/*     */ import com.hwacom.ngtms.room.shared.RtuConfig;
/*     */ import com.hwacom.ngtms.rtu.fm.hz.RtuHzMap;
/*     */ import com.hwacom.ngtms.rtu.fm.model.ModbusPinMapping;
/*     */ import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
/*     */ import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
/*     */ import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service
/*     */ public class RoomDeviceSMRServcie
/*     */ {
/*  33 */   private static final Logger logger = LoggerFactory.getLogger(RoomDeviceSMRServcie.class); @Autowired
/*     */   OpLogger opLogger;
/*  35 */   private static final String[] maiorAlarmItemNames = new String[] { "輸出低電壓告警", "輸出過電壓停機告警", "低電壓隔離開關動作顯示", "過高溫或通風扇故障告警", "熔絲熔斷告警", "交流輸入中斷告警" };
/*     */   
/*  37 */   private static final String[] minorAlarmItemNames = new String[] { "系統輸出電壓值", "系統輸出電流值" };
/*     */   
/*  39 */   private Map<String, SMRDTO> itemNameMap = null;
/*     */   
/*  41 */   private byte[] data = null;
/*     */   
/*  43 */   private ModbusMaster modbus = null;
/*     */   
/*  45 */   private Gson gson = new Gson();
/*     */   
/*     */   public void process() {
/*  48 */     IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  49 */     IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/*  50 */     IMap<Long, ModbusPinMapping> pingMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusPinMapping);
/*     */     
/*  52 */     EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*  53 */     PredicateBuilder pb = eo.get("deviceType").equal("SMR").and((Predicate)eo.get("enable").equal(Boolean.valueOf(true)));
/*  54 */     for (DeviceTcConfig tcConfig : deviceConfigMap.values((Predicate)pb)) {
/*  55 */       String deviceName = tcConfig.getDeviceName();
/*  56 */       this.modbus = null;
/*  57 */       if (this.modbus == null) {
/*     */         try {
/*  59 */           createModbusMaster((DeviceConfig)tcConfig);
/*  60 */         } catch (UnknownHostException e) {
/*  61 */           logger.error("Create Modbus Master failed , deviceName = '{}'", deviceName);
/*     */         } 
/*     */       }
/*  64 */       if (this.modbus.isConnected()) {
/*  65 */         this.itemNameMap = new HashMap<>();
/*  66 */         this.itemNameMap = putMainAlarmData(deviceName, pingMap, deviceConfigMap);
/*  67 */         int registerLength = 40;
/*  68 */         this.data = new byte[registerLength * 2];
/*     */         try {
/*  70 */           boolean[] booleanArray = this.modbus.readDiscreteInputs(1, 0, 15);
/*     */           
/*  72 */           byte[] tempByte = new byte[2];
/*  73 */           for (int i = 0; i < booleanArray.length; i++) {
/*  74 */             tempByte[0] = 0;
/*  75 */             tempByte[1] = (byte)(booleanArray[i] ? 1 : 0);
/*  76 */             int destinationIndex = 38 + i * 2;
/*  77 */             if (destinationIndex + tempByte.length < this.data.length)
/*     */             {
/*     */               
/*  80 */               System.arraycopy(tempByte, 0, this.data, destinationIndex, tempByte.length); } 
/*     */           } 
/*  82 */           byte[] temp = new byte[2];
/*     */           
/*  84 */           int[] intValue1 = this.modbus.readInputRegisters(1, 113, 1);
/*  85 */           temp[0] = (new Integer(intValue1[0] / 256)).byteValue();
/*  86 */           temp[1] = (new Integer(intValue1[0] % 256)).byteValue();
/*  87 */           System.arraycopy(temp, 0, this.data, 4, temp.length);
/*     */           
/*  89 */           int[] intValue2 = this.modbus.readInputRegisters(1, 114, 1);
/*  90 */           temp[0] = (new Integer(intValue2[0] / 256)).byteValue();
/*  91 */           temp[1] = (new Integer(intValue2[0] % 256)).byteValue();
/*  92 */           System.arraycopy(temp, 0, this.data, 6, temp.length);
/*     */ 
/*     */           
/*  95 */           if (IsMaiorAlarm(this.itemNameMap, maiorAlarmItemNames).booleanValue()) {
/*  96 */             temp[0] = 1;
/*  97 */             temp[1] = 0;
/*     */           } else {
/*  99 */             temp[0] = 0;
/* 100 */             temp[1] = 0;
/*     */           } 
/* 102 */           System.arraycopy(temp, 0, this.data, 38, temp.length);
/*     */           
/* 104 */           if (IsMinorAlarm(this.itemNameMap, minorAlarmItemNames).booleanValue()) {
/* 105 */             temp[0] = 1;
/* 106 */             temp[1] = 0;
/*     */           } else {
/* 108 */             temp[0] = 0;
/* 109 */             temp[1] = 0;
/*     */           } 
/* 111 */           System.arraycopy(temp, 0, this.data, 40, temp.length);
/* 112 */           this.modbus.disconnect();
/* 113 */           Thread.sleep(1000L);
/*     */           
/* 115 */           EntryObject aso = (new PredicateBuilder()).getEntryObject();
/* 116 */           PredicateBuilder pbas = aso.get("deviceName").equal(deviceName);
/* 117 */           for (ModbusPinMapping pingMapValue : pingMap.values((Predicate)pbas)) {
/*     */             try {
/* 119 */               int address = pingMapValue.getAddress().intValue() - 40000;
/* 120 */               int value1 = this.data[address * 2];
/* 121 */               if (value1 < 0) {
/* 122 */                 value1 = 0xFF & this.data[address * 2];
/*     */               }
/* 124 */               int value2 = this.data[address * 2 + 1];
/* 125 */               if (value2 < 0) {
/* 126 */                 value2 = 0xFF & this.data[address * 2 + 1];
/*     */               }
/* 128 */               int reslutValue = value1 * 256 + value2;
/* 129 */               dataMap.put(pingMapValue.getKeyName(), Integer.valueOf(Math.abs(reslutValue)));
/* 130 */             } catch (Exception e) {
/* 131 */               logger.error("Plug SMR Value Into dataMap failed , deviceName = '{}'", deviceName);
/*     */             } 
/*     */           } 
/* 134 */         } catch (Exception e) {
/* 135 */           logger.error("Get Modbus Value failed , deviceName = '{}'", deviceName);
/*     */         }  continue;
/*     */       } 
/* 138 */       logger.error("Modbus disConnect, deviceName = '{}'", deviceName);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void createModbusMaster(DeviceConfig deviceconfig) throws UnknownHostException {
/* 144 */     TcpParameters tcpParameters = new TcpParameters();
/* 145 */     tcpParameters.setHost(InetAddress.getByName(deviceconfig.getIp()));
/* 146 */     tcpParameters.setKeepAlive(true);
/* 147 */     tcpParameters.setPort(deviceconfig.getPort().intValue());
/* 148 */     this.modbus = ModbusMasterFactory.createModbusMasterTCP(tcpParameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<String, SMRDTO> putMainAlarmData(String deviceName, IMap<Long, ModbusPinMapping> pingMap, IMap<String, DeviceTcConfig> deviceConfigMap) {
/* 155 */     Map<String, SMRDTO> result = new HashMap<>();
/* 156 */     EntryObject aso = (new PredicateBuilder()).getEntryObject();
/* 157 */     PredicateBuilder pbas = aso.get("deviceName").equal(deviceName);
/* 158 */     for (ModbusPinMapping pingMapValue : pingMap.values((Predicate)pbas)) {
/*     */       try {
/* 160 */         DeviceTcConfig tcConfig = (DeviceTcConfig)deviceConfigMap.get(pingMapValue.getKeyName());
/* 161 */         if (tcConfig != null) {
/* 162 */           SMRDTO dto = new SMRDTO();
/* 163 */           dto.setAddress(pingMapValue.getAddress());
/* 164 */           String extend = tcConfig.getExtend();
/* 165 */           if (extend != null && this.gson.fromJson(extend, RtuConfig.class) != null) {
/* 166 */             RtuConfig rtuConfig = (RtuConfig)this.gson.fromJson(extend, RtuConfig.class);
/* 167 */             dto.setUpperLimit(rtuConfig.getUpperLimit());
/* 168 */             dto.setLowerLimit(rtuConfig.getLowerLimit());
/*     */           } 
/* 170 */           result.put(tcConfig.getDisplayName(), dto);
/*     */         } 
/* 172 */       } catch (Exception e) {
/* 173 */         logger.error("Put ItemName failed , pingMapValue = '{}'", pingMapValue.getKeyName());
/*     */       } 
/*     */     } 
/* 176 */     return result;
/*     */   }
/*     */   
/*     */   private Boolean IsMaiorAlarm(Map<String, SMRDTO> itemNameMap, String[] itemNames) {
/* 180 */     for (String itemName : itemNames) {
/* 181 */       if (itemNameMap.containsKey(itemName)) {
/* 182 */         int address = ((SMRDTO)itemNameMap.get(itemName)).getAddress().intValue() - 40000;
/* 183 */         int resultValue = this.data[address * 2] * 256 + this.data[address * 2 + 1];
/* 184 */         if (resultValue != 0) {
/* 185 */           return Boolean.valueOf(true);
/*     */         }
/*     */       } 
/*     */     } 
/* 189 */     return Boolean.valueOf(false);
/*     */   }
/*     */   
/*     */   private Boolean IsMinorAlarm(Map<String, SMRDTO> itemNameMap, String[] itemNames) {
/* 193 */     for (String itemName : itemNames) {
/* 194 */       if (itemNameMap.containsKey(itemName)) {
/* 195 */         SMRDTO dto = itemNameMap.get(itemName);
/* 196 */         int address = dto.getAddress().intValue() - 40000;
/* 197 */         int resultValue = this.data[address * 2] * 256 + this.data[address * 2 + 1];
/*     */         
/* 199 */         if (resultValue > dto.getUpperLimit().intValue() || resultValue < dto.getLowerLimit().intValue()) {
/* 200 */           return Boolean.valueOf(true);
/*     */         }
/*     */       } 
/*     */     } 
/* 204 */     return Boolean.valueOf(false);
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\RoomDeviceSMRServcie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */