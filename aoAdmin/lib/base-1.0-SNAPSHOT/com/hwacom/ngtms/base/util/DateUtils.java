/*    */ package com.hwacom.ngtms.base.util;
/*    */ 
/*    */ import java.time.Instant;
/*    */ import java.time.LocalDateTime;
/*    */ import java.time.ZoneId;
/*    */ import java.time.ZoneOffset;
/*    */ import java.time.temporal.ChronoUnit;
/*    */ import java.util.Date;
/*    */ import java.util.TimeZone;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DateUtils
/*    */ {
/*    */   public static LocalDateTime toLocalDateTime(Date date) {
/* 22 */     return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
/*    */   }
/*    */   
/*    */   public static LocalDateTime toLocalDateTime(long timestamp) {
/* 26 */     if (timestamp == 0L)
/* 27 */       throw new IllegalArgumentException(
/* 28 */           String.format("unknown format of timestamp %s", new Object[] { Long.valueOf(timestamp) })); 
/* 29 */     Instant instant = Instant.ofEpochMilli(timestamp);
/* 30 */     return LocalDateTime.ofInstant(instant, TimeZone.getDefault().toZoneId());
/*    */   }
/*    */   
/*    */   public static Date toDate(LocalDateTime ldt) {
/* 34 */     Instant instant = ldt.toInstant(ZoneOffset.UTC);
/* 35 */     return Date.from(instant);
/*    */   }
/*    */   
/*    */   public static Date nextHour(Date date) {
/* 39 */     LocalDateTime start = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
/* 40 */     LocalDateTime truncated = start.truncatedTo(ChronoUnit.HOURS);
/* 41 */     LocalDateTime nextHour = truncated.plus(1L, ChronoUnit.HOURS);
/* 42 */     return Date.from(nextHour.atZone(ZoneId.systemDefault()).toInstant());
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\bas\\util\DateUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */