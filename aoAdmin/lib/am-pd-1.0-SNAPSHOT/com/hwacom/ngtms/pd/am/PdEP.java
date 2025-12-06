/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.pd.am;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.cam.client.HomeEP;
import com.hwacom.ngtms.common.am.AmEntryPoint;
import com.hwacom.ngtms.pd.am.restygwt.PdCommonRestService;
import com.hwacom.ngtms.pd.am.view.Messages;
import com.hwacom.ngtms.pd.am.view.PdViewer;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import org.fusesource.restygwt.client.RestService;

public class PdEP extends AmEntryPoint {

  public static Messages messages = GWT.create(Messages.class);

  public static PdCommonRestService pdCommonService = GWT.create(PdCommonRestService.class);

  public PdEP() {
    super(
        messages.titleView_pd(),
        Arrays.asList(new SimpleEntry<>("Pd", pdCommonService)),
        HomeEP.camService);
  }

  @Override
  protected void serviceReady(RestService service, String fmeAddress, int port) {
    if (PdEP.pdCommonService == service) {
      webSocketIp = fmeAddress;
      webSocketPort = port;
    }
    super.serviceReady(service, fmeAddress, port);
  }

  @Override
  protected void allServicesReady() {
    init(new PdViewer());
  }
}
