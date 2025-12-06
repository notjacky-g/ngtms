/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.c.shared.SubSystem;
import com.hwacom.ngtms.c.shared.dto.CommonParametersDTO;
import java.io.Serializable;
import java.util.Map;

/**
 * Rest 傳送相關參數
 *
 * @author brian.cheng
 */
public class RptParametersDTO extends CommonParametersDTO implements Serializable, IsSerializable {
  private static final long serialVersionUID = -1234975920059729290L;

  private String lineId;
  private String direction;
  private String sectionId;
  private String deviceTypeInStr;
  private ReportQueryConditionDTO reportQueryConditionDTO;
  private String reportName;
  private String reportFormat;
  private String printer;
  private String reportId;
  private Map<String, Object> inputParameter;
  private Map<String, String> inputParameterInStr;
  private String id;
  private String deviceType;
  private String startLineId;
  private String startDirection;
  private String endLineId;
  private String endDirection;
  private SubSystem subSystem;

  public String getLineId() {
    return lineId;
  }

  public void setLineId(String lineId) {
    this.lineId = lineId;
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }

  public String getSectionId() {
    return sectionId;
  }

  public void setSectionId(String sectionId) {
    this.sectionId = sectionId;
  }

  public String getDeviceTypeInStr() {
    return deviceTypeInStr;
  }

  public void setDeviceTypeInStr(String deviceTypeInStr) {
    this.deviceTypeInStr = deviceTypeInStr;
  }

  public ReportQueryConditionDTO getReportQueryConditionDTO() {
    return reportQueryConditionDTO;
  }

  public void setReportQueryConditionDTO(ReportQueryConditionDTO reportQueryConditionDTO) {
    this.reportQueryConditionDTO = reportQueryConditionDTO;
  }

  public String getReportName() {
    return reportName;
  }

  public void setReportName(String reportName) {
    this.reportName = reportName;
  }

  public String getReportFormat() {
    return reportFormat;
  }

  public void setReportFormat(String reportFormat) {
    this.reportFormat = reportFormat;
  }

  public String getPrinter() {
    return printer;
  }

  public void setPrinter(String printer) {
    this.printer = printer;
  }

  public String getReportId() {
    return reportId;
  }

  public void setReportId(String reportId) {
    this.reportId = reportId;
  }

  public Map<String, Object> getInputParameter() {
    return inputParameter;
  }

  public void setInputParameter(Map<String, Object> inputParameter) {
    this.inputParameter = inputParameter;
  }

  public Map<String, String> getInputParameterInStr() {
    return inputParameterInStr;
  }

  public void setInputParameterInStr(Map<String, String> inputParameterInStr) {
    this.inputParameterInStr = inputParameterInStr;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public String getStartLineId() {
    return startLineId;
  }

  public void setStartLineId(String startLineId) {
    this.startLineId = startLineId;
  }

  public String getStartDirection() {
    return startDirection;
  }

  public void setStartDirection(String startDirection) {
    this.startDirection = startDirection;
  }

  public String getEndLineId() {
    return endLineId;
  }

  public void setEndLineId(String endLineId) {
    this.endLineId = endLineId;
  }

  public String getEndDirection() {
    return endDirection;
  }

  public void setEndDirection(String endDirection) {
    this.endDirection = endDirection;
  }

  public SubSystem getSubSystem() {
    return subSystem;
  }

  public void setSubSystem(SubSystem subSystem) {
    this.subSystem = subSystem;
  }
}
