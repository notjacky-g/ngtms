/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import org.apache.commons.pool2.KeyedPooledObjectFactory;
/*    */ import org.apache.commons.pool2.PooledObject;
/*    */ import org.apache.commons.pool2.impl.DefaultPooledObject;
/*    */ import org.kie.api.runtime.KieContainer;
/*    */ import org.kie.api.runtime.KieSession;
/*    */ import org.kie.api.runtime.rule.FactHandle;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KiePoolableSessionFactory
/*    */   implements KeyedPooledObjectFactory<String, KieSession>
/*    */ {
/* 21 */   private static final Logger logger = LoggerFactory.getLogger(KiePoolableSessionFactory.class);
/*    */   
/*    */   private KieContainer kieContainer;
/*    */   
/*    */   public KiePoolableSessionFactory(KieContainer kieContainer) {
/* 26 */     this.kieContainer = kieContainer;
/*    */   }
/*    */ 
/*    */   
/*    */   public PooledObject<KieSession> makeObject(String s) throws Exception {
/* 31 */     KieSession kieSession = this.kieContainer.newKieSession();
/* 32 */     DefaultPooledObject<KieSession> poolObject = new DefaultPooledObject(kieSession);
/*    */     
/* 34 */     return (PooledObject<KieSession>)poolObject;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void activateObject(String s, PooledObject<KieSession> pooledObject) throws Exception {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean validateObject(String s, PooledObject<KieSession> pooledObject) {
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void passivateObject(String s, PooledObject<KieSession> pooledObject) throws Exception {
/* 51 */     KieSession object = (KieSession)pooledObject.getObject();
/* 52 */     for (FactHandle factHandle : object.getFactHandles()) {
/* 53 */       logger.debug("passivateObject Delete %s ", factHandle);
/* 54 */       object.delete(factHandle);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void destroyObject(String s, PooledObject<KieSession> pooledObject) throws Exception {
/* 61 */     KieSession object = (KieSession)pooledObject.getObject();
/* 62 */     object.dispose();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\KiePoolableSessionFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */