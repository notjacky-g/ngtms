/*     */ package com.hwacom.ngtms.c.dis.fm.model;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.base.util.KeyUtils;
/*     */ import com.hwacom.ngtms.c.dis.shared.DisFullText;
/*     */ import com.hwacom.ngtms.c.dis.shared.PanelCategory;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Embedded;
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
/*     */ @Entity
/*     */ public class DisFullTextConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 400271756898138024L;
/*     */   @Id
/*     */   @Comment("全文庫編碼 ")
/*     */   private String id;
/*     */   @Comment("顯示訊息內容")
/*     */   @Embedded
/*     */   private DisFullText disFullText;
/*     */   @Comment("下層全彩圖形碼(1-255，0代表不使用)")
/*  40 */   private Integer lowerIconCodeId = Integer.valueOf(0);
/*     */   
/*     */ 
/*     */   @Comment("FullColor2_X INTEGER 下層全彩圖型位置ｘ軸")
/*  44 */   private Integer lowerIconCodeX = Integer.valueOf(0);
/*     */   
/*     */ 
/*     */   @Comment("FullColor2_Y INTEGER 下層全彩圖型位置ｙ軸 ")
/*  48 */   private Integer lowerIconCodeY = Integer.valueOf(0);
/*     */   
/*     */ 
/*     */   @Comment("排序")
/*  52 */   private Integer orderId = Integer.valueOf(999);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DisFullTextConfig() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public DisFullTextConfig(String full_text_type, PanelCategory category, String message, Integer g_code_id)
/*     */   {
/*  67 */     this.id = KeyUtils.getKey(new Object[] { full_text_type, category, message, g_code_id });
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/*  72 */     return Objects.hashCode(new Object[] { this.id });
/*     */   }
/*     */   
/*     */   public boolean equals(Object object)
/*     */   {
/*  77 */     if ((object instanceof DisFullTextConfig)) {
/*  78 */       if (!super.equals(object)) return false;
/*  79 */       DisFullTextConfig that = (DisFullTextConfig)object;
/*  80 */       return Objects.equal(this.id, that.id);
/*     */     }
/*  82 */     return false;
/*     */   }
/*     */   
/*     */   public String getId() {
/*  86 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  90 */     this.id = id;
/*     */   }
/*     */   
/*     */   public DisFullText getDisFullText() {
/*  94 */     return this.disFullText;
/*     */   }
/*     */   
/*     */   public void setDisFullText(DisFullText disFullText) {
/*  98 */     this.disFullText = disFullText;
/*     */   }
/*     */   
/*     */   public Integer getOrderId() {
/* 102 */     return this.orderId;
/*     */   }
/*     */   
/*     */   public void setOrderId(Integer orderId) {
/* 106 */     this.orderId = orderId;
/*     */   }
/*     */   
/*     */   public Integer getLowerIconCodeId() {
/* 110 */     return this.lowerIconCodeId;
/*     */   }
/*     */   
/*     */   public void setLowerIconCodeId(Integer lowerIconCodeId) {
/* 114 */     this.lowerIconCodeId = lowerIconCodeId;
/*     */   }
/*     */   
/*     */   public Integer getLowerIconCodeX() {
/* 118 */     return this.lowerIconCodeX;
/*     */   }
/*     */   
/*     */   public void setLowerIconCodeX(Integer lowerIconCodeX) {
/* 122 */     this.lowerIconCodeX = lowerIconCodeX;
/*     */   }
/*     */   
/*     */   public Integer getLowerIconCodeY() {
/* 126 */     return this.lowerIconCodeY;
/*     */   }
/*     */   
/*     */   public void setLowerIconCodeY(Integer lowerIconCodeY) {
/* 130 */     this.lowerIconCodeY = lowerIconCodeY;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\model\DisFullTextConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */