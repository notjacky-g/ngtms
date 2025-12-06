/*    */ package com.hwacom.ngtms.c.dgs.fm.model;
/*    */ 
/*    */ import com.hwacom.ngtms.base.annotation.Comment;
/*    */ import java.io.Serializable;
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
/*    */ @Entity
/*    */ public class RingRoadVd
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 2520220187969398357L;
/*    */   @Id
/*    */   @Comment("RingRoadConfig.id + \"-\" + vd")
/*    */   private String id;
/*    */   @Comment("vd")
/*    */   private String vd;
/*    */   @Comment("權重 ")
/*    */   private Integer weigth;
/*    */   
/*    */   public RingRoadVd() {}
/*    */   
/*    */   public RingRoadVd(String vd, int weigth) {
/* 36 */     this.vd = vd;
/* 37 */     this.weigth = Integer.valueOf(weigth);
/*    */   }
/*    */   
/*    */   public String getId() {
/* 41 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 45 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getVd() {
/* 49 */     return this.vd;
/*    */   }
/*    */   
/*    */   public void setVd(String vd) {
/* 53 */     this.vd = vd;
/*    */   }
/*    */   
/*    */   public Integer getWeigth() {
/* 57 */     return this.weigth;
/*    */   }
/*    */   
/*    */   public void setWeigth(Integer weigth) {
/* 61 */     this.weigth = weigth;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 66 */     int prime = 31;
/* 67 */     int result = 1;
/* 68 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 69 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 74 */     if (this == obj) return true; 
/* 75 */     if (obj == null) return false; 
/* 76 */     if (getClass() != obj.getClass()) return false; 
/* 77 */     RingRoadVd other = (RingRoadVd)obj;
/* 78 */     if (this.id == null)
/* 79 */     { if (other.id != null) return false;  }
/* 80 */     else if (!this.id.equals(other.id)) { return false; }
/* 81 */      return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 86 */     return "RoadRoadVd [id=" + this.id + ", vd=" + this.vd + ", weigth=" + this.weigth + "]";
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonDgs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dgs\fm\model\RingRoadVd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */