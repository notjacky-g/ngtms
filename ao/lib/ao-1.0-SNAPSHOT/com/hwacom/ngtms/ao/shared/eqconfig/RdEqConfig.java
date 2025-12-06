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
 * <p>element rd_data
 *
 * @author jeff.ku
 */
public class RdEqConfig implements Serializable {

  private static final long serialVersionUID = -2589417791033497545L;

  private List<Rd> rd = new ArrayList<>();

  public List<Rd> getRd() {
    return rd;
  }

  public void setRd(List<Rd> rd) {
    this.rd = rd;
  }
}
