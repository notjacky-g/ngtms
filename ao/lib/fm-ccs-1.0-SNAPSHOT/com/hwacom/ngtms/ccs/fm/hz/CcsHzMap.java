/*    */ package com.hwacom.ngtms.ccs.fm.hz;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.hz.HzMapEnum;
/*    */ import com.hwacom.ngtms.hcce.hz.HzMapIndex;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum CcsHzMap
/*    */   implements HzMapEnum
/*    */ {
/* 13 */   CctvConfig("Cctv組態資料");
/*    */   
/*    */   private String mapStoreBeanName;
/*    */   private String description;
/*    */   private HzMapIndex[] hzMapIndexes;
/*    */   
/*    */   private CcsHzMap(String description)
/*    */   {
/* 21 */     this.description = description;
/*    */   }
/*    */   
/*    */   private CcsHzMap(String description, String mapStoreBeanName) {
/* 25 */     this.description = description;
/* 26 */     this.mapStoreBeanName = mapStoreBeanName;
/*    */   }
/*    */   
/*    */   private CcsHzMap(String description, HzMapIndex[] hzMapIndexes) {
/* 30 */     this.description = description;
/* 31 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */   
/*    */   private CcsHzMap(String description, String mapStoreBeanName, HzMapIndex[] hzMapIndexes) {
/* 35 */     this.description = description;
/* 36 */     this.mapStoreBeanName = mapStoreBeanName;
/* 37 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */   
/*    */   public String toHzName()
/*    */   {
/* 42 */     return "CCS_" + toString();
/*    */   }
/*    */   
/*    */   public String getDataStoreBeanName() {
/* 46 */     return this.mapStoreBeanName;
/*    */   }
/*    */   
/*    */   public String getDescription()
/*    */   {
/* 51 */     return this.description;
/*    */   }
/*    */   
/*    */   public HzMapIndex[] getHzMapIndexes() {
/* 55 */     return this.hzMapIndexes;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\fm-ccs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ccs\fm\hz\CcsHzMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */