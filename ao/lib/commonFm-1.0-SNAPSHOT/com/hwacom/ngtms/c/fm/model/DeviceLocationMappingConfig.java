/*     */ package com.hwacom.ngtms.c.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.Id;
/*     */ import org.hibernate.annotations.GenericGenerator;
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
/*     */ 
/*     */ @Entity
/*     */ public class DeviceLocationMappingConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4462836378845820594L;
/*     */   @Id
/*     */   @Comment("UUID")
/*     */   @Column(length=40)
/*     */   @GeneratedValue(generator="system-uuid")
/*     */   @GenericGenerator(name="system-uuid", strategy="uuid2")
/*     */   private String id;
/*     */   @Comment("所屬機房")
/*     */   private String locationName;
/*     */   @Comment("設備編號")
/*     */   @Column(length=40)
/*     */   private String deviceName;
/*     */   @Comment("位置(如樓層等)")
/*     */   private String subLocation;
/*     */   @Comment("地點描述(如大門等)")
/*     */   private String description;
/*     */   @Comment("顯示順序")
/*     */   private Integer displayOrder;
/*     */   @Comment("機房底圖名稱")
/*     */   private String svgName;
/*     */   
/*     */   public String getId()
/*     */   {
/*  60 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  64 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getLocationName() {
/*  68 */     return this.locationName;
/*     */   }
/*     */   
/*     */   public void setLocationName(String locationName) {
/*  72 */     this.locationName = locationName;
/*     */   }
/*     */   
/*     */   public String getDeviceName() {
/*  76 */     return this.deviceName;
/*     */   }
/*     */   
/*     */   public void setDeviceName(String deviceName) {
/*  80 */     this.deviceName = deviceName;
/*     */   }
/*     */   
/*     */   public String getSubLocation() {
/*  84 */     return this.subLocation;
/*     */   }
/*     */   
/*     */   public void setSubLocation(String subLocation) {
/*  88 */     this.subLocation = subLocation;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  92 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(String description) {
/*  96 */     this.description = description;
/*     */   }
/*     */   
/*     */   public Integer getDisplayOrder() {
/* 100 */     return this.displayOrder;
/*     */   }
/*     */   
/*     */   public void setDisplayOrder(Integer displayOrder) {
/* 104 */     this.displayOrder = displayOrder;
/*     */   }
/*     */   
/*     */   public String getSvgName() {
/* 108 */     return this.svgName;
/*     */   }
/*     */   
/*     */   public void setSvgName(String svgName) {
/* 112 */     this.svgName = svgName;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 117 */     int prime = 31;
/* 118 */     int result = 1;
/* 119 */     result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
/* 120 */     return result;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 125 */     if (this == obj) return true;
/* 126 */     if (obj == null) return false;
/* 127 */     if (getClass() != obj.getClass()) return false;
/* 128 */     DeviceLocationMappingConfig other = (DeviceLocationMappingConfig)obj;
/* 129 */     if (this.id == null) {
/* 130 */       if (other.id != null) return false;
/* 131 */     } else if (!this.id.equals(other.id)) return false;
/* 132 */     return true;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 137 */     return "RoomDeviceLocationConfig [id=" + this.id + ", locationName=" + this.locationName + ", deviceName=" + this.deviceName + ", subLocation=" + this.subLocation + ", description=" + this.description + ", displayOrder=" + this.displayOrder + ", svgName=" + this.svgName + "]";
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\DeviceLocationMappingConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */