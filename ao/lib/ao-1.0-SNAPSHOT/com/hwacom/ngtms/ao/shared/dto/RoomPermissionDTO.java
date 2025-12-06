package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.Date;

public class RoomPermissionDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -503025048716403323L;

  //機房
  private String room;
  //人名
  private String name;
  //卡號
  private String card;
  //卡片有效開始日期
  private Date cardStartDate;
  //卡片有效結束日期
  private Date cardEndDate;
  //公司
  private String company;
  //電話
  private String phone;

  public String getRoom() {
    return room;
  }

  public void setRoom(String room) {
    this.room = room;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCard() {
    return card;
  }

  public void setCard(String card) {
    this.card = card;
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

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
