/*    */ package com.hwacom.ngtms.rtu.fm.hz;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.hz.HzMapEnum;
/*    */ import com.hwacom.ngtms.hcce.hz.HzMapIndex;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum RtuHzMap
/*    */   implements HzMapEnum
/*    */ {
/* 13 */   ModbusDeviceConfig("Modbus 設備設定", "modbusDeviceConfigMapStore"), 
/* 14 */   ModbusReadConfig("Modbus 讀取設定", "modbusReadConfigMapStore"), 
/* 15 */   ModbusPinMapping("Modbus 對應class 欄位設定", "modbusPinMappingMapStore"), 
/* 16 */   ModbusData("Modbus 接收資料"), 
/* 17 */   ModbusConnectStatus("Modbus 設備連斷線狀態");
/*    */   
/*    */   private String mapStoreBeanName;
/*    */   private String description;
/*    */   private HzMapIndex[] hzMapIndexes;
/*    */   
/*    */   private RtuHzMap(String description) {
/* 24 */     this.description = description;
/*    */   }
/*    */   
/*    */   private RtuHzMap(String description, String mapStoreBeanName) {
/* 28 */     this.description = description;
/* 29 */     this.mapStoreBeanName = mapStoreBeanName;
/*    */   }
/*    */   
/*    */   private RtuHzMap(String description, HzMapIndex[] hzMapIndexes) {
/* 33 */     this.description = description;
/* 34 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */   
/*    */   private RtuHzMap(String description, String mapStoreBeanName, HzMapIndex[] hzMapIndexes) {
/* 38 */     this.description = description;
/* 39 */     this.mapStoreBeanName = mapStoreBeanName;
/* 40 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */   
/*    */   public String toHzName()
/*    */   {
/* 45 */     return "RTU_" + toString();
/*    */   }
/*    */   
/*    */   public String getDataStoreBeanName() {
/* 49 */     return this.mapStoreBeanName;
/*    */   }
/*    */   
/*    */   public String getDescription()
/*    */   {
/* 54 */     return this.description;
/*    */   }
/*    */   
/*    */   public HzMapIndex[] getHzMapIndexes() {
/* 58 */     return this.hzMapIndexes;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\fm-rtu-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\rtu\fm\hz\RtuHzMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */