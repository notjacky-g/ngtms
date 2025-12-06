/*    */ package com.hwacom.ngtms.base.hazelcast.serializer;
/*    */ 
/*    */ import com.hazelcast.nio.serialization.Portable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HzPortableSerializerMeta
/*    */ {
/*    */   private int id;
/*    */   private Class<? extends Portable> typeClass;
/*    */   
/*    */   public HzPortableSerializerMeta(int id, Class<? extends Portable> typeClass)
/*    */   {
/* 18 */     this.id = id;
/* 19 */     this.typeClass = typeClass;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 23 */     return this.id;
/*    */   }
/*    */   
/*    */   public Class<? extends Portable> getTypeClass() {
/* 27 */     return this.typeClass;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hazelcast\serializer\HzPortableSerializerMeta.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */