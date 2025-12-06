/*     */ package com.hwacom.ngtms.common.fm.model;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Temporal;
/*     */ import javax.persistence.TemporalType;
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
/*     */ public class FunctionPermission
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -428301738261044210L;
/*     */   @Id
/*     */   @Comment("功能 unique Id")
/*     */   private String id;
/*     */   @Comment("名稱")
/*     */   private String name;
/*     */   @Comment("備註")
/*     */   private String description;
/*     */   @Comment("父身份Id")
/*     */   private String parentId;
/*     */   @Comment("層")
/*     */   private Integer level;
/*     */   @Comment("網址映射")
/*     */   private String urlMapping;
/*     */   @Comment("序列")
/*     */   private Integer sequence;
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   @Comment("更新時間")
/*     */   private Date updateTime;
/*     */   @Comment("啟用")
/*     */   private Boolean enable;
/*     */   
/*     */   public String getId() {
/*  54 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setId(String id) {
/*  59 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  64 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/*  69 */     this.name = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  74 */     return this.description;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDescription(String description) {
/*  79 */     this.description = description;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getParentId() {
/*  84 */     return this.parentId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParentId(String parentId) {
/*  89 */     this.parentId = parentId;
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getUpdateTime() {
/*  94 */     return this.updateTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUpdateTime(Date updateTime) {
/*  99 */     this.updateTime = updateTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getLevel() {
/* 104 */     return this.level;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLevel(Integer level) {
/* 109 */     this.level = level;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUrlMapping() {
/* 114 */     return this.urlMapping;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUrlMapping(String urlMapping) {
/* 119 */     this.urlMapping = urlMapping;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getSequence() {
/* 124 */     return this.sequence;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSequence(Integer sequence) {
/* 129 */     this.sequence = sequence;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean getEnable() {
/* 134 */     return this.enable;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnable(Boolean enable) {
/* 139 */     this.enable = enable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 147 */     int prime = 31;
/* 148 */     int result = 1;
/* 149 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 150 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 158 */     if (this == obj) return true; 
/* 159 */     if (obj == null) return false; 
/* 160 */     if (getClass() != obj.getClass()) return false; 
/* 161 */     FunctionPermission other = (FunctionPermission)obj;
/* 162 */     if (this.id == null)
/* 163 */     { if (other.id != null) return false;  }
/* 164 */     else if (!this.id.equals(other.id)) { return false; }
/* 165 */      return true;
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
/*     */   public String toString() {
/* 180 */     return MoreObjects.toStringHelper(this).add("id", this.id).add("name", this.name).add("description", this.description).add("parentId", this.parentId).add("level", this.level).add("urlMapping", this.urlMapping).add("sequence", this.sequence).add("updateTime", this.updateTime).add("enable", this.enable).toString();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\model\FunctionPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */