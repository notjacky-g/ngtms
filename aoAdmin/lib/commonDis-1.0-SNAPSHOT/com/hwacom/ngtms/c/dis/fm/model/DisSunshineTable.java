/*     */ package com.hwacom.ngtms.c.dis.fm.model;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
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
/*     */ public class DisSunshineTable
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -367051506061549722L;
/*     */   @Id
/*     */   @Comment("日期（只需要使用月日）ex:0101")
/*     */   @Column(length = 50)
/*     */   private String sunshineDate;
/*     */   @Comment("日出時間（只需要使用時分）")
/*     */   @Column(nullable = false)
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   private Date sunrise;
/*     */   @Comment("日落時間（只需要使用時分）")
/*     */   @Column(nullable = false)
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   private Date sunset;
/*     */   
/*     */   public int hashCode() {
/*  65 */     return Objects.hashCode(new Object[] { this.sunshineDate });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/*  70 */     if (object instanceof DisSunshineTable) {
/*  71 */       DisSunshineTable that = (DisSunshineTable)object;
/*  72 */       return Objects.equal(this.sunshineDate, that.sunshineDate);
/*     */     } 
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSunDuration() {
/*  79 */     return this.sunshineDate;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSunDuration(String sunDate) {
/*  84 */     this.sunshineDate = sunDate;
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getSunrise() {
/*  89 */     return this.sunrise;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSunrise(Date sunrise) {
/*  94 */     this.sunrise = sunrise;
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getSunset() {
/*  99 */     return this.sunset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSunset(Date sunset) {
/* 104 */     this.sunset = sunset;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\model\DisSunshineTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */