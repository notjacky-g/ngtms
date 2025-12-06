/*    */ package com.hwacom.ngtms.base.hibernate;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import org.hibernate.HibernateException;
/*    */ import org.hibernate.annotations.common.util.StringHelper;
/*    */ import org.hibernate.boot.model.naming.EntityNaming;
/*    */ import org.hibernate.boot.model.naming.Identifier;
/*    */ import org.hibernate.boot.model.naming.ImplicitBasicColumnNameSource;
/*    */ import org.hibernate.boot.model.naming.ImplicitEntityNameSource;
/*    */ import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImplicitSnakeCaseNamingStrategy
/*    */   extends ImplicitNamingStrategyLegacyJpaImpl
/*    */ {
/*    */   private static final long serialVersionUID = -3115966676148494742L;
/*    */   
/*    */   public Identifier determinePrimaryTableName(ImplicitEntityNameSource source) {
/* 28 */     String tableName = transformEntityNameOrSnakeCase(source.getEntityNaming());
/* 29 */     if (tableName == null)
/*    */     {
/*    */       
/* 32 */       throw new HibernateException("Could not determine primary table name for entity");
/*    */     }
/*    */     
/* 35 */     return toIdentifier(addUnderscores(tableName), source.getBuildingContext());
/*    */   }
/*    */ 
/*    */   
/*    */   private String transformEntityNameOrSnakeCase(EntityNaming entityNaming) {
/* 40 */     if (StringHelper.isNotEmpty(entityNaming.getJpaEntityName())) {
/* 41 */       return entityNaming.getJpaEntityName();
/*    */     }
/*    */     
/* 44 */     return addUnderscores(StringHelper.unqualify(entityNaming.getEntityName()));
/*    */   }
/*    */ 
/*    */   
/*    */   private static String addUnderscores(String name) {
/* 49 */     StringBuilder buf = new StringBuilder(name.replace('.', '_'));
/* 50 */     for (int i = 1; i < buf.length(); i++) {
/* 51 */       if (Character.isLowerCase(buf.charAt(i - 1)) && Character.isUpperCase(buf.charAt(i))) {
/* 52 */         buf.insert(i++, '_');
/*    */       }
/*    */     } 
/* 55 */     return buf.toString().toLowerCase(Locale.ROOT);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Identifier determineBasicColumnName(ImplicitBasicColumnNameSource source) {
/* 62 */     String property = source.getAttributePath().getProperty();
/* 63 */     String s = addUnderscores(property);
/* 64 */     return toIdentifier(s, source.getBuildingContext());
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hibernate\ImplicitSnakeCaseNamingStrategy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */