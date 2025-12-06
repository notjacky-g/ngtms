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
/*    */ @RunWith(SpringJUnit4ClassRunner.class)
/*    */ @ContextConfiguration(classes={RepositoryTestConfig.class}, loader=AnnotationConfigContextLoader.class)
/*    */ public abstract class RepositoryTestCase
/*    */ {
/*    */   @Autowired
/*    */   protected ApplicationContext appContext;
/*    */   
/*    */   static
/*    */   {
/* 27 */     System.setProperty("hcce.config.file", "conf/test/fmTest.properties");
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\test\RepositoryTestCase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */