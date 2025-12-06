/*    */ package com.hwacom.ngtms.cmdprot.cmdfmtdef;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnalysisResult
/*    */ {
/*    */   private boolean processing;
/* 11 */   private int length = -1;
/* 12 */   public static final AnalysisResult Process = new AnalysisResult(true);
/* 13 */   public static final AnalysisResult Skip = new AnalysisResult(false);
/*    */   
/*    */   public AnalysisResult() {}
/*    */   
/*    */   public AnalysisResult(boolean processing) {
/* 18 */     this.processing = processing;
/*    */   }
/*    */   
/*    */   public boolean isProcessing() {
/* 22 */     return this.processing;
/*    */   }
/*    */   
/*    */   public void setProcessing(boolean processing) {
/* 26 */     this.processing = processing;
/*    */   }
/*    */   
/*    */   public int getLength() {
/* 30 */     return this.length;
/*    */   }
/*    */   
/*    */   public void setLength(int length) {
/* 34 */     this.length = length;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\AnalysisResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */