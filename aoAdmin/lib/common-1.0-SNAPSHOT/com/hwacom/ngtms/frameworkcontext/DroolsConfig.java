/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Scanner;
/*    */ import org.kie.api.KieBase;
/*    */ import org.kie.api.KieServices;
/*    */ import org.kie.api.builder.KieBuilder;
/*    */ import org.kie.api.builder.KieFileSystem;
/*    */ import org.kie.api.builder.KieModule;
/*    */ import org.kie.api.builder.KieRepository;
/*    */ import org.kie.api.builder.ReleaseId;
/*    */ import org.kie.api.runtime.KieContainer;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.BeanFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.beans.factory.annotation.Qualifier;
/*    */ import org.springframework.beans.factory.annotation.Value;
/*    */ import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.context.annotation.DependsOn;
/*    */ import org.springframework.core.io.Resource;
/*    */ import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ public class DroolsConfig
/*    */ {
/* 34 */   private static final Logger logger = LoggerFactory.getLogger(DroolsConfig.class);
/*    */   @Autowired
/*    */   private BeanFactory beanFactory;
/*    */   @Value("${kieSession.pool.size:100}")
/* 38 */   private int kieSessionPoolSize = 100;
/*    */   
/*    */   private KieSessionPool kieSessionPool;
/*    */ 
/*    */   
/*    */   @Bean(name = {"kieSessionPool"})
/*    */   @DependsOn({"kieContainer"})
/*    */   public KieSessionPool createKieSessionPool(@Qualifier("kieContainer") KieContainer container) {
/* 46 */     if (this.kieSessionPool == null) {
/* 47 */       this.kieSessionPool = new KieSessionPool(container, this.kieSessionPoolSize);
/*    */     }
/* 49 */     return this.kieSessionPool;
/*    */   }
/*    */   
/*    */   @Bean(name = {"kieContainer"})
/*    */   @ConditionalOnMissingBean({KieContainer.class})
/*    */   public KieContainer createKieContainer() throws IOException {
/* 55 */     KieServices ks = KieServices.Factory.get();
/* 56 */     final KieRepository kr = ks.getRepository();
/* 57 */     kr.addKieModule(new KieModule()
/*    */         {
/*    */           public ReleaseId getReleaseId()
/*    */           {
/* 61 */             return kr.getDefaultReleaseId();
/*    */           }
/*    */         });
/* 64 */     KieFileSystem kfs = ks.newKieFileSystem();
/*    */     
/* 66 */     Resource[] files = (new PathMatchingResourcePatternResolver()).getResources("classpath*:rules/**/*.*");
/*    */     
/* 68 */     for (Resource file : files) {
/* 69 */       kfs.write("src/main/resources/" + file
/* 70 */           .getFilename(), convertStreamToString(file.getInputStream()));
/* 71 */       logger.info("find rule: {}", file.getFilename());
/*    */     } 
/*    */     
/* 74 */     KieBuilder kb = ks.newKieBuilder(kfs);
/* 75 */     kb.buildAll();
/* 76 */     KieContainer kContainer = ks.newKieContainer(kr.getDefaultReleaseId());
/* 77 */     return kContainer;
/*    */   }
/*    */   
/*    */   @Bean
/*    */   @ConditionalOnMissingBean({KieBase.class})
/*    */   public KieBase kieBase() throws IOException {
/* 83 */     return ((KieContainer)this.beanFactory.getBean(KieContainer.class)).getKieBase();
/*    */   }
/*    */   
/*    */   @Bean(name = {"reNewKieSessionPool"})
/*    */   public KieSessionPool reNewKieSessionPool() throws IOException {
/* 88 */     if (this.kieSessionPool != null) {
/* 89 */       this.kieSessionPool.preDestroy();
/* 90 */       this.kieSessionPool = null;
/*    */     } 
/* 92 */     this.kieSessionPool = createKieSessionPool(createKieContainer());
/* 93 */     return this.kieSessionPool;
/*    */   }
/*    */   
/*    */   private static String convertStreamToString(InputStream is) {
/* 97 */     try (Scanner scanner = new Scanner(is, "UTF-8")) {
/* 98 */       scanner.useDelimiter("\\A");
/* 99 */       return scanner.hasNext() ? scanner.next() : "";
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\DroolsConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */