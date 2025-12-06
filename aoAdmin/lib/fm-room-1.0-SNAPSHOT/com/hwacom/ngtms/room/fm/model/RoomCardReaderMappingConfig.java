/*     */ package com.hwacom.ngtms.room.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.Id;
/*     */ import org.hibernate.annotations.GenericGenerator;
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
/*     */ public class RoomCardReaderMappingConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4462836378845820594L;
/*     */   @Id
/*     */   @Comment("UUID")
/*     */   @Column(length = 40)
/*     */   @GeneratedValue(generator = "system-uuid")
/*     */   @GenericGenerator(name = "system-uuid", strategy = "uuid2")
/*     */   private String id;
/*     */   @Comment("卡片編號,對應ABA編號")
/*     */   private String cardId;
/*     */   @Comment("讀卡機編號")
/*     */   private String readerId;
/*     */   @Comment("是否加入讀卡機")
/*     */   private Boolean loginCardReader;
/*     */   
/*     */   public String getId() {
/*  47 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  51 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getCardId() {
/*  55 */     return this.cardId;
/*     */   }
/*     */   
/*     */   public void setCardId(String cardId) {
/*  59 */     this.cardId = cardId;
/*     */   }
/*     */   
/*     */   public String getReaderId() {
/*  63 */     return this.readerId;
/*     */   }
/*     */   
/*     */   public void setReaderId(String readerId) {
/*  67 */     this.readerId = readerId;
/*     */   }
/*     */   
/*     */   public Boolean getLoginCardReader() {
/*  71 */     return this.loginCardReader;
/*     */   }
/*     */   
/*     */   public void setLoginCardReader(Boolean loginCardReader) {
/*  75 */     this.loginCardReader = loginCardReader;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  80 */     int prime = 31;
/*  81 */     int result = 1;
/*  82 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/*  83 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  88 */     if (this == obj) return true; 
/*  89 */     if (obj == null) return false; 
/*  90 */     if (getClass() != obj.getClass()) return false; 
/*  91 */     RoomCardReaderMappingConfig other = (RoomCardReaderMappingConfig)obj;
/*  92 */     if (this.id == null)
/*  93 */     { if (other.id != null) return false;  }
/*  94 */     else if (!this.id.equals(other.id)) { return false; }
/*  95 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 100 */     return "RoomCardReaderMappingConfig [id=" + this.id + ", cardId=" + this.cardId + ", readerId=" + this.readerId + ", loginCardReader=" + this.loginCardReader + "]";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\fm\model\RoomCardReaderMappingConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */