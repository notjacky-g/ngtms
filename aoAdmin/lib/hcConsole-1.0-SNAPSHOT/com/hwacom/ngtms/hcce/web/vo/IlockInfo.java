/*    */ package com.hwacom.ngtms.hcce.web.vo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IlockInfo
/*    */ {
/*    */   private boolean locked;
/*    */   private int lockCount;
/*    */   private long remainingLeaseTime;
/*    */   
/*    */   public boolean isLocked() {
/* 16 */     return this.locked;
/*    */   }
/*    */   
/*    */   public void setLocked(boolean locked) {
/* 20 */     this.locked = locked;
/*    */   }
/*    */   
/*    */   public int getLockCount() {
/* 24 */     return this.lockCount;
/*    */   }
/*    */   
/*    */   public void setLockCount(int lockCount) {
/* 28 */     this.lockCount = lockCount;
/*    */   }
/*    */   
/*    */   public long getRemainingLeaseTime() {
/* 32 */     return this.remainingLeaseTime;
/*    */   }
/*    */   
/*    */   public void setRemainingLeaseTime(long remainingLeaseTime) {
/* 36 */     this.remainingLeaseTime = remainingLeaseTime;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcConsole-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\web\vo\IlockInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */