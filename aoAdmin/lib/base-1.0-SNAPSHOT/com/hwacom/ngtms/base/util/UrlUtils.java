/*    */ package com.hwacom.ngtms.base.util;
/*    */ 
/*    */ import java.net.InetAddress;
/*    */ import java.net.URL;
/*    */ import java.net.UnknownHostException;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UrlUtils
/*    */ {
/* 17 */   private static final Logger logger = LoggerFactory.getLogger(UrlUtils.class);
/*    */   
/*    */   public static boolean isInternalIp(String url) {
/*    */     try {
/* 21 */       String host = (new URL(url)).getHost();
/* 22 */       InetAddress inetAddress = InetAddress.getByName(host);
/* 23 */       byte[] bytes = inetAddress.getAddress();
/* 24 */       boolean internalIp = isInternalIp(bytes);
/* 25 */       logger.debug("url: '{}', host: '{}', internal ip: '{}'", new Object[] { url, host, Boolean.valueOf(internalIp) });
/* 26 */       return internalIp;
/* 27 */     } catch (UnknownHostException e) {
/* 28 */       logger.warn("Unknown host. url: '{}'", url, e);
/* 29 */       return false;
/* 30 */     } catch (Exception e) {
/* 31 */       logger.warn("url: '{}'", url, e);
/* 32 */       return true;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean isInternalIp(byte[] address) {
/* 44 */     int first = Byte.toUnsignedInt(address[0]);
/* 45 */     int second = Byte.toUnsignedInt(address[1]);
/* 46 */     boolean internalIp = false;
/* 47 */     if (first == 172) {
/* 48 */       internalIp = (second >= 16 && second <= 31);
/* 49 */     } else if (first == 192) {
/* 50 */       internalIp = (second == 168);
/*    */     } else {
/* 52 */       internalIp = (first == 10);
/*    */     } 
/* 54 */     return internalIp;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\bas\\util\UrlUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */