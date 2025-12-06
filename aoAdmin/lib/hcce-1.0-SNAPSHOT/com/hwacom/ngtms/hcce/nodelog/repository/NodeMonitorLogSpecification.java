/*    */ package com.hwacom.ngtms.hcce.nodelog.repository;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.nodelog.model.NodeMonitorLog;
/*    */ import java.util.Date;
/*    */ import javax.persistence.criteria.CriteriaBuilder;
/*    */ import javax.persistence.criteria.CriteriaQuery;
/*    */ import javax.persistence.criteria.Expression;
/*    */ import javax.persistence.criteria.Predicate;
/*    */ import javax.persistence.criteria.Root;
/*    */ import org.springframework.data.jpa.domain.Specification;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NodeMonitorLogSpecification
/*    */ {
/*    */   public static Specification<NodeMonitorLog> toPredicate(final Date startTime, final Date endTime) {
/* 20 */     return new Specification<NodeMonitorLog>()
/*    */       {
/*    */         public Predicate toPredicate(Root<NodeMonitorLog> root, CriteriaQuery<?> query, CriteriaBuilder cb)
/*    */         {
/* 24 */           Predicate predicate = cb.conjunction();
/*    */           
/* 26 */           if (startTime != null) {
/* 27 */             predicate
/* 28 */               .getExpressions()
/* 29 */               .add(cb.greaterThanOrEqualTo((Expression)root.get("updateDate"), startTime));
/*    */           }
/* 31 */           if (endTime != null) {
/* 32 */             predicate.getExpressions().add(cb.lessThanOrEqualTo((Expression)root.get("updateDate"), endTime));
/*    */           }
/* 34 */           return predicate;
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\nodelog\repository\NodeMonitorLogSpecification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */