/*    */ package com.hwacom.ngtms.hcce.hz;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum HcHzMap
/*    */   implements HzMapEnum
/*    */ {
/* 10 */   UserValidateCode("使用者驗證碼");
/*    */   
/*    */   private HzMapIndex[] hzMapIndexes;
/*    */   private String description;
/*    */   private String mapStoreBeanName;
/*    */   
/*    */   HcHzMap(String description) {
/* 17 */     this.description = description;
/*    */   }
/*    */   
/*    */   HcHzMap(String description, String mapStoreBeanName) {
/* 21 */     this.description = description;
/* 22 */     this.mapStoreBeanName = mapStoreBeanName;
/*    */   }
/*    */   
/*    */   HcHzMap(String description, HzMapIndex[] hzMapIndexes) {
/* 26 */     this.description = description;
/* 27 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */   
/*    */   HcHzMap(String description, String mapStoreBeanName, HzMapIndex[] hzMapIndexes) {
/* 31 */     this.description = description;
/* 32 */     this.mapStoreBeanName = mapStoreBeanName;
/* 33 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toHzName() {
/* 38 */     return "Hc_" + toString();
/*    */   }
/*    */   
/*    */   public String getDataStoreBeanName() {
/* 42 */     return this.mapStoreBeanName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 47 */     return this.description;
/*    */   }
/*    */   
/*    */   public HzMapIndex[] getHzMapIndexes() {
/* 51 */     return this.hzMapIndexes;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcConsole-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\hz\HcHzMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */