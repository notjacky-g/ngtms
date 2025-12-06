/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.eqconfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * oneday_eq_config_data
 *
 * <p>element iid_data
 *
 * @author jeff.ku
 */
public class IidEqConfig implements Serializable {

  private static final long serialVersionUID = -2589417791033497545L;

  private List<Iid> iid = new ArrayList<>();

  public List<Iid> getIid() {
    return iid;
  }

  public void setIid(List<Iid> iid) {
    this.iid = iid;
  }
}
