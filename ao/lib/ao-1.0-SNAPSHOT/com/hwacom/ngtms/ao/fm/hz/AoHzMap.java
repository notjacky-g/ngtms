/*    */ package com.hwacom.ngtms.ao.fm.hz;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.hz.HzMapEnum;
/*    */ import com.hwacom.ngtms.hcce.hz.HzMapIndex;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum AoHzMap
/*    */   implements HzMapEnum
/*    */ {
/* 13 */   NcuCardReaderMappingConfig("讀卡機對應的NCU", "ncuCardReaderMappingConfigMapStore"),
/* 14 */   NcuCardTapData("讀卡機當前刷卡紀錄", "ncuCardTapDataMapStore"),
/* 15 */   LifeFaceLockCardData("人臉辨識失敗未處理紀錄", "lifeFaceLockCardDataMapStore"),
/* 16 */   LifeFaceCompareData("人臉辨識失敗各機房最近發生的時間戳記", "lifeFaceCompareDataMapStore"),
/* 17 */   WaterStatusData("用水即時資料", "waterStatusDataMapStore"),
/* 18 */   PowerStatusData("用電即時資料", "powerStatusDataMapStore"),
/* 19 */   WaterPowerBaseConfig("用水用電基本設定值", "waterPowerBaseConfigMapStore"),
/* 20 */   MessageUser("簡訊接收者", "messageUserMapStore"),
/* 21 */   NcuVoiceAutoCloseData("聲光警報器自動關閉時間資訊");
/*    */   
/*    */   private String mapStoreBeanName;
/*    */   private String description;
/*    */   private HzMapIndex[] hzMapIndexes;
/*    */   
/*    */   AoHzMap(String description) {
/* 28 */     this.description = description;
/*    */   }
/*    */   
/*    */   AoHzMap(String description, String mapStoreBeanName) {
/* 32 */     this.description = description;
/* 33 */     this.mapStoreBeanName = mapStoreBeanName;
/*    */   }
/*    */   
/*    */   AoHzMap(String description, HzMapIndex[] hzMapIndexes) {
/* 37 */     this.description = description;
/* 38 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */   
/*    */   AoHzMap(String description, String mapStoreBeanName, HzMapIndex[] hzMapIndexes) {
/* 42 */     this.description = description;
/* 43 */     this.mapStoreBeanName = mapStoreBeanName;
/* 44 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toHzName() {
/* 49 */     return "AO_" + toString();
/*    */   }
/*    */   
/*    */   public String getDataStoreBeanName() {
/* 53 */     return this.mapStoreBeanName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 58 */     return this.description;
/*    */   }
/*    */   
/*    */   public HzMapIndex[] getHzMapIndexes() {
/* 62 */     return this.hzMapIndexes;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\hz\AoHzMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */