/*     */ package com.hwacom.ngtms.hcce.fme.controller.fm;
/*     */ 
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*     */ import com.hwacom.ngtms.hcce.fme.controller.runtime.TaskLoadingCalculator;
/*     */ import java.util.List;
/*     */ import org.quartz.Job;
/*     */ import org.quartz.JobDataMap;
/*     */ import org.quartz.JobDetail;
/*     */ import org.quartz.JobExecutionContext;
/*     */ import org.quartz.JobExecutionException;
/*     */ import org.quartz.SchedulerException;
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
/*     */ public abstract class JobBase
/*     */   implements Job
/*     */ {
/*  27 */   private final Logger logger = LoggerFactory.getLogger(JobBase.class);
/*     */   
/*     */   public static final String _FM_MAIN_BASE = "fmMainBase";
/*     */   protected FmeMainBase fmeMainBase;
/*     */   protected String fmeName;
/*     */   protected boolean noConcurrentExecution = true;
/*     */   protected boolean calulateTaskPerformance = true;
/*     */   protected JobDetail jobDetail;
/*     */   protected JobDataMap jobDataMap;
/*     */   protected TaskLoadingCalculator taskLoadingCalculator;
/*     */   
/*     */   public void execute(JobExecutionContext context) throws JobExecutionException {
/*  39 */     this.jobDetail = context.getJobDetail();
/*  40 */     this.jobDataMap = this.jobDetail.getJobDataMap();
/*  41 */     this.fmeMainBase = (FmeMainBase)this.jobDataMap.get("fmMainBase");
/*  42 */     this.fmeName = this.fmeMainBase.getFmeName();
/*     */     
/*  44 */     if (this.fmeMainBase.checkFmKeepRunning()) {
/*  45 */       if (this.noConcurrentExecution && isConcurrentExecution(context))
/*  46 */         return;  this.fmeMainBase.incRunningTaskNo();
/*     */       try {
/*  48 */         executeInternal(context);
/*     */       } finally {
/*  50 */         this.fmeMainBase.decRunningTaskNo();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void executeInternal(JobExecutionContext context) throws JobExecutionException {
/*  56 */     long startTime = System.nanoTime();
/*  57 */     long timestamp = System.currentTimeMillis();
/*     */     try {
/*  59 */       if (this.calulateTaskPerformance) {
/*  60 */         this
/*  61 */           .taskLoadingCalculator = this.fmeMainBase.getTaskLoadingCalculator(getClass().getSimpleName());
/*  62 */         if (this.taskLoadingCalculator == null) {
/*  63 */           this.taskLoadingCalculator = new TaskLoadingCalculator();
/*  64 */           this.fmeMainBase.addTaskLoadingCalculator(
/*  65 */               getClass().getSimpleName(), this.taskLoadingCalculator);
/*     */         } 
/*     */       } 
/*  68 */       if (this.fmeMainBase.checkFmKeepRunning()) {
/*  69 */         init(this.jobDataMap);
/*     */       }
/*  71 */     } catch (Exception e) {
/*     */ 
/*     */       
/*  74 */       throw new JobExecutionException("Failed to initialize the job: " + this.jobDetail.getKey(), e);
/*     */     } 
/*     */     try {
/*  77 */       if (this.fmeMainBase.checkFmKeepRunning()) {
/*  78 */         process();
/*     */       }
/*  80 */     } catch (Exception e) {
/*     */       
/*  82 */       throw new JobExecutionException("Failed to process the job: " + this.jobDetail.getKey(), e);
/*     */     } 
/*  84 */     if (this.calulateTaskPerformance) {
/*  85 */       this.taskLoadingCalculator.setLastExeTime(timestamp, System.nanoTime() - startTime);
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
/*     */   private boolean isConcurrentExecution(JobExecutionContext context) {
/*     */     try {
/* 103 */       List<JobExecutionContext> jobs = context.getScheduler().getCurrentlyExecutingJobs();
/* 104 */       for (JobExecutionContext job : jobs) {
/* 105 */         if (job.getTrigger().equals(context.getTrigger()) && 
/* 106 */           !job.getFireInstanceId().equals(context.getFireInstanceId())) {
/* 107 */           if (this.logger.isDebugEnabled())
/* 108 */             this.logger.debug("There's another instance running, so leaving " + this); 
/* 109 */           return true;
/*     */         } 
/*     */       } 
/* 112 */     } catch (SchedulerException schedulerException) {}
/*     */ 
/*     */     
/* 115 */     return false;
/*     */   }
/*     */   
/*     */   public DynamicConfig getDynamicConfig(String configName) {
/* 119 */     return this.fmeMainBase.getDynaConfig(configName);
/*     */   }
/*     */   
/*     */   protected abstract void init(JobDataMap paramJobDataMap);
/*     */   
/*     */   protected abstract void process();
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\controller\fm\JobBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */