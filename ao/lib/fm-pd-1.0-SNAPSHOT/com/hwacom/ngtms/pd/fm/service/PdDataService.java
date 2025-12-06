/*     */ package com.hwacom.ngtms.pd.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceLocationMappingConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceType;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadLine;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadSection;
/*     */ import com.hwacom.ngtms.c.fm.repository.DeviceLocationMappingConfigRepository;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfigPk;
/*     */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*     */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*     */ import com.hwacom.ngtms.pd.fm.hz.PdHzMap;
/*     */ import com.hwacom.ngtms.pd.fm.model.PdConfig;
/*     */ import com.hwacom.ngtms.pd.fm.model.PdLoopDeviceConfig;
/*     */ import com.hwacom.ngtms.pd.fm.model.PdLoopStatus;
/*     */ import com.hwacom.ngtms.pd.fm.model.PdStatus;
/*     */ import com.hwacom.ngtms.pd.fm.repository.PdLoopDeviceConfigRepository;
/*     */ import com.hwacom.ngtms.pd.fm.repository.PdStatusRepository;
/*     */ import com.hwacom.ngtms.pd.shared.dto.LoopDeviceConfigDTO;
/*     */ import com.hwacom.ngtms.pd.shared.dto.PdConfigDTO;
/*     */ import com.hwacom.ngtms.pd.shared.dto.PdStatusDTO;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.modelmapper.ModelMapper;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ 
/*     */ @org.springframework.stereotype.Service
/*     */ public class PdDataService
/*     */ {
/*  45 */   private static final Logger logger = LoggerFactory.getLogger(PdDataService.class);
/*     */   
/*     */   @Autowired
/*     */   PdStatusRepository pdStatusRepository;
/*     */   @Autowired
/*     */   PdLoopDeviceConfigRepository pdLoopDeviceConfigRepository;
/*     */   
/*     */   public List<PdConfigDTO> retrievePdConfig()
/*     */   {
/*  54 */     logger.debug("Retrieve PdConfigDTO.");
/*  55 */     List<PdConfigDTO> result = new ArrayList();
/*  56 */     IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap(CommonFmHzMap.DeviceTcConfig);
/*  57 */     IMap<String, RoadLine> lineMap = HzUtils.getMap(CommonFmHzMap.RoadLine);
/*  58 */     IMap<String, RoadSection> sectionMap = HzUtils.getMap(CommonFmHzMap.RoadSection);
/*  59 */     IMap<String, PdConfig> pdConfigMap = HzUtils.getMap(PdHzMap.Config);
/*  60 */     EntryObject entryObject = new PredicateBuilder().getEntryObject();
/*  61 */     PredicateBuilder pb = entryObject.get("deviceType").equal("PD");
/*  62 */     for (DeviceTcConfig each : deviceConfigMap.values(pb)) {
/*  63 */       PdConfigDTO dto = (PdConfigDTO)this.modelMapper.map(each, PdConfigDTO.class);
/*  64 */       PdConfig pdConfig = (PdConfig)pdConfigMap.get(each.getDeviceName());
/*  65 */       dto.setSectionName(((RoadSection)sectionMap.get(dto.getSectionId())).getSectionName());
/*  66 */       dto.setArea(pdConfig.getArea());
/*  67 */       dto.setMeterNo(pdConfig.getMeterNo());
/*  68 */       dto.setLoopNo(pdConfig.getLoopNo());
/*  69 */       dto.setPhone(pdConfig.getPhone());
/*  70 */       dto.setLineName(((RoadLine)lineMap.get(each.getLineId())).getLineName());
/*  71 */       dto.setLocation(com.hwacom.ngtms.c.util.DeviceUtils.getLocationName(each.getDeviceName()));
/*  72 */       result.add(dto);
/*     */     }
/*  74 */     return result;
/*     */   }
/*     */   
/*     */   public boolean checkPdConfig(String deviceName) {
/*  78 */     logger.debug("check PdConfig.");
/*  79 */     IMap<String, PdConfig> pdConfigMap = HzUtils.getMap(PdHzMap.Config);
/*  80 */     if (pdConfigMap.get(deviceName) != null) {
/*  81 */       return true;
/*     */     }
/*  83 */     return false; }
/*     */   
/*     */   @Autowired
/*     */   DeviceLocationMappingConfigRepository deviceLocationMappingConfigRepository;
/*     */   
/*  88 */   public boolean checkRemovedPdConfig(String deviceName) { logger.debug("check RemovedPdConfig.");
/*  89 */     Boolean check = Boolean.valueOf(false);
/*  90 */     IMap<Long, PdLoopDeviceConfig> pdLoopDeviceConfigMap = HzUtils.getMap(PdHzMap.LoopDeviceConfig);
/*  91 */     for (PdLoopDeviceConfig config : pdLoopDeviceConfigMap.values()) {
/*  92 */       if ((config.getPdDeviceName() != null) && (config.getPdDeviceName().equals(deviceName))) {
/*  93 */         check = Boolean.valueOf(true);
/*  94 */         break;
/*     */       }
/*     */     }
/*  97 */     return check.booleanValue();
/*     */   }
/*     */   
/*     */   public void savePdConfig(PdConfigDTO dto) {
/* 101 */     logger.debug("save PdConfigDTO.");
/* 102 */     IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap(CommonFmHzMap.DeviceTcConfig);
/*     */     
/* 104 */     IMap<String, DeviceLocationMappingConfig> locationMap = HzUtils.getMap(CommonFmHzMap.DeviceLocationMappingConfig);
/* 105 */     IMap<String, PdConfig> pdConfigMap = HzUtils.getMap(PdHzMap.Config);
/* 106 */     if (deviceConfigMap.get(dto.getDeviceName()) == null) {
/* 107 */       DeviceTcConfig deviceConfig = new DeviceTcConfig();
/*     */       
/* 109 */       deviceConfig.setDeviceName(dto.getDeviceName());
/* 110 */       deviceConfig.setDisplayName(dto.getDisplayName());
/* 111 */       deviceConfig.setDirection(dto.getDirection());
/* 112 */       deviceConfig.setLocation(dto.getLocation());
/* 113 */       deviceConfig.setIp(dto.getIp());
/* 114 */       deviceConfig.setPort(dto.getPort());
/* 115 */       deviceConfig.setMemo(dto.getMemo());
/* 116 */       deviceConfig.setMilepost(dto.getMilepost());
/* 117 */       deviceConfig.setLongitude(dto.getLongitude());
/* 118 */       deviceConfig.setLatitude(dto.getLatitude());
/* 119 */       deviceConfig.setDeviceType("PD");
/* 120 */       deviceConfig.setProject("");
/* 121 */       deviceConfig.setLineId(dto.getLineId());
/* 122 */       deviceConfig.setEnable(dto.getEnable());
/* 123 */       deviceConfig.setSectionId(dto.getSectionId());
/* 124 */       deviceConfigMap.put(dto.getDeviceName(), deviceConfig);
/*     */     }
/* 126 */     if (pdConfigMap.get(dto.getDeviceName()) == null) {
/* 127 */       PdConfig pdConfig = (PdConfig)this.modelMapper.map(dto, PdConfig.class);
/* 128 */       pdConfigMap.put(dto.getDeviceName(), pdConfig);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 133 */     PredicateBuilder pb = new PredicateBuilder().getEntryObject().get("deviceName").equal(dto.getDeviceName());
/* 134 */     DeviceLocationMappingConfig location = null;
/* 135 */     if ((locationMap.values(pb) == null) || (locationMap.values(pb).size() == 0)) {
/* 136 */       location = new DeviceLocationMappingConfig();
/* 137 */       location.setLocationName(dto.getLocation());
/* 138 */       location.setDeviceName(dto.getDeviceName());
/*     */     } else {
/* 140 */       Iterator localIterator = locationMap.values(pb).iterator(); if (localIterator.hasNext()) { DeviceLocationMappingConfig loc = (DeviceLocationMappingConfig)localIterator.next();
/* 141 */         location = loc;
/*     */       }
/*     */       
/* 144 */       location.setLocationName(dto.getLocation());
/*     */     }
/* 146 */     if (location != null) {
/* 147 */       location = (DeviceLocationMappingConfig)this.deviceLocationMappingConfigRepository.save(location);
/* 148 */       locationMap.put(location.getId(), location); } }
/*     */   
/*     */   @Autowired
/*     */   ModelMapper modelMapper;
/*     */   
/* 153 */   public void updatePdConfig(PdConfigDTO dto) { logger.debug("update PdConfigDTO.");
/* 154 */     IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap(CommonFmHzMap.DeviceTcConfig);
/*     */     
/* 156 */     IMap<String, DeviceLocationMappingConfig> locationMap = HzUtils.getMap(CommonFmHzMap.DeviceLocationMappingConfig);
/* 157 */     IMap<String, PdConfig> pdConfigMap = HzUtils.getMap(PdHzMap.Config);
/* 158 */     if (deviceConfigMap.get(dto.getDeviceName()) != null) {
/* 159 */       DeviceTcConfig deviceConfig = (DeviceTcConfig)deviceConfigMap.get(dto.getDeviceName());
/*     */       
/* 161 */       deviceConfig.setDeviceName(dto.getDeviceName());
/* 162 */       deviceConfig.setDisplayName(dto.getDisplayName());
/* 163 */       deviceConfig.setLocation(dto.getLocation());
/* 164 */       deviceConfig.setDirection(dto.getDirection());
/* 165 */       deviceConfig.setIp(dto.getIp());
/* 166 */       deviceConfig.setPort(dto.getPort());
/* 167 */       deviceConfig.setMemo(dto.getMemo());
/* 168 */       deviceConfig.setMilepost(dto.getMilepost());
/* 169 */       deviceConfig.setLongitude(dto.getLongitude());
/* 170 */       deviceConfig.setLatitude(dto.getLatitude());
/* 171 */       deviceConfig.setDeviceType("PD");
/* 172 */       deviceConfig.setProject("");
/* 173 */       deviceConfig.setLineId(dto.getLineId());
/* 174 */       deviceConfig.setEnable(dto.getEnable());
/* 175 */       deviceConfig.setSectionId(dto.getSectionId());
/* 176 */       deviceConfigMap.put(dto.getDeviceName(), deviceConfig);
/*     */     }
/* 178 */     if (pdConfigMap.get(dto.getDeviceName()) != null) {
/* 179 */       PdConfig pdConfig = (PdConfig)pdConfigMap.get(dto.getDeviceName());
/* 180 */       pdConfig.setArea(dto.getArea());
/* 181 */       pdConfig.setLoopNo(dto.getLoopNo());
/* 182 */       pdConfig.setMeterNo(dto.getMeterNo());
/* 183 */       pdConfig.setPhone(dto.getPhone());
/* 184 */       pdConfigMap.put(dto.getDeviceName(), pdConfig);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 189 */     PredicateBuilder pb = new PredicateBuilder().getEntryObject().get("deviceName").equal(dto.getDeviceName());
/* 190 */     DeviceLocationMappingConfig location = null;
/* 191 */     if ((locationMap.values(pb) == null) || (locationMap.values(pb).size() == 0)) {
/* 192 */       location = new DeviceLocationMappingConfig();
/* 193 */       location.setLocationName(dto.getLocation());
/* 194 */       location.setDeviceName(dto.getDeviceName());
/*     */     } else {
/* 196 */       Iterator localIterator = locationMap.values(pb).iterator(); if (localIterator.hasNext()) { DeviceLocationMappingConfig loc = (DeviceLocationMappingConfig)localIterator.next();
/* 197 */         location = loc;
/*     */       }
/*     */       
/* 200 */       location.setLocationName(dto.getLocation());
/*     */     }
/* 202 */     if (location != null) {
/* 203 */       location = (DeviceLocationMappingConfig)this.deviceLocationMappingConfigRepository.save(location);
/* 204 */       locationMap.put(location.getId(), location);
/*     */     } }
/*     */   
/*     */   @Autowired
/*     */   private HcceEnv hcceEnv;
/* 209 */   public void deletePdConfig(String deviceName) { logger.debug("delete PdConfigDTO.");
/* 210 */     IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap(CommonFmHzMap.DeviceTcConfig);
/* 211 */     IMap<Long, PdLoopDeviceConfig> pdLoopDeviceConfigMap = HzUtils.getMap(PdHzMap.LoopDeviceConfig);
/* 212 */     IMap<String, PdConfig> pdConfigMap = HzUtils.getMap(PdHzMap.Config);
/* 213 */     EntryObject entryObject = new PredicateBuilder().getEntryObject();
/* 214 */     PredicateBuilder pb = entryObject.get("pdDeviceName").equal(deviceName);
/* 215 */     DeviceTcConfig deviceConfig = (DeviceTcConfig)deviceConfigMap.get(deviceName);
/* 216 */     if (deviceConfig != null) {
/* 217 */       deviceConfigMap.remove(deviceName);
/*     */     }
/* 219 */     if (pdConfigMap.get(deviceName) != null) {
/* 220 */       pdConfigMap.remove(deviceName);
/*     */     }
/* 222 */     for (PdLoopDeviceConfig config : pdLoopDeviceConfigMap.values(pb)) {
/* 223 */       pdLoopDeviceConfigMap.remove(config.getId());
/*     */     }
/*     */   }
/*     */   
/*     */   public List<LoopDeviceConfigDTO> retrieveLoopDeviceConfig(List<String> deviceNames) {
/*     */     try {
/* 229 */       logger.debug("retrieve loopDeviceConfig");
/* 230 */       IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap(CommonFmHzMap.DeviceTcConfig);
/*     */       
/* 232 */       IMap<Long, PdLoopDeviceConfig> pdLoopDeviceConfigMap = HzUtils.getMap(PdHzMap.LoopDeviceConfig);
/* 233 */       IMap<String, PdConfig> pdConfigMap = HzUtils.getMap(PdHzMap.Config);
/* 234 */       List<LoopDeviceConfigDTO> result = new ArrayList();
/* 235 */       Map<String, List<LoopDeviceConfigDTO>> loopDeviceMap = new HashMap();
/* 236 */       String[] deviceNameArray = (String[])deviceNames.toArray(new String[deviceNames.size()]);
/* 237 */       EntryObject eo = new PredicateBuilder().getEntryObject();
/* 238 */       PredicateBuilder pb = eo.get("pdDeviceName").in(deviceNameArray);
/* 239 */       EntryObject entryObject = new PredicateBuilder().getEntryObject();
/* 240 */       PredicateBuilder predicateBuilder = entryObject.get("deviceName").in(deviceNameArray);
/*     */       
/* 242 */       for (PdLoopDeviceConfig each : pdLoopDeviceConfigMap.values(pb)) {
/* 243 */         LoopDeviceConfigDTO dto = (LoopDeviceConfigDTO)this.modelMapper.map(each, LoopDeviceConfigDTO.class);
/* 244 */         DeviceTcConfig deviceConfig = (DeviceTcConfig)deviceConfigMap.get(each.getDeviceName());
/* 245 */         if (deviceConfig != null) {
/* 246 */           dto.setDisplayName(deviceConfig.getDisplayName());
/* 247 */           dto.setCheck(Boolean.valueOf(true));
/* 248 */           String loopKey = each.getPdDeviceName() + "-" + String.valueOf(each.getLoopNo());
/* 249 */           List<LoopDeviceConfigDTO> configList = (List)loopDeviceMap.get(loopKey);
/* 250 */           if (configList == null) {
/* 251 */             configList = new ArrayList();
/*     */           }
/* 253 */           configList.add(dto);
/* 254 */           loopDeviceMap.put(loopKey, configList);
/*     */         }
/*     */       }
/*     */       
/* 258 */       for (PdConfig each : pdConfigMap.values(predicateBuilder)) {
/* 259 */         LoopDeviceConfigDTO dto = new LoopDeviceConfigDTO();
/* 260 */         dto.setDeviceName(each.getDeviceName());
/* 261 */         dto.setDisplayName(((DeviceTcConfig)deviceConfigMap.get(each.getDeviceName())).getDisplayName());
/* 262 */         dto.setCheck(Boolean.valueOf(false));
/* 263 */         List<LoopDeviceConfigDTO> list = new ArrayList();
/* 264 */         if (each.getLoopNo().intValue() > 0) {
/* 265 */           for (int i = 1; i <= each.getLoopNo().intValue(); i++) {
/* 266 */             LoopDeviceConfigDTO loopData = new LoopDeviceConfigDTO();
/* 267 */             loopData.setDeviceName(each.getDeviceName() + "-" + String.valueOf(i));
/* 268 */             loopData.setDisplayName(String.valueOf(i));
/* 269 */             loopData.setLoopNo(Integer.valueOf(i));
/* 270 */             loopData.setCheck(Boolean.valueOf(false));
/* 271 */             loopData.setChildren((List)loopDeviceMap.get(each.getDeviceName() + "-" + String.valueOf(i)));
/* 272 */             list.add(loopData);
/*     */           }
/*     */         }
/* 275 */         dto.setChildren(list);
/* 276 */         result.add(dto);
/*     */       }
/* 278 */       return result;
/*     */     }
/*     */     catch (Exception e) {
/* 281 */       logger.error("retrieveLoopDeviceConfig failed.", e); }
/* 282 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   public List<LoopDeviceConfigDTO> retrieveUnsetLoopDeviceConfig()
/*     */   {
/*     */     try {
/* 288 */       logger.debug("retrieve unsetLoopDeviceConfig.");
/* 289 */       IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap(CommonFmHzMap.DeviceTcConfig);
/*     */       
/* 291 */       IMap<Long, PdLoopDeviceConfig> pdLoopDeviceConfigMap = HzUtils.getMap(PdHzMap.LoopDeviceConfig);
/* 292 */       IMap<String, RoadLine> roadLineMap = HzUtils.getMap(CommonFmHzMap.RoadLine);
/* 293 */       IMap<String, DeviceType> deviceTypeMap = HzUtils.getMap(CommonFmHzMap.DeviceType);
/* 294 */       List<LoopDeviceConfigDTO> result = new ArrayList();
/* 295 */       DynamicConfig config = getDynamicConfig("addedPdDeviceType");
/* 296 */       String[] types = config.getValue().split(",");
/* 297 */       EntryObject entryObject = new PredicateBuilder().getEntryObject();
/* 298 */       PredicateBuilder predicateBuilder = entryObject.get("deviceType").in(types);
/*     */       
/* 300 */       List<String> allAddedPdDeviceNames = new ArrayList();
/*     */       
/* 302 */       for (Iterator localIterator = pdLoopDeviceConfigMap.values().iterator(); localIterator.hasNext();) { addedPdDevice = (PdLoopDeviceConfig)localIterator.next();
/* 303 */         allAddedPdDeviceNames.add(addedPdDevice.getDeviceName());
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 308 */       Object typeLineMap = new HashMap();
/* 309 */       for (PdLoopDeviceConfig addedPdDevice = deviceConfigMap.values(predicateBuilder).iterator(); addedPdDevice.hasNext();) { addedDevice = (DeviceTcConfig)addedPdDevice.next();
/* 310 */         if (!allAddedPdDeviceNames.contains(addedDevice.getDeviceName())) {
/* 311 */           String deviceType = addedDevice.getDeviceType();
/* 312 */           lineId = addedDevice.getLineId();
/* 313 */           if (((Map)typeLineMap).get(deviceType) == null) {
/* 314 */             ((Map)typeLineMap).put(deviceType, new HashMap());
/*     */           }
/* 316 */           Map<String, List<LoopDeviceConfigDTO>> lineMap = (Map)((Map)typeLineMap).get(deviceType);
/* 317 */           if ((lineId != null) && (lineMap.get(lineId) == null)) {
/* 318 */             lineMap.put(lineId, new ArrayList());
/*     */           }
/*     */           
/* 321 */           if (lineId != null) {
/* 322 */             RoadLine road = (RoadLine)roadLineMap.get(lineId);
/* 323 */             List<LoopDeviceConfigDTO> deviceList = (List)lineMap.get(lineId);
/* 324 */             LoopDeviceConfigDTO dto = new LoopDeviceConfigDTO();
/* 325 */             dto.setDisplayName(addedDevice.getDisplayName());
/* 326 */             dto.setCheck(Boolean.valueOf(true));
/* 327 */             dto.setDeviceName(addedDevice.getDeviceName());
/* 328 */             dto.setMemo(addedDevice.getMemo());
/* 329 */             dto.setLineId(addedDevice.getLineId());
/* 330 */             dto.setMileage(addedDevice.getMilepost());
/* 331 */             if (road != null) {
/* 332 */               dto.setLineName(road.getLineName());
/*     */             } else {
/* 334 */               dto.setLineName(addedDevice.getLineId());
/*     */             }
/* 336 */             deviceList.add(dto);
/*     */           }
/* 338 */           ((Map)typeLineMap).put(deviceType, lineMap);
/*     */         }
/*     */       }
/*     */       DeviceTcConfig addedDevice;
/*     */       String lineId;
/* 343 */       Map<String, List<LoopDeviceConfigDTO>> deviceMap = null;
/* 344 */       for (Map.Entry<String, Map<String, List<LoopDeviceConfigDTO>>> each : ((Map)typeLineMap).entrySet()) {
/* 345 */         deviceMap = new HashMap();
/* 346 */         for (Map.Entry<String, List<LoopDeviceConfigDTO>> eachSet : ((Map)each.getValue()).entrySet()) {
/* 347 */           deviceList = (List)eachSet.getValue();
/* 348 */           Collections.sort(deviceList, new Comparator()
/*     */           {
/*     */ 
/*     */             public int compare(LoopDeviceConfigDTO o1, LoopDeviceConfigDTO o2)
/*     */             {
/* 353 */               Integer o1Mileage = o1.getMileage();
/* 354 */               Integer o2Mileage = o2.getMileage();
/* 355 */               if ((o1Mileage != null) && (o2Mileage != null))
/* 356 */                 return o1Mileage.compareTo(o2Mileage);
/* 357 */               if ((o1Mileage != null) && (o2Mileage == null))
/* 358 */                 return 1;
/* 359 */               if ((o1Mileage == null) && (o2Mileage != null)) {
/* 360 */                 return -1;
/*     */               }
/* 362 */               return 0;
/*     */             }
/*     */             
/* 365 */           });
/* 366 */           deviceMap.put(eachSet.getKey(), deviceList);
/*     */         }
/* 368 */         ((Map)typeLineMap).put(each.getKey(), deviceMap);
/*     */       }
/*     */       List<LoopDeviceConfigDTO> deviceList;
/* 371 */       for (Map.Entry<String, Map<String, List<LoopDeviceConfigDTO>>> each : ((Map)typeLineMap).entrySet()) {
/* 372 */         LoopDeviceConfigDTO typeData = new LoopDeviceConfigDTO();
/* 373 */         typeData.setCheck(Boolean.valueOf(false));
/* 374 */         typeData.setDeviceName((String)each.getKey());
/* 375 */         typeData.setDisplayName(((DeviceType)deviceTypeMap.get(each.getKey())).getDescription());
/* 376 */         List<LoopDeviceConfigDTO> lineDataList = new ArrayList();
/* 377 */         for (Map.Entry<String, List<LoopDeviceConfigDTO>> lineEach : ((Map)each.getValue()).entrySet()) {
/* 378 */           String lineId = (String)lineEach.getKey();
/* 379 */           if (lineId != null) {
/* 380 */             LoopDeviceConfigDTO lineData = new LoopDeviceConfigDTO();
/* 381 */             lineData.setCheck(Boolean.valueOf(false));
/* 382 */             lineData.setDeviceName((String)each.getKey() + lineId);
/* 383 */             lineData.setDisplayName(((RoadLine)roadLineMap.get(lineId)).getLineName());
/* 384 */             lineData.setChildren((List)lineEach.getValue());
/* 385 */             lineData.setLineId(lineId);
/* 386 */             lineDataList.add(lineData);
/*     */           }
/*     */         }
/* 389 */         Collections.sort(lineDataList, new Comparator()
/*     */         {
/*     */ 
/*     */           public int compare(LoopDeviceConfigDTO o1, LoopDeviceConfigDTO o2)
/*     */           {
/* 394 */             String o1Line = o1.getLineId();
/* 395 */             String o2Line = o2.getLineId();
/* 396 */             if ((o1Line != null) && (o2Line != null))
/* 397 */               return o1Line.compareTo(o2Line);
/* 398 */             if ((o1Line != null) && (o2Line == null))
/* 399 */               return 1;
/* 400 */             if ((o1Line == null) && (o2Line != null)) {
/* 401 */               return -1;
/*     */             }
/* 403 */             return 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 414 */         });
/* 415 */         typeData.setChildren(lineDataList);
/* 416 */         result.add(typeData);
/*     */       }
/*     */       
/* 419 */       logger.debug("result.size:'{}'", Integer.valueOf(result.size()));
/* 420 */       return result;
/*     */     } catch (Exception e) {
/* 422 */       logger.error("retrieveUnsetLoopDeviceConfig failed.", e); }
/* 423 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   public void updateLoopDeviceConfig(String pdDeviceName, List<LoopDeviceConfigDTO> dtos)
/*     */   {
/*     */     try {
/* 429 */       logger.debug("update loopDeviceConfig,pdDevice:'{}'", pdDeviceName);
/*     */       
/* 431 */       IMap<Long, PdLoopDeviceConfig> pdLoopDeviceConfigMap = HzUtils.getMap(PdHzMap.LoopDeviceConfig);
/* 432 */       Map<Long, PdLoopDeviceConfig> saveMap = new HashMap();
/* 433 */       List<Long> removeList = new ArrayList();
/* 434 */       if ((dtos == null) || (dtos.size() == 0)) {
/* 435 */         logger.error("no LoopDeviceConfigDTO");
/* 436 */         return;
/*     */       }
/* 438 */       Integer count = Integer.valueOf(0);
/* 439 */       for (LoopDeviceConfigDTO dto : dtos) {
/* 440 */         EntryObject eo = new PredicateBuilder().getEntryObject();
/* 441 */         PredicateBuilder pb = eo.get("deviceName").equal(dto.getDeviceName());
/* 442 */         List<PdLoopDeviceConfig> configList = new ArrayList(pdLoopDeviceConfigMap.values(pb));
/* 443 */         PdLoopDeviceConfig config = new PdLoopDeviceConfig();
/*     */         
/* 445 */         if (configList.size() == 0) {
/* 446 */           logger.debug("add new PdLoopDeviceConfig.");
/* 447 */           config.setDeviceName(dto.getDeviceName());
/* 448 */           config.setDiameter(dto.getDiameter());
/* 449 */           if (pdDeviceName != "") {
/* 450 */             config.setLoopNo(String.valueOf(dto.getLoopNo()));
/* 451 */             config.setPdDeviceName(pdDeviceName);
/*     */           }
/* 453 */           config = (PdLoopDeviceConfig)this.pdLoopDeviceConfigRepository.save(config);
/* 454 */           saveMap.put(config.getId(), config);
/*     */         }
/*     */         else {
/* 457 */           logger.debug("update PdLoopDeviceConfig.");
/* 458 */           config = (PdLoopDeviceConfig)configList.get(0);
/* 459 */           if (pdDeviceName != "") {
/* 460 */             config.setLoopNo(String.valueOf(dto.getLoopNo()));
/* 461 */             config.setPdDeviceName(pdDeviceName);
/* 462 */             saveMap.put(config.getId(), config);
/*     */           } else {
/* 464 */             removeList.add(config.getId());
/*     */           }
/*     */         }
/* 467 */         count = Integer.valueOf(count.intValue() + 1);
/*     */       }
/* 469 */       if (removeList.size() > 0) {
/* 470 */         Long[] ids = (Long[])removeList.toArray(new Long[removeList.size()]);
/* 471 */         PredicateBuilder pb = new PredicateBuilder().getEntryObject().get("id").in(ids);
/* 472 */         pdLoopDeviceConfigMap.removeAll(pb);
/*     */       }
/*     */       
/* 475 */       pdLoopDeviceConfigMap.putAll(saveMap);
/* 476 */       logger.debug("updateLoopDeviceConfig update '{}' device", count);
/*     */     } catch (Exception e) {
/* 478 */       logger.error("updateLoopDeviceConfig failed.", e);
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateLoopDeviceData(LoopDeviceConfigDTO dto) {
/*     */     try {
/* 484 */       logger.debug("update loopDeviceData, deviceName:'{}'", dto.getDeviceName());
/*     */       
/* 486 */       IMap<Long, PdLoopDeviceConfig> pdLoopDeviceConfigMap = HzUtils.getMap(PdHzMap.LoopDeviceConfig);
/* 487 */       EntryObject eo = new PredicateBuilder().getEntryObject();
/* 488 */       PredicateBuilder pb = eo.get("deviceName").equal(dto.getDeviceName());
/* 489 */       PdLoopDeviceConfig config = (PdLoopDeviceConfig)new ArrayList(pdLoopDeviceConfigMap.values(pb)).get(0);
/* 490 */       config.setMemo(dto.getMemo());
/* 491 */       config.setDiameter(dto.getDiameter());
/* 492 */       pdLoopDeviceConfigMap.put(config.getId(), config);
/*     */     } catch (Exception e) {
/* 494 */       logger.error("updateLoopDeviceData failed.", e);
/*     */     }
/*     */   }
/*     */   
/*     */   public List<PdStatusDTO> retrievePdStatus() {
/* 499 */     logger.debug("retrieve pdStatus");
/* 500 */     IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap(CommonFmHzMap.DeviceTcConfig);
/* 501 */     IMap<String, PdConfig> pdConfigMap = HzUtils.getMap(PdHzMap.Config);
/* 502 */     IMap<String, PdStatus> pdStatusMap = HzUtils.getMap(PdHzMap.Status);
/* 503 */     List<PdStatusDTO> result = new ArrayList();
/* 504 */     for (PdStatus status : pdStatusMap.values()) {
/* 505 */       PdStatusDTO dto = new PdStatusDTO();
/* 506 */       dto.setPdId(status.getDeviceName());
/* 507 */       dto.setPdDisplayName(((DeviceTcConfig)deviceConfigMap.get(status.getDeviceName())).getDisplayName());
/* 508 */       dto.setLineId(((DeviceTcConfig)deviceConfigMap.get(status.getDeviceName())).getLineId());
/* 509 */       dto.setDataTime(status.getDataTime());
/* 510 */       dto.setConnectivity(status.getConnectivity());
/* 511 */       dto.setDoorOpen(status.getDoorOpen());
/* 512 */       dto.setPrimaryR(status.getPrimaryR());
/* 513 */       dto.setPrimaryS(status.getPrimaryS());
/* 514 */       dto.setPrimaryT(status.getPrimaryT());
/* 515 */       dto.setSecondaryR(status.getSecondaryR());
/* 516 */       dto.setSecondaryS(status.getSecondaryS());
/* 517 */       dto.setSecondaryT(status.getSecondaryT());
/* 518 */       Integer loopNo = ((PdConfig)pdConfigMap.get(status.getDeviceName())).getLoopNo();
/* 519 */       for (PdLoopStatus loopStatus : status.getPdLoops()) {
/* 520 */         if ((loopStatus.getLoopId().equals("1")) && 
/* 521 */           (Integer.valueOf(loopStatus.getLoopId()).intValue() <= loopNo.intValue())) {
/* 522 */           dto.setLoop1Status(loopStatus.getStatus());
/* 523 */         } else if ((loopStatus.getLoopId().equals("2")) && 
/* 524 */           (Integer.valueOf(loopStatus.getLoopId()).intValue() <= loopNo.intValue())) {
/* 525 */           dto.setLoop2Status(loopStatus.getStatus());
/* 526 */         } else if ((loopStatus.getLoopId().equals("3")) && 
/* 527 */           (Integer.valueOf(loopStatus.getLoopId()).intValue() <= loopNo.intValue())) {
/* 528 */           dto.setLoop3Status(loopStatus.getStatus());
/* 529 */         } else if ((loopStatus.getLoopId().equals("4")) && 
/* 530 */           (Integer.valueOf(loopStatus.getLoopId()).intValue() <= loopNo.intValue())) {
/* 531 */           dto.setLoop4Status(loopStatus.getStatus());
/* 532 */         } else if ((loopStatus.getLoopId().equals("5")) && 
/* 533 */           (Integer.valueOf(loopStatus.getLoopId()).intValue() <= loopNo.intValue())) {
/* 534 */           dto.setLoop5Status(loopStatus.getStatus());
/*     */         }
/*     */       }
/* 537 */       result.add(dto);
/*     */     }
/* 539 */     return result;
/*     */   }
/*     */   
/*     */   private DynamicConfig getDynamicConfig(String configName) {
/* 543 */     IMap<DynamicConfigPk, DynamicConfig> dynamicConfigMap = HzUtils.getMap(HzMap.DynamicConfig);
/*     */     
/* 545 */     DynamicConfig dynamicConfig = (DynamicConfig)dynamicConfigMap.get(new DynamicConfigPk(this.hcceEnv
/* 546 */       .getCurrentGroupName(), "PdFm", configName));
/* 547 */     return dynamicConfig;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\fm-pd-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\pd\fm\service\PdDataService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */