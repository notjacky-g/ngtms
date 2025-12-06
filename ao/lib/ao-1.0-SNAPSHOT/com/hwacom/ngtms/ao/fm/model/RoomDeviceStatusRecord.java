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
/*     */ @Entity
/*     */ public class RoomDeviceStatusRecord
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2607956956441668774L;
/*     */   @GeneratedValue(generator = "uuid")
/*     */   @GenericGenerator(name = "uuid", strategy = "uuid")
/*     */   @Id
/*     */   @Comment("UUID")
/*     */   private String id;
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   @Comment("時間")
/*     */   private Date dataTime;
/*     */   @Comment("設備編號")
/*     */   private String deviceName;
/*     */   @Comment("訊號類別")
/*     */   private String signalType;
/*     */   @Comment("訊號連線狀態")
/*     */   private Integer rtuStatus;
/*     */   @Comment("訊號狀態")
/*     */   private String density;
/*     */   @Comment("類比訊號等級")
/*     */   private Integer level;
/*     */   
/*     */   public String getDeviceName() {
/*  45 */     return this.deviceName;
/*     */   }
/*     */   
/*     */   public void setDeviceName(String deviceName) {
/*  49 */     this.deviceName = deviceName;
/*     */   }
/*     */   
/*     */   public String getId() {
/*  53 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  57 */     this.id = id;
/*     */   }
/*     */   
/*     */   public Date getDataTime() {
/*  61 */     return this.dataTime;
/*     */   }
/*     */   
/*     */   public void setDataTime(Date dataTime) {
/*  65 */     this.dataTime = dataTime;
/*     */   }
/*     */   
/*     */   public String getSignalType() {
/*  69 */     return this.signalType;
/*     */   }
/*     */   
/*     */   public void setSignalType(String signalType) {
/*  73 */     this.signalType = signalType;
/*     */   }
/*     */   
/*     */   public Integer getRtuStatus() {
/*  77 */     return this.rtuStatus;
/*     */   }
/*     */   
/*     */   public void setRtuStatus(Integer rtuStatus) {
/*  81 */     this.rtuStatus = rtuStatus;
/*     */   }
/*     */   
/*     */   public String getDensity() {
/*  85 */     return this.density;
/*     */   }
/*     */   
/*     */   public void setDensity(String density) {
/*  89 */     this.density = density;
/*     */   }
/*     */   
/*     */   public Integer getLevel() {
/*  93 */     return this.level;
/*     */   }
/*     */   
/*     */   public void setLevel(Integer level) {
/*  97 */     this.level = level;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 102 */     int prime = 31;
/* 103 */     int result = 1;
/* 104 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 105 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 110 */     if (this == obj) return true; 
/* 111 */     if (obj == null) return false; 
/* 112 */     if (getClass() != obj.getClass()) return false; 
/* 113 */     RoomDeviceStatusRecord other = (RoomDeviceStatusRecord)obj;
/* 114 */     if (this.id == null)
/* 115 */     { if (other.id != null) return false;  }
/* 116 */     else if (!this.id.equals(other.id)) { return false; }
/* 117 */      return true;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\model\RoomDeviceStatusRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */