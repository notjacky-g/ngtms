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
/* 11 */   private static final ThreadLocal<DataSource> dataSourceHolder = new ThreadLocal();
/*    */   
/*    */   public static void setDataSource(DataSource dataSource) {
/* 14 */     dataSourceHolder.set(dataSource);
/*    */   }
/*    */   
/*    */   public static DataSource getDataSource() {
/* 18 */     return (DataSource)dataSourceHolder.get();
/*    */   }
/*    */   
/*    */   public static void clearDataSource() {
/* 22 */     dataSourceHolder.remove();
/*    */   }
/*    */   
/*    */   public static enum DataSource {
/* 26 */     OLDB, 
/* 27 */     HDDB;
/*    */     
/*    */     private DataSource() {}
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\HistoryDataSourceHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */