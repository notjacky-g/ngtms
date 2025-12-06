/*    */ package com.hwacom.ngtms.base.util;
/*    */ 
/*    */ import java.net.InetAddress;
/*    */ import java.net.UnknownHostException;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResolveString
/*    */ {
/* 15 */   private static AtomicInteger serialNumber = new AtomicInteger();
/*    */   
/*    */   public static String resolve(String text) {
/* 18 */     if (text.indexOf("%HostName%") >= 0) {
/* 19 */       String hostName = null;
/*    */       try {
/* 21 */         hostName = InetAddress.getLocalHost().getHostName();
/*    */       } catch (UnknownHostException e) {
/* 23 */         hostName = "UnknownHost";
/*    */       }
/* 25 */       text = text.replaceAll("%HostName%", hostName);
/*    */     }
/*    */     
/* 28 */     if (text.indexOf("%Random%") >= 0)
/*    */     {
/*    */ 
/*    */ 
/*    */ 
/* 33 */       String randomStr = "R-" + serialNumber.incrementAndGet() + "-" + Long.toHexString(System.currentTimeMillis() % 100000000L);
/* 34 */       text = text.replaceAll("%Random%", randomStr);
/*    */     }
/* 36 */     if (text.indexOf("%UUID%") >= 0) {
/* 37 */       UUID.randomUUID().toString();
/* 38 */       String uuidStr = UUID.randomUUID().toString();
/* 39 */       text = text.replaceAll("%UUID%", uuidStr);
/*    */     }
/* 41 */     return text;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\util\ResolveString.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */