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
/*     */ import org.hibernate.jpa.HibernatePersistenceProvider;
/*     */ import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.context.annotation.Bean;
/*     */ import org.springframework.context.annotation.Configuration;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
/*     */ import org.springframework.jdbc.core.JdbcTemplate;
/*     */ import org.springframework.orm.jpa.JpaTransactionManager;
/*     */ import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
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
/*     */ @Configuration
/*     */ @EnableJpaRepositories(entityManagerFactoryRef="drdbEntityManagerFactory", transactionManagerRef="drdbTransactionManager", basePackages={"com.hwacom.ngtms.hcce.recovery"}, includeFilters={@org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.ANNOTATION, value={DrRepository.class})})
/*     */ public class DisasterRecoveryDataBaseConfig
/*     */ {
/*  49 */   private static Logger logger = LoggerFactory.getLogger(DisasterRecoveryDataBaseConfig.class);
/*     */   @Autowired
/*     */   private StandardPBEStringEncryptor standardPBEStringEncryptor;
/*     */   @Resource
/*     */   private Environment environment;
/*     */   @Autowired(required=false)
/*     */   private MetricRegistry metricRegistry;
/*     */   @Autowired(required=false)
/*     */   private HealthCheckRegistry healthCheckRegistry;
/*     */   
/*     */   @Bean(name={"drdbDataSource"})
/*     */   public DataSource drdbDataSource() {
/*  61 */     HikariConfig conf = new HikariConfig();
/*  62 */     conf.setDataSourceClassName(this.environment.getRequiredProperty("drds.driverClassName"));
/*  63 */     conf.addDataSourceProperty("url", 
/*  64 */       ResolveString.resolve(this.environment.getRequiredProperty("drds.url")));
/*  65 */     conf.addDataSourceProperty("user", this.environment.getRequiredProperty("drds.username"));
/*  66 */     String pwd = this.environment.getRequiredProperty("drds.password");
/*     */     
/*  68 */     Boolean enablePwdEncryption = (Boolean)this.environment.getProperty("drds.enable.password.encryption", Boolean.class, Boolean.valueOf(false));
/*  69 */     if (enablePwdEncryption.booleanValue()) {
/*  70 */       pwd = this.standardPBEStringEncryptor.decrypt(pwd);
/*     */     }
/*  72 */     conf.addDataSourceProperty("password", pwd);
/*  73 */     conf.setPoolName("DisasterRecovery");
/*  74 */     conf.setAutoCommit(((Boolean)this.environment.getProperty("drds.autoCommit", Boolean.class, Boolean.valueOf(true))).booleanValue());
/*  75 */     conf.setConnectionTestQuery(this.environment
/*  76 */       .getProperty("drds.connectionTestQuery", "select 1 from dual"));
/*  77 */     conf.setMaximumPoolSize(((Integer)this.environment.getRequiredProperty("drds.maximumPoolSize", Integer.class)).intValue());
/*  78 */     conf.setMaxLifetime(((Long)this.environment.getProperty("drds.maxLifetime", Long.class, Long.valueOf(1800000L))).longValue());
/*  79 */     conf.setConnectionTimeout(
/*  80 */       ((Long)this.environment.getProperty("drds.connectionTimeout", Long.class, Long.valueOf(10000L))).longValue());
/*  81 */     if (this.metricRegistry != null) {
/*  82 */       conf.setMetricRegistry(this.metricRegistry);
/*     */     }
/*  84 */     if (this.healthCheckRegistry != null) {
/*  85 */       conf.addHealthCheckProperty("connectivityCheckTimeoutMs", this.environment
/*     */       
/*  87 */         .getProperty("drds.connectivityCheckTimeoutMs", "1000"));
/*  88 */       conf.addHealthCheckProperty("expected99thPercentileMs", this.environment
/*     */       
/*  90 */         .getProperty("drds.expected99thPercentileMs", "10"));
/*  91 */       conf.setHealthCheckRegistry(this.healthCheckRegistry);
/*     */     }
/*     */     
/*  94 */     logger.info("-------------DisasterRecovery Database Config-------------");
/*  95 */     logger.info("DataSource ClassName: {}", conf.getDataSourceClassName());
/*  96 */     logger.info("DataSource URL: {}", conf.getDataSourceProperties().getProperty("url"));
/*  97 */     logger.info("DataSource User: {}", conf.getDataSourceProperties().getProperty("user"));
/*  98 */     logger.info("Hikari AutoCommit: {}", Boolean.valueOf(conf.isAutoCommit()));
/*  99 */     logger.info("Hikari ConnectionTestQuery: {}", conf.getConnectionTestQuery());
/* 100 */     logger.info("Hikari MaximumPoolSize: {}", Integer.valueOf(conf.getMaximumPoolSize()));
/* 101 */     logger.info("Hikari MaxLifetime: {}", Long.valueOf(conf.getMaxLifetime()));
/* 102 */     logger.info("Hikari ConnectionTimeout: {}", Long.valueOf(conf.getConnectionTimeout()));
/* 103 */     logger.info("Hikari HealthCheck: {}", conf.getHealthCheckProperties());
/* 104 */     logger.info("-----------------------------------------");
/* 105 */     HikariDataSource ds = new HikariDataSource(conf);
/* 106 */     return ds;
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
/*     */ 
/*     */ 
/*     */   @Bean(name={"drdbEntityManagerFactory"})
/*     */   public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("drdbDataSource") DataSource dataSource)
/*     */     throws ClassNotFoundException
/*     */   {
/* 144 */     LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
/*     */     
/* 146 */     entityManagerFactoryBean.setPersistenceUnitName("NGTMS_DRDB");
/* 147 */     entityManagerFactoryBean.setDataSource(dataSource);
/*     */     
/* 149 */     String[] packageToScan = this.environment.getRequiredProperty("drds.packages.to.scan").trim().split("\\s*,\\s*");
/* 150 */     entityManagerFactoryBean.setPackagesToScan(packageToScan);
/*     */     
/* 152 */     entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
/*     */     
/* 154 */     Properties jpaProterties = new Properties();
/* 155 */     jpaProterties.put("hibernate.dialect", this.environment
/* 156 */       .getRequiredProperty("drds.hibernate.dialect"));
/* 157 */     jpaProterties.put("hibernate.format_sql", this.environment
/* 158 */       .getRequiredProperty("drds.hibernate.format_sql"));
/* 159 */     jpaProterties.put("hibernate.show_sql", this.environment
/* 160 */       .getRequiredProperty("drds.hibernate.show_sql"));
/* 161 */     jpaProterties.put(com.hwacom.ngtms.base.hibernate.ConfigProperties.HIBERNATE_NAMING_STRATEGY[0], com.hwacom.ngtms.base.hibernate.ConfigProperties.HIBERNATE_NAMING_STRATEGY[1]);
/*     */     
/*     */ 
/* 164 */     jpaProterties.put(com.hwacom.ngtms.base.hibernate.ConfigProperties.HIBERNATE_PHYSICAL_NAMING_STRATEGY[0], com.hwacom.ngtms.base.hibernate.ConfigProperties.HIBERNATE_PHYSICAL_NAMING_STRATEGY[1]);
/*     */     
/*     */ 
/* 167 */     jpaProterties.put("hibernate.id.new_generator_mappings", this.environment
/*     */     
/* 169 */       .getProperty("drds.hibernate.id.new_generator_mappings", Boolean.class, Boolean.valueOf(false)));
/*     */     
/*     */ 
/*     */ 
/* 173 */     if ((this.environment.getProperty("ngtms.product.mode") != null) && 
/* 174 */       ("false".equalsIgnoreCase(this.environment.getProperty("ngtms.product.mode"))) && 
/* 175 */       (this.environment.getProperty("drds.hibernate.hbm2ddl.auto") != null)) {
/* 176 */       jpaProterties.put("hibernate.hbm2ddl.auto", this.environment
/* 177 */         .getProperty("drds.hibernate.hbm2ddl.auto"));
/*     */     }
/* 179 */     entityManagerFactoryBean.setJpaProperties(jpaProterties);
/* 180 */     return entityManagerFactoryBean;
/*     */   }
/*     */   
/*     */   @Bean(name={"drdbTransactionManager"})
/*     */   public JpaTransactionManager transactionManager(@Qualifier("drdbDataSource") DataSource dataSource) throws ClassNotFoundException
/*     */   {
/* 186 */     JpaTransactionManager transactionManager = new JpaTransactionManager();
/*     */     
/* 188 */     transactionManager.setEntityManagerFactory(entityManagerFactory(dataSource).getObject());
/*     */     
/* 190 */     return transactionManager;
/*     */   }
/*     */   
/*     */   @Bean(name={"drdbJdbcTemplate"})
/*     */   public JdbcTemplate jdbcTemplate(@Qualifier("drdbDataSource") DataSource dataSource) {
/* 195 */     JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
/* 196 */     return jdbcTemplate;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\frameworkcontext\DisasterRecoveryDataBaseConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */