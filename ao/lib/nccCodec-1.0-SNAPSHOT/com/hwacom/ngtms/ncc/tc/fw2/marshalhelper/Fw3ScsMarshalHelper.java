/*    */ package com.hwacom.ngtms.ncc.tc.fw2.marshalhelper;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.AnalysisResult;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandDefinitionException;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandFormatException;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.DecodeException;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.IllegalParamException;
/*    */ import com.hwacom.ngtms.ncc.tc.fw3.cmdparam.scs.GetFullColorScsHwStatusRspPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw3.cmdparam.scs.ScsFullColorDisplayMessageReportPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw3.cmdparam.scs.SetScsFullColorDisplayMessageReqPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw3.cmdparam.scs.SetScsFullColorGraphicPatternReqPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw3.cmdparam.scs.SetScsGraphicReqPm;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Fw3ScsMarshalHelper
/*    */ {
/*    */   public static AnalysisResult marshalAnalyze(SetScsFullColorGraphicPatternReqPm setScsFullColorGraphicPatternReqPm, String id, boolean encode, int availableLength)
/*    */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*    */   {
/* 23 */     if (encode) {
/* 24 */       if (setScsFullColorGraphicPatternReqPm.graphicPatternColorWithDescPm != null)
/* 25 */         return AnalysisResult.Process;
/* 26 */       return AnalysisResult.Skip;
/*    */     }
/* 28 */     if (availableLength > 0) return AnalysisResult.Process;
/* 29 */     return AnalysisResult.Skip;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static AnalysisResult marshalAnalyze(SetScsFullColorDisplayMessageReqPm setScsFullColorDisplayMessageReqPm, String id, boolean encode, int availableLength)
/*    */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*    */   {
/* 40 */     if ((setScsFullColorDisplayMessageReqPm.dataType < 7) || (setScsFullColorDisplayMessageReqPm.dataType > 8))
/*    */     {
/* 42 */       if (id.equals("textContent")) return AnalysisResult.Process;
/* 43 */       return AnalysisResult.Skip;
/*    */     }
/* 45 */     return AnalysisResult.Skip;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static AnalysisResult marshalAnalyze(ScsFullColorDisplayMessageReportPm scsFullColorDisplayMessageReportPm, String id, boolean encode, int availableLength)
/*    */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*    */   {
/* 55 */     if ((scsFullColorDisplayMessageReportPm.dataType < 7) || (scsFullColorDisplayMessageReportPm.dataType > 8))
/*    */     {
/* 57 */       if (id.equals("textContent")) return AnalysisResult.Process;
/* 58 */       return AnalysisResult.Skip;
/*    */     }
/* 60 */     return AnalysisResult.Skip;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static AnalysisResult marshalAnalyze(GetFullColorScsHwStatusRspPm getFullColorScsHwStatusRspPm, String id, boolean encode, int availableLength)
/*    */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*    */   {
/* 70 */     if ((getFullColorScsHwStatusRspPm.dataType < 7) || (getFullColorScsHwStatusRspPm.dataType > 8)) {
/* 71 */       if (id.equals("textContent")) return AnalysisResult.Process;
/* 72 */       return AnalysisResult.Skip;
/*    */     }
/* 74 */     return AnalysisResult.Skip;
/*    */   }
/*    */   
/*    */ 
/*    */   public static AnalysisResult marshalAnalyze(SetScsGraphicReqPm setScsGraphicReqPm, String id, boolean encode, int availableLength)
/*    */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*    */   {
/* 81 */     if (!encode) {
/* 82 */       if (availableLength > 0) return AnalysisResult.Process;
/* 83 */       return AnalysisResult.Skip;
/*    */     }
/* 85 */     return AnalysisResult.Process;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\marshalhelper\Fw3ScsMarshalHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */