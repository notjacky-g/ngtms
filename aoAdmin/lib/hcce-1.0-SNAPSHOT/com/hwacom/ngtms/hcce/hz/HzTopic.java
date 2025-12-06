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
/*    */ public enum HzTopic
/*    */   implements HzDistObjEnum
/*    */ {
/* 22 */   ClusterState("Cluster 狀態");
/*    */   
/*    */   private String description;
/*    */   
/*    */   HzTopic(String description) {
/* 27 */     this.description = description;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 31 */     return this.description;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toHzName() {
/* 37 */     return toString();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\hz\HzTopic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */