/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum CmStateDTO implements IsSerializable {
  Wait,
  Prepare,
  Standby,
  Active,
  Restart,
  DisConnected
}
