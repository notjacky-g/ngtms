/*     */ package com.hwacom.ngtms.ao.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Column;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ public class PdDeviceStatusRecord
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3213977767138201795L;
/*     */   @GeneratedValue(generator = "uuid")
/*     */   @GenericGenerator(name = "uuid", strategy = "uuid")
/*     */   @Id
/*     */   @Comment("UUID")
/*     */   private String id;
/*     */   @Column(length = 40)
/*     */   private String deviceName;
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   @Comment("時間")
/*     */   private Date dataTime;
/*     */   @Comment("連線狀態")
/*     */   private Integer connectivity;
/*     */   @Comment("箱門狀態")
/*     */   private Integer doorOpen;
/*     */   @Comment("一次側(R)狀態")
/*     */   private Integer primaryR;
/*     */   @Comment("一次側(S)狀態")
/*     */   private Integer primaryS;
/*     */   @Comment("一次側(T)狀態")
/*     */   private Integer primaryT;
/*     */   @Comment("二次側(R)狀態")
/*     */   private Integer secondaryR;
/*     */   @Comment("二次側(S)狀態")
/*     */   private Integer secondaryS;
/*     */   @Comment("二次側(T)狀態")
/*     */   private Integer secondaryT;
/*     */   @Comment("分迴路(1)狀態")
/*     */   private Integer loop1Status;
/*     */   @Comment("分迴路(2)狀態")
/*     */   private Integer loop2Status;
/*     */   @Comment("分迴路(3)狀態")
/*     */   private Integer loop3Status;
/*     */   @Comment("分迴路(4)狀態")
/*     */   private Integer loop4Status;
/*     */   @Comment("分迴路(5)狀態")
/*     */   private Integer loop5Status;
/*     */   
/*     */   public String getId() {
/*  73 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  77 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getDeviceName() {
/*  81 */     return this.deviceName;
/*     */   }
/*     */   
/*     */   public void setDeviceName(String deviceName) {
/*  85 */     this.deviceName = deviceName;
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
/*     */   public Integer getConnectivity() {
/*  97 */     return this.connectivity;
/*     */   }
/*     */   
/*     */   public void setConnectivity(Integer connectivity) {
/* 101 */     this.connectivity = connectivity;
/*     */   }
/*     */   
/*     */   public Integer getDoorOpen() {
/* 105 */     return this.doorOpen;
/*     */   }
/*     */   
/*     */   public void setDoorOpen(Integer doorOpen) {
/* 109 */     this.doorOpen = doorOpen;
/*     */   }
/*     */   
/*     */   public Integer getPrimaryR() {
/* 113 */     return this.primaryR;
/*     */   }
/*     */   
/*     */   public void setPrimaryR(Integer primaryR) {
/* 117 */     this.primaryR = primaryR;
/*     */   }
/*     */   
/*     */   public Integer getPrimaryS() {
/* 121 */     return this.primaryS;
/*     */   }
/*     */   
/*     */   public void setPrimaryS(Integer primaryS) {
/* 125 */     this.primaryS = primaryS;
/*     */   }
/*     */   
/*     */   public Integer getPrimaryT() {
/* 129 */     return this.primaryT;
/*     */   }
/*     */   
/*     */   public void setPrimaryT(Integer primaryT) {
/* 133 */     this.primaryT = primaryT;
/*     */   }
/*     */   
/*     */   public Integer getSecondaryR() {
/* 137 */     return this.secondaryR;
/*     */   }
/*     */   
/*     */   public void setSecondaryR(Integer secondaryR) {
/* 141 */     this.secondaryR = secondaryR;
/*     */   }
/*     */   
/*     */   public Integer getSecondaryS() {
/* 145 */     return this.secondaryS;
/*     */   }
/*     */   
/*     */   public void setSecondaryS(Integer secondaryS) {
/* 149 */     this.secondaryS = secondaryS;
/*     */   }
/*     */   
/*     */   public Integer getSecondaryT() {
/* 153 */     return this.secondaryT;
/*     */   }
/*     */   
/*     */   public void setSecondaryT(Integer secondaryT) {
/* 157 */     this.secondaryT = secondaryT;
/*     */   }
/*     */   
/*     */   public Integer getLoop1Status() {
/* 161 */     return this.loop1Status;
/*     */   }
/*     */   
/*     */   public void setLoop1Status(Integer loop1Status) {
/* 165 */     this.loop1Status = loop1Status;
/*     */   }
/*     */   
/*     */   public Integer getLoop2Status() {
/* 169 */     return this.loop2Status;
/*     */   }
/*     */   
/*     */   public void setLoop2Status(Integer loop2Status) {
/* 173 */     this.loop2Status = loop2Status;
/*     */   }
/*     */   
/*     */   public Integer getLoop3Status() {
/* 177 */     return this.loop3Status;
/*     */   }
/*     */   
/*     */   public void setLoop3Status(Integer loop3Status) {
/* 181 */     this.loop3Status = loop3Status;
/*     */   }
/*     */   
/*     */   public Integer getLoop4Status() {
/* 185 */     return this.loop4Status;
/*     */   }
/*     */   
/*     */   public void setLoop4Status(Integer loop4Status) {
/* 189 */     this.loop4Status = loop4Status;
/*     */   }
/*     */   
/*     */   public Integer getLoop5Status() {
/* 193 */     return this.loop5Status;
/*     */   }
/*     */   
/*     */   public void setLoop5Status(Integer loop5Status) {
/* 197 */     this.loop5Status = loop5Status;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 202 */     int prime = 31;
/* 203 */     int result = 1;
/* 204 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 205 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 210 */     if (this == obj) return true; 
/* 211 */     if (obj == null) return false; 
/* 212 */     if (getClass() != obj.getClass()) return false; 
/* 213 */     PdDeviceStatusRecord other = (PdDeviceStatusRecord)obj;
/* 214 */     if (this.id == null)
/* 215 */     { if (other.id != null) return false;  }
/* 216 */     else if (!this.id.equals(other.id)) { return false; }
/* 217 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 222 */     return "PdDeviceStatusRecord [id=" + this.id + ", deviceName=" + this.deviceName + ", dataTime=" + this.dataTime + ", connectivity=" + this.connectivity + ", doorOpen=" + this.doorOpen + ", primaryR=" + this.primaryR + ", primaryS=" + this.primaryS + ", primaryT=" + this.primaryT + ", secondaryR=" + this.secondaryR + ", secondaryS=" + this.secondaryS + ", secondaryT=" + this.secondaryT + ", loop1Status=" + this.loop1Status + ", loop2Status=" + this.loop2Status + ", loop3Status=" + this.loop3Status + ", loop4Status=" + this.loop4Status + ", loop5Status=" + this.loop5Status + "]";
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\model\PdDeviceStatusRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */