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
/*    */ public abstract interface CmdAroundAdvice
/*    */ {
/*    */   public abstract Object[] before(long paramLong, CmdRequestType paramCmdRequestType, Object[] paramArrayOfObject);
/*    */   
/*    */   public abstract TcResponse after(long paramLong, String paramString, TcResponse paramTcResponse);
/*    */   
/*    */   public static enum CmdRequestType
/*    */   {
/* 19 */     SyncSingleTcSingleRequest, 
/* 20 */     AsyncSingleTcSingleRequest, 
/* 21 */     AsyncSingleTcMultipleRequest, 
/* 22 */     AsyncMultipleTcSingleRequest;
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
/*    */     private CmdRequestType() {}
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
/*    */   public String getDeviceName(Object[] args)
/*    */   {
/* 48 */     return (String)args[0];
/*    */   }
/*    */   
/*    */   public String[] getDeviceNames(Object[] args) {
/* 52 */     return (String[])args[0];
/*    */   }
/*    */   
/*    */   public Object getCmdBindingObj(Object[] args) {
/* 56 */     return args[1];
/*    */   }
/*    */   
/*    */   public Object[] getCmdBindingObjs(Object[] args) {
/* 60 */     return (Object[])args[1];
/*    */   }
/*    */   
/*    */   public TcResponseCallback getTcResponseCallback(Object[] args) {
/* 64 */     return (TcResponseCallback)args[2];
/*    */   }
/*    */   
/*    */   public String getReqSessionId(Object[] args) {
/* 68 */     if (args.length > 3) return (String)args[3];
/* 69 */     return null;
/*    */   }
/*    */   
/*    */   public long getTimeout(Object[] args) {
/* 73 */     if (args.length > 4) return ((Long)args[4]).longValue();
/* 74 */     return 0L;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\ncc\client\CmdAroundAdvice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */