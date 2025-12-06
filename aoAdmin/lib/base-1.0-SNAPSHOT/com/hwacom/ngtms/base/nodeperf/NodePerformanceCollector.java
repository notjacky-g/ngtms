/*     */ package com.hwacom.ngtms.base.nodeperf;
/*     */ 
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import org.hyperic.sigar.CpuInfo;
/*     */ import org.hyperic.sigar.CpuPerc;
/*     */ import org.hyperic.sigar.FileSystem;
/*     */ import org.hyperic.sigar.FileSystemUsage;
/*     */ import org.hyperic.sigar.Mem;
/*     */ import org.hyperic.sigar.NetInterfaceStat;
/*     */ import org.hyperic.sigar.NetStat;
/*     */ import org.hyperic.sigar.NfsFileSystem;
/*     */ import org.hyperic.sigar.ProcCpu;
/*     */ import org.hyperic.sigar.ProcMem;
/*     */ import org.hyperic.sigar.ProcStat;
/*     */ import org.hyperic.sigar.Sigar;
/*     */ import org.hyperic.sigar.SigarException;
/*     */ import org.hyperic.sigar.SigarProxy;
/*     */ import org.hyperic.sigar.SigarProxyCache;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NodePerformanceCollector
/*     */ {
/*     */   private Sigar sigar;
/*     */   private SigarProxy proxy;
/*  40 */   private Map<String, NetInfInfo> lastNetInfInfoMap = new HashMap<>();
/*     */   
/*  42 */   private long lastTime = System.currentTimeMillis();
/*     */   
/*     */   public NodePerformanceCollector() throws SigarException {
/*  45 */     this.sigar = new Sigar();
/*  46 */     this.sigar.getUptime();
/*  47 */     this.proxy = SigarProxyCache.newInstance(this.sigar);
/*     */   }
/*     */   
/*     */   public Sigar getSigar() {
/*  51 */     return this.sigar;
/*     */   }
/*     */ 
/*     */   
/*     */   public NodePerformance getNodePerformance(String invalidDiskName, Optional<Long> optPid, BiConsumer<String, Exception> logger) {
/*  56 */     NodePerformance nodePerformance = new NodePerformance();
/*  57 */     nodePerformance.setTimestamp(new Date());
/*     */ 
/*     */     
/*     */     try {
/*  61 */       CpuInfo[] cpus = this.sigar.getCpuInfoList();
/*  62 */       List<CpuInfo> cpuInfoList = new ArrayList<>(cpus.length);
/*  63 */       for (CpuInfo cpu : cpus) {
/*  64 */         cpuInfoList.add(cpu);
/*     */       }
/*  66 */       nodePerformance.setCpuInfoList(cpuInfoList);
/*  67 */     } catch (Exception ex) {
/*  68 */       logger.accept("Failed to run sigar.getCpuInfoList", ex);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  73 */       CpuPerc cpuPerc = this.sigar.getCpuPerc();
/*  74 */       nodePerformance.setUser(cpuPerc.getUser());
/*  75 */       nodePerformance.setSys(cpuPerc.getSys());
/*  76 */       nodePerformance.setIdle(cpuPerc.getIdle());
/*  77 */       nodePerformance.setWait(cpuPerc.getWait());
/*  78 */       nodePerformance.setNice(cpuPerc.getNice());
/*  79 */       nodePerformance.setCombined(cpuPerc.getCombined());
/*  80 */       nodePerformance.setIrq(cpuPerc.getIrq());
/*  81 */     } catch (SigarException ex) {
/*  82 */       logger.accept("Failed to run sigar.getCpuPerc", ex);
/*     */     } 
/*     */     
/*     */     try {
/*  86 */       nodePerformance.setUptime(this.sigar.getUptime().getUptime());
/*  87 */     } catch (SigarException ex) {
/*  88 */       logger.accept("Failed to run sigar.getUptime", ex);
/*     */     } 
/*     */     
/*     */     try {
/*  92 */       nodePerformance.setLoadAverage(this.sigar.getLoadAverage());
/*  93 */     } catch (SigarException ex) {
/*  94 */       logger.accept("Failed to run sigar.getLoadAverage", ex);
/*     */     } 
/*     */     
/*     */     try {
/*  98 */       ProcStat procStat = this.sigar.getProcStat();
/*  99 */       nodePerformance.setTotalThreads(procStat.getThreads());
/* 100 */       nodePerformance.setTotalProcesses(procStat.getTotal());
/* 101 */     } catch (SigarException ex) {
/* 102 */       logger.accept("Failed to run sigar.getProcStat", ex);
/*     */     } 
/*     */     
/*     */     try {
/* 106 */       Mem mem = this.sigar.getMem();
/* 107 */       nodePerformance.setMemFree(mem.getFree());
/* 108 */       nodePerformance.setMemUsed(mem.getUsed());
/* 109 */       nodePerformance.setMemTotal(mem.getTotal());
/* 110 */       nodePerformance.setMemUsedPercent(mem.getUsedPercent());
/* 111 */     } catch (SigarException ex) {
/* 112 */       logger.accept("Failed to run sigar.getMem", ex);
/*     */     } 
/*     */     
/* 115 */     optPid.ifPresent(pid -> {
/*     */           paramNodePerformance.setProcId(pid.longValue());
/*     */           
/*     */           try {
/*     */             ProcMem procMem = this.sigar.getProcMem(pid.longValue());
/*     */             paramNodePerformance.setProcMemResident(procMem.getResident());
/*     */             paramNodePerformance.setProcMemTotoal(procMem.getSize());
/* 122 */           } catch (SigarException ex) {
/*     */             paramBiConsumer.accept("Failed to run sigar.getProcMem", ex);
/*     */           } 
/*     */           
/*     */           try {
/*     */             ProcCpu procCpu = this.sigar.getProcCpu(pid.longValue());
/*     */             
/*     */             paramNodePerformance.setProcCpuPercent(procCpu.getPercent());
/*     */             paramNodePerformance.setProcCpuTotal(procCpu.getTotal());
/* 131 */           } catch (SigarException ex) {
/*     */             paramBiConsumer.accept("Failed to run sigar.getProcCpu", ex);
/*     */           } 
/*     */         });
/* 135 */     String[] invalidDiskNames = invalidDiskName.split(",");
/* 136 */     Set<String> invalidNameSet = new HashSet<>();
/* 137 */     for (String diskName : invalidDiskNames) {
/* 138 */       invalidNameSet.add(diskName);
/*     */     }
/* 140 */     List<DiskInfo> diskInfoList = new ArrayList<>();
/*     */     
/*     */     try {
/* 143 */       FileSystem[] fslist = this.proxy.getFileSystemList();
/* 144 */       for (FileSystem fs : fslist) {
/*     */         
/* 146 */         if (fs instanceof NfsFileSystem) {
/* 147 */           NfsFileSystem nfs = (NfsFileSystem)fs;
/* 148 */           if (!nfs.ping()) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */         try {
/* 153 */           if (!invalidNameSet.contains(fs.getDevName())) {
/* 154 */             DiskInfo diskInfo = new DiskInfo();
/* 155 */             FileSystemUsage usage = this.sigar.getFileSystemUsage(fs.getDirName());
/* 156 */             if (usage.getTotal() > 0L)
/* 157 */             { diskInfo.setDevName(fs.getDevName());
/* 158 */               diskInfo.setDirName(fs.getDirName());
/* 159 */               diskInfo.setUsed(usage.getUsed());
/* 160 */               diskInfo.setAvail(usage.getAvail());
/* 161 */               diskInfo.setTotal(usage.getTotal());
/* 162 */               diskInfo.setUsePercent(usage.getUsePercent());
/* 163 */               diskInfoList.add(diskInfo); } 
/*     */           } 
/* 165 */         } catch (Exception ex) {
/* 166 */           logger.accept("Failed to run sigar.getFileSystemUsage, " + fs.getDirName(), ex);
/*     */         } 
/*     */       } 
/* 169 */     } catch (SigarException ex) {
/* 170 */       logger.accept("Failed to run sigar.getFileSystemList", ex);
/*     */     } 
/* 172 */     nodePerformance.setDiskInfoList(diskInfoList);
/*     */     
/* 174 */     List<NetInfInfo> netInfInfoList = new ArrayList<>();
/*     */     
/* 176 */     long sumOfRxSpeedPerSec = 0L;
/* 177 */     long sumOfTxSpeedPerSec = 0L;
/*     */     try {
/* 179 */       String[] ifNames = this.proxy.getNetInterfaceList();
/* 180 */       long timeDiff = System.currentTimeMillis() - this.lastTime;
/* 181 */       timeDiff /= 1000L;
/* 182 */       this.lastTime = System.currentTimeMillis();
/* 183 */       Set<String> nameSet = new HashSet<>();
/* 184 */       for (String ifName : ifNames) {
/* 185 */         if (!nameSet.contains(ifName)) {
/* 186 */           nameSet.add(ifName);
/* 187 */           NetInterfaceStat ifstat = this.sigar.getNetInterfaceStat(ifName);
/* 188 */           long rxBytes = ifstat.getRxBytes();
/* 189 */           long txBytes = ifstat.getTxBytes();
/* 190 */           if (rxBytes != 0L || txBytes != 0L)
/* 191 */           { NetInfInfo netInfInfo = new NetInfInfo();
/* 192 */             netInfInfo.setIfName(ifName);
/* 193 */             netInfInfo.setRxBytes(rxBytes);
/* 194 */             netInfInfo.setTxBytes(txBytes);
/* 195 */             NetInfInfo lastNetInfInfo = this.lastNetInfInfoMap.get(ifName);
/* 196 */             if (lastNetInfInfo == null) {
/* 197 */               this.lastNetInfInfoMap.put(ifName, netInfInfo);
/*     */             } else {
/* 199 */               netInfInfo.setLastRxBytes(lastNetInfInfo.getRxBytes());
/* 200 */               netInfInfo.setLastTxBytes(lastNetInfInfo.getTxBytes());
/* 201 */               netInfInfo.setRxSpeedPerSec(netInfInfo.getRxDiffBytes() / timeDiff);
/* 202 */               netInfInfo.setTxSpeedPerSec(netInfInfo.getTxDiffBytes() / timeDiff);
/* 203 */               sumOfRxSpeedPerSec += netInfInfo.getRxSpeedPerSec();
/* 204 */               sumOfTxSpeedPerSec += netInfInfo.getTxSpeedPerSec();
/* 205 */               this.lastNetInfInfoMap.put(ifName, netInfInfo);
/*     */             } 
/* 207 */             netInfInfoList.add(netInfInfo); } 
/*     */         } 
/*     */       } 
/* 210 */     } catch (SigarException ex) {
/* 211 */       logger.accept("Failed to run sigar.getNetInterfaceStat", ex);
/*     */     } 
/* 213 */     nodePerformance.setNetInfInfoList(netInfInfoList);
/* 214 */     nodePerformance.setSumOfRxSpeedPerSec(sumOfRxSpeedPerSec);
/* 215 */     nodePerformance.setSumOfTxSpeedPerSec(sumOfTxSpeedPerSec);
/* 216 */     NetStat myNetStat = new NetStat();
/*     */     try {
/* 218 */       NetStat netStat = this.sigar.getNetStat();
/* 219 */       myNetStat.setAllInboundTotal(netStat.getAllInboundTotal());
/* 220 */       myNetStat.setAllOutboundTotal(netStat.getAllOutboundTotal());
/* 221 */       myNetStat.setTcpBound(netStat.getTcpBound());
/* 222 */       myNetStat.setTcpClose(netStat.getTcpClose());
/* 223 */       myNetStat.setTcpCloseWait(netStat.getTcpCloseWait());
/* 224 */       myNetStat.setTcpClosing(netStat.getTcpClosing());
/* 225 */       myNetStat.setTcpEstablished(netStat.getTcpEstablished());
/* 226 */       myNetStat.setTcpFinWait1(netStat.getTcpFinWait1());
/* 227 */       myNetStat.setTcpFinWait2(netStat.getTcpFinWait2());
/* 228 */       myNetStat.setTcpIdle(netStat.getTcpIdle());
/* 229 */       myNetStat.setTcpInboundTotal(netStat.getTcpInboundTotal());
/* 230 */       myNetStat.setTcpLastAck(netStat.getTcpLastAck());
/* 231 */       myNetStat.setTcpListen(netStat.getTcpListen());
/* 232 */       myNetStat.setTcpOutboundTotal(netStat.getTcpOutboundTotal());
/*     */     }
/* 234 */     catch (SigarException ex) {
/* 235 */       logger.accept("Failed to run sigar.getNetStat", ex);
/*     */     } 
/*     */     
/* 238 */     nodePerformance.setNetStat(myNetStat);
/*     */ 
/*     */     
/* 241 */     nodePerformance.setProcJvmMaxMem(Runtime.getRuntime().maxMemory());
/* 242 */     nodePerformance.setProcJvmTotalMem(Runtime.getRuntime().totalMemory());
/* 243 */     nodePerformance.setProcJvmFreeMem(Runtime.getRuntime().freeMemory());
/* 244 */     nodePerformance.setProcJvmThreads(ManagementFactory.getThreadMXBean().getThreadCount());
/* 245 */     nodePerformance.setSystemLoadAverage(
/* 246 */         ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage());
/*     */     
/* 248 */     return nodePerformance;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\nodeperf\NodePerformanceCollector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */