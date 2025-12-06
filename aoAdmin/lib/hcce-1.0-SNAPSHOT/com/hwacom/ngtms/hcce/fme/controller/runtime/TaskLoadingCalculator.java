/*    */ package com.hwacom.ngtms.hcce.fme.controller.runtime;
/*    */ 
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TaskLoadingCalculator
/*    */ {
/*    */   private TaskLoadingCounter taskLoadingCounter;
/* 14 */   private List<Long> exeTimeList = new LinkedList<>();
/*    */   private long count;
/*    */   
/*    */   public TaskLoadingCalculator() {
/* 18 */     this.taskLoadingCounter = new TaskLoadingCounter();
/*    */   }
/*    */   
/*    */   public TaskLoadingCalculator(TaskLoadingCounter taskLoadingCounter) {
/* 22 */     this.taskLoadingCounter = taskLoadingCounter;
/*    */   }
/*    */   
/*    */   public synchronized void setLastExeTime(long execTimeStamp, long lastExeTime) {
/* 26 */     this.count++;
/* 27 */     this.taskLoadingCounter.setLastExeTime(execTimeStamp, lastExeTime);
/* 28 */     this.exeTimeList.add(Long.valueOf(lastExeTime));
/* 29 */     if (this.exeTimeList.size() > 100) this.exeTimeList.remove(0); 
/*    */   }
/*    */   
/*    */   public synchronized TaskLoadingCounter getTaskLoadingCounter() {
/* 33 */     long sum = 0L;
/* 34 */     int count = 0; int i;
/* 35 */     for (i = this.exeTimeList.size() - 1; i >= 0; i--) {
/* 36 */       sum += ((Long)this.exeTimeList.get(i)).longValue();
/* 37 */       count++;
/* 38 */       if (count >= 10)
/*    */         break; 
/* 40 */     }  if (count != 0)
/* 41 */     { this.taskLoadingCounter.setLastTenTimesAvg((long)(sum / count + 0.5D)); }
/* 42 */     else { this.taskLoadingCounter.setLastTenTimesAvg(0L); }
/*    */     
/* 44 */     for (i = this.exeTimeList.size() - 11; i >= 0; i--) {
/* 45 */       sum += ((Long)this.exeTimeList.get(i)).longValue();
/* 46 */       count++;
/*    */     } 
/* 48 */     if (count != 0)
/* 49 */     { this.taskLoadingCounter.setLastHundredTimesAvg((long)(sum / count + 0.5D)); }
/* 50 */     else { this.taskLoadingCounter.setLastHundredTimesAvg(0L); }
/* 51 */      this.taskLoadingCounter.setCount(this.count);
/* 52 */     return this.taskLoadingCounter;
/*    */   }
/*    */   
/*    */   public synchronized void reset() {
/* 56 */     this.exeTimeList.clear();
/* 57 */     this.taskLoadingCounter.setLastExeTime(0L, 0L);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\controller\runtime\TaskLoadingCalculator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */