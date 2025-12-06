/*     */ package com.hwacom.ngtms.c.dgs.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.c.shared.Direction;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.persistence.CascadeType;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.FetchType;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.JoinColumn;
/*     */ import javax.persistence.OneToMany;
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
/*     */ public class RingRoadConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5164464022681317978L;
/*     */   @Id
/*     */   @Comment("startDivisionId + \"-\" + startDirection+ \"-\" + endDivisionId + \"-\" + endDirection")
/*     */   private String id;
/*     */   @Comment("所屬出口分割點")
/*     */   @Column(nullable = false)
/*     */   private String startDivisionId;
/*     */   @Comment("所屬入口分割點 ")
/*     */   @Column
/*     */   private String endDivisionId;
/*     */   @Comment("")
/*     */   @Column(nullable = false)
/*     */   private String startLineId;
/*     */   @Comment("出口方向")
/*     */   @Column(nullable = false)
/*     */   private Direction startDirection;
/*     */   @Comment("出口路線里程數 ")
/*     */   @Column(nullable = false)
/*     */   private Integer startMileage;
/*     */   @Comment("出口描述")
/*     */   @Column
/*     */   private String startDescription;
/*     */   @Comment("入口路線")
/*     */   @Column(nullable = false)
/*     */   private String endLineId;
/*     */   @Comment("入口方向")
/*     */   @Column(nullable = false)
/*     */   private Direction endDirection;
/*     */   @Comment("入口路線里程數")
/*     */   @Column(nullable = false)
/*     */   private Integer endMileage;
/*     */   @Comment("入口描述")
/*     */   @Column
/*     */   private String endDescription;
/*     */   @Comment("出口前VD (環道起始端前VD)")
/*     */   @Column
/*     */   private String exitVd;
/*     */   @Comment("環道VD")
/*     */   @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
/*     */   @JoinColumn(name = "RingRoad_id")
/*  89 */   private List<RingRoadVd> RingRoadVds = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Comment("環道長度")
/*     */   @Column(nullable = false)
/*     */   private Integer length;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Comment("速限")
/*     */   @Column(nullable = false)
/* 105 */   private Integer speedLimit = Integer.valueOf(100);
/*     */ 
/*     */ 
/*     */   
/*     */   @Comment("預設旅行時間")
/*     */   private Integer freeTravelTime;
/*     */ 
/*     */ 
/*     */   
/*     */   @Comment("對應etag區段 ，可不設定 ")
/*     */   @Column
/*     */   private String etagSectionId;
/*     */ 
/*     */ 
/*     */   
/*     */   public RingRoadConfig() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public RingRoadConfig(String startDivisionId, String startLineId, Direction startDirection, String endDivisionId, String endLineId, Direction endDirection) {
/* 125 */     this.startDivisionId = startDivisionId;
/* 126 */     this.startLineId = startLineId;
/* 127 */     this.startDirection = startDirection;
/* 128 */     this.endLineId = endLineId;
/* 129 */     this.endDirection = endDirection;
/* 130 */     this.endDivisionId = endDivisionId;
/* 131 */     this.id = startDivisionId + "-" + startDirection + "-" + endDivisionId + "-" + endDirection;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 135 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 139 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getStartDivisionId() {
/* 143 */     return this.startDivisionId;
/*     */   }
/*     */   
/*     */   public void setStartDivisionId(String startDivisionId) {
/* 147 */     this.startDivisionId = startDivisionId;
/*     */   }
/*     */   
/*     */   public String getEndDivisionId() {
/* 151 */     return this.endDivisionId;
/*     */   }
/*     */   
/*     */   public void setEndDivisionId(String endDivisionId) {
/* 155 */     this.endDivisionId = endDivisionId;
/*     */   }
/*     */   
/*     */   public String getStartLineId() {
/* 159 */     return this.startLineId;
/*     */   }
/*     */   
/*     */   public void setStartLineId(String startLineId) {
/* 163 */     this.startLineId = startLineId;
/*     */   }
/*     */   
/*     */   public Direction getStartDirection() {
/* 167 */     return this.startDirection;
/*     */   }
/*     */   
/*     */   public void setStartDirection(Direction startDirection) {
/* 171 */     this.startDirection = startDirection;
/*     */   }
/*     */   
/*     */   public Integer getStartMileage() {
/* 175 */     return this.startMileage;
/*     */   }
/*     */   
/*     */   public void setStartMileage(Integer startMileage) {
/* 179 */     this.startMileage = startMileage;
/*     */   }
/*     */   
/*     */   public String getStartDescription() {
/* 183 */     return this.startDescription;
/*     */   }
/*     */   
/*     */   public void setStartDescription(String startDescription) {
/* 187 */     this.startDescription = startDescription;
/*     */   }
/*     */   
/*     */   public String getEndLineId() {
/* 191 */     return this.endLineId;
/*     */   }
/*     */   
/*     */   public void setEndLineId(String endLineId) {
/* 195 */     this.endLineId = endLineId;
/*     */   }
/*     */   
/*     */   public Direction getEndDirection() {
/* 199 */     return this.endDirection;
/*     */   }
/*     */   
/*     */   public void setEndDirection(Direction endDirection) {
/* 203 */     this.endDirection = endDirection;
/*     */   }
/*     */   
/*     */   public Integer getEndMileage() {
/* 207 */     return this.endMileage;
/*     */   }
/*     */   
/*     */   public void setEndMileage(Integer endMileage) {
/* 211 */     this.endMileage = endMileage;
/*     */   }
/*     */   
/*     */   public String getEndDescription() {
/* 215 */     return this.endDescription;
/*     */   }
/*     */   
/*     */   public void setEndDescription(String endDescription) {
/* 219 */     this.endDescription = endDescription;
/*     */   }
/*     */   
/*     */   public String getExitVd() {
/* 223 */     return this.exitVd;
/*     */   }
/*     */   
/*     */   public void setExitVd(String exitVd) {
/* 227 */     this.exitVd = exitVd;
/*     */   }
/*     */   
/*     */   public List<RingRoadVd> getRingRoadVds() {
/* 231 */     return this.RingRoadVds;
/*     */   }
/*     */   
/*     */   public void setRingRoadVds(List<RingRoadVd> ringRoadVds) {
/* 235 */     this.RingRoadVds = ringRoadVds;
/*     */   }
/*     */   
/*     */   public Integer getLength() {
/* 239 */     return this.length;
/*     */   }
/*     */   
/*     */   public void setLength(Integer length) {
/* 243 */     this.length = length;
/*     */   }
/*     */   
/*     */   public Integer getFreeTravelTime() {
/* 247 */     return this.freeTravelTime;
/*     */   }
/*     */   
/*     */   public void setFreeTravelTime(Integer freeTravelTime) {
/* 251 */     this.freeTravelTime = freeTravelTime;
/*     */   }
/*     */   
/*     */   public String getEtagSectionId() {
/* 255 */     return this.etagSectionId;
/*     */   }
/*     */   
/*     */   public void setEtagSectionId(String etagSectionId) {
/* 259 */     this.etagSectionId = etagSectionId;
/*     */   }
/*     */   
/*     */   public Integer getSpeedLimit() {
/* 263 */     return this.speedLimit;
/*     */   }
/*     */   
/*     */   public void setSpeedLimit(Integer speedLimit) {
/* 267 */     this.speedLimit = speedLimit;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 272 */     int prime = 31;
/* 273 */     int result = 1;
/* 274 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 275 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 280 */     if (this == obj) return true; 
/* 281 */     if (obj == null) return false; 
/* 282 */     if (getClass() != obj.getClass()) return false; 
/* 283 */     RingRoadConfig other = (RingRoadConfig)obj;
/* 284 */     if (this.id == null)
/* 285 */     { if (other.id != null) return false;  }
/* 286 */     else if (!this.id.equals(other.id)) { return false; }
/* 287 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 292 */     return "RingRoadConfig [id=" + this.id + ", startDivisionId=" + this.startDivisionId + ", endDivisionId=" + this.endDivisionId + ", startLineId=" + this.startLineId + ", startDirection=" + this.startDirection + ", startMileage=" + this.startMileage + ", startDescription=" + this.startDescription + ", endLineId=" + this.endLineId + ", endDirection=" + this.endDirection + ", endMileage=" + this.endMileage + ", endDescription=" + this.endDescription + ", exitVd=" + this.exitVd + ", RingRoadVd=" + this.RingRoadVds + ", length=" + this.length + ", speedLimit=" + this.speedLimit + ", freeTravelTime=" + this.freeTravelTime + ", etagSectionId=" + this.etagSectionId + "]";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonDgs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dgs\fm\model\RingRoadConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */