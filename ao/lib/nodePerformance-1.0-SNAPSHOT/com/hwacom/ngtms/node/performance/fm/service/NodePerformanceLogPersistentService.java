/*     */ package com.hwacom.ngtms.node.performance.fm.service;
/*     */ 
/*     */ import com.hwacom.ngtms.base.nodeperf.DiskInfo;
/*     */ import com.hwacom.ngtms.base.nodeperf.NodePerformance;
/*     */ import com.hwacom.ngtms.base.sysperflog.service.SysPerfLog;
/*     */ import com.hwacom.ngtms.base.sysperflog.service.SysPerfLogService;
/*     */ import com.hwacom.ngtms.node.performance.fm.model.NodePerformanceLog;
/*     */ import com.hwacom.ngtms.node.performance.fm.repository.NodePerformanceLogRepository;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.function.Consumer;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class NodePerformanceLogPersistentService
/*     */ {
/*  27 */   private static Logger logger = LoggerFactory.getLogger(NodePerformanceLogPersistentService.class);
/*     */   @Autowired
/*     */   private NodePerformanceLogRepository nodePerformanceLogRepository;
/*     */   @Autowired
/*     */   private SysPerfLogService sysPerfLogService;
/*     */   
/*     */   public void writeSysPerfLog(NodePerformance nodePerformance, Consumer<SysPerfLog> consumer) {
/*  34 */     if (this.sysPerfLogService == null) {
/*  35 */       return;
/*     */     }
/*  37 */     double[] loadAverage = nodePerformance.getLoadAverage();
/*     */     
/*  39 */     SysPerfLog sysPerfLog = this.sysPerfLogService.createSysPerfLog(nodePerformance
/*  40 */       .getGroupName(), nodePerformance
/*  41 */       .getNodeName(), nodePerformance
/*  42 */       .getTimestamp().getTime());
/*  43 */     StringBuilder description = new StringBuilder();
/*     */     
/*  45 */     if (loadAverage != null) {
/*  46 */       double d1 = loadAverage[0] * 100.0D;
/*  47 */       sysPerfLog.addCounterRound("cupLoadAv1Min", d1, 2);
/*  48 */       sysPerfLog.addCounterRound("cupLoadAv5Min", loadAverage[1] * 100.0D, 2);
/*  49 */       sysPerfLog.addCounterRound("cupLoadAv15Min", loadAverage[2] * 100.0D, 2);
/*     */     }
/*     */     else {
/*  52 */       d1 = (1.0D - nodePerformance.getIdle()) * 100.0D;
/*  53 */       sysPerfLog.addCounterRound("cupLoadAv1Min", d1, 2);
/*     */     }
/*     */     
/*  56 */     if (d1 > this.sysPerfLogService.getAlarmThresholdDouble("NodePerformance.cupLoadAv1Min", Double.MAX_VALUE))
/*     */     {
/*  58 */       description.append("[cupLoadAv1Min too high] ");
/*  59 */       sysPerfLog.issueAlarm(true);
/*     */     }
/*     */     
/*  62 */     double d1 = nodePerformance.getMemUsedPercent();
/*  63 */     sysPerfLog.addCounterRound("memUsed", d1, 2);
/*     */     
/*  65 */     if (d1 > this.sysPerfLogService.getAlarmThresholdDouble("NodePerformance.memUsed", Double.MAX_VALUE)) {
/*  66 */       description.append("[memUsed too high] ");
/*  67 */       sysPerfLog.issueAlarm(true);
/*     */     }
/*  69 */     sysPerfLog.addCounter("totalProcesses", Long.valueOf(nodePerformance.getTotalProcesses()));
/*  70 */     sysPerfLog.addCounter("totalThreads", Long.valueOf(nodePerformance.getTotalThreads()));
/*  71 */     for (DiskInfo diskInfo : nodePerformance.getDiskInfoList()) {
/*  72 */       d1 = diskInfo.getUsePercent() * 100.0D;
/*  73 */       sysPerfLog.addCounterRound("disk:" + diskInfo.getDirName(), d1, 2);
/*     */       
/*  75 */       if (d1 > this.sysPerfLogService.getAlarmThresholdDouble("NodePerformance.diskUsed", Double.MAX_VALUE))
/*     */       {
/*  77 */         description.append("[" + diskInfo.getDirName() + " usage too high] ");
/*  78 */         sysPerfLog.issueAlarm(true);
/*     */       }
/*     */     }
/*  81 */     if (description.length() > 0) sysPerfLog.setDescription(description.toString());
/*  82 */     consumer.accept(sysPerfLog);
/*  83 */     sysPerfLog.write();
/*     */   }
/*     */   
/*     */   public void saveNodePerformanceLog(NodePerformance nodePerformance) {
/*  87 */     if (this.nodePerformanceLogRepository == null) {
/*  88 */       return;
/*     */     }
/*  90 */     NodePerformanceLog nodePerformanceLog = new NodePerformanceLog();
/*  91 */     nodePerformanceLog.setGroupName(nodePerformance.getGroupName());
/*  92 */     nodePerformanceLog.setNodeName(nodePerformance.getNodeName());
/*     */     
/*  94 */     if (nodePerformance.getLoadAverage() != null)
/*  95 */       nodePerformanceLog.setCpuLoadAverage(Double.valueOf(nodePerformance.getLoadAverage()[0])); else
/*  96 */       nodePerformanceLog.setCpuLoadAverage(Double.valueOf(1.0D - nodePerformance.getIdle()));
/*  97 */     nodePerformanceLog.setMemUsedPercent(Double.valueOf(nodePerformance.getMemUsedPercent()));
/*  98 */     Map<String, Double> diskUsageMap = new HashMap();
/*  99 */     for (DiskInfo di : nodePerformance.getDiskInfoList()) {
/* 100 */       diskUsageMap.put(di.getDirName(), Double.valueOf(di.getUsePercent()));
/*     */     }
/* 102 */     nodePerformanceLog.setDiskUsage(diskUsageMap);
/* 103 */     nodePerformanceLog.setCpuUsage(nodePerformance.getCpuUsage());
/* 104 */     nodePerformanceLog.setSumOfRxSpeedPerSec(nodePerformance.getSumOfRxSpeedPerSec());
/* 105 */     nodePerformanceLog.setSumOfTxSpeedPerSec(nodePerformance.getSumOfTxSpeedPerSec());
/* 106 */     nodePerformanceLog.setRecordTime(new Date());
/* 107 */     this.nodePerformanceLogRepository.save(nodePerformanceLog);
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nodePerformance-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\node\performance\fm\service\NodePerformanceLogPersistentService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */