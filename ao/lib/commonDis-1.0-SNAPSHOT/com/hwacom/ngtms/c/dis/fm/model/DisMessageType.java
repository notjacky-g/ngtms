/*    */ package com.hwacom.ngtms.c.dis.fm.model;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum DisMessageType
/*    */ {
/* 13 */   EVENT("事故"), 
/*    */   
/* 15 */   Announcement("交通宣導"), 
/*    */   
/* 17 */   TRAVELTIME("旅行時間"), 
/*    */   
/* 19 */   CONGESTION("交通壅塞");
/*    */   
/*    */   private String description;
/*    */   
/*    */   private DisMessageType(String description) {
/* 24 */     this.description = description;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 28 */     return this.description;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\model\DisMessageType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */