/*    */ package com.hwacom.ngtms.ao.fm.model;
/*    */ 
/*    */ import com.hwacom.ngtms.base.annotation.Comment;
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.GeneratedValue;
/*    */ import javax.persistence.Id;
/*    */ import javax.persistence.Temporal;
/*    */ import javax.persistence.TemporalType;
/*    */ import org.hibernate.annotations.GenericGenerator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Entity
/*    */ public class LifeFaceLockCardLogData
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -8149706311950218562L;
/*    */   @GeneratedValue(generator = "uuid")
/*    */   @GenericGenerator(name = "uuid", strategy = "uuid")
/*    */   @Id
/*    */   @Comment("UUID")
/*    */   private String id;
/*    */   @Temporal(TemporalType.TIMESTAMP)
/*    */   @Comment("資料時間")
/*    */   private Date dataTime;
/*    */   @Comment("主機編號")
/*    */   private String ncuId;
/*    */   @Comment("鎖卡的卡號")
/*    */   private String lockCard;
/*    */   @Comment("臉辨結果")
/*    */   private boolean isFaceMatch;
/*    */   @Comment("卡片是否鎖卡")
/*    */   private boolean isLockCard;
/*    */   
/*    */   public String getId() {
/* 45 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 49 */     this.id = id;
/*    */   }
/*    */   
/*    */   public Date getDataTime() {
/* 53 */     return this.dataTime;
/*    */   }
/*    */   
/*    */   public void setDataTime(Date dataTime) {
/* 57 */     this.dataTime = dataTime;
/*    */   }
/*    */   
/*    */   public String getNcuId() {
/* 61 */     return this.ncuId;
/*    */   }
/*    */   
/*    */   public void setNcuId(String ncuId) {
/* 65 */     this.ncuId = ncuId;
/*    */   }
/*    */   
/*    */   public String getLockCard() {
/* 69 */     return this.lockCard;
/*    */   }
/*    */   
/*    */   public void setLockCard(String lockCard) {
/* 73 */     this.lockCard = lockCard;
/*    */   }
/*    */   
/*    */   public boolean isFaceMatch() {
/* 77 */     return this.isFaceMatch;
/*    */   }
/*    */   
/*    */   public void setFaceMatch(boolean isFaceMatch) {
/* 81 */     this.isFaceMatch = isFaceMatch;
/*    */   }
/*    */   
/*    */   public boolean isLockCard() {
/* 85 */     return this.isLockCard;
/*    */   }
/*    */   
/*    */   public void setLockCard(boolean isLockCard) {
/* 89 */     this.isLockCard = isLockCard;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 94 */     return "LifeFaceLockCardLogData [id=" + this.id + ", dataTime=" + this.dataTime + ", ncuId=" + this.ncuId + ", lockCard=" + this.lockCard + ", isFaceMatch=" + this.isFaceMatch + ", isLockCard=" + this.isLockCard + "]";
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\model\LifeFaceLockCardLogData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */