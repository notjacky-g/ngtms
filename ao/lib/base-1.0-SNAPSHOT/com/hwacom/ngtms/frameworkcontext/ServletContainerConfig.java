/*     */ package com.hwacom.ngtms.frameworkcontext;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.net.URI;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.boot.web.server.ErrorPage;
/*     */ import org.springframework.boot.web.server.WebServerFactoryCustomizer;
/*     */ import org.springframework.boot.web.servlet.ServletContextInitializer;
/*     */ import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ import org.springframework.context.ApplicationContextAware;
/*     */ import org.springframework.context.annotation.Bean;
/*     */ import org.springframework.context.annotation.Configuration;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.http.HttpStatus;
/*     */ import org.springframework.util.StringUtils;
/*     */ import org.springframework.web.multipart.commons.CommonsMultipartResolver;
/*     */ import org.springframework.web.servlet.ViewResolver;
/*     */ import org.springframework.web.servlet.config.annotation.EnableWebMvc;
/*     */ import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
/*     */ import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
/*     */ import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*     */ import org.springframework.web.servlet.i18n.CookieLocaleResolver;
/*     */ import org.thymeleaf.spring5.ISpringTemplateEngine;
/*     */ import org.thymeleaf.spring5.SpringTemplateEngine;
/*     */ import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
/*     */ import org.thymeleaf.spring5.view.ThymeleafViewResolver;
/*     */ import org.thymeleaf.templatemode.TemplateMode;
/*     */ import org.thymeleaf.templateresolver.ITemplateResolver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Configuration
/*     */ @EnableWebMvc
/*     */ public class ServletContainerConfig
/*     */   implements ApplicationContextAware, ServletContextInitializer, WebMvcConfigurer
/*     */ {
/*  46 */   private static final Logger logger = LoggerFactory.getLogger(ServletContainerConfig.class);
/*     */   
/*  48 */   private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/" };
/*     */   
/*     */ 
/*     */   private static final String STATIC_ASSETS_FOLDER_PARAM = "static.assets.folder";
/*     */   
/*     */   @Resource
/*     */   private Environment environment;
/*     */   
/*     */   @Value("${static.assets.folder:src/main/resources/temp}")
/*     */   private String staticAssetsFolderPath;
/*     */   
/*     */   @Value("${thymeleaf.templates.cache:false}")
/*     */   private Boolean thymeleafCache;
/*     */   
/*     */   private ApplicationContext applicationContext;
/*     */   
/*     */ 
/*     */   public void setApplicationContext(ApplicationContext applicationContext)
/*     */   {
/*  67 */     this.applicationContext = applicationContext;
/*     */   }
/*     */   
/*     */   public void onStartup(ServletContext servletContext) throws ServletException
/*     */   {
/*  72 */     if (this.environment.getActiveProfiles().length > 0) {
/*  73 */       logger.info("Web application configuration, profiles: {}", 
/*     */       
/*  75 */         (Object[])this.environment.getActiveProfiles());
/*     */     }
/*  77 */     logger.info("static.assets.folder: '{}'", this.staticAssetsFolderPath);
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer()
/*     */   {
/*  83 */     new WebServerFactoryCustomizer()
/*     */     {
/*     */       public void customize(ConfigurableServletWebServerFactory factory)
/*     */       {
/*  87 */         ServletContainerConfig.this.customizeDocumentRoot(factory);
/*  88 */         ServletContainerConfig.this.customizeErrorPages(factory);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   private void customizeDocumentRoot(ConfigurableServletWebServerFactory factory) {
/*  94 */     if (!StringUtils.isEmpty(this.staticAssetsFolderPath)) { File docRoot;
/*     */       File docRoot;
/*  96 */       if (this.staticAssetsFolderPath.startsWith(File.separator)) {
/*  97 */         docRoot = new File(this.staticAssetsFolderPath);
/*     */       } else {
/*  99 */         String workPath = Paths.get(".", new String[0]).toUri().normalize().getPath();
/* 100 */         docRoot = new File(workPath + this.staticAssetsFolderPath);
/*     */       }
/* 102 */       logger.info("Custom location document root folder: {}", docRoot.getAbsolutePath());
/* 103 */       if ((docRoot.exists()) && (docRoot.isDirectory())) {
/* 104 */         factory.setDocumentRoot(docRoot);
/*     */       } else {
/* 106 */         logger.warn("Custom document root folder {} doesn't exist.", docRoot.getAbsolutePath());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void customizeErrorPages(ConfigurableServletWebServerFactory factory) {
/* 112 */     ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
/* 113 */     ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
/* 114 */     ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
/* 115 */     factory.addErrorPages(new ErrorPage[] { error401Page, error404Page, error500Page });
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public ViewResolver viewResolver() {
/* 120 */     ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
/* 121 */     viewResolver.setTemplateEngine(templateEngine());
/*     */     
/* 123 */     viewResolver.setOrder(1);
/* 124 */     viewResolver.setCharacterEncoding("UTF-8");
/* 125 */     return viewResolver;
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public ISpringTemplateEngine templateEngine() {
/* 130 */     SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
/* 131 */     springTemplateEngine.setEnableSpringELCompiler(true);
/* 132 */     springTemplateEngine.setTemplateResolver(templateResolver());
/* 133 */     return springTemplateEngine;
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public ITemplateResolver templateResolver() {
/* 138 */     SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
/*     */     
/* 140 */     templateResolver.setApplicationContext(this.applicationContext);
/* 141 */     templateResolver.setPrefix("classpath:/templates/");
/* 142 */     templateResolver.setSuffix(".html");
/* 143 */     templateResolver.setTemplateMode(TemplateMode.HTML);
/* 144 */     templateResolver.setCharacterEncoding("UTF-8");
/* 145 */     templateResolver.setOrder(Integer.valueOf(0));
/*     */     
/* 147 */     if (this.thymeleafCache.booleanValue()) {
/* 148 */       templateResolver.setCacheable(true);
/*     */     } else {
/* 150 */       templateResolver.setCacheable(false);
/*     */     }
/* 152 */     return templateResolver;
/*     */   }
/*     */   
/*     */   public void addResourceHandlers(ResourceHandlerRegistry registry) {
/* 156 */     if (!registry.hasMappingForPattern("/images/**"))
/*     */     {
/*     */ 
/* 159 */       registry.addResourceHandler(new String[] { "/images/**" }).addResourceLocations(new String[] { "classpath:/static/images/" });
/*     */     }
/* 161 */     if (!registry.hasMappingForPattern("/css/**"))
/*     */     {
/*     */ 
/* 164 */       registry.addResourceHandler(new String[] { "/css/**" }).addResourceLocations(new String[] { "classpath:/static/css/" });
/*     */     }
/* 166 */     if (!registry.hasMappingForPattern("/js/**"))
/*     */     {
/*     */ 
/* 169 */       registry.addResourceHandler(new String[] { "/js/**" }).addResourceLocations(new String[] { "classpath:/static/js/" });
/*     */     }
/* 171 */     if (!registry.hasMappingForPattern("/webjars/**"))
/*     */     {
/*     */ 
/* 174 */       registry.addResourceHandler(new String[] { "/webjars/**" }).addResourceLocations(new String[] { "classpath:/META-INF/resources/webjars/" });
/*     */     }
/* 176 */     if (!registry.hasMappingForPattern("/**")) {
/* 177 */       registry.addResourceHandler(new String[] { "/**" }).addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Bean
/*     */   public CookieLocaleResolver localeResolver()
/*     */   {
/* 191 */     return new CookieLocaleResolver();
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public CommonsMultipartResolver multipartResolver() {
/* 196 */     CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
/* 197 */     multipartResolver.setMaxUploadSize(1048576L);
/* 198 */     return multipartResolver;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\ServletContainerConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */