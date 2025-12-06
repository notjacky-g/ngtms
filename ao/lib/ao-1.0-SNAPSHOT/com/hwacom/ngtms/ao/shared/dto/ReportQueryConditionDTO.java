/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/** @author bryan.lin */
public class ReportQueryConditionDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -1711312994843592353L;

  private String reportId;

  private Map<String, Object> inputParameter = new HashMap<String, Object>();

  private int offset;

  private float zoomRatio;

  /** @return the reportId */
  public String getReportId() {
    return reportId;
  }

  /** @param reportId the reportId to set */
  public void setReportId(String reportId) {
    this.reportId = reportId;
  }

  /** @return the inputParameter */
  public Map<String, Object> getInputParameter() {
    return inputParameter;
  }

  /** @param inputParameter the inputParameter to set */
  public void setInputParameter(Map<String, Object> inputParameter) {
    this.inputParameter = inputParameter;
  }

  /** @return the offset */
  public int getOffset() {
    return offset;
  }

  /** @param offset the offset to set */
  public void setOffset(int offset) {
    this.offset = offset;
  }

  /** @return the zoomRatio */
  public float getZoomRatio() {
    return zoomRatio;
  }

  /** @param zoomRatio the zoomRatio to set */
  public void setZoomRatio(float zoomRatio) {
    this.zoomRatio = zoomRatio;
  }
}
