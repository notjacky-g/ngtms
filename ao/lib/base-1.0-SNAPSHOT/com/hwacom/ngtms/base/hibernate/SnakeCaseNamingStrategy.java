/*    */ package com.hwacom.ngtms.base.hibernate;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Locale;
/*    */ import org.hibernate.boot.model.naming.Identifier;
/*    */ import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
/*    */ import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SnakeCaseNamingStrategy
/*    */   extends PhysicalNamingStrategyStandardImpl
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -3931319518092248067L;
/* 21 */   public static final SnakeCaseNamingStrategy INSTANCE = new SnakeCaseNamingStrategy();
/*    */   
/*    */   public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context)
/*    */   {
/* 25 */     return new Identifier(addUnderscores(name.getText()), name.isQuoted());
/*    */   }
/*    */   
/*    */   public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context)
/*    */   {
/* 30 */     return new Identifier(addUnderscores(name.getText()), name.isQuoted());
/*    */   }
/*    */   
/*    */   private static String addUnderscores(String name) {
/* 34 */     StringBuilder buf = new StringBuilder(name.replace('.', '_'));
/* 35 */     for (int i = 1; i < buf.length(); i++) {
/* 36 */       if ((Character.isLowerCase(buf.charAt(i - 1))) && (Character.isUpperCase(buf.charAt(i)))) {
/* 37 */         buf.insert(i++, '_');
/*    */       }
/*    */     }
/* 40 */     return buf.toString().toLowerCase(Locale.ROOT);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hibernate\SnakeCaseNamingStrategy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */