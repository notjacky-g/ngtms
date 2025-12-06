/*    */ package com.hwacom.ngtms.room.fm.hz;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.hz.HzMapEnum;
/*    */ import com.hwacom.ngtms.hcce.hz.HzMapIndex;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum RoomHzMap
/*    */   implements HzMapEnum
/*    */ {
/* 13 */   CardConfig("卡片組態設定", "roomCardConfigMapStore"),
/* 14 */   CardGroupConfig("定期卡群組設定", "roomCardGroupConfigMapStore"),
/* 15 */   CardReaderMappingConfig("卡片與卡機對應表", "roomCardReaderMappingConfigMapStore"),
/* 16 */   BackgroundSvgConfig("ROOM SVG 底圖組態", "roomBackgroundSvgConfigMapStore"),
/* 17 */   CardReaderLog("卡機事件紀錄", "roomCardReaderLogMapStore"),
/* 18 */   RoomDeviceStauts("機房設備狀態", "roomDeviceStatusMapStore");
/*    */   
/*    */   private String mapStoreBeanName;
/*    */   private String description;
/*    */   private HzMapIndex[] hzMapIndexes;
/*    */   
/*    */   RoomHzMap(String description) {
/* 25 */     this.description = description;
/*    */   }
/*    */   
/*    */   RoomHzMap(String description, String mapStoreBeanName) {
/* 29 */     this.description = description;
/* 30 */     this.mapStoreBeanName = mapStoreBeanName;
/*    */   }
/*    */   
/*    */   RoomHzMap(String description, HzMapIndex[] hzMapIndexes) {
/* 34 */     this.description = description;
/* 35 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */   
/*    */   RoomHzMap(String description, String mapStoreBeanName, HzMapIndex[] hzMapIndexes) {
/* 39 */     this.description = description;
/* 40 */     this.mapStoreBeanName = mapStoreBeanName;
/* 41 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toHzName() {
/* 46 */     return "ROOM_" + toString();
/*    */   }
/*    */   
/*    */   public String getDataStoreBeanName() {
/* 50 */     return this.mapStoreBeanName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 55 */     return this.description;
/*    */   }
/*    */   
/*    */   public HzMapIndex[] getHzMapIndexes() {
/* 59 */     return this.hzMapIndexes;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\fm\hz\RoomHzMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */