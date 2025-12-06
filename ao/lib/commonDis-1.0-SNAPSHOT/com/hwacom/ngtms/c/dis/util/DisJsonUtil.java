/*    */ package com.hwacom.ngtms.c.dis.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.json.JsonArray;
/*    */ import javax.json.JsonObject;
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
/*    */ public class DisJsonUtil
/*    */ {
/*    */   public static List<String> extractDevices(JsonObject jsonObject)
/*    */   {
/* 23 */     return extractStringList(jsonObject, "deviceNames");
/*    */   }
/*    */   
/*    */   public static List<String> extractStringList(JsonObject jsonObject, String name) {
/* 27 */     List<String> result = new ArrayList();
/* 28 */     JsonArray jsonArray = jsonObject.getJsonArray(name);
/* 29 */     for (int i = 0; i < jsonArray.size(); i++) {
/* 30 */       result.add(jsonArray.getString(i));
/*    */     }
/* 32 */     return result;
/*    */   }
/*    */   
/*    */   public static List<Integer> extractIntegerList(JsonObject jsonObject, String name) {
/* 36 */     List<Integer> result = new ArrayList();
/* 37 */     JsonArray jsonArray = jsonObject.getJsonArray(name);
/* 38 */     for (int i = 0; i < jsonArray.size(); i++) {
/* 39 */       result.add(Integer.valueOf(jsonArray.getInt(i)));
/*    */     }
/* 41 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\util\DisJsonUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */