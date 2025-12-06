/*    */ package com.hwacom.ngtms.hcce.web.vo;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValidationData
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String id;
/*    */   private String code;
/*    */   private Date date;
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 18 */     int prime = 31;
/* 19 */     int result = 1;
/* 20 */     result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
/* 21 */     return result;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj)
/*    */   {
/* 26 */     if (this == obj) return true;
/* 27 */     if (obj == null) return false;
/* 28 */     if (getClass() != obj.getClass()) return false;
/* 29 */     ValidationData other = (ValidationData)obj;
/* 30 */     if (this.id == null) {
/* 31 */       if (other.id != null) return false;
/* 32 */     } else if (!this.id.equals(other.id)) return false;
/* 33 */     return true;
/*    */   }
/*    */   
/*    */   public String getId() {
/* 37 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 41 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getCode() {
/* 45 */     return this.code;
/*    */   }
/*    */   
/*    */   public void setCode(String code) {
/* 49 */     this.code = code;
/*    */   }
/*    */   
/*    */   public Date getDate() {
/* 53 */     return this.date;
/*    */   }
/*    */   
/*    */   public void setDate(Date date) {
/* 57 */     this.date = date;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcConsole-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\web\vo\ValidationData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */