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
/*    */ public class ColumnCommentSetter
/*    */ {
/*    */   public static void setColumnComment(Metadata metadata)
/*    */   {
/* 15 */     for (PersistentClass persClass : metadata.getEntityBindings()) {
/* 16 */       Table table = persClass.getTable();
/* 17 */       if (table.isPhysicalTable()) {
/* 18 */         Class clazz = persClass.getMappedClass();
/* 19 */         if (clazz != null) {
/* 20 */           Iterator columnItr = table.getColumnIterator();
/* 21 */           while (columnItr.hasNext()) {
/* 22 */             Column column = (Column)columnItr.next();
/* 23 */             String columnName = column.getName();
/* 24 */             columnName = getFieldName(columnName);
/*    */             Field field;
/*    */             try {
/* 27 */               field = clazz.getDeclaredField(columnName);
/*    */             } catch (NoSuchFieldException|SecurityException e) {}
/* 29 */             continue;
/*    */             Field field;
/* 31 */             Comment comment = (Comment)field.getDeclaredAnnotation(Comment.class);
/* 32 */             if (comment != null) column.setComment(comment.value());
/*    */           }
/*    */         }
/*    */       }
/*    */     } }
/*    */   
/* 38 */   private static String getFieldName(String name) { StringBuilder sb = new StringBuilder();
/* 39 */     boolean isUpper = false;
/* 40 */     for (int i = 0; i < name.length(); i++)
/* 41 */       if (name.charAt(i) == '_') {
/* 42 */         isUpper = true;
/*    */ 
/*    */       }
/* 45 */       else if (isUpper) {
/* 46 */         sb.append(Character.toUpperCase(name.charAt(i)));
/* 47 */         isUpper = false;
/* 48 */       } else { sb.append(name.charAt(i));
/*    */       }
/* 50 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hibernate\ColumnCommentSetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */