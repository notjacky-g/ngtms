/*     */ package com.hwacom.ngtms.rtu.service;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.base.hazelcast.HazelcastClient;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.common.fm.model.DeviceConfig;
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
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ public class ModbusDevice implements Runnable {
/*  26 */   private static final Logger logger = LoggerFactory.getLogger(ModbusDevice.class);
/*     */ 
/*     */   
/*     */   private Map<Integer, ModbusPinMapping> pinMap;
/*     */ 
/*     */   
/*     */   private List<ModbusReadConfig> readCfgList;
/*     */ 
/*     */   
/*     */   private ModbusDeviceConfig modbusCfg;
/*     */ 
/*     */   
/*     */   private ModbusMaster modbus;
/*     */   
/*     */   private DeviceConfig devCfg;
/*     */   
/*     */   private HazelcastClient hzClient;
/*     */   
/*     */   private boolean closed = false;
/*     */ 
/*     */   
/*     */   public ModbusDevice(ModbusDeviceConfig cfg, DeviceConfig devCfg, Map<Integer, ModbusPinMapping> pinMap, List<ModbusReadConfig> readConfigList, HazelcastClient hzClient) {
/*  48 */     this.modbusCfg = cfg;
/*  49 */     this.readCfgList = readConfigList;
/*  50 */     this.pinMap = pinMap;
/*  51 */     this.devCfg = devCfg;
/*  52 */     this.hzClient = hzClient;
/*     */   }
/*     */   
/*     */   public void poll() throws UnknownHostException {
/*  56 */     if (this.closed)
/*  57 */       return;  if (this.modbus == null) createModbusMaster(); 
/*  58 */     IMap<String, Boolean> connectMap = this.hzClient.getIMap((HzDistObjEnum)RtuHzMap.ModbusConnectStatus);
/*  59 */     Boolean connected = (Boolean)connectMap.get(this.devCfg.getDeviceName());
/*  60 */     if (!this.modbus.isConnected()) {
/*     */       try {
/*  62 */         this.modbus.connect();
/*  63 */       } catch (Exception ex) {
/*  64 */         logger.trace(this.devCfg.getDeviceName() + " connect fail!", ex);
/*  65 */         if (connected == null || connected.booleanValue()) {
/*  66 */           connectMap.set(this.devCfg.getDeviceName(), Boolean.valueOf(false));
/*  67 */           setObjFieldNegative();
/*     */         } 
/*     */         return;
/*     */       } 
/*     */     }
/*  72 */     if (connected == null || !connected.booleanValue()) connectMap.set(this.devCfg.getDeviceName(), Boolean.valueOf(true)); 
/*  73 */     for (int i = 0; i < this.readCfgList.size(); i++) {
/*     */       
/*     */       try {
/*  76 */         ModbusReadConfig readCfg = this.readCfgList.get(i);
/*  77 */         readData(this.modbusCfg.getSlaveId().intValue(), readCfg);
/*     */       }
/*  79 */       catch (IndexOutOfBoundsException ex) {
/*     */         break;
/*  81 */       } catch (Exception ex) {
/*  82 */         logger.warn("read modbus data have exception!, deviceName : " + this.devCfg.getDeviceName(), ex);
/*     */       } 
/*     */     } 
/*     */   } private void readData(int slaveId, ModbusReadConfig readCfg) throws Exception {
/*     */     boolean[] coilData;
/*     */     int i;
/*     */     boolean[] diData;
/*  89 */     int k, inputData[], holdData[], holdDoData[], result[] = null;
/*  90 */     int address = readCfg.getStartAddress().intValue();
/*  91 */     switch (readCfg.getDataType()) {
/*     */       case COIL:
/*  93 */         coilData = this.modbus.readCoils(slaveId, address, readCfg.getLength().intValue());
/*  94 */         result = new int[coilData.length];
/*  95 */         for (i = 0; i < coilData.length; i++) {
/*  96 */           if (coilData[i]) { result[i] = 1; }
/*  97 */           else { result[i] = 0; }
/*     */         
/*  99 */         }  address++;
/*     */         break;
/*     */       case DISCRETE_INPUT:
/* 102 */         diData = this.modbus.readDiscreteInputs(slaveId, address, readCfg.getLength().intValue());
/* 103 */         result = new int[diData.length];
/*     */         
/* 105 */         for (k = 0; k < diData.length; k++) {
/* 106 */           if (diData[k]) { result[k] = 1; }
/* 107 */           else { result[k] = 0; }
/*     */         
/* 109 */         }  address += 10001;
/*     */         break;
/*     */       case INPUT_REGISTER:
/* 112 */         inputData = this.modbus.readInputRegisters(slaveId, address, readCfg.getLength().intValue());
/* 113 */         result = inputData;
/*     */         
/* 115 */         address += 30001;
/*     */         break;
/*     */       case HOLDING_REGISTER:
/* 118 */         holdData = this.modbus.readHoldingRegisters(slaveId, address, readCfg.getLength().intValue());
/* 119 */         result = holdData;
/* 120 */         address += 40001;
/*     */         break;
/*     */       case HOLDING_REGISTER_DO:
/* 123 */         holdDoData = this.modbus.readHoldingRegisters(slaveId, address, readCfg.getLength().intValue());
/* 124 */         result = holdDoData;
/* 125 */         address += 40001;
/*     */         break;
/*     */     } 
/* 128 */     IMap<String, Integer> dataMap = this.hzClient.getIMap((HzDistObjEnum)RtuHzMap.ModbusData);
/* 129 */     IMap<Integer, ModbusPinMapping> mappingMap = this.hzClient.getIMap((HzDistObjEnum)RtuHzMap.ModbusPinMapping);
/*     */     
/* 131 */     for (int j = 0; j < result.length; j++) {
/* 132 */       if (ModbusDataType.HOLDING_REGISTER_DO == readCfg.getDataType()) {
/* 133 */         int value = result[j];
/* 134 */         BigInteger bInt = BigInteger.valueOf(value);
/* 135 */         EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*     */ 
/*     */ 
/*     */         
/* 139 */         PredicateBuilder pb = eo.get("deviceName").equal(this.modbusCfg.getDeviceName()).and((Predicate)eo.get("address").equal(Integer.valueOf(address)));
/* 140 */         for (ModbusPinMapping pin : mappingMap.values((Predicate)pb)) {
/* 141 */           dataMap.set(pin.getKeyName(), Integer.valueOf(bInt.testBit(pin.getBitNumber().intValue()) ? 1 : 0));
/*     */         }
/*     */       } else {
/* 144 */         ModbusPinMapping pin = this.pinMap.get(Integer.valueOf(address + j));
/* 145 */         if (pin != null) {
/* 146 */           int value = result[j];
/* 147 */           if (pin.getInversion().booleanValue())
/* 148 */             if (value > 0) { value = 0; }
/* 149 */             else { value = 1; }
/*     */              
/* 151 */           Integer oldValue = (Integer)dataMap.get(pin.getKeyName());
/* 152 */           if (oldValue == null || oldValue.intValue() != value)
/* 153 */             dataMap.set(pin.getKeyName(), Integer.valueOf(value)); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setObjFieldNegative() {
/* 160 */     IMap<String, Integer> dataMap = this.hzClient.getIMap((HzDistObjEnum)RtuHzMap.ModbusData);
/* 161 */     synchronized (this.pinMap) {
/* 162 */       for (ModbusPinMapping pin : this.pinMap.values()) {
/*     */         try {
/* 164 */           dataMap.set(pin.getKeyName(), Integer.valueOf(-1));
/* 165 */         } catch (Exception ex) {
/* 166 */           logger.warn("set object field fail! deviceName:" + this.devCfg
/*     */               
/* 168 */               .getDeviceName() + " pinId:" + pin
/*     */               
/* 170 */               .getId(), ex);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void createModbusMaster() throws UnknownHostException {
/* 178 */     TcpParameters tcpParameters = new TcpParameters();
/* 179 */     tcpParameters.setHost(InetAddress.getByName(this.devCfg.getIp()));
/* 180 */     tcpParameters.setKeepAlive(true);
/* 181 */     tcpParameters.setPort(this.devCfg.getPort().intValue());
/* 182 */     this.modbus = ModbusMasterFactory.createModbusMasterTCP(tcpParameters);
/*     */   }
/*     */   
/*     */   public void close() {
/* 186 */     this.closed = true;
/* 187 */     if (this.modbus != null && this.modbus.isConnected()) {
/*     */       try {
/* 189 */         this.modbus.disconnect();
/* 190 */       } catch (Exception ex) {
/* 191 */         logger.warn("modbus disconnect have exception! device name : " + this.devCfg
/* 192 */             .getDeviceName(), ex);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/* 200 */       poll();
/* 201 */     } catch (Exception ex) {
/* 202 */       logger.warn(this.devCfg.getDeviceName() + " poll have exception!", ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\am-rtu-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\rtu\service\ModbusDevice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */