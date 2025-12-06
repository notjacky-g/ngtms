/*    */ package com.hwacom.ngtms.hcce.nodelog.repository;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.nodelog.model.NodeMonitorLog;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import javax.persistence.criteria.CriteriaBuilder;
/*    */ import javax.persistence.criteria.CriteriaQuery;
/*    */ import javax.persistence.criteria.Predicate;
/*    */ import javax.persistence.criteria.Root;
/*    */ import org.springframework.data.jpa.domain.Specification;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NodeMonitorLogSpecification
/*    */ {
/*    */   public static Specification<NodeMonitorLog> toPredicate(Date startTime, final Date endTime)
/*    */   {
/* 20 */     new Specification()
/*    */     {
/*    */       public Predicate toPredicate(Root<NodeMonitorLog> root, CriteriaQuery<?> query, CriteriaBuilder cb)
/*    */       {
/* 24 */         Predicate predicate = cb.conjunction();
/*    */         
/* 26 */         if (this.val$startTime != null)
/*    */         {
/*    */ 
/* 29 */           predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("updateDate"), this.val$startTime));
/*    */         }
/* 31 */         if (endTime != null) {
/* 32 */           predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("updateDate"), endTime));
/*    */         }
/* 34 */         return predicate;
/*    */       }
/*    */     };
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\nodelog\repository\NodeMonitorLogSpecification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */