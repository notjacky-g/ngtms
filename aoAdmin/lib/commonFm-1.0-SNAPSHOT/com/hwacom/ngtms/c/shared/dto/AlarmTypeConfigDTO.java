/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class AlarmTypeConfigDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -6252394722701989918L;
  /** 警報型態 */
  private String alarmType;
  /** 警報種類說明 */
  private String description;
  /** 是否屬也是 反應計畫 事件型態 */
  private Boolean rpsEvent;
  /** 嚴重程度 */
  private Integer severity = 0;
  /** 對應到 反應計畫產生軟體內的 邏輯名稱 （規則庫內的某個規則名稱） */
  private String rspLogicType;

  /** 所有AlarmType，前端才會用到此欄位 */
  private Boolean all = false;

  public String getAlarmType() {
    return alarmType;
  }

  public void setAlarmType(String alarmType) {
    this.alarmType = alarmType;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getRpsEvent() {
    return rpsEvent;
  }

  public void setRpsEvent(Boolean rpsEvent) {
    this.rpsEvent = rpsEvent;
  }

  public Integer getSeverity() {
    return severity;
  }

  public void setSeverity(Integer severity) {
    this.severity = severity;
  }

  public String getRspLogicType() {
    return rspLogicType;
  }

  public void setRspLogicType(String rspLogicType) {
    this.rspLogicType = rspLogicType;
  }

  public Boolean getAll() {
    return all;
  }

  public void setAll(Boolean all) {
    this.all = all;
  }
  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    // 這裏return alarmType 是為了給 AlarmSubTypeConfigViewerImpl SimpleComboBox
    // 使用，不能更改。
    return alarmType;
  }

  public String toInfoString() {
    StringBuilder builder = new StringBuilder();
    builder.append("AlarmTypeConfigDTO [alarmType=");
    builder.append(alarmType);
    builder.append(", description=");
    builder.append(description);
    builder.append(", rpsEvent=");
    builder.append(rpsEvent);
    builder.append(", severity=");
    builder.append(severity);
    builder.append(", rspLogicType=");
    builder.append(rspLogicType);
    builder.append("]");
    return builder.toString();
  }
}
