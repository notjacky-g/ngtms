/*     */ package com.hwacom.ngtms.hcce.fme.manager.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.IdClass;
/*     */ import javax.persistence.Transient;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ @IdClass(FmeDefinitionPk.class)
/*     */ public class FmeDefinition
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   @Id
/*     */   @Column(length=50, nullable=false)
/*     */   @Comment("Cluster 群組名稱")
/*     */   private String groupName;
/*     */   @Id
/*     */   @Column(length=50)
/*     */   @Comment("FME 名稱")
/*     */   private String fmeName;
/*     */   @Column(length=100, nullable=false)
/*     */   @Comment("FME 完整 Class 名稱")
/*     */   private String className;
/*     */   @Column(length=255)
/*     */   @Comment("FME 節點執行次序，節點名稱")
/*     */   private String nodePriority;
/*     */   @Transient
/*     */   private List<ProhibitNode> prohibitNodes;
/*     */   @Column(nullable=false)
/*     */   @Comment("是否啟用")
/*     */   private Boolean enable;
/*     */   @Column(length=255)
/*     */   @Comment("說明")
/*     */   private String description;
/*     */   
/*     */   public String getGroupName()
/*     */   {
/*  57 */     return this.groupName;
/*     */   }
/*     */   
/*     */   public void setGroupName(String groupName) {
/*  61 */     this.groupName = groupName;
/*     */   }
/*     */   
/*     */   public String getFmeName() {
/*  65 */     return this.fmeName;
/*     */   }
/*     */   
/*     */   public void setFmeName(String fmeName) {
/*  69 */     this.fmeName = fmeName;
/*     */   }
/*     */   
/*     */   public List<ProhibitNode> getProhibitNodes() {
/*  73 */     return this.prohibitNodes;
/*     */   }
/*     */   
/*     */   public void setProhibitNodes(List<ProhibitNode> prohibitNodes) {
/*  77 */     this.prohibitNodes = prohibitNodes;
/*     */   }
/*     */   
/*     */   public Boolean getEnable() {
/*  81 */     return this.enable;
/*     */   }
/*     */   
/*     */   public void setEnable(Boolean enable) {
/*  85 */     this.enable = enable;
/*     */   }
/*     */   
/*     */   public String getNodePriority() {
/*  89 */     return this.nodePriority;
/*     */   }
/*     */   
/*     */   public void setNodePriority(String nodePriority) {
/*  93 */     this.nodePriority = nodePriority;
/*     */   }
/*     */   
/*     */   public String getClassName() {
/*  97 */     return this.className;
/*     */   }
/*     */   
/*     */   public void setClassName(String className) {
/* 101 */     this.className = className;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 105 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(String description) {
/* 109 */     this.description = description;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 114 */     int prime = 31;
/* 115 */     int result = 1;
/* 116 */     result = 31 * result + (this.fmeName == null ? 0 : this.fmeName.hashCode());
/* 117 */     result = 31 * result + (this.groupName == null ? 0 : this.groupName.hashCode());
/* 118 */     return result;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 123 */     if (this == obj) return true;
/* 124 */     if (obj == null) return false;
/* 125 */     if (getClass() != obj.getClass()) return false;
/* 126 */     FmeDefinition other = (FmeDefinition)obj;
/* 127 */     if (this.fmeName == null) {
/* 128 */       if (other.fmeName != null) return false;
/* 129 */     } else if (!this.fmeName.equals(other.fmeName)) return false;
/* 130 */     if (this.groupName == null) {
/* 131 */       if (other.groupName != null) return false;
/* 132 */     } else if (!this.groupName.equals(other.groupName)) return false;
/* 133 */     return true;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 138 */     return "FmeDefinition [groupName=" + this.groupName + ", fmeName=" + this.fmeName + ", className=" + this.className + ", nodePriority=" + this.nodePriority + ", prohibitNodes=" + this.prohibitNodes + ", enable=" + this.enable + ", description=" + this.description + "]";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FmeDefinitionPk getPk()
/*     */   {
/* 156 */     return new FmeDefinitionPk(this.groupName, this.fmeName);
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\manager\model\FmeDefinition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */