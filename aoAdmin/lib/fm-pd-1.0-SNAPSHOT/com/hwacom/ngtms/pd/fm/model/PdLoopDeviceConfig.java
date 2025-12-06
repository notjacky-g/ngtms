/*     */ package com.hwacom.ngtms.pd.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ public class PdLoopDeviceConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4462836378845820594L;
/*     */   @Id
/*     */   @Comment("ID")
/*     */   @GeneratedValue(strategy = GenerationType.IDENTITY)
/*     */   private Long id;
/*     */   @Comment("PD點設備編號")
/*     */   @Column(length = 40)
/*     */   private String pdDeviceName;
/*     */   @Comment("迴路編號")
/*     */   private String loopNo;
/*     */   @Comment("所屬設備編號")
/*     */   @Column(length = 40)
/*     */   private String deviceName;
/*     */   @Comment("線徑")
/*     */   private Integer diameter;
/*     */   @Comment("備註")
/*     */   private String memo;
/*     */   
/*     */   public Long getId() {
/*  56 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Long id) {
/*  60 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getPdDeviceName() {
/*  64 */     return this.pdDeviceName;
/*     */   }
/*     */   
/*     */   public void setPdDeviceName(String pdDeviceName) {
/*  68 */     this.pdDeviceName = pdDeviceName;
/*     */   }
/*     */   
/*     */   public String getLoopNo() {
/*  72 */     return this.loopNo;
/*     */   }
/*     */   
/*     */   public void setLoopNo(String loopNo) {
/*  76 */     this.loopNo = loopNo;
/*     */   }
/*     */   
/*     */   public String getDeviceName() {
/*  80 */     return this.deviceName;
/*     */   }
/*     */   
/*     */   public void setDeviceName(String deviceName) {
/*  84 */     this.deviceName = deviceName;
/*     */   }
/*     */   
/*     */   public Integer getDiameter() {
/*  88 */     return this.diameter;
/*     */   }
/*     */   
/*     */   public void setDiameter(Integer diameter) {
/*  92 */     this.diameter = diameter;
/*     */   }
/*     */   
/*     */   public String getMemo() {
/*  96 */     return this.memo;
/*     */   }
/*     */   
/*     */   public void setMemo(String memo) {
/* 100 */     this.memo = memo;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 105 */     int prime = 31;
/* 106 */     int result = 1;
/* 107 */     result = 31 * result + ((this.deviceName == null) ? 0 : this.deviceName.hashCode());
/* 108 */     result = 31 * result + ((this.diameter == null) ? 0 : this.diameter.hashCode());
/* 109 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 110 */     result = 31 * result + ((this.loopNo == null) ? 0 : this.loopNo.hashCode());
/* 111 */     result = 31 * result + ((this.memo == null) ? 0 : this.memo.hashCode());
/* 112 */     result = 31 * result + ((this.pdDeviceName == null) ? 0 : this.pdDeviceName.hashCode());
/* 113 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 118 */     if (this == obj) return true; 
/* 119 */     if (obj == null) return false; 
/* 120 */     if (getClass() != obj.getClass()) return false; 
/* 121 */     PdLoopDeviceConfig other = (PdLoopDeviceConfig)obj;
/* 122 */     if (this.deviceName == null)
/* 123 */     { if (other.deviceName != null) return false;  }
/* 124 */     else if (!this.deviceName.equals(other.deviceName)) { return false; }
/* 125 */      if (this.diameter == null)
/* 126 */     { if (other.diameter != null) return false;  }
/* 127 */     else if (!this.diameter.equals(other.diameter)) { return false; }
/* 128 */      if (this.id == null)
/* 129 */     { if (other.id != null) return false;  }
/* 130 */     else if (!this.id.equals(other.id)) { return false; }
/* 131 */      if (this.loopNo == null)
/* 132 */     { if (other.loopNo != null) return false;  }
/* 133 */     else if (!this.loopNo.equals(other.loopNo)) { return false; }
/* 134 */      if (this.memo == null)
/* 135 */     { if (other.memo != null) return false;  }
/* 136 */     else if (!this.memo.equals(other.memo)) { return false; }
/* 137 */      if (this.pdDeviceName == null)
/* 138 */     { if (other.pdDeviceName != null) return false;  }
/* 139 */     else if (!this.pdDeviceName.equals(other.pdDeviceName)) { return false; }
/* 140 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 145 */     return "PdLoopDeviceConfig [id=" + this.id + ", pdDeviceName=" + this.pdDeviceName + ", loopNo=" + this.loopNo + ", deviceName=" + this.deviceName + ", diameter=" + this.diameter + ", memo=" + this.memo + "]";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\fm-pd-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\pd\fm\model\PdLoopDeviceConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */