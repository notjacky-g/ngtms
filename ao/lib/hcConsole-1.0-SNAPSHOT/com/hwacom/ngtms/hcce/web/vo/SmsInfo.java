/*    */ package com.hwacom.ngtms.hcce.web.vo;
/*    */ 
/*    */ 
/*    */ public class SmsInfo
/*    */ {
/*    */   private String toAddrs;
/*    */   
/*    */   private String messages;
/*    */   
/*    */   private String results;
/*    */   
/*    */ 
/*    */   public String getToAddrs()
/*    */   {
/* 15 */     return this.toAddrs;
/*    */   }
/*    */   
/*    */   public void setToAddrs(String toAddrs) {
/* 19 */     this.toAddrs = toAddrs;
/*    */   }
/*    */   
/*    */   public String getMessages() {
/* 23 */     return this.messages;
/*    */   }
/*    */   
/*    */   public void setMessages(String messages) {
/* 27 */     this.messages = messages;
/*    */   }
/*    */   
/*    */   public String getResults() {
/* 31 */     return this.results;
/*    */   }
/*    */   
/*    */   public void setResults(String results) {
/* 35 */     this.results = results;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 40 */     return "SmsForm [toAddrs=" + this.toAddrs + ", messages=" + this.messages + ", results=" + this.results + "]";
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcConsole-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\web\vo\SmsInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */