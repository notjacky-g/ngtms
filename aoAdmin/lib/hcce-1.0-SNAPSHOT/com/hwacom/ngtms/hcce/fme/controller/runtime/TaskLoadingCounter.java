/*    */ package com.hwacom.ngtms.hcce.fme.controller.runtime;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TaskLoadingCounter
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 2L;
/*    */   private long lastExeTime;
/*    */   private long lastTenTimesAvg;
/*    */   private long lastHundredTimesAvg;
/*    */   private long count;
/* 21 */   private long startTime = System.currentTimeMillis();
/*    */   private long lastExecTimestamp;
/*    */   
/*    */   public long getLastExeTime() {
/* 25 */     return this.lastExeTime;
/*    */   }
/*    */   
/*    */   public void setLastExeTime(long execTimestamp, long lastExeTime) {
/* 29 */     this.lastExecTimestamp = execTimestamp;
/* 30 */     this.lastExeTime = lastExeTime;
/*    */   }
/*    */   
/*    */   public long getLastTenTimesAvg() {
/* 34 */     return this.lastTenTimesAvg;
/*    */   }
/*    */   
/*    */   public void setLastTenTimesAvg(long lastTenTimesAvg) {
/* 38 */     this.lastTenTimesAvg = lastTenTimesAvg;
/*    */   }
/*    */   
/*    */   public long getLastHundredTimesAvg() {
/* 42 */     return this.lastHundredTimesAvg;
/*    */   }
/*    */   
/*    */   public void setLastHundredTimesAvg(long lastHundredTimesAvg) {
/* 46 */     this.lastHundredTimesAvg = lastHundredTimesAvg;
/*    */   }
/*    */   
/*    */   public long getCount() {
/* 50 */     return this.count;
/*    */   }
/*    */   
/*    */   public void setCount(long count) {
/* 54 */     this.count = count;
/*    */   }
/*    */   
/*    */   public long getStartTime() {
/* 58 */     return this.startTime;
/*    */   }
/*    */   
/*    */   public long getLastExecTimestamp() {
/* 62 */     return this.lastExecTimestamp;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 67 */     return "TaskLoadingCounter [lastExeTime=" + this.lastExeTime + ", lastTenTimesAvg=" + this.lastTenTimesAvg + ", lastHundredTimesAvg=" + this.lastHundredTimesAvg + ", count=" + this.count + ", startTime=" + this.startTime + ", lastExecTimestamp=" + this.lastExecTimestamp + "]";
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\controller\runtime\TaskLoadingCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */