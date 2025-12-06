/*    */ package com.hwacom.ngtms.base.hibernate;
/*    */ 
/*    */ import org.hibernate.dialect.MySQL5InnoDBDialect;
/*    */ import org.hibernate.tool.schema.spi.Exporter;
/*    */ 
/*    */ public class MySqlDBDialect extends MySQL5InnoDBDialect
/*    */ {
/*  8 */   private TableExporter tableExporter = new TableExporter(this);
/*    */   
/*    */   public Exporter<org.hibernate.mapping.Table> getTableExporter()
/*    */   {
/* 12 */     return this.tableExporter;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hibernate\MySqlDBDialect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */