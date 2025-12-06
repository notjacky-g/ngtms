/*    */ package com.hwacom.ngtms.c.dis.fm.hz;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum DisHzQueue
/*    */   implements HzDistObjEnum
/*    */ {
/* 14 */   MessageCompareLog("收集記錄各資顯設備定時比對記錄"), 
/*    */   
/*    */ 
/* 17 */   DisplayChangeLog("收集記錄資訊變更記錄");
/*    */   
/*    */   private String description;
/*    */   
/*    */   private DisHzQueue(String description)
/*    */   {
/* 23 */     this.description = description;
/*    */   }
/*    */   
/*    */ 
/*    */   public String toHzName()
/*    */   {
/* 29 */     return "DIS_" + toString();
/*    */   }
/*    */   
/*    */   public String getDescription()
/*    */   {
/* 34 */     return this.description;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\hz\DisHzQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */