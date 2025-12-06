/*     */ package com.hwacom.ngtms.c.dis.fm.model;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.base.MoreObjects.ToStringHelper;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.collect.ComparisonChain;
/*     */ import com.google.common.collect.Ordering;
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.base.util.KeyUtils;
/*     */ import com.hwacom.ngtms.c.shared.PriorityType;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.EnumType;
/*     */ import javax.persistence.Enumerated;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Lob;
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
/*     */ @Entity
/*     */ public class DisQueueItem
/*     */   implements Comparable<DisQueueItem>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4098890036975104241L;
/*     */   @Id
/*     */   @Comment("id")
/*     */   private String id;
/*     */   @Comment("設備名稱")
/*     */   @Column(nullable=false)
/*     */   private String deviceName;
/*     */   @Comment("顯示優先權的設計  強制手動 > 反應計畫 > 一般手動 > 排程 > 自動演算")
/*     */   @Column(nullable=false)
/*     */   @Enumerated(EnumType.STRING)
/*     */   private PriorityType priorityType;
/*     */   @Comment("反應計畫的優先順序 ")
/*     */   private Integer rspPriority;
/*     */   @Comment(" 反應計畫用,若rspPriority相同,再比較此欄位 ")
/*     */   private Integer subPriority;
/*     */   @Comment("資料時間(輸入時間)")
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   private Date dataTime;
/*     */   @Comment("一般手動模式的結束時間 ")
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   private Date endTime;
/*     */   @Comment("反應計畫的事件 originalEventId")
/*     */   private String incidentId;
/*     */   @Comment("移動性施工編號 ")
/*     */   private String constructionNo;
/*     */   @Comment("事件次類別 ,用來提示各fm是否需要處理資顯設備候隊表動態內容")
/*     */   private Integer alarmSubTypeId;
/*     */   @Comment("反應計畫的事件最新的 Id")
/*     */   private String eventSessionId;
/*     */   @Comment("新增熄滅到候隊表排序,忽略該筆候隊表文字顯示內容 直接視為熄滅")
/*  92 */   private Boolean turnOff = Boolean.FALSE;
/*     */   
/*     */   @Comment("屬於哪一種設備類型")
/*     */   @Column(length=20)
/*     */   private String deviceType;
/*     */   
/*     */   @Comment("記錄各類型設備不屬於共用的屬性")
/*     */   @Lob
/*     */   private byte[] contextData;
/*     */   
/*     */ 
/*     */   public DisQueueItem() {}
/*     */   
/*     */ 
/*     */   public DisQueueItem(String deviceName, PriorityType priorityType)
/*     */   {
/* 108 */     this.deviceName = deviceName.trim();
/* 109 */     this.priorityType = priorityType;
/* 110 */     this.dataTime = new Date();
/* 111 */     setId(KeyUtils.getKey(new Object[] { deviceName.trim(), priorityType }));
/*     */   }
/*     */   
/*     */   public DisQueueItem(String deviceName, PriorityType priorityType, String incidentId) {
/* 115 */     this.deviceName = deviceName.trim();
/* 116 */     this.priorityType = priorityType;
/* 117 */     this.incidentId = incidentId;
/* 118 */     this.dataTime = new Date();
/* 119 */     setId(KeyUtils.getKey(new Object[] { deviceName.trim(), priorityType, incidentId }));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int compareTo(DisQueueItem that)
/*     */   {
/* 127 */     return ComparisonChain.start().compare(getPriorityType().getPriority(), that.getPriorityType().getPriority()).compare(getRspPriority(), that.getRspPriority(), Ordering.natural().nullsLast()).compare(getSubPriority(), that.getSubPriority(), Ordering.natural().nullsLast()).result();
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
/*     */   public String toString()
/*     */   {
/* 140 */     return MoreObjects.toStringHelper(this).add("id", this.id).add("deviceName", this.deviceName).add("priorityType", this.priorityType).add("rspPriority", this.rspPriority).add("dataTime", this.dataTime).add("endTime", this.endTime).add("incidentId", this.incidentId).add("subPriority", this.subPriority).toString();
/*     */   }
/*     */   
/*     */   public PriorityType getPriorityType() {
/* 144 */     return this.priorityType;
/*     */   }
/*     */   
/*     */   public void setPriorityType(PriorityType priorityType) {
/* 148 */     this.priorityType = priorityType;
/*     */   }
/*     */   
/*     */   public Integer getRspPriority() {
/* 152 */     return this.rspPriority;
/*     */   }
/*     */   
/*     */   public void setRspPriority(Integer rspPriority) {
/* 156 */     this.rspPriority = rspPriority;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 160 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 164 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getDeviceName() {
/* 168 */     return this.deviceName;
/*     */   }
/*     */   
/*     */   public void setDeviceName(String deviceName) {
/* 172 */     this.deviceName = deviceName.trim();
/*     */   }
/*     */   
/*     */   public String getIncidentId() {
/* 176 */     return this.incidentId;
/*     */   }
/*     */   
/*     */   public void setIncidentId(String incidentId) {
/* 180 */     this.incidentId = incidentId;
/*     */   }
/*     */   
/*     */   public Date getDataTime() {
/* 184 */     return this.dataTime;
/*     */   }
/*     */   
/*     */   public void setDataTime(Date dataTime) {
/* 188 */     this.dataTime = dataTime;
/*     */   }
/*     */   
/*     */   public Date getEndTime() {
/* 192 */     return this.endTime;
/*     */   }
/*     */   
/*     */   public void setEndTime(Date endTime) {
/* 196 */     this.endTime = endTime;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 201 */     return Objects.hashCode(new Object[] { this.id });
/*     */   }
/*     */   
/*     */   public boolean equals(Object object)
/*     */   {
/* 206 */     if ((object instanceof DisQueueItem)) {
/* 207 */       if (!super.equals(object)) return false;
/* 208 */       DisQueueItem that = (DisQueueItem)object;
/* 209 */       return Objects.equal(this.id, that.id);
/*     */     }
/* 211 */     return false;
/*     */   }
/*     */   
/*     */   public String getConstructionNo() {
/* 215 */     return this.constructionNo;
/*     */   }
/*     */   
/*     */   public void setConstructionNo(String constructionNo) {
/* 219 */     this.constructionNo = constructionNo;
/*     */   }
/*     */   
/*     */   public Integer getSubPriority() {
/* 223 */     return this.subPriority;
/*     */   }
/*     */   
/*     */   public void setSubPriority(Integer subPriority) {
/* 227 */     this.subPriority = subPriority;
/*     */   }
/*     */   
/*     */   public Integer getAlarmSubTypeId() {
/* 231 */     return this.alarmSubTypeId;
/*     */   }
/*     */   
/*     */   public void setAlarmSubTypeId(Integer alarmSubTypeId) {
/* 235 */     this.alarmSubTypeId = alarmSubTypeId;
/*     */   }
/*     */   
/*     */   public String getEventSessionId() {
/* 239 */     return this.eventSessionId;
/*     */   }
/*     */   
/*     */   public void setEventSessionId(String eventSessionId) {
/* 243 */     this.eventSessionId = eventSessionId;
/*     */   }
/*     */   
/*     */   public Boolean getTurnOff() {
/* 247 */     if (this.turnOff != null) return this.turnOff;
/* 248 */     return Boolean.FALSE;
/*     */   }
/*     */   
/*     */   public void setTurnOff(Boolean turnOff) {
/* 252 */     this.turnOff = turnOff;
/*     */   }
/*     */   
/*     */   public String getDeviceType() {
/* 256 */     return this.deviceType;
/*     */   }
/*     */   
/*     */   public void setDeviceType(String deviceType) {
/* 260 */     this.deviceType = deviceType;
/*     */   }
/*     */   
/*     */   public byte[] getContextData() {
/* 264 */     return this.contextData;
/*     */   }
/*     */   
/*     */   public void setContextData(byte[] contextData) {
/* 268 */     this.contextData = contextData;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\model\DisQueueItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */