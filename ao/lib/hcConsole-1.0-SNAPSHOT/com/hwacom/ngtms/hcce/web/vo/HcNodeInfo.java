/*    */ package com.hwacom.ngtms.hcce.web.vo;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.core.NodeManager.NmState;
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HcNodeInfo
/*    */ {
/*    */   private String groupName;
/*    */   private String nodeName;
/*    */   private String version;
/*    */   private String revision;
/*    */   private String title;
/*    */   private String issueDate;
/*    */   private Date startTime;
/*    */   private NodeManager.NmState nmState;
/*    */   
/*    */   public String getGroupName()
/*    */   {
/* 24 */     return this.groupName;
/*    */   }
/*    */   
/*    */   public void setGroupName(String groupName) {
/* 28 */     this.groupName = groupName;
/*    */   }
/*    */   
/*    */   public String getNodeName() {
/* 32 */     return this.nodeName;
/*    */   }
/*    */   
/*    */   public void setNodeName(String nodeName) {
/* 36 */     this.nodeName = nodeName;
/*    */   }
/*    */   
/*    */   public String getVersion() {
/* 40 */     return this.version;
/*    */   }
/*    */   
/*    */   public void setVersion(String version) {
/* 44 */     this.version = version;
/*    */   }
/*    */   
/*    */   public String getIssueDate() {
/* 48 */     return this.issueDate;
/*    */   }
/*    */   
/*    */   public void setIssueDate(String issueDate) {
/* 52 */     this.issueDate = issueDate;
/*    */   }
/*    */   
/*    */   public NodeManager.NmState getNmState() {
/* 56 */     return this.nmState;
/*    */   }
/*    */   
/*    */   public void setNmState(NodeManager.NmState nmState) {
/* 60 */     this.nmState = nmState;
/*    */   }
/*    */   
/*    */   public Date getStartTime() {
/* 64 */     return this.startTime;
/*    */   }
/*    */   
/*    */   public void setStartTime(Date startTime) {
/* 68 */     this.startTime = startTime;
/*    */   }
/*    */   
/*    */   public String getRevision() {
/* 72 */     return this.revision;
/*    */   }
/*    */   
/*    */   public void setRevision(String revision) {
/* 76 */     this.revision = revision;
/*    */   }
/*    */   
/*    */   public String getTitle() {
/* 80 */     return this.title;
/*    */   }
/*    */   
/*    */   public void setTitle(String title) {
/* 84 */     this.title = title;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 89 */     return "HcNodeInfo [groupName=" + this.groupName + ", nodeName=" + this.nodeName + ", version=" + this.version + ", revision=" + this.revision + ", title=" + this.title + ", issueDate=" + this.issueDate + ", startTime=" + this.startTime + ", nmState=" + this.nmState + "]";
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcConsole-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\web\vo\HcNodeInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */