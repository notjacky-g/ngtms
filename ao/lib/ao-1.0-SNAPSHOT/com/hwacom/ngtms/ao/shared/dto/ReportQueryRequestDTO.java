/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** @author YiHsing Chen */
public class ReportQueryRequestDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 1246672152458659499L;

  private String queryWidgetFullName;

  private String deviceType;

  private List<ChartDTO> charts = new ArrayList<>();

  public ReportQueryRequestDTO() {}

  public String getQueryWidgetFullName() {
    return queryWidgetFullName;
  }

  public void setQueryWidgetFullName(String queryWidgetFullName) {
    this.queryWidgetFullName = queryWidgetFullName;
  }

  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public List<ChartDTO> getCharts() {
    return charts;
  }

  public void setCharts(List<ChartDTO> charts) {
    this.charts = charts;
  }

  @Override
  public String toString() {
    return "ReportQueryRequestDTO [queryWidgetFullName=" + queryWidgetFullName + "]";
  }
}
