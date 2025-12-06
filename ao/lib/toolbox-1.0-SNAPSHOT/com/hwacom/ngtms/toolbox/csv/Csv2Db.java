/*     */ package com.hwacom.ngtms.toolbox.csv;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.net.URL;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.HashSet;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.ibatis.jdbc.ScriptRunner;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
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
/*     */ public abstract class Csv2Db
/*     */ {
/*  42 */   private static Logger logger = LoggerFactory.getLogger(Csv2Db.class);
/*     */   
/*  44 */   protected Properties props = new Properties();
/*     */   protected Connection connection;
/*     */   
/*     */   public Csv2Db(String configPath) throws IOException, ClassNotFoundException, SQLException {
/*  48 */     InputStream in = getClass().getResourceAsStream(configPath);
/*  49 */     this.props.load(in);
/*  50 */     init();
/*     */   }
/*     */   
/*     */   public Csv2Db(Properties config) throws ClassNotFoundException, SQLException {
/*  54 */     this.props = config;
/*  55 */     init();
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
/*     */   private void init()
/*     */     throws ClassNotFoundException, SQLException
/*     */   {
/*  69 */     String driver = this.props.getProperty("driver");
/*  70 */     String url = this.props.getProperty("url");
/*  71 */     String user = this.props.getProperty("user");
/*  72 */     String password = this.props.getProperty("password");
/*     */     
/*  74 */     logger.info("connecting to {}", url);
/*  75 */     Class.forName(driver);
/*  76 */     this.connection = DriverManager.getConnection(url, user, password);
/*     */   }
/*     */   
/*     */   public void close()
/*     */   {
/*     */     try {
/*  82 */       this.connection.close();
/*     */     } catch (SQLException e) {
/*  84 */       logger.info("Failed when closing connection", e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Statement createStatement()
/*     */     throws SQLException
/*     */   {
/*  95 */     return this.connection.createStatement();
/*     */   }
/*     */   
/*     */   public void executeScript(String sqlPath) throws FileNotFoundException {
/*  99 */     Reader reader = new FileReader(sqlPath);
/* 100 */     ScriptRunner runner = new ScriptRunner(this.connection);
/* 101 */     runner.runScript(reader);
/*     */   }
/*     */   
/*     */   public String getProperty(String key) {
/* 105 */     return this.props.getProperty(key);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void createTable(String tableName)
/*     */     throws SQLException, IOException
/*     */   {
/* 117 */     Statement stmt = this.connection.createStatement(1005, 1008);Throwable localThrowable3 = null;
/*     */     try {
/* 119 */       String ddl = this.props.getProperty(tableName + ".ddl");
/*     */       
/* 121 */       DatabaseMetaData meta = this.connection.getMetaData();
/* 122 */       ResultSet result = meta.getTables(null, null, tableName, null);
/* 123 */       while (result.next()) {
/* 124 */         logger.info("drop table if exists " + tableName);
/* 125 */         stmt.executeUpdate("drop table if exists " + tableName);
/*     */       }
/*     */       
/* 128 */       String createQuery = FileUtils.readFileToString(new File(getResourceDir(ddl)), "UTF-8");
/* 129 */       String[] stmtString = createQuery.split(";");
/* 130 */       for (String s : stmtString) {
/* 131 */         if (!StringUtils.isBlank(s))
/*     */         {
/*     */ 
/* 134 */           logger.info(s);
/* 135 */           stmt.executeUpdate(s);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Throwable localThrowable1)
/*     */     {
/* 116 */       localThrowable3 = localThrowable1;throw localThrowable1;
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
/* 137 */       if (stmt != null) { if (localThrowable3 != null) try { stmt.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else { stmt.close();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void importData(String paramString)
/*     */     throws SQLException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void loadAll()
/*     */     throws SQLException, IOException
/*     */   {
/* 155 */     Set<String> tables = getTables();
/* 156 */     for (String table : tables) {
/* 157 */       logger.info("loading {} from csv", table);
/* 158 */       createTable(table);
/*     */       try {
/* 160 */         importData(table);
/*     */       } catch (Exception e) {
/* 162 */         logger.error("import csv to " + table + " fail !!", e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Set<String> getTables()
/*     */   {
/* 173 */     return getTables(this.props);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Set<String> getTables(Properties props)
/*     */   {
/* 183 */     Set<String> keys = props.stringPropertyNames();
/* 184 */     Set<String> tables = new HashSet();
/* 185 */     for (String key : keys) {
/* 186 */       if (key.endsWith(".ddl")) {
/* 187 */         tables.add(key.substring(0, key.indexOf(".ddl")));
/*     */       }
/*     */     }
/* 190 */     return tables;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getResourceDir(String resource)
/*     */   {
/* 200 */     URL urlClass = Csv2Db.class.getResource(resource);
/* 201 */     File fClass = FileUtils.toFile(urlClass);
/*     */     
/* 203 */     String strPath = fClass.getAbsolutePath().replace('\\', '/');
/* 204 */     return strPath;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\toolbox-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\toolbox\csv\Csv2Db.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */