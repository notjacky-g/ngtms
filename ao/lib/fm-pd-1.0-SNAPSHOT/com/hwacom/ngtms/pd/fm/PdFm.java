/*    */ package com.hwacom.ngtms.pd.fm;
/*    */ 
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
/*    */ 
/*    */ public class PdFm
/*    */   extends FmeMainBase
/*    */ {
/* 18 */   private static Logger logger = LoggerFactory.getLogger(PdFm.class);
/*    */   
/*    */   static
/*    */   {
/* 22 */     setDynamicConfigDeclares(new DynamicConfigDeclare[] { new DynamicConfigDeclare("addedPdDeviceType", "VD,ETAG,AVI,CMS,CSLS,LCS,CCTV") });
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public PdFm(String name, String description)
/*    */   {
/* 30 */     super(name, description);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isAllowConcurrentExecution()
/*    */   {
/* 38 */     return false;
/*    */   }
/*    */   
/*    */   public void init()
/*    */     throws FmException
/*    */   {}
/*    */   
/*    */   /* Error */
/*    */   protected void runFm()
/*    */     throws java.lang.Exception
/*    */   {
/*    */     // Byte code:
/*    */     //   0: getstatic 2	com/hwacom/ngtms/pd/fm/PdFm:logger	Lorg/slf4j/Logger;
/*    */     //   3: ldc 3
/*    */     //   5: aload_0
/*    */     //   6: invokevirtual 4	com/hwacom/ngtms/pd/fm/PdFm:getFmeName	()Ljava/lang/String;
/*    */     //   9: invokeinterface 5 3 0
/*    */     //   14: aload_0
/*    */     //   15: invokevirtual 6	com/hwacom/ngtms/pd/fm/PdFm:checkFmKeepRunning	()Z
/*    */     //   18: ifeq +16 -> 34
/*    */     //   21: ldc2_w 7
/*    */     //   24: invokestatic 9	java/lang/Thread:sleep	(J)V
/*    */     //   27: goto -13 -> 14
/*    */     //   30: astore_1
/*    */     //   31: goto -17 -> 14
/*    */     //   34: getstatic 2	com/hwacom/ngtms/pd/fm/PdFm:logger	Lorg/slf4j/Logger;
/*    */     //   37: ldc 11
/*    */     //   39: aload_0
/*    */     //   40: invokevirtual 4	com/hwacom/ngtms/pd/fm/PdFm:getFmeName	()Ljava/lang/String;
/*    */     //   43: invokeinterface 5 3 0
/*    */     //   48: goto +20 -> 68
/*    */     //   51: astore_2
/*    */     //   52: getstatic 2	com/hwacom/ngtms/pd/fm/PdFm:logger	Lorg/slf4j/Logger;
/*    */     //   55: ldc 11
/*    */     //   57: aload_0
/*    */     //   58: invokevirtual 4	com/hwacom/ngtms/pd/fm/PdFm:getFmeName	()Ljava/lang/String;
/*    */     //   61: invokeinterface 5 3 0
/*    */     //   66: aload_2
/*    */     //   67: athrow
/*    */     //   68: return
/*    */     // Line number table:
/*    */     //   Java source line #47	-> byte code offset #0
/*    */     //   Java source line #49	-> byte code offset #14
/*    */     //   Java source line #51	-> byte code offset #21
/*    */     //   Java source line #54	-> byte code offset #27
/*    */     //   Java source line #52	-> byte code offset #30
/*    */     //   Java source line #53	-> byte code offset #31
/*    */     //   Java source line #57	-> byte code offset #34
/*    */     //   Java source line #58	-> byte code offset #48
/*    */     //   Java source line #57	-> byte code offset #51
/*    */     //   Java source line #59	-> byte code offset #68
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	69	0	this	PdFm
/*    */     //   30	2	1	ex	InterruptedException
/*    */     //   51	16	2	localObject	Object
/*    */     // Exception table:
/*    */     //   from	to	target	type
/*    */     //   21	27	30	java/lang/InterruptedException
/*    */     //   0	34	51	finally
/*    */   }
/*    */   
/*    */   public void startTesting()
/*    */     throws FmException
/*    */   {}
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\fm-pd-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\pd\fm\PdFm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */