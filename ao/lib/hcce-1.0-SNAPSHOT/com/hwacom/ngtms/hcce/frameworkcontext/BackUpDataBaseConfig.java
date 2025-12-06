/*     */ package com.hwacom.ngtms.hcce.frameworkcontext;
/*     */ 
/*     */ import com.codahale.metrics.MetricRegistry;
/*     */ import com.codahale.metrics.health.HealthCheckRegistry;
/*     */ import com.hwacom.ngtms.base.util.ResolveString;
/*     */ import com.zaxxer.hikari.HikariConfig;
/*     */ import com.zaxxer.hikari.HikariDataSource;
/*     */ import java.util.Properties;
/*     */ import javax.annotation.Resource;
/*     */ import javax.sql.DataSource;
/*     */ import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.context.annotation.Bean;
/*     */ import org.springframework.context.annotation.Configuration;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.jdbc.core.JdbcTemplate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Configuration
/*     */ public class BackUpDataBaseConfig
/*     */ {
/*  28 */   private static Logger logger = LoggerFactory.getLogger(BackUpDataBaseConfig.class);
/*     */   @Autowired
/*     */   private StandardPBEStringEncryptor standardPBEStringEncryptor;
/*     */   @Resource
/*     */   private Environment environment;
/*     */   @Autowired(required=false)
/*     */   private MetricRegistry metricRegistry;
/*     */   @Autowired(required=false)
/*     */   private HealthCheckRegistry healthCheckRegistry;
/*     */   
/*     */   @Bean(name={"bkdbDataSource"})
/*     */   public DataSource bkdbDataSource()
/*     */   {
/*  41 */     HikariConfig conf = new HikariConfig();
/*  42 */     conf.setDataSourceClassName(this.environment.getRequiredProperty("bkds.driverClassName"));
/*  43 */     conf.addDataSourceProperty("url", 
/*  44 */       ResolveString.resolve(this.environment.getRequiredProperty("bkds.url")));
/*  45 */     conf.addDataSourceProperty("user", this.environment.getRequiredProperty("bkds.username"));
/*  46 */     String pwd = this.environment.getRequiredProperty("bkds.password");
/*     */     
/*  48 */     Boolean enablePwdEncryption = (Boolean)this.environment.getProperty("bkds.enable.password.encryption", Boolean.class, Boolean.valueOf(false));
/*  49 */     if (enablePwdEncryption.booleanValue()) {
/*  50 */       pwd = this.standardPBEStringEncryptor.decrypt(pwd);
/*     */     }
/*  52 */     conf.addDataSourceProperty("password", pwd);
/*  53 */     conf.setPoolName("BackUp");
/*  54 */     conf.setAutoCommit(((Boolean)this.environment.getProperty("bkds.autoCommit", Boolean.class, Boolean.valueOf(true))).booleanValue());
/*  55 */     conf.setConnectionTestQuery(this.environment
/*  56 */       .getProperty("bkds.connectionTestQuery", "select 1 from dual"));
/*  57 */     conf.setMaximumPoolSize(((Integer)this.environment.getRequiredProperty("bkds.maximumPoolSize", Integer.class)).intValue());
/*  58 */     conf.setMaxLifetime(((Long)this.environment.getProperty("bkds.maxLifetime", Long.class, Long.valueOf(1800000L))).longValue());
/*  59 */     conf.setConnectionTimeout(
/*  60 */       ((Long)this.environment.getProperty("bkds.connectionTimeout", Long.class, Long.valueOf(10000L))).longValue());
/*  61 */     if (this.metricRegistry != null) {
/*  62 */       conf.setMetricRegistry(this.metricRegistry);
/*     */     }
/*  64 */     if (this.healthCheckRegistry != null) {
/*  65 */       conf.addHealthCheckProperty("connectivityCheckTimeoutMs", this.environment
/*     */       
/*  67 */         .getProperty("bkds.connectivityCheckTimeoutMs", "1000"));
/*  68 */       conf.addHealthCheckProperty("expected99thPercentileMs", this.environment
/*     */       
/*  70 */         .getProperty("bkds.expected99thPercentileMs", "10"));
/*  71 */       conf.setHealthCheckRegistry(this.healthCheckRegistry);
/*     */     }
/*     */     
/*  74 */     logger.info("-------------BackUp Database Config-------------");
/*  75 */     logger.info("DataSource ClassName: {}", conf.getDataSourceClassName());
/*  76 */     logger.info("DataSource URL: {}", conf.getDataSourceProperties().getProperty("url"));
/*  77 */     logger.info("DataSource User: {}", conf.getDataSourceProperties().getProperty("user"));
/*  78 */     logger.info("Hikari AutoCommit: {}", Boolean.valueOf(conf.isAutoCommit()));
/*  79 */     logger.info("Hikari ConnectionTestQuery: {}", conf.getConnectionTestQuery());
/*  80 */     logger.info("Hikari MaximumPoolSize: {}", Integer.valueOf(conf.getMaximumPoolSize()));
/*  81 */     logger.info("Hikari MaxLifetime: {}", Long.valueOf(conf.getMaxLifetime()));
/*  82 */     logger.info("Hikari ConnectionTimeout: {}", Long.valueOf(conf.getConnectionTimeout()));
/*  83 */     logger.info("Hikari HealthCheck: {}", conf.getHealthCheckProperties());
/*  84 */     logger.info("-----------------------------------------");
/*  85 */     HikariDataSource ds = new HikariDataSource(conf);
/*  86 */     return ds;
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
/*     */   @Bean(name={"bkdbJdbcTemplate"})
/*     */   public JdbcTemplate jdbcTemplate(@Qualifier("bkdbDataSource") DataSource bkdbDataSource)
/*     */   {
/* 121 */     JdbcTemplate jdbcTemplate = new JdbcTemplate(bkdbDataSource);
/* 122 */     return jdbcTemplate;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\frameworkcontext\BackUpDataBaseConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */