package com.hwacom.ngtms.room.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.room.shared.CardStatus;
import com.hwacom.ngtms.room.shared.CardType;
import java.io.Serializable;
import java.util.Date;

public class RoomCardConfigDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 1L;

  private String id;

  /** ABA 編號 */
  private String aba;

  /** WEG1編號,由ABA推算的內碼 */
  private String weg1;

  /** WEG2編號,由ABA推算的內碼 */
  private String weg2;

  /** 實際可進入機房的開始日期 */
  private Date startDate;

  /** 實際可進入機房的結束日期 */
  private Date endDate;

  /** 卡片狀態 */
  private CardStatus cardStatus;

  /** 備註 */
  private String memo;

  /** 公司 */
  private String company;

  /** 名稱 */
  private String name;

  /** 身分證號碼 */
  private String idNo;

  /** 職工編號 */
  private String employeeNo;

  /** 職稱 */
  private String jobTitle;

  /** 電話 */
  private String tel;

  /** 手機 */
  private String mobile;

  /** 卡片種類 */
  private CardType cardType;

  /** 定期卡群組名稱 */
  private String groupName;

  /** 角色編號 */
  private String roleId;

  /** 發卡日期 */
  private Date issueDate;

  /** 預計歸還日期 */
  private Date expectedReturnDate;

  /** 歸還日期 */
  private Date returnDate;

  /** 卡片有效開始日期 */
  private Date cardStartDate;

  /** 卡片有效結束日期 */
  private Date cardEndDate;

  /** 是否已發卡 */
  private Boolean issued;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAba() {
    return aba;
  }

  public void setAba(String aba) {
    this.aba = aba;
  }

  public String getWeg1() {
    return weg1;
  }

  public void setWeg1(String weg1) {
    this.weg1 = weg1;
  }

  public String getWeg2() {
    return weg2;
  }

  public void setWeg2(String weg2) {
    this.weg2 = weg2;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public CardStatus getCardStatus() {
    return cardStatus;
  }

  public void setCardStatus(CardStatus cardStatus) {
    this.cardStatus = cardStatus;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIdNo() {
    return idNo;
  }

  public void setIdNo(String idNo) {
    this.idNo = idNo;
  }

  public String getEmployeeNo() {
    return employeeNo;
  }

  public void setEmployeeNo(String employeeNo) {
    this.employeeNo = employeeNo;
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }

  public String getTel() {
    return tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public CardType getCardType() {
    return cardType;
  }

  public void setCardType(CardType cardType) {
    this.cardType = cardType;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public Date getIssueDate() {
    return issueDate;
  }

  public void setIssueDate(Date issueDate) {
    this.issueDate = issueDate;
  }

  public Date getExpectedReturnDate() {
    return expectedReturnDate;
  }

  public void setExpectedReturnDate(Date expectedReturnDate) {
    this.expectedReturnDate = expectedReturnDate;
  }

  public Date getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(Date returnDate) {
    this.returnDate = returnDate;
  }

  public Date getCardStartDate() {
    return cardStartDate;
  }

  public void setCardStartDate(Date cardStartDate) {
    this.cardStartDate = cardStartDate;
  }

  public Date getCardEndDate() {
    return cardEndDate;
  }

  public void setCardEndDate(Date cardEndDate) {
    this.cardEndDate = cardEndDate;
  }

  public Boolean getIssued() {
    return issued;
  }

  public void setIssued(Boolean issued) {
    this.issued = issued;
  }

  @Override
  public String toString() {
    return "RoomCardConfigDTO [id="
        + id
        + ", aba="
        + aba
        + ", weg1="
        + weg1
        + ", weg2="
        + weg2
        + ", startDate="
        + startDate
        + ", endDate="
        + endDate
        + ", cardStatus="
        + cardStatus
        + ", memo="
        + memo
        + ", company="
        + company
        + ", name="
        + name
        + ", idNo="
        + idNo
        + ", employeeNo="
        + employeeNo
        + ", jobTitle="
        + jobTitle
        + ", tel="
        + tel
        + ", mobile="
        + mobile
        + ", cardType="
        + cardType
        + ", groupName="
        + groupName
        + ", roleId="
        + roleId
        + ", issueDate="
        + issueDate
        + ", returnDate="
        + returnDate
        + ", cardStartDate="
        + cardStartDate
        + ", cardEndDate="
        + cardEndDate
        + ", issued="
        + issued
        + "]";
  }
}
