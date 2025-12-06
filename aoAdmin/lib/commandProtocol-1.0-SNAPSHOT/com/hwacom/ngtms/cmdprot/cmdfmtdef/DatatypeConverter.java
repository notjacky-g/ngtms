/*    */ package com.hwacom.ngtms.cmdprot.cmdfmtdef;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DatatypeConverter
/*    */ {
/*    */   public static int parseHexInt(String hexInt) {
/* 11 */     if (hexInt.startsWith("0x")) hexInt = hexInt.substring(2); 
/* 12 */     return Integer.parseInt(hexInt, 16);
/*    */   }
/*    */   
/*    */   public static String printHexInt(int hexInt) {
/* 16 */     return "0x" + Integer.toHexString(hexInt);
/*    */   }
/*    */   
/*    */   public static int[] parseHexIntArray(String array) {
/* 20 */     String[] parts = array.split("[,|\\s]+");
/* 21 */     int[] values = new int[parts.length];
/* 22 */     for (int i = 0; i < values.length; ) { values[i] = parseHexInt(parts[i]); i++; }
/* 23 */      return values;
/*    */   }
/*    */   
/*    */   public static String printHexIntArray(int[] values) {
/* 27 */     StringBuilder sb = new StringBuilder();
/* 28 */     for (int i = 0; i < values.length; i++) {
/* 29 */       if (i != 0) sb.append(','); 
/* 30 */       sb.append("0x").append(Integer.toHexString(values[i]));
/*    */     } 
/* 32 */     return sb.toString();
/*    */   }
/*    */   
/*    */   public static int[] parseIntArray(String array) {
/* 36 */     String[] parts = array.split("[,|\\s]+");
/* 37 */     int[] values = new int[parts.length];
/* 38 */     for (int i = 0; i < values.length; ) { values[i] = Integer.parseInt(parts[i]); i++; }
/* 39 */      return values;
/*    */   }
/*    */   
/*    */   public static String printIntArray(int[] values) {
/* 43 */     StringBuilder sb = new StringBuilder();
/* 44 */     for (int i = 0; i < values.length; i++) {
/* 45 */       if (i != 0) sb.append(','); 
/* 46 */       sb.append(values[i]);
/*    */     } 
/* 48 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\DatatypeConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */