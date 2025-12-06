/*    */ package com.hwacom.ngtms.alarm.fm.service;
/*    */ 
/*    */ import com.hazelcast.core.HazelcastInstanceNotActiveException;
/*    */ import com.hazelcast.core.IQueue;
/*    */ import com.hwacom.ngtms.alarm.fm.hz.AlarmHzQueue;
/*    */ import com.hwacom.ngtms.alarm.fm.model.AlarmLevelConfig;
/*    */ import com.hwacom.ngtms.alarm.fm.model.AlarmLog;
/*    */ import com.hwacom.ngtms.alarm.fm.repository.AlarmLogRepository;
/*    */ import com.hwacom.ngtms.alarm.fm.shared.dto.AlarmLogDTO;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.concurrent.atomic.AtomicBoolean;
/*    */ import org.modelmapper.ModelMapper;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class AlarmReceiveProcessor
/*    */ {
/* 34 */   private static Logger logger = LoggerFactory.getLogger(AlarmReceiveProcessor.class);
/*    */   @Autowired
/*    */   private AlarmLogRepository alarmLogRepository;
/*    */   @Autowired
/*    */   private AlarmPostProcessor alarmPostProcessor;
/*    */   @Autowired
/*    */   private AlarmService alarmService; @Autowired
/* 41 */   private ModelMapper modelMapper; private AtomicBoolean keepRunning = new AtomicBoolean(true);
/*    */   
/*    */ 
/*    */   public void start()
/*    */   {
/* 46 */     this.keepRunning.set(true);
/*    */     
/* 48 */     Thread thread = new Thread(this.alarmProcessThread, "AlarmProcessThread");
/*    */     
/* 50 */     thread.setDaemon(true);
/* 51 */     thread.start();
/*    */   }
/*    */   
/*    */   public void stop() {
/* 55 */     this.keepRunning.set(false);
/*    */   }
/*    */   
/* 58 */   private Runnable alarmProcessThread = new Runnable()
/*    */   {
/*    */     public void run()
/*    */     {
/* 62 */       while (AlarmReceiveProcessor.this.keepRunning.get()) {
/*    */         try {
/* 64 */           IQueue<AlarmLogDTO> alarmMessageQueue = HzUtils.getQueue(AlarmHzQueue.Message);
/* 65 */           ArrayList<AlarmLogDTO> list = new ArrayList();
/*    */           
/* 67 */           if (alarmMessageQueue.drainTo(list, 1000) == 0) {
/* 68 */             TimeUnit.MILLISECONDS.sleep(1000L);
/*    */           }
/*    */           else
/*    */           {
/* 72 */             Set<AlarmLogDTO> set = new HashSet();
/* 73 */             for (Iterator localIterator = list.iterator(); localIterator.hasNext();) { dto = (AlarmLogDTO)localIterator.next();
/* 74 */               set.add(dto);
/*    */             }
/*    */             AlarmLogDTO dto;
/* 77 */             Object logs = new ArrayList();
/* 78 */             for (AlarmLogDTO dto : set) {
/* 79 */               AlarmLog alarmLog = (AlarmLog)AlarmReceiveProcessor.this.modelMapper.map(dto, AlarmLog.class);
/*    */               
/* 81 */               Optional<AlarmLevelConfig> optAlarmLevelConfig = AlarmReceiveProcessor.this.alarmService.getAlarmLevelConfig(alarmLog);
/* 82 */               if (!optAlarmLevelConfig.isPresent()) {
/* 83 */                 AlarmReceiveProcessor.logger.debug("AlarmLevelConfig is not present. AlarmLog: '{}'", alarmLog
/* 84 */                   .toString());
/*    */               }
/*    */               else
/*    */               {
/* 88 */                 AlarmLevelConfig alarmLevelConfig = (AlarmLevelConfig)optAlarmLevelConfig.get();
/* 89 */                 Optional<AlarmLog> optOldAlarmLog = AlarmReceiveProcessor.this.alarmService.findOldAlarmLog(dto);
/* 90 */                 if (!optOldAlarmLog.isPresent()) {
/* 91 */                   AlarmReceiveProcessor.logger.debug("Did not find old AlarmLog. dto: '{}'", dto.toString());
/*    */                 }
/* 93 */                 if ((!alarmLevelConfig.isSuppress()) && 
/* 94 */                   (!AlarmReceiveProcessor.this.alarmService.isAlarmExist(optOldAlarmLog, dto, alarmLevelConfig)))
/*    */                 {
/*    */ 
/* 97 */                   ((List)logs).add(alarmLog); }
/*    */               }
/*    */             }
/* :0 */             List<AlarmLog> savedLogs = AlarmReceiveProcessor.this.alarmLogRepository.saveAll((Iterable)logs);
/* :1 */             AlarmReceiveProcessor.this.alarmService.putToAlarm((List)logs);
/* :2 */             AlarmReceiveProcessor.this.alarmPostProcessor.process(savedLogs);
/*    */             
/*    */ 
/* :5 */             if (alarmMessageQueue.size() <= 2) TimeUnit.MILLISECONDS.sleep(100L); else {
/* :6 */               TimeUnit.MILLISECONDS.sleep(5L);
/*    */             }
/*    */           }
/*    */         } catch (InterruptedException localInterruptedException) {}catch (HazelcastInstanceNotActiveException ex) {
/*    */           try {
/* ;1 */             TimeUnit.SECONDS.sleep(1L);
/*    */           }
/*    */           catch (Exception localException) {}
/*    */         } catch (Throwable ex) {
/* ;5 */           AlarmReceiveProcessor.logger.error("Failed to process alarms", ex);
/*    */         }
/*    */       }
/*    */     }
/*    */   };
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\alarm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\alarm\fm\service\AlarmReceiveProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */