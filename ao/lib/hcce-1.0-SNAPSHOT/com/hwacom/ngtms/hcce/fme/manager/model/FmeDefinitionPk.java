/*    */ package com.hwacom.ngtms.hcce.fme.manager.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FmeDefinitionPk
/*    */   implements Serializable, Comparable<FmeDefinitionPk>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String groupName;
/*    */   private String fmeName;
/*    */   
/*    */   public FmeDefinitionPk() {}
/*    */   
/*    */   public FmeDefinitionPk(String groupName, String fmeName)
/*    */   {
/* 19 */     this.groupName = groupName;
/* 20 */     this.fmeName = fmeName;
/*    */   }
/*    */   
/*    */   public String getGroupName() {
/* 24 */     return this.groupName;
/*    */   }
/*    */   
/*    */   public void setGroupName(String groupName) {
/* 28 */     this.groupName = groupName;
/*    */   }
/*    */   
/*    */   public String getFmeName() {
/* 32 */     return this.fmeName;
/*    */   }
/*    */   
/*    */   public void setFmeName(String fmeName) {
/* 36 */     this.fmeName = fmeName;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 41 */     int prime = 31;
/* 42 */     int result = 1;
/* 43 */     result = 31 * result + (this.fmeName == null ? 0 : this.fmeName.hashCode());
/* 44 */     result = 31 * result + (this.groupName == null ? 0 : this.groupName.hashCode());
/* 45 */     return result;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj)
/*    */   {
/* 50 */     if (this == obj) return true;
/* 51 */     if (obj == null) return false;
/* 52 */     if (getClass() != obj.getClass()) return false;
/* 53 */     FmeDefinitionPk other = (FmeDefinitionPk)obj;
/* 54 */     if (this.fmeName == null) {
/* 55 */       if (other.fmeName != null) return false;
/* 56 */     } else if (!this.fmeName.equals(other.fmeName)) return false;
/* 57 */     if (this.groupName == null) {
/* 58 */       if (other.groupName != null) return false;
/* 59 */     } else if (!this.groupName.equals(other.groupName)) return false;
/* 60 */     return true;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 65 */     return "FmeDefinitionPk [groupName=" + this.groupName + ", fmeName=" + this.fmeName + "]";
/*    */   }
/*    */   
/*    */   public int compareTo(FmeDefinitionPk other)
/*    */   {
/* 70 */     int x = this.groupName.compareTo(other.groupName);
/*    */     
/* 72 */     if (x == 0) {
/* 73 */       if (this.fmeName == null) {
/* 74 */         if (other.fmeName != null) {
/* 75 */           x = -1;
/*    */         }
/* 77 */       } else if (other.fmeName == null) {
/* 78 */         x = 1;
/*    */       } else {
/* 80 */         x = this.fmeName.compareTo(other.fmeName);
/*    */       }
/*    */     }
/* 83 */     return x;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\manager\model\FmeDefinitionPk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */