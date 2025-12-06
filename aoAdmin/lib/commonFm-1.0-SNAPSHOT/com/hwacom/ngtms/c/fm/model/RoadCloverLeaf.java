/*     */ package com.hwacom.ngtms.c.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.c.shared.Direction;
/*     */ import com.hwacom.ngtms.c.shared.DivisionType;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.EnumType;
/*     */ import javax.persistence.Enumerated;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.Id;
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
/*     */ public class RoadCloverLeaf
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   @Id
/*     */   @Comment("UUID")
/*     */   @GeneratedValue(generator = "system-uuid")
/*     */   @GenericGenerator(name = "system-uuid", strategy = "uuid2")
/*     */   private String id;
/*     */   @Comment("分割點種類（C=系統交流道、I=交流道、T=收費站、S=服務區） ")
/*     */   @Enumerated(EnumType.STRING)
/*     */   private DivisionType divisionType;
/*     */   @Comment("主線編號1")
/*     */   private String lineId1;
/*     */   @Comment("里程數1")
/*     */   private Integer mileage1;
/*     */   @Comment("方向1")
/*     */   @Enumerated(EnumType.STRING)
/*     */   private Direction direction1;
/*     */   @Comment("主線編號2")
/*     */   private String lineId2;
/*     */   @Comment("里程數2")
/*     */   private Integer mileage2;
/*     */   @Comment("方向2")
/*     */   @Enumerated(EnumType.STRING)
/*     */   private Direction direction2;
/*     */   
/*     */   public int hashCode() {
/*  69 */     int hash = 0;
/*  70 */     hash += (this.id != null) ? this.id.hashCode() : 0;
/*     */     
/*  72 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/*  78 */     if (!(object instanceof RoadCloverLeaf)) {
/*  79 */       return false;
/*     */     }
/*  81 */     RoadCloverLeaf other = (RoadCloverLeaf)object;
/*  82 */     if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
/*  83 */       return false;
/*     */     }
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  90 */     return "com.hwacom.ngtms.common.fm.model.CloverLeaf[ id = " + this.id + " ]";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/*  95 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setId(String id) {
/* 100 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   public DivisionType getDivisionType() {
/* 105 */     return this.divisionType;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDivisionType(DivisionType divisionType) {
/* 110 */     this.divisionType = divisionType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLineId1() {
/* 115 */     return this.lineId1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLineId1(String lineId1) {
/* 120 */     this.lineId1 = lineId1;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getMileage1() {
/* 125 */     return this.mileage1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMileage1(Integer mileage1) {
/* 130 */     this.mileage1 = mileage1;
/*     */   }
/*     */ 
/*     */   
/*     */   public Direction getDirection1() {
/* 135 */     return this.direction1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDirection1(Direction direction1) {
/* 140 */     this.direction1 = direction1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLineId2() {
/* 145 */     return this.lineId2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLineId2(String lineId2) {
/* 150 */     this.lineId2 = lineId2;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getMileage2() {
/* 155 */     return this.mileage2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMileage2(Integer mileage2) {
/* 160 */     this.mileage2 = mileage2;
/*     */   }
/*     */ 
/*     */   
/*     */   public Direction getDirection2() {
/* 165 */     return this.direction2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDirection2(Direction direction2) {
/* 170 */     this.direction2 = direction2;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\RoadCloverLeaf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */