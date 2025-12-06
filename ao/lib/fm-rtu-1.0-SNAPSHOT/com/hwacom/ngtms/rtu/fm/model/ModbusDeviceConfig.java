/*    */ package com.hwacom.ngtms.rtu.fm.model;
/*    */ 
/*    */ import com.hwacom.ngtms.base.annotation.Comment;
/*    */ import java.io.Serializable;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.Id;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Entity
/*    */ public class ModbusDeviceConfig
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -3315387135259312275L;
/*    */   @Id
/*    */   @Comment("設備名稱")
/*    */   private String deviceName;
/*    */   @Comment("modbus slave id")
/*    */   private Integer slaveId;
/*    */   @Comment("pin group id")
/*    */   private Integer pinGroupId;
/*    */   @Comment("modbus 輪詢間隔秒數")
/*    */   private Integer pollIntervalSec;
/*    */   
/*    */   public String getDeviceName()
/*    */   {
/* 27 */     return this.deviceName;
/*    */   }
/*    */   
/*    */   public void setDeviceName(String deviceName) {
/* 31 */     this.deviceName = deviceName;
/*    */   }
/*    */   
/*    */   public Integer getSlaveId() {
/* 35 */     return this.slaveId;
/*    */   }
/*    */   
/*    */   public void setSlaveId(Integer slaveId) {
/* 39 */     this.slaveId = slaveId;
/*    */   }
/*    */   
/*    */   public Integer getPinGroupId() {
/* 43 */     return this.pinGroupId;
/*    */   }
/*    */   
/*    */   public void setPinGroupId(Integer pinGroupId) {
/* 47 */     this.pinGroupId = pinGroupId;
/*    */   }
/*    */   
/*    */   public Integer getPollIntervalSec() {
/* 51 */     return this.pollIntervalSec;
/*    */   }
/*    */   
/*    */   public void setPollIntervalSec(Integer pollIntervalSec) {
/* 55 */     this.pollIntervalSec = pollIntervalSec;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 60 */     int prime = 31;
/* 61 */     int result = 1;
/* 62 */     result = 31 * result + (this.deviceName == null ? 0 : this.deviceName.hashCode());
/* 63 */     return result;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj)
/*    */   {
/* 68 */     if (this == obj) return true;
/* 69 */     if (obj == null) return false;
/* 70 */     if (getClass() != obj.getClass()) return false;
/* 71 */     ModbusDeviceConfig other = (ModbusDeviceConfig)obj;
/* 72 */     if (this.deviceName == null) {
/* 73 */       if (other.deviceName != null) return false;
/* 74 */     } else if (!this.deviceName.equals(other.deviceName)) return false;
/* 75 */     return true;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 80 */     return "ModbusDeviceConfig [deviceName=" + this.deviceName + ", slaveId=" + this.slaveId + ", pinGroupId=" + this.pinGroupId + ", pollIntervalSec=" + this.pollIntervalSec + "]";
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\fm-rtu-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\rtu\fm\model\ModbusDeviceConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */