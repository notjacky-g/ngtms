/*    */ package com.hwacom.ngtms.c.dis.fm.model;
/*    */ 
/*    */ import com.hwacom.ngtms.base.annotation.Comment;
/*    */ import java.io.Serializable;
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
/*    */ public class DisDefaultTravelTime
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @Comment("旅行時間上限(單位：分)")
/*    */   private Integer maxTravelTime;
/*    */   @Comment("旅行時間下限(單位：分)")
/*    */   private Integer minTravelTime;
/*    */   
/*    */   public Integer getMaxTravelTime() {
/* 25 */     return this.maxTravelTime;
/*    */   }
/*    */   
/*    */   public void setMaxTravelTime(Integer maxTravelTime) {
/* 29 */     this.maxTravelTime = maxTravelTime;
/*    */   }
/*    */   
/*    */   public Integer getMinTravelTime() {
/* 33 */     return this.minTravelTime;
/*    */   }
/*    */   
/*    */   public void setMinTravelTime(Integer minTravelTime) {
/* 37 */     this.minTravelTime = minTravelTime;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\model\DisDefaultTravelTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */