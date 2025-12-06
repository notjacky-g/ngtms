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
 * <p>element fwl_data
 *
 * @author jeff.ku
 */
public class FwlEqConfig implements Serializable {

  private static final long serialVersionUID = -2589417791033497545L;

  private List<Fwl> fwl = new ArrayList<>();

  public List<Fwl> getFwl() {
    return fwl;
  }

  public void setFwl(List<Fwl> fwl) {
    this.fwl = fwl;
  }
}
