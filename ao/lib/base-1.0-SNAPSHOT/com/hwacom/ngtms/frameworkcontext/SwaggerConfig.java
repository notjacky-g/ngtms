/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import springfox.documentation.builders.PathSelectors;
/*    */ import springfox.documentation.builders.RequestHandlerSelectors;
/*    */ import springfox.documentation.spi.DocumentationType;
/*    */ import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
/*    */ import springfox.documentation.spring.web.plugins.Docket;
/*    */ import springfox.documentation.swagger2.annotations.EnableSwagger2;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ @EnableSwagger2
/*    */ public class SwaggerConfig
/*    */ {
/*    */   @Bean
/*    */   public Docket api()
/*    */   {
/* 21 */     return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\SwaggerConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */