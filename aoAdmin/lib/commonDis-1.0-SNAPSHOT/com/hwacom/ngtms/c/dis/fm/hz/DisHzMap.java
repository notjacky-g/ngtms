/*    */ package com.hwacom.ngtms.c.dis.fm.hz;
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
/*    */ public enum DisHzMap
/*    */   implements HzMapEnum
/*    */ {
/* 17 */   TravelTimeConfig("旅行時間組態", "disTravelTimeConfigMapStore", HzMapIndex.parsingDeclare("enable")),
/* 18 */   SunshineTable("資顯設備日照表", "disSunshineTableMapStore"),
/* 19 */   PhraseConfig("資顯設備片語庫", "disPhraseConfigMapStore"),
/* 20 */   PhraseType("資顯設備片語庫分類表", "disPhraseTypeMapStore"),
/* 21 */   GraphicConfig("資顯設備圖片庫", "disGraphicConfigMapStore"),
/* 22 */   FullTextConfig("資顯設備全文庫", "disFullTextConfigMapStore", 
/*    */ 
/*    */     
/* 25 */     HzMapIndex.parsingDeclare("disFullText.fullTextType,disFullText.category")),
/* 26 */   FullTextType("資顯設備全文庫分類表", "disFullTextTypeMapStore"),
/* 27 */   TravelTime("旅行時間計算後資料"),
/* 28 */   AccidentTiming("事件觸發的候隊表內容最初下載成功的時間"),
/* 29 */   QueueItem("候隊表", "disQueueItemMapStore");
/*    */   
/*    */   private String mapStoreBeanName;
/*    */   
/*    */   private String description;
/*    */   private HzMapIndex[] hzMapIndexes;
/*    */   
/*    */   DisHzMap(String description) {
/* 37 */     this.description = description;
/*    */   }
/*    */   
/*    */   DisHzMap(String description, String mapStoreBeanName) {
/* 41 */     this.description = description;
/* 42 */     this.mapStoreBeanName = mapStoreBeanName;
/*    */   }
/*    */   
/*    */   DisHzMap(String description, HzMapIndex[] hzMapIndexes) {
/* 46 */     this.description = description;
/* 47 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */   
/*    */   DisHzMap(String description, String mapStoreBeanName, HzMapIndex[] hzMapIndexes) {
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
/* 64 */     return "DIS_" + toString();
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


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\hz\DisHzMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */