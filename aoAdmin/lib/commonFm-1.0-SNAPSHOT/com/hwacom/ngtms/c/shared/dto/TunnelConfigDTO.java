package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.c.shared.Direction;
import java.io.Serializable;

public class TunnelConfigDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 1L;

  /**
   * 隧道ID,由道路id+隧道方向+里程而定 ex:T9N-N-17600-19900, 不是隧道機電Pc給我們tunnel Id, 隧道機電給我們的Id, 放在 tunnelCode欄位內}
   */
  private String tunnelId;

  /** 隧道名稱 */
  private String tunnelName;

  /** 隧道的通訊協定代碼; 機電Pc給我們tunnel Id, */
  private Integer tunnelCode;

  /**
   * 道路分區ID
   *
   * @see {@link com.hwacom.ngtms.common.fm.model.RoadSection RoadSection}
   */
  private String sectionId;

  /** 起始里程 */
  private Integer startMileage;

  /** 結束里程 */
  private Integer endMileage;

  /** 道路ID */
  private String lineId;

  /** 方向 */
  private Direction direction;

  public TunnelConfigDTO() {}

  public TunnelConfigDTO(String tunnelId, String tunnelName, Integer tunnelCode) {
    this.tunnelId = tunnelId;
    this.tunnelName = tunnelName;
    this.tunnelCode = tunnelCode;
  }

  public String getTunnelId() {
    return tunnelId;
  }

  public void setTunnelId(String tunnelId) {
    this.tunnelId = tunnelId;
  }

  public String getTunnelName() {
    return tunnelName;
  }

  public void setTunnelName(String tunnelName) {
    this.tunnelName = tunnelName;
  }

  public Integer getTunnelCode() {
    return tunnelCode;
  }

  public void setTunnelCode(Integer tunnelCode) {
    this.tunnelCode = tunnelCode;
  }

  public String getSectionId() {
    return sectionId;
  }

  public void setSectionId(String sectionId) {
    this.sectionId = sectionId;
  }

  public Integer getStartMileage() {
    return startMileage;
  }

  public void setStartMileage(Integer startMileage) {
    this.startMileage = startMileage;
  }

  public Integer getEndMileage() {
    return endMileage;
  }

  public void setEndMileage(Integer endMileage) {
    this.endMileage = endMileage;
  }

  public String getLineId() {
    return lineId;
  }

  public void setLineId(String lineId) {
    this.lineId = lineId;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((tunnelId == null) ? 0 : tunnelId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    TunnelConfigDTO other = (TunnelConfigDTO) obj;
    if (tunnelId == null) {
      if (other.tunnelId != null) return false;
    } else if (!tunnelId.equals(other.tunnelId)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "TunnelConfigDTO [tunnelId="
        + tunnelId
        + ", tunnelName="
        + tunnelName
        + ", tunnelCode="
        + tunnelCode
        + ", sectionId="
        + sectionId
        + ", startMileage="
        + startMileage
        + ", endMileage="
        + endMileage
        + ", lineId"
        + lineId
        + "]";
  }
}
