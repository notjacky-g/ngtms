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
/*    */ 
/*    */ public class CmdExecBinaryResult
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private byte[] stdOut;
/*    */   private String[] stdErr;
/*    */   private String exception;
/*    */   private Integer exitStatus;
/*    */   private long execTime;
/*    */   
/*    */   public byte[] getStdOut() {
/* 23 */     return this.stdOut;
/*    */   }
/*    */   
/*    */   public void setStdOut(byte[] stdOut) {
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
/*    */   public String toString() {
/* 64 */     return "ExecCmdResult [stdOut=" + 
/* 65 */       Arrays.toString(this.stdOut) + ", stdErr=" + 
/*    */       
/* 67 */       Arrays.toString((Object[])this.stdErr) + ", exception=" + this.exception + ", exitStatus=" + this.exitStatus + ", execTime=" + this.execTime + "]";
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\ssh\CmdExecBinaryResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */