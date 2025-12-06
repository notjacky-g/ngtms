/*     */ package com.hwacom.ngtms.base.test.frameworkcontext;
/*     */ 
/*     */ import com.hwacom.ngtms.base.hibernate.ConfigProperties;
/*     */ import com.hwacom.ngtms.base.util.ResolveString;
/*     */ import com.zaxxer.hikari.HikariConfig;
/*     */ import com.zaxxer.hikari.HikariDataSource;
/*     */ import java.util.Properties;
/*     */ import javax.annotation.Resource;
/*     */ import javax.persistence.Entity;
/*     */ import javax.sql.DataSource;
/*     */ import org.hibernate.jpa.HibernatePersistenceProvider;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
/*     */ import org.springframework.context.annotation.Bean;
/*     */ import org.springframework.context.annotation.ComponentScan;
/*     */ import org.springframework.context.annotation.ComponentScan.Filter;
/*     */ import org.springframework.context.annotation.Configuration;
/*     */ import org.springframework.context.annotation.DependsOn;
/*     */ import org.springframework.context.annotation.FilterType;
/*     */ import org.springframework.context.annotation.PropertySource;
/*     */ import org.springframework.context.annotation.PropertySources;
/*     */ import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
/*     */ import org.springframework.orm.jpa.JpaTransactionManager;
/*     */ import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
/*     */ import org.springframework.stereotype.Repository;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Configuration
/*     */ @EnableJpaRepositories(basePackages = {"com.hwacom.ngtms"}, entityManagerFactoryRef = "oldbEntityManagerFactory", transactionManagerRef = "oldbTransactionManager", basePackageClasses = {}, includeFilters = {@Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms.*.repository.*"})})
/*     */ @EnableTransactionManagement
/*     */ @ComponentScan(basePackages = {"com.hwacom.ngtms"}, basePackageClasses = {}, useDefaultFilters = false, includeFilters = {@Filter({Entity.class}), @Filter({Repository.class})})
/*     */ @PropertySources({@PropertySource({"classpath:${hcce.config.file:conf/test/fmTest.properties}"}), @PropertySource(value = {"file:${hcce.override.config.file}"}, ignoreResourceNotFound = true)})
/*     */ public class RepositoryTestConfig
/*     */ {
/*     */   @Resource
/*     */   private Environment environment;
/*     */   
/*     */   @Bean
/*     */   public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
/*  62 */     return new PropertySourcesPlaceholderConfigurer();
/*     */   }
/*     */   
/*     */   @Bean(name = {"oldbDataSource"})
/*     */   public DataSource oldbDataSource() {
/*  67 */     HikariConfig conf = new HikariConfig();
/*  68 */     conf.setDataSourceClassName(this.environment.getRequiredProperty("olds.driverClassName"));
/*  69 */     conf.addDataSourceProperty("url", 
/*  70 */         ResolveString.resolve(this.environment.getRequiredProperty("olds.url")));
/*  71 */     conf.addDataSourceProperty("user", this.environment.getRequiredProperty("olds.username"));
/*  72 */     conf.addDataSourceProperty("password", this.environment.getRequiredProperty("olds.password"));
/*  73 */     conf.setPoolName("Online");
/*  74 */     conf.setAutoCommit(((Boolean)this.environment.getProperty("olds.autoCommit", Boolean.class, Boolean.valueOf(true))).booleanValue());
/*  75 */     conf.setConnectionTestQuery(this.environment
/*  76 */         .getProperty("olds.connectionTestQuery", "select 1 from dual"));
/*  77 */     conf.setMaximumPoolSize(((Integer)this.environment.getRequiredProperty("olds.maximumPoolSize", Integer.class)).intValue());
/*  78 */     conf.setMaxLifetime(((Long)this.environment.getProperty("olds.maxLifetime", Long.class, Long.valueOf(1800000L))).longValue());
/*  79 */     conf.setConnectionTimeout(((Long)this.environment
/*  80 */         .getProperty("olds.connectionTimeout", Long.class, Long.valueOf(10000L))).longValue());
/*  81 */     HikariDataSource ds = new HikariDataSource(conf);
/*  82 */     return (DataSource)ds;
/*     */   }
/*     */ 
/*     */   
/*     */   @Bean(name = {"oldbEntityManagerFactory"})
/*     */   @DependsOn({"oldbDataSource"})
/*     */   public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("oldbDataSource") DataSource dataSource) throws ClassNotFoundException {
/*  89 */     LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
/*     */     
/*  91 */     entityManagerFactoryBean.setPersistenceUnitName("NGTMS_TEST");
/*  92 */     entityManagerFactoryBean.setDataSource(dataSource);
/*     */     
/*  94 */     String[] packageToScan = this.environment.getRequiredProperty("olds.packages.to.scan").trim().split("\\s*,\\s*");
/*  95 */     entityManagerFactoryBean.setPackagesToScan(packageToScan);
/*     */ 
/*     */     
/*  98 */     entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
/*     */     
/* 100 */     Properties jpaProterties = new Properties();
/* 101 */     jpaProterties.put("hibernate.dialect", this.environment
/* 102 */         .getRequiredProperty("olds.hibernate.dialect"));
/* 103 */     jpaProterties.put("hibernate.format_sql", this.environment
/* 104 */         .getRequiredProperty("olds.hibernate.format_sql"));
/* 105 */     jpaProterties.put("hibernate.show_sql", this.environment
/* 106 */         .getRequiredProperty("olds.hibernate.show_sql"));
/* 107 */     jpaProterties.put(ConfigProperties.HIBERNATE_NAMING_STRATEGY[0], ConfigProperties.HIBERNATE_NAMING_STRATEGY[1]);
/*     */ 
/*     */     
/* 110 */     jpaProterties.put(ConfigProperties.HIBERNATE_PHYSICAL_NAMING_STRATEGY[0], ConfigProperties.HIBERNATE_PHYSICAL_NAMING_STRATEGY[1]);
/*     */ 
/*     */     
/* 113 */     jpaProterties.put("hibernate.id.new_generator_mappings", this.environment
/*     */         
/* 115 */         .getProperty("olds.hibernate.id.new_generator_mappings", Boolean.class, Boolean.valueOf(false)));
/*     */     
/* 117 */     jpaProterties.put("hibernate.hbm2ddl.auto", this.environment
/* 118 */         .getProperty("olds.hibernate.hbm2ddl.auto"));
/* 119 */     entityManagerFactoryBean.setJpaProperties(jpaProterties);
/* 120 */     return entityManagerFactoryBean;
/*     */   }
/*     */ 
/*     */   
/*     */   @Bean(name = {"oldbTransactionManager"})
/*     */   @ConditionalOnBean(name = {"oldbDataSource"})
/*     */   public JpaTransactionManager transactionManager(@Qualifier("oldbDataSource") DataSource dataSource) throws ClassNotFoundException {
/* 127 */     JpaTransactionManager transactionManager = new JpaTransactionManager();
/*     */     
/* 129 */     transactionManager.setEntityManagerFactory(entityManagerFactory(dataSource).getObject());
/*     */     
/* 131 */     return transactionManager;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\test\frameworkcontext\RepositoryTestConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */