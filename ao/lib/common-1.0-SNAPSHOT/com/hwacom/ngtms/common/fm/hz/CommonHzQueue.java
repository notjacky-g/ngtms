/*    */ package com.hwacom.ngtms.common.fm.hz;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum CommonHzQueue
/*    */   implements HzDistObjEnum
/*    */ {
/* 13 */   OPERATION_LOG("操作記錄");
/*    */   
/*    */   private String description;
/*    */   
/*    */   private CommonHzQueue(String description) {
/* 18 */     this.description = description;
/*    */   }
/*    */   
/*    */ 
/*    */   public String toHzName()
/*    */   {
/* 24 */     return toString();
/*    */   }
/*    */   
/*    */   public String getDescription()
/*    */   {
/* 29 */     return this.description;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\hz\CommonHzQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */