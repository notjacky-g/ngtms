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
/*    */   private String mapStoreBeanName;
/*    */   private String description;
/*    */   private HzMapIndex[] hzMapIndexes;
/*    */   
/*    */   private HcHzMap(String description) {
/* 17 */     this.description = description;
/*    */   }
/*    */   
/*    */   private HcHzMap(String description, String mapStoreBeanName) {
/* 21 */     this.description = description;
/* 22 */     this.mapStoreBeanName = mapStoreBeanName;
/*    */   }
/*    */   
/*    */   private HcHzMap(String description, HzMapIndex[] hzMapIndexes) {
/* 26 */     this.description = description;
/* 27 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */   
/*    */   private HcHzMap(String description, String mapStoreBeanName, HzMapIndex[] hzMapIndexes) {
/* 31 */     this.description = description;
/* 32 */     this.mapStoreBeanName = mapStoreBeanName;
/* 33 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */   
/*    */   public String toHzName()
/*    */   {
/* 38 */     return "Hc_" + toString();
/*    */   }
/*    */   
/*    */   public String getDataStoreBeanName() {
/* 42 */     return this.mapStoreBeanName;
/*    */   }
/*    */   
/*    */   public String getDescription()
/*    */   {
/* 47 */     return this.description;
/*    */   }
/*    */   
/*    */   public HzMapIndex[] getHzMapIndexes() {
/* 51 */     return this.hzMapIndexes;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcConsole-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\hz\HcHzMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */