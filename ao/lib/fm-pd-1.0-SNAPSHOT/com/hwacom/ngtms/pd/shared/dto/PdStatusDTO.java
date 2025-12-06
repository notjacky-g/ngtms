package com.hwacom.ngtms.pd.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.Date;

public class PdStatusDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 1L;

  private String pdId;

  private String pdDisplayName;

  private Date dataTime;

  /** 連線狀態 0:連線,1:斷線 */
  private Integer connectivity;

  /** 箱門開啟 1:開啟,0:關閉 */
  private Integer doorOpen;

  /** 0:正常,1:異常 */
  private Integer primaryR;

  /** 0:正常,1:異常 */
  private Integer primaryS;

  /** 0:正常,1:異常 */
  private Integer primaryT;

  /** 0:正常,1:異常 */
  private Integer secondaryR;

  /** 0:正常,1:異常 */
  private Integer secondaryS;

  /** 0:正常,1:異常 */
  private Integer secondaryT;

  /** 0:正常,1:異常 */
  private Integer loop1Status;

  /** 0:正常,1:異常 */
  private Integer loop2Status;

  /** 0:正常,1:異常 */
  private Integer loop3Status;

  /** 0:正常,1:異常 */
  private Integer loop4Status;

  /** 0:正常,1:異常 */
  private Integer loop5Status;

  private String lineId;

  public String getPdId() {
    return pdId;
  }

  public void setPdId(String pdId) {
    this.pdId = pdId;
  }

  public String getPdDisplayName() {
    return pdDisplayName;
  }

  public void setPdDisplayName(String pdDisplayName) {
    this.pdDisplayName = pdDisplayName;
  }

  public Date getDataTime() {
    return dataTime;
  }

  public void setDataTime(Date dataTime) {
    this.dataTime = dataTime;
  }

  public Integer getConnectivity() {
    return connectivity;
  }

  public void setConnectivity(Integer connectivity) {
    this.connectivity = connectivity;
  }

  public Integer getDoorOpen() {
    return doorOpen;
  }

  public void setDoorOpen(Integer doorOpen) {
    this.doorOpen = doorOpen;
  }

  public Integer getPrimaryR() {
    return primaryR;
  }

  public void setPrimaryR(Integer primaryR) {
    this.primaryR = primaryR;
  }

  public Integer getPrimaryS() {
    return primaryS;
  }

  public void setPrimaryS(Integer primaryS) {
    this.primaryS = primaryS;
  }

  public Integer getPrimaryT() {
    return primaryT;
  }

  public void setPrimaryT(Integer primaryT) {
    this.primaryT = primaryT;
  }

  public Integer getSecondaryR() {
    return secondaryR;
  }

  public void setSecondaryR(Integer secondaryR) {
    this.secondaryR = secondaryR;
  }

  public Integer getSecondaryS() {
    return secondaryS;
  }

  public void setSecondaryS(Integer secondaryS) {
    this.secondaryS = secondaryS;
  }

  public Integer getSecondaryT() {
    return secondaryT;
  }

  public void setSecondaryT(Integer secondaryT) {
    this.secondaryT = secondaryT;
  }

  public Integer getLoop1Status() {
    return loop1Status;
  }

  public void setLoop1Status(Integer loop1Status) {
    this.loop1Status = loop1Status;
  }

  public Integer getLoop2Status() {
    return loop2Status;
  }

  public void setLoop2Status(Integer loop2Status) {
    this.loop2Status = loop2Status;
  }

  public Integer getLoop3Status() {
    return loop3Status;
  }

  public void setLoop3Status(Integer loop3Status) {
    this.loop3Status = loop3Status;
  }

  public Integer getLoop4Status() {
    return loop4Status;
  }

  public void setLoop4Status(Integer loop4Status) {
    this.loop4Status = loop4Status;
  }

  public Integer getLoop5Status() {
    return loop5Status;
  }

  public void setLoop5Status(Integer loopStatus) {
    this.loop5Status = loopStatus;
  }

  public String getLineId() {
    return lineId;
  }

  public void setLineId(String lineId) {
    this.lineId = lineId;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((pdId == null) ? 0 : pdId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    PdStatusDTO other = (PdStatusDTO) obj;
    if (pdId == null) {
      if (other.pdId != null) return false;
    } else if (!pdId.equals(other.pdId)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "PdStatusDTO [pdId="
        + pdId
        + ", pdDisplayName="
        + pdDisplayName
        + ", dataTime="
        + dataTime
        + ", connectivity="
        + connectivity
        + ", doorOpen="
        + doorOpen
        + ", primaryR="
        + primaryR
        + ", primaryS="
        + primaryS
        + ", primaryT="
        + primaryT
        + ", secondaryR="
        + secondaryR
        + ", secondaryS="
        + secondaryS
        + ", secondaryT="
        + secondaryT
        + ", loop1Status="
        + loop1Status
        + ", loop2Status="
        + loop2Status
        + ", loop3Status="
        + loop3Status
        + ", loop4Status="
        + loop4Status
        + ", loop5Status="
        + loop5Status
        + ", lineId="
        + lineId
        + "]";
  }
}
