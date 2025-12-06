/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HistoryRoutingDataSource
/*    */   extends AbstractRoutingDataSource
/*    */ {
/*    */   protected Object determineCurrentLookupKey()
/*    */   {
/* 15 */     return HistoryDataSourceHolder.getDataSource();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\HistoryRoutingDataSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */