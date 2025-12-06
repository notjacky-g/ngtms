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
/*    */   private HzMapIndex[] hzMapIndexes;
/*    */   
/*    */   private String description;
/*    */   private String mapStoreBeanName;
/*    */   
/*    */   CcsHzMap(String description) {
/* 21 */     this.description = description;
/*    */   }
/*    */   
/*    */   CcsHzMap(String description, String mapStoreBeanName) {
/* 25 */     this.description = description;
/* 26 */     this.mapStoreBeanName = mapStoreBeanName;
/*    */   }
/*    */   
/*    */   CcsHzMap(String description, HzMapIndex[] hzMapIndexes) {
/* 30 */     this.description = description;
/* 31 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */   
/*    */   CcsHzMap(String description, String mapStoreBeanName, HzMapIndex[] hzMapIndexes) {
/* 35 */     this.description = description;
/* 36 */     this.mapStoreBeanName = mapStoreBeanName;
/* 37 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toHzName() {
/* 42 */     return "CCS_" + toString();
/*    */   }
/*    */   
/*    */   public String getDataStoreBeanName() {
/* 46 */     return this.mapStoreBeanName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 51 */     return this.description;
/*    */   }
/*    */   
/*    */   public HzMapIndex[] getHzMapIndexes() {
/* 55 */     return this.hzMapIndexes;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\fm-ccs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ccs\fm\hz\CcsHzMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */