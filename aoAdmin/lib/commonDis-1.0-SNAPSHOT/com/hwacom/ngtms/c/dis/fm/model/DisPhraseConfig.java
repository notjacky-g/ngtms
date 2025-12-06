/*     */ package com.hwacom.ngtms.c.dis.fm.model;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.base.Objects;
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.base.util.KeyUtils;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Index;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ @Table(indexes = {@Index(columnList = "order_no")})
/*     */ public class DisPhraseConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1789812920916151116L;
/*     */   @Id
/*     */   @Comment("片語庫編碼 ")
/*     */   @Column(length = 70)
/*     */   private String id;
/*     */   @Comment("所屬的片語種類編號")
/*     */   @Column(length = 20)
/*     */   private String phraseType;
/*     */   @Comment("片語名稱")
/*     */   @Column(length = 50)
/*     */   private String phraseName;
/*     */   @Comment("排序")
/*     */   private Integer orderNo;
/*     */   
/*     */   public DisPhraseConfig() {}
/*     */   
/*     */   public DisPhraseConfig(String phraseType, String phraseName) {
/*  53 */     this.id = KeyUtils.getKey(new Object[] { phraseType, phraseName });
/*  54 */     this.phraseType = phraseType;
/*  55 */     this.phraseName = phraseName;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  60 */     return Objects.hashCode(new Object[] { Integer.valueOf(super.hashCode()), this.id });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/*  65 */     if (object instanceof DisPhraseConfig) {
/*  66 */       if (!super.equals(object)) return false; 
/*  67 */       DisPhraseConfig that = (DisPhraseConfig)object;
/*  68 */       return Objects.equal(this.id, that.id);
/*     */     } 
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  79 */     return MoreObjects.toStringHelper(this).add("id", this.id).add("phraseType", this.phraseType).add("phraseName", this.phraseName).toString();
/*     */   }
/*     */   
/*     */   public String getId() {
/*  83 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  87 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getPhraseType() {
/*  91 */     return this.phraseType;
/*     */   }
/*     */   
/*     */   public void setPhraseType(String phraseType) {
/*  95 */     this.phraseType = phraseType;
/*     */   }
/*     */   
/*     */   public String getPhraseName() {
/*  99 */     return this.phraseName;
/*     */   }
/*     */   
/*     */   public void setPhraseName(String phraseName) {
/* 103 */     this.phraseName = phraseName;
/*     */   }
/*     */   
/*     */   public Integer getOrderNo() {
/* 107 */     return this.orderNo;
/*     */   }
/*     */   
/*     */   public void setOrderNo(Integer orderNo) {
/* 111 */     this.orderNo = orderNo;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\model\DisPhraseConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */