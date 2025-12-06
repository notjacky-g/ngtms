/*     */ package com.hwacom.ngtms.c.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.c.shared.Direction;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.EnumType;
/*     */ import javax.persistence.Enumerated;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.PrePersist;
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
/*     */ public class RoadUnitSection
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6374648026587878365L;
/*     */   @Id
/*     */   @Column(length=20)
/*     */   private String unitId;
/*     */   @Column(nullable=false)
/*     */   private Integer startMileage;
/*     */   @Column(nullable=false)
/*     */   private Integer endMileage;
/*     */   @Column(nullable=false, length=20)
/*     */   private String lineId;
/*     */   @Column(length=1)
/*     */   @Enumerated(EnumType.STRING)
/*     */   private Direction direction;
/*     */   @Column(nullable=false, length=30)
/*     */   private String sectionId;
/*     */   
/*     */   public RoadUnitSection() {}
/*     */   
/*     */   public RoadUnitSection(int startMileage, int endMileage, String lineId, Direction direction, String sectionId)
/*     */   {
/*  48 */     this.unitId = (sectionId + "_" + startMileage / 100);
/*  49 */     this.startMileage = Integer.valueOf(startMileage);
/*  50 */     this.endMileage = Integer.valueOf(endMileage);
/*  51 */     this.lineId = lineId;
/*  52 */     this.direction = direction;
/*  53 */     this.sectionId = sectionId;
/*     */   }
/*     */   
/*     */   @PrePersist
/*     */   public void makeId() {
/*  58 */     this.unitId = (this.sectionId + "_" + this.startMileage.intValue() / 100);
/*     */   }
/*     */   
/*     */   public String getUnitId() {
/*  62 */     return this.unitId;
/*     */   }
/*     */   
/*     */   public void setUnitId(String unitId) {
/*  66 */     this.unitId = unitId;
/*     */   }
/*     */   
/*     */   public Integer getStartMileage() {
/*  70 */     return this.startMileage;
/*     */   }
/*     */   
/*     */   public void setStartMileage(Integer startMileage) {
/*  74 */     this.startMileage = startMileage;
/*     */   }
/*     */   
/*     */   public Integer getEndMileage() {
/*  78 */     return this.endMileage;
/*     */   }
/*     */   
/*     */   public void setEndMileage(Integer endMileage) {
/*  82 */     this.endMileage = endMileage;
/*     */   }
/*     */   
/*     */   public String getLineId() {
/*  86 */     return this.lineId;
/*     */   }
/*     */   
/*     */   public void setLineId(String lineId) {
/*  90 */     this.lineId = lineId;
/*     */   }
/*     */   
/*     */   public Direction getDirection() {
/*  94 */     return this.direction;
/*     */   }
/*     */   
/*     */   public void setDirection(Direction direction) {
/*  98 */     this.direction = direction;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 103 */     int hash = 0;
/* 104 */     hash += (this.unitId != null ? this.unitId.hashCode() : 0);
/* 105 */     return hash;
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
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 119 */     if (!(object instanceof RoadUnitSection)) {
/* 120 */       return false;
/*     */     }
/* 122 */     RoadUnitSection other = (RoadUnitSection)object;
/* 123 */     if (((this.unitId == null) && (other.unitId != null)) || ((this.unitId != null) && 
/* 124 */       (!this.unitId.equals(other.unitId)))) {
/* 125 */       return false;
/*     */     }
/* 127 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\RoadUnitSection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */