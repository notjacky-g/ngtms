/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.web.servlet.config.annotation.CorsRegistry;
/*    */ import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*    */ import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ public class CrossOriginConfig
/*    */ {
/*    */   @Bean
/*    */   public WebMvcConfigurer corsConfigurer() {
/* 20 */     return (WebMvcConfigurer)new WebMvcConfigurerAdapter()
/*    */       {
/*    */         public void addCorsMappings(CorsRegistry registry) {
/* 23 */           registry.addMapping("/api/**");
/* 24 */           registry.addMapping("/websocket/**");
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\CrossOriginConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */