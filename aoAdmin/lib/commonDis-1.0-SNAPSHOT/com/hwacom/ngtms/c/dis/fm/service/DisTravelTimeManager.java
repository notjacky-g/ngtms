/*     */ package com.hwacom.ngtms.c.dis.fm.service;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.ComparisonChain;
/*     */ import com.google.common.math.DoubleMath;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hazelcast.query.SqlPredicate;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.dis.fm.hz.DisHzMap;
/*     */ import com.hwacom.ngtms.c.dis.fm.model.DisDefaultTravelTime;
/*     */ import com.hwacom.ngtms.c.dis.fm.model.DisTravelTime;
/*     */ import com.hwacom.ngtms.c.dis.fm.model.DisTravelTimeConfig;
/*     */ import com.hwacom.ngtms.c.dis.fm.model.DisTravelTimeDivisionConfig;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadDivision;
/*     */ import com.hwacom.ngtms.c.fm.model.RoadSection;
/*     */ import com.hwacom.ngtms.c.shared.AreaType;
/*     */ import com.hwacom.ngtms.c.shared.DivisionType;
/*     */ import java.io.Serializable;
/*     */ import java.math.RoundingMode;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DisTravelTimeManager
/*     */ {
/*     */   public static final String DIVISION_NAME = "divisionName";
/*     */   public static final String LINE_ID = "lineId";
/*     */   private static final String AREA_TYPE = "areaType";
/*     */   public static final String MILEAGE = "mileage";
/*     */   public static final String ENABLE = "enable";
/*     */   private static final int MIN_SPEED_METER_MIN = 1000;
/*     */   private static final int MAX_SPEED_METER_MIN = 1666;
/*     */   public static final String REPLACE_TRAVELTIME = "@";
/*     */   public static final double TRAVELTIME_UNIT = 60.0D;
/*  67 */   private static Logger logger = LoggerFactory.getLogger(DisTravelTimeManager.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<RoadDivision> getRoadDivisions(DeviceTcConfig deviceConfig) throws NullPointerException {
/*  79 */     Preconditions.checkNotNull(deviceConfig.getSectionId(), "無法取得  %s 的 SectionId", deviceConfig);
/*  80 */     return getRoadDivisions(deviceConfig.getSectionId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<RoadDivision> getRoadDivisions(String sectionId) {
/*  90 */     IMap<String, RoadSection> roadSectionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadSection);
/*  91 */     IMap<String, RoadDivision> roadDivisionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadDivision);
/*     */     
/*  93 */     RoadSection roadSection = (RoadSection)roadSectionMap.get(sectionId);
/*     */     
/*  95 */     logger.debug("roadSection {} ", roadSection);
/*     */     
/*  97 */     RoadDivision startRoadDivision = (RoadDivision)roadDivisionMap.get(roadSection.getStartDivisionId());
/*     */     
/*  99 */     RoadDivision endRoadDivision = (RoadDivision)roadDivisionMap.get(roadSection.getEndDivisionId());
/*     */     
/* 101 */     Boolean increment = Boolean.valueOf(false);
/*     */     
/* 103 */     if (startRoadDivision.getMileage().intValue() < endRoadDivision.getMileage().intValue())
/*     */     {
/* 105 */       increment = Boolean.valueOf(true);
/*     */     }
/*     */ 
/*     */     
/* 109 */     return getRoadDivisions(startRoadDivision, Optional.of(increment));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<RoadDivision> getRoadDivisions(RoadDivision roadDivision, Optional<Boolean> increment) {
/* 122 */     IMap<String, RoadDivision> roadDivisionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadDivision);
/*     */ 
/*     */     
/* 125 */     Collection<RoadDivision> collection = roadDivisionMap.values((Predicate)getRoadDivisionPredicate(roadDivision, increment));
/*     */ 
/*     */     
/* 128 */     Collection<RoadDivision> collection2 = roadDivisionMap.values((Predicate)getBoundaryRoadDivisionPredicate(roadDivision, increment));
/*     */     
/* 130 */     logger.debug(" getRoadDivision {}", collection);
/*     */     
/* 132 */     logger.debug(" getBoundaryRoadDivision {}", collection2);
/*     */     
/* 134 */     List<RoadDivision> list = new ArrayList<>();
/*     */     
/* 136 */     list.addAll(collection);
/* 137 */     list.addAll(collection2);
/*     */     
/* 139 */     if (!list.isEmpty()) {
/*     */       
/* 141 */       MileageAscOrderComparator lineIdMileageAscOrderComparator = new MileageAscOrderComparator();
/*     */       
/* 143 */       if (increment.isPresent())
/*     */       {
/* 145 */         if (((Boolean)increment.get()).booleanValue()) {
/* 146 */           Collections.sort(list, lineIdMileageAscOrderComparator);
/*     */         } else {
/* 148 */           Collections.sort(list, Collections.reverseOrder(lineIdMileageAscOrderComparator));
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 153 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<RoadDivision> getChangeLineRoadDivisions(RoadDivision roadDivision, Optional<Boolean> increment) throws IllegalArgumentException {
/* 169 */     List<RoadDivision> result = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 174 */     boolean expression = (DivisionType.C.equals(roadDivision.getDivisionType()) || DivisionType.I.equals(roadDivision.getDivisionType()));
/*     */     
/* 176 */     Preconditions.checkArgument(expression, "此路段分割點非交流道");
/*     */     
/* 178 */     IMap<String, RoadDivision> roadDivisionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadDivision);
/*     */ 
/*     */     
/* 181 */     Collection<RoadDivision> changeLineRoadDivisions = roadDivisionMap.values((Predicate)getChangeLineRoadDivisionPredicate(roadDivision));
/*     */ 
/*     */ 
/*     */     
/* 185 */     for (RoadDivision changeLineRoadDivision : changeLineRoadDivisions) {
/*     */ 
/*     */       
/* 188 */       Collection<RoadDivision> collection = roadDivisionMap.values((Predicate)
/* 189 */           getRoadDivisionPredicate(changeLineRoadDivision, Optional.empty()));
/*     */ 
/*     */       
/* 192 */       Collection<RoadDivision> collection2 = roadDivisionMap.values((Predicate)
/* 193 */           getBoundaryRoadDivisionPredicate(changeLineRoadDivision, Optional.empty()));
/*     */       
/* 195 */       logger.debug(" getRoadDivision {}", collection);
/*     */       
/* 197 */       logger.debug(" getBoundaryRoadDivision {}", collection2);
/*     */       
/* 199 */       List<RoadDivision> list = new ArrayList<>();
/*     */       
/* 201 */       list.addAll(collection);
/* 202 */       list.addAll(collection2);
/*     */       
/* 204 */       if (!list.isEmpty()) {
/*     */         
/* 206 */         MileageAscOrderComparator lineIdMileageAscOrderComparator = new MileageAscOrderComparator();
/*     */         
/* 208 */         if (increment.isPresent())
/*     */         {
/* 210 */           if (((Boolean)increment.get()).booleanValue()) {
/* 211 */             Collections.sort(list, lineIdMileageAscOrderComparator);
/*     */           } else {
/* 213 */             Collections.sort(list, 
/* 214 */                 Collections.reverseOrder(lineIdMileageAscOrderComparator));
/*     */           } 
/*     */         }
/*     */       } 
/* 218 */       result.addAll(list);
/*     */     } 
/* 220 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PredicateBuilder getRoadDivisionPredicate(RoadDivision roadDivision, Optional<Boolean> increment) {
/* 233 */     EntryObject e = (new PredicateBuilder()).getEntryObject();
/* 234 */     PredicateBuilder predicate = e.get("lineId").equal(roadDivision.getLineId());
/*     */     
/* 236 */     if (increment.isPresent()) {
/* 237 */       if (((Boolean)increment.get()).booleanValue()) {
/* 238 */         predicate.and((Predicate)e.get("mileage").greaterEqual(roadDivision.getMileage()));
/*     */       } else {
/* 240 */         predicate.and((Predicate)e.get("mileage").lessEqual(roadDivision.getMileage()));
/*     */       } 
/*     */     }
/* 243 */     logger.debug("getRoadDivisionPredicate {}", predicate.toString());
/* 244 */     return predicate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PredicateBuilder getBoundaryRoadDivisionPredicate(RoadDivision roadDivision, Optional<Boolean> increment) {
/* 256 */     EntryObject e = (new PredicateBuilder()).getEntryObject();
/* 257 */     PredicateBuilder predicate = e.get("lineId").equal(roadDivision.getLineId());
/*     */     
/* 259 */     predicate.and((Predicate)e.get("areaType").notEqual((Comparable)AreaType.C));
/*     */     
/* 261 */     predicate.and((Predicate)e.get("travelTimeVisible").equal(Boolean.TRUE));
/*     */     
/* 263 */     if (increment.isPresent()) {
/* 264 */       if (((Boolean)increment.get()).booleanValue()) {
/* 265 */         predicate.and((Predicate)e.get("mileage").greaterEqual(roadDivision.getMileage()));
/*     */       } else {
/* 267 */         predicate.and((Predicate)e.get("mileage").lessEqual(roadDivision.getMileage()));
/*     */       } 
/*     */     }
/* 270 */     logger.debug("getRoadDivisionPredicate {}", predicate.toString());
/* 271 */     return predicate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PredicateBuilder getChangeLineRoadDivisionPredicate(RoadDivision roadDivision) {
/* 285 */     EntryObject e = (new PredicateBuilder()).getEntryObject();
/*     */ 
/*     */ 
/*     */     
/* 289 */     PredicateBuilder predicate = e.get("divisionName").equal(roadDivision.getDivisionName()).and((Predicate)e.get("lineId").notEqual(roadDivision.getLineId()));
/*     */     
/* 291 */     return predicate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class MileageAscOrderComparator
/*     */     implements Serializable, Comparator<RoadDivision>
/*     */   {
/*     */     private static final long serialVersionUID = 1092355229003998207L;
/*     */ 
/*     */ 
/*     */     
/*     */     public int compare(RoadDivision rd, RoadDivision that) {
/* 305 */       return ComparisonChain.start().compare(rd.getMileage(), that.getMileage()).result();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<RoadSection> getRoadSectionsAtSameRoadLine(String startRoadDivisionId, String endRoadDivisionId) throws IllegalArgumentException, NullPointerException {
/* 322 */     List<RoadSection> result = new ArrayList<>();
/*     */     
/* 324 */     IMap<String, RoadSection> roadSectionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadSection);
/* 325 */     IMap<String, RoadDivision> roadDivisionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadDivision);
/* 326 */     RoadDivision startRoadDivision = (RoadDivision)roadDivisionMap.get(startRoadDivisionId);
/*     */     
/* 328 */     RoadDivision endRoadDivision = (RoadDivision)roadDivisionMap.get(endRoadDivisionId);
/*     */ 
/*     */     
/* 331 */     Preconditions.checkNotNull(startRoadDivision, "無法取得Id為  %s 的Division", startRoadDivisionId);
/* 332 */     Preconditions.checkNotNull(endRoadDivision, "無法取得Id為   %s 的Division", endRoadDivisionId);
/*     */     
/* 334 */     Preconditions.checkArgument(startRoadDivision
/* 335 */         .getLineId().equals(endRoadDivision.getLineId()), "此路線不是同路線");
/*     */ 
/*     */     
/* 338 */     Collection<RoadDivision> roadDivisions = roadDivisionMap.values((Predicate)
/* 339 */         getRoadDivisions(startRoadDivision, endRoadDivision));
/*     */ 
/*     */     
/* 342 */     List<RoadDivision> roadDivisionList = new ArrayList<>();
/*     */     
/* 344 */     if (roadDivisions != null) {
/*     */       
/* 346 */       roadDivisionList.addAll(roadDivisions);
/*     */       
/* 348 */       MileageAscOrderComparator lineIdMileageAscOrderComparator = new MileageAscOrderComparator();
/*     */       
/* 350 */       if (startRoadDivision.getMileage().intValue() < endRoadDivision.getMileage().intValue()) {
/* 351 */         Collections.sort(roadDivisionList, lineIdMileageAscOrderComparator);
/*     */       } else {
/*     */         
/* 354 */         Collections.sort(roadDivisionList, 
/* 355 */             Collections.reverseOrder(lineIdMileageAscOrderComparator));
/*     */       } 
/*     */       
/* 358 */       for (int i = 0; i < roadDivisionList.size() - 1; i++) {
/*     */ 
/*     */ 
/*     */         
/* 362 */         Collection<RoadSection> collection = roadSectionMap.values((Predicate)
/* 363 */             getRoadSection(roadDivisionList.get(i), roadDivisionList.get(i + 1)));
/*     */         
/* 365 */         Iterator<RoadSection> iterator = collection.iterator();
/*     */         
/* 367 */         if (iterator.hasNext()) {
/*     */           
/* 369 */           RoadSection roadSection = iterator.next();
/* 370 */           result.add(roadSection);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 375 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private PredicateBuilder getRoadDivisions(RoadDivision startRoadDivision, RoadDivision endRoadDivision) {
/* 381 */     EntryObject e = (new PredicateBuilder()).getEntryObject();
/* 382 */     PredicateBuilder predicate = e.get("lineId").equal(startRoadDivision.getLineId());
/*     */     
/* 384 */     if (startRoadDivision.getMileage().intValue() < endRoadDivision.getMileage().intValue()) {
/* 385 */       predicate
/* 386 */         .and((Predicate)e.get("mileage").greaterEqual(startRoadDivision.getMileage()))
/* 387 */         .and((Predicate)e.get("mileage").lessEqual(endRoadDivision.getMileage()));
/*     */     } else {
/* 389 */       predicate
/* 390 */         .and((Predicate)e.get("mileage").lessEqual(startRoadDivision.getMileage()))
/* 391 */         .and((Predicate)e.get("mileage").greaterEqual(endRoadDivision.getMileage()));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 396 */     return predicate;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private PredicateBuilder getRoadSection(RoadDivision startRoadDivision, RoadDivision endRoadDivision) {
/* 402 */     EntryObject e = (new PredicateBuilder()).getEntryObject();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 407 */     PredicateBuilder predicate = e.get("lineid").equal(startRoadDivision.getLineId()).and((Predicate)e.get("startDivisionId").equal(startRoadDivision.getDivisionId())).and((Predicate)e.get("endDivisionId").equal(endRoadDivision.getDivisionId()));
/* 408 */     return predicate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RoadSection getTcRoadSection(String deviceName) throws NullPointerException {
/* 419 */     IMap<String, RoadSection> roadSectionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadSection);
/* 420 */     IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 421 */     DeviceTcConfig deviceConfig = (DeviceTcConfig)deviceConfigMap.get(deviceName);
/* 422 */     Preconditions.checkNotNull(deviceConfig, "無法取得  %s 的設備資訊", deviceName);
/* 423 */     RoadSection roadSection = (RoadSection)roadSectionMap.get(deviceConfig.getSectionId());
/* 424 */     return roadSection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTravelTimeMessage(String message, String replacement, int travelTime) {
/* 437 */     String result = "";
/*     */     
/* 439 */     if (StringUtils.isBlank(replacement))
/*     */     {
/* 441 */       replacement = "@";
/*     */     }
/*     */     
/* 444 */     result = message.replace(replacement, String.valueOf(travelTime));
/*     */     
/* 446 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<DisTravelTimeConfig> getTravelTimeConfig(String sql) {
/* 458 */     IMap<String, DisTravelTimeConfig> disTravelTimeConfigMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.TravelTimeConfig);
/* 459 */     Collection<DisTravelTimeConfig> configs = disTravelTimeConfigMap.values((Predicate)new SqlPredicate(sql));
/* 460 */     List<DisTravelTimeConfig> travelTimeConfig = new ArrayList<>(configs);
/* 461 */     return travelTimeConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int validateTravelTimeByMin(DisTravelTimeConfig disTravelTimeConfig, int travelTime) {
/* 474 */     int maxTraveltime = Math.max(disTravelTimeConfig.getMaxTravelTime().intValue(), disTravelTimeConfig.getMinTravelTime().intValue());
/*     */     
/* 476 */     int minTraveltime = Math.min(disTravelTimeConfig.getMaxTravelTime().intValue(), disTravelTimeConfig.getMinTravelTime().intValue());
/*     */     
/* 478 */     if (travelTime > maxTraveltime) {
/* 479 */       travelTime = maxTraveltime;
/*     */     }
/*     */     
/* 482 */     if (travelTime < minTraveltime) {
/* 483 */       travelTime = minTraveltime;
/*     */     }
/*     */     
/* 486 */     return travelTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisTravelTime genDisTravelTime(DisTravelTimeConfig disTravelTimeConfig, int travelTime, int validateTravelTime, int delayTime, RoadDivision targetRoadDivision, int maxTraveltime, int minTraveltime) {
/* 508 */     return (new DisTravelTime.Builder(disTravelTimeConfig.getDeviceName(), disTravelTimeConfig.getBoardId())).dataTime(new Date()).travelTime(Integer.valueOf(travelTime)).adjustTravelTime(Integer.valueOf(validateTravelTime)).tcDisplayTravelTime(Integer.valueOf(validateTravelTime + delayTime)).outOfBounds((travelTime < minTraveltime || travelTime > maxTraveltime)).minTravelTime(Integer.valueOf(minTraveltime)).maxTravelTime(Integer.valueOf(maxTraveltime)).divisionName(targetRoadDivision.getDivisionName()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<RoadSection> getDivisionConfigRoadSections(DisTravelTimeConfig disTravelTimeConfig) throws NullPointerException {
/* 524 */     String deviceName = disTravelTimeConfig.getDeviceName();
/*     */     
/* 526 */     List<DisTravelTimeDivisionConfig> disTravelTimeDivisionConfigs = disTravelTimeConfig.getDisTravelTimeDivisionConfig();
/*     */     
/* 528 */     List<RoadSection> roadSections = new ArrayList<>();
/*     */     
/* 530 */     RoadSection tcRoadSection = getTcRoadSection(deviceName);
/*     */     
/* 532 */     roadSections.add(tcRoadSection);
/*     */     
/* 534 */     if (disTravelTimeDivisionConfigs.size() == 1) {
/*     */       
/* 536 */       Iterator<DisTravelTimeDivisionConfig> iterator = disTravelTimeDivisionConfigs.iterator();
/* 537 */       if (iterator.hasNext()) {
/*     */         
/* 539 */         DisTravelTimeDivisionConfig division = iterator.next();
/* 540 */         String endDivisionId = tcRoadSection.getEndDivisionId();
/*     */         
/* 542 */         String divisionId = division.getDivisionId();
/* 543 */         if (endDivisionId.equals(divisionId)) {
/* 544 */           return roadSections;
/*     */         }
/* 546 */         roadSections.addAll(
/* 547 */             getRoadSectionsAtSameRoadLine(endDivisionId, divisionId));
/* 548 */         return roadSections;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 553 */     Collections.sort(disTravelTimeDivisionConfigs, new OrderAscComparator());
/*     */     
/* 555 */     List<String> roadDivisionIdAndTcList = new ArrayList<>();
/*     */     
/* 557 */     roadDivisionIdAndTcList.add(tcRoadSection.getStartDivisionId());
/*     */     
/* 559 */     for (DisTravelTimeDivisionConfig s : disTravelTimeDivisionConfigs) {
/* 560 */       roadDivisionIdAndTcList.add(s.getDivisionId());
/*     */     }
/*     */     
/* 563 */     roadSections.addAll(getRoadSections(roadDivisionIdAndTcList));
/*     */     
/* 565 */     return roadSections;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<RoadSection> getRoadSections(List<String> roadDivisionIdAndTcList) {
/* 575 */     List<RoadSection> roadSections = new ArrayList<>();
/* 576 */     IMap<String, RoadDivision> roadDivisionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadDivision);
/* 577 */     for (int i = 0; i < roadDivisionIdAndTcList.size() - 1; i++) {
/*     */       
/* 579 */       String startDivisionId = roadDivisionIdAndTcList.get(i);
/* 580 */       String endDivisionId = roadDivisionIdAndTcList.get(i + 1);
/*     */       
/* 582 */       RoadDivision startRoadDivision = (RoadDivision)roadDivisionMap.get(startDivisionId);
/* 583 */       RoadDivision endRoadDivision = (RoadDivision)roadDivisionMap.get(endDivisionId);
/*     */ 
/*     */       
/* 586 */       Preconditions.checkNotNull(startRoadDivision, "無法取得Id為  %s 的Division", startDivisionId);
/* 587 */       Preconditions.checkNotNull(endRoadDivision, "無法取得Id為   %s 的Division", endDivisionId);
/*     */       
/* 589 */       if (startRoadDivision.getDivisionName().equals(endRoadDivision.getDivisionName())) {
/*     */ 
/*     */ 
/*     */         
/* 593 */         logger.debug("startRoadDivision {},endRoadDivision {} doesn't count", startRoadDivision, endRoadDivision);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 600 */       else if (startRoadDivision.getLineId().equals(endRoadDivision.getLineId())) {
/* 601 */         roadSections.addAll(
/* 602 */             getRoadSectionsAtSameRoadLine(startDivisionId, endDivisionId));
/*     */ 
/*     */       
/*     */       }
/* 606 */       else if (DivisionType.C.equals(startRoadDivision.getDivisionType()) || DivisionType.I
/* 607 */         .equals(startRoadDivision.getDivisionType())) {
/*     */ 
/*     */ 
/*     */         
/* 611 */         Collection<RoadDivision> changeLineRoadDivisions = roadDivisionMap.values((Predicate)getChangeLineRoadDivisionPredicate(startRoadDivision));
/*     */         
/* 613 */         Iterator<RoadDivision> iterator = changeLineRoadDivisions.iterator();
/*     */         
/* 615 */         if (iterator.hasNext())
/*     */         {
/* 617 */           RoadDivision changeLineRoadDivision = iterator.next();
/*     */           try {
/* 619 */             roadSections.addAll(
/* 620 */                 getRoadSectionsAtSameRoadLine(changeLineRoadDivision
/* 621 */                   .getDivisionId(), endDivisionId));
/* 622 */           } catch (IllegalArgumentException e) {
/* 623 */             logger.error("轉換系統交流道失敗", e);
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 630 */         logger.warn("{} 路段轉換路線失敗 ,無法與{} 位於同一路線", startDivisionId, endDivisionId);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 635 */     return roadSections;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class OrderAscComparator
/*     */     implements Comparator<DisTravelTimeDivisionConfig>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1166853438859478104L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compare(DisTravelTimeDivisionConfig ccd, DisTravelTimeDivisionConfig that) {
/* 650 */       return ComparisonChain.start().compare(ccd.getOrder(), that.getOrder()).result();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Optional<Integer> calculateTravelTimeMin(DisTravelTimeConfig disTravelTimeConfig) {
/* 664 */     List<RoadSection> roadSections = getDivisionConfigRoadSections(disTravelTimeConfig);
/*     */     
/* 666 */     if (roadSections == null || roadSections.isEmpty()) {
/* 667 */       logger.warn("calculateTravelTimeMin disTravelTimeConfig can't get RoadSections  by DisTravelTimeDivisionConfig,disTravelTimeConfig : {}  ", disTravelTimeConfig
/*     */           
/* 669 */           .getId());
/* 670 */       return Optional.empty();
/*     */     } 
/* 672 */     String deviceName = disTravelTimeConfig.getDeviceName();
/*     */     
/* 674 */     RoadSection deviceRoadSection = getTcRoadSection(deviceName);
/* 675 */     int travelTime = calculateDeviceTravelTimeSec(deviceName);
/*     */     
/* 677 */     roadSections.remove(deviceRoadSection);
/*     */     
/* 679 */     if (!roadSections.isEmpty()) {
/*     */       
/* 681 */       Optional<Integer> calculateTravelTimeSec = calculateTravelTimeSec(roadSections);
/* 682 */       if (!calculateTravelTimeSec.isPresent()) {
/* 683 */         logger.warn("calculateTravelTimeSec is null , disTravelTimeConfig {}  ", disTravelTimeConfig
/*     */             
/* 685 */             .getId());
/* 686 */         return Optional.empty();
/*     */       } 
/*     */       
/* 689 */       travelTime += ((Integer)calculateTravelTimeSec.get()).intValue();
/*     */     } 
/*     */     
/* 692 */     int travelTimeMin = convertTravelTimeSec2Min(travelTime);
/*     */     
/* 694 */     return Optional.ofNullable(Integer.valueOf(travelTimeMin));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int convertTravelTimeSec2Min(int travelTime) {
/* 704 */     return DoubleMath.roundToInt(travelTime / 60.0D, RoundingMode.UP);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract int calculateDeviceTravelTimeSec(String paramString);
/*     */ 
/*     */   
/*     */   public Boolean saveDisTravelTimeConfig(DisTravelTimeConfig disTravelTimeConfig) {
/*     */     try {
/* 713 */       IMap<String, DisTravelTimeConfig> disTravelTimeConfigMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.TravelTimeConfig);
/* 714 */       disTravelTimeConfigMap.put(disTravelTimeConfig.getId(), disTravelTimeConfig);
/* 715 */       return Boolean.TRUE;
/* 716 */     } catch (Exception e) {
/* 717 */       logger.error("saveDisTravelTimeConfig ", e);
/* 718 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean deleteDisTravelTimeConfig(String id) {
/*     */     try {
/* 725 */       IMap<String, DisTravelTimeConfig> disTravelTimeConfigMap = HzUtils.getMap((HzDistObjEnum)DisHzMap.TravelTimeConfig);
/* 726 */       disTravelTimeConfigMap.delete(id);
/* 727 */       return Boolean.TRUE;
/* 728 */     } catch (Exception e) {
/* 729 */       logger.error("deleteDisTravelTimeConfig ", e);
/* 730 */       return Boolean.FALSE;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Optional<Integer> calculateTravelTimeSec(List<RoadSection> paramList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int calculateTravelTimeDefaultValue(DeviceTcConfig deviceConfig, RoadDivision targetRoadDivision, int speedLimit) {
/* 752 */     return Math.abs((deviceConfig.getMilepost().intValue() - targetRoadDivision.getMileage().intValue()) / speedLimit);
/*     */   }
/*     */ 
/*     */   
/*     */   public DisDefaultTravelTime calculateTravelTimeDefaultValue(String deviceName, List<String> roadDivisionIdList) {
/* 757 */     DisDefaultTravelTime result = new DisDefaultTravelTime();
/* 758 */     IMap<String, DeviceTcConfig> deviceConfigMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 759 */     DeviceTcConfig deviceConfig = (DeviceTcConfig)deviceConfigMap.get(deviceName);
/* 760 */     Set<RoadSection> roadSectionSet = new HashSet<>();
/*     */     
/* 762 */     RoadSection tcRoadSection = getTcRoadSection(deviceName);
/*     */ 
/*     */     
/* 765 */     if (roadDivisionIdList.size() == 1) {
/* 766 */       Iterator<String> iterator = roadDivisionIdList.iterator();
/* 767 */       if (iterator.hasNext()) {
/* 768 */         String endDivisionId = tcRoadSection.getEndDivisionId();
/* 769 */         String divisionId = iterator.next();
/* 770 */         if (endDivisionId.equals(divisionId)) {
/*     */           
/* 772 */           roadSectionSet.add(tcRoadSection);
/*     */         } else {
/*     */           
/* 775 */           roadSectionSet.addAll(getRoadSectionsAtSameRoadLine(endDivisionId, divisionId));
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 780 */       List<String> roadDivisionIdAndTcList = new ArrayList<>();
/* 781 */       roadDivisionIdAndTcList.add(tcRoadSection.getStartDivisionId());
/* 782 */       roadDivisionIdAndTcList.addAll(roadDivisionIdList);
/*     */ 
/*     */       
/* 785 */       for (int i = 0; i < roadDivisionIdAndTcList.size() - 1; i++) {
/* 786 */         String startDivisionId = roadDivisionIdAndTcList.get(i);
/* 787 */         String endDivisionId = roadDivisionIdAndTcList.get(i + 1);
/*     */         
/* 789 */         IMap<String, RoadDivision> roadDivisionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadDivision);
/* 790 */         RoadDivision startRoadDivision = (RoadDivision)roadDivisionMap.get(startDivisionId);
/* 791 */         RoadDivision endRoadDivision = (RoadDivision)roadDivisionMap.get(endDivisionId);
/* 792 */         logger.debug("calculateTravelTimeDefaultValue startRoadDivision {},endRoadDivision {}", startRoadDivision, endRoadDivision);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 797 */         Preconditions.checkNotNull(startRoadDivision, "無法取得Id為  %s 的Division", startDivisionId);
/* 798 */         Preconditions.checkNotNull(endRoadDivision, "無法取得Id為   %s 的Division", endDivisionId);
/*     */         
/* 800 */         if (startRoadDivision.getDivisionName().equals(endRoadDivision.getDivisionName())) {
/*     */ 
/*     */ 
/*     */           
/* 804 */           logger.debug("startRoadDivision {},endRoadDivision {} doesn't count", startRoadDivision, endRoadDivision);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 812 */         else if (startRoadDivision.getLineId().equals(endRoadDivision.getLineId())) {
/*     */           
/* 814 */           roadSectionSet.addAll(getRoadSectionsAtSameRoadLine(startDivisionId, endDivisionId));
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 819 */         else if (DivisionType.C.equals(startRoadDivision.getDivisionType()) || DivisionType.I
/* 820 */           .equals(startRoadDivision.getDivisionType())) {
/*     */ 
/*     */           
/* 823 */           Collection<RoadDivision> changeLineRoadDivisions = roadDivisionMap.values((Predicate)getChangeLineRoadDivisionPredicate(startRoadDivision));
/*     */           
/* 825 */           Iterator<RoadDivision> iterator = changeLineRoadDivisions.iterator();
/*     */           
/* 827 */           if (iterator.hasNext())
/*     */           {
/* 829 */             RoadDivision changeLineRoadDivision = iterator.next();
/*     */             try {
/* 831 */               roadSectionSet.addAll(
/* 832 */                   getRoadSectionsAtSameRoadLine(changeLineRoadDivision
/* 833 */                     .getDivisionId(), endDivisionId));
/*     */             }
/* 835 */             catch (IllegalArgumentException e) {
/* 836 */               logger.error("轉換系統交流道失敗", e);
/*     */             }
/*     */           
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 843 */           logger.warn("{} 路段轉換路線失敗 ,無法與{} 位於同一路線", startDivisionId, endDivisionId);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 849 */     int minTravelTime = 0;
/* 850 */     if (!roadSectionSet.isEmpty())
/*     */     {
/*     */       
/* 853 */       for (Map.Entry<RoadSection, DisDefaultTravelTime> e : calculateTravelTimeDefaultValue(deviceConfig, tcRoadSection, roadSectionSet).entrySet())
/*     */       {
/* 855 */         minTravelTime += ((DisDefaultTravelTime)e.getValue()).getMinTravelTime().intValue();
/*     */       }
/*     */     }
/* 858 */     result.setMaxTravelTime(Integer.valueOf(Math.round((minTravelTime * 4))));
/* 859 */     result.setMinTravelTime(Integer.valueOf(minTravelTime));
/*     */     
/* 861 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<RoadSection, DisDefaultTravelTime> calculateTravelTimeDefaultValue(DeviceTcConfig tcDeviceConfig, RoadSection tcRoadSection, Set<RoadSection> roadSectionSet) {
/* 867 */     Map<RoadSection, DisDefaultTravelTime> result = new HashMap<>();
/*     */     
/* 869 */     IMap<String, RoadDivision> roadDivisionMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.RoadDivision);
/*     */     
/* 871 */     for (RoadSection r : roadSectionSet) {
/* 872 */       DisDefaultTravelTime d = new DisDefaultTravelTime();
/* 873 */       RoadDivision startRoadDivision = (RoadDivision)roadDivisionMap.get(r.getStartDivisionId());
/* 874 */       RoadDivision endRoadDivision = (RoadDivision)roadDivisionMap.get(r.getEndDivisionId());
/*     */       
/* 876 */       int minSpeedMeterMin = 1000;
/* 877 */       int maxSpeedMeterMin = 1666;
/*     */       
/* 879 */       if (r.getMaxSpeed() != null && r.getMaxSpeed().intValue() > 0) {
/* 880 */         maxSpeedMeterMin = r.getMaxSpeed().intValue() * 1000 / 60;
/*     */       } else {
/* 882 */         logger.warn("RoadSection {}, getMaxSpeed invalid ,", r.getSectionId(), r.getMaxSpeed());
/*     */       } 
/* 884 */       if (r.getMinSpeed() != null && r.getMinSpeed().intValue() > 0) {
/* 885 */         minSpeedMeterMin = r.getMinSpeed().intValue() * 1000 / 60;
/*     */       } else {
/* 887 */         logger.warn("RoadSection {}, getMinSpeed invalid ,", r.getSectionId(), r.getMinSpeed());
/*     */       } 
/*     */       
/* 890 */       if (r.equals(tcRoadSection)) {
/* 891 */         d.setMaxTravelTime(
/* 892 */             Integer.valueOf(Math.abs((tcDeviceConfig
/* 893 */                 .getMilepost().intValue() - endRoadDivision.getMileage().intValue()) / minSpeedMeterMin)));
/* 894 */         d.setMinTravelTime(
/* 895 */             Integer.valueOf(Math.abs((tcDeviceConfig
/* 896 */                 .getMilepost().intValue() - endRoadDivision.getMileage().intValue()) / maxSpeedMeterMin)));
/*     */       } else {
/* 898 */         d.setMaxTravelTime(
/* 899 */             Integer.valueOf(Math.abs((startRoadDivision
/* 900 */                 .getMileage().intValue() - endRoadDivision.getMileage().intValue()) / minSpeedMeterMin)));
/*     */         
/* 902 */         d.setMinTravelTime(
/* 903 */             Integer.valueOf(Math.abs((startRoadDivision
/* 904 */                 .getMileage().intValue() - endRoadDivision.getMileage().intValue()) / maxSpeedMeterMin)));
/*     */       } 
/*     */       
/* 907 */       result.put(r, d);
/*     */     } 
/*     */     
/* 910 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PredicateBuilder getEnableDisTravelTimeConfigPredicate() {
/* 919 */     EntryObject e = (new PredicateBuilder()).getEntryObject();
/* 920 */     PredicateBuilder predicate = e.get("enable").equal(Boolean.TRUE);
/* 921 */     return predicate;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\service\DisTravelTimeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */