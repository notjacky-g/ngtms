/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.cam.restygwt.CommonRestService;
import com.hwacom.ngtms.cam.restygwt.RoadRestService;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.cam.view.road.RoadConfigViewer;
import com.hwacom.ngtms.common.am.AmEntryPoint;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import org.fusesource.restygwt.client.RestService;

public class RoadEP extends AmEntryPoint {
  public static final CommonRestService camService = GWT.create(CommonRestService.class);
  public static final RoadRestService roadService = GWT.create(RoadRestService.class);
  public static final Messages messages = GWT.create(Messages.class);

  public RoadEP() {
    super(
        messages.titleView_name(),
        Arrays.asList(
            new SimpleEntry<>("Common", camService), new SimpleEntry<>("Common", roadService)),
        HomeEP.camService);
  }

  @Override
  protected void serviceReady(RestService service, String fmeAddress, int port) {
    if (RoadEP.roadService == service) {
      webSocketIp = fmeAddress;
      webSocketPort = port;
    }
    super.serviceReady(service, fmeAddress, port);
  }

  @Override
  protected void allServicesReady() {
    init(new RoadConfigViewer());
  }
}
