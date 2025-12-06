/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HistoryRoutingDataSource
/*    */   extends AbstractRoutingDataSource
/*    */ {
/*    */   protected Object determineCurrentLookupKey() {
/* 15 */     return HistoryDataSourceHolder.getDataSource();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\HistoryRoutingDataSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */