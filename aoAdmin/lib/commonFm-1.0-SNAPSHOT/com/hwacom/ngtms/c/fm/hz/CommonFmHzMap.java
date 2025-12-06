/*     */ package com.hwacom.ngtms.c.fm.hz;
/*     */ 
/*     */ import com.hwacom.ngtms.hcce.hz.HzMapEnum;
/*     */ import com.hwacom.ngtms.hcce.hz.HzMapIndex;
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
/*     */ public enum CommonFmHzMap
/*     */   implements HzMapEnum
/*     */ {
/*  18 */   AlarmTypeConfig("警報類別組態", "alarmTypeConfigMapStore", HzMapIndex.parsingDeclare("rpsEvent")),
/*  19 */   AlarmSubTypeConfig("警報次類別組態", "alarmSubTypeConfigMapStore", HzMapIndex.parsingDeclare("id")),
/*  20 */   AlarmEventLock("事件鎖定資料"),
/*     */   
/*  22 */   EventAlarmContextData("事件告警其他資料"),
/*     */   
/*  24 */   DeviceHostLocation("Host位置組態", "deviceHostLocationMapStore"),
/*  25 */   DeviceGroup("設備群組組態", "deviceGroupMapStore"),
/*  26 */   BranchConfig("工務段組態", "branchConfigMapStore"),
/*     */   
/*  28 */   DeviceCategory("設備分類組態", "deviceCategoryMapStore"),
/*  29 */   DeviceType("設備類別組態", "deviceTypeMapStore"),
/*  30 */   DeviceTcConfig("交控設備組態", "deviceTcConfigMapStore", 
/*     */ 
/*     */     
/*  33 */     HzMapIndex.parsingDeclare("deviceName, deviceType, direction, enable")),
/*  34 */   DeviceLocationMappingConfig("設備類別組態", "deviceLocationMappingConfigMapStore"),
/*  35 */   MfccConfig("MFCC 組態", "mfccConfigMapStore"),
/*     */   
/*  37 */   RoadGps("道路GPS座標", "roadGpsMapStore"),
/*  38 */   RoadLine("道路組態", "roadLineMapStore"),
/*  39 */   RoadDivision("路段切割點組態", "roadDivisionMapStore", 
/*     */ 
/*     */     
/*  42 */     HzMapIndex.parsingDeclare("divisionType, lineId, areaType, travelTimeVisible, boundary, mileage:true")),
/*     */   
/*  44 */   RoadSection("路段組態", "roadSectionMapStore", 
/*     */ 
/*     */     
/*  47 */     HzMapIndex.parsingDeclare("lineId, direction, startDivisionId, endDivisionId, areaType")),
/*  48 */   RoadUnitSection("單位路段組態", "roadUnitSectionMapStore", HzMapIndex.parsingDeclare("sectionId")),
/*     */   
/*  50 */   CloverLeaf("系統交流道交匯點組態", "cloverLeafMapStore"),
/*     */ 
/*     */   
/*  53 */   MAQ_CurrentMessage("跑馬燈 目前顯示訊息"),
/*  54 */   DeviceTcStatus("設備狀態", HzMapIndex.parsingDeclare("deviceType, commStatus")),
/*  55 */   TunnelConfig("隧道組態", "tunnelConfigMapStore"),
/*     */   
/*  57 */   DeviceTcHardwareStatus("設備硬體狀態", "deviceTcHardwareStatusMapStore"),
/*     */   
/*  59 */   DevicePositionConfig("SVG 圖設備位置組態", "deviceSvgPositionConfigMapStore"),
/*     */ 
/*     */   
/*  62 */   NCC_TcConnectionStatusReport("TC 連線狀態"),
/*  63 */   NCC_AliveReport("NCC Alive 回報");
/*     */   
/*     */   private String mapStoreBeanName;
/*     */   
/*     */   private String description;
/*     */   private HzMapIndex[] hzMapIndexes;
/*     */   
/*     */   CommonFmHzMap(String description) {
/*  71 */     this.description = description;
/*     */   }
/*     */   
/*     */   CommonFmHzMap(String description, String mapStoreBeanName) {
/*  75 */     this.description = description;
/*  76 */     this.mapStoreBeanName = mapStoreBeanName;
/*     */   }
/*     */   
/*     */   CommonFmHzMap(String description, HzMapIndex[] hzMapIndexes) {
/*  80 */     this.description = description;
/*  81 */     this.hzMapIndexes = hzMapIndexes;
/*     */   }
/*     */   
/*     */   CommonFmHzMap(String description, String mapStoreBeanName, HzMapIndex[] hzMapIndexes) {
/*  85 */     this.description = description;
/*  86 */     this.mapStoreBeanName = mapStoreBeanName;
/*  87 */     this.hzMapIndexes = hzMapIndexes;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDataStoreBeanName() {
/*  92 */     return this.mapStoreBeanName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toHzName() {
/*  98 */     return toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 103 */     return this.description;
/*     */   }
/*     */ 
/*     */   
/*     */   public HzMapIndex[] getHzMapIndexes() {
/* 108 */     return this.hzMapIndexes;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\hz\CommonFmHzMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */