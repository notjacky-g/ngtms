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
/*    */ @Entity
/*    */ public class LifeFaceCompareData
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 7823014104208801963L;
/*    */   @Id
/*    */   @Comment("主機編號 _臉辨卡號 ")
/*    */   private String id;
/*    */   @Temporal(TemporalType.TIMESTAMP)
/*    */   @Comment("資料時間")
/*    */   private Date dataTime;
/*    */   @Comment("臉辨卡號")
/*    */   private String card;
/*    */   @Comment("臉辨結果")
/*    */   private boolean isMatch;
/*    */   
/*    */   public String getId() {
/* 31 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 35 */     this.id = id;
/*    */   }
/*    */   
/*    */   public Date getDataTime() {
/* 39 */     return this.dataTime;
/*    */   }
/*    */   
/*    */   public void setDataTime(Date dataTime) {
/* 43 */     this.dataTime = dataTime;
/*    */   }
/*    */   
/*    */   public String getCard() {
/* 47 */     return this.card;
/*    */   }
/*    */   
/*    */   public void setCard(String card) {
/* 51 */     this.card = card;
/*    */   }
/*    */   
/*    */   public boolean isMatch() {
/* 55 */     return this.isMatch;
/*    */   }
/*    */   
/*    */   public void setMatch(boolean isMatch) {
/* 59 */     this.isMatch = isMatch;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 64 */     return "LifeFaceCompareData [id=" + this.id + ", dataTime=" + this.dataTime + ", isMatch=" + this.isMatch + "]";
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\model\LifeFaceCompareData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */