/*     */ package com.hwacom.ngtms.pd.fm.service;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceLocationMappingConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceType;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadLine;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadSection;
/*     */ import com.hwacom.ngtms.c.fm.repository.DeviceLocationMappingConfigRepository;
/*     */ import com.hwacom.ngtms.c.util.DeviceUtils;
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
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.modelmapper.ModelMapper;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service
/*     */ public class PdDataService {
/*  45 */   private static final Logger logger = LoggerFactory.getLogger(PdDataService.class);
/*     */   
/*     */   @Autowired
/*     */   PdStatusRepository pdStatusRepository;
/*     */   
/*     */   @Autowired
/*     */   PdLoopDeviceConfigRepository pdLoopDeviceConfigRepository;
/*     */   
/*     */   public List<PdConfigDTO> retrievePdConfig() {
/*  54 */     logger.debug("Retrieve PdConfigDTO.");
/*  55 */     List<PdConfigDTO> result = new ArrayList<>();
/*  56 */     IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  57 */     IMap<String, RoadLine> lineMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadLine);
/*  58 */     IMap<String, RoadSection> sectionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadSection);
/*  59 */     IMap<String, PdConfig> pdConfigMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.Config);
/*  60 */     EntryObject entryObject = (new PredicateBuilder()).getEntryObject();
/*  61 */     PredicateBuilder pb = entryObject.get("deviceType").equal("PD");
/*  62 */     for (DeviceTcConfig each : deviceConfigMap.values((Predicate)pb)) {
/*  63 */       PdConfigDTO dto = (PdConfigDTO)this.modelMapper.map(each, PdConfigDTO.class);
/*  64 */       PdConfig pdConfig = (PdConfig)pdConfigMap.get(each.getDeviceName());
/*  65 */       dto.setSectionName(((RoadSection)sectionMap.get(dto.getSectionId())).getSectionName());
/*  66 */       dto.setArea(pdConfig.getArea());
/*  67 */       dto.setMeterNo(pdConfig.getMeterNo());
/*  68 */       dto.setLoopNo(pdConfig.getLoopNo());
/*  69 */       dto.setPhone(pdConfig.getPhone());
/*  70 */       dto.setLineName(((RoadLine)lineMap.get(each.getLineId())).getLineName());
/*  71 */       dto.setLocation(DeviceUtils.getLocationName(each.getDeviceName()));
/*  72 */       result.add(dto);
/*     */     } 
/*  74 */     return result; } @Autowired
/*     */   DeviceLocationMappingConfigRepository deviceLocationMappingConfigRepository; @Autowired
/*     */   ModelMapper modelMapper; @Autowired
/*     */   private HcceEnv hcceEnv; public boolean checkPdConfig(String deviceName) {
/*  78 */     logger.debug("check PdConfig.");
/*  79 */     IMap<String, PdConfig> pdConfigMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.Config);
/*  80 */     if (pdConfigMap.get(deviceName) != null) {
/*  81 */       return true;
/*     */     }
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkRemovedPdConfig(String deviceName) {
/*  88 */     logger.debug("check RemovedPdConfig.");
/*  89 */     Boolean check = Boolean.valueOf(false);
/*  90 */     IMap<Long, PdLoopDeviceConfig> pdLoopDeviceConfigMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.LoopDeviceConfig);
/*  91 */     for (PdLoopDeviceConfig config : pdLoopDeviceConfigMap.values()) {
/*  92 */       if (config.getPdDeviceName() != null && config.getPdDeviceName().equals(deviceName)) {
/*  93 */         check = Boolean.valueOf(true);
/*     */         break;
/*     */       } 
/*     */     } 
/*  97 */     return check.booleanValue();
/*     */   }
/*     */   
/*     */   public void savePdConfig(PdConfigDTO dto) {
/* 101 */     logger.debug("save PdConfigDTO.");
/* 102 */     IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*     */     
/* 104 */     IMap<String, DeviceLocationMappingConfig> locationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/* 105 */     IMap<String, PdConfig> pdConfigMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.Config);
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
/* 133 */     PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceName").equal(dto.getDeviceName());
/* 134 */     DeviceLocationMappingConfig location = null;
/* 135 */     if (locationMap.values((Predicate)pb) == null || locationMap.values((Predicate)pb).size() == 0) {
/* 136 */       location = new DeviceLocationMappingConfig();
/* 137 */       location.setLocationName(dto.getLocation());
/* 138 */       location.setDeviceName(dto.getDeviceName());
/*     */     } else {
/* 140 */       Iterator<DeviceLocationMappingConfig> iterator = locationMap.values((Predicate)pb).iterator(); if (iterator.hasNext()) { DeviceLocationMappingConfig loc = iterator.next();
/* 141 */         location = loc; }
/*     */ 
/*     */       
/* 144 */       location.setLocationName(dto.getLocation());
/*     */     } 
/* 146 */     if (location != null) {
/* 147 */       location = (DeviceLocationMappingConfig)this.deviceLocationMappingConfigRepository.save(location);
/* 148 */       locationMap.put(location.getId(), location);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updatePdConfig(PdConfigDTO dto) {
/* 153 */     logger.debug("update PdConfigDTO.");
/* 154 */     IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*     */     
/* 156 */     IMap<String, DeviceLocationMappingConfig> locationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/* 157 */     IMap<String, PdConfig> pdConfigMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.Config);
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
/* 189 */     PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceName").equal(dto.getDeviceName());
/* 190 */     DeviceLocationMappingConfig location = null;
/* 191 */     if (locationMap.values((Predicate)pb) == null || locationMap.values((Predicate)pb).size() == 0) {
/* 192 */       location = new DeviceLocationMappingConfig();
/* 193 */       location.setLocationName(dto.getLocation());
/* 194 */       location.setDeviceName(dto.getDeviceName());
/*     */     } else {
/* 196 */       Iterator<DeviceLocationMappingConfig> iterator = locationMap.values((Predicate)pb).iterator(); if (iterator.hasNext()) { DeviceLocationMappingConfig loc = iterator.next();
/* 197 */         location = loc; }
/*     */ 
/*     */       
/* 200 */       location.setLocationName(dto.getLocation());
/*     */     } 
/* 202 */     if (location != null) {
/* 203 */       location = (DeviceLocationMappingConfig)this.deviceLocationMappingConfigRepository.save(location);
/* 204 */       locationMap.put(location.getId(), location);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void deletePdConfig(String deviceName) {
/* 209 */     logger.debug("delete PdConfigDTO.");
/* 210 */     IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 211 */     IMap<Long, PdLoopDeviceConfig> pdLoopDeviceConfigMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.LoopDeviceConfig);
/* 212 */     IMap<String, PdConfig> pdConfigMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.Config);
/* 213 */     EntryObject entryObject = (new PredicateBuilder()).getEntryObject();
/* 214 */     PredicateBuilder pb = entryObject.get("pdDeviceName").equal(deviceName);
/* 215 */     DeviceTcConfig deviceConfig = (DeviceTcConfig)deviceConfigMap.get(deviceName);
/* 216 */     if (deviceConfig != null) {
/* 217 */       deviceConfigMap.remove(deviceName);
/*     */     }
/* 219 */     if (pdConfigMap.get(deviceName) != null) {
/* 220 */       pdConfigMap.remove(deviceName);
/*     */     }
/* 222 */     for (PdLoopDeviceConfig config : pdLoopDeviceConfigMap.values((Predicate)pb)) {
/* 223 */       pdLoopDeviceConfigMap.remove(config.getId());
/*     */     }
/*     */   }
/*     */   
/*     */   public List<LoopDeviceConfigDTO> retrieveLoopDeviceConfig(List<String> deviceNames) {
/*     */     try {
/* 229 */       logger.debug("retrieve loopDeviceConfig");
/* 230 */       IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*     */       
/* 232 */       IMap<Long, PdLoopDeviceConfig> pdLoopDeviceConfigMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.LoopDeviceConfig);
/* 233 */       IMap<String, PdConfig> pdConfigMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.Config);
/* 234 */       List<LoopDeviceConfigDTO> result = new ArrayList<>();
/* 235 */       Map<String, List<LoopDeviceConfigDTO>> loopDeviceMap = new HashMap<>();
/* 236 */       String[] deviceNameArray = deviceNames.<String>toArray(new String[deviceNames.size()]);
/* 237 */       EntryObject eo = (new PredicateBuilder()).getEntryObject();
/* 238 */       PredicateBuilder pb = eo.get("pdDeviceName").in((Comparable[])deviceNameArray);
/* 239 */       EntryObject entryObject = (new PredicateBuilder()).getEntryObject();
/* 240 */       PredicateBuilder predicateBuilder = entryObject.get("deviceName").in((Comparable[])deviceNameArray);
/*     */       
/* 242 */       for (PdLoopDeviceConfig each : pdLoopDeviceConfigMap.values((Predicate)pb)) {
/* 243 */         LoopDeviceConfigDTO dto = (LoopDeviceConfigDTO)this.modelMapper.map(each, LoopDeviceConfigDTO.class);
/* 244 */         DeviceTcConfig deviceConfig = (DeviceTcConfig)deviceConfigMap.get(each.getDeviceName());
/* 245 */         if (deviceConfig != null) {
/* 246 */           dto.setDisplayName(deviceConfig.getDisplayName());
/* 247 */           dto.setCheck(Boolean.valueOf(true));
/* 248 */           String loopKey = each.getPdDeviceName() + "-" + String.valueOf(each.getLoopNo());
/* 249 */           List<LoopDeviceConfigDTO> configList = loopDeviceMap.get(loopKey);
/* 250 */           if (configList == null) {
/* 251 */             configList = new ArrayList<>();
/*     */           }
/* 253 */           configList.add(dto);
/* 254 */           loopDeviceMap.put(loopKey, configList);
/*     */         } 
/*     */       } 
/*     */       
/* 258 */       for (PdConfig each : pdConfigMap.values((Predicate)predicateBuilder)) {
/* 259 */         LoopDeviceConfigDTO dto = new LoopDeviceConfigDTO();
/* 260 */         dto.setDeviceName(each.getDeviceName());
/* 261 */         dto.setDisplayName(((DeviceTcConfig)deviceConfigMap.get(each.getDeviceName())).getDisplayName());
/* 262 */         dto.setCheck(Boolean.valueOf(false));
/* 263 */         List<LoopDeviceConfigDTO> list = new ArrayList<>();
/* 264 */         if (each.getLoopNo().intValue() > 0) {
/* 265 */           for (int i = 1; i <= each.getLoopNo().intValue(); i++) {
/* 266 */             LoopDeviceConfigDTO loopData = new LoopDeviceConfigDTO();
/* 267 */             loopData.setDeviceName(each.getDeviceName() + "-" + String.valueOf(i));
/* 268 */             loopData.setDisplayName(String.valueOf(i));
/* 269 */             loopData.setLoopNo(Integer.valueOf(i));
/* 270 */             loopData.setCheck(Boolean.valueOf(false));
/* 271 */             loopData.setChildren(loopDeviceMap.get(each.getDeviceName() + "-" + String.valueOf(i)));
/* 272 */             list.add(loopData);
/*     */           } 
/*     */         }
/* 275 */         dto.setChildren(list);
/* 276 */         result.add(dto);
/*     */       } 
/* 278 */       return result;
/*     */     }
/* 280 */     catch (Exception e) {
/* 281 */       logger.error("retrieveLoopDeviceConfig failed.", e);
/* 282 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<LoopDeviceConfigDTO> retrieveUnsetLoopDeviceConfig() {
/*     */     try {
/* 288 */       logger.debug("retrieve unsetLoopDeviceConfig.");
/* 289 */       IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*     */       
/* 291 */       IMap<Long, PdLoopDeviceConfig> pdLoopDeviceConfigMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.LoopDeviceConfig);
/* 292 */       IMap<String, RoadLine> roadLineMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadLine);
/* 293 */       IMap<String, DeviceType> deviceTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 294 */       List<LoopDeviceConfigDTO> result = new ArrayList<>();
/* 295 */       DynamicConfig config = getDynamicConfig("addedPdDeviceType");
/* 296 */       String[] types = config.getValue().split(",");
/* 297 */       EntryObject entryObject = (new PredicateBuilder()).getEntryObject();
/* 298 */       PredicateBuilder predicateBuilder = entryObject.get("deviceType").in((Comparable[])types);
/*     */       
/* 300 */       List<String> allAddedPdDeviceNames = new ArrayList<>();
/*     */       
/* 302 */       for (PdLoopDeviceConfig addedPdDevice : pdLoopDeviceConfigMap.values()) {
/* 303 */         allAddedPdDeviceNames.add(addedPdDevice.getDeviceName());
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 308 */       Map<String, Map<String, List<LoopDeviceConfigDTO>>> typeLineMap = new HashMap<>();
/* 309 */       for (DeviceTcConfig addedDevice : deviceConfigMap.values((Predicate)predicateBuilder)) {
/* 310 */         if (!allAddedPdDeviceNames.contains(addedDevice.getDeviceName())) {
/* 311 */           String deviceType = addedDevice.getDeviceType();
/* 312 */           String lineId = addedDevice.getLineId();
/* 313 */           if (typeLineMap.get(deviceType) == null) {
/* 314 */             typeLineMap.put(deviceType, new HashMap<>());
/*     */           }
/* 316 */           Map<String, List<LoopDeviceConfigDTO>> lineMap = typeLineMap.get(deviceType);
/* 317 */           if (lineId != null && lineMap.get(lineId) == null) {
/* 318 */             lineMap.put(lineId, new ArrayList<>());
/*     */           }
/*     */           
/* 321 */           if (lineId != null) {
/* 322 */             RoadLine road = (RoadLine)roadLineMap.get(lineId);
/* 323 */             List<LoopDeviceConfigDTO> deviceList = lineMap.get(lineId);
/* 324 */             LoopDeviceConfigDTO dto = new LoopDeviceConfigDTO();
/* 325 */             dto.setDisplayName(addedDevice.getDisplayName());
/* 326 */             dto.setCheck(Boolean.valueOf(true));
/* 327 */             dto.setDeviceName(addedDevice.getDeviceName());
/* 328 */             dto.setMemo(addedDevice.getMemo());
/* 329 */             dto.setLineId(addedDevice.getLineId());
/* 330 */             if (road != null) {
/* 331 */               dto.setLineName(road.getLineName());
/*     */             } else {
/* 333 */               dto.setLineName(addedDevice.getLineId());
/*     */             } 
/* 335 */             deviceList.add(dto);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 342 */       Map<String, List<LoopDeviceConfigDTO>> deviceMap = null;
/* 343 */       for (Map.Entry<String, Map<String, List<LoopDeviceConfigDTO>>> each : typeLineMap.entrySet()) {
/* 344 */         deviceMap = new HashMap<>();
/* 345 */         for (Map.Entry<String, List<LoopDeviceConfigDTO>> eachSet : (Iterable<Map.Entry<String, List<LoopDeviceConfigDTO>>>)((Map)each.getValue()).entrySet()) {
/* 346 */           List<LoopDeviceConfigDTO> deviceList = eachSet.getValue();
/* 347 */           Collections.sort(deviceList, new Comparator<LoopDeviceConfigDTO>()
/*     */               {
/*     */                 
/*     */                 public int compare(LoopDeviceConfigDTO o1, LoopDeviceConfigDTO o2)
/*     */                 {
/* 352 */                   Integer o1Mileage = o1.getMileage();
/* 353 */                   Integer o2Mileage = o2.getMileage();
/* 354 */                   if (o1Mileage != null && o2Mileage != null)
/* 355 */                     return o1Mileage.compareTo(o2Mileage); 
/* 356 */                   if (o1Mileage != null && o2Mileage == null)
/* 357 */                     return 1; 
/* 358 */                   if (o1Mileage == null && o2Mileage != null) {
/* 359 */                     return -1;
/*     */                   }
/* 361 */                   return 0;
/*     */                 }
/*     */               });
/*     */           
/* 365 */           deviceMap.put(eachSet.getKey(), deviceList);
/*     */         } 
/* 367 */         typeLineMap.put(each.getKey(), deviceMap);
/*     */       } 
/*     */       
/* 370 */       for (Map.Entry<String, Map<String, List<LoopDeviceConfigDTO>>> each : typeLineMap.entrySet()) {
/* 371 */         LoopDeviceConfigDTO typeData = new LoopDeviceConfigDTO();
/* 372 */         typeData.setCheck(Boolean.valueOf(false));
/* 373 */         typeData.setDeviceName(each.getKey());
/* 374 */         typeData.setDisplayName(((DeviceType)deviceTypeMap.get(each.getKey())).getDescription());
/* 375 */         List<LoopDeviceConfigDTO> lineDataList = new ArrayList<>();
/* 376 */         for (Map.Entry<String, List<LoopDeviceConfigDTO>> lineEach : (Iterable<Map.Entry<String, List<LoopDeviceConfigDTO>>>)((Map)each.getValue()).entrySet()) {
/* 377 */           String lineId = lineEach.getKey();
/* 378 */           if (lineId != null) {
/* 379 */             LoopDeviceConfigDTO lineData = new LoopDeviceConfigDTO();
/* 380 */             lineData.setCheck(Boolean.valueOf(false));
/* 381 */             lineData.setDeviceName((String)each.getKey() + lineId);
/* 382 */             lineData.setDisplayName(((RoadLine)roadLineMap.get(lineId)).getLineName());
/* 383 */             lineData.setChildren(lineEach.getValue());
/* 384 */             lineDataList.add(lineData);
/*     */           } 
/*     */         } 
/* 387 */         Collections.sort(lineDataList, new Comparator<LoopDeviceConfigDTO>()
/*     */             {
/*     */               
/*     */               public int compare(LoopDeviceConfigDTO o1, LoopDeviceConfigDTO o2)
/*     */               {
/* 392 */                 String o1Line = o1.getLineId();
/* 393 */                 String o2Line = o2.getLineId();
/* 394 */                 if (o1Line != null && o2Line != null)
/* 395 */                   return o1Line.compareTo(o2Line); 
/* 396 */                 if (o1Line != null && o2Line == null)
/* 397 */                   return 1; 
/* 398 */                 if (o1Line == null && o2Line != null) {
/* 399 */                   return -1;
/*     */                 }
/* 401 */                 return 0;
/*     */               }
/*     */             });
/*     */         
/* 405 */         typeData.setChildren(lineDataList);
/* 406 */         Collections.sort(lineDataList, new Comparator<LoopDeviceConfigDTO>()
/*     */             {
/*     */               
/*     */               public int compare(LoopDeviceConfigDTO o1, LoopDeviceConfigDTO o2)
/*     */               {
/* 411 */                 return o1.getDeviceName().compareTo(o2.getDeviceName());
/*     */               }
/*     */             });
/* 414 */         result.add(typeData);
/*     */       } 
/*     */       
/* 417 */       logger.debug("result.size:'{}'", Integer.valueOf(result.size()));
/* 418 */       return result;
/* 419 */     } catch (Exception e) {
/* 420 */       logger.error("retrieveUnsetLoopDeviceConfig failed.", e);
/* 421 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateLoopDeviceConfig(String pdDeviceName, List<LoopDeviceConfigDTO> dtos) {
/*     */     try {
/* 427 */       logger.debug("update loopDeviceConfig,pdDevice:'{}'", pdDeviceName);
/*     */       
/* 429 */       IMap<Long, PdLoopDeviceConfig> pdLoopDeviceConfigMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.LoopDeviceConfig);
/* 430 */       Map<Long, PdLoopDeviceConfig> saveMap = new HashMap<>();
/* 431 */       List<Long> removeList = new ArrayList<>();
/* 432 */       if (dtos == null || dtos.size() == 0) {
/* 433 */         logger.error("no LoopDeviceConfigDTO");
/*     */         return;
/*     */       } 
/* 436 */       Integer count = Integer.valueOf(0);
/* 437 */       for (LoopDeviceConfigDTO dto : dtos) {
/* 438 */         EntryObject eo = (new PredicateBuilder()).getEntryObject();
/* 439 */         PredicateBuilder pb = eo.get("deviceName").equal(dto.getDeviceName());
/* 440 */         List<PdLoopDeviceConfig> configList = new ArrayList<>(pdLoopDeviceConfigMap.values((Predicate)pb));
/* 441 */         PdLoopDeviceConfig config = new PdLoopDeviceConfig();
/*     */         
/* 443 */         if (configList.size() == 0) {
/* 444 */           logger.debug("add new PdLoopDeviceConfig.");
/* 445 */           config.setDeviceName(dto.getDeviceName());
/* 446 */           config.setDiameter(dto.getDiameter());
/* 447 */           if (pdDeviceName != "") {
/* 448 */             config.setLoopNo(String.valueOf(dto.getLoopNo()));
/* 449 */             config.setPdDeviceName(pdDeviceName);
/*     */           } 
/* 451 */           config = (PdLoopDeviceConfig)this.pdLoopDeviceConfigRepository.save(config);
/* 452 */           saveMap.put(config.getId(), config);
/*     */         } else {
/*     */           
/* 455 */           logger.debug("update PdLoopDeviceConfig.");
/* 456 */           config = configList.get(0);
/* 457 */           if (pdDeviceName != "") {
/* 458 */             config.setLoopNo(String.valueOf(dto.getLoopNo()));
/* 459 */             config.setPdDeviceName(pdDeviceName);
/* 460 */             saveMap.put(config.getId(), config);
/*     */           } else {
/* 462 */             removeList.add(config.getId());
/*     */           } 
/*     */         } 
/* 465 */         count = Integer.valueOf(count.intValue() + 1);
/*     */       } 
/* 467 */       if (removeList.size() > 0) {
/* 468 */         Long[] ids = removeList.<Long>toArray(new Long[removeList.size()]);
/* 469 */         PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("id").in((Comparable[])ids);
/* 470 */         pdLoopDeviceConfigMap.removeAll((Predicate)pb);
/*     */       } 
/*     */       
/* 473 */       pdLoopDeviceConfigMap.putAll(saveMap);
/* 474 */       logger.debug("updateLoopDeviceConfig update '{}' device", count);
/* 475 */     } catch (Exception e) {
/* 476 */       logger.error("updateLoopDeviceConfig failed.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateLoopDeviceData(LoopDeviceConfigDTO dto) {
/*     */     try {
/* 482 */       logger.debug("update loopDeviceData, deviceName:'{}'", dto.getDeviceName());
/*     */       
/* 484 */       IMap<Long, PdLoopDeviceConfig> pdLoopDeviceConfigMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.LoopDeviceConfig);
/* 485 */       EntryObject eo = (new PredicateBuilder()).getEntryObject();
/* 486 */       PredicateBuilder pb = eo.get("deviceName").equal(dto.getDeviceName());
/* 487 */       PdLoopDeviceConfig config = (new ArrayList<>(pdLoopDeviceConfigMap.values((Predicate)pb))).get(0);
/* 488 */       config.setMemo(dto.getMemo());
/* 489 */       config.setDiameter(dto.getDiameter());
/* 490 */       pdLoopDeviceConfigMap.put(config.getId(), config);
/* 491 */     } catch (Exception e) {
/* 492 */       logger.error("updateLoopDeviceData failed.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<PdStatusDTO> retrievePdStatus() {
/* 497 */     logger.debug("retrieve pdStatus");
/* 498 */     IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 499 */     IMap<String, PdConfig> pdConfigMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.Config);
/* 500 */     IMap<String, PdStatus> pdStatusMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.Status);
/* 501 */     List<PdStatusDTO> result = new ArrayList<>();
/* 502 */     for (PdStatus status : pdStatusMap.values()) {
/* 503 */       PdStatusDTO dto = new PdStatusDTO();
/* 504 */       dto.setPdId(status.getDeviceName());
/* 505 */       dto.setPdDisplayName(((DeviceTcConfig)deviceConfigMap.get(status.getDeviceName())).getDisplayName());
/* 506 */       dto.setLineId(((DeviceTcConfig)deviceConfigMap.get(status.getDeviceName())).getLineId());
/* 507 */       dto.setDataTime(status.getDataTime());
/* 508 */       dto.setConnectivity(status.getConnectivity());
/* 509 */       dto.setDoorOpen(status.getDoorOpen());
/* 510 */       dto.setPrimaryR(status.getPrimaryR());
/* 511 */       dto.setPrimaryS(status.getPrimaryS());
/* 512 */       dto.setPrimaryT(status.getPrimaryT());
/* 513 */       dto.setSecondaryR(status.getSecondaryR());
/* 514 */       dto.setSecondaryS(status.getSecondaryS());
/* 515 */       dto.setSecondaryT(status.getSecondaryT());
/* 516 */       Integer loopNo = ((PdConfig)pdConfigMap.get(status.getDeviceName())).getLoopNo();
/* 517 */       for (PdLoopStatus loopStatus : status.getPdLoops()) {
/* 518 */         if (loopStatus.getLoopId().equals("1") && 
/* 519 */           Integer.valueOf(loopStatus.getLoopId()).intValue() <= loopNo.intValue()) {
/* 520 */           dto.setLoop1Status(loopStatus.getStatus()); continue;
/* 521 */         }  if (loopStatus.getLoopId().equals("2") && 
/* 522 */           Integer.valueOf(loopStatus.getLoopId()).intValue() <= loopNo.intValue()) {
/* 523 */           dto.setLoop2Status(loopStatus.getStatus()); continue;
/* 524 */         }  if (loopStatus.getLoopId().equals("3") && 
/* 525 */           Integer.valueOf(loopStatus.getLoopId()).intValue() <= loopNo.intValue()) {
/* 526 */           dto.setLoop3Status(loopStatus.getStatus()); continue;
/* 527 */         }  if (loopStatus.getLoopId().equals("4") && 
/* 528 */           Integer.valueOf(loopStatus.getLoopId()).intValue() <= loopNo.intValue()) {
/* 529 */           dto.setLoop4Status(loopStatus.getStatus()); continue;
/* 530 */         }  if (loopStatus.getLoopId().equals("5") && 
/* 531 */           Integer.valueOf(loopStatus.getLoopId()).intValue() <= loopNo.intValue()) {
/* 532 */           dto.setLoop5Status(loopStatus.getStatus());
/*     */         }
/*     */       } 
/* 535 */       result.add(dto);
/*     */     } 
/* 537 */     return result;
/*     */   }
/*     */   
/*     */   private DynamicConfig getDynamicConfig(String configName) {
/* 541 */     IMap<DynamicConfigPk, DynamicConfig> dynamicConfigMap = HzUtils.getMap((HzDistObjEnum)HzMap.DynamicConfig);
/*     */     
/* 543 */     DynamicConfig dynamicConfig = (DynamicConfig)dynamicConfigMap.get(new DynamicConfigPk(this.hcceEnv
/* 544 */           .getCurrentGroupName(), "PdFm", configName));
/* 545 */     return dynamicConfig;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\fm-pd-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\pd\fm\service\PdDataService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */