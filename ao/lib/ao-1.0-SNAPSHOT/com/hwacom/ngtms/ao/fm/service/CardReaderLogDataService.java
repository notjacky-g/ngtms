/*     */ package com.hwacom.ngtms.ao.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.ao.fm.hz.AoHzMap;
/*     */ import com.hwacom.ngtms.ao.fm.model.NcuCardReaderLogData;
/*     */ import com.hwacom.ngtms.ao.fm.model.NcuCardTapData;
/*     */ import com.hwacom.ngtms.ao.fm.repository.NcuCardReaderLogDataRepository;
/*     */ import com.hwacom.ngtms.ao.shared.NCUReceiveRecord;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service
/*     */ public class CardReaderLogDataService {
/*  32 */   private static final Logger logger = LoggerFactory.getLogger(CardReaderLogDataService.class);
/*     */   
/*     */   @Autowired
/*     */   HunDureNcuServiceImpl hunDureNCUServiceImpl;
/*     */   @Autowired
/*     */   private RoomOpenFailAlarmChecker roomOpenFailAlarmChecker;
/*     */   @Autowired
/*     */   NcuCardReaderLogDataRepository ncuCardReadeLogDataRepository;
/*  40 */   private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
/*     */ 
/*     */   
/*     */   public void process() {
/*  44 */     IMap<String, DeviceTcConfig> configMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/*  45 */     PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceType").equal("NCU");
/*  46 */     for (DeviceTcConfig config : configMap.values((Predicate)pb)) {
/*     */       
/*     */       try {
/*  49 */         List<NCUReceiveRecord> records = this.hunDureNCUServiceImpl.getNCUCardLog(config.getDeviceName());
/*  50 */         if (records != null && records.size() > 0) {
/*  51 */           List<NcuCardReaderLogData> realReaderList = transferToCardLog(records);
/*  52 */           this.ncuCardReadeLogDataRepository.saveAll(realReaderList);
/*  53 */           tapCardIntime(realReaderList);
/*  54 */           Collection<NcuCardReaderLogData> checkList = transferToSetCardRecords(realReaderList);
/*  55 */           this.roomOpenFailAlarmChecker.check(checkList);
/*     */         } 
/*  57 */       } catch (Exception e) {
/*  58 */         logger.error("Get Card Reader Log Data failed, ncu = '{}'", config.getDeviceName(), e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private List<NcuCardReaderLogData> transferToCardLog(List<NCUReceiveRecord> receiveRecords) throws ParseException {
/*  65 */     List<NcuCardReaderLogData> result = new ArrayList<>();
/*  66 */     for (NCUReceiveRecord record : receiveRecords) {
/*  67 */       NcuCardReaderLogData data = new NcuCardReaderLogData();
/*  68 */       data.setId(UUID.randomUUID().toString());
/*  69 */       data.setNcuId(record.getDeviceName());
/*  70 */       data.setDeviceId(record.getDeviceID().substring(2));
/*  71 */       data.setCardNumber(record.getCardNumber());
/*  72 */       data.setDataTime(this.sdf.parse(record.getDateTime()));
/*  73 */       data.setEventCode(record.getEventCode());
/*  74 */       data.setStatusCode(record.getStatusCode());
/*  75 */       result.add(data);
/*     */     } 
/*  77 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<NcuCardReaderLogData> transferToSetCardRecords(List<NcuCardReaderLogData> receiveRecords) throws ParseException {
/*  82 */     Set<NcuCardReaderLogData> result = new HashSet<>();
/*  83 */     Map<String, NcuCardReaderLogData> mapCard = new HashMap<>();
/*  84 */     if (receiveRecords.size() > 0) {
/*  85 */       receiveRecords.forEach(cardData -> mapCard.put(cardData.getCardNumber(), cardData));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  90 */     result.addAll(mapCard.values());
/*  91 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private void tapCardIntime(List<NcuCardReaderLogData> receiveRecords) {
/*  96 */     IMap<String, NcuCardTapData> tapMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.NcuCardTapData);
/*  97 */     Map<String, NcuCardTapData> tapCardMap = new HashMap<>();
/*     */     
/*  99 */     for (NcuCardReaderLogData data : receiveRecords) {
/* 100 */       if (data.getDeviceId().equals("34") || data
/* 101 */         .getDeviceId().equals("35")) {
/* 102 */         NcuCardTapData tapData = new NcuCardTapData();
/* 103 */         String id = data.getNcuId() + "-" + data.getDeviceId() + "-" + data.getCardNumber();
/* 104 */         tapData.setId(id);
/* 105 */         tapData.setNcuId(data.getNcuId());
/* 106 */         tapData.setDeviceId(data.getDeviceId());
/* 107 */         tapData.setCardNumber(data.getCardNumber());
/* 108 */         tapData.setDataTime(data.getDataTime());
/* 109 */         tapCardMap.put(id, tapData);
/*     */       } 
/*     */     } 
/* 112 */     tapMap.putAll(tapCardMap);
/* 113 */     Map<String, NcuCardTapData> outCardMap = new HashMap<>();
/* 114 */     Map<String, NcuCardTapData> inCardMap = new HashMap<>();
/*     */     
/* 116 */     tapMap
/* 117 */       .values()
/* 118 */       .forEach(tap -> {
/*     */           if (tap.getDeviceId().equals("35")) {
/*     */             outCardMap.put(tap.getId(), tap);
/*     */           } else if (tap.getDeviceId().equals("34")) {
/*     */             inCardMap.put(tap.getId(), tap);
/*     */           } 
/*     */         });
/*     */ 
/*     */     
/* 127 */     if (outCardMap.size() > 0)
/* 128 */       outCardMap
/* 129 */         .values()
/* 130 */         .forEach(remove -> {
/*     */             String removeRecord = remove.getNcuId() + "-" + "34" + "-" + remove.getCardNumber();
/*     */             if (inCardMap.get(removeRecord) != null) {
/*     */               tapMap.remove(remove.getId());
/*     */               tapMap.remove(removeRecord);
/*     */             } 
/*     */           }); 
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\CardReaderLogDataService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */