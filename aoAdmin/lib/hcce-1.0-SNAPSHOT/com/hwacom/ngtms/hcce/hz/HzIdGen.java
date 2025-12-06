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
/*    */ public enum HzIdGen
/*    */   implements HzDistObjEnum
/*    */ {
/*    */   private String description;
/*    */   
/*    */   HzIdGen(String description) {
/* 23 */     this.description = description;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 28 */     return this.description;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toHzName() {
/* 34 */     return toString();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\hz\HzIdGen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */