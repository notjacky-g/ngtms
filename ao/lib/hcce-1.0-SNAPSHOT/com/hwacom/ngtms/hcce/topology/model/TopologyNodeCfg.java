/*     */ package com.hwacom.ngtms.hcce.topology.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.IdClass;
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
/*     */ @IdClass(TopologyNodeCfgPk.class)
/*     */ public class TopologyNodeCfg
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   @Id
/*     */   @Column(length=50)
/*     */   @Comment("Cluster 群組名稱")
/*     */   private String groupName;
/*     */   @Id
/*     */   @Column(length=50)
/*     */   @Comment("節點名稱")
/*     */   private String nodeName;
/*     */   @Column(length=255)
/*     */   @Comment("說明")
/*     */   private String description;
/*     */   
/*     */   public TopologyNodeCfg() {}
/*     */   
/*     */   public TopologyNodeCfg(String groupName, String nodeName, String description)
/*     */   {
/*  46 */     this.groupName = groupName;
/*  47 */     this.nodeName = nodeName;
/*  48 */     this.description = description;
/*     */   }
/*     */   
/*     */   public String getGroupName() {
/*  52 */     return this.groupName;
/*     */   }
/*     */   
/*     */   public void setGroupName(String groupName) {
/*  56 */     this.groupName = groupName;
/*     */   }
/*     */   
/*     */   public String getNodeName() {
/*  60 */     return this.nodeName;
/*     */   }
/*     */   
/*     */   public void setNodeName(String nodeName) {
/*  64 */     this.nodeName = nodeName;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  68 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(String description) {
/*  72 */     this.description = description;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/*  77 */     int prime = 31;
/*  78 */     int result = 1;
/*  79 */     result = 31 * result + (this.groupName == null ? 0 : this.groupName.hashCode());
/*  80 */     result = 31 * result + (this.nodeName == null ? 0 : this.nodeName.hashCode());
/*  81 */     return result;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/*  86 */     if (this == obj) return true;
/*  87 */     if (obj == null) return false;
/*  88 */     if (getClass() != obj.getClass()) return false;
/*  89 */     TopologyNodeCfg other = (TopologyNodeCfg)obj;
/*  90 */     if (this.groupName == null) {
/*  91 */       if (other.groupName != null) return false;
/*  92 */     } else if (!this.groupName.equals(other.groupName)) return false;
/*  93 */     if (this.nodeName == null) {
/*  94 */       if (other.nodeName != null) return false;
/*  95 */     } else if (!this.nodeName.equals(other.nodeName)) return false;
/*  96 */     return true;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 101 */     return "TopologyNodeCfg [groupName=" + this.groupName + ", nodeName=" + this.nodeName + ", description=" + this.description + "]";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TopologyNodeCfgPk getPk()
/*     */   {
/* 111 */     return new TopologyNodeCfgPk(this.groupName, this.nodeName);
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\topology\model\TopologyNodeCfg.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */