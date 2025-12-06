/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.dto;

import java.io.Serializable;
import java.util.List;

public class RoomSetAndUnSetConfigDTO implements Serializable {

  private static final long serialVersionUID = -3647795514574175775L;

  private List<RoomSvgGroupConifgDTO> setGroupConfigList;

  private List<RoomSvgGroupConifgDTO> unsetGroupConfigList;

  public List<RoomSvgGroupConifgDTO> getSetGroupConfigList() {
    return setGroupConfigList;
  }

  public void setSetGroupConfigList(List<RoomSvgGroupConifgDTO> setGroupConfigList) {
    this.setGroupConfigList = setGroupConfigList;
  }

  public List<RoomSvgGroupConifgDTO> getUnsetGroupConfigList() {
    return unsetGroupConfigList;
  }

  public void setUnsetGroupConfigList(List<RoomSvgGroupConifgDTO> unsetGroupConfigList) {
    this.unsetGroupConfigList = unsetGroupConfigList;
  }
}
