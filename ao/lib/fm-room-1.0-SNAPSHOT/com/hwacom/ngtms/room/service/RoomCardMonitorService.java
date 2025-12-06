/*     */ package com.hwacom.ngtms.room.service;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.service.AlarmService;
/*     */ import com.hwacom.ngtms.c.fm.service.AlarmSubType;
/*     */ import com.hwacom.ngtms.c.shared.AlarmSource;
/*     */ import com.hwacom.ngtms.room.fm.hz.RoomHzMap;
/*     */ import com.hwacom.ngtms.room.fm.model.RoomCardConfig;
/*     */ import com.hwacom.ngtms.room.fm.model.RoomCardReaderMappingConfig;
/*     */ import com.hwacom.ngtms.room.fm.repository.RoomCardConfigRepository;
/*     */ import com.hwacom.ngtms.room.shared.CardType;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class RoomCardMonitorService
/*     */ {
/*  33 */   private static final Logger logger = LoggerFactory.getLogger(RoomCardMonitorService.class);
/*     */   
/*     */   @Autowired
/*     */   private RoomCardConfigRepository roomCardConfigRepository;
/*     */   
/*     */   @Autowired
/*     */   private RoomCardReaderConnectorService roomCardReaderConnectorService;
/*     */   @Autowired
/*     */   private AlarmService alarmService;
/*     */   @Autowired
/*     */   private AlarmSubType alarmSubType;
/*     */   
/*     */   public void doRegularCardMonitor() {
/*  46 */     Date now = new Date();
/*     */ 
/*     */     
/*  49 */     List<RoomCardConfig> deleteRoomCardConfigList = this.roomCardConfigRepository.findByCardTypeAndEndDateLessThanEqual(CardType.REGULAR, now);
/*  50 */     logger.debug("deleteRoomCardConfigList:'{}'", deleteRoomCardConfigList);
/*  51 */     Calendar c = Calendar.getInstance();
/*  52 */     c.setTime(now);
/*  53 */     c.add(5, 1);
/*     */     
/*  55 */     List<RoomCardConfig> checkRoomCardConfigList = this.roomCardConfigRepository.findByCardTypeAndEndDateLessThanEqual(CardType.REGULAR, c
/*  56 */         .getTime());
/*  57 */     deleteRoomCard(deleteRoomCardConfigList);
/*  58 */     checkRegularRoomCard(checkRoomCardConfigList);
/*     */ 
/*     */ 
/*     */     
/*  62 */     List<RoomCardConfig> addRoomCardConfigList = this.roomCardConfigRepository.findByCardTypeAndStartDateGreaterThanEqual(CardType.REGULAR, now);
/*  63 */     logger.debug("addRoomCardConfigList: '{}'", addRoomCardConfigList);
/*  64 */     addRoomCard(addRoomCardConfigList);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doTemporaryCardAndUnlimiteCardMonitor() {
/*  69 */     Date now = new Date();
/*     */ 
/*     */     
/*  72 */     List<RoomCardConfig> deleteRoomCardConfigList = this.roomCardConfigRepository.findWithCardTypeAndEndDateBetween(CardType.TEMPORARY, now, 
/*  73 */         getDateAfterOneHour(now));
/*  74 */     deleteRoomCard(deleteRoomCardConfigList);
/*     */ 
/*     */ 
/*     */     
/*  78 */     List<RoomCardConfig> addRoomCardConfigList = this.roomCardConfigRepository.findWithCardTypeAndStartDateBetween(CardType.TEMPORARY, now, 
/*  79 */         getDateBeforOneHour(now));
/*  80 */     addRoomCard(addRoomCardConfigList);
/*     */ 
/*     */ 
/*     */     
/*  84 */     List<RoomCardConfig> unlimiteCardConfigList = this.roomCardConfigRepository.findByCardType(CardType.UNLIMITE);
/*  85 */     addRoomCard(unlimiteCardConfigList);
/*     */   }
/*     */   
/*     */   public void doRoomCardReturnDateMonitor() {
/*     */     try {
/*  90 */       logger.debug("doRoomCardReturnDateMonitor.");
/*  91 */       Date now = new Date();
/*     */       
/*  93 */       List<CardType> cardTypes = new ArrayList<>(Arrays.asList(new CardType[] { CardType.REGULAR, CardType.TEMPORARY }));
/*     */       
/*  95 */       List<RoomCardConfig> NoReturnCards = this.roomCardConfigRepository.findByCardTypeInAndReturnDateIsNullAndExpectedReturnDateLessThan(cardTypes, now);
/*     */ 
/*     */       
/*  98 */       String memo = "卡片超過歸還日期未歸還";
/*  99 */       if (NoReturnCards != null) {
/* 100 */         for (RoomCardConfig cardConfig : NoReturnCards) {
/* 101 */           String cardId = cardConfig.getAba();
/* 102 */           String sessionId = cardId + "_" + cardConfig.getExpectedReturnDate().getTime();
/*     */           
/* 104 */           this.alarmService.sendAlarmWithMemo(AlarmSource.DETECTOR, this.alarmSubType
/*     */               
/* 106 */               .getRoomCardOverReturnDate(), sessionId, new Date(), null, 
/*     */ 
/*     */ 
/*     */               
/* 110 */               Integer.valueOf(5), null, null, null, null, null, null, cardId + memo);
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 120 */     catch (Exception e) {
/* 121 */       logger.error("doRoomCardReturnDateMonitor failded.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addRoomCard(List<RoomCardConfig> addRoomCardConfigList) {
/* 126 */     logger.debug("cardReader add card, card count:'{}'", Integer.valueOf(addRoomCardConfigList.size()));
/*     */     
/* 128 */     IMap<String, RoomCardReaderMappingConfig> roomCardReaderMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardReaderMappingConfig);
/* 129 */     for (RoomCardConfig cardConfig : addRoomCardConfigList) {
/* 130 */       EntryObject eo = (new PredicateBuilder()).getEntryObject();
/* 131 */       PredicateBuilder pb = eo.get("cardId").equal(cardConfig.getAba());
/* 132 */       for (RoomCardReaderMappingConfig mappingConfig : roomCardReaderMap.values((Predicate)pb)) {
/*     */         
/* 134 */         if (!mappingConfig.getLoginCardReader().booleanValue() && this.roomCardReaderConnectorService
/* 135 */           .issueCard(mappingConfig
/* 136 */             .getReaderId(), mappingConfig.getCardId()).booleanValue()) {
/* 137 */           logger.debug("mappingConfig set true:'{}'", mappingConfig);
/* 138 */           mappingConfig.setLoginCardReader(Boolean.valueOf(true));
/* 139 */           roomCardReaderMap.put(mappingConfig.getId(), mappingConfig);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void deleteRoomCard(List<RoomCardConfig> deleteRoomCardConfigList) {
/* 146 */     logger.debug("cardReader delete card, card count:'{}'", Integer.valueOf(deleteRoomCardConfigList.size()));
/*     */     
/* 148 */     IMap<String, RoomCardReaderMappingConfig> roomCardReaderMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardReaderMappingConfig);
/* 149 */     for (RoomCardConfig cardConfig : deleteRoomCardConfigList) {
/* 150 */       EntryObject eo = (new PredicateBuilder()).getEntryObject();
/* 151 */       PredicateBuilder pb = eo.get("cardId").equal(cardConfig.getAba());
/* 152 */       boolean sendAlarm = false;
/* 153 */       for (RoomCardReaderMappingConfig mappingConfig : roomCardReaderMap.values((Predicate)pb)) {
/* 154 */         if (this.roomCardReaderConnectorService.deleteCard(mappingConfig
/* 155 */             .getReaderId(), mappingConfig.getCardId()).booleanValue())
/*     */         {
/* 157 */           if (!mappingConfig.getLoginCardReader().booleanValue() || (mappingConfig
/* 158 */             .getLoginCardReader().booleanValue() && this.roomCardReaderConnectorService
/* 159 */             .deleteCard(mappingConfig
/* 160 */               .getReaderId(), mappingConfig.getCardId()).booleanValue())) {
/* 161 */             logger.debug("delete mappingConfig :'{}'", mappingConfig);
/* 162 */             if (cardConfig.getCardType() == CardType.TEMPORARY) {
/* 163 */               sendAlarm = true;
/*     */             }
/* 165 */             roomCardReaderMap.delete(mappingConfig.getId());
/*     */           } 
/*     */         }
/*     */       } 
/* 169 */       if (sendAlarm) {
/* 170 */         sendAlarm(cardConfig.getCardType(), cardConfig.getEndDate(), cardConfig.getAba());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkRegularRoomCard(List<RoomCardConfig> checkRoomCardConfigList) {
/* 176 */     logger.debug("cardReader regular check card, card count:'{}'", Integer.valueOf(checkRoomCardConfigList.size()));
/*     */     
/* 178 */     IMap<String, RoomCardReaderMappingConfig> roomCardReaderMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardReaderMappingConfig);
/* 179 */     for (RoomCardConfig cardConfig : checkRoomCardConfigList) {
/* 180 */       EntryObject eo = (new PredicateBuilder()).getEntryObject();
/* 181 */       PredicateBuilder pb = eo.get("cardId").equal(cardConfig.getAba());
/* 182 */       boolean sendAlarm = false;
/* 183 */       for (RoomCardReaderMappingConfig mappingConfig : roomCardReaderMap.values((Predicate)pb)) {
/* 184 */         if (mappingConfig.getLoginCardReader().booleanValue()) {
/* 185 */           sendAlarm = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 189 */       if (sendAlarm) {
/* 190 */         sendAlarm(cardConfig.getCardType(), new Date(), cardConfig.getAba());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Date getDateBeforOneHour(Date date) {
/*     */     try {
/* 198 */       Calendar calendar = Calendar.getInstance();
/* 199 */       calendar.setTime(date);
/* 200 */       calendar.add(11, -1);
/*     */       
/* 202 */       return calendar.getTime();
/* 203 */     } catch (Exception e) {
/* 204 */       logger.error("getDate Befor One Hour failed.", e);
/* 205 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Date getDateAfterOneHour(Date date) {
/*     */     try {
/* 212 */       Calendar calendar = Calendar.getInstance();
/* 213 */       calendar.setTime(date);
/* 214 */       calendar.add(11, 1);
/*     */       
/* 216 */       return calendar.getTime();
/* 217 */     } catch (Exception e) {
/* 218 */       return null;
/*     */     } 
/*     */   } private void sendAlarm(CardType carType, Date time, String cardId) {
/*     */     String memo;
/*     */     Integer alarmSubTypeId;
/* 223 */     logger.debug("send alarm, card id : '{}'", cardId);
/*     */ 
/*     */     
/* 226 */     String sessionId = cardId + "_" + time.getTime();
/* 227 */     if (carType == CardType.REGULAR) {
/* 228 */       memo = "即將到期, 如需要請辦理展延";
/* 229 */       alarmSubTypeId = this.alarmSubType.getRegularCardOverDue();
/*     */     } else {
/* 231 */       memo = "到期";
/* 232 */       alarmSubTypeId = this.alarmSubType.getTemporaryCardOverDue();
/*     */     } 
/*     */     
/* 235 */     this.alarmService.sendAlarmWithMemo(AlarmSource.DETECTOR, alarmSubTypeId, sessionId, time, null, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 241 */         Integer.valueOf(5), null, null, null, null, null, null, cardId + memo);
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\service\RoomCardMonitorService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */