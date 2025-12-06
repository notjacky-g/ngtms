/*      */ package com.hwacom.ngtms.ao.restful;
/*      */ 
/*      */ import com.google.gson.Gson;
/*      */ import com.hazelcast.core.IMap;
/*      */ import com.hazelcast.query.EntryObject;
/*      */ import com.hazelcast.query.Predicate;
/*      */ import com.hazelcast.query.PredicateBuilder;
/*      */ import com.hwacom.ngtms.ao.fm.hz.AoHzMap;
/*      */ import com.hwacom.ngtms.ao.fm.marshaller.PdConfigData1DayMarshaller;
/*      */ import com.hwacom.ngtms.ao.fm.marshaller.PdOperationData1MinMarshaller;
/*      */ import com.hwacom.ngtms.ao.fm.model.LifeFaceLockCardData;
/*      */ import com.hwacom.ngtms.ao.fm.model.NcuCardTapData;
/*      */ import com.hwacom.ngtms.ao.fm.model.PowerStatusData;
/*      */ import com.hwacom.ngtms.ao.fm.model.RoomDeviceStatusRecord;
/*      */ import com.hwacom.ngtms.ao.fm.model.WaterPowerBaseConfig;
/*      */ import com.hwacom.ngtms.ao.fm.model.WaterStatusData;
/*      */ import com.hwacom.ngtms.ao.fm.repository.RoomDeviceStatusRecordRepository;
/*      */ import com.hwacom.ngtms.ao.shared.PdConfigData1Day;
/*      */ import com.hwacom.ngtms.ao.shared.PdOperationData1Min;
/*      */ import com.hwacom.ngtms.ao.shared.dto.AnalogyDataDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.NcuVoiceDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.PowerStatusDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.PowerWaterStatusDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomAnalogRecordDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomAnalogRecordQueryParamDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomAnalogTreeDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomDeviceParamDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomGroupDeviceStatusDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomInfoDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomNCUStatusDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomSetAndUnSetConfigDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomStaffPeopleDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomSvgGroupConifgDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.WaterStatusDTO;
/*      */ import com.hwacom.ngtms.ao.util.TransferHelper;
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
/*      */ import com.hwacom.ngtms.c.fm.service.OpLogger;
/*      */ import com.hwacom.ngtms.c.restful.BaseRestful;
/*      */ import com.hwacom.ngtms.c.shared.Direction;
/*      */ import com.hwacom.ngtms.c.shared.SubSystem;
/*      */ import com.hwacom.ngtms.c.shared.dto.DeviceSvgPositionConfigDTO;
/*      */ import com.hwacom.ngtms.ccs.fm.hz.CcsHzMap;
/*      */ import com.hwacom.ngtms.ccs.shared.CctvConfig;
/*      */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*      */ import com.hwacom.ngtms.common.fm.model.DeviceConfig;
/*      */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*      */ import com.hwacom.ngtms.hcce.util.HcceUtils;
/*      */ import com.hwacom.ngtms.pd.fm.hz.PdHzMap;
/*      */ import com.hwacom.ngtms.pd.fm.model.PdConfig;
/*      */ import com.hwacom.ngtms.pd.fm.model.PdLoopDeviceConfig;
/*      */ import com.hwacom.ngtms.pd.fm.model.PdLoopStatus;
/*      */ import com.hwacom.ngtms.pd.fm.model.PdStatus;
/*      */ import com.hwacom.ngtms.room.fm.hz.RoomHzMap;
/*      */ import com.hwacom.ngtms.room.fm.model.RoomBackgroundSvgConfig;
/*      */ import com.hwacom.ngtms.room.fm.model.RoomCardConfig;
/*      */ import com.hwacom.ngtms.room.shared.RtuConfig;
/*      */ import com.hwacom.ngtms.room.shared.SignalType;
/*      */ import com.hwacom.ngtms.room.shared.dto.RoomCctvUrlDTO;
/*      */ import com.hwacom.ngtms.room.shared.dto.RoomDeviceStatusDTO;
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
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.time.ZonedDateTime;
/*      */ import java.time.temporal.ChronoUnit;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.annotation.PostConstruct;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import org.apache.commons.lang3.SerializationUtils;
/*      */ import org.modelmapper.ModelMapper;
/*      */ import org.slf4j.Logger;
/*      */ import org.slf4j.LoggerFactory;
/*      */ import org.springframework.beans.factory.annotation.Autowired;
/*      */ import org.springframework.web.bind.annotation.CrossOrigin;
/*      */ import org.springframework.web.bind.annotation.PathVariable;
/*      */ import org.springframework.web.bind.annotation.RequestBody;
/*      */ import org.springframework.web.bind.annotation.RequestMapping;
/*      */ import org.springframework.web.bind.annotation.RequestMethod;
/*      */ import org.springframework.web.bind.annotation.RestController;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @CrossOrigin
/*      */ @RestController
/*      */ @RequestMapping({"/api/ao"})
/*      */ public class AoViewerRestServiceImpl
/*      */   extends BaseRestful
/*      */ {
/*  124 */   private static final Logger logger = LoggerFactory.getLogger(AoViewerRestServiceImpl.class);
/*      */   
/*      */   private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
/*      */   
/*  128 */   private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*      */   
/*      */   @Autowired
/*      */   private RoomDeviceStatusRecordRepository roomDeviceStatusRecordRepository;
/*      */   @Autowired
/*      */   private ModelMapper modelMapper;
/*      */   @Autowired
/*      */   OpLogger opLogger;
/*  136 */   private Gson gson = new Gson();
/*      */   
/*      */   @PostConstruct
/*      */   public void init() {
/*  140 */     setSubSystem(SubSystem.ROOM);
/*      */   }
/*      */   
/*      */   @RequestMapping(value = {"/replayUrl"}, method = {RequestMethod.GET})
/*      */   public Map<String, String> getReplayUrl() {
/*      */     try {
/*  146 */       Map<String, String> result = new HashMap<>();
/*      */       
/*  148 */       DynamicConfig dynamicConfig = HcceUtils.getDynaConfig("CcsFm", "ReplayUrl");
/*  149 */       result.put("ReplayUrl", dynamicConfig.getValue());
/*      */ 
/*      */       
/*  152 */       DynamicConfig screenDynamicConfig = HcceUtils.getDynaConfig("CcsFm", "ScreenUrl");
/*  153 */       result.put("ScreenUrl", screenDynamicConfig.getValue());
/*      */ 
/*      */       
/*  156 */       DynamicConfig videoDynamicConfig = HcceUtils.getDynaConfig("CcsFm", "VideoUrl");
/*  157 */       result.put("VideoUrl", videoDynamicConfig.getValue());
/*      */       
/*  159 */       return result;
/*  160 */     } catch (RuntimeException ex) {
/*  161 */       logger.error("get replay url failed.", ex);
/*  162 */       return Collections.emptyMap();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/pdOneDayConfigXml"}, produces = {"application/xml;charset=UTF-8"}, method = {RequestMethod.GET})
/*      */   public String getPdConfigXml() {
/*      */     try {
/*  173 */       logger.debug("pdOneDayConfigXml.");
/*  174 */       PdConfigData1DayMarshaller marshaller = new PdConfigData1DayMarshaller();
/*  175 */       String text = marshaller.convertToXmlString(getPdConfigData());
/*  176 */       StringBuilder textSb = new StringBuilder();
/*  177 */       textSb.insert(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
/*  178 */       textSb.append(text);
/*      */       
/*  180 */       return textSb.toString().replaceAll("__", "_");
/*  181 */     } catch (Exception e) {
/*  182 */       logger.error("pdOneDayConfigXml failded.", e);
/*  183 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/pdOneMinOperationXml"}, produces = {"application/xml;charset=UTF-8"}, method = {RequestMethod.GET})
/*      */   public String getPdOneMinOperationXml() {
/*      */     try {
/*  194 */       logger.debug("getPdOneMinOperationXml.");
/*  195 */       PdOperationData1MinMarshaller marshaller = new PdOperationData1MinMarshaller();
/*  196 */       String text = marshaller.convertToXmlString(getPdOperationData());
/*  197 */       StringBuilder textSb = new StringBuilder();
/*  198 */       textSb.insert(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
/*  199 */       textSb.append(text);
/*      */       
/*  201 */       return textSb.toString().replaceAll("__", "_");
/*  202 */     } catch (Exception e) {
/*  203 */       logger.error("getPdOneMinOperationXml failed.", e);
/*  204 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   @RequestMapping(value = {"roomAnalogTree"}, method = {RequestMethod.GET})
/*      */   public List<RoomAnalogTreeDTO> getRoomAnalogTree() {
/*      */     try {
/*  211 */       logger.debug("getRoomAnalogTree.");
/*  212 */       List<RoomAnalogTreeDTO> result = new ArrayList<>();
/*      */       
/*  214 */       IMap<String, DeviceHostLocation> hostLocationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceHostLocation);
/*  215 */       IMap<String, DeviceTcConfig> hostConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*      */       
/*  217 */       IMap<String, DeviceLocationMappingConfig> locationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/*  218 */       List<DeviceHostLocation> hostLocations = new ArrayList<>(hostLocationMap.values());
/*  219 */       hostLocations.sort(Comparator.comparing(DeviceHostLocation::getId));
/*      */ 
/*      */       
/*  222 */       for (DeviceHostLocation hostLocation : hostLocations) {
/*  223 */         String locationName = hostLocation.getLocName();
/*  224 */         List<RoomAnalogTreeDTO> childrenDTOs = new ArrayList<>();
/*      */ 
/*      */         
/*  227 */         Collection<DeviceLocationMappingConfig> hostConfigs = locationMap.values((Predicate)(new PredicateBuilder())
/*  228 */             .getEntryObject().get("locationName").equal(locationName));
/*  229 */         if (hostConfigs.size() == 0) {
/*      */           continue;
/*      */         }
/*  232 */         for (DeviceLocationMappingConfig each : hostConfigs) {
/*      */           
/*  234 */           RoomAnalogTreeDTO treeDto = TransferHelper.transferToRoomAnalogTreeDTO((DeviceTcConfig)hostConfigMap
/*  235 */               .get(each.getDeviceName()), each);
/*  236 */           if (treeDto != null) {
/*  237 */             childrenDTOs.add(treeDto);
/*      */           }
/*      */         } 
/*  240 */         if (childrenDTOs.size() == 0) {
/*      */           continue;
/*      */         }
/*  243 */         childrenDTOs.sort(
/*  244 */             Comparator.comparing(RoomAnalogTreeDTO::getId, String.CASE_INSENSITIVE_ORDER));
/*  245 */         RoomAnalogTreeDTO dto = new RoomAnalogTreeDTO();
/*  246 */         dto.setType(RoomAnalogTreeDTO.Type.LOCATION);
/*  247 */         dto.setId(String.valueOf(hostLocation.getId()));
/*  248 */         dto.setDisplayName(hostLocation.getLocName());
/*  249 */         dto.setChildren(childrenDTOs);
/*  250 */         result.add(dto);
/*      */       } 
/*  252 */       return result;
/*  253 */     } catch (Exception e) {
/*  254 */       logger.error("getRoomAnalogTree failed.", e);
/*  255 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomAnalogRecord"}, method = {RequestMethod.POST})
/*      */   public List<RoomAnalogRecordDTO> getRoomAnalogRecord(@RequestBody RoomAnalogRecordQueryParamDTO dto) {
/*      */     try {
/*  263 */       logger.debug("getRoomAnalogRecord.");
/*  264 */       List<RoomAnalogRecordDTO> result = new ArrayList<>();
/*  265 */       if (dto == null) {
/*  266 */         logger.error("RoomAnalogRecordQueryParamDTO is null");
/*  267 */         return Collections.emptyList();
/*      */       } 
/*  269 */       Date startTime = dto.getStartTime();
/*  270 */       Date endTime = dto.getEndTime();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  279 */       SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
/*  280 */       List<RoomAnalogTreeDTO> treeDtos = dto.getRoomAnalogList();
/*      */       
/*  282 */       List<String> deviceNames = new ArrayList<>();
/*      */       
/*  284 */       List<AnalogyDataDTO> analogyDataList = new ArrayList<>();
/*      */       
/*  286 */       Map<String, RoomDeviceStatusRecord> statusRecordMap = new HashMap<>();
/*      */ 
/*      */       
/*  289 */       for (RoomAnalogTreeDTO treeDto : treeDtos) {
/*  290 */         if (treeDto.getAnalogType() != null) {
/*  291 */           AnalogyDataDTO analogyData = new AnalogyDataDTO();
/*  292 */           analogyData.setDeviceName(treeDto.getId());
/*  293 */           analogyData.setLocationName(treeDto.getDisplayName());
/*  294 */           analogyData.setAnalogType(treeDto.getAnalogType());
/*  295 */           analogyDataList.add(analogyData);
/*  296 */           deviceNames.add(treeDto.getId());
/*      */         } 
/*      */       } 
/*      */       
/*  300 */       if (analogyDataList.size() == 0) {
/*  301 */         logger.warn("treeDto is null.");
/*  302 */         return Collections.emptyList();
/*      */       } 
/*      */       
/*  305 */       List<RoomDeviceStatusRecord> allDatas = this.roomDeviceStatusRecordRepository.findByDeviceNameInAndDataTimeBetween(deviceNames, startTime, endTime);
/*      */ 
/*      */ 
/*      */       
/*  309 */       for (RoomDeviceStatusRecord record : allDatas) {
/*  310 */         String key = record.getDeviceName() + "_" + format.format(record.getDataTime());
/*  311 */         statusRecordMap.put(key, record);
/*      */       } 
/*  313 */       long diffSec = (endTime.getTime() - startTime.getTime()) / 1000L;
/*  314 */       long diffMin = diffSec / 60L;
/*  315 */       Calendar startCal = Calendar.getInstance();
/*  316 */       startCal.setTime(startTime);
/*      */ 
/*      */       
/*  319 */       for (int i = 0; i < diffMin; i++) {
/*  320 */         RoomAnalogRecordDTO recordDto = new RoomAnalogRecordDTO();
/*  321 */         recordDto.setId("" + i);
/*      */         
/*  323 */         for (AnalogyDataDTO analogyData : analogyDataList) {
/*  324 */           String deviceName = analogyData.getDeviceName();
/*  325 */           String key = analogyData.getDeviceName() + "_" + format.format(startCal.getTime());
/*  326 */           RoomDeviceStatusRecord record = statusRecordMap.get(key);
/*      */           
/*  328 */           if (record == null && (startCal
/*  329 */             .getTime().equals(startTime) || startCal.getTime().equals(endTime))) {
/*      */ 
/*      */             
/*  332 */             List<RoomDeviceStatusRecord> recordList = this.roomDeviceStatusRecordRepository.findTop1ByDeviceNameAndDataTimeLessThanEqualOrderByDataTimeDesc(analogyData
/*  333 */                 .getDeviceName(), startCal.getTime());
/*  334 */             if (recordList != null) {
/*  335 */               record = recordList.get(0);
/*      */             }
/*  337 */             statusRecordMap.put(key, record);
/*      */           }
/*  339 */           else if (record == null) {
/*  340 */             Calendar before1MinCal = Calendar.getInstance();
/*  341 */             before1MinCal.setTime(startCal.getTime());
/*  342 */             before1MinCal.add(12, -1);
/*  343 */             String lastKey = deviceName + "_" + format.format(before1MinCal.getTime());
/*  344 */             record = statusRecordMap.get(lastKey);
/*  345 */             statusRecordMap.put(key, record);
/*      */           } 
/*  347 */           fillData(recordDto, analogyData, record);
/*      */         } 
/*  349 */         recordDto.setDataTime(startCal.getTime());
/*  350 */         result.add(recordDto);
/*      */         
/*  352 */         startCal.add(12, 1);
/*      */       } 
/*  354 */       return result;
/*  355 */     } catch (Exception e) {
/*  356 */       logger.error("getRoomAnalogRecord failed.", e);
/*  357 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private RoomAnalogRecordDTO fillData(RoomAnalogRecordDTO recordDto, AnalogyDataDTO anologyData, RoomDeviceStatusRecord record) {
/*  364 */     if (recordDto.getPoint1Data() == null) {
/*  365 */       recordDto.setPoint1Data(anologyData);
/*  366 */       if (record != null) {
/*  367 */         recordDto.setPoint1Value(Double.valueOf(Double.valueOf(record.getDensity()).doubleValue() / 10.0D));
/*      */       }
/*  369 */     } else if (recordDto.getPoint2Data() == null) {
/*  370 */       recordDto.setPoint2Data(anologyData);
/*  371 */       if (record != null) {
/*  372 */         recordDto.setPoint2Value(Double.valueOf(Double.valueOf(record.getDensity()).doubleValue() / 10.0D));
/*      */       }
/*  374 */     } else if (recordDto.getPoint3Data() == null) {
/*  375 */       recordDto.setPoint3Data(anologyData);
/*  376 */       if (record != null) {
/*  377 */         recordDto.setPoint3Value(Double.valueOf(Double.valueOf(record.getDensity()).doubleValue() / 10.0D));
/*      */       }
/*  379 */     } else if (recordDto.getPoint4Data() == null) {
/*  380 */       recordDto.setPoint4Data(anologyData);
/*  381 */       if (record != null) {
/*  382 */         recordDto.setPoint4Value(Double.valueOf(Double.valueOf(record.getDensity()).doubleValue() / 10.0D));
/*      */       }
/*  384 */     } else if (recordDto.getPoint5Data() == null) {
/*  385 */       recordDto.setPoint5Data(anologyData);
/*  386 */       if (record != null) {
/*  387 */         recordDto.setPoint5Value(Double.valueOf(Double.valueOf(record.getDensity()).doubleValue() / 10.0D));
/*      */       }
/*  389 */     } else if (recordDto.getPoint6Data() == null) {
/*  390 */       recordDto.setPoint6Data(anologyData);
/*  391 */       if (record != null) {
/*  392 */         recordDto.setPoint6Value(Double.valueOf(Double.valueOf(record.getDensity()).doubleValue() / 10.0D));
/*      */       }
/*  394 */     } else if (recordDto.getPoint7Data() == null) {
/*  395 */       recordDto.setPoint7Data(anologyData);
/*  396 */       if (record != null) {
/*  397 */         recordDto.setPoint7Value(Double.valueOf(Double.valueOf(record.getDensity()).doubleValue() / 10.0D));
/*      */       }
/*  399 */     } else if (recordDto.getPoint8Data() == null) {
/*  400 */       recordDto.setPoint8Data(anologyData);
/*  401 */       if (record != null) {
/*  402 */         recordDto.setPoint8Value(Double.valueOf(Double.valueOf(record.getDensity()).doubleValue() / 10.0D));
/*      */       }
/*  404 */     } else if (recordDto.getPoint9Data() == null) {
/*  405 */       recordDto.setPoint9Data(anologyData);
/*  406 */       if (record != null) {
/*  407 */         recordDto.setPoint9Value(Double.valueOf(Double.valueOf(record.getDensity()).doubleValue() / 10.0D));
/*      */       }
/*  409 */     } else if (recordDto.getPoint10Data() == null) {
/*  410 */       recordDto.setPoint10Data(anologyData);
/*  411 */       if (record != null) {
/*  412 */         recordDto.setPoint10Value(Double.valueOf(Double.valueOf(record.getDensity()).doubleValue() / 10.0D));
/*      */       }
/*      */     } 
/*  415 */     return recordDto;
/*      */   }
/*      */   
/*      */   private PdConfigData1Day getPdConfigData() {
/*  419 */     PdConfigData1Day data = new PdConfigData1Day();
/*      */     try {
/*  421 */       logger.debug("Get Pd config data.");
/*  422 */       IMap<String, PdConfig> pdConfigMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.Config);
/*  423 */       IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*      */       
/*  425 */       IMap<Long, PdLoopDeviceConfig> pdLoopDeviceConfigMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.LoopDeviceConfig);
/*  426 */       data.setTime(this.dateFormat.format(new Date()));
/*  427 */       data.setFileName("1day_pd_config_data.xml");
/*  428 */       data.setControlCenterId("THB-3R");
/*  429 */       Map<String, List<String>> roomMap = new HashMap<>();
/*  430 */       for (PdConfig pdConfig : pdConfigMap.values()) {
/*  431 */         List<String> list = new ArrayList<>();
/*  432 */         if (roomMap.get(getLocationName(pdConfig.getDeviceName())) != null) {
/*  433 */           list = roomMap.get(getLocationName(pdConfig.getDeviceName()));
/*      */         }
/*  435 */         list.add(pdConfig.getDeviceName());
/*  436 */         roomMap.put(getLocationName(pdConfig.getDeviceName()), list);
/*      */       } 
/*      */       
/*  439 */       List<PdConfigData1Day.EngineRoom> rooms = new ArrayList<>();
/*  440 */       for (Map.Entry<String, List<String>> each : roomMap.entrySet()) {
/*  441 */         PdConfigData1Day.EngineRoom room = new PdConfigData1Day.EngineRoom();
/*  442 */         room.setId(each.getKey());
/*  443 */         List<PdConfigData1Day.EngineRoom.PdConfig> pds = new ArrayList<>();
/*  444 */         for (String pdDevice : each.getValue()) {
/*  445 */           PdConfigData1Day.EngineRoom.PdConfig pd = new PdConfigData1Day.EngineRoom.PdConfig();
/*  446 */           DeviceTcConfig config = (DeviceTcConfig)deviceConfigMap.get(pdDevice);
/*  447 */           pd.setId(pdDevice);
/*  448 */           if (config != null) {
/*  449 */             pd.setDirectionId(getDirectionId(config.getDirection()));
/*  450 */             pd.setMilepost(String.valueOf(config.getMilepost()));
/*  451 */             pd.setOdhId("");
/*  452 */             pd.setOdhIp("");
/*  453 */             pd.setLatitude(String.valueOf(config.getLatitude()));
/*  454 */             pd.setLongitude(String.valueOf(config.getLongitude()));
/*  455 */             pd.setFreewayId(config.getLineId());
/*  456 */             List<PdConfigData1Day.EngineRoom.PdConfig.Loop> loops = new ArrayList<>();
/*  457 */             for (int i = 1; i <= ((PdConfig)pdConfigMap.get(pdDevice)).getLoopNo().intValue(); i++) {
/*  458 */               PdConfigData1Day.EngineRoom.PdConfig.Loop loop = new PdConfigData1Day.EngineRoom.PdConfig.Loop();
/*  459 */               loop.setId(String.valueOf(i));
/*  460 */               List<PdConfigData1Day.EngineRoom.PdConfig.Loop.Eq> eqs = new ArrayList<>();
/*  461 */               EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*      */               
/*  463 */               PredicateBuilder pb = eo.get("pdDeviceName").equal(pdDevice).and((Predicate)eo.get("loopNo").equal(Integer.valueOf(i)));
/*  464 */               for (PdLoopDeviceConfig loopDeviceConfig : pdLoopDeviceConfigMap.values((Predicate)pb)) {
/*  465 */                 PdConfigData1Day.EngineRoom.PdConfig.Loop.Eq eq = new PdConfigData1Day.EngineRoom.PdConfig.Loop.Eq();
/*  466 */                 eq.setEqId(loopDeviceConfig.getDeviceName());
/*  467 */                 eqs.add(eq);
/*      */               } 
/*  469 */               loop.setEqs(eqs);
/*  470 */               loops.add(loop);
/*      */             } 
/*  472 */             pd.setLoops(loops);
/*      */           } 
/*  474 */           pds.add(pd);
/*      */         } 
/*  476 */         room.setPd(pds);
/*  477 */         rooms.add(room);
/*      */       } 
/*  479 */       data.setRooms(rooms);
/*      */     }
/*  481 */     catch (RuntimeException e) {
/*  482 */       logger.error("Get Pd config data failed.", e);
/*      */     } 
/*  484 */     return data;
/*      */   }
/*      */   
/*      */   public PdOperationData1Min getPdOperationData() {
/*  488 */     PdOperationData1Min data = new PdOperationData1Min();
/*      */     try {
/*  490 */       logger.debug("Get Pd operation data.");
/*  491 */       IMap<String, PdConfig> pdConfigMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.Config);
/*  492 */       IMap<String, PdStatus> pdStatusMap = HzUtils.getMap((HzDistObjEnum)PdHzMap.Status);
/*  493 */       data.setTime(this.dateFormat.format(new Date()));
/*  494 */       data.setFileName("1min_pd_operation_data.xml");
/*  495 */       data.setControlCenterId("THB-3R");
/*  496 */       List<PdOperationData1Min.Pd> list = new ArrayList<>();
/*  497 */       for (PdStatus status : pdStatusMap.values()) {
/*  498 */         PdOperationData1Min.Pd pd = new PdOperationData1Min.Pd();
/*  499 */         pd.setId(status.getDeviceName());
/*  500 */         pd.setPdCommonStatus(String.valueOf(status.getConnectivity()));
/*  501 */         PdOperationData1Min.Pd.Primary p = new PdOperationData1Min.Pd.Primary();
/*  502 */         p.setR((status.getPrimaryR() != null) ? String.valueOf(status.getPrimaryR()) : "1");
/*  503 */         p.setS((status.getPrimaryS() != null) ? String.valueOf(status.getPrimaryS()) : "1");
/*  504 */         p.setT((status.getPrimaryT() != null) ? String.valueOf(status.getPrimaryT()) : "1");
/*  505 */         pd.setPrimary(p);
/*  506 */         PdOperationData1Min.Pd.Secondary s = new PdOperationData1Min.Pd.Secondary();
/*  507 */         s.setR((status.getSecondaryR() != null) ? String.valueOf(status.getSecondaryR()) : "1");
/*  508 */         s.setS((status.getSecondaryS() != null) ? String.valueOf(status.getSecondaryS()) : "1");
/*  509 */         s.setT((status.getSecondaryT() != null) ? String.valueOf(status.getSecondaryT()) : "1");
/*  510 */         pd.setSecondary(s);
/*  511 */         PdOperationData1Min.Pd.LoopList loopList = new PdOperationData1Min.Pd.LoopList();
/*  512 */         List<PdOperationData1Min.Pd.Loop> loops = new ArrayList<>();
/*  513 */         loopList.setCapacity(String.valueOf(((PdConfig)pdConfigMap.get(status.getDeviceName())).getLoopNo()));
/*  514 */         for (int i = 1; i <= ((PdConfig)pdConfigMap.get(status.getDeviceName())).getLoopNo().intValue(); i++) {
/*  515 */           PdOperationData1Min.Pd.Loop loop = new PdOperationData1Min.Pd.Loop();
/*  516 */           loop.setId(String.valueOf(i));
/*  517 */           if (status.getConnectivity().intValue() == 0) {
/*  518 */             for (PdLoopStatus loopStatus : status.getPdLoops()) {
/*  519 */               if (loopStatus.getLoopId().equals(loop.getId())) {
/*  520 */                 loop.setUsage(String.valueOf(loopStatus.getStatus()));
/*      */               }
/*      */             } 
/*      */           } else {
/*  524 */             loop.setUsage("1");
/*      */           } 
/*  526 */           loops.add(loop);
/*      */         } 
/*  528 */         loopList.setLoops(loops);
/*  529 */         pd.setLoopList(loopList);
/*  530 */         list.add(pd);
/*      */       } 
/*  532 */       data.setPdList(list);
/*  533 */     } catch (RuntimeException e) {
/*  534 */       logger.error("Get Pd operation data failed.", e);
/*      */     } 
/*  536 */     return data;
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomGroupDeviceData"}, method = {RequestMethod.GET})
/*      */   public RoomSetAndUnSetConfigDTO getRoomGroupDeviceData() {
/*      */     try {
/*  543 */       logger.info("Get RoomDeviceGroupData.");
/*  544 */       RoomSetAndUnSetConfigDTO result = new RoomSetAndUnSetConfigDTO();
/*      */ 
/*      */       
/*  547 */       IMap<String, DeviceLocationMappingConfig> roomLocationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/*      */ 
/*      */       
/*  550 */       IMap<String, DeviceSvgPositionConfig> configMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DevicePositionConfig);
/*      */       
/*  552 */       IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*      */ 
/*      */       
/*  555 */       Map<String, String> deviceNameMap = new HashMap<>();
/*      */       
/*  557 */       Map<String, Set<String>> deviceNameUnsetMap = new HashMap<>();
/*      */       
/*  559 */       Map<String, Set<String>> deviceNameSetMap = new HashMap<>();
/*      */ 
/*      */ 
/*      */       
/*  563 */       Map<String, Map<String, Set<String>>> groupNameSetMap = new HashMap<>();
/*      */       
/*  565 */       for (DeviceLocationMappingConfig mappingConfig : roomLocationMap.values()) {
/*  566 */         String locationName = mappingConfig.getLocationName();
/*  567 */         String svgName = mappingConfig.getSvgName();
/*  568 */         if (locationName != null && locationName != "" && svgName != null && svgName != "") {
/*  569 */           String key = mappingConfig.getDeviceName();
/*  570 */           String value = locationName + "-" + mappingConfig.getSubLocation();
/*  571 */           deviceNameMap.put(key, value);
/*      */         } 
/*      */       } 
/*      */       
/*  575 */       for (DeviceSvgPositionConfig svgPositionConfig : configMap.values()) {
/*  576 */         String deviceName = svgPositionConfig.getDeviceName();
/*  577 */         String groupName = svgPositionConfig.getDeviceGroupName();
/*      */         
/*  579 */         if (deviceNameMap.get(deviceName) != null) {
/*  580 */           String key = deviceNameMap.get(deviceName);
/*      */           
/*  582 */           if (groupName == null || groupName.isEmpty()) {
/*      */             
/*  584 */             if (deviceNameUnsetMap.get(key) == null) {
/*  585 */               Set<String> deviceNames = new HashSet<>();
/*  586 */               deviceNameUnsetMap.put(key, deviceNames);
/*      */             } 
/*  588 */             ((Set<String>)deviceNameUnsetMap.get(key)).add(deviceName);
/*      */             
/*  590 */             if (deviceNameSetMap.get(key) == null) {
/*  591 */               Set<String> deviceNames = new HashSet<>();
/*  592 */               deviceNameSetMap.put(key, deviceNames);
/*      */             } 
/*      */             continue;
/*      */           } 
/*  596 */           if (deviceNameSetMap.get(key) == null) {
/*  597 */             Set<String> deviceNames = new HashSet<>();
/*  598 */             deviceNameSetMap.put(key, deviceNames);
/*      */           } 
/*  600 */           ((Set<String>)deviceNameSetMap.get(key)).add(deviceName);
/*      */           
/*  602 */           if (groupNameSetMap.get(key) == null) {
/*  603 */             Map<String, Set<String>> gMap = new HashMap<>();
/*  604 */             groupNameSetMap.put(key, gMap);
/*      */           } 
/*  606 */           Map<String, Set<String>> gValue = groupNameSetMap.get(key);
/*  607 */           if (!gValue.containsKey(groupName)) {
/*  608 */             gValue.put(groupName, new HashSet<>());
/*      */           }
/*  610 */           ((Set<String>)gValue.get(groupName)).add(deviceName);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  615 */       List<RoomSvgGroupConifgDTO> unsetGourpConfig = new ArrayList<>();
/*  616 */       for (Map.Entry<String, Set<String>> entry : deviceNameUnsetMap.entrySet()) {
/*  617 */         String locRoom = entry.getKey();
/*  618 */         RoomSvgGroupConifgDTO unsetDto = new RoomSvgGroupConifgDTO();
/*  619 */         unsetDto.setNodeKey(locRoom);
/*  620 */         unsetDto.setNodeName(locRoom);
/*  621 */         unsetDto.setRoomName(locRoom);
/*  622 */         unsetDto.setGroupName(null);
/*  623 */         unsetDto.setCheck(Boolean.valueOf(false));
/*  624 */         List<RoomSvgGroupConifgDTO> unsetGourpChildren = new ArrayList<>();
/*  625 */         for (String deviceName : entry.getValue()) {
/*  626 */           RoomSvgGroupConifgDTO unsetChild = new RoomSvgGroupConifgDTO();
/*  627 */           unsetChild.setNodeKey(deviceName);
/*  628 */           if (deviceConfigMap.get(deviceName) != null) {
/*  629 */             unsetChild.setNodeName(((DeviceTcConfig)deviceConfigMap.get(deviceName)).getDisplayName());
/*      */           } else {
/*  631 */             unsetChild.setNodeName(deviceName);
/*      */           } 
/*  633 */           unsetChild.setRoomName(locRoom);
/*  634 */           unsetChild.setGroupName(null);
/*  635 */           unsetChild.setCheck(Boolean.valueOf(true));
/*  636 */           unsetGourpChildren.add(unsetChild);
/*      */         } 
/*  638 */         unsetDto.setChildren(unsetGourpChildren);
/*  639 */         unsetGourpConfig.add(unsetDto);
/*      */       } 
/*      */       
/*  642 */       List<RoomSvgGroupConifgDTO> setGourpConfig = new ArrayList<>();
/*  643 */       for (Map.Entry<String, Set<String>> entry : deviceNameSetMap.entrySet()) {
/*  644 */         RoomSvgGroupConifgDTO setDto = new RoomSvgGroupConifgDTO();
/*  645 */         String locRoom = entry.getKey();
/*  646 */         setDto.setNodeKey(locRoom);
/*  647 */         setDto.setNodeName(locRoom);
/*  648 */         setDto.setRoomName(locRoom);
/*  649 */         setDto.setGroupName(null);
/*  650 */         setDto.setCheck(Boolean.valueOf(false));
/*  651 */         if (groupNameSetMap.get(locRoom) != null) {
/*  652 */           List<RoomSvgGroupConifgDTO> setGourpFirstChildren = new ArrayList<>();
/*  653 */           for (String name : ((Map)groupNameSetMap.get(locRoom)).keySet()) {
/*  654 */             RoomSvgGroupConifgDTO firstChild = new RoomSvgGroupConifgDTO();
/*  655 */             firstChild.setNodeKey(locRoom + "-" + name);
/*  656 */             firstChild.setNodeName(name);
/*  657 */             firstChild.setRoomName(locRoom);
/*  658 */             firstChild.setGroupName(name);
/*  659 */             firstChild.setCheck(Boolean.valueOf(false));
/*  660 */             List<RoomSvgGroupConifgDTO> setGourpSecondChildren = new ArrayList<>();
/*  661 */             ((Set)((Map)groupNameSetMap
/*  662 */               .get(locRoom))
/*  663 */               .get(name))
/*  664 */               .forEach(deviceName -> {
/*      */                   RoomSvgGroupConifgDTO secondChild = new RoomSvgGroupConifgDTO();
/*      */                   
/*      */                   secondChild.setNodeKey(deviceName);
/*      */                   if (deviceConfigMap.get(deviceName) != null) {
/*      */                     secondChild.setNodeName(((DeviceTcConfig)deviceConfigMap.get(deviceName)).getDisplayName());
/*      */                   } else {
/*      */                     secondChild.setNodeName(deviceName);
/*      */                   } 
/*      */                   secondChild.setRoomName(locRoom);
/*      */                   secondChild.setGroupName(name);
/*      */                   secondChild.setCheck(Boolean.valueOf(true));
/*      */                   setGourpSecondChildren.add(secondChild);
/*      */                 });
/*  678 */             firstChild.setChildren(setGourpSecondChildren);
/*  679 */             setGourpFirstChildren.add(firstChild);
/*      */           } 
/*  681 */           setDto.setChildren(setGourpFirstChildren);
/*      */         } 
/*  683 */         setGourpConfig.add(setDto);
/*      */       } 
/*  685 */       result.setSetGroupConfigList(setGourpConfig);
/*  686 */       result.setUnsetGroupConfigList(unsetGourpConfig);
/*  687 */       return result;
/*  688 */     } catch (Exception e) {
/*  689 */       logger.error("Get RoomDeviceGroupData failed.", e);
/*  690 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomGroupDeviceData"}, method = {RequestMethod.POST})
/*      */   public Boolean saveRoomGroupDeviceData(HttpServletRequest request, @RequestBody List<RoomSvgGroupConifgDTO> list) {
/*  699 */     IMap<String, DeviceSvgPositionConfig> configMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DevicePositionConfig);
/*      */     
/*  701 */     IMap<String, RoomBackgroundSvgConfig> backConfigMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.BackgroundSvgConfig);
/*      */     try {
/*  703 */       logger.info("Save RoomGroupDeviceData, size='{}'", Integer.valueOf(list.size()));
/*      */       
/*  705 */       EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*  706 */       PredicateBuilder pb = eo.get("deviceGroupName").isNotNull();
/*  707 */       Map<String, DeviceSvgPositionConfig> saveMap = new HashMap<>();
/*  708 */       for (DeviceSvgPositionConfig config : configMap.values((Predicate)pb)) {
/*  709 */         config.setDeviceGroupName(null);
/*  710 */         saveMap.put(config.getId(), config);
/*      */       } 
/*  712 */       configMap.putAll(saveMap);
/*  713 */       saveMap.clear();
/*      */ 
/*      */       
/*  716 */       for (RoomSvgGroupConifgDTO device : list) {
/*      */         
/*  718 */         EntryObject eo1 = (new PredicateBuilder()).getEntryObject();
/*  719 */         PredicateBuilder pb1 = eo1.get("deviceName").equal(device.getNodeKey());
/*  720 */         Collection<DeviceSvgPositionConfig> configList = configMap.values((Predicate)pb1);
/*  721 */         if (configList != null && !configList.isEmpty()) {
/*  722 */           configList.forEach(svgConfig -> {
/*      */                 svgConfig.setDeviceGroupName(device.getGroupName());
/*      */                 saveMap.put(svgConfig.getId(), svgConfig);
/*      */               });
/*      */           continue;
/*      */         } 
/*  728 */         String[] roomName = device.getRoomName().split("-");
/*  729 */         String roomFormat = null;
/*  730 */         if (roomName.length > 1) {
/*  731 */           if (roomName[1] != null && !roomName[1].isEmpty()) {
/*  732 */             if (roomName[1].equals("1F")) {
/*  733 */               roomFormat = roomName[0] + "1樓";
/*  734 */             } else if (roomName[1].equals("2F")) {
/*  735 */               roomFormat = roomName[0] + "2樓";
/*  736 */             } else if (roomName[1].equals("3F")) {
/*  737 */               roomFormat = roomName[0] + "3樓";
/*  738 */             } else if (roomName[1].equals("4F")) {
/*  739 */               roomFormat = roomName[0] + "4樓";
/*  740 */             } else if (roomName[1].equals("5F")) {
/*  741 */               roomFormat = roomName[0] + "5樓";
/*  742 */             } else if (roomName[1].equals("B1")) {
/*  743 */               roomFormat = roomName[0] + "地下1樓";
/*  744 */             } else if (roomName[1].equals("B2")) {
/*  745 */               roomFormat = roomName[0] + "地下2樓";
/*  746 */             } else if (roomName[1].equals("B3")) {
/*  747 */               roomFormat = roomName[0] + "地下3樓";
/*      */             } 
/*      */           } else {
/*  750 */             roomFormat = roomName[0];
/*      */           } 
/*      */         } else {
/*  753 */           roomFormat = roomName[0];
/*      */         } 
/*  755 */         EntryObject eo2 = (new PredicateBuilder()).getEntryObject();
/*  756 */         PredicateBuilder pb2 = eo2.get("description").equal(roomFormat);
/*  757 */         DeviceSvgPositionConfig config = new DeviceSvgPositionConfig();
/*  758 */         config.setId(UuidUtils.generateUUIDStr());
/*  759 */         config.setDeviceName(device.getNodeKey());
/*  760 */         config.setDeviceGroupName(device.getGroupName());
/*  761 */         backConfigMap
/*  762 */           .values((Predicate)pb2)
/*  763 */           .forEach(svgMap -> config.setSvgName(svgMap.getNameId()));
/*      */ 
/*      */ 
/*      */         
/*  767 */         saveMap.put(config.getId(), config);
/*      */       } 
/*      */       
/*  770 */       configMap.putAll(saveMap);
/*  771 */       return Boolean.TRUE;
/*  772 */     } catch (Exception e) {
/*  773 */       logger.error("Save RoomGroupDeviceData failed.", e);
/*  774 */       return Boolean.FALSE;
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
/*  787 */       logger.debug("QueryDevicePositionConfig, svgName='{}'", svgName);
/*  788 */       List<DeviceSvgPositionConfigDTO> result = new ArrayList<>();
/*      */       
/*  790 */       IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*      */ 
/*      */       
/*  793 */       IMap<String, DeviceSvgPositionConfig> positionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DevicePositionConfig);
/*      */       
/*  795 */       Map<String, List<DeviceSvgPositionConfig>> groupMap = new HashMap<>();
/*      */ 
/*      */       
/*  798 */       PredicateBuilder predicate = (new PredicateBuilder()).getEntryObject().get("svgName").equal(svgName);
/*      */       
/*  800 */       positionMap
/*  801 */         .values((Predicate)predicate)
/*  802 */         .forEach(svgValue -> {
/*      */             String deviceName = svgValue.getDeviceName();
/*      */             
/*      */             String groupName = svgValue.getDeviceGroupName();
/*      */             DeviceTcConfig tcConfig = (DeviceTcConfig)deviceMap.get(deviceName);
/*      */             if (groupName != null && !groupName.isEmpty()) {
/*      */               List<DeviceSvgPositionConfig> positionConfigs = (List<DeviceSvgPositionConfig>)groupMap.get(groupName);
/*      */               if (positionConfigs == null) {
/*      */                 positionConfigs = new ArrayList<>();
/*      */                 positionConfigs.add(svgValue);
/*      */                 groupMap.put(groupName, positionConfigs);
/*      */               } 
/*      */             } else {
/*      */               DeviceSvgPositionConfigDTO dto = new DeviceSvgPositionConfigDTO();
/*      */               dto = (DeviceSvgPositionConfigDTO)this.modelMapper.map(svgValue, DeviceSvgPositionConfigDTO.class);
/*      */               dto.setDisplayName(tcConfig.getDisplayName());
/*      */               dto.setDeviceType(tcConfig.getDeviceType());
/*      */               result.add(dto);
/*      */             } 
/*      */           });
/*  822 */       if (groupMap.size() > 0) {
/*  823 */         for (List<DeviceSvgPositionConfig> positionConfig : groupMap.values()) {
/*  824 */           DeviceSvgPositionConfigDTO dto = new DeviceSvgPositionConfigDTO();
/*  825 */           DeviceSvgPositionConfig config = positionConfig.get(0);
/*  826 */           String deviceGroupName = config.getDeviceGroupName();
/*  827 */           Float positionX = config.getPositionX();
/*  828 */           Float positionY = config.getPositionY();
/*  829 */           if (positionX == null) {
/*  830 */             positionX = Float.valueOf(0.0F);
/*      */           }
/*  832 */           if (positionY == null) {
/*  833 */             positionY = Float.valueOf(0.0F);
/*      */           }
/*  835 */           dto.setId(UuidUtils.generateUUIDStr());
/*  836 */           dto.setSvgName(svgName);
/*  837 */           dto.setDeviceName(deviceGroupName);
/*  838 */           dto.setDisplayName(deviceGroupName);
/*  839 */           dto.setDeviceType("ROOM_DEVICE_GROUP");
/*  840 */           dto.setTrafficDirection(null);
/*  841 */           dto.setPositionX(positionX);
/*  842 */           dto.setPositionY(positionY);
/*  843 */           dto.setDeviceGroupName(deviceGroupName);
/*  844 */           result.add(dto);
/*      */         } 
/*      */       }
/*  847 */       return result;
/*  848 */     } catch (RuntimeException e) {
/*  849 */       logger.error("QueryDevicePositionConfig failed.", e);
/*  850 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomDeviceStatusConfig"}, method = {RequestMethod.POST})
/*      */   public List<RoomDeviceStatusDTO> refreshRoomDeviceStatus(@RequestBody RoomDeviceParamDTO dto) {
/*      */     try {
/*  862 */       logger.info("Refresh RoomDeviceStatus.");
/*  863 */       List<String> deviceNameList = dto.getDeviceNameList();
/*  864 */       String svgName = dto.getSelectedSubLocationId();
/*  865 */       if (deviceNameList == null) {
/*  866 */         logger.error("deviceNameList is null.");
/*  867 */         return Collections.emptyList();
/*      */       } 
/*  869 */       if (svgName == null) {
/*  870 */         logger.error("svgName is null.");
/*  871 */         return Collections.emptyList();
/*      */       } 
/*  873 */       List<RoomDeviceStatusDTO> result = new ArrayList<>();
/*      */ 
/*      */       
/*  876 */       IMap<String, DeviceSvgPositionConfig> positionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DevicePositionConfig);
/*      */       
/*  878 */       IMap<String, DeviceTcStatus> statusMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcStatus);
/*      */       
/*  880 */       IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*      */       
/*  882 */       String[] deviceNameArray = deviceNameList.<String>toArray(new String[deviceNameList.size()]);
/*  883 */       EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*      */       
/*  885 */       PredicateBuilder pb = eo.get("deviceGroupName").in((Comparable[])deviceNameArray).and((Predicate)eo.get("svgName").in(new Comparable[] { svgName }));
/*  886 */       Set<String> groupNameList = new HashSet<>();
/*  887 */       Map<String, List<DeviceSvgPositionConfig>> groupDeviceMap = new HashMap<>();
/*  888 */       for (DeviceSvgPositionConfig positionConfig : positionMap.values((Predicate)pb)) {
/*  889 */         groupNameList.add(positionConfig.getDeviceGroupName());
/*  890 */         String deviceGroupName = positionConfig.getDeviceGroupName();
/*  891 */         List<DeviceSvgPositionConfig> positionConfigList = groupDeviceMap.get(deviceGroupName);
/*  892 */         if (positionConfigList == null) {
/*  893 */           positionConfigList = new ArrayList<>();
/*      */         }
/*  895 */         positionConfigList.add(positionConfig);
/*  896 */         groupDeviceMap.put(deviceGroupName, positionConfigList);
/*      */       } 
/*      */ 
/*      */       
/*  900 */       for (Map.Entry<String, List<DeviceSvgPositionConfig>> entry : groupDeviceMap.entrySet()) {
/*  901 */         Integer groupCommStatus = Integer.valueOf(0);
/*  902 */         RoomDeviceStatusDTO groupStatus = new RoomDeviceStatusDTO();
/*  903 */         groupStatus.setDeviceName(entry.getKey());
/*  904 */         groupStatus.setDeviceType("ROOM_DEVICE_GROUP");
/*  905 */         for (DeviceSvgPositionConfig config : entry.getValue()) {
/*  906 */           String deviceName = config.getDeviceName();
/*  907 */           DeviceTcStatus deviceStatus = (DeviceTcStatus)statusMap.get(deviceName);
/*  908 */           if (deviceStatus.getDeviceType().equals("充電機狀態") && deviceStatus
/*  909 */             .getContextData() != null) {
/*  910 */             Integer value = (Integer)SerializationUtils.deserialize(deviceStatus.getContextData());
/*  911 */             if (value.intValue() == 1) {
/*  912 */               groupCommStatus = Integer.valueOf(1);
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*  917 */           if (deviceStatus.getDeviceType().equals("空調") && deviceStatus.getContextData() != null) {
/*  918 */             Integer value = (Integer)SerializationUtils.deserialize(deviceStatus.getContextData());
/*  919 */             if (value.intValue() == 1 && ((DeviceTcConfig)deviceMap.get(deviceName)).getDisplayName().contains("主要警報")) {
/*  920 */               groupCommStatus = Integer.valueOf(1);
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*  925 */           if (deviceStatus == null || deviceStatus
/*  926 */             .getCommStatus() == null || deviceStatus
/*  927 */             .getCommStatus().toString().equals("1")) {
/*  928 */             groupCommStatus = Integer.valueOf(1);
/*      */             break;
/*      */           } 
/*      */         } 
/*  932 */         groupStatus.setStatus(groupCommStatus);
/*  933 */         result.add(groupStatus);
/*      */       } 
/*      */ 
/*      */       
/*  937 */       deviceNameList.removeAll(groupNameList);
/*      */       
/*  939 */       if (deviceNameList != null && !deviceNameList.isEmpty()) {
/*  940 */         for (String deviceName : deviceNameList) {
/*  941 */           DeviceTcConfig deviceConfig = (DeviceTcConfig)deviceMap.get(deviceName);
/*  942 */           String deviceType = "";
/*  943 */           if (deviceConfig != null) {
/*  944 */             deviceType = deviceConfig.getDeviceType();
/*      */           }
/*      */           
/*  947 */           Integer commStatus = Integer.valueOf((statusMap.get(deviceName) != null && ((DeviceTcStatus)statusMap.get(deviceName)).getCommStatus() != null) ? ((DeviceTcStatus)statusMap
/*  948 */               .get(deviceName)).getCommStatus().intValue() : 1);
/*      */ 
/*      */           
/*  951 */           RtuConfig config = null;
/*  952 */           if (!"cardReader".equals(deviceType)) {
/*  953 */             config = (RtuConfig)this.gson.fromJson(((DeviceTcConfig)deviceMap.get(deviceName)).getExtend(), RtuConfig.class);
/*      */           }
/*  955 */           RoomDeviceStatusDTO deviceStatusDTO = new RoomDeviceStatusDTO();
/*  956 */           deviceStatusDTO.setDeviceName(deviceName);
/*  957 */           deviceStatusDTO.setDeviceType(deviceType);
/*      */           
/*  959 */           deviceStatusDTO.setStatus(commStatus);
/*  960 */           if (commStatus.intValue() == 1) {
/*  961 */             if (!deviceType.equals("CCTV")) {
/*  962 */               deviceStatusDTO.setStatusContent("斷線");
/*      */             } else {
/*  964 */               deviceStatusDTO.setStatusContent("");
/*      */             } 
/*  966 */           } else if (commStatus.intValue() == 0) {
/*  967 */             if (config != null) {
/*  968 */               DeviceTcStatus deviceStatus = (DeviceTcStatus)statusMap.get(deviceName);
/*  969 */               if (SignalType.DIGITAL_IN.name().equals(config.getSignalType())) {
/*  970 */                 Integer value = null;
/*  971 */                 if (deviceStatus.getContextData() != null) {
/*  972 */                   value = (Integer)SerializationUtils.deserialize(deviceStatus.getContextData());
/*      */                 }
/*      */                 
/*  975 */                 if (deviceType.equals("機房門禁")) {
/*  976 */                   if (value.intValue() == 1) {
/*  977 */                     deviceStatusDTO.setStatusContent("正常");
/*  978 */                   } else if (value.intValue() == 0) {
/*  979 */                     deviceStatusDTO.setStatusContent("開啟");
/*      */                   } 
/*  981 */                 } else if (deviceType.equals("空調")) {
/*  982 */                   if (value.intValue() == 0) {
/*  983 */                     if (deviceConfig.getDisplayName().contains("主要警報")) {
/*  984 */                       deviceStatusDTO.setStatusContent("");
/*      */                     } else {
/*  986 */                       deviceStatusDTO.setStatusContent("待機");
/*      */                     } 
/*  988 */                   } else if (value.intValue() == 1) {
/*  989 */                     if (deviceConfig.getDisplayName().contains("主要警報")) {
/*  990 */                       deviceStatusDTO.setStatusContent("告警");
/*  991 */                       deviceStatusDTO.setStatus(Integer.valueOf(1));
/*      */                     } else {
/*  993 */                       deviceStatusDTO.setStatusContent("運轉");
/*      */                     } 
/*      */                   } 
/*  996 */                 } else if (deviceType.equals("主電源開關")) {
/*  997 */                   if (value.intValue() == 0) {
/*  998 */                     deviceStatusDTO.setStatusContent("正常");
/*  999 */                   } else if (value.intValue() == 1) {
/* 1000 */                     deviceStatusDTO.setStatusContent("異常-無市電");
/* 1001 */                     deviceStatusDTO.setStatus(Integer.valueOf(1));
/*      */                   } 
/* 1003 */                 } else if (deviceType.equals("發電機")) {
/* 1004 */                   if (value.intValue() == 0) {
/* 1005 */                     deviceStatusDTO.setStatusContent("正常");
/* 1006 */                   } else if (value.intValue() == 1) {
/* 1007 */                     deviceStatusDTO.setStatusContent("異常-啟動中");
/* 1008 */                     deviceStatusDTO.setStatus(Integer.valueOf(1));
/*      */                   } 
/* 1010 */                 } else if (deviceType.equals("AC UPS市電")) {
/* 1011 */                   if (value.intValue() == 0) {
/* 1012 */                     deviceStatusDTO.setStatusContent("正常");
/* 1013 */                   } else if (value.intValue() == 1) {
/* 1014 */                     deviceStatusDTO.setStatusContent("異常");
/* 1015 */                     deviceStatusDTO.setStatus(Integer.valueOf(1));
/*      */                   } 
/* 1017 */                 } else if (deviceType.equals("AC UPS狀態")) {
/* 1018 */                   if (value.intValue() == 0) {
/* 1019 */                     deviceStatusDTO.setStatusContent("正常");
/* 1020 */                   } else if (value.intValue() == 1) {
/* 1021 */                     deviceStatusDTO.setStatusContent("異常-啟動中");
/* 1022 */                     deviceStatusDTO.setStatus(Integer.valueOf(1));
/*      */                   } 
/* 1024 */                 } else if (deviceType.equals("AC UPS電池供電")) {
/* 1025 */                   if (value.intValue() == 0) {
/* 1026 */                     deviceStatusDTO.setStatusContent("正常");
/* 1027 */                   } else if (value.intValue() == 1) {
/* 1028 */                     deviceStatusDTO.setStatusContent("異常");
/* 1029 */                     deviceStatusDTO.setStatus(Integer.valueOf(1));
/*      */                   } 
/* 1031 */                 } else if (deviceType.equals("火警受信總機")) {
/* 1032 */                   if (value.intValue() == 0) {
/* 1033 */                     deviceStatusDTO.setStatusContent("正常");
/* 1034 */                   } else if (value.intValue() == 1) {
/* 1035 */                     deviceStatusDTO.setStatusContent("告警");
/* 1036 */                     deviceStatusDTO.setStatus(Integer.valueOf(1));
/*      */                   }
/*      */                 
/* 1039 */                 } else if (value.intValue() == 1) {
/* 1040 */                   deviceStatusDTO.setStatusContent("異常");
/* 1041 */                   deviceStatusDTO.setStatus(Integer.valueOf(1));
/* 1042 */                 } else if (value.intValue() == 0) {
/* 1043 */                   deviceStatusDTO.setStatusContent("正常");
/*      */                 } else {
/* 1045 */                   deviceStatusDTO.setStatusContent("");
/*      */                 }
/*      */               
/* 1048 */               } else if (SignalType.ANALOG_IN.name().equals(config.getSignalType())) {
/* 1049 */                 if (deviceStatus.getContextData() != null) {
/* 1050 */                   if ((deviceType.equals("濕度") || deviceType
/* 1051 */                     .equals("溫度") || deviceType
/* 1052 */                     .equals("DC電壓") || deviceType
/* 1053 */                     .equals("油槽容量") || deviceType
/* 1054 */                     .equals("電流") || deviceType
/* 1055 */                     .equals("FM發射機") || deviceType
/* 1056 */                     .equals("發射機輸出功率")) && (
/* 1057 */                     (Double)SerializationUtils.deserialize(deviceStatus.getContextData())).doubleValue() != -1.0D) {
/*      */ 
/*      */                     
/* 1060 */                     String displayValue = String.valueOf((
/*      */                         
/* 1062 */                         (Double)SerializationUtils.deserialize(deviceStatus.getContextData())).doubleValue());
/* 1063 */                     String updateValue = displayValue;
/* 1064 */                     if (displayValue.length() == 3) {
/* 1065 */                       updateValue = displayValue.substring(0, 3);
/* 1066 */                     } else if (displayValue.length() == 4) {
/* 1067 */                       updateValue = displayValue.substring(0, 4);
/* 1068 */                     } else if (displayValue.length() > 4) {
/* 1069 */                       updateValue = displayValue.substring(0, 5);
/*      */                     } 
/* 1071 */                     deviceStatusDTO.setStatusContent(updateValue + config.getUnit());
/* 1072 */                     if (deviceConfig.getExtend() != null) {
/*      */                       
/* 1074 */                       RtuConfig rtuConfig = (RtuConfig)this.gson.fromJson(deviceConfig.getExtend(), RtuConfig.class);
/* 1075 */                       if ((deviceType.equals("濕度") || deviceType
/* 1076 */                         .equals("溫度") || deviceType
/* 1077 */                         .equals("油槽容量")) && (
/* 1078 */                         Double.valueOf(updateValue).doubleValue() > rtuConfig.getUpperLimit().intValue() || 
/* 1079 */                         Double.valueOf(updateValue).doubleValue() < rtuConfig.getLowerLimit().intValue())) {
/* 1080 */                         deviceStatusDTO.setStatus(Integer.valueOf(1));
/*      */                       }
/*      */                     } 
/*      */                   } else {
/*      */                     
/* 1085 */                     deviceStatusDTO.setStatusContent(
/* 1086 */                         String.valueOf(
/* 1087 */                           (Integer)SerializationUtils.deserialize(deviceStatus.getContextData()) + config
/* 1088 */                           .getUnit()));
/*      */                   } 
/*      */                 } else {
/* 1091 */                   deviceStatusDTO.setStatusContent("");
/*      */                 } 
/* 1093 */               } else if (SignalType.DIGITAL_OUT.name().equals(config.getSignalType())) {
/* 1094 */                 if (deviceStatus.getContextData() != null) {
/*      */                   
/* 1096 */                   Integer value = (Integer)SerializationUtils.deserialize(deviceStatus.getContextData());
/* 1097 */                   deviceStatusDTO.setStatusContent((value.intValue() == 1) ? "開啟" : "關閉");
/*      */                 } else {
/* 1099 */                   deviceStatusDTO.setStatusContent("");
/*      */                 } 
/*      */               } 
/*      */             } else {
/* 1103 */               deviceStatusDTO.setStatusContent("");
/*      */             } 
/*      */           } 
/* 1106 */           result.add(deviceStatusDTO);
/*      */         } 
/*      */       }
/* 1109 */       return result;
/* 1110 */     } catch (Exception e) {
/* 1111 */       logger.error("Refresh RoomDeviceStatus failed.", e);
/* 1112 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomGoupDeviceStatus/{svgName}/{groupName:.+}"}, method = {RequestMethod.GET})
/*      */   public List<RoomGroupDeviceStatusDTO> getRoomGroupDeviceStatus(@PathVariable("svgName") String svgName, @PathVariable("groupName") String groupName) {
/*      */     try {
/* 1124 */       logger.info("getRoomGroupDeviceStatus, svgName:'{}', groupName:'{}'", svgName, groupName);
/* 1125 */       List<RoomGroupDeviceStatusDTO> result = new ArrayList<>();
/*      */       
/* 1127 */       IMap<String, DeviceSvgPositionConfig> positionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DevicePositionConfig);
/* 1128 */       IMap<String, DeviceTcStatus> statusMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcStatus);
/* 1129 */       IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 1130 */       EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*      */       
/* 1132 */       PredicateBuilder pb = eo.get("svgName").equal(svgName).and((Predicate)eo.get("deviceGroupName").equal(groupName));
/* 1133 */       List<String> deviceNameList = new ArrayList<>();
/*      */       
/* 1135 */       for (DeviceSvgPositionConfig positionConfig : positionMap.values((Predicate)pb)) {
/* 1136 */         deviceNameList.add(positionConfig.getDeviceName());
/*      */       }
/*      */       
/* 1139 */       for (String deviceName : deviceNameList) {
/* 1140 */         DeviceTcConfig deviceConfig = (DeviceTcConfig)deviceMap.get(deviceName);
/* 1141 */         String deviceType = deviceConfig.getDeviceType();
/* 1142 */         DeviceTcStatus deviceStatus = (DeviceTcStatus)statusMap.get(deviceName);
/*      */         
/* 1144 */         Integer commStatus = Integer.valueOf((deviceStatus != null && ((DeviceTcStatus)statusMap.get(deviceName)).getCommStatus() != null) ? ((DeviceTcStatus)statusMap
/* 1145 */             .get(deviceName)).getCommStatus().intValue() : 1);
/*      */ 
/*      */         
/* 1148 */         RtuConfig config = null;
/* 1149 */         if (!"cardReader".equals(deviceType)) {
/* 1150 */           config = (RtuConfig)this.gson.fromJson(((DeviceTcConfig)deviceMap.get(deviceName)).getExtend(), RtuConfig.class);
/*      */         }
/* 1152 */         RoomGroupDeviceStatusDTO dto = new RoomGroupDeviceStatusDTO();
/* 1153 */         dto.setDeviceName(deviceName);
/* 1154 */         dto.setDeviceType(deviceType);
/* 1155 */         dto.setDisplayName(deviceConfig.getDisplayName());
/*      */         
/* 1157 */         dto.setStatus(commStatus);
/* 1158 */         if (commStatus.intValue() == 1) {
/* 1159 */           if (!deviceType.equals("CCTV")) {
/* 1160 */             dto.setStatusContent("斷線");
/*      */           } else {
/* 1162 */             dto.setStatusContent("");
/*      */           } 
/* 1164 */         } else if (commStatus.intValue() == 0) {
/* 1165 */           if (config != null) {
/* 1166 */             byte[] contextData = deviceStatus.getContextData();
/* 1167 */             if (SignalType.DIGITAL_IN.name().equals(config.getSignalType())) {
/* 1168 */               Integer value = null;
/* 1169 */               if (contextData != null) {
/* 1170 */                 value = (Integer)SerializationUtils.deserialize(deviceStatus.getContextData());
/*      */               }
/* 1172 */               if (value != null && value.intValue() == 1) {
/* 1173 */                 if (deviceType.equals("空調")) {
/* 1174 */                   if (deviceConfig.getDisplayName().contains("主要警報")) {
/* 1175 */                     dto.setStatusContent("告警");
/* 1176 */                     dto.setStatus(Integer.valueOf(1));
/*      */                   } else {
/* 1178 */                     dto.setStatusContent("運轉");
/*      */                   } 
/* 1180 */                 } else if (deviceType.equals("充電機狀態")) {
/* 1181 */                   dto.setStatus(Integer.valueOf(1));
/* 1182 */                   dto.setStatusContent("告警");
/*      */                 } else {
/* 1184 */                   dto.setStatusContent("啟動");
/*      */                 }
/*      */               
/* 1187 */               } else if (deviceType.equals("空調")) {
/* 1188 */                 if (deviceConfig.getDisplayName().contains("主要警報")) {
/* 1189 */                   dto.setStatusContent("");
/*      */                 } else {
/* 1191 */                   dto.setStatusContent("待機");
/*      */                 } 
/*      */               } else {
/* 1194 */                 dto.setStatusContent("");
/*      */               }
/*      */             
/* 1197 */             } else if (SignalType.ANALOG_IN.name().equals(config.getSignalType())) {
/* 1198 */               if (contextData != null) {
/* 1199 */                 if ((deviceType.equals("濕度") || deviceType
/* 1200 */                   .equals("溫度") || deviceType
/* 1201 */                   .equals("DC電壓") || deviceType
/* 1202 */                   .equals("油槽容量") || deviceType
/* 1203 */                   .equals("電流") || deviceType
/* 1204 */                   .equals("FM發射機") || deviceType
/* 1205 */                   .equals("發射機輸出功率")) && (
/*      */                   
/* 1207 */                   (Double)SerializationUtils.deserialize(((DeviceTcStatus)statusMap
/* 1208 */                     .get(deviceName)).getContextData())).doubleValue() != -1.0D) {
/*      */ 
/*      */                   
/* 1211 */                   String displayValue = String.valueOf((
/*      */                       
/* 1213 */                       (Double)SerializationUtils.deserialize(((DeviceTcStatus)statusMap
/* 1214 */                         .get(deviceName)).getContextData())).doubleValue());
/* 1215 */                   String updateValue = displayValue;
/* 1216 */                   if (displayValue.length() == 3) {
/* 1217 */                     updateValue = displayValue.substring(0, 3);
/* 1218 */                   } else if (displayValue.length() == 4) {
/* 1219 */                     updateValue = displayValue.substring(0, 4);
/* 1220 */                   } else if (displayValue.length() > 4) {
/* 1221 */                     updateValue = displayValue.substring(0, 5);
/*      */                   } 
/* 1223 */                   dto.setStatusContent(updateValue + config.getUnit());
/*      */                 } else {
/* 1225 */                   dto.setStatusContent(
/* 1226 */                       String.valueOf(
/*      */                         
/* 1228 */                         (Integer)SerializationUtils.deserialize(((DeviceTcStatus)statusMap
/* 1229 */                           .get(deviceName)).getContextData()) + config
/* 1230 */                         .getUnit()));
/*      */                 } 
/*      */               } else {
/* 1233 */                 dto.setStatusContent("");
/*      */               } 
/* 1235 */             } else if (SignalType.DIGITAL_OUT.name().equals(config.getSignalType())) {
/* 1236 */               if (contextData != null) {
/*      */ 
/*      */                 
/* 1239 */                 Integer value = (Integer)SerializationUtils.deserialize(((DeviceTcStatus)statusMap.get(deviceName)).getContextData());
/* 1240 */                 dto.setStatusContent((value.intValue() == 1) ? "開啟" : "關閉");
/*      */               } else {
/* 1242 */                 dto.setStatusContent("");
/*      */               } 
/*      */             } 
/*      */           } else {
/* 1246 */             dto.setStatusContent("");
/*      */           } 
/*      */         } 
/* 1249 */         result.add(dto);
/*      */       } 
/*      */       
/* 1252 */       return result;
/* 1253 */     } catch (Exception e) {
/* 1254 */       logger.error("getRoomGroupDeviceStatus failed.", e);
/* 1255 */       return Collections.emptyList();
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
/* 1267 */     boolean result = true;
/*      */     try {
/* 1269 */       logger.info("Save RoomDevicePositionConfig, size='{}'", Integer.valueOf(list.size()));
/*      */       
/* 1271 */       IMap<String, DeviceSvgPositionConfig> configMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DevicePositionConfig);
/* 1272 */       Map<String, DeviceSvgPositionConfig> saveConfigMap = new HashMap<>();
/* 1273 */       for (DeviceSvgPositionConfigDTO dto : list) {
/* 1274 */         String groupName = dto.getDeviceGroupName();
/* 1275 */         if (groupName != null && !dto.getDeviceGroupName().isEmpty()) {
/* 1276 */           EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*      */ 
/*      */ 
/*      */           
/* 1280 */           PredicateBuilder pb = eo.get("svgName").equal(dto.getSvgName()).and((Predicate)eo.get("deviceGroupName").equal(groupName));
/* 1281 */           for (DeviceSvgPositionConfig config : configMap.values((Predicate)pb)) {
/* 1282 */             config.setPositionX(dto.getPositionX());
/* 1283 */             config.setPositionY(dto.getPositionY());
/* 1284 */             saveConfigMap.put(config.getId(), config);
/*      */           }  continue;
/*      */         } 
/* 1287 */         if (groupName == null) {
/* 1288 */           DeviceSvgPositionConfig config = (DeviceSvgPositionConfig)this.modelMapper.map(dto, DeviceSvgPositionConfig.class);
/* 1289 */           if (config.getId() == null) {
/* 1290 */             config.setId(UuidUtils.generateUUIDStr());
/*      */           }
/* 1292 */           saveConfigMap.put(config.getId(), config);
/*      */         } 
/*      */       } 
/*      */       
/* 1296 */       configMap.putAll(saveConfigMap);
/* 1297 */     } catch (Exception e) {
/* 1298 */       logger.error("Save RoomDevicePositionConfig failed, size='{}'", Integer.valueOf(list.size()), e);
/* 1299 */       result = false;
/*      */     } 
/* 1301 */     return Boolean.valueOf(result);
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomInfo"}, method = {RequestMethod.GET})
/*      */   public List<RoomInfoDTO> getRoomInfo() {
/*      */     try {
/* 1308 */       logger.debug("Get Room Info.");
/* 1309 */       List<RoomInfoDTO> result = new ArrayList<>();
/*      */       
/* 1311 */       IMap<String, RoomBackgroundSvgConfig> svgConfigMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.BackgroundSvgConfig);
/* 1312 */       for (RoomBackgroundSvgConfig config : svgConfigMap.values()) {
/* 1313 */         RoomInfoDTO dto = new RoomInfoDTO();
/* 1314 */         dto.setId(config.getNameId());
/* 1315 */         dto.setName(config.getDescription());
/* 1316 */         dto.setLineName(TransferHelper.getLineName(config.getRoomLineId()));
/* 1317 */         dto.setMileage(config.getRoomMileage());
/* 1318 */         dto.setStatus(-1);
/*      */         
/* 1320 */         if (config.getRoomPhone().equals("1")) {
/* 1321 */           dto.setLifefacePanel(1);
/*      */         } else {
/* 1323 */           dto.setLifefacePanel(0);
/*      */         } 
/* 1325 */         result.add(dto);
/*      */       } 
/*      */       
/* 1328 */       RoomInfoDTO lifeFaceDTO = new RoomInfoDTO();
/* 1329 */       lifeFaceDTO.setId("lifeServer");
/* 1330 */       lifeFaceDTO.setName("人臉辨識伺服器");
/* 1331 */       lifeFaceDTO.setLineName("國道1號");
/* 1332 */       lifeFaceDTO.setMileage(Integer.valueOf(999999));
/* 1333 */       lifeFaceDTO.setStatus(-1);
/* 1334 */       lifeFaceDTO.setLifefacePanel(0);
/* 1335 */       result.add(lifeFaceDTO);
/*      */       
/* 1337 */       Comparator<RoomInfoDTO> byMileage = Comparator.comparing(RoomInfoDTO::getMileage);
/*      */       
/* 1339 */       Comparator<RoomInfoDTO> byname = Comparator.comparing(RoomInfoDTO::getName);
/*      */       
/* 1341 */       result.sort(byMileage.thenComparing(byname));
/* 1342 */       return result;
/* 1343 */     } catch (Exception e) {
/* 1344 */       logger.error("Get Room Info faild.");
/* 1345 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomStatus"}, method = {RequestMethod.GET})
/*      */   public List<RoomInfoDTO> getRoomStatus() {
/*      */     try {
/* 1353 */       List<RoomInfoDTO> result = new ArrayList<>();
/*      */       
/* 1355 */       IMap<String, RoomBackgroundSvgConfig> svgConfigMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.BackgroundSvgConfig);
/* 1356 */       IMap<String, DeviceTcStatus> statusMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcStatus);
/*      */       
/* 1358 */       IMap<String, DeviceSvgPositionConfig> positionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DevicePositionConfig);
/*      */       
/* 1360 */       IMap<String, LifeFaceLockCardData> lifeFaceLockCardMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.LifeFaceLockCardData);
/*      */       
/* 1362 */       IMap<String, DeviceLocationMappingConfig> roomLocationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/* 1363 */       IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 1364 */       Set<String> roomIds = new HashSet<>(svgConfigMap.keySet());
/*      */       
/* 1366 */       ZonedDateTime now = ZonedDateTime.now();
/* 1367 */       ZonedDateTime todayZero = now.truncatedTo(ChronoUnit.DAYS);
/*      */       
/* 1369 */       Date from = Date.from(todayZero.toInstant());
/*      */       
/* 1371 */       Set<String> ncuIds = new HashSet<>();
/* 1372 */       for (LifeFaceLockCardData data : lifeFaceLockCardMap.values()) {
/*      */         
/* 1374 */         if (data.getDataTime().after(from)) {
/* 1375 */           String spiltId = data.getId();
/* 1376 */           String[] spiltData = spiltId.split("\\+");
/* 1377 */           String id = spiltData[1];
/* 1378 */           ncuIds.add(id);
/*      */         } 
/*      */       } 
/*      */       
/* 1382 */       for (String roomId : roomIds) {
/*      */         
/* 1384 */         PredicateBuilder mapping = (new PredicateBuilder()).getEntryObject().get("svgName").equal(roomId);
/* 1385 */         int Offline = 0;
/* 1386 */         int online = 0;
/* 1387 */         for (DeviceSvgPositionConfig config : positionMap.values((Predicate)mapping)) {
/* 1388 */           String deviceName = config.getDeviceName();
/* 1389 */           DeviceTcStatus status = (DeviceTcStatus)statusMap.get(deviceName);
/* 1390 */           if (status != null) {
/* 1391 */             if (status.getCommStatus().intValue() == 1) {
/* 1392 */               Offline = 1; break;
/*      */             } 
/* 1394 */             if (status.getCommStatus().intValue() == 0) {
/* 1395 */               if (status.getDeviceType().equals("充電機狀態") || status
/* 1396 */                 .getDeviceType().equals("主電源開關") || status
/* 1397 */                 .getDeviceType().equals("發電機") || status
/* 1398 */                 .getDeviceType().equals("AC UPS市電") || status
/* 1399 */                 .getDeviceType().equals("AC UPS狀態") || status
/* 1400 */                 .getDeviceType().equals("AC UPS電池供電") || status
/* 1401 */                 .getDeviceType().equals("火警受信總機")) {
/* 1402 */                 Integer value = (Integer)SerializationUtils.deserialize(status.getContextData());
/* 1403 */                 if (value.intValue() == 1) {
/* 1404 */                   Offline = 1;
/*      */                   break;
/*      */                 } 
/*      */               } 
/* 1408 */               if ((status.getDeviceType().equals("濕度") || status
/* 1409 */                 .getDeviceType().equals("溫度") || status
/* 1410 */                 .getDeviceType().equals("DC電壓") || status
/* 1411 */                 .getDeviceType().equals("油槽容量") || status
/* 1412 */                 .getDeviceType().equals("電流") || status
/* 1413 */                 .getDeviceType().equals("FM發射機") || status
/* 1414 */                 .getDeviceType().equals("發射機輸出功率")) && (
/* 1415 */                 (Double)SerializationUtils.deserialize(status.getContextData())).doubleValue() != -1.0D) {
/*      */                 
/* 1417 */                 String displayValue = String.valueOf((
/* 1418 */                     (Double)SerializationUtils.deserialize(status.getContextData())).doubleValue());
/* 1419 */                 String updateValue = displayValue;
/* 1420 */                 if (displayValue.length() == 3) {
/* 1421 */                   updateValue = displayValue.substring(0, 3);
/* 1422 */                 } else if (displayValue.length() == 4) {
/* 1423 */                   updateValue = displayValue.substring(0, 4);
/* 1424 */                 } else if (displayValue.length() > 4) {
/* 1425 */                   updateValue = displayValue.substring(0, 5);
/*      */                 } 
/* 1427 */                 if (((DeviceTcConfig)deviceMap.get(status.getId())).getExtend() != null) {
/*      */                   
/* 1429 */                   RtuConfig rtuConfig = (RtuConfig)this.gson.fromJson(((DeviceTcConfig)deviceMap.get(status.getId())).getExtend(), RtuConfig.class);
/* 1430 */                   if ((status.getDeviceType().equals("濕度") || status
/* 1431 */                     .getDeviceType().equals("溫度") || status
/* 1432 */                     .getDeviceType().equals("DC電壓") || status
/* 1433 */                     .getDeviceType().equals("油槽容量") || status
/* 1434 */                     .getDeviceType().equals("電流") || status
/* 1435 */                     .getDeviceType().equals("FM發射機") || status
/* 1436 */                     .getDeviceType().equals("發射機輸出功率")) && (
/* 1437 */                     Double.valueOf(updateValue).doubleValue() > rtuConfig.getUpperLimit().intValue() || 
/* 1438 */                     Double.valueOf(updateValue).doubleValue() < rtuConfig.getLowerLimit().intValue())) {
/* 1439 */                     Offline = 1;
/*      */                     
/*      */                     break;
/*      */                   } 
/*      */                 } 
/*      */               } 
/* 1445 */               online = 2;
/*      */             } 
/*      */           } 
/*      */         } 
/* 1449 */         RoomInfoDTO roomInfoDTO = new RoomInfoDTO();
/* 1450 */         roomInfoDTO.setId(roomId);
/* 1451 */         if (Offline == 0 && online == 2) {
/* 1452 */           roomInfoDTO.setStatus(0);
/* 1453 */         } else if (Offline > 0) {
/* 1454 */           roomInfoDTO.setStatus(1);
/* 1455 */         } else if (Offline == 0 && online == 0) {
/* 1456 */           roomInfoDTO.setStatus(-1);
/*      */         } 
/* 1458 */         if (((RoomBackgroundSvgConfig)svgConfigMap.get(roomId)).getRoomPhone().equals("1")) {
/* 1459 */           roomInfoDTO.setLifefacePanel(1);
/*      */         } else {
/* 1461 */           roomInfoDTO.setLifefacePanel(0);
/*      */         } 
/*      */         
/* 1464 */         if (roomInfoDTO.getLifefacePanel() == 1) {
/* 1465 */           roomInfoDTO.setLifefaceStatus(0);
/* 1466 */           if (ncuIds.size() > 0) {
/* 1467 */             for (String ncuId : ncuIds) {
/* 1468 */               EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*      */               
/* 1470 */               PredicateBuilder pb = eo.get("svgName").equal(roomId).and((Predicate)eo.get("deviceName").equal(ncuId));
/*      */               
/* 1472 */               Iterator<DeviceLocationMappingConfig> configValue = roomLocationMap.values((Predicate)pb).iterator();
/* 1473 */               if (configValue.hasNext()) {
/* 1474 */                 roomInfoDTO.setLifefaceStatus(1);
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           }
/*      */         } 
/* 1480 */         result.add(roomInfoDTO);
/*      */       } 
/*      */       
/* 1483 */       RoomInfoDTO dto = new RoomInfoDTO();
/* 1484 */       dto.setId("lifeServer");
/*      */       
/* 1486 */       if (statusMap.containsKey("lifeServer")) {
/* 1487 */         DeviceTcStatus status = (DeviceTcStatus)statusMap.get("lifeServer");
/* 1488 */         dto.setStatus(status.getCommStatus().intValue());
/*      */       } else {
/* 1490 */         dto.setStatus(1);
/*      */       } 
/* 1492 */       dto.setLifefacePanel(0);
/* 1493 */       result.add(dto);
/* 1494 */       return result;
/* 1495 */     } catch (Exception e) {
/* 1496 */       logger.error("Get Room Status faild.", e);
/* 1497 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/safeStatus"}, method = {RequestMethod.GET})
/*      */   public List<RoomInfoDTO> getSafeStatus() {
/*      */     try {
/* 1505 */       List<RoomInfoDTO> result = new ArrayList<>();
/* 1506 */       IMap<String, NcuVoiceDTO> voiceAutoCloseMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.NcuVoiceAutoCloseData);
/* 1507 */       IMap<String, DeviceTcConfig> deviceTcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*      */       
/* 1509 */       IMap<String, DeviceLocationMappingConfig> roomLocationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/* 1510 */       IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/* 1511 */       PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceType").equal("NCU");
/* 1512 */       for (DeviceTcConfig config : deviceTcConfigMap.values((Predicate)pb)) {
/* 1513 */         RoomInfoDTO dto = new RoomInfoDTO();
/* 1514 */         String deviceName = config.getDeviceName();
/*      */         
/* 1516 */         PredicateBuilder pb2 = (new PredicateBuilder()).getEntryObject().get("deviceName").equal(deviceName);
/* 1517 */         if (roomLocationMap.values((Predicate)pb2) != null && roomLocationMap.values((Predicate)pb2).size() > 0) {
/* 1518 */           DeviceLocationMappingConfig mappingConfig = roomLocationMap.values((Predicate)pb2).iterator().next();
/* 1519 */           dto.setId(mappingConfig.getSvgName());
/* 1520 */           String dataName = mappingConfig.getDeviceName() + "-DO-1";
/* 1521 */           String alarmName = mappingConfig.getDeviceName() + "-DO-2";
/* 1522 */           if (dataMap.containsKey(dataName)) {
/* 1523 */             if (((Integer)dataMap.get(dataName)).intValue() == 0) {
/* 1524 */               dto.setSafeStatus(0);
/* 1525 */             } else if (((Integer)dataMap.get(dataName)).intValue() == 1) {
/* 1526 */               dto.setSafeStatus(1);
/*      */             } else {
/* 1528 */               dto.setSafeStatus(-1);
/*      */             } 
/*      */           } else {
/* 1531 */             dto.setSafeStatus(-1);
/*      */           } 
/* 1533 */           if (dataMap.containsKey(alarmName)) {
/* 1534 */             if (((Integer)dataMap.get(alarmName)).intValue() == 0) {
/* 1535 */               dto.setAlarmStatus(0);
/* 1536 */             } else if (((Integer)dataMap.get(alarmName)).intValue() == 1) {
/*      */               
/* 1538 */               if (!config.getMemo().equals("0") && 
/* 1539 */                 voiceAutoCloseMap.get(alarmName) == null) {
/*      */                 
/* 1541 */                 Date date = new Date();
/* 1542 */                 long time = Long.parseLong(config.getMemo());
/* 1543 */                 NcuVoiceDTO voiceDTO = new NcuVoiceDTO();
/* 1544 */                 voiceDTO.setId(alarmName);
/* 1545 */                 voiceDTO.setDateTime(new Date(date.getTime() + time * 1000L));
/* 1546 */                 voiceAutoCloseMap.put(alarmName, voiceDTO);
/*      */               } 
/*      */               
/* 1549 */               dto.setAlarmStatus(1);
/* 1550 */               dto.setSafeStatus(2);
/*      */             } 
/*      */           } else {
/* 1553 */             dto.setAlarmStatus(0);
/*      */           } 
/*      */         } else {
/* 1556 */           dto.setSafeStatus(-1);
/* 1557 */           dto.setAlarmStatus(0);
/*      */         } 
/* 1559 */         result.add(dto);
/*      */       } 
/* 1561 */       return result;
/* 1562 */     } catch (Exception e) {
/* 1563 */       logger.error("Get Safe Status faild.", e);
/* 1564 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomSafeInfo/{backgroundId}"}, method = {RequestMethod.GET})
/*      */   public RoomNCUStatusDTO getRoomSafeInfo(@PathVariable("backgroundId") String backgroundId) {
/*      */     try {
/* 1572 */       RoomNCUStatusDTO result = new RoomNCUStatusDTO();
/* 1573 */       IMap<String, DeviceTcConfig> deviceTcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*      */       
/* 1575 */       IMap<String, DeviceLocationMappingConfig> roomLocationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/* 1576 */       IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/* 1577 */       IMap<String, NcuCardTapData> tapMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.NcuCardTapData);
/* 1578 */       IMap<String, RoomCardConfig> roomCardConfigMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardConfig);
/* 1579 */       PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceType").equal("NCU");
/* 1580 */       for (DeviceTcConfig config : deviceTcConfigMap.values((Predicate)pb)) {
/* 1581 */         EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*      */ 
/*      */ 
/*      */         
/* 1585 */         PredicateBuilder mappingPb = eo.get("svgName").equal(backgroundId).and((Predicate)eo.get("deviceName").equal(config.getDeviceName()));
/* 1586 */         Collection<DeviceLocationMappingConfig> roommDevices = roomLocationMap.values((Predicate)mappingPb);
/* 1587 */         if (roommDevices != null && roommDevices.size() > 0) {
/* 1588 */           DeviceLocationMappingConfig mappingConfig = roommDevices.iterator().next();
/* 1589 */           String deviceName = mappingConfig.getDeviceName();
/* 1590 */           result.setId(deviceName);
/* 1591 */           result.setRoomName(mappingConfig.getDescription());
/* 1592 */           String safeName = deviceName + "-DO-1";
/* 1593 */           if (dataMap.containsKey(safeName)) {
/* 1594 */             if (((Integer)dataMap.get(safeName)).intValue() == 0) {
/* 1595 */               result.setPreservationStatus(0);
/* 1596 */             } else if (((Integer)dataMap.get(safeName)).intValue() == 1) {
/* 1597 */               result.setPreservationStatus(1);
/*      */             } else {
/* 1599 */               result.setPreservationStatus(-1);
/*      */             } 
/*      */           } else {
/* 1602 */             result.setPreservationStatus(-1);
/*      */           } 
/* 1604 */           String alarmName = deviceName + "-DO-2";
/* 1605 */           if (dataMap.containsKey(alarmName)) {
/* 1606 */             if (((Integer)dataMap.get(alarmName)).intValue() == 0) {
/* 1607 */               result.setAlarmVideoStatus(0);
/* 1608 */             } else if (((Integer)dataMap.get(alarmName)).intValue() == 1) {
/* 1609 */               result.setAlarmVideoStatus(1);
/*      */             } else {
/* 1611 */               result.setAlarmVideoStatus(-1);
/*      */             } 
/*      */           } else {
/* 1614 */             result.setAlarmVideoStatus(-1);
/*      */           } 
/* 1616 */           if (result.getPreservationStatus() == 1) {
/*      */             
/* 1618 */             Set<RoomStaffPeopleDTO> staffPeopleList = new HashSet<>();
/* 1619 */             List<RoomStaffPeopleDTO> staffPeople = new ArrayList<>();
/* 1620 */             EntryObject tap = (new PredicateBuilder()).getEntryObject();
/*      */ 
/*      */ 
/*      */             
/* 1624 */             PredicateBuilder tapPb = tap.get("ncuId").equal(deviceName).and((Predicate)tap.get("deviceId").equal("34"));
/* 1625 */             tapMap
/* 1626 */               .values((Predicate)tapPb)
/* 1627 */               .forEach(tapData -> {
/*      */                   RoomStaffPeopleDTO dto = new RoomStaffPeopleDTO();
/*      */                   
/*      */                   dto.setId(tapData.getCardNumber());
/*      */                   
/*      */                   dto.setInTime(tapData.getDataTime());
/*      */                   
/*      */                   PredicateBuilder cardPb = (new PredicateBuilder()).getEntryObject().get("aba").equal(tapData.getCardNumber());
/*      */                   
/*      */                   if (roomCardConfigMap.values((Predicate)cardPb).iterator().hasNext()) {
/*      */                     RoomCardConfig roomConfig = roomCardConfigMap.values((Predicate)cardPb).iterator().next();
/*      */                     
/*      */                     dto.setName(roomConfig.getName());
/*      */                     
/*      */                     dto.setCellPhone(roomConfig.getTel());
/*      */                     
/*      */                     dto.setMemo(roomConfig.getCompany());
/*      */                     
/*      */                     staffPeople.add(dto);
/*      */                   } 
/*      */                 });
/*      */             
/* 1649 */             Comparator<RoomStaffPeopleDTO> inTime = Comparator.comparing(RoomStaffPeopleDTO::getInTime);
/* 1650 */             staffPeopleList.addAll(staffPeople);
/* 1651 */             staffPeopleList.stream().sorted(inTime.reversed());
/* 1652 */             result.setStaffpeople(staffPeopleList);
/*      */           } 
/*      */           
/* 1655 */           if (result.getAlarmVideoStatus() == 1) {
/* 1656 */             result.setPreservationStatus(2);
/*      */           }
/*      */         } 
/*      */       } 
/* 1660 */       return result;
/* 1661 */     } catch (Exception e) {
/* 1662 */       logger.error("Get Room SafeInfo failed.", e);
/* 1663 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomLine"}, method = {RequestMethod.GET})
/*      */   public List<String> getRoomLineData() {
/*      */     try {
/* 1671 */       logger.debug("Get Room Line Data");
/*      */       
/* 1673 */       IMap<String, RoomBackgroundSvgConfig> svgConfigMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.BackgroundSvgConfig);
/* 1674 */       Set<String> lineNames = new HashSet<>();
/* 1675 */       for (RoomBackgroundSvgConfig config : svgConfigMap.values()) {
/* 1676 */         String lineName = TransferHelper.getLineName(config.getRoomLineId());
/* 1677 */         if (lineNames.contains(lineName)) {
/*      */           continue;
/*      */         }
/* 1680 */         lineNames.add(lineName);
/*      */       } 
/*      */       
/* 1683 */       List<String> result = new ArrayList<>(lineNames);
/* 1684 */       result.sort(new Comparator<String>()
/*      */           {
/*      */             
/*      */             public int compare(String o1, String o2)
/*      */             {
/* 1689 */               return o1.compareTo(o2);
/*      */             }
/*      */           });
/* 1692 */       return result;
/* 1693 */     } catch (Exception e) {
/* 1694 */       logger.error("Get Room Line Data failed.");
/* 1695 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/powerWater"}, method = {RequestMethod.GET})
/*      */   public PowerWaterStatusDTO getPowerWaterStatus() {
/*      */     try {
/* 1703 */       IMap<String, WaterPowerBaseConfig> waterPowerBasicMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.WaterPowerBaseConfig);
/* 1704 */       IMap<String, WaterStatusData> waterStatusMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.WaterStatusData);
/* 1705 */       IMap<String, PowerStatusData> powerStatusMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.PowerStatusData);
/* 1706 */       PowerWaterStatusDTO result = new PowerWaterStatusDTO();
/* 1707 */       List<WaterStatusDTO> waterList = new ArrayList<>();
/* 1708 */       for (WaterStatusData waterData : waterStatusMap.values()) {
/* 1709 */         WaterStatusDTO waterDTO = new WaterStatusDTO();
/* 1710 */         waterDTO.setId(waterData.getId());
/* 1711 */         waterDTO.setDisplayName(((WaterPowerBaseConfig)waterPowerBasicMap.get(waterData.getId())).getDisplayName());
/* 1712 */         waterDTO.setDataTime(waterData.getDataTime());
/* 1713 */         waterDTO.setCumulateValue(waterData.getCumulateValue());
/* 1714 */         waterDTO.setInstantaneousValue(waterData.getInstantaneousValue());
/* 1715 */         waterDTO.setWaterAlarm(waterData.getWaterAlarm());
/* 1716 */         waterDTO.setWaterLastHour(waterData.getWaterLastHour());
/* 1717 */         waterDTO.setWater24Hour(waterData.getWater24Hour());
/* 1718 */         waterList.add(waterDTO);
/*      */       } 
/* 1720 */       List<PowerStatusDTO> powerList = new ArrayList<>();
/* 1721 */       for (PowerStatusData powerData : powerStatusMap.values()) {
/* 1722 */         PowerStatusDTO powerDTO = new PowerStatusDTO();
/* 1723 */         powerDTO.setId(powerData.getId());
/* 1724 */         powerDTO.setDisplayName(((WaterPowerBaseConfig)waterPowerBasicMap.get(powerData.getId())).getDisplayName());
/* 1725 */         powerDTO.setDataTime(powerData.getDataTime());
/* 1726 */         powerDTO.setPowerLastHour(powerData.getPowerLastHour());
/* 1727 */         powerDTO.setPower24Hour(powerData.getPower24Hour());
/* 1728 */         powerDTO.setPowerAlarm(powerData.getPowerAlarm());
/* 1729 */         powerDTO.setAi(powerData.getAi());
/* 1730 */         powerDTO.setAv(powerData.getAv());
/* 1731 */         powerDTO.setTi(powerData.getTi());
/* 1732 */         powerDTO.setTv(powerData.getTv());
/* 1733 */         powerDTO.setRi(powerData.getRi());
/* 1734 */         powerDTO.setRv(powerData.getRv());
/* 1735 */         powerDTO.setSi(powerData.getSi());
/* 1736 */         powerDTO.setSv(powerData.getSv());
/* 1737 */         powerDTO.setPf(powerData.getPf());
/* 1738 */         powerDTO.setKw(powerData.getKw());
/* 1739 */         powerDTO.setKwh(powerData.getKwh());
/* 1740 */         powerList.add(powerDTO);
/*      */       } 
/* 1742 */       result.setWaterStatusList(waterList);
/* 1743 */       result.setPowerStatusList(powerList);
/* 1744 */       return result;
/* 1745 */     } catch (RuntimeException ex) {
/* 1746 */       logger.error("Get Power Water Status Failed.", ex);
/* 1747 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomCctv/{backgroundId}"}, method = {RequestMethod.GET})
/*      */   public List<RoomCctvUrlDTO> getRoomCctvVideo(@PathVariable("backgroundId") String backgroundId) {
/*      */     try {
/* 1755 */       logger.debug("Get Room Cctv Video.");
/* 1756 */       List<RoomCctvUrlDTO> result = new ArrayList<>();
/* 1757 */       IMap<String, CctvConfig> cMap = HzUtils.getMap((HzDistObjEnum)CcsHzMap.CctvConfig);
/*      */       
/* 1759 */       IMap<String, DeviceLocationMappingConfig> roomLocationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/* 1760 */       IMap<String, DeviceTcConfig> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 1761 */       EntryObject eo = (new PredicateBuilder()).getEntryObject();
/* 1762 */       PredicateBuilder pb = eo.get("svgName").equal(backgroundId);
/* 1763 */       for (DeviceLocationMappingConfig c : roomLocationMap.values((Predicate)pb)) {
/* 1764 */         if (map.get(c.getDeviceName()) != null && ((DeviceTcConfig)map
/* 1765 */           .get(c.getDeviceName())).getDeviceType().equals("CCTV")) {
/* 1766 */           RoomCctvUrlDTO dto = new RoomCctvUrlDTO();
/* 1767 */           dto.setDeviceName(c.getDeviceName());
/* 1768 */           dto.setDisplayName(((DeviceTcConfig)map.get(c.getDeviceName())).getDisplayName());
/* 1769 */           if (cMap.get(c.getDeviceName()) != null) {
/* 1770 */             dto.setUrl(((CctvConfig)cMap.get(c.getDeviceName())).getLowUrl());
/*      */           } else {
/*      */             
/* 1773 */             String cctvurl = TransferHelper.returnCCTVurl(c.getDeviceName());
/* 1774 */             if (cctvurl != null && !cctvurl.equals("")) {
/* 1775 */               dto.setUrl(cctvurl);
/*      */             }
/*      */           } 
/* 1778 */           result.add(dto);
/*      */         } 
/*      */       } 
/* 1781 */       return result;
/* 1782 */     } catch (Exception e) {
/* 1783 */       logger.error("Get Room Cctv Video faild.");
/* 1784 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */   
/*      */   @RequestMapping(value = {"/roomDoOutput"}, method = {RequestMethod.PUT})
/*      */   public Boolean updateRoomDoOutput(@RequestBody RoomDoDTO dto, HttpServletRequest request) {
/*      */     try {
/* 1791 */       if (dto == null) {
/* 1792 */         logger.error("RoomDoDTO is null");
/* 1793 */         return Boolean.valueOf(false);
/*      */       } 
/* 1795 */       logger.debug("UpdateDoDevice, deviceName='{}', value='{}'", dto
/* 1796 */           .getDeviceName(), dto.getValue());
/* 1797 */       if (dto.getDeviceName() == null || dto.getValue() == null) {
/* 1798 */         logger.error("UpdateDoDevice deviceName or value is null.");
/* 1799 */         return Boolean.valueOf(false);
/*      */       } 
/*      */       
/* 1802 */       IMap<String, ModbusDeviceConfig> cfgMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusDeviceConfig);
/* 1803 */       IMap<String, DeviceConfig> devCfgMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.DeviceConfig);
/* 1804 */       IMap<Long, ModbusReadConfig> rMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusReadConfig);
/* 1805 */       IMap<Long, ModbusPinMapping> pMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusPinMapping);
/* 1806 */       IMap<String, NcuCardTapData> tapMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.NcuCardTapData);
/*      */ 
/*      */       
/* 1809 */       PredicateBuilder pinPb = (new PredicateBuilder()).getEntryObject().get("keyName").equal(dto.getDeviceName());
/* 1810 */       ModbusPinMapping pin = null;
/* 1811 */       Iterator<ModbusPinMapping> iterator = pMap.values((Predicate)pinPb).iterator(); if (iterator.hasNext()) { ModbusPinMapping p = iterator.next();
/* 1812 */         pin = p; }
/*      */ 
/*      */       
/* 1815 */       if (pin == null) {
/* 1816 */         logger.warn("Can not find ModbusPinMapping keyName='{}'", dto.getDeviceName());
/* 1817 */         return Boolean.valueOf(false);
/*      */       } 
/*      */       
/* 1820 */       ModbusDeviceConfig cfg = (ModbusDeviceConfig)cfgMap.get(pin.getDeviceName());
/* 1821 */       DeviceConfig devCfg = (DeviceConfig)devCfgMap.get(cfg.getDeviceName());
/* 1822 */       if (devCfg == null) {
/* 1823 */         logger.warn("{} : device config is null!", cfg.getDeviceName());
/* 1824 */         return Boolean.valueOf(false);
/*      */       } 
/* 1826 */       EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*      */ 
/*      */ 
/*      */       
/* 1830 */       PredicateBuilder pb = eo.get("pinGroupId").equal(cfg.getPinGroupId()).and((Predicate)eo.get("dataType").equal((Comparable)ModbusDataType.HOLDING_REGISTER_DO));
/*      */       
/* 1832 */       BigInteger data = getDoValue(cfg.getDeviceName(), pin.getAddress());
/* 1833 */       if (data == null) {
/* 1834 */         data = BigInteger.valueOf(0L);
/*      */       }
/* 1836 */       for (ModbusReadConfig readCfg : rMap.values((Predicate)pb)) {
/* 1837 */         if (readCfg.getStartAddress().intValue() + 40001 != pin.getAddress().intValue()) {
/* 1838 */           logger.debug("keyName:'{}', address:'{}'", pin.getKeyName(), pin.getAddress());
/*      */           continue;
/*      */         } 
/* 1841 */         ModbusMaster modbus = createModbusMaster(devCfg);
/* 1842 */         if (data.testBit(pin.getBitNumber().intValue())) {
/*      */           
/* 1844 */           if (!dto.getValue().booleanValue()) {
/* 1845 */             data = data.clearBit(pin.getBitNumber().intValue());
/*      */           
/*      */           }
/*      */         }
/* 1849 */         else if (dto.getValue().booleanValue()) {
/* 1850 */           data = data.setBit(0);
/*      */           
/* 1852 */           String[] deleteData = dto.getDeviceName().split("-");
/*      */           
/* 1854 */           PredicateBuilder tapPb = (new PredicateBuilder()).getEntryObject().get("ncuId").equal(deleteData[0]);
/* 1855 */           if (tapMap.values((Predicate)tapPb).size() > 0) {
/* 1856 */             for (NcuCardTapData tapData : tapMap.values((Predicate)tapPb)) {
/* 1857 */               tapMap.delete(tapData.getId());
/*      */             }
/*      */           }
/*      */         } 
/*      */         
/* 1862 */         logger.debug("writeSingleRegister, slaveId='{}', startAddress='{}', register='{}'", new Object[] { cfg
/*      */               
/* 1864 */               .getSlaveId(), readCfg
/* 1865 */               .getStartAddress(), data });
/*      */         
/* 1867 */         modbus.writeSingleRegister(cfg.getSlaveId().intValue(), readCfg.getStartAddress().intValue(), data.intValue());
/* 1868 */         if (modbus != null && modbus.isConnected()) {
/* 1869 */           modbus.disconnect();
/*      */         }
/*      */       } 
/*      */       
/* 1873 */       addLog(request, OperationItem.SET, dto
/*      */ 
/*      */           
/* 1876 */           .getDeviceName(), OperationResult.SUCCESS, (String)null, "room.updateDoDevice.success", new Object[] {
/*      */ 
/*      */ 
/*      */             
/* 1880 */             dto.getValue().booleanValue() ? "開啟" : "關閉" });
/* 1881 */       return Boolean.valueOf(true);
/* 1882 */     } catch (Exception e) {
/* 1883 */       logger.error("UpdateDoDevice failed.", e);
/* 1884 */       addLog(request, OperationItem.SET, dto
/*      */ 
/*      */           
/* 1887 */           .getDeviceName(), OperationResult.FAILURE, (String)null, "room.updateDoDevice.success", new Object[] {
/*      */ 
/*      */ 
/*      */             
/* 1891 */             dto.getValue().booleanValue() ? "開啟" : "關閉" });
/* 1892 */       return Boolean.valueOf(false);
/*      */     } 
/*      */   }
/*      */   
/*      */   private BigInteger getDoValue(String deviceName, Integer address) {
/* 1897 */     IMap<Long, ModbusPinMapping> pMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusPinMapping);
/* 1898 */     IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/* 1899 */     BigInteger data = BigInteger.valueOf(0L);
/* 1900 */     EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*      */     
/* 1902 */     PredicateBuilder pb = eo.get("deviceName").equal(deviceName).and((Predicate)eo.get("address").equal(address));
/* 1903 */     for (ModbusPinMapping pin : pMap.values((Predicate)pb)) {
/* 1904 */       Integer value = (Integer)dataMap.get(pin.getKeyName());
/* 1905 */       if (value != null) {
/* 1906 */         if (value.intValue() == 1) {
/* 1907 */           data = data.setBit(pin.getBitNumber().intValue()); continue;
/*      */         } 
/* 1909 */         data = data.clearBit(pin.getBitNumber().intValue());
/*      */       } 
/*      */     } 
/*      */     
/* 1913 */     return data;
/*      */   }
/*      */   
/*      */   private ModbusMaster createModbusMaster(DeviceConfig devCfg) throws UnknownHostException {
/* 1917 */     TcpParameters tcpParameters = new TcpParameters();
/* 1918 */     tcpParameters.setHost(InetAddress.getByName(devCfg.getIp()));
/* 1919 */     tcpParameters.setKeepAlive(true);
/* 1920 */     tcpParameters.setPort(devCfg.getPort().intValue());
/* 1921 */     ModbusMaster modbus = ModbusMasterFactory.createModbusMasterTCP(tcpParameters);
/* 1922 */     return modbus;
/*      */   }
/*      */   
/*      */   private String getDirectionId(Direction direction) {
/* 1926 */     if (direction == null) {
/* 1927 */       return "";
/*      */     }
/*      */     
/* 1930 */     switch (direction) {
/*      */       case N:
/* 1932 */         return "4";
/*      */       case S:
/* 1934 */         return "3";
/*      */       case W:
/* 1936 */         return "2";
/*      */       case E:
/* 1938 */         return "1";
/*      */     } 
/* 1940 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String getLocationName(String deviceName) {
/* 1946 */     IMap<String, DeviceLocationMappingConfig> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/*      */     
/* 1948 */     PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceName").equal(deviceName);
/* 1949 */     Iterator<DeviceLocationMappingConfig> iterator = map.values((Predicate)pb).iterator(); if (iterator.hasNext()) { DeviceLocationMappingConfig each = iterator.next();
/* 1950 */       return each.getLocationName(); }
/*      */     
/* 1952 */     return "";
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
/* 1963 */     String userId = TripleDESUtils.decrypt(request.getHeader("encryptedUserLogin"));
/* 1964 */     String ip = request.getRemoteHost();
/* 1965 */     Date now = new Date();
/* 1966 */     logger.debug("Get log time = ", now);
/* 1967 */     this.opLogger.addLog(userId, ip, SubSystem.ROOM, operationItem, deviceName, now, result, remark, descMsgId, args);
/*      */   }
/*      */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\restful\AoViewerRestServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */