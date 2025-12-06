/*    */ package com.hwacom.ngtms.hcce.hz;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.HzPortableObjEnum;
/*    */ import com.hwacom.ngtms.base.hazelcast.serializer.HzPortable;
/*    */ import com.hwacom.ngtms.base.hazelcast.serializer.HzPortableFactory;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class HzPortableRegister
/*    */ {
/*    */   public static <U extends HzPortableObjEnum> void registerPortable(Class<U> clazz) {
/* 18 */     if (!clazz.isEnum()) {
/* 19 */       throw new IllegalArgumentException("HzPortableEnumClass must be enum type.");
/*    */     }
/* 21 */     HzPortableObjEnum[] enumConstants = (HzPortableObjEnum[])clazz.getEnumConstants();
/* 22 */     for (HzPortableObjEnum obj : enumConstants) {
/* 23 */       HzPortable.registerClass(obj.getId(), obj.getClassType());
/* 24 */       HzPortableFactory.registerClass(obj.getId(), obj.getClassType());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\hz\HzPortableRegister.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */