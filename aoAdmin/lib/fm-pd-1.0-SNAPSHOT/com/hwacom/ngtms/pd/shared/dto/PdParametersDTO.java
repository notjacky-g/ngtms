package com.hwacom.ngtms.pd.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.List;

/**
 * Rest 傳送相關參數
 *
 * @author huei.yang
 */
public class PdParametersDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 1L;

  String pdDeviceName;

  List<LoopDeviceConfigDTO> loopDeviceConfigDTOs;

  public String getPdDeviceName() {
    return pdDeviceName;
  }

  public void setPdDeviceName(String pdDeviceName) {
    this.pdDeviceName = pdDeviceName;
  }

  public List<LoopDeviceConfigDTO> getLoopDeviceConfigDTOs() {
    return loopDeviceConfigDTOs;
  }

  public void setLoopDeviceConfigDTOs(List<LoopDeviceConfigDTO> loopDeviceConfigDTOs) {
    this.loopDeviceConfigDTOs = loopDeviceConfigDTOs;
  }
}
