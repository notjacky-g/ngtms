/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Properties;
/*    */ import org.quartz.spi.JobFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.scheduling.TaskScheduler;
/*    */ import org.springframework.scheduling.annotation.EnableScheduling;
/*    */ import org.springframework.scheduling.annotation.SchedulingConfigurer;
/*    */ import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
/*    */ import org.springframework.scheduling.config.ScheduledTaskRegistrar;
/*    */ import org.springframework.scheduling.quartz.SchedulerFactoryBean;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ @EnableScheduling
/*    */ public class SchedulerConfig
/*    */   implements SchedulingConfigurer
/*    */ {
/*    */   private static final String DEFAULT_CONF_FM_QUARTZ_PROPERTIES = "conf/fmQuartz.properties";
/*    */   private static final String SYSTEM_FM_QUARTZ_PROPERTIES_PATH = "fm.quartzPropertiesPath";
/*    */   private static final int SCHEDULER_POOL_SIZE = 8;
/*    */   @Autowired
/*    */   private ApplicationContext applicationContext;
/*    */   
/*    */   public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
/* 50 */     taskRegistrar.setScheduler(taskScheduler());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Bean
/*    */   public TaskScheduler taskScheduler() {
/* 57 */     ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
/* 58 */     taskScheduler.setPoolSize(8);
/* 59 */     taskScheduler.setThreadNamePrefix("Hcce.Scheduler");
/* 60 */     return (TaskScheduler)taskScheduler;
/*    */   }
/*    */ 
/*    */   
/*    */   @Bean
/*    */   public SchedulerFactoryBean scheduler() throws IOException {
/* 66 */     SchedulerFactoryBean bean = new SchedulerFactoryBean();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 72 */     AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
/* 73 */     jobFactory.setApplicationContext(this.applicationContext);
/* 74 */     bean.setJobFactory((JobFactory)jobFactory);
/*    */     
/* 76 */     bean.setWaitForJobsToCompleteOnShutdown(true);
/* 77 */     bean.setOverwriteExistingJobs(true);
/* 78 */     Properties quartzProperties = quartzProperties();
/*    */     
/* 80 */     bean.setSchedulerName(quartzProperties.getProperty("org.quartz.scheduler.instanceName"));
/* 81 */     bean.setQuartzProperties(quartzProperties);
/*    */     
/* 83 */     return bean;
/*    */   }
/*    */   
/*    */   @Bean
/*    */   public Properties quartzProperties() throws IOException {
/* 88 */     Properties quartzProperties = null;
/*    */ 
/*    */ 
/*    */     
/* 92 */     InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream(
/* 93 */         System.getProperty("fm.quartzPropertiesPath", "conf/fmQuartz.properties"));
/*    */     
/* 95 */     quartzProperties = new Properties();
/* 96 */     quartzProperties.load(fileInputStream);
/*    */     
/* 98 */     return quartzProperties;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\SchedulerConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */