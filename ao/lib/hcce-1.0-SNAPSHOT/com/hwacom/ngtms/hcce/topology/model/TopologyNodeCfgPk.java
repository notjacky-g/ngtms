/*    */ package com.hwacom.ngtms.hcce.topology.model;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ public class TopologyNodeCfgPk
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String groupName;
/*    */   private String nodeName;
/*    */   
/*    */   public TopologyNodeCfgPk() {}
/*    */   
/*    */   public TopologyNodeCfgPk(String groupName, String nodeName)
/*    */   {
/* 26 */     this.groupName = groupName;
/* 27 */     this.nodeName = nodeName;
/*    */   }
/*    */   
/*    */   public String getGroupName() {
/* 31 */     return this.groupName;
/*    */   }
/*    */   
/*    */   public void setGroupName(String groupName) {
/* 35 */     this.groupName = groupName;
/*    */   }
/*    */   
/*    */   public String getNodeName() {
/* 39 */     return this.nodeName;
/*    */   }
/*    */   
/*    */   public void setNodeName(String nodeName) {
/* 43 */     this.nodeName = nodeName;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 48 */     int prime = 31;
/* 49 */     int result = 1;
/* 50 */     result = 31 * result + (this.groupName == null ? 0 : this.groupName.hashCode());
/* 51 */     result = 31 * result + (this.nodeName == null ? 0 : this.nodeName.hashCode());
/* 52 */     return result;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj)
/*    */   {
/* 57 */     if (this == obj) return true;
/* 58 */     if (obj == null) return false;
/* 59 */     if (getClass() != obj.getClass()) return false;
/* 60 */     TopologyNodeCfgPk other = (TopologyNodeCfgPk)obj;
/* 61 */     if (this.groupName == null) {
/* 62 */       if (other.groupName != null) return false;
/* 63 */     } else if (!this.groupName.equals(other.groupName)) return false;
/* 64 */     if (this.nodeName == null) {
/* 65 */       if (other.nodeName != null) return false;
/* 66 */     } else if (!this.nodeName.equals(other.nodeName)) return false;
/* 67 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\topology\model\TopologyNodeCfgPk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */