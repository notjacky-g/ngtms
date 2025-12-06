/*    */ package com.hwacom.ngtms.c.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArrayListUtils
/*    */ {
/*    */   public static <T> ArrayList<T> convert(List<T> list)
/*    */   {
/* 16 */     ArrayList<T> result = new ArrayList();
/* 17 */     for (T t : list) {
/* 18 */       result.add(t);
/*    */     }
/* 20 */     return result;
/*    */   }
/*    */   
/*    */   public static <T> ArrayList<T> convert(Collection<T> list)
/*    */   {
/* 25 */     ArrayList<T> result = new ArrayList();
/* 26 */     for (T t : list) {
/* 27 */       result.add(t);
/*    */     }
/* 29 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\util\ArrayListUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */