/*     */ package com.hwacom.ngtms.room.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.room.shared.CardStatus;
/*     */ import com.hwacom.ngtms.room.shared.CardType;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.EnumType;
/*     */ import javax.persistence.Enumerated;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Temporal;
/*     */ import javax.persistence.TemporalType;
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
/*     */ public class RoomCardConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4462836378845820594L;
/*     */   @Id
/*     */   @Comment("UUID")
/*     */   @Column(length = 40)
/*     */   @GeneratedValue(generator = "system-uuid")
/*     */   @GenericGenerator(name = "system-uuid", strategy = "uuid2")
/*     */   private String id;
/*     */   @Comment("ABA 編號")
/*     */   @Column(nullable = false)
/*     */   private String aba;
/*     */   @Comment("WEG1編號,由ABA推算的內碼")
/*     */   @Column(length = 40)
/*     */   private String weg1;
/*     */   @Comment("WEG2編號,由ABA推算的內碼")
/*     */   private String weg2;
/*     */   @Comment("實際可進入機房的開始日期")
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   private Date startDate;
/*     */   @Comment("實際可進入機房的結束日期")
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   private Date endDate;
/*     */   @Comment("卡片狀態")
/*     */   @Enumerated(EnumType.STRING)
/*     */   private CardStatus cardStatus;
/*     */   @Comment("備註")
/*     */   private String memo;
/*     */   @Comment("公司")
/*     */   private String company;
/*     */   @Comment("名稱")
/*     */   private String name;
/*     */   @Comment("身分證號碼")
/*     */   private String idNo;
/*     */   @Comment("職工編號")
/*     */   private String employeeNo;
/*     */   @Comment("職稱")
/*     */   private String jobTitle;
/*     */   @Comment("電話")
/*     */   private String tel;
/*     */   @Comment("手機")
/*     */   private String mobile;
/*     */   @Comment("卡片種類")
/*     */   @Enumerated(EnumType.STRING)
/*     */   private CardType cardType;
/*     */   @Comment("定期卡群組名稱")
/*     */   private String groupName;
/*     */   @Comment("角色編號")
/*     */   private String roleId;
/*     */   @Comment("發卡日期")
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   private Date issueDate;
/*     */   @Comment("預計歸還日期")
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   private Date expectedReturnDate;
/*     */   @Comment("歸還日期")
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   private Date returnDate;
/*     */   @Comment("卡片有效開始日期")
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   private Date cardStartDate;
/*     */   @Comment("卡片有效結束日期")
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   private Date cardEndDate;
/*     */   @Comment("是否已發卡")
/*     */   @Column(nullable = false)
/*     */   private Boolean issued;
/*     */   
/*     */   public String getId() {
/* 145 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 149 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getAba() {
/* 153 */     return this.aba;
/*     */   }
/*     */   
/*     */   public void setAba(String aba) {
/* 157 */     this.aba = aba;
/*     */   }
/*     */   
/*     */   public String getWeg1() {
/* 161 */     return this.weg1;
/*     */   }
/*     */   
/*     */   public void setWeg1(String weg1) {
/* 165 */     this.weg1 = weg1;
/*     */   }
/*     */   
/*     */   public String getWeg2() {
/* 169 */     return this.weg2;
/*     */   }
/*     */   
/*     */   public void setWeg2(String weg2) {
/* 173 */     this.weg2 = weg2;
/*     */   }
/*     */   
/*     */   public Date getStartDate() {
/* 177 */     return this.startDate;
/*     */   }
/*     */   
/*     */   public void setStartDate(Date startDate) {
/* 181 */     this.startDate = startDate;
/*     */   }
/*     */   
/*     */   public Date getEndDate() {
/* 185 */     return this.endDate;
/*     */   }
/*     */   
/*     */   public void setEndDate(Date endDate) {
/* 189 */     this.endDate = endDate;
/*     */   }
/*     */   
/*     */   public CardStatus getCardStatus() {
/* 193 */     return this.cardStatus;
/*     */   }
/*     */   
/*     */   public void setCardStatus(CardStatus cardStatus) {
/* 197 */     this.cardStatus = cardStatus;
/*     */   }
/*     */   
/*     */   public String getMemo() {
/* 201 */     return this.memo;
/*     */   }
/*     */   
/*     */   public void setMemo(String memo) {
/* 205 */     this.memo = memo;
/*     */   }
/*     */   
/*     */   public String getCompany() {
/* 209 */     return this.company;
/*     */   }
/*     */   
/*     */   public void setCompany(String company) {
/* 213 */     this.company = company;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 217 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/* 221 */     this.name = name;
/*     */   }
/*     */   
/*     */   public String getIdNo() {
/* 225 */     return this.idNo;
/*     */   }
/*     */   
/*     */   public void setIdNo(String idNo) {
/* 229 */     this.idNo = idNo;
/*     */   }
/*     */   
/*     */   public String getEmployeeNo() {
/* 233 */     return this.employeeNo;
/*     */   }
/*     */   
/*     */   public void setEmployeeNo(String employeeNo) {
/* 237 */     this.employeeNo = employeeNo;
/*     */   }
/*     */   
/*     */   public String getJobTitle() {
/* 241 */     return this.jobTitle;
/*     */   }
/*     */   
/*     */   public void setJobTitle(String jobTitle) {
/* 245 */     this.jobTitle = jobTitle;
/*     */   }
/*     */   
/*     */   public String getTel() {
/* 249 */     return this.tel;
/*     */   }
/*     */   
/*     */   public void setTel(String tel) {
/* 253 */     this.tel = tel;
/*     */   }
/*     */   
/*     */   public String getMobile() {
/* 257 */     return this.mobile;
/*     */   }
/*     */   
/*     */   public void setMobile(String mobile) {
/* 261 */     this.mobile = mobile;
/*     */   }
/*     */   
/*     */   public CardType getCardType() {
/* 265 */     return this.cardType;
/*     */   }
/*     */   
/*     */   public void setCardType(CardType cardType) {
/* 269 */     this.cardType = cardType;
/*     */   }
/*     */   
/*     */   public String getGroupName() {
/* 273 */     return this.groupName;
/*     */   }
/*     */   
/*     */   public void setGroupName(String groupName) {
/* 277 */     this.groupName = groupName;
/*     */   }
/*     */   
/*     */   public String getRoleId() {
/* 281 */     return this.roleId;
/*     */   }
/*     */   
/*     */   public void setRoleId(String roleId) {
/* 285 */     this.roleId = roleId;
/*     */   }
/*     */   
/*     */   public Date getIssueDate() {
/* 289 */     return this.issueDate;
/*     */   }
/*     */   
/*     */   public void setIssueDate(Date issueDate) {
/* 293 */     this.issueDate = issueDate;
/*     */   }
/*     */   
/*     */   public Date getExpectedReturnDate() {
/* 297 */     return this.expectedReturnDate;
/*     */   }
/*     */   
/*     */   public void setExpectedReturnDate(Date expectedReturnDate) {
/* 301 */     this.expectedReturnDate = expectedReturnDate;
/*     */   }
/*     */   
/*     */   public Date getReturnDate() {
/* 305 */     return this.returnDate;
/*     */   }
/*     */   
/*     */   public void setReturnDate(Date returnDate) {
/* 309 */     this.returnDate = returnDate;
/*     */   }
/*     */   
/*     */   public Date getCardStartDate() {
/* 313 */     return this.cardStartDate;
/*     */   }
/*     */   
/*     */   public void setCardStartDate(Date cardStartDate) {
/* 317 */     this.cardStartDate = cardStartDate;
/*     */   }
/*     */   
/*     */   public Date getCardEndDate() {
/* 321 */     return this.cardEndDate;
/*     */   }
/*     */   
/*     */   public void setCardEndDate(Date cardEndDate) {
/* 325 */     this.cardEndDate = cardEndDate;
/*     */   }
/*     */   
/*     */   public Boolean getIssued() {
/* 329 */     return this.issued;
/*     */   }
/*     */   
/*     */   public void setIssued(Boolean issued) {
/* 333 */     this.issued = issued;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 338 */     int prime = 31;
/* 339 */     int result = 1;
/* 340 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 341 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 346 */     if (this == obj) return true; 
/* 347 */     if (obj == null) return false; 
/* 348 */     if (getClass() != obj.getClass()) return false; 
/* 349 */     RoomCardConfig other = (RoomCardConfig)obj;
/* 350 */     if (this.id == null)
/* 351 */     { if (other.id != null) return false;  }
/* 352 */     else if (!this.id.equals(other.id)) { return false; }
/* 353 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 358 */     return "RoomCardConfig [id=" + this.id + ", aba=" + this.aba + ", weg1=" + this.weg1 + ", weg2=" + this.weg2 + ", startDate=" + this.startDate + ", endDate=" + this.endDate + ", cardStatus=" + this.cardStatus + ", memo=" + this.memo + ", company=" + this.company + ", name=" + this.name + ", idNo=" + this.idNo + ", employeeNo=" + this.employeeNo + ", jobTitle=" + this.jobTitle + ", tel=" + this.tel + ", mobile=" + this.mobile + ", cardType=" + this.cardType + ", groupName=" + this.groupName + ", roleId=" + this.roleId + ", issueDate=" + this.issueDate + ", returnDate=" + this.returnDate + ", cardStartDate=" + this.cardStartDate + ", cardEndDate=" + this.cardEndDate + ", issued=" + this.issued + "]";
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\fm\model\RoomCardConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */