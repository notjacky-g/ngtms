/*    */ package com.hwacom.ngtms.base.hazelcast;
/*    */ 
/*    */ import com.hazelcast.nio.serialization.Portable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface HzPortableObjEnum
/*    */ {
/*    */   default int getId() {
/* 14 */     String className = getClassType().getName();
/* 15 */     return className.hashCode();
/*    */   }
/*    */   
/*    */   Class<? extends Portable> getClassType();
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hazelcast\HzPortableObjEnum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */