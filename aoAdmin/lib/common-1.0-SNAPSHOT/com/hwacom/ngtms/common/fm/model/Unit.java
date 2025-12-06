/*     */ package com.hwacom.ngtms.common.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Entity;
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
/*     */ public class Unit
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -9099192901631875091L;
/*     */   @Id
/*     */   @Comment("名稱")
/*     */   private String name;
/*     */   @Comment("全名")
/*     */   private String fullName;
/*     */   @Comment("負責人")
/*     */   private String manInCharge;
/*     */   @Comment("地址")
/*     */   private String address;
/*     */   @Comment("電子郵件")
/*     */   private String email;
/*     */   @Comment("電話")
/*     */   private String phone;
/*     */   @Comment("傳真")
/*     */   private String fax;
/*     */   @Comment("統一編號")
/*     */   private String taxId;
/*     */   @Comment("備註")
/*     */   private String description;
/*     */   @Comment("可否審核帳號")
/*     */   private Boolean haveAccountApprove;
/*     */   
/*     */   public String getName() {
/*  61 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  65 */     this.name = name;
/*     */   }
/*     */   
/*     */   public String getFullName() {
/*  69 */     return this.fullName;
/*     */   }
/*     */   
/*     */   public void setFullName(String fullName) {
/*  73 */     this.fullName = fullName;
/*     */   }
/*     */   
/*     */   public String getManInCharge() {
/*  77 */     return this.manInCharge;
/*     */   }
/*     */   
/*     */   public void setManInCharge(String manInCharge) {
/*  81 */     this.manInCharge = manInCharge;
/*     */   }
/*     */   
/*     */   public String getAddress() {
/*  85 */     return this.address;
/*     */   }
/*     */   
/*     */   public void setAddress(String address) {
/*  89 */     this.address = address;
/*     */   }
/*     */   
/*     */   public String getEmail() {
/*  93 */     return this.email;
/*     */   }
/*     */   
/*     */   public void setEmail(String email) {
/*  97 */     this.email = email;
/*     */   }
/*     */   
/*     */   public String getPhone() {
/* 101 */     return this.phone;
/*     */   }
/*     */   
/*     */   public void setPhone(String phone) {
/* 105 */     this.phone = phone;
/*     */   }
/*     */   
/*     */   public String getFax() {
/* 109 */     return this.fax;
/*     */   }
/*     */   
/*     */   public void setFax(String fax) {
/* 113 */     this.fax = fax;
/*     */   }
/*     */   
/*     */   public String getTaxId() {
/* 117 */     return this.taxId;
/*     */   }
/*     */   
/*     */   public void setTaxId(String taxId) {
/* 121 */     this.taxId = taxId;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 125 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(String description) {
/* 129 */     this.description = description;
/*     */   }
/*     */   
/*     */   public Boolean getHaveAccountApprove() {
/* 133 */     return this.haveAccountApprove;
/*     */   }
/*     */   
/*     */   public void setHaveAccountApprove(Boolean haveAccountApprove) {
/* 137 */     this.haveAccountApprove = haveAccountApprove;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 142 */     int prime = 31;
/* 143 */     int result = 1;
/* 144 */     result = 31 * result + ((this.name == null) ? 0 : this.name.hashCode());
/* 145 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 150 */     if (this == obj) return true; 
/* 151 */     if (obj == null) return false; 
/* 152 */     if (getClass() != obj.getClass()) return false; 
/* 153 */     Unit other = (Unit)obj;
/* 154 */     if (this.name == null)
/* 155 */     { if (other.name != null) return false;  }
/* 156 */     else if (!this.name.equals(other.name)) { return false; }
/* 157 */      return true;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\model\Unit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */