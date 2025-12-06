/*    */ package com.hwacom.ngtms.ao.fm.service;
/*    */ 
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hwacom.ngtms.alarm.fm.hz.AlarmHzMap;
/*    */ import com.hwacom.ngtms.alarm.fm.model.Alarm;
/*    */ import com.hwacom.ngtms.alarm.fm.model.AlarmLog;
/*    */ import com.hwacom.ngtms.alarm.fm.repository.AlarmLogRepository;
/*    */ import com.hwacom.ngtms.ao.fm.hz.AoHzMap;
/*    */ import com.hwacom.ngtms.ao.fm.model.LifeFaceLockCardData;
/*    */ import com.hwacom.ngtms.ao.fm.model.NcuCardReaderLogData;
/*    */ import com.hwacom.ngtms.ao.fm.model.NcuCardTapData;
/*    */ import com.hwacom.ngtms.ao.fm.repository.NcuCardReaderLogDataRepository;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import com.hwacom.ngtms.common.util.IMapLocker;
/*    */ import java.time.ZonedDateTime;
/*    */ import java.time.temporal.ChronoUnit;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ @Service
/*    */ public class CheckNotProccessService
/*    */ {
/* 30 */   private static final Logger logger = LoggerFactory.getLogger(CheckNotProccessService.class);
/*    */   @Autowired
/*    */   NcuCardReaderLogDataRepository ncuCardReadeLogDataRepository;
/*    */   @Autowired
/*    */   AlarmLogRepository alarmLogRepository;
/*    */   
/*    */   public void process() {
/* 37 */     IMap<String, NcuCardTapData> tapMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.NcuCardTapData);
/*    */     
/* 39 */     IMap<String, LifeFaceLockCardData> lifeFaceLockCardMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.LifeFaceLockCardData);
/* 40 */     IMap<String, Alarm> alarmMap = HzUtils.getMap((HzDistObjEnum)AlarmHzMap.Alarm);
/* 41 */     ZonedDateTime now = ZonedDateTime.now();
/* 42 */     ZonedDateTime todayZero = now.truncatedTo(ChronoUnit.DAYS);
/*    */     
/* 44 */     Date from = Date.from(todayZero.toInstant());
/* 45 */     logger.debug("Check NotProccess todayZero = '{}'", from);
/*    */     
/* 47 */     List<NcuCardTapData> upDateLogDatas = new ArrayList<>();
/*    */     
/* 49 */     for (NcuCardTapData data : tapMap.values()) {
/*    */       try {
/* 51 */         if (data.getDataTime().before(from)) {
/* 52 */           upDateLogDatas.add(data);
/* 53 */           tapMap.remove(data.getId());
/*    */         } 
/* 55 */       } catch (Exception e) {
/* 56 */         logger.error("Check NotProccess Service failed, Tap NCU = '{}'", data.getDeviceId(), e);
/*    */       } 
/*    */     } 
/* 59 */     for (LifeFaceLockCardData data : lifeFaceLockCardMap.values()) {
/*    */       try {
/* 61 */         if (data.getDataTime().before(from)) {
/* 62 */           lifeFaceLockCardMap.remove(data.getId());
/*    */         }
/* 64 */       } catch (Exception e) {
/* 65 */         logger.error("Check NotProccess Service failed, LifeFace NCU = '{}'", data.getNcuId(), e);
/*    */       } 
/*    */     } 
/*    */     
/* 69 */     List<NcuCardReaderLogData> updataLogs = new ArrayList<>();
/* 70 */     if (upDateLogDatas.size() > 0) {
/* 71 */       for (NcuCardTapData tapData : upDateLogDatas) {
/* 72 */         Date dataTime = tapData.getDataTime();
/* 73 */         String ncuId = tapData.getNcuId();
/* 74 */         String deviceId = tapData.getDeviceId();
/* 75 */         String cardNumber = tapData.getCardNumber();
/*    */ 
/*    */         
/* 78 */         NcuCardReaderLogData logData = this.ncuCardReadeLogDataRepository.findByDataTimeAndNcuIdAndDeviceIdAndCardNumber(dataTime, ncuId, deviceId, cardNumber);
/*    */         
/* 80 */         if (logData != null) {
/* 81 */           logData.setEventCode("異常:未刷出");
/* 82 */           updataLogs.add(logData);
/*    */         } 
/*    */       } 
/* 85 */       this.ncuCardReadeLogDataRepository.saveAll(updataLogs);
/*    */     } 
/*    */     
/* 88 */     for (Alarm alarm : alarmMap.values()) {
/* 89 */       Optional<AlarmLog> log = this.alarmLogRepository.findById(alarm.getLogId());
/* 90 */       if (log.isPresent())
/* 91 */         IMapLocker.removeMapping(alarmMap, alarm.getId()); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\CheckNotProccessService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */