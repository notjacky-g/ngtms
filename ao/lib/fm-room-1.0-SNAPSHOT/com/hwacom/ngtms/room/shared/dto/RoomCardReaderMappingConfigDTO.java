package com.hwacom.ngtms.room.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class RoomCardReaderMappingConfigDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 1L;

  private String id;

  /** 卡片編號,對應ABA編號 */
  private String cardId;

  /** 讀卡機編號 */
  private String readerId;

  /** 是否加入讀卡機，避免二次發卡之檢查用 */
  private Boolean loginCardReader;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCardId() {
    return cardId;
  }

  public void setCardId(String cardId) {
    this.cardId = cardId;
  }

  public String getReaderId() {
    return readerId;
  }

  public void setReaderId(String readerId) {
    this.readerId = readerId;
  }

  public Boolean getLoginCardReader() {
    return loginCardReader;
  }

  public void setLoginCardReader(Boolean loginCardReader) {
    this.loginCardReader = loginCardReader;
  }
}
