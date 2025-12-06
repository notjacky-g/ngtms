/*     */ package com.hwacom.ngtms.room.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.room.shared.EventCode;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ public class RoomCardReaderLog
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7928789526525484956L;
/*     */   @Id
/*     */   @Column(length = 40)
/*     */   @Comment("deviceName-eventCode")
/*     */   private String id;
/*     */   private String deviceName;
/*     */   private Boolean dataValid;
/*     */   private Date time;
/*     */   private String cardNumber;
/*     */   private EventCode eventCode;
/*     */   
/*     */   public String getId() {
/*  32 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  36 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getDeviceName() {
/*  40 */     return this.deviceName;
/*     */   }
/*     */   
/*     */   public void setDeviceName(String deviceName) {
/*  44 */     this.deviceName = deviceName;
/*     */   }
/*     */   
/*     */   public Boolean getDataValid() {
/*  48 */     return this.dataValid;
/*     */   }
/*     */   
/*     */   public void setDataValid(Boolean dataValid) {
/*  52 */     this.dataValid = dataValid;
/*     */   }
/*     */   
/*     */   public Date getTime() {
/*  56 */     return this.time;
/*     */   }
/*     */   
/*     */   public void setTime(Date time) {
/*  60 */     this.time = time;
/*     */   }
/*     */   
/*     */   public String getCardNumber() {
/*  64 */     return this.cardNumber;
/*     */   }
/*     */   
/*     */   public void setCardNumber(String cardNumber) {
/*  68 */     this.cardNumber = cardNumber;
/*     */   }
/*     */   
/*     */   public EventCode getEventCode() {
/*  72 */     return this.eventCode;
/*     */   }
/*     */   
/*     */   public void setEventCode(EventCode eventCode) {
/*  76 */     this.eventCode = eventCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  81 */     int prime = 31;
/*  82 */     int result = 1;
/*  83 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/*  84 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  89 */     if (this == obj) return true; 
/*  90 */     if (obj == null) return false; 
/*  91 */     if (getClass() != obj.getClass()) return false; 
/*  92 */     RoomCardReaderLog other = (RoomCardReaderLog)obj;
/*  93 */     if (this.id == null)
/*  94 */     { if (other.id != null) return false;  }
/*  95 */     else if (!this.id.equals(other.id)) { return false; }
/*  96 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 101 */     return "RoomCardReaderLog [id=" + this.id + ", deviceName=" + this.deviceName + ", dataValid=" + this.dataValid + ", time=" + this.time + ", cardNumber=" + this.cardNumber + ", eventCode=" + this.eventCode + "]";
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\fm\model\RoomCardReaderLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */