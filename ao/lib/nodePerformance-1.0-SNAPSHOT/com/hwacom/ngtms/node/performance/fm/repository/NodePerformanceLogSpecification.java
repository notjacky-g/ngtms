/*    */ package com.hwacom.ngtms.node.performance.fm.repository;
/*    */ 
/*    */ import com.hwacom.ngtms.node.performance.fm.model.NodePerformanceLog;
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
/*    */ 
/*    */ public class NodePerformanceLogSpecification
/*    */ {
/*    */   public static Specification<NodePerformanceLog> toPredicate(String groupName, final String nodeName, final Date startTime, final Date endTime)
/*    */   {
/* 21 */     new Specification()
/*    */     {
/*    */       public Predicate toPredicate(Root<NodePerformanceLog> root, CriteriaQuery<?> query, CriteriaBuilder cb)
/*    */       {
/* 25 */         Predicate predicate = cb.conjunction();
/*    */         
/* 27 */         if (this.val$groupName != null) {
/* 28 */           predicate.getExpressions().add(cb.equal(root.get("groupName"), this.val$groupName));
/*    */         }
/* 30 */         if (nodeName != null) {
/* 31 */           predicate.getExpressions().add(cb.equal(root.get("nodeName"), nodeName));
/*    */         }
/* 33 */         if (startTime != null)
/*    */         {
/*    */ 
/* 36 */           predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("recordTime"), startTime));
/*    */         }
/* 38 */         if (endTime != null) {
/* 39 */           predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("recordTime"), endTime));
/*    */         }
/* 41 */         return predicate;
/*    */       }
/*    */     };
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nodePerformance-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\node\performance\fm\repository\NodePerformanceLogSpecification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */