/*    */ package com.hwacom.ngtms.base.oplog.service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OperationHolder
/*    */ {
/*    */   private static OperationHolder instance;
/*    */   
/*    */ 
/*    */ 
/* 13 */   private ThreadLocal<Operation> threadLocal = new ThreadLocal();
/*    */   
/*    */ 
/*    */   public static OperationHolder instance()
/*    */   {
/* 18 */     if (instance == null) {
/* 19 */       instance = new OperationHolder();
/*    */     }
/* 21 */     return instance;
/*    */   }
/*    */   
/*    */   public Operation getOperation() {
/* 25 */     return (Operation)this.threadLocal.get();
/*    */   }
/*    */   
/*    */   public void setOperation(Operation operation) {
/* 29 */     this.threadLocal.set(operation);
/*    */   }
/*    */   
/*    */   public void remove() {
/* 33 */     this.threadLocal.remove();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\oplog\service\OperationHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */