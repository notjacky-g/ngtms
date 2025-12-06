/*     */ package com.hwacom.ngtms.c.dis.util;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.hwacom.ngtms.c.dis.shared.QueryRareWordResultMessage;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.cms.SetCmsCharPatternReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.wis.SetWisCharPatternReqPm;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransferHelper
/*     */ {
/*     */   public static byte[] transferHexToByteArray(String source, String separator) {
/*  26 */     String[] array = source.split(separator);
/*  27 */     byte[] result = new byte[array.length];
/*  28 */     for (int i = 0; i < array.length; i++) {
/*  29 */       String each = array[i];
/*  30 */       result[i] = 
/*  31 */         (byte)((Character.digit(each.charAt(0), 16) << 4) + Character.digit(each.charAt(1), 16));
/*     */     } 
/*  33 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] transferHexToByteArray(String source) {
/*  43 */     if (source.length() % 2 == 1) {
/*  44 */       source = source + "0";
/*     */     }
/*  46 */     byte[] result = new byte[source.length() / 2];
/*  47 */     for (int i = 0; i < source.length() / 2; i++) {
/*  48 */       int index = i * 2;
/*  49 */       result[i] = 
/*     */ 
/*     */         
/*  52 */         (byte)((Character.digit(source.charAt(index), 16) << 4) + Character.digit(source.charAt(index + 1), 16));
/*     */     } 
/*  54 */     return result;
/*     */   }
/*     */   
/*     */   public static int transferToInt(Byte firstByte, Byte secondByte) {
/*  58 */     int first = firstByte.byteValue() & 0xFF;
/*  59 */     int second = secondByte.byteValue() & 0xFF;
/*     */     
/*  61 */     return (first << 8) + second;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte reverse(byte b) {
/*  71 */     return (byte)(Integer.reverse(b) >>> 24);
/*     */   }
/*     */ 
/*     */   
/*     */   public static QueryRareWordResultMessage.QueryRareWordResult transferQueryRareWordResult(SetCmsCharPatternReqPm setCmsCharPatternReqPm) {
/*  76 */     QueryRareWordResultMessage.QueryRareWordResult result = new QueryRareWordResultMessage.QueryRareWordResult();
/*  77 */     result.setWidth(setCmsCharPatternReqPm.column);
/*  78 */     result.setHeight(setCmsCharPatternReqPm.row);
/*  79 */     result.setPattern(setCmsCharPatternReqPm.pattern);
/*  80 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static QueryRareWordResultMessage.QueryRareWordResult transferQueryRareWordResult(SetWisCharPatternReqPm setWisCharPatternReqPm) {
/*  85 */     QueryRareWordResultMessage.QueryRareWordResult result = new QueryRareWordResultMessage.QueryRareWordResult();
/*  86 */     result.setWidth(setWisCharPatternReqPm.column);
/*  87 */     result.setHeight(setWisCharPatternReqPm.row);
/*  88 */     result.setPattern(setWisCharPatternReqPm.pattern);
/*  89 */     return result;
/*     */   }
/*     */   
/*     */   public static List<Float> string2FloatList(String flashSpeed, String delimiter) {
/*  93 */     List<Float> flashSpeedList = new ArrayList<>();
/*  94 */     if (flashSpeed != null && !flashSpeed.isEmpty()) {
/*  95 */       Splitter splitter = Splitter.on(delimiter);
/*  96 */       Iterable<String> temp = splitter.omitEmptyStrings().split(flashSpeed);
/*  97 */       for (String s : temp) {
/*  98 */         flashSpeedList.add(Float.valueOf(Float.parseFloat(s)));
/*     */       }
/*     */     } 
/* 101 */     return flashSpeedList;
/*     */   }
/*     */   
/*     */   public static String floatList2String(List<Float> flashSpeedList, String delimiter) {
/* 105 */     StringBuffer sb = new StringBuffer();
/* 106 */     for (Float fl : flashSpeedList) {
/* 107 */       sb.append(fl).append(delimiter);
/*     */     }
/*     */     
/* 110 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\di\\util\TransferHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */