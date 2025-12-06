/*     */ package com.hwacom.ngtms.rtu.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.GenerationType;
/*     */ import javax.persistence.Id;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ public class ModbusPinMapping
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6351736235465187765L;
/*     */   @Id
/*     */   @Comment("ID")
/*     */   @GeneratedValue(strategy = GenerationType.IDENTITY)
/*     */   private Long id;
/*     */   @Comment("設備名稱")
/*     */   @Column(nullable = false)
/*     */   private String deviceName;
/*     */   @Comment("IMap key")
/*     */   @Column(unique = true, nullable = false)
/*     */   private String keyName;
/*     */   @Comment("Modbus位址")
/*     */   @Column(nullable = false)
/*     */   private Integer address;
/*     */   @Comment("是否反相")
/*     */   @Column(nullable = false)
/*     */   private Boolean inversion;
/*     */   @Comment("第幾個bit")
/*     */   private Integer bitNumber;
/*     */   
/*     */   public Long getId() {
/*  41 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Long id) {
/*  45 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getDeviceName() {
/*  49 */     return this.deviceName;
/*     */   }
/*     */   
/*     */   public void setDeviceName(String deviceName) {
/*  53 */     this.deviceName = deviceName;
/*     */   }
/*     */   
/*     */   public String getKeyName() {
/*  57 */     return this.keyName;
/*     */   }
/*     */   
/*     */   public void setKeyName(String keyName) {
/*  61 */     this.keyName = keyName;
/*     */   }
/*     */   
/*     */   public Integer getAddress() {
/*  65 */     return this.address;
/*     */   }
/*     */   
/*     */   public void setAddress(Integer address) {
/*  69 */     this.address = address;
/*     */   }
/*     */   
/*     */   public Boolean getInversion() {
/*  73 */     return this.inversion;
/*     */   }
/*     */   
/*     */   public void setInversion(Boolean inversion) {
/*  77 */     this.inversion = inversion;
/*     */   }
/*     */   
/*     */   public Integer getBitNumber() {
/*  81 */     return this.bitNumber;
/*     */   }
/*     */   
/*     */   public void setBitNumber(Integer bitNumber) {
/*  85 */     this.bitNumber = bitNumber;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  90 */     int prime = 31;
/*  91 */     int result = 1;
/*  92 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/*  93 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  98 */     if (this == obj) return true; 
/*  99 */     if (obj == null) return false; 
/* 100 */     if (getClass() != obj.getClass()) return false; 
/* 101 */     ModbusPinMapping other = (ModbusPinMapping)obj;
/* 102 */     if (this.id == null)
/* 103 */     { if (other.id != null) return false;  }
/* 104 */     else if (!this.id.equals(other.id)) { return false; }
/* 105 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 110 */     return "ModbusPinMapping [id=" + this.id + ", deviceName=" + this.deviceName + ", keyName=" + this.keyName + ", address=" + this.address + ", inversion=" + this.inversion + "]";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\fm-rtu-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\rtu\fm\model\ModbusPinMapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */