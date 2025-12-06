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
/*     */ import org.quartz.Scheduler;
/*     */ import org.quartz.SchedulerException;
/*     */ import org.quartz.Trigger;
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
/*     */ public abstract class JobBase
/*     */   implements Job
/*     */ {
/*  27 */   private final Logger logger = LoggerFactory.getLogger(JobBase.class);
/*     */   public static final String _FM_MAIN_BASE = "fmMainBase";
/*     */   protected FmeMainBase fmeMainBase;
/*     */   protected String fmeName;
/*  31 */   protected boolean noConcurrentExecution = true;
/*  32 */   protected boolean calulateTaskPerformance = true;
/*     */   protected JobDetail jobDetail;
/*     */   protected JobDataMap jobDataMap;
/*     */   protected TaskLoadingCalculator taskLoadingCalculator;
/*     */   
/*     */   /* Error */
/*     */   public void execute(JobExecutionContext context)
/*     */     throws JobExecutionException
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: invokeinterface 7 1 0
/*     */     //   7: putfield 8	com/hwacom/ngtms/hcce/fme/controller/fm/JobBase:jobDetail	Lorg/quartz/JobDetail;
/*     */     //   10: aload_0
/*     */     //   11: aload_0
/*     */     //   12: getfield 8	com/hwacom/ngtms/hcce/fme/controller/fm/JobBase:jobDetail	Lorg/quartz/JobDetail;
/*     */     //   15: invokeinterface 9 1 0
/*     */     //   20: putfield 10	com/hwacom/ngtms/hcce/fme/controller/fm/JobBase:jobDataMap	Lorg/quartz/JobDataMap;
/*     */     //   23: aload_0
/*     */     //   24: aload_0
/*     */     //   25: getfield 10	com/hwacom/ngtms/hcce/fme/controller/fm/JobBase:jobDataMap	Lorg/quartz/JobDataMap;
/*     */     //   28: ldc 11
/*     */     //   30: invokevirtual 12	org/quartz/JobDataMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   33: checkcast 13	com/hwacom/ngtms/hcce/fme/controller/fm/FmeMainBase
/*     */     //   36: putfield 14	com/hwacom/ngtms/hcce/fme/controller/fm/JobBase:fmeMainBase	Lcom/hwacom/ngtms/hcce/fme/controller/fm/FmeMainBase;
/*     */     //   39: aload_0
/*     */     //   40: aload_0
/*     */     //   41: getfield 14	com/hwacom/ngtms/hcce/fme/controller/fm/JobBase:fmeMainBase	Lcom/hwacom/ngtms/hcce/fme/controller/fm/FmeMainBase;
/*     */     //   44: invokevirtual 15	com/hwacom/ngtms/hcce/fme/controller/fm/FmeMainBase:getFmeName	()Ljava/lang/String;
/*     */     //   47: putfield 16	com/hwacom/ngtms/hcce/fme/controller/fm/JobBase:fmeName	Ljava/lang/String;
/*     */     //   50: aload_0
/*     */     //   51: getfield 14	com/hwacom/ngtms/hcce/fme/controller/fm/JobBase:fmeMainBase	Lcom/hwacom/ngtms/hcce/fme/controller/fm/FmeMainBase;
/*     */     //   54: invokevirtual 17	com/hwacom/ngtms/hcce/fme/controller/fm/FmeMainBase:checkFmKeepRunning	()Z
/*     */     //   57: ifeq +54 -> 111
/*     */     //   60: aload_0
/*     */     //   61: getfield 5	com/hwacom/ngtms/hcce/fme/controller/fm/JobBase:noConcurrentExecution	Z
/*     */     //   64: ifeq +12 -> 76
/*     */     //   67: aload_0
/*     */     //   68: aload_1
/*     */     //   69: invokespecial 18	com/hwacom/ngtms/hcce/fme/controller/fm/JobBase:isConcurrentExecution	(Lorg/quartz/JobExecutionContext;)Z
/*     */     //   72: ifeq +4 -> 76
/*     */     //   75: return
/*     */     //   76: aload_0
/*     */     //   77: getfield 14	com/hwacom/ngtms/hcce/fme/controller/fm/JobBase:fmeMainBase	Lcom/hwacom/ngtms/hcce/fme/controller/fm/FmeMainBase;
/*     */     //   80: invokevirtual 19	com/hwacom/ngtms/hcce/fme/controller/fm/FmeMainBase:incRunningTaskNo	()J
/*     */     //   83: pop2
/*     */     //   84: aload_0
/*     */     //   85: aload_1
/*     */     //   86: invokevirtual 20	com/hwacom/ngtms/hcce/fme/controller/fm/JobBase:executeInternal	(Lorg/quartz/JobExecutionContext;)V
/*     */     //   89: aload_0
/*     */     //   90: getfield 14	com/hwacom/ngtms/hcce/fme/controller/fm/JobBase:fmeMainBase	Lcom/hwacom/ngtms/hcce/fme/controller/fm/FmeMainBase;
/*     */     //   93: invokevirtual 21	com/hwacom/ngtms/hcce/fme/controller/fm/FmeMainBase:decRunningTaskNo	()J
/*     */     //   96: pop2
/*     */     //   97: goto +14 -> 111
/*     */     //   100: astore_2
/*     */     //   101: aload_0
/*     */     //   102: getfield 14	com/hwacom/ngtms/hcce/fme/controller/fm/JobBase:fmeMainBase	Lcom/hwacom/ngtms/hcce/fme/controller/fm/FmeMainBase;
/*     */     //   105: invokevirtual 21	com/hwacom/ngtms/hcce/fme/controller/fm/FmeMainBase:decRunningTaskNo	()J
/*     */     //   108: pop2
/*     */     //   109: aload_2
/*     */     //   110: athrow
/*     */     //   111: return
/*     */     // Line number table:
/*     */     //   Java source line #39	-> byte code offset #0
/*     */     //   Java source line #40	-> byte code offset #10
/*     */     //   Java source line #41	-> byte code offset #23
/*     */     //   Java source line #42	-> byte code offset #39
/*     */     //   Java source line #44	-> byte code offset #50
/*     */     //   Java source line #45	-> byte code offset #60
/*     */     //   Java source line #46	-> byte code offset #76
/*     */     //   Java source line #48	-> byte code offset #84
/*     */     //   Java source line #50	-> byte code offset #89
/*     */     //   Java source line #51	-> byte code offset #97
/*     */     //   Java source line #50	-> byte code offset #100
/*     */     //   Java source line #53	-> byte code offset #111
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	112	0	this	JobBase
/*     */     //   0	112	1	context	JobExecutionContext
/*     */     //   100	10	2	localObject	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   84	89	100	finally
/*     */   }
/*     */   
/*     */   public void executeInternal(JobExecutionContext context)
/*     */     throws JobExecutionException
/*     */   {
/*  56 */     long startTime = System.nanoTime();
/*  57 */     long timestamp = System.currentTimeMillis();
/*     */     try {
/*  59 */       if (this.calulateTaskPerformance)
/*     */       {
/*  61 */         this.taskLoadingCalculator = this.fmeMainBase.getTaskLoadingCalculator(getClass().getSimpleName());
/*  62 */         if (this.taskLoadingCalculator == null) {
/*  63 */           this.taskLoadingCalculator = new TaskLoadingCalculator();
/*  64 */           this.fmeMainBase.addTaskLoadingCalculator(
/*  65 */             getClass().getSimpleName(), this.taskLoadingCalculator);
/*     */         }
/*     */       }
/*  68 */       if (this.fmeMainBase.checkFmKeepRunning()) {
/*  69 */         init(this.jobDataMap);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  74 */       throw new JobExecutionException("Failed to initialize the job: " + this.jobDetail.getKey(), e);
/*     */     }
/*     */     try {
/*  77 */       if (this.fmeMainBase.checkFmKeepRunning()) {
/*  78 */         process();
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
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
/*     */   private boolean isConcurrentExecution(JobExecutionContext context)
/*     */   {
/*     */     try
/*     */     {
/* 103 */       List<JobExecutionContext> jobs = context.getScheduler().getCurrentlyExecutingJobs();
/* 104 */       for (JobExecutionContext job : jobs) {
/* 105 */         if ((job.getTrigger().equals(context.getTrigger())) && 
/* 106 */           (!job.getFireInstanceId().equals(context.getFireInstanceId()))) {
/* 107 */           if (this.logger.isDebugEnabled())
/* 108 */             this.logger.debug("There's another instance running, so leaving " + this);
/* 109 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (SchedulerException localSchedulerException) {}
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


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\controller\fm\JobBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */