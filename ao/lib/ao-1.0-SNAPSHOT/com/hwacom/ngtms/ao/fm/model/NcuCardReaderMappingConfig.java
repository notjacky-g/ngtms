/*    */ package com.hwacom.ngtms.ao.fm.model;
/*    */ 
/*    */ import com.hwacom.ngtms.base.annotation.Comment;
/*    */ import java.io.Serializable;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.GeneratedValue;
/*    */ import javax.persistence.Id;
/*    */ import org.hibernate.annotations.GenericGenerator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Entity
/*    */ public class NcuCardReaderMappingConfig
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1684460555671288358L;
/*    */   @GeneratedValue(generator = "uuid")
/*    */   @GenericGenerator(name = "uuid", strategy = "uuid")
/*    */   @Id
/*    */   @Comment("UUID")
/*    */   private String id;
/*    */   @Comment("主機編號")
/*    */   private String ncuId;
/*    */   @Comment("控制器編號")
/*    */   private String controlId;
/*    */   @Comment("控制器對應的識別ID")
/*    */   private String deviceId;
/*    */   
/*    */   public String getId() {
/* 35 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 39 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getNcuId() {
/* 43 */     return this.ncuId;
/*    */   }
/*    */   
/*    */   public void setNcuId(String ncuId) {
/* 47 */     this.ncuId = ncuId;
/*    */   }
/*    */   
/*    */   public String getControlId() {
/* 51 */     return this.controlId;
/*    */   }
/*    */   
/*    */   public void setControlId(String controlId) {
/* 55 */     this.controlId = controlId;
/*    */   }
/*    */   
/*    */   public String getDeviceId() {
/* 59 */     return this.deviceId;
/*    */   }
/*    */   
/*    */   public void setDeviceId(String deviceId) {
/* 63 */     this.deviceId = deviceId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 68 */     int prime = 31;
/* 69 */     int result = 1;
/* 70 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 71 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 76 */     if (this == obj) return true; 
/* 77 */     if (obj == null) return false; 
/* 78 */     if (getClass() != obj.getClass()) return false; 
/* 79 */     NcuCardReaderMappingConfig other = (NcuCardReaderMappingConfig)obj;
/* 80 */     if (this.id == null)
/* 81 */     { if (other.id != null) return false;  }
/* 82 */     else if (!this.id.equals(other.id)) { return false; }
/* 83 */      return true;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\model\NcuCardReaderMappingConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */