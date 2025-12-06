/*    */ package com.hwacom.ngtms.c.dis.fm.model;
/*    */ 
/*    */ import com.google.common.base.MoreObjects;
/*    */ import com.google.common.base.MoreObjects.ToStringHelper;
/*    */ import com.google.common.base.Objects;
/*    */ import com.hwacom.ngtms.base.annotation.Comment;
/*    */ import java.io.Serializable;
/*    */ import javax.persistence.Column;
/*    */ import javax.persistence.Entity;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Entity
/*    */ public class DisPhraseType
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 7771773490737632571L;
/*    */   @Id
/*    */   @Comment("片語種類編號 : 1.地點1, 2.地點2, 3.地點3, 4.地點補充 , 5.對象車  6.原因, 7.指示, 8.交通管制, 9.結果 , 10.其它")
/*    */   @Column(length=20)
/*    */   private String id;
/*    */   @Comment("片語名稱 : 1.地點1, 2.地點2, 3.地點3, 4.地點補充 , 5.對象車  6.原因, 7.指示, 8.交通管制, 9.結果 , 10.其它")
/*    */   @Column(nullable=false, length=20)
/*    */   private String phraseTypeName;
/*    */   
/*    */   public DisPhraseType() {}
/*    */   
/*    */   public DisPhraseType(String phraseTypeName)
/*    */   {
/* 48 */     this.id = phraseTypeName;
/* 49 */     this.phraseTypeName = phraseTypeName;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 54 */     return Objects.hashCode(new Object[] { this.id });
/*    */   }
/*    */   
/*    */   public boolean equals(Object object)
/*    */   {
/* 59 */     if ((object instanceof DisPhraseType)) {
/* 60 */       if (!super.equals(object)) return false;
/* 61 */       DisPhraseType that = (DisPhraseType)object;
/* 62 */       return Objects.equal(this.id, that.id);
/*    */     }
/* 64 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String toString()
/*    */   {
/* 72 */     return MoreObjects.toStringHelper(this).add("id", this.id).add("phraseTypeName", this.phraseTypeName).toString();
/*    */   }
/*    */   
/*    */   public String getId() {
/* 76 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 80 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getPhraseTypeName() {
/* 84 */     return this.phraseTypeName;
/*    */   }
/*    */   
/*    */   public void setPhraseTypeName(String phraseTypeName) {
/* 88 */     this.phraseTypeName = phraseTypeName;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\model\DisPhraseType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */