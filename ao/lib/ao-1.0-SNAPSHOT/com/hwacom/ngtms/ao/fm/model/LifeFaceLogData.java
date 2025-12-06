/*     */ package com.hwacom.ngtms.ao.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Temporal;
/*     */ import javax.persistence.TemporalType;
/*     */ import org.hibernate.annotations.GenericGenerator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ public class LifeFaceLogData
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 997820674301391322L;
/*     */   @GeneratedValue(generator = "uuid")
/*     */   @GenericGenerator(name = "uuid", strategy = "uuid")
/*     */   @Id
/*     */   @Comment("UUID")
/*     */   private String id;
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   @Comment("資料時間")
/*     */   private Date dataTime;
/*     */   @Comment("臉辨機房位置")
/*     */   private String cameraName;
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   @Comment("臉辨時間")
/*     */   private Date date;
/*     */   @Comment("臉辨結果")
/*     */   private boolean isMatch;
/*     */   @Comment("臉辨卡號")
/*     */   private String cardId;
/*     */   
/*     */   public String getId() {
/*  42 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  46 */     this.id = id;
/*     */   }
/*     */   
/*     */   public Date getDataTime() {
/*  50 */     return this.dataTime;
/*     */   }
/*     */   
/*     */   public void setDataTime(Date dataTime) {
/*  54 */     this.dataTime = dataTime;
/*     */   }
/*     */   
/*     */   public String getCameraName() {
/*  58 */     return this.cameraName;
/*     */   }
/*     */   
/*     */   public void setCameraName(String cameraName) {
/*  62 */     this.cameraName = cameraName;
/*     */   }
/*     */   
/*     */   public Date getDate() {
/*  66 */     return this.date;
/*     */   }
/*     */   
/*     */   public void setDate(Date date) {
/*  70 */     this.date = date;
/*     */   }
/*     */   
/*     */   public boolean isMatch() {
/*  74 */     return this.isMatch;
/*     */   }
/*     */   
/*     */   public void setMatch(boolean isMatch) {
/*  78 */     this.isMatch = isMatch;
/*     */   }
/*     */   
/*     */   public String getCardId() {
/*  82 */     return this.cardId;
/*     */   }
/*     */   
/*     */   public void setCardId(String cardId) {
/*  86 */     this.cardId = cardId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  91 */     int prime = 31;
/*  92 */     int result = 1;
/*  93 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/*  94 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  99 */     if (this == obj) return true; 
/* 100 */     if (obj == null) return false; 
/* 101 */     if (getClass() != obj.getClass()) return false; 
/* 102 */     LifeFaceLogData other = (LifeFaceLogData)obj;
/* 103 */     if (this.id == null)
/* 104 */     { if (other.id != null) return false;  }
/* 105 */     else if (!this.id.equals(other.id)) { return false; }
/* 106 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 111 */     return "LifeFaceLogData [id=" + this.id + ", dataTime=" + this.dataTime + ", cameraName=" + this.cameraName + ", date=" + this.date + ", isMatch=" + this.isMatch + "]";
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\model\LifeFaceLogData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */