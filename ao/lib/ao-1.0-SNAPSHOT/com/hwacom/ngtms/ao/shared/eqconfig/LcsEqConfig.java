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
 * <p>element lcs_data
 *
 * @author jeff.ku
 */
public class LcsEqConfig implements Serializable {

  private static final long serialVersionUID = -782980683143009689L;

  private List<Lcs> lcs = new ArrayList<>();

  public List<Lcs> getLcs() {
    return lcs;
  }

  public void setLcs(List<Lcs> lcs) {
    this.lcs = lcs;
  }
}
