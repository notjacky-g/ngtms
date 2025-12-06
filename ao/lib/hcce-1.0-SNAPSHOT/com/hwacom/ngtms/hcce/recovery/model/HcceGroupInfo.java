/*     */ package com.hwacom.ngtms.hcce.recovery.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.hcce.shared.ClusterMode;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Enumerated;
/*     */ import javax.persistence.Id;
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
/*     */ public class HcceGroupInfo
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2627100729488915157L;
/*     */   @Id
/*     */   @Column(length=50)
/*     */   @Comment("Cluster 群組名稱")
/*     */   private String groupName;
/*     */   @Comment("可否變成 Active 模式")
/*     */   private boolean allowActive;
/*     */   @Enumerated
/*     */   @Column(nullable=false)
/*     */   @Comment("Cluster 狀態")
/*     */   private ClusterMode clusterMode;
/*     */   @Column(length=255)
/*     */   @Comment("說明")
/*     */   private String description;
/*     */   
/*     */   public HcceGroupInfo() {}
/*     */   
/*     */   public HcceGroupInfo(String groupName, boolean allowActive, ClusterMode clusterMode, String description)
/*     */   {
/*  42 */     this.groupName = groupName;
/*  43 */     this.allowActive = allowActive;
/*  44 */     this.clusterMode = clusterMode;
/*  45 */     this.description = description;
/*     */   }
/*     */   
/*     */   public String getGroupName() {
/*  49 */     return this.groupName;
/*     */   }
/*     */   
/*     */   public void setGroupName(String groupName) {
/*  53 */     this.groupName = groupName;
/*     */   }
/*     */   
/*     */   public ClusterMode getClusterMode() {
/*  57 */     return this.clusterMode;
/*     */   }
/*     */   
/*     */   public void setClusterMode(ClusterMode clusterMode) {
/*  61 */     this.clusterMode = clusterMode;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  65 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(String description) {
/*  69 */     this.description = description;
/*     */   }
/*     */   
/*     */   public boolean isAllowActive() {
/*  73 */     return this.allowActive;
/*     */   }
/*     */   
/*     */   public void setAllowActive(boolean allowActive) {
/*  77 */     this.allowActive = allowActive;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  82 */     return "HcceGroupInfo [groupName=" + this.groupName + ", allowActive=" + this.allowActive + ", clusterMode=" + this.clusterMode + ", description=" + this.description + "]";
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
/*     */   public int hashCode()
/*     */   {
/*  95 */     int prime = 31;
/*  96 */     int result = 1;
/*  97 */     result = 31 * result + (this.allowActive ? 1231 : 1237);
/*  98 */     result = 31 * result + (this.clusterMode == null ? 0 : this.clusterMode.hashCode());
/*  99 */     result = 31 * result + (this.description == null ? 0 : this.description.hashCode());
/* 100 */     result = 31 * result + (this.groupName == null ? 0 : this.groupName.hashCode());
/* 101 */     return result;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 106 */     if (this == obj) return true;
/* 107 */     if (obj == null) return false;
/* 108 */     if (getClass() != obj.getClass()) return false;
/* 109 */     HcceGroupInfo other = (HcceGroupInfo)obj;
/* 110 */     if (this.allowActive != other.allowActive) return false;
/* 111 */     if (this.clusterMode != other.clusterMode) return false;
/* 112 */     if (this.description == null) {
/* 113 */       if (other.description != null) return false;
/* 114 */     } else if (!this.description.equals(other.description)) return false;
/* 115 */     if (this.groupName == null) {
/* 116 */       if (other.groupName != null) return false;
/* 117 */     } else if (!this.groupName.equals(other.groupName)) return false;
/* 118 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\recovery\model\HcceGroupInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */