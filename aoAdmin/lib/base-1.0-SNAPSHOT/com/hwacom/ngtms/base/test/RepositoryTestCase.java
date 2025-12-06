/*    */ package com.hwacom.ngtms.base.test;
/*    */ 
/*    */ import com.hwacom.ngtms.base.test.frameworkcontext.RepositoryTestConfig;
/*    */ import org.junit.runner.RunWith;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.test.context.ContextConfiguration;
/*    */ import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/*    */ import org.springframework.test.context.support.AnnotationConfigContextLoader;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @RunWith(SpringJUnit4ClassRunner.class)
/*    */ @ContextConfiguration(classes = {RepositoryTestConfig.class}, loader = AnnotationConfigContextLoader.class)
/*    */ public abstract class RepositoryTestCase
/*    */ {
/*    */   @Autowired
/*    */   protected ApplicationContext appContext;
/*    */   
/*    */   static {
/* 27 */     System.setProperty("hcce.config.file", "conf/test/fmTest.properties");
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\test\RepositoryTestCase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */