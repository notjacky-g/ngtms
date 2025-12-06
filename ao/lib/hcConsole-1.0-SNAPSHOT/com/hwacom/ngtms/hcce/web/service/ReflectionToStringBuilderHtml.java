/*    */ package com.hwacom.ngtms.hcce.web.service;
/*    */ 
/*    */ import org.apache.commons.lang.builder.ReflectionToStringBuilder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReflectionToStringBuilderHtml
/*    */ {
/*    */   public static String toString(Object element)
/*    */   {
/* 14 */     String text = ReflectionToStringBuilder.toString(element, HzDumpStyle.getInstance(), true);
/* 15 */     return text.replace("{", "<span class='classContent'>{").replace("}", "}</span>");
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcConsole-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\web\service\ReflectionToStringBuilderHtml.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */