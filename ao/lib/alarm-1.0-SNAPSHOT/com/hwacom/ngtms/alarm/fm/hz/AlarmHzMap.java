/*    */ package com.hwacom.ngtms.alarm.fm.hz;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.hz.HzMapEnum;
/*    */ import com.hwacom.ngtms.hcce.hz.HzMapIndex;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum AlarmHzMap
/*    */   implements HzMapEnum
/*    */ {
/* 13 */   Alarm("告警資料", "alarmMapStore"), 
/* 14 */   AlarmLevelConfig("Alarm level設定", "alarmLevelConfigMapStore", 
/* 15 */     HzMapIndex.parsingDeclare("alarmSubType")), 
/* 16 */   LevelConfig("Alarm level 設定", "alarmLevelConfigMapStore", 
/* 17 */     HzMapIndex.parsingDeclare("alarmSubType"));
/*    */   
/*    */ 
/*    */   private String description;
/*    */   private String mapStoreBeanName;
/*    */   private HzMapIndex[] hzMapIndexes;
/*    */   
/*    */   private AlarmHzMap(String description)
/*    */   {
/* 26 */     this.description = description;
/*    */   }
/*    */   
/*    */   private AlarmHzMap(String description, HzMapIndex[] hzMapIndexes) {
/* 30 */     this.description = description;
/* 31 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */   
/*    */   private AlarmHzMap(String description, String mapStoreBeanName) {
/* 35 */     this.description = description;
/* 36 */     this.mapStoreBeanName = mapStoreBeanName;
/*    */   }
/*    */   
/*    */   private AlarmHzMap(String description, String mapStoreBeanName, HzMapIndex[] hzMapIndexes) {
/* 40 */     this.description = description;
/* 41 */     this.mapStoreBeanName = mapStoreBeanName;
/* 42 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */   
/*    */   public String getDescription()
/*    */   {
/* 47 */     return this.description;
/*    */   }
/*    */   
/*    */   public String getDataStoreBeanName()
/*    */   {
/* 52 */     return this.mapStoreBeanName;
/*    */   }
/*    */   
/*    */   public String toHzName()
/*    */   {
/* 57 */     return "ALARM_" + toString();
/*    */   }
/*    */   
/*    */   public HzMapIndex[] getHzMapIndexes()
/*    */   {
/* 62 */     return this.hzMapIndexes;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\alarm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\alarm\fm\hz\AlarmHzMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */