/*     */ package com.hwacom.ngtms.base.sysperflog.service;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
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
/*     */ public class SysPerfLog
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private long id;
/*     */   private String module;
/*     */   private String jobName;
/*     */   private String description;
/*     */   private long startTime;
/*     */   private long endTime;
/*  28 */   private LinkedHashMap<String, Serializable> counterMap = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean issueAlarm;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient SysPerfLogService sysPerfLogService;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static SysPerfLog createSysPerfLog(SysPerfLogService sysPerfLogService, long id, String module, String jobName, long startTime, long endTime) {
/*  50 */     SysPerfLog sysPerfLog = new SysPerfLog();
/*  51 */     sysPerfLog.sysPerfLogService = sysPerfLogService;
/*  52 */     sysPerfLog.module = module;
/*  53 */     sysPerfLog.jobName = jobName;
/*  54 */     sysPerfLog.startTime = startTime;
/*  55 */     sysPerfLog.endTime = endTime;
/*  56 */     sysPerfLog.id = id;
/*  57 */     return sysPerfLog;
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
/*     */   public SysPerfLog setDescription(String description) {
/*  70 */     this.description = description;
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SysPerfLog addCounter(String name, Serializable value) {
/*  81 */     this.counterMap.put(name, value);
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SysPerfLog addCounterRound(String name, double value, int places) {
/*  93 */     this.counterMap.put(name, 
/*  94 */         Double.valueOf((new BigDecimal(value)).setScale(places, RoundingMode.HALF_UP).doubleValue()));
/*  95 */     return this;
/*     */   }
/*     */   
/*     */   public Serializable getCounter(String name) {
/*  99 */     return this.counterMap.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SysPerfLog issueAlarm(boolean issue) {
/* 109 */     if (!this.issueAlarm) this.issueAlarm = issue; 
/* 110 */     return this;
/*     */   }
/*     */   
/*     */   public void write() {
/* 114 */     if (this.sysPerfLogService != null) this.sysPerfLogService.writeSysPerfLog(this); 
/*     */   }
/*     */   
/*     */   public String getModule() {
/* 118 */     return this.module;
/*     */   }
/*     */   
/*     */   public String getJobName() {
/* 122 */     return this.jobName;
/*     */   }
/*     */   
/*     */   public long getStartTime() {
/* 126 */     return this.startTime;
/*     */   }
/*     */   
/*     */   public long getEndTime() {
/* 130 */     return this.endTime;
/*     */   }
/*     */   
/*     */   public Map<String, Serializable> getCounterMap() {
/* 134 */     return this.counterMap;
/*     */   }
/*     */   
/*     */   public boolean isIssueAlarm() {
/* 138 */     return this.issueAlarm;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 142 */     return this.description;
/*     */   }
/*     */   
/*     */   public long getId() {
/* 146 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 151 */     return "SysPerfLog [id=" + this.id + ", module=" + this.module + ", jobName=" + this.jobName + ", description=" + this.description + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", counterMap=" + this.counterMap + ", issueAlarm=" + this.issueAlarm + "]";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\sysperflog\service\SysPerfLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */