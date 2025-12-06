/*     */ package com.hwacom.ngtms.ao.restful;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.alarm.fm.model.AlarmLog;
/*     */ import com.hwacom.ngtms.alarm.fm.repository.AlarmLogRepository;
/*     */ import com.hwacom.ngtms.ao.fm.hz.AoHzMap;
/*     */ import com.hwacom.ngtms.ao.fm.model.MessageUser;
/*     */ import com.hwacom.ngtms.ao.fm.service.AoAlarmService;
/*     */ import com.hwacom.ngtms.ao.fm.service.Mp3playService;
/*     */ import com.hwacom.ngtms.ao.shared.AlarmType;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmLogPageLoadDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmMessageDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmRecordQueryParamDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmSendMessageDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmTypeDTO;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceLocationMappingConfig;
/*     */ import com.hwacom.ngtms.c.fm.service.OpLogger;
/*     */ import com.hwacom.ngtms.c.restful.BaseRestful;
/*     */ import com.hwacom.ngtms.c.shared.SubSystem;
/*     */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*     */ import com.hwacom.ngtms.common.fm.model.Role;
/*     */ import com.hwacom.ngtms.common.fm.model.User;
/*     */ import com.hwacom.ngtms.common.shared.dto.RoleDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.UserDTO;
/*     */ import com.hwacom.ngtms.common.util.DtoConverter;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.PostConstruct;
/*     */ import org.modelmapper.ModelMapper;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.data.domain.Sort;
/*     */ import org.springframework.web.bind.annotation.CrossOrigin;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @CrossOrigin
/*     */ @RestController
/*     */ @RequestMapping({"/api/smg/ao/alarm"})
/*     */ public class AoAlarmViewerRestServiceImpl
/*     */   extends BaseRestful
/*     */ {
/*  69 */   private static final Logger logger = LoggerFactory.getLogger(AoAlarmViewerRestServiceImpl.class);
/*     */   
/*     */   @Autowired
/*     */   private ModelMapper modelMapper;
/*     */   
/*     */   @Autowired
/*     */   private OpLogger opLogger;
/*     */   
/*     */   @PostConstruct
/*     */   public void init() {
/*  79 */     setSubSystem(SubSystem.SMG); } @Autowired
/*     */   private AoAlarmService aoAlarmService; @Autowired
/*     */   private Mp3playService mp3playService; @Autowired
/*     */   private AlarmLogRepository alarmLogRepository; @RequestMapping(value = {"/user"}, method = {RequestMethod.GET})
/*     */   public List<UserDTO> getUsers() {
/*     */     try {
/*  85 */       logger.debug("Get users.");
/*  86 */       List<UserDTO> list = new ArrayList<>();
/*  87 */       IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/*  88 */       if (userMap != null && userMap.values() != null) {
/*  89 */         for (User user : userMap.values()) {
/*  90 */           list.add(DtoConverter.from(user));
/*     */         }
/*     */       } else {
/*  93 */         logger.error("userList = null!");
/*     */       } 
/*  95 */       return list;
/*  96 */     } catch (Exception e) {
/*  97 */       logger.error("Get users failed.", e);
/*  98 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/role"}, method = {RequestMethod.GET})
/*     */   public List<RoleDTO> getRoles() {
/*     */     try {
/* 105 */       logger.debug("Get roles.");
/* 106 */       List<RoleDTO> list = new ArrayList<>();
/* 107 */       IMap<String, Role> roleMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountRole);
/* 108 */       if (roleMap != null && roleMap.values() != null) {
/* 109 */         for (Role role : roleMap.values()) {
/* 110 */           list.add(DtoConverter.from(role));
/*     */         }
/*     */       } else {
/* 113 */         logger.error("roleList = null!");
/*     */       } 
/* 115 */       list.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
/* 116 */       return list;
/* 117 */     } catch (Exception e) {
/* 118 */       logger.error("Get roles failed.", e);
/* 119 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/allTypes"}, method = {RequestMethod.GET})
/*     */   public List<AlarmTypeDTO> getAllAlarmType() {
/*     */     try {
/* 126 */       logger.debug("Get AllAlarmType.");
/* 127 */       List<AlarmTypeDTO> result = new ArrayList<>();
/* 128 */       for (AlarmType type : AlarmType.values()) {
/* 129 */         AlarmTypeDTO dto = new AlarmTypeDTO();
/* 130 */         dto.setAlarmType(type);
/* 131 */         result.add(dto);
/*     */       } 
/* 133 */       return result;
/* 134 */     } catch (Exception e) {
/* 135 */       logger.error("Get AllAlarmType failed.", e);
/* 136 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/allNonRtn"}, method = {RequestMethod.GET})
/*     */   public List<AlarmMessageDTO> getAllNonRtnAlarms() {
/*     */     try {
/* 144 */       logger.debug("Get AllNonRtnAlarms.");
/* 145 */       return this.aoAlarmService.retrieveAllNonRtnAlarms();
/* 146 */     } catch (Exception e) {
/* 147 */       logger.error("Get AllNonRtnAlarms failed.", e);
/* 148 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/confirm"}, method = {RequestMethod.POST})
/*     */   public String confirmAlarm(@RequestBody AlarmMessageDTO dto) {
/*     */     try {
/* 156 */       logger.debug("Confirm Alarm.");
/* 157 */       String confirmResult = null;
/* 158 */       if (dto == null) {
/* 159 */         throw new IllegalArgumentException("AlarmMessageDTO dto  is null.");
/*     */       }
/* 161 */       this.aoAlarmService.confirmAlarm(dto);
/* 162 */       return confirmResult;
/* 163 */     } catch (Exception e) {
/* 164 */       logger.error("Confirm Alarm failed.", e);
/* 165 */       return "failed";
/*     */     } 
/*     */   }
/*     */   
/*     */   @RequestMapping(value = {"/alarmLog/param"}, method = {RequestMethod.POST})
/*     */   public List<AlarmMessageDTO> getAlarmLogWithParam(@RequestBody AlarmRecordQueryParamDTO dto) {
/*     */     try {
/* 172 */       logger.info("getAlarmLogWithParam");
/* 173 */       if (dto == null) {
/* 174 */         return null;
/*     */       }
/*     */       
/* 177 */       IMap<String, DeviceLocationMappingConfig> roomLocationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/* 178 */       Date startTime = dto.getStartTime();
/* 179 */       Date endTime = dto.getEndTime();
/* 180 */       Sort sort = Sort.by(new Sort.Order[] { new Sort.Order(Sort.Direction.DESC, "timestamp") });
/* 181 */       List<AlarmMessageDTO> alarmMessages = new ArrayList<>();
/* 182 */       List<AlarmLog> alarmLogs = new ArrayList<>();
/* 183 */       Boolean isAllRoom = null;
/* 184 */       Boolean isAllAlarm = null;
/*     */       
/* 186 */       if (dto.getRoomDto().getDeviceName().equals("all")) {
/* 187 */         isAllRoom = Boolean.valueOf(true);
/*     */       } else {
/* 189 */         isAllRoom = Boolean.valueOf(false);
/*     */       } 
/* 191 */       if (dto.getAlarmMonitorType().getAlarmType().equals(AlarmType.ALL)) {
/* 192 */         isAllAlarm = Boolean.valueOf(true);
/*     */       } else {
/* 194 */         isAllAlarm = Boolean.valueOf(false);
/*     */       } 
/*     */       
/* 197 */       if (isAllRoom.booleanValue() && isAllAlarm.booleanValue()) {
/* 198 */         alarmLogs = this.alarmLogRepository.findByTimestampBetween(startTime, endTime, sort);
/*     */       
/*     */       }
/* 201 */       else if (isAllRoom.booleanValue() && !isAllAlarm.booleanValue()) {
/* 202 */         List<String> alarmTypeList = new ArrayList<>();
/* 203 */         alarmTypeList.add(dto.getAlarmMonitorType().getAlarmType().name());
/*     */         
/* 205 */         alarmLogs = this.alarmLogRepository.findByTimestampBetweenAndAlarmSubTypeIds(startTime, endTime, alarmTypeList, sort);
/*     */ 
/*     */       
/*     */       }
/* 209 */       else if (!isAllRoom.booleanValue() && isAllAlarm.booleanValue()) {
/* 210 */         List<String> deviceNames = new ArrayList<>();
/*     */         
/* 212 */         String locationName = dto.getRoomDto().getDisplayName();
/* 213 */         if (locationName.contains("-")) {
/* 214 */           String[] ds = locationName.split("-");
/* 215 */           locationName = ds[0] + ds[1];
/*     */         } 
/* 217 */         EntryObject eo = (new PredicateBuilder()).getEntryObject();
/* 218 */         PredicateBuilder pb = eo.get("locationName").equal(locationName);
/* 219 */         for (DeviceLocationMappingConfig mappingConfig : roomLocationMap.values((Predicate)pb)) {
/* 220 */           deviceNames.add(mappingConfig.getDeviceName());
/*     */         }
/*     */         
/* 223 */         alarmLogs = this.alarmLogRepository.findByDeviceNamesAndTimestamp(deviceNames, startTime, endTime, sort);
/*     */       }
/*     */       else {
/*     */         
/* 227 */         List<String> alarmTypeList = new ArrayList<>();
/* 228 */         alarmTypeList.add(dto.getAlarmMonitorType().getAlarmType().name());
/* 229 */         List<String> deviceNames = new ArrayList<>();
/*     */         
/* 231 */         String locationName = dto.getRoomDto().getDisplayName();
/* 232 */         if (locationName.contains("-")) {
/* 233 */           String[] ds = locationName.split("-");
/* 234 */           locationName = ds[0] + ds[1];
/*     */         } 
/* 236 */         EntryObject eo = (new PredicateBuilder()).getEntryObject();
/* 237 */         PredicateBuilder pb = eo.get("locationName").equal(locationName);
/* 238 */         for (DeviceLocationMappingConfig mappingConfig : roomLocationMap.values((Predicate)pb)) {
/* 239 */           deviceNames.add(mappingConfig.getDeviceName());
/*     */         }
/*     */ 
/*     */         
/* 243 */         alarmLogs = this.alarmLogRepository.findByDeviceNamesAndTimestampAndAlarmSubTypeIds(deviceNames, startTime, endTime, alarmTypeList, sort);
/*     */       } 
/*     */       
/* 246 */       if (alarmLogs != null && alarmLogs.size() > 0) {
/* 247 */         for (AlarmLog log : alarmLogs) {
/* 248 */           alarmMessages.add(this.aoAlarmService.toAlarmMessageDTO(log));
/*     */         }
/*     */       }
/* 251 */       return alarmMessages;
/* 252 */     } catch (Exception e) {
/* 253 */       logger.error("getAlarmLogWithParam failed.", e);
/* 254 */       return Collections.emptyList();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/alarmLogPageLoad"}, method = {RequestMethod.POST})
/*     */   public AlarmLogPageLoadDTO getAlarmLogWithParamPageLoad(@RequestBody AlarmRecordQueryParamDTO dto) {
/*     */     try {
/* 262 */       logger.info("getAlarmLogWithParamPageLoad");
/* 263 */       if (dto == null) {
/* 264 */         return null;
/*     */       }
/*     */       
/* 267 */       IMap<String, DeviceLocationMappingConfig> roomLocationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/* 268 */       AlarmLogPageLoadDTO result = new AlarmLogPageLoadDTO();
/* 269 */       Date startTime = dto.getStartTime();
/* 270 */       Date endTime = dto.getEndTime();
/* 271 */       int offset = dto.getOffset();
/* 272 */       Sort sort = Sort.by(new Sort.Order[] { new Sort.Order(Sort.Direction.DESC, "timestamp") });
/* 273 */       List<AlarmMessageDTO> alarmMessages = new ArrayList<>();
/* 274 */       List<AlarmLog> alarmLogs = new ArrayList<>();
/* 275 */       Boolean isAllRoom = null;
/* 276 */       Boolean isAllAlarm = null;
/*     */       
/* 278 */       if (dto.getRoomDto().getDeviceName().equals("all")) {
/* 279 */         isAllRoom = Boolean.valueOf(true);
/*     */       } else {
/* 281 */         isAllRoom = Boolean.valueOf(false);
/*     */       } 
/* 283 */       if (dto.getAlarmMonitorType().getAlarmType().equals(AlarmType.ALL)) {
/* 284 */         isAllAlarm = Boolean.valueOf(true);
/*     */       } else {
/* 286 */         isAllAlarm = Boolean.valueOf(false);
/*     */       } 
/*     */       
/* 289 */       Map<Integer, List<AlarmMessageDTO>> alarmPageMap = new HashMap<>();
/*     */       
/* 291 */       if (isAllRoom.booleanValue() && isAllAlarm.booleanValue()) {
/* 292 */         alarmLogs = this.alarmLogRepository.findByTimestampBetween(startTime, endTime, sort);
/*     */       
/*     */       }
/* 295 */       else if (isAllRoom.booleanValue() && !isAllAlarm.booleanValue()) {
/* 296 */         List<String> alarmTypeList = new ArrayList<>();
/* 297 */         alarmTypeList.add(dto.getAlarmMonitorType().getAlarmType().name());
/*     */         
/* 299 */         alarmLogs = this.alarmLogRepository.findByTimestampBetweenAndAlarmSubTypeIds(startTime, endTime, alarmTypeList, sort);
/*     */ 
/*     */       
/*     */       }
/* 303 */       else if (!isAllRoom.booleanValue() && isAllAlarm.booleanValue()) {
/* 304 */         List<String> deviceNames = new ArrayList<>();
/*     */         
/* 306 */         String locationName = dto.getRoomDto().getDisplayName();
/* 307 */         if (locationName.contains("-")) {
/* 308 */           String[] ds = locationName.split("-");
/* 309 */           locationName = ds[0] + ds[1];
/*     */         } 
/* 311 */         EntryObject eo = (new PredicateBuilder()).getEntryObject();
/* 312 */         PredicateBuilder pb = eo.get("locationName").equal(locationName);
/* 313 */         for (DeviceLocationMappingConfig mappingConfig : roomLocationMap.values((Predicate)pb)) {
/* 314 */           deviceNames.add(mappingConfig.getDeviceName());
/*     */         }
/*     */         
/* 317 */         alarmLogs = this.alarmLogRepository.findByDeviceNamesAndTimestamp(deviceNames, startTime, endTime, sort);
/*     */       }
/*     */       else {
/*     */         
/* 321 */         List<String> alarmTypeList = new ArrayList<>();
/* 322 */         alarmTypeList.add(dto.getAlarmMonitorType().getAlarmType().name());
/* 323 */         List<String> deviceNames = new ArrayList<>();
/*     */         
/* 325 */         String locationName = dto.getRoomDto().getDisplayName();
/* 326 */         if (locationName.contains("-")) {
/* 327 */           String[] ds = locationName.split("-");
/* 328 */           locationName = ds[0] + ds[1];
/*     */         } 
/* 330 */         EntryObject eo = (new PredicateBuilder()).getEntryObject();
/* 331 */         PredicateBuilder pb = eo.get("locationName").equal(locationName);
/* 332 */         for (DeviceLocationMappingConfig mappingConfig : roomLocationMap.values((Predicate)pb)) {
/* 333 */           deviceNames.add(mappingConfig.getDeviceName());
/*     */         }
/*     */ 
/*     */         
/* 337 */         alarmLogs = this.alarmLogRepository.findByDeviceNamesAndTimestampAndAlarmSubTypeIds(deviceNames, startTime, endTime, alarmTypeList, sort);
/*     */       } 
/*     */       
/* 340 */       if (alarmLogs != null && alarmLogs.size() > 0) {
/* 341 */         for (AlarmLog log : alarmLogs) {
/* 342 */           alarmMessages.add(this.aoAlarmService.toAlarmMessageDTO(log));
/*     */         }
/*     */       }
/*     */       
/* 346 */       for (int i = 0; i < alarmMessages.size(); i++) {
/* 347 */         Integer page = Integer.valueOf(i / offset + 1);
/* 348 */         List<AlarmMessageDTO> alarmList = alarmPageMap.get(page);
/* 349 */         if (alarmList == null) {
/* 350 */           alarmList = new ArrayList<>();
/*     */         }
/* 352 */         alarmList.add(alarmMessages.get(i));
/* 353 */         alarmPageMap.put(page, alarmList);
/*     */       } 
/*     */       
/* 356 */       Integer totalPage = Integer.valueOf(0);
/* 357 */       Integer alarmMessagesSize = Integer.valueOf(alarmMessages.size());
/* 358 */       if (alarmMessagesSize.intValue() > 0 && offset > 0) {
/* 359 */         totalPage = Integer.valueOf(alarmMessagesSize.intValue() / offset + 1);
/*     */       }
/*     */       
/* 362 */       result.setData(alarmPageMap.get(Integer.valueOf(1)));
/* 363 */       result.setOffset(offset);
/* 364 */       result.setTotalPage(totalPage.intValue());
/* 365 */       Integer resultSize = Integer.valueOf(0);
/* 366 */       if (result.getData() != null) {
/* 367 */         resultSize = Integer.valueOf(result.getData().size());
/*     */       }
/* 369 */       logger.info("getAlarmLogWithParamPageLoad resultSize:'{}', total page:'{}', offset:'{}'", new Object[] { resultSize, 
/*     */ 
/*     */             
/* 372 */             Integer.valueOf(result.getTotalPage()), 
/* 373 */             Integer.valueOf(result.getOffset()) });
/* 374 */       return result;
/* 375 */     } catch (Exception e) {
/* 376 */       logger.error("getAlarmLogWithParamPageLoad failed.", e);
/* 377 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/alarmSendMessage"}, method = {RequestMethod.POST})
/*     */   public void alarmSendMessage(@RequestBody AlarmSendMessageDTO dto) {
/*     */     try {
/* 385 */       IMap<String, MessageUser> messageUserMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.MessageUser);
/* 386 */       List<AlarmSendMessageDTO> sendList = new ArrayList<>();
/* 387 */       if (messageUserMap.size() > 0) {
/* 388 */         for (MessageUser user : messageUserMap.values()) {
/* 389 */           AlarmSendMessageDTO messageDTO = new AlarmSendMessageDTO();
/* 390 */           messageDTO.setToAddrs(user.getId());
/* 391 */           messageDTO.setMessages(dto.getMessages());
/* 392 */           sendList.add(messageDTO);
/*     */         } 
/*     */       } else {
/* 395 */         logger.debug("No MessageUser");
/*     */         return;
/*     */       } 
/* 398 */       if (sendList.size() > 0) {
/* 399 */         for (AlarmSendMessageDTO sendDTO : sendList) {
/* 400 */           String url = "http://10.21.99.25:18081/api/sms/send";
/* 401 */           HttpURLConnection connection = null;
/* 402 */           URL ur = new URL(url);
/* 403 */           connection = (HttpURLConnection)ur.openConnection();
/* 404 */           connection.setRequestMethod("POST");
/* 405 */           connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
/* 406 */           connection.setConnectTimeout(1000);
/* 407 */           connection.setDoInput(true);
/* 408 */           connection.setDoOutput(true);
/* 409 */           connection.connect();
/*     */           
/* 411 */           OutputStream out = null;
/* 412 */           Gson gson = new Gson();
/* 413 */           out = new BufferedOutputStream(connection.getOutputStream());
/* 414 */           BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
/* 415 */           writer.write(gson.toJson(sendDTO));
/* 416 */           writer.flush();
/* 417 */           writer.close();
/* 418 */           out.close();
/* 419 */           int responseCode = connection.getResponseCode();
/* 420 */           if (responseCode == 200) {
/* 421 */             logger.debug("Send Message Succeful, phone = '{}'", sendDTO.getToAddrs());
/*     */           }
/* 423 */           Thread.sleep(500L);
/*     */         } 
/*     */       }
/* 426 */     } catch (Exception e) {
/* 427 */       logger.error("alarmSendMessage failed.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/alarmSendVoice"}, method = {RequestMethod.GET})
/*     */   public void getPlayAlarmMp3() {
/*     */     try {
/* 435 */       this.mp3playService.run();
/* 436 */     } catch (Exception e) {
/* 437 */       logger.error("Get Play Alarm Mp3 Failed.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @RequestMapping(value = {"/alarmStopVoice"}, method = {RequestMethod.GET})
/*     */   public void getStopAlarmMp3() {
/*     */     try {
/* 445 */       this.mp3playService.close();
/* 446 */     } catch (Exception e) {
/* 447 */       logger.error("Get Play Alarm Mp3 Failed.", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\restful\AoAlarmViewerRestServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */