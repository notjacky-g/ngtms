/*    */ package com.hwacom.ngtms.base.hibernate;
/*    */ import org.hibernate.dialect.Dialect;
/*    */ import org.hibernate.dialect.MySQL5InnoDBDialect;
/*    */ import org.hibernate.mapping.Table;
/*    */ import org.hibernate.tool.schema.spi.Exporter;
/*    */ 
/*    */ public class MySqlDBDialect extends MySQL5InnoDBDialect {
/*  8 */   private TableExporter tableExporter = new TableExporter((Dialect)this);
/*    */ 
/*    */   
/*    */   public Exporter<Table> getTableExporter() {
/* 12 */     return (Exporter<Table>)this.tableExporter;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hibernate\MySqlDBDialect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */