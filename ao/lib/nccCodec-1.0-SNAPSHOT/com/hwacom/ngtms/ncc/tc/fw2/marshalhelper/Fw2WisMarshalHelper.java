/*     */ package com.hwacom.ngtms.ncc.tc.fw2.marshalhelper;
/*     */ 
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.AnalysisResult;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandDefinitionException;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandFormatException;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.DecodeException;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.IllegalParamException;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.wis.GetWisDisplayPatternReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.wis.GetWisDisplayPatternRspPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.wis.GetWisMonitorRspPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.wis.SetWisDisplayMessageReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.wis.SetWisGraphicPatternReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.wis.SetWisReactDefaultMessageReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.wis.WisDisplayMessageChangeReportPm;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Fw2WisMarshalHelper
/*     */ {
/*     */   public static AnalysisResult marshalAnalyze(SetWisGraphicPatternReqPm setWisGraphicPatternReqPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/*  50 */     if (encode) {
/*  51 */       if (setWisGraphicPatternReqPm.wisGraphicPatternColorWithDescPm != null)
/*  52 */         return AnalysisResult.Process;
/*  53 */       return AnalysisResult.Skip;
/*     */     }
/*  55 */     if (availableLength > 0) return AnalysisResult.Process;
/*  56 */     return AnalysisResult.Skip;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static AnalysisResult marshalAnalyze(GetWisDisplayPatternReqPm getWisDisplayPatternReqPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/*  73 */     if (encode) {
/*  74 */       if (getWisDisplayPatternReqPm.wisFrameIdWrapperPm != null) return AnalysisResult.Process;
/*  75 */       return AnalysisResult.Skip;
/*     */     }
/*  77 */     if (availableLength > 0) return AnalysisResult.Process;
/*  78 */     return AnalysisResult.Skip;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static AnalysisResult marshalAnalyze(GetWisDisplayPatternRspPm getWisDisplayPatternRspPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/*  96 */     if (encode) {
/*  97 */       if (getWisDisplayPatternRspPm.wisGraphicPatternColorPm != null) return AnalysisResult.Process;
/*  98 */       return AnalysisResult.Skip;
/*     */     }
/* 100 */     if (availableLength > 0) return AnalysisResult.Process;
/* 101 */     return AnalysisResult.Skip;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static AnalysisResult marshalAnalyze(SetWisReactDefaultMessageReqPm setWisReactDefaultMessageReqPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/* 121 */     if (setWisReactDefaultMessageReqPm.dataType == 0) {
/* 122 */       if (id.equals("wisTextContent")) return AnalysisResult.Process;
/* 123 */       return AnalysisResult.Skip;
/*     */     }
/* 125 */     if (id.equals("wisGraphicContent")) return AnalysisResult.Process;
/* 126 */     return AnalysisResult.Skip;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static AnalysisResult marshalAnalyze(GetWisMonitorRspPm getWisMonitorRspPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/* 146 */     if (getWisMonitorRspPm.dataType == 0) {
/* 147 */       if (id.equals("wisTextContent")) return AnalysisResult.Process;
/* 148 */       return AnalysisResult.Skip;
/*     */     }
/* 150 */     if (id.equals("wisGraphicContentWithDesc")) return AnalysisResult.Process;
/* 151 */     return AnalysisResult.Skip;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static AnalysisResult marshalAnalyze(SetWisDisplayMessageReqPm setWisDisplayMessageReqPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/* 170 */     if (setWisDisplayMessageReqPm.dataType == 0) {
/* 171 */       if (id.equals("wisTextContent")) return AnalysisResult.Process;
/* 172 */       return AnalysisResult.Skip;
/*     */     }
/* 174 */     if (id.equals("wisGraphicContent")) return AnalysisResult.Process;
/* 175 */     return AnalysisResult.Skip;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static AnalysisResult marshalAnalyze(WisDisplayMessageChangeReportPm wisDisplayMessageChangeReportPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/* 198 */     if (wisDisplayMessageChangeReportPm.dataType == 0) {
/* 199 */       if (id.equals("wisTextContent")) return AnalysisResult.Process;
/* 200 */       return AnalysisResult.Skip;
/*     */     }
/* 202 */     if (id.equals("wisGraphicContentWithDesc")) return AnalysisResult.Process;
/* 203 */     return AnalysisResult.Skip;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\marshalhelper\Fw2WisMarshalHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */