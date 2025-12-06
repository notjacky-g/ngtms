/*     */ package com.hwacom.ngtms.c.fm.model;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.c.shared.AreaType;
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
/*     */ public class BranchConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4904309908913603506L;
/*     */   @Id
/*     */   @Comment("UUID")
/*     */   private String id;
/*     */   @Comment("工務段名稱")
/*     */   @Column(nullable = false, length = 20)
/*     */   private String branchName;
/*     */   @Comment("快速道ID")
/*     */   private String expresswayId;
/*     */   @Comment("國道ID")
/*     */   private String lineId;
/*     */   @Comment("轄區起始公里數 ")
/*     */   private Integer startMileage;
/*     */   @Comment("分區 ")
/*     */   @Column(nullable = false)
/*     */   @Enumerated(EnumType.STRING)
/*     */   private AreaType areaType;
/*     */   @Comment("轄區結束公里數")
/*     */   private Integer endMileage;
/*     */   
/*     */   public String toString() {
/*  70 */     return MoreObjects.toStringHelper(this).add("super", super.toString()).add("id", this.id).add("branchName", this.branchName).add("expresswayId", this.expresswayId).add("freewayId", this.lineId).add("fromMilepost", this.startMileage).add("region", this.areaType).add("toMilepost", this.endMileage).toString();
/*     */   }
/*     */   
/*     */   public String getId() {
/*  74 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  78 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getBranchName() {
/*  82 */     return this.branchName;
/*     */   }
/*     */   
/*     */   public void setBranchName(String branchName) {
/*  86 */     this.branchName = branchName;
/*     */   }
/*     */   
/*     */   public String getExpresswayId() {
/*  90 */     return this.expresswayId;
/*     */   }
/*     */   
/*     */   public void setExpresswayId(String expresswayId) {
/*  94 */     this.expresswayId = expresswayId;
/*     */   }
/*     */   
/*     */   public String getLineId() {
/*  98 */     return this.lineId;
/*     */   }
/*     */   
/*     */   public void setLineId(String lineId) {
/* 102 */     this.lineId = lineId;
/*     */   }
/*     */   
/*     */   public Integer getStartMileage() {
/* 106 */     return this.startMileage;
/*     */   }
/*     */   
/*     */   public void setStartMileage(Integer startMileage) {
/* 110 */     this.startMileage = startMileage;
/*     */   }
/*     */   
/*     */   public AreaType getAreaType() {
/* 114 */     return this.areaType;
/*     */   }
/*     */   
/*     */   public void setAreaType(AreaType areaType) {
/* 118 */     this.areaType = areaType;
/*     */   }
/*     */   
/*     */   public Integer getEndMileage() {
/* 122 */     return this.endMileage;
/*     */   }
/*     */   
/*     */   public void setEndMileage(Integer endMileage) {
/* 126 */     this.endMileage = endMileage;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 131 */     int hash = 0;
/* 132 */     hash += (this.id != null) ? this.id.hashCode() : 0;
/* 133 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/* 140 */     if (!(object instanceof BranchConfig)) {
/* 141 */       return false;
/*     */     }
/* 143 */     BranchConfig other = (BranchConfig)object;
/* 144 */     if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
/* 145 */       return false;
/*     */     }
/* 147 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\BranchConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */