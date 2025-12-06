/*    */ package com.hwacom.ngtms.hcce.test;
/*    */ 
/*    */ 
/*    */ public class FmeInfo
/*    */ {
/*    */   private String fmeName;
/*    */   
/*    */   private String fmClassName;
/*    */   
/*    */   private String description;
/*    */   
/*    */ 
/*    */   public FmeInfo(String fmeName, String fmClassName)
/*    */   {
/* 15 */     this.fmeName = fmeName;
/* 16 */     this.fmClassName = fmClassName;
/*    */   }
/*    */   
/*    */   public FmeInfo(String fmeName, String fmClassName, String description) {
/* 20 */     this.fmeName = fmeName;
/* 21 */     this.fmClassName = fmClassName;
/* 22 */     this.description = description;
/*    */   }
/*    */   
/*    */   public String getFmeName() {
/* 26 */     return this.fmeName;
/*    */   }
/*    */   
/*    */   public void setFmeName(String fmeName) {
/* 30 */     this.fmeName = fmeName;
/*    */   }
/*    */   
/*    */   public String getFmClassName() {
/* 34 */     return this.fmClassName;
/*    */   }
/*    */   
/*    */   public void setFmClassName(String fmClassName) {
/* 38 */     this.fmClassName = fmClassName;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 42 */     return this.description;
/*    */   }
/*    */   
/*    */   public void setDescription(String description) {
/* 46 */     this.description = description;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 51 */     return "FmeInfo [fmeName=" + this.fmeName + ", fmClassName=" + this.fmClassName + ", description=" + this.description + "]";
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\test\FmeInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */