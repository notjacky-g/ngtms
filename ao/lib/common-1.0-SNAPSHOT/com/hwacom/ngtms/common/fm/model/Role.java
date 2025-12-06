/*     */ package com.hwacom.ngtms.common.fm.model;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.base.MoreObjects.ToStringHelper;
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import javax.persistence.ElementCollection;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.FetchType;
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
/*     */ @Entity
/*     */ public class Role
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8379462897914642933L;
/*     */   @Id
/*     */   @Comment("角色名稱")
/*     */   private String name;
/*     */   @Comment("備註")
/*     */   private String description;
/*     */   @ElementCollection(fetch=FetchType.EAGER)
/*  37 */   private Set<String> function_permissions = new HashSet();
/*     */   
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   @Comment("更新時間")
/*     */   private Date updateTime;
/*     */   
/*     */   @Comment("是否啟用")
/*     */   private Boolean enable;
/*     */   
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     int hash = 0;
/*  50 */     hash += (this.name != null ? this.name.hashCode() : 0);
/*  51 */     return hash;
/*     */   }
/*     */   
/*     */   public boolean equals(Object object)
/*     */   {
/*  56 */     if (!(object instanceof Role)) {
/*  57 */       return false;
/*     */     }
/*  59 */     Role other = (Role)object;
/*  60 */     if (((this.name == null) && (other.name != null)) || ((this.name != null) && 
/*  61 */       (!this.name.equals(other.name)))) {
/*  62 */       return false;
/*     */     }
/*  64 */     return true;
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/*  69 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name)
/*     */   {
/*  74 */     this.name = name;
/*     */   }
/*     */   
/*     */   public String getDescription()
/*     */   {
/*  79 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(String description)
/*     */   {
/*  84 */     this.description = description;
/*     */   }
/*     */   
/*     */   public Date getUpdateTime()
/*     */   {
/*  89 */     return this.updateTime;
/*     */   }
/*     */   
/*     */   public void setUpdateTime(Date updateTime)
/*     */   {
/*  94 */     this.updateTime = updateTime;
/*     */   }
/*     */   
/*     */   public Set<String> getFunctionPermissions()
/*     */   {
/*  99 */     return this.function_permissions;
/*     */   }
/*     */   
/*     */   public void setFunctionPermissions(Set<String> functionPermissions)
/*     */   {
/* 104 */     this.function_permissions = functionPermissions;
/*     */   }
/*     */   
/*     */   public void addFunctionPermission(FunctionPermission functionPermission) {
/* 108 */     this.function_permissions.add(functionPermission.getId());
/*     */   }
/*     */   
/*     */   public Boolean getEnable()
/*     */   {
/* 113 */     return this.enable;
/*     */   }
/*     */   
/*     */   public void setEnable(Boolean enable)
/*     */   {
/* 118 */     this.enable = enable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 129 */     return MoreObjects.toStringHelper(this).add("name", this.name).add("description", this.description).add("function_permissions", this.function_permissions).add("updateTime", this.updateTime).add("enable", this.enable).toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\model\Role.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */