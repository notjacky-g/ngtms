/*    */ package com.hwacom.ngtms.hcce.web.vo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RequestForm<T>
/*    */ {
/*    */   public static final String GetRecord = "get-record";
/*    */   public static final String SaveRecord = "save-record";
/*    */   public static final String StatusSuccess = "success";
/*    */   public static final String StatusError = "error";
/*    */   private String cmd;
/*    */   private int recid;
/*    */   private String status;
/*    */   private T record;
/*    */   
/*    */   public String getCmd() {
/* 20 */     return this.cmd;
/*    */   }
/*    */   
/*    */   public void setCmd(String cmd) {
/* 24 */     this.cmd = cmd;
/*    */   }
/*    */   
/*    */   public int getRecid() {
/* 28 */     return this.recid;
/*    */   }
/*    */   
/*    */   public void setRecid(int recid) {
/* 32 */     this.recid = recid;
/*    */   }
/*    */   
/*    */   public String getStatus() {
/* 36 */     return this.status;
/*    */   }
/*    */   
/*    */   public void setStatus(String status) {
/* 40 */     this.status = status;
/*    */   }
/*    */   
/*    */   public T getRecord() {
/* 44 */     return this.record;
/*    */   }
/*    */   
/*    */   public void setRecord(T record) {
/* 48 */     this.record = record;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcConsole-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\web\vo\RequestForm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */