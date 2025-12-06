/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public enum ClusterModeDTO implements Serializable, IsSerializable {
  Standby,
  Active,
  DisConnected;

  public static ClusterModeDTO getMode(String mode) {
    if (Standby.toString().equals(mode)) return ClusterModeDTO.Standby;
    else if (Active.toString().equals(mode)) return ClusterModeDTO.Active;
    else return ClusterModeDTO.DisConnected;
  }
}
