/*    */ package com.hwacom.ngtms.toolbox.csv;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.sql.Connection;
/*    */ import java.sql.DatabaseMetaData;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Statement;
/*    */ import java.util.Properties;
/*    */ import org.apache.commons.io.FileUtils;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Csv2MySQL
/*    */   extends Csv2Db
/*    */ {
/* 21 */   private static Logger logger = LoggerFactory.getLogger(Csv2MySQL.class);
/*    */   private final boolean importTestCsvDataFile;
/*    */   
/*    */   public Csv2MySQL(String configPath) throws IOException, ClassNotFoundException, SQLException
/*    */   {
/* 26 */     this(configPath, false);
/*    */   }
/*    */   
/*    */   public Csv2MySQL(String configPath, boolean importTestCsvDataFile) throws IOException, ClassNotFoundException, SQLException
/*    */   {
/* 31 */     super(configPath);
/* 32 */     this.importTestCsvDataFile = importTestCsvDataFile;
/*    */   }
/*    */   
/*    */   public void importData(String tableName) throws SQLException
/*    */   {
/* 37 */     String datafile = this.props.getProperty(tableName + ".datafile");
/* 38 */     if ((StringUtils.isBlank(datafile)) || ((!this.importTestCsvDataFile) && 
/* 39 */       (datafile.trim().toUpperCase().endsWith("_@TEST@.CSV")))) {
/* 40 */       logger.info("Bypass CSV file [" + datafile + "]");
/* 41 */       return;
/*    */     }
/*    */     
/* 44 */     String csvPath = getResourceDir(datafile);
/* 45 */     logger.info("import data from csv. table={}, csv={}", tableName, csvPath);
/*    */     
/*    */ 
/* 48 */     Statement stmt = this.connection.createStatement(1005, 1008);Throwable localThrowable3 = null;
/*    */     try {
/* 49 */       String query = "LOAD DATA LOCAL INFILE '" + csvPath + "' INTO TABLE " + tableName + " CHARACTER SET 'utf8'  FIELDS TERMINATED BY ','";
/*    */       
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 56 */       logger.info(query);
/* 57 */       stmt.executeUpdate(query);
/*    */     }
/*    */     catch (Throwable localThrowable1)
/*    */     {
/* 47 */       localThrowable3 = localThrowable1;throw localThrowable1;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     }
/*    */     finally
/*    */     {
/*    */ 
/*    */ 
/*    */ 
/* 58 */       if (stmt != null) { if (localThrowable3 != null) try { stmt.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else { stmt.close();
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void createTable(String tableName)
/*    */     throws SQLException, IOException
/*    */   {
/* 71 */     Statement stmt = this.connection.createStatement(1005, 1008);Throwable localThrowable3 = null;
/*    */     try {
/* 73 */       String ddl = this.props.getProperty(tableName + ".ddl");
/*    */       
/* 75 */       DatabaseMetaData meta = this.connection.getMetaData();
/* 76 */       ResultSet result = meta.getTables(null, null, tableName, null);
/* 77 */       while (result.next()) {
/* 78 */         logger.info("SET FOREIGN_KEY_CHECKS = 0");
/* 79 */         logger.info("drop table if exists " + tableName);
/* 80 */         stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
/* 81 */         stmt.executeUpdate("drop table if exists " + tableName);
/*    */       }
/*    */       
/* 84 */       String createQuery = FileUtils.readFileToString(new File(getResourceDir(ddl)), "UTF-8");
/* 85 */       String[] stmtString = createQuery.split(";");
/* 86 */       for (String s : stmtString) {
/* 87 */         if (!StringUtils.isBlank(s))
/*    */         {
/*    */ 
/* 90 */           logger.info(s);
/* 91 */           stmt.executeUpdate(s);
/*    */         }
/*    */       }
/*    */     }
/*    */     catch (Throwable localThrowable1)
/*    */     {
/* 70 */       localThrowable3 = localThrowable1;throw localThrowable1;
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
/*    */     }
/*    */     finally
/*    */     {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 93 */       if (stmt != null) if (localThrowable3 != null) try { stmt.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else stmt.close();
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\toolbox-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\toolbox\csv\Csv2MySQL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */