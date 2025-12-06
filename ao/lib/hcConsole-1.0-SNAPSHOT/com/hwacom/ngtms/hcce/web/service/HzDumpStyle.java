/*    */ package com.hwacom.ngtms.hcce.web.service;
/*    */ 
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Calendar;
/*    */ import java.util.Collection;
/*    */ import java.util.Date;
/*    */ import org.apache.commons.lang.builder.ReflectionToStringBuilder;
/*    */ import org.apache.commons.lang.builder.ToStringStyle;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HzDumpStyle
/*    */   extends ToStringStyle
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 19 */   private static final ToStringStyle instance = new HzDumpStyle();
/* 20 */   private SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
/*    */   
/*    */   public HzDumpStyle() {
/* 23 */     setArrayContentDetail(true);
/* 24 */     setUseShortClassName(true);
/* 25 */     setUseClassName(false);
/* 26 */     setUseIdentityHashCode(false);
/*    */     
/* 28 */     setFieldSeparator(", ");
/*    */   }
/*    */   
/*    */   public static ToStringStyle getInstance() {
/* 32 */     return instance;
/*    */   }
/*    */   
/*    */   public void appendDetail(StringBuffer buffer, String fieldName, Object value)
/*    */   {
/* 37 */     if ((value instanceof Date)) {
/* 38 */       value = this.sdFormat.format(value);
/* 39 */       buffer.append(value);
/* 40 */     } else if ((value instanceof Calendar)) {
/* 41 */       value = this.sdFormat.format(((Calendar)value).getTime());
/* 42 */       buffer.append(value);
/* 43 */     } else if (!value.getClass().getName().startsWith("java")) {
/* 44 */       buffer.append(ReflectionToStringBuilder.toString(value, instance, true));
/*    */     } else {
/* 46 */       super.appendDetail(buffer, fieldName, value);
/*    */     }
/*    */   }
/*    */   
/*    */   public void appendDetail(StringBuffer buffer, String fieldName, Collection value)
/*    */   {
/* 52 */     appendDetail(buffer, fieldName, value.toArray());
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcConsole-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\web\service\HzDumpStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */