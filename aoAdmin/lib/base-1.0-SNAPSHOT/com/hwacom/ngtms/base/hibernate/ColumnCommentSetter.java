/*    */ package com.hwacom.ngtms.base.hibernate;
/*    */ 
/*    */ import com.hwacom.ngtms.base.annotation.Comment;
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.Iterator;
/*    */ import org.hibernate.boot.Metadata;
/*    */ import org.hibernate.mapping.Column;
/*    */ import org.hibernate.mapping.PersistentClass;
/*    */ import org.hibernate.mapping.Table;
/*    */ 
/*    */ 
/*    */ public class ColumnCommentSetter
/*    */ {
/*    */   public static void setColumnComment(Metadata metadata) {
/* 15 */     for (PersistentClass persClass : metadata.getEntityBindings()) {
/* 16 */       Table table = persClass.getTable();
/* 17 */       if (!table.isPhysicalTable())
/* 18 */         continue;  Class clazz = persClass.getMappedClass();
/* 19 */       if (clazz == null)
/* 20 */         continue;  Iterator<Column> columnItr = table.getColumnIterator();
/* 21 */       while (columnItr.hasNext()) {
/* 22 */         Field field; Column column = columnItr.next();
/* 23 */         String columnName = column.getName();
/* 24 */         columnName = getFieldName(columnName);
/*    */         
/*    */         try {
/* 27 */           field = clazz.getDeclaredField(columnName);
/* 28 */         } catch (NoSuchFieldException|SecurityException e) {
/*    */           continue;
/*    */         } 
/* 31 */         Comment comment = field.<Comment>getDeclaredAnnotation(Comment.class);
/* 32 */         if (comment != null) column.setComment(comment.value()); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private static String getFieldName(String name) {
/* 38 */     StringBuilder sb = new StringBuilder();
/* 39 */     boolean isUpper = false;
/* 40 */     for (int i = 0; i < name.length(); i++) {
/* 41 */       if (name.charAt(i) == '_')
/* 42 */       { isUpper = true;
/*    */          }
/*    */       
/* 45 */       else if (isUpper)
/* 46 */       { sb.append(Character.toUpperCase(name.charAt(i)));
/* 47 */         isUpper = false; }
/* 48 */       else { sb.append(name.charAt(i)); }
/*    */     
/* 50 */     }  return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hibernate\ColumnCommentSetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */