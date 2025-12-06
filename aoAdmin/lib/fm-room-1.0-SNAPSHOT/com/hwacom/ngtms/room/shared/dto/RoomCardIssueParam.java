package com.hwacom.ngtms.room.shared.dto;

import java.io.Serializable;
import java.util.Map;

/** 機房門禁卡片發卡、修改之參數 */
public class RoomCardIssueParam implements Serializable {

  private static final long serialVersionUID = 1L;

  private RoomCardConfigDTO roomCardConfigDto;

  //Map<key, value> key:卡機名稱、value:true = 加卡，false = 刪卡
  private Map<String, Boolean> cardReaderAddCardMap;

  public RoomCardConfigDTO getRoomCardConfigDto() {
    return roomCardConfigDto;
  }

  public void setRoomCardConfigDto(RoomCardConfigDTO roomCardConfigDto) {
    this.roomCardConfigDto = roomCardConfigDto;
  }

  public Map<String, Boolean> getCardReaderAddCardMap() {
    return cardReaderAddCardMap;
  }

  public void setCardReaderAddCardMap(Map<String, Boolean> cardReaderAddCardMap) {
    this.cardReaderAddCardMap = cardReaderAddCardMap;
  }
}
