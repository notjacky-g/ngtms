/*     */ package com.hwacom.ngtms.frameworkcontext;
/*     */ 
/*     */ import com.codahale.metrics.MetricRegistry;
/*     */ import com.codahale.metrics.health.HealthCheckRegistry;
/*     */ import com.hwacom.ngtms.base.util.ResolveString;
/*     */ import com.zaxxer.hikari.HikariConfig;
/*     */ import com.zaxxer.hikari.HikariDataSource;
/*     */ import java.util.Properties;
/*     */ import javax.annotation.Resource;
/*     */ import javax.sql.DataSource;
/*     */ import org.hibernate.jpa.HibernatePersistenceProvider;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
/*     */ import org.springframework.context.annotation.Bean;
/*     */ import org.springframework.context.annotation.Configuration;
/*     */ import org.springframework.context.annotation.DependsOn;
/*     */ import org.springframework.context.annotation.Primary;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
/*     */ import org.springframework.jdbc.core.JdbcTemplate;
/*     */ import org.springframework.orm.jpa.JpaTransactionManager;
/*     */ import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
/*     */ import org.springframework.transaction.annotation.EnableTransactionManagement;
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
/*     */ @Configuration
/*     */ @EnableJpaRepositories(entityManagerFactoryRef="jpaEntityManagerFactory", transactionManagerRef="jpaTransactionManager", basePackages={"com.hwacom.ngtms"})
/*     */ @EnableTransactionManagement
/*     */ public class JpaDataBaseConfig
/*     */ {
/*  46 */   private static Logger logger = LoggerFactory.getLogger(JpaDataBaseConfig.class);
/*     */   @Resource
/*     */   private Environment environment;
/*     */   @Autowired(required=false)
/*     */   private MetricRegistry metricRegistry;
/*     */   @Autowired(required=false)
/*     */   private HealthCheckRegistry healthCheckRegistry;
/*     */   
/*     */   @Bean(name={"jpaDataSource"})
/*     */   public DataSource dataSource()
/*     */   {
/*  57 */     HikariConfig conf = new HikariConfig();
/*  58 */     conf.setDataSourceClassName(this.environment.getRequiredProperty("olds.driverClassName"));
/*  59 */     conf.addDataSourceProperty("url", 
/*  60 */       ResolveString.resolve(this.environment.getRequiredProperty("olds.url")));
/*  61 */     conf.addDataSourceProperty("user", this.environment.getRequiredProperty("olds.username"));
/*  62 */     conf.addDataSourceProperty("password", this.environment.getRequiredProperty("olds.password"));
/*  63 */     conf.setPoolName("JPA");
/*  64 */     conf.setAutoCommit(((Boolean)this.environment.getProperty("olds.autoCommit", Boolean.class, Boolean.valueOf(true))).booleanValue());
/*  65 */     conf.setConnectionTestQuery(this.environment
/*  66 */       .getProperty("olds.connectionTestQuery", "select 1 from dual"));
/*  67 */     conf.setMaximumPoolSize(((Integer)this.environment.getRequiredProperty("olds.maximumPoolSize", Integer.class)).intValue());
/*  68 */     conf.setMaxLifetime(((Long)this.environment.getProperty("olds.maxLifetime", Long.class, Long.valueOf(1800000L))).longValue());
/*  69 */     conf.setConnectionTimeout(
/*  70 */       ((Long)this.environment.getProperty("olds.connectionTimeout", Long.class, Long.valueOf(10000L))).longValue());
/*  71 */     if (this.metricRegistry != null) {
/*  72 */       conf.setMetricRegistry(this.metricRegistry);
/*     */     }
/*  74 */     if (this.healthCheckRegistry != null) {
/*  75 */       conf.addHealthCheckProperty("connectivityCheckTimeoutMs", this.environment
/*     */       
/*  77 */         .getProperty("olds.connectivityCheckTimeoutMs", "1000"));
/*  78 */       conf.addHealthCheckProperty("expected99thPercentileMs", this.environment
/*     */       
/*  80 */         .getProperty("olds.expected99thPercentileMs", "10"));
/*  81 */       conf.setHealthCheckRegistry(this.healthCheckRegistry);
/*     */     }
/*     */     
/*  84 */     logger.info("-------------JPA Database Config-------------");
/*  85 */     logger.info("DataSource ClassName: {}", conf.getDataSourceClassName());
/*  86 */     logger.info("DataSource URL: {}", conf.getDataSourceProperties().getProperty("url"));
/*  87 */     logger.info("DataSource User: {}", conf.getDataSourceProperties().getProperty("user"));
/*  88 */     logger.info("Hikari AutoCommit: {}", Boolean.valueOf(conf.isAutoCommit()));
/*  89 */     logger.info("Hikari ConnectionTestQuery: {}", conf.getConnectionTestQuery());
/*  90 */     logger.info("Hikari MaximumPoolSize: {}", Integer.valueOf(conf.getMaximumPoolSize()));
/*  91 */     logger.info("Hikari MaxLifetime: {}", Long.valueOf(conf.getMaxLifetime()));
/*  92 */     logger.info("Hikari ConnectionTimeout: {}", Long.valueOf(conf.getConnectionTimeout()));
/*  93 */     logger.info("Hikari HealthCheck: {}", conf.getHealthCheckProperties());
/*  94 */     logger.info("-----------------------------------------");
/*  95 */     HikariDataSource ds = new HikariDataSource(conf);
/*  96 */     return ds;
/*     */   }
/*     */   
/*     */   @Bean(name={"jpaEntityManagerFactory"})
/*     */   @DependsOn({"jpaDataSource"})
/*     */   public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("jpaDataSource") DataSource dataSource) throws ClassNotFoundException
/*     */   {
/* 103 */     LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
/*     */     
/* 105 */     entityManagerFactoryBean.setPersistenceUnitName("NGTMS_JPA");
/* 106 */     entityManagerFactoryBean.setDataSource(dataSource);
/*     */     
/* 108 */     String[] packageToScan = this.environment.getRequiredProperty("olds.packages.to.scan").trim().split("\\s*,\\s*");
/* 109 */     entityManagerFactoryBean.setPackagesToScan(packageToScan);
/*     */     
/* 111 */     entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
/*     */     
/* 113 */     Properties jpaProterties = new Properties();
/* 114 */     jpaProterties.put("hibernate.dialect", this.environment
/* 115 */       .getRequiredProperty("olds.hibernate.dialect"));
/* 116 */     jpaProterties.put("hibernate.format_sql", this.environment
/* 117 */       .getRequiredProperty("olds.hibernate.format_sql"));
/* 118 */     jpaProterties.put("hibernate.show_sql", this.environment
/* 119 */       .getRequiredProperty("olds.hibernate.show_sql"));
/* 120 */     jpaProterties.put(com.hwacom.ngtms.base.hibernate.ConfigProperties.HIBERNATE_NAMING_STRATEGY[0], com.hwacom.ngtms.base.hibernate.ConfigProperties.HIBERNATE_NAMING_STRATEGY[1]);
/*     */     
/*     */ 
/* 123 */     jpaProterties.put(com.hwacom.ngtms.base.hibernate.ConfigProperties.HIBERNATE_PHYSICAL_NAMING_STRATEGY[0], com.hwacom.ngtms.base.hibernate.ConfigProperties.HIBERNATE_PHYSICAL_NAMING_STRATEGY[1]);
/*     */     
/*     */ 
/* 126 */     jpaProterties.put("hibernate.id.new_generator_mappings", this.environment
/*     */     
/* 128 */       .getProperty("olds.hibernate.id.new_generator_mappings", Boolean.class, Boolean.valueOf(false)));
/*     */     
/*     */ 
/*     */ 
/* 132 */     if ((this.environment.getProperty("ngtms.product.mode") != null) && 
/* 133 */       ("false".equalsIgnoreCase(this.environment.getProperty("ngtms.product.mode"))) && 
/* 134 */       (this.environment.getProperty("olds.hibernate.hbm2ddl.auto") != null)) {
/* 135 */       jpaProterties.put("hibernate.hbm2ddl.auto", this.environment
/* 136 */         .getProperty("olds.hibernate.hbm2ddl.auto"));
/*     */     }
/* 138 */     entityManagerFactoryBean.setJpaProperties(jpaProterties);
/* 139 */     return entityManagerFactoryBean;
/*     */   }
/*     */   
/*     */   @Primary
/*     */   @Bean(name={"jpaTransactionManager"})
/*     */   @ConditionalOnBean(name={"jpaDataSource"})
/*     */   public JpaTransactionManager transactionManager(@Qualifier("jpaDataSource") DataSource dataSource) throws ClassNotFoundException
/*     */   {
/* 147 */     JpaTransactionManager transactionManager = new JpaTransactionManager();
/*     */     
/* 149 */     transactionManager.setEntityManagerFactory(entityManagerFactory(dataSource).getObject());
/*     */     
/* 151 */     return transactionManager;
/*     */   }
/*     */   
/*     */   @Primary
/*     */   @Bean(name={"jpaJdbcTemplate"})
/*     */   @ConditionalOnBean(name={"jpaDataSource"})
/*     */   public JdbcTemplate jdbcTemplate(@Qualifier("jpaDataSource") DataSource dataSource) {
/* 158 */     JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
/* 159 */     return jdbcTemplate;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\JpaDataBaseConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */