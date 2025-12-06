/*    */ package com.hwacom.ngtms.c.dis.fm.model;
/*    */ 
/*    */ import com.google.common.base.Objects;
/*    */ import com.hwacom.ngtms.base.annotation.Comment;
/*    */ import java.io.Serializable;
/*    */ import javax.persistence.Basic;
/*    */ import javax.persistence.Column;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.FetchType;
/*    */ import javax.persistence.Id;
/*    */ import javax.persistence.Lob;
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
/*    */ public class DisGraphic
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -7382569112379527332L;
/*    */   @Id
/*    */   @Comment("對應的dis_graphic_id")
/*    */   @Column(length=70)
/*    */   private String disGraphicConfigId;
/*    */   @Comment("圖片,PICTURE mysql MEDIUMBLOB 16,772,215 byte數加3bytes ")
/*    */   @Column(nullable=false)
/*    */   @Lob
/*    */   @Basic(fetch=FetchType.EAGER)
/*    */   private byte[] picture;
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 40 */     return Objects.hashCode(new Object[] { Integer.valueOf(super.hashCode()), this.disGraphicConfigId });
/*    */   }
/*    */   
/*    */   public boolean equals(Object object)
/*    */   {
/* 45 */     if ((object instanceof DisGraphic)) {
/* 46 */       DisGraphic that = (DisGraphic)object;
/* 47 */       return Objects.equal(this.disGraphicConfigId, that.disGraphicConfigId);
/*    */     }
/* 49 */     return false;
/*    */   }
/*    */   
/*    */   public String getDisGraphicConfigId() {
/* 53 */     return this.disGraphicConfigId;
/*    */   }
/*    */   
/*    */   public void setDisGraphicConfigId(String disGraphicConfigId) {
/* 57 */     this.disGraphicConfigId = disGraphicConfigId;
/*    */   }
/*    */   
/*    */   public byte[] getPicture() {
/* 61 */     return this.picture;
/*    */   }
/*    */   
/*    */   public void setPicture(byte[] picture) {
/* 65 */     this.picture = picture;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\model\DisGraphic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */