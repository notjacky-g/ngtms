/*     */ package com.hwacom.ngtms.frameworkcontext;
/*     */ 
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javassist.Modifier;
/*     */ import javax.persistence.EntityManager;
/*     */ import javax.persistence.EntityManagerFactory;
/*     */ import javax.persistence.PersistenceContext;
/*     */ import javax.persistence.PersistenceException;
/*     */ import javax.persistence.Query;
/*     */ import javax.persistence.Table;
/*     */ import javax.persistence.metamodel.EntityType;
/*     */ import javax.persistence.metamodel.Metamodel;
/*     */ import org.aspectj.lang.JoinPoint;
/*     */ import org.aspectj.lang.Signature;
/*     */ import org.aspectj.lang.annotation.Aspect;
/*     */ import org.aspectj.lang.annotation.Before;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.data.jpa.repository.JpaRepository;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ @Service
/*     */ @Aspect
/*     */ public class RepositoryAspect
/*     */ {
/*  38 */   private static final Logger logger = LoggerFactory.getLogger(RepositoryAspect.class);
/*     */   
/*     */   private static Set<String> useHddbClassSet;
/*     */   
/*     */   @PersistenceContext(unitName="NGTMS_OLDB")
/*     */   private EntityManager em;
/*     */   
/*     */   @Before("execution(*  com.hwacom.ngtms..fm.repository.*.find*(..))")
/*     */   private void changeDataSource(JoinPoint joinPoint)
/*     */   {
/*  48 */     Class<?> entityClass = null;
/*     */     try {
/*  50 */       Class<?> declaringType = joinPoint.getSignature().getDeclaringType();
/*  51 */       if (declaringType == null) {
/*  52 */         setUseOldb();
/*  53 */         return;
/*     */       }
/*  55 */       Type[] types = joinPoint.getSignature().getDeclaringType().getGenericInterfaces();
/*  56 */       if (types == null) {
/*  57 */         setUseOldb();
/*  58 */         return;
/*     */       }
/*  60 */       for (int i = 0; i < types.length; i++)
/*  61 */         if ((types[i] instanceof ParameterizedType)) {
/*  62 */           ParameterizedType pt = (ParameterizedType)types[i];
/*  63 */           if (pt.getRawType().equals(JpaRepository.class)) {
/*  64 */             entityClass = (Class)pt.getActualTypeArguments()[0];
/*  65 */             break;
/*     */           }
/*     */         }
/*  68 */       if (entityClass == null) {
/*  69 */         setUseOldb();
/*  70 */         return;
/*     */       }
/*     */     } catch (Exception ex) {
/*  73 */       logger.warn("getType have exception!.", ex); return;
/*     */     }
/*     */     Type[] types;
/*  76 */     String entityClassName = entityClass.getSimpleName();
/*     */     
/*  78 */     if (!getUseHddbClassSet().contains(entityClassName.toLowerCase())) {
/*  79 */       HistoryDataSourceHolder.setDataSource(HistoryDataSourceHolder.DataSource.OLDB);
/*  80 */       logger.debug("{} use oldb.", entityClassName);
/*  81 */       return;
/*     */     }
/*  83 */     String methodName = joinPoint.getSignature().getName();
/*  84 */     if (!methodName.startsWith("find")) {
/*  85 */       HistoryDataSourceHolder.setDataSource(HistoryDataSourceHolder.DataSource.OLDB);
/*  86 */       logger.debug("{} use oldb.", entityClassName);
/*  87 */       return;
/*     */     }
/*  89 */     Date firstDate = null;
/*  90 */     Object[] args = joinPoint.getArgs();
/*  91 */     boolean useHddb = true;
/*  92 */     for (Object arg : args) {
/*  93 */       if ((arg != null) && ((arg instanceof Date))) {
/*  94 */         Calendar c = Calendar.getInstance();
/*  95 */         c.add(2, -1);
/*  96 */         Date d = (Date)arg;
/*  97 */         if (firstDate == null) {
/*  98 */           firstDate = d;
/*     */         }
/*     */         
/* 101 */         if (!d.before(c.getTime())) {
/* 102 */           useHddb = false;
/* 103 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 107 */     if ((!useHddb) && (firstDate != null)) {
/* 108 */       HistoryDataSourceHolder.setDataSource(HistoryDataSourceHolder.DataSource.OLDB);
/* 109 */       logger.info("{} startTime {} use oldb.", methodName, firstDate);
/*     */     } else {
/* 111 */       HistoryDataSourceHolder.setDataSource(HistoryDataSourceHolder.DataSource.HDDB);
/* 112 */       if (firstDate != null) {
/* 113 */         logger.info("{} startTime {} use hddb.", methodName, firstDate);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void setUseOldb() {
/* 119 */     HistoryDataSourceHolder.setDataSource(HistoryDataSourceHolder.DataSource.OLDB);
/*     */   }
/*     */   
/*     */   public Set<String> getUseHddbClassSet() {
/* 123 */     if (useHddbClassSet == null) {
/* 124 */       synchronized (this) {
/* 125 */         if (useHddbClassSet == null) {
/* 126 */           initUseHddbClassSet();
/*     */         }
/*     */       }
/*     */     }
/* 130 */     return useHddbClassSet;
/*     */   }
/*     */   
/*     */   private void initUseHddbClassSet()
/*     */   {
/* 135 */     useHddbClassSet = new HashSet();
/* 136 */     Map<String, List<String>> tableRepoMap = new HashMap();
/*     */     
/*     */ 
/*     */ 
/*     */     List<String> tableList;
/*     */     
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 146 */       tableList = this.em.createNativeQuery(" Select table_name from (Select table_name , count(*) as table_count  from  INFORMATION_SCHEMA.partitions where  table_schema = 'ngtms' group by table_name)  f where f.table_count > 1;").getResultList();
/*     */     } catch (PersistenceException ex) { List<String> tableList;
/* 148 */       tableList = new ArrayList();
/*     */     }
/* 150 */     StringBuilder sb = new StringBuilder();
/* 151 */     for (Iterator localIterator = tableList.iterator(); localIterator.hasNext();) { s = (String)localIterator.next();
/* 152 */       sb.append(s + " ");
/*     */     }
/* 154 */     Object abstractEntitisMap = new HashMap();
/* 155 */     logger.info("RPT use history database with {" + sb.toString() + "}");
/* 156 */     for (EntityType<?> e : this.em.getEntityManagerFactory().getMetamodel().getEntities()) {
/* 157 */       if ((e.getJavaType() != null) && 
/* 158 */         (e.getJavaType().getAnnotation(Table.class) != null) && 
/* 159 */         (!((Table)e.getJavaType().getAnnotation(Table.class)).name().isEmpty()) && 
/* 160 */         (Modifier.isAbstract(e.getJavaType().getModifiers()))) {
/* 161 */         tableName = ((Table)e.getJavaType().getAnnotation(Table.class)).name().toLowerCase();
/* 162 */         ((Map)abstractEntitisMap).put(e.getJavaType(), tableName);
/*     */       }
/*     */     }
/*     */     String tableName;
/* 166 */     for (String s = this.em.getEntityManagerFactory().getMetamodel().getEntities().iterator(); s.hasNext();) { e = (EntityType)s.next();
/* 167 */       if (e.getJavaType() != null) {
/* 168 */         for (Class<?> clazz : ((Map)abstractEntitisMap).keySet())
/* 169 */           if (clazz.isAssignableFrom(e.getJavaType())) {
/* 170 */             String tableName = (String)((Map)abstractEntitisMap).get(clazz);
/* 171 */             if (!tableRepoMap.containsKey(tableName)) {
/* 172 */               tableRepoMap.put(tableName, new LinkedList());
/*     */             }
/* 174 */             ((List)tableRepoMap.get(tableName)).add(e.getJavaType().getSimpleName());
/*     */           }
/*     */       }
/*     */     }
/*     */     EntityType<?> e;
/* 179 */     for (EntityType<?> e : this.em.getEntityManagerFactory().getMetamodel().getEntities())
/* 180 */       if ((e.getJavaType() != null) && 
/* 181 */         (e.getJavaType().getAnnotation(Table.class) != null) && 
/* 182 */         (!((Table)e.getJavaType().getAnnotation(Table.class)).name().isEmpty())) {
/* 183 */         tableName = ((Table)e.getJavaType().getAnnotation(Table.class)).name().toLowerCase();
/* 184 */         if (!Modifier.isAbstract(e.getJavaType().getModifiers()))
/*     */         {
/*     */ 
/* 187 */           if (!tableRepoMap.containsKey(tableName)) {
/* 188 */             tableRepoMap.put(tableName, new LinkedList());
/*     */           }
/* 190 */           ((List)tableRepoMap.get(tableName)).add(e.getJavaType().getSimpleName());
/*     */         } }
/*     */     String tableName;
/* 193 */     for (String table : tableList)
/* 194 */       if (tableRepoMap.containsKey(table)) {
/* 195 */         for (String repo : (List)tableRepoMap.get(table)) {
/* 196 */           useHddbClassSet.add(repo.toLowerCase());
/*     */         }
/*     */       }
/*     */       else {
/* 200 */         String className = covertDbNameToClassName(table);
/* 201 */         useHddbClassSet.add(className.toLowerCase());
/*     */       }
/*     */   }
/*     */   
/*     */   private String covertDbNameToClassName(String dbName) {
/* 206 */     StringBuilder sb = new StringBuilder();
/* 207 */     boolean toUpper = true;
/* 208 */     for (int i = 0; i < dbName.length(); i++) {
/* 209 */       char ch = dbName.charAt(i);
/* 210 */       if (ch == '_') {
/* 211 */         toUpper = true;
/* 212 */       } else if (Character.isDigit(ch)) {
/* 213 */         sb.append(ch);
/* 214 */         toUpper = true;
/* 215 */       } else if (toUpper) {
/* 216 */         sb.append(Character.toUpperCase(ch));
/* 217 */         toUpper = false;
/*     */       } else {
/* 219 */         sb.append(ch);
/*     */       }
/*     */     }
/* 222 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\RepositoryAspect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */