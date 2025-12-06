/*    */ package com.hwacom.ngtms.hcce.hz;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum HzMap
/*    */   implements HzMapEnum
/*    */ {
/* 20 */   TopologyInfo("Cluster 的Topology組態及節點登入狀態"),
/* 21 */   FmeDefTable("FME 定義表格"),
/* 22 */   FmeExeStatus("FME 執行狀態"),
/* 23 */   FmResource("FME 發佈的資源位置"),
/* 24 */   ClusterMode("Cluster 狀態"),
/* 25 */   SystemStatus("System 狀態"),
/* 26 */   DynamicConfig("FME 動態組態", "dyanConfigMapStore"),
/* 27 */   SysPerfLog("系統效能參數"),
/* 28 */   ClientHeartbeat("Client heartbeat");
/*    */   
/*    */   private String description;
/*    */   
/*    */   private String mapStoreBeanName;
/*    */   private HzMapIndex[] hzMapIndexes;
/*    */   
/*    */   HzMap(String description) {
/* 36 */     this.description = description;
/*    */   }
/*    */   
/*    */   HzMap(String description, HzMapIndex[] hzMapIndexes) {
/* 40 */     this.description = description;
/* 41 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */   
/*    */   HzMap(String description, String mapStoreBeanName) {
/* 45 */     this.description = description;
/* 46 */     this.mapStoreBeanName = mapStoreBeanName;
/*    */   }
/*    */   
/*    */   HzMap(String description, String mapStoreBeanName, HzMapIndex[] hzMapIndexes) {
/* 50 */     this.description = description;
/* 51 */     this.mapStoreBeanName = mapStoreBeanName;
/* 52 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 57 */     return this.description;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDataStoreBeanName() {
/* 62 */     return this.mapStoreBeanName;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toHzName() {
/* 68 */     return toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public HzMapIndex[] getHzMapIndexes() {
/* 73 */     return this.hzMapIndexes;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\hz\HzMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */