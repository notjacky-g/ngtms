/*     */ package com.hwacom.ngtms.toolbox.sigar;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Properties;
/*     */ import org.hyperic.sigar.FileSystem;
/*     */ import org.hyperic.sigar.FileSystemUsage;
/*     */ import org.hyperic.sigar.Mem;
/*     */ import org.hyperic.sigar.NfsFileSystem;
/*     */ import org.hyperic.sigar.ResourceLimit;
/*     */ import org.hyperic.sigar.Sigar;
/*     */ import org.hyperic.sigar.SigarException;
/*     */ import org.hyperic.sigar.SigarNotImplementedException;
/*     */ import org.hyperic.sigar.SigarProxy;
/*     */ import org.hyperic.sigar.SigarProxyCache;
/*     */ import org.hyperic.sigar.Swap;
/*     */ import org.hyperic.sigar.Uptime;
/*     */ import org.hyperic.sigar.jmx.SigarInvokerJMX;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MySigar
/*     */ {
/*  31 */   private static Sigar sigar = new Sigar();
/*  32 */   private static SigarProxy proxy = SigarProxyCache.newInstance(sigar);
/*  33 */   private static SigarInvokerJMX invoker = SigarInvokerJMX.getInstance(proxy, "Type=ResourceLimit");
/*     */   
/*     */   private static MyEmail myEmail;
/*  36 */   private static String subject = "";
/*     */   
/*  38 */   private boolean defaultSendMail = false;
/*  39 */   private boolean checkNFSMount = false;
/*  40 */   private boolean checkPCSStatus = false;
/*  41 */   private boolean checkDrbdStatus = false;
/*  42 */   private boolean checkBkdbHealth = false;
/*  43 */   private int diskMaxPct = 80;
/*  44 */   private int openFileMaxCount = 1024;
/*     */   
/*     */   public MySigar() {
/*  47 */     Properties properties = new Properties();
/*  48 */     InputStream in = ClassLoader.getSystemResourceAsStream("/MySigar.properties");
/*  49 */     if (in == null) {
/*  50 */       in = getClass().getResourceAsStream("MySigar.properties");
/*     */     }
/*  52 */     if (in == null) {
/*     */       try {
/*  54 */         String path = System.getProperty("user.dir");
/*  55 */         String file = path + File.separator + "MySigar.properties";
/*  56 */         in = new BufferedInputStream(new FileInputStream(file));
/*     */       }
/*     */       catch (Exception localException1) {}
/*     */     }
/*  60 */     if (in == null) {
/*     */       try {
/*  62 */         in = new BufferedInputStream(new FileInputStream("/root/MySigar.properties"));
/*     */       }
/*     */       catch (Exception localException2) {}
/*     */     }
/*     */     try
/*     */     {
/*  68 */       properties.load(in);
/*     */     } catch (Exception e) {
/*  70 */       e.printStackTrace();
/*     */     }
/*     */     
/*  73 */     this.defaultSendMail = Boolean.parseBoolean(properties.getProperty("defaultSendMail", "false"));
/*  74 */     System.out.println("defaultSendMail:" + this.defaultSendMail);
/*  75 */     this.checkNFSMount = Boolean.parseBoolean(properties.getProperty("checkNFSMount", "false"));
/*  76 */     System.out.println("checkNFSMount:" + this.checkNFSMount);
/*  77 */     this.checkPCSStatus = Boolean.parseBoolean(properties.getProperty("checkPCSStatus", "false"));
/*  78 */     System.out.println("checkPCSStatus:" + this.checkPCSStatus);
/*  79 */     this.checkDrbdStatus = Boolean.parseBoolean(properties.getProperty("checkDrbdStatus", "false"));
/*  80 */     System.out.println("checkDrbdStatus:" + this.checkDrbdStatus);
/*  81 */     this.checkBkdbHealth = Boolean.parseBoolean(properties.getProperty("checkBkdbHealth", "false"));
/*  82 */     System.out.println("checkBkdbHealth:" + this.checkBkdbHealth);
/*     */     try
/*     */     {
/*  85 */       this.diskMaxPct = Integer.parseInt(properties.getProperty("diskMaxPct"));
/*  86 */       System.out.println("diskMaxPct:" + this.diskMaxPct);
/*     */     }
/*     */     catch (Exception localException3) {}
/*     */     try
/*     */     {
/*  91 */       this.openFileMaxCount = Integer.parseInt(properties.getProperty("openFileMaxCount"));
/*  92 */       System.out.println("openFileMaxCount:" + this.openFileMaxCount);
/*     */     }
/*     */     catch (Exception localException4) {}
/*     */     
/*  96 */     myEmail = new MyEmail(properties);
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
/*     */   private static void communicate(Process process, final StringBuffer sb, final String regex)
/*     */   {
/* 110 */     BufferedReader stdOut = new BufferedReader(new InputStreamReader(process.getInputStream()));
/*     */     
/* 112 */     BufferedReader stdErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
/*     */     
/*     */ 
/* 115 */     new Thread()
/*     */     {
/*     */       public void run() {
/*     */         try {
/*     */           String line;
/* 120 */           while ((line = this.val$stdOut.readLine()) != null) {
/* 121 */             if (regex != null) {
/* 122 */               if (line.trim().indexOf("regex") != -1) {
/* 123 */                 sb.append(line.trim()).append("\r\n");
/*     */               }
/*     */             } else {
/* 126 */               sb.append(line.trim()).append("\r\n");
/*     */             }
/*     */           }
/*     */         } catch (Exception e) {
/* 130 */           throw new Error(e);
/*     */         }
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
/*     */         String line;
/*     */       }
/* 147 */     }.start();new Thread()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try
/*     */         {
/*     */           String line;
/* 141 */           while ((line = this.val$stdErr.readLine()) != null) {
/* 142 */             sb.append(line.trim()).append("\r\n");
/*     */           }
/*     */         } catch (Exception e) {
/* 145 */           throw new Error(e);
/*     */         }
/*     */         String line;
/*     */       }
/*     */     }.start();
/*     */     try
/*     */     {
/* 152 */       process.waitFor();
/*     */     } catch (Exception e) {
/* 154 */       throw new Error(e);
/*     */     }
/*     */   }
/*     */   
/*     */   private static String formatUptime(double uptime) {
/* 159 */     String retval = "";
/* 160 */     int days = (int)uptime / 86400;
/*     */     
/* 162 */     if (days != 0) {
/* 163 */       retval = retval + days + " " + (days > 1 ? "days" : "day") + ", ";
/*     */     }
/* 165 */     int minutes = (int)uptime / 60;
/* 166 */     int hours = minutes / 60;
/* 167 */     hours %= 24;
/* 168 */     minutes %= 60;
/*     */     
/* 170 */     if (hours != 0) {
/* 171 */       retval = retval + hours + ":" + minutes;
/*     */     } else {
/* 173 */       retval = retval + minutes + " min";
/*     */     }
/* 175 */     return retval;
/*     */   }
/*     */   
/*     */   public String showFQDN() throws SigarException {
/* 179 */     return proxy.getFQDN();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StringBuffer showUpTime()
/*     */     throws SigarException
/*     */   {
/* 189 */     StringBuffer sb = new StringBuffer();
/* 190 */     double uptime = proxy.getUptime().getUptime();
/* 191 */     sb.append("uptime: ").append(formatUptime(uptime)).append("\t");
/*     */     try
/*     */     {
/* 194 */       double[] avg = proxy.getLoadAverage();
/* 195 */       sb.append("load average: ");
/* 196 */       sb.append(avg[0]).append("\t");
/* 197 */       sb.append(avg[1]).append("\t");
/* 198 */       sb.append(avg[2]).append("\r\n");
/*     */     }
/*     */     catch (SigarNotImplementedException localSigarNotImplementedException) {}
/* 201 */     return sb;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StringBuffer showDf()
/*     */     throws SigarException
/*     */   {
/* 211 */     StringBuffer sb = new StringBuffer();
/* 212 */     ArrayList<FileSystem> sys = new ArrayList();
/* 213 */     if (sys.size() == 0) {
/* 214 */       FileSystem[] fslist = proxy.getFileSystemList();
/* 215 */       for (int i = 0; i < fslist.length; i++) {
/* 216 */         if ((2 == fslist[i].getType()) || (3 == fslist[i].getType())) sys.add(fslist[i]);
/*     */       }
/*     */     }
/* 219 */     for (int i = 0; i < sys.size(); i++) {
/* 220 */       formatDf((FileSystem)sys.get(i), sb);
/* 221 */       sb.append("\r\n");
/*     */     }
/* 223 */     if ((this.checkNFSMount) && 
/* 224 */       (sb.toString().indexOf("nfs") == -1)) {
/* 225 */       this.defaultSendMail = true;
/* 226 */       subject = " NFS mount fail.";
/* 227 */       System.out.println(sb.toString());
/*     */     }
/*     */     
/* 230 */     return sb;
/*     */   }
/*     */   
/*     */   private void formatDf(FileSystem fs, StringBuffer sb) throws SigarException { long pct;
/*     */     long total;
/*     */     long avail;
/*     */     long used;
/* 237 */     try { if ((fs instanceof NfsFileSystem)) {
/* 238 */         NfsFileSystem nfs = (NfsFileSystem)fs;
/* 239 */         if (!nfs.ping()) {
/* 240 */           sb.append(nfs.getUnreachableMessage());
/* 241 */           return;
/*     */         }
/*     */       }
/* 244 */       FileSystemUsage usage = sigar.getFileSystemUsage(fs.getDirName());
/*     */       
/* 246 */       long used = usage.getTotal() - usage.getFree();
/* 247 */       long avail = usage.getAvail();
/* 248 */       long total = usage.getTotal();
/* 249 */       long pct = (usage.getUsePercent() * 100.0D);
/* 250 */       System.out.println(fs.getDirName() + " ->  " + pct + " Percent Used.");
/* 251 */       if (pct > this.diskMaxPct) {
/* 252 */         this.defaultSendMail = true;
/* 253 */         subject = " FileSystem Usage > " + this.diskMaxPct;
/* 254 */         System.out.println(sb.toString());
/*     */       }
/*     */     } catch (SigarException e) {
/* 257 */       used = avail = total = pct = 0L;
/*     */     }
/*     */     String usePct;
/*     */     String usePct;
/* 261 */     if (pct == 0L) {
/* 262 */       usePct = "-";
/*     */     } else {
/* 264 */       usePct = pct + "%";
/*     */     }
/* 266 */     ArrayList<String> items = new ArrayList();
/* 267 */     items.add(fs.getDevName());
/* 268 */     items.add(Sigar.formatSize(total * 1024L));
/* 269 */     items.add(Sigar.formatSize(used * 1024L));
/* 270 */     items.add(Sigar.formatSize(avail * 1024L));
/* 271 */     items.add(usePct);
/* 272 */     items.add(fs.getDirName());
/* 273 */     items.add(fs.getSysTypeName() + "/" + fs.getTypeName());
/* 274 */     sb.append(items);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StringBuffer showFree()
/*     */     throws SigarException
/*     */   {
/* 284 */     StringBuffer sb = new StringBuffer();
/* 285 */     Mem mem = sigar.getMem();
/* 286 */     Swap swap = sigar.getSwap();
/*     */     
/* 288 */     sb.append("Mem:\t");
/* 289 */     sb.append(formatFree(mem.getTotal())).append("k\t");
/* 290 */     sb.append(formatFree(mem.getUsed())).append("k\t");
/* 291 */     sb.append(formatFree(mem.getFree())).append("k\r\n");
/* 292 */     sb.append("Swap:\t");
/* 293 */     sb.append(formatFree(swap.getTotal())).append("k\t\t");
/* 294 */     sb.append(formatFree(swap.getUsed())).append("k\t\t");
/* 295 */     sb.append(formatFree(swap.getFree())).append("k\r\n");
/*     */     
/* 297 */     return sb;
/*     */   }
/*     */   
/*     */   private Long formatFree(long value) {
/* 301 */     return new Long(value / 1024L);
/*     */   }
/*     */   
/*     */   public StringBuffer showUlimit() throws SigarException {
/* 305 */     StringBuffer sb = new StringBuffer();
/* 306 */     String mode = "Cur";
/* 307 */     Long coreFile = (Long)invoker.invoke("Core" + mode);
/* 308 */     Long dataSeg = (Long)invoker.invoke("Data" + mode);
/* 309 */     Long file = (Long)invoker.invoke("FileSize" + mode);
/* 310 */     Long pipe = (Long)invoker.invoke("PipeSize" + mode);
/* 311 */     Long maxMemory = (Long)invoker.invoke("Memory" + mode);
/* 312 */     Long openFiles = (Long)invoker.invoke("OpenFiles" + mode);
/* 313 */     Long stackSize = (Long)invoker.invoke("Stack" + mode);
/* 314 */     Long cpuTime = (Long)invoker.invoke("Cpu" + mode);
/* 315 */     Long maxUserProcesses = (Long)invoker.invoke("Processes" + mode);
/* 316 */     Long virtualMemory = (Long)invoker.invoke("VirtualMemory" + mode);
/*     */     
/* 318 */     sb.append("core file size.......").append(formatUlimit(coreFile.longValue())).append("\r\n");
/* 319 */     sb.append("data seg size........").append(formatUlimit(dataSeg.longValue())).append("\r\n");
/* 320 */     sb.append("file size............").append(formatUlimit(file.longValue())).append("\r\n");
/* 321 */     sb.append("pipe size............").append(formatUlimit(pipe.longValue())).append("\r\n");
/* 322 */     sb.append("max memory size......").append(formatUlimit(maxMemory.longValue())).append("\r\n");
/* 323 */     sb.append("open files...........").append(formatUlimit(openFiles.longValue())).append("\r\n");
/* 324 */     sb.append("stack size...........").append(formatUlimit(stackSize.longValue())).append("\r\n");
/* 325 */     sb.append("cpu time.............").append(formatUlimit(cpuTime.longValue())).append("\r\n");
/* 326 */     sb.append("max user processes...").append(formatUlimit(maxUserProcesses.longValue())).append("\r\n");
/* 327 */     sb.append("virtual memory.......").append(formatUlimit(virtualMemory.longValue())).append("\r\n");
/* 328 */     return sb;
/*     */   }
/*     */   
/*     */   private String formatUlimit(long val) {
/* 332 */     if (val == ResourceLimit.INFINITY()) {
/* 333 */       return "unlimited";
/*     */     }
/* 335 */     return String.valueOf(val);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StringBuffer command_lsof_oidof()
/*     */   {
/* 345 */     StringBuffer sb = new StringBuffer();
/*     */     try {
/* 347 */       Runtime rt = Runtime.getRuntime();
/* 348 */       Process proc = rt.exec("lsof -p `pidof crmd` | grep socket");
/* 349 */       communicate(proc, sb, null);
/*     */     }
/*     */     catch (Exception localException) {}
/* 352 */     return sb;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StringBuffer command_lsof_oidof_wc()
/*     */   {
/* 361 */     StringBuffer sb = new StringBuffer();
/*     */     try {
/* 363 */       Runtime rt = Runtime.getRuntime();
/* 364 */       Process proc = rt.exec("lsof -p `pidof crmd` | grep socket | wc -l");
/*     */       
/* 366 */       StringBuffer s = new StringBuffer();
/* 367 */       communicate(proc, s, null);
/*     */       
/* 369 */       if (Integer.parseInt(s.toString()) > this.openFileMaxCount) {
/* 370 */         this.defaultSendMail = true;
/* 371 */         subject = " Open file > " + this.openFileMaxCount;
/* 372 */         sb.append(command_lsof_oidof());
/* 373 */         System.out.println(sb.toString());
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {}
/* 377 */     return sb;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StringBuffer command_pcs()
/*     */   {
/* 386 */     StringBuffer sb = new StringBuffer();
/*     */     try {
/* 388 */       Runtime rt = Runtime.getRuntime();
/* 389 */       Process proc = rt.exec("pcs status");
/* 390 */       communicate(proc, sb, null);
/* 391 */       String tmp = sb.toString();
/* 392 */       tmp.replaceAll("(\r\n|\n)", "");
/* 393 */       if ((tmp.indexOf("Failed") != -1) || 
/* 394 */         (tmp.indexOf("error") != -1) || 
/* 395 */         (tmp.indexOf("unmanaged") != -1)) {
/* 396 */         this.defaultSendMail = true;
/* 397 */         subject = " pcs status check has error.";
/* 398 */         System.out.println(sb.toString());
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {}
/* 402 */     return sb;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StringBuffer command_drbd()
/*     */   {
/* 411 */     StringBuffer sb = new StringBuffer();
/*     */     try {
/* 413 */       Runtime rt = Runtime.getRuntime();
/* 414 */       Process proc = rt.exec("cat /proc/drbd");
/* 415 */       communicate(proc, sb, null);
/* 416 */       String tmp = sb.toString();
/* 417 */       tmp.replace('\r', ' ');
/* 418 */       tmp.replace('\n', ' ');
/* 419 */       tmp.replaceAll(" ", "");
/*     */       
/* 421 */       tmp.replaceAll("(\r\n|\n)", "");
/*     */       
/* 423 */       if ((tmp.indexOf("cs:WFConnection") != -1) || 
/* 424 */         (tmp.indexOf("cs:StandAlone") != -1) || 
/* 425 */         (tmp.indexOf("cs:SyncSource") != -1) || 
/* 426 */         (tmp.indexOf("cs:SyncTarget") != -1) || 
/* 427 */         (tmp.indexOf("Inconsistent") != -1)) {
/* 428 */         this.defaultSendMail = true;
/* 429 */         subject = " drbd check has error.";
/* 430 */         System.out.println(sb.toString());
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {}
/* 434 */     return sb;
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
/*     */   public StringBuffer command_bkdb_m1showhealth()
/*     */   {
/* 456 */     StringBuffer sb = new StringBuffer();
/*     */     try {
/* 458 */       Runtime rt = Runtime.getRuntime();
/* 459 */       Process proc = rt.exec("/opt/hwacom/mysql/m1showhealth-csv.sh");
/* 460 */       communicate(proc, sb, "BKDB");
/* 461 */       String[] tmp = sb.toString().split(",");
/* 462 */       String state = null;
/* 463 */       String gtidMode = null;
/* 464 */       String health = null;
/* 465 */       if (tmp.length == 6) {
/* 466 */         state = tmp[3];
/* 467 */         gtidMode = tmp[4];
/* 468 */         health = tmp[5];
/* 469 */         if ((state.indexOf("UP") == -1) || 
/* 470 */           (gtidMode.indexOf("ON") == -1) || 
/* 471 */           (health.indexOf("OK") == -1)) {
/* 472 */           this.defaultSendMail = true;
/* 473 */           subject = " BKDB showhealth check has error.";
/* 474 */           System.out.println(sb.toString());
/*     */         }
/* 476 */       } else if (tmp.length > 6) {
/* 477 */         state = tmp[3];
/* 478 */         gtidMode = tmp[4];
/* 479 */         health = tmp[5];
/* 480 */         int index1 = health.indexOf("is");
/* 481 */         int index2 = health.indexOf("seconds");
/* 482 */         String delayStr = health.substring(index1 + 2, index2).trim();
/* 483 */         int delay = 0;
/*     */         try {
/* 485 */           delay = Integer.parseInt(delayStr);
/*     */         }
/*     */         catch (Exception localException) {}
/* 488 */         if ((state.indexOf("UP") == -1) || (delay > 500)) {
/* 489 */           this.defaultSendMail = true;
/* 490 */           subject = " BKDB showhealth check has error.";
/* 491 */           System.out.println(sb.toString());
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception localException1) {}
/* 496 */     return sb;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/* 500 */     StringBuffer sb = new StringBuffer();
/* 501 */     MySigar my = new MySigar();
/*     */     
/* 503 */     sb.append("---------------------------------------------------------------------")
/* 504 */       .append("\r\n");
/* 505 */     sb.append(my.showFQDN()).append("\r\n");
/* 506 */     sb.append("---------------------------------------------------------------------")
/* 507 */       .append("\r\n");
/* 508 */     sb.append("\r\n");
/*     */     
/* 510 */     sb.append(my.showUpTime());
/* 511 */     sb.append("\r\n");
/*     */     
/* 513 */     sb.append(my.showFree());
/* 514 */     sb.append("\r\n");
/*     */     
/* 516 */     sb.append(my.showDf());
/* 517 */     sb.append("\r\n");
/*     */     
/* 519 */     sb.append(my.showUlimit());
/*     */     
/* 521 */     if (my.checkPCSStatus)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 526 */       sb.append("\r\n---------------------------------------------------------------------").append("\r\n");
/* 527 */       sb.append(my.command_pcs());
/*     */     }
/*     */     
/* 530 */     if (my.checkDrbdStatus)
/*     */     {
/* 532 */       sb.append("\r\n---------------------------------------------------------------------").append("\r\n");
/* 533 */       sb.append(my.command_drbd());
/*     */     }
/*     */     
/* 536 */     if (my.checkBkdbHealth)
/*     */     {
/* 538 */       sb.append("\r\n---------------------------------------------------------------------").append("\r\n");
/* 539 */       sb.append(my.command_bkdb_m1showhealth());
/*     */     }
/*     */     
/* 542 */     System.out.println(sb.toString());
/*     */     
/* 544 */     System.out.println("subject:" + subject);
/* 545 */     System.out.println("SendMail:" + my.defaultSendMail);
/* 546 */     if (my.defaultSendMail) {
/* 547 */       myEmail.sendHtml(sb.toString(), subject, false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\toolbox-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\toolbox\sigar\MySigar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */