/*     */ package com.hwacom.ngtms.c.dis.fm.model;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.base.MoreObjects.ToStringHelper;
/*     */ import com.google.common.base.Objects;
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.GenerationType;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
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
/*     */ @Entity
/*     */ @Table(indexes={@javax.persistence.Index(columnList="device_type")})
/*     */ public class DisDisplayChangeLog
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5570312235182608180L;
/*     */   @Id
/*     */   @Comment("UUID")
/*     */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*     */   private Long id;
/*     */   @Comment("設備編號")
/*     */   @Column(nullable=false, length=40)
/*     */   private String deviceName;
/*     */   @Comment("設備種類")
/*     */   @Column(nullable=false, length=10)
/*     */   private String deviceType;
/*     */   @Comment("記錄 時間")
/*     */   @Column(nullable=false)
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   private Date dataTime;
/*     */   @Comment("r22 display_part SNALLINT 1為上層顯示面版、2為下層顯示面板")
/*     */   private Integer boardId;
/*     */   @Comment("現場顯示內容")
/*     */   @Column(nullable=false, length=100)
/*     */   private String deviceMessage;
/*     */   
/*     */   public int hashCode()
/*     */   {
/*  69 */     return Objects.hashCode(new Object[] { this.id });
/*     */   }
/*     */   
/*     */   public boolean equals(Object object)
/*     */   {
/*  74 */     if ((object instanceof DisDisplayChangeLog)) {
/*  75 */       if (!super.equals(object)) return false;
/*  76 */       DisDisplayChangeLog that = (DisDisplayChangeLog)object;
/*  77 */       return Objects.equal(this.id, that.id);
/*     */     }
/*  79 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/*  89 */     return MoreObjects.toStringHelper(this).add("id", this.id).add("deviceName", this.deviceName).add("boardId", this.boardId).add("message", this.deviceMessage).toString();
/*     */   }
/*     */   
/*     */   public Long getId() {
/*  93 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Long id) {
/*  97 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getDeviceName() {
/* 101 */     return this.deviceName;
/*     */   }
/*     */   
/*     */   public void setDeviceName(String deviceName) {
/* 105 */     this.deviceName = deviceName;
/*     */   }
/*     */   
/*     */   public Integer getBoardId() {
/* 109 */     return this.boardId;
/*     */   }
/*     */   
/*     */   public void setBoardId(Integer boardId) {
/* 113 */     this.boardId = boardId;
/*     */   }
/*     */   
/*     */   public String getDeviceType() {
/* 117 */     return this.deviceType;
/*     */   }
/*     */   
/*     */   public void setDeviceType(String deviceType) {
/* 121 */     this.deviceType = deviceType;
/*     */   }
/*     */   
/*     */   public Date getDataTime() {
/* 125 */     return this.dataTime;
/*     */   }
/*     */   
/*     */   public void setDataTime(Date dataTime) {
/* 129 */     this.dataTime = dataTime;
/*     */   }
/*     */   
/*     */   public String getDeviceMessage() {
/* 133 */     return this.deviceMessage;
/*     */   }
/*     */   
/*     */   public void setDeviceMessage(String deviceMessage) {
/* 137 */     this.deviceMessage = deviceMessage;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\model\DisDisplayChangeLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */