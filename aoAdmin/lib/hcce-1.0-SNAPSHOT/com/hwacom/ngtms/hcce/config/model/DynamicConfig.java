/*     */ package com.hwacom.ngtms.hcce.config.model;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ @IdClass(DynamicConfigPk.class)
/*     */ public class DynamicConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   @Id
/*     */   @Column(length = 50, nullable = false)
/*     */   @Comment("Cluster 群組名稱")
/*     */   private String groupName;
/*     */   @Id
/*     */   @Column(length = 50, nullable = false)
/*     */   @Comment("FME 名稱")
/*     */   private String fmeName;
/*     */   @Id
/*     */   @Column(length = 50, nullable = false)
/*     */   @Comment("組態名稱")
/*     */   private String name;
/*     */   @Column(length = 255)
/*     */   @Comment("組態值")
/*     */   private String value;
/*     */   
/*     */   public DynamicConfig() {}
/*     */   
/*     */   public DynamicConfig(String groupName, String fmeName, String name, String value) {
/*  52 */     this.groupName = groupName;
/*  53 */     this.fmeName = fmeName;
/*  54 */     this.name = name;
/*  55 */     this.value = value;
/*     */   }
/*     */   
/*     */   public String getGroupName() {
/*  59 */     return this.groupName;
/*     */   }
/*     */   
/*     */   public void setGroupName(String groupName) {
/*  63 */     this.groupName = groupName;
/*     */   }
/*     */   
/*     */   public String getFmeName() {
/*  67 */     return this.fmeName;
/*     */   }
/*     */   
/*     */   public void setFmeName(String fmeName) {
/*  71 */     this.fmeName = fmeName;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  75 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  79 */     this.name = name;
/*     */   }
/*     */   
/*     */   public String getValue() {
/*  83 */     return this.value;
/*     */   }
/*     */   
/*     */   public void setValue(String value) {
/*  87 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  92 */     int prime = 31;
/*  93 */     int result = 1;
/*  94 */     result = 31 * result + ((this.fmeName == null) ? 0 : this.fmeName.hashCode());
/*  95 */     result = 31 * result + ((this.groupName == null) ? 0 : this.groupName.hashCode());
/*  96 */     result = 31 * result + ((this.name == null) ? 0 : this.name.hashCode());
/*  97 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 102 */     if (this == obj) return true; 
/* 103 */     if (obj == null) return false; 
/* 104 */     if (getClass() != obj.getClass()) return false; 
/* 105 */     DynamicConfig other = (DynamicConfig)obj;
/* 106 */     if (this.fmeName == null)
/* 107 */     { if (other.fmeName != null) return false;  }
/* 108 */     else if (!this.fmeName.equals(other.fmeName)) { return false; }
/* 109 */      if (this.groupName == null)
/* 110 */     { if (other.groupName != null) return false;  }
/* 111 */     else if (!this.groupName.equals(other.groupName)) { return false; }
/* 112 */      if (this.name == null)
/* 113 */     { if (other.name != null) return false;  }
/* 114 */     else if (!this.name.equals(other.name)) { return false; }
/* 115 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 120 */     return "DynamicConfig [groupName=" + this.groupName + ", fmeName=" + this.fmeName + ", name=" + this.name + ", value=" + this.value + "]";
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
/*     */   public DynamicConfigPk getPk() {
/* 132 */     return new DynamicConfigPk(this.groupName, this.fmeName, this.name);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\config\model\DynamicConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */