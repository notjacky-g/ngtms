/*    */ package com.hwacom.ngtms.rtu.fm;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*    */ import com.hwacom.ngtms.hcce.core.exception.FmException;
/*    */ import com.hwacom.ngtms.hcce.fme.controller.fm.FmeMainBase;
/*    */ import com.hwacom.ngtms.hcce.shared.DynamicConfigDeclare;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RtuFm
/*    */   extends FmeMainBase
/*    */ {
/*    */   static
/*    */   {
/* 20 */     setDynamicConfigDeclares(new DynamicConfigDeclare[0]);
/*    */   }
/*    */   
/* 23 */   private static Logger logger = LoggerFactory.getLogger(RtuFm.class);
/*    */   
/*    */   public RtuFm(String fmeName, String description) {
/* 26 */     super(fmeName, description);
/*    */   }
/*    */   
/*    */   public void init() throws FmException
/*    */   {
/* 31 */     logger.info("{} init", getFmeName());
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
/*    */ 
/*    */ 
/*    */   protected void onDynaConfigUpdated(String key, DynamicConfig dynamicConfig)
/*    */   {
/* 60 */     rescheduleJob(key, dynamicConfig.getValue());
/*    */   }
/*    */   
/*    */   public boolean isAllowConcurrentExecution()
/*    */   {
/* 65 */     return false;
/*    */   }
/*    */   
/*    */   /* Error */
/*    */   protected void runFm()
/*    */     throws java.lang.Exception
/*    */   {
/*    */     // Byte code:
/*    */     //   0: getstatic 2	com/hwacom/ngtms/rtu/fm/RtuFm:logger	Lorg/slf4j/Logger;
/*    */     //   3: ldc 6
/*    */     //   5: aload_0
/*    */     //   6: invokevirtual 4	com/hwacom/ngtms/rtu/fm/RtuFm:getFmeName	()Ljava/lang/String;
/*    */     //   9: invokeinterface 5 3 0
/*    */     //   14: aload_0
/*    */     //   15: invokevirtual 7	com/hwacom/ngtms/rtu/fm/RtuFm:checkFmKeepRunning	()Z
/*    */     //   18: ifeq +16 -> 34
/*    */     //   21: ldc2_w 8
/*    */     //   24: invokestatic 10	java/lang/Thread:sleep	(J)V
/*    */     //   27: goto -13 -> 14
/*    */     //   30: astore_1
/*    */     //   31: goto -17 -> 14
/*    */     //   34: aload_0
/*    */     //   35: invokevirtual 12	com/hwacom/ngtms/rtu/fm/RtuFm:unbindFmRmiService	()V
/*    */     //   38: goto +15 -> 53
/*    */     //   41: astore_1
/*    */     //   42: getstatic 2	com/hwacom/ngtms/rtu/fm/RtuFm:logger	Lorg/slf4j/Logger;
/*    */     //   45: ldc 14
/*    */     //   47: aload_1
/*    */     //   48: invokeinterface 15 3 0
/*    */     //   53: getstatic 2	com/hwacom/ngtms/rtu/fm/RtuFm:logger	Lorg/slf4j/Logger;
/*    */     //   56: ldc 16
/*    */     //   58: aload_0
/*    */     //   59: invokevirtual 4	com/hwacom/ngtms/rtu/fm/RtuFm:getFmeName	()Ljava/lang/String;
/*    */     //   62: invokeinterface 5 3 0
/*    */     //   67: goto +39 -> 106
/*    */     //   70: astore_2
/*    */     //   71: aload_0
/*    */     //   72: invokevirtual 12	com/hwacom/ngtms/rtu/fm/RtuFm:unbindFmRmiService	()V
/*    */     //   75: goto +15 -> 90
/*    */     //   78: astore_3
/*    */     //   79: getstatic 2	com/hwacom/ngtms/rtu/fm/RtuFm:logger	Lorg/slf4j/Logger;
/*    */     //   82: ldc 14
/*    */     //   84: aload_3
/*    */     //   85: invokeinterface 15 3 0
/*    */     //   90: getstatic 2	com/hwacom/ngtms/rtu/fm/RtuFm:logger	Lorg/slf4j/Logger;
/*    */     //   93: ldc 16
/*    */     //   95: aload_0
/*    */     //   96: invokevirtual 4	com/hwacom/ngtms/rtu/fm/RtuFm:getFmeName	()Ljava/lang/String;
/*    */     //   99: invokeinterface 5 3 0
/*    */     //   104: aload_2
/*    */     //   105: athrow
/*    */     //   106: return
/*    */     // Line number table:
/*    */     //   Java source line #38	-> byte code offset #0
/*    */     //   Java source line #41	-> byte code offset #14
/*    */     //   Java source line #43	-> byte code offset #21
/*    */     //   Java source line #46	-> byte code offset #27
/*    */     //   Java source line #44	-> byte code offset #30
/*    */     //   Java source line #45	-> byte code offset #31
/*    */     //   Java source line #50	-> byte code offset #34
/*    */     //   Java source line #53	-> byte code offset #38
/*    */     //   Java source line #51	-> byte code offset #41
/*    */     //   Java source line #52	-> byte code offset #42
/*    */     //   Java source line #54	-> byte code offset #53
/*    */     //   Java source line #55	-> byte code offset #67
/*    */     //   Java source line #49	-> byte code offset #70
/*    */     //   Java source line #50	-> byte code offset #71
/*    */     //   Java source line #53	-> byte code offset #75
/*    */     //   Java source line #51	-> byte code offset #78
/*    */     //   Java source line #52	-> byte code offset #79
/*    */     //   Java source line #54	-> byte code offset #90
/*    */     //   Java source line #56	-> byte code offset #106
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	107	0	this	RtuFm
/*    */     //   30	2	1	ex	InterruptedException
/*    */     //   41	7	1	ex	Exception
/*    */     //   70	35	2	localObject	Object
/*    */     //   78	7	3	ex	Exception
/*    */     // Exception table:
/*    */     //   from	to	target	type
/*    */     //   21	27	30	java/lang/InterruptedException
/*    */     //   34	38	41	java/lang/Exception
/*    */     //   14	34	70	finally
/*    */     //   71	75	78	java/lang/Exception
/*    */   }
/*    */   
/*    */   public void startTesting()
/*    */     throws FmException
/*    */   {}
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\fm-rtu-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\rtu\fm\RtuFm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */