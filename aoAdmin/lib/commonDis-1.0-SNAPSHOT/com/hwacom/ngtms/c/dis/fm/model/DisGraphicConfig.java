/*     */ package com.hwacom.ngtms.c.dis.fm.model;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.base.Objects;
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.c.dis.shared.PanelCategory;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Basic;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.EnumType;
/*     */ import javax.persistence.Enumerated;
/*     */ import javax.persistence.FetchType;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Lob;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ public class DisGraphicConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5993460251463868371L;
/*     */   @Id
/*     */   @Comment("id")
/*     */   @Column(length = 70)
/*     */   private String id;
/*     */   @Comment("面板種類")
/*     */   @Column(length = 20)
/*     */   @Enumerated(EnumType.STRING)
/*     */   private PanelCategory category;
/*     */   @Comment("圖型模式")
/*     */   @Column(nullable = false)
/*     */   private Integer graphicMode;
/*     */   @Comment("R22已下載於Tc圖片編號，用來指定下載訊息到TC顯示時使用的圖片")
/*     */   @Column(nullable = false)
/*  64 */   private Integer tcCodeId = Integer.valueOf(0);
/*     */ 
/*     */   
/*     */   @Comment("圖形說明")
/*     */   @Column(length = 50)
/*     */   private String description;
/*     */ 
/*     */   
/*     */   @Comment("檔案名稱與副檔名")
/*     */   @Column(length = 30)
/*     */   private String fileName;
/*     */ 
/*     */   
/*     */   @Comment("PICTURE mysql MEDIUMBLOB 16,772,215 byte數加3bytes 圖片")
/*     */   @Column(length = 16777215, nullable = false)
/*     */   @Lob
/*     */   @Basic(fetch = FetchType.EAGER)
/*     */   private byte[] picture;
/*     */ 
/*     */ 
/*     */   
/*     */   public DisGraphicConfig() {}
/*     */ 
/*     */   
/*     */   public DisGraphicConfig(PanelCategory category, Integer graphicMode, String description, String fileName) {
/*  89 */     this.category = category;
/*  90 */     this.graphicMode = graphicMode;
/*  91 */     this.description = description;
/*  92 */     this.fileName = fileName;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  97 */     return Objects.hashCode(new Object[] { this.id });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/* 102 */     if (object instanceof DisGraphicConfig) {
/* 103 */       if (!super.equals(object)) return false; 
/* 104 */       DisGraphicConfig that = (DisGraphicConfig)object;
/* 105 */       return Objects.equal(this.id, that.id);
/*     */     } 
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 116 */     return MoreObjects.toStringHelper(this).add("id", this.id).add("description", this.description).add("fileName", this.fileName).toString();
/*     */   }
/*     */   
/*     */   public String getId() {
/* 120 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 124 */     this.id = id;
/*     */   }
/*     */   
/*     */   public PanelCategory getCategory() {
/* 128 */     return this.category;
/*     */   }
/*     */   
/*     */   public void setCategory(PanelCategory category) {
/* 132 */     this.category = category;
/*     */   }
/*     */   
/*     */   public Integer getGraphicMode() {
/* 136 */     return this.graphicMode;
/*     */   }
/*     */   
/*     */   public void setGraphicMode(Integer graphicMode) {
/* 140 */     this.graphicMode = graphicMode;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 144 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(String description) {
/* 148 */     this.description = description;
/*     */   }
/*     */   
/*     */   public String getFileName() {
/* 152 */     return this.fileName;
/*     */   }
/*     */   
/*     */   public void setFileName(String fileName) {
/* 156 */     this.fileName = fileName;
/*     */   }
/*     */   
/*     */   public byte[] getPicture() {
/* 160 */     return this.picture;
/*     */   }
/*     */   
/*     */   public void setPicture(byte[] picture) {
/* 164 */     this.picture = picture;
/*     */   }
/*     */   
/*     */   public Integer getTcCodeId() {
/* 168 */     return this.tcCodeId;
/*     */   }
/*     */   
/*     */   public void setTcCodeId(Integer gCodeId) {
/* 172 */     this.tcCodeId = gCodeId;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\model\DisGraphicConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */