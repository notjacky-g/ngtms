/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class SMRDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -3464427325431192284L;

  Integer address;

  Integer upperLimit;

  Integer lowerLimit;

  public Integer getAddress() {
    return address;
  }

  public void setAddress(Integer address) {
    this.address = address;
  }

  public Integer getUpperLimit() {
    return upperLimit;
  }

  public void setUpperLimit(Integer upperLimit) {
    this.upperLimit = upperLimit;
  }

  public Integer getLowerLimit() {
    return lowerLimit;
  }

  public void setLowerLimit(Integer lowerLimit) {
    this.lowerLimit = lowerLimit;
  }
}
