/*    */ package com.hwacom.ngtms.hcce.hz;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum HzQueue
/*    */   implements HzDistObjEnum
/*    */ {
/* 22 */   NodeUnregistered("Node 從 Cluster 離線");
/*    */   
/*    */   private String description;
/*    */   
/*    */   private HzQueue(String description) {
/* 27 */     this.description = description;
/*    */   }
/*    */   
/*    */   public String getDescription()
/*    */   {
/* 32 */     return this.description;
/*    */   }
/*    */   
/*    */ 
/*    */   public String toHzName()
/*    */   {
/* 38 */     return toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\hz\HzQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */