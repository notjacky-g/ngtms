/*    */ package com.hwacom.ngtms.base.oplog.service;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import javax.persistence.PostPersist;
/*    */ import javax.persistence.PostRemove;
/*    */ import javax.persistence.PostUpdate;
/*    */ import javax.persistence.PrePersist;
/*    */ import javax.persistence.PreRemove;
/*    */ import javax.persistence.PreUpdate;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OperationEntityListener
/*    */ {
/* 22 */   private static final Logger logger = LoggerFactory.getLogger(OperationEntityListener.class);
/*    */   
/*    */   @PrePersist
/*    */   public void prePersist(Object o) {
/* 26 */     logger.debug("Pre Persist: '{}'", toClassNameOrNull(o));
/* 27 */     setPreviousValue(o, Operation.Action.CREATE);
/*    */   }
/*    */   
/*    */   private String toClassNameOrNull(Object object) {
/* 31 */     return Optional.<Object>ofNullable(object).map(o -> o.getClass().getName()).orElse(null);
/*    */   }
/*    */   
/*    */   private Optional<Operation> getOperation() {
/* 35 */     return Optional.ofNullable(OperationHolder.instance().getOperation());
/*    */   }
/*    */   
/*    */   private void setPreviousValue(Object o, Operation.Action action) {
/* 39 */     Optional<Operation> optOperation = getOperation();
/* 40 */     logger.debug("Set previous value. action: '{}', operation exists: '{}'", action, 
/*    */ 
/*    */         
/* 43 */         Boolean.valueOf(optOperation.isPresent()));
/* 44 */     optOperation.ifPresent(operation -> {
/*    */           operation.setAction(paramAction);
/*    */           operation.setPreviousValue(paramObject);
/*    */           OperationHolder.instance().setOperation(operation);
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   @PostPersist
/*    */   public void postPersist(Object o) {
/* 54 */     logger.debug("Post persist: '{}'", toClassNameOrNull(o));
/* 55 */     setCurrentValue(o);
/*    */   }
/*    */   
/*    */   private void setCurrentValue(Object o) {
/* 59 */     Optional<Operation> optOperation = getOperation();
/* 60 */     logger.debug("Set current value. operation exists: '{}'", Boolean.valueOf(optOperation.isPresent()));
/* 61 */     optOperation.ifPresent(operation -> {
/*    */           operation.setCurrentValue(paramObject);
/*    */           OperationHolder.instance().setOperation(operation);
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   @PreUpdate
/*    */   public void preUpdate(Object o) {
/* 70 */     logger.debug("Pre update: '{}'", toClassNameOrNull(o));
/* 71 */     setPreviousValue(o, Operation.Action.UPDATE);
/*    */   }
/*    */   
/*    */   @PostUpdate
/*    */   public void PostUpdate(Object o) {
/* 76 */     logger.debug("Post update: '{}'", toClassNameOrNull(o));
/* 77 */     setCurrentValue(o);
/*    */   }
/*    */   
/*    */   @PreRemove
/*    */   public void preRemove(Object o) {
/* 82 */     logger.debug("Pre remove: '{}'", toClassNameOrNull(o));
/* 83 */     setPreviousValue(o, Operation.Action.DELETE);
/*    */   }
/*    */   
/*    */   @PostRemove
/*    */   public void postRemove(Object o) {
/* 88 */     logger.debug("Post remove: '{}'", toClassNameOrNull(o));
/* 89 */     setCurrentValue(o);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\oplog\service\OperationEntityListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */