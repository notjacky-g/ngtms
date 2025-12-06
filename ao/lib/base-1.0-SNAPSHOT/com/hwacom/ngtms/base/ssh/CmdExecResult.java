/*    */ package com.hwacom.ngtms.base.ssh;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CmdExecResult
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String[] stdOut;
/*    */   private String[] stdErr;
/*    */   private String exception;
/*    */   private Integer exitStatus;
/*    */   private long execTime;
/*    */   
/*    */   public String[] getStdOut()
/*    */   {
/* 23 */     return this.stdOut;
/*    */   }
/*    */   
/*    */   public void setStdOut(String[] stdOut) {
/* 27 */     this.stdOut = stdOut;
/*    */   }
/*    */   
/*    */   public String[] getStdErr() {
/* 31 */     return this.stdErr;
/*    */   }
/*    */   
/*    */   public void setStdErr(String[] stdErr) {
/* 35 */     this.stdErr = stdErr;
/*    */   }
/*    */   
/*    */   public Integer getExitStatus() {
/* 39 */     return this.exitStatus;
/*    */   }
/*    */   
/*    */   public void setExitStatus(Integer exitStatus) {
/* 43 */     this.exitStatus = exitStatus;
/*    */   }
/*    */   
/*    */   public String getException() {
/* 47 */     return this.exception;
/*    */   }
/*    */   
/*    */   public void setException(String exception) {
/* 51 */     this.exception = exception;
/*    */   }
/*    */   
/*    */   public long getExecTime() {
/* 55 */     return this.execTime;
/*    */   }
/*    */   
/*    */   public void setExecTime(long execTime) {
/* 59 */     this.execTime = execTime;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String toString()
/*    */   {
/* 67 */     return "ExecCmdResult [stdOut=" + Arrays.toString(this.stdOut) + ", stdErr=" + Arrays.toString(this.stdErr) + ", exception=" + this.exception + ", exitStatus=" + this.exitStatus + ", execTime=" + this.execTime + "]";
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\ssh\CmdExecResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */