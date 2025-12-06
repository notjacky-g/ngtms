/*    */ package com.hwacom.ngtms.node.performance.fm.hz;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.hz.HzMapEnum;
/*    */ import com.hwacom.ngtms.hcce.hz.HzMapIndex;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum NodePerformanceHzMap
/*    */   implements HzMapEnum
/*    */ {
/* 13 */   NodePerformanceLog("節點OS層效能資料");
/*    */   
/*    */ 
/*    */   private String description;
/*    */   private String mapStoreBeanName;
/*    */   private HzMapIndex[] hzMapIndexes;
/*    */   
/*    */   private NodePerformanceHzMap(String description)
/*    */   {
/* 22 */     this.description = description;
/*    */   }
/*    */   
/*    */   private NodePerformanceHzMap(String description, HzMapIndex[] hzMapIndexes) {
/* 26 */     this.description = description;
/* 27 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */   
/*    */   private NodePerformanceHzMap(String description, String mapStoreBeanName) {
/* 31 */     this.description = description;
/* 32 */     this.mapStoreBeanName = mapStoreBeanName;
/*    */   }
/*    */   
/*    */   private NodePerformanceHzMap(String description, String mapStoreBeanName, HzMapIndex[] hzMapIndexes)
/*    */   {
/* 37 */     this.description = description;
/* 38 */     this.mapStoreBeanName = mapStoreBeanName;
/* 39 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */   
/*    */   public String getDescription()
/*    */   {
/* 44 */     return this.description;
/*    */   }
/*    */   
/*    */   public String getDataStoreBeanName()
/*    */   {
/* 49 */     return this.mapStoreBeanName;
/*    */   }
/*    */   
/*    */   public String toHzName()
/*    */   {
/* 54 */     return toString();
/*    */   }
/*    */   
/*    */   public HzMapIndex[] getHzMapIndexes()
/*    */   {
/* 59 */     return this.hzMapIndexes;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nodePerformance-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\node\performance\fm\hz\NodePerformanceHzMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */