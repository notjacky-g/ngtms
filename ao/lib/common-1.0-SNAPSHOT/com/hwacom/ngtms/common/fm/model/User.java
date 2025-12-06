/*     */ package com.hwacom.ngtms.common.fm.model;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.base.MoreObjects.ToStringHelper;
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.base.annotation.NoOperationLog;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.ElementCollection;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.FetchType;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ @Table(name="user")
/*     */ public class User
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -947061226411094521L;
/*     */   @Id
/*     */   @Comment("帳號")
/*     */   private String login;
/*     */   @Column(nullable=false)
/*     */   @Comment("密碼1")
/*     */   @NoOperationLog
/*     */   private String pwd1;
/*     */   @Comment("是否修改過預設密碼")
/*     */   private Boolean pwdChanged;
/*     */   @Column
/*     */   @Comment("密碼2")
/*     */   @NoOperationLog
/*     */   private String pwd2;
/*     */   @Column
/*     */   @Comment("密碼3")
/*     */   @NoOperationLog
/*     */   private String pwd3;
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   @Comment("密碼最後修改時間")
/*     */   private Date lastPwdChangeTime;
/*     */   @Comment("雙重認證碼")
/*     */   @NoOperationLog
/*     */   private String mfaSecret;
/*     */   @Comment("名稱")
/*     */   private String name;
/*     */   @Comment("備註")
/*     */   private String description;
/*     */   @Comment("是否啟用")
/*     */   private Boolean enable;
/*     */   @Comment("手機號碼")
/*     */   private String mobile;
/*     */   @Comment("電子郵件")
/*     */   private String mail;
/*     */   @Temporal(TemporalType.DATE)
/*     */   @Comment("開始時間")
/*     */   private Date startTime;
/*     */   @Temporal(TemporalType.DATE)
/*     */   @Comment("時間結束")
/*     */   private Date endTime;
/*     */   @Comment("檢查過期")
/*     */   private Boolean checkExpired;
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   @Comment("更新時間")
/*     */   private Date updateTime;
/*     */   @ElementCollection(fetch=FetchType.EAGER)
/*     */   @Comment("角色名稱")
/*  94 */   private Set<String> roleNames = new HashSet();
/*     */   
/*     */   @ElementCollection(fetch=FetchType.EAGER)
/*     */   @Comment("服務單位名稱")
/*  98 */   private Set<String> unitNames = new HashSet();
/*     */   
/*     */ 
/*     */   @Comment("是否為系統帳號")
/*     */   private Boolean localAccount;
/*     */   
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   @Comment("最後登入時間")
/*     */   private Date lastLoginTime;
/*     */   
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 111 */     int hash = 0;
/* 112 */     hash += (this.login != null ? this.login.hashCode() : 0);
/* 113 */     return hash;
/*     */   }
/*     */   
/*     */   public boolean equals(Object object)
/*     */   {
/* 118 */     if (!(object instanceof User)) {
/* 119 */       return false;
/*     */     }
/* 121 */     User other = (User)object;
/* 122 */     if (((this.login == null) && (other.login != null)) || ((this.login != null) && 
/* 123 */       (!this.login.equals(other.login)))) {
/* 124 */       return false;
/*     */     }
/* 126 */     return true;
/*     */   }
/*     */   
/*     */   public String getLogin()
/*     */   {
/* 131 */     return this.login;
/*     */   }
/*     */   
/*     */   public void setLogin(String login)
/*     */   {
/* 136 */     this.login = login;
/*     */   }
/*     */   
/*     */   public String getPwd1()
/*     */   {
/* 141 */     return this.pwd1;
/*     */   }
/*     */   
/*     */   public void setPwd1(String pwd1)
/*     */   {
/* 146 */     if (pwd1 == null) {
/* 147 */       return;
/*     */     }
/* 149 */     String prefix = "{SHA-256}";
/* 150 */     if (pwd1.startsWith(prefix)) {
/* 151 */       this.pwd1 = pwd1;
/*     */     } else {
/* 153 */       this.pwd1 = (prefix + pwd1);
/*     */     }
/*     */   }
/*     */   
/*     */   public Boolean getPwdChanged() {
/* 158 */     return (Boolean)Optional.ofNullable(this.pwdChanged).orElse(Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   public void setPwdChanged(Boolean pwdChanged) {
/* 162 */     this.pwdChanged = pwdChanged;
/*     */   }
/*     */   
/*     */   public String getPwd2() {
/* 166 */     return this.pwd2;
/*     */   }
/*     */   
/*     */   public void setPwd2(String pwd2) {
/* 170 */     this.pwd2 = pwd2;
/*     */   }
/*     */   
/*     */   public String getPwd3() {
/* 174 */     return this.pwd3;
/*     */   }
/*     */   
/*     */   public void setPwd3(String pwd3) {
/* 178 */     this.pwd3 = pwd3;
/*     */   }
/*     */   
/*     */   public Date getLastPwdChangeTime() {
/* 182 */     return this.lastPwdChangeTime;
/*     */   }
/*     */   
/*     */   public void setLastPwdChangeTime(Date lastPwdChangeTime) {
/* 186 */     this.lastPwdChangeTime = lastPwdChangeTime;
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 191 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name)
/*     */   {
/* 196 */     this.name = name;
/*     */   }
/*     */   
/*     */   public String getMfaSecret() {
/* 200 */     return this.mfaSecret;
/*     */   }
/*     */   
/*     */   public void setMfaSecret(String mfaSecret) {
/* 204 */     this.mfaSecret = mfaSecret;
/*     */   }
/*     */   
/*     */   public String getDescription()
/*     */   {
/* 209 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(String description)
/*     */   {
/* 214 */     this.description = description;
/*     */   }
/*     */   
/*     */   public Date getStartTime()
/*     */   {
/* 219 */     return this.startTime;
/*     */   }
/*     */   
/*     */   public void setStartTime(Date startTime)
/*     */   {
/* 224 */     this.startTime = startTime;
/*     */   }
/*     */   
/*     */   public Date getEndTime()
/*     */   {
/* 229 */     return this.endTime;
/*     */   }
/*     */   
/*     */   public void setEndTime(Date endTime)
/*     */   {
/* 234 */     this.endTime = endTime;
/*     */   }
/*     */   
/*     */   public Boolean getCheckExpired()
/*     */   {
/* 239 */     return this.checkExpired;
/*     */   }
/*     */   
/*     */   public void setCheckExpired(Boolean checkExpired)
/*     */   {
/* 244 */     this.checkExpired = checkExpired;
/*     */   }
/*     */   
/*     */   public Date getUpdateTime()
/*     */   {
/* 249 */     return this.updateTime;
/*     */   }
/*     */   
/*     */   public void setUpdateTime(Date updateTime)
/*     */   {
/* 254 */     this.updateTime = updateTime;
/*     */   }
/*     */   
/*     */   public Set<String> getRoleNames()
/*     */   {
/* 259 */     return this.roleNames;
/*     */   }
/*     */   
/*     */   public void setRoleNames(Set<String> roles)
/*     */   {
/* 264 */     this.roleNames = roles;
/*     */   }
/*     */   
/*     */   public boolean addRole(Role role) {
/* 268 */     return this.roleNames.add(role.getName());
/*     */   }
/*     */   
/*     */   public Set<String> getUnitNames() {
/* 272 */     return this.unitNames;
/*     */   }
/*     */   
/*     */   public void setUnitNames(Set<String> units) {
/* 276 */     this.unitNames = units;
/*     */   }
/*     */   
/*     */   public boolean addUnit(Unit unit) {
/* 280 */     return this.unitNames.add(unit.getName());
/*     */   }
/*     */   
/*     */   public Boolean getEnable()
/*     */   {
/* 285 */     return this.enable;
/*     */   }
/*     */   
/*     */   public void setEnable(Boolean enable)
/*     */   {
/* 290 */     this.enable = enable;
/*     */   }
/*     */   
/*     */   public String getMobile() {
/* 294 */     return this.mobile;
/*     */   }
/*     */   
/*     */   public void setMobile(String mobile) {
/* 298 */     this.mobile = mobile;
/*     */   }
/*     */   
/*     */   public static long getSerialVersionUID() {
/* 302 */     return -947061226411094521L;
/*     */   }
/*     */   
/*     */   public String getMail() {
/* 306 */     return this.mail;
/*     */   }
/*     */   
/*     */   public void setMail(String eMail) {
/* 310 */     this.mail = eMail;
/*     */   }
/*     */   
/*     */   public Boolean getLocalAccount() {
/* 314 */     return (Boolean)Optional.ofNullable(this.localAccount).orElse(Boolean.valueOf(true));
/*     */   }
/*     */   
/*     */   public void setLocalAccount(Boolean localAccount) {
/* 318 */     this.localAccount = localAccount;
/*     */   }
/*     */   
/*     */   public Date getLastLoginTime() {
/* 322 */     return this.lastLoginTime;
/*     */   }
/*     */   
/*     */   public void setLastLoginTime(Date lastLoginTime) {
/* 326 */     this.lastLoginTime = lastLoginTime;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 347 */     return MoreObjects.toStringHelper(this).add("login", this.login).add("password", this.pwd1).add("name", this.name).add("description", this.description).add("enable", this.enable).add("mobile", this.mobile).add("mail", this.mail).add("startTime", this.startTime).add("endTime", this.endTime).add("checkExpired", this.checkExpired).add("updateTime", this.updateTime).add("roleNames", this.roleNames).add("unitNames", this.unitNames).add("localAccount", this.localAccount).add("lastLoginTime", this.lastLoginTime).toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\model\User.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */