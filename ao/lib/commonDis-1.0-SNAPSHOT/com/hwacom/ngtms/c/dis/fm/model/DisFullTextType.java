/*    */ package com.hwacom.ngtms.c.dis.fm.model;
/*    */ 
/*    */ import com.google.common.base.Objects;
/*    */ import com.hwacom.ngtms.base.annotation.Comment;
/*    */ import com.hwacom.ngtms.base.util.KeyUtils;
/*    */ import com.hwacom.ngtms.c.dis.shared.PanelCategory;
/*    */ import java.io.Serializable;
/*    */ import javax.persistence.Column;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.EnumType;
/*    */ import javax.persistence.Enumerated;
/*    */ import javax.persistence.Id;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Entity
/*    */ public class DisFullTextType
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 6505886517262447810L;
/*    */   @Id
/*    */   @Comment("FullTextType INTEGER 分類(0=警告 1=控制 2=宣導 etc …)")
/*    */   @Column(length=70)
/*    */   private String id;
/*    */   @Comment("面板種類")
/*    */   @Column(nullable=false, length=20)
/*    */   @Enumerated(EnumType.STRING)
/*    */   private PanelCategory category;
/*    */   @Comment("描述")
/*    */   @Column(nullable=false, length=50)
/*    */   private String description;
/*    */   
/*    */   public DisFullTextType() {}
/*    */   
/*    */   public DisFullTextType(PanelCategory category, String description)
/*    */   {
/* 46 */     this.id = KeyUtils.getKey(new Object[] { category, description });
/* 47 */     this.category = category;
/* 48 */     this.description = description;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 53 */     return Objects.hashCode(new Object[] { this.id });
/*    */   }
/*    */   
/*    */   public boolean equals(Object object)
/*    */   {
/* 58 */     if ((object instanceof DisFullTextType)) {
/* 59 */       DisFullTextType that = (DisFullTextType)object;
/* 60 */       return Objects.equal(this.id, that.id);
/*    */     }
/* 62 */     return false;
/*    */   }
/*    */   
/*    */   public String getId() {
/* 66 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 70 */     this.id = id;
/*    */   }
/*    */   
/*    */   public PanelCategory getCategory() {
/* 74 */     return this.category;
/*    */   }
/*    */   
/*    */   public void setCategory(PanelCategory category) {
/* 78 */     this.category = category;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 82 */     return this.description;
/*    */   }
/*    */   
/*    */   public void setDescription(String description) {
/* 86 */     this.description = description;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\model\DisFullTextType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */