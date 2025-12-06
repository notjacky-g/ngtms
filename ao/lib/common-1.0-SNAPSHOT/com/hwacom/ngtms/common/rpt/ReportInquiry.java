/*    */ package com.hwacom.ngtms.common.rpt;
/*    */ 
/*    */ import com.hwacom.ngtms.common.shared.PreviewData;
/*    */ import com.hwacom.ngtms.common.shared.PreviewGridData;
/*    */ import com.hwacom.ngtms.common.shared.PreviewHeader;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract interface ReportInquiry
/*    */ {
/*    */   public abstract List<Map<String, Object>> inquire(Map<String, Object> paramMap);
/*    */   
/*    */   public List<PreviewData> inquireGridData(Map<String, Object> inputParameter)
/*    */   {
/* 39 */     return Collections.emptyList();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Map<String, PreviewHeader> retrievePreviewHeader()
/*    */   {
/* 49 */     return Collections.emptyMap();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public PreviewGridData inquirePreviewGridData(Map<String, Object> inputParameter)
/*    */   {
/* 59 */     return new PreviewGridData();
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
/*    */   public String generatePoiFile(Map<String, Object> inputParameter, byte[] slaveReport1, byte[] slaveReport2)
/*    */   {
/* 72 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\rpt\ReportInquiry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */