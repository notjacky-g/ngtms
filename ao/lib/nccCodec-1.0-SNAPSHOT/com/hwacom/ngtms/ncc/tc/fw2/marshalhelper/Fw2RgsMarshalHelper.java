/*     */ package com.hwacom.ngtms.ncc.tc.fw2.marshalhelper;
/*     */ 
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.AnalysisResult;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandDefinitionException;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandFormatException;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.DecodeException;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.IllegalParamException;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rgs.GenericMessagePm.MsgListItem;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rgs.GetRgsGraphicPatternReqPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rgs.GetRgsGraphicPatternRspPm;
/*     */ import java.io.UnsupportedEncodingException;
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
/*     */ public class Fw2RgsMarshalHelper
/*     */ {
/*     */   public static AnalysisResult marshalAnalyze(GetRgsGraphicPatternReqPm getRgsGraphicPatternReqPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/*  36 */     if (encode) {
/*  37 */       if (getRgsGraphicPatternReqPm.rgsFrameIdWrapperPm != null) return AnalysisResult.Process;
/*  38 */       return AnalysisResult.Skip;
/*     */     }
/*  40 */     if (availableLength > 0) return AnalysisResult.Process;
/*  41 */     return AnalysisResult.Skip;
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
/*     */   public static AnalysisResult marshalAnalyze(GetRgsGraphicPatternRspPm getRgsGraphicPatternRspPm, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/*  61 */     if (encode) {
/*  62 */       if (getRgsGraphicPatternRspPm.rgsGraphicPatternColorWithDescPm != null)
/*  63 */         return AnalysisResult.Process;
/*  64 */       return AnalysisResult.Skip;
/*     */     }
/*  66 */     if (availableLength > 0) return AnalysisResult.Process;
/*  67 */     return AnalysisResult.Skip;
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
/*     */   public static AnalysisResult marshalAnalyze(GenericMessagePm.MsgListItem msgListItem, String id, boolean encode, int availableLength)
/*     */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*     */   {
/* 101 */     if (encode) {
/* 102 */       return AnalysisResult.Process;
/*     */     }
/* 104 */     if (id.equals("color")) {
/* 105 */       AnalysisResult analysisResult = new AnalysisResult(true);
/*     */       try
/*     */       {
/* 108 */         msg = new String(msgListItem.message, "Big5");
/*     */       } catch (UnsupportedEncodingException e) { String msg;
/* 110 */         throw new DecodeException("UnsupportedEncodingException:Big5");
/*     */       }
/*     */       String msg;
/* 113 */       int decrease = 0;
/* 114 */       for (byte b : msgListItem.message) {
/* 115 */         if ((b == 13) || (b == 10)) decrease++;
/*     */       }
/* 117 */       analysisResult.setLength((msg.length() - decrease) * 6);
/* 118 */       return analysisResult; }
/* 119 */     return AnalysisResult.Skip;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\marshalhelper\Fw2RgsMarshalHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */