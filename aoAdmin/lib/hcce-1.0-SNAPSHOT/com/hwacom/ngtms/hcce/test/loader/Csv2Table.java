/*     */ package com.hwacom.ngtms.hcce.test.loader;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.core.io.ClassPathResource;
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
/*     */ public class Csv2Table
/*     */ {
/*  29 */   private static Logger logger = LoggerFactory.getLogger(Csv2Table.class);
/*     */   
/*     */   private DbType dbType;
/*     */   protected Connection connection;
/*     */   
/*     */   public Csv2Table(DbType dbType, String url, String userName, String password) throws ClassNotFoundException, SQLException {
/*  35 */     this.dbType = dbType;
/*     */     try {
/*  37 */       switch (dbType) {
/*     */         case H2:
/*  39 */           Class.forName("org.h2.Driver");
/*     */           break;
/*     */         case MySQL:
/*  42 */           Class.forName("com.mysql.jdbc.Driver");
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/*  47 */       this.connection = DriverManager.getConnection(url, userName, password);
/*  48 */     } catch (Exception ex) {
/*  49 */       if (this.connection != null) {
/*     */         try {
/*  51 */           this.connection.close();
/*  52 */           this.connection = null;
/*  53 */         } catch (SQLException e) {
/*  54 */           logger.error("Failed to close connection", e);
/*     */         } 
/*     */       }
/*  57 */       throw ex;
/*     */     } 
/*     */   }
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
/*     */   public void loadData(String tableName, String dataFile, boolean containColumnName) throws SQLException, IOException {
/*     */     String fileName;
/*  72 */     ClassPathResource cr = new ClassPathResource(dataFile);
/*  73 */     File file = cr.getFile();
/*     */     
/*  75 */     String colLine = null;
/*  76 */     if (containColumnName) {
/*  77 */       try (BufferedReader in = new BufferedReader(new InputStreamReader(cr
/*  78 */               .getInputStream(), "UTF8"))) {
/*  79 */         colLine = in.readLine();
/*  80 */         String UTF8_BOM = "﻿";
/*  81 */         if (colLine != null && colLine.startsWith(UTF8_BOM)) {
/*  82 */           colLine = colLine.substring(1);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  87 */     switch (this.dbType) {
/*     */       case MySQL:
/*  89 */         fileName = file.getCanonicalPath().replace('\\', '/');
/*  90 */         try (Statement stmt = this.connection.createStatement()) {
/*     */           String sql;
/*     */           
/*  93 */           if (containColumnName) {
/*  94 */             sql = "LOAD DATA LOCAL INFILE '" + fileName + "' INTO TABLE " + tableName + " CHARACTER SET 'utf8' FIELDS TERMINATED BY ',' IGNORE 1 LINES (" + colLine + ")";
/*     */ 
/*     */ 
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */ 
/*     */             
/* 103 */             sql = "LOAD DATA LOCAL INFILE '" + fileName + "' INTO TABLE " + tableName + " CHARACTER SET 'utf8' FIELDS TERMINATED BY ','";
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 110 */           logger.debug("SQL: {}", sql);
/* 111 */           int n = stmt.executeUpdate(sql);
/* 112 */           logger.info("Load data from {} to table {}, insert {} rows", new Object[] { file
/* 113 */                 .getName(), tableName, Integer.valueOf(n) });
/*     */         } 
/*     */         break;
/*     */       case H2:
/* 117 */         try (Statement stmt = this.connection.createStatement()) {
/*     */           String sql;
/* 119 */           if (containColumnName) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 126 */             sql = "INSERT INTO " + tableName + "(" + colLine + ") SELECT * FROM CSVREAD('" + file.getCanonicalPath() + "',null,'charset=UTF-8 null=\\\\N')";
/*     */           } else {
/*     */             
/* 129 */             ResultSet rs = stmt.executeQuery("select * from " + tableName + " limit 1");
/* 130 */             ResultSetMetaData rsMetaData = rs.getMetaData();
/* 131 */             int cols = rsMetaData.getColumnCount();
/* 132 */             StringBuilder sb = new StringBuilder();
/* 133 */             for (int i = 1; i <= cols; i++) {
/* 134 */               sb.append(rsMetaData.getColumnName(i)).append(",");
/*     */             }
/* 136 */             sb.setLength(colLine.length() - 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 143 */             sql = "INSERT INTO " + tableName + " SELECT * FROM CSVREAD('" + file.getCanonicalPath() + "','" + sb.toString() + "','charset=UTF-8 null=\\\\N')";
/*     */           } 
/*     */           
/* 146 */           logger.debug("SQL: {}", sql);
/* 147 */           int n = stmt.executeUpdate(sql);
/* 148 */           logger.info("Load data from {} to table {}, insert {} rows", new Object[] { file
/* 149 */                 .getName(), tableName, Integer.valueOf(n) });
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadData(boolean containColumnName, String... dataFileNames) throws SQLException, IOException {
/* 174 */     for (String dataFile : dataFileNames) {
/* 175 */       String tableName = dataFile;
/* 176 */       int index1 = tableName.lastIndexOf('\\');
/* 177 */       if (index1 >= 0) {
/* 178 */         tableName = tableName.substring(index1 + 1);
/*     */       } else {
/* 180 */         index1 = tableName.lastIndexOf('/');
/* 181 */         if (index1 >= 0) {
/* 182 */           tableName = tableName.substring(index1 + 1);
/*     */         }
/*     */       } 
/*     */       
/* 186 */       int index2 = tableName.lastIndexOf('#');
/* 187 */       if (index2 >= 0) { tableName = tableName.substring(0, index2); }
/*     */       else
/* 189 */       { index2 = tableName.lastIndexOf('.');
/* 190 */         if (index2 >= 0) tableName = tableName.substring(0, index2);  }
/*     */       
/* 192 */       loadData(tableName, dataFile, containColumnName);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void close() {
/* 197 */     if (this.connection != null)
/*     */       try {
/* 199 */         this.connection.close();
/* 200 */         this.connection = null;
/* 201 */       } catch (SQLException e) {
/* 202 */         logger.error("Failed to close connection", e);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\test\loader\Csv2Table.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */