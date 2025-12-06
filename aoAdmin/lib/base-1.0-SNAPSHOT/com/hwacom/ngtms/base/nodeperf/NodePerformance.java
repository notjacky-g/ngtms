/*     */ package com.hwacom.ngtms.base.nodeperf;
/*     */ 
/*     */ import com.hazelcast.nio.serialization.Portable;
/*     */ import com.hazelcast.nio.serialization.PortableReader;
/*     */ import com.hazelcast.nio.serialization.PortableWriter;
/*     */ import com.hwacom.ngtms.base.hazelcast.serializer.HzPortable;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.hyperic.sigar.CpuInfo;
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
/*     */ public class NodePerformance
/*     */   extends HzPortable
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Date timestamp;
/*     */   private double user;
/*     */   private double sys;
/*     */   private double nice;
/*     */   private double idle;
/*     */   private double wait;
/*     */   private double irq;
/*     */   private double combined;
/*     */   private double[] loadAverage;
/*     */   private double uptime;
/*     */   private long memUsed;
/*     */   private long memFree;
/*     */   private long memTotal;
/*     */   private double memUsedPercent;
/*     */   private long totalThreads;
/*     */   private long totalProcesses;
/*     */   private long procId;
/*     */   private long procMemTotoal;
/*     */   private long procMemResident;
/*     */   private double procCpuPercent;
/*     */   private long procCpuTotal;
/*     */   private long procJvmMaxMem;
/*     */   private long procJvmTotalMem;
/*     */   private long procJvmFreeMem;
/*     */   private int procJvmThreads;
/*     */   private double SystemLoadAverage;
/*     */   private NetStat netStat;
/*  56 */   private List<DiskInfo> diskInfoList = new ArrayList<>();
/*  57 */   private List<NetInfInfo> netInfInfoList = new ArrayList<>();
/*  58 */   private List<CpuInfo> cpuInfoList = new ArrayList<>();
/*     */   
/*  60 */   private String groupName = "";
/*  61 */   private String nodeName = "";
/*     */   
/*     */   private long sumOfRxSpeedPerSec;
/*     */   
/*     */   private long sumOfTxSpeedPerSec;
/*     */ 
/*     */   
/*     */   public void writePortable(PortableWriter writer) throws IOException {
/*  69 */     writer.writeLong("timestamp", (this.timestamp != null) ? this.timestamp.getTime() : 0L);
/*  70 */     writer.writeDouble("user", this.user);
/*  71 */     writer.writeDouble("sys", this.sys);
/*  72 */     writer.writeDouble("nice", this.nice);
/*  73 */     writer.writeDouble("idle", this.idle);
/*  74 */     writer.writeDouble("wait", this.wait);
/*  75 */     writer.writeDouble("irq", this.irq);
/*  76 */     writer.writeDouble("combined", this.combined);
/*  77 */     writer.writeDoubleArray("loadAverage", this.loadAverage);
/*  78 */     writer.writeDouble("uptime", this.uptime);
/*  79 */     writer.writeLong("memUsed", this.memUsed);
/*  80 */     writer.writeLong("memFree", this.memFree);
/*  81 */     writer.writeLong("memTotal", this.memTotal);
/*  82 */     writer.writeDouble("memUsedPercent", this.memUsedPercent);
/*  83 */     writer.writeLong("totalThreads", this.totalThreads);
/*  84 */     writer.writeLong("totalProcesses", this.totalProcesses);
/*  85 */     writer.writeLong("procId", this.procId);
/*  86 */     writer.writeLong("procMemTotoal", this.procMemTotoal);
/*  87 */     writer.writeLong("procMemResident", this.procMemResident);
/*  88 */     writer.writeDouble("procCpuPercent", this.procCpuPercent);
/*  89 */     writer.writeLong("procCpuTotal", this.procCpuTotal);
/*  90 */     writer.writeLong("procJvmMaxMem", this.procJvmMaxMem);
/*  91 */     writer.writeLong("procJvmTotalMem", this.procJvmTotalMem);
/*  92 */     writer.writeLong("procJvmFreeMem", this.procJvmFreeMem);
/*  93 */     writer.writeInt("procJvmThreads", this.procJvmThreads);
/*  94 */     writer.writeDouble("SystemLoadAverage", this.SystemLoadAverage);
/*  95 */     if (this.netStat != null) { writer.writePortable("netStat", (Portable)this.netStat); }
/*  96 */     else { writer.writeNullPortable("netStat", 1, 1004); }
/*  97 */      writer.writePortableArray("diskInfoList", (Portable[])this.diskInfoList.toArray((Object[])new DiskInfo[0]));
/*  98 */     writer.writePortableArray("netInfInfoList", (Portable[])this.netInfInfoList.toArray((Object[])new NetInfInfo[0]));
/*  99 */     writer.writeUTF("groupName", this.groupName);
/* 100 */     writer.writeUTF("nodeName", this.nodeName);
/* 101 */     writer.writeLong("sumOfRxSpeedPerSec", this.sumOfRxSpeedPerSec);
/* 102 */     writer.writeLong("sumOfTxSpeedPerSec", this.sumOfTxSpeedPerSec);
/* 103 */     writer.writeInt("cpuNumber", this.cpuInfoList.size());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readPortable(PortableReader reader) throws IOException {
/* 108 */     long time = reader.readLong("timestamp");
/* 109 */     this.timestamp = (time == 0L) ? null : new Date(time);
/* 110 */     this.user = reader.readDouble("user");
/* 111 */     this.sys = reader.readDouble("sys");
/* 112 */     this.nice = reader.readDouble("nice");
/* 113 */     this.idle = reader.readDouble("idle");
/* 114 */     this.wait = reader.readDouble("wait");
/* 115 */     this.irq = reader.readDouble("irq");
/* 116 */     this.combined = reader.readDouble("combined");
/* 117 */     this.loadAverage = reader.readDoubleArray("loadAverage");
/* 118 */     this.uptime = reader.readDouble("uptime");
/* 119 */     this.memUsed = reader.readLong("memUsed");
/* 120 */     this.memFree = reader.readLong("memFree");
/* 121 */     this.memTotal = reader.readLong("memTotal");
/* 122 */     this.memUsedPercent = reader.readDouble("memUsedPercent");
/* 123 */     this.totalThreads = reader.readLong("totalThreads");
/* 124 */     this.totalProcesses = reader.readLong("totalProcesses");
/* 125 */     this.procId = reader.readLong("procId");
/* 126 */     this.procMemTotoal = reader.readLong("procMemTotoal");
/* 127 */     this.procMemResident = reader.readLong("procMemResident");
/* 128 */     this.procCpuPercent = reader.readDouble("procCpuPercent");
/* 129 */     this.procCpuTotal = reader.readLong("procCpuTotal");
/* 130 */     this.procJvmMaxMem = reader.readLong("procJvmMaxMem");
/* 131 */     this.procJvmTotalMem = reader.readLong("procJvmTotalMem");
/* 132 */     this.procJvmFreeMem = reader.readLong("procJvmFreeMem");
/* 133 */     this.procJvmThreads = reader.readInt("procJvmThreads");
/* 134 */     this.SystemLoadAverage = reader.readDouble("SystemLoadAverage");
/* 135 */     this.netStat = (NetStat)reader.readPortable("netStat");
/* 136 */     Portable[] portables = reader.readPortableArray("diskInfoList");
/* 137 */     for (Portable p : portables) {
/* 138 */       this.diskInfoList.add((DiskInfo)p);
/*     */     }
/* 140 */     portables = reader.readPortableArray("netInfInfoList");
/* 141 */     for (Portable p : portables) {
/* 142 */       this.netInfInfoList.add((NetInfInfo)p);
/*     */     }
/* 144 */     this.groupName = reader.readUTF("groupName");
/* 145 */     this.nodeName = reader.readUTF("nodeName");
/* 146 */     this.sumOfRxSpeedPerSec = reader.readLong("sumOfRxSpeedPerSec");
/* 147 */     this.sumOfTxSpeedPerSec = reader.readLong("sumOfTxSpeedPerSec");
/* 148 */     int cpuNumber = reader.readInt("cpuNumber");
/* 149 */     for (int i = 0; i < cpuNumber; i++) {
/* 150 */       this.cpuInfoList.add(new CpuInfo());
/*     */     }
/*     */   }
/*     */   
/*     */   public Date getTimestamp() {
/* 155 */     return this.timestamp;
/*     */   }
/*     */   
/*     */   public void setTimestamp(Date timestamp) {
/* 159 */     this.timestamp = timestamp;
/*     */   }
/*     */   
/*     */   public double getUser() {
/* 163 */     return this.user;
/*     */   }
/*     */   
/*     */   public void setUser(double user) {
/* 167 */     this.user = user;
/*     */   }
/*     */   
/*     */   public double getSys() {
/* 171 */     return this.sys;
/*     */   }
/*     */   
/*     */   public void setSys(double sys) {
/* 175 */     this.sys = sys;
/*     */   }
/*     */   
/*     */   public double getNice() {
/* 179 */     return this.nice;
/*     */   }
/*     */   
/*     */   public void setNice(double nice) {
/* 183 */     this.nice = nice;
/*     */   }
/*     */   
/*     */   public double getCpuUsage() {
/* 187 */     return 1.0D - this.idle;
/*     */   }
/*     */   
/*     */   public int getCpuUsagePercentage() {
/* 191 */     return (int)(getCpuUsage() * 100.0D);
/*     */   }
/*     */   
/*     */   public double getIdle() {
/* 195 */     return this.idle;
/*     */   }
/*     */   
/*     */   public void setIdle(double idle) {
/* 199 */     this.idle = idle;
/*     */   }
/*     */   
/*     */   public double getWait() {
/* 203 */     return this.wait;
/*     */   }
/*     */   
/*     */   public void setWait(double wait) {
/* 207 */     this.wait = wait;
/*     */   }
/*     */   
/*     */   public double getIrq() {
/* 211 */     return this.irq;
/*     */   }
/*     */   
/*     */   public void setIrq(double irq) {
/* 215 */     this.irq = irq;
/*     */   }
/*     */   
/*     */   public double getCombined() {
/* 219 */     return this.combined;
/*     */   }
/*     */   
/*     */   public void setCombined(double combined) {
/* 223 */     this.combined = combined;
/*     */   }
/*     */   
/*     */   public List<DiskInfo> getDiskInfoList() {
/* 227 */     return this.diskInfoList;
/*     */   }
/*     */   
/*     */   public void setDiskInfoList(List<DiskInfo> diskInfoList) {
/* 231 */     this.diskInfoList = diskInfoList;
/*     */   }
/*     */   
/*     */   public long getProcId() {
/* 235 */     return this.procId;
/*     */   }
/*     */   
/*     */   public void setProcId(long procId) {
/* 239 */     this.procId = procId;
/*     */   }
/*     */   
/*     */   public List<NetInfInfo> getNetInfInfoList() {
/* 243 */     return this.netInfInfoList;
/*     */   }
/*     */   
/*     */   public void setNetInfInfoList(List<NetInfInfo> netInfInfoList) {
/* 247 */     this.netInfInfoList = netInfInfoList;
/*     */   }
/*     */   
/*     */   public double getUptime() {
/* 251 */     return this.uptime;
/*     */   }
/*     */   
/*     */   public void setUptime(double uptime) {
/* 255 */     this.uptime = uptime;
/*     */   }
/*     */   
/*     */   public double[] getLoadAverage() {
/* 259 */     return this.loadAverage;
/*     */   }
/*     */   
/*     */   public void setLoadAverage(double[] loadAverage) {
/* 263 */     this.loadAverage = loadAverage;
/*     */   }
/*     */   
/*     */   public long getTotalThreads() {
/* 267 */     return this.totalThreads;
/*     */   }
/*     */   
/*     */   public void setTotalThreads(long totalThreads) {
/* 271 */     this.totalThreads = totalThreads;
/*     */   }
/*     */   
/*     */   public long getTotalProcesses() {
/* 275 */     return this.totalProcesses;
/*     */   }
/*     */   
/*     */   public void setTotalProcesses(long totalProcesses) {
/* 279 */     this.totalProcesses = totalProcesses;
/*     */   }
/*     */   
/*     */   public double getProcCpuPercent() {
/* 283 */     return this.procCpuPercent;
/*     */   }
/*     */   
/*     */   public void setProcCpuPercent(double procCpuPercent) {
/* 287 */     this.procCpuPercent = procCpuPercent;
/*     */   }
/*     */   
/*     */   public NetStat getNetStat() {
/* 291 */     return this.netStat;
/*     */   }
/*     */   
/*     */   public void setNetStat(NetStat netStat) {
/* 295 */     this.netStat = netStat;
/*     */   }
/*     */   
/*     */   public long getMemUsed() {
/* 299 */     return this.memUsed;
/*     */   }
/*     */   
/*     */   public void setMemUsed(long memUsed) {
/* 303 */     this.memUsed = memUsed;
/*     */   }
/*     */   
/*     */   public long getMemFree() {
/* 307 */     return this.memFree;
/*     */   }
/*     */   
/*     */   public void setMemFree(long memFree) {
/* 311 */     this.memFree = memFree;
/*     */   }
/*     */   
/*     */   public long getMemTotal() {
/* 315 */     return this.memTotal;
/*     */   }
/*     */   
/*     */   public void setMemTotal(long memTotal) {
/* 319 */     this.memTotal = memTotal;
/*     */   }
/*     */   
/*     */   public double getMemUsedPercent() {
/* 323 */     return this.memUsedPercent;
/*     */   }
/*     */   
/*     */   public void setMemUsedPercent(double memUsedPercent) {
/* 327 */     this.memUsedPercent = memUsedPercent;
/*     */   }
/*     */   
/*     */   public long getProcMemTotoal() {
/* 331 */     return this.procMemTotoal;
/*     */   }
/*     */   
/*     */   public void setProcMemTotoal(long procMemTotoal) {
/* 335 */     this.procMemTotoal = procMemTotoal;
/*     */   }
/*     */   
/*     */   public long getProcMemResident() {
/* 339 */     return this.procMemResident;
/*     */   }
/*     */   
/*     */   public void setProcMemResident(long procMemResident) {
/* 343 */     this.procMemResident = procMemResident;
/*     */   }
/*     */   
/*     */   public long getProcCpuTotal() {
/* 347 */     return this.procCpuTotal;
/*     */   }
/*     */   
/*     */   public void setProcCpuTotal(long procCpuTotal) {
/* 351 */     this.procCpuTotal = procCpuTotal;
/*     */   }
/*     */   
/*     */   public long getProcJvmMaxMem() {
/* 355 */     return this.procJvmMaxMem;
/*     */   }
/*     */   
/*     */   public void setProcJvmMaxMem(long procJvmMaxMem) {
/* 359 */     this.procJvmMaxMem = procJvmMaxMem;
/*     */   }
/*     */   
/*     */   public long getProcJvmTotalMem() {
/* 363 */     return this.procJvmTotalMem;
/*     */   }
/*     */   
/*     */   public void setProcJvmTotalMem(long procJvmTotalMem) {
/* 367 */     this.procJvmTotalMem = procJvmTotalMem;
/*     */   }
/*     */   
/*     */   public long getProcJvmFreeMem() {
/* 371 */     return this.procJvmFreeMem;
/*     */   }
/*     */   
/*     */   public void setProcJvmFreeMem(long procJvmFreeMem) {
/* 375 */     this.procJvmFreeMem = procJvmFreeMem;
/*     */   }
/*     */   
/*     */   public int getProcJvmThreads() {
/* 379 */     return this.procJvmThreads;
/*     */   }
/*     */   
/*     */   public void setProcJvmThreads(int procJvmThreads) {
/* 383 */     this.procJvmThreads = procJvmThreads;
/*     */   }
/*     */   
/*     */   public double getSystemLoadAverage() {
/* 387 */     return this.SystemLoadAverage;
/*     */   }
/*     */   
/*     */   public void setSystemLoadAverage(double systemLoadAverage) {
/* 391 */     this.SystemLoadAverage = systemLoadAverage;
/*     */   }
/*     */   
/*     */   public List<CpuInfo> getCpuInfoList() {
/* 395 */     return this.cpuInfoList;
/*     */   }
/*     */   
/*     */   public void setCpuInfoList(List<CpuInfo> cpuInfoList) {
/* 399 */     this.cpuInfoList = cpuInfoList;
/*     */   }
/*     */   
/*     */   public String getGroupName() {
/* 403 */     return this.groupName;
/*     */   }
/*     */   
/*     */   public void setGroupName(String groupName) {
/* 407 */     this.groupName = groupName;
/*     */   }
/*     */   
/*     */   public String getNodeName() {
/* 411 */     return this.nodeName;
/*     */   }
/*     */   
/*     */   public void setNodeName(String nodeName) {
/* 415 */     this.nodeName = nodeName;
/*     */   }
/*     */   
/*     */   public long getSumOfRxSpeedPerSec() {
/* 419 */     return this.sumOfRxSpeedPerSec;
/*     */   }
/*     */   
/*     */   public void setSumOfRxSpeedPerSec(long sumOfRxSpeedPerSec) {
/* 423 */     this.sumOfRxSpeedPerSec = sumOfRxSpeedPerSec;
/*     */   }
/*     */   
/*     */   public long getSumOfTxSpeedPerSec() {
/* 427 */     return this.sumOfTxSpeedPerSec;
/*     */   }
/*     */   
/*     */   public void setSumOfTxSpeedPerSec(long sumOfTxSpeedPerSec) {
/* 431 */     this.sumOfTxSpeedPerSec = sumOfTxSpeedPerSec;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 436 */     return "NodePerformance [timestamp=" + this.timestamp + ", user=" + this.user + ", sys=" + this.sys + ", nice=" + this.nice + ", idle=" + this.idle + ", wait=" + this.wait + ", irq=" + this.irq + ", combined=" + this.combined + ", loadAverage=" + 
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
/* 453 */       Arrays.toString(this.loadAverage) + ", uptime=" + this.uptime + ", memUsed=" + this.memUsed + ", memFree=" + this.memFree + ", memTotal=" + this.memTotal + ", memUsedPercent=" + this.memUsedPercent + ", totalThreads=" + this.totalThreads + ", totalProcesses=" + this.totalProcesses + ", procId=" + this.procId + ", procMemTotoal=" + this.procMemTotoal + ", procMemResident=" + this.procMemResident + ", procCpuPercent=" + this.procCpuPercent + ", procCpuTotal=" + this.procCpuTotal + ", procJvmMaxMem=" + this.procJvmMaxMem + ", procJvmTotalMem=" + this.procJvmTotalMem + ", procJvmFreeMem=" + this.procJvmFreeMem + ", procJvmThreads=" + this.procJvmThreads + ", SystemLoadAverage=" + this.SystemLoadAverage + ", netStat=" + this.netStat + ", diskInfoList=" + this.diskInfoList + ", netInfInfoList=" + this.netInfInfoList + ", cpuInfoList=" + this.cpuInfoList + ", groupName=" + this.groupName + ", nodeName=" + this.nodeName + ", sumOfRxSpeedPerSec=" + this.sumOfRxSpeedPerSec + ", sumOfTxSpeedPerSec=" + this.sumOfTxSpeedPerSec + "]";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\nodeperf\NodePerformance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */