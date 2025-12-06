/*    */ package com.hwacom.ngtms.hcce.web.vo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DistObjInfo
/*    */ {
/*    */   private String name;
/*    */   private String type;
/* 13 */   private long size = -1L;
/*    */   private boolean predicate;
/*    */   private boolean query;
/*    */   private boolean performance;
/*    */   private boolean destroy;
/*    */   private String comment;
/*    */   
/*    */   public String getName() {
/* 21 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 25 */     this.name = name;
/*    */   }
/*    */   
/*    */   public String getType() {
/* 29 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(String type) {
/* 33 */     this.type = type;
/*    */   }
/*    */   
/*    */   public long getSize() {
/* 37 */     return this.size;
/*    */   }
/*    */   
/*    */   public void setSize(long size) {
/* 41 */     this.size = size;
/*    */   }
/*    */   
/*    */   public boolean isPredicate() {
/* 45 */     return this.predicate;
/*    */   }
/*    */   
/*    */   public void setPredicate(boolean predicate) {
/* 49 */     this.predicate = predicate;
/*    */   }
/*    */   
/*    */   public boolean isQuery() {
/* 53 */     return this.query;
/*    */   }
/*    */   
/*    */   public void setQuery(boolean query) {
/* 57 */     this.query = query;
/*    */   }
/*    */   
/*    */   public boolean isPerformance() {
/* 61 */     return this.performance;
/*    */   }
/*    */   
/*    */   public void setPerformance(boolean performance) {
/* 65 */     this.performance = performance;
/*    */   }
/*    */   
/*    */   public boolean isDestroy() {
/* 69 */     return this.destroy;
/*    */   }
/*    */   
/*    */   public void setDestroy(boolean destroy) {
/* 73 */     this.destroy = destroy;
/*    */   }
/*    */   
/*    */   public String getComment() {
/* 77 */     return this.comment;
/*    */   }
/*    */   
/*    */   public void setComment(String comment) {
/* 81 */     this.comment = comment;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcConsole-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\web\vo\DistObjInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */