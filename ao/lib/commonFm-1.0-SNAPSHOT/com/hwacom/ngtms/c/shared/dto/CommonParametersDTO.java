package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

/**
 * Rest 傳送相關參數 共用的參數設定 log 時會參考此 DTO 的內容
 *
 * @author brian.cheng
 */
public class CommonParametersDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -1567439209486252059L;

  private String ip;
  private String userId;
  private String deviceName;
  private String remark;
  private String logMessage;

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getLogMessage() {
    return logMessage;
  }

  public void setLogMessage(String logMessage) {
    this.logMessage = logMessage;
  }
}
