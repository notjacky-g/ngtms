/*    */ package com.hwacom.ngtms.ao.fm.model;
/*    */ 
/*    */ import com.hwacom.ngtms.base.annotation.Comment;
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.Id;
/*    */ import javax.persistence.Temporal;
/*    */ import javax.persistence.TemporalType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Entity
/*    */ public class NcuCardTapData
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 7949586230660331212L;
/*    */   @Id
/*    */   @Comment("主機編號-卡機識別號-cardNumber")
/*    */   private String id;
/*    */   @Comment("主機編號")
/*    */   private String ncuId;
/*    */   @Comment("卡機識別號")
/*    */   private String deviceId;
/*    */   @Comment("卡片編號")
/*    */   private String cardNumber;
/*    */   @Temporal(TemporalType.TIMESTAMP)
/*    */   @Comment("資料時間")
/*    */   private Date dataTime;
/*    */   
/*    */   public String getId() {
/* 36 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 40 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getNcuId() {
/* 44 */     return this.ncuId;
/*    */   }
/*    */   
/*    */   public void setNcuId(String ncuId) {
/* 48 */     this.ncuId = ncuId;
/*    */   }
/*    */   
/*    */   public String getDeviceId() {
/* 52 */     return this.deviceId;
/*    */   }
/*    */   
/*    */   public void setDeviceId(String deviceId) {
/* 56 */     this.deviceId = deviceId;
/*    */   }
/*    */   
/*    */   public String getCardNumber() {
/* 60 */     return this.cardNumber;
/*    */   }
/*    */   
/*    */   public void setCardNumber(String cardNumber) {
/* 64 */     this.cardNumber = cardNumber;
/*    */   }
/*    */   
/*    */   public Date getDataTime() {
/* 68 */     return this.dataTime;
/*    */   }
/*    */   
/*    */   public void setDataTime(Date dataTime) {
/* 72 */     this.dataTime = dataTime;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 77 */     int prime = 31;
/* 78 */     int result = 1;
/* 79 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 80 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj == null) return false; 
/* 87 */     if (getClass() != obj.getClass()) return false; 
/* 88 */     NcuCardTapData other = (NcuCardTapData)obj;
/* 89 */     if (this.id == null)
/* 90 */     { if (other.id != null) return false;  }
/* 91 */     else if (!this.id.equals(other.id)) { return false; }
/* 92 */      return true;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\model\NcuCardTapData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */