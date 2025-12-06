/*     */ package com.hwacom.ngtms.c.dgs.fm.model;
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
/*     */ @Entity
/*     */ public class RampVdType
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2162372526354531022L;
/*     */   @Id
/*     */   @Comment("id")
/*     */   private Integer id;
/*     */   @Comment("匝道名稱")
/*     */   private String name;
/*     */   @Comment("該類別之說明 ")
/*     */   private String memo;
/*     */   @Comment("總匝道口有無 VD")
/*     */   private Boolean vdFather;
/*     */   @Comment("匝道口1有無 VD")
/*     */   private Boolean vdChild1;
/*     */   @Comment("匝道口2有無 VD")
/*     */   private Boolean vdChild2;
/*     */   
/*     */   public Integer getId() {
/*  43 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Integer id) {
/*  47 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getMemo() {
/*  51 */     return this.memo;
/*     */   }
/*     */   
/*     */   public void setMemo(String memo) {
/*  55 */     this.memo = memo;
/*     */   }
/*     */   
/*     */   public Boolean getVdFather() {
/*  59 */     return this.vdFather;
/*     */   }
/*     */   
/*     */   public void setVdFather(Boolean vdFather) {
/*  63 */     this.vdFather = vdFather;
/*     */   }
/*     */   
/*     */   public Boolean getVdChild1() {
/*  67 */     return this.vdChild1;
/*     */   }
/*     */   
/*     */   public void setVdChild1(Boolean vdChild1) {
/*  71 */     this.vdChild1 = vdChild1;
/*     */   }
/*     */   
/*     */   public Boolean getVdChild2() {
/*  75 */     return this.vdChild2;
/*     */   }
/*     */   
/*     */   public void setVdChild2(Boolean vdChild2) {
/*  79 */     this.vdChild2 = vdChild2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  86 */     return "RampVdType [id=" + this.id + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  91 */     int prime = 31;
/*  92 */     int result = 1;
/*  93 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/*  94 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  99 */     if (this == obj) return true; 
/* 100 */     if (obj == null) return false; 
/* 101 */     if (getClass() != obj.getClass()) return false; 
/* 102 */     RampVdType other = (RampVdType)obj;
/* 103 */     if (this.id == null)
/* 104 */     { if (other.id != null) return false;  }
/* 105 */     else if (!this.id.equals(other.id)) { return false; }
/* 106 */      return true;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 110 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/* 114 */     this.name = name;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonDgs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dgs\fm\model\RampVdType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */