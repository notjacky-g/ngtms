/*     */ package com.hwacom.ngtms.c.dgs.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.c.shared.Direction;
/*     */ import java.io.Serializable;
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
/*     */ @Entity
/*     */ public class RoadLaneCount
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   @Id
/*     */   @Comment("id")
/*     */   private String id;
/*     */   @Comment("主線編號")
/*     */   private String lineId;
/*     */   @Comment("開始里程")
/*     */   private Integer startMile;
/*     */   @Comment("結束里程")
/*     */   private Integer endMile;
/*     */   @Comment("方向")
/*     */   @Enumerated(EnumType.STRING)
/*     */   private Direction direction;
/*     */   @Comment("車道數")
/*     */   private Integer laneCount;
/*     */   
/*     */   public int hashCode() {
/*  54 */     int hash = 0;
/*  55 */     hash += (this.id != null) ? this.id.hashCode() : 0;
/*     */     
/*  57 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/*  63 */     if (!(object instanceof RoadLaneCount)) {
/*  64 */       return false;
/*     */     }
/*  66 */     RoadLaneCount other = (RoadLaneCount)object;
/*  67 */     if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
/*  68 */       return false;
/*     */     }
/*  70 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  75 */     return "com.hwacom.ngtms.common.fm.model.LaneCount[ id = " + this.id + " ]";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/*  80 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setId(String id) {
/*  85 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLineId() {
/*  90 */     return this.lineId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLineId(String lineId) {
/*  95 */     this.lineId = lineId;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getStartMile() {
/* 100 */     return this.startMile;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStartMile(Integer startMile) {
/* 105 */     this.startMile = startMile;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getEndMile() {
/* 110 */     return this.endMile;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEndMile(Integer endMile) {
/* 115 */     this.endMile = endMile;
/*     */   }
/*     */ 
/*     */   
/*     */   public Direction getDirection() {
/* 120 */     return this.direction;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDirection(Direction direction) {
/* 125 */     this.direction = direction;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getLaneCount() {
/* 130 */     return this.laneCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLaneCount(Integer laneCount) {
/* 135 */     this.laneCount = laneCount;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonDgs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dgs\fm\model\RoadLaneCount.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */