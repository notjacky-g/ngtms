/*     */ package com.hwacom.ngtms.ncc.tc.fw2.marshalhelper;
/*     */ 
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.AnalysisResult;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandDefinitionException;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandFormatException;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.DecodeException;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.IllegalParamException;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.cms.CmsDisplayMessageChangeReportPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.cms.ExtCmsDisplayMessageChangeReportPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.cms.GetCmsDisplayPatternReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.cms.GetCmsDisplayPatternRspPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.cms.GetCmsMonitorRspPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.cms.GetExtCmsMonitorRspPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.cms.SetAddCmsLoopMessageReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.cms.SetCmsDisplayMessageReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.cms.SetCmsFullColorDisplayReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.cms.SetCmsFullColorGraphicPatternReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.cms.SetCmsFullColorGraphicReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.cms.SetCmsGraphicPatternReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.cms.SetCmsPrestoreMessageReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.cms.SetCmsReactDefaultMessageReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.cms.SetExtCmsDisplayMessageReqPm;
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
/*     */ public class Fw2CmsMarshalHelper
/*     */ {
/*     */   public static AnalysisResult marshalAnalyze(SetCmsGraphicPatternReqPm setCmsGraphicPatternReqPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/*  58 */     if (encode) {
/*  59 */       if (setCmsGraphicPatternReqPm.graphicPatternColorWithDescPm != null)
/*  60 */         return AnalysisResult.Process;
/*  61 */       return AnalysisResult.Skip;
/*     */     }
/*  63 */     if (availableLength > 0) return AnalysisResult.Process;
/*  64 */     return AnalysisResult.Skip;
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
/*     */   public static AnalysisResult marshalAnalyze(SetCmsFullColorGraphicPatternReqPm setCmsFullColorGraphicPatternReqPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/*  82 */     if (encode) {
/*  83 */       if (setCmsFullColorGraphicPatternReqPm.graphicPatternColorWithDescPm != null)
/*  84 */         return AnalysisResult.Process;
/*  85 */       return AnalysisResult.Skip;
/*     */     }
/*  87 */     if (availableLength > 0) return AnalysisResult.Process;
/*  88 */     return AnalysisResult.Skip;
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
/*     */   public static AnalysisResult marshalAnalyze(GetCmsDisplayPatternReqPm getCmsDisplayPatternReqPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/* 105 */     if (encode) {
/* 106 */       if (getCmsDisplayPatternReqPm.frameIdWrapperPm != null) return AnalysisResult.Process;
/* 107 */       return AnalysisResult.Skip;
/*     */     }
/* 109 */     if (availableLength > 0) return AnalysisResult.Process;
/* 110 */     return AnalysisResult.Skip;
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
/*     */   public static AnalysisResult marshalAnalyze(GetCmsDisplayPatternRspPm getCmsDisplayPatternRspPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/* 128 */     if (encode) {
/* 129 */       if (getCmsDisplayPatternRspPm.graphicPatternColorPm != null) return AnalysisResult.Process;
/* 130 */       return AnalysisResult.Skip;
/*     */     }
/* 132 */     if (availableLength > 0) return AnalysisResult.Process;
/* 133 */     return AnalysisResult.Skip;
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
/*     */   public static AnalysisResult marshalAnalyze(SetCmsReactDefaultMessageReqPm setCmsReactDefaultMessageReqPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/* 153 */     if (setCmsReactDefaultMessageReqPm.dataType == 0) {
/* 154 */       if (id.equals("textContent")) return AnalysisResult.Process;
/* 155 */       return AnalysisResult.Skip;
/*     */     }
/* 157 */     if (id.equals("graphicContent")) return AnalysisResult.Process;
/* 158 */     return AnalysisResult.Skip;
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
/*     */   public static AnalysisResult marshalAnalyze(GetCmsMonitorRspPm getCmsMonitorRspPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/* 178 */     if (getCmsMonitorRspPm.dataType == 0) {
/* 179 */       if (id.equals("textContent")) return AnalysisResult.Process;
/* 180 */       return AnalysisResult.Skip;
/*     */     }
/* 182 */     if (id.equals("graphicContentWithDesc")) return AnalysisResult.Process;
/* 183 */     return AnalysisResult.Skip;
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
/*     */   public static AnalysisResult marshalAnalyze(SetCmsDisplayMessageReqPm setCmsDisplayMessageReqPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/* 202 */     if (setCmsDisplayMessageReqPm.dataType == 0) {
/* 203 */       if (id.equals("textContent")) return AnalysisResult.Process;
/* 204 */       return AnalysisResult.Skip;
/*     */     }
/* 206 */     if (id.equals("graphicContent")) return AnalysisResult.Process;
/* 207 */     return AnalysisResult.Skip;
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
/*     */   public static AnalysisResult marshalAnalyze(CmsDisplayMessageChangeReportPm cmsDisplayMessageChangeReportPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/* 230 */     if (cmsDisplayMessageChangeReportPm.dataType == 0) {
/* 231 */       if (id.equals("textContent")) return AnalysisResult.Process;
/* 232 */       return AnalysisResult.Skip;
/*     */     }
/* 234 */     if (id.equals("graphicContentWithDesc")) return AnalysisResult.Process;
/* 235 */     return AnalysisResult.Skip;
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
/*     */   public static AnalysisResult marshalAnalyze(SetAddCmsLoopMessageReqPm setAddCmsLoopMessageReqPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/* 255 */     if (setAddCmsLoopMessageReqPm.dataType == 0) {
/* 256 */       if (id.equals("textContent")) return AnalysisResult.Process;
/* 257 */       return AnalysisResult.Skip;
/*     */     }
/* 259 */     if (id.equals("graphicContent")) return AnalysisResult.Process;
/* 260 */     return AnalysisResult.Skip;
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
/*     */   public static AnalysisResult marshalAnalyze(GetExtCmsMonitorRspPm getExtCmsMonitorRspPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/* 281 */     if (encode) {
/* 282 */       if (getExtCmsMonitorRspPm.textContentPm != null) return AnalysisResult.Process;
/* 283 */       return AnalysisResult.Skip;
/*     */     }
/* 285 */     if (availableLength > 0) return AnalysisResult.Process;
/* 286 */     return AnalysisResult.Skip;
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
/*     */   public static AnalysisResult marshalAnalyze(SetExtCmsDisplayMessageReqPm setExtCmsDisplayMessageReqPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/* 306 */     if (encode) {
/* 307 */       if (setExtCmsDisplayMessageReqPm.textContentPm != null) return AnalysisResult.Process;
/* 308 */       return AnalysisResult.Skip;
/*     */     }
/* 310 */     if (availableLength > 0) return AnalysisResult.Process;
/* 311 */     return AnalysisResult.Skip;
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
/*     */   public static AnalysisResult marshalAnalyze(SetCmsPrestoreMessageReqPm setCmsPrestoreMessageReqPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/* 331 */     if (encode) {
/* 332 */       if (setCmsPrestoreMessageReqPm.textContentPm != null) return AnalysisResult.Process;
/* 333 */       return AnalysisResult.Skip;
/*     */     }
/* 335 */     if (availableLength > 0) return AnalysisResult.Process;
/* 336 */     return AnalysisResult.Skip;
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
/*     */ 
/*     */   public static AnalysisResult marshalAnalyze(ExtCmsDisplayMessageChangeReportPm extCmsDisplayMessageChangeReportPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/* 360 */     if (encode) {
/* 361 */       if (extCmsDisplayMessageChangeReportPm.textContentPm != null) return AnalysisResult.Process;
/* 362 */       return AnalysisResult.Skip;
/*     */     }
/* 364 */     if (availableLength > 0) return AnalysisResult.Process;
/* 365 */     return AnalysisResult.Skip;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static AnalysisResult marshalAnalyze(SetCmsFullColorGraphicReqPm setCmsFullColorGraphicReqPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/* 376 */     if (encode) {
/* 377 */       if (setCmsFullColorGraphicReqPm.graphicPatternColorWithDescPm != null)
/* 378 */         return AnalysisResult.Process;
/* 379 */       return AnalysisResult.Skip;
/*     */     }
/* 381 */     if (availableLength > 0) return AnalysisResult.Process;
/* 382 */     return AnalysisResult.Skip;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static AnalysisResult marshalAnalyze(SetCmsFullColorDisplayReqPm setCmsFullColorDisplayReqPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/* 393 */     if ((setCmsFullColorDisplayReqPm.dataType < 7) || (setCmsFullColorDisplayReqPm.dataType > 8)) {
/* 394 */       if (id.equals("textContent")) return AnalysisResult.Process;
/* 395 */       return AnalysisResult.Skip;
/*     */     }
/* 397 */     return AnalysisResult.Skip;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\marshalhelper\Fw2CmsMarshalHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */