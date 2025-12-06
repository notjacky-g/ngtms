/*    */ package com.hwacom.ngtms.hcce.hz;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface HzQueueEnum
/*    */   extends HzDistObjEnum
/*    */ {
/*    */   default String getDataStoreBeanName() {
/* 13 */     return null;
/*    */   }
/*    */   
/*    */   default Integer getSyncBackCount() {
/* 17 */     return null;
/*    */   }
/*    */   
/*    */   default Integer getAsyncBackCount() {
/* 21 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\hz\HzQueueEnum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */