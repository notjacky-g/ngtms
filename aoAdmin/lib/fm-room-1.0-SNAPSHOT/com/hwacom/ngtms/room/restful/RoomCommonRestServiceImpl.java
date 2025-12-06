/*      */ package com.hwacom.ngtms.room.restful;
/*      */ 
/*      */ import com.google.gson.Gson;
/*      */ import com.hazelcast.core.IMap;
/*      */ import com.hazelcast.query.EntryObject;
/*      */ import com.hazelcast.query.Predicate;
/*      */ import com.hazelcast.query.PredicateBuilder;
/*      */ import com.hwacom.ngtms.base.crypto.TripleDESUtils;
/*      */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*      */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*      */ import com.hwacom.ngtms.base.oplog.shared.OperationItem;
/*      */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*      */ import com.hwacom.ngtms.base.util.UuidUtils;
/*      */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*      */ import com.hwacom.ngtms.c.fm.model.DeviceHostLocation;
/*      */ import com.hwacom.ngtms.c.fm.model.DeviceLocationMappingConfig;
/*      */ import com.hwacom.ngtms.c.fm.model.DeviceSvgPositionConfig;
/*      */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*      */ import com.hwacom.ngtms.c.fm.model.DeviceTcStatus;
/*      */ import com.hwacom.ngtms.c.fm.model.DeviceType;
/*      */ import com.hwacom.ngtms.c.fm.service.OpLogger;
/*      */ import com.hwacom.ngtms.c.restful.BaseRestful;
/*      */ import com.hwacom.ngtms.c.shared.SubSystem;
/*      */ import com.hwacom.ngtms.c.shared.dto.DeviceSvgPositionConfigDTO;
/*      */ import com.hwacom.ngtms.c.shared.dto.DeviceTypeDTO;
/*      */ import com.hwacom.ngtms.ccs.fm.hz.CcsHzMap;
/*      */ import com.hwacom.ngtms.ccs.shared.CctvConfig;
/*      */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*      */ import com.hwacom.ngtms.common.fm.model.DeviceConfig;
/*      */ import com.hwacom.ngtms.room.fm.hz.RoomHzMap;
/*      */ import com.hwacom.ngtms.room.fm.model.RoomBackgroundSvgConfig;
/*      */ import com.hwacom.ngtms.room.fm.model.RoomCardConfig;
/*      */ import com.hwacom.ngtms.room.fm.model.RoomCardGroupConfig;
/*      */ import com.hwacom.ngtms.room.fm.model.RoomCardReaderLog;
/*      */ import com.hwacom.ngtms.room.fm.model.RoomCardReaderMappingConfig;
/*      */ import com.hwacom.ngtms.room.fm.repository.RoomCardConfigRepository;
/*      */ import com.hwacom.ngtms.room.fm.repository.RoomCardGroupConfigRepository;
/*      */ import com.hwacom.ngtms.room.fm.repository.RoomCardReaderMappingConfigRepository;
/*      */ import com.hwacom.ngtms.room.service.RoomCardReaderConnectorService;
/*      */ import com.hwacom.ngtms.room.shared.CardStatus;
/*      */ import com.hwacom.ngtms.room.shared.EventCode;
/*      */ import com.hwacom.ngtms.room.shared.RtuConfig;
/*      */ import com.hwacom.ngtms.room.shared.SignalType;
/*      */ import com.hwacom.ngtms.room.shared.dto.RoomBackgroundSvgConfigDTO;
/*      */ import com.hwacom.ngtms.room.shared.dto.RoomCardConfigDTO;
/*      */ import com.hwacom.ngtms.room.shared.dto.RoomCardGroupConfigDTO;
/*      */ import com.hwacom.ngtms.room.shared.dto.RoomCardIssueParam;
/*      */ import com.hwacom.ngtms.room.shared.dto.RoomCardPermissionTreeDTO;
/*      */ import com.hwacom.ngtms.room.shared.dto.RoomCardReaderLogDTO;
/*      */ import com.hwacom.ngtms.room.shared.dto.RoomCardReaderMappingConfigDTO;
/*      */ import com.hwacom.ngtms.room.shared.dto.RoomCctvUrlDTO;
/*      */ import com.hwacom.ngtms.room.shared.dto.RoomDeviceConfigDTO;
/*      */ import com.hwacom.ngtms.room.shared.dto.RoomDeviceLocationConfigDTO;
/*      */ import com.hwacom.ngtms.room.shared.dto.RoomDeviceStatusDTO;
/*      */ import com.hwacom.ngtms.room.shared.dto.RoomDeviceSubLocationConfigDTO;
/*      */ import com.hwacom.ngtms.room.shared.dto.RoomDoDTO;
/*      */ import com.hwacom.ngtms.rtu.fm.hz.RtuHzMap;
/*      */ import com.hwacom.ngtms.rtu.fm.model.ModbusDeviceConfig;
/*      */ import com.hwacom.ngtms.rtu.fm.model.ModbusPinMapping;
/*      */ import com.hwacom.ngtms.rtu.fm.model.ModbusReadConfig;
/*      */ import com.hwacom.ngtms.rtu.shared.ModbusDataType;
/*      */ import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
/*      */ import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
/*      */ import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
/*      */ import java.math.BigInteger;
/*      */ import java.net.InetAddress;
/*      */ import java.net.UnknownHostException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Optional;
/*      */ import java.util.Set;
/*      */ import javax.annotation.PostConstruct;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import org.modelmapper.ModelMapper;
/*      */ import org.slf4j.Logger;
/*      */ import org.slf4j.LoggerFactory;
/*      */ import org.springframework.beans.factory.annotation.Autowired;
/*      */ import org.springframework.web.bind.annotation.CrossOrigin;
/*      */ import org.springframework.web.bind.annotation.PathVariable;
/*      */ import org.springframework.web.bind.annotation.RequestBody;
/*      */ import org.springframework.web.bind.annotation.RequestMapping;
/*      */ import org.springframework.web.bind.annotation.RequestMethod;
/*      */ import org.springframework.web.bind.annotation.RequestParam;
/*      */ import org.springframework.web.bind.annotation.RestController;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @CrossOrigin
/*      */ @RestController
/*      */ @RequestMapping({"/api/room/common"})
/*      */ public class RoomCommonRestServiceImpl
/*      */   extends BaseRestful
/*      */ {
/*  103 */   private static final Logger logger = LoggerFactory.getLogger(RoomCommonRestServiceImpl.class);
/*      */   
/*      */   @Autowired
/*      */   RoomCardGroupConfigRepository roomCardGroupConfigRepository;
/*      */   @Autowired
/*      */   RoomCardConfigRepository roomCardConfigRepository;
/*      */   @Autowired
/*      */   RoomCardReaderMappingConfigRepository roomCardReaderMappingConfigRepository;
/*      */   @Autowired
/*      */   RoomCardReaderConnectorService roomCardReaderConnectorService;
/*      */   @Autowired
/*      */   OpLogger opLogger;
/*      */   @Autowired
/*      */   ModelMapper modelmapper;
/*  117 */   private Gson gson = new Gson();
/*      */   
/*      */   @PostConstruct
/*      */   public void init() {
/*  121 */     setSubSystem(SubSystem.ROOM);
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomCardGroupConfig"}, method = {RequestMethod.GET})
/*      */   public List<RoomCardGroupConfigDTO> getRoomCardGroupConfig() {
/*      */     try {
/*  128 */       logger.debug("get RoomCardGroupConfig.");
/*  129 */       List<RoomCardGroupConfigDTO> result = new ArrayList<>();
/*  130 */       IMap<Long, RoomCardGroupConfig> roomCardGroupMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardGroupConfig);
/*      */       
/*  132 */       for (RoomCardGroupConfig config : roomCardGroupMap.values()) {
/*  133 */         result.add(this.modelmapper.map(config, RoomCardGroupConfigDTO.class));
/*      */       }
/*      */       
/*  136 */       return result;
/*  137 */     } catch (Exception e) {
/*  138 */       logger.error("get RoomCardGroupConfig failed.");
/*  139 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */   
/*      */   @RequestMapping(value = {"/roomCardGroupConfig"}, method = {RequestMethod.POST})
/*      */   public Boolean createRoomCardGroupConfig(@RequestBody RoomCardGroupConfigDTO dto) {
/*      */     try {
/*  146 */       logger.debug("create RoomCardGroupConfig.");
/*  147 */       if (dto == null) {
/*  148 */         logger.error("RoomCardGroupConfigDTO is null");
/*  149 */         return Boolean.valueOf(false);
/*      */       } 
/*  151 */       if (dto.getId() != null) {
/*  152 */         logger.error("the RoomCardGroupConfig id='{}' is already existed.");
/*  153 */         return Boolean.valueOf(false);
/*      */       } 
/*  155 */       IMap<Long, RoomCardGroupConfig> roomCardGroupMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardGroupConfig);
/*      */       
/*  157 */       RoomCardGroupConfig saveConfig = (RoomCardGroupConfig)this.roomCardGroupConfigRepository.save(this.modelmapper.map(dto, RoomCardGroupConfig.class));
/*  158 */       roomCardGroupMap.put(saveConfig.getId(), saveConfig);
/*      */       
/*  160 */       return Boolean.valueOf(true);
/*  161 */     } catch (Exception e) {
/*  162 */       logger.error("create RoomCardGroupConfig failed.");
/*  163 */       return Boolean.valueOf(false);
/*      */     } 
/*      */   }
/*      */   
/*      */   @RequestMapping(value = {"/roomCardGroupConfig"}, method = {RequestMethod.PUT})
/*      */   public Boolean updateRoomCardGroupConfig(@RequestBody RoomCardGroupConfigDTO dto) {
/*      */     try {
/*  170 */       logger.debug("update RoomCardGroupConfig.");
/*  171 */       if (dto == null) {
/*  172 */         logger.error("RoomCardGroupConfigDTO is null");
/*  173 */         return Boolean.valueOf(false);
/*      */       } 
/*  175 */       if (dto.getId() == null) {
/*  176 */         logger.error("the RoomCardGroupConfig id is null.");
/*  177 */         return Boolean.valueOf(false);
/*      */       } 
/*  179 */       IMap<Long, RoomCardGroupConfig> roomCardGroupMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardGroupConfig);
/*  180 */       if (!roomCardGroupMap.containsKey(dto.getId())) {
/*  181 */         logger.error("RoomCardGroupConfig id='{}' is not existed.", dto.getId());
/*  182 */         return Boolean.valueOf(false);
/*      */       } 
/*  184 */       roomCardGroupMap.put(dto.getId(), this.modelmapper.map(dto, RoomCardGroupConfig.class));
/*      */       
/*  186 */       return Boolean.valueOf(true);
/*  187 */     } catch (Exception e) {
/*  188 */       logger.error("update RoomCardGroupConfig failed.");
/*  189 */       return Boolean.valueOf(false);
/*      */     } 
/*      */   }
/*      */   
/*      */   @RequestMapping(value = {"/roomCardGroupConfig/{id}"}, method = {RequestMethod.DELETE})
/*      */   public Boolean deleteRoomCardGroupConfig(@PathVariable("id") Long id) {
/*      */     try {
/*  196 */       logger.debug("delete RoomCardGroupConfig.");
/*  197 */       if (id == null) {
/*  198 */         logger.error("RoomCardGroupConfig id is null");
/*  199 */         return Boolean.valueOf(false);
/*      */       } 
/*  201 */       IMap<Long, RoomCardGroupConfig> roomCardGroupMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardGroupConfig);
/*  202 */       if (!roomCardGroupMap.containsKey(id)) {
/*  203 */         logger.error("RoomCardGroupConfig id='{}' is not existed.", id);
/*  204 */         return Boolean.valueOf(false);
/*      */       } 
/*  206 */       roomCardGroupMap.delete(id);
/*      */       
/*  208 */       return Boolean.valueOf(true);
/*  209 */     } catch (Exception e) {
/*  210 */       logger.error("delete RoomCardGroupConfig failed.");
/*  211 */       return Boolean.valueOf(false);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomBackgroundSvgConfig"}, method = {RequestMethod.GET})
/*      */   public List<RoomBackgroundSvgConfigDTO> getBackgroundSvgConfig() {
/*      */     try {
/*  219 */       logger.debug("Get BackgroundSvgConfig.");
/*      */       
/*  221 */       IMap<String, RoomBackgroundSvgConfig> configMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.BackgroundSvgConfig);
/*  222 */       Map<String, RoomBackgroundSvgConfigDTO> result = new HashMap<>();
/*      */       
/*  224 */       configMap
/*  225 */         .values()
/*  226 */         .forEach(config -> {
/*      */             RoomBackgroundSvgConfigDTO dto = (RoomBackgroundSvgConfigDTO)this.modelmapper.map(config, RoomBackgroundSvgConfigDTO.class);
/*      */             
/*      */             paramMap.put(dto.getNameId(), dto);
/*      */           });
/*      */       
/*  232 */       return new ArrayList<>(result.values());
/*  233 */     } catch (RuntimeException e) {
/*  234 */       logger.debug("Get BackgroundSvgConfig failed.");
/*  235 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomDevicePositionConfig/{svgName}"}, method = {RequestMethod.GET})
/*      */   public List<DeviceSvgPositionConfigDTO> queryDevicePositionConfig(@PathVariable("svgName") String svgName) {
/*      */     try {
/*  248 */       logger.debug("QueryDevicePositionConfig, svgName='{}'", svgName);
/*  249 */       List<DeviceSvgPositionConfigDTO> result = new ArrayList<>();
/*      */ 
/*      */       
/*  252 */       IMap<String, DeviceLocationMappingConfig> locationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/*      */ 
/*      */       
/*  255 */       IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*      */ 
/*      */ 
/*      */       
/*  259 */       IMap<String, DeviceSvgPositionConfig> positionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DevicePositionConfig);
/*      */ 
/*      */ 
/*      */       
/*  263 */       PredicateBuilder predicate = (new PredicateBuilder()).getEntryObject().get("svgName").equal(svgName);
/*      */       
/*  265 */       locationMap
/*  266 */         .values((Predicate)predicate)
/*  267 */         .stream()
/*  268 */         .forEach(location -> {
/*      */             try {
/*      */               if (location.getDeviceName() != null) {
/*      */                 String deviceName = location.getDeviceName();
/*      */                 
/*      */                 DeviceTcConfig tcConfig = (DeviceTcConfig)paramIMap1.get(deviceName);
/*      */                 
/*      */                 EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*      */                 
/*      */                 PredicateBuilder pb = eo.get("deviceName").equal(deviceName).and((Predicate)eo.get("svgName").equal(paramString));
/*      */                 
/*      */                 if (paramIMap2.values((Predicate)pb).isEmpty()) {
/*      */                   DeviceSvgPositionConfigDTO dto = new DeviceSvgPositionConfigDTO();
/*      */                   
/*      */                   dto.setId(UuidUtils.generateUUIDStr());
/*      */                   dto.setSvgName(paramString);
/*      */                   dto.setDeviceName(deviceName);
/*      */                   dto.setDisplayName(tcConfig.getDisplayName());
/*      */                   dto.setDeviceType(tcConfig.getDeviceType());
/*      */                   dto.setTrafficDirection(null);
/*      */                   dto.setPositionX(Float.valueOf(0.0F));
/*      */                   dto.setPositionY(Float.valueOf(0.0F));
/*      */                   paramList.add(dto);
/*      */                 } else {
/*      */                   for (DeviceSvgPositionConfig each : paramIMap2.values((Predicate)pb)) {
/*      */                     DeviceSvgPositionConfigDTO dto = new DeviceSvgPositionConfigDTO();
/*      */                     dto = (DeviceSvgPositionConfigDTO)this.modelmapper.map(each, DeviceSvgPositionConfigDTO.class);
/*      */                     dto.setDisplayName(tcConfig.getDisplayName());
/*      */                     dto.setDeviceType(tcConfig.getDeviceType());
/*      */                     paramList.add(dto);
/*      */                   } 
/*      */                 } 
/*      */               } 
/*  301 */             } catch (Exception e) {
/*      */               logger.error("QueryDevicePositionConfig failed , deviceName not Exit", e);
/*      */             } 
/*      */           });
/*  305 */       return result;
/*  306 */     } catch (RuntimeException e) {
/*  307 */       logger.error("QueryDevicePositionConfig failed.", e);
/*  308 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomDevicePositionConfig"}, method = {RequestMethod.POST})
/*      */   public Boolean saveRoomDevicePositionConfigs(HttpServletRequest request, @RequestBody List<DeviceSvgPositionConfigDTO> list) {
/*  320 */     boolean result = true;
/*      */     try {
/*  322 */       logger.debug("Save RoomDevicePositionConfig, size='{}'", Integer.valueOf(list.size()));
/*      */       
/*  324 */       IMap<String, DeviceSvgPositionConfig> configMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DevicePositionConfig);
/*  325 */       list.forEach(dto -> {
/*      */             DeviceSvgPositionConfig config = (DeviceSvgPositionConfig)this.modelmapper.map(dto, DeviceSvgPositionConfig.class);
/*      */             
/*      */             if (config.getId() == null) {
/*      */               config.setId(UuidUtils.generateUUIDStr());
/*      */             }
/*      */             paramIMap.put(config.getId(), config);
/*      */           });
/*  333 */     } catch (Exception e) {
/*  334 */       logger.error("Save RoomDevicePositionConfig failed, size='{}'", Integer.valueOf(list.size()), e);
/*  335 */       result = false;
/*      */     } 
/*  337 */     return Boolean.valueOf(result);
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomDeviceLocationConfig"}, method = {RequestMethod.GET})
/*      */   public List<RoomDeviceLocationConfigDTO> getRoomDeviceLocationConfigData() {
/*      */     try {
/*  344 */       logger.debug("Get RoomDeviceLocationConfigData.");
/*      */       
/*  346 */       List<RoomDeviceLocationConfigDTO> result = new ArrayList<>();
/*      */ 
/*      */       
/*  349 */       IMap<String, DeviceLocationMappingConfig> roomLocationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/*      */ 
/*      */       
/*  352 */       IMap<Integer, DeviceHostLocation> locationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceHostLocation);
/*      */       
/*  354 */       Set<String> subLocationName = new HashSet<>();
/*  355 */       for (DeviceHostLocation hostLocation : locationMap.values()) {
/*  356 */         String locName = hostLocation.getLocName();
/*  357 */         RoomDeviceLocationConfigDTO dto = new RoomDeviceLocationConfigDTO();
/*  358 */         dto.setId(hostLocation.getId());
/*  359 */         dto.setLocationName(locName);
/*      */ 
/*      */         
/*  362 */         PredicateBuilder predicate = (new PredicateBuilder()).getEntryObject().get("locationName").equal(locName);
/*  363 */         roomLocationMap
/*  364 */           .values((Predicate)predicate)
/*  365 */           .stream()
/*  366 */           .forEach(roomData -> paramSet.add(roomData.getSubLocation()));
/*      */ 
/*      */ 
/*      */         
/*  370 */         dto.setSubLocation(new ArrayList<>(subLocationName));
/*  371 */         result.add(dto);
/*  372 */         subLocationName.clear();
/*      */       } 
/*  374 */       return result;
/*  375 */     } catch (Exception e) {
/*  376 */       logger.error("Get RoomDeviceLocationConfigData failed.", e);
/*  377 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomDeviceLocationConfig/{hostId}/{subLocationName}"}, method = {RequestMethod.GET})
/*      */   public RoomBackgroundSvgConfigDTO getRoomLocationMapConfig(@PathVariable("hostId") Integer hostId, @PathVariable("subLocationName") String subLocationName) {
/*      */     try {
/*  395 */       logger.debug("Get RoomLocationMapConfig. hostId = '{}',hostId ='{}'", hostId, subLocationName);
/*      */       
/*  397 */       if (hostId == null) {
/*  398 */         throw new IllegalArgumentException("hostId is null");
/*      */       }
/*  400 */       RoomBackgroundSvgConfigDTO dto = new RoomBackgroundSvgConfigDTO();
/*      */ 
/*      */       
/*  403 */       IMap<String, DeviceLocationMappingConfig> roomLocationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/*      */ 
/*      */       
/*  406 */       IMap<Integer, DeviceHostLocation> locationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceHostLocation);
/*  407 */       if (locationMap.containsKey(hostId)) {
/*  408 */         String locName = ((DeviceHostLocation)locationMap.get(hostId)).getLocName();
/*      */         
/*  410 */         EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*  411 */         PredicateBuilder predicate = null;
/*  412 */         if (!subLocationName.equals("null") && !subLocationName.isEmpty()) {
/*      */ 
/*      */ 
/*      */           
/*  416 */           predicate = eo.get("locationName").equal(locName).and((Predicate)eo.get("subLocation").equal(subLocationName));
/*      */         } else {
/*  418 */           predicate = eo.get("locationName").equal(locName);
/*      */         } 
/*      */         
/*  421 */         Optional<DeviceLocationMappingConfig> config = roomLocationMap.values((Predicate)predicate).stream().findFirst();
/*  422 */         if (config.isPresent()) {
/*  423 */           dto.setNameId(((DeviceLocationMappingConfig)config.get()).getSvgName());
/*      */         }
/*      */       } 
/*  426 */       return dto;
/*  427 */     } catch (Exception e) {
/*  428 */       logger.error("Get RoomLocationMapConfig failed.", e);
/*  429 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomDeviceStatusConfig/{deviceNameList}"}, method = {RequestMethod.GET})
/*      */   public List<RoomDeviceStatusDTO> refreshRoomDeviceStatus(@RequestParam("deviceNameList") List<String> deviceNameList) {
/*      */     // Byte code:
/*      */     //   0: getstatic com/hwacom/ngtms/room/restful/RoomCommonRestServiceImpl.logger : Lorg/slf4j/Logger;
/*      */     //   3: ldc 'Refresh RoomDeviceStatus.'
/*      */     //   5: invokeinterface debug : (Ljava/lang/String;)V
/*      */     //   10: new java/util/ArrayList
/*      */     //   13: dup
/*      */     //   14: invokespecial <init> : ()V
/*      */     //   17: astore_2
/*      */     //   18: getstatic com/hwacom/ngtms/c/fm/hz/CommonFmHzMap.DeviceTcStatus : Lcom/hwacom/ngtms/c/fm/hz/CommonFmHzMap;
/*      */     //   21: invokestatic getMap : (Lcom/hwacom/ngtms/base/hazelcast/HzDistObjEnum;)Lcom/hazelcast/core/IMap;
/*      */     //   24: astore_3
/*      */     //   25: getstatic com/hwacom/ngtms/c/fm/hz/CommonFmHzMap.DeviceTcConfig : Lcom/hwacom/ngtms/c/fm/hz/CommonFmHzMap;
/*      */     //   28: invokestatic getMap : (Lcom/hwacom/ngtms/base/hazelcast/HzDistObjEnum;)Lcom/hazelcast/core/IMap;
/*      */     //   31: astore #4
/*      */     //   33: aload_1
/*      */     //   34: ifnull -> 667
/*      */     //   37: aload_1
/*      */     //   38: invokeinterface isEmpty : ()Z
/*      */     //   43: ifne -> 667
/*      */     //   46: aload_1
/*      */     //   47: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   52: astore #5
/*      */     //   54: aload #5
/*      */     //   56: invokeinterface hasNext : ()Z
/*      */     //   61: ifeq -> 667
/*      */     //   64: aload #5
/*      */     //   66: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   71: checkcast java/lang/String
/*      */     //   74: astore #6
/*      */     //   76: aload #4
/*      */     //   78: aload #6
/*      */     //   80: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   85: checkcast com/hwacom/ngtms/c/fm/model/DeviceTcConfig
/*      */     //   88: invokevirtual getDeviceType : ()Ljava/lang/String;
/*      */     //   91: astore #7
/*      */     //   93: aload_3
/*      */     //   94: aload #6
/*      */     //   96: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   101: ifnull -> 141
/*      */     //   104: aload_3
/*      */     //   105: aload #6
/*      */     //   107: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   112: checkcast com/hwacom/ngtms/c/fm/model/DeviceTcStatus
/*      */     //   115: invokevirtual getCommStatus : ()Ljava/lang/Integer;
/*      */     //   118: ifnull -> 141
/*      */     //   121: aload_3
/*      */     //   122: aload #6
/*      */     //   124: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   129: checkcast com/hwacom/ngtms/c/fm/model/DeviceTcStatus
/*      */     //   132: invokevirtual getCommStatus : ()Ljava/lang/Integer;
/*      */     //   135: invokevirtual intValue : ()I
/*      */     //   138: goto -> 142
/*      */     //   141: iconst_1
/*      */     //   142: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   145: astore #8
/*      */     //   147: aconst_null
/*      */     //   148: astore #9
/*      */     //   150: ldc 'cardReader'
/*      */     //   152: aload #7
/*      */     //   154: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   157: ifne -> 189
/*      */     //   160: aload_0
/*      */     //   161: getfield gson : Lcom/google/gson/Gson;
/*      */     //   164: aload #4
/*      */     //   166: aload #6
/*      */     //   168: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   173: checkcast com/hwacom/ngtms/c/fm/model/DeviceTcConfig
/*      */     //   176: invokevirtual getExtend : ()Ljava/lang/String;
/*      */     //   179: ldc com/hwacom/ngtms/room/shared/RtuConfig
/*      */     //   181: invokevirtual fromJson : (Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
/*      */     //   184: checkcast com/hwacom/ngtms/room/shared/RtuConfig
/*      */     //   187: astore #9
/*      */     //   189: new com/hwacom/ngtms/room/shared/dto/RoomDeviceStatusDTO
/*      */     //   192: dup
/*      */     //   193: invokespecial <init> : ()V
/*      */     //   196: astore #10
/*      */     //   198: aload #10
/*      */     //   200: aload #6
/*      */     //   202: invokevirtual setDeviceName : (Ljava/lang/String;)V
/*      */     //   205: aload #10
/*      */     //   207: aload #7
/*      */     //   209: invokevirtual setDeviceType : (Ljava/lang/String;)V
/*      */     //   212: aload #10
/*      */     //   214: aload #8
/*      */     //   216: invokevirtual setStatus : (Ljava/lang/Integer;)V
/*      */     //   219: aload #8
/*      */     //   221: invokevirtual intValue : ()I
/*      */     //   224: iconst_1
/*      */     //   225: if_icmpne -> 258
/*      */     //   228: aload #7
/*      */     //   230: ldc 'CCTV'
/*      */     //   232: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   235: ifne -> 248
/*      */     //   238: aload #10
/*      */     //   240: ldc '斷線'
/*      */     //   242: invokevirtual setStatusContent : (Ljava/lang/String;)V
/*      */     //   245: goto -> 655
/*      */     //   248: aload #10
/*      */     //   250: ldc ''
/*      */     //   252: invokevirtual setStatusContent : (Ljava/lang/String;)V
/*      */     //   255: goto -> 655
/*      */     //   258: aload #8
/*      */     //   260: invokevirtual intValue : ()I
/*      */     //   263: ifne -> 655
/*      */     //   266: aload #9
/*      */     //   268: ifnull -> 648
/*      */     //   271: getstatic com/hwacom/ngtms/room/shared/SignalType.DIGITAL_IN : Lcom/hwacom/ngtms/room/shared/SignalType;
/*      */     //   274: invokevirtual name : ()Ljava/lang/String;
/*      */     //   277: aload #9
/*      */     //   279: invokevirtual getSignalType : ()Ljava/lang/String;
/*      */     //   282: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   285: ifeq -> 359
/*      */     //   288: aload_3
/*      */     //   289: aload #6
/*      */     //   291: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   296: checkcast com/hwacom/ngtms/c/fm/model/DeviceTcStatus
/*      */     //   299: invokevirtual getContextData : ()[B
/*      */     //   302: invokestatic deserialize : ([B)Ljava/lang/Object;
/*      */     //   305: checkcast java/lang/Integer
/*      */     //   308: astore #11
/*      */     //   310: aload #11
/*      */     //   312: invokevirtual intValue : ()I
/*      */     //   315: iconst_1
/*      */     //   316: if_icmpne -> 349
/*      */     //   319: aload #7
/*      */     //   321: ldc '氣冷式冰水主機'
/*      */     //   323: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   326: ifeq -> 339
/*      */     //   329: aload #10
/*      */     //   331: ldc '運轉'
/*      */     //   333: invokevirtual setStatusContent : (Ljava/lang/String;)V
/*      */     //   336: goto -> 356
/*      */     //   339: aload #10
/*      */     //   341: ldc '啟動'
/*      */     //   343: invokevirtual setStatusContent : (Ljava/lang/String;)V
/*      */     //   346: goto -> 356
/*      */     //   349: aload #10
/*      */     //   351: ldc ''
/*      */     //   353: invokevirtual setStatusContent : (Ljava/lang/String;)V
/*      */     //   356: goto -> 655
/*      */     //   359: getstatic com/hwacom/ngtms/room/shared/SignalType.ANALOG_IN : Lcom/hwacom/ngtms/room/shared/SignalType;
/*      */     //   362: invokevirtual name : ()Ljava/lang/String;
/*      */     //   365: aload #9
/*      */     //   367: invokevirtual getSignalType : ()Ljava/lang/String;
/*      */     //   370: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   373: ifeq -> 558
/*      */     //   376: aload_3
/*      */     //   377: aload #6
/*      */     //   379: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   384: checkcast com/hwacom/ngtms/c/fm/model/DeviceTcStatus
/*      */     //   387: invokevirtual getContextData : ()[B
/*      */     //   390: ifnull -> 548
/*      */     //   393: aload #7
/*      */     //   395: ldc 'humidity'
/*      */     //   397: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   400: ifne -> 413
/*      */     //   403: aload #7
/*      */     //   405: ldc 'temperature'
/*      */     //   407: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   410: ifeq -> 496
/*      */     //   413: aload_3
/*      */     //   414: aload #6
/*      */     //   416: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   421: checkcast com/hwacom/ngtms/c/fm/model/DeviceTcStatus
/*      */     //   424: invokevirtual getContextData : ()[B
/*      */     //   427: invokestatic deserialize : ([B)Ljava/lang/Object;
/*      */     //   430: checkcast java/lang/Integer
/*      */     //   433: invokevirtual intValue : ()I
/*      */     //   436: iconst_m1
/*      */     //   437: if_icmpeq -> 496
/*      */     //   440: aload #10
/*      */     //   442: new java/lang/StringBuilder
/*      */     //   445: dup
/*      */     //   446: invokespecial <init> : ()V
/*      */     //   449: aload_3
/*      */     //   450: aload #6
/*      */     //   452: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   457: checkcast com/hwacom/ngtms/c/fm/model/DeviceTcStatus
/*      */     //   460: invokevirtual getContextData : ()[B
/*      */     //   463: invokestatic deserialize : ([B)Ljava/lang/Object;
/*      */     //   466: checkcast java/lang/Integer
/*      */     //   469: invokevirtual intValue : ()I
/*      */     //   472: i2d
/*      */     //   473: invokevirtual append : (D)Ljava/lang/StringBuilder;
/*      */     //   476: aload #9
/*      */     //   478: invokevirtual getUnit : ()Ljava/lang/String;
/*      */     //   481: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   484: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   487: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
/*      */     //   490: invokevirtual setStatusContent : (Ljava/lang/String;)V
/*      */     //   493: goto -> 655
/*      */     //   496: aload #10
/*      */     //   498: new java/lang/StringBuilder
/*      */     //   501: dup
/*      */     //   502: invokespecial <init> : ()V
/*      */     //   505: aload_3
/*      */     //   506: aload #6
/*      */     //   508: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   513: checkcast com/hwacom/ngtms/c/fm/model/DeviceTcStatus
/*      */     //   516: invokevirtual getContextData : ()[B
/*      */     //   519: invokestatic deserialize : ([B)Ljava/lang/Object;
/*      */     //   522: checkcast java/lang/Integer
/*      */     //   525: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*      */     //   528: aload #9
/*      */     //   530: invokevirtual getUnit : ()Ljava/lang/String;
/*      */     //   533: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   536: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   539: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
/*      */     //   542: invokevirtual setStatusContent : (Ljava/lang/String;)V
/*      */     //   545: goto -> 655
/*      */     //   548: aload #10
/*      */     //   550: ldc ''
/*      */     //   552: invokevirtual setStatusContent : (Ljava/lang/String;)V
/*      */     //   555: goto -> 655
/*      */     //   558: getstatic com/hwacom/ngtms/room/shared/SignalType.DIGITAL_OUT : Lcom/hwacom/ngtms/room/shared/SignalType;
/*      */     //   561: invokevirtual name : ()Ljava/lang/String;
/*      */     //   564: aload #9
/*      */     //   566: invokevirtual getSignalType : ()Ljava/lang/String;
/*      */     //   569: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   572: ifeq -> 655
/*      */     //   575: aload_3
/*      */     //   576: aload #6
/*      */     //   578: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   583: checkcast com/hwacom/ngtms/c/fm/model/DeviceTcStatus
/*      */     //   586: invokevirtual getContextData : ()[B
/*      */     //   589: ifnull -> 638
/*      */     //   592: aload_3
/*      */     //   593: aload #6
/*      */     //   595: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   600: checkcast com/hwacom/ngtms/c/fm/model/DeviceTcStatus
/*      */     //   603: invokevirtual getContextData : ()[B
/*      */     //   606: invokestatic deserialize : ([B)Ljava/lang/Object;
/*      */     //   609: checkcast java/lang/Integer
/*      */     //   612: astore #11
/*      */     //   614: aload #10
/*      */     //   616: aload #11
/*      */     //   618: invokevirtual intValue : ()I
/*      */     //   621: iconst_1
/*      */     //   622: if_icmpne -> 630
/*      */     //   625: ldc '開啟'
/*      */     //   627: goto -> 632
/*      */     //   630: ldc '關閉'
/*      */     //   632: invokevirtual setStatusContent : (Ljava/lang/String;)V
/*      */     //   635: goto -> 655
/*      */     //   638: aload #10
/*      */     //   640: ldc ''
/*      */     //   642: invokevirtual setStatusContent : (Ljava/lang/String;)V
/*      */     //   645: goto -> 655
/*      */     //   648: aload #10
/*      */     //   650: ldc ''
/*      */     //   652: invokevirtual setStatusContent : (Ljava/lang/String;)V
/*      */     //   655: aload_2
/*      */     //   656: aload #10
/*      */     //   658: invokeinterface add : (Ljava/lang/Object;)Z
/*      */     //   663: pop
/*      */     //   664: goto -> 54
/*      */     //   667: aload_2
/*      */     //   668: areturn
/*      */     //   669: astore_2
/*      */     //   670: getstatic com/hwacom/ngtms/room/restful/RoomCommonRestServiceImpl.logger : Lorg/slf4j/Logger;
/*      */     //   673: ldc 'Refresh RoomDeviceStatus failed.'
/*      */     //   675: aload_2
/*      */     //   676: invokeinterface error : (Ljava/lang/String;Ljava/lang/Throwable;)V
/*      */     //   681: invokestatic emptyList : ()Ljava/util/List;
/*      */     //   684: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #442	-> 0
/*      */     //   #443	-> 10
/*      */     //   #445	-> 18
/*      */     //   #447	-> 25
/*      */     //   #448	-> 33
/*      */     //   #449	-> 46
/*      */     //   #450	-> 76
/*      */     //   #451	-> 93
/*      */     //   #452	-> 96
/*      */     //   #453	-> 124
/*      */     //   #452	-> 142
/*      */     //   #456	-> 147
/*      */     //   #457	-> 150
/*      */     //   #458	-> 160
/*      */     //   #460	-> 189
/*      */     //   #461	-> 198
/*      */     //   #462	-> 205
/*      */     //   #464	-> 212
/*      */     //   #465	-> 219
/*      */     //   #466	-> 228
/*      */     //   #467	-> 238
/*      */     //   #469	-> 248
/*      */     //   #471	-> 258
/*      */     //   #472	-> 266
/*      */     //   #473	-> 271
/*      */     //   #474	-> 288
/*      */     //   #476	-> 291
/*      */     //   #477	-> 310
/*      */     //   #478	-> 319
/*      */     //   #479	-> 329
/*      */     //   #481	-> 339
/*      */     //   #484	-> 349
/*      */     //   #486	-> 356
/*      */     //   #487	-> 376
/*      */     //   #488	-> 393
/*      */     //   #491	-> 416
/*      */     //   #490	-> 427
/*      */     //   #489	-> 433
/*      */     //   #493	-> 440
/*      */     //   #498	-> 452
/*      */     //   #497	-> 463
/*      */     //   #496	-> 469
/*      */     //   #499	-> 478
/*      */     //   #494	-> 487
/*      */     //   #493	-> 490
/*      */     //   #501	-> 496
/*      */     //   #505	-> 508
/*      */     //   #504	-> 519
/*      */     //   #506	-> 530
/*      */     //   #502	-> 539
/*      */     //   #501	-> 542
/*      */     //   #509	-> 548
/*      */     //   #511	-> 558
/*      */     //   #512	-> 575
/*      */     //   #513	-> 592
/*      */     //   #516	-> 595
/*      */     //   #515	-> 606
/*      */     //   #517	-> 614
/*      */     //   #518	-> 635
/*      */     //   #519	-> 638
/*      */     //   #523	-> 648
/*      */     //   #526	-> 655
/*      */     //   #527	-> 664
/*      */     //   #529	-> 667
/*      */     //   #530	-> 669
/*      */     //   #531	-> 670
/*      */     //   #532	-> 681
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   310	46	11	value	Ljava/lang/Integer;
/*      */     //   614	21	11	value	Ljava/lang/Integer;
/*      */     //   93	571	7	deviceType	Ljava/lang/String;
/*      */     //   147	517	8	commStatus	Ljava/lang/Integer;
/*      */     //   150	514	9	config	Lcom/hwacom/ngtms/room/shared/RtuConfig;
/*      */     //   198	466	10	dto	Lcom/hwacom/ngtms/room/shared/dto/RoomDeviceStatusDTO;
/*      */     //   76	588	6	deviceName	Ljava/lang/String;
/*      */     //   18	651	2	result	Ljava/util/List;
/*      */     //   25	644	3	statusMap	Lcom/hazelcast/core/IMap;
/*      */     //   33	636	4	deviceMap	Lcom/hazelcast/core/IMap;
/*      */     //   670	15	2	e	Ljava/lang/Exception;
/*      */     //   0	685	0	this	Lcom/hwacom/ngtms/room/restful/RoomCommonRestServiceImpl;
/*      */     //   0	685	1	deviceNameList	Ljava/util/List;
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   18	651	2	result	Ljava/util/List<Lcom/hwacom/ngtms/room/shared/dto/RoomDeviceStatusDTO;>;
/*      */     //   25	644	3	statusMap	Lcom/hazelcast/core/IMap<Ljava/lang/String;Lcom/hwacom/ngtms/c/fm/model/DeviceTcStatus;>;
/*      */     //   33	636	4	deviceMap	Lcom/hazelcast/core/IMap<Ljava/lang/String;Lcom/hwacom/ngtms/c/fm/model/DeviceTcConfig;>;
/*      */     //   0	685	1	deviceNameList	Ljava/util/List<Ljava/lang/String;>;
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   0	668	669	java/lang/Exception
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomCctvUrl"}, method = {RequestMethod.GET})
/*      */   public String getRoomCctvUrl() {
/*      */     try {
/*  540 */       logger.debug("Get RoomCctvUrl.");
/*  541 */       String url = null;
/*  542 */       return url;
/*  543 */     } catch (Exception e) {
/*  544 */       logger.error("Get RoomCctvUrl failed.", e);
/*  545 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomCardConfig"}, method = {RequestMethod.GET})
/*      */   public List<RoomCardConfigDTO> getRoomCardConfig() {
/*      */     try {
/*  553 */       logger.debug("getRoomCardConfig.");
/*  554 */       List<RoomCardConfigDTO> result = new ArrayList<>();
/*  555 */       IMap<String, RoomCardConfig> roomCardConfigMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardConfig);
/*  556 */       for (RoomCardConfig config : roomCardConfigMap.values()) {
/*  557 */         result.add(this.modelmapper.map(config, RoomCardConfigDTO.class));
/*      */       }
/*      */       
/*  560 */       return result;
/*  561 */     } catch (Exception e) {
/*  562 */       logger.error("get RoomCardConfig failed.", e);
/*  563 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomCardConfig"}, method = {RequestMethod.POST})
/*      */   public Boolean createRoomCardConfig(@RequestBody RoomCardConfigDTO dto) {
/*      */     try {
/*  571 */       logger.debug("createRoomCardConfig.");
/*  572 */       if (dto == null) {
/*  573 */         logger.error("createRoomCardConfig failed, RoomCardConfigDTO is null");
/*  574 */         return Boolean.valueOf(false);
/*      */       } 
/*  576 */       if (dto.getAba() == null) {
/*  577 */         logger.error("createRoomCardConfig failed, aba is null");
/*  578 */         return Boolean.valueOf(false);
/*      */       } 
/*  580 */       if (dto.getCardType() == null) {
/*  581 */         logger.error("createRoomCardConfig failed, cardType is null");
/*  582 */         return Boolean.valueOf(false);
/*      */       } 
/*  584 */       IMap<String, RoomCardConfig> roomCardConfigMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardConfig);
/*  585 */       RoomCardConfig config = (RoomCardConfig)this.modelmapper.map(dto, RoomCardConfig.class);
/*  586 */       config.setIssued(Boolean.valueOf(false));
/*  587 */       config.setCardStatus(CardStatus.DISABLE);
/*  588 */       RoomCardConfig newCard = (RoomCardConfig)this.roomCardConfigRepository.save(config);
/*  589 */       roomCardConfigMap.put(newCard.getId(), newCard);
/*      */       
/*  591 */       return Boolean.valueOf(true);
/*  592 */     } catch (Exception e) {
/*  593 */       logger.error("create RoomCardConfig failed.", e);
/*  594 */       return Boolean.valueOf(false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomCardConfig"}, method = {RequestMethod.PUT})
/*      */   public String updateRoomCardConfig(HttpServletRequest req, @RequestBody RoomCardIssueParam param) {
/*      */     try {
/*  603 */       logger.debug("update RoomCardConfig.");
/*  604 */       List<String> addFailDevices = new ArrayList<>();
/*  605 */       RoomCardConfigDTO dto = param.getRoomCardConfigDto();
/*  606 */       if (dto.getId() == null) {
/*  607 */         logger.error("update RoomCard failed, id == null");
/*  608 */         return null;
/*      */       } 
/*  610 */       if (dto.getAba() == null || dto.getAba().length() != 10) {
/*  611 */         logger.error("update RoomCard failed, aba:'{}'", dto.getAba());
/*  612 */         return null;
/*      */       } 
/*  614 */       String userId = TripleDESUtils.decrypt(req.getHeader("encryptedUserLogin"));
/*  615 */       IMap<String, RoomCardConfig> roomCardConfigMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardConfig);
/*      */       
/*  617 */       IMap<String, RoomCardReaderMappingConfig> cardMappingMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardReaderMappingConfig);
/*  618 */       Map<String, Boolean> cardPermisionMap = param.getCardReaderAddCardMap();
/*  619 */       List<String> addCardDeviceLsit = new ArrayList<>();
/*  620 */       List<String> deleteCardDeviceList = new ArrayList<>();
/*  621 */       Date now = new Date();
/*  622 */       Date startDate = dto.getStartDate();
/*  623 */       Date endDate = dto.getEndDate();
/*  624 */       RoomCardConfig updateCard = (RoomCardConfig)this.modelmapper.map(dto, RoomCardConfig.class);
/*  625 */       roomCardConfigMap.put(updateCard.getId(), updateCard);
/*  626 */       if (cardPermisionMap != null && cardPermisionMap.size() != 0) {
/*  627 */         for (Map.Entry<String, Boolean> entry : cardPermisionMap.entrySet()) {
/*  628 */           String deviceName = entry.getKey();
/*  629 */           Boolean permission = entry.getValue();
/*  630 */           if (deviceName != null && !deviceName.equals("null")) {
/*  631 */             if (permission.booleanValue()) {
/*  632 */               logger.debug("true entry.getKey():'{}'", entry.getKey());
/*  633 */               addCardDeviceLsit.add(deviceName); continue;
/*  634 */             }  if (!permission.booleanValue()) {
/*  635 */               logger.debug("false entry.getKey():'{}'", entry.getKey());
/*  636 */               deleteCardDeviceList.add(deviceName);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/*  641 */       logger.debug("addCardDeviceLsit:'{}'", addCardDeviceLsit);
/*  642 */       logger.debug("deleteCardDeviceList:'{}'", deleteCardDeviceList);
/*  643 */       String[] addCardDevices = addCardDeviceLsit.<String>toArray(new String[0]);
/*  644 */       String[] deleteCardDevices = deleteCardDeviceList.<String>toArray(new String[0]);
/*  645 */       logger.debug("addCardDevices count:'{}', deleteCardDevices count:'{}'", addCardDevices, deleteCardDevices);
/*      */ 
/*      */ 
/*      */       
/*  649 */       EntryObject addCardEo = (new PredicateBuilder()).getEntryObject();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  654 */       PredicateBuilder addCardPd = addCardEo.get("readerId").in((Comparable[])addCardDevices).and((Predicate)addCardEo.get("cardId").equal(dto.getAba()));
/*  655 */       EntryObject deleteCardEo = (new PredicateBuilder()).getEntryObject();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  660 */       PredicateBuilder deleteCardPd = deleteCardEo.get("readerId").in((Comparable[])deleteCardDevices).and((Predicate)deleteCardEo.get("cardId").equal(dto.getAba()));
/*      */       
/*  662 */       for (RoomCardReaderMappingConfig mapping : cardMappingMap.values((Predicate)deleteCardPd)) {
/*      */         
/*  664 */         if (!mapping.getLoginCardReader().booleanValue()) {
/*  665 */           logger.debug("login = false, delete cardReaderMapping:'{}'", mapping);
/*  666 */           cardMappingMap.delete(mapping.getId());
/*  667 */           this.opLogger.addLog(userId, "", SubSystem.ROOM, OperationItem.SET, mapping
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  672 */               .getReaderId(), new Date(), OperationResult.SUCCESS, "NotToCardReader", "room.deleteCardPermission", new Object[] { mapping
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  677 */                 .getCardId() }); continue;
/*  678 */         }  if (mapping.getLoginCardReader().booleanValue() == true && this.roomCardReaderConnectorService
/*  679 */           .deleteCard(mapping
/*  680 */             .getReaderId(), mapping.getCardId()).booleanValue()) {
/*  681 */           logger.debug("delete cardReaderMapping:'{}'", mapping);
/*  682 */           cardMappingMap.delete(mapping.getId());
/*  683 */           this.opLogger.addLog(userId, "", SubSystem.ROOM, OperationItem.SET, mapping
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  688 */               .getReaderId(), new Date(), OperationResult.SUCCESS, "ToCardReader", "room.deleteCardPermission", new Object[] { mapping
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  693 */                 .getCardId() });
/*      */         } 
/*      */       } 
/*      */       
/*  697 */       List<String> existedCardReaders = new ArrayList<>();
/*  698 */       Map<String, RoomCardReaderMappingConfig> saveMap = new HashMap<>();
/*  699 */       for (RoomCardReaderMappingConfig mapping : cardMappingMap.values((Predicate)addCardPd)) {
/*  700 */         existedCardReaders.add(mapping.getReaderId());
/*      */         
/*  702 */         if (!mapping.getLoginCardReader().booleanValue() && now.after(startDate) && now.before(endDate)) {
/*  703 */           if (this.roomCardReaderConnectorService.issueCard(mapping
/*  704 */               .getReaderId(), mapping.getCardId()).booleanValue()) {
/*  705 */             mapping.setLoginCardReader(Boolean.valueOf(true));
/*  706 */             logger.debug("addCard cardReaderMapping:'{}'", mapping);
/*  707 */             saveMap.put(mapping.getId(), mapping);
/*  708 */             this.opLogger.addLog(userId, "", SubSystem.ROOM, OperationItem.SET, mapping
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  713 */                 .getReaderId(), new Date(), OperationResult.SUCCESS, "ToCardReader", "room.addCardPermission", new Object[] { mapping
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  718 */                   .getCardId() }); continue;
/*      */           } 
/*  720 */           String failDeviceLocation = getCardReaderLocation(mapping.getReaderId());
/*  721 */           if (failDeviceLocation != null) {
/*  722 */             addFailDevices.add(failDeviceLocation);
/*      */           }
/*  724 */           this.opLogger.addLog(userId, "", SubSystem.ROOM, OperationItem.SET, mapping
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  729 */               .getReaderId(), new Date(), OperationResult.FAILURE, "ToCardReader", "room.addCardPermission", new Object[] { mapping
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  734 */                 .getCardId() });
/*      */         } 
/*      */       } 
/*      */       
/*  738 */       cardMappingMap.putAll(saveMap);
/*  739 */       logger.debug("existedCardReaders:'{}'", existedCardReaders);
/*      */       
/*  741 */       addCardDeviceLsit.removeAll(existedCardReaders);
/*  742 */       logger.debug("removeAll existed addCardDeviceLsit:'{}'", addCardDeviceLsit);
/*  743 */       List<RoomCardReaderMappingConfig> saveList = new ArrayList<>();
/*  744 */       for (String cardReader : addCardDeviceLsit) {
/*  745 */         RoomCardReaderMappingConfig cardReaderMapping = new RoomCardReaderMappingConfig();
/*  746 */         cardReaderMapping.setCardId(dto.getAba());
/*  747 */         cardReaderMapping.setReaderId(cardReader);
/*      */         
/*  749 */         if (now.after(startDate) && now.before(endDate)) {
/*  750 */           if (this.roomCardReaderConnectorService.issueCard(cardReader, dto.getAba()).booleanValue()) {
/*  751 */             cardReaderMapping.setLoginCardReader(Boolean.valueOf(true));
/*  752 */             this.opLogger.addLog(userId, "", SubSystem.ROOM, OperationItem.SET, cardReader, new Date(), OperationResult.SUCCESS, "ToCardReader", "room.addCardPermission", new Object[] { dto
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  762 */                   .getAba() });
/*      */           } else {
/*  764 */             cardReaderMapping.setLoginCardReader(Boolean.valueOf(false));
/*  765 */             String failDeviceLocation = getCardReaderLocation(cardReader);
/*  766 */             if (failDeviceLocation != null) {
/*  767 */               addFailDevices.add(failDeviceLocation);
/*      */             }
/*  769 */             this.opLogger.addLog(userId, "", SubSystem.ROOM, OperationItem.SET, cardReader, new Date(), OperationResult.FAILURE, "ToCardReader", "room.addCardPermission", new Object[] { dto
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  779 */                   .getAba() });
/*      */           } 
/*  781 */           saveList.add(cardReaderMapping);
/*      */         } 
/*  783 */         logger.debug("create new cardReaderMapping:'{}'", cardReaderMapping);
/*      */       } 
/*      */       
/*  786 */       List<RoomCardReaderMappingConfig> saveMappingList = this.roomCardReaderMappingConfigRepository.saveAll(saveList);
/*  787 */       Map<String, RoomCardReaderMappingConfig> mappingConfigMap = new HashMap<>();
/*  788 */       for (RoomCardReaderMappingConfig config : saveMappingList) {
/*  789 */         mappingConfigMap.put(config.getId(), config);
/*      */       }
/*  791 */       cardMappingMap.putAll(mappingConfigMap);
/*      */       
/*  793 */       logger.debug("addFailDevices size:'{}'", Integer.valueOf(addFailDevices.size()));
/*  794 */       StringBuilder resultMsg = new StringBuilder();
/*  795 */       if (addFailDevices.size() > 0) {
/*  796 */         resultMsg.append("卡機加卡失敗: ");
/*  797 */         resultMsg.append(System.lineSeparator());
/*  798 */         for (int i = 0; i < addFailDevices.size(); i++) {
/*  799 */           if (i == addFailDevices.size() - 1) {
/*  800 */             resultMsg.append((String)addFailDevices.get(i) + System.lineSeparator());
/*      */           } else {
/*  802 */             resultMsg.append((String)addFailDevices.get(i) + ",");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  807 */       return resultMsg.toString();
/*  808 */     } catch (Exception e) {
/*  809 */       logger.error("update RoomCardConfig failed.", e);
/*  810 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String getCardReaderLocation(String readerId) {
/*  817 */     IMap<String, DeviceLocationMappingConfig> deviceLocationMappingMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/*  818 */     PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceName").equal(readerId);
/*      */     
/*  820 */     List<DeviceLocationMappingConfig> location = new ArrayList<>(deviceLocationMappingMap.values((Predicate)pb));
/*  821 */     if (location != null && location.size() > 0) {
/*  822 */       DeviceLocationMappingConfig locationMapping = location.get(0);
/*  823 */       return locationMapping.getDescription() + locationMapping.getSubLocation();
/*      */     } 
/*  825 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/issueRoomCard"}, method = {RequestMethod.POST})
/*      */   public Boolean issueRoomCard(HttpServletRequest req, @RequestBody RoomCardIssueParam param) {
/*      */     try {
/*  833 */       logger.debug("issueRoomCard. map:'{}'", param.getCardReaderAddCardMap());
/*  834 */       if (param.getCardReaderAddCardMap() != null) {
/*  835 */         logger.debug("map.size():'{}'", Integer.valueOf(param.getCardReaderAddCardMap().size()));
/*      */       }
/*  837 */       RoomCardConfigDTO dto = param.getRoomCardConfigDto();
/*  838 */       logger.debug("issue RoomCard.");
/*  839 */       if (dto.getId() == null) {
/*  840 */         logger.error("issue RoomCard failed, id == null");
/*  841 */         return Boolean.valueOf(false);
/*      */       } 
/*  843 */       if (dto.getAba() == null || dto.getAba().length() != 10) {
/*  844 */         logger.error("issue RoomCard failed, aba:'{}'", dto.getAba());
/*  845 */         return Boolean.valueOf(false);
/*      */       } 
/*  847 */       String userId = TripleDESUtils.decrypt(req.getHeader("encryptedUserLogin"));
/*      */       
/*  849 */       IMap<String, RoomCardConfig> roomCardConfigMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardConfig);
/*      */       
/*  851 */       IMap<String, RoomCardReaderMappingConfig> roomCardReaderMappingMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardReaderMappingConfig);
/*  852 */       RoomCardConfig roomCardConfig = (RoomCardConfig)this.modelmapper.map(dto, RoomCardConfig.class);
/*  853 */       roomCardConfig.setIssued(Boolean.valueOf(true));
/*  854 */       roomCardConfig.setIssueDate(new Date());
/*  855 */       roomCardConfigMap.put(roomCardConfig.getId(), roomCardConfig);
/*  856 */       String aba = dto.getAba();
/*  857 */       Map<String, Boolean> cardPermissionMap = param.getCardReaderAddCardMap();
/*  858 */       List<RoomCardReaderMappingConfig> saveList = new ArrayList<>();
/*  859 */       for (Map.Entry<String, Boolean> entry : cardPermissionMap.entrySet()) {
/*  860 */         if (((Boolean)entry.getValue()).booleanValue()) {
/*  861 */           RoomCardReaderMappingConfig config = new RoomCardReaderMappingConfig();
/*  862 */           config.setCardId(aba);
/*  863 */           config.setReaderId(entry.getKey());
/*  864 */           config.setLoginCardReader(Boolean.valueOf(false));
/*  865 */           logger.debug("add RoomCardReaderMappingConfig:'{}'", config);
/*  866 */           saveList.add(config);
/*  867 */           this.opLogger.addLog(userId, "", SubSystem.ROOM, OperationItem.SET, entry
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  872 */               .getKey(), new Date(), OperationResult.SUCCESS, "NotToCardReader", "room.addCardPermission", new Object[] { aba });
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  881 */       List<RoomCardReaderMappingConfig> saveConfigList = this.roomCardReaderMappingConfigRepository.saveAll(saveList);
/*  882 */       Map<String, RoomCardReaderMappingConfig> mappingConfigMap = new HashMap<>();
/*  883 */       for (RoomCardReaderMappingConfig config : saveConfigList) {
/*  884 */         mappingConfigMap.put(config.getId(), config);
/*      */       }
/*  886 */       roomCardReaderMappingMap.putAll(mappingConfigMap);
/*      */       
/*  888 */       return Boolean.valueOf(true);
/*  889 */     } catch (Exception e) {
/*  890 */       logger.error("issue RoomCard failed.", e);
/*  891 */       return Boolean.valueOf(false);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/issueRoomCardImmediately"}, method = {RequestMethod.POST})
/*      */   public String issueRoomCardImmediately(HttpServletRequest req, @RequestBody RoomCardIssueParam param) {
/*      */     try {
/*  899 */       RoomCardConfigDTO dto = param.getRoomCardConfigDto();
/*  900 */       logger.debug("issue RoomCard Immediately.");
/*  901 */       if (dto.getId() == null) {
/*  902 */         logger.error("issue RoomCard failed, id == null");
/*  903 */         return null;
/*      */       } 
/*  905 */       if (dto.getAba() == null || dto.getAba().length() != 10) {
/*  906 */         logger.error("issue RoomCard failed, aba:'{}'", dto.getAba());
/*  907 */         return null;
/*      */       } 
/*  909 */       String userId = TripleDESUtils.decrypt(req.getHeader("encryptedUserLogin"));
/*  910 */       IMap<String, RoomCardConfig> roomCardConfigMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardConfig);
/*      */       
/*  912 */       IMap<String, RoomCardReaderMappingConfig> roomCardReaderMappingMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardReaderMappingConfig);
/*  913 */       RoomCardConfig roomCardConfig = (RoomCardConfig)this.modelmapper.map(dto, RoomCardConfig.class);
/*  914 */       roomCardConfig.setIssued(Boolean.valueOf(true));
/*  915 */       roomCardConfig.setIssueDate(new Date());
/*  916 */       roomCardConfigMap.put(roomCardConfig.getId(), roomCardConfig);
/*  917 */       String aba = dto.getAba();
/*  918 */       Map<String, Boolean> cardPermissionMap = param.getCardReaderAddCardMap();
/*  919 */       StringBuilder resultMsg = new StringBuilder();
/*  920 */       List<String> issueFailedCardReader = new ArrayList<>();
/*  921 */       List<String> issueDuplicateCardReader = new ArrayList<>();
/*  922 */       List<String> removeFailedCarReader = new ArrayList<>();
/*  923 */       List<RoomCardReaderMappingConfig> saveList = new ArrayList<>();
/*  924 */       for (Map.Entry<String, Boolean> entry : cardPermissionMap.entrySet()) {
/*  925 */         EntryObject ob = (new PredicateBuilder()).getEntryObject();
/*      */         
/*  927 */         PredicateBuilder pb = ob.get("cardId").equal(aba).and((Predicate)ob.get("readerId").equal(entry.getKey()));
/*  928 */         OperationResult result = OperationResult.SUCCESS;
/*  929 */         String remark = "ToCardReader";
/*      */         
/*  931 */         RoomCardReaderMappingConfig currentMappingConfig = null;
/*      */         
/*  933 */         List<RoomCardReaderMappingConfig> mappingConfigList = new ArrayList<>(roomCardReaderMappingMap.values((Predicate)pb));
/*  934 */         if (mappingConfigList.size() > 0) {
/*  935 */           currentMappingConfig = mappingConfigList.get(0);
/*      */         }
/*  937 */         if (((Boolean)entry.getValue()).booleanValue()) {
/*      */           
/*  939 */           if (currentMappingConfig != null && currentMappingConfig.getLoginCardReader().booleanValue()) {
/*  940 */             issueDuplicateCardReader.add(getCardReaderLocation(entry.getKey())); continue;
/*      */           } 
/*  942 */           RoomCardReaderMappingConfig config = new RoomCardReaderMappingConfig();
/*  943 */           config.setCardId(aba);
/*  944 */           config.setReaderId(entry.getKey());
/*  945 */           if (this.roomCardReaderConnectorService.issueCard(entry.getKey(), aba).booleanValue()) {
/*  946 */             config.setLoginCardReader(Boolean.valueOf(true));
/*      */           } else {
/*      */             
/*  949 */             config.setLoginCardReader(Boolean.valueOf(false));
/*  950 */             issueFailedCardReader.add(getCardReaderLocation(entry.getKey()));
/*  951 */             result = OperationResult.FAILURE;
/*      */           } 
/*  953 */           saveList.add(config);
/*  954 */           this.opLogger.addLog(userId, "", SubSystem.ROOM, OperationItem.SET, entry
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  959 */               .getKey(), new Date(), result, remark, "room.addCardPermission", new Object[] { aba });
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  969 */         if (currentMappingConfig != null) {
/*  970 */           if (!currentMappingConfig.getLoginCardReader().booleanValue()) {
/*  971 */             logger.debug("issueRoomCardImediately login = false, delete cardReaderMapping:'{}'", currentMappingConfig);
/*      */ 
/*      */             
/*  974 */             remark = "NotToCardReader";
/*  975 */             roomCardReaderMappingMap.delete(currentMappingConfig.getId());
/*  976 */           } else if (currentMappingConfig.getLoginCardReader().booleanValue() == true && this.roomCardReaderConnectorService
/*  977 */             .deleteCard(currentMappingConfig
/*  978 */               .getReaderId(), currentMappingConfig.getCardId()).booleanValue()) {
/*  979 */             logger.debug("issueRoomCardImediately delete cardReaderMapping:'{}'", currentMappingConfig);
/*      */             
/*  981 */             roomCardReaderMappingMap.delete(currentMappingConfig.getId());
/*      */           } else {
/*  983 */             result = OperationResult.FAILURE;
/*  984 */             removeFailedCarReader.add(getCardReaderLocation(entry.getKey()));
/*      */           } 
/*      */         }
/*  987 */         this.opLogger.addLog(userId, "", SubSystem.ROOM, OperationItem.SET, entry
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  992 */             .getKey(), new Date(), result, remark, "room.deleteCardPermission", new Object[] { aba });
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1002 */       List<RoomCardReaderMappingConfig> saveConfigList = this.roomCardReaderMappingConfigRepository.saveAll(saveList);
/* 1003 */       Map<String, RoomCardReaderMappingConfig> mappingConfigMap = new HashMap<>();
/* 1004 */       for (RoomCardReaderMappingConfig config : saveConfigList) {
/* 1005 */         mappingConfigMap.put(config.getId(), config);
/*      */       }
/* 1007 */       roomCardReaderMappingMap.putAll(mappingConfigMap);
/*      */ 
/*      */       
/* 1010 */       if (issueDuplicateCardReader.size() > 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1022 */       if (issueFailedCardReader.size() > 0) {
/* 1023 */         resultMsg.append("卡機加卡失敗:");
/* 1024 */         resultMsg.append(System.lineSeparator());
/* 1025 */         for (int i = 0; i < issueFailedCardReader.size(); i++) {
/* 1026 */           if (i == issueFailedCardReader.size() - 1) {
/* 1027 */             resultMsg.append((String)issueFailedCardReader.get(i) + System.lineSeparator());
/*      */           } else {
/* 1029 */             resultMsg.append((String)issueFailedCardReader.get(i) + ",");
/*      */           } 
/*      */         } 
/*      */       } 
/* 1033 */       if (removeFailedCarReader.size() > 0) {
/* 1034 */         resultMsg.append("卡機刪卡失敗:");
/* 1035 */         resultMsg.append(System.lineSeparator());
/* 1036 */         for (int i = 0; i < removeFailedCarReader.size(); i++) {
/* 1037 */           if (i == removeFailedCarReader.size() - 1) {
/* 1038 */             resultMsg.append((String)removeFailedCarReader.get(i) + System.lineSeparator());
/*      */           } else {
/* 1040 */             resultMsg.append((String)removeFailedCarReader.get(i) + ",");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1045 */       return resultMsg.toString();
/* 1046 */     } catch (Exception e) {
/* 1047 */       logger.error("issueRoomCardImediately failed.", e);
/* 1048 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   @RequestMapping(value = {"/cardReaderLocation"}, method = {RequestMethod.GET})
/*      */   public List<RoomCardPermissionTreeDTO> getCardReaderLocation() {
/*      */     try {
/* 1055 */       logger.debug("Get RoomDeviceLocationConfigData.");
/*      */       
/* 1057 */       List<RoomCardPermissionTreeDTO> result = new ArrayList<>();
/* 1058 */       IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*      */ 
/*      */       
/* 1061 */       PredicateBuilder cardReaderPredicate = (new PredicateBuilder()).getEntryObject().get("deviceType").equal("cardReader");
/* 1062 */       List<String> cardReaderDeviceNameList = new ArrayList<>();
/* 1063 */       deviceMap
/* 1064 */         .values((Predicate)cardReaderPredicate)
/* 1065 */         .stream()
/* 1066 */         .forEach(cardReader -> paramList.add(cardReader.getDeviceName()));
/*      */ 
/*      */ 
/*      */       
/* 1070 */       String[] cardReaderNames = cardReaderDeviceNameList.<String>toArray(new String[0]);
/*      */ 
/*      */ 
/*      */       
/* 1074 */       IMap<String, DeviceLocationMappingConfig> roomLocationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/*      */ 
/*      */ 
/*      */       
/* 1078 */       PredicateBuilder locPredicate = (new PredicateBuilder()).getEntryObject().get("deviceName").in((Comparable[])cardReaderNames);
/* 1079 */       List<String> hostNameList = new ArrayList<>();
/* 1080 */       for (DeviceLocationMappingConfig mappingConfig : roomLocationMap.values((Predicate)locPredicate)) {
/* 1081 */         hostNameList.add(mappingConfig.getLocationName());
/*      */       }
/* 1083 */       String[] hostConfigNames = hostNameList.<String>toArray(new String[0]);
/*      */       
/* 1085 */       PredicateBuilder deviceHostPredicate = (new PredicateBuilder()).getEntryObject().get("locName").in((Comparable[])hostConfigNames);
/*      */ 
/*      */       
/* 1088 */       IMap<Integer, DeviceHostLocation> locationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceHostLocation);
/*      */       
/* 1090 */       for (DeviceHostLocation deviceHostLocation : locationMap.values((Predicate)deviceHostPredicate)) {
/*      */         
/* 1092 */         Map<String, String> roomCardLocationMap = new HashMap<>();
/* 1093 */         String locName = deviceHostLocation.getLocName();
/* 1094 */         RoomCardPermissionTreeDTO dto = new RoomCardPermissionTreeDTO();
/*      */         
/* 1096 */         PredicateBuilder hostPredicate = (new PredicateBuilder()).getEntryObject().get("locName").equal(locName);
/* 1097 */         List<DeviceHostLocation> hostLocation = new ArrayList<>(locationMap.values((Predicate)hostPredicate));
/* 1098 */         if (hostLocation != null && hostLocation.size() != 0) {
/* 1099 */           dto.setId(((DeviceHostLocation)hostLocation.get(0)).getId());
/*      */         }
/* 1101 */         dto.setLocationName(locName);
/*      */         
/* 1103 */         EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*      */         
/* 1105 */         PredicateBuilder predicate = eo.get("locationName").equal(locName).and((Predicate)eo.get("deviceName").in((Comparable[])cardReaderNames));
/* 1106 */         logger.debug("roomLocationMap.size():'{}'", Integer.valueOf(roomLocationMap.values((Predicate)predicate).size()));
/* 1107 */         roomLocationMap
/* 1108 */           .values((Predicate)predicate)
/* 1109 */           .stream()
/* 1110 */           .forEach(roomData -> paramMap.put(roomData.getDescription() + roomData.getSubLocation(), roomData.getDeviceName()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1116 */         dto.setCardReaderLocation(roomCardLocationMap);
/* 1117 */         result.add(dto);
/*      */       } 
/* 1119 */       return result;
/* 1120 */     } catch (Exception e) {
/* 1121 */       logger.error("getCardReaderLocationConfig failed.", e);
/* 1122 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomCardReaderMappingConfig/{aba}"}, method = {RequestMethod.GET})
/*      */   public List<RoomCardReaderMappingConfigDTO> getRoomCardReaderMappingConfig(@PathVariable("aba") String aba) {
/*      */     try {
/* 1130 */       logger.debug("get RoomCardReaderMappingConfig, aba:'{}'", aba);
/* 1131 */       List<RoomCardReaderMappingConfigDTO> result = new ArrayList<>();
/*      */ 
/*      */       
/* 1134 */       IMap<String, RoomCardReaderMappingConfig> cardReaderMappingMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardReaderMappingConfig);
/* 1135 */       logger.debug("cardReaderMappingMap size:'{}'", Integer.valueOf(cardReaderMappingMap.size()));
/* 1136 */       PredicateBuilder predicate = (new PredicateBuilder()).getEntryObject().get("cardId").equal(aba);
/* 1137 */       for (RoomCardReaderMappingConfig config : cardReaderMappingMap.values((Predicate)predicate)) {
/* 1138 */         result.add(this.modelmapper.map(config, RoomCardReaderMappingConfigDTO.class));
/*      */       }
/*      */       
/* 1141 */       logger.debug("result.size():'{}'", Integer.valueOf(result.size()));
/* 1142 */       return result;
/* 1143 */     } catch (Exception e) {
/* 1144 */       logger.error("getRoomCardReaderMappingConfig failed.", e);
/* 1145 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomDeviceSubLocationConfig"}, method = {RequestMethod.GET})
/*      */   public List<RoomDeviceSubLocationConfigDTO> getRoomDeviceSubLocationConfigData() {
/*      */     try {
/* 1153 */       logger.debug("Get RoomDeviceLocationConfigData.");
/* 1154 */       List<RoomDeviceSubLocationConfigDTO> result = new ArrayList<>();
/*      */ 
/*      */       
/* 1157 */       IMap<String, DeviceLocationMappingConfig> roomLocationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/*      */       
/* 1159 */       IMap<String, DeviceTcStatus> statusMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcStatus);
/*      */ 
/*      */       
/* 1162 */       IMap<String, RoomBackgroundSvgConfig> svgConfigMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.BackgroundSvgConfig);
/*      */ 
/*      */       
/* 1165 */       Map<String, Set<String>> subLocationMap = new HashMap<>();
/*      */       
/* 1167 */       Map<String, Set<String>> deviceNameMap = new HashMap<>();
/*      */       
/* 1169 */       Map<String, String> svgMap = new HashMap<>();
/*      */       
/* 1171 */       Set<String> locationNames = new HashSet<>();
/* 1172 */       for (DeviceLocationMappingConfig mappingConfig : roomLocationMap.values()) {
/* 1173 */         String locationName = mappingConfig.getLocationName();
/* 1174 */         String subLocationName = mappingConfig.getSubLocation();
/* 1175 */         String deviceName = mappingConfig.getDeviceName();
/* 1176 */         String svgName = mappingConfig.getSvgName();
/* 1177 */         String key = locationName + "-" + subLocationName;
/* 1178 */         if (locationName != null && locationName != "" && svgName != null && svgName != "") {
/*      */           
/* 1180 */           if (subLocationName != null && subLocationName != "") {
/* 1181 */             if (subLocationMap.get(locationName) == null) {
/* 1182 */               Set<String> subLocationNames = new HashSet<>();
/* 1183 */               subLocationMap.put(locationName, subLocationNames);
/*      */             } 
/* 1185 */             ((Set<String>)subLocationMap.get(locationName)).add(subLocationName);
/*      */           } 
/*      */           
/* 1188 */           if (deviceNameMap.get(key) == null) {
/* 1189 */             Set<String> deviceNames = new HashSet<>();
/* 1190 */             deviceNameMap.put(key, deviceNames);
/*      */           } 
/* 1192 */           ((Set<String>)deviceNameMap.get(key)).add(deviceName);
/*      */           
/* 1194 */           svgMap.put(key, svgName);
/*      */           
/* 1196 */           locationNames.add(locationName);
/*      */         } 
/*      */       } 
/* 1199 */       for (Iterator<String> iterator = locationNames.iterator(); iterator.hasNext(); ) { String locationName = iterator.next();
/* 1200 */         ((Set)subLocationMap
/* 1201 */           .get(locationName))
/* 1202 */           .forEach(subValue -> {
/*      */               RoomDeviceSubLocationConfigDTO subLocationConfigDto = new RoomDeviceSubLocationConfigDTO();
/*      */               
/*      */               String id = paramString1 + "-" + subValue;
/*      */               
/*      */               subLocationConfigDto.setId(id);
/*      */               
/*      */               subLocationConfigDto.setLocationNo(null);
/*      */               subLocationConfigDto.setLocationName(paramString1);
/*      */               subLocationConfigDto.setSubLocation(subValue);
/*      */               String svgkey = (String)paramMap1.get(id);
/*      */               subLocationConfigDto.setBackgroundSvgId(((RoomBackgroundSvgConfig)paramIMap1.get(svgkey)).getNameId());
/*      */               Integer status = Integer.valueOf(0);
/*      */               for (String deviceName : paramMap2.get(id)) {
/*      */                 if (paramIMap2.get(deviceName) == null) {
/*      */                   status = Integer.valueOf(1);
/*      */                   break;
/*      */                 } 
/*      */                 Integer deviceStatus = ((DeviceTcStatus)paramIMap2.get(deviceName)).getCommStatus();
/*      */                 if (deviceStatus == null || deviceStatus.intValue() == 1) {
/*      */                   status = Integer.valueOf(1);
/*      */                   break;
/*      */                 } 
/*      */                 if (deviceStatus.intValue() == 2) {
/*      */                   status = Integer.valueOf(2);
/*      */                 }
/*      */               } 
/*      */               subLocationConfigDto.setStatus(status);
/*      */               paramList.add(subLocationConfigDto);
/*      */             }); }
/*      */       
/* 1233 */       return result;
/* 1234 */     } catch (Exception e) {
/* 1235 */       logger.error("Get RoomDeviceSubLocationConfigData failed.", e);
/* 1236 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomCardReaderLog/{locationName}/{subLocationName}"}, method = {RequestMethod.GET})
/*      */   public List<RoomCardReaderLogDTO> getCardReaderData(@PathVariable("locationName") String locName, @PathVariable("subLocationName") String subLocationName) {
/*      */     try {
/* 1254 */       logger.debug("Get CardReaderData. locName = '{}',subLocationName ='{}'", locName, subLocationName);
/*      */       
/* 1256 */       if (locName == null) {
/* 1257 */         throw new IllegalArgumentException("locName is null");
/*      */       }
/* 1259 */       List<RoomCardReaderLogDTO> list = new ArrayList<>();
/*      */ 
/*      */       
/* 1262 */       IMap<String, DeviceLocationMappingConfig> roomLocationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/* 1263 */       IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 1264 */       IMap<String, RoomCardReaderLog> cardReaderLogMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardReaderLog);
/* 1265 */       IMap<String, DeviceTcStatus> statusMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcStatus);
/*      */       
/* 1267 */       EntryObject eo = (new PredicateBuilder()).getEntryObject();
/* 1268 */       PredicateBuilder predicate = null;
/* 1269 */       if (!subLocationName.equals("null") && !subLocationName.isEmpty()) {
/*      */         
/* 1271 */         predicate = eo.get("locationName").equal(locName).and((Predicate)eo.get("subLocation").equal(subLocationName));
/*      */       } else {
/* 1273 */         predicate = eo.get("locationName").equal(locName);
/*      */       } 
/* 1275 */       for (DeviceLocationMappingConfig each : roomLocationMap.values((Predicate)predicate)) {
/* 1276 */         if (((DeviceTcConfig)deviceMap.get(each.getDeviceName())).getDeviceType().equals("cardReader")) {
/*      */           
/* 1278 */           RoomCardReaderLog openEventLog = (RoomCardReaderLog)cardReaderLogMap.get(each.getDeviceName() + "-" + EventCode.DOOR_OPEN.getId());
/*      */           
/* 1280 */           RoomCardReaderLog closeEventLog = (RoomCardReaderLog)cardReaderLogMap.get(each.getDeviceName() + "-" + EventCode.DOOR_CLOSE.getId());
/* 1281 */           RoomCardReaderLogDTO eventLog = new RoomCardReaderLogDTO();
/* 1282 */           if (each.getDescription() != null) {
/* 1283 */             eventLog.setDescription(((DeviceTcConfig)deviceMap
/* 1284 */                 .get(each.getDeviceName())).getDisplayName() + "-" + each
/*      */                 
/* 1286 */                 .getSubLocation() + "-" + each
/*      */                 
/* 1288 */                 .getDescription());
/*      */           }
/* 1290 */           eventLog.setDeviceName(each.getDeviceName());
/* 1291 */           eventLog.setStatus(((DeviceTcStatus)statusMap.get(each.getDeviceName())).getCommStatus());
/* 1292 */           if (openEventLog != null && closeEventLog != null) {
/* 1293 */             if (openEventLog.getTime().after(closeEventLog.getTime())) {
/* 1294 */               eventLog.setTime(openEventLog.getTime());
/* 1295 */               eventLog.setEventCode(openEventLog.getEventCode());
/*      */             } else {
/* 1297 */               eventLog.setTime(closeEventLog.getTime());
/* 1298 */               eventLog.setEventCode(closeEventLog.getEventCode());
/*      */             } 
/*      */           } else {
/* 1301 */             eventLog.setTime(new Date());
/* 1302 */             if (eventLog.getStatus().intValue() == 0) {
/* 1303 */               eventLog.setEventCode(EventCode.DOOR_CLOSE);
/* 1304 */             } else if (eventLog.getStatus().intValue() == 2) {
/* 1305 */               eventLog.setEventCode(EventCode.DOOR_OPEN);
/*      */             } else {
/* 1307 */               eventLog.setEventCode(EventCode.NONE);
/*      */             } 
/*      */           } 
/* 1310 */           list.add(eventLog);
/*      */         } 
/*      */       } 
/* 1313 */       return list;
/* 1314 */     } catch (Exception e) {
/* 1315 */       logger.error("Get CardReaderData failed.", e);
/* 1316 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/doorStatus/{deviceName}"}, method = {RequestMethod.POST})
/*      */   public Boolean changeDoorOpen(@PathVariable("deviceName") String deviceName) {
/*      */     try {
/* 1324 */       logger.debug("Change DoorOpen.");
/* 1325 */       return this.roomCardReaderConnectorService.openDoor(deviceName, Integer.valueOf(2));
/* 1326 */     } catch (Exception e) {
/* 1327 */       logger.error("Change DoorOpen failed.", e);
/* 1328 */       return Boolean.valueOf(false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomDeviceConfig/{locationName}/{subLocationName}"}, method = {RequestMethod.GET})
/*      */   public List<RoomDeviceConfigDTO> getRoomDeviceConfig(@PathVariable("locationName") String locName, @PathVariable("subLocationName") String subLocationName) {
/*      */     try {
/* 1340 */       logger.debug("Get RoomDeviceConfig. locName = '{}',subLocationName ='{}'", locName, subLocationName);
/*      */       
/* 1342 */       List<RoomDeviceConfigDTO> result = new ArrayList<>();
/*      */       
/* 1344 */       IMap<String, DeviceLocationMappingConfig> roomLocationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/* 1345 */       IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 1346 */       IMap<String, DeviceType> typeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 1347 */       List<String> typelist = new ArrayList<>();
/* 1348 */       for (DeviceType each : typeMap.values()) {
/* 1349 */         if (each.getCategory().equals("ROOM") && 
/* 1350 */           !each.getId().equals("cardReader")) {
/* 1351 */           typelist.add(each.getId());
/*      */         }
/*      */       } 
/*      */       
/* 1355 */       EntryObject eo = (new PredicateBuilder()).getEntryObject();
/* 1356 */       PredicateBuilder predicate = null;
/* 1357 */       if (!subLocationName.equals("null") && !subLocationName.isEmpty()) {
/*      */         
/* 1359 */         predicate = eo.get("locationName").equal(locName).and((Predicate)eo.get("subLocation").equal(subLocationName));
/*      */       } else {
/* 1361 */         predicate = eo.get("locationName").equal(locName);
/*      */       } 
/* 1363 */       for (DeviceLocationMappingConfig each : roomLocationMap.values((Predicate)predicate)) {
/* 1364 */         DeviceTcConfig device = (DeviceTcConfig)deviceMap.get(each.getDeviceName());
/* 1365 */         if (typelist.contains(device.getDeviceType()) && 
/* 1366 */           device.getExtend() != null && this.gson
/* 1367 */           .fromJson(device.getExtend(), RtuConfig.class) != null) {
/* 1368 */           RtuConfig config = (RtuConfig)this.gson.fromJson(device.getExtend(), RtuConfig.class);
/* 1369 */           RoomDeviceConfigDTO dto = new RoomDeviceConfigDTO();
/* 1370 */           dto.setDeviceName(device.getDeviceName());
/* 1371 */           dto.setDisplayName(device.getDisplayName());
/* 1372 */           dto.setCategory("ROOM");
/* 1373 */           dto.setAlarmCheck(Boolean.valueOf(config.isAlarm()));
/* 1374 */           dto.setUpperLimit(config.getUpperLimit());
/* 1375 */           dto.setLowerLimit(config.getLowerLimit());
/* 1376 */           dto.setTypeDiscription(((DeviceType)typeMap.get(device.getDeviceType())).getDescription());
/* 1377 */           dto.setType(device.getDeviceType());
/* 1378 */           if (config.getSignalType().equals(SignalType.DIGITAL_IN.name())) {
/* 1379 */             dto.setSignalType(SignalType.DIGITAL_IN);
/* 1380 */           } else if (config.getSignalType().equals(SignalType.ANALOG_IN.name())) {
/* 1381 */             dto.setSignalType(SignalType.ANALOG_IN);
/* 1382 */           } else if (config.getSignalType().equals(SignalType.DIGITAL_OUT.name())) {
/* 1383 */             dto.setSignalType(SignalType.DIGITAL_OUT);
/* 1384 */           } else if (config.getSignalType().equals(SignalType.ANALOG_OUT.name())) {
/* 1385 */             dto.setSignalType(SignalType.ANALOG_OUT);
/*      */           } 
/* 1387 */           dto.setUnit(config.getUnit());
/* 1388 */           result.add(dto);
/*      */         } 
/*      */       } 
/*      */       
/* 1392 */       return result;
/* 1393 */     } catch (Exception e) {
/* 1394 */       logger.error("Get RoomDeviceConfig failed.", e);
/* 1395 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomDeviceType"}, method = {RequestMethod.GET})
/*      */   public List<DeviceTypeDTO> getRoomDeviceType() {
/*      */     try {
/* 1403 */       logger.debug("Get RoomDeviceType.");
/* 1404 */       List<DeviceTypeDTO> result = new ArrayList<>();
/* 1405 */       IMap<String, DeviceType> typeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/* 1406 */       for (DeviceType each : typeMap.values()) {
/* 1407 */         if (each.getCategory().equals("ROOM") && 
/* 1408 */           !each.getId().equals("cardReader")) {
/* 1409 */           DeviceTypeDTO dto = (DeviceTypeDTO)this.modelmapper.map(each, DeviceTypeDTO.class);
/* 1410 */           result.add(dto);
/*      */         } 
/*      */       } 
/* 1413 */       return result;
/* 1414 */     } catch (Exception e) {
/* 1415 */       logger.error("Get RoomDeviceType failed.", e);
/* 1416 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomDeviceConfig/update"}, method = {RequestMethod.POST})
/*      */   public Boolean updateRoomDeviceConfig(@RequestBody RoomDeviceConfigDTO dto, HttpServletRequest request) {
/*      */     try {
/* 1429 */       logger.debug("Update RoomDeviceConfig");
/* 1430 */       IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 1431 */       RtuConfig config = new RtuConfig();
/* 1432 */       config.setAlarm(dto.getAlarmCheck().booleanValue());
/* 1433 */       config.setUnit((dto.getUnit() != null) ? dto.getUnit() : "");
/* 1434 */       config.setLowerLimit(dto.getLowerLimit());
/* 1435 */       config.setUpperLimit(dto.getUpperLimit());
/* 1436 */       if (dto.getSignalType() == SignalType.ANALOG_IN) {
/* 1437 */         config.setSignalType(SignalType.ANALOG_IN.name());
/* 1438 */       } else if (dto.getSignalType() == SignalType.ANALOG_OUT) {
/* 1439 */         config.setSignalType(SignalType.ANALOG_OUT.name());
/* 1440 */       } else if (dto.getSignalType() == SignalType.DIGITAL_IN) {
/* 1441 */         config.setSignalType(SignalType.DIGITAL_IN.name());
/* 1442 */       } else if (dto.getSignalType() == SignalType.DIGITAL_OUT) {
/* 1443 */         config.setSignalType(SignalType.DIGITAL_OUT.name());
/*      */       } 
/* 1445 */       DeviceTcConfig device = (DeviceTcConfig)deviceMap.get(dto.getDeviceName());
/* 1446 */       String extend = this.gson.toJson(config);
/* 1447 */       device.setExtend(extend);
/* 1448 */       deviceMap.put(dto.getDeviceName(), device);
/* 1449 */       return Boolean.valueOf(true);
/* 1450 */     } catch (Exception e) {
/* 1451 */       logger.error("Update RoomDeviceConfig failed.", e);
/* 1452 */       return Boolean.valueOf(false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomDeviceSubLocationConfig/query/{deviceName}"}, method = {RequestMethod.GET})
/*      */   public List<String> querySelectedRoomSubLocation(@PathVariable("deviceName") String deviceName) {
/*      */     try {
/* 1462 */       logger.debug("Query SelectedRoomSubLocation.");
/* 1463 */       List<String> idList = new ArrayList<>();
/*      */       
/* 1465 */       IMap<String, DeviceLocationMappingConfig> roomLocationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/* 1466 */       EntryObject eo = (new PredicateBuilder()).getEntryObject();
/* 1467 */       PredicateBuilder pb = eo.get("deviceName").equal(deviceName);
/*      */       
/* 1469 */       IMap<Integer, DeviceHostLocation> locationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceHostLocation);
/* 1470 */       for (DeviceLocationMappingConfig config : roomLocationMap.values((Predicate)pb)) {
/* 1471 */         EntryObject entryObject = (new PredicateBuilder()).getEntryObject();
/* 1472 */         PredicateBuilder predicate = entryObject.get("locName").equal(config.getLocationName());
/* 1473 */         for (DeviceHostLocation hostLocation : locationMap.values((Predicate)predicate)) {
/* 1474 */           idList.add(String.valueOf(hostLocation.getId()) + "-" + config.getSubLocation());
/*      */         }
/*      */       } 
/* 1477 */       return idList;
/* 1478 */     } catch (Exception e) {
/* 1479 */       logger.error("Query SelectedRoomSubLocation failed.", e);
/* 1480 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/cctvUrl/{locationName}/{subLocation}"}, method = {RequestMethod.GET})
/*      */   public List<RoomCctvUrlDTO> getRoomCctvUrls(@PathVariable("locationName") String locationName, @PathVariable("subLocation") String subLocation) {
/*      */     try {
/* 1489 */       List<RoomCctvUrlDTO> result = new ArrayList<>();
/* 1490 */       IMap<String, CctvConfig> cMap = HzUtils.getMap((HzDistObjEnum)CcsHzMap.CctvConfig);
/*      */       
/* 1492 */       IMap<String, DeviceLocationMappingConfig> roomLocationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/* 1493 */       IMap<String, DeviceTcConfig> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 1494 */       EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*      */       
/* 1496 */       PredicateBuilder pb = eo.get("locationName").equal(locationName).and((Predicate)eo.get("subLocation").equal(subLocation));
/* 1497 */       for (DeviceLocationMappingConfig c : roomLocationMap.values((Predicate)pb)) {
/* 1498 */         if (map.get(c.getDeviceName()) != null && ((DeviceTcConfig)map
/* 1499 */           .get(c.getDeviceName())).getDeviceType().equals("CCTV")) {
/* 1500 */           RoomCctvUrlDTO dto = new RoomCctvUrlDTO();
/* 1501 */           dto.setDeviceName(c.getDeviceName());
/* 1502 */           dto.setDisplayName(((DeviceTcConfig)map.get(c.getDeviceName())).getDisplayName());
/* 1503 */           if (cMap.get(c.getDeviceName()) != null) {
/* 1504 */             dto.setUrl(((CctvConfig)cMap.get(c.getDeviceName())).getLowUrl());
/*      */           }
/* 1506 */           result.add(dto);
/*      */         } 
/*      */       } 
/* 1509 */       return result;
/* 1510 */     } catch (Exception e) {
/* 1511 */       logger.error("Get RoomCctvUrls failed.", e);
/* 1512 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */   
/*      */   @RequestMapping(value = {"/doDevice"}, method = {RequestMethod.PUT})
/*      */   public Boolean updateDoDevice(@RequestBody RoomDoDTO dto, HttpServletRequest request) {
/*      */     try {
/* 1519 */       if (dto == null) {
/* 1520 */         logger.error("RoomDoDTO is null");
/* 1521 */         return Boolean.valueOf(false);
/*      */       } 
/* 1523 */       logger.debug("UpdateDoDevice, deviceName='{}', value='{}'", dto
/* 1524 */           .getDeviceName(), dto.getValue());
/* 1525 */       if (dto.getDeviceName() == null || dto.getValue() == null) {
/* 1526 */         logger.error("UpdateDoDevice deviceName or value is null.");
/* 1527 */         return Boolean.valueOf(false);
/*      */       } 
/*      */       
/* 1530 */       logger.info("Start ModbusDeviceManager init.");
/* 1531 */       IMap<String, ModbusDeviceConfig> cfgMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusDeviceConfig);
/* 1532 */       IMap<String, DeviceConfig> devCfgMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.DeviceConfig);
/* 1533 */       IMap<Long, ModbusReadConfig> rMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusReadConfig);
/* 1534 */       IMap<Long, ModbusPinMapping> pMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusPinMapping);
/*      */ 
/*      */       
/* 1537 */       PredicateBuilder pinPb = (new PredicateBuilder()).getEntryObject().get("keyName").equal(dto.getDeviceName());
/* 1538 */       ModbusPinMapping pin = null;
/* 1539 */       Iterator<ModbusPinMapping> iterator = pMap.values((Predicate)pinPb).iterator(); if (iterator.hasNext()) { ModbusPinMapping p = iterator.next();
/* 1540 */         pin = p; }
/*      */ 
/*      */       
/* 1543 */       if (pin == null) {
/* 1544 */         logger.warn("Can not find ModbusPinMapping keyName='{}'", dto.getDeviceName());
/* 1545 */         return Boolean.valueOf(false);
/*      */       } 
/*      */       
/* 1548 */       ModbusDeviceConfig cfg = (ModbusDeviceConfig)cfgMap.get(pin.getDeviceName());
/* 1549 */       DeviceConfig devCfg = (DeviceConfig)devCfgMap.get(cfg.getDeviceName());
/* 1550 */       if (devCfg == null) {
/* 1551 */         logger.warn("{} : device config is null!", cfg.getDeviceName());
/* 1552 */         return Boolean.valueOf(false);
/*      */       } 
/* 1554 */       EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*      */ 
/*      */ 
/*      */       
/* 1558 */       PredicateBuilder pb = eo.get("pinGroupId").equal(cfg.getPinGroupId()).and((Predicate)eo.get("dataType").equal((Comparable)ModbusDataType.HOLDING_REGISTER_DO));
/*      */       
/* 1560 */       BigInteger data = getDoValue(cfg.getDeviceName(), pin.getAddress());
/* 1561 */       if (data == null) {
/* 1562 */         data = BigInteger.valueOf(0L);
/*      */       }
/* 1564 */       for (ModbusReadConfig readCfg : rMap.values((Predicate)pb)) {
/* 1565 */         if (readCfg.getStartAddress().intValue() + 40001 != pin.getAddress().intValue()) {
/* 1566 */           logger.debug("keyName:'{}', address:'{}'", pin.getKeyName(), pin.getAddress());
/*      */           continue;
/*      */         } 
/* 1569 */         ModbusMaster modbus = createModbusMaster(devCfg);
/* 1570 */         if (data.testBit(pin.getBitNumber().intValue())) {
/* 1571 */           if (!dto.getValue().booleanValue()) {
/* 1572 */             data = data.clearBit(pin.getBitNumber().intValue());
/*      */           }
/*      */         }
/* 1575 */         else if (dto.getValue().booleanValue()) {
/* 1576 */           data = data.setBit(pin.getBitNumber().intValue());
/*      */         } 
/*      */         
/* 1579 */         logger.debug("writeSingleRegister, slaveId='{}', startAddress='{}', register='{}'", new Object[] { cfg
/*      */               
/* 1581 */               .getSlaveId(), readCfg
/* 1582 */               .getStartAddress(), data });
/*      */         
/* 1584 */         modbus.writeSingleRegister(cfg.getSlaveId().intValue(), readCfg.getStartAddress().intValue(), data.intValue());
/* 1585 */         if (modbus != null && modbus.isConnected()) {
/* 1586 */           modbus.disconnect();
/*      */         }
/*      */       } 
/*      */       
/* 1590 */       addLog(request, OperationItem.SET, dto
/*      */ 
/*      */           
/* 1593 */           .getDeviceName(), OperationResult.SUCCESS, (String)null, "room.updateDoDevice.success", new Object[] {
/*      */ 
/*      */ 
/*      */             
/* 1597 */             dto.getValue().booleanValue() ? "開啟" : "關閉" });
/* 1598 */       return Boolean.valueOf(true);
/* 1599 */     } catch (Exception e) {
/* 1600 */       logger.error("UpdateDoDevice failed.", e);
/* 1601 */       addLog(request, OperationItem.SET, dto
/*      */ 
/*      */           
/* 1604 */           .getDeviceName(), OperationResult.FAILURE, (String)null, "room.updateDoDevice.success", new Object[] {
/*      */ 
/*      */ 
/*      */             
/* 1608 */             dto.getValue().booleanValue() ? "開啟" : "關閉" });
/* 1609 */       return Boolean.valueOf(false);
/*      */     } 
/*      */   }
/*      */   
/*      */   private BigInteger getDoValue(String deviceName, Integer address) {
/* 1614 */     IMap<Long, ModbusPinMapping> pMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusPinMapping);
/* 1615 */     IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/* 1616 */     BigInteger data = BigInteger.valueOf(0L);
/* 1617 */     EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*      */     
/* 1619 */     PredicateBuilder pb = eo.get("deviceName").equal(deviceName).and((Predicate)eo.get("address").equal(address));
/* 1620 */     for (ModbusPinMapping pin : pMap.values((Predicate)pb)) {
/* 1621 */       Integer value = (Integer)dataMap.get(pin.getKeyName());
/* 1622 */       if (value != null) {
/* 1623 */         if (value.intValue() == 1) {
/* 1624 */           data = data.setBit(pin.getBitNumber().intValue()); continue;
/*      */         } 
/* 1626 */         data = data.clearBit(pin.getBitNumber().intValue());
/*      */       } 
/*      */     } 
/*      */     
/* 1630 */     return data;
/*      */   }
/*      */   
/*      */   private ModbusMaster createModbusMaster(DeviceConfig devCfg) throws UnknownHostException {
/* 1634 */     TcpParameters tcpParameters = new TcpParameters();
/* 1635 */     tcpParameters.setHost(InetAddress.getByName(devCfg.getIp()));
/* 1636 */     tcpParameters.setKeepAlive(true);
/* 1637 */     tcpParameters.setPort(devCfg.getPort().intValue());
/* 1638 */     ModbusMaster modbus = ModbusMasterFactory.createModbusMasterTCP(tcpParameters);
/* 1639 */     return modbus;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addLog(HttpServletRequest request, OperationItem operationItem, String deviceName, OperationResult result, String remark, String descMsgId, Object... args) {
/* 1650 */     String userId = TripleDESUtils.decrypt(request.getHeader("encryptedUserLogin"));
/* 1651 */     String ip = request.getRemoteHost();
/* 1652 */     this.opLogger.addLog(userId, ip, SubSystem.WMS, operationItem, deviceName, new Date(), result, remark, descMsgId, args);
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\lib\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\restful\RoomCommonRestServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */