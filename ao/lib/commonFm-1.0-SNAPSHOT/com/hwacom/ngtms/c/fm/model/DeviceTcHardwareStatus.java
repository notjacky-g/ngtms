/*     */ package com.hwacom.ngtms.c.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.c.shared.TcProtocolType;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.EnumType;
/*     */ import javax.persistence.Enumerated;
/*     */ import javax.persistence.Id;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ public class DeviceTcHardwareStatus
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4474833581320291159L;
/*     */   @Id
/*     */   @Comment("設備類型-硬體狀態")
/*     */   private String id;
/*     */   @Column(length=40)
/*     */   @Comment("設備類型,共用為 null")
/*     */   private String deviceType;
/*     */   @Comment("硬體狀態描述")
/*     */   private String description;
/*     */   @Comment("硬體狀態名稱(中文)")
/*     */   private String name;
/*     */   @Comment("bit編號")
/*     */   private Integer bitNo;
/*     */   @Comment("共用硬體狀態")
/*     */   private Boolean common;
/*     */   @Comment("通訊協定版本")
/*     */   @Enumerated(EnumType.STRING)
/*  54 */   private TcProtocolType protocolType = TcProtocolType.FW2;
/*     */   
/*     */ 
/*     */   public String getId()
/*     */   {
/*  59 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  63 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getDeviceType() {
/*  67 */     return this.deviceType;
/*     */   }
/*     */   
/*     */   public void setDeviceType(String deviceType) {
/*  71 */     this.deviceType = deviceType;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  75 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(String description) {
/*  79 */     this.description = description;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  83 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  87 */     this.name = name;
/*     */   }
/*     */   
/*     */   public Integer getBitNo() {
/*  91 */     return this.bitNo;
/*     */   }
/*     */   
/*     */   public void setBitNo(Integer bitNo) {
/*  95 */     this.bitNo = bitNo;
/*     */   }
/*     */   
/*     */   public Boolean getCommon() {
/*  99 */     return this.common;
/*     */   }
/*     */   
/*     */   public void setCommon(Boolean common) {
/* 103 */     this.common = common;
/*     */   }
/*     */   
/*     */   public TcProtocolType getProtocolType() {
/* 107 */     return this.protocolType;
/*     */   }
/*     */   
/*     */   public void setProtocolType(TcProtocolType protocolType) {
/* 111 */     this.protocolType = protocolType;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 116 */     int prime = 31;
/* 117 */     int result = 1;
/* 118 */     result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
/* 119 */     return result;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 124 */     if (this == obj) return true;
/* 125 */     if (obj == null) return false;
/* 126 */     if (getClass() != obj.getClass()) return false;
/* 127 */     DeviceTcHardwareStatus other = (DeviceTcHardwareStatus)obj;
/* 128 */     if (this.id == null) {
/* 129 */       if (other.id != null) return false;
/* 130 */     } else if (!this.id.equals(other.id)) return false;
/* 131 */     return true;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 136 */     return "HardwareStatus [id=" + this.id + ", deviceType=" + this.deviceType + ", description=" + this.description + ", name=" + this.name + ", bitNo=" + this.bitNo + ", common=" + this.common + ", protocolType=" + this.protocolType + "]";
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\DeviceTcHardwareStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */