/*     */ package com.hwacom.ngtms.hcce.fme.controller.fm;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.function.BiConsumer;
/*     */ import org.quartz.CronScheduleBuilder;
/*     */ import org.quartz.Job;
/*     */ import org.quartz.JobBuilder;
/*     */ import org.quartz.JobDataMap;
/*     */ import org.quartz.JobDetail;
/*     */ import org.quartz.JobKey;
/*     */ import org.quartz.ScheduleBuilder;
/*     */ import org.quartz.Scheduler;
/*     */ import org.quartz.SchedulerException;
/*     */ import org.quartz.Trigger;
/*     */ import org.quartz.TriggerBuilder;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class FmScheduleSupport
/*     */ {
/*  29 */   private static Logger logger = LoggerFactory.getLogger(FmScheduleSupport.class);
/*     */   
/*  31 */   private final ConcurrentHashMap<String, JobKey> jobKeyMap = new ConcurrentHashMap<>();
/*     */   
/*  33 */   private final ConcurrentHashMap<JobKey, JobDetail> jobDetailMap = new ConcurrentHashMap<>();
/*     */   
/*     */   @Autowired
/*     */   protected Scheduler scheduler;
/*     */ 
/*     */   
/*     */   protected void deleteAllJob() {
/*  40 */     for (JobKey jobKey : this.jobDetailMap.keySet()) {
/*     */       try {
/*  42 */         if (!this.scheduler.isShutdown()) {
/*  43 */           logger.info("Delete job: {}", jobKey);
/*  44 */           this.scheduler.deleteJob(jobKey);
/*     */         } 
/*  46 */       } catch (Exception ex) {
/*  47 */         logger.error("Failed to delete job:{}", jobKey);
/*     */       } 
/*     */     } 
/*  50 */     this.jobDetailMap.clear();
/*     */   }
/*     */   
/*     */   public void deleteJob(JobKey jobKey) {
/*  54 */     if (jobKey == null)
/*     */       return;  try {
/*  56 */       if (!this.scheduler.isShutdown()) this.scheduler.deleteJob(jobKey); 
/*  57 */     } catch (Exception ex) {
/*  58 */       logger.error("Failed to delete job:{}", jobKey);
/*     */     } 
/*  60 */     this.jobDetailMap.remove(jobKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public JobKey scheduleJob(Class<? extends Job> jobClass, String cronExpression) throws SchedulerException {
/*  65 */     return scheduleJob(jobClass, (JobDataMap)null, cronExpression, new Date());
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
/*     */   public JobKey scheduleJob(Class<? extends Job> jobClass, JobDataMap dataMap, String cronExpression) throws SchedulerException {
/*  78 */     return scheduleJob(jobClass, dataMap, cronExpression, new Date());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scheduleReschedulableJob(String dynamicConfigName, Class<? extends Job> jobClass, JobDataMap dataMap, String cronExpression) throws SchedulerException {
/*  87 */     JobKey jobKey = scheduleJob(jobClass, dataMap, cronExpression);
/*  88 */     if (jobKey == null) {
/*  89 */       logger.warn("JobKey is null. dynamicConfigName: '{}', jobClass: '{}'", dynamicConfigName, jobClass
/*     */ 
/*     */           
/*  92 */           .getName());
/*     */     } else {
/*  94 */       this.jobKeyMap.put(dynamicConfigName, jobKey);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void rescheduleJob(String dynamicConfigName, String cronExpression) {
/*  99 */     rescheduleJob(dynamicConfigName, (jobDetail, jobDataMap) -> {
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/*     */             scheduleReschedulableJob(paramString1, jobDetail.getJobClass(), jobDataMap, paramString2);
/* 105 */           } catch (SchedulerException e) {
/*     */             logger.error("Reschedule Job failed!", (Throwable)e);
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   private void rescheduleJob(String dynamicConfigName, BiConsumer<JobDetail, JobDataMap> consumer) {
/* 112 */     if (this.jobKeyMap.containsKey(dynamicConfigName)) {
/* 113 */       JobKey jobKey = this.jobKeyMap.get(dynamicConfigName);
/* 114 */       JobDetail jobDetail = this.jobDetailMap.get(jobKey);
/* 115 */       deleteJob(jobKey);
/* 116 */       JobDataMap jobDataMap = jobDetail.getJobDataMap();
/* 117 */       consumer.accept(jobDetail, jobDataMap.isEmpty() ? null : jobDataMap);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void cancelSchedule(String dynamicConfigName) {
/* 122 */     JobKey jobKey = this.jobKeyMap.remove(dynamicConfigName);
/* 123 */     deleteJob(jobKey);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scheduleReschedulableJob(String dynamicConfigName, Class<? extends Job> jobClass, JobDataMap dataMap, String cronExpression, String jobKeyName) throws SchedulerException {
/* 133 */     JobBuilder builder = JobBuilder.newJob(jobClass).withIdentity(jobKeyName, getFmeName());
/* 134 */     if (dataMap != null) {
/* 135 */       builder.usingJobData(dataMap);
/*     */     }
/* 137 */     JobDetail jobDetail = builder.build();
/*     */     
/* 139 */     JobKey jobKey = scheduleJob(jobDetail, (ScheduleBuilder<?>)CronScheduleBuilder.cronSchedule(cronExpression), new Date());
/* 140 */     this.jobKeyMap.put(dynamicConfigName, jobKey);
/*     */   }
/*     */   
/*     */   public void rescheduleJob(String dynamicConfigName, String cronExpression, String jobKeyName) {
/* 144 */     rescheduleJob(dynamicConfigName, (jobDetail, jobDataMap) -> {
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/*     */             scheduleReschedulableJob(paramString1, jobDetail.getJobClass(), jobDataMap, paramString2, paramString3);
/* 150 */           } catch (SchedulerException e) {
/*     */             logger.error("Reschedule Job failed!", (Throwable)e);
/*     */           } 
/*     */         });
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
/*     */   public JobKey scheduleJob(Class<? extends Job> jobClass, JobDataMap dataMap, String cronExpression, Date triggerStartTime) throws SchedulerException {
/* 170 */     return scheduleJob(jobClass, dataMap, 
/* 171 */         (ScheduleBuilder<?>)CronScheduleBuilder.cronSchedule(cronExpression), triggerStartTime);
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
/*     */   public JobKey scheduleJob(Class<? extends Job> jobClass, JobDataMap dataMap, ScheduleBuilder<?> scheduleBuilder) throws SchedulerException {
/* 184 */     return scheduleJob(jobClass, dataMap, scheduleBuilder, new Date());
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
/*     */   public JobKey scheduleJob(Class<? extends Job> jobClass, JobDataMap dataMap, ScheduleBuilder<?> scheduleBuilder, Date triggerStartTime) throws SchedulerException {
/* 202 */     JobBuilder builder = JobBuilder.newJob(jobClass).withIdentity(jobClass.getName(), getFmeName());
/* 203 */     if (dataMap != null) {
/* 204 */       builder.usingJobData(dataMap);
/*     */     }
/* 206 */     JobDetail jobDetail = builder.build();
/*     */     
/* 208 */     return scheduleJob(jobDetail, scheduleBuilder, triggerStartTime);
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
/*     */   public JobKey scheduleJob(JobDetail jobDetail, ScheduleBuilder<?> scheduleBuilder, Date triggerStartTime) throws SchedulerException {
/* 221 */     if (this.scheduler.isShutdown()) {
/* 222 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 229 */     Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobDetail.getKey().getName(), getFmeName()).withSchedule(scheduleBuilder).startAt(triggerStartTime).build();
/* 230 */     if (this.scheduler.checkExists(jobDetail.getKey())) {
/* 231 */       logger.warn("Duplicated Job Key: '{}', cancel adding the job into schedule", jobDetail
/* 232 */           .getKey());
/* 233 */       return null;
/*     */     } 
/* 235 */     if (JobBase.class.isAssignableFrom(jobDetail.getJobClass())) {
/* 236 */       jobDetail.getJobDataMap().put("fmMainBase", this);
/*     */     }
/*     */     
/* 239 */     this.scheduler.scheduleJob(jobDetail, trigger);
/*     */     
/* 241 */     this.jobDetailMap.put(jobDetail.getKey(), jobDetail);
/*     */     
/* 243 */     return jobDetail.getKey();
/*     */   }
/*     */   
/*     */   public abstract String getFmeName();
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\controller\fm\FmScheduleSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */