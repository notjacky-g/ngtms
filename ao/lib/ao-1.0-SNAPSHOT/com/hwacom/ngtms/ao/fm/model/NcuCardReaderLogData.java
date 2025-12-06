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
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ public class NcuCardReaderLogData
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4233018896965143621L;
/*     */   @GeneratedValue(generator = "uuid")
/*     */   @GenericGenerator(name = "uuid", strategy = "uuid")
/*     */   @Id
/*     */   @Comment("UUID")
/*     */   private String id;
/*     */   @Comment("主機編號")
/*     */   private String ncuId;
/*     */   @Comment("事件碼")
/*     */   private String eventCode;
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   @Comment("資料時間")
/*     */   private Date dataTime;
/*     */   @Comment("卡片編號")
/*     */   private String cardNumber;
/*     */   @Comment("卡機識別號")
/*     */   private String deviceId;
/*     */   @Comment("狀態碼")
/*     */   private String statusCode;
/*     */   
/*     */   public String getId() {
/*  45 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  49 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getNcuId() {
/*  53 */     return this.ncuId;
/*     */   }
/*     */   
/*     */   public void setNcuId(String ncuId) {
/*  57 */     this.ncuId = ncuId;
/*     */   }
/*     */   
/*     */   public String getEventCode() {
/*  61 */     return this.eventCode;
/*     */   }
/*     */   
/*     */   public void setEventCode(String eventCode) {
/*  65 */     this.eventCode = eventCode;
/*     */   }
/*     */   
/*     */   public Date getDataTime() {
/*  69 */     return this.dataTime;
/*     */   }
/*     */   
/*     */   public void setDataTime(Date dataTime) {
/*  73 */     this.dataTime = dataTime;
/*     */   }
/*     */   
/*     */   public String getCardNumber() {
/*  77 */     return this.cardNumber;
/*     */   }
/*     */   
/*     */   public void setCardNumber(String cardNumber) {
/*  81 */     this.cardNumber = cardNumber;
/*     */   }
/*     */   
/*     */   public String getDeviceId() {
/*  85 */     return this.deviceId;
/*     */   }
/*     */   
/*     */   public void setDeviceId(String deviceId) {
/*  89 */     this.deviceId = deviceId;
/*     */   }
/*     */   
/*     */   public String getStatusCode() {
/*  93 */     return this.statusCode;
/*     */   }
/*     */   
/*     */   public void setStatusCode(String statusCode) {
/*  97 */     this.statusCode = statusCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 102 */     int prime = 31;
/* 103 */     int result = 1;
/* 104 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 105 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 110 */     if (this == obj) return true; 
/* 111 */     if (obj == null) return false; 
/* 112 */     if (getClass() != obj.getClass()) return false; 
/* 113 */     NcuCardReaderLogData other = (NcuCardReaderLogData)obj;
/* 114 */     if (this.id == null)
/* 115 */     { if (other.id != null) return false;  }
/* 116 */     else if (!this.id.equals(other.id)) { return false; }
/* 117 */      return true;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\model\NcuCardReaderLogData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */