/*     */ package com.hwacom.ngtms.pd.fm.model;
/*     */ 
/*     */ import com.google.common.base.Objects;
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
/*     */ @Entity
/*     */ @Table(indexes = {@Index(columnList = "loop_id")})
/*     */ public class PdLoopStatus
/*     */   implements Serializable
/*     */ {
/*  23 */   public static final Integer LOOP_USAGE_ON = Integer.valueOf(0);
/*     */   
/*  25 */   public static final Integer LOOP_USAGE_OFF = Integer.valueOf(1);
/*     */   
/*  27 */   public static final Integer LOOP_STATUS_ON = Integer.valueOf(0);
/*     */   
/*  29 */   public static final Integer LOOP_STATUS_OFF = Integer.valueOf(1);
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 4870748366249070897L;
/*     */   
/*     */   @Id
/*     */   private String id;
/*     */   
/*     */   @Column(nullable = false)
/*     */   private String loopId;
/*     */   
/*     */   private Integer status;
/*     */ 
/*     */   
/*     */   public boolean compareLoopStstus(PdLoopStatus that) {
/*  44 */     if (Objects.equal(this.loopId, that.loopId) && Objects.equal(this.status, that.status)) {
/*  45 */       return true;
/*     */     }
/*  47 */     return false;
/*     */   }
/*     */   
/*     */   public PdLoopStatus(String pdId, String loopId) {
/*  51 */     this.loopId = loopId;
/*  52 */     setId(KeyUtils.getKey(new Object[] { pdId, loopId }));
/*     */   }
/*     */ 
/*     */   
/*     */   public PdLoopStatus() {}
/*     */ 
/*     */   
/*     */   public String getId() {
/*  60 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  64 */     this.id = id;
/*     */   }
/*     */   
/*     */   public Integer getStatus() {
/*  68 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(Integer status) {
/*  72 */     this.status = status;
/*     */   }
/*     */   
/*     */   public String getLoopId() {
/*  76 */     return this.loopId;
/*     */   }
/*     */   
/*     */   public void setLoopId(String loopId) {
/*  80 */     this.loopId = loopId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  85 */     int hash = 0;
/*  86 */     hash += (this.id != null) ? this.id.hashCode() : 0;
/*  87 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/*  93 */     if (!(object instanceof PdLoopStatus)) {
/*  94 */       return false;
/*     */     }
/*  96 */     PdLoopStatus other = (PdLoopStatus)object;
/*  97 */     if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
/*  98 */       return false;
/*     */     }
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 105 */     return "PdLoopStatus [id=" + this.id + ", loopId=" + this.loopId + ", status=" + this.status + "]";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\fm-pd-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\pd\fm\model\PdLoopStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */