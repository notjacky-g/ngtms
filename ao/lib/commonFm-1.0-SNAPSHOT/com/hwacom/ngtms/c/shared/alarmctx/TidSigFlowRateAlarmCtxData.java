/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.alarmctx;

import com.hwacom.ngtms.c.shared.AlarmContextData;

/** @author jeff.ku */
public class TidSigFlowRateAlarmCtxData implements AlarmContextData {

  private String rmsId;

  /**
   * 設備代碼(道路代碼-方向-交流道里程)，交流道里程採國道高速公路局發布之交流道中心里程(http://www.freeway.gov.tw/
   * Publish.aspx?cnid=1288)，如N1-N-025即表國1北向台北交流道
   */
  private String rampId;

  /** 5分鐘需求流率(輛/五分鐘) */
  private Integer flowRate;

  @Override
  public String toString() {
    return "TidSigAlarmCtxData{"
        + "rmsId="
        + rmsId
        + ", rampId="
        + rampId
        + ", flowRate="
        + flowRate
        + '}';
  }

  @Override
  public void decode(String strData) {
    if (strData != null) {
      String[] parts = strData.split(";");
      if ((parts.length > 0) && (parts[0].length() > 0)) {
        rmsId = parts[0];
      }
      if ((parts.length > 1) && (parts[1].length() > 0)) {
        rampId = parts[1];
      }
      if ((parts.length > 2) && (parts[2].length() > 0)) {
        flowRate = Integer.valueOf(parts[2]);
      }
    }
  }

  @Override
  public String encode() {
    StringBuilder sb = new StringBuilder();
    if (rmsId != null) sb.append(rmsId);
    sb.append(';');
    if (rampId != null) sb.append(rampId);
    sb.append(';');
    if (flowRate != null) sb.append(flowRate);
    sb.append(';');
    return sb.toString();
  }

  public String getRmsId() {
    return rmsId;
  }

  public void setRmsId(String rmsId) {
    this.rmsId = rmsId;
  }

  public String getRampId() {
    return rampId;
  }

  public void setRampId(String rampId) {
    this.rampId = rampId;
  }

  public Integer getFlowRate() {
    return flowRate;
  }

  public void setFlowRate(Integer flowRate) {
    this.flowRate = flowRate;
  }
}
