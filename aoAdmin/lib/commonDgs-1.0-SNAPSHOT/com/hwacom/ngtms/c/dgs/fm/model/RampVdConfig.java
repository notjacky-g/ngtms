/*     */ package com.hwacom.ngtms.c.dgs.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.c.shared.Direction;
/*     */ import com.hwacom.ngtms.c.shared.RampType;
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
/*     */ public class RampVdConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 42567900762062775L;
/*     */   @Id
/*     */   @Comment("匝道編號")
/*     */   private Integer id;
/*     */   @Comment("屬於哪個交流道，也就是分割點的編號")
/*     */   @Column(nullable = false)
/*     */   private String divisionId;
/*     */   @Comment("方向")
/*     */   @Column(nullable = false)
/*     */   @Enumerated(EnumType.STRING)
/*     */   private Direction direction;
/*     */   @Comment("出入口 ")
/*     */   @Column(nullable = false)
/*     */   @Enumerated(EnumType.STRING)
/*     */   private RampType rampType;
/*     */   @Comment("出入口敘述 ")
/*     */   private String rampTypeDesc;
/*     */   @Comment("匝道型態(對應到RampVdType的id)")
/*     */   private Integer rampVdType;
/*     */   @Comment("該匝道是否屬於特例情況（0正常、1特例）如果屬於特殊狀況，該匝道計算流量的方式就必須寫HARD CODE")
/*     */   private Integer exception;
/*     */   @Comment("指定之VD1（預設為總匝道口）")
/*     */   private String vd1;
/*     */   @Comment("指定之VD2（預設為匝道口1）")
/*     */   private String vd2;
/*     */   @Comment("指定之VD3（預設為匝道口2)")
/*     */   private String vd3;
/*     */   @Comment("該匝道的壅塞程度是要拿哪支VD來算")
/*     */   private String degreeVd1;
/*     */   @Comment("該匝道的壅塞程度是要拿哪支VD來算")
/*     */   private String degreeVd2;
/*     */   @Comment("該匝道的壅塞程度是要拿哪支VD來算")
/*     */   private String degreeVd3;
/*     */   @Comment("該匝道的壅塞程度是要拿哪支VD來算")
/*     */   private String degreeVd4;
/*     */   @Comment("該匝道的壅塞程度是要拿哪支VD來算")
/*     */   private String degreeVd5;
/*     */   @Comment("該匝道的壅塞程度是要拿哪支VD來算")
/*     */   private String degreeVd6;
/*     */   
/*     */   public Integer getId() {
/*  96 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Integer id) {
/* 100 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getDivisionId() {
/* 104 */     return this.divisionId;
/*     */   }
/*     */   
/*     */   public void setDivisionId(String divisionId) {
/* 108 */     this.divisionId = divisionId;
/*     */   }
/*     */   
/*     */   public Direction getDirection() {
/* 112 */     return this.direction;
/*     */   }
/*     */   
/*     */   public void setDirection(Direction direction) {
/* 116 */     this.direction = direction;
/*     */   }
/*     */   
/*     */   public RampType getRampType() {
/* 120 */     return this.rampType;
/*     */   }
/*     */   
/*     */   public void setRampType(RampType rampType) {
/* 124 */     this.rampType = rampType;
/*     */   }
/*     */   
/*     */   public String getRampTypeDesc() {
/* 128 */     return this.rampTypeDesc;
/*     */   }
/*     */   
/*     */   public void setRampTypeDesc(String rampTypeDesc) {
/* 132 */     this.rampTypeDesc = rampTypeDesc;
/*     */   }
/*     */   
/*     */   public Integer getRampVdType() {
/* 136 */     return this.rampVdType;
/*     */   }
/*     */   
/*     */   public void setRampVdType(Integer rampVdType) {
/* 140 */     this.rampVdType = rampVdType;
/*     */   }
/*     */   
/*     */   public Integer getException() {
/* 144 */     return this.exception;
/*     */   }
/*     */   
/*     */   public void setException(Integer exception) {
/* 148 */     this.exception = exception;
/*     */   }
/*     */   
/*     */   public String getVd1() {
/* 152 */     return this.vd1;
/*     */   }
/*     */   
/*     */   public void setVd1(String vd1) {
/* 156 */     this.vd1 = vd1;
/*     */   }
/*     */   
/*     */   public String getVd2() {
/* 160 */     return this.vd2;
/*     */   }
/*     */   
/*     */   public void setVd2(String vd2) {
/* 164 */     this.vd2 = vd2;
/*     */   }
/*     */   
/*     */   public String getVd3() {
/* 168 */     return this.vd3;
/*     */   }
/*     */   
/*     */   public void setVd3(String vd3) {
/* 172 */     this.vd3 = vd3;
/*     */   }
/*     */   
/*     */   public String getDegreeVd1() {
/* 176 */     return this.degreeVd1;
/*     */   }
/*     */   
/*     */   public void setDegreeVd1(String degreeVd1) {
/* 180 */     this.degreeVd1 = degreeVd1;
/*     */   }
/*     */   
/*     */   public String getDegreeVd2() {
/* 184 */     return this.degreeVd2;
/*     */   }
/*     */   
/*     */   public void setDegreeVd2(String degreeVd2) {
/* 188 */     this.degreeVd2 = degreeVd2;
/*     */   }
/*     */   
/*     */   public String getDegreeVd3() {
/* 192 */     return this.degreeVd3;
/*     */   }
/*     */   
/*     */   public void setDegreeVd3(String degreeVd3) {
/* 196 */     this.degreeVd3 = degreeVd3;
/*     */   }
/*     */   
/*     */   public String getDegreeVd4() {
/* 200 */     return this.degreeVd4;
/*     */   }
/*     */   
/*     */   public void setDegreeVd4(String degreeVd4) {
/* 204 */     this.degreeVd4 = degreeVd4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 211 */     return "RampVdConfig [divisionId=" + this.divisionId + ", direction=" + this.direction + ", rampType=" + this.rampType + ", rampVdType=" + this.rampVdType + "]";
/*     */   }
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
/*     */   public int hashCode() {
/* 224 */     int prime = 31;
/* 225 */     int result = 1;
/* 226 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 227 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 232 */     if (this == obj) return true; 
/* 233 */     if (obj == null) return false; 
/* 234 */     if (getClass() != obj.getClass()) return false; 
/* 235 */     RampVdConfig other = (RampVdConfig)obj;
/* 236 */     if (this.id == null)
/* 237 */     { if (other.id != null) return false;  }
/* 238 */     else if (!this.id.equals(other.id)) { return false; }
/* 239 */      return true;
/*     */   }
/*     */   
/*     */   public String getDegreeVd5() {
/* 243 */     return this.degreeVd5;
/*     */   }
/*     */   
/*     */   public void setDegreeVd5(String degreeVd5) {
/* 247 */     this.degreeVd5 = degreeVd5;
/*     */   }
/*     */   
/*     */   public String getDegreeVd6() {
/* 251 */     return this.degreeVd6;
/*     */   }
/*     */   
/*     */   public void setDegreeVd6(String degreeVd6) {
/* 255 */     this.degreeVd6 = degreeVd6;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonDgs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dgs\fm\model\RampVdConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */