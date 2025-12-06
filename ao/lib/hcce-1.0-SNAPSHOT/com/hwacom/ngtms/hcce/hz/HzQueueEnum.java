/*    */ package com.hwacom.ngtms.hcce.hz;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract interface HzQueueEnum
/*    */   extends HzDistObjEnum
/*    */ {
/*    */   public String getDataStoreBeanName()
/*    */   {
/* 13 */     return null;
/*    */   }
/*    */   
/*    */   public Integer getSyncBackCount() {
/* 17 */     return null;
/*    */   }
/*    */   
/*    */   public Integer getAsyncBackCount() {
/* 21 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\hz\HzQueueEnum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */