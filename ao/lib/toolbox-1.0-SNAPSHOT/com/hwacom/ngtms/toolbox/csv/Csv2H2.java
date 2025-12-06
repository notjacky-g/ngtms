/*    */ package com.hwacom.ngtms.toolbox.csv;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.sql.Connection;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.ResultSetMetaData;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Statement;
/*    */ import java.util.Properties;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import org.apache.commons.io.FileUtils;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Csv2H2
/*    */   extends Csv2Db
/*    */ {
/* 23 */   static Logger logger = LoggerFactory.getLogger(Csv2H2.class);
/*    */   
/*    */   public Csv2H2(String configPath) throws IOException, ClassNotFoundException, SQLException {
/* 26 */     super(configPath);
/*    */   }
/*    */   
/*    */   public void importData(String tableName) throws SQLException
/*    */   {
/* 31 */     String datafile = this.props.getProperty(tableName + ".datafile");
/* 32 */     if (StringUtils.isBlank(datafile)) {
/* 33 */       logger.warn("No CSV file can be imported into table [{}]", tableName);
/* 34 */       return;
/*    */     }
/*    */     
/* 37 */     String csvPath = getResourceDir(datafile);
/* 38 */     logger.info("import data from csv. table={}, csv={}", tableName, csvPath);
/*    */     
/*    */ 
/* 41 */     Statement stmt = this.connection.createStatement(1005, 1008);Throwable localThrowable3 = null;
/*    */     try {
/* 42 */       ResultSet rs = stmt.executeQuery("select * from " + tableName);
/*    */       
/* 44 */       int cols = rs.getMetaData().getColumnCount();
/* 45 */       StringBuffer header = new StringBuffer();
/* 46 */       for (int i = 1; i <= cols; i++) header.append(rs.getMetaData().getColumnName(i)).append(",");
/* 47 */       header.deleteCharAt(header.length() - 1);
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
/* 58 */       String insertQuery = "INSERT INTO " + tableName + " SELECT * FROM CSVREAD('" + csvPath + "', '" + header.toString() + "', 'charset=UTF-8 fieldSeparator=,');";
/*    */       
/* 60 */       logger.info("Import [{}] datas by SQL:\n{}", Integer.valueOf(stmt.executeUpdate(insertQuery)), insertQuery);
/*    */     }
/*    */     catch (Throwable localThrowable1)
/*    */     {
/* 40 */       localThrowable3 = localThrowable1;throw localThrowable1;
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
/* 61 */       if (stmt != null) if (localThrowable3 != null) try { stmt.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else stmt.close();
/*    */     }
/*    */   }
/*    */   
/*    */   public void createTable(String tableName) throws SQLException, IOException
/*    */   {
/* 67 */     Statement stmt = this.connection.createStatement(1005, 1008);Throwable localThrowable3 = null;
/* 68 */     try { String ddl = this.props.getProperty(tableName + ".ddl");
/*    */       
/* 70 */       String createQuery = FileUtils.readFileToString(new File(getResourceDir(ddl)), "UTF-8");
/* 71 */       String[] stmtString = createQuery.split(";");
/* 72 */       for (String s : stmtString) {
/* 73 */         if ((!StringUtils.isBlank(s)) && (s.trim().toUpperCase().startsWith("CREATE TABLE")))
/*    */         {
/* 75 */           int columnDeclareEndIndex = searchColumnDeclareEnd(s.trim());
/*    */           
/* 77 */           String middleSql = columnDeclareEndIndex == -1 ? s : s.substring(0, columnDeclareEndIndex);
/* 78 */           String finalSql = middleSql.replace(" FLOAT(", " DECIMAL(");
/*    */           
/* 80 */           logger.info(finalSql);
/* 81 */           stmt.executeUpdate("drop table if exists " + tableName);
/* 82 */           stmt.executeUpdate(finalSql);
/*    */         }
/*    */       }
/*    */     }
/*    */     catch (Throwable localThrowable1)
/*    */     {
/* 66 */       localThrowable3 = localThrowable1;throw localThrowable1;
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
/* 84 */       if (stmt != null) if (localThrowable3 != null) try { stmt.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else stmt.close();
/*    */     }
/*    */   }
/*    */   
/* 88 */   private int searchColumnDeclareEnd(String sql) throws SQLException { if (!sql.toUpperCase().startsWith("CREATE TABLE")) { return -1;
/*    */     }
/* 90 */     Pattern pattern = Pattern.compile("\\)|\\(");
/* 91 */     Matcher matcher = pattern.matcher(sql);
/*    */     
/* 93 */     int parenthesesWeightNumber = 0;
/* 94 */     while (matcher.find()) {
/* 95 */       parenthesesWeightNumber += (matcher.group().equals("(") ? 1 : -1);
/* 96 */       if (parenthesesWeightNumber == 0) { return matcher.end();
/*    */       }
/*    */     }
/* 99 */     throw new SQLException("異常的 SQL：存在不成對的括號，SQL：" + sql);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\toolbox-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\toolbox\csv\Csv2H2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */