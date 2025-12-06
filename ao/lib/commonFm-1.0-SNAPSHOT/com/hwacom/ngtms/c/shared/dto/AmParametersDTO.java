/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.List;

/**
 * Rest 傳送相關參數
 *
 * @author brian.cheng
 */
public class AmParametersDTO implements Serializable, IsSerializable {
  private static final long serialVersionUID = 6672347227909840987L;

  private String deviceType;
  private String groupId;
  private DeviceGroupDTO deviceGroupDTO;
  private List<String> deviceTypeList;
  private List<String> deviceNameList;
  private List<List<String>> csvData;
  private List<Integer> csvHeaderWidths;
  private String fmeName;

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public DeviceGroupDTO getDeviceGroupDTO() {
    return deviceGroupDTO;
  }

  public void setDeviceGroupDTO(DeviceGroupDTO deviceGroupDTO) {
    this.deviceGroupDTO = deviceGroupDTO;
  }

  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public List<String> getDeviceTypeList() {
    return deviceTypeList;
  }

  public void setDeviceTypeList(List<String> deviceTypeList) {
    this.deviceTypeList = deviceTypeList;
  }

  public List<String> getDeviceNameList() {
    return deviceNameList;
  }

  public void setDeviceNameList(List<String> deviceNameList) {
    this.deviceNameList = deviceNameList;
  }

  public List<List<String>> getCsvData() {
    return csvData;
  }

  public void setCsvData(List<List<String>> csvData) {
    this.csvData = csvData;
  }

  public List<Integer> getCsvHeaderWidths() {
    return csvHeaderWidths;
  }

  public void setCsvHeaderWidths(List<Integer> csvHeaderWidths) {
    this.csvHeaderWidths = csvHeaderWidths;
  }

  public String getFmeName() {
    return fmeName;
  }

  public void setFmeName(String fmeName) {
    this.fmeName = fmeName;
  }
}
