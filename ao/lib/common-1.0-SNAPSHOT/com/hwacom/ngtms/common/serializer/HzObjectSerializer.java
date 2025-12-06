/*    */ package com.hwacom.ngtms.common.serializer;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.serializer.HzSerializer;
/*    */ import org.nustaq.serialization.FSTConfiguration;
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
/*    */ public abstract class HzObjectSerializer
/*    */   implements HzSerializer
/*    */ {
/* 30 */   protected static ThreadLocal<FSTConfiguration> conf = new ThreadLocal()
/*    */   {
/*    */     public FSTConfiguration initialValue() {
/* 33 */       FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();
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
/*    */ 
/* 47 */       return conf;
/*    */     }
/*    */   };
/*    */   protected int typeId;
/*    */   
/*    */   public void setTypeId(int typeId)
/*    */   {
/* 54 */     this.typeId = typeId;
/*    */   }
/*    */   
/*    */   public void registerClass(Class<?> clazz) {
/* 58 */     ((FSTConfiguration)conf.get()).registerClass(new Class[] { clazz });
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\serializer\HzObjectSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */