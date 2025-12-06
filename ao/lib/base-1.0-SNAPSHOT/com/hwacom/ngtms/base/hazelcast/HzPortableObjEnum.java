/*    */ package com.hwacom.ngtms.base.hazelcast;
/*    */ 
/*    */ import com.hazelcast.nio.serialization.Portable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract interface HzPortableObjEnum
/*    */ {
/*    */   public int getId()
/*    */   {
/* 14 */     String className = getClassType().getName();
/* 15 */     return className.hashCode();
/*    */   }
/*    */   
/*    */   public abstract Class<? extends Portable> getClassType();
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hazelcast\HzPortableObjEnum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */