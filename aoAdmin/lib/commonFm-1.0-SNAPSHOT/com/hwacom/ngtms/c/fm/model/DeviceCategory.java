/*    */ package com.hwacom.ngtms.c.fm.model;
/*    */ 
/*    */ import com.hwacom.ngtms.base.annotation.Comment;
/*    */ import java.io.Serializable;
/*    */ import javax.persistence.Column;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.Id;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Entity
/*    */ public class DeviceCategory
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 871115560612150888L;
/*    */   @Id
/*    */   @Comment("設備分類名稱 (資收 = DGS, 資顯 = DIS, 交管 = TC 等) ")
/*    */   private String id;
/*    */   @Comment("設備分類中文描述 ")
/*    */   @Column(length = 40)
/*    */   private String description;
/*    */   @Comment("上層設備分類名稱")
/*    */   @Column(length = 40)
/*    */   private String parentId;
/*    */   
/*    */   public String getId() {
/* 30 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 34 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 38 */     return this.description;
/*    */   }
/*    */   
/*    */   public void setDescription(String description) {
/* 42 */     this.description = description;
/*    */   }
/*    */   
/*    */   public String getParentId() {
/* 46 */     return this.parentId;
/*    */   }
/*    */   
/*    */   public void setParentId(String parentId) {
/* 50 */     this.parentId = parentId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 55 */     int prime = 31;
/* 56 */     int result = 1;
/* 57 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 58 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 63 */     if (this == obj) return true; 
/* 64 */     if (obj == null) return false; 
/* 65 */     if (getClass() != obj.getClass()) return false; 
/* 66 */     DeviceCategory other = (DeviceCategory)obj;
/* 67 */     if (this.id == null)
/* 68 */     { if (other.id != null) return false;  }
/* 69 */     else if (!this.id.equals(other.id)) { return false; }
/* 70 */      return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 75 */     return "DeviceCategory [id=" + this.id + ", description=" + this.description + "]";
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\DeviceCategory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */