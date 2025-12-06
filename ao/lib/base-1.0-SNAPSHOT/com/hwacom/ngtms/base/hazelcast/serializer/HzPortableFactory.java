/*    */ package com.hwacom.ngtms.base.hazelcast.serializer;
/*    */ 
/*    */ import com.hazelcast.nio.serialization.Portable;
/*    */ import com.hazelcast.nio.serialization.PortableFactory;
/*    */ import com.ibm.icu.impl.IllegalIcuArgumentException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HzPortableFactory
/*    */   implements PortableFactory
/*    */ {
/* 19 */   private static final Logger logger = LoggerFactory.getLogger(HzPortableFactory.class);
/*    */   
/* 21 */   private static final Map<Integer, Class<? extends Portable>> classMap = new HashMap();
/*    */   
/*    */   public Portable create(int classId)
/*    */   {
/* 25 */     Class<? extends Portable> clazz = (Class)classMap.get(Integer.valueOf(classId));
/* 26 */     if (clazz == null) {
/* 27 */       return null;
/*    */     }
/*    */     try {
/* 30 */       return (Portable)clazz.newInstance();
/*    */     } catch (InstantiationException|IllegalAccessException ex) {
/* 32 */       logger.warn("Failed to load class ", ex); }
/* 33 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */   public static void registerClass(int classId, Class<? extends Portable> clazz)
/*    */   {
/* 39 */     if (!classMap.containsKey(Integer.valueOf(classId))) {
/* 40 */       classMap.put(Integer.valueOf(classId), clazz);
/*    */ 
/*    */ 
/*    */     }
/*    */     else
/*    */     {
/*    */ 
/*    */ 
/* 48 */       throw new IllegalIcuArgumentException("Duplicated classId, id:" + classId + ", " + clazz.getName() + ", " + ((Class)classMap.get(Integer.valueOf(classId))).getName());
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hazelcast\serializer\HzPortableFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */