/*     */ package com.hwacom.ngtms.c.dis.fm.model;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.base.MoreObjects.ToStringHelper;
/*     */ import com.google.common.base.Objects;
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.c.shared.DisplayMatch;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.EnumType;
/*     */ import javax.persistence.Enumerated;
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
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ @Table(indexes={@javax.persistence.Index(columnList="device_name,data_time")})
/*     */ public class DisMessageCompareLog
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1242301089680689304L;
/*     */   @Id
/*     */   @Comment("UUID")
/*     */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*     */   private Long id;
/*     */   @Comment("設備編號")
/*     */   @Column(nullable=false, length=40)
/*     */   private String deviceName;
/*     */   @Comment("設備種類")
/*     */   @Column(nullable=false, length=20)
/*     */   private String deviceType;
/*     */   @Comment("記錄時間 ")
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   private Date dataTime;
/*     */   @Comment("中心最後下載指令內容")
/*     */   @Column(nullable=false, length=100)
/*     */   private String commandMessage;
/*     */   @Comment("現場顯示內容")
/*     */   @Column(nullable=false, length=100)
/*     */   private String deviceDisplay;
/*     */   @Comment("現場顯示內容比對是否符合")
/*     */   @Enumerated(EnumType.STRING)
/*     */   private DisplayMatch isDisplayContentMatch;
/*     */   
/*     */   public int hashCode()
/*     */   {
/*  77 */     return Objects.hashCode(new Object[] { Integer.valueOf(super.hashCode()), this.id });
/*     */   }
/*     */   
/*     */   public boolean equals(Object object)
/*     */   {
/*  82 */     if ((object instanceof DisMessageCompareLog)) {
/*  83 */       if (!super.equals(object)) return false;
/*  84 */       DisMessageCompareLog that = (DisMessageCompareLog)object;
/*  85 */       return Objects.equal(this.id, that.id);
/*     */     }
/*  87 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/*  98 */     return MoreObjects.toStringHelper(this).add("id", this.id).add("deviceName", this.deviceName).add("dataTime", this.dataTime).add("commandMessage", this.commandMessage).add("deviceDisplay", this.deviceDisplay).toString();
/*     */   }
/*     */   
/*     */   public Long getId() {
/* 102 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Long id) {
/* 106 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getDeviceName() {
/* 110 */     return this.deviceName;
/*     */   }
/*     */   
/*     */   public void setDeviceName(String deviceName) {
/* 114 */     this.deviceName = deviceName;
/*     */   }
/*     */   
/*     */   public String getCommandMessage() {
/* 118 */     return this.commandMessage;
/*     */   }
/*     */   
/*     */   public void setCommandMessage(String commandMessage) {
/* 122 */     this.commandMessage = commandMessage;
/*     */   }
/*     */   
/*     */   public String getDeviceDisplay() {
/* 126 */     return this.deviceDisplay;
/*     */   }
/*     */   
/*     */   public void setDeviceDisplay(String deviceDisplay) {
/* 130 */     this.deviceDisplay = deviceDisplay;
/* 131 */     if (this.deviceDisplay != null) {
/* 132 */       this.deviceDisplay.replace('\r', ' ').replace('\n', ' ');
/* 133 */       this.deviceDisplay.replaceAll("\r\n", "");
/*     */     }
/*     */   }
/*     */   
/*     */   public Date getDataTime() {
/* 138 */     return this.dataTime;
/*     */   }
/*     */   
/*     */   public void setDataTime(Date dataTime) {
/* 142 */     this.dataTime = dataTime;
/*     */   }
/*     */   
/*     */   public String getDeviceType() {
/* 146 */     return this.deviceType;
/*     */   }
/*     */   
/*     */   public void setDeviceType(String deviceType) {
/* 150 */     this.deviceType = deviceType;
/*     */   }
/*     */   
/*     */   public DisplayMatch getIsDisplayContentMatch() {
/* 154 */     return this.isDisplayContentMatch;
/*     */   }
/*     */   
/*     */   public void setIsDisplayContentMatch(DisplayMatch isDisplayContentMatch) {
/* 158 */     this.isDisplayContentMatch = isDisplayContentMatch;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\model\DisMessageCompareLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */