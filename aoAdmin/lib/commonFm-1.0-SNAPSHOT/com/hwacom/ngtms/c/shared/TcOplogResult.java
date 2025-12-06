package com.hwacom.ngtms.c.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.base.oplog.shared.OperationItem;
import com.hwacom.ngtms.ncc.remote.TcResponse;
import java.io.Serializable;

/**
 * 對 TC 操作需要的參數，用於記錄 oplog
 *
 * @author brian.cheng
 */
public class TcOplogResult implements Serializable, IsSerializable {

  private static final long serialVersionUID = -1649390971230962152L;

  private String ip;
  private String userId;
  private String logMessage;
  private OperationItem operationItem;
  private SubSystem subSystem;
  private Integer cmdId;
  private String deviceName;
  private String remark;
  private TcResponse tcResponse;

  public TcOplogResult(String ip, String userId) {
    this.ip = ip;
    this.userId = userId;
  }

  public TcOplogResult(String ip, String userId, OperationItem operationItem, SubSystem subSystem) {
    this.ip = ip;
    this.userId = userId;
    this.operationItem = operationItem;
    this.subSystem = subSystem;
  }

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

  public String getLogMessage() {
    return logMessage;
  }

  public void setLogMessage(String logMessage) {
    this.logMessage = logMessage;
  }

  public OperationItem getOperationItem() {
    return operationItem;
  }

  public void setOperationItem(OperationItem operationItem) {
    this.operationItem = operationItem;
  }

  public SubSystem getSubSystem() {
    return subSystem;
  }

  public void setSubSystem(SubSystem subSystem) {
    this.subSystem = subSystem;
  }

  public Integer getCmdId() {
    return cmdId;
  }

  public void setCmdId(Integer cmdId) {
    this.cmdId = cmdId;
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

  public TcResponse getTcResponse() {
    return tcResponse;
  }

  public void setTcResponse(TcResponse tcResponse) {
    this.tcResponse = tcResponse;
  }

  @Override
  public String toString() {
    return "TcParameters= ip:"
        + ip
        + ", userId:"
        + userId
        + ", deviceName:"
        + deviceName
        + ", response:"
        + tcResponse.getResult()
        + ", cmdId:"
        + cmdId
        + ", reamrk:"
        + remark
        + ", logMessage:"
        + logMessage
        + ", operationItem:"
        + operationItem;
  }
}
