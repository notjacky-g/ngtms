/*     */ package com.hwacom.ngtms.c.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
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
/*     */ @Entity
/*     */ public class RoadLine
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7962937567559951101L;
/*     */   @Id
/*     */   @Comment("路線編號")
/*     */   @Column(length = 20)
/*     */   private String lineId;
/*     */   @Comment("路線名稱")
/*     */   @Column(length = 20)
/*     */   private String lineName;
/*     */   @Comment("方向（NS=南北向，EW=東西向）")
/*     */   @Column(length = 2)
/*     */   @Enumerated(EnumType.STRING)
/*     */   private Direction direction;
/*     */   @Comment("開始里程（公尺）")
/*     */   private Integer startMileage;
/*     */   @Comment("結束里程（公尺）")
/*     */   private Integer endMileage;
/*     */   @Comment("備註")
/*     */   @Column(length = 50)
/*     */   private String memo;
/*     */   @Comment("ENABLE（Y = ENABLE、N=DISABLE）")
/*     */   private Boolean enable;
/*     */   private Integer gCodeId;
/*     */   
/*     */   public RoadLine() {}
/*     */   
/*     */   public RoadLine(String lineId) {
/*  68 */     this.lineId = lineId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RoadLine(String lineId, String lineName, Direction direction, Integer startMileage, Integer endMileage, String memo, Boolean enable) {
/*  79 */     this.lineId = lineId;
/*  80 */     this.lineName = lineName;
/*  81 */     this.direction = direction;
/*  82 */     this.startMileage = startMileage;
/*  83 */     this.endMileage = endMileage;
/*  84 */     this.memo = memo;
/*  85 */     this.enable = enable;
/*     */   }
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
/*     */   public RoadLine(String lineId, String lineName, Direction direction, Integer startMileage, Integer endMileage, String memo, Boolean enable, Integer gCodeId) {
/*  98 */     this.lineId = lineId;
/*  99 */     this.lineName = lineName;
/* 100 */     this.direction = direction;
/* 101 */     this.startMileage = startMileage;
/* 102 */     this.endMileage = endMileage;
/* 103 */     this.memo = memo;
/* 104 */     this.enable = enable;
/* 105 */     this.gCodeId = gCodeId;
/*     */   }
/*     */   
/*     */   public String getLineId() {
/* 109 */     return this.lineId;
/*     */   }
/*     */   
/*     */   public void setLineId(String lineId) {
/* 113 */     this.lineId = lineId;
/*     */   }
/*     */   
/*     */   public String getLineName() {
/* 117 */     return this.lineName;
/*     */   }
/*     */   
/*     */   public void setLineName(String lineName) {
/* 121 */     this.lineName = lineName;
/*     */   }
/*     */   
/*     */   public Direction getDirection() {
/* 125 */     return this.direction;
/*     */   }
/*     */   
/*     */   public void setDirection(Direction direction) {
/* 129 */     this.direction = direction;
/*     */   }
/*     */   
/*     */   public Integer getStartMileage() {
/* 133 */     return this.startMileage;
/*     */   }
/*     */   
/*     */   public void setStartMileage(Integer startMileage) {
/* 137 */     this.startMileage = startMileage;
/*     */   }
/*     */   
/*     */   public Integer getEndMileage() {
/* 141 */     return this.endMileage;
/*     */   }
/*     */   
/*     */   public void setEndMileage(Integer endMileage) {
/* 145 */     this.endMileage = endMileage;
/*     */   }
/*     */   
/*     */   public String getMemo() {
/* 149 */     return this.memo;
/*     */   }
/*     */   
/*     */   public void setMemo(String memo) {
/* 153 */     this.memo = memo;
/*     */   }
/*     */   
/*     */   public Boolean isEnable() {
/* 157 */     return this.enable;
/*     */   }
/*     */   
/*     */   public void setEnable(Boolean enable) {
/* 161 */     this.enable = enable;
/*     */   }
/*     */   
/*     */   public Integer getgCodeId() {
/* 165 */     return this.gCodeId;
/*     */   }
/*     */   
/*     */   public void setgCodeId(Integer gCodeId) {
/* 169 */     this.gCodeId = gCodeId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 177 */     return "RoadLine [lineId=" + this.lineId + ", lineName=" + this.lineName + ", direction=" + this.direction + ", startMileage=" + this.startMileage + ", endMileage=" + this.endMileage + ", memo=" + this.memo + ", enable=" + this.enable + ", gCodeId=" + this.gCodeId + "]";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\RoadLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */