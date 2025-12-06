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
/*     */ public class PowerStatusData
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2896484878523676476L;
/*     */   @Id
/*     */   @Comment("機房序號")
/*     */   private String id;
/*     */   @Comment("用電警報(上個小時、昨日同時段、去年度平均累積值)")
/*     */   private String powerAlarm;
/*     */   @Comment("用電量(最近一小時累積值)")
/*     */   private Double powerLastHour;
/*     */   @Comment("用電量(當下前24小時累積值)")
/*     */   private Double power24Hour;
/*     */   @Comment("R相電壓(V)")
/*     */   private Double rv;
/*     */   @Comment("S相電壓(V)")
/*     */   private Double sv;
/*     */   @Comment("T相電壓(V)")
/*     */   private Double tv;
/*     */   @Comment("平均相電壓(V)")
/*     */   private Double av;
/*     */   @Comment("R相電流(I)")
/*     */   private Double ri;
/*     */   @Comment("S相電流(I)")
/*     */   private Double si;
/*     */   @Comment("T相電流(I)")
/*     */   private Double ti;
/*     */   @Comment("平均相電流(I)")
/*     */   private Double ai;
/*     */   @Comment("總功率")
/*     */   private Double kw;
/*     */   @Comment("總功率因數")
/*     */   private Double pf;
/*     */   @Comment("累積度數")
/*     */   private Double kwh;
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   @Comment("資料時間")
/*     */   private Date dataTime;
/*     */   
/*     */   public String getId() {
/*  68 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  72 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getPowerAlarm() {
/*  76 */     return this.powerAlarm;
/*     */   }
/*     */   
/*     */   public void setPowerAlarm(String powerAlarm) {
/*  80 */     this.powerAlarm = powerAlarm;
/*     */   }
/*     */   
/*     */   public Double getPowerLastHour() {
/*  84 */     return this.powerLastHour;
/*     */   }
/*     */   
/*     */   public void setPowerLastHour(Double powerLastHour) {
/*  88 */     this.powerLastHour = powerLastHour;
/*     */   }
/*     */   
/*     */   public Double getPower24Hour() {
/*  92 */     return this.power24Hour;
/*     */   }
/*     */   
/*     */   public void setPower24Hour(Double power24Hour) {
/*  96 */     this.power24Hour = power24Hour;
/*     */   }
/*     */   
/*     */   public Double getRv() {
/* 100 */     return this.rv;
/*     */   }
/*     */   
/*     */   public void setRv(Double rv) {
/* 104 */     this.rv = rv;
/*     */   }
/*     */   
/*     */   public Double getSv() {
/* 108 */     return this.sv;
/*     */   }
/*     */   
/*     */   public void setSv(Double sv) {
/* 112 */     this.sv = sv;
/*     */   }
/*     */   
/*     */   public Double getTv() {
/* 116 */     return this.tv;
/*     */   }
/*     */   
/*     */   public void setTv(Double tv) {
/* 120 */     this.tv = tv;
/*     */   }
/*     */   
/*     */   public Double getAv() {
/* 124 */     return this.av;
/*     */   }
/*     */   
/*     */   public void setAv(Double av) {
/* 128 */     this.av = av;
/*     */   }
/*     */   
/*     */   public Double getRi() {
/* 132 */     return this.ri;
/*     */   }
/*     */   
/*     */   public void setRi(Double ri) {
/* 136 */     this.ri = ri;
/*     */   }
/*     */   
/*     */   public Double getSi() {
/* 140 */     return this.si;
/*     */   }
/*     */   
/*     */   public void setSi(Double si) {
/* 144 */     this.si = si;
/*     */   }
/*     */   
/*     */   public Double getTi() {
/* 148 */     return this.ti;
/*     */   }
/*     */   
/*     */   public void setTi(Double ti) {
/* 152 */     this.ti = ti;
/*     */   }
/*     */   
/*     */   public Double getAi() {
/* 156 */     return this.ai;
/*     */   }
/*     */   
/*     */   public void setAi(Double ai) {
/* 160 */     this.ai = ai;
/*     */   }
/*     */   
/*     */   public Double getKw() {
/* 164 */     return this.kw;
/*     */   }
/*     */   
/*     */   public void setKw(Double kw) {
/* 168 */     this.kw = kw;
/*     */   }
/*     */   
/*     */   public Double getPf() {
/* 172 */     return this.pf;
/*     */   }
/*     */   
/*     */   public void setPf(Double pf) {
/* 176 */     this.pf = pf;
/*     */   }
/*     */   
/*     */   public Double getKwh() {
/* 180 */     return this.kwh;
/*     */   }
/*     */   
/*     */   public void setKwh(Double kwh) {
/* 184 */     this.kwh = kwh;
/*     */   }
/*     */   
/*     */   public Date getDataTime() {
/* 188 */     return this.dataTime;
/*     */   }
/*     */   
/*     */   public void setDataTime(Date dataTime) {
/* 192 */     this.dataTime = dataTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 197 */     int prime = 31;
/* 198 */     int result = 1;
/* 199 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 200 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 205 */     if (this == obj) return true; 
/* 206 */     if (obj == null) return false; 
/* 207 */     if (getClass() != obj.getClass()) return false; 
/* 208 */     PowerStatusData other = (PowerStatusData)obj;
/* 209 */     if (this.id == null)
/* 210 */     { if (other.id != null) return false;  }
/* 211 */     else if (!this.id.equals(other.id)) { return false; }
/* 212 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 217 */     return "PowerStatusDataInTime [id=" + this.id + ", powerAlarm=" + this.powerAlarm + ", powerLastHour=" + this.powerLastHour + ", power24Hour=" + this.power24Hour + ", rv=" + this.rv + ", sv=" + this.sv + ", tv=" + this.tv + ", av=" + this.av + ", ri=" + this.ri + ", si=" + this.si + ", ti=" + this.ti + ", ai=" + this.ai + ", kw=" + this.kw + ", pf=" + this.pf + ", kwh=" + this.kwh + ", dataTime=" + this.dataTime + "]";
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\model\PowerStatusData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */