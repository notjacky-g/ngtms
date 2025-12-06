/*    */ package com.hwacom.ngtms.hcce.hz;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HzMapIndex
/*    */ {
/*    */   private String attribute;
/*    */   private boolean ordered;
/*    */   
/*    */   public HzMapIndex() {}
/*    */   
/*    */   public HzMapIndex(String attribute) {
/* 18 */     this.attribute = attribute;
/*    */   }
/*    */   
/*    */   public HzMapIndex(String attribute, boolean ordered) {
/* 22 */     this.attribute = attribute;
/* 23 */     this.ordered = ordered;
/*    */   }
/*    */   
/*    */   public String getAttribute() {
/* 27 */     return this.attribute;
/*    */   }
/*    */   
/*    */   public void setAttribute(String attribute) {
/* 31 */     this.attribute = attribute;
/*    */   }
/*    */   
/*    */   public boolean isOrdered() {
/* 35 */     return this.ordered;
/*    */   }
/*    */   
/*    */   public void setOrdered(boolean ordered) {
/* 39 */     this.ordered = ordered;
/*    */   }
/*    */   
/*    */   public static HzMapIndex[] parsingDeclare(String declares) {
/* 43 */     String[] parts = declares.split("\\s*,\\s*");
/* 44 */     ArrayList<HzMapIndex> list = new ArrayList<>();
/* 45 */     for (String part : parts) {
/* 46 */       if (part.length() != 0) {
/* 47 */         int i = part.indexOf(':');
/* 48 */         if (i >= 0)
/* 49 */         { String field = part.substring(0, i).trim();
/* 50 */           String ordered = part.substring(i + 1).trim();
/* 51 */           list.add(new HzMapIndex(field, Boolean.parseBoolean(ordered))); }
/* 52 */         else { list.add(new HzMapIndex(part)); } 
/*    */       } 
/* 54 */     }  HzMapIndex[] hzMapIndexex = new HzMapIndex[list.size()];
/* 55 */     list.toArray(hzMapIndexex);
/* 56 */     return hzMapIndexex;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\hz\HzMapIndex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */