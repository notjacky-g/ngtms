/*     */ package com.hwacom.ngtms.toolbox.csv;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ public class Csv2MySQLWithEmptyConvert2Null
/*     */   extends Csv2Db
/*     */ {
/*  25 */   static Logger logger = LoggerFactory.getLogger(Csv2MySQLWithEmptyConvert2Null.class);
/*     */   
/*     */   private final boolean importTestCsvDataFile;
/*  28 */   private Map<String, String> loadFileSqlColumnPartMap = new HashMap();
/*     */   
/*     */   public Csv2MySQLWithEmptyConvert2Null(String configPath) throws IOException, ClassNotFoundException, SQLException
/*     */   {
/*  32 */     this(configPath, false);
/*     */   }
/*     */   
/*     */   public Csv2MySQLWithEmptyConvert2Null(String configPath, boolean importTestCsvDataFile) throws IOException, ClassNotFoundException, SQLException
/*     */   {
/*  37 */     super(configPath);
/*  38 */     this.importTestCsvDataFile = importTestCsvDataFile;
/*     */   }
/*     */   
/*     */   public void importData(String tableName) throws SQLException
/*     */   {
/*  43 */     String datafile = this.props.getProperty(tableName + ".datafile");
/*  44 */     if ((StringUtils.isBlank(datafile)) || ((!this.importTestCsvDataFile) && 
/*  45 */       (datafile.trim().toUpperCase().endsWith("_@TEST@.CSV")))) {
/*  46 */       logger.info("Bypass CSV file [" + datafile + "]");
/*  47 */       return;
/*     */     }
/*     */     
/*  50 */     String csvPath = getResourceDir(datafile);
/*  51 */     logger.info("import data from csv. table={}, csv={}", tableName, csvPath);
/*     */     
/*     */ 
/*  54 */     Statement stmt = this.connection.createStatement(1005, 1008);Throwable localThrowable3 = null;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/*  63 */       String query = "LOAD DATA LOCAL INFILE '" + csvPath + "' INTO TABLE " + tableName + " CHARACTER SET 'utf8'  FIELDS TERMINATED BY ',' " + (String)this.loadFileSqlColumnPartMap.get(tableName);
/*     */       
/*  65 */       logger.info(query);
/*  66 */       stmt.executeUpdate(query);
/*     */     }
/*     */     catch (Throwable localThrowable1)
/*     */     {
/*  53 */       localThrowable3 = localThrowable1;throw localThrowable1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  67 */       if (stmt != null) { if (localThrowable3 != null) try { stmt.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else { stmt.close();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void createTable(String tableName)
/*     */     throws SQLException, IOException
/*     */   {
/*  80 */     Statement stmt = this.connection.createStatement(1005, 1008);Throwable localThrowable3 = null;
/*     */     try {
/*  82 */       String ddl = this.props.getProperty(tableName + ".ddl");
/*     */       
/*  84 */       DatabaseMetaData meta = this.connection.getMetaData();
/*  85 */       ResultSet result = meta.getTables(null, null, tableName, null);
/*  86 */       while (result.next()) {
/*  87 */         logger.info("SET FOREIGN_KEY_CHECKS = 0");
/*  88 */         logger.info("drop table if exists " + tableName);
/*  89 */         stmt.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
/*  90 */         stmt.executeUpdate("drop table if exists " + tableName);
/*     */       }
/*     */       
/*  93 */       String createQuery = FileUtils.readFileToString(new File(getResourceDir(ddl)), "UTF-8");
/*  94 */       String[] stmtString = createQuery.split(";");
/*  95 */       for (String s : stmtString) {
/*  96 */         if (!StringUtils.isBlank(s))
/*     */         {
/*     */ 
/*  99 */           logger.info(s);
/*     */           
/* 101 */           stmt.executeUpdate(s);
/* 102 */           parseTableCreateSqlForLoadFileSql(s);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Throwable localThrowable1)
/*     */     {
/*  79 */       localThrowable3 = localThrowable1;throw localThrowable1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 104 */       if (stmt != null) if (localThrowable3 != null) try { stmt.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else stmt.close();
/*     */     }
/*     */   }
/*     */   
/* 108 */   private void parseTableCreateSqlForLoadFileSql(String createTableSql) { if (!createTableSql.trim().toUpperCase().startsWith("CREATE TABLE")) { return;
/*     */     }
/* 110 */     boolean isSqlColumnField = false;
/* 111 */     String tableName = null;
/* 112 */     List<String> columnList = new ArrayList();
/*     */     
/* 114 */     for (String segment : createTableSql.split("`")) {
/* 115 */       if (isSqlColumnField) {
/* 116 */         if (tableName == null) {
/* 117 */           tableName = segment;
/* 118 */         } else if (!columnList.contains(segment)) {
/* 119 */           columnList.add(segment);
/*     */         }
/*     */       } else {
/* 122 */         if (segment.matches("(?s).*\\s*KEY\\s*(?s).*"))
/*     */           break;
/*     */       }
/* 125 */       isSqlColumnField = !isSqlColumnField;
/*     */     }
/*     */     
/* 128 */     generateLoadFileSqlColumnPart(tableName, columnList);
/*     */   }
/*     */   
/*     */   private void generateLoadFileSqlColumnPart(String tableName, List<String> columnList) {
/* 132 */     if (this.loadFileSqlColumnPartMap.containsKey(tableName))
/* 133 */       logger.warn("Duplicate table [{}] creation SQL detected!!!!!!!!!!!", tableName);
/* 134 */     System.err.print("==========================\nTable [" + tableName + "]\n==========================\n");
/*     */     
/*     */ 
/* 137 */     StringBuilder columnListBuilder = new StringBuilder();
/* 138 */     StringBuilder setColumnBuilder = new StringBuilder();
/*     */     
/* 140 */     for (String column : columnList) {
/* 141 */       System.err.print("Column [" + column + "]\n");
/*     */       
/* 143 */       columnListBuilder.append(",@v").append(column);
/* 144 */       setColumnBuilder
/* 145 */         .append(",")
/* 146 */         .append(column)
/* 147 */         .append(" = NULLIF(@v")
/* 148 */         .append(column)
/* 149 */         .append(",'')");
/*     */     }
/*     */     
/* 152 */     System.err.print("==========================\n\n");
/* 153 */     this.loadFileSqlColumnPartMap.put(tableName, 
/*     */     
/* 155 */       "(" + 
/*     */       
/*     */ 
/* 158 */       columnListBuilder
/* 159 */       .substring(1) + ") SET " + 
/* 160 */       setColumnBuilder
/* 161 */       .substring(1));
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\toolbox-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\toolbox\csv\Csv2MySQLWithEmptyConvert2Null.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */