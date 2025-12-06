/*
 * © HwaCom Systems Inc. 2017
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.rtu.am;

import com.google.gwt.core.shared.GWT;
import com.hwacom.ngtms.cam.client.HomeEP;
import com.hwacom.ngtms.common.am.AmEntryPoint;
import com.hwacom.ngtms.rtu.am.restygwt.RtuCommonRestService;
import com.sencha.gxt.widget.core.client.Composite;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import org.fusesource.restygwt.client.RestService;

public class RtuEP extends AmEntryPoint {

  public static final RtuCommonRestService rtuCommonRestService =
      GWT.create(RtuCommonRestService.class);

  public RtuEP() {
    super("RTU", Arrays.asList(new SimpleEntry<>("Rtu", rtuCommonRestService)), HomeEP.camService);
  }

  @Override
  protected void serviceReady(RestService service, String fmeAddress, int port) {
    if (RtuEP.rtuCommonRestService == service) {
      webSocketIp = fmeAddress;
      webSocketPort = port;
    }
    super.serviceReady(service, fmeAddress, port);
  }

  @Override
  protected void allServicesReady() {
    init(new Composite());
  }
}
