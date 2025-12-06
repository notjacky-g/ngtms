/*    */ package com.hwacom.ngtms.common.fm.hz;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum CommonHzTopic
/*    */   implements HzDistObjEnum
/*    */ {
/* 13 */   ACCOUNT_ACCESS_DENIED("使用者存取不具權限的功能");
/*    */   
/*    */   private String description;
/*    */   
/*    */   private CommonHzTopic(String description) {
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


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\hz\CommonHzTopic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */