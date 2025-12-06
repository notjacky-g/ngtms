/*     */ package com.hwacom.ngtms.ao.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Temporal;
/*     */ import javax.persistence.TemporalType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ public class WaterStatusData
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7060960630452100771L;
/*     */   @Id
/*     */   @Comment("機房序號")
/*     */   private String id;
/*     */   @Comment("用水警報")
/*     */   private String waterAlarm;
/*     */   @Comment("用水量(最近一小時累積值)")
/*     */   private Double waterLastHour;
/*     */   @Comment("用水量(當下前24小時累積值)")
/*     */   private Double water24Hour;
/*     */   @Comment("累積度數")
/*     */   private Double cumulateValue;
/*     */   @Comment("瞬間值(m3/h)")
/*     */   private Double instantaneousValue;
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   @Comment("資料時間")
/*     */   private Date dataTime;
/*     */   
/*     */   public String getId() {
/*  41 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  45 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getWaterAlarm() {
/*  49 */     return this.waterAlarm;
/*     */   }
/*     */   
/*     */   public void setWaterAlarm(String waterAlarm) {
/*  53 */     this.waterAlarm = waterAlarm;
/*     */   }
/*     */   
/*     */   public Double getWaterLastHour() {
/*  57 */     return this.waterLastHour;
/*     */   }
/*     */   
/*     */   public void setWaterLastHour(Double waterLastHour) {
/*  61 */     this.waterLastHour = waterLastHour;
/*     */   }
/*     */   
/*     */   public Double getWater24Hour() {
/*  65 */     return this.water24Hour;
/*     */   }
/*     */   
/*     */   public void setWater24Hour(Double water24Hour) {
/*  69 */     this.water24Hour = water24Hour;
/*     */   }
/*     */   
/*     */   public Double getCumulateValue() {
/*  73 */     return this.cumulateValue;
/*     */   }
/*     */   
/*     */   public void setCumulateValue(Double cumulateValue) {
/*  77 */     this.cumulateValue = cumulateValue;
/*     */   }
/*     */   
/*     */   public Double getInstantaneousValue() {
/*  81 */     return this.instantaneousValue;
/*     */   }
/*     */   
/*     */   public void setInstantaneousValue(Double instantaneousValue) {
/*  85 */     this.instantaneousValue = instantaneousValue;
/*     */   }
/*     */   
/*     */   public Date getDataTime() {
/*  89 */     return this.dataTime;
/*     */   }
/*     */   
/*     */   public void setDataTime(Date dataTime) {
/*  93 */     this.dataTime = dataTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  98 */     int prime = 31;
/*  99 */     int result = 1;
/* 100 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 101 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 106 */     if (this == obj) return true; 
/* 107 */     if (obj == null) return false; 
/* 108 */     if (getClass() != obj.getClass()) return false; 
/* 109 */     WaterStatusData other = (WaterStatusData)obj;
/* 110 */     if (this.id == null)
/* 111 */     { if (other.id != null) return false;  }
/* 112 */     else if (!this.id.equals(other.id)) { return false; }
/* 113 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 118 */     return "WaterStatusDataInTime [id=" + this.id + ", waterAlarm=" + this.waterAlarm + ", waterLastHour=" + this.waterLastHour + ", water24Hour=" + this.water24Hour + ", cumulateValue=" + this.cumulateValue + ", instantaneousValue=" + this.instantaneousValue + ", dataTime=" + this.dataTime + "]";
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\model\WaterStatusData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */