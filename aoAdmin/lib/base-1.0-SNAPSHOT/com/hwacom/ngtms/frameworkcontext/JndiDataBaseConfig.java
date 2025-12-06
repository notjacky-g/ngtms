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
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.sql.DataSource;
/*     */ import org.hibernate.jpa.HibernatePersistenceProvider;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
/*     */ import org.springframework.context.annotation.Bean;
/*     */ import org.springframework.context.annotation.Configuration;
/*     */ import org.springframework.context.annotation.DependsOn;
/*     */ import org.springframework.context.annotation.Primary;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
/*     */ import org.springframework.jdbc.core.JdbcTemplate;
/*     */ import org.springframework.jdbc.datasource.DataSourceTransactionManager;
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
/*     */ @Configuration
/*     */ @EnableJpaRepositories(entityManagerFactoryRef = "oldbEntityManagerFactory", transactionManagerRef = "oldbTransactionManager", basePackages = {"com.hwacom.ngtms"})
/*     */ @EnableTransactionManagement
/*     */ public class JndiDataBaseConfig
/*     */ {
/*  44 */   private static Logger logger = LoggerFactory.getLogger(JndiDataBaseConfig.class);
/*     */   
/*     */   @Resource
/*     */   private Environment environment;
/*     */   
/*     */   @Autowired(required = false)
/*     */   private MetricRegistry metricRegistry;
/*     */   @Autowired(required = false)
/*     */   private HealthCheckRegistry healthCheckRegistry;
/*     */   @Value("${spring.datasource.jndi-name:jdbc/DefaultDS}")
/*     */   private String jndiName;
/*     */   
/*     */   @Bean(name = {"oldbDataSource"})
/*     */   public DataSource dataSource() {
/*  58 */     HikariConfig conf = new HikariConfig();
/*  59 */     DataSource ds = null;
/*     */     
/*  61 */     Context ctx = null;
/*     */     try {
/*  63 */       ctx = new InitialContext();
/*  64 */       ds = (DataSource)ctx.lookup(this.jndiName);
/*  65 */       logger.debug("ctx.lookup jndiName: {}", ds);
/*  66 */       conf.setDataSource(ds);
/*  67 */     } catch (Exception e) {
/*     */       
/*     */       try {
/*  70 */         Context initCtx = (Context)ctx.lookup("java:/comp/env");
/*  71 */         logger.debug("initCtx: {}", initCtx);
/*  72 */         ds = (DataSource)initCtx.lookup(this.jndiName);
/*  73 */         logger.debug("initCtx.lookup jndiName: {}", ds);
/*  74 */       } catch (Exception exception) {}
/*     */     } finally {
/*     */       
/*     */       try {
/*  78 */         ctx.close();
/*  79 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */     
/*  83 */     conf.setDriverClassName(this.environment.getRequiredProperty("olds.driverClassName"));
/*  84 */     conf.setJdbcUrl(ResolveString.resolve(this.environment.getRequiredProperty("olds.url")));
/*  85 */     conf.setUsername(this.environment.getRequiredProperty("olds.username"));
/*  86 */     conf.setPassword(this.environment.getRequiredProperty("olds.password"));
/*  87 */     conf.setPoolName("JNDI");
/*  88 */     conf.setAutoCommit(((Boolean)this.environment.getProperty("olds.autoCommit", Boolean.class, Boolean.valueOf(true))).booleanValue());
/*  89 */     conf.setConnectionTestQuery(this.environment
/*  90 */         .getProperty("olds.connectionTestQuery", "select 1 from dual"));
/*  91 */     conf.setMaximumPoolSize(((Integer)this.environment.getRequiredProperty("olds.maximumPoolSize", Integer.class)).intValue());
/*  92 */     conf.setMaxLifetime(((Long)this.environment.getProperty("olds.maxLifetime", Long.class, Long.valueOf(1800000L))).longValue());
/*  93 */     conf.setConnectionTimeout(((Long)this.environment
/*  94 */         .getProperty("olds.connectionTimeout", Long.class, Long.valueOf(10000L))).longValue());
/*  95 */     if (this.metricRegistry != null) {
/*  96 */       conf.setMetricRegistry(this.metricRegistry);
/*     */     }
/*  98 */     if (this.healthCheckRegistry != null) {
/*  99 */       conf.addHealthCheckProperty("connectivityCheckTimeoutMs", this.environment
/*     */           
/* 101 */           .getProperty("olds.connectivityCheckTimeoutMs", "1000"));
/* 102 */       conf.addHealthCheckProperty("expected99thPercentileMs", this.environment
/*     */           
/* 104 */           .getProperty("olds.expected99thPercentileMs", "10"));
/* 105 */       conf.setHealthCheckRegistry(this.healthCheckRegistry);
/*     */     } 
/*     */     
/* 108 */     logger.info("-------------JNDI Database Config-------------");
/* 109 */     logger.info("Driver ClassName: {}", conf.getDriverClassName());
/* 110 */     logger.info("Jdbc URL: {}", conf.getJdbcUrl());
/* 111 */     logger.info("Jdbc User: {}", conf.getUsername());
/* 112 */     logger.info("Hikari AutoCommit: {}", Boolean.valueOf(conf.isAutoCommit()));
/* 113 */     logger.info("Hikari ConnectionTestQuery: {}", conf.getConnectionTestQuery());
/* 114 */     logger.info("Hikari MaximumPoolSize: {}", Integer.valueOf(conf.getMaximumPoolSize()));
/* 115 */     logger.info("Hikari MaxLifetime: {}", Long.valueOf(conf.getMaxLifetime()));
/* 116 */     logger.info("Hikari ConnectionTimeout: {}", Long.valueOf(conf.getConnectionTimeout()));
/* 117 */     logger.info("Hikari HealthCheck: {}", conf.getHealthCheckProperties());
/* 118 */     logger.info("-----------------------------------------");
/*     */     
/* 120 */     if (ds != null) {
/* 121 */       return (DataSource)new HikariDataSource(conf);
/*     */     }
/* 123 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Bean(name = {"oldbEntityManagerFactory"})
/*     */   @DependsOn({"oldbDataSource"})
/*     */   public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("oldbDataSource") DataSource dataSource) throws ClassNotFoundException {
/* 131 */     LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
/*     */     
/* 133 */     entityManagerFactoryBean.setPersistenceUnitName("NGTMS_OLDB");
/* 134 */     entityManagerFactoryBean.setDataSource(dataSource);
/*     */     
/* 136 */     String[] packageToScan = this.environment.getRequiredProperty("olds.packages.to.scan").trim().split("\\s*,\\s*");
/* 137 */     entityManagerFactoryBean.setPackagesToScan(packageToScan);
/*     */     
/* 139 */     entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
/*     */     
/* 141 */     Properties jpaProterties = new Properties();
/* 142 */     jpaProterties.put("hibernate.dialect", this.environment
/* 143 */         .getRequiredProperty("olds.hibernate.dialect"));
/* 144 */     jpaProterties.put("hibernate.format_sql", this.environment
/* 145 */         .getRequiredProperty("olds.hibernate.format_sql"));
/* 146 */     jpaProterties.put("hibernate.show_sql", this.environment
/* 147 */         .getRequiredProperty("olds.hibernate.show_sql"));
/* 148 */     jpaProterties.put(ConfigProperties.HIBERNATE_NAMING_STRATEGY[0], ConfigProperties.HIBERNATE_NAMING_STRATEGY[1]);
/*     */ 
/*     */     
/* 151 */     jpaProterties.put(ConfigProperties.HIBERNATE_PHYSICAL_NAMING_STRATEGY[0], ConfigProperties.HIBERNATE_PHYSICAL_NAMING_STRATEGY[1]);
/*     */ 
/*     */     
/* 154 */     jpaProterties.put("hibernate.id.new_generator_mappings", this.environment
/*     */         
/* 156 */         .getProperty("olds.hibernate.id.new_generator_mappings", Boolean.class, Boolean.valueOf(false)));
/*     */ 
/*     */ 
/*     */     
/* 160 */     if (this.environment.getProperty("ngtms.product.mode") != null && "false"
/* 161 */       .equalsIgnoreCase(this.environment.getProperty("ngtms.product.mode")) && 
/* 162 */       this.environment.getProperty("olds.hibernate.hbm2ddl.auto") != null) {
/* 163 */       jpaProterties.put("hibernate.hbm2ddl.auto", this.environment
/* 164 */           .getProperty("olds.hibernate.hbm2ddl.auto"));
/*     */     }
/* 166 */     entityManagerFactoryBean.setJpaProperties(jpaProterties);
/* 167 */     return entityManagerFactoryBean;
/*     */   }
/*     */ 
/*     */   
/*     */   @Primary
/*     */   @Bean(name = {"oldbTransactionManager"})
/*     */   @ConditionalOnBean(name = {"oldbDataSource"})
/*     */   public JpaTransactionManager jpaTransactionManager(@Qualifier("oldbDataSource") DataSource dataSource) throws ClassNotFoundException {
/* 175 */     JpaTransactionManager transactionManager = new JpaTransactionManager();
/*     */     
/* 177 */     transactionManager.setEntityManagerFactory(entityManagerFactory(dataSource).getObject());
/*     */     
/* 179 */     return transactionManager;
/*     */   }
/*     */ 
/*     */   
/*     */   @Bean(name = {"transactionManager"})
/*     */   @ConditionalOnBean(name = {"oldbDataSource"})
/*     */   public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("oldbDataSource") DataSource dataSource) throws ClassNotFoundException {
/* 186 */     DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
/* 187 */     transactionManager.setDefaultTimeout(30);
/* 188 */     transactionManager.setRollbackOnCommitFailure(true);
/*     */ 
/*     */ 
/*     */     
/* 192 */     return transactionManager;
/*     */   }
/*     */   
/*     */   @Primary
/*     */   @Bean(name = {"oldbJdbcTemplate"})
/*     */   @ConditionalOnBean(name = {"oldbDataSource"})
/*     */   public JdbcTemplate jdbcTemplate(@Qualifier("oldbDataSource") DataSource dataSource) {
/* 199 */     JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
/* 200 */     return jdbcTemplate;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\JndiDataBaseConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */