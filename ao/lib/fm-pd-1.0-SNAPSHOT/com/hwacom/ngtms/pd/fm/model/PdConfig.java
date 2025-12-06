/*     */ package com.hwacom.ngtms.pd.fm.model;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ public class PdConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4462836378845820594L;
/*     */   @Id
/*     */   @Comment("設備編號")
/*     */   @Column(length=40)
/*     */   private String deviceName;
/*     */   @Comment("電錶號")
/*     */   private String meterNo;
/*     */   @Comment("所屬區數")
/*     */   private String area;
/*     */   @Comment("聯絡電話")
/*     */   private String phone;
/*     */   @Comment("總迴路數")
/*     */   private Integer loopNo;
/*     */   
/*     */   public String getDeviceName()
/*     */   {
/*  48 */     return this.deviceName;
/*     */   }
/*     */   
/*     */   public void setDeviceName(String deviceName) {
/*  52 */     this.deviceName = deviceName;
/*     */   }
/*     */   
/*     */   public String getMeterNo() {
/*  56 */     return this.meterNo;
/*     */   }
/*     */   
/*     */   public void setMeterNo(String meterNo) {
/*  60 */     this.meterNo = meterNo;
/*     */   }
/*     */   
/*     */   public String getArea() {
/*  64 */     return this.area;
/*     */   }
/*     */   
/*     */   public void setArea(String area) {
/*  68 */     this.area = area;
/*     */   }
/*     */   
/*     */   public String getPhone() {
/*  72 */     return this.phone;
/*     */   }
/*     */   
/*     */   public void setPhone(String phone) {
/*  76 */     this.phone = phone;
/*     */   }
/*     */   
/*     */   public Integer getLoopNo() {
/*  80 */     return this.loopNo;
/*     */   }
/*     */   
/*     */   public void setLoopNo(Integer loopNo) {
/*  84 */     this.loopNo = loopNo;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/*  89 */     int prime = 31;
/*  90 */     int result = 1;
/*  91 */     result = 31 * result + (this.deviceName == null ? 0 : this.deviceName.hashCode());
/*  92 */     return result;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/*  97 */     if (this == obj) return true;
/*  98 */     if (obj == null) return false;
/*  99 */     if (getClass() != obj.getClass()) return false;
/* 100 */     PdConfig other = (PdConfig)obj;
/* 101 */     if (this.deviceName == null) {
/* 102 */       if (other.deviceName != null) return false;
/* 103 */     } else if (!this.deviceName.equals(other.deviceName)) return false;
/* 104 */     return true;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 109 */     return "PdConfig [deviceName=" + this.deviceName + ", meterNo=" + this.meterNo + ", area=" + this.area + ", phone=" + this.phone + ", loopNo=" + this.loopNo + "]";
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\fm-pd-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\pd\fm\model\PdConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */