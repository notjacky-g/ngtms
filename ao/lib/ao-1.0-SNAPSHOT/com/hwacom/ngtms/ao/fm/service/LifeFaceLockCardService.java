/*     */ package com.hwacom.ngtms.ao.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.ao.fm.hz.AoHzMap;
/*     */ import com.hwacom.ngtms.ao.fm.model.LifeFaceCompareData;
/*     */ import com.hwacom.ngtms.ao.fm.model.LifeFaceLockCardData;
/*     */ import com.hwacom.ngtms.ao.fm.model.LifeFaceLockCardLogData;
/*     */ import com.hwacom.ngtms.ao.fm.model.LifeFaceLogData;
/*     */ import com.hwacom.ngtms.ao.fm.model.NcuCardReaderLogData;
/*     */ import com.hwacom.ngtms.ao.fm.repository.LifeFaceLockCardLogDataRepository;
/*     */ import com.hwacom.ngtms.ao.fm.repository.LifeFaceLogDataRepository;
/*     */ import com.hwacom.ngtms.ao.fm.repository.NcuCardReaderLogDataRepository;
/*     */ import com.hwacom.ngtms.ao.shared.NCUCardData;
/*     */ import com.hwacom.ngtms.ao.shared.dto.LifeFaceDTO;
/*     */ import com.hwacom.ngtms.ao.util.TransferHelper;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationItem;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*     */ import com.hwacom.ngtms.c.fm.service.OpLogger;
/*     */ import com.hwacom.ngtms.c.shared.SubSystem;
/*     */ import com.hwacom.ngtms.room.fm.hz.RoomHzMap;
/*     */ import com.hwacom.ngtms.room.fm.model.RoomCardReaderMappingConfig;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.TimeZone;
/*     */ import java.util.UUID;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @Service
/*     */ public class LifeFaceLockCardService {
/*  44 */   private static final Logger logger = LoggerFactory.getLogger(LifeFaceLockCardService.class);
/*     */   
/*  46 */   private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
/*  47 */   private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */   
/*     */   @Autowired
/*     */   LifeFaceLogDataRepository lifeFaceLogDataRepository;
/*     */   
/*     */   @Autowired
/*     */   NcuCardReaderLogDataRepository ncuCardReadeLogDataRepository;
/*     */   @Autowired
/*     */   LifeFaceLockCardLogDataRepository lifeFaceLockCardLogDataRepository;
/*     */   @Autowired
/*     */   HunDureNcuServiceImpl hunDureNcuServiceImpl;
/*     */   @Autowired
/*     */   OpLogger opLogger;
/*     */   
/*     */   public void processLiveFaceMatchMessage(LifeFaceDTO dto) {
/*     */     try {
/*  63 */       IMap<String, LifeFaceCompareData> lifeFaceCompareMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.LifeFaceCompareData);
/*     */       
/*  65 */       LifeFaceLogData logData = new LifeFaceLogData();
/*  66 */       logData.setId(UUID.randomUUID().toString());
/*  67 */       logData.setDataTime(new Date());
/*  68 */       String roomLocation = dto.getCameraName();
/*  69 */       logData.setCameraName(TransferHelper.getRoomName(roomLocation));
/*  70 */       String matchString = dto.getIsMatch();
/*  71 */       boolean matchB = false;
/*  72 */       if (matchString.equals("true")) {
/*  73 */         matchB = true;
/*     */       }
/*  75 */       logData.setMatch(matchB);
/*  76 */       String dateString = dto.getDate();
/*  77 */       this.sf.setTimeZone(TimeZone.getTimeZone("UTC"));
/*  78 */       Date dateValue = this.sf.parse(dateString);
/*  79 */       logData.setDate(dateValue);
/*  80 */       String card = dto.getCardNumber();
/*  81 */       if (card != null) {
/*  82 */         logData.setCardId(card);
/*     */       }
/*  84 */       this.lifeFaceLogDataRepository.save(logData);
/*     */       
/*  86 */       if (card.length() != 10) {
/*     */         return;
/*     */       }
/*  89 */       String ncuId = TransferHelper.getNcuId(roomLocation);
/*  90 */       String compareId = ncuId + "_" + card;
/*     */       
/*  92 */       if (lifeFaceCompareMap.get(ncuId) == null) {
/*  93 */         LifeFaceCompareData compareData = new LifeFaceCompareData();
/*  94 */         compareData.setId(compareId);
/*  95 */         compareData.setDataTime(dateValue);
/*  96 */         compareData.setCard(card);
/*  97 */         compareData.setMatch(matchB);
/*  98 */         lifeFaceCompareMap.put(compareId, compareData);
/*  99 */         findlockCardAndsaveLog(ncuId, card, dateValue, matchB);
/*     */       }
/*     */       else {
/*     */         
/* 103 */         Date compareDate = ((LifeFaceCompareData)lifeFaceCompareMap.get(ncuId)).getDataTime();
/* 104 */         if (compareDate != null) {
/* 105 */           long diffMinutes = dateValue.getTime() - compareDate.getTime();
/* 106 */           double minutes = Math.floor((diffMinutes / 1000L / 60L));
/* 107 */           if (minutes > 2.0D) {
/* 108 */             LifeFaceCompareData compareData = new LifeFaceCompareData();
/* 109 */             compareData.setId(compareId);
/* 110 */             compareData.setDataTime(dateValue);
/* 111 */             compareData.setCard(card);
/* 112 */             compareData.setMatch(matchB);
/* 113 */             lifeFaceCompareMap.put(compareId, compareData);
/* 114 */             findlockCardAndsaveLog(ncuId, card, dateValue, matchB);
/*     */           } 
/*     */         } 
/*     */       } 
/* 118 */     } catch (Exception e) {
/* 119 */       logger.error("ProcessLiveFaceMatchMessage process LifeFace failed!!", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void findlockCardAndsaveLog(String ncuId, String cardNum, Date date, boolean isMatch) {
/* 125 */     IMap<String, LifeFaceLockCardData> lifeFaceLockCardMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.LifeFaceLockCardData);
/*     */     
/* 127 */     Calendar endDate = Calendar.getInstance();
/* 128 */     endDate.setTime(date);
/* 129 */     Calendar startDate = Calendar.getInstance();
/* 130 */     startDate.setTime(date);
/* 131 */     startDate.add(12, -2);
/* 132 */     Date startTime = startDate.getTime();
/* 133 */     Date endTime = endDate.getTime();
/*     */     
/* 135 */     List<NcuCardReaderLogData> lockCards = this.ncuCardReadeLogDataRepository.findByDataTimeBetweenAndNcuIdAndDeviceIdOrderByDataTimeDesc(startTime, endTime, ncuId, "34");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     NcuCardReaderLogData findCard = lockCards.stream().filter(lockCard -> cardNum.equals(lockCard.getCardNumber())).findAny().orElse(null);
/*     */ 
/*     */     
/* 146 */     if (findCard != null && cardNum.equals(findCard.getCardNumber())) {
/*     */       return;
/*     */     }
/* 149 */     List<String> lockIds = new ArrayList<>();
/* 150 */     Set<String> ncuIds = new HashSet<>();
/*     */     
/* 152 */     if (lockCards != null && lockCards.size() > 0) {
/* 153 */       List<LifeFaceLockCardLogData> lockCardList = new ArrayList<>();
/* 154 */       for (NcuCardReaderLogData data : lockCards) {
/* 155 */         LifeFaceLockCardLogData lockCard = new LifeFaceLockCardLogData();
/* 156 */         lockCard.setId(UUID.randomUUID().toString());
/* 157 */         lockCard.setDataTime(new Date());
/* 158 */         lockCard.setFaceMatch(isMatch);
/* 159 */         lockCard.setLockCard(data.getCardNumber());
/* 160 */         lockCard.setNcuId(ncuId);
/* 161 */         lockCard.setLockCard(false);
/* 162 */         lockCardList.add(lockCard);
/*     */       } 
/* 164 */       this.lifeFaceLockCardLogDataRepository.saveAll(lockCardList);
/*     */       
/* 166 */       for (LifeFaceLockCardLogData logData : lockCardList) {
/* 167 */         LifeFaceLockCardData lockData = new LifeFaceLockCardData();
/* 168 */         String dateString = this.sdf.format(logData.getDataTime());
/* 169 */         lockData.setId(dateString + "+" + logData.getNcuId() + "+" + logData.getLockCard());
/* 170 */         lockData.setDataTime(logData.getDataTime());
/* 171 */         lockData.setNcuId(logData.getNcuId());
/* 172 */         lockData.setLockCard(logData.isLockCard());
/* 173 */         lockData.setFaceMatch(logData.isFaceMatch());
/* 174 */         lifeFaceLockCardMap.put(lockData.getId(), lockData);
/*     */       } 
/*     */     } 
/*     */     
/* 178 */     lockCard(lockIds, ncuIds);
/*     */   }
/*     */ 
/*     */   
/*     */   public void processLiveFaceNotMatchMessage(LifeFaceDTO dto) {
/*     */     try {
/* 184 */       IMap<String, LifeFaceCompareData> lifeFaceCompareMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.LifeFaceCompareData);
/*     */       
/* 186 */       LifeFaceLogData logData = new LifeFaceLogData();
/* 187 */       logData.setId(UUID.randomUUID().toString());
/* 188 */       logData.setDataTime(new Date());
/* 189 */       String roomLocation = dto.getCameraName();
/* 190 */       logData.setCameraName(TransferHelper.getRoomName(roomLocation));
/* 191 */       String matchString = dto.getIsMatch();
/* 192 */       boolean matchB = false;
/* 193 */       if (matchString.equals("true")) {
/* 194 */         matchB = true;
/*     */       }
/* 196 */       logData.setMatch(matchB);
/* 197 */       String dateString = dto.getDate();
/* 198 */       this.sf.setTimeZone(TimeZone.getTimeZone("UTC"));
/* 199 */       Date dateValue = this.sf.parse(dateString);
/* 200 */       logData.setDate(dateValue);
/* 201 */       this.lifeFaceLogDataRepository.save(logData);
/* 202 */       String ncuId = TransferHelper.getNcuId(roomLocation);
/*     */       
/* 204 */       if (lifeFaceCompareMap.get(ncuId) == null) {
/* 205 */         LifeFaceCompareData compareData = new LifeFaceCompareData();
/* 206 */         compareData.setId(ncuId);
/* 207 */         compareData.setDataTime(dateValue);
/* 208 */         compareData.setMatch(matchB);
/* 209 */         lifeFaceCompareMap.put(ncuId, compareData);
/* 210 */         findlockCardAndsaveLog(ncuId, dateValue, matchB);
/*     */       }
/*     */       else {
/*     */         
/* 214 */         Date compareDate = ((LifeFaceCompareData)lifeFaceCompareMap.get(ncuId)).getDataTime();
/* 215 */         if (compareDate != null) {
/* 216 */           long diffMinutes = dateValue.getTime() - compareDate.getTime();
/* 217 */           double minutes = Math.floor((diffMinutes / 1000L / 60L));
/* 218 */           if (minutes > 2.0D) {
/* 219 */             LifeFaceCompareData compareData = new LifeFaceCompareData();
/* 220 */             compareData.setId(ncuId);
/* 221 */             compareData.setDataTime(dateValue);
/* 222 */             compareData.setMatch(matchB);
/* 223 */             lifeFaceCompareMap.put(ncuId, compareData);
/* 224 */             findlockCardAndsaveLog(ncuId, dateValue, matchB);
/*     */           } 
/*     */         } 
/*     */       } 
/* 228 */     } catch (Exception e) {
/* 229 */       logger.error("ProcessLiveFaceNotMatchMessage process LifeFace failed!!", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void findlockCardAndsaveLog(String ncuId, Date date, boolean isMatch) {
/* 236 */     IMap<String, LifeFaceLockCardData> lifeFaceLockCardMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.LifeFaceLockCardData);
/*     */     
/* 238 */     Calendar endDate = Calendar.getInstance();
/* 239 */     endDate.setTime(date);
/* 240 */     Calendar startDate = Calendar.getInstance();
/* 241 */     startDate.setTime(date);
/* 242 */     startDate.add(12, -2);
/* 243 */     Date startTime = startDate.getTime();
/* 244 */     Date endTime = endDate.getTime();
/*     */     
/* 246 */     List<NcuCardReaderLogData> lockCards = this.ncuCardReadeLogDataRepository.findByDataTimeBetweenAndNcuIdAndDeviceIdOrderByDataTimeDesc(startTime, endTime, ncuId, "34");
/*     */     
/* 248 */     logger.debug("findlockCardAndsaveLog!");
/* 249 */     List<String> lockIds = new ArrayList<>();
/* 250 */     Set<String> ncuIds = new HashSet<>();
/*     */     
/* 252 */     if (lockCards != null && lockCards.size() > 0) {
/* 253 */       List<LifeFaceLockCardLogData> lockCardList = new ArrayList<>();
/* 254 */       for (NcuCardReaderLogData data : lockCards) {
/* 255 */         LifeFaceLockCardLogData lockCard = new LifeFaceLockCardLogData();
/* 256 */         lockCard.setId(UUID.randomUUID().toString());
/* 257 */         lockCard.setDataTime(new Date());
/* 258 */         lockCard.setFaceMatch(isMatch);
/* 259 */         lockCard.setLockCard(data.getCardNumber());
/* 260 */         lockCard.setNcuId(ncuId);
/* 261 */         lockCard.setLockCard(false);
/* 262 */         lockCardList.add(lockCard);
/*     */       } 
/* 264 */       this.lifeFaceLockCardLogDataRepository.saveAll(lockCardList);
/*     */       
/* 266 */       for (LifeFaceLockCardLogData logData : lockCardList) {
/* 267 */         LifeFaceLockCardData lockData = new LifeFaceLockCardData();
/* 268 */         String dateString = this.sdf.format(logData.getDataTime());
/* 269 */         lockData.setId(dateString + "+" + logData.getNcuId() + "+" + logData.getLockCard());
/* 270 */         lockData.setDataTime(logData.getDataTime());
/* 271 */         lockData.setNcuId(logData.getNcuId());
/* 272 */         lockData.setLockCard(logData.isLockCard());
/* 273 */         lockData.setFaceMatch(logData.isFaceMatch());
/* 274 */         lifeFaceLockCardMap.put(lockData.getId(), lockData);
/* 275 */         lockIds.add(lockData.getId());
/* 276 */         ncuIds.add(logData.getNcuId() + "-" + logData.getLockCard());
/*     */       } 
/*     */     } 
/*     */     
/* 280 */     lockCard(lockIds, ncuIds);
/*     */   }
/*     */   
/*     */   private void lockCard(List<String> lockIds, Set<String> ncuIds) {
/* 284 */     logger.debug("lockCards LockIds Size = '{}' , ncuIds = '{}' ", Integer.valueOf(lockIds.size()), Integer.valueOf(ncuIds.size()));
/* 285 */     if (lockIds.size() == 0 && ncuIds.size() == 0) {
/*     */       return;
/*     */     }
/* 288 */     for (String ncu : ncuIds) {
/* 289 */       String[] spiltData = ncu.split("-");
/* 290 */       String ncuId = spiltData[0];
/* 291 */       String cardNu = spiltData[1];
/*     */       
/* 293 */       if (ncuId.equals("NCU_18") || ncuId
/* 294 */         .equals("NCU_19") || ncuId
/* 295 */         .equals("NCU_20") || ncuId
/* 296 */         .equals("NCU_21") || ncuId
/* 297 */         .equals("NCU_22") || ncuId
/* 298 */         .equals("NCU_23") || ncuId
/* 299 */         .equals("NCU_24")) {
/*     */         continue;
/*     */       }
/*     */       
/* 303 */       IMap<String, RoomCardReaderMappingConfig> cardMappingMap = HzUtils.getMap((HzDistObjEnum)RoomHzMap.CardReaderMappingConfig);
/*     */       
/* 305 */       boolean lock = false;
/* 306 */       EntryObject deleteCardEo = (new PredicateBuilder()).getEntryObject();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 312 */       PredicateBuilder deleteCardPd = deleteCardEo.get("loginCardReader").equal(Boolean.valueOf(true)).and((Predicate)deleteCardEo.get("readerId").equal(ncuId)).and((Predicate)deleteCardEo.get("cardId").equal(cardNu));
/* 313 */       for (RoomCardReaderMappingConfig mapping : cardMappingMap.values((Predicate)deleteCardPd)) {
/*     */         try {
/* 315 */           NCUCardData cardData = new NCUCardData();
/* 316 */           cardData.setcCardNo(mapping.getCardId());
/* 317 */           int deleteResult = this.hunDureNcuServiceImpl.deleteCard(ncuId, cardData);
/* 318 */           if (deleteResult == 0 || deleteResult == 6) {
/*     */             
/* 320 */             cardMappingMap.delete(mapping.getId());
/* 321 */             this.opLogger.addLog("自動鎖卡", "", SubSystem.ROOM, OperationItem.SET, mapping
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 326 */                 .getReaderId(), new Date(), OperationResult.SUCCESS, "NotToCardReader", "room.deleteCardPermission", new Object[] { mapping
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 331 */                   .getCardId() });
/*     */           } else {
/*     */             
/* 334 */             this.opLogger.addLog("自動鎖卡", "", SubSystem.ROOM, OperationItem.SET, mapping
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 339 */                 .getReaderId(), new Date(), OperationResult.FAILURE, "NotToCardReader", "room.deleteCardPermission", new Object[] { mapping
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 344 */                   .getCardId() });
/*     */           } 
/* 346 */           Thread.sleep(20L);
/* 347 */         } catch (Exception e) {
/* 348 */           lock = true;
/* 349 */           logger.error("Set Lock Card = '{}' failed.", mapping.getCardId());
/*     */         } 
/*     */       } 
/* 352 */       if (!lock) {
/*     */         
/* 354 */         IMap<String, LifeFaceLockCardData> lifeFaceLockCardMap = HzUtils.getMap((HzDistObjEnum)AoHzMap.LifeFaceLockCardData);
/* 355 */         for (String lockId : lockIds) {
/* 356 */           if (lifeFaceLockCardMap.containsKey(lockId)) {
/* 357 */             LifeFaceLockCardData data = (LifeFaceLockCardData)lifeFaceLockCardMap.get(lockId);
/* 358 */             data.setLockCard(true);
/* 359 */             lifeFaceLockCardMap.put(lockId, data);
/*     */           } 
/*     */         } 
/* 362 */         this.lifeFaceLockCardLogDataRepository.updateIsLock(true, ncuId, cardNu);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\LifeFaceLockCardService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */