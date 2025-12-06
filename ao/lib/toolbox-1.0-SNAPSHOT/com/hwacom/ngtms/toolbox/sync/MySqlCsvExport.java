/*     */ package com.hwacom.ngtms.toolbox.sync;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.time.LocalDate;
/*     */ import java.time.ZoneId;
/*     */ import java.time.ZonedDateTime;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MySqlCsvExport
/*     */ {
/*  48 */   private static Logger logger = LoggerFactory.getLogger(MySqlCsvExport.class);
/*     */   
/*  50 */   private static String PROP_FILE_NAME = "configExport.properties";
/*  51 */   private static boolean CHANGE_MYSQL_VAR = true;
/*  52 */   private static DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
/*  53 */   private static DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMdd");
/*     */   
/*  55 */   private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*  56 */   private SimpleDateFormat fileDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
/*     */   
/*     */   private Connection connection;
/*     */   private String workingRecFile;
/*     */   private LocalDate processingDate;
/*  61 */   private List<String> tableFilter = Collections.EMPTY_LIST;
/*     */   
/*     */ 
/*  64 */   private AtomicInteger extractDataNo = new AtomicInteger();
/*  65 */   private AtomicLong extractRowNo = new AtomicLong();
/*  66 */   private CompositeConfiguration config = new CompositeConfiguration();
/*  67 */   private boolean isExportAll = false;
/*     */   
/*     */   public static void main(String[] args) {
/*  70 */     MySqlCsvExport obj = new MySqlCsvExport(args);
/*     */     try {
/*  72 */       obj.init();
/*  73 */       obj.process();
/*     */     } catch (Exception e) {
/*  75 */       logger.error(e.getMessage(), e);
/*     */     } finally {
/*  77 */       obj.close();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public MySqlCsvExport(String[] args)
/*     */   {
/*  84 */     if (args.length >= 1) {
/*  85 */       PROP_FILE_NAME = args[0];
/*     */     } else {
/*  87 */       logger.debug("確認 configExport.properties 載入是否正確!!");
/*     */     }
/*     */   }
/*     */   
/*     */   public void init() throws Exception {
/*  92 */     PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
/*  93 */     propertiesConfiguration.setEncoding("UTF-8");
/*  94 */     propertiesConfiguration.load(PROP_FILE_NAME);
/*     */     
/*  96 */     this.config.addConfiguration(propertiesConfiguration);
/*     */     
/*  98 */     String driver = this.config.getString("driver");
/*  99 */     String url = this.config.getString("url");
/* 100 */     String user = this.config.getString("user");
/* 101 */     String password = this.config.getString("password");
/* 102 */     Class.forName(driver);
/* 103 */     this.connection = DriverManager.getConnection(url, user, password);
/*     */     
/* 105 */     this.workingRecFile = this.config.getString("workingRecFile", "exportRec.txt");
/* 106 */     File f = new File(this.workingRecFile);
/* 107 */     if ((f.isDirectory()) || (!f.exists())) {
/* 108 */       throw new Exception("workingRecFile 設定不正確!");
/*     */     }
/*     */     
/* 111 */     BufferedReader br = new BufferedReader(new FileReader(f));Throwable localThrowable3 = null;
/* 112 */     try { this.processingDate = ofDate(br.readLine().trim());
/*     */     }
/*     */     catch (Throwable localThrowable1)
/*     */     {
/* 111 */       localThrowable3 = localThrowable1;throw localThrowable1;
/*     */     } finally {
/* 113 */       if (br != null) if (localThrowable3 != null) try { br.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else br.close();
/*     */     }
/* 115 */     LocalDate now = getLocalDate();
/* 116 */     if (now.isBefore(this.processingDate)) {
/* 117 */       throw new Exception("資料已處理到:" + this.processingDate);
/*     */     }
/*     */     
/* 120 */     String[] tf = this.config.getStringArray("tableFilter");
/* 121 */     if ((tf != null) && (tf.length > 0)) {
/* 122 */       this.tableFilter = Arrays.asList(tf);
/*     */     }
/*     */     
/* 125 */     String exportAll = this.config.getString("exportAll");
/* 126 */     if ((exportAll != null) && (exportAll.trim().equalsIgnoreCase("true"))) {
/* 127 */       this.isExportAll = true;
/*     */     }
/*     */   }
/*     */   
/*     */   private void changeStatementVar(Statement stmt) throws SQLException {
/* 132 */     if (CHANGE_MYSQL_VAR)
/*     */     {
/* 134 */       stmt.executeUpdate("SET SESSION sql_log_bin=0");
/*     */       
/* 136 */       stmt.executeUpdate("set global wait_timeout=2880000000");
/*     */       
/* 138 */       stmt.executeUpdate("set global interactive_timeout=604800");
/*     */     }
/*     */   }
/*     */   
/*     */   private LocalDate getLocalDate() {
/* 143 */     String minusDaysStr = this.config.getString("minusDays");
/* 144 */     int minusDays = 7;
/*     */     try {
/* 146 */       minusDays = Integer.parseInt(minusDaysStr);
/*     */     } catch (Exception e) {
/* 148 */       minusDays = 14;
/*     */     }
/* 150 */     LocalDate now = LocalDate.now().minusDays(minusDays);
/* 151 */     return now;
/*     */   }
/*     */   
/*     */   public void process() throws Exception {
/* 155 */     LocalDate now = getLocalDate();
/* 156 */     List<TableMeta> tms = getWantExportTable();
/* 157 */     while (now.isAfter(this.processingDate)) {
/* 158 */       TableMeta tableMeta = null;
/*     */       try {
/* 160 */         this.processingDate = this.processingDate.plusDays(1L);
/* 161 */         for (int i = 0; i < tms.size(); i++) {
/* 162 */           tableMeta = (TableMeta)tms.get(i);
/* 163 */           tableMeta.startTime = this.processingDate;
/* 164 */           getSqlCommand(tableMeta);
/* 165 */           extractBackupData(tableMeta);
/*     */         }
/*     */         
/* 168 */         BufferedWriter bw = new BufferedWriter(new FileWriter(new File(this.workingRecFile)));Throwable localThrowable3 = null;
/* 169 */         try { bw.write(this.processingDate.format(formatter2));
/* 170 */           bw.flush();
/*     */         }
/*     */         catch (Throwable localThrowable1)
/*     */         {
/* 168 */           localThrowable3 = localThrowable1;throw localThrowable1;
/*     */         }
/*     */         finally {
/* 171 */           if (bw != null) if (localThrowable3 != null) try { bw.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else bw.close();
/*     */         }
/* 173 */       } catch (Exception e) { logger.error("Failed to process...table:" + (tableMeta != null ? tableMeta.tableName : ""), e);
/*     */       }
/*     */     }
/*     */     
/* 177 */     logger.info("匯出資料檔案個數 {}", Integer.valueOf(this.extractDataNo.get()));
/* 178 */     logger.info("匯出資料筆數 {}", Long.valueOf(this.extractRowNo.get()));
/*     */   }
/*     */   
/*     */   public void close()
/*     */   {
/*     */     try {
/* 184 */       if (this.connection != null) this.connection.close();
/*     */     } catch (SQLException e) {
/* 186 */       logger.info("Failed when closing connection", e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private LocalDate ofDate(String dateStr)
/*     */   {
/* 197 */     int year = Integer.parseInt(dateStr.substring(0, 4));
/* 198 */     int month = Integer.parseInt(dateStr.substring(4, 6));
/* 199 */     int dayOfMonth = Integer.parseInt(dateStr.substring(6, 8));
/* 200 */     return LocalDate.of(year, month, dayOfMonth);
/*     */   }
/*     */   
/*     */   private List<TableMeta> getWantExportTable() throws SQLException {
/* 204 */     Statement stmt = this.connection.createStatement();Throwable localThrowable3 = null;
/* 205 */     try { changeStatementVar(stmt);
/*     */       
/* 207 */       ResultSet rs = stmt.executeQuery("select TABLE_NAME, PARTITION_EXPRESSION from information_schema.PARTITIONS WHERE TABLE_SCHEMA = 'ngtms' and PARTITION_NAME is not null group by TABLE_NAME");
/*     */       
/* 209 */       List<TableMeta> values = new ArrayList();
/* 210 */       TableMeta obj; while (rs.next()) {
/* 211 */         obj = new TableMeta();
/* 212 */         obj.tableName = rs.getString(1);
/* 213 */         obj.pKey = rs.getString(2).replace("`", "");
/* 214 */         int index = obj.tableName.indexOf("_");
/* 215 */         if (index != -1) {
/* 216 */           obj.moduleName = obj.tableName.substring(0, index);
/*     */         }
/* 218 */         if (this.isExportAll) {
/* 219 */           values.add(obj);
/* 220 */         } else if (this.tableFilter.contains(obj.tableName)) {
/* 221 */           values.add(obj);
/*     */         } else {
/* 223 */           logger.debug("table:" + obj.tableName + " 不在匯出清單裡...");
/*     */         }
/*     */       }
/* 226 */       return values;
/*     */     }
/*     */     catch (Throwable localThrowable1)
/*     */     {
/* 204 */       localThrowable3 = localThrowable1;throw localThrowable1;
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
/* 227 */       if (stmt != null) if (localThrowable3 != null) try { stmt.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else stmt.close();
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   private int extractDataToCsv7z(String sqlCommand, String outFilePath, String entryName)
/*     */     throws SQLException, IOException
/*     */   {
/*     */     // Byte code:
/*     */     //   0: new 50	java/io/File
/*     */     //   3: dup
/*     */     //   4: aload_2
/*     */     //   5: invokespecial 51	java/io/File:<init>	(Ljava/lang/String;)V
/*     */     //   8: astore 4
/*     */     //   10: aload_0
/*     */     //   11: getfield 45	com/hwacom/ngtms/toolbox/sync/MySqlCsvExport:connection	Ljava/sql/Connection;
/*     */     //   14: invokeinterface 124 1 0
/*     */     //   19: astore 5
/*     */     //   21: aconst_null
/*     */     //   22: astore 6
/*     */     //   24: new 144	org/apache/commons/compress/archivers/sevenz/SevenZOutputFile
/*     */     //   27: dup
/*     */     //   28: aload 4
/*     */     //   30: invokespecial 145	org/apache/commons/compress/archivers/sevenz/SevenZOutputFile:<init>	(Ljava/io/File;)V
/*     */     //   33: astore 7
/*     */     //   35: aconst_null
/*     */     //   36: astore 8
/*     */     //   38: aload_0
/*     */     //   39: aload 5
/*     */     //   41: invokespecial 125	com/hwacom/ngtms/toolbox/sync/MySqlCsvExport:changeStatementVar	(Ljava/sql/Statement;)V
/*     */     //   44: aload 7
/*     */     //   46: new 146	org/apache/commons/compress/archivers/sevenz/SevenZMethodConfiguration
/*     */     //   49: dup
/*     */     //   50: getstatic 147	org/apache/commons/compress/archivers/sevenz/SevenZMethod:LZMA2	Lorg/apache/commons/compress/archivers/sevenz/SevenZMethod;
/*     */     //   53: new 148	org/tukaani/xz/LZMA2Options
/*     */     //   56: dup
/*     */     //   57: iconst_3
/*     */     //   58: invokespecial 149	org/tukaani/xz/LZMA2Options:<init>	(I)V
/*     */     //   61: invokespecial 150	org/apache/commons/compress/archivers/sevenz/SevenZMethodConfiguration:<init>	(Lorg/apache/commons/compress/archivers/sevenz/SevenZMethod;Ljava/lang/Object;)V
/*     */     //   64: invokestatic 151	java/util/Collections:singletonList	(Ljava/lang/Object;)Ljava/util/List;
/*     */     //   67: invokevirtual 152	org/apache/commons/compress/archivers/sevenz/SevenZOutputFile:setContentMethods	(Ljava/lang/Iterable;)V
/*     */     //   70: aload 7
/*     */     //   72: aload 4
/*     */     //   74: aload_3
/*     */     //   75: invokevirtual 153	org/apache/commons/compress/archivers/sevenz/SevenZOutputFile:createArchiveEntry	(Ljava/io/File;Ljava/lang/String;)Lorg/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry;
/*     */     //   78: astore 9
/*     */     //   80: aload 7
/*     */     //   82: aload 9
/*     */     //   84: invokevirtual 154	org/apache/commons/compress/archivers/sevenz/SevenZOutputFile:putArchiveEntry	(Lorg/apache/commons/compress/archivers/ArchiveEntry;)V
/*     */     //   87: new 155	com/hwacom/ngtms/toolbox/sync/MySqlCsvExport$1
/*     */     //   90: dup
/*     */     //   91: aload_0
/*     */     //   92: aload 7
/*     */     //   94: invokespecial 156	com/hwacom/ngtms/toolbox/sync/MySqlCsvExport$1:<init>	(Lcom/hwacom/ngtms/toolbox/sync/MySqlCsvExport;Lorg/apache/commons/compress/archivers/sevenz/SevenZOutputFile;)V
/*     */     //   97: astore 10
/*     */     //   99: new 99	java/io/BufferedWriter
/*     */     //   102: dup
/*     */     //   103: new 157	java/io/OutputStreamWriter
/*     */     //   106: dup
/*     */     //   107: aload 10
/*     */     //   109: ldc 34
/*     */     //   111: invokespecial 158	java/io/OutputStreamWriter:<init>	(Ljava/io/OutputStream;Ljava/lang/String;)V
/*     */     //   114: invokespecial 102	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
/*     */     //   117: astore 11
/*     */     //   119: iconst_0
/*     */     //   120: istore 12
/*     */     //   122: aload 5
/*     */     //   124: aload_1
/*     */     //   125: invokeinterface 127 2 0
/*     */     //   130: astore 13
/*     */     //   132: aload 13
/*     */     //   134: invokeinterface 159 1 0
/*     */     //   139: astore 14
/*     */     //   141: aload 14
/*     */     //   143: invokeinterface 160 1 0
/*     */     //   148: istore 15
/*     */     //   150: iconst_1
/*     */     //   151: istore 16
/*     */     //   153: iload 16
/*     */     //   155: iload 15
/*     */     //   157: if_icmpgt +36 -> 193
/*     */     //   160: iload 16
/*     */     //   162: iconst_1
/*     */     //   163: if_icmpeq +10 -> 173
/*     */     //   166: aload 11
/*     */     //   168: ldc -95
/*     */     //   170: invokevirtual 105	java/io/BufferedWriter:write	(Ljava/lang/String;)V
/*     */     //   173: aload 11
/*     */     //   175: aload 14
/*     */     //   177: iload 16
/*     */     //   179: invokeinterface 162 2 0
/*     */     //   184: invokevirtual 105	java/io/BufferedWriter:write	(Ljava/lang/String;)V
/*     */     //   187: iinc 16 1
/*     */     //   190: goto -37 -> 153
/*     */     //   193: aload 11
/*     */     //   195: invokevirtual 163	java/io/BufferedWriter:newLine	()V
/*     */     //   198: aload 13
/*     */     //   200: invokeinterface 130 1 0
/*     */     //   205: ifeq +233 -> 438
/*     */     //   208: iconst_1
/*     */     //   209: istore 16
/*     */     //   211: iload 16
/*     */     //   213: iload 15
/*     */     //   215: iconst_1
/*     */     //   216: iadd
/*     */     //   217: if_icmpge +210 -> 427
/*     */     //   220: iload 16
/*     */     //   222: iconst_1
/*     */     //   223: if_icmpeq +10 -> 233
/*     */     //   226: aload 11
/*     */     //   228: ldc -95
/*     */     //   230: invokevirtual 105	java/io/BufferedWriter:write	(Ljava/lang/String;)V
/*     */     //   233: aload 13
/*     */     //   235: iload 16
/*     */     //   237: invokeinterface 164 2 0
/*     */     //   242: astore 17
/*     */     //   244: aload 17
/*     */     //   246: ifnonnull +13 -> 259
/*     */     //   249: aload 11
/*     */     //   251: ldc -91
/*     */     //   253: invokevirtual 105	java/io/BufferedWriter:write	(Ljava/lang/String;)V
/*     */     //   256: goto +165 -> 421
/*     */     //   259: aload 17
/*     */     //   261: instanceof 166
/*     */     //   264: ifeq +50 -> 314
/*     */     //   267: aload 17
/*     */     //   269: checkcast 166	java/lang/String
/*     */     //   272: astore 18
/*     */     //   274: aload 11
/*     */     //   276: new 69	java/lang/StringBuilder
/*     */     //   279: dup
/*     */     //   280: invokespecial 70	java/lang/StringBuilder:<init>	()V
/*     */     //   283: ldc -89
/*     */     //   285: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   288: aload 18
/*     */     //   290: ldc -88
/*     */     //   292: ldc -87
/*     */     //   294: invokevirtual 170	java/lang/String:replaceAll	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
/*     */     //   297: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   300: ldc -89
/*     */     //   302: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   305: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   308: invokevirtual 105	java/io/BufferedWriter:write	(Ljava/lang/String;)V
/*     */     //   311: goto +110 -> 421
/*     */     //   314: aload 17
/*     */     //   316: instanceof 171
/*     */     //   319: ifeq +43 -> 362
/*     */     //   322: aload 11
/*     */     //   324: new 69	java/lang/StringBuilder
/*     */     //   327: dup
/*     */     //   328: invokespecial 70	java/lang/StringBuilder:<init>	()V
/*     */     //   331: ldc -89
/*     */     //   333: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   336: aload_0
/*     */     //   337: getfield 14	com/hwacom/ngtms/toolbox/sync/MySqlCsvExport:dateTimeFormat	Ljava/text/SimpleDateFormat;
/*     */     //   340: aload 17
/*     */     //   342: invokevirtual 172	java/text/SimpleDateFormat:format	(Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   345: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   348: ldc -89
/*     */     //   350: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   353: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   356: invokevirtual 105	java/io/BufferedWriter:write	(Ljava/lang/String;)V
/*     */     //   359: goto +62 -> 421
/*     */     //   362: aload 17
/*     */     //   364: instanceof 173
/*     */     //   367: ifeq +44 -> 411
/*     */     //   370: aload 17
/*     */     //   372: checkcast 173	java/sql/Blob
/*     */     //   375: astore 18
/*     */     //   377: aload 18
/*     */     //   379: lconst_1
/*     */     //   380: aload 18
/*     */     //   382: invokeinterface 174 1 0
/*     */     //   387: l2i
/*     */     //   388: invokeinterface 175 4 0
/*     */     //   393: astore 19
/*     */     //   395: aload 11
/*     */     //   397: invokestatic 176	java/util/Base64:getEncoder	()Ljava/util/Base64$Encoder;
/*     */     //   400: aload 19
/*     */     //   402: invokevirtual 177	java/util/Base64$Encoder:encodeToString	([B)Ljava/lang/String;
/*     */     //   405: invokevirtual 105	java/io/BufferedWriter:write	(Ljava/lang/String;)V
/*     */     //   408: goto +13 -> 421
/*     */     //   411: aload 11
/*     */     //   413: aload 17
/*     */     //   415: invokevirtual 178	java/lang/Object:toString	()Ljava/lang/String;
/*     */     //   418: invokevirtual 105	java/io/BufferedWriter:write	(Ljava/lang/String;)V
/*     */     //   421: iinc 16 1
/*     */     //   424: goto -213 -> 211
/*     */     //   427: aload 11
/*     */     //   429: invokevirtual 163	java/io/BufferedWriter:newLine	()V
/*     */     //   432: iinc 12 1
/*     */     //   435: goto -237 -> 198
/*     */     //   438: aload 11
/*     */     //   440: invokevirtual 107	java/io/BufferedWriter:close	()V
/*     */     //   443: aload 13
/*     */     //   445: invokeinterface 179 1 0
/*     */     //   450: iload 12
/*     */     //   452: istore 16
/*     */     //   454: aload 7
/*     */     //   456: ifnull +33 -> 489
/*     */     //   459: aload 8
/*     */     //   461: ifnull +23 -> 484
/*     */     //   464: aload 7
/*     */     //   466: invokevirtual 180	org/apache/commons/compress/archivers/sevenz/SevenZOutputFile:close	()V
/*     */     //   469: goto +20 -> 489
/*     */     //   472: astore 17
/*     */     //   474: aload 8
/*     */     //   476: aload 17
/*     */     //   478: invokevirtual 66	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*     */     //   481: goto +8 -> 489
/*     */     //   484: aload 7
/*     */     //   486: invokevirtual 180	org/apache/commons/compress/archivers/sevenz/SevenZOutputFile:close	()V
/*     */     //   489: aload 5
/*     */     //   491: ifnull +37 -> 528
/*     */     //   494: aload 6
/*     */     //   496: ifnull +25 -> 521
/*     */     //   499: aload 5
/*     */     //   501: invokeinterface 143 1 0
/*     */     //   506: goto +22 -> 528
/*     */     //   509: astore 17
/*     */     //   511: aload 6
/*     */     //   513: aload 17
/*     */     //   515: invokevirtual 66	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*     */     //   518: goto +10 -> 528
/*     */     //   521: aload 5
/*     */     //   523: invokeinterface 143 1 0
/*     */     //   528: iload 16
/*     */     //   530: ireturn
/*     */     //   531: astore 9
/*     */     //   533: aload 9
/*     */     //   535: astore 8
/*     */     //   537: aload 9
/*     */     //   539: athrow
/*     */     //   540: astore 20
/*     */     //   542: aload 7
/*     */     //   544: ifnull +33 -> 577
/*     */     //   547: aload 8
/*     */     //   549: ifnull +23 -> 572
/*     */     //   552: aload 7
/*     */     //   554: invokevirtual 180	org/apache/commons/compress/archivers/sevenz/SevenZOutputFile:close	()V
/*     */     //   557: goto +20 -> 577
/*     */     //   560: astore 21
/*     */     //   562: aload 8
/*     */     //   564: aload 21
/*     */     //   566: invokevirtual 66	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*     */     //   569: goto +8 -> 577
/*     */     //   572: aload 7
/*     */     //   574: invokevirtual 180	org/apache/commons/compress/archivers/sevenz/SevenZOutputFile:close	()V
/*     */     //   577: aload 20
/*     */     //   579: athrow
/*     */     //   580: astore 7
/*     */     //   582: aload 7
/*     */     //   584: astore 6
/*     */     //   586: aload 7
/*     */     //   588: athrow
/*     */     //   589: astore 22
/*     */     //   591: aload 5
/*     */     //   593: ifnull +37 -> 630
/*     */     //   596: aload 6
/*     */     //   598: ifnull +25 -> 623
/*     */     //   601: aload 5
/*     */     //   603: invokeinterface 143 1 0
/*     */     //   608: goto +22 -> 630
/*     */     //   611: astore 23
/*     */     //   613: aload 6
/*     */     //   615: aload 23
/*     */     //   617: invokevirtual 66	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*     */     //   620: goto +10 -> 630
/*     */     //   623: aload 5
/*     */     //   625: invokeinterface 143 1 0
/*     */     //   630: aload 22
/*     */     //   632: athrow
/*     */     // Line number table:
/*     */     //   Java source line #242	-> byte code offset #0
/*     */     //   Java source line #243	-> byte code offset #10
/*     */     //   Java source line #244	-> byte code offset #24
/*     */     //   Java source line #243	-> byte code offset #35
/*     */     //   Java source line #245	-> byte code offset #38
/*     */     //   Java source line #248	-> byte code offset #44
/*     */     //   Java source line #249	-> byte code offset #64
/*     */     //   Java source line #248	-> byte code offset #67
/*     */     //   Java source line #251	-> byte code offset #70
/*     */     //   Java source line #252	-> byte code offset #80
/*     */     //   Java source line #253	-> byte code offset #87
/*     */     //   Java source line #275	-> byte code offset #99
/*     */     //   Java source line #276	-> byte code offset #119
/*     */     //   Java source line #277	-> byte code offset #122
/*     */     //   Java source line #278	-> byte code offset #132
/*     */     //   Java source line #279	-> byte code offset #141
/*     */     //   Java source line #282	-> byte code offset #150
/*     */     //   Java source line #283	-> byte code offset #160
/*     */     //   Java source line #284	-> byte code offset #173
/*     */     //   Java source line #282	-> byte code offset #187
/*     */     //   Java source line #286	-> byte code offset #193
/*     */     //   Java source line #288	-> byte code offset #198
/*     */     //   Java source line #289	-> byte code offset #208
/*     */     //   Java source line #290	-> byte code offset #220
/*     */     //   Java source line #291	-> byte code offset #233
/*     */     //   Java source line #292	-> byte code offset #244
/*     */     //   Java source line #294	-> byte code offset #259
/*     */     //   Java source line #295	-> byte code offset #267
/*     */     //   Java source line #296	-> byte code offset #274
/*     */     //   Java source line #297	-> byte code offset #311
/*     */     //   Java source line #298	-> byte code offset #322
/*     */     //   Java source line #299	-> byte code offset #362
/*     */     //   Java source line #300	-> byte code offset #370
/*     */     //   Java source line #301	-> byte code offset #377
/*     */     //   Java source line #302	-> byte code offset #395
/*     */     //   Java source line #303	-> byte code offset #408
/*     */     //   Java source line #289	-> byte code offset #421
/*     */     //   Java source line #306	-> byte code offset #427
/*     */     //   Java source line #307	-> byte code offset #432
/*     */     //   Java source line #309	-> byte code offset #438
/*     */     //   Java source line #310	-> byte code offset #443
/*     */     //   Java source line #311	-> byte code offset #450
/*     */     //   Java source line #312	-> byte code offset #454
/*     */     //   Java source line #243	-> byte code offset #531
/*     */     //   Java source line #312	-> byte code offset #540
/*     */     //   Java source line #243	-> byte code offset #580
/*     */     //   Java source line #312	-> byte code offset #589
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	633	0	this	MySqlCsvExport
/*     */     //   0	633	1	sqlCommand	String
/*     */     //   0	633	2	outFilePath	String
/*     */     //   0	633	3	entryName	String
/*     */     //   8	65	4	outFile	File
/*     */     //   19	605	5	stmt	Statement
/*     */     //   22	592	6	localThrowable6	Throwable
/*     */     //   33	540	7	sevenZOutput	SevenZOutputFile
/*     */     //   580	7	7	localThrowable4	Throwable
/*     */     //   36	527	8	localThrowable7	Throwable
/*     */     //   78	5	9	entry	org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry
/*     */     //   531	7	9	localThrowable2	Throwable
/*     */     //   97	11	10	os	OutputStream
/*     */     //   117	322	11	bw	BufferedWriter
/*     */     //   120	331	12	count	int
/*     */     //   130	314	13	rs	ResultSet
/*     */     //   139	37	14	rsmd	java.sql.ResultSetMetaData
/*     */     //   148	66	15	columnCount	int
/*     */     //   151	37	16	i	int
/*     */     //   209	320	16	i	int
/*     */     //   242	172	17	column	Object
/*     */     //   472	5	17	localThrowable	Throwable
/*     */     //   509	5	17	localThrowable1	Throwable
/*     */     //   272	17	18	s	String
/*     */     //   375	6	18	blob	java.sql.Blob
/*     */     //   393	8	19	bytes	byte[]
/*     */     //   540	38	20	localObject1	Object
/*     */     //   560	5	21	localThrowable3	Throwable
/*     */     //   589	42	22	localObject2	Object
/*     */     //   611	5	23	localThrowable5	Throwable
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   464	469	472	java/lang/Throwable
/*     */     //   499	506	509	java/lang/Throwable
/*     */     //   38	454	531	java/lang/Throwable
/*     */     //   38	454	540	finally
/*     */     //   531	542	540	finally
/*     */     //   552	557	560	java/lang/Throwable
/*     */     //   24	489	580	java/lang/Throwable
/*     */     //   531	580	580	java/lang/Throwable
/*     */     //   24	489	589	finally
/*     */     //   531	591	589	finally
/*     */     //   601	608	611	java/lang/Throwable
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   private int extractDataToCsv(String sqlCommand, String outFile)
/*     */     throws SQLException, IOException
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 45	com/hwacom/ngtms/toolbox/sync/MySqlCsvExport:connection	Ljava/sql/Connection;
/*     */     //   4: invokeinterface 124 1 0
/*     */     //   9: astore_3
/*     */     //   10: aconst_null
/*     */     //   11: astore 4
/*     */     //   13: new 99	java/io/BufferedWriter
/*     */     //   16: dup
/*     */     //   17: new 157	java/io/OutputStreamWriter
/*     */     //   20: dup
/*     */     //   21: new 181	java/io/FileOutputStream
/*     */     //   24: dup
/*     */     //   25: aload_2
/*     */     //   26: invokespecial 182	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
/*     */     //   29: ldc 34
/*     */     //   31: invokespecial 158	java/io/OutputStreamWriter:<init>	(Ljava/io/OutputStream;Ljava/lang/String;)V
/*     */     //   34: invokespecial 102	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
/*     */     //   37: astore 5
/*     */     //   39: aconst_null
/*     */     //   40: astore 6
/*     */     //   42: aload_0
/*     */     //   43: aload_3
/*     */     //   44: invokespecial 125	com/hwacom/ngtms/toolbox/sync/MySqlCsvExport:changeStatementVar	(Ljava/sql/Statement;)V
/*     */     //   47: iconst_0
/*     */     //   48: istore 7
/*     */     //   50: aload_3
/*     */     //   51: aload_1
/*     */     //   52: invokeinterface 127 2 0
/*     */     //   57: astore 8
/*     */     //   59: aload 8
/*     */     //   61: invokeinterface 159 1 0
/*     */     //   66: astore 9
/*     */     //   68: aload 9
/*     */     //   70: invokeinterface 160 1 0
/*     */     //   75: istore 10
/*     */     //   77: iconst_1
/*     */     //   78: istore 11
/*     */     //   80: iload 11
/*     */     //   82: iload 10
/*     */     //   84: if_icmpgt +36 -> 120
/*     */     //   87: iload 11
/*     */     //   89: iconst_1
/*     */     //   90: if_icmpeq +10 -> 100
/*     */     //   93: aload 5
/*     */     //   95: ldc -95
/*     */     //   97: invokevirtual 105	java/io/BufferedWriter:write	(Ljava/lang/String;)V
/*     */     //   100: aload 5
/*     */     //   102: aload 9
/*     */     //   104: iload 11
/*     */     //   106: invokeinterface 162 2 0
/*     */     //   111: invokevirtual 105	java/io/BufferedWriter:write	(Ljava/lang/String;)V
/*     */     //   114: iinc 11 1
/*     */     //   117: goto -37 -> 80
/*     */     //   120: aload 5
/*     */     //   122: invokevirtual 163	java/io/BufferedWriter:newLine	()V
/*     */     //   125: aload 8
/*     */     //   127: invokeinterface 130 1 0
/*     */     //   132: ifeq +187 -> 319
/*     */     //   135: iconst_1
/*     */     //   136: istore 11
/*     */     //   138: iload 11
/*     */     //   140: iload 10
/*     */     //   142: iconst_1
/*     */     //   143: iadd
/*     */     //   144: if_icmpge +164 -> 308
/*     */     //   147: iload 11
/*     */     //   149: iconst_1
/*     */     //   150: if_icmpeq +10 -> 160
/*     */     //   153: aload 5
/*     */     //   155: ldc -95
/*     */     //   157: invokevirtual 105	java/io/BufferedWriter:write	(Ljava/lang/String;)V
/*     */     //   160: aload 8
/*     */     //   162: iload 11
/*     */     //   164: invokeinterface 164 2 0
/*     */     //   169: astore 12
/*     */     //   171: aload 12
/*     */     //   173: ifnonnull +13 -> 186
/*     */     //   176: aload 5
/*     */     //   178: ldc -91
/*     */     //   180: invokevirtual 105	java/io/BufferedWriter:write	(Ljava/lang/String;)V
/*     */     //   183: goto +119 -> 302
/*     */     //   186: aload 12
/*     */     //   188: instanceof 166
/*     */     //   191: ifeq +27 -> 218
/*     */     //   194: aload 12
/*     */     //   196: checkcast 166	java/lang/String
/*     */     //   199: astore 13
/*     */     //   201: aload 5
/*     */     //   203: aload 13
/*     */     //   205: ldc -73
/*     */     //   207: ldc -72
/*     */     //   209: invokevirtual 170	java/lang/String:replaceAll	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
/*     */     //   212: invokevirtual 105	java/io/BufferedWriter:write	(Ljava/lang/String;)V
/*     */     //   215: goto +87 -> 302
/*     */     //   218: aload 12
/*     */     //   220: instanceof 171
/*     */     //   223: ifeq +20 -> 243
/*     */     //   226: aload 5
/*     */     //   228: aload_0
/*     */     //   229: getfield 14	com/hwacom/ngtms/toolbox/sync/MySqlCsvExport:dateTimeFormat	Ljava/text/SimpleDateFormat;
/*     */     //   232: aload 12
/*     */     //   234: invokevirtual 172	java/text/SimpleDateFormat:format	(Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   237: invokevirtual 105	java/io/BufferedWriter:write	(Ljava/lang/String;)V
/*     */     //   240: goto +62 -> 302
/*     */     //   243: aload 12
/*     */     //   245: instanceof 173
/*     */     //   248: ifeq +44 -> 292
/*     */     //   251: aload 12
/*     */     //   253: checkcast 173	java/sql/Blob
/*     */     //   256: astore 13
/*     */     //   258: aload 13
/*     */     //   260: lconst_1
/*     */     //   261: aload 13
/*     */     //   263: invokeinterface 174 1 0
/*     */     //   268: l2i
/*     */     //   269: invokeinterface 175 4 0
/*     */     //   274: astore 14
/*     */     //   276: aload 5
/*     */     //   278: invokestatic 176	java/util/Base64:getEncoder	()Ljava/util/Base64$Encoder;
/*     */     //   281: aload 14
/*     */     //   283: invokevirtual 177	java/util/Base64$Encoder:encodeToString	([B)Ljava/lang/String;
/*     */     //   286: invokevirtual 105	java/io/BufferedWriter:write	(Ljava/lang/String;)V
/*     */     //   289: goto +13 -> 302
/*     */     //   292: aload 5
/*     */     //   294: aload 12
/*     */     //   296: invokevirtual 178	java/lang/Object:toString	()Ljava/lang/String;
/*     */     //   299: invokevirtual 105	java/io/BufferedWriter:write	(Ljava/lang/String;)V
/*     */     //   302: iinc 11 1
/*     */     //   305: goto -167 -> 138
/*     */     //   308: aload 5
/*     */     //   310: invokevirtual 163	java/io/BufferedWriter:newLine	()V
/*     */     //   313: iinc 7 1
/*     */     //   316: goto -191 -> 125
/*     */     //   319: aload 8
/*     */     //   321: invokeinterface 179 1 0
/*     */     //   326: iload 7
/*     */     //   328: istore 11
/*     */     //   330: aload 5
/*     */     //   332: ifnull +33 -> 365
/*     */     //   335: aload 6
/*     */     //   337: ifnull +23 -> 360
/*     */     //   340: aload 5
/*     */     //   342: invokevirtual 107	java/io/BufferedWriter:close	()V
/*     */     //   345: goto +20 -> 365
/*     */     //   348: astore 12
/*     */     //   350: aload 6
/*     */     //   352: aload 12
/*     */     //   354: invokevirtual 66	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*     */     //   357: goto +8 -> 365
/*     */     //   360: aload 5
/*     */     //   362: invokevirtual 107	java/io/BufferedWriter:close	()V
/*     */     //   365: aload_3
/*     */     //   366: ifnull +35 -> 401
/*     */     //   369: aload 4
/*     */     //   371: ifnull +24 -> 395
/*     */     //   374: aload_3
/*     */     //   375: invokeinterface 143 1 0
/*     */     //   380: goto +21 -> 401
/*     */     //   383: astore 12
/*     */     //   385: aload 4
/*     */     //   387: aload 12
/*     */     //   389: invokevirtual 66	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*     */     //   392: goto +9 -> 401
/*     */     //   395: aload_3
/*     */     //   396: invokeinterface 143 1 0
/*     */     //   401: iload 11
/*     */     //   403: ireturn
/*     */     //   404: astore 7
/*     */     //   406: aload 7
/*     */     //   408: astore 6
/*     */     //   410: aload 7
/*     */     //   412: athrow
/*     */     //   413: astore 15
/*     */     //   415: aload 5
/*     */     //   417: ifnull +33 -> 450
/*     */     //   420: aload 6
/*     */     //   422: ifnull +23 -> 445
/*     */     //   425: aload 5
/*     */     //   427: invokevirtual 107	java/io/BufferedWriter:close	()V
/*     */     //   430: goto +20 -> 450
/*     */     //   433: astore 16
/*     */     //   435: aload 6
/*     */     //   437: aload 16
/*     */     //   439: invokevirtual 66	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*     */     //   442: goto +8 -> 450
/*     */     //   445: aload 5
/*     */     //   447: invokevirtual 107	java/io/BufferedWriter:close	()V
/*     */     //   450: aload 15
/*     */     //   452: athrow
/*     */     //   453: astore 5
/*     */     //   455: aload 5
/*     */     //   457: astore 4
/*     */     //   459: aload 5
/*     */     //   461: athrow
/*     */     //   462: astore 17
/*     */     //   464: aload_3
/*     */     //   465: ifnull +35 -> 500
/*     */     //   468: aload 4
/*     */     //   470: ifnull +24 -> 494
/*     */     //   473: aload_3
/*     */     //   474: invokeinterface 143 1 0
/*     */     //   479: goto +21 -> 500
/*     */     //   482: astore 18
/*     */     //   484: aload 4
/*     */     //   486: aload 18
/*     */     //   488: invokevirtual 66	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
/*     */     //   491: goto +9 -> 500
/*     */     //   494: aload_3
/*     */     //   495: invokeinterface 143 1 0
/*     */     //   500: aload 17
/*     */     //   502: athrow
/*     */     // Line number table:
/*     */     //   Java source line #324	-> byte code offset #0
/*     */     //   Java source line #325	-> byte code offset #13
/*     */     //   Java source line #324	-> byte code offset #39
/*     */     //   Java source line #327	-> byte code offset #42
/*     */     //   Java source line #328	-> byte code offset #47
/*     */     //   Java source line #329	-> byte code offset #50
/*     */     //   Java source line #330	-> byte code offset #59
/*     */     //   Java source line #331	-> byte code offset #68
/*     */     //   Java source line #334	-> byte code offset #77
/*     */     //   Java source line #335	-> byte code offset #87
/*     */     //   Java source line #336	-> byte code offset #100
/*     */     //   Java source line #334	-> byte code offset #114
/*     */     //   Java source line #338	-> byte code offset #120
/*     */     //   Java source line #340	-> byte code offset #125
/*     */     //   Java source line #341	-> byte code offset #135
/*     */     //   Java source line #342	-> byte code offset #147
/*     */     //   Java source line #343	-> byte code offset #160
/*     */     //   Java source line #344	-> byte code offset #171
/*     */     //   Java source line #346	-> byte code offset #186
/*     */     //   Java source line #347	-> byte code offset #194
/*     */     //   Java source line #348	-> byte code offset #201
/*     */     //   Java source line #349	-> byte code offset #215
/*     */     //   Java source line #350	-> byte code offset #226
/*     */     //   Java source line #351	-> byte code offset #243
/*     */     //   Java source line #352	-> byte code offset #251
/*     */     //   Java source line #353	-> byte code offset #258
/*     */     //   Java source line #354	-> byte code offset #276
/*     */     //   Java source line #355	-> byte code offset #289
/*     */     //   Java source line #341	-> byte code offset #302
/*     */     //   Java source line #358	-> byte code offset #308
/*     */     //   Java source line #359	-> byte code offset #313
/*     */     //   Java source line #361	-> byte code offset #319
/*     */     //   Java source line #362	-> byte code offset #326
/*     */     //   Java source line #363	-> byte code offset #330
/*     */     //   Java source line #324	-> byte code offset #404
/*     */     //   Java source line #363	-> byte code offset #413
/*     */     //   Java source line #324	-> byte code offset #453
/*     */     //   Java source line #363	-> byte code offset #462
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	503	0	this	MySqlCsvExport
/*     */     //   0	503	1	sqlCommand	String
/*     */     //   0	503	2	outFile	String
/*     */     //   9	486	3	stmt	Statement
/*     */     //   11	474	4	localThrowable6	Throwable
/*     */     //   37	409	5	bw	BufferedWriter
/*     */     //   453	7	5	localThrowable4	Throwable
/*     */     //   40	396	6	localThrowable7	Throwable
/*     */     //   48	279	7	count	int
/*     */     //   404	7	7	localThrowable2	Throwable
/*     */     //   57	263	8	rs	ResultSet
/*     */     //   66	37	9	rsmd	java.sql.ResultSetMetaData
/*     */     //   75	66	10	columnCount	int
/*     */     //   78	37	11	i	int
/*     */     //   136	266	11	i	int
/*     */     //   169	126	12	column	Object
/*     */     //   348	5	12	localThrowable	Throwable
/*     */     //   383	5	12	localThrowable1	Throwable
/*     */     //   199	5	13	s	String
/*     */     //   256	6	13	blob	java.sql.Blob
/*     */     //   274	8	14	bytes	byte[]
/*     */     //   413	38	15	localObject1	Object
/*     */     //   433	5	16	localThrowable3	Throwable
/*     */     //   462	39	17	localObject2	Object
/*     */     //   482	5	18	localThrowable5	Throwable
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   340	345	348	java/lang/Throwable
/*     */     //   374	380	383	java/lang/Throwable
/*     */     //   42	330	404	java/lang/Throwable
/*     */     //   42	330	413	finally
/*     */     //   404	415	413	finally
/*     */     //   425	430	433	java/lang/Throwable
/*     */     //   13	365	453	java/lang/Throwable
/*     */     //   404	453	453	java/lang/Throwable
/*     */     //   13	365	462	finally
/*     */     //   404	464	462	finally
/*     */     //   473	479	482	java/lang/Throwable
/*     */   }
/*     */   
/*     */   private void extractBackupData(TableMeta tableMeta)
/*     */     throws Exception
/*     */   {
/* 367 */     String r23aCompress = this.config.getString("r23aCompress");
/* 368 */     String fileExtention = null;
/* 369 */     int rows = 0;
/* 370 */     if ("7z".equals(r23aCompress)) fileExtention = ".7z"; else {
/* 371 */       fileExtention = ".csv";
/*     */     }
/*     */     
/*     */ 
/* 375 */     String csvFileName = tableMeta.tableName + "_" + this.fileDateFormat.format(tableMeta.getDate()) + ".csv";
/* 376 */     String outFilePath = this.config.getString("r23aCsvRepository") + File.separator;
/* 377 */     if ((tableMeta.moduleName != null) && (tableMeta.moduleName.trim().length() > 0)) {
/* 378 */       outFilePath = outFilePath + tableMeta.moduleName + File.separator;
/*     */     }
/* 380 */     outFilePath = outFilePath + tableMeta.tableName;
/*     */     
/*     */ 
/*     */ 
/* 384 */     File outFile = new File(outFilePath, tableMeta.tableName + "_" + this.fileDateFormat.format(tableMeta.getDate()) + fileExtention);
/*     */     
/* 386 */     tableMeta.csvFileName = csvFileName;
/* 387 */     tableMeta.outFileName = outFile.getAbsolutePath();
/* 388 */     logger.info(tableMeta.toString());
/*     */     
/* 390 */     outFile.getParentFile().mkdirs();
/* 391 */     if (!outFile.exists()) {
/*     */       try {
/* 393 */         File tempFile = new File(outFile.getAbsolutePath() + ".temp");
/* 394 */         if (fileExtention.equals(".7z"))
/*     */         {
/* 396 */           rows = extractDataToCsv7z(tableMeta.sqlCommand, tempFile
/* 397 */             .getAbsolutePath(), csvFileName);
/*     */         } else {
/* 399 */           rows = extractDataToCsv(tableMeta.sqlCommand, tempFile.getAbsolutePath());
/*     */         }
/* 401 */         outFile.delete();
/* 402 */         tempFile.renameTo(outFile);
/* 403 */         this.extractDataNo.incrementAndGet();
/* 404 */         this.extractRowNo.addAndGet(rows);
/* 405 */         logger.info("Extract data from table: {}, rows {}", tableMeta.tableName, Integer.valueOf(rows));
/*     */       } catch (Exception ex) {
/* 407 */         throw new IOException("Failed to extract data from " + tableMeta.tableName, ex);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void getSqlCommand(TableMeta tableMeta) {
/* 413 */     String date = tableMeta.startTime.format(formatter1);
/* 414 */     StringBuffer sb = new StringBuffer();
/* 415 */     sb.append("select * from ");
/* 416 */     sb.append(tableMeta.tableName);
/* 417 */     sb.append(" where ");
/* 418 */     sb.append(tableMeta.pKey);
/* 419 */     sb.append(" >= '");
/* 420 */     sb.append(date).append(" 00:00:00'");
/* 421 */     sb.append(" and ");
/* 422 */     sb.append(tableMeta.pKey);
/* 423 */     sb.append(" <= '");
/* 424 */     sb.append(date).append(" 23:59:59'");
/* 425 */     sb.append(" order by ");
/* 426 */     sb.append(tableMeta.pKey);
/*     */     
/* 428 */     tableMeta.sqlCommand = sb.toString();
/*     */   }
/*     */   
/*     */ 
/*     */   static class TableMeta
/*     */   {
/*     */     String tableName;
/*     */     
/*     */     String moduleName;
/*     */     
/*     */     String pKey;
/*     */     
/*     */     String sqlCommand;
/*     */     
/*     */     LocalDate startTime;
/*     */     
/*     */     String csvFileName;
/*     */     
/*     */     String outFileName;
/*     */     
/*     */ 
/*     */     Date getDate()
/*     */     {
/* 451 */       Date value = null;
/* 452 */       if (this.startTime == null) value = new Date();
/* 453 */       value = Date.from(this.startTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
/* 454 */       return value;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 459 */       return "TableMeta [tableName=" + this.tableName + ", moduleName=" + this.moduleName + ", pKey=" + this.pKey + ", sqlCommand=" + this.sqlCommand + ", startTime=" + this.startTime + ", csvFileName=" + this.csvFileName + ", outFileName=" + this.outFileName + "]";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\toolbox-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\toolbox\sync\MySqlCsvExport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */