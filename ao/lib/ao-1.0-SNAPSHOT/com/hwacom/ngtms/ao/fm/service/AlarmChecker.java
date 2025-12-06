/*     */ package com.hwacom.ngtms.ao.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.IQueue;
/*     */ import com.hwacom.ngtms.alarm.fm.hz.AlarmHzQueue;
/*     */ import com.hwacom.ngtms.alarm.fm.model.AlarmLevelConfig;
/*     */ import com.hwacom.ngtms.alarm.fm.model.AlarmLog;
/*     */ import com.hwacom.ngtms.alarm.fm.service.AlarmService;
/*     */ import com.hwacom.ngtms.alarm.fm.shared.dto.AlarmLogDTO;
/*     */ import com.hwacom.ngtms.alarm.shared.AlarmState;
/*     */ import com.hwacom.ngtms.ao.shared.AlarmType;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AlarmChecker<T>
/*     */ {
/*  27 */   private static final Logger logger = LoggerFactory.getLogger(AlarmChecker.class);
/*     */   
/*     */   @Autowired
/*     */   private AlarmService alarmService;
/*     */   @Autowired
/*     */   private AoAlarmService aoAlarmService;
/*     */   private Collection<T> c;
/*     */   
/*     */   public synchronized void check(Collection<T> c) {
/*  36 */     this.c = c;
/*  37 */     check();
/*     */   }
/*     */   
/*     */   public void check() {
/*  41 */     for (T t : source()) {
/*     */       try {
/*  43 */         if (isAlarm(t)) {
/*  44 */           put(toDTO(t, AlarmState.UNACK_ALM)); continue;
/*     */         } 
/*  46 */         changeStateToUnackRtnIfNeed(t);
/*     */       }
/*  48 */       catch (Exception e) {
/*  49 */         logger.error("Check alarm failed, t='{}'", t.toString(), e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void changeStateToUnackRtnIfNeed(T t) {
/*  55 */     AlarmLogDTO dto = toDTO(t, AlarmState.UNACK_RTN);
/*  56 */     if (dto != null && dto.getDegree() != null) {
/*  57 */       Optional<AlarmLog> optAlarmLog = this.alarmService.findOldAlarmLog(dto);
/*  58 */       if (optAlarmLog.isPresent()) {
/*  59 */         if (((AlarmLog)optAlarmLog.get()).getAlarmState().equals(AlarmState.UNACK_ALM)) {
/*  60 */           returnToNormal(dto);
/*     */         }
/*     */       }
/*  63 */       else if (dto.getDegree().intValue() == 0) {
/*     */         
/*  65 */         List<AlarmLog> unackAlarmLogs = this.alarmService.findAllLevelUnackedAlarmLog(dto.getDeviceName(), dto.getAlarmSubType());
/*  66 */         for (AlarmLog alarmLog : unackAlarmLogs) {
/*  67 */           returnToNormal(
/*  68 */               toDTO(t, AlarmState.UNACK_RTN, Optional.ofNullable(alarmLog.getDegree())));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void returnToNormal(AlarmLogDTO dto) {
/*  76 */     logger.debug("Alarm return back to normal. Dto: '{}'", dto);
/*  77 */     put(dto);
/*     */   }
/*     */   
/*     */   protected Collection<T> source() {
/*  81 */     return this.c;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int changeToAlarmLevel(AlarmType alarmType, double value) {
/*  86 */     Optional<AlarmLevelConfig> optAlarmLevelConfig = this.aoAlarmService.getAlarmLevelConfig(alarmType, value);
/*  87 */     if (!optAlarmLevelConfig.isPresent()) {
/*  88 */       logger.debug("AlarmLevelConfig is not present. AlarmType: '{}', value: '{}'", alarmType
/*     */           
/*  90 */           .toString(), 
/*  91 */           Double.valueOf(value));
/*  92 */       return 0;
/*     */     } 
/*  94 */     AlarmLevelConfig alarmLevelConfig = optAlarmLevelConfig.get();
/*  95 */     return alarmLevelConfig.getAlarmLevel().intValue();
/*     */   }
/*     */   
/*     */   protected abstract boolean isAlarm(T paramT);
/*     */   
/*     */   private void put(AlarmLogDTO dto) {
/* 101 */     IQueue<AlarmLogDTO> alarmMessageQueue = HzUtils.getQueue((HzDistObjEnum)AlarmHzQueue.Message);
/*     */     
/* 103 */     alarmMessageQueue.offer(dto);
/*     */   }
/*     */   
/*     */   private AlarmLogDTO toDTO(T t, AlarmState alarmState) {
/* 107 */     return toDTO(t, alarmState, Optional.empty());
/*     */   }
/*     */   
/*     */   protected abstract AlarmLogDTO toDTO(T paramT, AlarmState paramAlarmState, Optional<Integer> paramOptional);
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\AlarmChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */