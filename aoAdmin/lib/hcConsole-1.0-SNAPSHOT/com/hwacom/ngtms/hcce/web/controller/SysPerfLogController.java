/*     */ package com.hwacom.ngtms.hcce.web.controller;
/*     */ 
/*     */ import com.hazelcast.config.Config;
/*     */ import com.hazelcast.core.HazelcastInstance;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.query.EntryObject;
/*     */ import com.hazelcast.query.Predicate;
/*     */ import com.hazelcast.query.PredicateBuilder;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.sysperflog.service.SysPerfLog;
/*     */ import com.hwacom.ngtms.hcce.core.NodeManager;
/*     */ import com.hwacom.ngtms.hcce.hz.HzDistObjRegister;
/*     */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*     */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*     */ import java.io.Serializable;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMethod;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ import org.springframework.web.servlet.ModelAndView;
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
/*     */ @RestController
/*     */ @RequestMapping({"/sysPerfLog"})
/*     */ public class SysPerfLogController
/*     */ {
/*  44 */   SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
/*     */   @Autowired
/*     */   private NodeManager nodeManager;
/*     */   @Autowired
/*     */   private Config hazelcastConfig;
/*     */   
/*     */   @RequestMapping
/*     */   public ModelAndView nccConsole() {
/*  52 */     ModelAndView model = new ModelAndView();
/*  53 */     model.setViewName("sysPerfLog");
/*  54 */     model.addObject("hcceEnv", this.hcceEnv);
/*  55 */     return model;
/*     */   }
/*     */   
/*     */   @Autowired
/*     */   private HcceEnv hcceEnv;
/*     */   
/*     */   @RequestMapping(value = {"/sidebarData"}, method = {RequestMethod.GET})
/*     */   public HashMap<String, Object> sidebarData() {
/*  63 */     HazelcastInstance hazelcastInstance = HzUtils.getHzInstance();
/*  64 */     HashMap<String, Object> result = new HashMap<>();
/*  65 */     result.put("timestamp", this.sdFormat.format(new Date()));
/*  66 */     if (hazelcastInstance != null) {
/*  67 */       IMap<String, SysPerfLog> sysPerfLogMap = HzUtils.getMap((HzDistObjEnum)HzMap.SysPerfLog);
/*  68 */       HashSet<String> moduleSet = new HashSet<>();
/*  69 */       for (String key : sysPerfLogMap.keySet()) {
/*  70 */         int i = key.indexOf('.');
/*  71 */         if (i > 0) {
/*  72 */           moduleSet.add(key.substring(0, i));
/*     */         }
/*     */       } 
/*  75 */       String[] moduleNames = new String[moduleSet.size()];
/*  76 */       moduleSet.toArray((Object[])moduleNames);
/*  77 */       Arrays.sort((Object[])moduleNames);
/*  78 */       result.put("moduleNames", moduleNames);
/*     */     } 
/*  80 */     return result;
/*     */   }
/*     */   
/*     */   @Autowired
/*     */   private HzDistObjRegister hzDistObjRegister;
/*     */   
/*     */   @RequestMapping(value = {"/moduleLogs"}, method = {RequestMethod.GET})
/*     */   public HashMap<String, Object> moduleLogs(String name) {
/*  88 */     HazelcastInstance hazelcastInstance = HzUtils.getHzInstance();
/*  89 */     HashMap<String, Object> result = new HashMap<>();
/*  90 */     result.put("timestamp", this.sdFormat.format(new Date()));
/*  91 */     if (hazelcastInstance != null) {
/*  92 */       IMap<String, SysPerfLog> sysPerfLogMap = HzUtils.getMap((HzDistObjEnum)HzMap.SysPerfLog);
/*  93 */       EntryObject e = (new PredicateBuilder()).getEntryObject();
/*  94 */       PredicateBuilder predicate = e.get("module").equal(name);
/*  95 */       Collection<SysPerfLog> sysPerfLogs = sysPerfLogMap.values((Predicate)predicate);
/*  96 */       SysPerfLogDto[] sysPerfLogDtos = new SysPerfLogDto[sysPerfLogs.size()];
/*  97 */       int i = 0;
/*  98 */       for (SysPerfLog sysPerfLog : sysPerfLogs) {
/*  99 */         SysPerfLogDto sysPerfLogDto = new SysPerfLogDto();
/* 100 */         sysPerfLogDtos[i] = sysPerfLogDto;
/* 101 */         sysPerfLogDto.setId(sysPerfLog.getId());
/* 102 */         sysPerfLogDto.setModule(sysPerfLog.getModule());
/* 103 */         sysPerfLogDto.setJobName(sysPerfLog.getJobName());
/* 104 */         sysPerfLogDto.setDescription(sysPerfLog.getDescription());
/* 105 */         if (sysPerfLog.getStartTime() > 0L)
/* 106 */           sysPerfLogDto.setStartTime(this.sdFormat.format(new Date(sysPerfLog.getStartTime()))); 
/* 107 */         if (sysPerfLog.getEndTime() > 0L)
/* 108 */           sysPerfLogDto.setEndTime(this.sdFormat.format(new Date(sysPerfLog.getEndTime()))); 
/* 109 */         LinkedHashMap<String, String> counterMap = new LinkedHashMap<>();
/* 110 */         sysPerfLogDto.setCounterMap(counterMap);
/* 111 */         for (String key : sysPerfLog.getCounterMap().keySet()) {
/* 112 */           Serializable value = (Serializable)sysPerfLog.getCounterMap().get(key);
/* 113 */           counterMap.put(key, value.toString());
/*     */         } 
/* 115 */         sysPerfLogDto.setIssueAlarm(sysPerfLog.isIssueAlarm());
/* 116 */         i++;
/*     */       } 
/* 118 */       result.put("sysPerfLogs", sysPerfLogDtos);
/*     */     } 
/* 120 */     return result;
/*     */   }
/*     */   
/*     */   static class SysPerfLogDto {
/*     */     private long id;
/*     */     private String module;
/*     */     private String jobName;
/*     */     private String description;
/*     */     private String startTime;
/*     */     private String endTime;
/* 130 */     private LinkedHashMap<String, String> counterMap = new LinkedHashMap<>();
/*     */     private boolean issueAlarm;
/*     */     
/*     */     public long getId() {
/* 134 */       return this.id;
/*     */     }
/*     */     
/*     */     public void setId(long id) {
/* 138 */       this.id = id;
/*     */     }
/*     */     
/*     */     public String getModule() {
/* 142 */       return this.module;
/*     */     }
/*     */     
/*     */     public void setModule(String module) {
/* 146 */       this.module = module;
/*     */     }
/*     */     
/*     */     public String getJobName() {
/* 150 */       return this.jobName;
/*     */     }
/*     */     
/*     */     public void setJobName(String jobName) {
/* 154 */       this.jobName = jobName;
/*     */     }
/*     */     
/*     */     public String getDescription() {
/* 158 */       return this.description;
/*     */     }
/*     */     
/*     */     public void setDescription(String description) {
/* 162 */       this.description = description;
/*     */     }
/*     */     
/*     */     public String getStartTime() {
/* 166 */       return this.startTime;
/*     */     }
/*     */     
/*     */     public void setStartTime(String startTime) {
/* 170 */       this.startTime = startTime;
/*     */     }
/*     */     
/*     */     public String getEndTime() {
/* 174 */       return this.endTime;
/*     */     }
/*     */     
/*     */     public void setEndTime(String endTime) {
/* 178 */       this.endTime = endTime;
/*     */     }
/*     */     
/*     */     public LinkedHashMap<String, String> getCounterMap() {
/* 182 */       return this.counterMap;
/*     */     }
/*     */     
/*     */     public void setCounterMap(LinkedHashMap<String, String> counterMap) {
/* 186 */       this.counterMap = counterMap;
/*     */     }
/*     */     
/*     */     public boolean isIssueAlarm() {
/* 190 */       return this.issueAlarm;
/*     */     }
/*     */     
/*     */     public void setIssueAlarm(boolean issueAlarm) {
/* 194 */       this.issueAlarm = issueAlarm;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\hcConsole-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\web\controller\SysPerfLogController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */