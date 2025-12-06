/*     */ package com.hwacom.ngtms.c.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.c.shared.AreaType;
/*     */ import com.hwacom.ngtms.c.shared.DivisionType;
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
/*     */ @Entity
/*     */ public class RoadDivision
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7742577824409130384L;
/*     */   @Id
/*     */   @Comment("分割點編號")
/*     */   private String divisionId;
/*     */   @Comment("分割點名稱 ")
/*     */   private String divisionName;
/*     */   @Comment("分割點種類")
/*     */   @Column(nullable=false)
/*     */   @Enumerated(EnumType.STRING)
/*     */   private DivisionType divisionType;
/*     */   @Comment("里程數（公尺）")
/*     */   private Integer mileage;
/*     */   @Comment(" 路線編號（屬於哪一個道路）")
/*     */   @Column(nullable=false)
/*     */   private String lineId;
/*     */   @Comment("轄區 (N=北區、C=中區、S=南區)")
/*     */   @Column(nullable=false)
/*     */   @Enumerated(EnumType.STRING)
/*     */   private AreaType areaType;
/*     */   @Comment("切割點是否屬於交接路段。 交接路段切割點位於 北區或南區與中區的交界處，交接路段切割點至轄區路段允許輸入反應計畫事件")
/*  60 */   private Boolean boundary = Boolean.valueOf(false);
/*     */   
/*     */ 
/*     */   @Comment("轄區內的切割點皆可被選為 旅行時間目的地，轄區外的切割點，其 travelTimeVisible = true 時，可被選為 旅行時間目的地")
/*  64 */   private Boolean travelTimeVisible = Boolean.valueOf(false);
/*     */   
/*     */   public String toString() {
/*  67 */     return "com.hwacom.ngtms.common.fm.model.RoadDivision[ id=" + this.divisionId + " ]";
/*     */   }
/*     */   
/*     */   public RoadDivision() {}
/*     */   
/*     */   public RoadDivision(String divisionId, DivisionType divisionType, String lineId) {
/*  73 */     this.divisionId = divisionId;
/*  74 */     this.divisionType = divisionType;
/*  75 */     this.lineId = lineId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RoadDivision(String divisionId, String divisionName, DivisionType divisionType, Integer mileage, String lineId, AreaType areaType)
/*     */   {
/*  85 */     this.divisionId = divisionId;
/*  86 */     this.divisionName = divisionName;
/*  87 */     this.divisionType = divisionType;
/*  88 */     this.mileage = mileage;
/*  89 */     this.lineId = lineId;
/*  90 */     this.areaType = areaType;
/*     */   }
/*     */   
/*     */   public String getDivisionId() {
/*  94 */     return this.divisionId;
/*     */   }
/*     */   
/*     */   public void setDivisionId(String divisionId) {
/*  98 */     this.divisionId = divisionId;
/*     */   }
/*     */   
/*     */   public String getDivisionName() {
/* 102 */     return this.divisionName;
/*     */   }
/*     */   
/*     */   public void setDivisionName(String divisionName) {
/* 106 */     this.divisionName = divisionName;
/*     */   }
/*     */   
/*     */   public DivisionType getDivisionType() {
/* 110 */     return this.divisionType;
/*     */   }
/*     */   
/*     */   public void setDivisionType(DivisionType divisionType) {
/* 114 */     this.divisionType = divisionType;
/*     */   }
/*     */   
/*     */   public Integer getMileage() {
/* 118 */     return this.mileage;
/*     */   }
/*     */   
/*     */   public void setMileage(Integer mileage) {
/* 122 */     this.mileage = mileage;
/*     */   }
/*     */   
/*     */   public String getLineId() {
/* 126 */     return this.lineId;
/*     */   }
/*     */   
/*     */   public void setLineId(String lineId) {
/* 130 */     this.lineId = lineId;
/*     */   }
/*     */   
/*     */   public AreaType getAreaType() {
/* 134 */     return this.areaType;
/*     */   }
/*     */   
/*     */   public void setAreaType(AreaType areaType) {
/* 138 */     this.areaType = areaType;
/*     */   }
/*     */   
/*     */   public Boolean getBoundary() {
/* 142 */     return this.boundary;
/*     */   }
/*     */   
/*     */   public void setBoundary(Boolean boundary) {
/* 146 */     this.boundary = boundary;
/*     */   }
/*     */   
/*     */   public Boolean getTravelTimeVisible() {
/* 150 */     return this.travelTimeVisible;
/*     */   }
/*     */   
/*     */   public void setTravelTimeVisible(Boolean travelTimeVisible) {
/* 154 */     this.travelTimeVisible = travelTimeVisible;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\RoadDivision.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */