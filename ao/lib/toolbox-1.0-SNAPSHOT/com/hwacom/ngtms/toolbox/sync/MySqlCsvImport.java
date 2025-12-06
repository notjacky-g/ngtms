/*     */ package com.hwacom.ngtms.toolbox.sync;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.time.LocalDate;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
/*     */ import org.apache.commons.compress.archivers.sevenz.SevenZFile;
/*     */ import org.apache.commons.configuration.CompositeConfiguration;
/*     */ import org.apache.commons.configuration.PropertiesConfiguration;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MySqlCsvImport
/*     */ {
/*  45 */   private static Logger logger = LoggerFactory.getLogger(MySqlCsvImport.class);
/*     */   
/*  47 */   private static int MAX_TEMP_FILE_SIZE = 20000000;
/*  48 */   private static String PROP_FILE_NAME = "configImport.properties";
/*  49 */   private static boolean CHANGE_MYSQL_VAR = true;
/*     */   
/*  51 */   private DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
/*  52 */   private DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMdd");
/*     */   
/*  54 */   private Pattern lineSeparatorPattern = Pattern.compile("[\\r\\n]+");
/*     */   
/*     */   protected Connection connection;
/*     */   private String workingRecFile;
/*     */   private LocalDate processingDate;
/*  59 */   private List<String> tableFilter = Collections.EMPTY_LIST;
/*     */   
/*     */ 
/*  62 */   private CompositeConfiguration config = new CompositeConfiguration();
/*     */   private String workingDirPath;
/*  64 */   private boolean isImportAll = false;
/*     */   
/*     */   public static void main(String[] args) {
/*  67 */     MySqlCsvImport obj = new MySqlCsvImport(args);
/*     */     try {
/*  69 */       obj.init();
/*  70 */       obj.process();
/*     */     } catch (Exception e) {
/*  72 */       logger.error(e.getMessage(), e);
/*     */     } finally {
/*  74 */       obj.close();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public MySqlCsvImport(String[] args)
/*     */   {
/*  81 */     if (args.length >= 1) {
/*  82 */       PROP_FILE_NAME = args[0];
/*     */     } else {
/*  84 */       logger.debug("確認 configImport.properties 載入是否正確!!");
/*     */     }
/*     */   }
/*     */   
/*     */   public void init() throws Exception {
/*  89 */     PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
/*  90 */     propertiesConfiguration.setEncoding("UTF-8");
/*  91 */     propertiesConfiguration.load(PROP_FILE_NAME);
/*     */     
/*  93 */     this.config.addConfiguration(propertiesConfiguration);
/*     */     
/*  95 */     String driver = this.config.getString("driver");
/*  96 */     String url = this.config.getString("url");
/*  97 */     String user = this.config.getString("user");
/*  98 */     String password = this.config.getString("password");
/*  99 */     Class.forName(driver);
/* 100 */     this.connection = DriverManager.getConnection(url, user, password);
/*     */     
/* 102 */     this.workingRecFile = this.config.getString("workingRecFile", "importRec.txt");
/* 103 */     File f = new File(this.workingRecFile);
/* 104 */     if ((f.isDirectory()) || (!f.exists())) {
/* 105 */       throw new Exception("workingRecFile 設定不正確!");
/*     */     }
/*     */     
/* 108 */     this.workingDirPath = this.config.getString("workingDir", "./");
/* 109 */     if ((!this.workingDirPath.endsWith("/")) && (!this.workingDirPath.endsWith("\\"))) {
/* 110 */       this.workingDirPath += "/";
/*     */     }
/*     */     
/* 113 */     BufferedReader br = new BufferedReader(new FileReader(f));Throwable localThrowable3 = null;
/* 114 */     try { this.processingDate = ofDate(br.readLine().trim());
/*     */     }
/*     */     catch (Throwable localThrowable1)
/*     */     {
/* 113 */       localThrowable3 = localThrowable1;throw localThrowable1;
/*     */     } finally {
/* 115 */       if (br != null) if (localThrowable3 != null) try { br.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else br.close();
/*     */     }
/* 117 */     LocalDate now = getLocalDate();
/* 118 */     if (now.isBefore(this.processingDate)) {
/* 119 */       throw new Exception("資料已處理到:" + this.processingDate);
/*     */     }
/*     */     
/* 122 */     String[] tf = this.config.getStringArray("tableFilter");
/* 123 */     if ((tf != null) && (tf.length > 0)) {
/* 124 */       this.tableFilter = Arrays.asList(tf);
/*     */     }
/*     */     
/* 127 */     String importAll = this.config.getString("importAll");
/* 128 */     if ((importAll != null) && (importAll.trim().equalsIgnoreCase("true"))) {
/* 129 */       this.isImportAll = true;
/*     */     }
/*     */   }
/*     */   
/*     */   private void changeStatementVar(Statement stmt) throws SQLException {
/* 134 */     if (CHANGE_MYSQL_VAR)
/*     */     {
/* 136 */       stmt.executeUpdate("SET SESSION sql_log_bin=0");
/*     */       
/* 138 */       stmt.executeUpdate("set global wait_timeout=2880000000");
/*     */       
/* 140 */       stmt.executeUpdate("set global interactive_timeout=604800");
/*     */     }
/*     */   }
/*     */   
/*     */   private LocalDate getLocalDate() {
/* 145 */     String minusDaysStr = this.config.getString("minusDays");
/* 146 */     int minusDays = 7;
/*     */     try {
/* 148 */       minusDays = Integer.parseInt(minusDaysStr);
/*     */     } catch (Exception e) {
/* 150 */       minusDays = 14;
/*     */     }
/* 152 */     LocalDate now = LocalDate.now().minusDays(minusDays);
/* 153 */     return now;
/*     */   }
/*     */   
/*     */   private Map<String, File> getBackupData(LocalDate processingDate) throws Exception {
/* 157 */     String r23aCompress = this.config.getString("r23aCompress");
/* 158 */     String r23aCsvRepository = this.config.getString("r23aCsvRepository");
/*     */     
/* 160 */     String fileExtention = null;
/* 161 */     if ("7z".equals(r23aCompress)) fileExtention = ".7z"; else {
/* 162 */       fileExtention = ".csv";
/*     */     }
/* 164 */     Map<String, File> files = new HashMap();
/*     */     
/* 166 */     if (this.isImportAll) {
/* 167 */       this.tableFilter = getWantImportTable();
/*     */     }
/*     */     
/*     */ 
/* 171 */     for (String tableName : this.tableFilter) {
/* 172 */       String moduleName = tableName.split("_")[0];
/* 173 */       String fileDateFormat = processingDate.format(this.formatter2) + "_000000";
/* 174 */       String outFilePath = r23aCsvRepository + File.separator + moduleName + File.separator + tableName + File.separator + tableName + "_" + fileDateFormat + fileExtention;
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
/* 185 */       logger.debug("file1:{}", outFilePath);
/*     */       
/* 187 */       File f = new File(outFilePath);
/* 188 */       if (f.exists()) {
/* 189 */         files.put(tableName, f);
/* 190 */         logger.debug("file2:{}", f.getAbsolutePath());
/*     */       }
/*     */     }
/* 193 */     return files;
/*     */   }
/*     */   
/*     */   private List<String> getWantImportTable() throws SQLException {
/* 197 */     Statement stmt = this.connection.createStatement();Throwable localThrowable3 = null;
/* 198 */     try { changeStatementVar(stmt);
/*     */       
/* 200 */       ResultSet rs = stmt.executeQuery("select TABLE_NAME, PARTITION_EXPRESSION from information_schema.PARTITIONS WHERE TABLE_SCHEMA = 'ngtms' and PARTITION_NAME is not null group by TABLE_NAME");
/*     */       
/* 202 */       List<String> values = new ArrayList();
/* 203 */       while (rs.next()) {
/* 204 */         values.add(rs.getString(1));
/*     */       }
/* 206 */       return values;
/*     */     }
/*     */     catch (Throwable localThrowable1)
/*     */     {
/* 197 */       localThrowable3 = localThrowable1;throw localThrowable1;
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 207 */       if (stmt != null) if (localThrowable3 != null) try { stmt.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else stmt.close();
/*     */     }
/*     */   }
/*     */   
/* 211 */   public void process() throws SQLException, IOException { LocalDate now = getLocalDate();
/* 212 */     logger.debug("localDate:{}, processingDate:{}", now, this.processingDate);
/* 213 */     int count = 0;
/* 214 */     int getBackupDataSize = 0;
/* 215 */     while (now.isAfter(this.processingDate)) {
/*     */       Iterator<String> it;
/* 217 */       try { this.processingDate = this.processingDate.plusDays(1L);
/* 218 */         Map<String, File> files = getBackupData(this.processingDate);
/* 219 */         getBackupDataSize = files.size();
/* 220 */         it = files.keySet().iterator();
/* 221 */         while (it.hasNext()) {
/* 222 */           String tableName = (String)it.next();
/* 223 */           logger.debug("tableName:{}", tableName);
/* 224 */           File file = (File)files.get(tableName);
/*     */           
/* 226 */           if (file.getAbsolutePath().endsWith("7z")) {
/* 227 */             importDataFromCsv7zWithHeader(tableName, file.getAbsolutePath());
/*     */           } else {
/* 229 */             importDataFromCsvWithHeader(tableName, file.getAbsolutePath());
/*     */           }
/* 231 */           count++;
/*     */         }
/*     */       } catch (Exception e) {
/* 234 */         logger.error("Failed to process..", e);
/*     */       }
/*     */       
/* 237 */       if (count == getBackupDataSize) {
/* 238 */         BufferedWriter bw = new BufferedWriter(new FileWriter(new File(this.workingRecFile)));it = null;
/* 239 */         try { bw.write(this.processingDate.format(this.formatter2));
/* 240 */           bw.flush();
/*     */         }
/*     */         catch (Throwable localThrowable1)
/*     */         {
/* 238 */           it = localThrowable1;throw localThrowable1;
/*     */         }
/*     */         finally {
/* 241 */           if (bw != null) if (it != null) try { bw.close(); } catch (Throwable localThrowable2) { it.addSuppressed(localThrowable2); } else bw.close();
/*     */         } }
/* 243 */       count = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public void close()
/*     */   {
/*     */     try {
/* 250 */       if (this.connection != null) this.connection.close();
/*     */     } catch (SQLException e) {
/* 252 */       logger.info("Failed when closing connection", e);
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
/*     */   private int importDataFromCsvWithHeader(String tableName, String filePath, String partitionName, String partitionDate)
/*     */     throws SQLException, IOException
/*     */   {
/* 271 */     Statement stmt = this.connection.createStatement();Throwable localThrowable3 = null;
/* 272 */     try { changeStatementVar(stmt);
/*     */       
/* 274 */       ResultSet rs = stmt.executeQuery("SELECT PARTITION_NAME,PARTITION_DESCRIPTION FROM INFORMATION_SCHEMA.PARTITIONS WHERE TABLE_NAME='" + tableName + "' AND TABLE_SCHEMA='ngtms' ORDER BY PARTITION_NAME");
/*     */       
/*     */ 
/*     */ 
/* 278 */       ArrayList<PartitionInfo> partitionInfos = new ArrayList();
/* 279 */       while (rs.next()) {
/* 280 */         String name = rs.getString(1);
/* 281 */         String description = rs.getString(2);
/* 282 */         if (name != null) {
/* 283 */           PartitionInfo partitionInfo = new PartitionInfo(name, description);
/* 284 */           partitionInfos.add(partitionInfo);
/*     */         } }
/* 286 */       rs.close();
/*     */       
/* 288 */       if (partitionInfos.size() > 0) {
/* 289 */         partitionDate = "'" + partitionDate + "'";
/* 290 */         PartitionInfo partitionInfo = new PartitionInfo(partitionName, partitionDate);
/* 291 */         if (!partitionInfos.contains(partitionInfo)) {
/* 292 */           partitionInfos.add(partitionInfo);
/* 293 */           Collections.sort(partitionInfos);
/* 294 */           int index = partitionInfos.indexOf(partitionInfo);
/* 295 */           String sql = "";
/* 296 */           if (index == partitionInfos.size() - 1) {
/* 297 */             sql = "ALTER TABLE " + tableName + " ADD PARTITION (PARTITION " + partitionName + " VALUES LESS THAN (" + partitionDate + "));";
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 305 */             logger.info("partition sql:{}", sql);
/* 306 */             stmt.executeUpdate(sql);
/* 307 */             logger.debug("Add partition: " + partitionName + " to " + tableName);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 322 */             sql = "ALTER TABLE " + tableName + " REORGANIZE PARTITION " + ((PartitionInfo)partitionInfos.get(index + 1)).name + " INTO (PARTITION " + partitionName + " VALUES LESS THAN (" + partitionDate + ")," + "PARTITION " + ((PartitionInfo)partitionInfos.get(index + 1)).name + " VALUES LESS THAN (" + ((PartitionInfo)partitionInfos.get(index + 1)).description + "));";
/*     */             
/* 324 */             logger.info("partition sql:{}", sql);
/* 325 */             logger.debug("Add partition: " + 
/* 326 */               ((PartitionInfo)partitionInfos.get(index + 1)).name + " to " + tableName);
/* 327 */             stmt.executeUpdate(sql);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Throwable localThrowable1)
/*     */     {
/* 271 */       localThrowable3 = localThrowable1;throw localThrowable1;
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
/* 331 */       if (stmt != null) if (localThrowable3 != null) try { stmt.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else stmt.close(); }
/* 332 */     if (filePath.endsWith(".7z")) return importDataFromCsv7zWithHeader(tableName, filePath);
/* 333 */     return importDataFromCsvWithHeader(tableName, filePath);
/*     */   }
/*     */   
/*     */ 
/*     */   private String replaceColName(String colLine, String colName, String tranColName)
/*     */   {
/* 339 */     Pattern regex = Pattern.compile("(^|,)\\s*(" + colName + ")\\s*(,|$)");
/* 340 */     StringBuilder sb = new StringBuilder(colLine);
/* 341 */     Matcher matcher = regex.matcher(sb);
/* 342 */     if (matcher.find()) {
/* 343 */       sb.replace(matcher.start(2), matcher.end(2), tranColName);
/*     */     }
/* 345 */     return sb.toString();
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   private int importDataFromCsvWithHeader(String tableName, String filePath)
/*     */     throws SQLException, IOException
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_2
/*     */     //   1: bipush 92
/*     */     //   3: bipush 47
/*     */     //   5: invokevirtual 184	java/lang/String:replace	(CC)Ljava/lang/String;
/*     */     //   8: astore_2
/*     */     //   9: aload_0
/*     */     //   10: aload_2
/*     */     //   11: invokespecial 185	com/hwacom/ngtms/toolbox/sync/MySqlCsvImport:getLineSeparator	(Ljava/lang/String;)Ljava/lang/String;
/*     */     //   14: astore 4
/*     */     //   16: new 62	java/io/BufferedReader
/*     */     //   19: dup
/*     */     //   20: new 186	java/io/InputStreamReader
/*     */     //   23: dup
/*     */     //   24: new 187	java/io/FileInputStream
/*     */     //   27: dup
/*     */     //   28: aload_2
/*     */     //   29: invokespecial 188	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
/*     */     //   32: ldc -67
/*     */     //   34: invokespecial 190	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
/*     */     //   37: invokespecial 65	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
/*     */     //   40: astore 5
/*     */     //   42: aconst_null
/*     */     //   43: astore 6
/*     */     //   45: aload_0
/*     */     //   46: getfield 41	com/hwacom/ngtms/toolbox/sync/MySqlCsvImport:connection	Ljava/sql/Connection;
/*     */     //   49: invokeinterface 115 1 0
/*     */     //   54: astore 7
/*     */     //   56: aconst_null
/*     */     //   57: astore 8
/*     */     //   59: aload 5
/*     */     //   61: invokevirtual 66	java/io/BufferedReader:readLine	()Ljava/lang/String;
/*     */     //   64: astore_3
/*     */     //   65: aload_3
/*     */     //   66: ifnonnull +83 -> 149
/*     */     //   69: iconst_0
/*     */     //   70: istore 9
/*     */     //   72: aload 7
/*     */     //   74: ifnull +37 -> 111
/*     */     //   77: aload 8
/*     */     //   79: ifnull +25 -> 104
/*     */     //   82: aload 7
/*     */     //   84: invokeinterface 124 1 0
/*     */     //   89: goto +22 -> 111
/*     */     //   92: astore 10
/*     */     //   94: aload 8
/*     */     //   96: aload 10
/*     */     //   98: invokevirtual 72	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*     */     //   101: goto +10 -> 111
/*     */     //   104: aload 7
/*     */     //   106: invokeinterface 124 1 0
/*     */     //   111: aload 5
/*     */     //   113: ifnull +33 -> 146
/*     */     //   116: aload 6
/*     */     //   118: ifnull +23 -> 141
/*     */     //   121: aload 5
/*     */     //   123: invokevirtual 70	java/io/BufferedReader:close	()V
/*     */     //   126: goto +20 -> 146
/*     */     //   129: astore 10
/*     */     //   131: aload 6
/*     */     //   133: aload 10
/*     */     //   135: invokevirtual 72	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*     */     //   138: goto +8 -> 146
/*     */     //   141: aload 5
/*     */     //   143: invokevirtual 70	java/io/BufferedReader:close	()V
/*     */     //   146: iload 9
/*     */     //   148: ireturn
/*     */     //   149: ldc -65
/*     */     //   151: astore 9
/*     */     //   153: aload_3
/*     */     //   154: aload 9
/*     */     //   156: invokevirtual 192	java/lang/String:startsWith	(Ljava/lang/String;)Z
/*     */     //   159: ifeq +9 -> 168
/*     */     //   162: aload_3
/*     */     //   163: iconst_1
/*     */     //   164: invokevirtual 193	java/lang/String:substring	(I)Ljava/lang/String;
/*     */     //   167: astore_3
/*     */     //   168: aload_3
/*     */     //   169: invokevirtual 194	java/lang/String:toLowerCase	()Ljava/lang/String;
/*     */     //   172: astore_3
/*     */     //   173: aload_0
/*     */     //   174: getfield 41	com/hwacom/ngtms/toolbox/sync/MySqlCsvImport:connection	Ljava/sql/Connection;
/*     */     //   177: invokeinterface 195 1 0
/*     */     //   182: astore 10
/*     */     //   184: aload 10
/*     */     //   186: aconst_null
/*     */     //   187: aconst_null
/*     */     //   188: aload_1
/*     */     //   189: aconst_null
/*     */     //   190: invokeinterface 196 5 0
/*     */     //   195: astore 11
/*     */     //   197: new 58	java/lang/StringBuilder
/*     */     //   200: dup
/*     */     //   201: invokespecial 59	java/lang/StringBuilder:<init>	()V
/*     */     //   204: astore 12
/*     */     //   206: aload 11
/*     */     //   208: invokeinterface 121 1 0
/*     */     //   213: ifeq +234 -> 447
/*     */     //   216: aload 11
/*     */     //   218: bipush 6
/*     */     //   220: invokeinterface 122 2 0
/*     */     //   225: astore 13
/*     */     //   227: aload 13
/*     */     //   229: ldc -59
/*     */     //   231: invokevirtual 82	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
/*     */     //   234: ifeq +103 -> 337
/*     */     //   237: aload 11
/*     */     //   239: iconst_4
/*     */     //   240: invokeinterface 122 2 0
/*     */     //   245: astore 14
/*     */     //   247: new 58	java/lang/StringBuilder
/*     */     //   250: dup
/*     */     //   251: invokespecial 59	java/lang/StringBuilder:<init>	()V
/*     */     //   254: ldc -58
/*     */     //   256: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   259: aload 14
/*     */     //   261: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   264: ldc -57
/*     */     //   266: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   269: invokevirtual 61	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   272: astore 15
/*     */     //   274: aload_0
/*     */     //   275: aload_3
/*     */     //   276: aload 14
/*     */     //   278: aload 15
/*     */     //   280: invokespecial 200	com/hwacom/ngtms/toolbox/sync/MySqlCsvImport:replaceColName	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
/*     */     //   283: astore_3
/*     */     //   284: aload 12
/*     */     //   286: invokevirtual 201	java/lang/StringBuilder:length	()I
/*     */     //   289: ifne +14 -> 303
/*     */     //   292: aload 12
/*     */     //   294: ldc -54
/*     */     //   296: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   299: pop
/*     */     //   300: goto +11 -> 311
/*     */     //   303: aload 12
/*     */     //   305: ldc -53
/*     */     //   307: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   310: pop
/*     */     //   311: aload 12
/*     */     //   313: aload 14
/*     */     //   315: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   318: ldc -52
/*     */     //   320: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   323: aload 15
/*     */     //   325: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   328: ldc -51
/*     */     //   330: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   333: pop
/*     */     //   334: goto +110 -> 444
/*     */     //   337: aload 13
/*     */     //   339: ldc -50
/*     */     //   341: invokevirtual 56	java/lang/String:endsWith	(Ljava/lang/String;)Z
/*     */     //   344: ifeq +100 -> 444
/*     */     //   347: aload 11
/*     */     //   349: iconst_4
/*     */     //   350: invokeinterface 122 2 0
/*     */     //   355: astore 14
/*     */     //   357: new 58	java/lang/StringBuilder
/*     */     //   360: dup
/*     */     //   361: invokespecial 59	java/lang/StringBuilder:<init>	()V
/*     */     //   364: ldc -58
/*     */     //   366: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   369: aload 14
/*     */     //   371: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   374: ldc -57
/*     */     //   376: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   379: invokevirtual 61	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   382: astore 15
/*     */     //   384: aload_0
/*     */     //   385: aload_3
/*     */     //   386: aload 14
/*     */     //   388: aload 15
/*     */     //   390: invokespecial 200	com/hwacom/ngtms/toolbox/sync/MySqlCsvImport:replaceColName	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
/*     */     //   393: astore_3
/*     */     //   394: aload 12
/*     */     //   396: invokevirtual 201	java/lang/StringBuilder:length	()I
/*     */     //   399: ifne +14 -> 413
/*     */     //   402: aload 12
/*     */     //   404: ldc -54
/*     */     //   406: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   409: pop
/*     */     //   410: goto +11 -> 421
/*     */     //   413: aload 12
/*     */     //   415: ldc -53
/*     */     //   417: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   420: pop
/*     */     //   421: aload 12
/*     */     //   423: aload 14
/*     */     //   425: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   428: ldc -49
/*     */     //   430: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   433: aload 15
/*     */     //   435: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   438: bipush 41
/*     */     //   440: invokevirtual 208	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
/*     */     //   443: pop
/*     */     //   444: goto -238 -> 206
/*     */     //   447: aload 11
/*     */     //   449: invokeinterface 154 1 0
/*     */     //   454: aload_0
/*     */     //   455: aload 7
/*     */     //   457: invokespecial 116	com/hwacom/ngtms/toolbox/sync/MySqlCsvImport:changeStatementVar	(Ljava/sql/Statement;)V
/*     */     //   460: aload 7
/*     */     //   462: ldc -47
/*     */     //   464: invokeinterface 85 2 0
/*     */     //   469: pop
/*     */     //   470: new 58	java/lang/StringBuilder
/*     */     //   473: dup
/*     */     //   474: invokespecial 59	java/lang/StringBuilder:<init>	()V
/*     */     //   477: ldc -46
/*     */     //   479: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   482: aload_2
/*     */     //   483: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   486: ldc -45
/*     */     //   488: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   491: aload_1
/*     */     //   492: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   495: ldc -44
/*     */     //   497: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   500: aload 4
/*     */     //   502: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   505: ldc -43
/*     */     //   507: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   510: aload_3
/*     */     //   511: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   514: ldc -42
/*     */     //   516: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   519: aload 12
/*     */     //   521: invokevirtual 201	java/lang/StringBuilder:length	()I
/*     */     //   524: ifle +11 -> 535
/*     */     //   527: aload 12
/*     */     //   529: invokevirtual 61	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   532: goto +5 -> 537
/*     */     //   535: ldc -96
/*     */     //   537: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   540: invokevirtual 61	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   543: astore 13
/*     */     //   545: getstatic 7	com/hwacom/ngtms/toolbox/sync/MySqlCsvImport:logger	Lorg/slf4j/Logger;
/*     */     //   548: aload 13
/*     */     //   550: invokeinterface 215 2 0
/*     */     //   555: aload 7
/*     */     //   557: aload 13
/*     */     //   559: invokeinterface 85 2 0
/*     */     //   564: istore 14
/*     */     //   566: iload 14
/*     */     //   568: istore 15
/*     */     //   570: aload 7
/*     */     //   572: ifnull +37 -> 609
/*     */     //   575: aload 8
/*     */     //   577: ifnull +25 -> 602
/*     */     //   580: aload 7
/*     */     //   582: invokeinterface 124 1 0
/*     */     //   587: goto +22 -> 609
/*     */     //   590: astore 16
/*     */     //   592: aload 8
/*     */     //   594: aload 16
/*     */     //   596: invokevirtual 72	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*     */     //   599: goto +10 -> 609
/*     */     //   602: aload 7
/*     */     //   604: invokeinterface 124 1 0
/*     */     //   609: aload 5
/*     */     //   611: ifnull +33 -> 644
/*     */     //   614: aload 6
/*     */     //   616: ifnull +23 -> 639
/*     */     //   619: aload 5
/*     */     //   621: invokevirtual 70	java/io/BufferedReader:close	()V
/*     */     //   624: goto +20 -> 644
/*     */     //   627: astore 16
/*     */     //   629: aload 6
/*     */     //   631: aload 16
/*     */     //   633: invokevirtual 72	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*     */     //   636: goto +8 -> 644
/*     */     //   639: aload 5
/*     */     //   641: invokevirtual 70	java/io/BufferedReader:close	()V
/*     */     //   644: iload 15
/*     */     //   646: ireturn
/*     */     //   647: astore 9
/*     */     //   649: aload 9
/*     */     //   651: astore 8
/*     */     //   653: aload 9
/*     */     //   655: athrow
/*     */     //   656: astore 17
/*     */     //   658: aload 7
/*     */     //   660: ifnull +37 -> 697
/*     */     //   663: aload 8
/*     */     //   665: ifnull +25 -> 690
/*     */     //   668: aload 7
/*     */     //   670: invokeinterface 124 1 0
/*     */     //   675: goto +22 -> 697
/*     */     //   678: astore 18
/*     */     //   680: aload 8
/*     */     //   682: aload 18
/*     */     //   684: invokevirtual 72	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*     */     //   687: goto +10 -> 697
/*     */     //   690: aload 7
/*     */     //   692: invokeinterface 124 1 0
/*     */     //   697: aload 17
/*     */     //   699: athrow
/*     */     //   700: astore 7
/*     */     //   702: aload 7
/*     */     //   704: astore 6
/*     */     //   706: aload 7
/*     */     //   708: athrow
/*     */     //   709: astore 19
/*     */     //   711: aload 5
/*     */     //   713: ifnull +33 -> 746
/*     */     //   716: aload 6
/*     */     //   718: ifnull +23 -> 741
/*     */     //   721: aload 5
/*     */     //   723: invokevirtual 70	java/io/BufferedReader:close	()V
/*     */     //   726: goto +20 -> 746
/*     */     //   729: astore 20
/*     */     //   731: aload 6
/*     */     //   733: aload 20
/*     */     //   735: invokevirtual 72	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*     */     //   738: goto +8 -> 746
/*     */     //   741: aload 5
/*     */     //   743: invokevirtual 70	java/io/BufferedReader:close	()V
/*     */     //   746: aload 19
/*     */     //   748: athrow
/*     */     // Line number table:
/*     */     //   Java source line #359	-> byte code offset #0
/*     */     //   Java source line #361	-> byte code offset #9
/*     */     //   Java source line #362	-> byte code offset #16
/*     */     //   Java source line #364	-> byte code offset #45
/*     */     //   Java source line #362	-> byte code offset #56
/*     */     //   Java source line #365	-> byte code offset #59
/*     */     //   Java source line #366	-> byte code offset #65
/*     */     //   Java source line #412	-> byte code offset #72
/*     */     //   Java source line #367	-> byte code offset #149
/*     */     //   Java source line #368	-> byte code offset #153
/*     */     //   Java source line #369	-> byte code offset #162
/*     */     //   Java source line #371	-> byte code offset #168
/*     */     //   Java source line #373	-> byte code offset #173
/*     */     //   Java source line #374	-> byte code offset #184
/*     */     //   Java source line #375	-> byte code offset #197
/*     */     //   Java source line #377	-> byte code offset #206
/*     */     //   Java source line #378	-> byte code offset #216
/*     */     //   Java source line #379	-> byte code offset #227
/*     */     //   Java source line #380	-> byte code offset #237
/*     */     //   Java source line #381	-> byte code offset #247
/*     */     //   Java source line #382	-> byte code offset #274
/*     */     //   Java source line #383	-> byte code offset #284
/*     */     //   Java source line #384	-> byte code offset #303
/*     */     //   Java source line #385	-> byte code offset #311
/*     */     //   Java source line #386	-> byte code offset #334
/*     */     //   Java source line #387	-> byte code offset #347
/*     */     //   Java source line #388	-> byte code offset #357
/*     */     //   Java source line #389	-> byte code offset #384
/*     */     //   Java source line #390	-> byte code offset #394
/*     */     //   Java source line #391	-> byte code offset #413
/*     */     //   Java source line #392	-> byte code offset #421
/*     */     //   Java source line #394	-> byte code offset #444
/*     */     //   Java source line #395	-> byte code offset #447
/*     */     //   Java source line #396	-> byte code offset #454
/*     */     //   Java source line #397	-> byte code offset #460
/*     */     //   Java source line #398	-> byte code offset #470
/*     */     //   Java source line #408	-> byte code offset #521
/*     */     //   Java source line #409	-> byte code offset #545
/*     */     //   Java source line #410	-> byte code offset #555
/*     */     //   Java source line #411	-> byte code offset #566
/*     */     //   Java source line #412	-> byte code offset #570
/*     */     //   Java source line #362	-> byte code offset #647
/*     */     //   Java source line #412	-> byte code offset #656
/*     */     //   Java source line #362	-> byte code offset #700
/*     */     //   Java source line #412	-> byte code offset #709
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	749	0	this	MySqlCsvImport
/*     */     //   0	749	1	tableName	String
/*     */     //   0	749	2	filePath	String
/*     */     //   64	447	3	colLine	String
/*     */     //   14	487	4	lineSeparator	String
/*     */     //   40	702	5	in	BufferedReader
/*     */     //   43	689	6	localThrowable8	Throwable
/*     */     //   54	637	7	stmt	Statement
/*     */     //   700	7	7	localThrowable6	Throwable
/*     */     //   57	624	8	localThrowable9	Throwable
/*     */     //   70	77	9	i	int
/*     */     //   151	4	9	UTF8_BOM	String
/*     */     //   647	7	9	localThrowable4	Throwable
/*     */     //   647	7	9	localThrowable10	Throwable
/*     */     //   92	5	10	localThrowable	Throwable
/*     */     //   129	5	10	localThrowable1	Throwable
/*     */     //   182	3	10	meta	java.sql.DatabaseMetaData
/*     */     //   195	253	11	result	ResultSet
/*     */     //   204	324	12	convert	StringBuilder
/*     */     //   225	113	13	typeName	String
/*     */     //   543	15	13	sql	String
/*     */     //   245	69	14	colName	String
/*     */     //   355	69	14	colName	String
/*     */     //   564	3	14	n	int
/*     */     //   272	52	15	tranColName	String
/*     */     //   382	263	15	tranColName	String
/*     */     //   590	5	16	localThrowable2	Throwable
/*     */     //   627	5	16	localThrowable3	Throwable
/*     */     //   656	42	17	localObject1	Object
/*     */     //   678	5	18	localThrowable5	Throwable
/*     */     //   709	38	19	localObject2	Object
/*     */     //   729	5	20	localThrowable7	Throwable
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   82	89	92	java/lang/Throwable
/*     */     //   121	126	129	java/lang/Throwable
/*     */     //   580	587	590	java/lang/Throwable
/*     */     //   619	624	627	java/lang/Throwable
/*     */     //   59	72	647	java/lang/Throwable
/*     */     //   149	570	647	java/lang/Throwable
/*     */     //   59	72	656	finally
/*     */     //   149	570	656	finally
/*     */     //   647	658	656	finally
/*     */     //   668	675	678	java/lang/Throwable
/*     */     //   45	111	700	java/lang/Throwable
/*     */     //   149	609	700	java/lang/Throwable
/*     */     //   647	700	700	java/lang/Throwable
/*     */     //   45	111	709	finally
/*     */     //   149	609	709	finally
/*     */     //   647	711	709	finally
/*     */     //   721	726	729	java/lang/Throwable
/*     */   }
/*     */   
/*     */   private int importDataFromCsv7zWithHeader(String tableName, String filePath)
/*     */     throws SQLException, IOException
/*     */   {
/* 426 */     int rows = 0;
/* 427 */     final SevenZFile sevenZFile = new SevenZFile(new File(filePath));Throwable localThrowable7 = null;
/* 428 */     try { InputStream is = new InputStream()
/*     */       {
/*     */         public int read() throws IOException {
/* 431 */           return sevenZFile.read();
/*     */         }
/*     */         
/*     */         public int read(byte[] b) throws IOException {
/* 435 */           return sevenZFile.read(b);
/*     */         }
/*     */         
/*     */         public int read(byte[] b, int off, int len) throws IOException {
/* 439 */           return sevenZFile.read(b, off, len);
/*     */         }
/*     */       };
/*     */       
/*     */       SevenZArchiveEntry entry;
/* 444 */       while ((entry = sevenZFile.getNextEntry()) != null) {
/* 445 */         BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
/* 446 */         boolean finish = false;
/* 447 */         String colLine = null;
/* 448 */         int partCount = 0;
/* 449 */         while (!finish)
/*     */         {
/* 451 */           int size = 0;
/* 452 */           if (colLine == null) {
/* 453 */             colLine = br.readLine();
/* 454 */             if (colLine == null) break;
/*     */           }
/* 456 */           File tempFile = new File(this.workingDirPath + "temp-" + entry.getName());
/* 457 */           try { OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(tempFile), "UTF-8");Throwable localThrowable8 = null;
/*     */             try {
/* 459 */               osw.write(colLine);
/* 460 */               osw.write(10);
/* 461 */               String line; while ((line = br.readLine()) != null) {
/* 462 */                 osw.write(line);
/* 463 */                 osw.write(10);
/* 464 */                 size += line.length();
/* 465 */                 if (size > MAX_TEMP_FILE_SIZE) break;
/*     */               }
/* 467 */               osw.close();
/* 468 */               if (size > 0) {
/* 469 */                 int r = importDataFromCsvWithHeader(tableName, tempFile.getAbsolutePath());
/* 470 */                 rows += r;
/* 471 */                 partCount++;
/* 472 */                 logger.info("Load data to table:{} from csv: {}, part {}, rows {}", new Object[] { tableName, tempFile
/*     */                 
/*     */ 
/* 475 */                   .getAbsolutePath(), 
/* 476 */                   Integer.valueOf(partCount), 
/* 477 */                   Integer.valueOf(r) });
/*     */               }
/* 479 */               if (line == null) {
/* 480 */                 if (osw != null) if (localThrowable8 != null) try { osw.close(); } catch (Throwable localThrowable) { localThrowable8.addSuppressed(localThrowable); } else osw.close();
/* 481 */                 tempFile.delete(); break;
/*     */               }
/*     */             }
/*     */             catch (Throwable localThrowable2)
/*     */             {
/* 457 */               localThrowable8 = localThrowable2;throw localThrowable2;
/*     */             }
/*     */             finally {}
/*     */           }
/*     */           finally
/*     */           {
/*     */             String line;
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
/* 481 */             tempFile.delete();
/*     */           }
/*     */         }
/* 484 */         br.close();
/*     */       }
/*     */     }
/*     */     catch (Throwable localThrowable5)
/*     */     {
/* 427 */       localThrowable7 = localThrowable5;throw localThrowable5;
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
/* 486 */       if (sevenZFile != null) if (localThrowable7 != null) try { sevenZFile.close(); } catch (Throwable localThrowable6) { localThrowable7.addSuppressed(localThrowable6); } else sevenZFile.close(); }
/* 487 */     return rows;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private LocalDate ofDate(String dateStr)
/*     */   {
/* 497 */     int year = Integer.parseInt(dateStr.substring(0, 4));
/* 498 */     int month = Integer.parseInt(dateStr.substring(4, 6));
/* 499 */     int dayOfMonth = Integer.parseInt(dateStr.substring(6, 8));
/* 500 */     return LocalDate.of(year, month, dayOfMonth);
/*     */   }
/*     */   
/*     */   private void reorganizePartition(String tableName, String systemExitPartitionName, String endDate) throws SQLException, IOException
/*     */   {
/* 505 */     Statement stmt = this.connection.createStatement();Throwable localThrowable3 = null;
/* 506 */     try { changeStatementVar(stmt);
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
/* 518 */       StringBuffer sb = new StringBuffer("alter table ").append(tableName).append(" reorganize partition ").append(systemExitPartitionName).append(" into ( ");
/*     */       
/* 520 */       LocalDate enddt = ofDate(endDate);
/*     */       
/* 522 */       LocalDate dt = ofDate(systemExitPartitionName.substring(1, systemExitPartitionName.length()));
/* 523 */       while ((dt.isAfter(enddt)) || (dt.isEqual(enddt))) {
/* 524 */         String partitionDate = enddt.format(this.formatter1);
/*     */         
/* 526 */         LocalDate yestoday = enddt.minusDays(1L);
/* 527 */         String partitionName = "p" + yestoday.format(this.formatter2);
/*     */         
/* 529 */         sb.append("\r\n partition ")
/* 530 */           .append(partitionName)
/* 531 */           .append(" values less than ('")
/* 532 */           .append(partitionDate)
/* 533 */           .append("'),");
/* 534 */         enddt = enddt.plusDays(1L);
/*     */       }
/* 536 */       dt = dt.plusDays(1L);
/* 537 */       sb.append("\r\n partition ")
/* 538 */         .append(systemExitPartitionName)
/* 539 */         .append(" values less than ('")
/* 540 */         .append(dt.format(this.formatter1))
/* 541 */         .append("'),");
/*     */       
/* 543 */       String sql = sb.toString().substring(0, sb.toString().length() - 1) + ")";
/* 544 */       logger.info("partition sql:{}", sql);
/* 545 */       stmt.executeUpdate(sql);
/*     */     }
/*     */     catch (Throwable localThrowable1)
/*     */     {
/* 505 */       localThrowable3 = localThrowable1;throw localThrowable1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 546 */       if (stmt != null) if (localThrowable3 != null) try { stmt.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else stmt.close();
/*     */     }
/*     */   }
/*     */   
/*     */   static class PartitionInfo implements Comparable<PartitionInfo> {
/*     */     String name;
/*     */     String description;
/*     */     
/* 554 */     public PartitionInfo(String name, String description) { this.name = name;
/* 555 */       this.description = description;
/*     */     }
/*     */     
/*     */     public int compareTo(PartitionInfo o)
/*     */     {
/* 560 */       return this.name.compareTo(o.name);
/*     */     }
/*     */     
/*     */     public int hashCode()
/*     */     {
/* 565 */       int prime = 31;
/* 566 */       int result = 1;
/* 567 */       result = 31 * result + (this.description == null ? 0 : this.description.hashCode());
/* 568 */       result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
/* 569 */       return result;
/*     */     }
/*     */     
/*     */     public boolean equals(Object obj)
/*     */     {
/* 574 */       if (this == obj) return true;
/* 575 */       if (obj == null) return false;
/* 576 */       if (getClass() != obj.getClass()) return false;
/* 577 */       PartitionInfo other = (PartitionInfo)obj;
/* 578 */       if (this.description == null) {
/* 579 */         if (other.description != null) return false;
/* 580 */       } else if (!this.description.equals(other.description)) return false;
/* 581 */       if (this.name == null) {
/* 582 */         if (other.name != null) return false;
/* 583 */       } else if (!this.name.equals(other.name)) return false;
/* 584 */       return true;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 589 */       return "PartitionInfo [name=" + this.name + ", description=" + this.description + "]";
/*     */     }
/*     */   }
/*     */   
/*     */   private String getLineSeparator(String filePath) {
/* 594 */     InputStreamReader isr = null;
/*     */     try {
/* 596 */       isr = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
/* 597 */       char[] buffer = new char['Ѐ'];
/* 598 */       int len = isr.read(buffer);
/* 599 */       String s = String.valueOf(buffer, 0, len);
/* 600 */       Matcher matcher = this.lineSeparatorPattern.matcher(s);
/* 601 */       if (matcher.find()) {
/* 602 */         return matcher.group(0);
/*     */       }
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
/* 614 */       return "\\n";
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 605 */       logger.error("Failed to detect line separator", ex);
/*     */     } finally {
/* 607 */       if (isr != null) {
/*     */         try {
/* 609 */           isr.close();
/*     */         }
/*     */         catch (IOException localIOException3) {}
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\toolbox-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\toolbox\sync\MySqlCsvImport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */