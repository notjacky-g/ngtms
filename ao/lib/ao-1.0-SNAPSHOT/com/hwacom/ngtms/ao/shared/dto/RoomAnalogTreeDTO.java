package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.List;

public class RoomAnalogTreeDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -5903728260887693512L;

  private Type type;

  /** deviceName */
  private String id;

  /** locationName+subLocationName(有才加)+displayName */
  private String displayName;

  private AnalogType analogType;

  private List<RoomAnalogTreeDTO> children;

  public enum Type {
    /** 第一層 host_location */
    LOCATION,
    /** 第二層 room_analog */
    ANALOG
  }

  public enum AnalogType {
    /** 溫度 */
    TEMPERATURE,
    /** 濕度 */
    HUMIDITY,
    /** DC電壓 */
    VOLTAGE,
    /** 油槽容量 */
    OILTANK,
    /** 電流 */
    CURRENT,
    /** FM發射機 */
    FMT,
    /** 發射機輸出功率 */
    TRANSMITTERPOWER,
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public AnalogType getAnalogType() {
    return analogType;
  }

  public void setAnalogType(AnalogType analogType) {
    this.analogType = analogType;
  }

  public List<RoomAnalogTreeDTO> getChildren() {
    return children;
  }

  public void setChildren(List<RoomAnalogTreeDTO> children) {
    this.children = children;
  }
}
