/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HistoryDataSourceHolder
/*    */ {
/* 11 */   private static final ThreadLocal<DataSource> dataSourceHolder = new ThreadLocal<>();
/*    */   
/*    */   public static void setDataSource(DataSource dataSource) {
/* 14 */     dataSourceHolder.set(dataSource);
/*    */   }
/*    */   
/*    */   public static DataSource getDataSource() {
/* 18 */     return dataSourceHolder.get();
/*    */   }
/*    */   
/*    */   public static void clearDataSource() {
/* 22 */     dataSourceHolder.remove();
/*    */   }
/*    */   
/*    */   public enum DataSource {
/* 26 */     OLDB,
/* 27 */     HDDB;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\HistoryDataSourceHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */