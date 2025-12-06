/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am;

import com.hwacom.ngtms.hcce.am.view.HcceViewer;
import java.util.ArrayList;
import java.util.List;
import org.fusesource.restygwt.client.RestService;

/** @author johnson.lan */
public class HcceEP extends GwtEntryPoint {

  public HcceEP() {
    super(GwtEntryPoint.HCCE_DR_SWITCH_EP_NAME);
  }

  @Override
  protected void allServicesReady() {
    init(new HcceViewer());
  }

  @Override
  protected List<RestService> getAdditionalRestServices() {
    List<RestService> list = new ArrayList<>(super.getAdditionalRestServices());
    list.add(backupSystemHcceService);
    return list;
  }
}
