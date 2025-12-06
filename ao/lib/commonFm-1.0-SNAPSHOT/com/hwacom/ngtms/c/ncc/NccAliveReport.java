/*    */ package com.hwacom.ngtms.c.ncc;
/*    */ 
/*    */ import com.google.common.base.MoreObjects;
/*    */ import com.google.common.base.MoreObjects.ToStringHelper;
/*    */ import com.google.common.base.Objects;
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
/*    */ 
/*    */ public class NccAliveReport
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1744994141730812424L;
/*    */   private String mfccId;
/*    */   private Long lastReportTime;
/*    */   
/*    */   public NccAliveReport() {}
/*    */   
/*    */   public NccAliveReport(String mfccId, Long lastReportTime)
/*    */   {
/* 28 */     this.mfccId = mfccId;
/* 29 */     this.lastReportTime = lastReportTime;
/*    */   }
/*    */   
/*    */   public String getMfccId() {
/* 33 */     return this.mfccId;
/*    */   }
/*    */   
/*    */   public void setMfccId(String mfccId) {
/* 37 */     this.mfccId = mfccId;
/*    */   }
/*    */   
/*    */   public Long getLastReportTime() {
/* 41 */     return this.lastReportTime;
/*    */   }
/*    */   
/*    */   public void setLastReportTime(Long lastReportTime) {
/* 45 */     this.lastReportTime = lastReportTime;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String toString()
/*    */   {
/* 53 */     return MoreObjects.toStringHelper(this).add("mfccId", this.mfccId).add("lastReportTime", this.lastReportTime).toString();
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 58 */     return Objects.hashCode(new Object[] { this.mfccId });
/*    */   }
/*    */   
/*    */   public boolean equals(Object object)
/*    */   {
/* 63 */     if ((object instanceof NccAliveReport)) {
/* 64 */       NccAliveReport that = (NccAliveReport)object;
/* 65 */       return Objects.equal(this.mfccId, that.mfccId);
/*    */     }
/* 67 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\ncc\NccAliveReport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */