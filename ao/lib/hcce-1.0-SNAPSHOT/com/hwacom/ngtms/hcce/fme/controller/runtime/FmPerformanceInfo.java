/*    */ package com.hwacom.ngtms.hcce.fme.controller.runtime;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FmPerformanceInfo
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Date time;
/*    */   private Map<String, Serializable> counterMap;
/*    */   
/*    */   public FmPerformanceInfo add(String type, Serializable value)
/*    */   {
/* 24 */     this.counterMap.put(type, value);
/* 25 */     return this;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 30 */     return "FmPerfCounters [time=" + this.time + ", counterMap=" + this.counterMap + "]";
/*    */   }
/*    */   
/*    */   public Date getTime() {
/* 34 */     return this.time;
/*    */   }
/*    */   
/*    */   public void setTime(Date time) {
/* 38 */     this.time = time;
/*    */   }
/*    */   
/*    */   public Map<String, Serializable> getCounterMap() {
/* 42 */     return this.counterMap;
/*    */   }
/*    */   
/*    */   public void setCounterMap(Map<String, Serializable> counterMap) {
/* 46 */     this.counterMap = counterMap;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\controller\runtime\FmPerformanceInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */