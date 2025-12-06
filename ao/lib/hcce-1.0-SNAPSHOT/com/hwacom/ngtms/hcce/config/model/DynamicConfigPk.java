/*    */ package com.hwacom.ngtms.hcce.config.model;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DynamicConfigPk
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String groupName;
/*    */   private String fmeName;
/*    */   private String name;
/*    */   
/*    */   public DynamicConfigPk() {}
/*    */   
/*    */   public DynamicConfigPk(String groupName, String fmeName, String name)
/*    */   {
/* 31 */     this.groupName = groupName;
/* 32 */     this.fmeName = fmeName;
/* 33 */     this.name = name;
/*    */   }
/*    */   
/*    */   public String getGroupName() {
/* 37 */     return this.groupName;
/*    */   }
/*    */   
/*    */   public void setGroupName(String groupName) {
/* 41 */     this.groupName = groupName;
/*    */   }
/*    */   
/*    */   public String getFmeName() {
/* 45 */     return this.fmeName;
/*    */   }
/*    */   
/*    */   public void setFmeName(String fmeName) {
/* 49 */     this.fmeName = fmeName;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 53 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 57 */     this.name = name;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 62 */     int prime = 31;
/* 63 */     int result = 1;
/* 64 */     result = 31 * result + (this.fmeName == null ? 0 : this.fmeName.hashCode());
/* 65 */     result = 31 * result + (this.groupName == null ? 0 : this.groupName.hashCode());
/* 66 */     result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
/* 67 */     return result;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj)
/*    */   {
/* 72 */     if (this == obj) return true;
/* 73 */     if (obj == null) return false;
/* 74 */     if (getClass() != obj.getClass()) return false;
/* 75 */     DynamicConfigPk other = (DynamicConfigPk)obj;
/* 76 */     if (this.fmeName == null) {
/* 77 */       if (other.fmeName != null) return false;
/* 78 */     } else if (!this.fmeName.equals(other.fmeName)) return false;
/* 79 */     if (this.groupName == null) {
/* 80 */       if (other.groupName != null) return false;
/* 81 */     } else if (!this.groupName.equals(other.groupName)) return false;
/* 82 */     if (this.name == null) {
/* 83 */       if (other.name != null) return false;
/* 84 */     } else if (!this.name.equals(other.name)) return false;
/* 85 */     return true;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 90 */     return "DynamicConfigPk [groupName=" + this.groupName + ", fmeName=" + this.fmeName + ", name=" + this.name + "]";
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\config\model\DynamicConfigPk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */