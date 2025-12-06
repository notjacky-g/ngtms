/*     */ package com.hwacom.ngtms.frameworkcontext;
/*     */ 
/*     */ import com.codahale.metrics.MetricRegistry;
/*     */ import com.codahale.metrics.health.HealthCheckRegistry;
/*     */ import com.hwacom.ngtms.base.hibernate.ConfigProperties;
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
/*     */ import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
/*     */ import org.springframework.context.annotation.Bean;
/*     */ import org.springframework.context.annotation.ComponentScan.Filter;
/*     */ import org.springframework.context.annotation.Configuration;
/*     */ import org.springframework.context.annotation.FilterType;
/*     */ import org.springframework.context.annotation.Primary;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
/*     */ import org.springframework.data.repository.CrudRepository;
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
/*     */ 
/*     */ 
/*     */ @Configuration
/*     */ @EnableJpaRepositories(entityManagerFactoryRef = "oldbEntityManagerFactory", transactionManagerRef = "oldbTransactionManager", basePackages = {"com.hwacom.ngtms"}, includeFilters = {@Filter(type = FilterType.ASSIGNABLE_TYPE, value = {CrudRepository.class})}, excludeFilters = {@Filter(type = FilterType.ANNOTATION, value = {HistoryRepository.class})})
/*     */ public class OnlineDataBaseConfig
/*     */ {
/*  57 */   private static Logger logger = LoggerFactory.getLogger(OnlineDataBaseConfig.class);
/*     */   @Autowired
/*     */   private StandardPBEStringEncryptor standardPBEStringEncryptor;
/*     */   @Resource
/*     */   private Environment environment;
/*     */   @Autowired(required = false)
/*     */   private MetricRegistry metricRegistry;
/*     */   @Autowired(required = false)
/*     */   private HealthCheckRegistry healthCheckRegistry;
/*     */   
/*     */   @Primary
/*     */   @Bean(name = {"oldbDataSource"})
/*     */   public DataSource oldbDataSource() {
/*  70 */     HikariConfig conf = new HikariConfig();
/*  71 */     conf.setDataSourceClassName(this.environment.getRequiredProperty("olds.driverClassName"));
/*  72 */     conf.addDataSourceProperty("url", 
/*  73 */         ResolveString.resolve(this.environment.getRequiredProperty("olds.url")));
/*  74 */     conf.addDataSourceProperty("user", this.environment.getRequiredProperty("olds.username"));
/*  75 */     String pwd = this.environment.getRequiredProperty("olds.password");
/*     */     
/*  77 */     Boolean enablePwdEncryption = (Boolean)this.environment.getProperty("olds.enable.password.encryption", Boolean.class, Boolean.valueOf(false));
/*  78 */     if (enablePwdEncryption.booleanValue()) {
/*  79 */       pwd = this.standardPBEStringEncryptor.decrypt(pwd);
/*     */     }
/*  81 */     conf.addDataSourceProperty("password", pwd);
/*  82 */     conf.setPoolName("Online");
/*  83 */     conf.setAutoCommit(((Boolean)this.environment.getProperty("olds.autoCommit", Boolean.class, Boolean.valueOf(true))).booleanValue());
/*  84 */     conf.setConnectionTestQuery(this.environment
/*  85 */         .getProperty("olds.connectionTestQuery", "select 1 from dual"));
/*  86 */     conf.setMaximumPoolSize(((Integer)this.environment.getRequiredProperty("olds.maximumPoolSize", Integer.class)).intValue());
/*  87 */     conf.setMaxLifetime(((Long)this.environment.getProperty("olds.maxLifetime", Long.class, Long.valueOf(1800000L))).longValue());
/*  88 */     conf.setConnectionTimeout(((Long)this.environment
/*  89 */         .getProperty("olds.connectionTimeout", Long.class, Long.valueOf(10000L))).longValue());
/*  90 */     if (this.metricRegistry != null) {
/*  91 */       conf.setMetricRegistry(this.metricRegistry);
/*     */     }
/*  93 */     if (this.healthCheckRegistry != null) {
/*  94 */       conf.addHealthCheckProperty("connectivityCheckTimeoutMs", this.environment
/*     */           
/*  96 */           .getProperty("olds.connectivityCheckTimeoutMs", "1000"));
/*  97 */       conf.addHealthCheckProperty("expected99thPercentileMs", this.environment
/*     */           
/*  99 */           .getProperty("olds.expected99thPercentileMs", "10"));
/* 100 */       conf.setHealthCheckRegistry(this.healthCheckRegistry);
/*     */     } 
/*     */     
/* 103 */     logger.info("-------------Online Database Config-------------");
/* 104 */     logger.info("DataSource ClassName: {}", conf.getDataSourceClassName());
/* 105 */     logger.info("DataSource URL: {}", conf.getDataSourceProperties().getProperty("url"));
/* 106 */     logger.info("DataSource User: {}", conf.getDataSourceProperties().getProperty("user"));
/* 107 */     logger.info("Hikari AutoCommit: {}", Boolean.valueOf(conf.isAutoCommit()));
/* 108 */     logger.info("Hikari ConnectionTestQuery: {}", conf.getConnectionTestQuery());
/* 109 */     logger.info("Hikari MaximumPoolSize: {}", Integer.valueOf(conf.getMaximumPoolSize()));
/* 110 */     logger.info("Hikari MaxLifetime: {}", Long.valueOf(conf.getMaxLifetime()));
/* 111 */     logger.info("Hikari ConnectionTimeout: {}", Long.valueOf(conf.getConnectionTimeout()));
/* 112 */     logger.info("Hikari HealthCheck: {}", conf.getHealthCheckProperties());
/* 113 */     logger.info("-----------------------------------------");
/* 114 */     HikariDataSource ds = new HikariDataSource(conf);
/* 115 */     return (DataSource)ds;
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Primary
/*     */   @Bean(name = {"oldbEntityManagerFactory"})
/*     */   public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("oldbDataSource") DataSource dataSource) throws ClassNotFoundException {
/* 155 */     LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
/*     */     
/* 157 */     entityManagerFactoryBean.setPersistenceUnitName("NGTMS_OLDB");
/* 158 */     entityManagerFactoryBean.setDataSource(dataSource);
/*     */     
/* 160 */     String[] packageToScan = this.environment.getRequiredProperty("olds.packages.to.scan").trim().split("\\s*,\\s*");
/* 161 */     entityManagerFactoryBean.setPackagesToScan(packageToScan);
/*     */     
/* 163 */     entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
/*     */     
/* 165 */     Properties jpaProterties = new Properties();
/* 166 */     jpaProterties.put("hibernate.dialect", this.environment
/* 167 */         .getRequiredProperty("olds.hibernate.dialect"));
/* 168 */     jpaProterties.put("hibernate.format_sql", this.environment
/* 169 */         .getRequiredProperty("olds.hibernate.format_sql"));
/* 170 */     jpaProterties.put("hibernate.show_sql", this.environment
/* 171 */         .getRequiredProperty("olds.hibernate.show_sql"));
/* 172 */     jpaProterties.put(ConfigProperties.HIBERNATE_NAMING_STRATEGY[0], ConfigProperties.HIBERNATE_NAMING_STRATEGY[1]);
/*     */ 
/*     */     
/* 175 */     jpaProterties.put(ConfigProperties.HIBERNATE_PHYSICAL_NAMING_STRATEGY[0], ConfigProperties.HIBERNATE_PHYSICAL_NAMING_STRATEGY[1]);
/*     */ 
/*     */     
/* 178 */     jpaProterties.put("hibernate.id.new_generator_mappings", this.environment
/*     */         
/* 180 */         .getProperty("olds.hibernate.id.new_generator_mappings", Boolean.class, Boolean.valueOf(false)));
/*     */ 
/*     */ 
/*     */     
/* 184 */     if (this.environment.getProperty("ngtms.product.mode") != null && "false"
/* 185 */       .equalsIgnoreCase(this.environment.getProperty("ngtms.product.mode")) && 
/* 186 */       this.environment.getProperty("olds.hibernate.hbm2ddl.auto") != null) {
/* 187 */       jpaProterties.put("hibernate.hbm2ddl.auto", this.environment
/* 188 */           .getProperty("olds.hibernate.hbm2ddl.auto"));
/*     */     }
/* 190 */     entityManagerFactoryBean.setJpaProperties(jpaProterties);
/* 191 */     return entityManagerFactoryBean;
/*     */   }
/*     */ 
/*     */   
/*     */   @Primary
/*     */   @Bean(name = {"oldbTransactionManager"})
/*     */   @ConditionalOnBean(name = {"oldbDataSource"})
/*     */   public JpaTransactionManager transactionManager(@Qualifier("oldbDataSource") DataSource dataSource) throws ClassNotFoundException {
/* 199 */     JpaTransactionManager transactionManager = new JpaTransactionManager();
/*     */     
/* 201 */     transactionManager.setEntityManagerFactory(entityManagerFactory(dataSource).getObject());
/*     */     
/* 203 */     return transactionManager;
/*     */   }
/*     */   
/*     */   @Primary
/*     */   @Bean(name = {"oldbJdbcTemplate"})
/*     */   @ConditionalOnBean(name = {"oldbDataSource"})
/*     */   public JdbcTemplate jdbcTemplate(@Qualifier("oldbDataSource") DataSource dataSource) {
/* 210 */     JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
/* 211 */     return jdbcTemplate;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\OnlineDataBaseConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */