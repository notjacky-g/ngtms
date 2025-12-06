/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import com.hwacom.ngtms.base.util.ResolveString;
/*    */ import com.zaxxer.hikari.HikariConfig;
/*    */ import com.zaxxer.hikari.HikariDataSource;
/*    */ import java.io.IOException;
/*    */ import java.util.Properties;
/*    */ import javax.sql.DataSource;
/*    */ import org.activiti.spring.SpringAsyncExecutor;
/*    */ import org.activiti.spring.SpringProcessEngineConfiguration;
/*    */ import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
/*    */ import org.activiti.spring.boot.ActivitiProperties;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.beans.factory.annotation.Qualifier;
/*    */ import org.springframework.boot.context.properties.EnableConfigurationProperties;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.core.env.Environment;
/*    */ import org.springframework.jdbc.datasource.DataSourceTransactionManager;
/*    */ import org.springframework.transaction.PlatformTransactionManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ @EnableConfigurationProperties({ActivitiProperties.class})
/*    */ public class ActivitiConfig
/*    */   extends AbstractProcessEngineAutoConfiguration
/*    */ {
/* 34 */   private static final Logger logger = LoggerFactory.getLogger(ActivitiConfig.class);
/*    */   @Autowired
/*    */   Environment environment;
/*    */   
/*    */   @Bean(name={"activitiDataSource"})
/*    */   public DataSource dataSource() {
/* 40 */     HikariConfig conf = new HikariConfig();
/* 41 */     conf.setDataSourceClassName(this.environment.getRequiredProperty("activiti.db.driver"));
/* 42 */     conf.addDataSourceProperty("url", 
/* 43 */       ResolveString.resolve(this.environment.getRequiredProperty("activiti.db.url")));
/* 44 */     conf.addDataSourceProperty("user", this.environment.getRequiredProperty("activiti.db.account"));
/* 45 */     conf.addDataSourceProperty("password", this.environment.getRequiredProperty("activiti.db.password"));
/* 46 */     conf.setPoolName("Activiti");
/* 47 */     conf.setAutoCommit(((Boolean)this.environment.getProperty("activiti.db.autoCommit", Boolean.class, Boolean.valueOf(true))).booleanValue());
/* 48 */     conf.setConnectionTestQuery(this.environment
/* 49 */       .getProperty("activiti.db.connectionTestQuery", "select 1 from dual"));
/* 50 */     conf.setMaximumPoolSize(
/* 51 */       ((Integer)this.environment.getRequiredProperty("activiti.db.maximumPoolSize", Integer.class)).intValue());
/* 52 */     conf.setMaxLifetime(((Long)this.environment.getProperty("activiti.db.maxLifetime", Long.class, Long.valueOf(1800000L))).longValue());
/* 53 */     conf.setConnectionTimeout(
/* 54 */       ((Long)this.environment.getProperty("activiti.db.connectionTimeout", Long.class, Long.valueOf(10000L))).longValue());
/* 55 */     logger.info("-------------Activiti Database Config-------------");
/* 56 */     logger.info("DataSource ClassName: {}", conf.getDataSourceClassName());
/* 57 */     logger.info("DataSource URL: {}", conf.getDataSourceProperties().getProperty("url"));
/* 58 */     logger.info("DataSource User: {}", conf.getDataSourceProperties().getProperty("user"));
/* 59 */     logger.info("Hikari AutoCommit: {}", Boolean.valueOf(conf.isAutoCommit()));
/* 60 */     logger.info("Hikari ConnectionTestQuery: {}", conf.getConnectionTestQuery());
/* 61 */     logger.info("Hikari MaximumPoolSize: {}", Integer.valueOf(conf.getMaximumPoolSize()));
/* 62 */     logger.info("Hikari MaxLifetime: {}", Long.valueOf(conf.getMaxLifetime()));
/* 63 */     logger.info("Hikari ConnectionTimeout: {}", Long.valueOf(conf.getConnectionTimeout()));
/* 64 */     logger.info("Hikari HealthCheck: {}", conf.getHealthCheckProperties());
/* 65 */     logger.info("-----------------------------------------");
/* 66 */     HikariDataSource ds = new HikariDataSource(conf);
/* 67 */     return ds;
/*    */   }
/*    */   
/*    */ 
/*    */   @Bean(name={"activitiTransactionManager"})
/*    */   public PlatformTransactionManager transactionManager(@Qualifier("activitiDataSource") DataSource dataSource)
/*    */   {
/* 74 */     return new DataSourceTransactionManager(dataSource);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Bean
/*    */   public SpringProcessEngineConfiguration springProcessEngineConfiguration(@Qualifier("activitiDataSource") DataSource dataSource, @Qualifier("activitiTransactionManager") PlatformTransactionManager transactionManager, SpringAsyncExecutor springAsyncExecutor)
/*    */     throws IOException
/*    */   {
/* 85 */     return baseSpringProcessEngineConfiguration(dataSource, transactionManager, springAsyncExecutor);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\ActivitiConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */