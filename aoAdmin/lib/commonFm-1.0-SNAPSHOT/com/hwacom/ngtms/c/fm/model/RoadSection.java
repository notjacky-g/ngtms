/*     */ package com.hwacom.ngtms.c.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.c.shared.AreaType;
/*     */ import com.hwacom.ngtms.c.shared.Direction;
/*     */ import com.hwacom.ngtms.c.shared.EventExeMode;
/*     */ import com.hwacom.ngtms.c.shared.EventLogMode;
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
/*     */ public class RoadSection
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4347380096072943208L;
/*     */   @Id
/*     */   @Comment("路段編號")
/*     */   @Column(length = 20)
/*     */   private String sectionId;
/*     */   @Comment("路段名稱")
/*     */   @Column(length = 40)
/*     */   private String sectionName;
/*     */   @Comment("路線編號（屬於哪一個路線）")
/*     */   @Column(nullable = false, length = 20)
/*     */   private String lineId;
/*     */   @Comment("方向（N=北上，S=南下，E=東向，W=西向）")
/*     */   @Column(length = 1)
/*     */   @Enumerated(EnumType.STRING)
/*     */   private Direction direction;
/*     */   @Comment("開始的分割點編號（系統交流道、交流道、收費站、服務區）")
/*     */   @Column(nullable = false, length = 20)
/*     */   private String startDivisionId;
/*     */   @Comment("")
/*     */   @Column(length = 20)
/*     */   private String endDivisionId;
/*     */   @Comment("")
/*     */   @Column(nullable = false)
/*     */   private short laneCount;
/*     */   @Comment("")
/*     */   private Integer maxSpeed;
/*     */   @Comment(" 最底限速")
/*     */   private Integer minSpeed;
/*     */   @Comment("轄區 (N=北區、C=中區、S=南區)")
/*     */   @Column(length = 1)
/*     */   @Enumerated(EnumType.STRING)
/*     */   private AreaType areaType;
/*     */   @Comment("主標竿VD")
/*     */   @Column(length = 20)
/*     */   private String standardVd;
/*     */   @Comment("次標竿VD1")
/*     */   @Column(length = 20)
/*     */   private String subStandardVd1;
/*     */   @Comment("次標竿VD2")
/*     */   @Column(length = 20)
/*     */   private String subStandardVd2;
/*     */   @Comment("允許手動建立事件")
/*     */   private Boolean manualEnabled;
/*     */   @Comment("反應計畫事件登錄模式 ")
/*     */   @Enumerated(EnumType.STRING)
/*     */   private EventLogMode eventLogMode;
/*     */   @Comment("反應計畫事件執行模式")
/*     */   @Enumerated(EnumType.STRING)
/*     */   private EventExeMode eventExecMode;
/*     */   @Comment("車道容量(預設:2000,單位:PCU)")
/*     */   private Integer sectionFlow;
/*     */   @Comment("自由車速")
/*     */   private Integer freeSpeed;
/*     */   @Comment("DDS壅塞路段顯示設定")
/*     */   private Boolean ddsRoadSectionEnabled;
/*     */   @Comment("DDS壅塞路段顯示設定群組一")
/*     */   private Boolean ddsRoadSectionGroup1;
/*     */   @Comment("DDS壅塞路段顯示設定群組二 ")
/*     */   private Boolean ddsRoadSectionGroup2;
/*     */   @Comment("DDS壅塞路段顯示設定群組三")
/*     */   private Boolean ddsRoadSectionGroup3;
/*     */   @Comment("DDS壅塞路段顯示設定群組四")
/*     */   private Boolean ddsRoadSectionGroup4;
/*     */   
/*     */   public RoadSection() {}
/*     */   
/*     */   public RoadSection(String sectionId, String lineid, String startDivisionId, short laneCount) {
/* 138 */     this.sectionId = sectionId;
/* 139 */     this.lineId = lineid;
/* 140 */     this.startDivisionId = startDivisionId;
/* 141 */     this.laneCount = laneCount;
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
/*     */   public RoadSection(String sectionId, String sectionName, String lineid, Direction direction, String startDivisionId, String endDivisionId, short laneCount, Integer maxSpeed, Integer minSpeed) {
/* 154 */     this.sectionId = sectionId;
/* 155 */     this.sectionName = sectionName;
/* 156 */     this.lineId = lineid;
/* 157 */     this.direction = direction;
/* 158 */     this.startDivisionId = startDivisionId;
/* 159 */     this.endDivisionId = endDivisionId;
/* 160 */     this.laneCount = laneCount;
/* 161 */     this.maxSpeed = maxSpeed;
/* 162 */     this.minSpeed = minSpeed;
/*     */   }
/*     */   
/*     */   public String getSectionId() {
/* 166 */     return this.sectionId;
/*     */   }
/*     */   
/*     */   public void setSectionId(String sectionId) {
/* 170 */     this.sectionId = sectionId;
/*     */   }
/*     */   
/*     */   public String getSectionName() {
/* 174 */     return this.sectionName;
/*     */   }
/*     */   
/*     */   public void setSectionName(String sectionName) {
/* 178 */     this.sectionName = sectionName;
/*     */   }
/*     */   
/*     */   public String getLineid() {
/* 182 */     return this.lineId;
/*     */   }
/*     */   
/*     */   public void setLineid(String lineid) {
/* 186 */     this.lineId = lineid;
/*     */   }
/*     */   
/*     */   public Direction getDirection() {
/* 190 */     return this.direction;
/*     */   }
/*     */   
/*     */   public void setDirection(Direction direction) {
/* 194 */     this.direction = direction;
/*     */   }
/*     */   
/*     */   public String getStartDivisionId() {
/* 198 */     return this.startDivisionId;
/*     */   }
/*     */   
/*     */   public void setStartDivisionId(String startDivisionId) {
/* 202 */     this.startDivisionId = startDivisionId;
/*     */   }
/*     */   
/*     */   public String getEndDivisionId() {
/* 206 */     return this.endDivisionId;
/*     */   }
/*     */   
/*     */   public void setEndDivisionId(String endDivisionId) {
/* 210 */     this.endDivisionId = endDivisionId;
/*     */   }
/*     */   
/*     */   public short getLaneCount() {
/* 214 */     return this.laneCount;
/*     */   }
/*     */   
/*     */   public void setLaneCount(short laneCount) {
/* 218 */     this.laneCount = laneCount;
/*     */   }
/*     */   
/*     */   public Integer getMaxSpeed() {
/* 222 */     return this.maxSpeed;
/*     */   }
/*     */   
/*     */   public void setMaxSpeed(Integer maxSpeed) {
/* 226 */     this.maxSpeed = maxSpeed;
/*     */   }
/*     */   
/*     */   public Integer getMinSpeed() {
/* 230 */     return this.minSpeed;
/*     */   }
/*     */   
/*     */   public void setMinSpeed(Integer minSpeed) {
/* 234 */     this.minSpeed = minSpeed;
/*     */   }
/*     */   
/*     */   public AreaType getAreaType() {
/* 238 */     return this.areaType;
/*     */   }
/*     */   
/*     */   public void setAreaType(AreaType areaType) {
/* 242 */     this.areaType = areaType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStandardVd() {
/* 247 */     return this.standardVd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStandardVd(String standardVd) {
/* 252 */     this.standardVd = standardVd;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSubStandardVd1() {
/* 257 */     return this.subStandardVd1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSubStandardVd1(String subStandardVd1) {
/* 262 */     this.subStandardVd1 = subStandardVd1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSubStandardVd2() {
/* 267 */     return this.subStandardVd2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSubStandardVd2(String subStandardVd2) {
/* 272 */     this.subStandardVd2 = subStandardVd2;
/*     */   }
/*     */   
/*     */   public String getLineId() {
/* 276 */     return this.lineId;
/*     */   }
/*     */   
/*     */   public void setLineId(String lineId) {
/* 280 */     this.lineId = lineId;
/*     */   }
/*     */   
/*     */   public Boolean getManualEnabled() {
/* 284 */     return this.manualEnabled;
/*     */   }
/*     */   
/*     */   public void setManualEnabled(Boolean manualEnabled) {
/* 288 */     this.manualEnabled = manualEnabled;
/*     */   }
/*     */   
/*     */   public EventLogMode getEventLogMode() {
/* 292 */     return this.eventLogMode;
/*     */   }
/*     */   
/*     */   public void setEventLogMode(EventLogMode eventLogMode) {
/* 296 */     this.eventLogMode = eventLogMode;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 301 */     int prime = 31;
/* 302 */     int result = 1;
/* 303 */     result = 31 * result + ((this.sectionId == null) ? 0 : this.sectionId.hashCode());
/* 304 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 309 */     if (this == obj) return true; 
/* 310 */     if (obj == null) return false; 
/* 311 */     if (getClass() != obj.getClass()) return false; 
/* 312 */     RoadSection other = (RoadSection)obj;
/* 313 */     if (this.sectionId == null)
/* 314 */     { if (other.sectionId != null) return false;  }
/* 315 */     else if (!this.sectionId.equals(other.sectionId)) { return false; }
/* 316 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 321 */     return "RoadSection [sectionId=" + this.sectionId + ", sectionName=" + this.sectionName + ", lineId=" + this.lineId + ", direction=" + this.direction + ", startDivisionId=" + this.startDivisionId + ", endDivisionId=" + this.endDivisionId + ", laneCount=" + this.laneCount + ", maxSpeed=" + this.maxSpeed + ", minSpeed=" + this.minSpeed + ", areaType=" + this.areaType + ", standardVd=" + this.standardVd + ", subStandardVd1=" + this.subStandardVd1 + ", subStandardVd2=" + this.subStandardVd2 + ", manualEnabled=" + this.manualEnabled + ", eventLogMode=" + this.eventLogMode + "]";
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
/*     */   public EventExeMode getEventExecMode() {
/* 355 */     return this.eventExecMode;
/*     */   }
/*     */   
/*     */   public void setEventExecMode(EventExeMode eventExecMode) {
/* 359 */     this.eventExecMode = eventExecMode;
/*     */   }
/*     */   
/*     */   public Integer getSectionFlow() {
/* 363 */     return this.sectionFlow;
/*     */   }
/*     */   
/*     */   public void setSectionFlow(Integer sectionFlow) {
/* 367 */     this.sectionFlow = sectionFlow;
/*     */   }
/*     */   
/*     */   public Integer getFreeSpeed() {
/* 371 */     return this.freeSpeed;
/*     */   }
/*     */   
/*     */   public void setFreeSpeed(Integer freeSpeed) {
/* 375 */     this.freeSpeed = freeSpeed;
/*     */   }
/*     */   
/*     */   public Boolean getDdsRoadSectionEnabled() {
/* 379 */     return this.ddsRoadSectionEnabled;
/*     */   }
/*     */   
/*     */   public void setDdsRoadSectionEnabled(Boolean ddsRoadSectionEnabled) {
/* 383 */     this.ddsRoadSectionEnabled = ddsRoadSectionEnabled;
/*     */   }
/*     */   
/*     */   public Boolean getDdsRoadSectionGroup1() {
/* 387 */     return this.ddsRoadSectionGroup1;
/*     */   }
/*     */   
/*     */   public void setDdsRoadSectionGroup1(Boolean ddsRoadSectionGroup1) {
/* 391 */     this.ddsRoadSectionGroup1 = ddsRoadSectionGroup1;
/*     */   }
/*     */   
/*     */   public Boolean getDdsRoadSectionGroup2() {
/* 395 */     return this.ddsRoadSectionGroup2;
/*     */   }
/*     */   
/*     */   public void setDdsRoadSectionGroup2(Boolean ddsRoadSectionGroup2) {
/* 399 */     this.ddsRoadSectionGroup2 = ddsRoadSectionGroup2;
/*     */   }
/*     */   
/*     */   public Boolean getDdsRoadSectionGroup3() {
/* 403 */     return this.ddsRoadSectionGroup3;
/*     */   }
/*     */   
/*     */   public void setDdsRoadSectionGroup3(Boolean ddsRoadSectionGroup3) {
/* 407 */     this.ddsRoadSectionGroup3 = ddsRoadSectionGroup3;
/*     */   }
/*     */   
/*     */   public Boolean getDdsRoadSectionGroup4() {
/* 411 */     return this.ddsRoadSectionGroup4;
/*     */   }
/*     */   
/*     */   public void setDdsRoadSectionGroup4(Boolean ddsRoadSectionGroup4) {
/* 415 */     this.ddsRoadSectionGroup4 = ddsRoadSectionGroup4;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\RoadSection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */