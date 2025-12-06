/*      */ package com.hwacom.ngtms.ao.restful;
/*      */ 
/*      */ import com.hazelcast.core.IMap;
/*      */ import com.hazelcast.query.EntryObject;
/*      */ import com.hazelcast.query.Predicate;
/*      */ import com.hazelcast.query.PredicateBuilder;
/*      */ import com.hwacom.ngtms.ao.fm.hz.AoHzMap;
/*      */ import com.hwacom.ngtms.ao.fm.model.LifeFaceLockCardData;
/*      */ import com.hwacom.ngtms.ao.fm.model.LifeFaceLockCardLogData;
/*      */ import com.hwacom.ngtms.ao.fm.model.NcuCardReaderLogData;
/*      */ import com.hwacom.ngtms.ao.fm.model.NcuCardReaderMappingConfig;
/*      */ import com.hwacom.ngtms.ao.fm.repository.LifeFaceLockCardDataRepository;
/*      */ import com.hwacom.ngtms.ao.fm.repository.LifeFaceLockCardLogDataRepository;
/*      */ import com.hwacom.ngtms.ao.fm.repository.NcuCardReaderLogDataRepository;
/*      */ import com.hwacom.ngtms.ao.fm.service.HunDureNcuServiceImpl;
/*      */ import com.hwacom.ngtms.ao.shared.NCUCardData;
/*      */ import com.hwacom.ngtms.ao.shared.NCUDateTime;
/*      */ import com.hwacom.ngtms.ao.shared.dto.LifeFaceLockCardDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.NcuConfigDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomCardReaderLogQueryParamDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomNCUCardDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomNCUCardLogDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomNCUMessageDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomNCUStatusDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomNcuDeviceNameDTO;
/*      */ import com.hwacom.ngtms.ao.shared.dto.RoomPermissionDTO;
/*      */ import com.hwacom.ngtms.ao.util.TransferHelper;
/*      */ import com.hwacom.ngtms.base.crypto.TripleDESUtils;
/*      */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*      */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*      */ import com.hwacom.ngtms.base.oplog.shared.OperationItem;
/*      */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*      */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*      */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*      */ import com.hwacom.ngtms.c.fm.service.OpLogger;
/*      */ import com.hwacom.ngtms.c.restful.BaseRestful;
/*      */ import com.hwacom.ngtms.c.shared.SubSystem;
/*      */ import com.hwacom.ngtms.room.fm.hz.RoomHzMap;
/*      */ import com.hwacom.ngtms.room.fm.model.RoomCardConfig;
/*      */ import com.hwacom.ngtms.room.fm.model.RoomCardReaderMappingConfig;
/*      */ import com.hwacom.ngtms.room.fm.repository.RoomCardConfigRepository;
/*      */ import com.hwacom.ngtms.room.fm.repository.RoomCardReaderMappingConfigRepository;
/*      */ import com.hwacom.ngtms.room.shared.CardStatus;
/*      */ import com.hwacom.ngtms.room.shared.dto.RoomCardConfigDTO;
/*      */ import com.hwacom.ngtms.room.shared.dto.RoomCardIssueParam;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.time.ZonedDateTime;
/*      */ import java.time.temporal.ChronoUnit;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.stream.Collectors;
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
/*      */ import org.springframework.web.bind.annotation.RestController;
/*      */ 
/*      */ @CrossOrigin
/*      */ @RestController
/*      */ @RequestMapping({"/api/ao/ncu"})
/*      */ public class AoNcuViewerRestServiceImpl extends BaseRestful {
/*   78 */   private static final Logger logger = LoggerFactory.getLogger(AoNcuViewerRestServiceImpl.class);
/*      */   
/*      */   private static final String dateTimeFormat = "yyyyMMdd";
/*   81 */   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
/*      */   @Autowired
/*      */   ModelMapper modelmapper;
/*      */   @Autowired
/*      */   OpLogger opLogger;
/*      */   @Autowired
/*      */   HunDureNcuServiceImpl hunDureNcuServiceImpl;
/*      */   @Autowired
/*      */   RoomCardConfigRepository roomCardConfigRepository;
/*      */   @Autowired
/*      */   NcuCardReaderLogDataRepository ncuCardReadeLogDataRepository;
/*      */   @Autowired
/*      */   RoomCardReaderMappingConfigRepository roomCardReaderMappingConfigRepository;
/*      */   @Autowired
/*      */   LifeFaceLockCardDataRepository lifeFaceLockCardDataRepository;
/*      */   @Autowired
/*      */   LifeFaceLockCardLogDataRepository lifeFaceLockCardLogDataRepository;
/*      */   
/*      */   @PostConstruct
/*      */   public void init() {
/*  101 */     setSubSystem(SubSystem.ROOM);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/card/{deviceName:.+}"}, method = {RequestMethod.GET})
/*      */   public NCUCardData queryCard(@PathVariable("deviceName") String deviceName, @RequestBody NCUCardData cardData) {
/*      */     try {
/*  109 */       logger.debug("Query Card = '{}'", deviceName);
/*  110 */       return this.hunDureNcuServiceImpl.queryCard(deviceName, cardData);
/*  111 */     } catch (Exception e) {
/*  112 */       logger.error("Query Card failed.");
/*  113 */       return new NCUCardData();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/card/{deviceName:.+}"}, method = {RequestMethod.POST})
/*      */   public int addCard(@PathVariable("deviceName") String deviceName, @RequestBody NCUCardData cardData) {
/*      */     try {
/*  122 */       logger.debug("Add Card = '{}'", deviceName);
/*  123 */       return this.hunDureNcuServiceImpl.addCard(deviceName, cardData);
/*  124 */     } catch (Exception e) {
/*  125 */       logger.error("Add Card failed.");
/*  126 */       return -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/card/{deviceName:.+}"}, method = {RequestMethod.PUT})
/*      */   public int modifyCard(@PathVariable("deviceName") String deviceName, @RequestBody NCUCardData cardData) {
/*      */     try {
/*  135 */       logger.debug("Modify Card = '{}'", deviceName);
/*  136 */       return this.hunDureNcuServiceImpl.modifyCard(deviceName, cardData);
/*  137 */     } catch (Exception e) {
/*  138 */       logger.error("Modify Card failed.");
/*  139 */       return -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/card/{deviceName:.+}"}, method = {RequestMethod.DELETE})
/*      */   public int deleteCard(@PathVariable("deviceName") String deviceName, @RequestBody NCUCardData cardData) {
/*      */     try {
/*  148 */       logger.debug("Delete Card = '{}'", deviceName);
/*  149 */       return this.hunDureNcuServiceImpl.deleteCard(deviceName, cardData);
/*  150 */     } catch (Exception e) {
/*  151 */       logger.error("Delete Card failed.");
/*  152 */       return -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/allCard/{deviceName:.+}"}, method = {RequestMethod.DELETE})
/*      */   public Boolean deleteAllCard(@PathVariable("deviceName") String deviceName) {
/*      */     try {
/*  160 */       logger.debug("DELETE All Card = '{}'", deviceName);
/*  161 */       return this.hunDureNcuServiceImpl.deleteAllCards(deviceName);
/*  162 */     } catch (Exception e) {
/*  163 */       logger.error("DELETE All Card.");
/*  164 */       return Boolean.FALSE;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/openDoor/{deviceName:.+}"}, method = {RequestMethod.PUT})
/*      */   public Boolean openDoor(@PathVariable String deviceName, @RequestBody RoomNCUStatusDTO dto, HttpServletRequest request) {
/*      */     try {
/*  175 */       logger.debug("Open Door cardReader = '{}'", dto.getId());
/*  176 */       if (dto.getId() != null) {
/*      */         
/*  178 */         IMap<String, NcuCardReaderMappingConfig> cardReaderMappingMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.NcuCardReaderMappingConfig);
/*      */         
/*  180 */         PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("controlId").equal(dto.getId());
/*  181 */         NcuCardReaderMappingConfig config = cardReaderMappingMap.values((Predicate)pb).iterator().next();
/*  182 */         return this.hunDureNcuServiceImpl.openDoor(config.getNcuId(), config.getDeviceId(), request);
/*      */       } 
/*  184 */       logger.warn("Open Door cardReader is null.");
/*  185 */       return Boolean.FALSE;
/*      */     }
/*  187 */     catch (Exception e) {
/*  188 */       logger.error("Open Door failed.");
/*  189 */       return Boolean.FALSE;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/configData"}, method = {RequestMethod.GET})
/*      */   public List<NcuConfigDTO> getNcuConfig() {
/*      */     try {
/*  197 */       logger.debug("Get NCU Config");
/*  198 */       List<NcuConfigDTO> result = new ArrayList<>();
/*  199 */       IMap<String, DeviceTcConfig> deviceTcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  200 */       PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceType").equal("NCU");
/*  201 */       for (DeviceTcConfig config : deviceTcConfigMap.values((Predicate)pb)) {
/*  202 */         NcuConfigDTO dto = new NcuConfigDTO();
/*  203 */         dto.setId(config.getDeviceName());
/*  204 */         dto.setName(config.getDisplayName());
/*  205 */         dto.setEnable(config.getEnable().booleanValue());
/*  206 */         dto.setIp(config.getIp());
/*  207 */         dto.setPort(config.getPort().toString());
/*  208 */         dto.setLatitude(config.getLatitude());
/*  209 */         dto.setLongitude(config.getLongitude());
/*  210 */         dto.setAlarmTime(Integer.valueOf(config.getMemo()));
/*  211 */         result.add(dto);
/*      */       } 
/*  213 */       return result;
/*  214 */     } catch (Exception e) {
/*  215 */       logger.error("Get NCU Config failed.");
/*  216 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/configData"}, method = {RequestMethod.POST})
/*      */   public Boolean addNcuConfig(@RequestBody NcuConfigDTO dto) {
/*      */     try {
/*  224 */       logger.debug("Add NCU Config");
/*  225 */       IMap<String, DeviceTcConfig> deviceTcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  226 */       if (dto != null) {
/*  227 */         DeviceTcConfig config = new DeviceTcConfig();
/*  228 */         config.setDeviceName(dto.getId());
/*  229 */         config.setDisplayName(dto.getName());
/*  230 */         config.setIp(dto.getIp());
/*  231 */         config.setPort(Integer.valueOf(dto.getPort()));
/*  232 */         config.setLatitude(dto.getLatitude());
/*  233 */         config.setLongitude(dto.getLongitude());
/*  234 */         config.setDeviceType("NCU");
/*  235 */         config.setProject("ao");
/*  236 */         config.setMemo(dto.getAlarmTime().toString());
/*  237 */         deviceTcConfigMap.put(config.getDeviceName(), config);
/*      */       } 
/*  239 */       return Boolean.TRUE;
/*  240 */     } catch (Exception e) {
/*  241 */       logger.error("Add NCU Config failed.");
/*  242 */       return Boolean.FALSE;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/configData"}, method = {RequestMethod.PUT})
/*      */   public Boolean updateNcuConfig(@RequestBody NcuConfigDTO dto) {
/*      */     try {
/*  250 */       logger.debug("Update NCU Config");
/*  251 */       IMap<String, DeviceTcConfig> deviceTcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  252 */       if (dto != null && 
/*  253 */         deviceTcConfigMap.containsKey(dto.getId())) {
/*  254 */         DeviceTcConfig config = (DeviceTcConfig)deviceTcConfigMap.get(dto.getId());
/*  255 */         config.setDisplayName(dto.getName());
/*  256 */         config.setIp(dto.getIp());
/*  257 */         config.setPort(Integer.valueOf(dto.getPort()));
/*  258 */         config.setLatitude(dto.getLatitude());
/*  259 */         config.setLongitude(dto.getLongitude());
/*  260 */         config.setMemo(dto.getAlarmTime().toString());
/*  261 */         deviceTcConfigMap.put(config.getDeviceName(), config);
/*      */       } 
/*      */       
/*  264 */       return Boolean.TRUE;
/*  265 */     } catch (Exception e) {
/*  266 */       logger.error("Update NCU Config failed.");
/*  267 */       return Boolean.FALSE;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/configData/{deviceName:.+}"}, method = {RequestMethod.DELETE})
/*      */   public Boolean deleteNcuConfig(@PathVariable("deviceName") String deviceName) {
/*      */     try {
/*  275 */       logger.debug("Delete NCU Config = '{}'", deviceName);
/*  276 */       IMap<String, DeviceTcConfig> deviceTcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  277 */       if (deviceName != null && 
/*  278 */         deviceTcConfigMap.containsKey(deviceName)) {
/*  279 */         deviceTcConfigMap.remove(deviceName);
/*      */       }
/*      */       
/*  282 */       return Boolean.TRUE;
/*  283 */     } catch (Exception e) {
/*  284 */       logger.error("Delete NCU Config failed.");
/*  285 */       return Boolean.FALSE;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/ncuTreeData"}, method = {RequestMethod.GET})
/*      */   public List<NcuConfigDTO> getNcuTreeData() {
/*      */     try {
/*  293 */       logger.debug("Get NCU Tree Data");
/*  294 */       List<NcuConfigDTO> result = new ArrayList<>();
/*  295 */       IMap<String, DeviceTcConfig> deviceTcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  296 */       PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceType").equal("NCU");
/*  297 */       for (DeviceTcConfig config : deviceTcConfigMap.values((Predicate)pb)) {
/*  298 */         NcuConfigDTO dto = new NcuConfigDTO();
/*  299 */         dto.setId(config.getDeviceName());
/*  300 */         dto.setName(config.getDisplayName());
/*  301 */         dto.setMileage(config.getMilepost());
/*  302 */         dto.setLineName(TransferHelper.getLineName(config.getLineId()));
/*  303 */         result.add(dto);
/*      */       } 
/*      */       
/*  306 */       Comparator<NcuConfigDTO> byMileage = Comparator.comparing(NcuConfigDTO::getMileage);
/*      */       
/*  308 */       Comparator<NcuConfigDTO> byname = Comparator.comparing(NcuConfigDTO::getName);
/*      */       
/*  310 */       result.sort(byMileage.thenComparing(byname));
/*  311 */       return result;
/*  312 */     } catch (Exception e) {
/*  313 */       logger.error("Get NCU Tree Data failed.");
/*  314 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/roomCardConfig"}, method = {RequestMethod.POST})
/*      */   public Boolean createRoomCardConfig(@RequestBody RoomCardConfigDTO dto) {
/*      */     try {
/*  322 */       logger.debug("createRoomCardConfig.");
/*  323 */       if (dto == null) {
/*  324 */         logger.error("createRoomCardConfig failed, RoomCardConfigDTO is null");
/*  325 */         return Boolean.valueOf(false);
/*      */       } 
/*  327 */       if (dto.getAba() == null) {
/*  328 */         logger.error("createRoomCardConfig failed, aba is null");
/*  329 */         return Boolean.valueOf(false);
/*      */       } 
/*  331 */       IMap<String, RoomCardConfig> roomCardConfigMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardConfig);
/*  332 */       RoomCardConfig cardConfig = new RoomCardConfig();
/*  333 */       String cUUId = UUID.randomUUID().toString();
/*  334 */       cardConfig.setId(cUUId);
/*  335 */       cardConfig.setCardStatus(CardStatus.DISABLE);
/*  336 */       cardConfig.setAba(dto.getAba());
/*  337 */       cardConfig.setIssued(Boolean.valueOf(false));
/*  338 */       RoomCardConfig saveConfig = (RoomCardConfig)this.roomCardConfigRepository.save(cardConfig);
/*  339 */       roomCardConfigMap.put(saveConfig.getId(), saveConfig);
/*  340 */       return Boolean.valueOf(true);
/*  341 */     } catch (Exception e) {
/*  342 */       logger.error("create RoomCardConfig failed.", e);
/*  343 */       return Boolean.valueOf(false);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/cardConfig"}, method = {RequestMethod.POST})
/*      */   public Boolean saveCardConfig(HttpServletRequest req, @RequestBody RoomCardIssueParam param) {
/*      */     try {
/*  351 */       logger.debug("Save NCU Card Config");
/*  352 */       RoomCardConfigDTO dto = param.getRoomCardConfigDto();
/*  353 */       if (dto.getId() == null) {
/*  354 */         logger.error("Save NCU Card Config failed, id = null");
/*  355 */         return null;
/*      */       } 
/*  357 */       if (dto.getAba() == null || dto.getAba().length() != 10) {
/*  358 */         logger.error("Save NCU Card Config failed, cardNo:'{}'", dto.getAba());
/*  359 */         return null;
/*      */       } 
/*  361 */       String userId = TripleDESUtils.decrypt(req.getHeader("encryptedUserLogin"));
/*  362 */       IMap<String, RoomCardConfig> roomCardConfigMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardConfig);
/*      */       
/*  364 */       IMap<String, RoomCardReaderMappingConfig> cardMappingMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardReaderMappingConfig);
/*      */ 
/*      */       
/*  367 */       CardStatus oldSatus = null;
/*  368 */       CardStatus newStatus = dto.getCardStatus();
/*  369 */       boolean clearSucc = false;
/*  370 */       if (roomCardConfigMap.containsKey(dto.getId())) {
/*  371 */         oldSatus = ((RoomCardConfig)roomCardConfigMap.get(dto.getId())).getCardStatus();
/*  372 */         if (oldSatus.equals(CardStatus.ENABLE) && newStatus
/*  373 */           .equals(CardStatus.DISABLE) && dto
/*  374 */           .getIssued().booleanValue() && dto
/*  375 */           .getIssueDate() != null) {
/*      */           
/*  377 */           EntryObject deleteCardEo = (new PredicateBuilder()).getEntryObject();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  382 */           PredicateBuilder deleteCardPd = deleteCardEo.get("cardId").equal(dto.getAba()).and((Predicate)deleteCardEo.get("loginCardReader").equal(Boolean.valueOf(true)));
/*  383 */           NCUCardData cardData = new NCUCardData();
/*  384 */           cardData.setcCardNo(dto.getAba());
/*  385 */           for (RoomCardReaderMappingConfig mapping : cardMappingMap.values((Predicate)deleteCardPd)) {
/*      */             try {
/*  387 */               int deleteResult = this.hunDureNcuServiceImpl.deleteCard(mapping.getReaderId(), cardData);
/*  388 */               if (deleteResult == 0 || deleteResult == 6) {
/*      */                 
/*  390 */                 cardMappingMap.delete(mapping.getId());
/*  391 */                 clearSucc = true;
/*  392 */                 this.opLogger.addLog(userId, "", SubSystem.ROOM, OperationItem.SET, mapping
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*  397 */                     .getReaderId(), new Date(), OperationResult.SUCCESS, "NotToCardReader", "room.deleteCardPermission", new Object[] { dto
/*      */ 
/*      */ 
/*      */ 
/*      */                       
/*  402 */                       .getAba() });
/*      */                 continue;
/*      */               } 
/*  405 */               clearSucc = false;
/*  406 */               this.opLogger.addLog(userId, "", SubSystem.ROOM, OperationItem.SET, mapping
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  411 */                   .getReaderId(), new Date(), OperationResult.FAILURE, "NotToCardReader", "room.deleteCardPermission", new Object[] { dto
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*  416 */                     .getAba() });
/*      */             }
/*  418 */             catch (Exception e) {
/*  419 */               logger.error("Delete NCU Card permission failed.", e);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  424 */       RoomCardConfig roomCardConfig = (RoomCardConfig)this.modelmapper.map(dto, RoomCardConfig.class);
/*  425 */       if (clearSucc) {
/*  426 */         roomCardConfig.setIssued(Boolean.valueOf(false));
/*  427 */         roomCardConfig.setIssueDate(null);
/*      */       } 
/*  429 */       roomCardConfigMap.put(roomCardConfig.getId(), roomCardConfig);
/*  430 */       return Boolean.TRUE;
/*  431 */     } catch (Exception e) {
/*  432 */       logger.error("Save NCU Card Config failed.", e);
/*  433 */       return Boolean.FALSE;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/cardPermission"}, method = {RequestMethod.POST})
/*      */   public RoomNCUMessageDTO addCardPermission(@RequestBody RoomNCUCardDTO dto, HttpServletRequest req) {
/*      */     try {
/*  442 */       logger.debug("Add NCU Card Permission.");
/*  443 */       IMap<String, DeviceTcConfig> deviceTcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  444 */       IMap<String, RoomCardConfig> roomCardConfigMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardConfig);
/*      */       
/*  446 */       IMap<String, RoomCardReaderMappingConfig> cardMappingMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardReaderMappingConfig);
/*      */       
/*  448 */       String userId = TripleDESUtils.decrypt(req.getHeader("encryptedUserLogin"));
/*      */       
/*  450 */       List<String> cardNos = dto.getCardNos();
/*  451 */       String[] cardArray = cardNos.<String>toArray(new String[cardNos.size()]);
/*      */       
/*  453 */       List<String> addCardDeviceLsit = new ArrayList<>();
/*      */       
/*  455 */       List<String> unNeedCardDeviceLsit = new ArrayList<>();
/*      */       
/*  457 */       Set<String> successDevices = new HashSet<>();
/*      */       
/*  459 */       Set<String> failDevices = new HashSet<>();
/*      */       
/*  461 */       Set<String> upsuccDevices = new HashSet<>();
/*      */       
/*  463 */       Set<String> upfailedDevices = new HashSet<>();
/*      */       
/*  465 */       Map<String, RoomCardReaderMappingConfig> cardsMap = new HashMap<>();
/*  466 */       Map<String, RoomCardConfig> roomCardMap = new HashMap<>();
/*      */       
/*  468 */       for (Map.Entry<String, Boolean> entry : (Iterable<Map.Entry<String, Boolean>>)dto.getCardReaderAddCardMap().entrySet()) {
/*  469 */         String deviceName = entry.getKey();
/*  470 */         if (((Boolean)entry.getValue()).booleanValue()) {
/*  471 */           addCardDeviceLsit.add(deviceName); continue;
/*      */         } 
/*  473 */         unNeedCardDeviceLsit.add(deviceName);
/*      */       } 
/*      */ 
/*      */       
/*  477 */       for (String ncuDeviceName : addCardDeviceLsit) {
/*  478 */         EntryObject cardEo = (new PredicateBuilder()).getEntryObject();
/*      */         
/*  480 */         PredicateBuilder cardPd = cardEo.get("cardStatus").equal((Comparable)CardStatus.ENABLE).and((Predicate)cardEo.get("aba").in((Comparable[])cardArray));
/*      */         
/*  482 */         if (dto.isUpDateCard()) {
/*  483 */           for (RoomCardConfig cardConfig : roomCardConfigMap.values((Predicate)cardPd)) {
/*  484 */             String cardNo = cardConfig.getAba();
/*      */             try {
/*  486 */               NCUCardData cardData = new NCUCardData();
/*  487 */               cardData.setcCardNo(cardNo);
/*  488 */               cardData.setcStartDate(this.dateFormat.format(dto.getCardStartDate()));
/*  489 */               cardData.setcEndDate(this.dateFormat.format(dto.getCardEndDate()));
/*  490 */               cardData.setAccessControlDoor("FFFF");
/*      */               
/*  492 */               if (ncuDeviceName.equals("NCU_4"))
/*      */               {
/*  494 */                 if (dto.isAccessControlDoor()) {
/*  495 */                   cardData.setAccessControlDoor("0000");
/*      */                 } else {
/*  497 */                   cardData.setAccessControlDoor("FFFF");
/*      */                 } 
/*      */               }
/*  500 */               int addResult = this.hunDureNcuServiceImpl.modifyCard(ncuDeviceName, cardData);
/*  501 */               if (addResult == 0 || addResult == 4) {
/*  502 */                 upsuccDevices.add(ncuDeviceName);
/*  503 */                 EntryObject cardMp = (new PredicateBuilder()).getEntryObject();
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  508 */                 PredicateBuilder cardMappingPb = cardMp.get("readerId").equal(ncuDeviceName).and((Predicate)cardMp.get("cardId").equal(cardNo));
/*      */                 
/*  510 */                 Iterator<RoomCardReaderMappingConfig> cardValue = cardMappingMap.values((Predicate)cardMappingPb).iterator();
/*      */                 
/*  512 */                 if (!cardValue.hasNext()) {
/*  513 */                   RoomCardReaderMappingConfig cardMapping = new RoomCardReaderMappingConfig();
/*  514 */                   String cUUID = UUID.randomUUID().toString();
/*  515 */                   cardMapping.setId(cUUID);
/*  516 */                   cardMapping.setCardId(cardNo);
/*  517 */                   cardMapping.setReaderId(ncuDeviceName);
/*  518 */                   cardMapping.setLoginCardReader(Boolean.valueOf(true));
/*  519 */                   cardsMap.put(cUUID, cardMapping);
/*      */                 } 
/*  521 */                 cardConfig.setIssued(Boolean.valueOf(true));
/*  522 */                 cardConfig.setIssueDate(new Date());
/*  523 */                 cardConfig.setCardStartDate(dto.getCardStartDate());
/*  524 */                 cardConfig.setCardEndDate(dto.getCardEndDate());
/*  525 */                 roomCardMap.put(cardConfig.getId(), cardConfig);
/*  526 */                 this.opLogger.addLog(userId, "", SubSystem.ROOM, OperationItem.SET, ncuDeviceName, new Date(), OperationResult.SUCCESS, "ToCardReader", "room.upDateCardPermission", new Object[] { cardNo });
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               }
/*      */               else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  538 */                 upfailedDevices.add(ncuDeviceName + "-" + cardNo);
/*      */               } 
/*  540 */               Thread.sleep(30L);
/*  541 */             } catch (Exception e) {
/*      */               
/*  543 */               this.opLogger.addLog(userId, "", SubSystem.ROOM, OperationItem.SET, ncuDeviceName, new Date(), OperationResult.FAILURE, "ToCardReader.", "room.upDateCardPermission", new Object[] { cardNo });
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
/*  554 */               logger.error("upDate permission failed, cardNo = '{}'", cardNo, e);
/*      */             } 
/*      */           }  continue;
/*      */         } 
/*  558 */         for (RoomCardConfig cardConfig : roomCardConfigMap.values((Predicate)cardPd)) {
/*  559 */           String cardNo = cardConfig.getAba();
/*      */           try {
/*  561 */             NCUCardData cardData = new NCUCardData();
/*  562 */             cardData.setcCardNo(cardNo);
/*  563 */             cardData.setcStartDate(this.dateFormat.format(cardConfig.getCardStartDate()));
/*  564 */             cardData.setcEndDate(this.dateFormat.format(cardConfig.getCardEndDate()));
/*  565 */             cardData.setAccessControlDoor("FFFF");
/*      */             
/*  567 */             if (ncuDeviceName.equals("NCU_4"))
/*      */             {
/*  569 */               if (dto.isAccessControlDoor()) {
/*  570 */                 cardData.setAccessControlDoor("0000");
/*      */               } else {
/*  572 */                 cardData.setAccessControlDoor("FFFF");
/*      */               } 
/*      */             }
/*  575 */             int addResult = this.hunDureNcuServiceImpl.addCard(ncuDeviceName, cardData);
/*  576 */             if (addResult == 0 || addResult == 4) {
/*  577 */               successDevices.add(ncuDeviceName);
/*  578 */               EntryObject cardMp = (new PredicateBuilder()).getEntryObject();
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  583 */               PredicateBuilder cardMappingPb = cardMp.get("readerId").equal(ncuDeviceName).and((Predicate)cardMp.get("cardId").equal(cardNo));
/*      */               
/*  585 */               Iterator<RoomCardReaderMappingConfig> cardValue = cardMappingMap.values((Predicate)cardMappingPb).iterator();
/*      */               
/*  587 */               if (!cardValue.hasNext()) {
/*  588 */                 RoomCardReaderMappingConfig cardMapping = new RoomCardReaderMappingConfig();
/*  589 */                 String cUUID = UUID.randomUUID().toString();
/*  590 */                 cardMapping.setId(cUUID);
/*  591 */                 cardMapping.setCardId(cardNo);
/*  592 */                 cardMapping.setReaderId(ncuDeviceName);
/*  593 */                 cardMapping.setLoginCardReader(Boolean.valueOf(true));
/*  594 */                 cardsMap.put(cUUID, cardMapping);
/*      */               } 
/*  596 */               cardConfig.setIssued(Boolean.valueOf(true));
/*  597 */               cardConfig.setIssueDate(new Date());
/*  598 */               roomCardMap.put(cardConfig.getId(), cardConfig);
/*  599 */               this.opLogger.addLog(userId, "", SubSystem.ROOM, OperationItem.SET, ncuDeviceName, new Date(), OperationResult.SUCCESS, "ToCardReader", "room.addCardPermission", new Object[] { cardNo });
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  611 */               failDevices.add(ncuDeviceName + "-" + cardNo);
/*      */             } 
/*  613 */             Thread.sleep(30L);
/*  614 */           } catch (Exception e) {
/*      */             
/*  616 */             this.opLogger.addLog(userId, "", SubSystem.ROOM, OperationItem.SET, ncuDeviceName, new Date(), OperationResult.FAILURE, "ToCardReader", "room.addCardPermission", new Object[] { cardNo });
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
/*  627 */             logger.error("Add permission failed, cardNo = '{}'", cardNo, e);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  633 */       for (String ncuDeviceName : unNeedCardDeviceLsit) {
/*  634 */         EntryObject cardEo = (new PredicateBuilder()).getEntryObject();
/*      */         
/*  636 */         PredicateBuilder cardMappingPb = cardEo.get("readerId").equal(ncuDeviceName).and((Predicate)cardEo.get("cardId").in((Comparable[])cardArray));
/*  637 */         for (RoomCardReaderMappingConfig mappingConig : cardMappingMap.values((Predicate)cardMappingPb)) {
/*      */           try {
/*  639 */             NCUCardData cardData = new NCUCardData();
/*  640 */             cardData.setcCardNo(mappingConig.getCardId());
/*      */             
/*  642 */             int deleteResult = this.hunDureNcuServiceImpl.deleteCard(mappingConig.getReaderId(), cardData);
/*  643 */             if (deleteResult == 0 || deleteResult == 6) {
/*  644 */               cardMappingMap.delete(mappingConig.getId());
/*  645 */               this.opLogger.addLog(userId, "", SubSystem.ROOM, OperationItem.SET, mappingConig
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  650 */                   .getReaderId(), new Date(), OperationResult.SUCCESS, "NotToCardReader", "room.deleteCardPermission", new Object[] { mappingConig
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*  655 */                     .getCardId() });
/*      */               continue;
/*      */             } 
/*  658 */             this.opLogger.addLog(userId, "", SubSystem.ROOM, OperationItem.SET, mappingConig
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  663 */                 .getReaderId(), new Date(), OperationResult.FAILURE, "NotToCardReader", "room.deleteCardPermission", new Object[] { mappingConig
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  668 */                   .getCardId() });
/*      */           }
/*  670 */           catch (Exception e) {
/*  671 */             logger.error("Delete NCU Card permission failed.", e);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  677 */       List<RoomCardReaderMappingConfig> saveConfigList = this.roomCardReaderMappingConfigRepository.saveAll(cardsMap.values());
/*  678 */       Map<String, RoomCardReaderMappingConfig> mappingConfigMap = new HashMap<>();
/*  679 */       for (RoomCardReaderMappingConfig config : saveConfigList) {
/*  680 */         mappingConfigMap.put(config.getId(), config);
/*      */       }
/*  682 */       cardMappingMap.putAll(mappingConfigMap);
/*  683 */       roomCardConfigMap.putAll(roomCardMap);
/*      */       
/*  685 */       List<String> addSuccessDevices = new ArrayList<>();
/*  686 */       List<String> addFailDevices = new ArrayList<>();
/*  687 */       if (dto.isUpDateCard()) {
/*  688 */         addSuccessDevices = new ArrayList<>(upsuccDevices);
/*  689 */         addFailDevices = new ArrayList<>(upfailedDevices);
/*      */       } else {
/*  691 */         addSuccessDevices = new ArrayList<>(successDevices);
/*  692 */         addFailDevices = new ArrayList<>(failDevices);
/*      */       } 
/*  694 */       StringBuilder resultMsg1 = new StringBuilder();
/*      */       
/*  696 */       if (addSuccessDevices.size() > 0) {
/*  697 */         if (dto.isUpDateCard()) {
/*  698 */           resultMsg1.append("主機更新權限成功:");
/*      */         } else {
/*  700 */           resultMsg1.append("主機新增權限成功:");
/*      */         } 
/*  702 */         resultMsg1.append(System.lineSeparator());
/*  703 */         for (int i = 0; i < addSuccessDevices.size(); i++) {
/*  704 */           String displayName = ((DeviceTcConfig)deviceTcConfigMap.get(addSuccessDevices.get(i))).getDisplayName();
/*  705 */           if (i == addSuccessDevices.size() - 1) {
/*  706 */             resultMsg1.append(displayName + System.lineSeparator());
/*      */           } else {
/*  708 */             resultMsg1.append(displayName + ", ");
/*      */           } 
/*      */         } 
/*      */       } 
/*  712 */       StringBuilder resultMsg2 = new StringBuilder();
/*      */       
/*  714 */       if (addFailDevices.size() > 0) {
/*  715 */         if (dto.isUpDateCard()) {
/*  716 */           resultMsg2.append("主機更新權限失敗:");
/*      */         } else {
/*  718 */           resultMsg2.append("主機新增權限失敗:");
/*      */         } 
/*  720 */         resultMsg2.append(System.lineSeparator());
/*  721 */         for (int i = 0; i < addFailDevices.size(); i++) {
/*  722 */           String[] deviceNameSp = ((String)addFailDevices.get(i)).split("-");
/*      */           
/*  724 */           String displayName = ((DeviceTcConfig)deviceTcConfigMap.get(deviceNameSp[0])).getDisplayName() + "-" + deviceNameSp[1];
/*  725 */           if (i == addFailDevices.size() - 1) {
/*  726 */             resultMsg2.append(displayName + System.lineSeparator());
/*      */           } else {
/*  728 */             resultMsg2.append(displayName + ", ");
/*      */           } 
/*      */         } 
/*      */       } 
/*  732 */       RoomNCUMessageDTO messageDTO = new RoomNCUMessageDTO();
/*  733 */       messageDTO.setNcuMessage(resultMsg1.toString() + resultMsg2.toString());
/*  734 */       return messageDTO;
/*  735 */     } catch (Exception e) {
/*  736 */       logger.error("Add NCU Card Permissionfailed.", e);
/*  737 */       return new RoomNCUMessageDTO();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/ncu"}, method = {RequestMethod.GET})
/*      */   public List<RoomNcuDeviceNameDTO> getNcu() {
/*      */     try {
/*  745 */       List<RoomNcuDeviceNameDTO> result = new ArrayList<>();
/*  746 */       IMap<String, DeviceTcConfig> deviceTcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  747 */       PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceType").equal("NCU");
/*  748 */       for (DeviceTcConfig config : deviceTcConfigMap.values((Predicate)pb)) {
/*  749 */         RoomNcuDeviceNameDTO dto = new RoomNcuDeviceNameDTO();
/*  750 */         dto.setDeviceName(config.getDeviceName());
/*  751 */         dto.setDisplayName(config.getDisplayName());
/*  752 */         result.add(dto);
/*      */       } 
/*      */ 
/*      */       
/*  756 */       Comparator<RoomNcuDeviceNameDTO> byDisplayName = Comparator.comparing(RoomNcuDeviceNameDTO::getDisplayName);
/*  757 */       result.sort(byDisplayName);
/*  758 */       return result;
/*  759 */     } catch (Exception e) {
/*  760 */       logger.error("Get NCU failed.", e);
/*  761 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/nculocation/{deviceName:.+}"}, method = {RequestMethod.GET})
/*      */   public List<RoomNcuDeviceNameDTO> getlocationByNcu(@PathVariable String deviceName) {
/*      */     try {
/*  769 */       List<RoomNcuDeviceNameDTO> result = new ArrayList<>();
/*  770 */       IMap<String, DeviceTcConfig> deviceTcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*      */       
/*  772 */       IMap<String, NcuCardReaderMappingConfig> cardReaderMappingMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.NcuCardReaderMappingConfig);
/*  773 */       PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("ncuId").equal(deviceName);
/*  774 */       for (NcuCardReaderMappingConfig mappingConfig : cardReaderMappingMap.values((Predicate)pb)) {
/*  775 */         RoomNcuDeviceNameDTO dto = new RoomNcuDeviceNameDTO();
/*  776 */         dto.setDeviceName(mappingConfig.getControlId());
/*  777 */         DeviceTcConfig tcConfig = (DeviceTcConfig)deviceTcConfigMap.get(mappingConfig.getControlId());
/*  778 */         if (tcConfig != null) {
/*  779 */           dto.setDisplayName(tcConfig.getDisplayName());
/*      */         }
/*  781 */         result.add(dto);
/*      */       } 
/*      */ 
/*      */       
/*  785 */       Comparator<RoomNcuDeviceNameDTO> byDisplayName = Comparator.comparing(RoomNcuDeviceNameDTO::getDisplayName);
/*  786 */       result.sort(byDisplayName);
/*      */       
/*  788 */       RoomNcuDeviceNameDTO allDTO = new RoomNcuDeviceNameDTO();
/*  789 */       allDTO.setDeviceName("all");
/*  790 */       allDTO.setDisplayName("全部");
/*  791 */       result.add(0, allDTO);
/*  792 */       return result;
/*  793 */     } catch (Exception e) {
/*  794 */       logger.error("Get Location By Ncu failed.", e);
/*  795 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/ncuCardReaderLog"}, method = {RequestMethod.POST})
/*      */   public List<RoomNCUCardLogDTO> getNcuCardReaderLog(@RequestBody RoomCardReaderLogQueryParamDTO dto) {
/*      */     try {
/*  804 */       logger.debug("Get Ncu Card Reader Log Location = '{}'", dto.getLocation());
/*  805 */       List<RoomNCUCardLogDTO> result = new ArrayList<>();
/*  806 */       List<NcuCardReaderLogData> records = new ArrayList<>();
/*  807 */       IMap<String, DeviceTcConfig> deviceTcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*      */       
/*  809 */       IMap<String, NcuCardReaderMappingConfig> cardReaderMappingMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.NcuCardReaderMappingConfig);
/*  810 */       IMap<String, RoomCardConfig> roomCardConfigMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardConfig);
/*      */       
/*  812 */       if (dto.getLocation().equals("all")) {
/*      */         
/*  814 */         records = this.ncuCardReadeLogDataRepository.findByDataTimeBetweenAndNcuIdOrderByDataTimeDesc(dto
/*  815 */             .getStartTime(), dto.getEndTime(), dto.getNcnDeviceName());
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  820 */         PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("controlId").equal(dto.getLocation());
/*  821 */         NcuCardReaderMappingConfig config = cardReaderMappingMap.values((Predicate)pb).iterator().next();
/*      */ 
/*      */         
/*  824 */         records = this.ncuCardReadeLogDataRepository.findByDataTimeBetweenAndNcuIdAndDeviceIdOrderByDataTimeDesc(dto
/*  825 */             .getStartTime(), dto.getEndTime(), config.getNcuId(), config.getDeviceId());
/*      */       } 
/*  827 */       records.forEach(record -> {
/*      */             RoomNCUCardLogDTO logDTO = new RoomNCUCardLogDTO();
/*      */             
/*      */             logDTO.setId(record.getId());
/*      */             
/*      */             if (record.getEventCode() != null) {
/*      */               logDTO.setEventCode(record.getEventCode());
/*      */             } else {
/*      */               logDTO.setEventCode("");
/*      */             } 
/*      */             
/*      */             DeviceTcConfig tcConfig = (DeviceTcConfig)deviceTcConfigMap.get(record.getNcuId());
/*      */             
/*      */             logDTO.setDisplayName(tcConfig.getDisplayName());
/*      */             
/*      */             EntryObject eo = (new PredicateBuilder()).getEntryObject();
/*      */             
/*      */             PredicateBuilder pb1 = eo.get("deviceId").equal(record.getDeviceId()).and((Predicate)eo.get("ncuId").equal(record.getNcuId()));
/*      */             
/*      */             String cardReaderId = ((NcuCardReaderMappingConfig)cardReaderMappingMap.values((Predicate)pb1).iterator().next()).getControlId();
/*      */             DeviceTcConfig cardReader = (DeviceTcConfig)deviceTcConfigMap.get(cardReaderId);
/*      */             logDTO.setLocation(cardReader.getDisplayName());
/*      */             logDTO.setCardNumber(record.getCardNumber());
/*      */             PredicateBuilder cardPb = (new PredicateBuilder()).getEntryObject().get("aba").equal(record.getCardNumber());
/*      */             Iterator<RoomCardConfig> cardValue = roomCardConfigMap.values((Predicate)cardPb).iterator();
/*      */             if (cardValue.hasNext()) {
/*      */               RoomCardConfig cardConfig = cardValue.next();
/*      */               if (cardConfig != null) {
/*      */                 logDTO.setName(cardConfig.getName());
/*      */                 logDTO.setCompany(cardConfig.getCompany());
/*      */               } 
/*      */             } 
/*      */             logDTO.setTime(record.getDataTime());
/*      */             logDTO.setStatusCode(TransferHelper.resultToStatusCode(record.getStatusCode()));
/*      */             result.add(logDTO);
/*      */           });
/*  863 */       Comparator<RoomNCUCardLogDTO> byTime = Comparator.comparing(RoomNCUCardLogDTO::getTime);
/*  864 */       result.sort(byTime.reversed());
/*  865 */       return result;
/*  866 */     } catch (Exception e) {
/*  867 */       logger.error("Get Ncu Card Reader Log failed.", e);
/*  868 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/lockCard"}, method = {RequestMethod.GET})
/*      */   public List<LifeFaceLockCardDTO> getAllLockCards() {
/*      */     try {
/*  876 */       logger.debug("Get All Lock Card.");
/*      */       
/*  878 */       IMap<String, LifeFaceLockCardData> lifeFaceLockCardMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.LifeFaceLockCardData);
/*  879 */       IMap<String, DeviceTcConfig> deviceTcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  880 */       IMap<String, RoomCardConfig> roomCardConfigMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardConfig);
/*  881 */       List<LifeFaceLockCardDTO> result = new ArrayList<>();
/*  882 */       Map<String, List<String>> cardMap = new HashMap<>();
/*  883 */       List<String> cardNumbers = new ArrayList<>();
/*  884 */       ZonedDateTime now = ZonedDateTime.now();
/*  885 */       ZonedDateTime todayZero = now.truncatedTo(ChronoUnit.DAYS);
/*      */       
/*  887 */       Date from = Date.from(todayZero.toInstant());
/*  888 */       for (LifeFaceLockCardData data : lifeFaceLockCardMap.values()) {
/*      */         
/*  890 */         if (data.getDataTime().after(from)) {
/*  891 */           String spiltId = data.getId();
/*  892 */           String[] spiltData = spiltId.split("\\+");
/*  893 */           String id = spiltData[0] + "+" + spiltData[1];
/*  894 */           String cardNu = spiltData[2];
/*  895 */           if (cardMap.containsKey(id)) {
/*  896 */             cardNumbers = cardMap.get(id);
/*  897 */             cardNumbers.add(cardNu);
/*  898 */             cardMap.put(id, cardNumbers); continue;
/*      */           } 
/*  900 */           cardNumbers = new ArrayList<>();
/*  901 */           cardNumbers.add(cardNu);
/*  902 */           cardMap.put(id, cardNumbers);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  907 */       for (String data : cardMap.keySet()) {
/*  908 */         Set<String> lockPeople = new HashSet<>();
/*  909 */         LifeFaceLockCardDTO dto = new LifeFaceLockCardDTO();
/*  910 */         dto.setId(data);
/*  911 */         String[] value = data.split("\\+");
/*  912 */         dto.setLifeFaceTime(value[0]);
/*  913 */         DeviceTcConfig tcConfig = (DeviceTcConfig)deviceTcConfigMap.get(value[1]);
/*  914 */         if (tcConfig != null) {
/*  915 */           dto.setLifeFaceLocation(tcConfig.getDisplayName());
/*      */         }
/*  917 */         dto.setLockCards(cardMap.get(data));
/*  918 */         if (dto.getLockCards().size() > 0) {
/*  919 */           for (String cardId : dto.getLockCards()) {
/*  920 */             String keySet = data + "+" + cardId;
/*  921 */             if (lifeFaceLockCardMap.get(keySet) != null) {
/*  922 */               dto.setLockCard(((LifeFaceLockCardData)lifeFaceLockCardMap.get(keySet)).isLockCard());
/*      */             }
/*      */             
/*  925 */             PredicateBuilder cardPb = (new PredicateBuilder()).getEntryObject().get("aba").equal(cardId);
/*  926 */             Iterator<RoomCardConfig> cardValue = roomCardConfigMap.values((Predicate)cardPb).iterator();
/*  927 */             if (cardValue.hasNext()) {
/*  928 */               RoomCardConfig cardConfig = cardValue.next();
/*  929 */               if (cardConfig != null) {
/*  930 */                 lockPeople.add(cardConfig.getName());
/*      */               }
/*      */             } 
/*      */           } 
/*  934 */           dto.setLockPeople((List)lockPeople.stream().collect(Collectors.toList()));
/*      */         } 
/*  936 */         dto.setFaceMatch(false);
/*  937 */         result.add(dto);
/*      */       } 
/*  939 */       return result;
/*  940 */     } catch (Exception e) {
/*  941 */       logger.error("Get All Lock Card failed.", e);
/*  942 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/lockCard"}, method = {RequestMethod.POST})
/*      */   public List<LifeFaceLockCardDTO> getLockCards(@RequestBody RoomCardReaderLogQueryParamDTO dto) {
/*      */     try {
/*  950 */       logger.debug("Get Lock Cards.");
/*  951 */       IMap<String, DeviceTcConfig> deviceTcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  952 */       IMap<String, RoomCardConfig> roomCardConfigMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardConfig);
/*      */       
/*  954 */       List<LifeFaceLockCardLogData> records = this.lifeFaceLockCardLogDataRepository.findByDataTimeBetweenAndNcuIdOrderByDataTimeDesc(dto
/*  955 */           .getStartTime(), dto.getEndTime(), dto.getNcnDeviceName());
/*  956 */       List<LifeFaceLockCardDTO> result = new ArrayList<>();
/*  957 */       Map<String, List<String>> cardMap = new HashMap<>();
/*  958 */       Map<String, Boolean> abdMap = new HashMap<>();
/*  959 */       List<String> cardNumbers = new ArrayList<>();
/*  960 */       for (LifeFaceLockCardLogData data : records) {
/*  961 */         String id = data.getDataTime() + "+" + data.getNcuId();
/*  962 */         String cardNu = data.getLockCard();
/*  963 */         if (cardMap.containsKey(id)) {
/*  964 */           cardNumbers = cardMap.get(id);
/*  965 */           cardNumbers.add(cardNu);
/*  966 */           cardMap.put(id, cardNumbers);
/*  967 */           abdMap.put(cardNu, Boolean.valueOf(data.isLockCard())); continue;
/*      */         } 
/*  969 */         cardNumbers = new ArrayList<>();
/*  970 */         cardNumbers.add(cardNu);
/*  971 */         cardMap.put(id, cardNumbers);
/*  972 */         abdMap.put(cardNu, Boolean.valueOf(data.isLockCard()));
/*      */       } 
/*      */ 
/*      */       
/*  976 */       for (String data : cardMap.keySet()) {
/*  977 */         Set<String> lockPeople = new HashSet<>();
/*  978 */         LifeFaceLockCardDTO lockDto = new LifeFaceLockCardDTO();
/*  979 */         lockDto.setId(data);
/*  980 */         String[] value = data.split("\\+");
/*  981 */         lockDto.setLifeFaceTime(value[0]);
/*  982 */         DeviceTcConfig tcConfig = (DeviceTcConfig)deviceTcConfigMap.get(value[1]);
/*  983 */         if (tcConfig != null) {
/*  984 */           lockDto.setLifeFaceLocation(tcConfig.getDisplayName());
/*      */         }
/*  986 */         lockDto.setLockCards(cardMap.get(data));
/*      */         
/*  988 */         if (lockDto.getLockCards().size() > 0) {
/*  989 */           for (String cardId : lockDto.getLockCards()) {
/*  990 */             lockDto.setLockCard(((Boolean)abdMap.get(cardId)).booleanValue());
/*      */             
/*  992 */             PredicateBuilder cardPb = (new PredicateBuilder()).getEntryObject().get("aba").equal(cardId);
/*  993 */             Iterator<RoomCardConfig> cardValue = roomCardConfigMap.values((Predicate)cardPb).iterator();
/*  994 */             if (cardValue.hasNext()) {
/*  995 */               lockDto.setLockCard(false);
/*  996 */               RoomCardConfig cardConfig = cardValue.next();
/*  997 */               if (cardConfig != null) {
/*  998 */                 lockPeople.add(cardConfig.getName());
/*      */               }
/*      */             } 
/*      */           } 
/* 1002 */           lockDto.setLockPeople((List)lockPeople.stream().collect(Collectors.toList()));
/*      */         } 
/* 1004 */         lockDto.setFaceMatch(false);
/* 1005 */         result.add(lockDto);
/*      */       } 
/* 1007 */       return result;
/* 1008 */     } catch (Exception e) {
/* 1009 */       logger.error("Get Lock Cards failed.", e);
/* 1010 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/lockCard"}, method = {RequestMethod.PUT})
/*      */   public Boolean setLockCard(HttpServletRequest req, @RequestBody LifeFaceLockCardDTO dto) {
/*      */     try {
/* 1018 */       logger.debug("Set Lock Card.");
/* 1019 */       if (dto == null) {
/* 1020 */         logger.error("LifeFaceLockCardDTO dto is NULL.");
/* 1021 */         return Boolean.FALSE;
/*      */       } 
/* 1023 */       if (dto.getLockCards().size() < 1) {
/* 1024 */         logger.error("LifeFaceLockCardDTO LockCards No Values.");
/* 1025 */         return Boolean.FALSE;
/*      */       } 
/*      */       
/* 1028 */       IMap<String, LifeFaceLockCardData> lifeFaceLockCardMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.LifeFaceLockCardData);
/*      */       
/* 1030 */       IMap<String, RoomCardReaderMappingConfig> cardMappingMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardReaderMappingConfig);
/*      */       
/* 1032 */       boolean lock = false;
/*      */       
/* 1034 */       String userId = TripleDESUtils.decrypt(req.getHeader("encryptedUserLogin"));
/* 1035 */       String spiltId = dto.getId();
/* 1036 */       String[] spiltData = spiltId.split("\\+");
/* 1037 */       String id = spiltData[1];
/* 1038 */       List<String> cardNus = dto.getLockCards();
/* 1039 */       String[] cardArray = cardNus.<String>toArray(new String[cardNus.size()]);
/* 1040 */       EntryObject deleteCardEo = (new PredicateBuilder()).getEntryObject();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1046 */       PredicateBuilder deleteCardPd = deleteCardEo.get("loginCardReader").equal(Boolean.valueOf(true)).and((Predicate)deleteCardEo.get("readerId").equal(id)).and((Predicate)deleteCardEo.get("cardId").in((Comparable[])cardArray));
/* 1047 */       logger.debug("Set Lock CardMap Size = '{}'", Integer.valueOf(cardMappingMap.values((Predicate)deleteCardPd).size()));
/* 1048 */       for (RoomCardReaderMappingConfig mapping : cardMappingMap.values((Predicate)deleteCardPd)) {
/*      */         try {
/* 1050 */           NCUCardData cardData = new NCUCardData();
/* 1051 */           cardData.setcCardNo(mapping.getCardId());
/* 1052 */           int deleteResult = this.hunDureNcuServiceImpl.deleteCard(id, cardData);
/* 1053 */           if (deleteResult == 0 || deleteResult == 6) {
/*      */             
/* 1055 */             cardMappingMap.delete(mapping.getId());
/* 1056 */             this.opLogger.addLog(userId, "", SubSystem.ROOM, OperationItem.SET, mapping
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1061 */                 .getReaderId(), new Date(), OperationResult.SUCCESS, "NotToCardReader", "room.deleteCardPermission", new Object[] { mapping
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1066 */                   .getCardId() });
/*      */           } else {
/*      */             
/* 1069 */             this.opLogger.addLog(userId, "", SubSystem.ROOM, OperationItem.SET, mapping
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1074 */                 .getReaderId(), new Date(), OperationResult.FAILURE, "NotToCardReader", "room.deleteCardPermission", new Object[] { mapping
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1079 */                   .getCardId() });
/*      */           } 
/* 1081 */           Thread.sleep(20L);
/* 1082 */         } catch (Exception e) {
/* 1083 */           lock = true;
/* 1084 */           logger.error("Set Lock Card = '{}' failed.", mapping.getCardId());
/*      */         } 
/*      */       } 
/* 1087 */       if (!lock) {
/* 1088 */         for (String cardNu : cardNus) {
/*      */           
/* 1090 */           List<LifeFaceLockCardLogData> refreshRecords = this.lifeFaceLockCardLogDataRepository.findByNcuIdAndLockCard(id, cardNu);
/* 1091 */           if (refreshRecords.size() > 0) {
/* 1092 */             this.lifeFaceLockCardLogDataRepository.updateIsLock(true, id, cardNu);
/*      */           }
/* 1094 */           String keyValue = spiltId + "+" + cardNu;
/* 1095 */           if (lifeFaceLockCardMap.containsKey(keyValue)) {
/* 1096 */             LifeFaceLockCardData data = (LifeFaceLockCardData)lifeFaceLockCardMap.get(keyValue);
/* 1097 */             data.setLockCard(true);
/* 1098 */             lifeFaceLockCardMap.put(keyValue, data);
/*      */           } 
/*      */         } 
/* 1101 */         return Boolean.TRUE;
/*      */       } 
/* 1103 */       return Boolean.FALSE;
/* 1104 */     } catch (Exception e) {
/* 1105 */       logger.error("Set Lock Card failed.", e);
/* 1106 */       return Boolean.FALSE;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/lockCard"}, method = {RequestMethod.DELETE})
/*      */   public Boolean setUnLockCard(HttpServletRequest req, @RequestBody LifeFaceLockCardDTO dto) {
/*      */     try {
/* 1114 */       logger.debug("Set UnLock Card.");
/* 1115 */       if (dto == null) {
/* 1116 */         logger.error("LifeFaceLockCardDTO dto is NULL.");
/* 1117 */         return Boolean.FALSE;
/*      */       } 
/* 1119 */       if (dto.getLockCards().size() < 1) {
/* 1120 */         logger.error("LifeFaceLockCardDTO LockCards No Values.");
/* 1121 */         return Boolean.FALSE;
/*      */       } 
/*      */       
/* 1124 */       IMap<String, LifeFaceLockCardData> lifeFaceLockCardMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.LifeFaceLockCardData);
/* 1125 */       IMap<String, RoomCardConfig> roomCardConfigMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardConfig);
/*      */       
/* 1127 */       IMap<String, RoomCardReaderMappingConfig> cardMappingMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardReaderMappingConfig);
/* 1128 */       Map<String, RoomCardReaderMappingConfig> cardsMap = new HashMap<>();
/*      */       
/* 1130 */       boolean unlock = false;
/*      */       
/* 1132 */       String userId = TripleDESUtils.decrypt(req.getHeader("encryptedUserLogin"));
/* 1133 */       String spiltId = dto.getId();
/* 1134 */       String[] spiltData = spiltId.split("\\+");
/* 1135 */       String id = spiltData[1];
/* 1136 */       List<String> cardNus = dto.getLockCards();
/* 1137 */       String[] cardArray = cardNus.<String>toArray(new String[cardNus.size()]);
/* 1138 */       EntryObject cardEo = (new PredicateBuilder()).getEntryObject();
/*      */       
/* 1140 */       PredicateBuilder cardPd = cardEo.get("cardStatus").equal((Comparable)CardStatus.ENABLE).and((Predicate)cardEo.get("aba").in((Comparable[])cardArray));
/* 1141 */       for (RoomCardConfig cardConfig : roomCardConfigMap.values((Predicate)cardPd)) {
/* 1142 */         String cardNo = cardConfig.getAba();
/*      */         try {
/* 1144 */           NCUCardData cardData = new NCUCardData();
/* 1145 */           cardData.setcCardNo(cardNo);
/* 1146 */           cardData.setcStartDate(this.dateFormat.format(cardConfig.getCardStartDate()));
/* 1147 */           cardData.setcEndDate(this.dateFormat.format(cardConfig.getCardEndDate()));
/* 1148 */           int addResult = this.hunDureNcuServiceImpl.addCard(id, cardData);
/* 1149 */           if (addResult == 0 || addResult == 4) {
/* 1150 */             EntryObject cardMp = (new PredicateBuilder()).getEntryObject();
/*      */             
/* 1152 */             PredicateBuilder cardMappingPb = cardMp.get("readerId").equal(id).and((Predicate)cardMp.get("cardId").equal(cardNo));
/*      */             
/* 1154 */             Iterator<RoomCardReaderMappingConfig> cardValue = cardMappingMap.values((Predicate)cardMappingPb).iterator();
/*      */             
/* 1156 */             if (!cardValue.hasNext()) {
/* 1157 */               RoomCardReaderMappingConfig cardMapping = new RoomCardReaderMappingConfig();
/* 1158 */               String cUUID = UUID.randomUUID().toString();
/* 1159 */               cardMapping.setId(cUUID);
/* 1160 */               cardMapping.setCardId(cardNo);
/* 1161 */               cardMapping.setReaderId(id);
/* 1162 */               cardMapping.setLoginCardReader(Boolean.valueOf(true));
/* 1163 */               cardsMap.put(cUUID, cardMapping);
/*      */             } 
/* 1165 */             this.opLogger.addLog(userId, "", SubSystem.ROOM, OperationItem.SET, id, new Date(), OperationResult.SUCCESS, "ToCardReader", "room.addCardPermission", new Object[] { cardNo });
/*      */           } 
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
/* 1177 */           Thread.sleep(30L);
/* 1178 */         } catch (Exception e) {
/*      */           
/* 1180 */           this.opLogger.addLog(userId, "", SubSystem.ROOM, OperationItem.SET, id, new Date(), OperationResult.FAILURE, "ToCardReader", "room.addCardPermission", new Object[] { cardNo });
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
/* 1191 */           unlock = true;
/* 1192 */           logger.error("Add permission failed, cardNo = '{}'", cardNo, e);
/*      */         } 
/*      */       } 
/*      */       
/* 1196 */       List<RoomCardReaderMappingConfig> saveConfigList = this.roomCardReaderMappingConfigRepository.saveAll(cardsMap.values());
/* 1197 */       Map<String, RoomCardReaderMappingConfig> mappingConfigMap = new HashMap<>();
/* 1198 */       for (RoomCardReaderMappingConfig config : saveConfigList) {
/* 1199 */         mappingConfigMap.put(config.getId(), config);
/*      */       }
/* 1201 */       cardMappingMap.putAll(mappingConfigMap);
/*      */       
/* 1203 */       if (!unlock) {
/* 1204 */         for (String cardNu : cardNus) {
/*      */           
/* 1206 */           List<LifeFaceLockCardLogData> refreshRecords = this.lifeFaceLockCardLogDataRepository.findByNcuIdAndLockCard(id, cardNu);
/* 1207 */           if (refreshRecords.size() > 0) {
/* 1208 */             this.lifeFaceLockCardLogDataRepository.updateIsLock(false, id, cardNu);
/*      */           }
/* 1210 */           String keyValue = spiltId + "+" + cardNu;
/* 1211 */           if (lifeFaceLockCardMap.containsKey(keyValue)) {
/* 1212 */             LifeFaceLockCardData data = (LifeFaceLockCardData)lifeFaceLockCardMap.get(keyValue);
/* 1213 */             data.setLockCard(false);
/* 1214 */             lifeFaceLockCardMap.put(keyValue, data);
/*      */           } 
/*      */         } 
/* 1217 */         return Boolean.TRUE;
/*      */       } 
/* 1219 */       return Boolean.FALSE;
/* 1220 */     } catch (Exception e) {
/* 1221 */       logger.error("Set UnLock Card failed.", e);
/* 1222 */       return Boolean.FALSE;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/permission/{deviceName:.+}"}, method = {RequestMethod.GET})
/*      */   public List<RoomPermissionDTO> getOneRoomPermissin(@PathVariable("deviceName") String deviceName) {
/*      */     try {
/* 1231 */       logger.debug("Get One Room Permissin.");
/* 1232 */       if (deviceName == null) {
/* 1233 */         logger.error("deviceName is NULL.");
/* 1234 */         return Collections.emptyList();
/*      */       } 
/* 1236 */       IMap<String, RoomCardConfig> roomCardConfigMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardConfig);
/*      */       
/* 1238 */       IMap<String, RoomCardReaderMappingConfig> cardMappingMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardReaderMappingConfig);
/* 1239 */       List<RoomPermissionDTO> result = new ArrayList<>();
/* 1240 */       EntryObject cardEo = (new PredicateBuilder()).getEntryObject();
/*      */       
/* 1242 */       PredicateBuilder cardPd = cardEo.get("readerId").equal(deviceName).and((Predicate)cardEo.get("loginCardReader").equal(Boolean.valueOf(true)));
/* 1243 */       for (RoomCardReaderMappingConfig config : cardMappingMap.values((Predicate)cardPd)) {
/* 1244 */         String aba = config.getCardId();
/* 1245 */         if (aba != null && aba.length() == 10) {
/* 1246 */           PredicateBuilder cardPb = (new PredicateBuilder()).getEntryObject().get("aba").equal(aba);
/* 1247 */           Iterator<RoomCardConfig> cardValue = roomCardConfigMap.values((Predicate)cardPb).iterator();
/* 1248 */           if (cardValue.hasNext()) {
/* 1249 */             RoomCardConfig cardConfig = cardValue.next();
/* 1250 */             if (cardConfig != null) {
/* 1251 */               RoomPermissionDTO dto = new RoomPermissionDTO();
/* 1252 */               dto.setRoom(TransferHelper.getNcuName(deviceName));
/* 1253 */               dto.setCard(cardConfig.getAba());
/* 1254 */               dto.setName(cardConfig.getName());
/* 1255 */               dto.setCardStartDate(cardConfig.getCardStartDate());
/* 1256 */               dto.setCardEndDate(cardConfig.getCardEndDate());
/* 1257 */               dto.setCompany(cardConfig.getCompany());
/* 1258 */               dto.setPhone(cardConfig.getTel());
/* 1259 */               result.add(dto); continue;
/*      */             } 
/* 1261 */             logger.debug("One Room Permissin CardConfig Is Null.");
/*      */             continue;
/*      */           } 
/* 1264 */           logger.debug("One Room Permissin CardValue Don't Have Next.");
/*      */           continue;
/*      */         } 
/* 1267 */         logger.debug("One Room Permissin Aba Is NULL.");
/*      */       } 
/*      */       
/* 1270 */       return result;
/* 1271 */     } catch (Exception e) {
/* 1272 */       logger.error("Get One Room Permissin failed.", e);
/* 1273 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/permissions"}, method = {RequestMethod.GET})
/*      */   public List<RoomPermissionDTO> getAllRoomPermissin() {
/*      */     try {
/* 1281 */       logger.debug("Get All Room Permissin.");
/* 1282 */       IMap<String, RoomCardConfig> roomCardConfigMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardConfig);
/*      */       
/* 1284 */       IMap<String, RoomCardReaderMappingConfig> cardMappingMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardReaderMappingConfig);
/* 1285 */       IMap<String, DeviceTcConfig> deviceTcConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 1286 */       PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceType").equal("NCU");
/* 1287 */       List<RoomPermissionDTO> result = new ArrayList<>();
/* 1288 */       for (DeviceTcConfig config : deviceTcConfigMap.values((Predicate)pb)) {
/* 1289 */         EntryObject cardEo = (new PredicateBuilder()).getEntryObject();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1294 */         PredicateBuilder cardPd = cardEo.get("readerId").equal(config.getDeviceName()).and((Predicate)cardEo.get("loginCardReader").equal(Boolean.valueOf(true)));
/* 1295 */         for (RoomCardReaderMappingConfig mapping : cardMappingMap.values((Predicate)cardPd)) {
/* 1296 */           String aba = mapping.getCardId();
/* 1297 */           if (aba != null && aba.length() == 10) {
/* 1298 */             PredicateBuilder cardPb = (new PredicateBuilder()).getEntryObject().get("aba").equal(aba);
/* 1299 */             Iterator<RoomCardConfig> cardValue = roomCardConfigMap.values((Predicate)cardPb).iterator();
/* 1300 */             if (cardValue.hasNext()) {
/* 1301 */               RoomCardConfig cardConfig = cardValue.next();
/* 1302 */               if (cardConfig != null) {
/* 1303 */                 RoomPermissionDTO dto = new RoomPermissionDTO();
/* 1304 */                 dto.setRoom(TransferHelper.getNcuName(config.getDeviceName()));
/* 1305 */                 dto.setCard(cardConfig.getAba());
/* 1306 */                 dto.setName(cardConfig.getName());
/* 1307 */                 dto.setCardStartDate(cardConfig.getCardStartDate());
/* 1308 */                 dto.setCardEndDate(cardConfig.getCardEndDate());
/* 1309 */                 dto.setCompany(cardConfig.getCompany());
/* 1310 */                 dto.setPhone(cardConfig.getTel());
/* 1311 */                 result.add(dto); continue;
/*      */               } 
/* 1313 */               logger.debug("All Room Permissin CardConfig Is Null.");
/*      */               continue;
/*      */             } 
/* 1316 */             logger.debug("All Room Permissin CardValue Don't Have Next.");
/*      */             continue;
/*      */           } 
/* 1319 */           logger.debug("All Room Permissin Aba Is NULL.");
/*      */         } 
/*      */       } 
/*      */       
/* 1323 */       return result;
/* 1324 */     } catch (Exception e) {
/* 1325 */       logger.error("Get All Room Permissin failed.", e);
/* 1326 */       return Collections.emptyList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/dataTimeNow/{deviceName:.+}"}, method = {RequestMethod.GET})
/*      */   public NcuConfigDTO getNcuHostDataTimeNow(@PathVariable("deviceName") String deviceName) {
/*      */     try {
/* 1334 */       logger.debug("Get NcuHost DataTimeNow.");
/* 1335 */       if (deviceName == null) {
/* 1336 */         logger.error("deviceName is NULL.");
/* 1337 */         return new NcuConfigDTO();
/*      */       } 
/* 1339 */       NCUDateTime respond = this.hunDureNcuServiceImpl.getNCUTime(deviceName);
/* 1340 */       NcuConfigDTO result = new NcuConfigDTO();
/* 1341 */       result.setId(deviceName);
/* 1342 */       result.setDataTimeNow(respond.getDate() + " " + respond.getTime());
/* 1343 */       if (respond.getDate() == null || respond.getTime() == null) {
/* 1344 */         result.setDataTimeNow("");
/*      */       }
/* 1346 */       return result;
/* 1347 */     } catch (Exception e) {
/* 1348 */       logger.error("Get NcuHost DataTimeNow failed.", e);
/* 1349 */       return new NcuConfigDTO();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @RequestMapping(value = {"/synchronizeTime/{deviceName:.+}"}, method = {RequestMethod.PUT})
/*      */   public Boolean synchronizeNcuHostTime(@PathVariable("deviceName") String deviceName, HttpServletRequest request) {
/*      */     try {
/* 1358 */       return this.hunDureNcuServiceImpl.synchronizeNCUTime(deviceName);
/* 1359 */     } catch (Exception e) {
/* 1360 */       logger.error("synchronize NcuHostTime.", e);
/* 1361 */       return Boolean.FALSE;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\restful\AoNcuViewerRestServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */