/*    */ package com.hwacom.ngtms.base.util;
/*    */ 
/*    */ import java.util.UUID;
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
/*    */ public class UuidUtils
/*    */ {
/*    */   public static String generateUUIDStr()
/*    */   {
/* 19 */     return StandardRandomStrategy.INSTANCE.generateUUID(null).toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\util\UuidUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */