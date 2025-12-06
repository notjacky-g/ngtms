/*     */ package com.hwacom.ngtms.c.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*     */ import com.hwacom.ngtms.c.shared.Direction;
/*     */ import com.hwacom.ngtms.c.shared.LocationType;
/*     */ import com.hwacom.ngtms.c.shared.OpStatusHiNibble;
/*     */ import com.hwacom.ngtms.c.shared.OpStatusLowNibble;
/*     */ import com.hwacom.ngtms.c.shared.PriorityType;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.EnumType;
/*     */ import javax.persistence.Enumerated;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.GenerationType;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Temporal;
/*     */ import javax.persistence.TemporalType;
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
/*     */ public class DeviceOpStatusLog
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8286492548609635750L;
/*     */   @Id
/*     */   @Comment("UUID")
/*     */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*     */   private Long id;
/*     */   @Comment("設備名稱")
/*     */   @Column(nullable=false, length=40)
/*     */   private String deviceName;
/*     */   @Comment("路線編號（屬於哪一個國道）")
/*     */   @Column(length=20)
/*     */   private String lineId;
/*     */   @Comment("F=高速公路，H=快速道路，T=隧道，R=匝道，P=保留 (程度表的key)S=服務區，L=地方道路")
/*     */   @Column(length=20)
/*     */   @Enumerated(EnumType.STRING)
/*     */   private LocationType location;
/*     */   @Comment("方向（N=北上，S=南下，E=東向，W=西向）")
/*     */   @Column(length=1)
/*     */   @Enumerated(EnumType.STRING)
/*     */   private Direction direction;
/*     */   @Comment("里程數（公尺）")
/*     */   @Column(length=11)
/*     */   private Integer mileage;
/*     */   @Comment("記錄時間 ")
/*     */   @Column(nullable=false)
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   private Date dataTime;
/*     */   @Comment("同ems通訊狀態, 0:正常 1:異常 ")
/*     */   @Column(nullable=false)
/*     */   private Integer commStatus;
/*     */   @Comment("opStatus bit4~bit7")
/*     */   @Enumerated(EnumType.STRING)
/*     */   private OpStatusHiNibble opStatusHiNibble;
/*     */   @Comment("opStatus bit0~bit3")
/*     */   @Enumerated(EnumType.STRING)
/*     */   private OpStatusLowNibble opStatusLowNibble;
/*     */   @Comment("現場顯示內容 ")
/*     */   @Column(length=500)
/*     */   private String display;
/*     */   @Comment("新增此筆資料的模組")
/*     */   private String createdBy;
/*     */   @Comment("使用者 ID")
/*     */   @Column(name="user_id")
/*     */   private String userId;
/*     */   @Comment("預設下架時間")
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   private Date discontinuedTime;
/*     */   @Comment("顯示優先權的設計  強制手動 > 反應計畫 > 一般手動 > 排程 > 自動演算")
/*     */   @Enumerated(EnumType.STRING)
/*     */   private PriorityType priorityType;
/*     */   @Comment("操作結果")
/*     */   @Enumerated(EnumType.STRING)
/*     */   private OperationResult operationResult;
/*     */   
/*     */   public OperationResult getOperationResult()
/*     */   {
/* 128 */     return this.operationResult;
/*     */   }
/*     */   
/*     */   public void setOperationResult(OperationResult operationResult) {
/* 132 */     this.operationResult = operationResult;
/*     */   }
/*     */   
/*     */   public String getUserId() {
/* 136 */     return this.userId;
/*     */   }
/*     */   
/*     */   public void setUserId(String userId) {
/* 140 */     this.userId = userId;
/*     */   }
/*     */   
/*     */   public Date getDiscontinuedTime() {
/* 144 */     return this.discontinuedTime;
/*     */   }
/*     */   
/*     */   public void setDiscontinuedTime(Date discontinuedTime) {
/* 148 */     this.discontinuedTime = discontinuedTime;
/*     */   }
/*     */   
/*     */   public PriorityType getPriorityType() {
/* 152 */     return this.priorityType;
/*     */   }
/*     */   
/*     */   public void setPriorityType(PriorityType priorityType) {
/* 156 */     this.priorityType = priorityType;
/*     */   }
/*     */   
/*     */   public Long getId() {
/* 160 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Long id) {
/* 164 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getDeviceName() {
/* 168 */     return this.deviceName;
/*     */   }
/*     */   
/*     */   public void setDeviceName(String deviceName) {
/* 172 */     this.deviceName = deviceName;
/*     */   }
/*     */   
/*     */   public String getLineId() {
/* 176 */     return this.lineId;
/*     */   }
/*     */   
/*     */   public void setLineId(String lineId) {
/* 180 */     this.lineId = lineId;
/*     */   }
/*     */   
/*     */   public LocationType getLocation() {
/* 184 */     return this.location;
/*     */   }
/*     */   
/*     */   public void setLocation(LocationType location) {
/* 188 */     this.location = location;
/*     */   }
/*     */   
/*     */   public Direction getDirection() {
/* 192 */     return this.direction;
/*     */   }
/*     */   
/*     */   public void setDirection(Direction direction) {
/* 196 */     this.direction = direction;
/*     */   }
/*     */   
/*     */   public Date getDataTime() {
/* 200 */     return this.dataTime;
/*     */   }
/*     */   
/*     */   public void setDataTime(Date dataTime) {
/* 204 */     this.dataTime = dataTime;
/*     */   }
/*     */   
/*     */   public Integer getCommStatus() {
/* 208 */     return this.commStatus;
/*     */   }
/*     */   
/*     */   public void setCommStatus(Integer commStatus) {
/* 212 */     this.commStatus = commStatus;
/*     */   }
/*     */   
/*     */   public OpStatusHiNibble getOpStatusHiNibble() {
/* 216 */     return this.opStatusHiNibble;
/*     */   }
/*     */   
/*     */   public void setOpStatusHiNibble(OpStatusHiNibble opStatusHiNibble) {
/* 220 */     this.opStatusHiNibble = opStatusHiNibble;
/*     */   }
/*     */   
/*     */   public OpStatusLowNibble getOpStatusLowNibble() {
/* 224 */     return this.opStatusLowNibble;
/*     */   }
/*     */   
/*     */   public void setOpStatusLowNibble(OpStatusLowNibble opStatusLowNibble) {
/* 228 */     this.opStatusLowNibble = opStatusLowNibble;
/*     */   }
/*     */   
/*     */   public String getDisplay() {
/* 232 */     return this.display;
/*     */   }
/*     */   
/*     */   public void setDisplay(String display) {
/* 236 */     this.display = display;
/*     */   }
/*     */   
/*     */   public Integer getMileage() {
/* 240 */     return this.mileage;
/*     */   }
/*     */   
/*     */   public void setMileage(Integer mileage) {
/* 244 */     this.mileage = mileage;
/*     */   }
/*     */   
/*     */   public String getCreatedBy() {
/* 248 */     return this.createdBy;
/*     */   }
/*     */   
/*     */   public void setCreatedBy(String createdBy) {
/* 252 */     this.createdBy = createdBy;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\DeviceOpStatusLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */