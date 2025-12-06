/*     */ package com.hwacom.ngtms.ao.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Temporal;
/*     */ import javax.persistence.TemporalType;
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
/*     */ @Entity
/*     */ public class WaterPowerBaseHourLogData
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8252418302805740715L;
/*     */   @GeneratedValue(generator = "uuid")
/*     */   @GenericGenerator(name = "uuid", strategy = "uuid")
/*     */   @Id
/*     */   @Comment("UUID")
/*     */   private String id;
/*     */   @Comment("機房序號")
/*     */   private String roomId;
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   @Comment("資料時間")
/*     */   private Date dataTime;
/*     */   @Comment("每小時用水量")
/*     */   private Double hourWater;
/*     */   @Comment("每小時用電量")
/*     */   private Double hourPower;
/*     */   @Comment("每小時累積水量(度)")
/*     */   private Double hourAccumulationWater;
/*     */   @Comment("每小時累積電量(度)")
/*     */   private Double hourAccumulationPower;
/*     */   
/*     */   public String getId() {
/*  46 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  50 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getRoomId() {
/*  54 */     return this.roomId;
/*     */   }
/*     */   
/*     */   public void setRoomId(String roomId) {
/*  58 */     this.roomId = roomId;
/*     */   }
/*     */   
/*     */   public Date getDataTime() {
/*  62 */     return this.dataTime;
/*     */   }
/*     */   
/*     */   public void setDataTime(Date dataTime) {
/*  66 */     this.dataTime = dataTime;
/*     */   }
/*     */   
/*     */   public Double getHourWater() {
/*  70 */     return this.hourWater;
/*     */   }
/*     */   
/*     */   public void setHourWater(Double hourWater) {
/*  74 */     this.hourWater = hourWater;
/*     */   }
/*     */   
/*     */   public Double getHourPower() {
/*  78 */     return this.hourPower;
/*     */   }
/*     */   
/*     */   public void setHourPower(Double hourPower) {
/*  82 */     this.hourPower = hourPower;
/*     */   }
/*     */   
/*     */   public Double getHourAccumulationWater() {
/*  86 */     return this.hourAccumulationWater;
/*     */   }
/*     */   
/*     */   public void setHourAccumulationWater(Double hourAccumulationWater) {
/*  90 */     this.hourAccumulationWater = hourAccumulationWater;
/*     */   }
/*     */   
/*     */   public Double getHourAccumulationPower() {
/*  94 */     return this.hourAccumulationPower;
/*     */   }
/*     */   
/*     */   public void setHourAccumulationPower(Double hourAccumulationPower) {
/*  98 */     this.hourAccumulationPower = hourAccumulationPower;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 103 */     int prime = 31;
/* 104 */     int result = 1;
/* 105 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 106 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 111 */     if (this == obj) return true; 
/* 112 */     if (obj == null) return false; 
/* 113 */     if (getClass() != obj.getClass()) return false; 
/* 114 */     WaterPowerBaseHourLogData other = (WaterPowerBaseHourLogData)obj;
/* 115 */     if (this.id == null)
/* 116 */     { if (other.id != null) return false;  }
/* 117 */     else if (!this.id.equals(other.id)) { return false; }
/* 118 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 123 */     return "WaterPowerBaseHourLogData [id=" + this.id + ", roomId=" + this.roomId + ", dataTime=" + this.dataTime + ", hourWater=" + this.hourWater + ", hourPower=" + this.hourPower + ", hourAccumulationWater=" + this.hourAccumulationWater + ", hourAccumulationPower=" + this.hourAccumulationPower + "]";
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\model\WaterPowerBaseHourLogData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */