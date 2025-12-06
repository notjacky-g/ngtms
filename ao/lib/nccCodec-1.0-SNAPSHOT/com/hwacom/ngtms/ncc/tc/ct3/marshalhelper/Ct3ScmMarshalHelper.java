/*    */ package com.hwacom.ngtms.ncc.tc.ct3.marshalhelper;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.AnalysisResult;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandDefinitionException;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandFormatException;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.DecodeException;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.IllegalParamException;
/*    */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.sig.QuerySegmentSpecialDayRspPm;
/*    */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.sig.QuerySegmentSpecialDayRspPm.SegmentListItem;
/*    */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.sig.SetSegmentSpecialDayReqPm;
/*    */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.sig.SetSegmentSpecialDayReqPm.SegmentListItem;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class Ct3ScmMarshalHelper
/*    */ {
/*    */   public static AnalysisResult marshalAnalyze(SetSegmentSpecialDayReqPm setSegmentSpecialDayReqPm, String id, boolean encode, int availableLength)
/*    */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*    */   {
/* 20 */     if (encode) {
/* 21 */       if (setSegmentSpecialDayReqPm.segmentList.isEmpty()) return AnalysisResult.Skip;
/* 22 */       return AnalysisResult.Process;
/*    */     }
/* 24 */     if (availableLength > 0) {
/* 25 */       AnalysisResult result = new AnalysisResult(true);
/* 26 */       result.setLength(availableLength);
/* 27 */       return result;
/*    */     }
/* 29 */     return AnalysisResult.Skip;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static AnalysisResult marshalAnalyze(SetSegmentSpecialDayReqPm.SegmentListItem listItem, String id, boolean encode, int availableLength)
/*    */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*    */   {
/* 40 */     if (encode) {
/* 41 */       if (listItem == null) return AnalysisResult.Skip;
/* 42 */       return AnalysisResult.Process;
/*    */     }
/* 44 */     if (availableLength > 0) {
/* 45 */       AnalysisResult result = new AnalysisResult(true);
/* 46 */       result.setLength(availableLength);
/* 47 */       return result;
/*    */     }
/* 49 */     return AnalysisResult.Skip;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static AnalysisResult marshalAnalyze(QuerySegmentSpecialDayRspPm querySegmentSpecialDayRspPm, String id, boolean encode, int availableLength)
/*    */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*    */   {
/* 60 */     if (encode) {
/* 61 */       if (querySegmentSpecialDayRspPm.segmentList.isEmpty()) return AnalysisResult.Skip;
/* 62 */       return AnalysisResult.Process;
/*    */     }
/* 64 */     if (availableLength > 0) {
/* 65 */       AnalysisResult result = new AnalysisResult(true);
/* 66 */       result.setLength(availableLength);
/* 67 */       return result;
/*    */     }
/* 69 */     return AnalysisResult.Skip;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static AnalysisResult marshalAnalyze(QuerySegmentSpecialDayRspPm.SegmentListItem listItem, String id, boolean encode, int availableLength)
/*    */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*    */   {
/* 80 */     if (encode) {
/* 81 */       if (listItem == null) return AnalysisResult.Skip;
/* 82 */       return AnalysisResult.Process;
/*    */     }
/* 84 */     if (availableLength > 0) {
/* 85 */       AnalysisResult result = new AnalysisResult(true);
/* 86 */       result.setLength(availableLength);
/* 87 */       return result;
/*    */     }
/* 89 */     return AnalysisResult.Skip;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\marshalhelper\Ct3ScmMarshalHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */