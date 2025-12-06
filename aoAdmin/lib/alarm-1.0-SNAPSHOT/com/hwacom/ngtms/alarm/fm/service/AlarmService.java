/*     */ package com.hwacom.ngtms.alarm.fm.service;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.alarm.fm.hz.AlarmHzMap;
/*     */ import com.hwacom.ngtms.alarm.fm.model.Alarm;
/*     */ import com.hwacom.ngtms.alarm.fm.model.AlarmLevelConfig;
/*     */ import com.hwacom.ngtms.alarm.fm.model.AlarmLog;
/*     */ import com.hwacom.ngtms.alarm.fm.repository.AlarmLogRepository;
/*     */ import com.hwacom.ngtms.alarm.fm.shared.dto.AlarmLogDTO;
/*     */ import com.hwacom.ngtms.alarm.shared.AlarmState;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.common.util.IMapLocker;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service("AlarmProcessorService")
/*     */ public class AlarmService
/*     */ {
/*  35 */   private static final Logger logger = LoggerFactory.getLogger(AlarmService.class);
/*     */   @Autowired
/*     */   private AlarmLogRepository alarmLogRepository;
/*     */   
/*     */   public Collection<AlarmLevelConfig> getAlarmLevelConfigs(String alarmSubType) {
/*  40 */     IMap<String, AlarmLevelConfig> alarmLevelMap = HzUtils.getMap((HzDistObjEnum)AlarmHzMap.AlarmLevelConfig);
/*  41 */     EntryObject e = (new PredicateBuilder()).getEntryObject();
/*  42 */     PredicateBuilder predicate = e.get("alarmSubType").equal(alarmSubType);
/*  43 */     return alarmLevelMap.values((Predicate)predicate);
/*     */   }
/*     */   
/*     */   public Optional<AlarmLevelConfig> getAlarmLevelConfig(AlarmLog log) {
/*  47 */     IMap<String, AlarmLevelConfig> alarmLevelMap = HzUtils.getMap((HzDistObjEnum)AlarmHzMap.AlarmLevelConfig);
/*  48 */     EntryObject e = (new PredicateBuilder()).getEntryObject();
/*  49 */     PredicateBuilder predicate = e.get("alarmSubType").equal(log.getAlarmSubType());
/*  50 */     AlarmLevelConfig result = null;
/*  51 */     for (AlarmLevelConfig cfg : alarmLevelMap.values((Predicate)predicate)) {
/*  52 */       if (Objects.equal(cfg.getAlarmLevel(), log.getDegree())) {
/*  53 */         result = cfg;
/*     */       }
/*     */     } 
/*  56 */     return Optional.ofNullable(result);
/*     */   }
/*     */   
/*     */   public List<AlarmLog> findAllLevelUnackedAlarmLog(String deviceName, String alarmSubType) {
/*  60 */     List<AlarmLog> result = new ArrayList<>();
/*  61 */     Collection<AlarmLevelConfig> alarmLevelConfigs = getAlarmLevelConfigs(alarmSubType);
/*  62 */     logger.debug("Find Unacked alarm log. deviceName: '{}', alarmSubType: '{}'", deviceName, alarmSubType);
/*     */     
/*  64 */     for (AlarmLevelConfig alarmLevelConfig : alarmLevelConfigs) {
/*     */       
/*  66 */       Optional<AlarmLog> opt = this.alarmLogRepository.findFirstByDeviceNameAndAlarmSubTypeAndDegreeOrderByTimestampDesc(deviceName, alarmSubType, alarmLevelConfig
/*  67 */           .getAlarmLevel());
/*  68 */       if (opt.isPresent()) {
/*  69 */         AlarmLog alarmLog = opt.get();
/*  70 */         if (alarmLog.getAlarmState() == AlarmState.UNACK_ALM) {
/*  71 */           logger.debug("Unack alarm log found. alarmLevel: '{}'", alarmLevelConfig.getAlarmLevel());
/*  72 */           result.add(alarmLog);
/*     */         } 
/*     */       } 
/*     */     } 
/*  76 */     return result;
/*     */   }
/*     */   
/*     */   public Optional<AlarmLog> findOldAlarmLog(AlarmLogDTO dto) {
/*  80 */     if (dto.getDegree().intValue() == 0)
/*     */     {
/*  82 */       return this.alarmLogRepository.findFirstByDeviceNameAndAlarmSubTypeAndDegreeAndAlarmStateNotOrderByTimestampDesc(dto
/*  83 */           .getDeviceName(), dto.getAlarmSubType(), Integer.valueOf(0), AlarmState.ACK_RTN);
/*     */     }
/*     */     
/*  86 */     Optional<AlarmLog> opt = this.alarmLogRepository.findFirstByDeviceNameAndAlarmSubTypeAndDegreeOrderByTimestampDesc(dto
/*  87 */         .getDeviceName(), dto.getAlarmSubType(), dto.getDegree());
/*  88 */     if (opt.isPresent() && (
/*  89 */       (AlarmLog)opt.get()).getAlarmState() == AlarmState.ACK_RTN) {
/*  90 */       return Optional.empty();
/*     */     }
/*     */     
/*  93 */     return opt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSameAlarm(AlarmLog oldLog, String alarmType, int level, String deviceName) {
/*  99 */     boolean sameSessionId = (oldLog.getAlarmSubType().equals(alarmType) && oldLog.getDeviceName().equals(deviceName));
/* 100 */     if (level == 0) {
/* 101 */       return sameSessionId;
/*     */     }
/* 103 */     return (sameSessionId && oldLog.getDegree().intValue() == level);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAlarmExist(String deviceName, String alarmSubType) {
/*     */     try {
/* 109 */       for (AlarmLevelConfig alarmLevelConfig : getAlarmLevelConfigs(alarmSubType)) {
/*     */ 
/*     */         
/* 112 */         Optional<AlarmLog> optAlarmLog = this.alarmLogRepository.findFirstByDeviceNameAndAlarmSubTypeAndDegreeAndAlarmStateNotOrderByTimestampDesc(deviceName, alarmSubType, alarmLevelConfig
/* 113 */             .getAlarmLevel(), AlarmState.ACK_RTN);
/* 114 */         if (optAlarmLog.isPresent()) {
/* 115 */           return true;
/*     */         }
/*     */       } 
/* 118 */     } catch (Exception e) {
/* 119 */       logger.warn("Check alarm exist failed! deviceName: '{}', alarmSubType: '{}'", new Object[] { deviceName, alarmSubType, e });
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAlarmExist(Optional<AlarmLog> optOldAlarmLog, AlarmLogDTO dto, AlarmLevelConfig alarmLevelConfig) {
/* 130 */     if (optOldAlarmLog.isPresent()) {
/* 131 */       AlarmLog oldAlarmLog = optOldAlarmLog.get();
/* 132 */       if (alarmLevelConfig.getRecordTime() != null && alarmLevelConfig.getRecordTime().booleanValue()) {
/* 133 */         return false;
/*     */       }
/* 135 */       if (!oldAlarmLog.getAlarmState().equals(AlarmState.ACK_RTN) && 
/* 136 */         !dto.getAlarmState().equals(AlarmState.UNACK_RTN)) {
/* 137 */         return true;
/*     */       }
/*     */     } 
/* 140 */     return false;
/*     */   }
/*     */   
/*     */   public void putToAlarm(List<AlarmLog> saveList) {
/* 144 */     IMap<String, Alarm> alarmMap = HzUtils.getMap((HzDistObjEnum)AlarmHzMap.Alarm);
/* 145 */     for (AlarmLog log : saveList) {
/* 146 */       if (log.getAlarmState().equals(AlarmState.UNACK_RTN) && log != null) {
/* 147 */         updateIfAlarmReturn(log);
/*     */         continue;
/*     */       } 
/* 150 */       Optional<AlarmLevelConfig> optAlarmLevelConfig = getAlarmLevelConfig(log);
/* 151 */       if (optAlarmLevelConfig.isPresent()) {
/* 152 */         AlarmLevelConfig config = optAlarmLevelConfig.get();
/* 153 */         if (config.getRecordTime() != null && config.getRecordTime().booleanValue())
/*     */           continue; 
/* 155 */       }  Alarm alarm = new Alarm();
/* 156 */       alarm.setId(UUID.randomUUID().toString());
/* 157 */       alarm.setLogId(log.getId());
/* 158 */       alarm.setState(log.getAlarmState());
/* 159 */       IMapLocker.addOrUpdateMapping(alarmMap, alarm.getId(), alarm);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateIfAlarmReturn(AlarmLog log) {
/* 164 */     IMap<String, Alarm> alarmMap = HzUtils.getMap((HzDistObjEnum)AlarmHzMap.Alarm);
/* 165 */     for (Iterator<Alarm> iterator = alarmMap.values().iterator(); iterator.hasNext(); ) { Alarm alm = iterator.next();
/* 166 */       Optional<AlarmLog> optAlarmLog = this.alarmLogRepository.findById(alm.getLogId());
/* 167 */       optAlarmLog.ifPresent(alarmLog -> {
/*     */             if (isSameAlarm(alarmLog, paramAlarmLog1.getAlarmSubType(), paramAlarmLog1.getDegree().intValue(), paramAlarmLog1.getDeviceName()) && alarmLog.getAlarmState().equals(AlarmState.UNACK_ALM)) {
/*     */               paramAlarm.setLogId(paramAlarmLog1.getId());
/*     */               paramAlarm.setState(paramAlarmLog1.getAlarmState());
/*     */               IMapLocker.addOrUpdateMapping(paramIMap, paramAlarm.getId(), paramAlarm);
/*     */             } 
/*     */           }); }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\alarm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\alarm\fm\service\AlarmService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */