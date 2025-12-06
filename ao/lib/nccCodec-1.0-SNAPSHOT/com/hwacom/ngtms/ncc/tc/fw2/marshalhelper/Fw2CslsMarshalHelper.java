/*    */ package com.hwacom.ngtms.ncc.tc.fw2.marshalhelper;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.AnalysisResult;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandDefinitionException;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandFormatException;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.DecodeException;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.IllegalParamException;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.csls.GetCslsDisplayPatternReqPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.csls.GetCslsDisplayPatternRspPm;
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
/*    */ public class Fw2CslsMarshalHelper
/*    */ {
/*    */   public static AnalysisResult marshalAnalyze(GetCslsDisplayPatternReqPm getCslsDisplayPatternReqPm, String id, boolean encode, int availableLength)
/*    */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*    */   {
/* 32 */     if (encode) {
/* 33 */       if (getCslsDisplayPatternReqPm.cslsFrameIdWrapperPm != null) return AnalysisResult.Process;
/* 34 */       return AnalysisResult.Skip;
/*    */     }
/* 36 */     if (availableLength > 0) return AnalysisResult.Process;
/* 37 */     return AnalysisResult.Skip;
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
/*    */   public static AnalysisResult marshalAnalyze(GetCslsDisplayPatternRspPm getCslsDisplayPatternRspPm, String id, boolean encode, int availableLength)
/*    */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*    */   {
/* 55 */     if (encode) {
/* 56 */       if (getCslsDisplayPatternRspPm.cslsGraphicPatternColorPm != null)
/* 57 */         return AnalysisResult.Process;
/* 58 */       return AnalysisResult.Skip;
/*    */     }
/* 60 */     if (availableLength > 0) return AnalysisResult.Process;
/* 61 */     return AnalysisResult.Skip;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\marshalhelper\Fw2CslsMarshalHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */