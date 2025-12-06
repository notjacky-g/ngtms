/*    */ package com.hwacom.ngtms.ncc.tc.ct3.marshalhelper;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.AnalysisResult;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandDefinitionException;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandFormatException;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.DecodeException;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.IllegalParamException;
/*    */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.cms.QueryDisplayTextFullColorParamRspPm;
/*    */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.cms.QueryOffLineDisplayModeRspPm;
/*    */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.cms.QueryTextFullColorParamRspPm;
/*    */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.cms.SetOffLineDisplayModeReqPm;
/*    */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.cms.SetTextFullColorParamReqPm;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Ct3CmsMarshalHelper
/*    */ {
/*    */   public static AnalysisResult marshalAnalyze(SetOffLineDisplayModeReqPm setOffLineDisplayModeReqPm, String id, boolean encode, int availableLength)
/*    */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*    */   {
/* 23 */     if (encode) {
/* 24 */       return AnalysisResult.Process;
/*    */     }
/* 26 */     AnalysisResult result = new AnalysisResult(true);
/* 27 */     result.setLength(availableLength);
/* 28 */     return result;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static AnalysisResult marshalAnalyze(QueryOffLineDisplayModeRspPm queryOffLineDisplayModeRspPm, String id, boolean encode, int availableLength)
/*    */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*    */   {
/* 39 */     if (encode) {
/* 40 */       return AnalysisResult.Process;
/*    */     }
/* 42 */     AnalysisResult result = new AnalysisResult(true);
/* 43 */     result.setLength(availableLength);
/* 44 */     return result;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static AnalysisResult marshalAnalyze(SetTextFullColorParamReqPm reqPm, String id, boolean encode, int availableLength)
/*    */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*    */   {
/* 52 */     if (encode) {
/* 53 */       return AnalysisResult.Process;
/*    */     }
/* 55 */     if ((id.equals("bgColorList")) || (id.equals("glitterSpeedList"))) {
/* 56 */       AnalysisResult result = new AnalysisResult(true);
/* 57 */       result.setLength(reqPm.fontColorList.size());
/* 58 */       return result;
/*    */     }
/* 60 */     return AnalysisResult.Process;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static AnalysisResult marshalAnalyze(QueryTextFullColorParamRspPm rspPm, String id, boolean encode, int availableLength)
/*    */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*    */   {
/* 68 */     if (encode) {
/* 69 */       return AnalysisResult.Process;
/*    */     }
/* 71 */     if ((id.equals("bgColorList")) || (id.equals("glitterSpeedList"))) {
/* 72 */       AnalysisResult result = new AnalysisResult(true);
/* 73 */       result.setLength(rspPm.fontColorList.size());
/* 74 */       return result;
/*    */     }
/* 76 */     return AnalysisResult.Process;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static AnalysisResult marshalAnalyze(QueryDisplayTextFullColorParamRspPm rspPm, String id, boolean encode, int availableLength)
/*    */     throws CommandDefinitionException, IllegalParamException, CommandFormatException, DecodeException
/*    */   {
/* 84 */     if (encode) {
/* 85 */       return AnalysisResult.Process;
/*    */     }
/* 87 */     if ((id.equals("bgColorList")) || (id.equals("glitterSpeedList"))) {
/* 88 */       AnalysisResult result = new AnalysisResult(true);
/* 89 */       result.setLength(rspPm.fontColorList.size());
/* 90 */       return result;
/*    */     }
/* 92 */     return AnalysisResult.Process;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\marshalhelper\Ct3CmsMarshalHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */