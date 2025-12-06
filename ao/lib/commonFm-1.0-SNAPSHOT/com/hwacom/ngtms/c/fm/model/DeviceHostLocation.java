/*     */ package com.hwacom.ngtms.c.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
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
/*     */ @Entity
/*     */ public class DeviceHostLocation
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5523375815144498874L;
/*     */   @Id
/*     */   @Comment("ID")
/*     */   private Integer id;
/*     */   @Column(nullable=false)
/*     */   @Comment("機房名稱,需唯一")
/*     */   private String locName;
/*     */   @Comment("路線編號")
/*     */   private String lineId;
/*     */   @Comment("緯度")
/*     */   private Double latitude;
/*     */   @Comment("經度")
/*     */   private Double longitude;
/*     */   
/*     */   public Integer getId()
/*     */   {
/*  43 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Integer id) {
/*  47 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getLocName() {
/*  51 */     return this.locName;
/*     */   }
/*     */   
/*     */   public void setLocName(String locName) {
/*  55 */     this.locName = locName;
/*     */   }
/*     */   
/*     */   public String getLineId() {
/*  59 */     return this.lineId;
/*     */   }
/*     */   
/*     */   public void setLineId(String lineId) {
/*  63 */     this.lineId = lineId;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  68 */     return "HostLocation [id=" + this.id + "]";
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/*  73 */     int prime = 31;
/*  74 */     int result = 1;
/*  75 */     result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
/*  76 */     return result;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/*  81 */     if (this == obj) return true;
/*  82 */     if (obj == null) return false;
/*  83 */     if (getClass() != obj.getClass()) return false;
/*  84 */     DeviceHostLocation other = (DeviceHostLocation)obj;
/*  85 */     if (this.id == null) {
/*  86 */       if (other.id != null) return false;
/*  87 */     } else if (!this.id.equals(other.id)) return false;
/*  88 */     return true;
/*     */   }
/*     */   
/*     */   public Double getLatitude() {
/*  92 */     return this.latitude;
/*     */   }
/*     */   
/*     */   public void setLatitude(Double latitude) {
/*  96 */     this.latitude = latitude;
/*     */   }
/*     */   
/*     */   public Double getLongitude() {
/* 100 */     return this.longitude;
/*     */   }
/*     */   
/*     */   public void setLongitude(Double longitude) {
/* 104 */     this.longitude = longitude;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\DeviceHostLocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */