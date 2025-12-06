/*    */ package com.hwacom.ngtms.c.dgs.fm.hz;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.hz.HzMapEnum;
/*    */ import com.hwacom.ngtms.hcce.hz.HzMapIndex;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum DgsHzMap
/*    */   implements HzMapEnum
/*    */ {
/* 17 */   RingRoadConfig("環道組態", "ringRoadConfigMapStore", 
/*    */ 
/*    */     
/* 20 */     HzMapIndex.parsingDeclare("startDivisionId, endDivisionId, startLineId, endLineId, startDirection, endDirection, startMileage:true, endMileage:true")),
/*    */   
/* 22 */   RoadLaneCount("道路車道數組態", "roadLaneCountMapStore", 
/*    */ 
/*    */     
/* 25 */     HzMapIndex.parsingDeclare("lineId, startMile:true, endMile:true, direction")),
/* 26 */   RampVdConfig("匝道VD組態", "rmpVdConfigMapStore"),
/* 27 */   RampVdType("匝道種類組態", "rmpVdTypeMapStore"),
/* 28 */   SectionTraffic("路段交通資料"),
/* 29 */   SectionTrafficTemp("路段交通資料計算暫存");
/*    */   
/*    */   private String mapStoreBeanName;
/*    */   
/*    */   private String description;
/*    */   private HzMapIndex[] hzMapIndexes;
/*    */   
/*    */   DgsHzMap(String description) {
/* 37 */     this.description = description;
/*    */   }
/*    */   
/*    */   DgsHzMap(String description, String mapStoreBeanName) {
/* 41 */     this.description = description;
/* 42 */     this.mapStoreBeanName = mapStoreBeanName;
/*    */   }
/*    */   
/*    */   DgsHzMap(String description, HzMapIndex[] hzMapIndexes) {
/* 46 */     this.description = description;
/* 47 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */   
/*    */   DgsHzMap(String description, String mapStoreBeanName, HzMapIndex[] hzMapIndexes) {
/* 51 */     this.description = description;
/* 52 */     this.mapStoreBeanName = mapStoreBeanName;
/* 53 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDataStoreBeanName() {
/* 58 */     return this.mapStoreBeanName;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toHzName() {
/* 64 */     return "DGS_" + toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 69 */     return this.description;
/*    */   }
/*    */ 
/*    */   
/*    */   public HzMapIndex[] getHzMapIndexes() {
/* 74 */     return this.hzMapIndexes;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonDgs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dgs\fm\hz\DgsHzMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */