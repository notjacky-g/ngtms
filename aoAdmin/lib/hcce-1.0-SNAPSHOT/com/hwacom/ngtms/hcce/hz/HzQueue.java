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
/*    */   HzQueue(String description) {
/* 27 */     this.description = description;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 32 */     return this.description;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toHzName() {
/* 38 */     return toString();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\hz\HzQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */