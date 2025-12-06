/*     */ package com.hwacom.ngtms.c.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.c.shared.MfccStatus;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.EnumType;
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
/*     */ public class MfccConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4568908500326080895L;
/*     */   @Id
/*     */   @Column(unique = true, nullable = false, length = 20)
/*     */   @Comment("MFCC軟體編號")
/*     */   private String mfccId;
/*     */   @Column(length = 20)
/*     */   @Comment("MFCC軟體名稱")
/*     */   private String mfccName;
/*     */   @Column(nullable = false, length = 20)
/*     */   @Comment("所屬主機編號")
/*     */   private String hostId;
/*     */   @Column(length = 50)
/*     */   @Comment("備註說明")
/*     */   private String memo;
/*     */   @Enumerated(EnumType.ORDINAL)
/*     */   @Comment("MFCC設備狀態")
/*     */   private MfccStatus status;
/*     */   @Column(length = 50)
/*     */   @Comment("此欄位須跟hcce_group_info裡面的group_name對照")
/*     */   private String groupName;
/*     */   
/*     */   public MfccConfig() {}
/*     */   
/*     */   public MfccConfig(String mfccId, String hostId) {
/*  57 */     this.mfccId = mfccId;
/*  58 */     this.hostId = hostId;
/*     */   }
/*     */   
/*     */   public MfccConfig(String mfccId, String mfccName, String hostId, String memo, MfccStatus status) {
/*  62 */     this.mfccId = mfccId;
/*  63 */     this.mfccName = mfccName;
/*  64 */     this.hostId = hostId;
/*  65 */     this.memo = memo;
/*  66 */     this.status = status;
/*     */   }
/*     */   
/*     */   public String getMfccId() {
/*  70 */     return this.mfccId;
/*     */   }
/*     */   
/*     */   public void setMfccId(String mfccId) {
/*  74 */     this.mfccId = mfccId;
/*     */   }
/*     */   
/*     */   public String getMfccName() {
/*  78 */     return this.mfccName;
/*     */   }
/*     */   
/*     */   public void setMfccName(String mfccName) {
/*  82 */     this.mfccName = mfccName;
/*     */   }
/*     */   
/*     */   public String getHostId() {
/*  86 */     return this.hostId;
/*     */   }
/*     */   
/*     */   public void setHostId(String hostId) {
/*  90 */     this.hostId = hostId;
/*     */   }
/*     */   
/*     */   public String getMemo() {
/*  94 */     return this.memo;
/*     */   }
/*     */   
/*     */   public void setMemo(String memo) {
/*  98 */     this.memo = memo;
/*     */   }
/*     */   
/*     */   public MfccStatus getStatus() {
/* 102 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(MfccStatus status) {
/* 106 */     this.status = status;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 111 */     int hash = 0;
/* 112 */     hash += (this.mfccId != null) ? this.mfccId.hashCode() : 0;
/* 113 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/* 119 */     if (!(object instanceof MfccConfig)) {
/* 120 */       return false;
/*     */     }
/* 122 */     MfccConfig other = (MfccConfig)object;
/* 123 */     if ((this.mfccId == null && other.mfccId != null) || (this.mfccId != null && 
/* 124 */       !this.mfccId.equals(other.mfccId))) {
/* 125 */       return false;
/*     */     }
/* 127 */     return true;
/*     */   }
/*     */   
/*     */   public String getGroupName() {
/* 131 */     return this.groupName;
/*     */   }
/*     */   
/*     */   public void setGroupName(String groupName) {
/* 135 */     this.groupName = groupName;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\MfccConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */