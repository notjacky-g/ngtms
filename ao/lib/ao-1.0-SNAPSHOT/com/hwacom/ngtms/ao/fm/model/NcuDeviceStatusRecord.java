/*    */ package com.hwacom.ngtms.ao.fm.model;
/*    */ 
/*    */ import com.hwacom.ngtms.base.annotation.Comment;
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.GeneratedValue;
/*    */ import javax.persistence.Id;
/*    */ import javax.persistence.Temporal;
/*    */ import javax.persistence.TemporalType;
/*    */ import org.hibernate.annotations.GenericGenerator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Entity
/*    */ public class NcuDeviceStatusRecord
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 339486230697951236L;
/*    */   @GeneratedValue(generator = "uuid")
/*    */   @GenericGenerator(name = "uuid", strategy = "uuid")
/*    */   @Id
/*    */   @Comment("UUID")
/*    */   private String id;
/*    */   @Temporal(TemporalType.TIMESTAMP)
/*    */   @Comment("時間")
/*    */   private Date dataTime;
/*    */   @Comment("設備編號")
/*    */   private String deviceName;
/*    */   @Comment("連線狀態 : 正常:0, 異常:1")
/*    */   private Integer statusValue;
/*    */   
/*    */   public String getId() {
/* 36 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 40 */     this.id = id;
/*    */   }
/*    */   
/*    */   public Date getDataTime() {
/* 44 */     return this.dataTime;
/*    */   }
/*    */   
/*    */   public void setDataTime(Date dataTime) {
/* 48 */     this.dataTime = dataTime;
/*    */   }
/*    */   
/*    */   public String getDeviceName() {
/* 52 */     return this.deviceName;
/*    */   }
/*    */   
/*    */   public void setDeviceName(String deviceName) {
/* 56 */     this.deviceName = deviceName;
/*    */   }
/*    */   
/*    */   public Integer getStatusValue() {
/* 60 */     return this.statusValue;
/*    */   }
/*    */   
/*    */   public void setStatusValue(Integer statusValue) {
/* 64 */     this.statusValue = statusValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 69 */     int prime = 31;
/* 70 */     int result = 1;
/* 71 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 72 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 77 */     if (this == obj) return true; 
/* 78 */     if (obj == null) return false; 
/* 79 */     if (getClass() != obj.getClass()) return false; 
/* 80 */     NcuDeviceStatusRecord other = (NcuDeviceStatusRecord)obj;
/* 81 */     if (this.id == null)
/* 82 */     { if (other.id != null) return false;  }
/* 83 */     else if (!this.id.equals(other.id)) { return false; }
/* 84 */      return true;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\model\NcuDeviceStatusRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */