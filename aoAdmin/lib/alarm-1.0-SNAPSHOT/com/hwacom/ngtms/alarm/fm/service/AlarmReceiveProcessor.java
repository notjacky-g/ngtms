/*     */ package com.hwacom.ngtms.alarm.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.HazelcastInstanceNotActiveException;
/*     */ import com.hazelcast.core.IQueue;
/*     */ import com.hwacom.ngtms.alarm.fm.hz.AlarmHzQueue;
/*     */ import com.hwacom.ngtms.alarm.fm.model.AlarmLevelConfig;
/*     */ import com.hwacom.ngtms.alarm.fm.model.AlarmLog;
/*     */ import com.hwacom.ngtms.alarm.fm.repository.AlarmLogRepository;
/*     */ import com.hwacom.ngtms.alarm.fm.shared.dto.AlarmLogDTO;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import org.modelmapper.ModelMapper;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class AlarmReceiveProcessor
/*     */ {
/*  34 */   private static Logger logger = LoggerFactory.getLogger(AlarmReceiveProcessor.class);
/*     */   
/*     */   @Autowired
/*     */   private AlarmLogRepository alarmLogRepository;
/*     */   
/*     */   @Autowired
/*     */   private AlarmPostProcessor alarmPostProcessor;
/*  41 */   private AtomicBoolean keepRunning = new AtomicBoolean(true); @Autowired
/*     */   private AlarmService alarmService; @Autowired
/*     */   private ModelMapper modelMapper; private Runnable alarmProcessThread;
/*     */   
/*     */   public void start() {
/*  46 */     this.keepRunning.set(true);
/*     */     
/*  48 */     Thread thread = new Thread(this.alarmProcessThread, "AlarmProcessThread");
/*     */     
/*  50 */     thread.setDaemon(true);
/*  51 */     thread.start();
/*     */   }
/*     */   
/*     */   public void stop() {
/*  55 */     this.keepRunning.set(false);
/*     */   }
/*     */   public AlarmReceiveProcessor() {
/*  58 */     this.alarmProcessThread = new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/*  62 */           while (AlarmReceiveProcessor.this.keepRunning.get()) {
/*     */             
/*  64 */             try { IQueue<AlarmLogDTO> alarmMessageQueue = HzUtils.getQueue((HzDistObjEnum)AlarmHzQueue.Message);
/*  65 */               ArrayList<AlarmLogDTO> list = new ArrayList<>();
/*     */               
/*  67 */               if (alarmMessageQueue.drainTo(list, 1000) == 0) {
/*  68 */                 TimeUnit.MILLISECONDS.sleep(1000L);
/*     */                 
/*     */                 continue;
/*     */               } 
/*  72 */               Set<AlarmLogDTO> set = new HashSet<>();
/*  73 */               for (AlarmLogDTO dto : list) {
/*  74 */                 set.add(dto);
/*     */               }
/*     */               
/*  77 */               List<AlarmLog> logs = new ArrayList<>();
/*  78 */               for (AlarmLogDTO dto : set) {
/*  79 */                 AlarmLog alarmLog = (AlarmLog)AlarmReceiveProcessor.this.modelMapper.map(dto, AlarmLog.class);
/*     */                 
/*  81 */                 Optional<AlarmLevelConfig> optAlarmLevelConfig = AlarmReceiveProcessor.this.alarmService.getAlarmLevelConfig(alarmLog);
/*  82 */                 if (!optAlarmLevelConfig.isPresent()) {
/*  83 */                   AlarmReceiveProcessor.logger.debug("AlarmLevelConfig is not present. AlarmLog: '{}'", alarmLog
/*  84 */                       .toString());
/*     */                   
/*     */                   continue;
/*     */                 } 
/*  88 */                 AlarmLevelConfig alarmLevelConfig = optAlarmLevelConfig.get();
/*  89 */                 Optional<AlarmLog> optOldAlarmLog = AlarmReceiveProcessor.this.alarmService.findOldAlarmLog(dto);
/*  90 */                 if (!optOldAlarmLog.isPresent()) {
/*  91 */                   AlarmReceiveProcessor.logger.debug("Did not find old AlarmLog. dto: '{}'", dto.toString());
/*     */                 }
/*  93 */                 if (alarmLevelConfig.isSuppress() || AlarmReceiveProcessor.this
/*  94 */                   .alarmService.isAlarmExist(optOldAlarmLog, dto, alarmLevelConfig)) {
/*     */                   continue;
/*     */                 }
/*  97 */                 logs.add(alarmLog);
/*     */               } 
/*     */               
/* 100 */               List<AlarmLog> savedLogs = AlarmReceiveProcessor.this.alarmLogRepository.saveAll(logs);
/* 101 */               AlarmReceiveProcessor.this.alarmService.putToAlarm(logs);
/* 102 */               AlarmReceiveProcessor.this.alarmPostProcessor.process(savedLogs);
/*     */ 
/*     */               
/* 105 */               if (alarmMessageQueue.size() <= 2) { TimeUnit.MILLISECONDS.sleep(100L); continue; }
/* 106 */                TimeUnit.MILLISECONDS.sleep(5L); }
/* 107 */             catch (InterruptedException interruptedException) {  }
/* 108 */             catch (HazelcastInstanceNotActiveException ex)
/*     */             
/*     */             { try {
/* 111 */                 TimeUnit.SECONDS.sleep(1L);
/* 112 */               } catch (Exception exception) {} }
/*     */             
/* 114 */             catch (Throwable ex)
/* 115 */             { AlarmReceiveProcessor.logger.error("Failed to process alarms", ex); }
/*     */           
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\alarm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\alarm\fm\service\AlarmReceiveProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */