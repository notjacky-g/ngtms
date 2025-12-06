/*     */ package com.hwacom.ngtms.hcce.fme.manager.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.GenerationType;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
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
/*     */ @Table(indexes={@javax.persistence.Index(columnList="group_name"), @javax.persistence.Index(columnList="fme_name")})
/*     */ public class ProhibitNode
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   @Id
/*     */   @GeneratedValue(strategy=GenerationType.IDENTITY)
/*     */   @Comment("流水號")
/*     */   private Long id;
/*     */   @Column(length=50, nullable=false, name="group_name")
/*     */   @Comment("Cluster 群組名稱")
/*     */   private String groupName;
/*     */   @Column(length=50, nullable=false, name="fme_name")
/*     */   @Comment("FME 名稱")
/*     */   private String fmeName;
/*     */   @Column(length=50, nullable=false)
/*     */   @Comment("節點名稱")
/*     */   private String nodeName;
/*     */   @Column(length=255)
/*     */   @Comment("說明")
/*     */   private String description;
/*     */   
/*     */   public String toString()
/*     */   {
/*  51 */     return "ProhibitNode [id=" + this.id + ", groupName=" + this.groupName + ", fmeName=" + this.fmeName + ", nodeName=" + this.nodeName + ", description=" + this.description + "]";
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
/*     */   public int hashCode()
/*     */   {
/*  66 */     int prime = 31;
/*  67 */     int result = 1;
/*  68 */     result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
/*  69 */     return result;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/*  74 */     if (this == obj) return true;
/*  75 */     if (obj == null) return false;
/*  76 */     if (getClass() != obj.getClass()) return false;
/*  77 */     ProhibitNode other = (ProhibitNode)obj;
/*  78 */     if (this.id == null) {
/*  79 */       if (other.id != null) return false;
/*  80 */     } else if (!this.id.equals(other.id)) return false;
/*  81 */     return true;
/*     */   }
/*     */   
/*     */   public String getGroupName() {
/*  85 */     return this.groupName;
/*     */   }
/*     */   
/*     */   public void setGroupName(String groupName) {
/*  89 */     this.groupName = groupName;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  93 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(String description) {
/*  97 */     this.description = description;
/*     */   }
/*     */   
/*     */   public Long getId() {
/* 101 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Long id) {
/* 105 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getFmeName() {
/* 109 */     return this.fmeName;
/*     */   }
/*     */   
/*     */   public void setFmeName(String fmeName) {
/* 113 */     this.fmeName = fmeName;
/*     */   }
/*     */   
/*     */   public String getNodeName() {
/* 117 */     return this.nodeName;
/*     */   }
/*     */   
/*     */   public void setNodeName(String nodeName) {
/* 121 */     this.nodeName = nodeName;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\manager\model\ProhibitNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */