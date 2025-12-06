package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class RoomNCUCardDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -9137438294634360277L;

  //卡片數量(1~N)
  List<String> cardNos;
  //Map<key, value> key:卡機名稱、value:true = 加卡，false = 刪卡
  private Map<String, Boolean> cardReaderAddCardMap;
  //更新卡片內容
  private boolean upDateCard;
  //是否允許進入三樓主機房
  private boolean accessControlDoor;
  /** 卡片有效開始日期 */
  private Date cardStartDate;
  /** 卡片有效結束日期 */
  private Date cardEndDate;

  public List<String> getCardNos() {
    return cardNos;
  }

  public void setCardNos(List<String> cardNos) {
    this.cardNos = cardNos;
  }

  public Map<String, Boolean> getCardReaderAddCardMap() {
    return cardReaderAddCardMap;
  }

  public void setCardReaderAddCardMap(Map<String, Boolean> cardReaderAddCardMap) {
    this.cardReaderAddCardMap = cardReaderAddCardMap;
  }

  public boolean isUpDateCard() {
    return upDateCard;
  }

  public void setUpDateCard(boolean upDateCard) {
    this.upDateCard = upDateCard;
  }

  public boolean isAccessControlDoor() {
    return accessControlDoor;
  }

  public void setAccessControlDoor(boolean accessControlDoor) {
    this.accessControlDoor = accessControlDoor;
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
}
