package com.hwacom.ngtms.room.shared.dto;

import java.io.Serializable;
import java.util.Map;

public class RoomCardPermissionTreeDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer id;

  private String locationName;

  /** Map<key, value> key= description + subLocation, value =deviceName(卡機) */
  private Map<String, String> cardReaderLocation;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLocationName() {
    return locationName;
  }

  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }

  public Map<String, String> getCardReaderLocation() {
    return cardReaderLocation;
  }

  public void setCardReaderLocation(Map<String, String> cardReaderLocation) {
    this.cardReaderLocation = cardReaderLocation;
  }
}
