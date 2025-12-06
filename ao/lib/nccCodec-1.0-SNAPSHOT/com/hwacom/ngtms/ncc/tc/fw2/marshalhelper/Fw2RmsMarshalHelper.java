/*    */ package com.hwacom.ngtms.ncc.tc.fw2.marshalhelper;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.AnalysisResult;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandDefinitionException;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandFormatException;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.DecodeException;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.IllegalParamException;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms.GetRmsBosDisplayPatternReqPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms.GetRmsBosDisplayPatternRspPm;
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
/*    */ public class Fw2RmsMarshalHelper
/*    */ {
/*    */   public static AnalysisResult marshalAnalyze(GetRmsBosDisplayPatternReqPm getRmsBosDisplayPatternReqPm, String id, boolean encode, int availableLength)
/*    */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*    */   {
/* 33 */     if (encode) {
/* 34 */       if (getRmsBosDisplayPatternReqPm.frameIdWrapperPm != null) return AnalysisResult.Process;
/* 35 */       return AnalysisResult.Skip;
/*    */     }
/* 37 */     if (availableLength > 0) return AnalysisResult.Process;
/* 38 */     return AnalysisResult.Skip;
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
/*    */   public static AnalysisResult marshalAnalyze(GetRmsBosDisplayPatternRspPm getRmsBosDisplayPatternRspPm, String id, boolean encode, int availableLength)
/*    */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*    */   {
/* 57 */     if (encode) {
/* 58 */       if (getRmsBosDisplayPatternRspPm.bosPatternColorPm != null) return AnalysisResult.Process;
/* 59 */       return AnalysisResult.Skip;
/*    */     }
/* 61 */     if (availableLength > 0) return AnalysisResult.Process;
/* 62 */     return AnalysisResult.Skip;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\marshalhelper\Fw2RmsMarshalHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */