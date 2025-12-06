/*    */ package com.hwacom.ngtms.pd.fm.hz;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.hz.HzMapEnum;
/*    */ import com.hwacom.ngtms.hcce.hz.HzMapIndex;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum PdHzMap
/*    */   implements HzMapEnum
/*    */ {
/* 13 */   Config("pd 組態設定", "pdConfigMapStore"),
/* 14 */   LoopDeviceConfig("pd 迴路底下設備組態設定", "pdLoopDeviceConfigMapStore"),
/* 15 */   Status("pd 狀態", "pdStatusMapStore");
/*    */   
/*    */   private String mapStoreBeanName;
/*    */   
/*    */   private String description;
/*    */   private HzMapIndex[] hzMapIndexes;
/*    */   
/*    */   PdHzMap(String description) {
/* 23 */     this.description = description;
/*    */   }
/*    */   
/*    */   PdHzMap(String description, String mapStoreBeanName) {
/* 27 */     this.description = description;
/* 28 */     this.mapStoreBeanName = mapStoreBeanName;
/*    */   }
/*    */   
/*    */   PdHzMap(String description, HzMapIndex[] hzMapIndexes) {
/* 32 */     this.description = description;
/* 33 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */   
/*    */   PdHzMap(String description, String mapStoreBeanName, HzMapIndex[] hzMapIndexes) {
/* 37 */     this.description = description;
/* 38 */     this.mapStoreBeanName = mapStoreBeanName;
/* 39 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toHzName() {
/* 44 */     return "PD_" + toString();
/*    */   }
/*    */   
/*    */   public String getDataStoreBeanName() {
/* 48 */     return this.mapStoreBeanName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 53 */     return this.description;
/*    */   }
/*    */   
/*    */   public HzMapIndex[] getHzMapIndexes() {
/* 57 */     return this.hzMapIndexes;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\fm-pd-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\pd\fm\hz\PdHzMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */