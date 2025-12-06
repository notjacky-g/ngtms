/*    */ package com.hwacom.ngtms.ao.fm.model;
/*    */ 
/*    */ import com.hwacom.ngtms.base.annotation.Comment;
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.Id;
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
/*    */ public class LifeFaceLockCardData
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -4156435027092041718L;
/*    */   @Id
/*    */   @Comment("資料時間 +主機編號+卡片編號")
/*    */   private String id;
/*    */   @Comment("資料時間")
/*    */   private Date dataTime;
/*    */   @Comment("主機編號")
/*    */   private String ncuId;
/*    */   @Comment("臉辨結果")
/*    */   private boolean isFaceMatch;
/*    */   @Comment("卡片是否鎖卡")
/*    */   private boolean isLockCard;
/*    */   
/*    */   public String getId() {
/* 35 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 39 */     this.id = id;
/*    */   }
/*    */   
/*    */   public Date getDataTime() {
/* 43 */     return this.dataTime;
/*    */   }
/*    */   
/*    */   public void setDataTime(Date dataTime) {
/* 47 */     this.dataTime = dataTime;
/*    */   }
/*    */   
/*    */   public String getNcuId() {
/* 51 */     return this.ncuId;
/*    */   }
/*    */   
/*    */   public void setNcuId(String ncuId) {
/* 55 */     this.ncuId = ncuId;
/*    */   }
/*    */   
/*    */   public boolean isFaceMatch() {
/* 59 */     return this.isFaceMatch;
/*    */   }
/*    */   
/*    */   public void setFaceMatch(boolean isFaceMatch) {
/* 63 */     this.isFaceMatch = isFaceMatch;
/*    */   }
/*    */   
/*    */   public boolean isLockCard() {
/* 67 */     return this.isLockCard;
/*    */   }
/*    */   
/*    */   public void setLockCard(boolean isLockCard) {
/* 71 */     this.isLockCard = isLockCard;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 76 */     int prime = 31;
/* 77 */     int result = 1;
/* 78 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 79 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 84 */     if (this == obj) return true; 
/* 85 */     if (obj == null) return false; 
/* 86 */     if (getClass() != obj.getClass()) return false; 
/* 87 */     LifeFaceLockCardData other = (LifeFaceLockCardData)obj;
/* 88 */     if (this.id == null)
/* 89 */     { if (other.id != null) return false;  }
/* 90 */     else if (!this.id.equals(other.id)) { return false; }
/* 91 */      return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 96 */     return "LifeFaceLockCardData [id=" + this.id + ", dataTime=" + this.dataTime + ", ncuId=" + this.ncuId + ", isFaceMatch=" + this.isFaceMatch + ", isLockCard=" + this.isLockCard + "]";
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\model\LifeFaceLockCardData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */