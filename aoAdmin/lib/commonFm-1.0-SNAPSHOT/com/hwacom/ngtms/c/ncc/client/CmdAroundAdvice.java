/*    */ package com.hwacom.ngtms.c.ncc.client;
/*    */ 
/*    */ import com.hwacom.ngtms.ncc.remote.TcResponse;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface CmdAroundAdvice
/*    */ {
/*    */   Object[] before(long paramLong, CmdRequestType paramCmdRequestType, Object[] paramArrayOfObject);
/*    */   
/*    */   TcResponse after(long paramLong, String paramString, TcResponse paramTcResponse);
/*    */   
/*    */   public enum CmdRequestType
/*    */   {
/* 19 */     SyncSingleTcSingleRequest,
/* 20 */     AsyncSingleTcSingleRequest,
/* 21 */     AsyncSingleTcMultipleRequest,
/* 22 */     AsyncMultipleTcSingleRequest;
/*    */   }
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
/*    */ 
/*    */ 
/*    */   
/*    */   default String getDeviceName(Object[] args) {
/* 48 */     return (String)args[0];
/*    */   }
/*    */   
/*    */   default String[] getDeviceNames(Object[] args) {
/* 52 */     return (String[])args[0];
/*    */   }
/*    */   
/*    */   default Object getCmdBindingObj(Object[] args) {
/* 56 */     return args[1];
/*    */   }
/*    */   
/*    */   default Object[] getCmdBindingObjs(Object[] args) {
/* 60 */     return (Object[])args[1];
/*    */   }
/*    */   
/*    */   default TcResponseCallback getTcResponseCallback(Object[] args) {
/* 64 */     return (TcResponseCallback)args[2];
/*    */   }
/*    */   
/*    */   default String getReqSessionId(Object[] args) {
/* 68 */     if (args.length > 3) return (String)args[3]; 
/* 69 */     return null;
/*    */   }
/*    */   
/*    */   default long getTimeout(Object[] args) {
/* 73 */     if (args.length > 4) return ((Long)args[4]).longValue(); 
/* 74 */     return 0L;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\ncc\client\CmdAroundAdvice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */