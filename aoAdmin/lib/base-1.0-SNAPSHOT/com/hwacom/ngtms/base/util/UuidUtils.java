/*    */ package com.hwacom.ngtms.base.util;
/*    */ 
/*    */ import org.hibernate.id.uuid.StandardRandomStrategy;
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
/*    */ 
/*    */ public class UuidUtils
/*    */ {
/*    */   public static String generateUUIDStr() {
/* 19 */     return StandardRandomStrategy.INSTANCE.generateUUID(null).toString();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\bas\\util\UuidUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */