/*    */ package com.hwacom.ngtms.base.hazelcast.serializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HzSerializerMeta
/*    */ {
/*    */   private int id;
/*    */   private Class<? extends HzSerializer> hzSerializerClass;
/*    */   private Class<?> typeClass;
/*    */   
/*    */   public HzSerializerMeta(int id, Class<?> typeClass, Class<? extends HzSerializer> hzSerializerClass) {
/* 17 */     this.id = id;
/* 18 */     this.hzSerializerClass = hzSerializerClass;
/* 19 */     this.typeClass = typeClass;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 23 */     return this.id;
/*    */   }
/*    */   
/*    */   public Class<? extends HzSerializer> getHzSerializerClass() {
/* 27 */     return this.hzSerializerClass;
/*    */   }
/*    */   
/*    */   public Class<?> getTypeClass() {
/* 31 */     return this.typeClass;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hazelcast\serializer\HzSerializerMeta.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */