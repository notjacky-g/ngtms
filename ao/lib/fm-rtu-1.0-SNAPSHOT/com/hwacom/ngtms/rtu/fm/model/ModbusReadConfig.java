/*    */ package com.hwacom.ngtms.rtu.fm.model;
/*    */ 
/*    */ import com.hwacom.ngtms.base.annotation.Comment;
/*    */ import com.hwacom.ngtms.rtu.shared.ModbusDataType;
/*    */ import java.io.Serializable;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.EnumType;
/*    */ import javax.persistence.Enumerated;
/*    */ import javax.persistence.GeneratedValue;
/*    */ import javax.persistence.GenerationType;
/*    */ import javax.persistence.Id;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Entity
/*    */ public class ModbusReadConfig
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 9176340915920871528L;
/*    */   @Id
/*    */   @Comment("ID")
/*    */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*    */   private Long id;
/*    */   @Comment("腳位群組ID")
/*    */   private Integer pinGroupId;
/*    */   @Comment("Modbus資料類型")
/*    */   @Enumerated(EnumType.STRING)
/*    */   private ModbusDataType dataType;
/*    */   @Comment("讀取起始位址")
/*    */   private Integer startAddress;
/*    */   @Comment("讀取長度")
/*    */   private Integer length;
/*    */   
/*    */   public Long getId()
/*    */   {
/* 37 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(Long id) {
/* 41 */     this.id = id;
/*    */   }
/*    */   
/*    */   public Integer getPinGroupId() {
/* 45 */     return this.pinGroupId;
/*    */   }
/*    */   
/*    */   public void setPinGroupId(Integer pinGroupId) {
/* 49 */     this.pinGroupId = pinGroupId;
/*    */   }
/*    */   
/*    */   public ModbusDataType getDataType() {
/* 53 */     return this.dataType;
/*    */   }
/*    */   
/*    */   public void setDataType(ModbusDataType dataType) {
/* 57 */     this.dataType = dataType;
/*    */   }
/*    */   
/*    */   public Integer getStartAddress() {
/* 61 */     return this.startAddress;
/*    */   }
/*    */   
/*    */   public void setStartAddress(Integer startAddress) {
/* 65 */     this.startAddress = startAddress;
/*    */   }
/*    */   
/*    */   public Integer getLength() {
/* 69 */     return this.length;
/*    */   }
/*    */   
/*    */   public void setLength(Integer length) {
/* 73 */     this.length = length;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 78 */     int prime = 31;
/* 79 */     int result = 1;
/* 80 */     result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
/* 81 */     return result;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj)
/*    */   {
/* 86 */     if (this == obj) return true;
/* 87 */     if (obj == null) return false;
/* 88 */     if (getClass() != obj.getClass()) return false;
/* 89 */     ModbusReadConfig other = (ModbusReadConfig)obj;
/* 90 */     if (this.id == null) {
/* 91 */       if (other.id != null) return false;
/* 92 */     } else if (!this.id.equals(other.id)) return false;
/* 93 */     return true;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 98 */     return "ModbusReadConfig [id=" + this.id + ", pinGroupId=" + this.pinGroupId + ", dataType=" + this.dataType + ", startAddress=" + this.startAddress + ", length=" + this.length + "]";
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\fm-rtu-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\rtu\fm\model\ModbusReadConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */