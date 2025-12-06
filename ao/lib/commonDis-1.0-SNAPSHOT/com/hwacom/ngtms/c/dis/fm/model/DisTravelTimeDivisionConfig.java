/*     */ package com.hwacom.ngtms.c.dis.fm.model;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.base.MoreObjects.ToStringHelper;
/*     */ import com.google.common.base.Objects;
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.base.util.KeyUtils;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
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
/*     */ @Entity
/*     */ public class DisTravelTimeDivisionConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8113512707443328771L;
/*     */   @Id
/*     */   @Comment("id")
/*     */   @Column(length=41)
/*     */   private String id;
/*     */   @Comment("旅行時間設定主表id ,DisTravelTimeConfig id")
/*     */   @Column(length=41)
/*     */   private String parent;
/*     */   @Comment("做為前端顯示用的的順序 ，由0開始")
/*  43 */   private Integer divisionOrder = Integer.valueOf(0);
/*     */   @Comment("分割點編號")
/*     */   @Column(length=20)
/*  46 */   private String divisionId = "0";
/*     */   
/*     */ 
/*     */   public DisTravelTimeDivisionConfig() {}
/*     */   
/*     */ 
/*     */   public DisTravelTimeDivisionConfig(String parent, Integer divisionOrder, String divisionId)
/*     */   {
/*  54 */     this.id = KeyUtils.getKey(new Object[] { parent, divisionOrder });
/*  55 */     this.parent = parent;
/*  56 */     this.divisionOrder = divisionOrder;
/*  57 */     this.divisionId = divisionId;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/*  62 */     return Objects.hashCode(new Object[] { this.id });
/*     */   }
/*     */   
/*     */   public boolean equals(Object object)
/*     */   {
/*  67 */     if ((object instanceof DisTravelTimeDivisionConfig)) {
/*  68 */       DisTravelTimeDivisionConfig that = (DisTravelTimeDivisionConfig)object;
/*  69 */       return Objects.equal(this.id, that.id);
/*     */     }
/*  71 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/*  80 */     return MoreObjects.toStringHelper(this).add("id", this.id).add("divisionOrder", this.divisionOrder).add("divisionId", this.divisionId).toString();
/*     */   }
/*     */   
/*     */   public String getId() {
/*  84 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  88 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getParent() {
/*  92 */     return this.parent;
/*     */   }
/*     */   
/*     */   public void setParent(String parent) {
/*  96 */     this.parent = parent;
/*     */   }
/*     */   
/*     */   public Integer getOrder() {
/* 100 */     return this.divisionOrder;
/*     */   }
/*     */   
/*     */   public void setOrder(Integer order) {
/* 104 */     this.divisionOrder = order;
/*     */   }
/*     */   
/*     */   public String getDivisionId() {
/* 108 */     return this.divisionId;
/*     */   }
/*     */   
/*     */   public void setDivisionId(String divisionId) {
/* 112 */     this.divisionId = divisionId;
/*     */   }
/*     */   
/*     */   public Integer getDivisionOrder() {
/* 116 */     return this.divisionOrder;
/*     */   }
/*     */   
/*     */   public void setDivisionOrder(Integer divisionOrder) {
/* 120 */     this.divisionOrder = divisionOrder;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\model\DisTravelTimeDivisionConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */