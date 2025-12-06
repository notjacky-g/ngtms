/*    */ package com.hwacom.ngtms.base.util;
/*    */ 
/*    */ import java.util.Date;
/*    */ import org.joda.time.DateTime;
/*    */ import org.joda.time.ReadableInstant;
/*    */ import org.joda.time.format.DateTimeFormat;
/*    */ import org.joda.time.format.DateTimeFormatter;
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
/*    */ 
/*    */ 
/*    */ public class KeyUtils
/*    */ {
/*    */   public static final String _DataTimeFormatPattern = "yyyyMMddHHmm";
/*    */   public static final String _DateFormatPattern = "yyyyMMdd";
/*    */   public static final String _DateTimeFormatPattern = "yyyyMMdd HH:mm";
/*    */   public static final String _DateTimeFullFormatPattern = "yyyyMMdd HH:mm:ss.S";
/*    */   public static final String _Dash = "-";
/*    */   
/*    */   public static String getKey(String prefix, Date dataTime) {
/* 31 */     return getKey(new DateTime(dataTime), new Object[] { prefix });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getKey(String prefix, DateTime dataTime) {
/* 42 */     return getKey(dataTime, new Object[] { prefix });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getKey(Date dataTime, Object... prefix) {
/* 53 */     return getKey(new DateTime(dataTime), prefix);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getKey(DateTime dataTime, Object... prefix) {
/* 62 */     DateTimeFormatter FORMATER = DateTimeFormat.forPattern("-yyyyMMddHHmm");
/* 63 */     StringBuilder sb = new StringBuilder();
/* 64 */     for (int i = 0; i < prefix.length; i++) {
/* 65 */       if (i > 0) {
/* 66 */         sb.append("-");
/*    */       }
/* 68 */       sb.append(prefix[i].toString());
/*    */     } 
/* 70 */     sb.append(FORMATER.print((ReadableInstant)dataTime));
/* 71 */     return sb.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getKey(Object... prefix) {
/* 82 */     DateTimeFormatter FORMATER = DateTimeFormat.forPattern("-yyyyMMddHHmm");
/* 83 */     StringBuilder sb = new StringBuilder();
/* 84 */     StringBuilder dateStr = new StringBuilder();
/* 85 */     for (int i = 0; i < prefix.length; i++) {
/* 86 */       if (prefix[i] instanceof DateTime) {
/* 87 */         dateStr.append(FORMATER.print((ReadableInstant)prefix[i]));
/*    */       }
/* 89 */       else if (prefix[i] instanceof Date) {
/* 90 */         dateStr.append(FORMATER.print(((Date)prefix[i]).getTime()));
/*    */       } else {
/*    */         
/* 93 */         if (i > 0) {
/* 94 */           sb.append("-");
/*    */         }
/* 96 */         sb.append(prefix[i].toString());
/*    */       } 
/* 98 */     }  return sb.append(dateStr).toString();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\bas\\util\KeyUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */