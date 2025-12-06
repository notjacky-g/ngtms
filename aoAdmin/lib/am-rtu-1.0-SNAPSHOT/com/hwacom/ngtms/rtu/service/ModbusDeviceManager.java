/*     */ package com.hwacom.ngtms.rtu.service;
/*     */ import com.hazelcast.core.EntryEvent;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.map.listener.EntryAddedListener;
/*     */ import com.hazelcast.map.listener.EntryRemovedListener;
/*     */ import com.hazelcast.map.listener.EntryUpdatedListener;
/*     */ import com.hazelcast.map.listener.MapListener;
/*     */ import com.hwacom.ngtms.base.hazelcast.HazelcastClient;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*     */ import com.hwacom.ngtms.common.fm.model.DeviceConfig;
/*     */ import com.hwacom.ngtms.rtu.fm.hz.RtuHzMap;
/*     */ import com.hwacom.ngtms.rtu.fm.model.ModbusDeviceConfig;
/*     */ import com.hwacom.ngtms.rtu.fm.model.ModbusPinMapping;
/*     */ import com.hwacom.ngtms.rtu.fm.model.ModbusReadConfig;
/*     */ import com.intelligt.modbus.jlibmodbus.Modbus;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.annotation.PreDestroy;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service
/*     */ public class ModbusDeviceManager {
/*  35 */   private static Logger logger = LoggerFactory.getLogger(ModbusDeviceManager.class);
/*     */   
/*  37 */   private ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
/*     */   
/*     */   @Autowired
/*     */   private HazelcastClient hzClient;
/*  41 */   private Map<String, Map<Integer, ModbusPinMapping>> pinMap = new HashMap<>();
/*     */   
/*  43 */   private Map<Integer, List<ModbusReadConfig>> readCfgMap = new HashMap<>();
/*     */   
/*  45 */   private Map<String, ModbusRuntimeData> modbusDataMap = new ConcurrentHashMap<>();
/*     */   
/*  47 */   private ScheduledFuture<?> initFuture = null;
/*     */   
/*     */   @PostConstruct
/*     */   public void init() {
/*  51 */     this
/*  52 */       .initFuture = this.executor.scheduleAtFixedRate(new Runnable()
/*     */         {
/*     */           
/*     */           public void run()
/*     */           {
/*  57 */             ModbusDeviceManager.logger.info("Start ModbusDeviceManager init.");
/*     */             
/*  59 */             IMap<String, ModbusDeviceConfig> cfgMap = ModbusDeviceManager.this.hzClient.getIMap((HzDistObjEnum)RtuHzMap.ModbusDeviceConfig);
/*  60 */             IMap<String, DeviceConfig> devCfgMap = ModbusDeviceManager.this.hzClient.getIMap((HzDistObjEnum)CommonHzMap.DeviceConfig);
/*  61 */             IMap<Long, ModbusReadConfig> rMap = ModbusDeviceManager.this.hzClient.getIMap((HzDistObjEnum)RtuHzMap.ModbusReadConfig);
/*  62 */             IMap<Long, ModbusPinMapping> pMap = ModbusDeviceManager.this.hzClient.getIMap((HzDistObjEnum)RtuHzMap.ModbusPinMapping);
/*  63 */             Modbus.setAutoIncrementTransactionId(true);
/*     */             
/*  65 */             for (ModbusReadConfig read : rMap.values()) {
/*  66 */               if (!ModbusDeviceManager.this.readCfgMap.containsKey(read.getPinGroupId()))
/*  67 */                 ModbusDeviceManager.this.readCfgMap.put(read.getPinGroupId(), new ArrayList()); 
/*  68 */               ((List<ModbusReadConfig>)ModbusDeviceManager.this.readCfgMap.get(read.getPinGroupId())).add(read);
/*     */             } 
/*     */             
/*  71 */             for (ModbusPinMapping pin : pMap.values()) {
/*  72 */               if (!ModbusDeviceManager.this.pinMap.containsKey(pin.getDeviceName()))
/*  73 */                 ModbusDeviceManager.this.pinMap.put(pin.getDeviceName(), new HashMap<>()); 
/*  74 */               ((Map<Integer, ModbusPinMapping>)ModbusDeviceManager.this.pinMap.get(pin.getDeviceName())).put(pin.getAddress(), pin);
/*     */             } 
/*     */             
/*  77 */             for (ModbusDeviceConfig cfg : cfgMap.values()) {
/*  78 */               DeviceConfig devCfg = (DeviceConfig)devCfgMap.get(cfg.getDeviceName());
/*  79 */               if (devCfg == null) {
/*  80 */                 ModbusDeviceManager.logger.warn("{} : device config is null!", cfg.getDeviceName());
/*     */                 continue;
/*     */               } 
/*  83 */               ModbusDeviceManager.this.createNewModbus(cfg, devCfg);
/*     */             } 
/*  85 */             rMap.addEntryListener((MapListener)new ModbusDeviceManager.ModbusReadConfigMapListener(), false);
/*  86 */             pMap.addEntryListener((MapListener)new ModbusDeviceManager.ModbusPinMappingMapListener(), true);
/*  87 */             cfgMap.addEntryListener((MapListener)new ModbusDeviceManager.ModbusDeviceConfigMapListener(), false);
/*  88 */             devCfgMap.addEntryListener((MapListener)new ModbusDeviceManager.DeviceConfigMapListener(), true);
/*  89 */             if (ModbusDeviceManager.this.initFuture != null) ModbusDeviceManager.this.initFuture.cancel(false); 
/*  90 */             ModbusDeviceManager.logger.info("End ModbusDeviceManager init.");
/*     */           }
/*     */         }30L, 30L, TimeUnit.SECONDS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createNewModbus(ModbusDeviceConfig cfg, DeviceConfig devCfg) {
/*  99 */     List<ModbusReadConfig> readCfgList = this.readCfgMap.get(cfg.getPinGroupId());
/* 100 */     Map<Integer, ModbusPinMapping> pinMappingMap = this.pinMap.get(cfg.getDeviceName());
/* 101 */     if (readCfgList == null) {
/* 102 */       logger.warn("{} not have read config!", cfg.getDeviceName());
/*     */       return;
/*     */     } 
/* 105 */     if (pinMappingMap == null) {
/* 106 */       logger.warn("{} not have pin mapping config!", cfg.getDeviceName());
/*     */       return;
/*     */     } 
/*     */     try {
/* 110 */       ModbusRuntimeData data = this.modbusDataMap.get(cfg.getDeviceName());
/* 111 */       if (data != null) {
/* 112 */         data.modbus.close();
/* 113 */         data.future.cancel(false);
/* 114 */         data.modbus = new ModbusDevice(cfg, devCfg, pinMappingMap, readCfgList, this.hzClient);
/* 115 */         data
/* 116 */           .future = this.executor.scheduleAtFixedRate(data.modbus, 60L, cfg
/* 117 */             .getPollIntervalSec().intValue(), TimeUnit.SECONDS);
/*     */       } else {
/* 119 */         ModbusDevice modbus = new ModbusDevice(cfg, devCfg, pinMappingMap, readCfgList, this.hzClient);
/*     */         
/* 121 */         ScheduledFuture<?> future = this.executor.scheduleAtFixedRate(modbus, 60L, cfg.getPollIntervalSec().intValue(), TimeUnit.SECONDS);
/* 122 */         data = new ModbusRuntimeData(cfg.getDeviceName(), modbus, future);
/* 123 */         this.modbusDataMap.put(cfg.getDeviceName(), data);
/*     */       } 
/* 125 */     } catch (Exception ex) {
/* 126 */       logger.warn(cfg.getDeviceName() + " modbus init have exception!", ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   @PreDestroy
/*     */   public void close() {
/* 132 */     for (ModbusRuntimeData data : this.modbusDataMap.values()) {
/*     */       try {
/* 134 */         data.modbus.close();
/* 135 */         data.future.cancel(false);
/* 136 */       } catch (Exception ex) {
/* 137 */         logger.warn(data.deviceName + " stop thread have exception!", ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public class ModbusRuntimeData { public String deviceName;
/*     */     
/*     */     public ModbusRuntimeData(String deviceName, ModbusDevice modbus, ScheduledFuture<?> future) {
/* 145 */       this.deviceName = deviceName;
/* 146 */       this.modbus = modbus;
/* 147 */       this.future = future;
/*     */     }
/*     */ 
/*     */     
/*     */     public ModbusDevice modbus;
/*     */     
/*     */     public ScheduledFuture<?> future; }
/*     */ 
/*     */ 
/*     */   
/*     */   public class DeviceConfigMapListener
/*     */     implements EntryUpdatedListener<String, DeviceConfig>
/*     */   {
/*     */     public void entryUpdated(EntryEvent<String, DeviceConfig> event) {
/* 161 */       if (!ModbusDeviceManager.this.modbusDataMap.containsKey(event.getKey()))
/* 162 */         return;  if (((DeviceConfig)event.getValue()).getIp() == null || ((DeviceConfig)event.getValue()).getPort() == null)
/* 163 */         return;  if (((DeviceConfig)event.getValue()).getIp().equals(((DeviceConfig)event.getOldValue()).getIp()) && ((DeviceConfig)event
/* 164 */         .getValue()).getPort().equals(((DeviceConfig)event.getOldValue()).getPort()))
/* 165 */         return;  IMap<String, ModbusDeviceConfig> cfgMap = ModbusDeviceManager.this.hzClient.getIMap((HzDistObjEnum)RtuHzMap.ModbusDeviceConfig);
/* 166 */       ModbusDeviceConfig cfg = (ModbusDeviceConfig)cfgMap.get(event.getKey());
/* 167 */       if (cfg == null)
/* 168 */         return;  ModbusDeviceManager.this.createNewModbus(cfg, (DeviceConfig)event.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class ModbusDeviceConfigMapListener
/*     */     implements EntryAddedListener<String, ModbusDeviceConfig>, EntryRemovedListener<String, ModbusDeviceConfig>, EntryUpdatedListener<String, ModbusDeviceConfig>
/*     */   {
/*     */     public void entryUpdated(EntryEvent<String, ModbusDeviceConfig> event) {
/* 179 */       ModbusDeviceManager.ModbusRuntimeData data = (ModbusDeviceManager.ModbusRuntimeData)ModbusDeviceManager.this.modbusDataMap.get(event.getKey());
/* 180 */       if (data == null)
/* 181 */         return;  IMap<String, DeviceConfig> devCfgMap = ModbusDeviceManager.this.hzClient.getIMap((HzDistObjEnum)CommonHzMap.DeviceConfig);
/* 182 */       DeviceConfig devCfg = (DeviceConfig)devCfgMap.get(event.getKey());
/* 183 */       if (devCfg == null)
/* 184 */         return;  ModbusDeviceManager.this.createNewModbus((ModbusDeviceConfig)event.getValue(), devCfg);
/*     */     }
/*     */ 
/*     */     
/*     */     public void entryRemoved(EntryEvent<String, ModbusDeviceConfig> event) {
/* 189 */       ModbusDeviceManager.ModbusRuntimeData data = (ModbusDeviceManager.ModbusRuntimeData)ModbusDeviceManager.this.modbusDataMap.remove(event.getKey());
/* 190 */       if (data == null)
/* 191 */         return;  data.modbus.close();
/* 192 */       data.future.cancel(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void entryAdded(EntryEvent<String, ModbusDeviceConfig> event) {
/* 197 */       IMap<String, DeviceConfig> devCfgMap = ModbusDeviceManager.this.hzClient.getIMap((HzDistObjEnum)CommonHzMap.DeviceConfig);
/* 198 */       DeviceConfig devCfg = (DeviceConfig)devCfgMap.get(event.getKey());
/* 199 */       if (devCfg == null)
/* 200 */         return;  ModbusDeviceManager.this.createNewModbus((ModbusDeviceConfig)event.getValue(), devCfg);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class ModbusReadConfigMapListener
/*     */     implements EntryAddedListener<Long, ModbusReadConfig>, EntryRemovedListener<Long, ModbusReadConfig>, EntryUpdatedListener<Long, ModbusReadConfig>
/*     */   {
/*     */     public void entryRemoved(EntryEvent<Long, ModbusReadConfig> event) {
/* 211 */       List<ModbusReadConfig> cfgList = (List<ModbusReadConfig>)ModbusDeviceManager.this.readCfgMap.get(((ModbusReadConfig)event.getValue()).getPinGroupId());
/* 212 */       if (cfgList == null) {
/* 213 */         ModbusDeviceManager.logger.warn("remove ModbusReadConfig have invalid id, id : " + ((ModbusReadConfig)event
/* 214 */             .getValue()).getPinGroupId());
/*     */         return;
/*     */       } 
/* 217 */       for (int i = 0; i < cfgList.size(); i++) {
/* 218 */         if (((ModbusReadConfig)cfgList.get(i)).getId().equals(event.getKey())) {
/* 219 */           cfgList.remove(i);
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void entryAdded(EntryEvent<Long, ModbusReadConfig> event) {
/* 227 */       List<ModbusReadConfig> cfgList = (List<ModbusReadConfig>)ModbusDeviceManager.this.readCfgMap.get(((ModbusReadConfig)event.getValue()).getPinGroupId());
/* 228 */       if (cfgList == null) {
/* 229 */         cfgList = new ArrayList<>();
/* 230 */         ModbusDeviceManager.this.readCfgMap.put(((ModbusReadConfig)event.getValue()).getPinGroupId(), cfgList);
/*     */       } 
/* 232 */       cfgList.add(event.getValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public void entryUpdated(EntryEvent<Long, ModbusReadConfig> event) {
/* 237 */       List<ModbusReadConfig> cfgList = (List<ModbusReadConfig>)ModbusDeviceManager.this.readCfgMap.get(((ModbusReadConfig)event.getValue()).getPinGroupId());
/* 238 */       if (cfgList == null) {
/* 239 */         ModbusDeviceManager.logger.warn("update ModbusReadConfig have invalid device name, deviceName : " + ((ModbusReadConfig)event
/*     */             
/* 241 */             .getValue()).getPinGroupId());
/*     */         return;
/*     */       } 
/* 244 */       for (int i = 0; i < cfgList.size(); i++) {
/* 245 */         if (((ModbusReadConfig)cfgList.get(i)).getId().equals(event.getKey())) {
/* 246 */           cfgList.remove(i);
/*     */           break;
/*     */         } 
/*     */       } 
/* 250 */       cfgList.add(event.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class ModbusPinMappingMapListener
/*     */     implements EntryAddedListener<Long, ModbusPinMapping>, EntryRemovedListener<Long, ModbusPinMapping>, EntryUpdatedListener<Long, ModbusPinMapping>
/*     */   {
/*     */     public void entryUpdated(EntryEvent<Long, ModbusPinMapping> event) {
/* 261 */       Map<Integer, ModbusPinMapping> map = (Map<Integer, ModbusPinMapping>)ModbusDeviceManager.this.pinMap.get(((ModbusPinMapping)event.getValue()).getDeviceName());
/* 262 */       if (map == null) {
/* 263 */         ModbusDeviceManager.logger.warn("update ModbusPinMapping have invalid deviceName, deviceName : " + ((ModbusPinMapping)event
/*     */             
/* 265 */             .getValue()).getDeviceName());
/*     */         return;
/*     */       } 
/* 268 */       synchronized (map) {
/* 269 */         if (!((ModbusPinMapping)event.getOldValue()).getAddress().equals(((ModbusPinMapping)event.getValue()).getAddress())) {
/* 270 */           map.remove(((ModbusPinMapping)event.getOldValue()).getAddress());
/*     */         }
/* 272 */         map.put(((ModbusPinMapping)event.getValue()).getAddress(), event.getValue());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void entryRemoved(EntryEvent<Long, ModbusPinMapping> event) {
/* 278 */       Map<Integer, ModbusPinMapping> map = (Map<Integer, ModbusPinMapping>)ModbusDeviceManager.this.pinMap.get(((ModbusPinMapping)event.getValue()).getDeviceName());
/* 279 */       if (map == null) {
/* 280 */         ModbusDeviceManager.logger.warn("update ModbusPinMapping have invalid device name, deviceName : " + ((ModbusPinMapping)event
/*     */             
/* 282 */             .getValue()).getDeviceName());
/*     */         return;
/*     */       } 
/* 285 */       synchronized (map) {
/* 286 */         map.remove(((ModbusPinMapping)event.getValue()).getAddress());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void entryAdded(EntryEvent<Long, ModbusPinMapping> event) {
/* 292 */       Map<Integer, ModbusPinMapping> map = (Map<Integer, ModbusPinMapping>)ModbusDeviceManager.this.pinMap.get(((ModbusPinMapping)event.getValue()).getDeviceName());
/* 293 */       if (map == null) {
/* 294 */         map = new HashMap<>();
/* 295 */         ModbusDeviceManager.this.pinMap.put(((ModbusPinMapping)event.getValue()).getDeviceName(), map);
/*     */       } 
/* 297 */       synchronized (map) {
/* 298 */         map.put(((ModbusPinMapping)event.getValue()).getAddress(), event.getValue());
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\am-rtu-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\rtu\service\ModbusDeviceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */