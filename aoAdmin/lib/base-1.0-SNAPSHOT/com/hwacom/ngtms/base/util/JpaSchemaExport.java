/*    */ package com.hwacom.ngtms.base.util;
/*    */ 
/*    */ import java.util.Properties;
/*    */ import javax.persistence.Persistence;
/*    */ import org.springframework.boot.CommandLineRunner;
/*    */ 
/*    */ public class JpaSchemaExport
/*    */   implements CommandLineRunner {
/*    */   public static void execute(String persistenceUnitName, String destination) {
/* 10 */     Properties persistenceProperties = new Properties();
/* 11 */     persistenceProperties.setProperty("hibernate.hbm2ddl.auto", "true");
/* 12 */     persistenceProperties.setProperty("javax.persistence.schema-generation.database.action", "none");
/*    */     
/* 14 */     persistenceProperties.setProperty("javax.persistence.schema-generation.scripts.action", "create");
/*    */     
/* 16 */     persistenceProperties.setProperty("javax.persistence.schema-generation.create-source", "metadata");
/*    */     
/* 18 */     persistenceProperties.setProperty("javax.persistence.schema-generation.scripts.create-target", destination);
/*    */     
/* 20 */     Persistence.generateSchema(persistenceUnitName, persistenceProperties);
/*    */   }
/*    */ 
/*    */   
/*    */   public void run(String... args) throws Exception {
/* 25 */     if (args != null && args.length >= 2) {
/* 26 */       execute(args[0], args[1]);
/*    */     } else {
/* 28 */       execute("NGTMS_OLDB", "build/generated-schema.sql");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\bas\\util\JpaSchemaExport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */