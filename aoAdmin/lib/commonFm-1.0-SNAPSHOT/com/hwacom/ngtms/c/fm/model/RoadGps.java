/*     */ package com.hwacom.ngtms.c.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
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
/*     */ public class RoadGps
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8482196403293616659L;
/*     */   public static final String LATITUDE = "latitude";
/*     */   public static final String LONGITUDE = "longitude";
/*     */   @Id
/*     */   @GeneratedValue(strategy = GenerationType.IDENTITY)
/*     */   private Long no;
/*     */   @Comment("路線")
/*     */   private String lineId;
/*     */   @Comment("公里數1")
/*     */   private Double km1;
/*     */   @Comment("公里數2")
/*     */   private Double km2;
/*     */   @Comment("緯度 ")
/*     */   private Double lat;
/*     */   @Comment("經度 ")
/*     */   private Double lng;
/*     */   
/*     */   public Double getKm1() {
/*  47 */     return this.km1;
/*     */   }
/*     */   
/*     */   public void setKm1(Double km1) {
/*  51 */     this.km1 = km1;
/*     */   }
/*     */   
/*     */   public Double getKm2() {
/*  55 */     return this.km2;
/*     */   }
/*     */   
/*     */   public void setKm2(Double km2) {
/*  59 */     this.km2 = km2;
/*     */   }
/*     */   
/*     */   public Long getNo() {
/*  63 */     return this.no;
/*     */   }
/*     */   
/*     */   public void setNo(Long no) {
/*  67 */     this.no = no;
/*     */   }
/*     */   
/*     */   public String getLineId() {
/*  71 */     return this.lineId;
/*     */   }
/*     */   
/*     */   public void setLineId(String lineId) {
/*  75 */     this.lineId = lineId;
/*     */   }
/*     */   
/*     */   public Double getLat() {
/*  79 */     return this.lat;
/*     */   }
/*     */   
/*     */   public void setLat(Double lat) {
/*  83 */     this.lat = lat;
/*     */   }
/*     */   
/*     */   public Double getLng() {
/*  87 */     return this.lng;
/*     */   }
/*     */   
/*     */   public void setLng(Double lng) {
/*  91 */     this.lng = lng;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  96 */     return "RoadGps [no=" + this.no + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 101 */     int prime = 31;
/* 102 */     int result = 1;
/* 103 */     result = 31 * result + ((this.no == null) ? 0 : this.no.hashCode());
/* 104 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 109 */     if (this == obj) return true; 
/* 110 */     if (obj == null) return false; 
/* 111 */     if (getClass() != obj.getClass()) return false; 
/* 112 */     RoadGps other = (RoadGps)obj;
/* 113 */     if (this.no == null)
/* 114 */     { if (other.no != null) return false;  }
/* 115 */     else if (!this.no.equals(other.no)) { return false; }
/* 116 */      return true;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\RoadGps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */