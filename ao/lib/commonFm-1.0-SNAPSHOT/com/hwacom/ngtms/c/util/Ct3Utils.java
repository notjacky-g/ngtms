/*    */ package com.hwacom.ngtms.c.util;
/*    */ 
/*    */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.TimeNoWeekNoSecPm;
/*    */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.TimeNoWeekPm;
/*    */ import java.util.Calendar;
/*    */ import org.joda.time.DateTime;
/*    */ 
/*    */ public class Ct3Utils
/*    */ {
/*    */   public static java.util.Date timeNoWeek2Date(TimeNoWeekPm pm)
/*    */   {
/* 12 */     int year = pm.year + 1911;
/* 13 */     DateTime time = new DateTime(year, pm.month, pm.day, pm.hour, pm.min, pm.sec);
/* 14 */     return time.toDate();
/*    */   }
/*    */   
/*    */   public static java.util.Date timeNoWeekNoSec2Date(TimeNoWeekNoSecPm pm) {
/* 18 */     int year = pm.year + 1911;
/* 19 */     DateTime time = new DateTime(year, pm.month, pm.day, pm.hour, pm.min, 0);
/* 20 */     return time.toDate();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static String byte2VersionNoString(int version_no)
/*    */   {
/* 31 */     int major = (version_no & 0xF0) >> 4;
/* 32 */     int minor = version_no & 0xF;
/*    */     
/* 34 */     return major + "." + minor;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static Long dateTransferLong(int year, int month, int day)
/*    */   {
/* 45 */     Calendar calendar = Calendar.getInstance();
/* 46 */     calendar.set(1, year + 1911);
/* 47 */     calendar.set(2, month - 1);
/* 48 */     calendar.set(5, day);
/* 49 */     return Long.valueOf(calendar.getTime().getTime());
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\util\Ct3Utils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */