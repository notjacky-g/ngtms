/*     */ package com.hwacom.ngtms.room.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.room.shared.EventCode;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.GenerationType;
/*     */ import javax.persistence.Id;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ public class RoomCardReaderLogData
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   @Id
/*     */   @Comment("ID")
/*     */   @GeneratedValue(strategy = GenerationType.IDENTITY)
/*     */   private Long id;
/*     */   @Comment("設備編號(卡機編號)")
/*     */   private String deviceName;
/*     */   @Comment("資料是否正確(IP驗證)")
/*     */   private Boolean dataValid;
/*     */   @Comment("時間")
/*     */   private Date time;
/*     */   @Comment("卡號")
/*     */   private String cardNumber;
/*     */   @Comment("動作代碼")
/*     */   private EventCode eventCode;
/*     */   
/*     */   public Long getId() {
/*  38 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Long id) {
/*  42 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getDeviceName() {
/*  46 */     return this.deviceName;
/*     */   }
/*     */   
/*     */   public void setDeviceName(String deviceName) {
/*  50 */     this.deviceName = deviceName;
/*     */   }
/*     */   
/*     */   public Boolean getDataValid() {
/*  54 */     return this.dataValid;
/*     */   }
/*     */   
/*     */   public void setDataValid(Boolean dataValid) {
/*  58 */     this.dataValid = dataValid;
/*     */   }
/*     */   
/*     */   public Date getTime() {
/*  62 */     return this.time;
/*     */   }
/*     */   
/*     */   public void setTime(Date time) {
/*  66 */     this.time = time;
/*     */   }
/*     */   
/*     */   public String getCardNumber() {
/*  70 */     return this.cardNumber;
/*     */   }
/*     */   
/*     */   public void setCardNumber(String cardNumber) {
/*  74 */     this.cardNumber = cardNumber;
/*     */   }
/*     */   
/*     */   public EventCode getEventCode() {
/*  78 */     return this.eventCode;
/*     */   }
/*     */   
/*     */   public void setEventCode(EventCode eventCode) {
/*  82 */     this.eventCode = eventCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  87 */     int prime = 31;
/*  88 */     int result = 1;
/*  89 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/*  90 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  95 */     if (this == obj) return true; 
/*  96 */     if (obj == null) return false; 
/*  97 */     if (getClass() != obj.getClass()) return false; 
/*  98 */     RoomCardReaderLogData other = (RoomCardReaderLogData)obj;
/*  99 */     if (this.id == null)
/* 100 */     { if (other.id != null) return false;  }
/* 101 */     else if (!this.id.equals(other.id)) { return false; }
/* 102 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 107 */     return "RoomCardReaderLogData [id=" + this.id + ", deviceName=" + this.deviceName + ", dataValid=" + this.dataValid + ", time=" + this.time + ", cardNumber=" + this.cardNumber + ", eventCode=" + this.eventCode + "]";
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\fm\model\RoomCardReaderLogData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */