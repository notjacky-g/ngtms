/*     */ package com.hwacom.ngtms.c.fm.model;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Inheritance;
/*     */ import javax.persistence.InheritanceType;
/*     */ import javax.persistence.Temporal;
/*     */ import javax.persistence.TemporalType;
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ @Inheritance(strategy=InheritanceType.JOINED)
/*     */ public class DeviceStatus
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  19 */   public static final Integer STATUS_ONLINE = Integer.valueOf(0);
/*     */   
/*  21 */   public static final Integer STATUS_OFFLINE = Integer.valueOf(1);
/*     */   
/*  23 */   public static final Integer STATUS_DISABLE = Integer.valueOf(2);
/*     */   
/*     */ 
/*     */   @Id
/*     */   private String id;
/*     */   
/*     */ 
/*     */   public String deviceType;
/*     */   
/*     */ 
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   private Date dataTime;
/*     */   
/*     */ 
/*     */   private Integer commStatus;
/*     */   
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   private Date lastUpdateTime;
/*     */   
/*     */   private Integer lastUpdateStatus;
/*     */   
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  47 */     int prime = 31;
/*  48 */     int result = 1;
/*  49 */     result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
/*  50 */     return result;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/*  55 */     if (this == obj) return true;
/*  56 */     if (obj == null) return false;
/*  57 */     if (getClass() != obj.getClass()) return false;
/*  58 */     DeviceStatus other = (DeviceStatus)obj;
/*  59 */     if (this.id == null) {
/*  60 */       if (other.id != null) return false;
/*  61 */     } else if (!this.id.equals(other.id)) return false;
/*  62 */     return true;
/*     */   }
/*     */   
/*     */   public String getDeviceName() {
/*  66 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getId() {
/*  70 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  74 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getDeviceType() {
/*  78 */     return this.deviceType;
/*     */   }
/*     */   
/*     */   public void setDeviceType(String deviceType) {
/*  82 */     this.deviceType = deviceType;
/*     */   }
/*     */   
/*     */   public Date getTimestamp() {
/*  86 */     return this.dataTime;
/*     */   }
/*     */   
/*     */   public void setTimestamp(Date dataTime) {
/*  90 */     this.dataTime = dataTime;
/*     */   }
/*     */   
/*     */   public Integer getCommStatus() {
/*  94 */     return this.commStatus;
/*     */   }
/*     */   
/*     */   public void setCommStatus(Integer commStatus) {
/*  98 */     this.commStatus = commStatus;
/*     */   }
/*     */   
/*     */   public Date getLastUpdateTime() {
/* 102 */     return this.lastUpdateTime;
/*     */   }
/*     */   
/*     */   public void setLastUpdateTime(Date lastUpdateTime) {
/* 106 */     this.lastUpdateTime = lastUpdateTime;
/*     */   }
/*     */   
/*     */   public Integer getLastUpdateStatus() {
/* 110 */     return this.lastUpdateStatus;
/*     */   }
/*     */   
/*     */   public void setLastUpdateStatus(Integer lastUpdateStatus) {
/* 114 */     this.lastUpdateStatus = lastUpdateStatus;
/*     */   }
/*     */   
/*     */   public boolean isAlive() {
/* 118 */     if ((this.lastUpdateStatus == null) || (this.commStatus == null)) {
/* 119 */       return false;
/*     */     }
/* 121 */     return (this.lastUpdateStatus.intValue() == 0) && (this.commStatus.intValue() == 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isDataTimeExpire(int interval)
/*     */   {
/* 129 */     Date time = new Date();
/* 130 */     if (this.dataTime == null) return true;
/* 131 */     return time.getTime() - this.dataTime.getTime() > interval * 1000;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 136 */     return "DeviceStatus [id=" + this.id + ", dataTime=" + this.dataTime + ", commStatus=" + this.commStatus + ", lastUpdateTime=" + this.lastUpdateTime + ", lastUpdateStatus=" + this.lastUpdateStatus + "]";
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\DeviceStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */