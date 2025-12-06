/*      */ package com.hwacom.ngtms.ao.restful;
/*      */ 
/*      */ import com.hazelcast.core.IMap;
/*      */ import com.hazelcast.query.EntryObject;
/*      */ import com.hazelcast.query.Predicate;
/*      */ import com.hazelcast.query.PredicateBuilder;
/*      */ import com.hwacom.ngtms.ao.fm.remote.BulkRptRemote;
/*      */ import com.hwacom.ngtms.ao.fm.service.rpt.RptFmException;
/*      */ import com.hwacom.ngtms.ao.fm.service.rpt.RptService;
/*      */ import com.hwacom.ngtms.ao.shared.AlarmType;
/*      */ import com.hwacom.ngtms.ao.shared.dto.AlarmTypeDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.DeviceConfigBaseDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.FieldDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.OperatorDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.PdLocationDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.ReportConfigBaseDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.ReportLineGraphBtDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.ReportPreviewInfoDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.ReportQueryConditionDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.ReportQueryWidgetDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.ReportTreeBaseDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.ReportTreeDetailDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RptParametersDTO;
/*      */ import com.hwacom.ngtms.ao.util.AoRptValueFormatter;
/*      */ import com.hwacom.ngtms.ao.util.TransferHelper;
/*      */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*      */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*      */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*      */ import com.hwacom.ngtms.base.rmi.RmiUtils;
/*      */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*      */ import com.hwacom.ngtms.c.fm.model.DeviceHostLocation;
/*      */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*      */ import com.hwacom.ngtms.c.fm.model.DeviceTcHardwareStatus;
/*      */ import com.hwacom.ngtms.c.fm.model.DeviceType;
/*      */ import com.hwacom.ngtms.c.fm.model.RoadDivision;
/*      */ import com.hwacom.ngtms.c.fm.model.RoadLine;
/*      */ import com.hwacom.ngtms.c.fm.model.RoadSection;
/*      */ import com.hwacom.ngtms.c.fm.repository.DeviceTcHardwareStatusRepository;
/*      */ import com.hwacom.ngtms.c.fm.service.RptEnvVar;
/*      */ import com.hwacom.ngtms.c.restful.BaseRestful;
/*      */ import com.hwacom.ngtms.c.shared.AreaType;
/*      */ import com.hwacom.ngtms.c.shared.Direction;
/*      */ import com.hwacom.ngtms.c.shared.SubSystem;
/*      */ import com.hwacom.ngtms.c.shared.TcProtocolType;
/*      */ import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
/*      */ import com.hwacom.ngtms.c.shared.dto.DeviceTypeDTO;
/*      */ import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
/*      */ import com.hwacom.ngtms.c.shared.dto.RoadSectionDTO;
/*      */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*      */ import com.hwacom.ngtms.common.fm.model.User;
/*      */ import com.hwacom.ngtms.common.shared.PreviewData;
/*      */ import com.hwacom.ngtms.common.shared.PreviewGridData;
/*      */ import com.hwacom.ngtms.common.shared.PreviewHeader;
/*      */ import com.hwacom.ngtms.common.shared.ReportFormat;
/*      */ import com.hwacom.ngtms.common.shared.ReportPreviewInfo;
/*      */ import com.hwacom.ngtms.common.shared.ReportRepositoryTreeNode;
/*      */ import com.hwacom.ngtms.common.shared.dto.PreviewDataDTO;
/*      */ import com.hwacom.ngtms.common.shared.dto.PreviewGridDataDTO;
/*      */ import com.hwacom.ngtms.common.shared.dto.PreviewHeaderDTO;
/*      */ import java.security.Principal;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.stream.Collectors;
/*      */ import javax.annotation.PostConstruct;
/*      */ import org.modelmapper.ModelMapper;
/*      */ import org.slf4j.Logger;
/*      */ import org.slf4j.LoggerFactory;
/*      */ import org.springframework.beans.factory.annotation.Autowired;
/*      */ import org.springframework.security.authentication.AbstractAuthenticationToken;
/*      */ import org.springframework.web.bind.annotation.CrossOrigin;
/*      */ import org.springframework.web.bind.annotation.PathVariable;
/*      */ import org.springframework.web.bind.annotation.RequestBody;
/*      */ import org.springframework.web.bind.annotation.RequestMapping;
/*      */ import org.springframework.web.bind.annotation.RequestMethod;
/*      */ import org.springframework.web.bind.annotation.RestController;
/*      */ 
/*      */ @CrossOrigin
/*      */ @RestController
/*      */ @RequestMapping({"/api/rpt/ao"})
/*      */ public class AoRptViewerRestServiceImpl
/*      */   extends BaseRestful {
/*   88 */   private static final Logger logger = LoggerFactory.getLogger(AoRptViewerRestServiceImpl.class); @Autowired
/*      */   private RptEnvVar rptEnvVar; @Autowired
/*      */   private RptService rptService; @Autowired
/*      */   protected MessageSourceExt messageSourceExt; @Autowired
/*      */   private DeviceTcHardwareStatusRepository deviceTcHardwareStatusRepository;
/*      */   @Autowired
/*      */   private ModelMapper modelMapper;
/*      */   
/*      */   @PostConstruct
/*      */   public void init() {
/*   98 */     setSubSystem(SubSystem.RPT);
/*      */   }
/*      */   
/*      */   public void setRptEnvVar(RptEnvVar rptEnvVar) {
/*  102 */     this.rptEnvVar = rptEnvVar;
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/abnormalStatus/{deviceType}"}, method = {RequestMethod.GET})
/*      */   public List<FieldDTO> fetchHardwareStatuesByDeviceType(@PathVariable("deviceType") String deviceType) {
/*  108 */     List<FieldDTO> list = new ArrayList<>();
/*      */     try {
/*  110 */       logger.error("fetch hardware statues by deviceType.");
/*  111 */       IMap<String, DeviceTcConfig> deviceMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  112 */       EntryObject typeEo = (new PredicateBuilder()).getEntryObject();
/*  113 */       PredicateBuilder pb = typeEo.get("deviceType").in(new Comparable[] { deviceType });
/*  114 */       TcProtocolType protocolType = null;
/*  115 */       Iterator<DeviceTcConfig> iterator = deviceMap.values((Predicate)pb).iterator(); if (iterator.hasNext()) { DeviceTcConfig each = iterator.next();
/*  116 */         protocolType = each.getProtocolType(); }
/*      */ 
/*      */       
/*  119 */       List<String> typeList = new ArrayList<>();
/*  120 */       typeList.add(deviceType);
/*  121 */       typeList.add("null");
/*      */       
/*  123 */       for (DeviceTcHardwareStatus status : this.deviceTcHardwareStatusRepository.findByProtocolTypeAndDeviceTypeIn(protocolType, typeList)) {
/*      */ 
/*      */         
/*  126 */         if (deviceType.equals("SCM") && (
/*  127 */           status.getDescription().equals("TIMMING_PLAN_ON_TRANSITION") || status
/*  128 */           .getDescription().equals("MEMORY_ERROR") || status
/*  129 */           .getDescription().equals("COM_LINE_BAD") || status
/*  130 */           .getDescription().equals("SIGNAL_DRIVE_ERROR"))) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/*  135 */         if (deviceType.equals("VD") && (
/*  136 */           status.getDescription().equals("MEMORY_ERROR") || status
/*  137 */           .getDescription().equals("DETECTOR_READY"))) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/*  142 */         FieldDTO dto = new FieldDTO();
/*  143 */         dto.setId(status.getId());
/*  144 */         dto.setDisplayName(status.getName());
/*  145 */         list.add(dto);
/*      */       } 
/*  147 */       list.sort((o1, o2) -> o1.getDisplayName().compareTo(o2.getDisplayName()));
/*  148 */       return list;
/*  149 */     } catch (Exception e) {
/*  150 */       logger.error("fetch hardware statues by deviceType failed.", e);
/*  151 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */   
/*      */   @RequestMapping(value = {"/roadLine"}, method = {RequestMethod.GET})
/*      */   public List<RoadLineDTO> findPaths() {
/*  157 */     List<RoadLineDTO> list = new ArrayList<>();
/*      */     try {
/*  159 */       logger.debug("Find paths.");
/*  160 */       IMap<String, RoadLine> imap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadLine);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  166 */       list = (List<RoadLineDTO>)imap.values().stream().map(TransferHelper::transferRoadLineDTO).sorted(Comparator.comparing(RoadLineDTO::getLineId)).collect(Collectors.toList());
/*  167 */     } catch (RuntimeException e) {
/*  168 */       logger.error("Find paths failed.", e);
/*      */     } 
/*  170 */     logger.debug("list:'{}'", list);
/*  171 */     return list;
/*      */   }
/*      */   
/*      */   @RequestMapping(value = {"/location"}, method = {RequestMethod.GET})
/*      */   public List<PdLocationDTO> retrieveLocation() {
/*      */     try {
/*  177 */       logger.debug("RetrieveLocation");
/*      */       
/*  179 */       IMap<String, DeviceHostLocation> locationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceHostLocation);
/*  180 */       List<PdLocationDTO> locationList = new ArrayList<>();
/*  181 */       for (DeviceHostLocation location : locationMap.values()) {
/*  182 */         PdLocationDTO dto = new PdLocationDTO();
/*  183 */         dto.setId(location.getId());
/*  184 */         dto.setLocName(location.getLocName());
/*  185 */         locationList.add(dto);
/*      */       } 
/*  187 */       return locationList;
/*  188 */     } catch (Exception e) {
/*  189 */       logger.error("RetrieveLocation failed.", e);
/*  190 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/allTypes"}, method = {RequestMethod.GET})
/*      */   public List<AlarmTypeDTO> getAllAlarmType() {
/*      */     try {
/*  198 */       logger.debug("Get AllAlarmType.");
/*  199 */       List<AlarmTypeDTO> result = new ArrayList<>();
/*  200 */       for (AlarmType type : AlarmType.values()) {
/*  201 */         AlarmTypeDTO dto = new AlarmTypeDTO();
/*  202 */         dto.setAlarmType(type);
/*  203 */         result.add(dto);
/*      */       } 
/*  205 */       logger.debug("result:'{}'", result);
/*  206 */       return result;
/*  207 */     } catch (Exception e) {
/*  208 */       logger.error("Get AllAlarmType failed.", e);
/*  209 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */   
/*      */   @RequestMapping(value = {"/deviceType/{category}"}, method = {RequestMethod.GET})
/*      */   public List<DeviceTypeDTO> fetchDeviceTypeByCategory(@PathVariable("category") String category) {
/*      */     try {
/*  216 */       logger.debug("fetchDeviceTypeByCategory, category:'{}'", category);
/*  217 */       List<DeviceTypeDTO> result = new ArrayList<>();
/*  218 */       IMap<String, DeviceType> deviceTypeMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceType);
/*  219 */       PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("category").equal(category);
/*  220 */       for (DeviceType deviceType : deviceTypeMap.values((Predicate)pb)) {
/*  221 */         if (!deviceType.getId().equals("cardReader")) {
/*  222 */           result.add(this.modelMapper.map(deviceType, DeviceTypeDTO.class));
/*      */         }
/*      */       } 
/*  225 */       Collections.sort(result, new Comparator<DeviceTypeDTO>()
/*      */           {
/*      */             
/*      */             public int compare(DeviceTypeDTO o1, DeviceTypeDTO o2)
/*      */             {
/*  230 */               return o2.getId().compareTo(o1.getId());
/*      */             }
/*      */           });
/*  233 */       return result;
/*  234 */     } catch (Exception e) {
/*  235 */       logger.error("fetchDeviceTypeByCategory failed", e);
/*  236 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/fetchReportConfigData"}, method = {RequestMethod.POST})
/*      */   public List<ReportConfigBaseDTO> fetchReportConfigData(@RequestBody RptParametersDTO params) {
/*  247 */     SubSystem subSystem = params.getSubSystem();
/*      */     try {
/*  249 */       logger.debug("Fetch report config data, subSystem='{}'", subSystem);
/*  250 */       Set<String> allCategories = findAllReportCategory(subSystem);
/*  251 */       return (List<ReportConfigBaseDTO>)allCategories
/*  252 */         .stream()
/*  253 */         .map(s -> {
/*      */             ReportConfigBaseDTO dto = new ReportConfigBaseDTO();
/*      */             
/*      */             dto.setId(UUID.randomUUID().toString());
/*      */             
/*      */             dto.setDisplayName(s);
/*      */             return dto;
/*  260 */           }).collect(Collectors.toList());
/*  261 */     } catch (RuntimeException e) {
/*  262 */       logger.error("fetchReportConfigData failed!", e);
/*  263 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */   
/*      */   private Set<String> findAllReportCategory(SubSystem module) {
/*  268 */     if (this.rptEnvVar.getUsingRptServiceRemotePolicy().booleanValue()) {
/*      */       
/*  270 */       BulkRptRemote remote = (BulkRptRemote)RmiUtils.getRemoteClient(this.rptEnvVar.getBulkProcessServiceUri(), BulkRptRemote.class);
/*  271 */       remote.setRptEnvVar(this.rptEnvVar);
/*  272 */       return remote.findAllReportCategory(module);
/*      */     } 
/*  274 */     return this.rptService.findAllReportCategory(module);
/*      */   }
/*      */ 
/*      */   
/*      */   private Set<String> findAllReportSubCategoryByCategory(SubSystem module, String category) {
/*  279 */     if (this.rptEnvVar.getUsingRptServiceRemotePolicy().booleanValue()) {
/*      */       
/*  281 */       BulkRptRemote remote = (BulkRptRemote)RmiUtils.getRemoteClient(this.rptEnvVar.getBulkProcessServiceUri(), BulkRptRemote.class);
/*  282 */       remote.setRptEnvVar(this.rptEnvVar);
/*  283 */       return remote.findAllReportSubCategoryByCategory(module, category);
/*      */     } 
/*  285 */     return this.rptService.findAllReportSubCategoryByCategory(module, category);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private List<ReportRepositoryTreeNode> findReportWithCategoryAndSubCategory(SubSystem module, String category, String subCategory) {
/*  291 */     if (this.rptEnvVar.getUsingRptServiceRemotePolicy().booleanValue()) {
/*      */       
/*  293 */       BulkRptRemote remote = (BulkRptRemote)RmiUtils.getRemoteClient(this.rptEnvVar.getBulkProcessServiceUri(), BulkRptRemote.class);
/*  294 */       remote.setRptEnvVar(this.rptEnvVar);
/*  295 */       return remote.findReportWithCategoryAndSubCategory(module, category, subCategory);
/*      */     } 
/*  297 */     return this.rptService.findReportWithCategoryAndSubCategory(module, category, subCategory);
/*      */   }
/*      */ 
/*      */   
/*      */   private String getDeviceTypeByReportName(String reportName) {
/*  302 */     switch (reportName) {
/*      */       case "MON_01資訊可變標誌即時資料報表":
/*      */       case "OPR1_01操作記錄報表":
/*      */       case "STA_01現場終端設備狀態記錄報表":
/*      */       case "OPR2_06現場終端設備運作記錄報表":
/*      */       case "OPR2_03現場終端設備操作記錄統計報表":
/*      */       case "MON_07設備狀態即時監視報表":
/*      */       case "STA_04現場終端設備狀態記錄統計報表":
/*      */       case "OPR2_07定時比對記錄報表":
/*  311 */         return "CMS";
/*      */       case "MON_03速限可變標誌即時資料報表":
/*  313 */         return "CSLS";
/*      */       case "MON_05霧慢行標誌即時資料報表":
/*  315 */         return "FS";
/*      */       case "PRM_32坍方程度參數表":
/*      */       case "DATA_11小時變位量資料記錄報表":
/*      */       case "DATA_33坍方事件資料記錄報表":
/*  319 */         return "LS";
/*      */       case "DATA_09週期雨量資料記錄報表":
/*      */       case "DATA_31雨量事件資料記錄報表":
/*  322 */         return "RD";
/*      */       case "DATA_24VD詳細記錄報表":
/*      */       case "DATA_01五分鐘交通資料記錄報表":
/*      */       case "DATA_00一天交通資料":
/*      */       case "DATA_02五分鐘車道使用率及車間距報表":
/*      */       case "DATA_03一分鐘交通資料記錄報表":
/*      */       case "DATA_04一分鐘車道使用率及車間距報表":
/*      */       case "VD_04非標竿VD流量比對檢核表":
/*      */       case "VD_02VD車速準確度檢測表":
/*      */       case "VD_03標竿VD流量比對檢核表":
/*      */       case "DATA_25VD總計記錄報表":
/*      */       case "VD_00VD流量準確度檢測表一":
/*      */       case "VD_01VD流量準確度檢測表二":
/*  335 */         return "VD";
/*      */       case "OPR2_01匝道儀控操作記錄報表":
/*      */       case "OPR2_02匝道儀控資訊顯示操作記錄報表":
/*      */       case "OPR2_04匝道儀控操作記錄統計報表":
/*      */       case "MON_06匝道儀控即時資料報表":
/*  340 */         return "RMS";
/*      */       case "MON_02旅行時間標誌即時資料報表":
/*  342 */         return "TTS";
/*      */       case "DATA_08五分鐘濃霧資料記錄報表":
/*      */       case "DATA_30濃霧事件資料記錄報表":
/*  345 */         return "VI";
/*      */       case "DATA_10時段風力資料記錄報表":
/*      */       case "DATA_32風力事件資料記錄報表":
/*  348 */         return "WD";
/*      */       case "MON_10天候資訊可變標誌即時資料報表":
/*  350 */         return "WIS";
/*      */       case "MON_09路徑導引標誌即時資料報表":
/*  352 */         return "RGS";
/*      */       case "MON_11車道管制號誌即時資料報表":
/*  354 */         return "LCS";
/*      */       case "MON_12三面轉板即時資料報表":
/*  356 */         return "SCS";
/*      */     } 
/*  358 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/fetchAllLines"})
/*      */   public List<DeviceConfigBaseDTO> fetchAllLines() {
/*      */     try {
/*  370 */       logger.debug("fetchAllLines.");
/*  371 */       IMap<String, RoadLine> roadLineMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadLine);
/*  372 */       return (List<DeviceConfigBaseDTO>)roadLineMap
/*  373 */         .values()
/*  374 */         .stream()
/*  375 */         .map(l -> {
/*      */             DeviceConfigBaseDTO roadLineDto = new DeviceConfigBaseDTO();
/*      */             
/*      */             roadLineDto.setId(UUID.randomUUID().toString());
/*      */             
/*      */             roadLineDto.setLineId(l.getLineId());
/*      */             roadLineDto.setType(DeviceConfigBaseDTO.Type.LINE);
/*      */             roadLineDto.setDisplayName(l.getLineName());
/*      */             return roadLineDto;
/*  384 */           }).sorted(
/*  385 */           Comparator.comparing(DeviceConfigBaseDTO::getLineId, String.CASE_INSENSITIVE_ORDER))
/*  386 */         .collect(Collectors.toList());
/*  387 */     } catch (RuntimeException e) {
/*  388 */       logger.error("fetchAllLines failed!", e);
/*  389 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/fetchDirections"}, method = {RequestMethod.POST})
/*      */   public List<DeviceConfigBaseDTO> fetchDirections(@RequestBody RptParametersDTO params) {
/*  401 */     String lineId = params.getLineId();
/*      */     try {
/*  403 */       logger.debug("fetchDirections, lineId='{}'", lineId);
/*  404 */       RoadLine roadLine = getRoadLine(lineId);
/*  405 */       List<DeviceConfigBaseDTO> directionDtos = new ArrayList<>();
/*  406 */       if (roadLine != null) {
/*  407 */         if (roadLine.getDirection() == Direction.N || roadLine.getDirection() == Direction.S) {
/*  408 */           DeviceConfigBaseDTO northDto = new DeviceConfigBaseDTO();
/*  409 */           northDto.setId(UUID.randomUUID().toString());
/*  410 */           northDto.setLineId(lineId);
/*  411 */           northDto.setDirection(Direction.N.toString());
/*  412 */           northDto.setType(DeviceConfigBaseDTO.Type.DIRECTION);
/*  413 */           northDto.setDisplayName("北向");
/*      */           
/*  415 */           DeviceConfigBaseDTO southDto = new DeviceConfigBaseDTO();
/*  416 */           southDto.setId(UUID.randomUUID().toString());
/*  417 */           southDto.setLineId(lineId);
/*  418 */           southDto.setDirection(Direction.S.toString());
/*  419 */           southDto.setType(DeviceConfigBaseDTO.Type.DIRECTION);
/*  420 */           southDto.setDisplayName("南向");
/*      */           
/*  422 */           directionDtos.add(northDto);
/*  423 */           directionDtos.add(southDto);
/*  424 */         } else if (roadLine.getDirection() == Direction.E || roadLine
/*  425 */           .getDirection() == Direction.W) {
/*  426 */           DeviceConfigBaseDTO westDto = new DeviceConfigBaseDTO();
/*  427 */           westDto.setId(UUID.randomUUID().toString());
/*  428 */           westDto.setLineId(lineId);
/*  429 */           westDto.setDirection(Direction.W.toString());
/*  430 */           westDto.setType(DeviceConfigBaseDTO.Type.DIRECTION);
/*  431 */           westDto.setDisplayName("西向");
/*      */           
/*  433 */           DeviceConfigBaseDTO eastDto = new DeviceConfigBaseDTO();
/*  434 */           eastDto.setId(UUID.randomUUID().toString());
/*  435 */           eastDto.setLineId(lineId);
/*  436 */           eastDto.setDirection(Direction.E.toString());
/*  437 */           eastDto.setType(DeviceConfigBaseDTO.Type.DIRECTION);
/*  438 */           eastDto.setDisplayName("東向");
/*      */           
/*  440 */           directionDtos.add(westDto);
/*  441 */           directionDtos.add(eastDto);
/*      */         } 
/*      */       }
/*  444 */       return directionDtos;
/*  445 */     } catch (RuntimeException e) {
/*  446 */       logger.error("fetchDirections failed.", e);
/*  447 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private RoadLine getRoadLine(String lineId) {
/*  458 */     IMap<String, RoadLine> roadLineMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadLine);
/*  459 */     if (roadLineMap == null) {
/*  460 */       return null;
/*      */     }
/*  462 */     RoadLine roadLine = (RoadLine)roadLineMap.get(lineId);
/*  463 */     return (roadLine == null || roadLine.isEnable().booleanValue()) ? null : roadLine;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/fetchRoadSections"}, method = {RequestMethod.POST})
/*      */   public List<DeviceConfigBaseDTO> fetchRoadSections(@RequestBody RptParametersDTO params) {
/*  475 */     String lineId = params.getLineId();
/*  476 */     String direction = params.getDirection();
/*      */     try {
/*  478 */       logger.debug("fetchRoadSections, lineId='{}', direction='{}'", lineId, direction);
/*  479 */       Direction directionEnum = Enum.<Direction>valueOf(Direction.class, direction);
/*  480 */       IMap<String, RoadSection> roadSectionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadSection);
/*  481 */       EntryObject entryObject = (new PredicateBuilder()).getEntryObject();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  487 */       PredicateBuilder predicate = entryObject.get("lineid").equal(lineId).and((Predicate)entryObject.get("direction").equal((Comparable)directionEnum)).and((Predicate)entryObject.get("areaType").equal((Comparable)AreaType.C));
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
/*  509 */       List<DeviceConfigBaseDTO> roadSectionDtos = (List<DeviceConfigBaseDTO>)roadSectionMap.values((Predicate)predicate).stream().map(s -> { RoadDivision roadDivision = getRoadDivision(s.getEndDivisionId()); if (roadDivision != null) { DeviceConfigBaseDTO roadSectionDto = new DeviceConfigBaseDTO(); roadSectionDto.setId(UUID.randomUUID().toString()); roadSectionDto.setLineId(lineId); roadSectionDto.setSectionId(s.getSectionId()); roadSectionDto.setSectionMileage(roadDivision.getMileage().intValue()); roadSectionDto.setDirection(direction); roadSectionDto.setType(DeviceConfigBaseDTO.Type.SECTION); roadSectionDto.setDisplayName(s.getSectionName()); return roadSectionDto; }  return null; }).collect(Collectors.toList());
/*      */       
/*  511 */       if (directionEnum == Direction.S || directionEnum == Direction.E) {
/*  512 */         roadSectionDtos.sort(Comparator.comparing(DeviceConfigBaseDTO::getSectionMileage));
/*      */       } else {
/*  514 */         roadSectionDtos.sort(
/*  515 */             Comparator.<DeviceConfigBaseDTO, Comparable>comparing(DeviceConfigBaseDTO::getSectionMileage).reversed());
/*      */       } 
/*  517 */       return roadSectionDtos;
/*  518 */     } catch (RuntimeException e) {
/*  519 */       logger.error("fetchRoadSections failed! lineId='{}', direction='{}'", new Object[] { lineId, direction, e });
/*  520 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private RoadDivision getRoadDivision(String endDivisionId) {
/*  530 */     IMap<String, RoadDivision> map = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadDivision);
/*  531 */     return (map == null) ? null : (RoadDivision)map.get(endDivisionId);
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
/*      */   @RequestMapping(value = {"/fetchDevices"}, method = {RequestMethod.POST})
/*      */   public List<DeviceConfigBaseDTO> fetchDevices(@RequestBody RptParametersDTO params) {
/*  545 */     String lineId = params.getLineId();
/*  546 */     String sectionId = params.getSectionId();
/*  547 */     String direction = params.getDirection();
/*  548 */     String deviceType = params.getDeviceTypeInStr();
/*      */     try {
/*  550 */       logger.debug("fetchRoadSections, lineId='{}', sectionId='{}', dir='{}', type='{}'", new Object[] { lineId, sectionId, direction, deviceType });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  556 */       Direction directionEnum = Enum.<Direction>valueOf(Direction.class, direction);
/*  557 */       IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  558 */       EntryObject entryObject = (new PredicateBuilder()).getEntryObject();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  563 */       PredicateBuilder predicate = entryObject.get("lineId").equal(lineId).and((Predicate)entryObject.get("sectionId").equal(sectionId));
/*      */       
/*  565 */       if (deviceType.equals("CMS")) {
/*  566 */         String[] types = { "CMS", "CMSRST" };
/*  567 */         predicate = predicate.and((Predicate)entryObject.get("deviceType").in((Comparable[])types));
/*      */       } else {
/*  569 */         String type = deviceType;
/*  570 */         predicate = predicate.and((Predicate)entryObject.get("deviceType").equal(type));
/*      */       } 
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
/*  588 */       List<DeviceConfigBaseDTO> deviceDtos = (List<DeviceConfigBaseDTO>)deviceConfigMap.values((Predicate)predicate).stream().map(d -> { DeviceConfigBaseDTO deviceDto = new DeviceConfigBaseDTO(); deviceDto.setId(d.getDeviceName()); deviceDto.setLineId(lineId); deviceDto.setSectionId(sectionId); deviceDto.setDeviceMilepost(d.getMilepost().intValue()); deviceDto.setType(DeviceConfigBaseDTO.Type.DEVICE); deviceDto.setDisplayName(d.getDisplayName()); return deviceDto; }).collect(Collectors.toList());
/*      */       
/*  590 */       if (directionEnum == Direction.S || directionEnum == Direction.E) {
/*  591 */         deviceDtos.sort(Comparator.comparing(DeviceConfigBaseDTO::getDeviceMilepost));
/*      */       } else {
/*  593 */         deviceDtos.sort(Comparator.<DeviceConfigBaseDTO, Comparable>comparing(DeviceConfigBaseDTO::getDeviceMilepost).reversed());
/*      */       } 
/*  595 */       return deviceDtos;
/*  596 */     } catch (RuntimeException e) {
/*  597 */       logger.error("fetchVdDevices failed.", e);
/*  598 */       return Collections.emptyList();
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
/*      */   @RequestMapping(value = {"/fetchQueryData"}, method = {RequestMethod.POST})
/*      */   public ReportPreviewInfoDTO fetchQueryData(@RequestBody RptParametersDTO params, Principal principal) {
/*  612 */     ReportQueryConditionDTO conditionDto = params.getReportQueryConditionDTO();
/*      */     try {
/*  614 */       addSsoUserToInputParameter(conditionDto.getInputParameter(), principal);
/*      */       
/*  616 */       ReportPreviewInfo previvewInfo = preview(conditionDto
/*  617 */           .getReportId(), conditionDto
/*  618 */           .getInputParameter(), conditionDto
/*  619 */           .getOffset(), conditionDto
/*  620 */           .getZoomRatio());
/*  621 */       return transferToReportPreviewInfoDTO(previvewInfo);
/*  622 */     } catch (RuntimeException e) {
/*  623 */       logger.error("fetchQueryData failed.", e);
/*  624 */       return new ReportPreviewInfoDTO();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private ReportPreviewInfo preview(String reportId, Map<String, Object> inputParameter, int pageNumber, float zoomRatio) throws RptFmException {
/*  631 */     if (this.rptEnvVar.getUsingRptServiceRemotePolicy().booleanValue()) {
/*      */       
/*  633 */       BulkRptRemote remote = (BulkRptRemote)RmiUtils.getRemoteClient(this.rptEnvVar.getBulkProcessServiceUri(), BulkRptRemote.class);
/*  634 */       remote.setRptEnvVar(this.rptEnvVar);
/*  635 */       return remote.preview(reportId, inputParameter, pageNumber, zoomRatio);
/*      */     } 
/*  637 */     return this.rptService.preview(reportId, inputParameter, pageNumber, zoomRatio);
/*      */   }
/*      */ 
/*      */   
/*      */   private ReportPreviewInfoDTO transferToReportPreviewInfoDTO(ReportPreviewInfo previewInfo) {
/*  642 */     ReportPreviewInfoDTO dto = new ReportPreviewInfoDTO();
/*  643 */     if (previewInfo == null) {
/*  644 */       return dto;
/*      */     }
/*  646 */     dto.setReportContent(previewInfo.getReportContent());
/*  647 */     dto.setPreviewTime(previewInfo.getPreviewTime());
/*  648 */     dto.setTotalPage(previewInfo.getTotalPage());
/*  649 */     return dto;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/saveDownloadChart"}, method = {RequestMethod.POST})
/*      */   public String saveDownloadChart(@RequestBody RptParametersDTO params, Principal principal) {
/*  661 */     String reportName = params.getReportName();
/*  662 */     ReportQueryConditionDTO conditionDto = params.getReportQueryConditionDTO();
/*      */     try {
/*  664 */       logger.debug("saveDownloadChart, reportId='{}', inputParameter='{}'", reportName, conditionDto);
/*      */       
/*  666 */       addSsoUserToInputParameter(conditionDto.getInputParameter(), principal);
/*  667 */       params.setDeviceName("");
/*  668 */       params.setUserId("");
/*  669 */       params.setIp("");
/*  670 */       params.setRemark("");
/*  671 */       params.setLogMessage(this.messageSourceExt.getMessage(""));
/*  672 */       return export(reportName, conditionDto.getInputParameter(), ReportFormat.XLS);
/*  673 */     } catch (RuntimeException e) {
/*  674 */       logger.error("saveDownloadChart failed.", e);
/*  675 */       params.setDeviceName("");
/*  676 */       params.setUserId("");
/*  677 */       params.setIp("");
/*  678 */       params.setRemark("");
/*  679 */       params.setLogMessage(this.messageSourceExt.getMessage(""));
/*  680 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private String export(String reportId, Map<String, Object> inputParameter, ReportFormat format) {
/*  685 */     if (this.rptEnvVar.getUsingRptServiceRemotePolicy().booleanValue()) {
/*      */       
/*  687 */       BulkRptRemote remote = (BulkRptRemote)RmiUtils.getRemoteClient(this.rptEnvVar.getBulkProcessServiceUri(), BulkRptRemote.class);
/*  688 */       remote.setRptEnvVar(this.rptEnvVar);
/*  689 */       return remote.export(reportId, inputParameter, format);
/*      */     } 
/*  691 */     return this.rptService.export(reportId, inputParameter, format);
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
/*      */   @RequestMapping(value = {"/saveDownloadReport"}, method = {RequestMethod.POST})
/*      */   public String saveDownloadReport(@RequestBody RptParametersDTO params, Principal principal) {
/*  705 */     String reportName = params.getReportName();
/*  706 */     ReportQueryConditionDTO conditionDto = params.getReportQueryConditionDTO();
/*  707 */     String format = params.getReportFormat();
/*      */     try {
/*  709 */       logger.debug("downloadChart, reportId='{}', input='{}', format='{}'", new Object[] { reportName, conditionDto, format });
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  714 */       ReportFormat reportFormat = Enum.<ReportFormat>valueOf(ReportFormat.class, format);
/*  715 */       addSsoUserToInputParameter(conditionDto.getInputParameter(), principal);
/*  716 */       params.setDeviceName("");
/*  717 */       params.setUserId("");
/*  718 */       params.setIp("");
/*  719 */       params.setRemark("");
/*  720 */       params.setLogMessage(this.messageSourceExt.getMessage(""));
/*  721 */       return export(reportName, conditionDto.getInputParameter(), reportFormat);
/*  722 */     } catch (RuntimeException e) {
/*  723 */       logger.error("downloadReport failed.", e);
/*  724 */       params.setDeviceName("");
/*  725 */       params.setUserId("");
/*  726 */       params.setIp("");
/*  727 */       params.setRemark("");
/*  728 */       params.setLogMessage(this.messageSourceExt.getMessage(""));
/*  729 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/retrievePrinterList"})
/*      */   public List<String> retrievePrinterList() {
/*      */     try {
/*  741 */       logger.debug("retrievePrinterList.");
/*  742 */       return getPrinterList();
/*  743 */     } catch (RuntimeException e) {
/*  744 */       logger.error("retrievePrinterList failed.", e);
/*  745 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */   
/*      */   private List<String> getPrinterList() {
/*  750 */     if (this.rptEnvVar.getUsingRptServiceRemotePolicy().booleanValue()) {
/*      */       
/*  752 */       BulkRptRemote remote = (BulkRptRemote)RmiUtils.getRemoteClient(this.rptEnvVar.getBulkProcessServiceUri(), BulkRptRemote.class);
/*  753 */       remote.setRptEnvVar(this.rptEnvVar);
/*  754 */       return remote.retrievePrinterList();
/*      */     } 
/*  756 */     return this.rptService.retrievePrinterList();
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
/*      */   @RequestMapping(value = {"/savePrintReport"}, method = {RequestMethod.POST})
/*      */   public void savePrintReport(@RequestBody RptParametersDTO params, Principal principal) {
/*  769 */     String reportName = params.getReportName();
/*  770 */     ReportQueryConditionDTO conditionDto = params.getReportQueryConditionDTO();
/*  771 */     String printer = params.getPrinter();
/*      */     try {
/*  773 */       logger.debug("printReport, name='{}', condition='{}', printer='{}'", new Object[] { reportName, conditionDto, printer });
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  778 */       addSsoUserToInputParameter(conditionDto.getInputParameter(), principal);
/*  779 */       print(reportName, conditionDto.getInputParameter(), printer);
/*  780 */       params.setDeviceName("");
/*  781 */       params.setUserId("");
/*  782 */       params.setIp("");
/*  783 */       params.setRemark("");
/*  784 */       params.setLogMessage(this.messageSourceExt.getMessage(""));
/*  785 */     } catch (RuntimeException e) {
/*  786 */       logger.error("printReport failed.", e);
/*  787 */       params.setDeviceName("");
/*  788 */       params.setUserId("");
/*  789 */       params.setIp("");
/*  790 */       params.setRemark("");
/*  791 */       params.setLogMessage(this.messageSourceExt.getMessage(""));
/*      */     } 
/*      */   }
/*      */   
/*      */   private void print(String reportId, Map<String, Object> inputParameter, String printer) {
/*  796 */     if (this.rptEnvVar.getUsingRptServiceRemotePolicy().booleanValue()) {
/*      */       
/*  798 */       BulkRptRemote remote = (BulkRptRemote)RmiUtils.getRemoteClient(this.rptEnvVar.getBulkProcessServiceUri(), BulkRptRemote.class);
/*  799 */       remote.setRptEnvVar(this.rptEnvVar);
/*  800 */       remote.print(reportId, inputParameter, printer);
/*      */     } else {
/*  802 */       this.rptService.print(reportId, inputParameter, printer);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/fetchPreviewHeader"}, method = {RequestMethod.POST})
/*      */   public Map<String, PreviewHeaderDTO> fetchPreviewHeader(@RequestBody RptParametersDTO params) {
/*  814 */     String reportId = params.getReportId();
/*      */     try {
/*  816 */       logger.debug("fetchPreviewHeader, reportId='{}'", reportId);
/*  817 */       return AoRptValueFormatter.transferToPreviewHeaderDTOMap(retrievePreviewHeader(reportId));
/*  818 */     } catch (RuntimeException e) {
/*  819 */       logger.error("fetchPreviewHeader failed.", e);
/*  820 */       return Collections.emptyMap();
/*      */     } 
/*      */   }
/*      */   
/*      */   private Map<String, PreviewHeader> retrievePreviewHeader(String reportId) {
/*  825 */     if (this.rptEnvVar.getUsingRptServiceRemotePolicy().booleanValue()) {
/*      */       
/*  827 */       BulkRptRemote remote = (BulkRptRemote)RmiUtils.getRemoteClient(this.rptEnvVar.getBulkProcessServiceUri(), BulkRptRemote.class);
/*  828 */       remote.setRptEnvVar(this.rptEnvVar);
/*  829 */       return remote.retrievePreviewHeader(reportId);
/*      */     } 
/*  831 */     return this.rptService.retrievePreviewHeader(reportId);
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
/*      */   @RequestMapping(value = {"/query"}, method = {RequestMethod.POST})
/*      */   public List<PreviewDataDTO> query(@RequestBody RptParametersDTO params, Principal principal) {
/*  844 */     String reportId = params.getReportId();
/*  845 */     ReportQueryConditionDTO condition = params.getReportQueryConditionDTO();
/*      */     try {
/*  847 */       logger.debug("query, reportId='{}', condition='{}'", reportId, condition);
/*  848 */       addSsoUserToInputParameter(condition.getInputParameter(), principal);
/*  849 */       return AoRptValueFormatter.transferToPreviewDataDTOs(
/*  850 */           query(reportId, condition.getInputParameter()));
/*  851 */     } catch (RuntimeException e) {
/*  852 */       logger.error("query failed.", e);
/*  853 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */   
/*      */   public List<PreviewData> query(String reportId, Map<String, Object> inputParameter) {
/*  858 */     if (this.rptEnvVar.getUsingRptServiceRemotePolicy().booleanValue()) {
/*      */       
/*  860 */       BulkRptRemote remote = (BulkRptRemote)RmiUtils.getRemoteClient(this.rptEnvVar.getBulkProcessServiceUri(), BulkRptRemote.class);
/*  861 */       remote.setRptEnvVar(this.rptEnvVar);
/*  862 */       return remote.query(reportId, inputParameter);
/*      */     } 
/*  864 */     return this.rptService.query(reportId, inputParameter);
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
/*      */   @RequestMapping(value = {"/queryWithHeader"}, method = {RequestMethod.POST})
/*      */   public PreviewGridDataDTO queryWithHeader(@RequestBody RptParametersDTO params, Principal principal) {
/*  878 */     String reportId = params.getReportId();
/*  879 */     ReportQueryConditionDTO condition = params.getReportQueryConditionDTO();
/*      */     try {
/*  881 */       logger.debug("queryWithHeader, reportId='{}', condition='{}'", reportId, condition);
/*  882 */       addSsoUserToInputParameter(condition.getInputParameter(), principal);
/*  883 */       return AoRptValueFormatter.transferToPreviewGridDataDTO(
/*  884 */           queryPreviewGridData(reportId, condition.getInputParameter()));
/*  885 */     } catch (RuntimeException e) {
/*  886 */       logger.error("queryWithHeader failed.", e);
/*  887 */       return new PreviewGridDataDTO();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private PreviewGridData queryPreviewGridData(String reportId, Map<String, Object> inputParameter) {
/*  893 */     if (this.rptEnvVar.getUsingRptServiceRemotePolicy().booleanValue()) {
/*      */       
/*  895 */       BulkRptRemote remote = (BulkRptRemote)RmiUtils.getRemoteClient(this.rptEnvVar.getBulkProcessServiceUri(), BulkRptRemote.class);
/*  896 */       remote.setRptEnvVar(this.rptEnvVar);
/*  897 */       return remote.queryPreviewGridData(reportId, inputParameter);
/*      */     } 
/*  899 */     return this.rptService.queryPreviewGridData(reportId, inputParameter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping({"/fetchSysOperators"})
/*      */   public List<OperatorDTO> fetchSysOperators() {
/*      */     try {
/*  911 */       logger.debug("fetchSysOperators");
/*  912 */       IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/*      */       
/*  914 */       List<User> users = new ArrayList<>(userMap.values());
/*  915 */       return AoRptValueFormatter.transferToOperatorDTOs(users);
/*  916 */     } catch (RuntimeException e) {
/*  917 */       logger.error("fetchSysOperators failed.", e);
/*  918 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/saveSchedule"}, method = {RequestMethod.POST})
/*      */   public Boolean saveSchedule(@RequestBody RptParametersDTO params, Principal principal) {
/*  930 */     Map<String, Object> inputParameter = params.getInputParameter();
/*      */     try {
/*  932 */       logger.debug("saveSchedule");
/*  933 */       addSsoUserToInputParameter(inputParameter, principal);
/*  934 */       scheduleReport(inputParameter);
/*  935 */       params.setDeviceName("");
/*  936 */       params.setUserId("");
/*  937 */       params.setIp("");
/*  938 */       params.setRemark("");
/*  939 */       params.setLogMessage(this.messageSourceExt.getMessage(""));
/*  940 */       return Boolean.valueOf(true);
/*  941 */     } catch (RuntimeException e) {
/*  942 */       logger.error("saveSchedule failed.", e);
/*  943 */       params.setDeviceName("");
/*  944 */       params.setUserId("");
/*  945 */       params.setIp("");
/*  946 */       params.setRemark("");
/*  947 */       params.setLogMessage(this.messageSourceExt.getMessage(""));
/*  948 */       return Boolean.valueOf(false);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void scheduleReport(Map<String, Object> inputParameter) {
/*  953 */     if (this.rptEnvVar.getUsingRptServiceRemotePolicy().booleanValue()) {
/*      */       
/*  955 */       BulkRptRemote remote = (BulkRptRemote)RmiUtils.getRemoteClient(this.rptEnvVar.getBulkProcessServiceUri(), BulkRptRemote.class);
/*  956 */       remote.setRptEnvVar(this.rptEnvVar);
/*  957 */       remote.scheduleReport(inputParameter);
/*      */     } else {
/*  959 */       this.rptService.scheduleReport(inputParameter);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/deleteSchedule"}, method = {RequestMethod.POST})
/*      */   public Boolean deleteSchedule(@RequestBody RptParametersDTO params) {
/*  971 */     String id = params.getId();
/*      */     try {
/*  973 */       logger.debug("deleteSchedule, id='{}'", id);
/*  974 */       removeExportFile(id);
/*  975 */       params.setDeviceName("");
/*  976 */       params.setUserId("");
/*  977 */       params.setIp("");
/*  978 */       params.setRemark("");
/*  979 */       params.setLogMessage(this.messageSourceExt.getMessage(""));
/*      */       
/*  981 */       return Boolean.valueOf(true);
/*  982 */     } catch (RuntimeException e) {
/*  983 */       logger.error("deleteSchedule failed.", e);
/*  984 */       params.setDeviceName("");
/*  985 */       params.setUserId("");
/*  986 */       params.setIp("");
/*  987 */       params.setRemark("");
/*  988 */       params.setLogMessage(this.messageSourceExt.getMessage(""));
/*      */       
/*  990 */       return Boolean.valueOf(false);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void removeExportFile(String id) {
/*  995 */     if (this.rptEnvVar.getUsingRptServiceRemotePolicy().booleanValue()) {
/*      */       
/*  997 */       BulkRptRemote remote = (BulkRptRemote)RmiUtils.getRemoteClient(this.rptEnvVar.getBulkProcessServiceUri(), BulkRptRemote.class);
/*  998 */       remote.setRptEnvVar(this.rptEnvVar);
/*  999 */       remote.removeExportFile(id);
/*      */     } else {
/* 1001 */       this.rptService.removeExportFile(id);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void addSsoUserToInputParameter(Map<String, Object> inputParameter, Principal principal) {
/* 1006 */     if (inputParameter == null) {
/*      */       return;
/*      */     }
/* 1009 */     if (principal != null) {
/* 1010 */       AbstractAuthenticationToken auth = (AbstractAuthenticationToken)principal;
/* 1011 */       inputParameter.put("userName", auth.getName());
/*      */     } 
/*      */   }
/*      */   
/*      */   @RequestMapping(value = {"/getHdaReportTreeBySubSystem"}, method = {RequestMethod.POST})
/*      */   public List<ReportTreeBaseDTO> getHdaReportTreeBySubSystem(@RequestBody RptParametersDTO params) {
/* 1017 */     SubSystem subSystem = params.getSubSystem();
/*      */     try {
/* 1019 */       List<ReportTreeBaseDTO> dtos = new ArrayList<>();
/*      */       try {
/* 1021 */         Set<String> allCategories = findAllReportCategory(subSystem);
/* 1022 */         for (String s : allCategories) {
/* 1023 */           ReportTreeBaseDTO dto = new ReportTreeBaseDTO();
/* 1024 */           dto.setId(UUID.randomUUID().toString());
/* 1025 */           dto.setDisplayName(s);
/* 1026 */           dto.getChildren().addAll(getSubCategoryHdaReports(subSystem, s));
/* 1027 */           dtos.add(dto);
/*      */         } 
/* 1029 */       } catch (RuntimeException e) {
/* 1030 */         logger.warn("fetchHdaTreeData failed!", e);
/*      */       } 
/* 1032 */       return dtos;
/* 1033 */     } catch (RuntimeException e) {
/* 1034 */       logger.error("getHdaReportTreeBySubSystem failed.", e);
/* 1035 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/deviceConfig/{deviceType}/{lineId}"}, method = {RequestMethod.GET})
/*      */   public List<DeviceConfigDTO> getVdDevice(@PathVariable("deviceType") String deviceType, @PathVariable("lineId") String lineId) {
/*      */     try {
/* 1043 */       List<DeviceConfigDTO> result = new ArrayList<>();
/* 1044 */       logger.debug("getVdDevice");
/* 1045 */       IMap<String, DeviceTcConfig> ConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 1046 */       EntryObject entryObject = (new PredicateBuilder()).getEntryObject();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1051 */       PredicateBuilder predicate = entryObject.get("deviceType").equal(deviceType).and((Predicate)entryObject.get("lineId").equal(lineId));
/* 1052 */       Iterator<DeviceTcConfig> getData = ConfigMap.values((Predicate)predicate).iterator();
/* 1053 */       while (getData.hasNext()) {
/* 1054 */         DeviceTcConfig deviceTcConfig = getData.next();
/* 1055 */         if (deviceTcConfig.getEnable().booleanValue()) {
/* 1056 */           DeviceConfigDTO dto = new DeviceConfigDTO();
/* 1057 */           dto.setDeviceName(deviceTcConfig.getDeviceName());
/* 1058 */           dto.setDisplayName(deviceTcConfig.getDisplayName());
/* 1059 */           dto.setDirection(deviceTcConfig.getDirection());
/* 1060 */           result.add(dto);
/*      */         } 
/*      */       } 
/*      */       
/* 1064 */       return result;
/* 1065 */     } catch (Exception exception) {
/* 1066 */       logger.error("getVdDevice failed", exception);
/* 1067 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */   
/*      */   @RequestMapping(value = {"/cctvDevice"}, method = {RequestMethod.GET})
/*      */   public List<DeviceConfigDTO> getCctvDevice() {
/*      */     try {
/* 1074 */       List<DeviceConfigDTO> result = new ArrayList<>();
/* 1075 */       logger.debug("getCctvDevice");
/* 1076 */       IMap<String, DeviceTcConfig> configMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 1077 */       EntryObject entryObject = (new PredicateBuilder()).getEntryObject();
/* 1078 */       PredicateBuilder predicate = entryObject.get("deviceType").equal("CCTV");
/* 1079 */       for (DeviceTcConfig c : configMap.values((Predicate)predicate)) {
/* 1080 */         DeviceConfigDTO dto = new DeviceConfigDTO();
/* 1081 */         dto.setDeviceName(c.getDeviceName());
/* 1082 */         dto.setDisplayName(c.getDisplayName());
/* 1083 */         result.add(dto);
/*      */       } 
/*      */       
/* 1086 */       return result;
/* 1087 */     } catch (Exception exception) {
/* 1088 */       logger.error("getCctvDevice failed", exception);
/* 1089 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roadSection"}, method = {RequestMethod.GET})
/*      */   public List<RoadSectionDTO> getAllRoadSection() {
/*      */     try {
/* 1101 */       logger.debug("Get all road section.");
/* 1102 */       List<RoadSectionDTO> result = new ArrayList<>();
/* 1103 */       IMap<String, RoadLine> roadLineMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadLine);
/* 1104 */       IMap<String, RoadSection> roadSectionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadSection);
/* 1105 */       for (RoadSection section : roadSectionMap.values()) {
/* 1106 */         String lineName = ((RoadLine)roadLineMap.get(section.getLineId())).getLineName();
/* 1107 */         RoadSectionDTO dto = (RoadSectionDTO)this.modelMapper.map(section, RoadSectionDTO.class);
/* 1108 */         dto.setLineName(lineName);
/* 1109 */         result.add(dto);
/*      */       } 
/* 1111 */       return result;
/* 1112 */     } catch (RuntimeException e) {
/* 1113 */       logger.error("Get all road section failed", e);
/* 1114 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */   
/*      */   public List<ReportTreeBaseDTO> getSubCategoryHdaReports(SubSystem subSystem, String category) {
/* 1119 */     List<ReportTreeBaseDTO> dtos = new ArrayList<>();
/*      */     try {
/* 1121 */       Set<String> allSubCategories = findAllReportSubCategoryByCategory(subSystem, category);
/* 1122 */       for (String s : allSubCategories)
/*      */       {
/* 1124 */         if (s == null) {
/* 1125 */           dtos.addAll(getHdaReports(subSystem, category, s)); continue;
/*      */         } 
/* 1127 */         ReportTreeBaseDTO dto = new ReportTreeBaseDTO();
/* 1128 */         dto.setId(UUID.randomUUID().toString());
/* 1129 */         dto.setDisplayName(s);
/* 1130 */         dto.getChildren().addAll(getHdaReports(subSystem, category, s));
/* 1131 */         dtos.add(dto);
/*      */       }
/*      */     
/* 1134 */     } catch (RuntimeException e) {
/* 1135 */       logger.warn("getSubCategoryReports failed! category:'{}'", category, e);
/* 1136 */       throw e;
/*      */     } 
/* 1138 */     return dtos;
/*      */   }
/*      */ 
/*      */   
/*      */   private List<ReportTreeBaseDTO> getHdaReports(SubSystem subSystem, String category, String subCategory) {
/* 1143 */     List<ReportTreeBaseDTO> dtos = new ArrayList<>();
/* 1144 */     ReportTreeBaseDTO roadMapDto = new ReportTreeBaseDTO();
/*      */     try {
/* 1146 */       roadMapDto.setRoadMap(true);
/*      */       
/* 1148 */       List<ReportRepositoryTreeNode> reports = findReportWithCategoryAndSubCategory(subSystem, category, subCategory);
/* 1149 */       for (ReportRepositoryTreeNode n : reports) {
/* 1150 */         ReportTreeDetailDTO dto = new ReportTreeDetailDTO();
/* 1151 */         dto.setId(n.getId());
/* 1152 */         dto.setDisplayName(n.getName());
/* 1153 */         if (n.getRoadMap() == Boolean.TRUE) {
/*      */           
/* 1155 */           roadMapDto.getChildren().add(dto);
/*      */           continue;
/*      */         } 
/* 1158 */         ReportQueryWidgetDTO reportQueryWidgetDto = new ReportQueryWidgetDTO();
/* 1159 */         reportQueryWidgetDto
/* 1160 */           .getReportLineGraphBtDTOs()
/* 1161 */           .addAll(transferToReportLineGraphBtDTOs(n.getCharts()));
/* 1162 */         reportQueryWidgetDto.setQueryConditionWidgetName(n.getRipViewerClass());
/* 1163 */         dto.setReportQueryWidgetDTO(reportQueryWidgetDto);
/* 1164 */         dtos.add(dto);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1172 */       if (roadMapDto.getChildren().size() > 0) {
/* 1173 */         dtos.add(roadMapDto);
/*      */       }
/* 1175 */     } catch (RuntimeException e) {
/* 1176 */       logger.warn("getReports failed! category:'{}', subCategory:'{}'", new Object[] { category, subCategory, e });
/* 1177 */       throw e;
/*      */     } 
/* 1179 */     return dtos;
/*      */   }
/*      */   
/*      */   private List<ReportLineGraphBtDTO> transferToReportLineGraphBtDTOs(Map<String, String> charts) {
/* 1183 */     List<ReportLineGraphBtDTO> resultList = new ArrayList<>();
/* 1184 */     for (Map.Entry<String, String> e : charts.entrySet()) {
/* 1185 */       ReportLineGraphBtDTO dto = new ReportLineGraphBtDTO();
/* 1186 */       dto.setLineGraphId(e.getKey());
/* 1187 */       dto.setDisplayName(e.getValue());
/* 1188 */       resultList.add(dto);
/*      */     } 
/* 1190 */     return resultList;
/*      */   }
/*      */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\restful\AoRptViewerRestServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */