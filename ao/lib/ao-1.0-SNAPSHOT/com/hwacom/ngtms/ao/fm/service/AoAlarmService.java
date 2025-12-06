/*     */ package com.hwacom.ngtms.ao.fm.service;
/*     */ import com.hazelcast.core.EntryEvent;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.map.listener.EntryAddedListener;
/*     */ import com.hazelcast.map.listener.EntryRemovedListener;
/*     */ import com.hazelcast.map.listener.EntryUpdatedListener;
/*     */ import com.hazelcast.map.listener.MapListener;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.alarm.fm.hz.AlarmHzMap;
/*     */ import com.hwacom.ngtms.alarm.fm.model.Alarm;
/*     */ import com.hwacom.ngtms.alarm.fm.model.AlarmLevelConfig;
/*     */ import com.hwacom.ngtms.alarm.fm.model.AlarmLog;
/*     */ import com.hwacom.ngtms.alarm.fm.repository.AlarmLogRepository;
/*     */ import com.hwacom.ngtms.alarm.fm.service.AlarmService;
/*     */ import com.hwacom.ngtms.alarm.shared.AlarmState;
/*     */ import com.hwacom.ngtms.ao.shared.AlarmType;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmMessageDTO;
/*     */ import com.hwacom.ngtms.ao.util.TransferHelper;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceLocationMappingConfig;
/*     */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*     */ import com.hwacom.ngtms.common.fm.model.DeviceConfig;
/*     */ import com.hwacom.ngtms.common.util.IMapLocker;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service
/*     */ @Scope("prototype")
/*     */ public class AoAlarmService {
/*  44 */   private static final Logger logger = LoggerFactory.getLogger(AoAlarmService.class);
/*     */   
/*     */   @Autowired
/*     */   private AlarmLogRepository alarmLogRepository;
/*     */   @Autowired
/*     */   private AlarmService alarmService;
/*  50 */   private List<Runnable> listenerRemover = new ArrayList<>();
/*     */   
/*     */   public void clear() {
/*  53 */     logger.debug("Clear listener.");
/*  54 */     this.listenerRemover.forEach(remover -> remover.run());
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized List<AlarmMessageDTO> registerAlarmMessageListener(Consumer<Object> consumer) {
/*  59 */     IMap<Long, AlarmLog> alarmMap = HzUtils.getMap((HzDistObjEnum)AlarmHzMap.Alarm);
/*     */     
/*  61 */     EntryAddedListener<String, Alarm> entryAddedListener = event -> consumer.accept(toAlarmMessageDTO((Alarm)event.getValue()));
/*     */ 
/*     */ 
/*     */     
/*  65 */     Predicate<Alarm> predicate = createNonAckAlmPredicate();
/*     */     
/*  67 */     EntryUpdatedListener<String, Alarm> entryUpdatedListener = event -> {
/*     */         if (predicate.test(event.getValue())) {
/*     */           consumer.accept(toAlarmMessageDTO((Alarm)event.getValue()));
/*     */         } else {
/*     */           consumer.accept(((Alarm)event.getValue()).getId());
/*     */         } 
/*     */       };
/*     */ 
/*     */     
/*  76 */     EntryRemovedListener<String, Alarm> entryRemovedListener = event -> consumer.accept(toAlarmMessageDTO((Alarm)event.getOldValue()).getId());
/*     */ 
/*     */     
/*  79 */     String addedListenerId = alarmMap.addEntryListener((MapListener)entryAddedListener, true);
/*  80 */     String updatedListenerId = alarmMap.addEntryListener((MapListener)entryUpdatedListener, true);
/*  81 */     String removedListenerId = alarmMap.addEntryListener((MapListener)entryRemovedListener, true);
/*  82 */     this.listenerRemover.add(() -> alarmMap.removeEntryListener(addedListenerId));
/*  83 */     this.listenerRemover.add(() -> alarmMap.removeEntryListener(updatedListenerId));
/*  84 */     this.listenerRemover.add(() -> alarmMap.removeEntryListener(removedListenerId));
/*  85 */     return retrieveAlarms(predicate);
/*     */   }
/*     */   
/*     */   public Predicate<Alarm> createNonAckAlmPredicate() {
/*  89 */     return message -> !message.getState().equals(AlarmState.ACK_ALM);
/*     */   }
/*     */   
/*     */   private Predicate<Alarm> createUnackRtnAlarmPredicate() {
/*  93 */     return message -> message.getState().equals(AlarmState.UNACK_RTN);
/*     */   }
/*     */   
/*     */   public List<AlarmMessageDTO> retrieveAllNonRtnAlarms() {
/*  97 */     IMap<String, Alarm> alarmMap = HzUtils.getMap((HzDistObjEnum)AlarmHzMap.Alarm);
/*  98 */     List<AlarmMessageDTO> result = new ArrayList<>();
/*  99 */     for (Alarm alarm : alarmMap.values()) {
/* 100 */       if (alarm.getState() == AlarmState.UNACK_ALM || alarm.getState() == AlarmState.ACK_ALM) {
/* 101 */         Optional<AlarmLog> optAlarmLog = this.alarmLogRepository.findById(alarm.getLogId());
/* 102 */         if (optAlarmLog.isPresent()) {
/* 103 */           AlarmLog alarmLog = optAlarmLog.get();
/* 104 */           Optional.<AlarmMessageDTO>of(toAlarmMessageDTO(alarm, alarmLog)).ifPresent(dto -> result.add(dto));
/*     */         } 
/*     */       } 
/*     */     } 
/* 108 */     Collections.sort(result, this::compare);
/* 109 */     return result;
/*     */   }
/*     */   
/*     */   public List<AlarmMessageDTO> retrieveAlarms(Predicate<Alarm> predicate) {
/* 113 */     List<AlarmMessageDTO> result = new ArrayList<>();
/* 114 */     IMap<String, Alarm> alarmMap = HzUtils.getMap((HzDistObjEnum)AlarmHzMap.Alarm);
/* 115 */     for (Alarm alarm : alarmMap.values()) {
/* 116 */       if (predicate.test(alarm)) {
/* 117 */         result.add(toAlarmMessageDTO(alarm));
/*     */       }
/*     */     } 
/* 120 */     Collections.sort(result, this::compare);
/* 121 */     return result;
/*     */   }
/*     */   
/*     */   public AlarmMessageDTO toAlarmMessageDTO(Alarm alarm) {
/* 125 */     if (alarm.getLogId() != null) {
/* 126 */       Optional<AlarmLog> optAlarmLog = this.alarmLogRepository.findById(alarm.getLogId());
/* 127 */       if (optAlarmLog.isPresent()) {
/* 128 */         return toAlarmMessageDTO(alarm, optAlarmLog.get());
/*     */       }
/*     */     } 
/* 131 */     AlarmMessageDTO dto = new AlarmMessageDTO();
/* 132 */     dto.setId(alarm.getId());
/* 133 */     return dto;
/*     */   }
/*     */   
/*     */   public AlarmMessageDTO toAlarmMessageDTO(AlarmLog alarmlog) {
/* 137 */     Optional<AlarmLog> optAlarmLog = this.alarmLogRepository.findById(alarmlog.getId());
/* 138 */     if (optAlarmLog.isPresent()) {
/* 139 */       return toAlarmMessageDTO(null, optAlarmLog.get());
/*     */     }
/* 141 */     return new AlarmMessageDTO();
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmAllUnackRtnAlarm() {
/* 146 */     List<AlarmMessageDTO> dtos = retrieveAlarms(createUnackRtnAlarmPredicate());
/* 147 */     logger.debug("Before confirm all unack rtn alarms. size: '{}'", Integer.valueOf(dtos.size()));
/* 148 */     for (AlarmMessageDTO dto : dtos) {
/* 149 */       confirmAlarm(dto);
/*     */     }
/* 151 */     logger.debug("All unack rtn alarm confirmed!");
/*     */   }
/*     */   
/*     */   public void confirmAlarm(AlarmMessageDTO dto) {
/* 155 */     changeAlarmStateAndPriority(dto);
/* 156 */     IMap<String, Alarm> alarmMap = HzUtils.getMap((HzDistObjEnum)AlarmHzMap.Alarm);
/* 157 */     Alarm alarm = (Alarm)alarmMap.get(dto.getId());
/* 158 */     alarm.setState(dto.getState());
/* 159 */     Optional<AlarmLog> optAlarmLog = this.alarmLogRepository.findById(alarm.getLogId());
/* 160 */     optAlarmLog.ifPresent(alarmLog -> {
/*     */           alarmLog.setPriority(dto.getPriority());
/*     */           
/*     */           alarmLog.setAlarmState(dto.getState());
/*     */           alarmLog.setTimestamp(new Date());
/*     */           this.alarmLogRepository.save(alarmLog);
/*     */           try {
/*     */             if (dto.getState().equals(AlarmState.ACK_ALM)) {
/*     */               alarm.setLogId(alarmLog.getId());
/*     */               IMapLocker.addOrUpdateMapping(alarmMap, alarm.getId(), alarm);
/*     */             } else if (dto.getState().equals(AlarmState.ACK_RTN)) {
/*     */               IMapLocker.removeMapping(alarmMap, alarm.getId());
/*     */             } 
/* 173 */           } catch (RuntimeException e) {
/*     */             this.alarmLogRepository.delete(alarmLog);
/*     */             throw e;
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<AlarmLevelConfig> getAlarmLevelConfig(AlarmType alarmType, double value) {
/* 182 */     Collection<AlarmLevelConfig> alarmLevelConfigs = this.alarmService.getAlarmLevelConfigs(alarmType.toString());
/* 183 */     int alarmLevel = 0;
/* 184 */     AlarmLevelConfig result = null;
/* 185 */     for (AlarmLevelConfig cfg : alarmLevelConfigs) {
/* 186 */       if (value >= cfg.getAlarmThreshold().doubleValue() && alarmLevel < cfg.getAlarmLevel().intValue()) {
/* 187 */         result = cfg;
/* 188 */         alarmLevel = cfg.getAlarmLevel().intValue();
/*     */       } 
/*     */     } 
/* 191 */     return Optional.ofNullable(result);
/*     */   }
/*     */   
/*     */   public String getAlarmStateMessage(AlarmState state) {
/* 195 */     String stateMsg = null;
/* 196 */     if (state.equals(AlarmState.UNACK_ALM)) {
/* 197 */       stateMsg = "未確認";
/* 198 */     } else if (state.equals(AlarmState.ACK_ALM)) {
/* 199 */       stateMsg = "待處理";
/* 200 */     } else if (state.equals(AlarmState.UNACK_RTN)) {
/* 201 */       stateMsg = "已自動排除";
/* 202 */     } else if (state.equals(AlarmState.ACK_RTN)) {
/* 203 */       stateMsg = "已處理排除";
/*     */     } 
/* 205 */     return stateMsg;
/*     */   }
/*     */   
/*     */   private Optional<AlarmMessageDTO> filterCustomNotification(Alarm alarm, AlarmLog alarmLog) {
/* 209 */     Optional<AlarmLevelConfig> optAlarmLevelConfig = this.alarmService.getAlarmLevelConfig(alarmLog);
/*     */     
/* 211 */     boolean notification = ((Boolean)optAlarmLevelConfig.<Boolean>map(AlarmLevelConfig::getCustomNotification).orElse(Boolean.valueOf(false))).booleanValue();
/* 212 */     if (notification) {
/* 213 */       return Optional.of(toAlarmMessageDTO(alarm, alarmLog));
/*     */     }
/* 215 */     return Optional.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   private void changeAlarmStateAndPriority(AlarmMessageDTO dto) {
/* 220 */     if (dto.getState().equals(AlarmState.UNACK_ALM)) {
/* 221 */       dto.setState(AlarmState.ACK_ALM);
/* 222 */     } else if (dto.getState().equals(AlarmState.UNACK_RTN)) {
/* 223 */       dto.setState(AlarmState.ACK_RTN);
/*     */     } 
/* 225 */     if (dto.getAlarmLevel() == 1) {
/* 226 */       dto.setPriority("非常危急");
/* 227 */     } else if (dto.getAlarmLevel() == 2) {
/* 228 */       dto.setPriority("優先");
/*     */     } else {
/* 230 */       dto.setPriority("一般");
/*     */     } 
/*     */   }
/*     */   
/*     */   private AlarmMessageDTO toAlarmMessageDTO(Alarm alarm, AlarmLog alarmLog) {
/* 235 */     AlarmMessageDTO dto = new AlarmMessageDTO();
/* 236 */     if (alarm != null && alarm.getId() != null) {
/* 237 */       dto.setId(alarm.getId());
/*     */     } else {
/* 239 */       dto.setId(UUID.randomUUID().toString());
/*     */     } 
/* 241 */     dto.setTimestamp(alarmLog.getTimestamp());
/* 242 */     dto.setDeviceName(alarmLog.getDeviceName());
/* 243 */     IMap<String, DeviceConfig> deviceConfigs = HzUtils.getMap((HzDistObjEnum)CommonHzMap.DeviceConfig);
/*     */     
/* 245 */     Optional<DeviceConfig> optDeviceConfig = Optional.ofNullable(deviceConfigs.get(dto.getDeviceName()));
/* 246 */     String ncuDisplayName = TransferHelper.getNcuName(dto.getDeviceName());
/* 247 */     if (optDeviceConfig.isPresent()) {
/* 248 */       if (ncuDisplayName != null) {
/* 249 */         if (((DeviceConfig)optDeviceConfig.get()).getDeviceType().equals("燈光開關")) {
/* 250 */           dto.setDisplayName(ncuDisplayName);
/* 251 */         } else if (((DeviceConfig)optDeviceConfig.get()).getDeviceType().equals("NCU")) {
/* 252 */           dto.setDisplayName(ncuDisplayName + "-NCU主機");
/*     */         } else {
/* 254 */           dto.setDisplayName(ncuDisplayName + "-" + ((DeviceConfig)optDeviceConfig.get()).getDisplayName());
/*     */         } 
/*     */       } else {
/*     */         
/* 258 */         IMap<String, DeviceLocationMappingConfig> roomLocationMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceLocationMappingConfig);
/* 259 */         EntryObject eo = (new PredicateBuilder()).getEntryObject();
/* 260 */         PredicateBuilder pb = eo.get("deviceName").equal(dto.getDeviceName());
/* 261 */         Collection<DeviceLocationMappingConfig> mappingConfig = roomLocationMap.values((Predicate)pb);
/* 262 */         if (mappingConfig != null && mappingConfig.size() > 0) {
/* 263 */           if (mappingConfig.iterator() != null && mappingConfig.iterator().hasNext()) {
/* 264 */             String resultDisplayValue = "";
/* 265 */             resultDisplayValue = ((DeviceLocationMappingConfig)mappingConfig.iterator().next()).getDescription();
/* 266 */             dto.setDisplayName(resultDisplayValue + " " + ((DeviceConfig)optDeviceConfig.get()).getDisplayName());
/*     */           } else {
/* 268 */             dto.setDisplayName(((DeviceConfig)optDeviceConfig.get()).getDisplayName());
/*     */           } 
/*     */         } else {
/* 271 */           dto.setDisplayName(((DeviceConfig)optDeviceConfig.get()).getDisplayName());
/*     */         } 
/*     */       } 
/*     */     } else {
/* 275 */       dto.setDisplayName(dto.getDeviceName());
/*     */     } 
/* 277 */     dto.setAlarmType(AlarmType.valueOf(alarmLog.getAlarmSubType()).getName());
/* 278 */     dto.setMessage(alarmLog.getMessage());
/* 279 */     dto.setAlarmLevel(alarmLog.getDegree().intValue());
/* 280 */     dto.setState(alarmLog.getAlarmState());
/* 281 */     dto.setNote(alarmLog.getNote());
/* 282 */     dto.setOperator(alarmLog.getOperator());
/* 283 */     dto.setPriority(alarmLog.getPriority());
/* 284 */     return dto;
/*     */   }
/*     */   
/*     */   private int compare(AlarmMessageDTO o1, AlarmMessageDTO o2) {
/* 288 */     int firstlevel = o1.getAlarmLevel();
/* 289 */     int secondlevel = o2.getAlarmLevel();
/*     */     
/* 291 */     int compareResult = Integer.compare(firstlevel, secondlevel);
/* 292 */     return compareResult;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\AoAlarmService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */