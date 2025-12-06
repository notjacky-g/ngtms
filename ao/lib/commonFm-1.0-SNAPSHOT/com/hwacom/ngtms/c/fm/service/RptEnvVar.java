/*    */ package com.hwacom.ngtms.c.fm.service;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class RptEnvVar
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -8861910768979335390L;
/*    */   private Boolean usingRptServiceRemotePolicy;
/*    */   private String exportFileDirectory;
/*    */   private String absoluteResourcePath;
/*    */   private String resourceBaseUri;
/*    */   private String bulkProcessServiceUri;
/*    */   
/*    */   public Boolean getUsingRptServiceRemotePolicy()
/*    */   {
/* 24 */     return this.usingRptServiceRemotePolicy;
/*    */   }
/*    */   
/*    */   public void setUsingRptServiceRemotePolicy(Boolean usingRptServiceRemotePolicy) {
/* 28 */     this.usingRptServiceRemotePolicy = usingRptServiceRemotePolicy;
/*    */   }
/*    */   
/*    */   public String getExportFileDirectory() {
/* 32 */     return this.exportFileDirectory;
/*    */   }
/*    */   
/*    */   public void setExportFileDirectory(String exportFileDirectory) {
/* 36 */     this.exportFileDirectory = exportFileDirectory;
/*    */   }
/*    */   
/*    */   public String getAbsoluteResourcePath() {
/* 40 */     return this.absoluteResourcePath;
/*    */   }
/*    */   
/*    */   public void setAbsoluteResourcePath(String absoluteResourcePath) {
/* 44 */     this.absoluteResourcePath = absoluteResourcePath;
/*    */   }
/*    */   
/*    */   public String getResourceBaseUri() {
/* 48 */     return this.resourceBaseUri;
/*    */   }
/*    */   
/*    */   public void setResourceBaseUri(String resourceBaseUri) {
/* 52 */     this.resourceBaseUri = resourceBaseUri;
/*    */   }
/*    */   
/*    */   public String getBulkProcessServiceUri() {
/* 56 */     return this.bulkProcessServiceUri;
/*    */   }
/*    */   
/*    */   public void setBulkProcessServiceUri(String bulkProcessServiceUri) {
/* 60 */     this.bulkProcessServiceUri = bulkProcessServiceUri;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 65 */     return "RptEnvVar [usingRptServiceRemotePolicy=" + this.usingRptServiceRemotePolicy + ", exportFileDirectory=" + this.exportFileDirectory + ", absoluteResourcePath=" + this.absoluteResourcePath + ", resourceBaseUri=" + this.resourceBaseUri + ", bulkProcessServiceUri=" + this.bulkProcessServiceUri + "]";
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\service\RptEnvVar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */