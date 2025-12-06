/*     */ package com.hwacom.ngtms.pd.fm.model;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.persistence.CascadeType;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.FetchType;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.JoinColumn;
/*     */ import javax.persistence.OneToMany;
/*     */ import javax.persistence.Temporal;
/*     */ import javax.persistence.TemporalType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ public class PdStatus
/*     */   implements Serializable
/*     */ {
/*  28 */   public static final Integer COMM_STATUS_ONLINE = Integer.valueOf(0);
/*     */   
/*  30 */   public static final Integer COMM_STATUS_OFFLINE = Integer.valueOf(1);
/*     */   
/*  32 */   public static final Integer DOOR_CLOSE = Integer.valueOf(0);
/*     */   
/*  34 */   public static final Integer DOOR_OPEN = Integer.valueOf(1);
/*     */   
/*  36 */   public static final Integer RST_STATUS_ON = Integer.valueOf(0);
/*     */   
/*  38 */   public static final Integer RST_STATUS_OFF = Integer.valueOf(1);
/*     */   
/*  40 */   public static final Integer RST_STATUS_IGNORE = Integer.valueOf(-1);
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -5500387914683020237L;
/*     */ 
/*     */   
/*     */   @Id
/*     */   @Column(length = 40)
/*     */   private String deviceName;
/*     */ 
/*     */   
/*     */   @Column(nullable = false)
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   private Date dataTime;
/*     */ 
/*     */   
/*     */   private Integer connectivity;
/*     */ 
/*     */   
/*     */   private Integer doorOpen;
/*     */ 
/*     */   
/*     */   private Integer primaryR;
/*     */ 
/*     */   
/*     */   private Integer primaryS;
/*     */   
/*     */   private Integer primaryT;
/*     */   
/*     */   private Integer secondaryR;
/*     */   
/*     */   private Integer secondaryS;
/*     */   
/*     */   private Integer secondaryT;
/*     */   
/*     */   @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
/*     */   @JoinColumn(name = "pd_status_id")
/*  77 */   private List<PdLoopStatus> pdLoops = new ArrayList<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean compareStatus(PdStatus that) {
/*  82 */     int size = this.pdLoops.size();
/*  83 */     if (size != that.pdLoops.size()) {
/*  84 */       return false;
/*     */     }
/*     */     
/*  87 */     for (int i = 0; i < size; i++) {
/*  88 */       if (!((PdLoopStatus)this.pdLoops.get(i)).compareLoopStstus(that.pdLoops.get(i))) {
/*  89 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  93 */     if (Objects.equal(this.connectivity, that.connectivity) && 
/*  94 */       Objects.equal(this.doorOpen, that.doorOpen) && 
/*  95 */       Objects.equal(this.primaryR, that.primaryR) && 
/*  96 */       Objects.equal(this.primaryS, that.primaryS) && 
/*  97 */       Objects.equal(this.primaryT, that.primaryT) && 
/*  98 */       Objects.equal(this.secondaryR, that.secondaryR) && 
/*  99 */       Objects.equal(this.secondaryS, that.secondaryS) && 
/* 100 */       Objects.equal(this.secondaryT, that.secondaryT)) {
/* 101 */       return true;
/*     */     }
/* 103 */     return false;
/*     */   }
/*     */   
/*     */   public PdStatus(String deviceName, Date dataTime) {
/* 107 */     this.deviceName = deviceName;
/* 108 */     this.dataTime = dataTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public PdStatus() {}
/*     */ 
/*     */   
/*     */   public String getDeviceName() {
/* 116 */     return this.deviceName;
/*     */   }
/*     */   
/*     */   public void setDeviceName(String deviceName) {
/* 120 */     this.deviceName = deviceName;
/*     */   }
/*     */   
/*     */   public Date getDataTime() {
/* 124 */     return this.dataTime;
/*     */   }
/*     */   
/*     */   public void setDataTime(Date dataTime) {
/* 128 */     this.dataTime = dataTime;
/*     */   }
/*     */   
/*     */   public Integer getConnectivity() {
/* 132 */     return this.connectivity;
/*     */   }
/*     */   
/*     */   public void setConnectivity(Integer connectivity) {
/* 136 */     this.connectivity = connectivity;
/*     */   }
/*     */   
/*     */   public Integer getPrimaryR() {
/* 140 */     return this.primaryR;
/*     */   }
/*     */   
/*     */   public void setPrimaryR(Integer primaryR) {
/* 144 */     this.primaryR = primaryR;
/*     */   }
/*     */   
/*     */   public Integer getPrimaryS() {
/* 148 */     return this.primaryS;
/*     */   }
/*     */   
/*     */   public void setPrimaryS(Integer primaryS) {
/* 152 */     this.primaryS = primaryS;
/*     */   }
/*     */   
/*     */   public Integer getPrimaryT() {
/* 156 */     return this.primaryT;
/*     */   }
/*     */   
/*     */   public void setPrimaryT(Integer primaryT) {
/* 160 */     this.primaryT = primaryT;
/*     */   }
/*     */   
/*     */   public Integer getSecondaryR() {
/* 164 */     return this.secondaryR;
/*     */   }
/*     */   
/*     */   public void setSecondaryR(Integer secondaryR) {
/* 168 */     this.secondaryR = secondaryR;
/*     */   }
/*     */   
/*     */   public Integer getSecondaryS() {
/* 172 */     return this.secondaryS;
/*     */   }
/*     */   
/*     */   public void setSecondaryS(Integer secondaryS) {
/* 176 */     this.secondaryS = secondaryS;
/*     */   }
/*     */   
/*     */   public Integer getSecondaryT() {
/* 180 */     return this.secondaryT;
/*     */   }
/*     */   
/*     */   public void setSecondaryT(Integer secondaryT) {
/* 184 */     this.secondaryT = secondaryT;
/*     */   }
/*     */   
/*     */   public Integer getDoorOpen() {
/* 188 */     return this.doorOpen;
/*     */   }
/*     */   
/*     */   public void setDoorOpen(Integer doorOpen) {
/* 192 */     this.doorOpen = doorOpen;
/*     */   }
/*     */   
/*     */   public List<PdLoopStatus> getPdLoops() {
/* 196 */     return this.pdLoops;
/*     */   }
/*     */   
/*     */   public void setPdLoops(List<PdLoopStatus> pdLoops) {
/* 200 */     this.pdLoops = pdLoops;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 205 */     int prime = 31;
/* 206 */     int result = 1;
/* 207 */     result = 31 * result + ((this.deviceName == null) ? 0 : this.deviceName.hashCode());
/* 208 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 213 */     if (this == obj) return true; 
/* 214 */     if (obj == null) return false; 
/* 215 */     if (getClass() != obj.getClass()) return false; 
/* 216 */     PdStatus other = (PdStatus)obj;
/* 217 */     if (this.deviceName == null)
/* 218 */     { if (other.deviceName != null) return false;  }
/* 219 */     else if (!this.deviceName.equals(other.deviceName)) { return false; }
/* 220 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 225 */     return "PdStatus [devcieName=" + this.deviceName + ", dataTime=" + this.dataTime + ", connectivity=" + this.connectivity + ", doorOpen=" + this.doorOpen + ", primaryR=" + this.primaryR + ", primaryS=" + this.primaryS + ", primaryT=" + this.primaryT + ", secondaryR=" + this.secondaryR + ", secondaryS=" + this.secondaryS + ", secondaryT=" + this.secondaryT + "]";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\fm-pd-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\pd\fm\model\PdStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */