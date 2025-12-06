/*    */ package com.hwacom.ngtms.ao.am.view.rpt;
/*    */ 
/*    */ import com.google.gwt.user.client.rpc.IsSerializable;
/*    */ import com.sencha.gxt.widget.core.client.Composite;
/*    */ import java.io.Serializable;
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
/*    */ public abstract class Viewer
/*    */   extends Composite
/*    */ {
/*    */   public void selectItem(ViewerRequiredInfo item) {}
/*    */   
/*    */   public static class ViewerRequiredInfo
/*    */     implements Serializable, IsSerializable
/*    */   {
/*    */     private String reportCategory;
/*    */     private String reportName;
/*    */     private Map<String, String> reportInputParameter;
/*    */     
/*    */     public String getReportCategory() {
/* 38 */       return this.reportCategory;
/*    */     }
/*    */     
/*    */     public void setReportCategory(String reportCategory) {
/* 42 */       this.reportCategory = reportCategory;
/*    */     }
/*    */     
/*    */     public String getReportName() {
/* 46 */       return this.reportName;
/*    */     }
/*    */     
/*    */     public void setReportName(String reportName) {
/* 50 */       this.reportName = reportName;
/*    */     }
/*    */     
/*    */     public Map<String, String> getReportInputParameter() {
/* 54 */       return this.reportInputParameter;
/*    */     }
/*    */     
/*    */     public void setReportInputParameter(Map<String, String> reportInputParameter) {
/* 58 */       this.reportInputParameter = reportInputParameter;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\rpt\Viewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */