/*     */ package com.hwacom.ngtms.frameworkcontext;
/*     */ 
/*     */ import com.codahale.metrics.MetricRegistry;
/*     */ import com.codahale.metrics.health.HealthCheckRegistry;
/*     */ import com.hwacom.ngtms.base.hibernate.ConfigProperties;
/*     */ import com.hwacom.ngtms.base.util.ResolveString;
/*     */ import com.zaxxer.hikari.HikariConfig;
/*     */ import com.zaxxer.hikari.HikariDataSource;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ import org.springframework.context.annotation.ComponentScan.Filter;
/*     */ import org.springframework.context.annotation.Configuration;
/*     */ import org.springframework.context.annotation.EnableAspectJAutoProxy;
/*     */ import org.springframework.context.annotation.FilterType;
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
/*     */ @Configuration
/*     */ @EnableJpaRepositories(entityManagerFactoryRef = "hddbEntityManagerFactory", transactionManagerRef = "hddbTransactionManager", basePackages = {"com.hwacom.ngtms"}, includeFilters = {@Filter(type = FilterType.ANNOTATION, value = {HistoryRepository.class})})
/*     */ @EnableAspectJAutoProxy
/*     */ public class HistoryDataBaseConfig
/*     */ {
/*  55 */   private static Logger logger = LoggerFactory.getLogger(HistoryDataBaseConfig.class);
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   private StandardPBEStringEncryptor standardPBEStringEncryptor;
/*     */ 
/*     */   
/*     */   @Resource
/*     */   private Environment environment;
/*     */ 
/*     */   
/*     */   @Bean(name = {"hddbDataSource"})
/*     */   public DataSource hddbDataSource() {
/*  68 */     HikariConfig conf = new HikariConfig();
/*  69 */     conf.setDataSourceClassName(this.environment.getRequiredProperty("hdds.driverClassName"));
/*  70 */     conf.addDataSourceProperty("url", 
/*  71 */         ResolveString.resolve(this.environment.getRequiredProperty("hdds.url")));
/*  72 */     conf.addDataSourceProperty("user", this.environment.getRequiredProperty("hdds.username"));
/*  73 */     String pwd = this.environment.getRequiredProperty("hdds.password");
/*     */     
/*  75 */     Boolean enablePwdEncryption = (Boolean)this.environment.getProperty("hdds.enable.password.encryption", Boolean.class, Boolean.valueOf(false));
/*  76 */     if (enablePwdEncryption.booleanValue()) {
/*  77 */       pwd = this.standardPBEStringEncryptor.decrypt(pwd);
/*     */     }
/*  79 */     conf.addDataSourceProperty("password", pwd);
/*  80 */     conf.setPoolName("History");
/*  81 */     conf.setAutoCommit(((Boolean)this.environment.getProperty("hdds.autoCommit", Boolean.class, Boolean.valueOf(true))).booleanValue());
/*  82 */     conf.setConnectionTestQuery(this.environment
/*  83 */         .getProperty("hdds.connectionTestQuery", "select 1 from dual"));
/*  84 */     conf.setMaximumPoolSize(((Integer)this.environment.getRequiredProperty("hdds.maximumPoolSize", Integer.class)).intValue());
/*  85 */     conf.setMaxLifetime(((Long)this.environment.getProperty("hdds.maxLifetime", Long.class, Long.valueOf(1800000L))).longValue());
/*  86 */     conf.setConnectionTimeout(((Long)this.environment
/*  87 */         .getProperty("hdds.connectionTimeout", Long.class, Long.valueOf(10000L))).longValue());
/*  88 */     if (this.metricRegistry != null) {
/*  89 */       conf.setMetricRegistry(this.metricRegistry);
/*     */     }
/*  91 */     if (this.healthCheckRegistry != null) {
/*  92 */       conf.addHealthCheckProperty("connectivityCheckTimeoutMs", this.environment
/*     */           
/*  94 */           .getProperty("hdds.connectivityCheckTimeoutMs", "1000"));
/*  95 */       conf.addHealthCheckProperty("expected99thPercentileMs", this.environment
/*     */           
/*  97 */           .getProperty("hdds.expected99thPercentileMs", "10"));
/*  98 */       conf.setHealthCheckRegistry(this.healthCheckRegistry);
/*     */     } 
/*     */     
/* 101 */     logger.info("-------------History Database Config-------------");
/* 102 */     logger.info("DataSource ClassName: {}", conf.getDataSourceClassName());
/* 103 */     logger.info("DataSource URL: {}", conf.getDataSourceProperties().getProperty("url"));
/* 104 */     logger.info("DataSource User: {}", conf.getDataSourceProperties().getProperty("user"));
/* 105 */     logger.info("Hikari AutoCommit: {}", Boolean.valueOf(conf.isAutoCommit()));
/* 106 */     logger.info("Hikari ConnectionTestQuery: {}", conf.getConnectionTestQuery());
/* 107 */     logger.info("Hikari MaximumPoolSize: {}", Integer.valueOf(conf.getMaximumPoolSize()));
/* 108 */     logger.info("Hikari MaxLifetime: {}", Long.valueOf(conf.getMaxLifetime()));
/* 109 */     logger.info("Hikari ConnectionTimeout: {}", Long.valueOf(conf.getConnectionTimeout()));
/* 110 */     logger.info("Hikari HealthCheck: {}", conf.getHealthCheckProperties());
/* 111 */     logger.info("-----------------------------------------");
/* 112 */     HikariDataSource ds = new HikariDataSource(conf);
/* 113 */     return (DataSource)ds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   private DataSource oldbDataSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Autowired(required = false)
/*     */   private MetricRegistry metricRegistry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Autowired(required = false)
/*     */   private HealthCheckRegistry healthCheckRegistry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Bean(name = {"hddbEntityManagerFactory"})
/*     */   public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("hddbDataSource") DataSource hddbDataSource) throws ClassNotFoundException {
/* 151 */     LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
/*     */     
/* 153 */     entityManagerFactoryBean.setPersistenceUnitName("NGTMS_HDDB");
/* 154 */     HistoryRoutingDataSource routingDataSource = new HistoryRoutingDataSource();
/* 155 */     Map<Object, Object> dataSourceMap = new HashMap<>();
/* 156 */     dataSourceMap.put(HistoryDataSourceHolder.DataSource.OLDB, this.oldbDataSource);
/* 157 */     dataSourceMap.put(HistoryDataSourceHolder.DataSource.HDDB, hddbDataSource());
/* 158 */     routingDataSource.setTargetDataSources(dataSourceMap);
/* 159 */     routingDataSource.setDefaultTargetDataSource(hddbDataSource());
/* 160 */     routingDataSource.afterPropertiesSet();
/* 161 */     entityManagerFactoryBean.setDataSource((DataSource)routingDataSource);
/*     */     
/* 163 */     String[] packageToScan = this.environment.getRequiredProperty("hdds.packages.to.scan").trim().split("\\s*,\\s*");
/* 164 */     entityManagerFactoryBean.setPackagesToScan(packageToScan);
/*     */     
/* 166 */     entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
/*     */     
/* 168 */     Properties jpaProterties = new Properties();
/* 169 */     jpaProterties.put("hibernate.dialect", this.environment
/* 170 */         .getRequiredProperty("hdds.hibernate.dialect"));
/* 171 */     jpaProterties.put("hibernate.format_sql", this.environment
/* 172 */         .getRequiredProperty("hdds.hibernate.format_sql"));
/* 173 */     jpaProterties.put("hibernate.show_sql", this.environment
/* 174 */         .getRequiredProperty("hdds.hibernate.show_sql"));
/* 175 */     jpaProterties.put(ConfigProperties.HIBERNATE_NAMING_STRATEGY[0], ConfigProperties.HIBERNATE_NAMING_STRATEGY[1]);
/*     */ 
/*     */     
/* 178 */     jpaProterties.put(ConfigProperties.HIBERNATE_PHYSICAL_NAMING_STRATEGY[0], ConfigProperties.HIBERNATE_PHYSICAL_NAMING_STRATEGY[1]);
/*     */ 
/*     */     
/* 181 */     jpaProterties.put("hibernate.id.new_generator_mappings", this.environment
/*     */         
/* 183 */         .getProperty("hdds.hibernate.id.new_generator_mappings", Boolean.class, Boolean.valueOf(false)));
/*     */ 
/*     */ 
/*     */     
/* 187 */     if (this.environment.getProperty("ngtms.product.mode") != null && "false"
/* 188 */       .equalsIgnoreCase(this.environment.getProperty("ngtms.product.mode")) && 
/* 189 */       this.environment.getProperty("hdds.hibernate.hbm2ddl.auto") != null) {
/* 190 */       jpaProterties.put("hibernate.hbm2ddl.auto", this.environment
/* 191 */           .getProperty("hdds.hibernate.hbm2ddl.auto"));
/*     */     }
/* 193 */     entityManagerFactoryBean.setJpaProperties(jpaProterties);
/* 194 */     return entityManagerFactoryBean;
/*     */   }
/*     */ 
/*     */   
/*     */   @Bean(name = {"hddbTransactionManager"})
/*     */   public JpaTransactionManager transactionManager(@Qualifier("hddbDataSource") DataSource hddbDataSource) throws ClassNotFoundException {
/* 200 */     JpaTransactionManager transactionManager = new JpaTransactionManager();
/*     */     
/* 202 */     transactionManager.setEntityManagerFactory(entityManagerFactory(hddbDataSource).getObject());
/*     */     
/* 204 */     return transactionManager;
/*     */   }
/*     */   
/*     */   @Bean(name = {"hddbJdbcTemplate"})
/*     */   public JdbcTemplate jdbcTemplate(@Qualifier("hddbDataSource") DataSource hddbDataSource) {
/* 209 */     JdbcTemplate jdbcTemplate = new JdbcTemplate(hddbDataSource);
/* 210 */     return jdbcTemplate;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\HistoryDataBaseConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */