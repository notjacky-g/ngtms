/*    */ package com.hwacom.ngtms.ncc.remote;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract interface NccCallback
/*    */ {
/*    */   public abstract void onResponse(String paramString1, String paramString2, TcResponse paramTcResponse);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onMuliResponses(String sessionId, String[] deviceNames, TcResponse[] tcResponses)
/*    */   {
/* 24 */     for (int i = 0; i < deviceNames.length; i++) {
/* 25 */       onResponse(sessionId, deviceNames[i], tcResponses[i]);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\remote\NccCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */