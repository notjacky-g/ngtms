/*    */ package com.hwacom.ngtms.ncc.tc.fw2.marshalhelper;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.AnalysisResult;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandDefinitionException;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandFormatException;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.DecodeException;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.IllegalParamException;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.ettu.AddEttuAllocateRspPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.ettu.RemoveEttuAllocateRspPm;
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
/*    */ public class Fw2EttuMarshalHelper
/*    */ {
/*    */   public static AnalysisResult marshalAnalyze(AddEttuAllocateRspPm addEttuAllocateRspPm, String id, boolean encode, int availableLength)
/*    */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*    */   {
/* 33 */     if (encode) {
/* 34 */       if ((id.equals("phoneNumber")) && (addEttuAllocateRspPm.phoneNumberPm != null))
/* 35 */         return AnalysisResult.Process;
/* 36 */       if ((id.equals("segment")) && (addEttuAllocateRspPm.segmentPm != null))
/* 37 */         return AnalysisResult.Process;
/* 38 */       if ((id.equals("area")) && (addEttuAllocateRspPm.areaPm != null))
/* 39 */         return AnalysisResult.Process;
/* 40 */       if ((id.equals("allocate")) && (addEttuAllocateRspPm.allocatePm != null))
/* 41 */         return AnalysisResult.Process;
/* 42 */       return AnalysisResult.Skip;
/*    */     }
/* 44 */     if (availableLength > 3) return AnalysisResult.Process;
/* 45 */     return AnalysisResult.Skip;
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
/*    */   public static AnalysisResult marshalAnalyze(RemoveEttuAllocateRspPm removeEttuAllocateRspPm, String id, boolean encode, int availableLength)
/*    */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*    */   {
/* 63 */     if (encode) {
/* 64 */       if (removeEttuAllocateRspPm.phoneNumberPm != null) return AnalysisResult.Process;
/* 65 */       return AnalysisResult.Skip;
/*    */     }
/* 67 */     if (availableLength > 3) return AnalysisResult.Process;
/* 68 */     return AnalysisResult.Skip;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\marshalhelper\Fw2EttuMarshalHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */