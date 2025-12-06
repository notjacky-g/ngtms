/*     */ package com.hwacom.ngtms.cmdprot.cmdfmtdef;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BytesUtility
/*     */ {
/*     */   public static int getBitsValue(long bitsData, int fromIndex, int bitLen) {
/*  11 */     long mask = ((1 << bitLen) - 1);
/*  12 */     long shiftNo = (fromIndex - bitLen + 1);
/*  13 */     mask <<= (int)shiftNo;
/*  14 */     long value = (bitsData & mask) >> (int)shiftNo;
/*  15 */     return (int)value;
/*     */   }
/*     */   
/*     */   public static long setBitsValue(long bitsData, int fromIndex, int bitLen, int value) {
/*  19 */     long mask = ((1 << bitLen) - 1);
/*  20 */     long shiftNo = (fromIndex - bitLen + 1);
/*  21 */     long d = value & mask;
/*  22 */     d <<= (int)shiftNo;
/*  23 */     bitsData |= d;
/*  24 */     return bitsData;
/*     */   }
/*     */   
/*     */   public static void setValueToBytes(byte[] data, int fromIndex, int byteNo, long value) {
/*  28 */     for (int i = 0, shiftNo = byteNo - 1; i < byteNo; i++, shiftNo--) {
/*  29 */       data[i + fromIndex] = (byte)(int)(value >> shiftNo * 8 & 0xFFL);
/*     */     }
/*     */   }
/*     */   
/*     */   public static long setBytesToValue(byte[] data) {
/*  34 */     long bitsData = 0L;
/*  35 */     for (int i = 0; i < data.length; i++) {
/*  36 */       bitsData = bitsData << 8L | (data[i] & 0xFF);
/*     */     }
/*  38 */     return bitsData;
/*     */   }
/*     */   
/*  41 */   public static final char[] hexArray = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toHexString(byte[] data) {
/*  46 */     if (data == null || data.length == 0) return "[]"; 
/*  47 */     char[] hexChars = new char[data.length * 3 - 1];
/*     */     
/*  49 */     for (int j = 0; j < data.length; j++) {
/*  50 */       int i = j * 3;
/*  51 */       if (j > 0) {
/*  52 */         hexChars[i - 1] = ' ';
/*     */       }
/*  54 */       int v = data[j] & 0xFF;
/*     */       
/*  56 */       hexChars[i] = hexArray[v >>> 4];
/*  57 */       hexChars[i + 1] = hexArray[v & 0xF];
/*     */     } 
/*  59 */     return new String(hexChars);
/*     */   }
/*     */   
/*     */   public static String toHexString(byte[] data, int maxLen) {
/*  63 */     if (data == null || data.length == 0) return "[]"; 
/*  64 */     int len = data.length * 3 - 1;
/*  65 */     if (len > maxLen) len = maxLen / 3 * 3; 
/*  66 */     char[] hexChars = new char[len];
/*     */     
/*  68 */     for (int j = 0; j < data.length; j++) {
/*  69 */       int i = j * 3;
/*     */       
/*  71 */       if (j > 0) {
/*  72 */         hexChars[i - 1] = ' ';
/*     */       }
/*  74 */       int v = data[j] & 0xFF;
/*  75 */       if (i + 3 < len) {
/*  76 */         hexChars[i] = hexArray[v >>> 4];
/*  77 */         hexChars[i + 1] = hexArray[v & 0xF];
/*     */       } else {
/*  79 */         hexChars[i + 2] = '.'; hexChars[i + 1] = '.'; hexChars[i] = '.';
/*     */         break;
/*     */       } 
/*     */     } 
/*  83 */     return new String(hexChars);
/*     */   }
/*     */   
/*     */   public static String toHexCode(byte[] data) {
/*  87 */     if (data == null || data.length == 0) return "{}";
/*     */     
/*  89 */     StringBuilder sb = new StringBuilder();
/*  90 */     sb.append('{');
/*  91 */     for (int j = 0; j < data.length; j++) {
/*     */       
/*  93 */       if (j > 0) {
/*  94 */         sb.append(',');
/*     */       }
/*  96 */       int v = data[j] & 0xFF;
/*  97 */       sb.append("(byte)0x");
/*  98 */       sb.append(hexArray[v >>> 4]);
/*  99 */       sb.append(hexArray[v & 0xF]);
/*     */     } 
/* 101 */     sb.append('}');
/* 102 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static boolean checkLRC(byte[] data, int startIndex, int length, int lrcIndex) {
/* 106 */     byte LRC = calculateLRC(data, startIndex, length);
/* 107 */     if (LRC == data[lrcIndex]) return true;
/*     */ 
/*     */     
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte calculateLRC(byte[] data, int startIndex, int length) {
/* 115 */     byte LRC = data[startIndex];
/* 116 */     for (int i = 1; i < length; i++) {
/* 117 */       LRC = (byte)(LRC ^ data[i + startIndex]);
/*     */     }
/* 119 */     return LRC;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\BytesUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */