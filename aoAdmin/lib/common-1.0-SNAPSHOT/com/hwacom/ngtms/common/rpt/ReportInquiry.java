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
/*    */ 
/*    */ public interface ReportInquiry
/*    */ {
/*    */   List<Map<String, Object>> inquire(Map<String, Object> paramMap);
/*    */   
/*    */   default List<PreviewData> inquireGridData(Map<String, Object> inputParameter) {
/* 39 */     return Collections.emptyList();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default Map<String, PreviewHeader> retrievePreviewHeader() {
/* 49 */     return Collections.emptyMap();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default PreviewGridData inquirePreviewGridData(Map<String, Object> inputParameter) {
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
/*    */   
/*    */   default String generatePoiFile(Map<String, Object> inputParameter, byte[] slaveReport1, byte[] slaveReport2) {
/* 72 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\rpt\ReportInquiry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */