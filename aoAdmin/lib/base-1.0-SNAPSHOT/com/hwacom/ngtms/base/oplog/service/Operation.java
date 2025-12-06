/*     */ package com.hwacom.ngtms.base.oplog.service;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.base.annotation.NoOperationLog;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Transient;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Operation
/*     */ {
/*  23 */   private static final Logger logger = LoggerFactory.getLogger(Operation.class);
/*     */   
/*     */   private Action action;
/*     */   
/*     */   private Object previousValue;
/*     */   
/*     */   private Object currentValue;
/*     */   
/*     */   public Action getAction() {
/*  32 */     return this.action;
/*     */   }
/*     */   
/*     */   public void setAction(Action action) {
/*  36 */     this.action = action;
/*     */   }
/*     */   
/*     */   public Object getPreviousValue() {
/*  40 */     return this.previousValue;
/*     */   }
/*     */   
/*     */   public void setPreviousValue(Object previousValue) {
/*  44 */     this.previousValue = previousValue;
/*     */   }
/*     */   
/*     */   public Object getCurrentValue() {
/*  48 */     return this.currentValue;
/*     */   }
/*     */   
/*     */   public void setCurrentValue(Object currentValue) {
/*  52 */     this.currentValue = currentValue;
/*     */   }
/*     */   
/*     */   public Map<String, Diff> diff() {
/*  56 */     if (isNoOperationLog()) {
/*  57 */       return new HashMap<>();
/*     */     }
/*  59 */     return diffFields();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isNoOperationLog() {
/*  64 */     return (isNoOperationLog(this.previousValue) || isNoOperationLog(this.currentValue));
/*     */   }
/*     */   
/*     */   private boolean isNoOperationLog(Object o) {
/*  68 */     if (o != null) {
/*  69 */       return (o.getClass().getDeclaredAnnotation(NoOperationLog.class) != null);
/*     */     }
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private Map<String, Diff> diffFields() {
/*  76 */     Map<String, Diff> result = new HashMap<>();
/*  77 */     Object o = (this.previousValue != null) ? this.previousValue : this.currentValue;
/*  78 */     if (o == null) {
/*  79 */       return result;
/*     */     }
/*  81 */     for (Field field : o.getClass().getDeclaredFields()) {
/*     */       try {
/*  83 */         if (!isIgnoredField(field)) {
/*     */ 
/*     */           
/*  86 */           String fieldName = field.getName();
/*  87 */           Object previousFieldValue = getFieldValue(this.previousValue, field);
/*  88 */           Object currentFieldValue = getFieldValue(this.currentValue, field);
/*  89 */           if (isFieldChanged(previousFieldValue, currentFieldValue))
/*  90 */           { Diff diff = new Diff();
/*  91 */             diff.setFieldName(fieldName);
/*  92 */             diff.setDefaultFieldDescription(getDefaultFieldDescription(field));
/*  93 */             diff.setFieldMessageCode(o.getClass().getSimpleName() + "." + fieldName);
/*  94 */             diff.setPreviousFieldValue(previousFieldValue);
/*  95 */             diff.setCurrentFieldValue(currentFieldValue);
/*  96 */             result.put(fieldName, diff); } 
/*     */         } 
/*  98 */       } catch (Exception e) {
/*  99 */         logger.warn("Can't diff field. fieldName: '{}'", field.getName(), e);
/*     */       } 
/*     */     } 
/* 102 */     return result;
/*     */   }
/*     */   
/*     */   private boolean isIgnoredField(Field field) {
/* 106 */     return (Objects.equals("serialVersionUID", field.getName()) || field
/* 107 */       .isAnnotationPresent((Class)Id.class) || field
/* 108 */       .isAnnotationPresent((Class)Transient.class) || field
/* 109 */       .isAnnotationPresent((Class)NoOperationLog.class));
/*     */   }
/*     */   
/*     */   private Object getFieldValue(Object o, Field field) {
/* 113 */     if (o != null) {
/* 114 */       field.setAccessible(true);
/*     */       try {
/* 116 */         return field.get(o);
/* 117 */       } catch (Exception e) {
/* 118 */         logger.warn("Can't get value from '{}', fieldName: '{}'", new Object[] { o
/*     */               
/* 120 */               .getClass().getName(), field
/* 121 */               .getName(), e });
/*     */       } 
/*     */     } 
/*     */     
/* 125 */     return null;
/*     */   }
/*     */   
/*     */   private boolean isFieldChanged(Object previousFieldValue, Object currentFieldValue) {
/* 129 */     return (!Objects.equals(previousFieldValue, currentFieldValue) || this.action == Action.CREATE || this.action == Action.DELETE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getDefaultFieldDescription(Field field) {
/* 135 */     return Optional.ofNullable(field.getDeclaredAnnotation(Comment.class))
/* 136 */       .map(Comment::value)
/* 137 */       .filter(v -> (v != null && !v.isEmpty()))
/* 138 */       .orElse(field.getName());
/*     */   }
/*     */   
/*     */   public enum Action {
/* 142 */     CREATE,
/* 143 */     UPDATE,
/* 144 */     DELETE;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Diff
/*     */   {
/*     */     private String fieldName;
/*     */     
/*     */     private String defaultFieldDescription;
/*     */     
/*     */     private String fieldMessageCode;
/*     */     
/*     */     private Object previousFieldValue;
/*     */     private Object currentFieldValue;
/*     */     
/*     */     public String getFieldName() {
/* 160 */       return this.fieldName;
/*     */     }
/*     */     
/*     */     public void setFieldName(String fieldName) {
/* 164 */       this.fieldName = fieldName;
/*     */     }
/*     */     
/*     */     public String getDefaultFieldDescription() {
/* 168 */       return this.defaultFieldDescription;
/*     */     }
/*     */     
/*     */     public void setDefaultFieldDescription(String defaultFieldDescription) {
/* 172 */       this.defaultFieldDescription = defaultFieldDescription;
/*     */     }
/*     */     
/*     */     public String getFieldMessageCode() {
/* 176 */       return this.fieldMessageCode;
/*     */     }
/*     */     
/*     */     public void setFieldMessageCode(String fieldMessageCode) {
/* 180 */       this.fieldMessageCode = fieldMessageCode;
/*     */     }
/*     */     
/*     */     public Object getPreviousFieldValue() {
/* 184 */       return this.previousFieldValue;
/*     */     }
/*     */     
/*     */     public void setPreviousFieldValue(Object previousFieldValue) {
/* 188 */       this.previousFieldValue = previousFieldValue;
/*     */     }
/*     */     
/*     */     public Object getCurrentFieldValue() {
/* 192 */       return this.currentFieldValue;
/*     */     }
/*     */     
/*     */     public void setCurrentFieldValue(Object currentFieldValue) {
/* 196 */       this.currentFieldValue = currentFieldValue;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\oplog\service\Operation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */