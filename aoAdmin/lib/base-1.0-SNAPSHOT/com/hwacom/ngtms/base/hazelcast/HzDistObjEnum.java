/*    */ package com.hwacom.ngtms.base.hazelcast;
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
/*    */ 
/*    */ 
/*    */ public interface HzDistObjEnum
/*    */ {
/*    */   String toHzName();
/*    */   
/*    */   default String getDescription() {
/* 19 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hazelcast\HzDistObjEnum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */