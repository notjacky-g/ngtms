/*    */ package com.hwacom.ngtms.base.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import org.apache.commons.beanutils.BeanUtils;
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
/*    */ public class TransferUtils
/*    */ {
/*    */   public static <Dis, Src> ArrayList<Dis> transferObjects(Iterable<Src> iterable, Class<Dis> clazz) {
/* 26 */     ArrayList<Dis> dtos = new ArrayList<>();
/* 27 */     iterable.forEach(v -> paramArrayList.add(transferObject(v, paramClass)));
/* 28 */     return dtos;
/*    */   }
/*    */   
/*    */   public static <Dis, Src> Dis transferObject(Src v, Class<Dis> clazz) {
/*    */     try {
/* 33 */       Dis dto = clazz.newInstance();
/* 34 */       BeanUtils.copyProperties(dto, v);
/* 35 */       return dto;
/* 36 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException|InstantiationException e) {
/* 37 */       throw new RuntimeException("failed to transfer to DTO");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\bas\\util\TransferUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */