/*    */ package com.hwacom.ngtms.common.fm.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ import java.util.Objects;
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
/*    */ public class PartitionPk
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -2939254440932292625L;
/*    */   private String id;
/*    */   private Date dataTime;
/*    */   
/*    */   public String getId()
/*    */   {
/* 27 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 31 */     this.id = id;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 36 */     int hash = 3;
/* 37 */     hash = 29 * hash + Objects.hashCode(this.id);
/* 38 */     hash = 29 * hash + Objects.hashCode(this.dataTime);
/* 39 */     return hash;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj)
/*    */   {
/* 44 */     if (obj == null) {
/* 45 */       return false;
/*    */     }
/* 47 */     if (getClass() != obj.getClass()) {
/* 48 */       return false;
/*    */     }
/* 50 */     PartitionPk other = (PartitionPk)obj;
/* 51 */     if (!Objects.equals(this.id, other.id)) {
/* 52 */       return false;
/*    */     }
/* 54 */     if (!Objects.equals(this.dataTime, other.dataTime)) {
/* 55 */       return false;
/*    */     }
/* 57 */     return true;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 62 */     return "com.hwacom.ngtms.common.model.PartitionPk[ id=" + this.id + " ]";
/*    */   }
/*    */   
/*    */   public Date getDataTime()
/*    */   {
/* 67 */     return this.dataTime;
/*    */   }
/*    */   
/*    */   public void setDataTime(Date dataTime)
/*    */   {
/* 72 */     this.dataTime = dataTime;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\model\PartitionPk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */