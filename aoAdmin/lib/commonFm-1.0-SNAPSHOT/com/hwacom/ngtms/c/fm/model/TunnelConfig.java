/*     */ package com.hwacom.ngtms.c.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.base.util.KeyUtils;
/*     */ import com.hwacom.ngtms.c.shared.Direction;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.EnumType;
/*     */ import javax.persistence.Enumerated;
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
/*     */ public class TunnelConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   @Id
/*     */   private String tunnelId;
/*     */   @Column(nullable = false)
/*     */   @Comment("隧道名稱")
/*     */   private String tunnelName;
/*     */   @Comment("隧道的通訊協定代碼")
/*     */   private Integer tunnelCode;
/*     */   @Column(nullable = false)
/*     */   @Comment("道路分區ID")
/*     */   private String sectionId;
/*     */   @Column(nullable = false)
/*     */   @Comment("起始里程")
/*     */   private Integer startMileage;
/*     */   @Column(nullable = false)
/*     */   @Comment("結束里程")
/*     */   private Integer endMileage;
/*     */   @Column(nullable = false)
/*     */   @Comment("道路ID")
/*     */   private String lineId;
/*     */   @Column(nullable = false)
/*     */   @Comment("方向")
/*     */   @Enumerated(EnumType.STRING)
/*     */   private Direction direction;
/*     */   
/*     */   public TunnelConfig() {}
/*     */   
/*     */   public TunnelConfig(String lineId, Direction direction, Integer startMileage, Integer endMileage) {
/*  77 */     this.lineId = lineId;
/*  78 */     this.startMileage = startMileage;
/*  79 */     this.endMileage = endMileage;
/*  80 */     this.direction = direction;
/*  81 */     setTunnelId(KeyUtils.getKey(new Object[] { lineId, direction, startMileage, endMileage }));
/*     */   }
/*     */   
/*     */   public String getTunnelId() {
/*  85 */     return this.tunnelId;
/*     */   }
/*     */   
/*     */   public void setTunnelId(String tunnelId) {
/*  89 */     this.tunnelId = tunnelId;
/*     */   }
/*     */   
/*     */   public String getTunnelName() {
/*  93 */     return this.tunnelName;
/*     */   }
/*     */   
/*     */   public void setTunnelName(String tunnelName) {
/*  97 */     this.tunnelName = tunnelName;
/*     */   }
/*     */   
/*     */   public Integer getTunnelCode() {
/* 101 */     return this.tunnelCode;
/*     */   }
/*     */   
/*     */   public void setTunnelCode(Integer tunnelCode) {
/* 105 */     this.tunnelCode = tunnelCode;
/*     */   }
/*     */   
/*     */   public String getSectionId() {
/* 109 */     return this.sectionId;
/*     */   }
/*     */   
/*     */   public void setSectionId(String sectionId) {
/* 113 */     this.sectionId = sectionId;
/*     */   }
/*     */   
/*     */   public Integer getStartMileage() {
/* 117 */     return this.startMileage;
/*     */   }
/*     */   
/*     */   public void setStartMileage(Integer startMileage) {
/* 121 */     this.startMileage = startMileage;
/*     */   }
/*     */   
/*     */   public Integer getEndMileage() {
/* 125 */     return this.endMileage;
/*     */   }
/*     */   
/*     */   public void setEndMileage(Integer endMileage) {
/* 129 */     this.endMileage = endMileage;
/*     */   }
/*     */   
/*     */   public String getLineId() {
/* 133 */     return this.lineId;
/*     */   }
/*     */   
/*     */   public void setLineId(String lineId) {
/* 137 */     this.lineId = lineId;
/*     */   }
/*     */   
/*     */   public Direction getDirection() {
/* 141 */     return this.direction;
/*     */   }
/*     */   
/*     */   public void setDirection(Direction direction) {
/* 145 */     this.direction = direction;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 150 */     int prime = 31;
/* 151 */     int result = 1;
/* 152 */     result = 31 * result + ((this.tunnelId == null) ? 0 : this.tunnelId.hashCode());
/* 153 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 158 */     if (this == obj) return true; 
/* 159 */     if (obj == null) return false; 
/* 160 */     if (getClass() != obj.getClass()) return false; 
/* 161 */     TunnelConfig other = (TunnelConfig)obj;
/* 162 */     if (this.tunnelId == null)
/* 163 */     { if (other.tunnelId != null) return false;  }
/* 164 */     else if (!this.tunnelId.equals(other.tunnelId)) { return false; }
/* 165 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 170 */     return "TunnelConfig [tunnelId=" + this.tunnelId + ", tunnelName=" + this.tunnelName + ", tunnelCode=" + this.tunnelCode + ", sectionId=" + this.sectionId + ", startMileage=" + this.startMileage + ", endMileage=" + this.endMileage + ", lineId" + this.lineId + "]";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\TunnelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */