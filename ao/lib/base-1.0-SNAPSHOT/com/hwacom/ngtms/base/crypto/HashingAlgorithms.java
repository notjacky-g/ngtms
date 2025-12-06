/*    */ package com.hwacom.ngtms.base.crypto;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum HashingAlgorithms
/*    */ {
/* 10 */   MD5("MD5", 1), 
/* 11 */   SHA1("SHA-1", 2), 
/* 12 */   SHA2("SHA-2", 3), 
/* 13 */   SHA3("SHA-3", 4), 
/* 14 */   SHA256("SHA-256", 5);
/*    */   
/*    */   private String name;
/*    */   private int index;
/*    */   
/*    */   private HashingAlgorithms(String name, int index) {
/* 20 */     this.name = name;
/* 21 */     this.index = index;
/*    */   }
/*    */   
/*    */   public static String Name(int index) {
/* 25 */     for (HashingAlgorithms c : ) {
/* 26 */       if (c.getIndex() == index) {
/* 27 */         return c.name;
/*    */       }
/*    */     }
/* 30 */     return null;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 34 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 38 */     this.name = name;
/*    */   }
/*    */   
/*    */   public int getIndex() {
/* 42 */     return this.index;
/*    */   }
/*    */   
/*    */   public void setIndex(int index) {
/* 46 */     this.index = index;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\crypto\HashingAlgorithms.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */